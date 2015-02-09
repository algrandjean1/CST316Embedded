package airUI.pkg;

/*
*****************************************************************************************************************
*  
*****************************************************************************************************************
*/

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Customize
{
	String man = "Manuel";

	/*
	*****************************************************************************************************************
	* roomList - the List that will hold the all the rooms that are added to the system from the user
	* setPreList - the list that will hold all the preset that are placed for the home system, from the user 
	*****************************************************************************************************************
	*/
	ArrayList<String> roomList = new ArrayList<String>();
	ArrayList<String> setPreList = new ArrayList<String>();
	ArrayList<String> devicesList = new ArrayList<String>();
	

	/*
	*****************************************************************************************************************
	* The Swing components that will be used are:
	* 
	* JFrame: 
	*	mainWin - the main window that has all the exit, minimize, etc.
	*
	* JPanel: 
	*	mainPan - the window that will be placed on top of the JFrame, with this panel, we will use it to hold
	*             all of the buttons, combobox, JTextField. 
	*
	* JComboBox: 
	*	Rooms - this will be filled with all of the rooms from the roomList arrayList.
	*   setPreset - this will be filled with all of the presets from the setPreList arrayList.
	*   devices - this will filled with all of the devices that are being controlled
	* 
	* JButton:
	* 	addRoom - This will be the button to add the rooms
	* 	addPreset - this will be the button to add Presets
	*   addDevices - this will be the button to add Devices
	* 
	*****************************************************************************************************************
	*/

	JFrame mainWin;
	JPanel mainPan;
	JComboBox Rooms;
	JComboBox setPreset;
	JComboBox devices;
	JButton addRoom;
	JButton addPreset;
	JButton addDevices;
	
	public Customize()
	{
		/*
		*****************************************************************************************************************
		* In the Constructor we just instantiate everything that will be used:
		* 
		* JFrame: 
		*	mainWin - "customize title in the Top right corner"
		*
		* JPanel: 
		*	mainPan - nothing instantiated by default
		*
		* JComboBox: 
		*	Rooms - roomList array list will be used
		*	setPreset - setPreList array list will be used
		*	devices - devicesList array list will be used
		*
		*****************************************************************************************************************
		*/

		mainWin = new JFrame("Customize");
		
		mainPan = new JPanel();
		
		Rooms = new JComboBox(roomList.toArray());
		setPreset = new JComboBox(setPreList.toArray());
		devices = new JComboBox(devicesList.toArray());
		
		addRoom = new JButton("Add Room");
		addPreset = new JButton("Add Preset");	
		addDevices = new JButton("Add Devices");
		
	}
	
	public void layOut()
	{
		mainWin.setSize(600, 600);
		mainWin.add(mainPan);
		
		mainPan.setLayout(null);
		mainPan.setSize(600, 600);
		
		Rooms.setBounds(30, 100, 130, 100);
		mainPan.add(Rooms);
		
		setPreset.setBounds(200, 100, 130,100);
		mainPan.add(setPreset);
		
		devices.setBounds(370, 100, 130, 100);
		mainPan.add(devices);
		
		//
		
		
	}
	
	public static void main(String[] args)
	{
		Customize run = new Customize();

		run.mainWin.setVisible(true);
		run.mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		run.layOut();

	}
}
