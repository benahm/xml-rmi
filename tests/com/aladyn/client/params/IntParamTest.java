package com.aladyn.client.params;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.aladyn.client.params.IntParam;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class IntParamTest {
	int i;
	IntParam intParam;
	@Before
	public void beforeTest(){
		i=10;
		intParam=new IntParam(i);
	}
	@Test
	public void testToXML() {
		assertTrue(intParam.toXML().equals("<value> <int>" + i + "</int> </value>"));
	}

}
