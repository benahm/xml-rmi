package com.aladyn.parser;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class RequestTest
{

	private static Request request;
	
	@BeforeClass
	public static void setUpClass() {
		request = new Request();
		
		request.setMethodName("uneMethode");
		
		request.addValue((Object)new Integer(5));
		request.addType(Integer.class);
		
		request.addValue((Object) ((double) 5) );
		request.addType(double.class);
		
		request.addValue((Object)new String("toto"));
		request.addType(String.class);
	}
	
	@Test
	public final void testGetMethodName()
	{
		assertEquals("uneMethode", request.getMethodName());
	}

	@Test
	public final void testGetValues()
	{
		assertEquals(3, request.getValues().size());
		assertEquals(new Integer(5), request.getValue(0));
		assertEquals(5.0, request.getValue(1));
		assertEquals(new String("toto"), request.getValue(2));
	}

	@Test
	public final void testGetValuesArray()
	{
		Object[] values = request.getValuesArray();
		assertEquals(3, values.length);
		assertEquals(new Integer(5), values[0]);
		assertEquals(5.0, values[1]);
		assertEquals(new String("toto"), values[2]);
	}

	@Test
	public final void testGetTypes()
	{
		assertEquals(3, request.getTypes().size());
		assertEquals(Integer.class, request.getType(0));
		assertEquals(double.class, request.getType(1));
		assertEquals(String.class, request.getType(2));
	}

	@Test
	public final void testGetTypeArray()
	{
		Object[] types = request.getTypeArray();
		assertEquals(3, types.length);
		assertEquals(Integer.class, types[0]);
		assertEquals(double.class, types[1]);
		assertEquals(String.class, types[2]);
	}

}
