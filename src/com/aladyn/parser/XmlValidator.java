package com.aladyn.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import com.aladyn.ProtoResponseFault;
import com.aladyn.parser.exception.NotValidXmlForSchemaException;

/**
 * Validateur d'un document XML selon une règle RelaxNG Le document peut être de
 * plusieurs formes : Une chaine de caractère, un fichier, un flux binnaire ou
 * de caractères
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * 
 */
public class XmlValidator {
	String relaxNgFileSource;

	Validator validator;
	InputStreamReader xmlReader;
	SchemaFactory sf;
	Schema schemaSource;
	Source source;

	public XmlValidator(String relaxNgFileSource) {
		this.relaxNgFileSource = relaxNgFileSource;
	}


	/**
	 * Prend un document XML contenu dans une chaine de caractère
	 * 
	 * @param xmlString
	 * @param relaxNgFileSource
	 * @throws NotValidXmlForSchemaException
	 */
	public void isValidate(String xmlString)
			throws NotValidXmlForSchemaException {

		doIsValidate(new StreamSource(new StringReader(xmlString)),
				relaxNgFileSource);
	}

	/**
	 * Prend un fichier
	 * 
	 * @param xmlFile
	 * @param relaxNgFileSource
	 * @throws NotValidXmlForSchemaException
	 */
	public void isValidate(File xmlFile) throws NotValidXmlForSchemaException {
		try {
			doIsValidate(new StreamSource(new InputStreamReader(
					new FileInputStream(xmlFile))), relaxNgFileSource);
		} catch (FileNotFoundException e) {
			throw new NotValidXmlForSchemaException(100, "Fichier "
					+ xmlFile.getName() + " non trouvé", e);
		}
	}

	public void isValidate(InputStream xmlStream)
			throws NotValidXmlForSchemaException {
		doIsValidate(new StreamSource(new InputStreamReader(xmlStream)),
				relaxNgFileSource);
	}


	/**
	 * Prend un flux binaire
	 * 
	 * @param xmlSource
	 * @param relaxNgFileSource
	 * @throws NotValidXmlForSchemaException
	 */
	public void isValidate(InputStream xmlStream, String relaxNgFileSource)
			throws NotValidXmlForSchemaException {
		doIsValidate(new StreamSource(new InputStreamReader(xmlStream)),
				relaxNgFileSource);
	}

	/**
	 * Prend un flux de caractères
	 * 
	 * @param xmlSource
	 * @param relaxNgFileSource
	 * @throws NotValidXmlForSchemaException
	 */
	public void isValidate(InputStreamReader xmlReader, String relaxNgFileSource)
			throws NotValidXmlForSchemaException {
		doIsValidate(new StreamSource(xmlReader), relaxNgFileSource);
	}

	/**
	 * Validation d'un XML 
	 * 
	 * @param xmlSource
	 * @param relaxNgFileSource
	 * @throws NotValidXmlForSchemaException
	 */
	public void doIsValidate(Source source, String relaxNgFileSource)
			throws NotValidXmlForSchemaException {
		
		System.setProperty(SchemaFactory.class.getName() + ":"
				+ XMLConstants.RELAXNG_NS_URI,
				"com.thaiopensource.relaxng.jaxp.CompactSyntaxSchemaFactory");

		sf = SchemaFactory.newInstance(XMLConstants.RELAXNG_NS_URI);

		try {
			sf.setProperty(
					"http://relaxng.org/properties/datatype-library-factory",
					new org.relaxng.datatype.helpers.DatatypeLibraryLoader());
		} catch (SAXNotRecognizedException e) {
			throw new NotValidXmlForSchemaException(ProtoResponseFault.SERVER_EXCEPTION,
					ProtoResponseFault.LIBRARY_SCHEMA, e);
		} catch (SAXNotSupportedException e) {
			throw new NotValidXmlForSchemaException(ProtoResponseFault.SERVER_EXCEPTION,
					ProtoResponseFault.LIBRARY_SCHEMA, e);
		}

		try {
			schemaSource = sf.newSchema(new URL(relaxNgFileSource));
		} catch (SAXException e) {
			throw new NotValidXmlForSchemaException(ProtoResponseFault.XML_DOCUMENT_NOT_VALID,
					ProtoResponseFault.NOT_VALID_RNG, e);
		} catch (MalformedURLException e) {
			throw new NotValidXmlForSchemaException(ProtoResponseFault.XML_DOCUMENT_NOT_VALID,
					ProtoResponseFault.NOT_VALID_RNG, e);
		}

		validator = schemaSource.newValidator();

		try {
			validator.validate(source);
		} catch (SAXException e) {
			throw new NotValidXmlForSchemaException(ProtoResponseFault.XML_DOCUMENT_NOT_VALID,
					ProtoResponseFault.NOT_VALID_XML, e);
		} catch (IOException e) {
			throw new NotValidXmlForSchemaException(ProtoResponseFault.SERVER_EXCEPTION,
					ProtoResponseFault.VALIDATION_ERROR, e);
		}

	}


}