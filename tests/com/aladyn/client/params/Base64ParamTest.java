package com.aladyn.client.params;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class Base64ParamTest {
	Byte[] b = { 1,2,3,4,5,6,7,8 };
	Base64Param base64Param;

	@Before
	public void beforeTest() {
		base64Param = new Base64Param(b);
	}

	@Test
	public void testToXML() {
		String build=base64Param.toXML();
		String expect="<value><base64>12345678</base64></value>";
		assertTrue(build.equals(expect));
	}

}
