package br.com.rformagio.microservice.person.service;

import br.com.rformagio.microservice.person.TesteUtil;
import br.com.rformagio.microservice.person.data.PersonData;
import br.com.rformagio.microservice.person.domain.Person;
import br.com.rformagio.microservice.person.exception.PersonNotFoundExcepetion;
import br.com.rformagio.microservice.person.persintence.PersonRepository;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

    @Mock
    PersonRepository personRepository;

    @InjectMocks
    PersonService personService;

    @Test
    public void givenPerson_whenSave_thenReturnId(){

        final Long ID_CREATED = 101L;

        given(personRepository.save(
                TesteUtil
                .buildPerson(getPersonDataMock(), null)
        )).willReturn(
                TesteUtil
        .buildPerson(getPersonDataMock(), ID_CREATED)
        );

        Long id = personService.save(getPersonDataMock());

        assertEquals(id, ID_CREATED);
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    public void givenAExistPersonID_whenFind_thenReturnThePerson() throws PersonNotFoundExcepetion {

        final Long EXIST_ID = 101L;

        Optional<Person> optional = Optional.of(TesteUtil
                .buildPerson(getPersonDataMock(), EXIST_ID));

        given(personRepository.findById(
                EXIST_ID
        )).willReturn(
                optional
        );

        PersonData personData = personService.findById(EXIST_ID);

        assertNotNull(personData);
        verify(personRepository, times(1)).findById(anyLong());
    }

    @Test(expected = PersonNotFoundExcepetion.class)
    public void givenANonExistPersonID_whenFind_thenShouldThrowException() throws PersonNotFoundExcepetion {

        final Long NON_EXIST_ID = 101L;

        given(personRepository.findById(
                NON_EXIST_ID
        )).willReturn(
                Optional.ofNullable(null)
        );

        personService.findById(NON_EXIST_ID);

        //assertNull(personData);
        verify(personRepository, times(1)).findById(anyLong());

    }

    private PersonData getPersonDataMock(){
        return PersonData.builder()
                .doc("123456789")
                .name("Teste")
                .build();
    }

}
