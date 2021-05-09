package com.jwt.udemy.domain.exceptions;

public class SenhaInvalidaException extends RuntimeException{
    public SenhaInvalidaException () {
        super("Senha inválida");
    }
}
