package br.com.rformagio.microservice.person.service;

import br.com.rformagio.microservice.person.data.PersonData;
import br.com.rformagio.microservice.person.domain.Person;
import br.com.rformagio.microservice.person.exception.PersonNotFoundExcepetion;
import br.com.rformagio.microservice.person.persintence.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Long save(PersonData personData){
        Person person = personRepository.save(buildPerson(personData));
        return person.getId();
    }

    public PersonData findById(Long id) throws PersonNotFoundExcepetion {
        Person person = personRepository.findById(id).orElseThrow(
                () -> new PersonNotFoundExcepetion(String.valueOf(id),
                        "Pessoa não encontrada!")
        );
        return buildPersonData(person);
    }

    private PersonData buildPersonData(Person person){
        return PersonData.builder()
                .id(person.getId())
                .name(person.getNome())
                .doc(person.getDoc()).build();
    }

    private Person buildPerson(PersonData personData){
        Person person = new Person();
        person.setDoc(personData.getDoc());
        person.setNome(personData.getName());
        return person;
    }
}
