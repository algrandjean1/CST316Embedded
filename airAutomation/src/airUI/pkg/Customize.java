package airUI.pkg;


//layout for comments

/*
 *****************************************************************************************************************
 *
 *****************************************************************************************************************
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Customize implements ActionListener, ItemListener, ChangeListener
{
	Date time = new Date();
	SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
	
	Properties props;
	Room newRoom;
    
	int TEMPRANGE = 27;
	int TIMERANGE = 49;
    
	ArrayList<String> tempRange = new ArrayList<String>();
	ArrayList<String> tempRange2 = new ArrayList<String>();
	ArrayList<String> timeRange = new ArrayList<String>();
	ArrayList<String> timeRange2 = new ArrayList<String>();
    
	protected JFrame mainWin;
	protected JPanel mainPan;
    
	protected JLabel rLabel;
	protected JLabel Temp;
	protected JLabel To;
	protected JLabel To2;
	protected JLabel SleepSch;
	protected JLabel RPre;
	protected JLabel newR;
    
	protected JTextField roomName;
    
	protected JSpinner lowTemp;
	protected JSpinner highTemp;
	protected JSpinner lowTime;
	protected JSpinner highTime;
    
	protected SpinnerListModel tempModel;
	protected SpinnerListModel tempModel2;
	protected SpinnerListModel timeModel;
	protected SpinnerListModel timeModel2;
    
	protected JComboBox<String> roomBox;
	protected JButton addModRooms;
	protected JButton backButton;
	
	protected MainDriver driver;
	protected Room r;
    
	public Customize(MainDriver driver)
	{
		this.driver = driver;
		
		mainWin = new JFrame("Customize");
		mainPan = new JPanel();
        
		Temp = new JLabel("Temp: ");
		rLabel = new JLabel("Rooms: ");
		To = new JLabel(" to ");
		To2 = new JLabel(" to ");
		SleepSch = new JLabel("Sleep Schedule: ");
		RPre = new JLabel("Room's Preset: ");
		newR = new JLabel("New Room Name: ");
        
		roomName = new JTextField();
        
		addModRooms = new JButton("Add/Modify");
		backButton = new JButton("Back");
        
		lowTime = new JSpinner();
		highTime = new JSpinner();
		lowTemp = new JSpinner();
		highTemp = new JSpinner();
		
		
		//roomModel = new DefaultComboBoxModel(roomList);
		roomBox = new JComboBox<String>();
        
		//this is to fill in for the Temperature settings range
		int start = 60;
		tempRange.add("None");
		tempRange2.add("None");
		for(int i = 1; i < TEMPRANGE; i++)
		{
			tempRange.add(Integer.toString(start));
			tempRange2.add(Integer.toString(start));
			start++;
		}
		
		
		tempModel = new SpinnerListModel(tempRange);
		tempModel = new SpinnerListModel(tempRange2);
		
        
		String am = "AM";
		String pm = "PM";
		int k = 1;
		String odd = "00";
		String even = "30";
		timeRange.add("0:00");
		timeRange2.add("0:00");
		for(int i = 1; i < TIMERANGE; i++)
		{
			if(i % 2 == 0)
			{
				if(k > 12)
				{
					timeRange.add(Integer.toString(k % 12) + ":" + even + pm);
					timeRange2.add(Integer.toString(k % 12) + ":" + even + pm);
				}
				else
				{
					timeRange.add(Integer.toString(k) + ":" + even + am);
					timeRange2.add(Integer.toString(k) + ":" + even + am);
				}
				k++;
			}
			else
			{
				if(k > 12)
				{
					timeRange.add(Integer.toString(k % 12) + ":" + odd + pm);
					timeRange2.add(Integer.toString(k % 12) + ":" + odd + pm);
				}
				else
				{
					timeRange.add(Integer.toString(k) + ":" + odd + am);
					timeRange2.add(Integer.toString(k) + ":" + odd + am);
				}
			}
		}
        
		timeModel = new SpinnerListModel(timeRange);
		timeModel2 = new SpinnerListModel(timeRange2);
		
		mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		layOut();
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
		rLabel.setBounds(10,100,80,30);
		mainPan.add(rLabel);
        
		//the combobox with the Rooms
		roomBox.setBounds(60, 100, 100, 25);
		roomBox.addItemListener(this);
		mainPan.add(roomBox);
        
		//the label "Temp: "
		Temp.setBounds(170,100,60,30);
		mainPan.add(Temp);
        
		//the Spinner on left for temp
		lowTemp.setBounds(220, 100, 120, 30);
		mainPan.add(lowTemp);
        
		//the label " to "
		To.setBounds(350,100,40,30);
		mainPan.add(To);
        
		//the Spinner on the right for temp
		highTemp.setBounds(420,100,120,30);
		mainPan.add(highTemp);
        
		//The label "New Rooms Name: "
		newR.setBounds(170, 150, 120, 30);
		mainPan.add(newR);
        
		//the textfield that is blank for new Rooms
		roomName.setBounds(295,150,120,30);
		mainPan.add(roomName);
        
		//the button add or modify a rooms
		addModRooms.setBounds(430,150,120,30);
		mainPan.add(addModRooms);
		addModRooms.addActionListener(this);
        
        
		//the label "Sleep Schedule: "
		SleepSch.setBounds(170,200, 150, 30);
		mainPan.add(SleepSch);
        
		//the Spinner on the left for time
		lowTime.setBounds(170,250,80,30);
		mainPan.add(lowTime);
        
		//Second label " to "
		To2.setBounds(260,250,40,30);
		mainPan.add(To2);
        
		//the spinner on the right for time
		highTime.setBounds(290,250,80,30);
		mainPan.add(highTime);
        
		//the back button
		backButton.setBounds(300,300,60,30);
		mainPan.add(backButton);
		backButton.addActionListener(driver);
        
	}
    /*
     public static void main(String[] args)
     {
     Customize run = new Customize();
     run.setUp();
     }
     */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == addModRooms)
		{
			String lowTempRead = lowTemp.getModel().toString();
			roomName.setText(lowTempRead);
			addModRoomsButton();
		}
        
	}
    
	public void itemStateChanged(ItemEvent event)
	{
		if(event.getStateChange() == ItemEvent.SELECTED)
		{
			Object compare = event.getSource();
			if(roomBox==compare)
			{
				roomBox.revalidate();
			}
		}
	}
	
	public void stateChanged(ChangeEvent e)
	{
		if (tempModel instanceof SpinnerListModel || tempModel2 instanceof SpinnerListModel)
		{
            correctRange();
        }
	}
	
	
	public void correctRange()
	{
		int lowEnd = Integer.parseInt(lowTemp.getValue().toString());
		int highEnd = Integer.parseInt(highTemp.getValue().toString());
		
		if(lowEnd >= highEnd)
		{
			tempModel2.setValue(tempModel.getNextValue());
		}
		else if(highEnd <= lowEnd)
		{
			tempModel.setValue(tempModel2.getPreviousValue());
		}
	}
	
	public void addModRoomsButton()
	{
		if(roomName.getText() != null)
		{
			String nameOfRoom = roomName.getText();
			String lowEnd = lowTemp.getValue().toString();
			String highEnd = highTemp.getValue().toString();
			newRoom.createRoom(nameOfRoom, lowEnd, highEnd);
			
			int sizeOfListRoom = newRoom.getSize();
			ArrayList<String> roomList = newRoom.getroomList();
			
			if(sizeOfListRoom == roomList.size())
			{
				for(int i = 0; i < sizeOfListRoom;++i)
				{
					roomBox.addItem(roomList.get(i));
				}
			}
		}
		else
		{
			//Need to create room object, then get lowerBound and upperBound
			//to compare to current low and high end to see if modify necessary
			
			String lowEnd = lowTemp.getValue().toString();
			String highEnd = highTemp.getValue().toString();
			String modRoom = roomName.getText();
            
			if(newRoom.removeRoom(modRoom) == true)
			{
				newRoom.createRoom(modRoom, lowEnd, highEnd);
				
			}
			
		}
	}
	public void showcustomize(){
		mainWin.setVisible(true);
	}
    
	public void hidecustomize(){
		mainWin.setVisible(false);
	}
}