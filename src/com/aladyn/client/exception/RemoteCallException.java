package com.aladyn.client.exception;

import com.aladyn.XmlRmiException;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class RemoteCallException extends XmlRmiException {

	private static final long serialVersionUID = 9165724150762363589L;
	
	public RemoteCallException(int code, String message,
			Exception otherExceptions)
	{
		super(code, message, otherExceptions);
	}

	public RemoteCallException(int code, String message)
	{
		super(code, message);
	}
	
}
