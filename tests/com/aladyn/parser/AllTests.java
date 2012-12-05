package com.aladyn.parser;

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
@SuiteClasses({ DOMProcessTest.class, 
		RequestTest.class, XMLFileParserTest.class, XMLParserTest.class,
		XmlProcessFacadeTest.class, XmlValidatorTest.class })
public class AllTests
{

}
