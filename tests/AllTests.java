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
@SuiteClasses({ com.aladyn.client.AllTests.class, 
	com.aladyn.client.params.AllTests.class, 
	com.aladyn.parser.AllTests.class, 
	com.aladyn.server.AllTests.class })
public class AllTests
{

}
