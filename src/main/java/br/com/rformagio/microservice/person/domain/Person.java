package br.com.rformagio.microservice.person.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    String nome;
    String doc;

}
