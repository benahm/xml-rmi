package com.aladyn.parser.exception;

import com.aladyn.XmlRmiException;

/**
 * 
 * Déclanché lorsque le document XML reçu ne correspond 
 * pas au schema RelaxNG
 *  
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class NotValidXmlForSchemaException extends XmlRmiException
{
	private static final long serialVersionUID = -2645827324547874050L;
	
	public NotValidXmlForSchemaException(int code, String message, Exception otherExceptions)
	{
		super(code, message, otherExceptions);
	}

}
