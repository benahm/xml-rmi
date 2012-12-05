package com.aladyn.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * DOMProcess regroupe des methodes static pour la manipulation du DOM
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * @see org.w3c.dom
 */
public class DOMProcess {
	/**
	 * getBase64 récupère un tableau de byte à partir d'un element de type
	 * base64
	 * 
	 * @param element
	 *            base64
	 * @return tableau de byte
	 */
	public static byte[] getBase64(Element element) {
		String content = element.getTextContent();
		byte[] bytes = new byte[8];
		char[] chars = content.toCharArray();
		for (int i = 0; i < 8; i++) {
			bytes[i] = (byte) chars[i];
		}
		return bytes;
	}

	/**
	 * getDate récupére un objet Date à partir d'un element de type datetime
	 * 
	 * @param element
	 *            datetime
	 * @return Objet Date
	 */
	public static Date getDate(Element element) {
		String dateString = element.getTextContent();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss");
		Date date = null;
		try {
			date = f.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * getBool récupère un booleen à partir d'un element de type boolean
	 * 
	 * @param element
	 *            boolean
	 * @return boolean
	 */
	public static boolean getBool(Element element) {
		String content = element.getTextContent();
		if (content.equals("0"))
			return false;
		else
			return true;
	}

	/**
	 * getBase64 récupère un tableau de byte à partir d'un element de type
	 * base64
	 * 
	 * @param element
	 *            base64
	 * @return tableau de byte
	 */
	public static double getDouble(Element element) {
		return Double.parseDouble(element.getTextContent());
	}

	/**
	 * getInt récupère un int à partir d'un element de type int
	 * 
	 * @param element
	 *            int
	 * @return int
	 */
	public static int getInt(Element element) {
		return Integer.parseInt(element.getTextContent());
	}

	/**
	 * return le firstElement si il existe sinon null
	 * 
	 * @param parent
	 * @return
	 */
	public static Element getFirstElement(Node parent) {
		NodeList nl = parent.getChildNodes();
		for (int i = 0; i < nl.getLength(); ++i) {
			if (nl.item(i).getNodeType() == Node.ELEMENT_NODE)
				return (Element) nl.item(i);
		}
		return null;
	}

	/**
	 * return le secondElement si il existe sinon null
	 * 
	 * @param parent
	 * @return
	 */
	public static Element getSecondElement(Node parent) {
		NodeList nl = parent.getChildNodes();
		int nb = 0;
		for (int i = 0; i < nl.getLength(); ++i) {
			if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
				if (nb == 1)
					return (Element) nl.item(i);
				nb++;
			}
		}
		return null;
	}
}
