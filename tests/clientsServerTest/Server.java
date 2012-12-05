package clientsServerTest;

import com.aladyn.server.XMLRMIServer;
import com.aladyn.server.exception.XMLRMIServerException;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		XMLRMIServer server=new XMLRMIServer();
		server.setObjectPool("com.aladyn.server.objects");
		server.setInterfacePool("com.aladyn.shared.interfaces");
		try {
			server.startXmlRmiServer();
		} catch (XMLRMIServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
