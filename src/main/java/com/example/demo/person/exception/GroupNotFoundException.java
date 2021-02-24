package com.example.demo.person.exception;

public class GroupNotFoundException extends Exception {  //GroupException

    public GroupNotFoundException(long id) {
        super("Can't find Group with: " + id);
    }
}
