/**
 * AirAutomation project to monitor indoor air quality.
 */

package airUI.pkg;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
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
	private Properties roomProps, userProps, props;
	private String tempThresholdLow, tempThresholdHigh, humidityThresholdLow, humidityThresholdHigh, carbonDioxideThreshold, methaneThreshold;
	private String temperature, humidity, carbonDioxide, methane;
	private String name, lowerBound, upperBound;
	private static Hashtable<String, Room> roomList = new Hashtable<String, Room>();
	
	private ArrayList<String> usersReadings = new ArrayList<String>();

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
	/**
	 *constructor for new Room
	 * @param name name of new room
	 * @param lowerBound lower boundary of temperature to be set
	 * @param upperBound upper boundary of temperature to be set
	 */
	private Room(String name, String lowerBound, String upperBound, XBeeHandler xbeeHandler) {
		try {
			// initializes the XBee listener
			this.xbeeHandler = xbeeHandler;
			xbeeHandler.getXbee().addDataListener(this);

			// initialize user properties from default
			userProps = new Properties(roomProps);
			this.name = name;
			this.lowerBound = lowerBound;
			this.upperBound = upperBound;

			// reads in threshold values to base the automation of events
			// default settings for a room
			try {
				//File file = new File("room.properties");
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
			}catch(FileNotFoundException e){
				System.out.println("The property file was not found in the classpath");
			}catch(IOException ioex){
				System.out.println("IOException Error occured while reading from the property file.");
				ioex.printStackTrace();
			}catch(NullPointerException e){
				System.out.println("One of the properties in the property file is missing.");
			}
			
			// load properties from last invocation
			FileInputStream inIt = new FileInputStream("airAutomation/user.properties");
			userProps.load(inIt);
			inIt.close();
			/*
			popUserReadingsArray();
			int sizeOfReadings = usersReadings.size();
			
			for(int i=0;i<sizeOfReadings;i++)
			{
				System.out.print(usersReadings.get(i) + "\n");
				String THCM = getTemperature() + "," +  getHumidity() + "," + getCarbonDioxide() + "," + getMethane();
				System.out.println(THCM);
				userProps.setProperty(usersReadings.get(i),THCM);
			}
			*/
			userProps.setProperty("roomName", name);
			userProps.setProperty("tempThresholdLow", lowerBound);
			userProps.setProperty("tempThresholdHigh", upperBound);
			//userProps.setProperty("Temprature", temperature);
			//userProps.setProperty("Humidity", humidity);
			//userProps.setProperty("CarbonDioxide", carbonDioxide);
			//userProps.setProperty("Methane", methane);
			
			// write user settings to properties file if they change
			FileOutputStream out = new FileOutputStream("user.properties");
			userProps.store(out, "User settings saved");
			out.close();
		}catch(FileNotFoundException e){
			System.out.println("The property file was not found in the classpath");
		}catch(IOException e){
			System.out.println("IOException Error occured while reading from the property file.");
			e.printStackTrace();
		}catch(NullPointerException e){
			System.out.println("One of the properties in the property file is missing.");
		}
	} // end constructor

	/**
	 *creates new room object with the name, upper, and lower boundary for temperature reading
	 * @param name name of room to add to hashtable	} // end constructor

	 * @param lowerBound lower boundary for temperature to be set
	 * @param upperBound upper boundary of temperature to be set
	 */
	public static Room createRoom(String name, String lowerBound, String upperBound, XBeeHandler xbeeHandler) {
		Room newRoom;

		if (roomList.containsKey(name))
		{
			newRoom = roomList.get(name);
		} else {
			newRoom = new Room(name, lowerBound, upperBound, xbeeHandler);
			roomList.put(name, newRoom);
			System.out.println("Added: " + name + " to the room List.");
		} // end if
		return newRoom;
	}

	public void dataReceived(XBeeMessage xbeeMessage) {
		try {
			String line;
			String[] sensorData;

			// in the future this will allow for other rooms to receive
			//if (xbeeMessage.getDevice().get64BitAddress().equals(dragon.get64BitAddress())) {
				System.out.println("Dragon: " + xbeeMessage.getDataString());

				line = xbeeMessage.getDataString();
				while(line != null) {
					sensorData = line.split(":");

					this.temperature = sensorData[0];
					this.humidity = sensorData[1];
					this.carbonDioxide = sensorData[2];
					this.methane = sensorData[3];
				} // end while parsing data

	}
	
	public void popUserReadingsArray()
	{
		FileInputStream inIt;
		props = new Properties();
		try 
		{
			inIt = new FileInputStream("airAutomation/userSettings.properties");
			props.load(inIt);
			inIt.close();
			
			Enumeration e = props.propertyNames();
			
			while(e.hasMoreElements())
			{
				usersReadings.add((String) e.nextElement());
			}
			
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void writeUsers(String temp, String Humid, String CO2, String Me)
	{
		String names;
		String tempHumCOMe = temp + "," + Humid + "," + CO2 + "," + Me;
		
		try
		{
			FileOutputStream out = new FileOutputStream("user.properties");
			userProps.setProperty(name, tempHumCOMe);
			userProps.store(out, "User settings saved");
			out.close();
		}
		catch(IOException e)
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
	 * check if a specific room is in the list
	 * @param name name of room
	 * @return true if it is
	 */
	public static boolean containsRoom(String name)
	{
		return roomList.containsKey(name);
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

	public String getLowerBound(){
		return lowerBound;
	}

	public String getUpperBound(){
		return upperBound;
	}
	
	public String getName()
	{
		return name;
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


}
