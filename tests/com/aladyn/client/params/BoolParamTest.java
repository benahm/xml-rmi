package com.aladyn.client.params;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.aladyn.client.params.BoolParam;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class BoolParamTest {
	boolean b;
	BoolParam boolParam;

	@Before
	public void beforeTest() {
		b = true;
		boolParam = new BoolParam(b);
	}

	@Test
	public void testToXML() {
		String buildXML = boolParam.toXML().replaceAll(" ", "");
		if (b)
			assertTrue(buildXML.equals("<value><boolean>" + 1
					+ "</boolean></value>"));
		else
			assertTrue(buildXML.equals("<value><boolean>" + 0
					+ "</boolean></value>"));
	}

}
