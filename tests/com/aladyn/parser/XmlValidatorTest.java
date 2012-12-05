package com.aladyn.parser;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.aladyn.parser.exception.NotValidXmlForSchemaException;
import com.aladyn.server.relaxng.RelaxNGLoader;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class XmlValidatorTest
{

	XmlValidator validator = new XmlValidator(RelaxNGLoader.getResource());

	@Test
	public final void isValidate_file_true_1()
	{
		try
		{
			validator.isValidate(new File("tests/com/aladyn/parser/xml/xml1.xml"));
		}
		catch (NotValidXmlForSchemaException e)
		{
			e.printStackTrace();
			Assert.assertTrue(false);
		}		
		Assert.assertTrue(true);
	}
	
	@Test
	public final void isValidate_string_true_1()
	{
		String xml = "<?xml version='1.0'?><methodCall><methodName>makeSum" +
				"</methodName><params><param><value><int>41</int>" +
				"</value></param><param><value><double>42</double>" +
				"</value></param></params></methodCall>";
		try
		{
			validator.isValidate(xml);
		}
		catch (NotValidXmlForSchemaException e)
		{
			e.printStackTrace();
			Assert.assertTrue(false);
		}		
		Assert.assertTrue(true);
	}
	
	@Test
	public final void isValidate_inputStream_true_1()
	{
	}
	
	@Test
	public final void isValidate_inputStreamReader_true_1()
	{
	}
	
	/**
	 * Teste si un document XML mal formé est refusé 
	 */
	@Test
	public final void isValidate_false_1()
	{
		try
		{
			validator.isValidate(new File("src/com/aladyn/parser/xml/xmlNotValid1.xml"));
			Assert.assertTrue("doit lever une exception dans isValidate_false_1", true);
		}
		catch (NotValidXmlForSchemaException e)
		{
			Assert.assertTrue(true);
		}		
	}
	
	/**
	 * le document XML ne correspondant pas à la grammaire 
	 */
	@Test
	public final void isValidate_false_2()
	{
		try
		{
			validator.isValidate(new File("src/com/aladyn/parser/xml/xmlNotValid2.xml"));
			Assert.assertTrue("doit lever une exception dans isValidate_false_2", true);
		}
		catch (NotValidXmlForSchemaException e)
		{
			Assert.assertTrue(true);
		}		
	}
	
	/**
	 * le fichier XML n'existe pas 
	 */
	@Test
	public final void isValidate_false_3()
	{
		try
		{
			validator.isValidate("src/com/aladyn/parser/xml/nexistepas.xml");
			Assert.assertTrue("doit lever une exception dans isValidate_false_3", true);
		}
		catch (NotValidXmlForSchemaException e)
		{
			Assert.assertTrue(true);
		}		
	}

}
