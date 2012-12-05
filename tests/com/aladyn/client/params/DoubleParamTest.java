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
public class DoubleParamTest {
	double d;
	DoubleParam doubleParam;
	@Before
	public void beforeTest(){
		d=3.14159;
		doubleParam=new DoubleParam(d);
	}
	@Test
	public void testToXML() {
		assertTrue(doubleParam.toXML().equals("<value><double>" + d + "</double></value>"));
	}

}
