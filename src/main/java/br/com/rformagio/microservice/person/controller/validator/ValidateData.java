package br.com.rformagio.microservice.person.controller.validator;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ValidateData {

    public Long validateId(String id){
        if(StringUtils.isEmpty(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Id é obrigatório");
        } else if(!id.matches("[0-9]+")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Id deve ser um número.");
        }

         return Long.valueOf(id);
    }
}
