package com.epf.rentmanager.exception;

public class ContraintException extends Exception{
    public ContraintException(){

    }
    public ContraintException(String errorMessage){
        super(errorMessage);
    }
}
