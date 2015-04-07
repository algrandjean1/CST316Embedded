/**
 * 
 */
package testAirUI.airUI.pkg;
import airUI.pkg.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Cuahuc
 *
 */
public class customizeTest 
{

	Customize c = new Customize(new MainDriver());
	Room r;
	

	/**
	 * Test method for {@link airUI.pkg.Customize#correctRange(java.lang.String, int, int)}.
	 */
	@Test
	public void testCorrectRange() 
	{
		//Return false if nothing is wrong
		assertFalse(c.correctRange("Alain", 66,75));
		//Return True if low is higher than high or high is lower than low
		assertTrue(c.correctRange("Bryan", 85,75));
	}

	/**
	 * Test method for {@link airUI.pkg.Customize#addModRoomsButton(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddModRoomsButton() 
	{
		r.createRoom("Fatimah", "65","75");
		r.createRoom("Temo", "68","78");
		r.createRoom("Bryan", "65","80");
		r.createRoom("Alain", "60", "70");
		
		//return true is added a new user without problems
		assertTrue(c.addModRoomsButton("Gary", "63","73"));
		//return true if able to modify user ranges
		assertTrue(c.addModRoomsButton("Fatimah", "63","73"));
		//return false if not need to modify existing model
		assertFalse(c.addModRoomsButton("Temo", "68","78"));
	}

}
