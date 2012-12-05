package com.aladyn.client.params;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class StringParamTest {
	String text;
	StringParam stringParam;

	@Before
	public void beforeTest() {
		text = "ileteunefois";
		stringParam = new StringParam(text);
	}

	@Test
	public void testToXML() {
		assertTrue(stringParam.toXML().equals(
				"<value><string>" + text + "</string></value>"));
	}
}
