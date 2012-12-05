package com.aladyn;

import com.aladyn.client.exception.UpdateException;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public interface XMLRMISerializable {
	public void updateFromXML(org.w3c.dom.Element theXML) throws UpdateException ;
}
