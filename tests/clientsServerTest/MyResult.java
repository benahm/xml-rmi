package clientsServerTest;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class MyResult
{

	private Object returnMethod;
	private int num;
	
	public MyResult(Object returnMethod, int num)
	{
		this.returnMethod = returnMethod;
		this.num = num;
	}

	public Object getReturnMethod()
	{
		return returnMethod;
	}

	public int getNum()
	{
		return num;
	}

}
