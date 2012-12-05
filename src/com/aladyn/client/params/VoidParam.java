package com.aladyn.client.params;

/**
 * VoidParam param spécifier le type void
 *  
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class VoidParam implements IParam {

	@Override
	public String getType() {
		return "void";
	}

	@Override
	public String toXML() {
		return "<value>void</value>";
	}

}
