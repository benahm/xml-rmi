package com.aladyn.client;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.aladyn.client.exception.UpdateException;
import com.aladyn.client.params.ObjectParam;
import com.aladyn.client.params.classesAndInterfaces.ClassExample;
import com.aladyn.client.params.classesAndInterfaces.InterfaceExample;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class UpdaterTest {

	ClassExample object;
	InterfaceExample inter;
	ObjectParam objectParam;
	Updater update = new Updater();

	@Before
	public void berforeTest() {
		IdTable.clear();
		object = new ClassExample();
		objectParam = new ObjectParam(object, InterfaceExample.class);
		IdTable.put(objectParam);
	}

	public void testUpdateString() {
		
		String xmlStringResponse = "<methodResponse> <params> <param> <value><int>10</int></value></param> "
				+ "<param> <value> "
				+ "<object oid=\"aladyn.com/xml-rmi/101\" >"
				+ "<fields>"
				+ "<field name =\"i\"><value><int>-9</int></value></field>"
				+ "<field name = \"d\"><value><double>3.2</double></value></field>"
				+ "</fields>"
				+ "<methods></methods>"
				+ "</object> </value>"
				+ "</param> </params></methodResponse>";
		
		try {
			update.update(xmlStringResponse);
		} catch (UpdateException e) {
			e.printStackTrace();
		}
		
		assertTrue(object.d == 3.2 && object.i == -9);
	}

	public void testUpdateFile() {

		try {
			update.update(new File("tests/com/aladyn/client/xml/updateTestFile.xml"));
		} catch (UpdateException e) {
			e.printStackTrace();
		}
		
		assertTrue(object.d == 19.9 && object.i == -213);
	}
	
	@Test
	public void test() {
		testUpdateString();
		testUpdateFile();
	}

}
