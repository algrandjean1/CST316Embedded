/**
 * AirAutomation project to monitor indoor air quality.
 */

package airUI.pkg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.listeners.IDataReceiveListener;
import com.digi.xbee.api.models.XBee64BitAddress;
import com.digi.xbee.api.models.XBeeMessage;


/**
 * @author BMosAir
 * @Version Spring 2015
 * @Project CST316
 */

/**
 * Room object with sensor data
 */
public class Room implements IDataReceiveListener {
	private XBeeHandler xbeeHandler;
	private RemoteXBeeDevice dragon;	
	private String name, humidity, carbonDioxide, methane;
	private String temperature, lowerBound, upperBound;
	private static Hashtable<String, Room> roomList = new Hashtable<String, Room>();

	/**
	 *constructor for new Room 
	 * @param name name of new room
	 * @param lowerBound lower boundary of temperature to be set
	 * @param upperBound upper boundary of temperature to be set
	 */
	private Room(String name, String lowerBound, String upperBound) {
		this.name = name;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		
	}

	/**
	 *creates new room object with the name, upper, and lower boundary for temperature reading
	 * @param name name of room to add to hashtable
	 * @param lowerBound lower boundary for temperature to be set
	 * @param upperBound upper boundary of temperature to be set
	 */
	public static Room createRoom(String name, String lowerBound, String upperBound) {
		Room newRoom;

		if (roomList.containsKey(name)) {
			newRoom = roomList.get(name);
		} else {
			newRoom = new Room(name, lowerBound, upperBound);
			roomList.put(name, newRoom);
			System.out.println("Added: " + name + " to the room List.");
		} // end if
		return newRoom;
	}

	/**
	 * IDataReceiveListener is an interface for setting up a listener
	 * @method dataReceived: access the data being transmitted from XBee
	 */
	IDataReceiveListener dataReceiveListener = new IDataReceiveListener()
	{
		public void dataReceived(XBeeMessage xbeeMessage) {}
	};

	/**
	 * sets up the listener and updates the Rooms readings
	 * @param xbeeMessage is the data message received from the remote XBee device
	 */
	public void dataReceived(XBeeMessage xbeeMessage) {
		try {
		 dragon = xbeeHandler.getXbeeNetwork().getDevice("DRAGON");
			String line;
			String[] sensorData;
			xbeeHandler.getXbee().addDataListener(dataReceiveListener);
			XBee64BitAddress priorDestination = dragon.getDestinationAddress();
			if (xbeeMessage.getDevice().get64BitAddress().equals(dragon.get64BitAddress())) {
				System.out.println("Dragon: " + xbeeMessage.getDataString());

				line = xbeeMessage.getDataString();
				while(line != null) {
					sensorData = line.split("\\s	");

					this.temperature = sensorData[0];
					this.humidity = sensorData[1];
					this.carbonDioxide = sensorData[2];
					this.methane = sensorData[3];
				} // end while parsing data
			} // end if

			xbeeHandler.getXbee().removeDataListener(dataReceiveListener);
			dragon.setDestinationAddress(priorDestination);
			xbeeHandler.getXbee().close();
		} catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println("Settings saved");
	}

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
	 * locates the rom specified and removes it
	 * @param name the name of the room to remove
	 * @return true if removed, false otherwise
	 */
	public static Boolean removeRoom(String name) {
		if (roomList.containsKey(name)) {
			roomList.remove(name);
			System.out.println("Removed: " + name + " from the room List.");

			return true;
		} // end if

		return false;
	}

	public static int getSize() {
		return roomList.size();
	}
	
	/**
	 * @return the humidity
	 */
	public String getHumidity() {
		return humidity;
	}

	/**
	 * @return the carbonDioxide
	 */
	public String getCarbonDioxide() {
		return carbonDioxide;
	}

	/**
	 * @return the methane
	 */
	public String getMethane() {
		return methane;
	}


	/*
	public static void main(String[] args) throws Exception {
		try {
		Room room = createRoom("bryan", "65", "85");
		System.out.println("Room value: " + room.toString());

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	*/

}
