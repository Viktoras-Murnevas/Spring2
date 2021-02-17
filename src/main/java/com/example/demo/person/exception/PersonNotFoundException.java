package com.example.demo.person.exception;

public class PersonNotFoundException extends Exception {
    public PersonNotFoundException(long pid) {
        super("Cant find person with this PID: " + pid);
    }
}


