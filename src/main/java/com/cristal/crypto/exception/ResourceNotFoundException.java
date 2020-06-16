package com.cristal.crypto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.NoResultException;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Object not Founds")
public class ResourceNotFoundException extends NoResultException {


    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
