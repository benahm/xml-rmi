package com.aladyn.client.params;

import static org.junit.Assert.assertEquals;

import java.util.Hashtable;

import org.junit.Before;
import org.junit.Test;

import com.aladyn.client.exception.ToXMLException;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class StructParamTest {

	Hashtable<String, Object> struct;
	StructParam structParam;
	BoolParam boolParam;

	@Before
	public void beforeTest() {
		struct = new Hashtable<String, Object>();
		struct.put("boolean", true);
		struct.put("int", 2012);
		struct.put("double", 3.14159);
		structParam = new StructParam(struct);

	}

	@Test
	public void testToXML() {
		String buildXML = null;
		try {
			buildXML = structParam.toXML().replaceAll(" ", "");
		} catch (ToXMLException e) {
			e.printStackTrace();
		}
		String expectedXML = FileRead.readFileAsString(
				"tests/com/aladyn/client/params/xml/structParamFile.xml")
				.replaceAll(" ", "");
		assertEquals(expectedXML, buildXML);
	}

}
