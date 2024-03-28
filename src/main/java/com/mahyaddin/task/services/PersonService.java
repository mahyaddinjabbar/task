package com.mahyaddin.task.services;

import com.mahyaddin.task.domain.*;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonLegalIdRepository personLegalIdRepository;

    @Autowired
    private PersonAddressRepository personAddressRepository;


    public void addHistoricalRecord(Person person) {
        personRepository.save(person);
    }

    public List<Person> getSnapshotOnDate(Date date, Optional<Integer> personId) {
        // Get the AuditReader
        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        // Retrieve the list of Person revisions as of the given date
        List<Number> personIds = (List<Number>) auditReader.getRevisions(Person.class, 0)
                .stream()
                .filter(rev -> {
                    Date revisionDate = auditReader.getRevisionDate(rev);
                    return !revisionDate.after(date);
                })
                .map(rev -> auditReader.createQuery()
                        .forEntitiesAtRevision(Person.class, rev)
                        .getResultList())
                .flatMap(List::stream)
                .map(person -> ((Person) person).getPersonId())
                .distinct()
                .collect(Collectors.toList());

        // If personId is provided, filter the list to include only that personId
        if (personId.isPresent()) {
            personIds = personIds.stream()
                    .filter(id -> id.equals(personId.get()))
                    .toList();
        }

        // Retrieve Person entities along with associated PersonLegalId and PersonAddress
        return personIds.stream()
                .map(personIdValue -> {
                    Person person = entityManager.find(Person.class, personIdValue);
                    List<PersonLegalId> legalIds = entityManager.createQuery(
                                    "SELECT l FROM person_legal_id l " +
                                            "WHERE l.person.personId = :personId " +
                                            "AND :date BETWEEN l.creationDate AND l.lastModified", PersonLegalId.class)
                            .setParameter("personId", personIdValue)
                            .setParameter("date", date)
                            .getResultList();
                    List<PersonAddress> addresses = entityManager.createQuery(
                                    "SELECT a FROM person_address a " +
                                            "WHERE a.person.personId = :personId " +
                                            "AND :date BETWEEN a.creationDate AND a.lastModified", PersonAddress.class)
                            .setParameter("personId", personIdValue)
                            .setParameter("date", date)
                            .getResultList();
                    person.setLegalIds(legalIds);
                    person.setPersonAddresses(addresses);
                    return person;
                })
                .collect(Collectors.toList());
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }


    public void addPersonRecords(List<Person> persons) {
        // Save each historical record to the database
        personRepository.saveAll(persons);
    }

    public void addAddressRecords(List<PersonAddress> addresses){

        for (PersonAddress address : addresses) {
            if (address.getAddressType() == null || address.getPerson() == null) {
                throw new RuntimeException("Null value detected in composite identifier of PersonAddress");
            }
            personAddressRepository.save(address);
        }
    }

    public void addLegalIdRecords(List<PersonLegalId> legalIds) {
        personLegalIdRepository.saveAll(legalIds);
    }
}
