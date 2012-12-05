package com.aladyn.server;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Registre liant chaque classe à un numéro port
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class XMLRMIRegistry {
	
	private HashMap<String, Integer> listObjetsPorts = new HashMap<String, Integer>();
	
	private int registryPort;

	
	/**
	 * Constructeur associant pour chaque objet partagé un numéro de port
	 * 
	 * @param port
	 * 		le port du registre
	 * @param classList
	 * 		la liste des classes qui serons explosées par le registre
	 */
	public XMLRMIRegistry(int port, ArrayList<Class<?>> classList) {
		this.registryPort = port;
		for (Class<?> clazz : classList) {
			listObjetsPorts.put(clazz.getSimpleName(), ++port);
		}
	}
	
	/**
	 * Retourne le port associé au nom d'un objet donné
	 * 
	 * @param className
	 * @return
	 * 		le numéro du port ou null si le nom 
	 * 		de l'objet n'est pas dans le registre
	 */
	public int getPort(String className) {
		Integer port = listObjetsPorts.get(className);
		return (port == null) ? 0 : port.intValue();
	}
	
	/**
	 * Retourne le numéro de port du registre
	 * @return
	 */
	public int getRegistryPort() {
		return registryPort;
	}
	
	/**
	 * Retourne le nombre de ports associés au objets partagés
	 * @return
	 */
	public int getNbPorts() {
		return listObjetsPorts.size();
	}
	
	@Override
	public String toString() {
		return listObjetsPorts.values().toString() + "\n" 
				+ listObjetsPorts.keySet().toString();
	}

}
