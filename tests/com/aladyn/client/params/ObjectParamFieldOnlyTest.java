package com.aladyn.client.params;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.aladyn.client.exception.ToXMLException;
import com.aladyn.client.params.classesAndInterfaces.ClassExampleFieldOnly;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class ObjectParamFieldOnlyTest {

	ClassExampleFieldOnly classe;
	ObjectParamFieldOnly objectParam;

	@Before
	public void berforeTest() {
		classe = new ClassExampleFieldOnly();
		objectParam = new ObjectParamFieldOnly(classe);
	}

	@Test
	public void testToXML() {
		String buildXML = null;
		try {
			buildXML = objectParam.toXML().replaceAll(" ", "");
		} catch (ToXMLException e) {
			e.printStackTrace();
		}
		String expectedXML = FileRead.readFileAsString("tests/com/aladyn/client/params/xml/objectParamFieldOnlyFile.xml").replaceAll(" ", "");
		assertTrue(expectedXML.equals(buildXML));
	}


}
