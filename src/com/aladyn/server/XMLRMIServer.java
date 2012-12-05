package com.aladyn.server;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.aladyn.ProtoResponseFault;
import com.aladyn.server.exception.PackageClassLoaderException;
import com.aladyn.server.exception.XMLRMIServerException;

/**
 * Classe à usage côté serveur, qui encapsule tout la complexité du protocole XML-RMI
 * Elle instancie un registre et les objets partagé qui effecturons le traitement des appeles XML-RMI
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * 
 */
public class XMLRMIServer {
	
	private XMLRMIRegistry registry;
	private XMLRMIRegistryServeur registryServer;
	private int port;
	private ExecutorService sharedObjectPool;
	private int poolSize = 10;
	private ArrayList<Class<?>> classList;
	private String packageInterfaceName = null;
	
	
	/**
	 * Constucteur sans parametre (par defaut port=5000)
	 */
	public XMLRMIServer() {
		this.port = 5000;
	}
	
	/**
	 * Construteur avec port en parametre
	 * 
	 * @param port port d"ecoute de XMLRMIServer
	 */
	public XMLRMIServer(int port) {
		this.port = port;
	}
	
	
	/**
	 * Specifier le package ou se trouve tout les objets qui peuvent être appeler à distance
	 *  
	 * @param packageSharedObjectName le nom du package
	 */
	public void setObjectPool(String packageSharedObjectName) {
		try {
			classList = PackageClassLoader.loadClasses(packageSharedObjectName);
		} catch (PackageClassLoaderException e1) {
			//@TODO lancer une exception 
			//et envoyer une erreur au client
			e1.printStackTrace();
		}
	}
	
	/**
	 * Specifier le package où se trouve toutes les interfaces partagées entre le client et le serveur
	 *  
	 * @param packageInterfaceName
	 */
	public void setInterfacePool(String packageInterfaceName) {
		this.packageInterfaceName  = packageInterfaceName;
		InterfacePool.getInstance().setInterfacePoolPackage(packageInterfaceName);
	}
	
	/**
	 * Chargement du registre
	 */
	private void loadRegistry() {
		registry = new XMLRMIRegistry(port, classList);
	}
	
	/**
	 * Chargement et execution du 
	 * serveur registre en écoute sur le port indiqué
	 */
	private void loadRegistryServer() {
		registryServer = new XMLRMIRegistryServeur(registry);
		registryServer.start();
	}
	
	/**
	 * Chargement et execution des objets partagés dans le pool de threads 
	 * en écouté sur les ports attribués par le registre
	 */
	private void loadSharedObjects() {
//		poolSize = classList.size();
		sharedObjectPool = Executors.newFixedThreadPool(poolSize);
		for (Class<?> clazz : classList) {
			sharedObjectPool.execute(
					new SharedObjectListener(
							clazz, registry.getPort(clazz.getSimpleName())));
		}
	}
	
	/**
	 * Lancement du service XML-RMI en attente de la connexion des clients
	 * 
	 * @throws XMLRMIServerException 
	 */
	public void startXmlRmiServer() throws XMLRMIServerException {
		
		// les objets partagés et les classes doivent être chargés
		if (classList == null) throw new XMLRMIServerException(
				ProtoResponseFault.SERVER_EXCEPTION, 
				"Veuillez indiquer le package contenant les objets partagés");
		if (packageInterfaceName == null) throw new XMLRMIServerException(
				ProtoResponseFault.SERVER_EXCEPTION, 
				"Veuillez indiquer le package contenant les interfaces partagés");
		
		// attrape toutes les exceptions non traitées par les Threads
//		Thread.getDefaultUncaughtExceptionHandler();
		
		// chargement du registre
		loadRegistry();
		
		// chargement du serveur registre 
		loadRegistryServer();
		
		// chaque objet partagé est associé à un thread
		loadSharedObjects();
		
		
		System.out.println("tous les threads sont lancés");
		
	}

	/**
	 * Arrêt du service XML-RMI
	 */
	public void stopXmlRmiServer() {
		registryServer.closeRegistry();
		sharedObjectPool.shutdownNow();
	}
	

}
