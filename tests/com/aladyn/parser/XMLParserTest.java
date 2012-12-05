package com.aladyn.parser;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.aladyn.server.InterfacePool;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class XMLParserTest {

	XMLParser xmlParser;
	Document doc = null;
	Request request;
    DocumentBuilder db;
	@Before
	public void setUp() throws Exception {
		InterfacePool.getInstance().setInterfacePoolPackage("com.aladyn.shared.interfaces");
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		request = new Request();
		xmlParser = new XMLParser(request);
	}

	// test les type simple
	@Test
	public void testParse1() {
		try {
			doc = db.parse("tests/com/aladyn/parser/xml/xml1.xml");
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		xmlParser.parse(doc);
		System.out.println(request);
		assertTrue(request.getType(0) == int.class
				&& (Integer) request.getValue(0) == 41);
		assertTrue(request.getType(1) == boolean.class
				&& (Boolean) request.getValue(1) == true);
		assertTrue(request.getType(2) == double.class
				&& (Double) request.getValue(2) == 42.0);
		assertTrue(request.getType(3) == String.class
				&& ((String) request.getValue(3)).equals("hello"));
		assertTrue(request.getType(4) == Date.class
				&& request.getValue(4).toString()
						.equals("Sat Oct 13 01:19:01 CEST 2012"));
		assertTrue(request.getType(5) == byte[].class);
	}

	// test struct
	@Test
	public void testParse2() {
		try {
			doc = db.parse("tests/com/aladyn/parser/xml/xml2.xml");
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("-------------------------------");
		xmlParser.parse(doc);
		System.out.println(request);
		assertTrue(request.getType(0) == Hashtable.class);
		@SuppressWarnings("unchecked")
		Hashtable<String, Object> hash = (Hashtable<String, Object>) request.getValue(0);
		assertTrue((Integer)hash.get("upperBound")==139);
		assertTrue((Integer)hash.get("lowerBound")==18);
	}
	
	// test Array
	@Test 
	public void testParse3(){
		try {
			doc = db.parse("tests/com/aladyn/parser/xml/xml3.xml");
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("-------------------------------");
		xmlParser.parse(doc);
		System.out.println(request);
		assertTrue(request.getType(0) == Vector.class);
		@SuppressWarnings("unchecked")
		Vector<Object> vect=(Vector<Object>) request.getValue(0);
		assertTrue((Integer)vect.get(0)==12);
		assertTrue(vect.get(1).equals("Egypt"));
		assertTrue(!(Boolean)vect.get(2));
		assertTrue((Integer)vect.get(3)==-31);
	}
	
	//test Object
	@Test 
	public void testParse4(){
		try {
			doc = db.parse("tests/com/aladyn/parser/xml/xml4.xml");
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("-------------------------------");
		xmlParser.parse(doc);
		System.out.println(request);
		Object myObject=request.getValue(0);
		Class<?> myClass=myObject.getClass();
		Field[] fields=myClass.getDeclaredFields();
		try {
			assertTrue(fields[0].get(myObject).equals("http://www.jm.fr/10999998"));
			assertTrue((Double)fields[1].get(myObject)==10.0);
			assertTrue((Double)fields[2].get(myObject)==20.0);
			assertTrue(fields[3].get(myObject).equals("@"));
		} catch (IllegalArgumentException e) {
			fail("Exception");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			fail("Exception");
			e.printStackTrace();
		}
		Method[] methods=myClass.getDeclaredMethods();
		assertTrue(methods[0].getName().equals("toString"));
	}
	
	//test Object in Array
	@Test
	public void testParse5() {
		(InterfacePool.getInstance()).setInterfacePoolPackage("com.aladyn.client.params.classesAndInterfaces");
		try {
			doc = db.parse("tests/com/aladyn/parser/xml/xml5.xml");
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("-------------------------------");
		xmlParser.parse(doc);
		System.out.println(request);
		
		assertTrue(request.getType(0) == Vector.class);
		@SuppressWarnings("unchecked")
		Vector<Object> vect=(Vector<Object>) request.getValue(0);
		assertTrue((Integer)vect.get(0)==12);
		assertTrue(vect.get(1).equals("Egypt"));
		assertTrue(!(Boolean)vect.get(2));
		assertTrue((Integer)vect.get(3)==-31);
		
		Object myObject=vect.get(4);
		Class<?> myClass=myObject.getClass();
		Field[] fields=myClass.getDeclaredFields();
		try {
			assertTrue(fields[0].get(myObject).equals("http://www.jm.fr/1700000"));
			assertTrue((Double)fields[1].get(myObject)==10);
			assertTrue((Double)fields[2].get(myObject)==20);
			assertTrue(fields[3].get(myObject).equals("@"));
		} catch (IllegalArgumentException e) {
			fail("Exception");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			fail("Exception");
			e.printStackTrace();			
		}
		Method[] methods=myClass.getDeclaredMethods();
		assertTrue(methods[0].getName().equals("toString"));
	}
	
	//test Object in Array in struct
	@Test
	public void testParse6() {
		try {
			doc = db.parse("tests/com/aladyn/parser/xml/xml6.xml");
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("-------------------------------");
		xmlParser.parse(doc);
		System.out.println(request);
		
		assertTrue(request.getType(0) == Hashtable.class);
		@SuppressWarnings("unchecked")
		Hashtable<String, Object> hash=(Hashtable<String, Object>) request.getValue(0);
		assertTrue((Integer)hash.get("upperBound")==139);
		assertTrue((Integer)hash.get("lowerBound")==18);
		
		assertTrue(hash.get("array").getClass() == Vector.class);
		@SuppressWarnings("unchecked")
		Vector<Object> vect=(Vector<Object>) hash.get("array");
		assertTrue((Integer)vect.get(0)==12);
		assertTrue(vect.get(1).equals("Egypt"));
		assertTrue(!(Boolean)vect.get(2));
		assertTrue((Integer)vect.get(3)==-31);
		
		Object myObject=vect.get(4);
		Class<?> myClass=myObject.getClass();
		Field[] fields=myClass.getDeclaredFields();
		try {
			assertTrue(fields[0].get(myObject).equals("aladyn.com/xml-rmi/101"));
		} catch (IllegalArgumentException e) {
			fail("Exception");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		Method[] methods=myClass.getDeclaredMethods();
		assertTrue(methods[0].getName().equals("getX"));
		assertTrue(methods[1].getName().equals("getY"));
		
	}

}
