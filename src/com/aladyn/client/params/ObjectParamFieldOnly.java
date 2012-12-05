package com.aladyn.client.params;

import java.lang.reflect.Field;

import com.aladyn.ProtoResponseFault;
import com.aladyn.client.exception.NullObjectFieldException;
import com.aladyn.client.exception.ToXMLException;

/**
 * Génére une representation en XML de 
 * l'objet en param (seulement pour les fields)
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * 
 * @see ObjectParam
 */
public class ObjectParamFieldOnly implements IParam {
	
	Object object;

	public ObjectParamFieldOnly(Object object) {
		this.object = object;
	}

	@Override
	public String getType() {
		return "object";
	}

	/**
	 * Conversion vers du XML en incluant seulement les fields
	 * @throws ToXMLException 
	 * 
	 * @throws NullObjectFieldException
	 */
	@Override
	public String toXML() throws ToXMLException {
		StringBuffer xml = new StringBuffer();
		try {
			IParam field;
			if (object == null) {
				throw new NullObjectFieldException(ProtoResponseFault.CLIENT_EXCEPTION,
						ProtoResponseFault.NULL_OBJECT_FIELD_ERROR);
			}
			Class<? extends Object> class1 = object.getClass();
			xml.append("<value> <object oid=\"" + class1.getDeclaredField("oid").get(object) + "\">");
			Field fields[] = class1.getDeclaredFields();
			xml.append("<fields>");
			for (int i = 1; i < fields.length; i++) {
				xml.append("<field name=\"" + fields[i].getName() + "\">");
				field = FieldFactory.wrapParam(fields[i].get(object));
				xml.append(field.toXML());
				xml.append("</field>");
			}
			xml.append("</fields>");
			xml.append("<methods></methods>");
			xml.append("</object> </value>");
		} catch (NullObjectFieldException e) {
			throw new ToXMLException(
					ProtoResponseFault.CLIENT_EXCEPTION,
					ProtoResponseFault.TYPE_OBJECT_ERROR);
		} catch (IllegalArgumentException e) {
			throw new ToXMLException(
					ProtoResponseFault.CLIENT_EXCEPTION,
					ProtoResponseFault.TYPE_OBJECT_ERROR);
		} catch (IllegalAccessException e) {
			throw new ToXMLException(
					ProtoResponseFault.CLIENT_EXCEPTION,
					ProtoResponseFault.TYPE_OBJECT_ERROR);
		} catch (NoSuchFieldException e) {
			throw new ToXMLException(
					ProtoResponseFault.CLIENT_EXCEPTION,
					ProtoResponseFault.TYPE_OBJECT_ERROR);
		} catch (SecurityException e) {
			throw new ToXMLException(
					ProtoResponseFault.CLIENT_EXCEPTION,
					ProtoResponseFault.TYPE_OBJECT_ERROR);
		}
		return xml.toString();
	}

}
