package com.aladyn.parser;

import java.io.File;
import java.io.InputStream;

import com.aladyn.parser.exception.NotValidXmlForSchemaException;
import com.aladyn.parser.exception.XmlParserException;
import com.aladyn.parser.exception.XmlProcessFacadeException;
import com.aladyn.server.relaxng.RelaxNGLoader;

/**
 * Parse un document XML, répondant à une spécifiaction RelaxNG
 * et retourn l'objet Request
 *  
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class XmlProcessFacade
{
	private XmlValidator validator = new XmlValidator(RelaxNGLoader.getResource());
	private XMLFileParser parser = new XMLFileParser();
	
	private Request request;
	

	/**
	 * La source à parser est une chaine de caractère
	 * 
	 * @param xmlString
	 * @return
	 * @throws XmlProcessFacadeException
	 */
	public Request process(String xmlString) throws XmlProcessFacadeException { 
		request = new Request();
		try	{
			validator.isValidate(xmlString);
			parser.parse(xmlString, request);
		} catch (NotValidXmlForSchemaException e) {
			throw new XmlProcessFacadeException(e.getCode(), e.getMessage(), e);
		} catch (XmlParserException e) {
			throw new XmlProcessFacadeException(e.getCode(), e.getMessage(), e);
		}
		return request;
	}
	
	/**
	 * La source à parser est un fichier
	 * 
	 * @param xmlFile
	 * @return
	 * @throws XmlProcessFacadeException
	 */
	public Request process(File xmlFile) throws XmlProcessFacadeException { 
		request = new Request();
		try	{
			validator.isValidate(xmlFile);
			parser.parse(xmlFile, request);
		} catch (NotValidXmlForSchemaException e) {
			throw new XmlProcessFacadeException(e.getCode(), e.getMessage(), e);		
		} catch (XmlParserException e) {
			throw new XmlProcessFacadeException(e.getCode(), e.getMessage(), e);		
		}  
		return request;
	}
	
	/**
	 * La source à parser est un flux
	 * 
	 * @param xmlStream
	 * @return
	 * @throws XmlProcessFacadeException
	 */
	public Request process(InputStream xmlStream) throws XmlProcessFacadeException	{
		request = new Request();
		try	{
			validator.isValidate(xmlStream);
			parser.parse(xmlStream, request);
		} catch (NotValidXmlForSchemaException e) {
			throw new XmlProcessFacadeException(e.getCode(), e.getMessage(), e);		
		} catch (XmlParserException e) {
			throw new XmlProcessFacadeException(e.getCode(), e.getMessage(), e);		
		} 
		return request;
	}
}
