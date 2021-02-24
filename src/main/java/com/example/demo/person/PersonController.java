package com.example.demo.person;

import com.example.demo.person.exception.PersonNotFoundException;
import com.example.demo.person.exception.GroupNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/persons")
public class PersonController {

    PersonService personService;

//    @Autowired                                                                                                        //SU SITUO NEPASILEIDZIA
//    public PersonController(PersonService personService) {                                                            //SU SITUO NEPASILEIDZIA
//        this.personService = personService;                                                                           //SU SITUO NEPASILEIDZIA
//    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Person>> getPersons(){
        List<Person> list = personService.getAll();
        return new ResponseEntity<List<Person>>(list, HttpStatus.OK);
    }

//    @GetMapping("{pid}")
//    public ResponseEntity<Person> getPersonByPid(@PathVariable("pid") Long pid) {
////        try {
//            Person person = personService.getById(pid);
//            return new ResponseEntity<Person>(person, HttpStatus.OK);
////        } catch (PersonNotFoundException ex) {
//            //log.error("getPersonByPid", ex);
////            return ResponseEntity.notFound().build();
////        }
//    }
//
//    @DeleteMapping("{pid}")
//    public ResponseEntity<Void> deletePerson(@PathVariable("pid") Long pid) {
//        try {
//            personService.delete(pid);
//            return ResponseEntity.ok().build();
//        } catch (PersonNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    // POST—add new data
//    @PostMapping(path = "{pid}", consumes = "application/json")
//    public ResponseEntity<Void> addPerson(@RequestBody Person person) {
//        try {
//            personService.addPerson(person);
//            return ResponseEntity.ok().build();
//        } catch (PersonAlreadyExists e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    // PUT—replace existing data
//    @PutMapping("{pid}")
//    public ResponseEntity<Void> updatePerson(@RequestBody Person person, @PathVariable("pid") Long pid) {
//        try {
//            personService.updatePerson(person, pid);
//            return ResponseEntity.ok().build();
//        } catch (PersonNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

    //CRUD
    //** CREATE */
    // POST—add new data
    @PostMapping
    public ResponseEntity<Void> addPerson(@RequestBody Person person, UriComponentsBuilder builder) {
        boolean success = personService.save(person);
        if (!success) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/person/{id}").buildAndExpand(person.getPid()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //** READ */
    @GetMapping("{pid}")
    public ResponseEntity<Person> getPersonByPid(@PathVariable("pid") Long pid) {
        Person person = personService.getById(pid);
        return new ResponseEntity<Person>(person, HttpStatus.OK);
    }

    //** UPDATE */
    // PUT—replace existing data
    // Kazko neveikia
    @PutMapping
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        personService.update(person);
        return new ResponseEntity<Person>(person, HttpStatus.OK);
    }

    //** DELETE */
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

    // Groupi

    private GroupService groupService;
    @Autowired
    public PersonController(PersonService personService, GroupService groupService) {
        this.personService = personService;
        this.groupService = groupService;
    }

    @GetMapping("{pid}/groups")
    public ResponseEntity<Collection<Group>> getPersonGroups(@PathVariable("pid") Long pid) {

        try {
            Person person = personService.getById(pid);
            return new ResponseEntity<Collection<Group>>(person.getGroups(), HttpStatus.OK);
        } catch (PersonNotFoundException ex) {
            //log.error("getPersonGroups", ex);
            return ResponseEntity.notFound().build();
        }

    }

    @PatchMapping("{pid}/groups/{id}")
    public ResponseEntity<?> setGroup(@PathVariable("pid") long pid, @PathVariable("id") long id) {

        try {
            Person person = personService.getById(pid);
            Group group = groupService.findById(id);
            Set<Group> groups = person.getGroups();
            groups.add(group);
            person.setGroups(groups);
            personService.saveAndFlush(person);
            return ResponseEntity.ok().build();
        } catch (GroupNotFoundException | PersonNotFoundException ex) {
            //log.error("setGroup", ex);
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("{pid}/groups/{id}")
    public ResponseEntity<?> removeFromGroup(@PathVariable("pid") long pid, @PathVariable("id") long id) {
        try {
            Person person = personService.getById(pid);
            Group group = groupService.findById(id);
            Set<Group> groups = person.getGroups();
            if (groups.contains(group)) {
                groups.remove(group);
                person.setGroups(groups);
                personService.saveAndFlush(person);
            }

            return ResponseEntity.ok().build();
        } catch (GroupNotFoundException | PersonNotFoundException ex) {
            //log.error("setGroup", ex);
            return ResponseEntity.notFound().build();
        }
    }
}
