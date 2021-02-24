package com.example.demo.person;

import com.example.demo.person.exception.PersonAlreadyExists;
import com.example.demo.person.exception.PersonNotFoundException;

import java.util.List;

public interface PersonService {

    List<Person> getAll();

//    Person getById(long pid) throws PersonNotFoundException;
//
//    void delete(long pid) throws PersonNotFoundException;
//
//    void addPerson(Person person) throws PersonAlreadyExists;
//
//    void updatePerson(Person person, long pid) throws  PersonNotFoundException;

    //CRUD
    /** READ*/
    public Person getById(long pid) throws PersonNotFoundException;
    /** DELETE*/
    public void delete(long pid) throws PersonNotFoundException;
    /** CREATE*/
    public boolean save(Person person);
    /** UPDATE */
    public boolean update(Person person)throws PersonNotFoundException;

    public void saveAndFlush(Person person);
}
