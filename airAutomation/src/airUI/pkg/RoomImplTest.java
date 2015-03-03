/**
 * 
 */

package airUI.pkg;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author bjduarte
 * @version Spring 2015
 */

public class RoomImplTest {
private RoomImpl roomImpl;
private Room room;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link airUI.pkg.RoomImpl#RoomImpl()}.
	 */
	@Test
	public void testRoomImpl() {
		
		Room master = RoomImpl.getRoom("MasterRoom");
		Assert.assertEquals("MasterRoom", master);
Assert.assertEquals("65", master.getLowerBound());
Assert.assertEquals("85", master.getUpperBound());
	}

	/**
	 * Test method for {@link airUI.pkg.RoomImpl#getRoom(java.lang.String)}.
	 */
	@Test
	public void testGetRoom() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link airUI.pkg.RoomImpl#getroomList()}.
	 */
	@Test
	public void testGetroomList() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link airUI.pkg.RoomImpl#removeRoom(java.lang.String)}.
	 */
	@Test
	public void testRemoveRoom() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link airUI.pkg.RoomImpl#addRoom(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddRoom() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#Object()}.
	 */
	@Test
	public void testObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#getClass()}.
	 */
	@Test
	public void testGetClass() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#clone()}.
	 */
	@Test
	public void testClone() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#notify()}.
	 */
	@Test
	public void testNotify() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#notifyAll()}.
	 */
	@Test
	public void testNotifyAll() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#wait(long)}.
	 */
	@Test
	public void testWaitLong() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#wait(long, int)}.
	 */
	@Test
	public void testWaitLongInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#wait()}.
	 */
	@Test
	public void testWait() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#finalize()}.
	 */
	@Test
	public void testFinalize() {
		fail("Not yet implemented");
	}

}
