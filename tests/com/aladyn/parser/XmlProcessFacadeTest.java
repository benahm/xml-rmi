package com.aladyn.parser;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.aladyn.client.IdTable;
import com.aladyn.parser.exception.XmlProcessFacadeException;
import com.aladyn.server.InterfacePool;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class XmlProcessFacadeTest
{

	private XmlProcessFacade processXml = new XmlProcessFacade();
	private Request request;
	
	@Before
	public void setUp() {
		InterfacePool.getInstance().setInterfacePoolPackage("com.aladyn.shared.interfaces");
		IdTable.clear();
	}
	
	@Test
	public final void process_file_true_1()
	{
		try	{
			request = processXml.process(new File("tests/com/aladyn/parser/xml/xml1.xml"));
		} catch (XmlProcessFacadeException e) {
			assertTrue(false);
			return;
		}
		Assert.assertTrue(request.getMethodName().equals("makeSum"));
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
	
	// teste deux désérialisations successives avec 
	// des objets différents
	@Test
	public final void process_file_true_2()
	{
		try	{
			request = processXml.process(new File("tests/com/aladyn/parser/xml/xml4-1.xml"));
		} catch (XmlProcessFacadeException e) {
			assertTrue(false);
			return;
		}
		Object myObject = request.getValue(0);
		Class<?> myClass = myObject.getClass();
		Field[] fields=myClass.getDeclaredFields();
		try {
			assertTrue(fields[0].get(myObject).equals("http://www.jm.fr/7777777"));
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

		Method[] methods = myClass.getDeclaredMethods();
		assertTrue(methods[0].getName().equals("toString"));
		
		try	{
			request = processXml.process(new File("tests/com/aladyn/parser/xml/xml4-2.xml"));
		} catch (XmlProcessFacadeException e) {
			Assert.assertTrue(false);
			return;
		}
		Object myObject2 = request.getValue(0);
		Class<?> myClass2 = myObject2.getClass();
		Field[] fields2 = myClass2.getDeclaredFields();
		try {
			assertTrue(fields2[0].get(myObject2).equals("http://www.jm.fr/1111"));
			assertTrue((Double)fields2[1].get(myObject2)==10.0);
			assertTrue((Double)fields2[2].get(myObject2)==40.0);
			assertTrue(fields2[3].get(myObject2).equals("^"));
		} catch (IllegalArgumentException e) {
			fail("Exception");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			fail("Exception");
			e.printStackTrace();
		}
		
		Method[] methods2 = myClass2.getDeclaredMethods();
		assertTrue(methods2[0].getName().equals("toString"));
	}
	
	// teste deux désérialisations successives avec 
	// le même objet
	@Test
	public final void process_file_true_3()
	{
		try	{
			request = processXml.process(new File("tests/com/aladyn/parser/xml/xml5-1.xml"));
		} catch (XmlProcessFacadeException e) {
			assertTrue(false);
			return;
		}
		
		try	{
			request = processXml.process(new File("tests/com/aladyn/parser/xml/xml5-1.xml"));
		} catch (Exception e) {
			assertTrue(true);
			return;
		}
		
		assertTrue("on ne devrait pas être ici", false);
	}
	
	@Test
	public final void process_string_true_1()
	{
		String xml = "<?xml version='1.0'?>" +
				"<methodCall>" +
				"  <methodName>makeSum</methodName>" +
					"<params>" +
						" <param> <value><int>41</int></value></param>" +
						"<param><value> <double>42</double>   </value></param>" +
						"<param><value> <double>678</double>   </value></param>" +
					"  </params>" +
				"</methodCall>";
		try	{
			request = processXml.process(xml);
		} catch (XmlProcessFacadeException e) {
			Assert.assertTrue(false);
			return;
		}
		Assert.assertTrue(request.getMethodName().equals("makeSum"));
		assertTrue(request.getType(0) == int.class
				&& (Integer) request.getValue(0) == 41);
		assertTrue(request.getType(1) == double.class
				&& (Double) request.getValue(1) == 42.0);
		assertTrue(request.getType(2) == double.class
				&& (Double) request.getValue(2) == 678);
	}
	
	@Test
	public final void process_inputStream_true_1()
	{
		
	}
	
	@Test
	public final void process_inputStreamReader_true_1()
	{
		
	}
	
	@Test
	public final void process_false_1()
	{
		try	{
			request = processXml.process("xml1DontExist.xml");
			Assert.assertTrue(false);
		} catch (XmlProcessFacadeException e) {
			Assert.assertTrue(true);
		}
	}

}
