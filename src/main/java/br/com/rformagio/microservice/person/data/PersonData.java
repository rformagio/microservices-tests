package br.com.rformagio.microservice.person.data;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
public class PersonData {

    Long id;

    @NotEmpty(message = "Nome é obrigatório!")
    String name;

    @NotEmpty(message = "Documento é obrigatório!")
    @Size(min = 3, max = 14)
    String doc;
}
