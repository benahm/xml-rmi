package com.aladyn.client;

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
@SuiteClasses({  ClientSideTest.class, UpdaterTest.class, IdTableTest.class })
public class AllTests
{

}
