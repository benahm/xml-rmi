package com.aladyn;

/**
 * Exception pour le protocole XML-RMI
 * Est composé d'un code et d'un message
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class XmlRmiException extends Exception
{

	private static final long serialVersionUID = -6921858784234223732L;
	private int code = -1;
	
	public XmlRmiException(int code, String message, Exception otherExceptions)
	{
		super(message, otherExceptions);
		this.code = code;
	}
	
	public XmlRmiException(int code, String message)
	{
		super(message);
		this.code = code;
	}
	
	public int getCode()
	{
		return code;
	}
}
