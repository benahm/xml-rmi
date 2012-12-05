package com.aladyn.client.objects;

import com.aladyn.XMLRMIField;
import com.aladyn.shared.interfaces.Inter;
import com.aladyn.shared.interfaces.InterObjInObj;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class ObjInObj implements InterObjInObj {
	@XMLRMIField(serializationName = "obj", serializationType = "com.aladyn.shared.interfaces.Inter")
	public Inter obj=new Obj();
	@Override
	public String getName() {
		return "Obj" ;
	}

	@Override
	public Inter getObj() {
		return obj;
	}

}
