package com.aladyn.server.exception;

import com.aladyn.XmlRmiException;

/**
 * BuildObjectException exception levé si la contruction dynamique de l'object 
 * se passe mal
 *  
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class BuildObjectException extends XmlRmiException {
	
	private static final long serialVersionUID = 1L;

	public BuildObjectException(int code, String message, Exception otherExceptions)
	{
		super(code, message, otherExceptions);
	}
	public BuildObjectException (int code, String message){
		super(code,message);
	}

}
