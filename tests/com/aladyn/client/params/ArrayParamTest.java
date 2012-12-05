package com.aladyn.client.params;

import static org.junit.Assert.assertEquals;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import com.aladyn.client.exception.ToXMLException;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class ArrayParamTest {

	Vector<Object> array;
	ArrayParam arrayParam;
	BoolParam boolParam;

	@Before
	public void beforeTest() {
		array = new Vector<Object>();
		array.add("boolean");
		array.add("int");
		array.add("double");
		arrayParam = new ArrayParam(array);

	}

	@Test
	public void testToXML() {
		String buildXML = null;
		try {
			buildXML = arrayParam.toXML().replaceAll(" ", "");
		} catch (ToXMLException e) {
			e.printStackTrace();
		}
		String expectedXML = FileRead.readFileAsString("tests/com/aladyn/client/params/xml/arrayParamFile.xml").replaceAll(" ", "");
		assertEquals(expectedXML,buildXML);
	}

}
