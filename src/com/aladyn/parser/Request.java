package com.aladyn.parser;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Request Classe pour stocker les parametres de la requete et les types associés
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class Request {

	private String methodName = "";
	
	/**
	 * valeur des attributs
	 */
	private ArrayList<Object> attributsValue = new ArrayList<Object>();
	
	/**
	 * type des attributs
	 */
	private ArrayList<Class<?>> attributsType = new ArrayList<Class<?>>();

	
	
	public String getMethodName() {
		return methodName;
	}

	public ArrayList<Object> getValues() {
		return attributsValue;
	}

	public Object[] getValuesArray() {
		return attributsValue.toArray();
	}
	
	public ArrayList<Class<?>> getTypes() {
		return attributsType;
	}

	public Class<?>[] getTypeArray() {

		return attributsType.toArray(new Class<?>[attributsType.size()]);
	}
	

	public void setMethodName(String methodName) {
		this.methodName = methodName;
		System.out.println("setMethodName");
	}

	
	public void addValue(Object attributsValue) {
		this.attributsValue.add(attributsValue);
	}

	public void addtValues(ArrayList<Object> attributValue) {
		this.attributsValue.add(attributValue);
	}

	public void addType(Class<?> attributsType) {
		this.attributsType.add(attributsType);
	}

	public Object getValue(int i){
		return attributsValue.get(i);
	}
	
	public Class<?> getType(int i){
		return attributsType.get(i);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(methodName + "\n");
		for (int i = 0; i < attributsType.size(); i++) {

			if (getType(i) == Vector.class) {
				s.append("Array: ");
				Vector<Object> vector = (Vector<Object>) getValue(i);
				for (Object o : vector) {
					s.append(" (" + o + ") ");
				}
				s.append("\n");
			} else if (getType(i) == Object.class)
				s.append("Object: "+getValue(i));
			else if (getType(i) == byte[].class) {
				byte[] bytes = (byte[]) getValue(i);
				s.append("base64: ");
				for (int j = 0; j < bytes.length; j++) {
					s.append(bytes[i]);
				}
				s.append("\n");
			} else
				s.append(attributsType.get(i) + " : " + getValue(i)
						+ "\n");
		}

		return s.toString();
	}

}
