package com.aladyn.client.params;

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
@SuiteClasses({ ArrayParamTest.class, Base64ParamTest.class,
		BoolParamTest.class, DateTimeParamTest.class, DoubleParamTest.class,
		FetchSourceCodeTest.class, IntParamTest.class,
		ObjectParamFieldOnlyTest.class, ObjectParamTest.class,
		StringParamTest.class, StructParamTest.class })
public class AllTests
{

}
