package com.aladyn.client.params;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aladyn.ProtoResponseFault;
import com.aladyn.client.exception.FetchSourceCodeException;

/**
 * FetchSrouceCode récupére le code source pour d'une methode donnée
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * 
 */
public class FetchSourceCode {

	private Class<?> myClass;

	
	public FetchSourceCode(Class<?> myClass) {
		this.myClass = myClass;
	}

	
	/**
	 * getSourceMethod retourne le code source de la methode passé en paramêtre,
	 * si le source de la classe n'a pas été trouvé, l'utilisateur est invité à
	 * écrire le code source de la methode dans un fichier srcMethod.java
	 * 
	 * @param method
	 * 		la methode pour la quelle en cherche à récupérer le code source
	 * @return le code source de la methode
	 * @throws FetchSourceCodeException
	 */
	public String getSourceMethod(Method method)
			throws FetchSourceCodeException {
		
		String filePath = "/" + (myClass.getCanonicalName()).replace('.', '/')
				+ ".java";
		File file = new File(".");
		File[] list = file.listFiles();
		String subFile = null;
		for (int i = 0; i < list.length; i++) {
			subFile = list[i].getAbsolutePath();
			if (new File(subFile + filePath).exists()) {
				filePath = subFile + filePath;
				break;
			}
		}
		if (new File(filePath).exists()) {
			return methodSourceCode(method, filePath);
		} else {
			File srcMethods = new File("srcMethod.java");
			System.out
					.println("La class "
							+ myClass.getName()
							+ " n'a pas été trouvé, veuillez copier les corps des methodes"
							+ "\ndans le fichier suivant :\n"
							+srcMethods.getAbsolutePath()+"\\"+"srcMethod.java");
			if (!srcMethods.exists()) {
				try {
					srcMethods.createNewFile();
				} catch (IOException e) {
					throw new FetchSourceCodeException(
							ProtoResponseFault.CLIENT_EXCEPTION,
							ProtoResponseFault.CAN_NOT_CREATE_METHOD_FILE_SOUCE, e);
				}
			} else
				return methodSourceCode(method, "srcMethod.java");
		}
		return subFile;
	}

	/**
	 * methodSourceCode retourne le code source de la method à partir du chemin
	 * de fichier en paramêtre
	 * 
	 * @param method
	 * 		la methode
	 * @param filePath
	 *      chemin du fichier ou se trouve le code source de la methode
	 * @return code source de la methode
	 * @throws FetchSourceCodeException
	 */
	private String methodSourceCode(Method method, String filePath)
			throws FetchSourceCodeException {
		String srcSourceFile = FileR.readFileAsString(filePath);
		StringBuffer pattern = new StringBuffer();
		int modifier = method.getModifiers();
		if (Modifier.isAbstract(modifier))
			modifier -= 1024;
		pattern.append(Modifier.toString(modifier));
		pattern.append(" +");
		pattern.append(method.getReturnType().getSimpleName());
		pattern.append(" +");
		pattern.append(method.getName());
		pattern.append(" *\\( *");
		Class<?>[] params = method.getParameterTypes();
		for (int i = 0; i < params.length - 1; i++) {
			pattern.append(params[i].getSimpleName() + " ?.+, *");
		}
		if (params.length != 0)
			pattern.append(params[params.length - 1].getSimpleName() + " ?.+");
		pattern.append("\\)");
		System.out.println(pattern + "");
		Pattern p = Pattern.compile(pattern.toString());
		Matcher m = p.matcher(srcSourceFile);
		String str = "";
		if (m.find()) {

			str = m.group();
		}
		if (str.equals(""))
			throw new FetchSourceCodeException(
					ProtoResponseFault.CLIENT_EXCEPTION, 
					ProtoResponseFault.METHOD_NOT_FOUND);
		System.out.println("le corps de la method =" + str
				+ srcSourceFile.charAt(m.end() - 1));
		int fromIndex = m.end(), indexClose, indexOpen;
		while (true) {
			indexOpen = srcSourceFile.indexOf("{", fromIndex);
			indexClose = srcSourceFile.indexOf("}", fromIndex);
			if (indexClose < indexOpen || indexOpen == -1)
				break;
			fromIndex = indexOpen + 1;
		}

		System.out.println(str
				+ srcSourceFile.substring(m.end(), indexClose + 1));

		return str + srcSourceFile.substring(m.end(), indexClose + 1);
	}

}
