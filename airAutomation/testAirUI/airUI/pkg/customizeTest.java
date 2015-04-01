/**
 * 
 */
package airUI.pkg;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Cuahuc
 *
 */
public class customizeTest 
{

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		Customize c = new Customize();
		Room r;
	}

	/**
	 * Test method for {@link airUI.pkg.Customize#correctRange(java.lang.String, int, int)}.
	 */
	@Test
	public void testCorrectRange() 
	{
		//Return false if nothing is wrong
		assertFalse(c.correctRange("Alain", 66,75));
		//Return True if 
		assertTrue(c.correctRange("Bryan", 85,75));
	}

	/**
	 * Test method for {@link airUI.pkg.Customize#addModRoomsButton(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddModRoomsButton() 
	{
		r.createRoom("Fatimah", 65,75);
		r.createRoom("Temo", 68,78);
		r.createRoom("Bryan", 65,80);
		r.createRoom("Alain", 60,70);
		assertTrue(c.addModRoomsButton("Gary", 63,73));
		assertTrue(c.addModRoomsButton("Fatimah", 63,73));
		assertFalse(c.addModRoomsButton("Temo", 68,78));
	}

}
