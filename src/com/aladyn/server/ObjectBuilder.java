package com.aladyn.server;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.NotFoundException;

import com.aladyn.ProtoResponseFault;
import com.aladyn.server.exception.BuildObjectException;

/**
 * ObjectBuilder contient tout les methodes permetant de construire un objet
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * 
 */
public class ObjectBuilder {

	private CtClass buildClass;
	private Class<?> myClass = null;
	private boolean isCreated = false;
	private ClassPool pool=ClassPool.getDefault();;

	/**
	 * Constructeur
	 * 
	 * @param nameClass
	 * 		le nom de la class a cree
	 */
	public ObjectBuilder(String nameClass) {
		try {
			buildClass = pool.get(nameClass);
		} catch (NotFoundException e) {
			buildClass = pool.makeClass(nameClass);
		}

	}

	/**
	 * addField : ajoute un champs à la class en construction
	 * 
	 * @param srcField
	 * 		le nom du champs
	 * @throws BuildObjectException
	 */
	public void addField(String srcField) throws BuildObjectException {
		checkCreated();
		CtField field;
		try {
			field = CtField.make(srcField, buildClass);
			buildClass.addField(field);
		} catch (CannotCompileException e) {
			e.printStackTrace();
			throw new BuildObjectException(ProtoResponseFault.METHOD_CALL_FALL,
					"Erreur compilation fields", e);
		}
	}

	/**
	 * addMethod : ajoute une methode à la classe en construction
	 * 
	 * @param srcMethod
	 * 		le code source de la methode à ajoute
	 * @throws BuildObjectException
	 */
	public void addMethod(String srcMethod) throws BuildObjectException {
		checkCreated();
		CtMethod method;
		try {
			System.out.println(srcMethod);
			method = CtNewMethod.make(srcMethod, buildClass);
			buildClass.addMethod(method);
		} catch (CannotCompileException e) {
			throw new BuildObjectException(ProtoResponseFault.METHOD_CALL_FALL,
					"Erreur compilation methods", e);
		}

	}

	/**
	 * addConstructor : ajoute un constructeur à la classe
	 * 
	 * @param parameters
	 * 		les paramettres du constructeurs
	 * @param srcConstructor
	 * 		le corps (code source) du constructeur
	 * @throws BuildObjectException
	 */
	public void addConstructor(String srcConstructor)
			throws BuildObjectException {
		checkCreated();
		CtConstructor constructor;
		try {
			constructor = CtNewConstructor.make(srcConstructor, buildClass);
			buildClass.addConstructor(constructor);
		} catch (CannotCompileException e) {
			throw new BuildObjectException(ProtoResponseFault.METHOD_CALL_FALL,
					"Erreur compilation construteur", e);
		}
	}

	/**
	 * addInterface : ajoute une interface a la classe en contruction
	 * 
	 * @param inter
	 * 		l'interface à ajoute
	 * @throws BuildObjectException
	 */
	public void addInterface(Class<?> inter) throws BuildObjectException {
		checkCreated();
		ClassPool pool = ClassPool.getDefault();
		CtClass ctInter = null;
		try {
			ctInter = pool.get(inter.getName());
			buildClass.addInterface(ctInter);
		} catch (NotFoundException e) {
			throw new BuildObjectException(ProtoResponseFault.METHOD_CALL_FALL,
					"Interface no trouvée", e);
		}

	}

	public void addImport(String pack) throws BuildObjectException{
		try {
			checkCreated();
			pool.insertClassPath(pack);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retourne la classe générée grâce à ObjectBuilder 
	 * 
	 * @return
	 * @throws BuildObjectException
	 */
	public Class<?> getMyClass() throws BuildObjectException {
		try {
			checkCreated();
			if (isCreated) {
				return myClass;
			}
			isCreated = true;
			myClass = buildClass.toClass();
			System.out.println("*le contenu de la classe généré");
			System.out.println(myClass);
			for (int i = 0; i < myClass.getDeclaredFields().length; i++) {
				System.out.println(myClass.getDeclaredFields()[i]);
			}
			for (int i = 0; i < myClass.getDeclaredMethods().length; i++) {
				System.out.println(myClass.getDeclaredMethods()[i]);
			}
			for (int i = 0; i < myClass.getInterfaces().length; i++) {
				System.out.println(myClass.getInterfaces()[i]);
			}
			return myClass;
		} catch (CannotCompileException e) {
			throw new BuildObjectException(ProtoResponseFault.METHOD_CALL_FALL,
					"Erreur compilation class", e);
		}
	}

	
	private void checkCreated() throws BuildObjectException {
		if(isCreated) 
			throw new BuildObjectException(0,ProtoResponseFault.ERROR_OBJECT_BUILDER);
	}

}
