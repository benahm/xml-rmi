package com.aladyn.client.params;


/**
 * Base64Param Class qui encapsule les types base64 de xml-rpc
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * 
 */
public class Base64Param implements IParam {
	
	Byte[] base;

	public Base64Param(Object object) {
		this.base = (Byte[]) object;
	}

	@Override
	public String getType() {
		return "base64";
	}

	@Override
	public String toXML() {
		StringBuffer xml = new StringBuffer("<value><base64>");
		for (int i = 0; i < base.length; i++) {
			xml.append(base[i]);
		}
		xml.append("</base64></value>");
		return xml.toString();
	}

}
