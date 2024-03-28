package com.mahyaddin.task;

import com.fasterxml.classmate.util.ClassStack;
import com.mahyaddin.task.domain.*;
import com.mahyaddin.task.domain.enums.addressType;
import com.mahyaddin.task.domain.enums.idType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityManager;
import java.sql.Date;

@SpringBootApplication
@EnableJpaAuditing
public class TaskApplication {

	@Autowired
	private EntityManager entityManager;


	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(PersonRepository personRepository, PersonAddressRepository personAddressRepository, PersonLegalIdRepository personLegalIdRepository){
		return args -> {
			Person person1 = new Person(
					"Mark",
					"Zuckerberg",
					Date.valueOf("1988-06-12"),
					'M');

			personRepository.save(person1);

			Person person2 = new Person(
					"Elon",
					"Musk",
					Date.valueOf("1971-06-28"),
					'M');

			personRepository.save(person2);

			PersonAddress address1 = new PersonAddress(
					addressType.DECLARED,
					person1,
					"Riga",
					"Maza Lubanas",
					"Apt 6"
			);

			personAddressRepository.save(address1);

			PersonAddress address2 = new PersonAddress(
					addressType.HOME,
					person2,
					"Riga",
					"Reznas",
					"Apt 101"
			);

			personAddressRepository.save(address2);

			PersonAddress address3 = new PersonAddress(
					addressType.DECLARED,
					person2,
					"Baku",
					"Merkez",
					"Apt 41"
			);

			personAddressRepository.save(address3);



			PersonLegalId legalId1 = new PersonLegalId(
					idType.ID_CARD,
					person1,
					1234567809,
					Date.valueOf("2022-09-22"),
					"Migration Office"
			);


			personLegalIdRepository.save(legalId1);

			PersonLegalId legalId2 = new PersonLegalId(
					idType.PASSPORT,
					person2,
					128867809,
					Date.valueOf("2019-11-22"),
					"Passport Office"
			);

			personLegalIdRepository.save(legalId2);



		};
	}

}


