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
public class XMLRMIRegistryTest
{
	private int port = 5000;
	private String packageName = "com.aladyn.server.sharedObjectsTest";
	private ArrayList<Class<?>> classList = null;
	private XMLRMIRegistry registry = null;

	
	@Test
	public final void testXMLRMIRegistry() {
		try	{
			classList = PackageClassLoader.loadClasses(packageName);
		} catch (PackageClassLoaderException e)	{
			e.printStackTrace();
		}
		
		registry = new XMLRMIRegistry(port, classList);
		
		System.out.println(registry);
		
		ArrayList<Integer> objectPortRegistry = new ArrayList<Integer>();
		objectPortRegistry.add(registry.getPort(ObjectTest1.class.getSimpleName()));
		objectPortRegistry.add(registry.getPort(ObjectTest2.class.getSimpleName()));
		objectPortRegistry.add(registry.getPort(ObjectTest3.class.getSimpleName()));
		objectPortRegistry.add(registry.getPort(ObjectTest123.class.getSimpleName()));
		
		ArrayList<Integer> objectsPortExpected = new ArrayList<Integer>(); 
		objectsPortExpected.add(5001);
		objectsPortExpected.add(5002);
		objectsPortExpected.add(5003);
		objectsPortExpected.add(5004);
		
		Assert.assertTrue(objectsPortExpected.containsAll(objectPortRegistry));
	}

}
