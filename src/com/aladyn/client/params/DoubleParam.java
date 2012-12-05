package com.aladyn.client.params;

/**
 * DoubleParam Class qui encapsule un double
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * 
 */
public class DoubleParam implements IParam {
	
	double doubleval;

	public DoubleParam(Object doubleval) {
		if (doubleval instanceof Integer)
			this.doubleval = (Integer) doubleval;
		else
			this.doubleval = (Double) doubleval;
	}

	@Override
	public String getType() {
		return "double";
	}

	@Override
	public String toXML() {
		return "<value><double>" + doubleval + "</double></value>";
	}

}
