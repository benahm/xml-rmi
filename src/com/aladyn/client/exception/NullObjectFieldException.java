package com.aladyn.client.exception;

import com.aladyn.XmlRmiException;

/**
 * NullObjectFieldException exception levé si on essaye d'envoyer un objet qui
 * contient un champs objet non initialisé
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * 
 */
public class NullObjectFieldException extends XmlRmiException {

	private static final long serialVersionUID = -8477880245307204869L;

	public NullObjectFieldException(int code, String message,
			Exception otherExceptions)
	{
		super(code, message, otherExceptions);
	}

	public NullObjectFieldException(int code, String message)
	{
		super(code, message);
	}
}
