package com.aladyn.parser.exception;

import com.aladyn.XmlRmiException;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class TypeParseException extends XmlRmiException
{
	private static final long serialVersionUID = -1428295046562943473L;
	
	public TypeParseException(int code, String message, Exception otherExceptions)
	{
		super(code, message, otherExceptions);
	}
}
