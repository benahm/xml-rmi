package com.aladyn.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Expose le registre à traver le réseau
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class XMLRMIRegistryServeur extends Thread {
	
	private XMLRMIRegistry registry;
	
	private ServerSocket server;
	
	private ExecutorService pool;
	private int poolSize = 10;
	

	/**
	 * Constructeur qui prend un registre
	 * 
	 * @param port
	 * 		le port du registre
	 * @param classList
	 * 		la liste des classes qui serons explosées par le registre
	 */
	public XMLRMIRegistryServeur(XMLRMIRegistry registry) {
		this.registry = registry;
	}
	
	
	/**
	 * Lancement du serveur registre
	 */
	@Override
	public void run() {
		
		System.out.println("Lancement du registre, port : " + 
				registry.getRegistryPort() + " ,avec " + 
				registry.getNbPorts() + " classes chargés" );
		
		pool = Executors.newFixedThreadPool(poolSize);
				
		try	{
			server = new ServerSocket(registry.getRegistryPort());
		}catch (IOException e) {
			// Impossible de lancer le serveur
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
		
		while(true)	{
			try	{
				
				System.out.println("nouveau client pour le registre");
				pool.submit(new ProcessRegistryThread(server.accept()));
				
			} catch (IOException e) {
				// si on ferme le serveur, on déclanche une exception
				// on profite pour la rattraper et interompre le thread
				Thread.currentThread().interrupt();
				pool.shutdownNow();
				break;
			}

		}
	}
	
	
	/**
	 * Fermeture du registre
	 */
	public void closeRegistry()	{
		try	{
			server.close();
		} catch (IOException e) {
			// Erreur lors de la fermeture du server
			e.printStackTrace();
		}
	}
	
	
	class ProcessRegistryThread implements Runnable {
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		private final Socket socket;
		public ProcessRegistryThread(Socket socket) {
			this.socket = socket;
		}
		@Override
		public void run() {
			try	{
				ois = new ObjectInputStream(socket.getInputStream());
				oos = new ObjectOutputStream(socket.getOutputStream());
				String portRequest = (String) ois.readObject();
				String portResponse = String.valueOf(registry.getPort(portRequest));
				System.out.println("reponse du registre : " + portResponse);
				oos.writeObject(portResponse);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try	{
					ois.close();
					oos.close();
					socket.close();
				} catch (IOException e)	{
					// erreur lors de la fermeture de la connexion
					e.printStackTrace();
				}
			}
		}
	}
	
}
