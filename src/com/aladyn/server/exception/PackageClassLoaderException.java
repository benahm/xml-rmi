package com.aladyn.server.exception;

import com.aladyn.XmlRmiException;

/**
 * Déclanché lors du chargement des classes dans un package
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class PackageClassLoaderException extends XmlRmiException
{

	private static final long serialVersionUID = -7516966387866677833L;

	public PackageClassLoaderException(int code, String message,
			Exception otherExceptions)
	{
		super(code, message, otherExceptions);
	}
}
