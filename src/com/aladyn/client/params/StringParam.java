package com.aladyn.client.params;

/**
 * StringParam class qui encapsule un string pour l"envoie
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * 
 */
public class StringParam implements IParam {

	String stringval;

	public StringParam(Object stringval)  {
		String val = (String) stringval;
		this.stringval = val;
	}

	@Override
	public String getType() {
		return "string";
	}

	@Override
	public String toXML() {
		return "<value><string>" + stringval + "</string></value>";
	}

}
