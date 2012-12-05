package com.aladyn.server;

import java.util.ArrayList;

import junit.framework.Assert;
import org.junit.Test;

import com.aladyn.server.exception.PackageClassLoaderException;
import com.aladyn.server.sharedObjectsTest.*;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class PackageClassLoaderTest
{

	// les classes ne sont pas forcément chargées dans l'ordre alphabétique
	@Test
	public final void testPackageClassLoader()
	{
		String packageName = "com.aladyn.server.sharedObjectsTest";
		ArrayList<Class<?>> classList = null;
		try	{
			classList = PackageClassLoader.loadClasses(packageName);
		} catch (PackageClassLoaderException e)	{
			e.printStackTrace();
		}
		
		Assert.assertTrue(classList.contains(ObjectTest1.class));
		Assert.assertTrue(classList.contains(ObjectTest2.class));
		Assert.assertTrue(classList.contains(ObjectTest3.class));
		
		Assert.assertFalse(classList.contains(Integer.class));
		
		Assert.assertEquals(4, classList.size());
	}

}
