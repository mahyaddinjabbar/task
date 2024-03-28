package com.mahyaddin.task.restapi;

import com.mahyaddin.task.domain.Person;
import com.mahyaddin.task.domain.PersonAddress;
import com.mahyaddin.task.domain.PersonLegalId;
import com.mahyaddin.task.domain.PersonRepository;
import com.mahyaddin.task.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
public class PersonsController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }


    @PostMapping("/addRecord")
    public ResponseEntity<String> addPersonRecords(@RequestBody List<Person> persons) {
        try {
            personService.addPersonRecords(persons);
            return ResponseEntity.ok("Person records added successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add Person records: " + e.getMessage());
        }
    }

    @PostMapping("/addRecord/personLegalId")
    public ResponseEntity<String> addLegalIdRecords(@RequestBody List<PersonLegalId> legalIds){
        try{
            personService.addLegalIdRecords(legalIds);
            return ResponseEntity.ok("Legal Id records added successfully");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add Legal Id records: " + e.getMessage());

        }
    }


    @PostMapping("/addRecord/personAddress")
    public ResponseEntity<String> addAddressRecords(@RequestBody List<PersonAddress> addresses){
        try{
            personService.addAddressRecords(addresses);
            return ResponseEntity.ok("Address records added successfully");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add Adress records: " + e.getMessage());

        }
    }


    @GetMapping("/getPerson")
    public List<Person> getPerson(@RequestParam("asOfDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate asOfDate,
                                  @RequestParam(value = "personId", required = false) Integer personId) {
        Date date = Date.from(asOfDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return personService.getSnapshotOnDate(date, Optional.ofNullable(personId));
    }
}

