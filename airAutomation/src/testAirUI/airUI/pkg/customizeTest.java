/**
 * 
 */
package testAirUI.airUI.pkg;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import airUI.pkg.Customize;
import airUI.pkg.MainDriver;
import airUI.pkg.Room;
import airUI.pkg.XBeeHandler;

/**
 * @author Cuahuc
 *
 */
@SuppressWarnings("unused")
public class customizeTest 
{
	private XBeeHandler xbeeHandler;

	Customize c = new Customize(new MainDriver());
	Room r;
	

	/**
	 * Test method for {@link airUI.pkg.Customize#correctRange(java.lang.String, int, int)}.
	@Test
	public void testCorrectRange() 
	{
		//Return false if nothing is wrong
		assertFalse(c.correctRange("Alain", 66,75));
		//Return True if low is higher than high or high is lower than low
		assertTrue(c.correctRange("Bryan", 85,75));
	}
	 */

	/**
	 * Test method for {@link airUI.pkg.Customize#populateKeysList(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@SuppressWarnings({ "hiding", "static-access" })
	@Test
	public void testAddModRoomsButton() throws Exception
	{
		XBeeHandler xbeeHandler = new XBeeHandler();
		r.createRoom("Fatimah", "65","75", xbeeHandler);
		r.createRoom("Temo", "68","78", xbeeHandler);
		r.createRoom("Bryan", "65","80", xbeeHandler);
		r.createRoom("Alain", "60", "70", xbeeHandler);
		
		//return true is added a new user without problems
		//assertTrue(c.populateKeysList("Gary", "63","73", xbeeHandler));
		
		//return false if able to modify user ranges
		//assertFalse(c.populateKeysList("Fatimah", "63","73", xbeeHandler));
		
		//return false if not need to modify existing model
		//assertFalse(c.populateKeysList("Temo", "68","78", xbeeHandler));
	}

}
