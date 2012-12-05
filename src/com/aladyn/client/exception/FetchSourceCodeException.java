package com.aladyn.client.exception;

import com.aladyn.XmlRmiException;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class FetchSourceCodeException extends XmlRmiException {

	private static final long serialVersionUID = 8439420811207138488L;

	public FetchSourceCodeException(int code, String message,
			Exception otherExceptions)
	{
		super(code, message, otherExceptions);
	}

	public FetchSourceCodeException(int code, String message)
	{
		super(code, message);
	}
}
