package com.aladyn.server;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import com.aladyn.ProtoResponseFault;
import com.aladyn.server.exception.PackageClassLoaderException;

/**
 * Récupère les classes contenues dans un package 
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class PackageClassLoader {

	/**
	 * Récupère les classes dans le package en paramêtre
	 * 
	 * @param packageName
	 * 		le nom du package
	 * @return ArrayList<Class<?>> 
	 * 		la liste des classes 
	 * @throws PackageClassLoaderException 
	 */
	public static ArrayList<Class<?>> loadClasses(String packageName) throws PackageClassLoaderException 
	{
		ArrayList<Class<?>> availableClasses = new ArrayList<Class<?>>();
		File directory = null;
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		String path = packageName.replace('.', '/');
		URL resource = cl.getResource(path);
		String[] files = null;
		int i = 0;
		try {
			directory = new File(resource.getFile());
			if (directory.exists())
			{
				files = directory.list();
				for (i = 0; i < files.length; i++)
				{
					if (files[i].endsWith(".class"))
					{
						Class<?> clazz = Class.forName(packageName
								+ '.'
								+ files[i].substring(0, files[i].length()
										- ".class".length()));
						availableClasses.add(clazz);
					}
				}
			}
		} catch(NullPointerException e) {
			throw new PackageClassLoaderException(
					ProtoResponseFault.SERVER_EXCEPTION,
					ProtoResponseFault.PACKAGE_DONT_EXISTS + packageName, e);
		} catch (ClassNotFoundException e) {
			throw new PackageClassLoaderException(
					ProtoResponseFault.SERVER_EXCEPTION, 
					ProtoResponseFault.CLASS_NOT_FOUND + files[i], e);
		}
		return availableClasses;
	}

}
