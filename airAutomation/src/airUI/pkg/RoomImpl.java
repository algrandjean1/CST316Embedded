/**
 * AirAutomation project to monitor indoor air quality.
 */

package airUI.pkg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

/**
 * @author BMosAir
 *@Version Spring 2015
 *@Project CST316
 */

/**
 * RoomImpl implements methods for operating on a Room
 */
public class RoomImpl  {
	private String name, lowerBound, upperBound;
	private static Hashtable<String, Room> roomList = new Hashtable<String, Room>();

	/**
	 * reads from the file of rooms and adds them to the HashTable
	 * @throws Exception 
	 */
	public RoomImpl() throws Exception {
	try {
		BufferedReader fin = new BufferedReader(new FileReader(new File(
				"roomList.txt")));
		String line;
		String[] roomInfo;
		Room newRoom;
		while ((line = fin.readLine()) != null) {
			roomInfo = line.split("\\s");

			name = roomInfo[0];
			lowerBound = roomInfo[1];
			upperBound = roomInfo[2];

			newRoom = new Room(name, lowerBound, upperBound);
			roomList.put(name, newRoom);
		} // end while loop building list of rooms

		fin.close();
	} catch (Exception e) {
		throw e;
	} // end catch
} // end constructor

/**
 * searches list for room
 * @param name name of room
 * @return room object if found
 */
	public static Room getRoom(String name) {
		if (roomList.containsKey(name)) {
			return roomList.get(name);
		} // end if

		return null;
	}
/**
 * creates ArrayList of room names from hashtable
 * @return ArrayList of room names
 */
	public static ArrayList<String> getroomList() {
		return Collections.list(roomList.keys());
	}
	
/**
 *locates room and removes it from hashtable 
 * @param name name of room to  remove
 */
	public static void removeRoom(String name) {
		if (roomList.containsKey(name)) {
			roomList.remove(name);
			System.out.println("Removed: " + name + " from the room List.");
		} // end if
	}
	
	/**
	 *creates new room object with the name, upper, and lower boundary for temperature reading
	 * @param name name of room to add to hashtable
	 * @param lowerBound lower boundary for temperature to be set
	 * @param upperBound upper boundary of temperature to be set
	 */
	public static void addRoom(String name, String lowerBound, String upperBound) {
		if (!roomList.containsKey(name)) {
			Room newRoom = new Room(name, lowerBound, upperBound);
			roomList.put(name, newRoom);
			System.out.println("Added: " + name + " to the room List.");
		} // end if
	}

}
