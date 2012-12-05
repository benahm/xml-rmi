package com.aladyn.client.params;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.aladyn.ProtoResponseFault;
import com.aladyn.XMLRMIField;
import com.aladyn.XMLRMISerializable;
import com.aladyn.client.IdTable;
import com.aladyn.client.exception.FetchSourceCodeException;
import com.aladyn.client.exception.NullObjectFieldException;
import com.aladyn.client.exception.ToXMLException;
import com.aladyn.client.exception.UpdateException;
import com.aladyn.client.params.exception.FieldFactoryException;
import com.aladyn.parser.DOMProcess;
import com.aladyn.parser.XMLParser;

/**
 * ObjectParam Class qui encapsule l'objet à envoyer
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * 
 */
public class ObjectParam implements IParam, XMLRMISerializable {

	private Object object;
	private Class<?> inter;

	public ObjectParam(Object object, Class<?> inter) {
		this.object = object;
		this.inter = inter;
	}

	@Override
	public String getType() {
		return "object";
	}

	/**
	 * Conversion vers du XML
	 * 
	 * @throws ToXMLException 
	 * @throws NullObjectFieldException
	 */
	@Override
	public String toXML() throws ToXMLException {
		FetchSourceCode src = new FetchSourceCode(object.getClass());
		StringBuffer xml = new StringBuffer();
		try {
			IParam field = null;
			if (object == null) {
				throw new NullObjectFieldException(ProtoResponseFault.CLIENT_EXCEPTION, 
						ProtoResponseFault.NULL_OBJECT_FIELD_ERROR);
			}
			xml.append("<value> <object oid=\"" + IdTable.put(this) + "\"  type=\""+inter.getSimpleName()+"\">");
			Field fields[] = object.getClass().getDeclaredFields();
			xml.append("<fields>");
			String name,typeName;
			XMLRMIField annotation;
			for (int i = 0; i < fields.length; i++) {
				annotation = fields[i].getAnnotation(XMLRMIField.class);
				if (annotation == null)
					continue;
				name = annotation.serializationName();

				xml.append("<field name=\"" + name + "\">");
					System.out.println(FieldFactory.getTypeClass(annotation.serializationType()));
				typeName=annotation.serializationType();
					try {
						field = FieldFactory.createField(typeName, fields[i].get(object),fields[i].getType());
					} catch (IllegalArgumentException e) {
						throw new ToXMLException(
								ProtoResponseFault.CLIENT_EXCEPTION,
								ProtoResponseFault.TYPE_OBJECT_ERROR);
					} catch (IllegalAccessException e) {
						throw new ToXMLException(
								ProtoResponseFault.CLIENT_EXCEPTION,
								ProtoResponseFault.TYPE_OBJECT_ERROR);
					} catch (FieldFactoryException e) {
						throw new ToXMLException(
								ProtoResponseFault.CLIENT_EXCEPTION,
								ProtoResponseFault.TYPE_OBJECT_ERROR);
					}
				
				xml.append(field.toXML());
				xml.append("</field>");
			}
			xml.append("</fields>");
			xml.append("<methods>");

			Method[] methods = inter.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				xml.append("<method language=\"java\">");
				// recupération du code source
				try {
					xml.append(src.getSourceMethod(methods[i]));
				} catch (FetchSourceCodeException e) {
					e.printStackTrace();
				}
				xml.append("</method>");
			}
			xml.append("</methods>");
			xml.append("</object> </value>");
		} catch (NullObjectFieldException e) {
			throw new ToXMLException(
					ProtoResponseFault.CLIENT_EXCEPTION,
					ProtoResponseFault.NULL_OBJECT_FIELD_ERROR);
		} catch (FieldFactoryException e) {
			throw new ToXMLException(
					ProtoResponseFault.CLIENT_EXCEPTION,
					e.getMessage());
		}
		return xml.toString();
	}

	/**
	 * Mise à jour depuis XML
	 * 
	 * @throws UpdateException 
	 */
	@Override
	public void updateFromXML(Element xml) throws UpdateException {
		XMLParser parser=new XMLParser(null);
		NodeList listFields = xml.getElementsByTagName("field");
		Element e = null;
		Class<? extends Object> class1 = object.getClass();
		Field[] objectFields=class1.getFields();
		XMLRMIField annot;
		int j=0;
		for (int i = 0; i < listFields.getLength(); i++) {
			e = (Element) listFields.item(i);
			Object value=parser.treatValue(DOMProcess
					.getFirstElement(e)).getValue();
			if(value.getClass()==Object.class){
				e = DOMProcess.getFirstElement(listFields.item(i));
				e = DOMProcess.getFirstElement(e);
				ObjectParam param = (ObjectParam) IdTable.get(e.getAttribute("oid"));
				if (param == null)
					throw new UpdateException(ProtoResponseFault.CLIENT_EXCEPTION,
							ProtoResponseFault.OBJECT_NOT_FOUND);
				param.updateFromXML(e); // recursion 
			}
			System.out.println(value.getClass()+"+++");
			while(objectFields[j].getAnnotation(XMLRMIField.class)==null && j<objectFields.length)
				j++;//recherche des fields avec annotation
			if(j==objectFields.length) break;
			annot=objectFields[j].getAnnotation(XMLRMIField.class);
			System.out.println(annot.serializationName()+objectFields[i].getName()+j);
			if(annot != null)
				try {
					objectFields[i].set(object, value);
				} catch (IllegalArgumentException e1) {
					throw new UpdateException(ProtoResponseFault.CLIENT_EXCEPTION,
							ProtoResponseFault.OBJECT_UPDATE_ERROR, e1);
				} catch (IllegalAccessException e1) {
					throw new UpdateException(ProtoResponseFault.CLIENT_EXCEPTION,
							ProtoResponseFault.OBJECT_UPDATE_ERROR, e1);
				}
			
			j++;
		}
		System.out.println("ObjectParam.updateFromXML()");
	}

}
