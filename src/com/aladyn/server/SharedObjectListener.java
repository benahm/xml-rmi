package com.aladyn.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.aladyn.ProtoResponseFault;
import com.aladyn.XmlRmiException;
import com.aladyn.parser.Request;
import com.aladyn.parser.XmlProcessFacade;
import com.aladyn.server.exception.ResponseBuilderException;

/**
 * Thread contenant l'objet partagé et l'exposant par le réseau
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class SharedObjectListener extends Thread {
	
	private Class<?> sharedClass;
	private Object sharedObject;
	
	private XmlProcessFacade processXml = new XmlProcessFacade();
	private MethodCaller methodCaller = new MethodCaller();
	private ResponseBuilder protoBuilder = new ResponseBuilder();
	private Request request;
	private ArrayList<Object> results;
	
	private ServerSocket server;
	private Socket socket;
	private int port;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	
	/**
	 * Prends la classe de l'objet partagé 
	 * et le port auquel il sera exposé
	 *   
	 * @param sharedClass
	 * @param port
	 */
	public SharedObjectListener(Class<?> sharedClass, int port) {
		this.sharedClass = sharedClass;
		this.port = port;
	}
	
	
	/**
	 * Lancement du serveur de l'objet partagé
	 */
	@Override
	public void run()
	{
		System.out.println("Lancement du thread qui gère l'objet paragé : " + sharedClass.toString() + " sur le port : " + port);
		
		try	{
			this.sharedObject = sharedClass.newInstance();
		} catch (InstantiationException e)	{
			// Impossible d'instancer la classe
			e.printStackTrace();
//			throw new RuntimeException();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		try	{
			server = new ServerSocket(port);
			server.setSoTimeout(100000);
		}catch (IOException e) {
			// Impossible de lancer le serveur
			e.printStackTrace();
//			throw new RuntimeException();
		}
		
		while(true)	{
			try	{
				
				// Nouveau client accepté
				socket = server.accept();	
				ois = new ObjectInputStream(socket.getInputStream());
				oos = new ObjectOutputStream(socket.getOutputStream());
				
				System.out.println("connection sur " + sharedClass.getSimpleName() + " accèptée");
								
				// Réception de l'appele distant
				String xmlRequest = (String) ois.readObject();
				
				// Construire l'appele en parsant le XML
				request = processXml.process(xmlRequest);
				
				// Appeler la méthode distante
				results = methodCaller.call(sharedObject, request);
				
				// Construire la réponse
				String xmlResponse = protoBuilder.buildXmlResponse(results);

				// Envoie de la réponse
				oos.writeObject(xmlResponse);
				
				System.out.println(sharedClass.getSimpleName() + " a renvoyé la réponse");
				
			} catch (IOException e) {
				// si on ferme le serveur, il déclanche une exception
				// on profite pour la ratrapé et interompre le thread
				Thread.currentThread().interrupt();
				break;
			} catch (ClassNotFoundException e) {
				sendFaultResponse(ProtoResponseFault.CLIENT_EXCEPTION,
						ProtoResponseFault.SOCKET_FLOW);
			} catch (XmlRmiException  e) {
				sendFaultResponse(e.getCode(), e.getMessage());
			} finally {
				try {
					ois.close();
					oos.close();
					socket.close();
				} catch (IOException e)	{
					e.printStackTrace();
				}
			}
		}
	}
	
	private void sendFaultResponse(int faultCode, String stringFault)
	{
		try	{
			oos.writeObject(protoBuilder.buildXmlFaultResponse(
					faultCode, stringFault));
		} catch (IOException e) {
			// pas de traitement, on passe au client suivant
			e.printStackTrace();
		} catch (ResponseBuilderException e) {
			// @TODO essayer de logger ou de passer au main serveur
			e.printStackTrace();
		}
	}
	
	/**
	 * Fermer le serveur de l'objet partagé
	 */
	public void closeServer() {
		try	{
			server.close();
		} catch (IOException e) {
			// Erreur lors de la fermeture du server
			e.printStackTrace();
		}
	}
}
