package com.soporte.soportem.exception;

//Excepcion para cuando se incumple una regla
public class BusinessException extends RuntimeException{

    public BusinessException(String mensaje){
        super(mensaje);
    }
}
