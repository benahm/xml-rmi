package com.aladyn.server;

import java.io.File;
import java.util.ArrayList;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import com.aladyn.parser.Request;
import com.aladyn.parser.XmlProcessFacade;
import com.aladyn.parser.exception.XmlProcessFacadeException;
import com.aladyn.server.exception.ProcessMethodCallException;
import com.aladyn.server.objects.SuperCalculator;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class MethodCallerTest {

	XmlProcessFacade process = new XmlProcessFacade();
	MethodCaller caller = new MethodCaller();
	SuperCalculator sharedObject = new SuperCalculator();
	Request request;
	ArrayList<Object> response;

	InterfacePool inters = InterfacePool.getInstance();

	@Before
	public void setUp() {
		request = null;
		response = null;
		inters.setInterfacePoolPackage("com.aladyn.shared.interfaces");

		InterfacePool.getInstance().setInterfacePoolPackage(
				"com.aladyn.shared.interfaces");
	}

	public void makeProcess(String xmlFile) throws XmlProcessFacadeException {
		synchronized (xmlFile) {
			request = process.process(new File(xmlFile));
		}
	}

	@Test
	public void testMethodCallMakeSum() {
		try {
			makeProcess("tests/com/aladyn/server/xml/callSuperCalculatorMakeSum.xml");
			response = caller.call(sharedObject, request);
		} catch (ProcessMethodCallException e) {
			e.printStackTrace();
		} catch (XmlProcessFacadeException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(response.get(0).equals(125));
	}

	@Test
	public void testMethodCallMakeSumObj() {
		try {
			makeProcess("tests/com/aladyn/server/xml/callSuperCalculatorMakeSumObj.xml");
			response = caller.call(sharedObject, request);
		} catch (ProcessMethodCallException e) {
			e.printStackTrace();
		} catch (XmlProcessFacadeException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(response.get(0).equals(new Double(20)));
		Assert.assertTrue(response.get(1).equals(request.getValue(0)));
	}

	// test deux appels avec deux objets différents
	@Test
	public void testMethodCallMakeSumObj2() {
		// premier objet tester
		try {
			makeProcess("tests/com/aladyn/server/xml/callSuperCalculatorMakeSumObj2.xml");
			response = caller.call(sharedObject, request);
		} catch (ProcessMethodCallException e) {
			e.printStackTrace();
		} catch (XmlProcessFacadeException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(response.get(0).equals(new Double(1100)));
		Assert.assertTrue(response.get(1).equals(request.getValue(0)));

		// seconde objet tester
		try {
			makeProcess("tests/com/aladyn/server/xml/callSuperCalculatorMakeSumObj3.xml");
			response = caller.call(sharedObject, request);
		} catch (ProcessMethodCallException e) {
			e.printStackTrace();
		} catch (XmlProcessFacadeException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(response.get(0).equals(new Double(12)));
		Assert.assertTrue(response.get(1).equals(request.getValue(0)));
	}

	// test deux appels avec le même objet
	@Test
	public void testMethodCallMakeSumObj3() {
		// premier objet tester
		try {
			makeProcess("tests/com/aladyn/server/xml/callSuperCalculatorMakeSumObj4.xml");
			response = caller.call(sharedObject, request);
		} catch (ProcessMethodCallException e) {
			e.printStackTrace();
		} catch (XmlProcessFacadeException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(response.get(0).equals(new Double(52)));
		Assert.assertTrue(response.get(1).equals(request.getValue(0)));

		// même objet testé deux fois
		try {
			makeProcess("tests/com/aladyn/server/xml/callSuperCalculatorMakeSumObj4.xml");
			response = caller.call(sharedObject, request);
		} catch (ProcessMethodCallException e) {
			e.printStackTrace();
		} catch (XmlProcessFacadeException e) {
			// peut pas construire deux fois un objet avec le même identifiant
			Assert.assertTrue(true);
			return;
		}
		Assert.assertFalse("devrait lancer une exception", false);
	}

	// test de MakeSumObjAndDouble
	@Test
	public void testMethodCallMakeSumObjAndDouble() {
		try {
			makeProcess("tests/com/aladyn/server/xml/callSuperCalculatorMakeSumObjAndDouble.xml");
			response = caller.call(sharedObject, request);
		} catch (ProcessMethodCallException e) {
			e.printStackTrace();
		} catch (XmlProcessFacadeException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(response.get(0).equals(31.1));
	}

	// test de MakeSumTabInt
	@Test
	public void testMethodCallMakeSumTabInt() {
		try {
			makeProcess("tests/com/aladyn/server/xml/callSuperCalculatorMakeSumTabInt.xml");
			response = caller.call(sharedObject, request);
		} catch (ProcessMethodCallException e) {
			e.printStackTrace();
		} catch (XmlProcessFacadeException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(response.get(0).equals(0));
	}

	// test de MakeSumStruct
	@Test
	public void testMethodCallMakeSumStruct() {
		try {
			makeProcess("tests/com/aladyn/server/xml/callSuperCalculatorMakeSumStruct.xml");
			response = caller.call(sharedObject, request);
		} catch (ProcessMethodCallException e) {
			e.printStackTrace();
		} catch (XmlProcessFacadeException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(response.get(0).equals(0));
	}

	// test de displayDate
	@Test
	public void testMethodCallDisplayDate() {
		try {
			makeProcess("tests/com/aladyn/server/xml/callSuperCalculatorDisplayDate.xml");
			response = caller.call(sharedObject, request);
		} catch (ProcessMethodCallException e) {
			e.printStackTrace();
		} catch (XmlProcessFacadeException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(response.get(0).toString()
				.equals("Sat Oct 13 01:19:01 CEST 2012"));
	}

	// test de displayString
	@Test
	public void testMethodCallDisplayString() {
		try {
			makeProcess("tests/com/aladyn/server/xml/callSuperCalculatorDisplayString.xml");
			response = caller.call(sharedObject, request);
		} catch (ProcessMethodCallException e) {
			e.printStackTrace();
		} catch (XmlProcessFacadeException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(response.get(0).equals("hello world"));
	}

	// test de displayBase64
	@Ignore
	public void testMethodCallDisplayBase64() {
		try {
			makeProcess("tests/com/aladyn/server/xml/callSuperCalculatorDisplayBase64.xml");
			response = caller.call(sharedObject, request);
		} catch (ProcessMethodCallException e) {
			e.printStackTrace();
		} catch (XmlProcessFacadeException e) {
			e.printStackTrace();
		}
		String str="adfkirjn";
		byte[] bytes= str.getBytes();
		 Assert.assertTrue(response.get(0).equals(bytes));
	}

	// test de Xor
	@Test
	public void testMethodCallXor() {
		try {
			makeProcess("tests/com/aladyn/server/xml/callSuperCalculatorXor.xml");
			response = caller.call(sharedObject, request);
		} catch (ProcessMethodCallException e) {
			e.printStackTrace();
		} catch (XmlProcessFacadeException e) {
			e.printStackTrace();
		}
		Assert.assertTrue((Boolean) response.get(0));
	}

	// test de MakeSumTabObj
	@Test
	public void testMethodCallMakeSumTabObj() {
		try {
			makeProcess("tests/com/aladyn/server/xml/callSuperCalculatorMakeSumTabObj.xml");
			response = caller.call(sharedObject, request);
		} catch (ProcessMethodCallException e) {
			e.printStackTrace();
		} catch (XmlProcessFacadeException e) {
			e.printStackTrace();
		}
		System.out.println(response.get(0));
		Assert.assertTrue((Double) response.get(0) == 20.0);
	}

	// test de MakeSumTabObj
	@Test
	public void testMethodCallMakeSumObjInObj() {
		try {
			makeProcess("tests/com/aladyn/server/xml/callSuperCalculatorMakeSumObjInObj.xml");
			response = caller.call(sharedObject, request);
		} catch (ProcessMethodCallException e) {
			e.printStackTrace();
		} catch (XmlProcessFacadeException e) {
			e.printStackTrace();
		}
		System.out.println(response.get(0));
		Assert.assertTrue((Double) response.get(0) == 1100.0);
	}

	// test de MakeSumStructObj
	@Test
	public void testMethodCallMakeSumStructObj() {
		try {
			makeProcess("tests/com/aladyn/server/xml/callSuperCalculatorMakeSumStructObj.xml");
			response = caller.call(sharedObject, request);
		} catch (ProcessMethodCallException e) {
			e.printStackTrace();
		} catch (XmlProcessFacadeException e) {
			e.printStackTrace();
		}
		System.out.println(response.get(0));
		Assert.assertTrue((Double) response.get(0) == 20.0);
	}
}
