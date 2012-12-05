package com.aladyn.client.exception;

import com.aladyn.XmlRmiException;

/***
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class UpdateException extends XmlRmiException {

	private static final long serialVersionUID = 2287522777390264126L;

	public UpdateException(int code, String message, Exception otherExceptions)
	{
		super(code, message, otherExceptions);
	}

	public UpdateException(int code, String message)
	{
		super(code, message);
	}
}
