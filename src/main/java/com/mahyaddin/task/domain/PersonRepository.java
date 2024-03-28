package com.mahyaddin.task.domain;

import com.mahyaddin.task.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {


    // Method to find person information by birth date and person ID
    List<Person> findByBirthDateAndPersonId(Date birthDate, Integer personId);

    // Method to find person information by birth date
    List<Person> findByBirthDate(Date birthDate);

    List<Person> findAll();


}
