package com.aladyn.client;

import java.util.HashMap;

/**
 * Gestion des oid des objets a envoyer
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 */
public class IdTable {

	static HashMap<String, Object> ids = new HashMap<String, Object>();
	static int id = 100;

	/**
	 * Reference un objet dans la table des oid
	 * 
	 * @param value l'objet à ajouter
	 * @return return l'id affecter à l'objet
	 */
	public static String put(Object value) {
		String idString = "aladyn.com/xml-rmi/" + (++id);
		ids.put(idString, value);
		return idString;
	}

	/**
	 * Return un objet déjà referencer dans la table des id
	 * @param id de l'objet
	 * @return l'objet 
	 */
	public static Object get(String id) {
		return ids.get(id);
	}
	
	/**
	 * Efface les objets stockés
	 */
	public static void clear() {
		ids.clear();
		id = 100;
	}
	
}
