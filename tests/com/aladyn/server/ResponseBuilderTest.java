package com.aladyn.server;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.aladyn.client.params.FileRead;

import com.aladyn.server.exception.ResponseBuilderException;
import com.aladyn.server.sharedObjectsTest.*;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class ResponseBuilderTest {
	
	ResponseBuilder rBuilder;
	
	ArrayList<Object> result;
	
	int faultCode;
	String faultString;

	@Before
	public void setUp() throws Exception {
		result = new ArrayList<Object>();
		rBuilder = new ResponseBuilder();
		
		faultCode = 0;
		faultString = "";
	}

	@Test
	public void testBuildXmlResponse() {
		result.add(new ObjectTest123());
		String response = null;
		try	{
			response = rBuilder.buildXmlResponse(result).replaceAll("\\s+", "");
		} catch (ResponseBuilderException e) {
			e.printStackTrace();
		}
		String expectedResponse = FileRead.readFileAsString("tests/com/aladyn/server/xml/BuilderResponseObject.xml").replaceAll("\\s+", "");
		assertTrue(expectedResponse.equals(response));
	}

	@Test
	public void testBuildXmlFaultResponse() {
		faultCode = 300;
		faultString = "JUnit";
		String response = null;
		try	{
			response = rBuilder.buildXmlFaultResponse(faultCode, faultString).replaceAll("\\s+", "");
		} catch (ResponseBuilderException e) {			
			e.printStackTrace();
		}
		String expectString = FileRead.readFileAsString("tests/com/aladyn/server/xml/BuilderResponseFault.xml").replaceAll("\\s+", "");
		String expectStringInverse = FileRead.readFileAsString("tests/com/aladyn/server/xml/BuilderResponseFaultInverse.xml").replaceAll("\\s+", "");
		
		System.out.println(response);
		System.out.println(expectString);
		System.out.println(expectStringInverse);
		
		assertTrue(expectString.equals(response)
				|| expectStringInverse.equals(response)); 
	}

}
