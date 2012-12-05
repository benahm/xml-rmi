package com.aladyn.server;

import java.util.ArrayList;
import java.util.Hashtable;

import com.aladyn.ProtoResponseFault;
import com.aladyn.client.exception.ToXMLException;
import com.aladyn.client.params.FieldFactory;
import com.aladyn.server.exception.ResponseBuilderException;

/**
 * Construit la reponse du protocole XML-RMI
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * 
 */
public class ResponseBuilder {

	/**
	 * Construit la réponse xml à partir de la liste des
	 * valeurs à retourner
	 * 
	 * @param tab les des valeurs à retourner pour le client
	 * @return reponse
	 * @throws ResponseBuilderException 
	 */
	public String buildXmlResponse(ArrayList<Object> tab) throws ResponseBuilderException {
		StringBuilder xmlResponse = new StringBuilder();
		xmlResponse.append("<methodResponse>" + "<params>");
		for (int i = 0; i < tab.size(); i++) {
			try {
				xmlResponse.append("<param>"
						+ FieldFactory.wrapParam(tab.get(i)).toXML()
						+ "</param>");
			} catch (ToXMLException e) {
				throw new ResponseBuilderException(
						ProtoResponseFault.SERVER_EXCEPTION,
						e.getMessage(), e);
			}
		}
		xmlResponse.append("</params>" + "</methodResponse>");
		return xmlResponse.toString();
	}

	/**
	 * Construit une reponse indiquant une exceptions
	 * produit dans le service XML-RMI 
	 * 
	 * @param faultCode code de l'exception
	 * @param faultString description de l'exception
	 * @return reponse
	 * @throws ResponseBuilderException 
	 */
	public String buildXmlFaultResponse(int faultCode, String faultString) throws ResponseBuilderException {
		StringBuilder xmlFaultResponse = new StringBuilder();
		Hashtable<String, Object> response = new Hashtable<String, Object>();
		response.put("faultCode", faultCode);
		response.put("faultString", faultString);
		try {
			xmlFaultResponse.append("<methodResponse><fault>"
					+ FieldFactory.wrapParam(response).toXML()
					+ "</fault></methodResponse>");
		} catch (ToXMLException e) {
			throw new ResponseBuilderException(
					ProtoResponseFault.SERVER_EXCEPTION,
					e.getMessage(), e);
		}
		return xmlFaultResponse.toString();
	}
}
