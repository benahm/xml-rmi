package com.aladyn.parser.exception;

import com.aladyn.XmlRmiException;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class XmlFileException extends XmlRmiException
{
	private static final long serialVersionUID = 7236970603259208864L;
	
	public XmlFileException(int code, String message, Exception otherExceptions)
	{
		super(code, message, otherExceptions);
	}
}
