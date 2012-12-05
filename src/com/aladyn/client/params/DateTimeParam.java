package com.aladyn.client.params;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * DateTimeParam Class qui encapsule les valeurs dateTime.iso8601 
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * 
 */
public class DateTimeParam implements IParam {

	Date date;
	
	public DateTimeParam(Object date) {
		this.date=(Date) date;
	}
	
	@Override
	public String getType() {
		return "datetime";
	}

	@Override
	public String toXML() {
	String dateFormated = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss").format(date);
		// return la date en format iso8601
		return "<value><dateTime.iso8601>" +dateFormated+"</dateTime.iso8601></value>";
	}

}
