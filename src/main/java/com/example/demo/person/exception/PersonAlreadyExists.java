package com.example.demo.person.exception;

public class PersonAlreadyExists extends Exception{
    public PersonAlreadyExists(long pid) {
        super("Person already exists with this PID: " + pid);
    }
}
