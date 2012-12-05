package com.aladyn.server.exception;

import com.aladyn.XmlRmiException;

/**
 * Une erreur générale lié à la transformation de la requête XML en un objet Request
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class ProcessMethodCallException extends XmlRmiException
{
	private static final long serialVersionUID = -6172955080587571310L;
	
	public ProcessMethodCallException(int code, String message, Exception otherExceptions)
	{
		super(code, message, otherExceptions);
	}
}
