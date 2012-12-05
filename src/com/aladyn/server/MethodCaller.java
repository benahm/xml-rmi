package com.aladyn.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.aladyn.ProtoResponseFault;
import com.aladyn.client.params.VoidParam;
import com.aladyn.parser.Request;
import com.aladyn.server.exception.ProcessMethodCallException;

/**
 * Invoque un appele de méthode sur l'objet partagé
 * le nom de la méthode et les paramêtres sont dans l'objet Request
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class MethodCaller
{
	private ArrayList<Object> results = new ArrayList<Object>();
	
	/**
	 * Appele une méthode de sharedObject 
	 * 
	 * @param sharedObject
	 * 		l'objet sur lequel la méthode va être appelé
	 * @param request
	 * 		l'objet possédant le nom de la méthode à appeler
	 * 		ainsi que les paramêtres
	 * @return
	 * @throws ProcessMethodCallException
	 */
	public ArrayList<Object> call(Object sharedObject, Request request) 
			throws ProcessMethodCallException {
		
		try	{
		
			System.out.println("***BeginCallMethod***");			
			System.out.println(request.getValues());
			
			Method method = sharedObject.getClass().getMethod(
					request.getMethodName(), 
					request.getTypeArray());
			Object callResult = method.invoke(sharedObject, request.getValuesArray());
			
			System.out.println(method.getName());
			System.out.println("result = " + callResult);
			
			results.clear();
			
			if (callResult == null)
				results.add(new VoidParam());
			else
				results.add(callResult);
			
			for (int i = 0; i < request.getValues().size(); i++) {
				if (request.getType(i).isInterface()) {
					System.out.println("interface trouvé");
					results.add(request.getValue(i));
				}
			}
			
			System.out.println("***EndCallMethod***");
			
			return results; 
			
		} catch (NoSuchMethodException e) {
			throw new ProcessMethodCallException(
					ProtoResponseFault.METHOD_CALL_FALL, 
					ProtoResponseFault.METHOD_DONT_EXISTS, 
					e);
		}  catch (IllegalAccessException e) {
			throw new ProcessMethodCallException(
					ProtoResponseFault.METHOD_CALL_FALL, 
					ProtoResponseFault.METHOD_DONT_EXISTS, 
					e);
		} catch (SecurityException e) {
			throw new ProcessMethodCallException(
					ProtoResponseFault.SERVER_EXCEPTION, 
					ProtoResponseFault.SECURITY_VIOLATION, 
					e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new ProcessMethodCallException(
					ProtoResponseFault.SERVER_EXCEPTION, 
					ProtoResponseFault.ILLEGAL_ARGUMENTS, 
					e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new ProcessMethodCallException(
					ProtoResponseFault.SERVER_EXCEPTION,
					ProtoResponseFault.INVOCATION_ERROR, 
					e);
		}
	}
}
