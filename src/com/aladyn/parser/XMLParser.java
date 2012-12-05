package com.aladyn.parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.aladyn.parser.exception.InterfaceNotFoundException;
import com.aladyn.server.InterfacePool;
import com.aladyn.server.ObjectBuilder;
import com.aladyn.server.exception.BuildObjectException;

/**
 * Classe qui parse le fichier xml et remplir l'objet de type request
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * @see Request
 */
public class XMLParser {
	Request request;

	public XMLParser(Request request) {
		this.request = request;
	}

	/**
	 * Parser un document DOM
	 * 
	 * @param doc document DOM
	 */
	public void parse(Document doc) {
		NodeList list = doc.getElementsByTagName("param");
		for (int i = 0; i < list.getLength(); i++) {

			Element firstElement = DOMProcess.getFirstElement(list.item(i));

			Map.Entry<Class<?>, Object> entry = treatValue(firstElement);
			if (entry != null) {
				request.addType(entry.getKey());
				request.addValue(entry.getValue());
			}
		}
	}

	/**
	 * parse un element array
	 * 
	 * @param element array
	 * @return Vector construit à partir de l'element parsé
	 * @see Vector
	 */
	public Vector<Object> treatArray(Element element) {
		Vector<Object> result = new Vector<Object>();
		Element firstElement = DOMProcess.getFirstElement(element);
		if (firstElement == null)
			return result;
		NodeList list = firstElement.getElementsByTagName("value");
		for (int i = 0; i < list.getLength(); i++) {
			result.add(treatValue((Element) list.item(i)).getValue());
		}
		return result;
	}

	/**
	 * parse un element struct
	 * 
	 * @param element struct
	 * @return Hashtable construite à partir de l'element parsé
	 * @see Hashtable
	 */
	public Hashtable<String, Object> treatStruct(Element element) {
		Hashtable<String, Object> table = new Hashtable<String, Object>();
		NodeList list = element.getElementsByTagName("member");
		Element firstElement, secondElement;
		for (int i = 0; i < list.getLength(); i++) {
			firstElement = DOMProcess.getFirstElement((Element) list.item(i));
			secondElement = DOMProcess.getSecondElement((Element) list.item(i));
			table.put(firstElement.getTextContent(), treatValue(secondElement)
					.getValue());
		}
		return table;
	}

	/**
	 * parse un element de type value
	 * 
	 * @param element value
	 * @return Map.Entry construite à partir de l'element parsé (contient le
	 *         type et la valeur de l'element)
	 * @see Map.Entry
	 */
	public Map.Entry<Class<?>, Object> treatValue(Element element) {
		Element firstElement = DOMProcess.getFirstElement(element);
		
		if (firstElement == null) return null; // text
		
		String firstElementName = firstElement.getNodeName();
		
		if (firstElementName.equals("object"))
			return treatObject(firstElement);
		if (firstElementName.equals("int"))
			return new MyEntry<Class<?>, Object>(int.class,
					DOMProcess.getInt(firstElement));
		if (firstElementName.equals("i4"))
			return new MyEntry<Class<?>, Object>(int.class,
					DOMProcess.getInt(firstElement));
		if (firstElementName.equals("boolean"))
			return new MyEntry<Class<?>, Object>(boolean.class,
					DOMProcess.getBool(firstElement));
		if (firstElementName.equals("double"))
			return new MyEntry<Class<?>, Object>(double.class,
					DOMProcess.getDouble(firstElement));
		if (firstElementName.equals("string"))
			return new MyEntry<Class<?>, Object>(String.class,
					firstElement.getTextContent());
		if (firstElementName.equals("dateTime.iso8601"))
			return new MyEntry<Class<?>, Object>(Date.class,
					DOMProcess.getDate(firstElement));
	    if (firstElementName.equals("base64"))
			return new MyEntry<Class<?>, Object>(byte[].class,
					DOMProcess.getBase64(firstElement));
		if (firstElementName.equals("array"))
			return new MyEntry<Class<?>, Object>(Vector.class,
					treatArray(firstElement));
		if (firstElementName.equals("struct"))
			return new MyEntry<Class<?>, Object>(Hashtable.class,
					treatStruct(firstElement));

		return null;

	}

	/**
	 * parse un element objet
	 * 
	 * @param element objet
	 * @return Map.Entry construite à partir de l'element objet (contient le
	 *         type et la valeur de l'objet)
	 * @see Map.Entry
	 */
	public Map.Entry<Class<?>, Object> treatObject(Element element) {
		Class<?> inter = null;
		ArrayList<Class<?>> objectFields = new ArrayList<Class<?>>();
		String oid = element.getAttribute("oid");
		String oidParts[] = oid.split("/");
		String oidNumber = oidParts[oidParts.length - 1];
		String nameClass = "ReceivedObject" + oidNumber.toString();
		ObjectBuilder builder = new ObjectBuilder(nameClass);
		ObjectBuilder builderBackup = new ObjectBuilder(nameClass + "Prim");
		try {
			builder.addField("public String oid = \"" + oid + "\";");
			Element fieldElement, methodElement;
			fieldElement = DOMProcess.getFirstElement(element);
			String fieldName, srcField;
			NodeList listField = fieldElement.getElementsByTagName("field");
			for (int i = 0; i < listField.getLength(); i++) {
				fieldName = ((Element) listField.item(i)).getAttribute("name");
				Map.Entry<Class<?>, Object> entry = treatValue(DOMProcess
						.getFirstElement((Element) listField.item(i)));
				if (entry.getKey() == String.class) {
					srcField = "public " + entry.getKey().getName() + " "
							+ fieldName + " " + "= \"" + entry.getValue()
							+ "\";";
					builder.addField(srcField);
					builderBackup.addField(srcField);
				} else if (entry.getKey().isInterface()) {
					srcField = "public " + entry.getKey().getName() + " "
							+ fieldName + " " + "=  new "
							+ entry.getValue().getClass().getSimpleName()
							+ "();";
					builder.addField(srcField);
					builderBackup.addField(srcField);
					objectFields.add(entry.getKey());
				} else {
					srcField = "public " + entry.getKey().getName() + " "
							+ fieldName + " " + "= " + entry.getValue() + ";";
					builder.addField(srcField);
					builderBackup.addField(srcField);
				}
			}
			methodElement = DOMProcess.getSecondElement(element);
			NodeList listMethod = methodElement.getElementsByTagName("method");
			String srcMethod;
			for (int i = 0; i < listMethod.getLength(); i++) {
				srcMethod = ((Element) listMethod.item(i)).getTextContent();
				for (Class<?> objectField : objectFields) {
					srcMethod = srcMethod.replace(
							" " + objectField.getSimpleName(), " "
									+ objectField.getName());
				}
				builder.addMethod(srcMethod);
				builderBackup.addMethod(srcMethod);
			}
			Class<?> myClass = builderBackup.getMyClass();
			inter = (InterfacePool.getInstance()).fetchInterface(myClass); // get the right interface
			System.out.println(inter.getName() + "------------");
			builder.addInterface(inter);
		} catch (BuildObjectException e) {
			e.printStackTrace();
		} catch (InterfaceNotFoundException e) {
			e.printStackTrace();
		}
		try {
			return new MyEntry<Class<?>, Object>(inter, 
					builder.getMyClass().newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
					e.printStackTrace();
		} catch (BuildObjectException e) {
			e.printStackTrace();
		}
		return null;
	}
}
