/**
 * 
 */
package airUI.pkg;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author BMosAir
 *
 */
public class roomTest {
	private static Room room;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
		room = Room.createRoom("", "", "");
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	@Before
	public void setUp() throws Exception {
		try {
		Room.createRoom("master", "65", "85");
		Room.createRoom("kids", "65", "85");
		} catch(Exception e) {
			System.err.println(e);
		}
	}

	/**
	 * Test method for {@link airUI.pkg.Room#createRoom(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testCreateRoom() {
		Room bryan = Room.createRoom("bryan", "65", "85");
		
		// does the room exist
		Assert.assertNotNull(Room.getRoom("bryan"));
		
		// am I referring to the correct room
		Assert.assertEquals(bryan, Room.getRoom("bryan"));
		
		// what is the size of the room list
		Assert.assertEquals(4, room.getSize());
		
		// try to create a room that already exists
		Assert.assertEquals(bryan, Room.createRoom("bryan", "65", "85"));
		
		bryan = null;
		Assert.assertNull(bryan);
		
	}

	/**
	 * Test method for {@link airUI.pkg.Room#dataReceived(com.digi.xbee.api.models.XBeeMessage)}.
	 */
	@Test
	public void testDataReceived() {
		// TODO
	}

	/**
	 * Test method for {@link airUI.pkg.Room#getRoom(java.lang.String)}.
	 */
	@Test
	public void testGetRoom() {
		System.out.println("getRoom: ");
	}

	/**
	 * Test method for {@link airUI.pkg.Room#getroomList()}.
	 */
	@Test
	public void testGetroomList() {

	}

	/**
	 * Test method for {@link airUI.pkg.Room#removeRoom(java.lang.String)}.
	 */
	@Test
	public void testRemoveRoom() {
		System.out.println("remove room test: ");

	}

	/**
	 * Test method for {@link airUI.pkg.Room#getHumidity()}.
	 */
	@Test
	public void testGetHumidity() {
		System.out.println("get humidity test: ");
	}

	/**
	 * Test method for {@link airUI.pkg.Room#getCarbonDioxide()}.
	 */
	@Test
	public void testGetCarbonDioxide() {
		// TODO
	}

	/**
	 * Test method for {@link airUI.pkg.Room#getMethane()}.
	 */
	@Test
	public void testGetMethane() {
		// TODO
	}

}
