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

	//Date time = new Date();
	//SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
	
	int TEMPRANGE = 27;
	int TIMERANGE = 49;
	
	ArrayList<String> roomList = new ArrayList<String>();
	ArrayList<String> setPreList = new ArrayList<String>();
	
	ArrayList<String> tempR = new ArrayList<String>();
	ArrayList<String> tempR2 = new ArrayList<String>();
	ArrayList<String> timeR = new ArrayList<String>();
	ArrayList<String> timeR2 = new ArrayList<String>();

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

	
	JButton addModPre;
	JButton addModRooms;
	JButton back;

	public Customize()
	{
		
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
		
		//this is to fill in for the Temperature settings range
		int start = 60;
		tempR.add("None");
		tempR2.add("None");
		for(int i = 1; i < TEMPRANGE; i++)
		{
			tempR.add(Integer.toString(start));
			tempR2.add(Integer.toString(start));
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
	
	public void layOut()
	{
		//Window
		mainWin.setSize(600, 600);
		mainWin.add(mainPan);
		
		//Panel on the window
		mainPan.setLayout(null);
		mainPan.setSize(600, 600);
		
		//The label "Room: " 
		Room.setBounds(10,100,80,30);
		mainPan.add(Room);
		
		//the combobox with the Rooms
		Rooms.setBounds(60, 100, 100, 25);
		Rooms.addItemListener(this);
		mainPan.add(Rooms);
		
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
		newR.setBounds(170, 210, 120, 30);
		mainPan.add(newR);
		
		//the textfield that is blank for new preset
		newRoom.setBounds(295,210,120,30);
		mainPan.add(newRoom);
		
		//the button add or modify a preset
		addModRooms.setBounds(430,210,120,30);
		mainPan.add(addModRooms);
		addModRooms.addActionListener(this);
		
		//the label "Rooms: "
		//Room.setBounds(10,300,80,30);
		//mainPan.add(Room);
		
		//the combobox with all the rooms
		//Rooms.setBounds(60, 300, 100, 25);
		//Rooms.addItemListener(this);
		//mainPan.add(Rooms);
		
		//The Label "New Room Name: "
		//newR.setBounds(170,350,120,30);
		//mainPan.add(newR);
		
		//the textfield that is blank for new Rooms
		//newRoom.setBounds(295,350,120,30);
		//mainPan.add(newRoom);
		
		//the Label "Room's preset: "
		//RPre.setBounds(430,350,100,30);
		//mainPan.add(RPre);
		
		//the button add or modify a room
		//addModRooms.setBounds(170,390,120,30);
		//mainPan.add(addModRooms);
		//addModRooms.addActionListener(this);
		
		//the preset same as setPreset, but that will be attached to rooms
		//roomPreset.setBounds(430,390,100,25);
		//mainPan.add(roomPreset);
		
		//the back button
		back.setBounds(500,500,60,30);
		mainPan.add(back);
		
	}
	
	public void setUp()
	{
		mainWin.setVisible(true);
		mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		layOut();
	}
	
	public static void main(String[] args)
	{
		Customize run = new Customize();
		run.setUp();
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == addModPre)
		{
			if(newPres.getText() != null)
			{
				setPreset.addItem(newPres.getText());
				roomPreset.addItem(newPres.getText());
			}
			else
			{
				;
			}
		}
		else if(e.getSource() == addModRooms)
		{
			if(newRoom.getText() != null)
			{
				Rooms.addItem(newRoom.getText());
			}
			else
			{
				
				;
			}
		}
		else if(e.getSource() == back)
		{
			;
		}
	}

	public void itemStateChanged(ItemEvent event) 
	{
		;
	}
}
