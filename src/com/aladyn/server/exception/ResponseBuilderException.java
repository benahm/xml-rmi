package com.aladyn.server.exception;

import com.aladyn.XmlRmiException;

/**
 * Erreur lors de la construction de la réponse pour le client
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class ResponseBuilderException extends XmlRmiException
{

	private static final long serialVersionUID = -6877298093156226853L;

	public ResponseBuilderException(int code, String message,
			Exception otherExceptions)
	{
		super(code, message, otherExceptions);
	}

}
