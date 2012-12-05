package com.aladyn.client;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.aladyn.client.ClientSide;
import com.aladyn.client.exception.NotConformNameMethodException;
import com.aladyn.client.params.FileRead;
import com.aladyn.client.params.IParam;
import com.aladyn.client.params.IntParam;
import com.aladyn.client.params.ObjectParam;
import com.aladyn.client.params.classesAndInterfaces.ClassExample2;
import com.aladyn.client.params.classesAndInterfaces.InterfaceExample;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class ClientSideTest {
	
	int i;
	IntParam intParam;
	ClassExample2 classe;
	InterfaceExample inter;
	ObjectParam objectParam;
	String methodName = "display";
	IParam params[]=new IParam[2];

	@Before
	public void setUp() throws Exception {
		i = 10;
		IdTable.clear();
		intParam = new IntParam(i);
		classe = new ClassExample2();
		objectParam = new ObjectParam(classe, InterfaceExample.class);
		params[0] = intParam;
		params[1] = objectParam;
	}

	@Test
	public void testMethodCall() {
		String buildXML = "";
		try {
			buildXML = ClientSide.buildRequestl(methodName, params).replaceAll(" ", "");
		} catch (NotConformNameMethodException e) {
			e.printStackTrace();
		}
		String expectedXML = FileRead.readFileAsString("tests/com/aladyn/client/xml/clientSideFile.xml").replaceAll(" ", "");
		System.out.println(buildXML);
		System.out.println(expectedXML);
		assertTrue(expectedXML.equals(buildXML));
	}

}
