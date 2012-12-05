package com.aladyn.client.params.exception;

import com.aladyn.XmlRmiException;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class FileRException extends XmlRmiException
{

	private static final long serialVersionUID = 5372790515171097266L;

	public FileRException(int code, String message, Exception otherExceptions)
	{
		super(code, message, otherExceptions);
	}

}
