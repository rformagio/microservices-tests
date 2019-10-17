package br.com.rformagio.microservice.person.controller;

import br.com.rformagio.microservice.person.TesteUtil;
import br.com.rformagio.microservice.person.controller.validator.ValidateData;
import br.com.rformagio.microservice.person.data.PersonData;
import br.com.rformagio.microservice.person.exception.PersonNotFoundExcepetion;
import br.com.rformagio.microservice.person.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest3 {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PersonService personService;

    @MockBean
    ValidateData validateData;

    @Test
    public void givenTheNewPerson_whenCreate_thenReturnTheId() throws Exception{

        final Long ID_CREATED = 101L;

        PersonData personData = TesteUtil.getPersonDataMock();

        given(personService.save(personData)).willReturn(ID_CREATED);

        mockMvc.perform(post("/api/v1/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TesteUtil.toJson(personData)))
                .andExpect(status().isOk());
    }

    @Test
    public void givenInvalidId_whenFindById_thenReturnBadRequest() throws Exception{

        final String ID_INVALID = "string";

        given(validateData.validateId(ID_INVALID)).willThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Id must be a number"));

        given(personService.findById(anyLong())).willReturn(null);

        mockMvc.perform(get("/api/v1/person/" + ID_INVALID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenValidId_whenFindById_thenReturnPerson() throws Exception{

        final Long ID = 1L;

        PersonData personData = PersonData.builder()
                .doc("123456789")
                .name("Teste")
                .id(ID)
                .build();

        given(validateData.validateId(String.valueOf(ID))).willReturn(ID);

        given(personService.findById(ID)).willReturn(personData);

        mockMvc.perform(get("/api/v1/person/" + ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.id", is(ID.intValue())));
    }


    @Test
    public void givenANotFoundId_whenFindById_thenShouldReturnNotFound() throws Exception {

        final Long ID_NOT_FOUND = 1001L;

        given(validateData.validateId(String.valueOf(ID_NOT_FOUND))).willReturn(ID_NOT_FOUND);

        given(personService.findById(anyLong())).willThrow(PersonNotFoundExcepetion.class);

        mockMvc.perform(get("/api/v1/person/" + ID_NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void givenANewPersonWithInvalidData_whenSave_thenSholdReturn() throws Exception{

        final Long ID_CREATED = 101L;
        final String EMPTY_NAME = "";
        final String DOC_SIZE_TWO = "12";

        PersonData personData = PersonData.builder()
                .name(EMPTY_NAME)
                .doc(DOC_SIZE_TWO)
                .build();

        given(personService.save(personData)).willReturn(ID_CREATED);

        mockMvc.perform(post("/api/v1/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TesteUtil.toJson(personData)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.name())))
                .andExpect(jsonPath("$.errors", hasSize(2)));
    }

}
