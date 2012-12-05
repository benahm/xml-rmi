package clientsServerTest;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.aladyn.server.PackageClassLoader;
import com.aladyn.server.exception.PackageClassLoaderException;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class Client {

	/**
	 * @param args
	 * @throws PackageClassLoaderException 
	 */

	public static void main(String[] args) throws PackageClassLoaderException {
		
		String ip = "localhost";
		
		int error = 0;
		Random r = new Random(); 
		ArrayList<Class<?>> clazz = 
				PackageClassLoader.loadClasses("com.aladyn.server.objects");

		ExecutorService executor = Executors.newFixedThreadPool(10);		
		CompletionService<MyResult> completionService =
				new ExecutorCompletionService<MyResult>(executor);

		
		ArrayList<Future<MyResult>> futures = new ArrayList<Future<MyResult>>();
		
		String clazzName;
		Callable<MyResult> c = null;
		MyResult res;
		ArrayList<Callable<MyResult>> list = new ArrayList<Callable<MyResult>>();
		try {
			
			for (int i = 0; i < 100; i++) {
				
					clazzName = clazz.get(r.nextInt(clazz.size())).getSimpleName();
				
					if(clazzName.equals("SuperCalculator"))
						c = new ThreadClientSuperCalculator(ip, i, i+1, i+1*2);
					
					if(clazzName.equals("UltimateCalculator"))
						c = new ThreadClientUltimateCalculator(ip, i);
				
				System.out.println("Client " + i + " créé");
				
//				futures.add(completionService.submit(c));
				
				list.add(c);
			}
			
			for (Callable<MyResult> callable : list) {
				futures.add(completionService.submit(callable));
			}

			for (int i = 0; i < futures.size(); ++i) {
				try	{
					
					res = completionService.take().get();

					if (res.getReturnMethod() != null)
						System.out.println("Client " + res.getNum() + 
								" : retour de l'appel de méthode : " + res.getReturnMethod());
					else {
						System.out.println("Client " + res.getNum() + 
								" : erreur survenu durant l'appele de methode");
						error++;
					}
					
				} catch (ExecutionException e)	{
					e.printStackTrace();
				}
			}
			System.out.println("il y a eu " + error + " erreurs");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			executor.shutdown();
		}
				
	}


}
