package airUI.pkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Customize implements ActionListener, ItemListener, ChangeListener
{
	//Date time = new Date();
	//SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

	Properties props;
	Room newRoom;

	int CURRENTTEMP = 60;
	int CURRENTTEMP2 = 61;
	int LOWRANGE = 60;
	int HIGHRANGE = 85;
	int STEPS = 1;
	//int TIMERANGE = 49;

	//ArrayList<String> timeRange = new ArrayList<String>();
	//ArrayList<String> timeRange2 = new ArrayList<String>();
	ArrayList<Room> keys = new ArrayList<Room>();

	protected JFrame mainWin;
	protected JPanel mainPan;

	protected JLabel rLabel;
	protected JLabel Temp;
	protected JLabel To;
	//protected JLabel To2;
	//protected JLabel SleepSch;
	protected JLabel RPre;
	protected JLabel newR;

	protected JTextField roomName;
	protected JSpinner lowTemp;
	protected JSpinner highTemp;
	protected JSpinner lowTime;
	protected JSpinner highTime;

	protected SpinnerNumberModel tempModel;
	protected SpinnerNumberModel tempModel2;

	//protected SpinnerListModel timeModel;
	//protected SpinnerListModel timeModel2;
	protected JComboBox roomBox;
	protected JButton addModRooms;
	
	protected JButton backButton;
	protected JButton saveButton;

	protected MainDriver driver;
	protected Room r;
	
	public Customize(MainDriver driver)
	{
		this.driver = driver;

		mainWin = new JFrame("Customize");
		mainPan = new JPanel();

		Temp = new JLabel("Temperature: ");
		rLabel = new JLabel("Rooms: ");
		To = new JLabel(" to ");
		//To2 = new JLabel(" to ");
		//SleepSch = new JLabel("Sleep Schedule: ");
		RPre = new JLabel("Room's Preset: ");
		newR = new JLabel("New Room Name: ");

		roomName = new JTextField();

		tempModel = new SpinnerNumberModel(CURRENTTEMP, LOWRANGE, HIGHRANGE, STEPS);
		tempModel2 = new SpinnerNumberModel(CURRENTTEMP2, LOWRANGE, HIGHRANGE, STEPS);

		lowTemp = new JSpinner(tempModel);
		highTemp = new JSpinner(tempModel2);

		roomBox = new JComboBox(keys.toArray());

		addModRooms = new JButton("Add/Modify");
		backButton = new JButton("Back");
		saveButton = new JButton("Save");

		/*
		//This is used to fill in the time
		int k = 1;
		String am = "AM";
		String pm = "PM";
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

		lowTime = new JSpinner(timeModel);
		highTime = new JSpinner(timeModel2);
		*/
		
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

		//the label "Temperature: "
		Temp.setBounds(170,100,90,30);
		mainPan.add(Temp);

		//the Spinner on left for temp
		lowTemp.setBounds(250, 100, 120, 30);
		mainPan.add(lowTemp);

		//the label " to "
		To.setBounds(375,100,25,30);
		mainPan.add(To);

		//the Spinner on the right for temp
		highTemp.setBounds(405,100,120,30);
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
		
		/*
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
		*/
		
		//the back button
		backButton.setBounds(300,300,60,30);
		mainPan.add(backButton);
		backButton.addActionListener(driver);
		
		//the save button
		saveButton.setBounds(230,300,60,30);
		mainPan.add(saveButton);
		saveButton.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e)
	{
		//System.out.println("Action Performed");
		if(e.getSource() == addModRooms)
		{
			String nameOfRoom = roomName.getText();
			String lowEnd = lowTemp.getValue().toString();
			String highEnd = highTemp.getValue().toString();

			addModRoomsButton(nameOfRoom, lowEnd, highEnd);
		}
		else if(e.getSource() == saveButton)
		{
			//have all the user's settings written to be saved;
		}

	}

	public void itemStateChanged(ItemEvent event)
	{
		//System.out.println("item state change");
		if(event.getStateChange() == ItemEvent.SELECTED)
		{
			Object compare = event.getSource();
			if(roomBox==compare)
			{
				String keyToget = roomBox.getSelectedItem().toString();
				int setLow = Integer.parseInt(newRoom.getRoom(keyToget).getLowerBound());
				int setHigh = Integer.parseInt(newRoom.getRoom(keyToget).getUpperBound());
				setRoomValues(keyToget, setLow, setHigh);
				roomBox.revalidate();
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		//System.out.println("State change");
		String name = roomName.getText();
		int lowEnd = Integer.parseInt(lowTemp.getValue().toString());
		int highEnd = Integer.parseInt(highTemp.getValue().toString());

		if (tempModel instanceof SpinnerNumberModel || tempModel2 instanceof SpinnerNumberModel)
		{
			//System.out.println("go inside state change");
            correctRange(name, lowEnd, highEnd);
        }
	}


	public boolean correctRange(String N, int L, int H)
	{
		boolean corrected = false;
		System.out.println("Correct Range");
		if(L >= H || H <= L)
		{
			System.out.println("Has been Corrected");
			setRoomValues(N,H,L);
			corrected = true;
		}
		return corrected;
	}

	public boolean addModRoomsButton(String N, String L, String H)
	{
		boolean added = false;
		System.out.println("Add Modify Room Buttons");
		
		String n = N;
		String l = L;
		String h = H;

		int low = Integer.parseInt(l);
		int high = Integer.parseInt(h);
	
		if(correctRange(N,low,high))
		{
			addModRoomsButton(n,h,l);
		}

		if(N.equals("") || N.equals(" "))
		{
			System.out.println("Blank");
			boolean comapre = false;

			String modRoom = roomBox.getSelectedItem().toString();
			String lowCompare = newRoom.getRoom(modRoom).getLowerBound();
			String highCompare = newRoom.getRoom(modRoom).getUpperBound();
			String lowEnd = lowTemp.getValue().toString();
			String highEnd = highTemp.getValue().toString();

			if(lowCompare.equalsIgnoreCase(lowEnd) && highCompare.equalsIgnoreCase(highEnd))
			{
				added = false;
			}
			else
			{
				comapre = newRoom.removeRoom(modRoom);
				if(comapre == true)
				{
					correctRange(N,low,high);
					newRoom.createRoom(modRoom, lowEnd, highEnd);
					added = true;
					roomBox.revalidate();
				}
				else
				{
					added = false;
				}
			}
		}
		else
		{
			System.out.println("text not null");
			if(newRoom.getRoom(N) == null)
			{
				keys.add(newRoom.createRoom(N, L, H));
				roomBox.revalidate();
				added = true;
			}
			else if(roomName.getText().equalsIgnoreCase(N))
			{
				addModRoomsButton("",L, H);
			}
		}
		return added;
	}

	public void setRoomValues(String na, int lo, int hi)
	{
		System.out.println("Setting all room values");
		roomName.setText(na);
		tempModel.setValue(lo);
		tempModel2.setValue(hi);
	}

	
	public void readUserSettings()
	{
		try
		{
			FileInputStream inIt = new FileInputStream("airAutomation/user.properties");
			props.load(inIt);
			inIt.close();
		
		
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void writeUserSettings(String na, String lb, String ub)
	{
		try
		{
			FileOutputStream out = new FileOutputStream("user.properties");
			String nameToSet = na;
			String lowAndHigh = lb + "," + ub;
			
			props.setProperty(nameToSet, lowAndHigh);
			
			props.store(out, "User settings saved");
			out.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void populateRoomBox(ArrayList<Room> toPop)
	{
		int toPopSize = toPop.size();
		removeAllItems(toPop);
		String name;
		for(int i = 0; i < toPopSize; i++)
		{
			name = toPop.get(i).getName();
			roomBox.addItem(name);
		}
	}
	
	public void removeAllItems(ArrayList<Room> rm)
	{
		int Size = rm.size();
		for(int i = 0; i < Size; i++)
		{
			rm.remove(i);
		}
	}
	
	public void modifyKeys(ArrayList<Room> mv, String toModName)
	{
		Room temp;
		int SIZE = mv.size();
		for(int i = 0; i < SIZE; i++)
		{
			temp = mv.get(i);
			String name = temp.getName();
			if(name.equalsIgnoreCase(toModName))
			{
				//remove whole room 
				//then add the new room
				;
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
