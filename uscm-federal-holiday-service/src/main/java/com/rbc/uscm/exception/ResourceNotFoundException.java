package com.rbc.uscm.exception;

public class ResourceNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	/*public ResourceNotFoundException() {
		
	}*/
	public ResourceNotFoundException(String message){
        super(message);
    }
}
