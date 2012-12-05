package clientsServerTest;

import java.util.concurrent.Callable;

import com.aladyn.client.XMLRMIClient;
import com.aladyn.client.exception.ConnectionException;
import com.aladyn.client.exception.RemoteCallException;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class ThreadClientUltimateCalculator implements Callable<MyResult>
{
	
	private Object returnMethod;
	private int num;
	private String ip;

	public ThreadClientUltimateCalculator(String ip, int num)
	{
		this.num = num;
		this.ip = ip;
	}
	
	@Override
	public MyResult call() throws Exception
	{
		XMLRMIClient client = new XMLRMIClient(5000, ip);
		try {
			client.setConnection("UltimateCalculator");
			returnMethod = client.remoteCall("laVerite");
		} catch (ConnectionException e) {
			e.printStackTrace();
			return new MyResult(null, num);
		} catch (RemoteCallException e) {
			e.printStackTrace();
			return new MyResult(null, num);
		}
		
		return new MyResult(returnMethod, num);
	}

}
