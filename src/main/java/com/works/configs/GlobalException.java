package com.works.configs;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public void handleException(Exception e) {
        System.out.println(e.getStackTrace()[0].toString());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object methodArgumentNotValid( MethodArgumentNotValidException ex ) {
        return parseErrors(ex);
    }

    private List parseErrors(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List errors = new ArrayList();
        for (FieldError fieldError : fieldErrors) {
            Map<String, Object> error = new HashMap<>();
            error.put("field", fieldError.getField());
            error.put("message", fieldError.getDefaultMessage());
            errors.add(error);
        }
        return errors;
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleDataIntegrity(DataIntegrityViolationException ex) {

        Map<String, Object> error = new HashMap<>();
        error.put("error", "Data Integrity Violation");
        // İstersen message'ı sadeleştir
        if (ex.getRootCause() != null) {
            error.put("message", "Bu kayıt zaten mevcut (ör: email benzersiz olmalı)");
        } else {
            error.put("message", "Veritabanı kural ihlali");
        }

        return error;
    }


}
