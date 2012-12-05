package com.aladyn.parser;


import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.aladyn.parser.exception.XmlParserException;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class XMLFileParserTest
{

	private XMLFileParser parser;
	private Request request;
	
	@Before
	public void setUp()
	{
		parser = new XMLFileParser();
	    request =new Request();
	}
	
	@Test
	public final void parse_file_true_1()
	{
		try	{
			 parser.parse(new File("tests/com/aladyn/parser/xml/xml1.xml"),request);
		} catch (XmlParserException e)	{
			Assert.assertTrue(false);
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
	
	@Test
	public final void parse_string_true_1()
	{
		String xml = "<?xml version='1.0'?>" +
				"<methodCall>" +
					"<methodName>makeSum</methodName>" +
					"<params>" +
						"<param><value><int>41</int></value></param>" +
						"<param><value><double>42</double></value></param>" +
					"</params>" +
				"</methodCall>";
		
		try	{
			parser.parse(xml,request);
		} catch (XmlParserException e) {
			Assert.assertTrue(false);
			return;
		}
		
		Assert.assertTrue(request.getMethodName().equals("makeSum"));
		Assert.assertTrue(request.getType(0).equals(int.class));
		Assert.assertTrue(request.getType(1).equals(double.class));
		Assert.assertTrue((Integer) request.getValue(0) == 41);
		Assert.assertTrue((Double) request.getValue(1) == 42);
	}
	
	@Test
	public final void parse_inputStream_true_1()
	{
		
	}
	
	@Test
	public final void parse_inputStreamReader_true_1()
	{
		
	}
	
	@Test
	public final void parse_false_1()
	{
		try
		{
			parser.parse(new File("src/com/aladyn/xml/xmlDontExist.xml"),request);
			Assert.assertTrue(false);
		}
		catch (XmlParserException e)
		{
			Assert.assertTrue(true);
		}
	}

}
