package com.aladyn.client.exception;

import com.aladyn.XmlRmiException;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class ConnectionException extends XmlRmiException {

	private static final long serialVersionUID = 4264177819397095369L;
	
	public ConnectionException(int code, String message,
			Exception otherExceptions)
	{
		super(code, message, otherExceptions);
	}
	

}
