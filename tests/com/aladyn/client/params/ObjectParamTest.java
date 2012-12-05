package com.aladyn.client.params;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.aladyn.client.IdTable;
import com.aladyn.client.exception.ToXMLException;
import com.aladyn.client.exception.UpdateException;
import com.aladyn.client.params.classesAndInterfaces.ClassExample;
import com.aladyn.client.params.classesAndInterfaces.InterfaceExample;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class ObjectParamTest {

	ClassExample object = new ClassExample();
	InterfaceExample inter;
	ObjectParam objectParam = new ObjectParam(object, InterfaceExample.class);
	
	@Before
	public void setUp() {
		IdTable.clear();
	}

	@Test
	public void testToXML() {
		String buildXML = null;
		try {
			buildXML = objectParam.toXML().replaceAll(" ", "");
		} catch (ToXMLException e) {
			e.printStackTrace();
		}
		String expectedXML = FileRead.readFileAsString(
				"tests/com/aladyn/client/params/xml/objectParamToXMLFile.xml")
				.replaceAll(" ", "");
		System.out.println(buildXML);
		System.out.println(expectedXML);
		assertTrue(expectedXML.equals(buildXML));
	}

	@Test
	public void testUpdateFromXML() {
		IdTable.put(objectParam);
		Document doc = null;
		try {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse("tests/com/aladyn/client/params/xml/objectParamUpdateFromXMLFile.xml");
			Element xml = doc.getDocumentElement();
			objectParam.updateFromXML(xml);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UpdateException e) {
			e.printStackTrace();
		}
		assertTrue(object.d==0.8888 && object.i==222);
	}
}
