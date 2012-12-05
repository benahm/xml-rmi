package com.aladyn.parser.exception;

import com.aladyn.XmlRmiException;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class InterfaceNotFoundException extends XmlRmiException {

	private static final long serialVersionUID = 1L;
	
	public InterfaceNotFoundException(int code, String message)	
	{
		super(code, message);
	}

}
