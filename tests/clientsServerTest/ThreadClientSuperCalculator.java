package clientsServerTest;

import java.util.concurrent.Callable;

import com.aladyn.client.XMLRMIClient;
import com.aladyn.client.exception.ConnectionException;
import com.aladyn.client.exception.RemoteCallException;
import com.aladyn.client.params.DoubleParam;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class ThreadClientSuperCalculator implements Callable<MyResult>
{
	
	private Object returnMethod;
	private double x, y;
	private int num;
	private String ip;
	
	public ThreadClientSuperCalculator(String ip, int num, int x, int y)
	{
		this.x = x;
		this.y = y;
		this.num = num;
		this.ip = ip;
	}
	
	@Override
	public MyResult call() throws Exception
	{
		XMLRMIClient client = new XMLRMIClient(5000, ip);
		try {
			client.setConnection("SuperCalculator");
			returnMethod = client.remoteCall("makeMult",
					new DoubleParam(x), new DoubleParam(y));
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
