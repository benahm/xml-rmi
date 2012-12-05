package com.aladyn.client.params;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.aladyn.ProtoResponseFault;
import com.aladyn.client.params.exception.FileRException;

/**
 * Transforme un fichier en une chaine de caractères
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class FileR {
	
	/**
	 * Prend le chemain du fichier et le retourne sous 
	 * forme d'une chaine de caractères
	 * 
	 * @param filePath
	 * @return
	 */
	public  static String readFileAsString(String filePath) {
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			char[] buf = new char[1024];
			int numRead = 0;
			while ((numRead = reader.read(buf)) != -1) {
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
				buf = new char[1024];
			}
			reader.close();
		} catch (FileNotFoundException e) {
			new FileRException(ProtoResponseFault.CLIENT_EXCEPTION,
					ProtoResponseFault.FILE_NOT_FOUND, e);
		} catch (IOException e) {
			new FileRException(ProtoResponseFault.CLIENT_EXCEPTION,
					ProtoResponseFault.FILE_IO_ERROR, e);
		}

		return fileData.toString();
	}

}
