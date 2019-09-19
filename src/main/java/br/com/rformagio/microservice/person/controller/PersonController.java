package br.com.rformagio.microservice.person.controller;

import br.com.rformagio.microservice.person.data.PersonData;
import br.com.rformagio.microservice.person.service.PersonService;
import br.com.rformagio.microservice.person.controller.validator.ValidateData;
import br.com.rformagio.microservice.person.exception.PersonNotFoundExcepetion;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/person")
public class PersonController {

    private PersonService personService;

    private ValidateData validateData;

    @Autowired
    public PersonController(PersonService personService,
                           ValidateData validateData){
        this.personService = personService;
        this.validateData = validateData;
    }

    @ApiOperation(value = "Insere uma pessoa na base.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pessoa inserida com sucesso!"),
            @ApiResponse(code = 400, message = "Problema nos dados enviados!")
    }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Long savePerson(@RequestBody @Valid PersonData personData) {
        return personService.save(personData);
    }



    @ApiOperation(value = "Pesquisa uma pessoa na base pelo id")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Pessoa não encontrada!"),
            @ApiResponse(code = 400, message = "Problema no id informado."),
            @ApiResponse(code = 302, message = "Sucesso!.")
    }
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public PersonData findById(@PathVariable("id") String id ) throws PersonNotFoundExcepetion {

        return personService.findById(validateData
                .validateId(id));
    }

}
