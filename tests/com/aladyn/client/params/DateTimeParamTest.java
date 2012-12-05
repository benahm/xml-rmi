package com.aladyn.client.params;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class DateTimeParamTest {
	Date d;
	DateTimeParam dateTimeParam;

	@Before
	public void beforeTest() {
		d =new Date();
		dateTimeParam = new DateTimeParam(d);
	}

	@Test
	public void testToXML() {
		String buildXML=dateTimeParam.toXML();
		System.out.println(buildXML);
		String theDate=new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss").format(d);
		String expectXML="<value><dateTime.iso8601>"+theDate+"</dateTime.iso8601></value>";
		assertTrue(buildXML.equals(expectXML));
	}

}
