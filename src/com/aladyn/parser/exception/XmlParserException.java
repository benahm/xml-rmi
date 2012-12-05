package com.aladyn.parser.exception;

import com.aladyn.XmlRmiException;

/**
 * 
 * Cette exception préviens qu'il y a une erreur liée à la 
 * lecture du fichier contenant le XML
 * Elle peut aussi etre déclanché par SAX, en particulier si le fichier 
 * ne correspond pas à un document XML bien construit
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class XmlParserException extends XmlRmiException
{

	private static final long serialVersionUID = 7236970603259208864L;
	
	public XmlParserException(int code, String message, Exception otherExceptions)
	{
		super(code, message, otherExceptions);
	}
}
