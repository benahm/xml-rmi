package com.aladyn.server.objects;

import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import com.aladyn.shared.interfaces.Inter;
import com.aladyn.shared.interfaces.InterObjInObj;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class SuperCalculator {

	public int x = 1;
	public String y = "f(x)";
	public double pi(){
		return 3.14159;
	}
	public int makeSum(int a, int b) {
		return a + b;
	}

	public double  makeMult ( double a , double b) {
		return a * b;
	}

	public double makeSumObj(Inter obj) {
		return obj.getX() + obj.getY();
	}

	public double makeSumObjAndDouble(double a, Inter obj) {
		return a + obj.getX() + obj.getY();
	}

	public int makeSumTabInt(Vector<Object> tab) {
		int result = 0;
		for (int i = 0; i < tab.size(); i++) {
			result += (Integer)tab.elementAt(i);
		}
		return result;
	}

	public int makeSumStruct(Hashtable<String, Integer> hash) {
		return hash.get("a") + hash.get("b");
	}

	public double makeSumTabObj(Vector<Object> tab){
		return ((Inter) tab.elementAt(0)).getX()+((Inter)tab.elementAt(1)).getX();
	}
	
	public double makeSumStructObj(Hashtable<String, Inter> struct){
		return struct.get("a").getX()+struct.get("b").getX();
	}
	
	public double makeSumObjInObj(InterObjInObj objInObj){
		return objInObj.getObj().getX()+objInObj.getObj().getY();
	}
	
	public Date displayDate(Date date) {
		System.out.println(date.toString());
		return date;
	}

	public String displayString(String text) {
		System.out.println(text);
		return text;
	}

	public boolean xor(boolean bool1, boolean bool2) {
		return ((bool1 || bool2) && !(bool1 && bool2));
	}

	public byte[] displayBase64(byte[] base64) {
		System.out.println(base64);
		return base64;
	}
	
	public void print() {
		System.out.println("je suis un super calculateur !");
	}
	
	
}
