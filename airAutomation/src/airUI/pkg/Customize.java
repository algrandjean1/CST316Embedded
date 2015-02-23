package airUI.pkg;

//layout for comments

/*
*****************************************************************************************************************
*  
*****************************************************************************************************************
*/

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Customize implements ActionListener
{
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
	* JButton:
	* 	addRoom - creates button with "Add Room" name
	* 	addPreset - creates button with "Add Preset" name
	* 	addDevices - creates button with "Add Devices" name
	*
	*****************************************************************************************************************
	*/
	
	public Customize()
	{
		
		roomList.add("Default");
		setPreList.add("Default");
		devicesList.add("Default");
		
		mainWin = new JFrame("Customize");
		mainPan = new JPanel();
		
		Rooms = new JComboBox(roomList.toArray());
		setPreset = new JComboBox(setPreList.toArray());
		devices = new JComboBox(devicesList.toArray());
		
		addRoom = new JButton("Add Room");
		addPreset = new JButton("Add Preset");	
		addDevices = new JButton("Add Devices");
		
	}
	
	/*
	*****************************************************************************************************************
	*  Layout - here is where the lay out of the window is laid out. 
	*  it is not using a standard layout that the java library has. It 
	*  is laying it using X, Y coordinates and the size of the component is 
	*  using a width, and height. To do this it is using the "setBounds" method.
	*  the first two numbers in each method are the (X,Y) coordinates. The next
	*  two are the (width, height) of the component. It also add the "actionlistener"
	*  to it read to receive the information.
	*****************************************************************************************************************
	*/
	
	public void layOut()
	{
		mainWin.setSize(600, 600);
		mainWin.add(mainPan);
		
		mainPan.setLayout(null);
		mainPan.setSize(600, 600);
		
		Rooms.setBounds(30, 100, 100, 25);
		mainPan.add(Rooms);
		
		setPreset.setBounds(200, 100, 100, 25);
		mainPan.add(setPreset);
		
		devices.setBounds(370, 100, 100, 30);
		mainPan.add(devices);
		
		
		addRoom.setBounds(30, 220, 100, 40);
		addRoom.addActionListener(this);
		mainPan.add(addRoom);
		
		addPreset.setBounds(170, 220, 130, 40);
		addPreset.addActionListener(this);
		mainPan.add(addPreset);
		
		addDevices.setBounds(370, 220,130, 40);
		addDevices.addActionListener(this);
		mainPan.add(addDevices);
		
	}
	
	/*
	*****************************************************************************************************************
	* The Next Three methods are what the action listener is going to be using to add
	* to the array list. As their names suggest addToRooms adds to the roomList arrayList
	* and etc. As of Sprint 1, they will not be used.
	*****************************************************************************************************************
	*/
	
	public void addToRooms(String aRoom)
	{
		roomList.add(aRoom);
	}
	
	public void addToPresets(String aSet)
	{
		setPreList.add(aSet);
	}
	
	public void addToDevices(String aDevice)
	{
		devicesList.add(aDevice);
	}
	
	/*
	*****************************************************************************************************************
	* Here is the main Function, which is what is needed to call all of the
	* components needed to show the window display. 
	*****************************************************************************************************************
	*/
	
	/*
	public static void main(String[] args)
	{
		Customize run = new Customize();

		run.mainWin.setVisible(true);
		run.mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		run.layOut();

	}
	*/
	
	/*
	*****************************************************************************************************************
	* Here is where the actions are received and does the appropriate thing 
	* according to what is needed. As of Sprint 1, this will have nothing.  
	*****************************************************************************************************************
	*/
	
	public void actionPerformed(ActionEvent event) 
	{
		if(event.equals(addRoom))
		{
			;
		}
		else if(event.equals(addDevices))
		{
			;
		}
		else if(event.equals(addPreset))
		{
			;
		}
	}
}
