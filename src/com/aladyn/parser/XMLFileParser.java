package com.aladyn.parser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.aladyn.ProtoResponseFault;
import com.aladyn.parser.exception.XmlParserException;

/**
 * Parse un fichier (ou un flux) XML correspondant à un appele à distance d'une
 * méthode et rempli un objets avec les données du XML
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * 
 */
public class XMLFileParser {
	
	private XMLParser xmlParser;

	/**
	 * Prend une chaine de caractère contenant un document XML
	 * 
	 * @param xmlString
	 * @throws XmlParserException
	 */
	public void parse(String xmlString, Request request)
			throws XmlParserException {
		System.out.println("file");
		try {
			parse(new ByteArrayInputStream(xmlString.getBytes("UTF-16")),
					request);
		} catch (UnsupportedEncodingException e) {
			throw new XmlParserException(ProtoResponseFault.XML_DOCUMENT_NOT_VALID, 
					ProtoResponseFault.XML_ENCODAGE, e);
		}
	}

	/**
	 * Prend un fichier
	 * 
	 * @param xmlFile
	 * @throws XmlParserException
	 */
	public void parse(File xmlFile, Request request)
			throws XmlParserException {
		System.out.println("file");
		try {
			parse(new FileInputStream(xmlFile), request);
		} catch (FileNotFoundException e) {
			throw new XmlParserException(ProtoResponseFault.XML_DOCUMENT_NOT_VALID, 
					ProtoResponseFault.XML_ENCODAGE + xmlFile.getName(), e);
		}
	}

	/**
	 * Prend un flux d'octets
	 * 
	 * @param xmlFileStream
	 * @param request
	 * @throws XmlParserException
	 */
	public void parse(InputStreamReader xmlReader, Request request)
			throws XmlParserException {
		Scanner sc = new Scanner(xmlReader);
		String xml = sc.useDelimiter("\\A").next();
		sc.close();
		parse(xml, request);
	}

	/**
	 * Prend un flux binaire
	 * 
	 * @param xmlFileStream
	 * @throws XmlParserException
	 */
	public void parse(InputStream xmlFileStream, Request request)
			throws XmlParserException {
		
		Document doc = null;
		try {
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(xmlFileStream);
			
			xmlParser = new XMLParser(request);
			request.setMethodName(getMethodName(doc.getDocumentElement()));
			xmlParser.parse(doc);
			
		} catch (IOException e) {
			throw new XmlParserException(ProtoResponseFault.SERVER_EXCEPTION, 
					ProtoResponseFault.PARSE_ERROR, e);
		} catch (SAXException e) {
			throw new XmlParserException(ProtoResponseFault.SERVER_EXCEPTION, 
					ProtoResponseFault.PARSE_ERROR, e);
		} catch (ParserConfigurationException e) {
			throw new XmlParserException(ProtoResponseFault.SERVER_EXCEPTION, 
					ProtoResponseFault.PARSE_ERROR, e);
		} catch (Exception e) {
			throw new XmlParserException(ProtoResponseFault.REQUEST_CONSTRUCTION, 
					e.getMessage(), e);
		}
		
	}

	/**
	 * récupère le non de la method
	 * 
	 * @param e
	 * 		element
	 * @return le nom de la method
	 */
	private String getMethodName(Element e) {
		return DOMProcess.getFirstElement(e).getTextContent();
	}

}
