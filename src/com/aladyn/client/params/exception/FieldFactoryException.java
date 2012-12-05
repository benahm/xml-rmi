package com.aladyn.client.params.exception;

import com.aladyn.XmlRmiException;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class FieldFactoryException extends XmlRmiException
{

	private static final long serialVersionUID = 6826089714042921028L;

	public FieldFactoryException(int code, String message,
			Exception otherExceptions)
	{
		super(code, message, otherExceptions);
	}

}
