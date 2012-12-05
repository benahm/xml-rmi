package com.aladyn.client.params;

import java.sql.Date;
import java.util.Hashtable;
import java.util.Vector;

import com.aladyn.ProtoResponseFault;
import com.aladyn.client.params.exception.FieldFactoryException;

/**
 * FieldFactory Class factory qui génère des valeurs encapsulées
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * 
 * @see IParam
 */
public class FieldFactory {
	
	/**
	 * CreateField return l'objet IParam qui encapsule le champs
	 * 
	 * @param o 
	 * 		objet
	 * @param field 
	 * 		champs
	 * @return le champs encapsuler dans la bonne sous classe de IParam
	 * @throws FieldFactoryException 
	 */
	public static IParam createField(String typeName,Object fieldValue,Class<?> type) 
			throws FieldFactoryException {
		
		if (getTypeClass(typeName) == int.class)
			return new IntParam(fieldValue);
		else if (getTypeClass(typeName) == boolean.class)
			return new BoolParam(fieldValue);
		else if (getTypeClass(typeName) == double.class)
			return new DoubleParam(fieldValue);
		else if (getTypeClass(typeName) == boolean.class)
			return new BoolParam(fieldValue);
		else if (getTypeClass(typeName) == String.class)
			return new StringParam(fieldValue);
		else if (getTypeClass(typeName) == Date.class)
			return new DateTimeParam(fieldValue);
		else if (getTypeClass(typeName) == byte[].class)
			return new Base64Param(fieldValue);
		else if (getTypeClass(typeName) == Vector.class)
			return new ArrayParam(fieldValue);
		else if (getTypeClass(typeName) == Hashtable.class)
			return new StructParam(fieldValue);
		
		return new ObjectParam(fieldValue, type);
	}

	/**
	 * wrapValue elle retourne la valeur encapsulé de o
	 * 
	 * @param o
	 * 		objet a encapsulé
	 * @return objet encapsulé
	 */
	public static IParam wrapValue(Object o) {
		Class<? extends Object> type = o.getClass();
		if (type == Integer.class)
			return new IntParam(o);
		else if (type == Boolean.class)
			return new BoolParam(o);
		else if (type == Double.class)
			return new DoubleParam(o);
		else if (type == Date.class)
			return new DateTimeParam(o);
		else if (type == String.class)
			return new StringParam(o);
		else if (type == Byte[].class)
			return new Base64Param(o);
		return null;
	}

	/**
	 * wrapParam elle retroune un l'objet encapsulé dans le param qui convient
	 * 
	 * @param o
	 * 		l'objet
	 * @return l'encaplustion
	 */
	public static IParam wrapParam(Object o) {
		Class<? extends Object> type = o.getClass();
		if (type == Hashtable.class)
			return new StructParam(o);
		else if (type == Vector.class)
			return new ArrayParam(o);
		else if (type == VoidParam.class)
			return new VoidParam();
		else if (type == Integer.class)
			return new IntParam(o);
		else if (type == Boolean.class)
			return new BoolParam(o);
		else if (type == Double.class)
			return new DoubleParam(o);
		else if (type == Date.class)
			return new DateTimeParam(o);
		else if (type == String.class)
			return new StringParam(o);
		else if (type == Byte[].class)
			return new Base64Param(o);
		return new ObjectParamFieldOnly(o);

	}

	/**
	 * getTypeClass return le type point class selon la chaine de caractères
	 * passé en paramétre
	 * 
	 * @param typeName
	 *            chaine de caratères
	 * @return le point class
	 * @throws FieldFactoryException 
	 */
	public static final Class<?> getTypeClass(String typeName) 
			throws FieldFactoryException {
		
		if (typeName.equals("byte"))
			return byte.class;
		if (typeName.equals("short"))
			return short.class;
		if (typeName.equals("int"))
			return int.class;
		if (typeName.equals("long"))
			return long.class;
		if (typeName.equals("char"))
			return char.class;
		if (typeName.equals("float"))
			return float.class;
		if (typeName.equals("double"))
			return double.class;
		if (typeName.equals("boolean"))
			return boolean.class;
		if (typeName.equals("void"))
			return void.class;
		try {
			System.out.println(typeName);
			return Class.forName(typeName);
		} catch (ClassNotFoundException e) {
			throw new FieldFactoryException(ProtoResponseFault.CLIENT_EXCEPTION,
					ProtoResponseFault.TYPE_DONT_EXISTS, e);
		}
	}

}
