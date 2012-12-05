package com.aladyn.client.exception;

import com.aladyn.XmlRmiException;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class ToXMLException extends XmlRmiException {

	private static final long serialVersionUID = 8895137528473937839L;

	public ToXMLException(int code, String message, Exception otherExceptions)
	{
		super(code, message, otherExceptions);
	}
	
	public ToXMLException(int code, String message)
	{
		super(code, message);
	}
}
