package com.aladyn.client;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class IdTableTest {
	Object o;
	Object oprim;
	String put;
	@Before
	public void setUp() throws Exception {
		IdTable.clear();
		o = new Object();
		oprim = new Object();
		put = IdTable.put(o);
	}

	@Test
	public void testPut() {
		assertTrue(!IdTable.put(oprim).equals(put));
	}

	@Test
	public void testGet() {
		assertTrue(IdTable.get(put).equals(o));
		assertTrue(IdTable.get("toto") == null);
	}

}
