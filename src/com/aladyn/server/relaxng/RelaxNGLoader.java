package com.aladyn.server.relaxng;

/**
 * RelaxNGLoader class pour chargé les ressource ( fichier relaxNG )
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad 
 *
 */

public class RelaxNGLoader {

	/**
	 * getResource
	 * @return la ressource
	 */
	public static String getResource(){
		return RelaxNGLoader.class.getResource("xml-rmi.rnc")+"";
	}
}
