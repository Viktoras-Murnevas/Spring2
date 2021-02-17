package com.example.demo.person;

import com.example.demo.person.exception.PersonAlreadyExists;
import com.example.demo.person.exception.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("api/persons")
public class PersonController {

    PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Person>> getPersons(){
        List<Person> list = personService.getAll();
        return new ResponseEntity<List<Person>>(list, HttpStatus.OK);
    }

    @GetMapping("{pid}")
    public ResponseEntity<Person> getPersonByPid(@PathVariable("pid") Long pid) {
        try {
            Person person = personService.getById(pid);
            return new ResponseEntity<Person>(person, HttpStatus.OK);
        } catch (PersonNotFoundException ex) {
            //log.error("getPersonByPid", ex);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{pid}")
    public ResponseEntity<Void> deletePerson(@PathVariable("pid") Long pid) {
        try {
            personService.delete(pid);
            return ResponseEntity.ok().build();
        } catch (PersonNotFoundException e) {
            //log.error("deletePerson", e);
            return ResponseEntity.notFound().build();
        }
    }

    //    POST—add new data
    @PostMapping(path = "{pid}", consumes = "application/json")
    public ResponseEntity<Void> addPerson(@RequestBody Person person) {
        try {
            personService.addPerson(person);
            return ResponseEntity.ok().build();
        } catch (PersonAlreadyExists e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT—replace existing data
    @PutMapping("{pid}")
    public ResponseEntity<Void> updatePerson(@RequestBody Person person, @PathVariable("pid") Long pid) {
        try {
            personService.updatePerson(person, pid);
            return ResponseEntity.ok().build();
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
