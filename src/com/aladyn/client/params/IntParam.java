package com.aladyn.client.params;

/**
 * IntParam Class qui encabsule un entier
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * 
 */

public class IntParam implements IParam {
	
	Integer integer;

	public IntParam(Object integer) {
		this.integer = (Integer) integer;
	}

	@Override
	public String getType() {
		return "int";
	}

	@Override
	public String toXML() {
		return "<value> <int>" + integer + "</int> </value>";
	}

}
