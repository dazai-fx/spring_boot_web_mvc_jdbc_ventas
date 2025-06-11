package org.iesvdm.modelo.exception;

public class ClienteNotFoundException extends Throwable{
    public ClienteNotFoundException(String mensaje){
        super(mensaje);
    }
}
