package com.aladyn.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.aladyn.ProtoResponseFault;
import com.aladyn.client.exception.ClientSideException;
import com.aladyn.client.exception.NotConformNameMethodException;
import com.aladyn.client.exception.ToXMLException;
import com.aladyn.client.params.IParam;


/**
 * ClientSide : client qui s'occupe de l'envoi du XML par socket
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class ClientSide {
	
	private static Socket socket = null;
	private static ObjectInputStream ois = null;
	private static ObjectOutputStream oos = null;

	
	/**
	 * send : envoie une chaine de caracteres au serveur
	 * 
	 * @param text : chaine a envoye
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static synchronized void send(String text, int port, String addr) 
			throws ClientSideException 
	{
			try	{
				socket = new Socket(addr, port);
				System.out.println("SOCKET = send " + socket);
				oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject(text);
			} catch (UnknownHostException e) {
				throw new ClientSideException (
						ProtoResponseFault.CLIENT_EXCEPTION, 
						ProtoResponseFault.CONNECTION_NOT_ESTABLISH);
			} catch (IOException e)	{
				e.printStackTrace();
			}
	}
	
	/**
	 * receive : reçoie le message du serveur
	 * 
	 * @return
	 * @throws ClientSideException 
	 */
	public static synchronized String receive() 
			throws ClientSideException 
	{
		String text = null;
		try {
			System.out.println("SOCKET = receive " + socket);
			ois = new ObjectInputStream(socket.getInputStream());
			text = (String) ois.readObject();
			System.out.println("Reception \n" + text);
			ois.close();
			socket.close(); // fermeture de la socket
		} catch (UnknownHostException e) {
			throw new ClientSideException(
					ProtoResponseFault.CLIENT_EXCEPTION, 
					ProtoResponseFault.CONNECTION_NOT_ESTABLISH, e);
		} catch (IOException e) {
			throw new ClientSideException(
					ProtoResponseFault.CLIENT_EXCEPTION, 
					ProtoResponseFault.COMMUNICATION_WITH_SERVER_ERROR, e);
		} catch (ClassNotFoundException e) {
			throw new ClientSideException(
					ProtoResponseFault.CLIENT_EXCEPTION, 
					e.getMessage(), e);
		} return text;
	}

	/**
	 * buildRequestl : génère un document XML en fonction des objets passés en paramêtre
	 * 
	 * @param methodName
	 *      Nom de la methode
	 * @param args
	 *      Parametres de la methode distante
	 * @throws NotConformNameMethodException 
	 */
	public static synchronized String buildRequestl(String methodName, IParam args[]) throws NotConformNameMethodException {
		
		String xml = "";
		String pattern = "[a-zA-Z0-9_\\.,/]+";
		
		if (! methodName.matches(pattern)) 
			throw new NotConformNameMethodException(
					ProtoResponseFault.CLIENT_EXCEPTION, 
					ProtoResponseFault.METHOD_NAME_NOT_CONFORME);
		
		for (int i = 0; i < args.length; i++) {
			IParam param = args[i];
			try {
				xml += "<param>  " + param.toXML() + "  </param>";
			} catch (ToXMLException e) {
				e.printStackTrace();
			}
		}
		
		if(xml=="") xml="<param>  " + "<value>"+"</value>"+ "  </param>";
		
		xml = "<methodCall>" + "<methodName>" + methodName + "</methodName>"
				+ "<params>" + xml + "</params> " + " </methodCall>";
		
		System.out.println("le xml généré : \n" + xml);
		
		return xml;
	}

}
