package br.com.rformagio.microservice.person.persintence;

import br.com.rformagio.microservice.person.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
