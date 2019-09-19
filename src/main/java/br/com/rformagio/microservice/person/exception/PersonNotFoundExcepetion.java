package br.com.rformagio.microservice.person.exception;

import lombok.Getter;

public class PersonNotFoundExcepetion extends Exception {

    @Getter
    String id;

    public PersonNotFoundExcepetion(String id, String errorMessage) {
        super(errorMessage);
        this.id = id;
    }

}
