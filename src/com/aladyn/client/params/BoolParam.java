package com.aladyn.client.params;

/**
 * BoolParam Class qui encapsule un boolean
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * 
 */
public class BoolParam implements IParam {
	
	private int bool;

	public BoolParam(Object bool) {
		if((Boolean)bool) this.bool=1;
		else this.bool=0;
	}

	@Override
	public String getType() {
		return "boolean";
	}

	@Override
	public String toXML() {
		return "<value> <boolean> "+bool+" </boolean> </value>";
	}

}
