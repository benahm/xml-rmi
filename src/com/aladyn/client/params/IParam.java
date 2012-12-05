package com.aladyn.client.params;

import com.aladyn.client.exception.ToXMLException;

/**
 * interface IParam 
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * 
 */

public interface IParam {
	
	public String getType();

	/**
	 * methode toXML
	 * @return le XML de IParam
	 * @throws ToXMLException 
	 */
	public String toXML() throws ToXMLException;
}
