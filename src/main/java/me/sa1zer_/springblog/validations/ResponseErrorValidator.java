package me.sa1zer_.springblog.validations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Map;

@Service
public class ResponseErrorValidator {

    public ResponseEntity<Object> mapValidationService(BindingResult result) {
        if(result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            if(result.getAllErrors().isEmpty()) {
                for(ObjectError error : result.getAllErrors()) {
                    errors.put(error.getCode(), error.getDefaultMessage());
                }
            }

            for(FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
