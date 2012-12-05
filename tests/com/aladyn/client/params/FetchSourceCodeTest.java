package com.aladyn.client.params;

import java.lang.reflect.Method;

import org.junit.Test;

import com.aladyn.client.exception.FetchSourceCodeException;
import com.aladyn.server.objects.SuperCalculator;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class FetchSourceCodeTest
{

	@Test
	public final void test()
	{
		FetchSourceCode code = new FetchSourceCode(Object.class);
		Method m = null;
		try {
			m = SuperCalculator.class.getDeclaredMethod("makeSum",
					int.class,int.class);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		try {
			code.getSourceMethod(m);
		} catch (FetchSourceCodeException e) {
			System.out.println(e.getMessage());
		}
		System.out.println((SuperCalculator.class.getCanonicalName()).replace(
				'.', '/'));
	}

}
