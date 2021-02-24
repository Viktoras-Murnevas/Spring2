package com.example.demo.person;

import com.example.demo.person.exception.PersonAlreadyExists;
import com.example.demo.person.exception.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImp implements PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonServiceImp(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> getAll() {
        return personRepository.findAll();
    }

//    @Override
//    public Person getById(final long pid) throws PersonNotFoundException {
//        Person person = personRepository.findById(pid).orElseThrow(() -> new PersonNotFoundException(pid));
//        return person;
//    }
//
//    @Override
//    public void delete(long pid) throws PersonNotFoundException {
//        Person person = personRepository.findById(pid).orElseThrow(() -> new PersonNotFoundException(pid));
//        personRepository.deleteById(pid);
//    }
//
//    @Override
//    public void addPerson(Person person) throws PersonAlreadyExists {
//        if(personRepository.existsById(person.getPid())) {
//            throw new PersonAlreadyExists(person.getPid());
//        } else {
//            personRepository.save(person);
//        }
//    }
//
//    @Override
//    public void updatePerson(Person person, long pid) throws  PersonNotFoundException {
//        Person updatedPerson = personRepository.findById(pid).orElseThrow(() -> new PersonNotFoundException(pid));
//        updatedPerson.setName(person.getName());
//        updatedPerson.setMiddleName(person.getMiddleName());
//        updatedPerson.setSurname(person.getSurname());
//        updatedPerson.setEmail(person.getEmail());
//        updatedPerson.setPhone(person.getPhone());
//        personRepository.save(updatedPerson);
//    }

    @Override
    public Person getById(final long pid) throws PersonNotFoundException {
        Person person = personRepository.findById(pid).orElseThrow(() -> new PersonNotFoundException(pid));
        return person;

    }

    @Override
    public void delete(long pid) throws PersonNotFoundException {
        Person person = personRepository.findById(pid).orElseThrow(() -> new PersonNotFoundException(pid));
        personRepository.deleteById(pid);
    }

    @Override
    public boolean save(Person person) {
        Optional<Person> p = personRepository.findById(person.getPid());

        if (p.isPresent())
            return false;
        else {
            personRepository.save(person);
            return true;
        }
    }

    @Override
    public boolean update(Person person) throws PersonNotFoundException {
        boolean doneUpdate = false;
        long pid = person.getPid();
        Optional<Person> p = personRepository.findById(pid);
        
        if (p.isPresent()) {
            personRepository.save(person);
            doneUpdate = true;
        }
     else {
            new PersonNotFoundException(pid);
        }
        return doneUpdate;
    }

    @Override
    public void saveAndFlush(Person person) {
        personRepository.saveAndFlush(person);

    }
}