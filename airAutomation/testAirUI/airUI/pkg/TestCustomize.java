/**
 * 
 */
package airUI.pkg;

import airUI.pkg.Room;
import junit.framework.TestCase;

/**
 * @author Cuahuc
 *
 */
public class TestCustomize extends TestCase 
{

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	
	protected void setUp() throws Exception 
	{
		super.setUp();
		Customize cust = new Customize();
		Room r = new Room();
		
	}

	/**
	 * Test method for {@link airUI.pkg.Customize#correctRange(int, int)}.
	 */
	public void testCorrectRange() 
	{
		boolean testFalse = cust.correctRange("68", "77");
		boolean testTrue = cust.correctRange("77", "86")
		assertFalse(testFalse);
		assertTrue(testTrue);
	}

	/**
	 * Test method for {@link airUI.pkg.Customize#addModRoomsButton(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	public void testAddModRoomsButton() 
	{
		r.createRoom("Alain", "70", "80");
		r.createRoom("Bryan", "80", "85");
		r.createRoom("Fatimah", "73", "78");
		r.createRoom("temo", "61", "66");
		
		r.getRoom("temo");
		
		
	}

	/**
	 * Test method for {@link airUI.pkg.Customize#setRoomValues(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	public void testSetRoomValues() 
	{
		fail("Not yet implemented");
	}

}
