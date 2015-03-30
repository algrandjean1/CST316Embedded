/**
 * AirAutomation project to monitor indoor air quality.
 */

package airUI.pkg;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Properties;

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
public class Room implements IDataReceiveListener
{
	private XBeeHandler xbeeHandler;
	private RemoteXBeeDevice dragon;
	private Properties roomProps, userProps;
	private String tempThresholdLow, tempThresholdHigh, humidityThresholdLow, humidityThresholdHigh, carbonDioxideThreshold, methaneThreshold;
	private String temperature, humidity, carbonDioxide, methane;
	private String name, lowerBound, upperBound;
	private static Hashtable<String, Room> roomList = new Hashtable<String, Room>();
	
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public void setCarbonDioxide(String carbonDioxide) {
		this.carbonDioxide = carbonDioxide;
	}

	public void setMethane(String methane) {
		this.methane = methane;
	}


	/**
	 *constructor for new Room
	 * @param name name of new room
	 * @param lowerBound lower boundary of temperature to be set
	 * @param upperBound upper boundary of temperature to be set
	 */
	private Room(String name, String lowerBound, String upperBound) {
		try {
			// initialize user properties from default
			Properties userProps = new Properties(roomProps);

			this.name = name;
			this.lowerBound = lowerBound;
			this.upperBound = upperBound;

			// reads in threshold values to base the automation of events
			// default settings for a room
			try {
				this.roomProps = new Properties();
				FileInputStream in = new FileInputStream("airAutomation/room.properties");
				roomProps.load(in);
				in.close();

				this.tempThresholdLow = roomProps.getProperty("tempThresholdLow");
				this.tempThresholdHigh = roomProps.getProperty("tempThresholdHigh");
				this.humidityThresholdLow = roomProps.getProperty("humidityThresholdLow");
				this.humidityThresholdHigh = roomProps.getProperty("humidityThresholdHigh");
				this.carbonDioxideThreshold = roomProps.getProperty("carbonDioxideThreshold");
				this.methaneThreshold = roomProps.getProperty("methaneThreshold");

			} catch(IOException ioex) {
				ioex.printStackTrace();
			}

			// load properties from last invocation
			FileInputStream in = new FileInputStream("airAutomation/user.properties");
			userProps.load(in);
			in.close();

			// set the properties from user input
			userProps.setProperty("roomName", name);
			userProps.setProperty("tempThresholdLow", lowerBound);
			userProps.setProperty("tempThresholdHigh", upperBound);

			// reads in last values stored, to be overridden by serial data from XBee
			this.temperature = userProps.getProperty("temperature");
			System.out.println("Temperature: " + temperature);;
			this.humidity = userProps.getProperty("humidity");
			System.out.println("Humidity: " + humidity);;
			this.carbonDioxide = userProps.getProperty("carbonDioxide");
			System.out.println("Carbon Dioxide: " + carbonDioxide);;
			this.methane = userProps.getProperty("methane");
			System.out.println("Methane: " + methane);;

			// write user settings to properties file if they change
			FileOutputStream out = new FileOutputStream("user.properties");
			userProps.store(out, "User settings saved");
			out.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	} // end constructor

	/**
	 *creates new room object with the name, upper, and lower boundary for temperature reading
	 * @param name name of room to add to hashtable
	 * @param lowerBound lower boundary for temperature to be set
	 * @param upperBound upper boundary of temperature to be set
	 */
	public static Room createRoom(String name, String lowerBound, String upperBound)
	{
		Room newRoom;

		if (roomList.containsKey(name))
		{
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
	IDataReceiveListener dataReceiveListener = Room.getRoom("");

	public void dataReceived(XBeeMessage xbeeMessage) {
		try {
			dragon = xbeeHandler.getXbeeNetwork().getDevice("DRAGON");
			xbeeHandler.getXbee().addDataListener(dataReceiveListener);
			XBee64BitAddress priorDestination = dragon.getDestinationAddress();
			String line;
			String[] sensorData;

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
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * searches list for room
	 * @param name name of room
	 * @return room object if found
	 */
	public static Room getRoom(String name)
	{
		if (roomList.containsKey(name))
		{
			return roomList.get(name);
		} // end if

		return null;
	}
	/**
	 * creates ArrayList of room names from hashtable
	 * @return ArrayList of room names
	 */
	public static ArrayList<String> getroomList()
	{
		return Collections.list(roomList.keys());
	}

	/**
	 * locates the room specified and removes it
	 * @param name the name of the room to remove
	 * @return true if removed, false otherwise
	 */
	public static Boolean removeRoom(String name)
	{
		if (roomList.containsKey(name))
		{
			roomList.remove(name);
			System.out.println("Removed: " + name + " from the room List.");

			return true;
		} // end if

		return false;
	}

	public static int getSize()
	{
		return roomList.size();
	}

	/**
	 * @return the lowerBound
	 */
	public String getLowerBound() {
		return lowerBound;
	}

	/**
	 * @return the upperBound
	 */
	public String getUpperBound() {
		return upperBound;
	}

	/**
	 * @return the temperature
	 */
	public String getTemperature() {
		return temperature;
	}

	/**
	 * @return the humidity
	 */
	public String getHumidity()
	{
		return humidity;
	}

	/**
	 * @return the carbonDioxide
	 */
	public String getCarbonDioxide()
	{
		return carbonDioxide;
	}

	/**
	 * @return the methane
	 */
	public String getMethane()
	{
		return methane;
	}


	public static void main(String[] args) throws Exception {
		/*
		try {
			Room room = createRoom("bryan", "65", "85");
			System.out.println("Room value: " + room.toString());

		} catch(Exception e) {
			e.printStackTrace();
		} 
		*/
	}

}
