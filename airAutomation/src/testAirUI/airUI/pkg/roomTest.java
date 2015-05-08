/**
 * whitebox unit tests on the Room class 
 */
package testAirUI.airUI.pkg;

import airUI.pkg.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import airUI.pkg.Room;
import airUI.pkg.XBeeHandler;

/**
 * @author BMosAir
 *
 */
@SuppressWarnings("unused")
public class roomTest {
	private Room room;
	private XBeeHandler xbeeHandler;

	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		try {
		Room.createRoom("", "", "", xbeeHandler);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	//@Before
	public void setUp() throws Exception {
		try {
		Room.createRoom("master", "65", "85", xbeeHandler);
		Room.createRoom("kids", "65", "85", xbeeHandler);
		} catch(Exception e) {
			System.err.println(e);
		}
	}

	/**
	 * Test method for {@link airUI.pkg.Room#createRoom(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	//@Test
	public void testCreateRoom() {
		Room bryan = Room.createRoom("bryan", "65", "85", xbeeHandler);
		
		// does the room exist
		Assert.assertNotNull(Room.getRoom("bryan"));
		
		// am I referring to the correct room
		Assert.assertEquals(bryan, Room.getRoom("bryan"));
		
		// what is the size of the room list
		Assert.assertEquals(4, Room.getSize());
		
		// try to create a room that already exists
		Assert.assertEquals(bryan, Room.createRoom("bryan", "65", "85", xbeeHandler));
		
		bryan = null;
		Assert.assertNull(bryan);
		
	}

	/**
	 * Test method for {@link airUI.pkg.Room#dataReceived(com.digi.xbee.api.models.XBeeMessage)}.
	 */
	//@Test
	public void testDataReceived() {
		
	}

	/**
	 * Test method for {@link airUI.pkg.Room#getRoom(java.lang.String)}.
	 */
	//@Test
	public void testGetRoom() {
		Room danielle = Room.createRoom("dani", "65", "85", xbeeHandler);
		Assert.assertEquals(danielle, Room.getRoom("dani"));
	}
	
	/**
	 * Test method for {@link airUI.pkg.Room#containsRoom(java.lang.String)}.
	//@Test
	public void testContainsRoom() {
		Assert.assertTrue(Room.containsRoom("master"));
		Assert.assertTrue(Room.containsRoom("kids"));
		Assert.assertTrue(Room.containsRoom("KIDS"));
		Assert.assertTrue(!Room.containsRoom("m aster"));
		Assert.assertTrue(!Room.containsRoom("Mohammed"));
	}
	 */

	/**
	 * Test method for {@link airUI.pkg.Room#getroomList()}.
	 */
	//@Test
	public void testGetroomList() {
int listSize = Room.getroomList().size();
Assert.assertEquals(4, listSize);
	}

	/**
	 * Test method for {@link airUI.pkg.Room#removeRoom(java.lang.String)}.
	 */
	//@Test
	public void testRemoveRoom() {
		Assert.assertFalse(Room.removeRoom("bryan"));
		
		Assert.assertTrue(Room.removeRoom("dani"));
	}
/**
* Test method for {@link airUI.pkg.Room#getTemperature()}.
 */
	@Test
	public void testGetTemperature() {
		Room bryan = Room.createRoom("", "", "", xbeeHandler);
		Assert.assertEquals("\"\"", bryan.getTemperature());
	}
	
	/**
	 * Test method for {@link airUI.pkg.Room#getHumidity()}.
	 */
	//@Test
	public void testGetHumidity() {
		Room bryan = Room.createRoom("bryan", "65", "85", xbeeHandler);
Assert.assertEquals("\"\"", bryan.getHumidity());
	}

	/**
	 * Test method for {@link airUI.pkg.Room#getCarbonDioxide()}.
	 */
	//@Test
	public void testGetCarbonDioxide() {
		Room bryan = Room.createRoom("bryan", "65", "85", xbeeHandler);
		Assert.assertEquals("\"\"", bryan.getCarbonDioxide());
	}

	/**
	 * Test method for {@link airUI.pkg.Room#getMethane()}.
	 */
	//@Test
	public void testGetMethane() {
		Room bryan = Room.createRoom("", "", "", xbeeHandler);
		Assert.assertEquals("\"\"", bryan.getMethane());
	}

}
