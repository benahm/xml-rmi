package com.aladyn.client.objects;

import com.aladyn.XMLRMIField;
import com.aladyn.shared.interfaces.Inter;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class Obj implements Inter{
	@XMLRMIField(serializationName = "x", serializationType = "int")
	public int x=0;
	@Override
	public double getX() {
		return 10.0;
	}

	@Override
	public double getY() {
		return 10.0;
	}



}
