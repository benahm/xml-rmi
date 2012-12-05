package com.aladyn.client;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.aladyn.ProtoResponseFault;
import com.aladyn.client.exception.UpdateException;
import com.aladyn.client.params.ObjectParam;
import com.aladyn.parser.DOMProcess;
import com.aladyn.parser.XMLParser;
import com.aladyn.parser.XmlValidator;
import com.aladyn.parser.exception.NotValidXmlForSchemaException;
import com.aladyn.server.relaxng.RelaxNGLoader;

/**
 * Traitement du xml reçu en reçu en retour et mise à jour des objets
 * envoyer en paramètres
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * 
 */
public class Updater {
	private XmlValidator validator = new XmlValidator(
			RelaxNGLoader.getResource() + "");
	private Object returnMethod;
	private XMLParser parser = new XMLParser(null);

	/**
	 * Mise à jour depuis un xml passer comme chaine de caractères
	 * 
	 * @param xmlString
	 *            xml
	 * @throws UpdateException
	 */
	public void update(String xmlString) throws UpdateException {

		try {
			validator.isValidate(xmlString);
			update(new ByteArrayInputStream(xmlString.getBytes("UTF-16")));
		} catch (UnsupportedEncodingException e) {
			throw new UpdateException(ProtoResponseFault.CLIENT_EXCEPTION,
					ProtoResponseFault.XML_ENCODAGE, e);
		} catch (NotValidXmlForSchemaException e) {
			throw new UpdateException(ProtoResponseFault.CLIENT_EXCEPTION,
					ProtoResponseFault.NOT_VALID_XML, e);
		}

	}

	/**
	 * Mise à jour depuis un fichier xml
	 * 
	 * @param xmlFile
	 *            xml
	 * @throws UpdateException
	 */
	public void update(File xmlFile) throws UpdateException {

		try {
			validator.isValidate(xmlFile);
			update(new FileInputStream(xmlFile));
		} catch (FileNotFoundException e) {
			throw new UpdateException(ProtoResponseFault.CLIENT_EXCEPTION,
					ProtoResponseFault.FILE_NOT_FOUND, e);
		} catch (NotValidXmlForSchemaException e) {
			throw new UpdateException(ProtoResponseFault.CLIENT_EXCEPTION,
					ProtoResponseFault.NOT_VALID_XML, e);
		}
	}

	/**
	 * Mise à jour depuis un InputStream
	 * 
	 * @param xmlStream
	 *            xml input stream
	 * @throws UpdateException
	 */
	private void update(InputStream xmlStream) throws UpdateException {

		Document doc = null;
		try {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(xmlStream);
			Element xml = doc.getDocumentElement();
			xml = DOMProcess.getFirstElement(xml);
			if (xml.getNodeName().equals("params")) {
				NodeList params = xml.getElementsByTagName("param");
				Element e = null;
				Element firstElement = DOMProcess
						.getFirstElement((Element) params.item(0));
				Entry<Class<?>, Object> treatValue = parser
						.treatValue(firstElement);
				if (treatValue != null)
					returnMethod = treatValue.getValue();
				for (int i = 1; i < params.getLength(); i++) {
					e = DOMProcess.getFirstElement(params.item(i));
					e = DOMProcess.getFirstElement(e);
					ObjectParam param = (ObjectParam) IdTable.get(e
							.getAttribute("oid"));
					if (param == null)
						throw new UpdateException(
								ProtoResponseFault.CLIENT_EXCEPTION,
								ProtoResponseFault.OBJECT_TO_UPDATE_NOT_FOUND);
					param.updateFromXML(e);
				}
			} else {
				Element fault = DOMProcess.getFirstElement(xml);
				Hashtable<String, Object> hash = parser.treatStruct(DOMProcess
						.getFirstElement(fault));
				System.out.println(hash);
				throw new UpdateException(ProtoResponseFault.CLIENT_EXCEPTION,
						ProtoResponseFault.ERROR_RECEIVE
								+ hash.get("faultCode") + ",  "
								+ hash.get("faultString"));

			}
		} catch (SAXException e) {
			throw new UpdateException(ProtoResponseFault.CLIENT_EXCEPTION,
					ProtoResponseFault.NOT_VALID_XML, e);
		} catch (IOException  e) {
			throw new UpdateException(ProtoResponseFault.CLIENT_EXCEPTION,
					ProtoResponseFault.NOT_VALID_XML, e);
		} catch (ParserConfigurationException e) {
			throw new UpdateException(ProtoResponseFault.CLIENT_EXCEPTION,
					ProtoResponseFault.NOT_VALID_XML, e);
		}

	}

	/**
	 * Retourne le return de la methode appeler à distance
	 * 
	 * @return valeur de retour de la methode distante
	 */
	public Object getReturnMethod() {
		return returnMethod;
	}

}
