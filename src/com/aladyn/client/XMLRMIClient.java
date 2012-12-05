package com.aladyn.client;

import java.util.ArrayList;

import com.aladyn.ProtoResponseFault;
import com.aladyn.client.exception.ClientSideException;
import com.aladyn.client.exception.ConnectionException;
import com.aladyn.client.exception.NotConformNameMethodException;
import com.aladyn.client.exception.UpdateException;
import com.aladyn.client.exception.RemoteCallException;
import com.aladyn.client.params.FieldFactory;
import com.aladyn.client.params.IParam;
import com.aladyn.client.params.ObjectParamFieldOnly;

/**
 * XMLRMIClient classe à usage côté client, qui encapsule tout la complexité du
 * protocole XML-RMI
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * 
 */
public class XMLRMIClient {

	private int portRegistry;
	private int portRemoteObject=0;
	private String addr;

	/**
	 * Construteur
	 * 
	 * @param port
	 * 		port du XMLRMIRegistry
	 * @param addr
	 *      address du XMLRMIRegisty
	 */
	public XMLRMIClient(int port, String addr) {
		this.portRegistry = port;
		this.addr = addr;
	}

	/**
	 * setConnection etablie la connection avec un objet distant
	 * 
	 * @param objectName
	 *            le nom de l'objet distant
	 * @throws ConnectionException 
	 */
	public void setConnection(String objectName) throws ConnectionException {
		try {
			
			ClientSide.send(objectName, portRegistry, addr);
			this.portRemoteObject = Integer.parseInt(ClientSide.receive());
			
		} catch (ClientSideException e)	{
			throw new ConnectionException(ProtoResponseFault.CLIENT_EXCEPTION, 
					e.getMessage(), e);
		}
		
	}

	/**
	 * remoteCall appet distant d'un methode sur l'objet distant
	 * 
	 * @param methodName
	 *            le nom de la methode distante
	 * @param params
	 *            les parametres de la methodes distante
	 * @return le return de la methode distante
	 * @throws RemoteCallException 
	 */
	public Object remoteCall(String methodName, IParam... params) throws RemoteCallException {
		try {
			// doit être vérifier dans set connection
			System.out.println("port : " + portRemoteObject);
			if(portRemoteObject==0) 
				throw new RemoteCallException(
						ProtoResponseFault.CLIENT_EXCEPTION,
						ProtoResponseFault.CONNECTION_NOT_ESTABLISH);
			
			ClientSide.send(ClientSide.buildRequestl(methodName, params),
					portRemoteObject, addr);
			
			String response = ClientSide.receive();
			
			Updater up = new Updater();
			up.update(response);
			
			return up.getReturnMethod();
			
		} catch (NotConformNameMethodException e) {
			throw new RemoteCallException(
					ProtoResponseFault.CLIENT_EXCEPTION,
					e.getMessage(), e);
		} catch (UpdateException e) {
			throw new RemoteCallException(
					ProtoResponseFault.CLIENT_EXCEPTION,
					e.getMessage(), e);
		} catch (ClientSideException e)	{
			throw new RemoteCallException(
					ProtoResponseFault.CLIENT_EXCEPTION,
					e.getMessage(), e);
		}
	}
	/**
	 * remoteCall appet distant d'un methode sur l'objet distant avec les parametres non encapsulé
	 * 
	 * @param methodName 
	 * 		le nom de la methode distante
	 * @param params 
	 * 		les parametres non ecapsulé dans des IParam
	 * @return 
	 * 		le return de la methode distante  
	 * @throws RemoteCallException
	 */
	public Object remoteCall(String methodName, Object... params) throws RemoteCallException {
		ArrayList<IParam> paramsList=new ArrayList<IParam>();
		IParam wrapParam;
		for (Object object : params) {
			wrapParam = FieldFactory.wrapParam(object);
			if(wrapParam.getClass()==ObjectParamFieldOnly.class)
				throw new RemoteCallException(ProtoResponseFault.CLIENT_EXCEPTION, 
						ProtoResponseFault.TYPE_OBJECTPARAM_ENCAPSULATION);
			paramsList.add(wrapParam);
		}
		IParam[] paramsArray=paramsList.toArray(new IParam[paramsList.size()]); 
		return remoteCall(methodName, paramsArray);
	}
	
}