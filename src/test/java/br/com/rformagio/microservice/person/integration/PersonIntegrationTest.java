package br.com.rformagio.microservice.person.integration;

import br.com.rformagio.microservice.person.TesteUtil;
import br.com.rformagio.microservice.person.data.PersonData;
import br.com.rformagio.microservice.person.persintence.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PersonRepository personRepository;

    @Test
    public void givenTheNewPerson_whenCreate_thenReturnTheId() throws Exception {

        final Long ID_CREATED = 101L;

        doReturn(
                TesteUtil
                        .buildPerson(getPersonDataMock(), ID_CREATED)
        ).when(
                personRepository

        ).save(
                TesteUtil
                        .buildPerson(getPersonDataMock(), null)
        );

        mockMvc.perform(post("/api/v1/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TesteUtil.toJson(getPersonDataMock())))
                .andExpect(status().isOk());


    }

    private PersonData getPersonDataMock(){
        return PersonData.builder()
                .doc("123456789")
                .name("Teste")
                .build();
    }

}
