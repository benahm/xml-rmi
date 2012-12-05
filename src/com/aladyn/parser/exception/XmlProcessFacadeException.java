package com.aladyn.parser.exception;

import com.aladyn.XmlRmiException;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class XmlProcessFacadeException extends XmlRmiException
{
	private static final long serialVersionUID = 9048863880235888305L;
	
	public XmlProcessFacadeException(int code, String message, Exception otherExceptions)
	{
		super(code, message, otherExceptions);
	}
}
