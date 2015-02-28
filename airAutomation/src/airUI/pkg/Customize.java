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
import javax.swing.SpinnerListModel;

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
	/*
	*****************************************************************************************************************
	* this will be used to compare the time that the user selected to be there sleep schedule and with time the
	* actual time of the day, and appropriately turn off or on any device
	* we will use the SimpleDateFormat class to make it only output the hour, minutes and am pm since that is all
	* we will need.
	*****************************************************************************************************************
	*/
	//Date time = new Date();
	//SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
	
	int TEMPRANGE = 21;
	int TIMERANGE = 49;
	
	
	/*
	*****************************************************************************************************************
	* roomList - the List that will hold the all the rooms that are added to the system from the user
	* setPreList - the list that will hold all the preset that are placed for the home system, from the user 
	* the tempR will be used to have preset range for the temperature
	* the timeR will be used to have preset times that will be used to 
	*****************************************************************************************************************
	*/
	
	ArrayList<String> roomList = new ArrayList<String>();
	ArrayList<String> setPreList = new ArrayList<String>();
	//ArrayList<String> devicesList = new ArrayList<String>();
	
	ArrayList<Integer> tempR = new ArrayList<Integer>();
	ArrayList<Integer> tempR2 = new ArrayList<Integer>();
	ArrayList<String> timeR = new ArrayList<String>();
	ArrayList<String> timeR2 = new ArrayList<String>();
	
	//int[] tempR = new int[TEMPRANGE];
	//String[] timeR = new String[TIMERANGE];
	

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
	*   Rpre: 		"Room's Preset: "
	*   newP:		"New Preset Name: "
	*   newR: 		"New Room Name: "
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
	*   back - this will be used to go back to the Main Page.
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
	JLabel RPre;
	JLabel newP;
	JLabel newR;
	
	JTextField newPres;
	JTextField newRoom;
	
	JSpinner lowTemp;
	JSpinner highTemp;
	JSpinner lowTime;
	JSpinner highTime;
	
	SpinnerListModel tempModel;
	SpinnerListModel tempModel2;
	SpinnerListModel timeModel;
	SpinnerListModel timeModel2;
	
	JComboBox Rooms;
	JComboBox setPreset;
	JComboBox roomPreset;
	//JComboBox devices;
	
	JButton addModPre;
	JButton addModRooms;
	JButton back;
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
	* 	RPre: 		"Room's Preset: "
	* 	newP:		"New Preset Name: "
	*   newR: 		"New Room Name: "
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
	*   back - "back"
	*****************************************************************************************************************
	*/
	
	public Customize()
	{
		
		roomList.add("Default");
		roomList.add("Living Room");
		roomList.add("Kitchen");
		roomList.add("Master Bedroom");
		roomList.add("Children's Room");
		
		setPreList.add("Default");
		setPreList.add("Winter");
		setPreList.add("Fall");
		setPreList.add("Spring");
		setPreList.add("Summer");
		//devicesList.add("Default");
		
		mainWin = new JFrame("Customize");
		mainPan = new JPanel();
		
		Preset = new JLabel("Preset: ");
		Temp = new JLabel("Temp: ");
		Room = new JLabel("Rooms: ");
		To = new JLabel(" to ");
		To2 = new JLabel(" to ");
		SleepSch = new JLabel("Sleep Schedule: ");
		RPre = new JLabel("Room's Preset: ");
		newP = new JLabel("New Preset Name: ");
		newR = new JLabel("New Room Name: ");
		
		newPres = new JTextField();
		newRoom = new JTextField();
		
		addModPre = new JButton("Add/Modify");
		addModRooms = new JButton("Add/Modify");
		back = new JButton("Back");
		
		lowTemp = new JSpinner();
		highTemp = new JSpinner();
		lowTime = new JSpinner();
		highTime = new JSpinner();
		
		
		timeModel = new SpinnerListModel();
		
		Rooms = new JComboBox(roomList.toArray());
		setPreset = new JComboBox(setPreList.toArray());
		roomPreset = new JComboBox(setPreList.toArray());
		//devices = new JComboBox(devicesList.toArray());
		
		//addRoom = new JButton("Add Room");
		//addPreset = new JButton("Add Preset");	
		//addDevices = new JButton("Add Devices");
		
		
		
		//this is to fill in for the Temperature settings range
		int start = 60;
		//tempR.add(0);
		for(int i = 1; i < TEMPRANGE; i++)
		{
			tempR.add(start);
			tempR2.add(start);
			start++;
		}
		
		tempModel = new SpinnerListModel(tempR);
		tempModel2 = new SpinnerListModel(tempR2);
		
		/*
		*****************************************************************************************************************
		*  This will be used to fill in the String array with all the times that will be available for the sleep
		*  schedule It is filling in two at a time since the Spinner class that will be using requires that each 
		*  list be separated
		*****************************************************************************************************************
		*/
		String am = "AM";
		String pm = "PM";
		int k = 1;
		String odd = "00";
		String even = "30";
		timeR.add("0:00");
		timeR2.add("0:00");
		for(int i = 1; i < TIMERANGE; i++)
		{	
			if(i % 2 == 0)
			{
				if(k > 12)
				{
					timeR.add(Integer.toString(k % 12) + ":" + even + pm);
					timeR2.add(Integer.toString(k % 12) + ":" + even + pm);
				}
				else
				{
					timeR.add(Integer.toString(k) + ":" + even + am);
					timeR2.add(Integer.toString(k) + ":" + even + am); 
				}
				k++;
			}
			else
			{
				if(k > 12)
				{
					timeR.add(Integer.toString(k % 12) + ":" + odd + pm);
					timeR2.add(Integer.toString(k % 12) + ":" + odd + pm);
				}
				else
				{
					timeR.add(Integer.toString(k) + ":" + odd + am);
					timeR2.add(Integer.toString(k) + ":" + odd + am);
				}
			}
		}
		
		timeModel = new SpinnerListModel(timeR);
		timeModel2 = new SpinnerListModel(timeR2);
		
		//layOut();
	}
	
	/*
	*****************************************************************************************************************
	*  Layout - here is where the lay out of the window is laid out. 
	*  it is not using a standard layout that the java library has. It 
	*  is laying it using X, Y coordinates and the size of the component is 
	*  using a width, and height. To do this it is using the "setBounds" method.
	*  the first two numbers in each method are the (X,Y) coordinates. The next
	*  two are the (width, height) of the component. It also add the "actionlistener"
	*  to it read to receive the information for the buttons, and it adds an "ItemListener" 
	*  for the comboboxs so whenever a selected item is change it will update the text areas.
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
		lowTemp.setBounds(220, 100, 120, 30);
		lowTemp.setModel(tempModel);
		mainPan.add(lowTemp);
		
		//the label " to "
		To.setBounds(345,100,40,30);
		mainPan.add(To);
		
		//the Spinner on the right for temp
		highTemp.setBounds(390,100,120,30);
		highTemp.setModel(tempModel2);
		mainPan.add(highTemp);
		
		//the label "Sleep Schedule: "
		SleepSch.setBounds(170, 140, 150, 30);
		mainPan.add(SleepSch);
		
		//the Spinner on the left for time
		lowTime.setBounds(170,170,80,30);
		lowTime.setModel(timeModel);
		mainPan.add(lowTime);
		
		//Second label " to "
		To2.setBounds(260,170,40,30);
		mainPan.add(To2);
		
		//the spinner on the right for time
		highTime.setBounds(290,170,80,30);
		highTime.setModel(timeModel2);
		mainPan.add(highTime);
		
		//The label "New Preset Name: "
		newP.setBounds(170, 210, 120, 30);
		mainPan.add(newP);
		
		//the textfield that is blank for new preset
		newPres.setBounds(295,210,120,30);
		mainPan.add(newPres);
		
		//the button add or modify a preset
		addModPre.setBounds(430,210,120,30);
		mainPan.add(addModPre);
		addModPre.addActionListener(this);
		
		//the label "Rooms: "
		Room.setBounds(10,300,80,30);
		mainPan.add(Room);
		
		//the combobox with all the rooms
		Rooms.setBounds(60, 300, 100, 25);
		Rooms.addItemListener(this);
		mainPan.add(Rooms);
		
		//The Label "New Room Name: "
		newR.setBounds(170,350,120,30);
		mainPan.add(newR);
		
		//the textfield that is blank for new Rooms
		newRoom.setBounds(295,350,120,30);
		mainPan.add(newRoom);
		
		//the Label "Room's preset: "
		RPre.setBounds(430,350,100,30);
		mainPan.add(RPre);
		
		//the button add or modify a room
		addModRooms.setBounds(170,390,120,30);
		mainPan.add(addModRooms);
		addModRooms.addActionListener(this);
		
		//the preset same as setPreset, but that will be attached to rooms
		roomPreset.setBounds(430,390,100,25);
		mainPan.add(roomPreset);
		
		//the back button
		back.setBounds(500,500,60,30);
		mainPan.add(back);
		
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
	
	public void setUp()
	{
		mainWin.setVisible(true);
		mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		layOut();
	}
	
	/*
	*****************************************************************************************************************
	* Here is the main Function, which is what is needed to call all of the
	* components needed to show the window display. 
	*****************************************************************************************************************
	*/
	
	
	public static void main(String[] args)
	{
		Customize run = new Customize();
		run.setUp();
	}
	
	
	/*
	*****************************************************************************************************************
	* Here is where the actions are received and does the appropriate thing 
	* according to what is needed. As of Sprint 1, this will have nothing.  
	*****************************************************************************************************************
	*/
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == addModPre)
		{
			if(newPres.getSelectedText() != null)
			{
				addToPresets(newPres.getSelectedText());
			}
			else
			{
				//add current info to modify current preset's data
				;
			}
		}
		else if(e.getSource() == addModRooms)
		{
			if(newRoom.getSelectedText() != null)
			{
				addToRooms(newRoom.getSelectedText());
			}
			else
			{
				//add new preset to currently selected room
				;
			}
		}
		else if(e.getSource() == back)
		{
			;
		}
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
