package br.com.rformagio.microservice.person.controller;

import br.com.rformagio.microservice.person.TesteUtil;
import br.com.rformagio.microservice.person.data.PersonData;
import br.com.rformagio.microservice.person.exception.PersonNotFoundExcepetion;
import br.com.rformagio.microservice.person.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PersonService personService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

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

        given(personService.findById(ID)).willReturn(personData);


        mockMvc.perform(get("/api/v1/person/" + ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
        .andExpect(jsonPath("$.id", is(ID.intValue())));
    }

    @Test
    public void givenANotFoundId_whenFindById_thenShouldReturnNotFound() throws Exception {

        final Long ID_NOT_FOUND = 1001L;

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
