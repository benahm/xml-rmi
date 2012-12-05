package com.aladyn.client.params.classesAndInterfaces;

import com.aladyn.XMLRMIField;


/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class ClassExample implements InterfaceExample{
	@XMLRMIField(serializationName = "x", serializationType = "int")
	public int i=111;
	@XMLRMIField(serializationName = "y", serializationType = "double")
	public double d=0.9999;
	public String text="chaine de caracteres";
	public void method(){}
	
}
