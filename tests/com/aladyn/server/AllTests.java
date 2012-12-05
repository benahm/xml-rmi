package com.aladyn.server;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ MethodCallerTest.class, ObjectBuilderTest.class, InterfacePoolTest.class,
		PackageClassLoaderTest.class, ResponseBuilderTest.class,
		XMLRMIRegistryTest.class })
public class AllTests
{

}
