package br.com.rformagio.microservice.person;

import br.com.rformagio.microservice.person.data.PersonData;
import br.com.rformagio.microservice.person.domain.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public class TesteUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new ParameterNamesModule())
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    public static String toJson(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(final String json, final Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Person buildPerson(PersonData personData, Long id){
        Person person = new Person();
        person.setDoc(personData.getDoc());
        person.setNome(personData.getName());
        person.setId(id);
        return person;
    }

    public static PersonData getPersonDataMock(){
        return PersonData.builder()
                .doc("123456789")
                .name("Teste")
                .build();
    }

}
