package com.aladyn.client.exception;

import com.aladyn.XmlRmiException;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class NotConformNameMethodException extends XmlRmiException {

	private static final long serialVersionUID = 6120966463975056683L;

	public NotConformNameMethodException(int code, String message,
			Exception otherExceptions)
	{
		super(code, message, otherExceptions);
	}
	
	public NotConformNameMethodException(int code, String message)
	{
		super(code, message);
	}

}
