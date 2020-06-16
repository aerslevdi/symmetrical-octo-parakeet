package com.cristal.crypto.exception;

public class ElementNotFoundException extends RuntimeException {

    public ElementNotFoundException(String message, Throwable cause){
        super(message,cause);
}

    public ElementNotFoundException(String message){
        super(message);
    }

    public ElementNotFoundException(Throwable cause){
        super(cause);
    }

}
