package com.aladyn.server.exception;

import com.aladyn.XmlRmiException;

/**
 * La mise en place du serveur XML-RMI a rencontré une erreur
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class XMLRMIServerException extends XmlRmiException
{
	private static final long serialVersionUID = 2727094857563287123L;

	public XMLRMIServerException(int code, String message, Exception otherExceptions)
	{
		super(code, message, otherExceptions);
	}

	public XMLRMIServerException(int code, String message)
	{
		super(code, message);
	}

}
