package com.aladyn.parser;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class DOMProcessTest {

	Node doc;
	@Before
	public void setUp() throws Exception {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse("tests/com/aladyn/parser/xml/xml1.xml").getDocumentElement();

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetFirstElement() {
		assertTrue(DOMProcess.getFirstElement(doc).getNodeName().equals("methodName"));
	}

	@Test
	public void testGetSecondElement() {
		assertTrue(DOMProcess.getSecondElement(doc).getNodeName().equals("params"));
	}

}
