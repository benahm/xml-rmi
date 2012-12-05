package com.aladyn.server;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.aladyn.client.objects.Obj;
import com.aladyn.parser.exception.InterfaceNotFoundException;
import com.aladyn.server.InterfacePool;
import com.aladyn.shared.interfaces.Inter;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class InterfacePoolTest {

	InterfacePool iPool;

	@Before
	public void setUp() throws Exception {
		iPool = InterfacePool.getInstance();
		
	}

	@Test
	public void testFetchInterfaceTrue() {
		iPool.setInterfacePoolPackage("com.aladyn.shared.interfaces");
		try {
			assertTrue(iPool.fetchInterface(Obj.class) == Inter.class);
		} catch (InterfaceNotFoundException e) {
			fail();
		}

	}

	@Test
	public void testFetchInterfaceFalse() {
		iPool.setInterfacePoolPackage("PackageDoesNTExits");
		try {
			iPool.fetchInterface(Obj.class);
			fail();
		} catch (InterfaceNotFoundException e) {
			assertTrue(true);
		}

	}
}
