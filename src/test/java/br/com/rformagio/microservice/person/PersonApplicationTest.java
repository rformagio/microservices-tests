package br.com.rformagio.microservice.person;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PersonApplicationTest.class})
public class PersonApplicationTest {

    @Test
    public void contextLoads() {
    }
}
