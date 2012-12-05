package com.aladyn.client.exception;

import com.aladyn.XmlRmiException;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class ClientSideException extends XmlRmiException
{

	private static final long serialVersionUID = -8471784473135149172L;

	public ClientSideException(int code, String message,
			Exception otherExceptions)
	{
		super(code, message, otherExceptions);
	}

	public ClientSideException(int code, String message)
	{
		super(code, message);
	}

}
