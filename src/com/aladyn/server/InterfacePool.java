package com.aladyn.server;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import com.aladyn.ProtoResponseFault;
import com.aladyn.parser.exception.InterfaceNotFoundException;
import com.aladyn.server.exception.PackageClassLoaderException;

/**
 * Gére la gestion des interfaces partagées côté serveur 
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class InterfacePool {

	private static ArrayList<Class<?>> interfacePool=new ArrayList<Class<?>>();;
	private static String packageName = null;
	
	// instance 
	private static InterfacePool instance = null;

	// private constructor
	private InterfacePool() {
	}

	// singleton pattern
	public static  InterfacePool getInstance() {
		if (instance == null) instance = new InterfacePool();
		return instance;
	}
	
	/**
	 * Specifie le package où se trouve les interfaces partagées côté serveur
	 * 
	 * @param packageName package des interfaces partagées
	 */
	public void setInterfacePoolPackage(String packageName) {
		InterfacePool.packageName = packageName;
		try {
			this.interfacePool = PackageClassLoader.loadClasses(packageName);
		} catch (PackageClassLoaderException e) {
			InterfacePool.packageName=null;
		}
	}
	
	/**
	 * Récupère du pool l'interface implémenté par l'objet en paramêtre
	 * 
	 * @param obj l'objet pour le quel on cherche une inteface
	 * @return l'interface implémenté par obj 
	 * @throws InterfaceNotFoundException interface non trouvée
	 */
	public Class<?> fetchInterface(Class<?> obj) throws InterfaceNotFoundException {
		if(InterfacePool.packageName == null) 
			throw new InterfaceNotFoundException(ProtoResponseFault.SERVER_EXCEPTION, 
				ProtoResponseFault.PACKAGE_DONT_EXISTS);
		for (int i = 0; i < interfacePool.size(); i++) {
			if (I_Equals(interfacePool.get(i), obj))
				return interfacePool.get(i);
		}
		throw new InterfaceNotFoundException(ProtoResponseFault.SERVER_EXCEPTION, 
				ProtoResponseFault.XML_ENCODAGE + obj.getName());
	}

	/**
	 * Verifie l'égalité en deux interfaces
	 * 
	 * @param inter1 inteface 1
	 * @param inter2 inteface 2
	 * @return boolean
	 */
	private boolean I_Equals(Class<?> inter1, Class<?> inter2) {
		Method[] methods1 = inter1.getDeclaredMethods();
		Method method2;
		try {
			for (int i = 0; i < methods1.length; i++) {
				method2 = inter2.getDeclaredMethod(methods1[i].getName(),
						methods1[i].getParameterTypes());
				if (!M_Equals(method2, methods1[i]))
					return false;
			}
		} catch (SecurityException e) {
			return false;
		} catch (NoSuchMethodException e) {
			return false;
		}
		return true;
	}

	/**
	 * Verifie l'égalité entre deux methodes sans prendre 
	 * en compte le modifier Abstact
	 * 
	 * @param m1 methode 1
	 * @param m2 methode 2
	 * @return boolean
	 */
	private boolean M_Equals(Method m1, Method m2) {
		int modifierM1 = m1.getModifiers(), modifierM2 = m2.getModifiers();
		if (Modifier.isAbstract(modifierM1))
			modifierM1 -= 1024;
		if (Modifier.isAbstract(modifierM2))
			modifierM2 -= 1024;
		if (modifierM1 != modifierM2)
			return false;
		if (m1.getReturnType() != m2.getReturnType())
			return false;
		if (!m1.getName().equals(m2.getName()))
			return false;
		if (m1.getParameterTypes().length != m2.getParameterTypes().length)
			return false;
		Class<?>[] paramM1 = m1.getParameterTypes();
		Class<?>[] paramM2 = m1.getParameterTypes();
		for (int j = 0; j < paramM1.length - 1; j++) {
			if (paramM1[j] != paramM2[j])
				return false;
		}
		return true;
	}

}
