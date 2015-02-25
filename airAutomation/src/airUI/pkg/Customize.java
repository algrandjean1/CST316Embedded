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
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Customize implements ActionListener, ItemListener
{
	//Date time = new Date();
	//SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
	
	int TEMPRANGE = 21;
	int TIMERANGE = 49;
	
	int start = 60;
	
	
	/*
	*****************************************************************************************************************
	* roomList - the List that will hold the all the rooms that are added to the system from the user
	* setPreList - the list that will hold all the preset that are placed for the home system, from the user 
	*****************************************************************************************************************
	*/
	
	ArrayList<String> roomList = new ArrayList<String>();
	ArrayList<String> setPreList = new ArrayList<String>();
	//ArrayList<String> devicesList = new ArrayList<String>();
	
	int[] tempR = new int[TEMPRANGE];
	String[] timeR = new String[TIMERANGE];
	

	/*
	*****************************************************************************************************************
	* The Swing components that will be used are:
	* 
	* JFrame: 
	*	mainWin - 	the main window that has all the exit, minimize, etc.
	*
	* JPanel: 
	*	mainPan - 	the window that will be placed on top of the JFrame, with this panel, we will use it to hold
	*             	all of the buttons, combobox, JTextField. 
	*             
	* JTextField:
	* 	newPre - 	this will be used to create a new preset with any name user wants
	* 	newRoom - 	this will be used to create a new room with any name the user wants
	*             
	* JLabel:
	* 	Preset: 	"Presets: " is what is looks like
	* 	Rooms: 		"Rooms: "
	* 	Temp: 		"Temp: "
	* 	To: 		" to "
	* 	SleepSch: 	"Sleep Schedule: "
	* 
	* JSpinner:
	* 	lowTemp: 	to be the low preset for user in their preferred temperature range
	* 	highTemp: 	to be the high preset for user in their preferred temperature range
	*	lowTime: 	to be the low preset for user in their preferred time to go to sleep
	*	highTime: 	to be the high preset for user in their preferred time to go to wake up
	*
	* JComboBox: 
	*	Rooms - 	this will be filled with all of the rooms from the roomList arrayList.
	*   setPreset - this will be filled with all of the presets from the setPreList arrayList.
	*   devices - 	this will filled with all of the devices that are being controlled
	*   roomPreset: the presets for a room specifically 
	* 
	* JButton:
	* 	addRoom - 	This will be the button to add the rooms
	* 	addPreset - this will be the button to add Presets
	*   addDevices - this will be the button to add Devices
	*   addModPre - this will be used to either modify or add a new preset
	*   addModRooms - this will be used to either modify or add a new Room
	* 
	*****************************************************************************************************************
	*/

	JFrame mainWin;
	JPanel mainPan;
	
	JLabel Preset;
	JLabel Room;
	JLabel Temp;
	JLabel To;
	JLabel To2;
	JLabel SleepSch;
	
	JTextField newPres;
	JTextField newRoom;
	
	JSpinner lowTemp;
	JSpinner highTemp;
	JSpinner lowTime;
	JSpinner highTime;
	
	JComboBox Rooms;
	JComboBox setPreset;
	JComboBox roomPreset;
	//JComboBox devices;
	
	JButton addModPre;
	JButton addModRooms;
	//JButton addRoom;
	//JButton addPreset;
	//JButton addDevices;
	
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
	*
	* JTextField:
	* 	newPre - 	nothing in it by default
	* 	newRoom - 	nothing in it by default
	*             
	* JLabel:
	* 	Preset: 	"Presets: " 
	* 	Rooms: 		"Rooms: "
	* 	Temp: 		"Temp: "
	* 	To: 		" to "
	* 	SleepSch: 	"Sleep Schedule: "
	* 
	* JSpinner:
	* 	lowTemp: 	nothing in it by default
	* 	highTemp: 	nothing in it by default
	*	lowTime: 	nothing in it by default
	*	highTime: 	nothing in it by default
	*
	* JComboBox: 
	*	Rooms - 	roomList arraylist
	*   setPreset - setPreList arraylist
	*   devices - 	this will filled with all of the devices that are being controlled
	*   roomPreset: preset attached to a room 
	* 
	* JButton:
	* 	addRoom - 	This will be the button to add the rooms
	* 	addPreset - this will be the button to add Presets
	*   addDevices - this will be the button to add Devices
	*   addModPre - "add/modify"
	*   addModRooms - "add/modify"
	*****************************************************************************************************************
	*/
	
	public Customize()
	{
		
		roomList.add("Default");
		setPreList.add("Default");
		//devicesList.add("Default");
		
		mainWin = new JFrame("Customize");
		mainPan = new JPanel();
		
		Preset = new JLabel("Preset: ");
		Temp = new JLabel("Temp: ");
		Room = new JLabel("Rooms: ");
		To = new JLabel(" to ");
		To2 = new JLabel(" to ");
		SleepSch = new JLabel("Sleep Schedule: ");
		
		newPres = new JTextField();
		newRoom = new JTextField();
		
		addModPre = new JButton("Add/Modify");
		addModRooms = new JButton("Add/Modify");
		
		lowTemp = new JSpinner();
		highTemp = new JSpinner();
		lowTime = new JSpinner();
		highTime = new JSpinner();
		
		Rooms = new JComboBox(roomList.toArray());
		setPreset = new JComboBox(setPreList.toArray());
		roomPreset = new JComboBox(setPreList.toArray());
		//devices = new JComboBox(devicesList.toArray());
		
		//addRoom = new JButton("Add Room");
		//addPreset = new JButton("Add Preset");	
		//addDevices = new JButton("Add Devices");
		
		
		
		//this is to fill in for the Temperature settings
		for(int i = 0; i < TEMPRANGE-1; i++)
		{
			tempR[i] = start;
			start++;
		}
		
		
		/*
		*****************************************************************************************************************
		*  This will be use to fill in the String array with all the times that will be available for the sleep
		*  schedule 
		*****************************************************************************************************************
		*/
		String ampm = "AM";
		int k = 1;
		String odd = "00";
		String even = "30";
		timeR[0] = "0:00";
		for(int i = 1; i < TIMERANGE; i++)
		{
			if(i % 2 == 0)
			{
				;
			}
		}
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
		//Window
		mainWin.setSize(600, 600);
		mainWin.add(mainPan);
		
		//Panel on the window
		mainPan.setLayout(null);
		mainPan.setSize(600, 600);
		
		//The label "Preset: " 
		Preset.setBounds(10,100,80,30);
		mainPan.add(Preset);
		
		//the combobox with the Presets
		setPreset.setBounds(60, 100, 100, 25);
		setPreset.addItemListener(this);
		mainPan.add(setPreset);
		
		//the label "Temp: "
		Temp.setBounds(170,100,60,30);
		mainPan.add(Temp);
		
		//the Spinner on left for temp
		lowTemp.setBounds(220, 100, 80, 30);
		mainPan.add(lowTemp);
		
		//the label " to "
		To.setBounds(310,100,40,30);
		mainPan.add(To);
		
		//the Spinner on the right for temp
		highTemp.setBounds(350,100,80,30);
		mainPan.add(highTemp);
		
		//the label "Sleep Schedule: "
		SleepSch.setBounds(170, 140, 150, 30);
		mainPan.add(SleepSch);
		
		//the Spinner on the left for time
		lowTime.setBounds(170,170,80,30);
		mainPan.add(lowTime);
		
		//Second label " to "
		To2.setBounds(260,170,40,30);
		mainPan.add(To2);
		
		//the spinner on the right for time
		highTime.setBounds(290,170,80,30);
		mainPan.add(highTime);
		
		//the textfield that is blank for new preset
		newPres.setBounds(170,210,120,30);
		mainPan.add(newPres);
		
		//the button add or modify a preset
		addModPre.setBounds(300,210,120,30);
		mainPan.add(addModPre);
		addModPre.addActionListener(this);
		
		//the label "Rooms: "
		Room.setBounds(10,300,80,30);
		mainPan.add(Room);
		
		//the combobox with all the rooms
		Rooms.setBounds(60, 300, 100, 25);
		Rooms.addItemListener(this);
		mainPan.add(Rooms);
		
		//the textfield that is blank for new Rooms
		newRoom.setBounds(170,350,120,30);
		mainPan.add(newRoom);
		
		//the preset same as setPreset, but that will be attached to rooms
		roomPreset.setBounds(300,350,100,25);
		mainPan.add(roomPreset);
		
		//the button add or modify a room
		addModRooms.setBounds(410,350,120,30);
		mainPan.add(addModRooms);
		
		
		//devices.setBounds(370, 100, 100, 30);
		//mainPan.add(devices);
		
		//addRoom.setBounds(30, 220, 100, 40);
		//addRoom.addActionListener(this);
		//mainPan.add(addRoom);
		
		//addPreset.setBounds(170, 220, 130, 40);
		//addPreset.addActionListener(this);
		//mainPan.add(addPreset);
		
		//addDevices.setBounds(370, 220,130, 40);
		//addDevices.addActionListener(this);
		//mainPan.add(addDevices);
		
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
	
	/*public void addToDevices(String aDevice)
	{
		devicesList.add(aDevice);
	}*/
	
	/*
	*****************************************************************************************************************
	* Here is the main Function, which is what is needed to call all of the
	* components needed to show the window display. 
	*****************************************************************************************************************
	*/
	
	
	public static void main(String[] args)
	{
		Customize run = new Customize();

		run.mainWin.setVisible(true);
		run.mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		run.layOut();

	}
	
	
	/*
	*****************************************************************************************************************
	* Here is where the actions are received and does the appropriate thing 
	* according to what is needed. As of Sprint 1, this will have nothing.  
	*****************************************************************************************************************
	*/
	
	public void actionPerformed(ActionEvent e) 
	{
		;
	}

	/*
	*****************************************************************************************************************
	*  Here is where it update the info when an item is selected from the combo boxes
	*  as of Sprint 1, this will have nothing
	*****************************************************************************************************************
	*/
	public void itemStateChanged(ItemEvent event) 
	{
		;
	}
}
