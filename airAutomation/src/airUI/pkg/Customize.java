package airUI.pkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Customize implements ActionListener, ItemListener, ChangeListener
{
	private XBeeHandler xbeeHandler;
	Properties props;
	Properties roomProps;
	Room newRoom;
	
	int STEPS = 1;
	
	String lowFromProp;
	String highFromProp;

	ArrayList<Room> keys = new ArrayList<Room>();
	ArrayList<String> loadList = new ArrayList<String>();

	protected JFrame mainWin;
	protected JPanel mainPan;

	protected JLabel rLabel;
	protected JLabel Temp;
	protected JLabel To;
	protected JLabel RPre;
	protected JLabel newR;

	protected JTextField roomName;
	protected JTextField removeName;
	
	protected JSpinner lowTemp;
	protected JSpinner highTemp;
	protected JSpinner lowTime;
	protected JSpinner highTime;

	protected SpinnerNumberModel tempModel;
	protected SpinnerNumberModel tempModel2;

	protected JComboBox<String> roomBox;
	protected JButton addModRooms;
	
	protected JButton backButton;
	protected JButton saveButton;
	protected JButton removeButton;

	protected MainDriver driver;
	protected Room r;
	
	public Customize(MainDriver driver)
	{
		this.driver = driver;
		
		this.props = new Properties();
		this.roomProps = new Properties();
		FileInputStream in;
		try 
		{
			in = new FileInputStream("airAutomation/room.properties");
			roomProps.load(in);
			in.close();
			
			lowFromProp = roomProps.getProperty("tempThresholdLow");
			highFromProp = roomProps.getProperty("tempThresholdHigh");
			
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		mainWin = new JFrame("Customize");
		mainPan = new JPanel();

		Temp = new JLabel("Temperature: ");
		rLabel = new JLabel("Rooms: ");
		To = new JLabel(" to ");
		RPre = new JLabel("Room's Preset: ");
		newR = new JLabel("New Room Name: ");

		roomName = new JTextField();
		removeName = new JTextField();
		
		int LOWRANGE = Integer.parseInt(lowFromProp);
		int HIGHRANGE = Integer.parseInt(highFromProp);
		
		int CURRENTTEMP = LOWRANGE;
		int CURRENTTEMP2 = LOWRANGE+1;
		
		tempModel = new SpinnerNumberModel(CURRENTTEMP, LOWRANGE, HIGHRANGE, STEPS);
		tempModel2 = new SpinnerNumberModel(CURRENTTEMP2, LOWRANGE, HIGHRANGE, STEPS);

		lowTemp = new JSpinner(tempModel);
		highTemp = new JSpinner(tempModel2);

		roomBox = new JComboBox(keys.toArray());

		addModRooms = new JButton("Add/Modify");
		backButton = new JButton("Back");
		saveButton = new JButton("Save");
		removeButton = new JButton("remove");
		
		mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		readUserSettings();
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
		
		//the textfield that is blank for rooms to remove
		removeName.setBounds(170,300,120,30);
		mainPan.add(removeName);
		
		//the button to remove rooms
		removeButton.setBounds(295,300,120,30);
		mainPan.add(removeButton);
		removeButton.addActionListener(this);
		
		//the back button
		backButton.setBounds(300,500,60,30);
		mainPan.add(backButton);
		backButton.addActionListener(driver);
		
		//the save button
		saveButton.setBounds(230,500,60,30);
		mainPan.add(saveButton);
		saveButton.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e)
	{
		//System.out.println("Action Performed ");
		if(e.getSource() == addModRooms)
		{
			//System.out.println("addModRooms button");
			String nameOfRoom = roomName.getText();
			String lowEnd = lowTemp.getValue().toString();
			String highEnd = highTemp.getValue().toString();

			populateKeysList(nameOfRoom, lowEnd, highEnd);
		}
		else if(e.getSource() == saveButton)
		{
			//System.out.println("Save Button");
			writeUserSettings(keys);
		}
		else if(e.getSource() == removeButton)
		{
			//System.out.println("remove button");
			String rmItem = removeName.getText();
			rmFromKeysList(rmItem);
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
				//System.out.println("Room Box changed");
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
		//System.out.println("Correct Range");
		if(L >= H || H <= L)
		{
			//System.out.println("Has been Corrected");
			setRoomValues(N,H,L);
			corrected = true;
		}
		return corrected;
	}

	public boolean populateKeysList(String N, String L, String H)
	{
		boolean added = false;
		//System.out.println("Add Modify Room Buttons");
		
		String n = N;
		String l = L;
		String h = H;

		int low = Integer.parseInt(l);
		int high = Integer.parseInt(h);
	
		if(correctRange(N,low,high))
		{
			populateKeysList(n,h,l);
		}

		if(N.equals("") || N.equals(" "))
		{
			//System.out.println("Blank");
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
					//System.out.println("Modify existing file");
					correctRange(N,low,high);
					newRoom.createRoom(modRoom, lowEnd, highEnd, xbeeHandler);
					modifyKeys(keys, modRoom, lowEnd, highEnd);
					populateRoomBox(keys);
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
			//System.out.println("text not null");
			if(!newRoom.containsRoom(N))
			{
				//System.out.println("Create new Room");
				keys.add(newRoom.createRoom(N, L, H, xbeeHandler));
				populateRoomBox(keys);
				roomBox.revalidate();
				added = true;
			}
			else if(roomName.getText().equalsIgnoreCase(N))
			{
				populateKeysList("",L, H);
			}
		}
		return added;
	}

	public void setRoomValues(String na, int lo, int hi)
	{
		//System.out.println("Setting all room values");
		roomName.setText(na);
		tempModel.setValue(lo);
		tempModel2.setValue(hi);
	}


	public void readUserSettings()
	{
		//System.out.println("read user Settings");
		try
		{
			String name;
			String lowandHigh;
			String low;
			String high;
			
			Room loadUsers;
			FileInputStream inIt = new FileInputStream("airAutomation/userSettings.properties");
			props.load(inIt);
			inIt.close();	
			Enumeration keysToLoad = props.propertyNames();
			
			while(keysToLoad.hasMoreElements())
			{
				loadList.add((String) keysToLoad.nextElement());
			}
			
			//System.out.println(loadList.size());
			for(int i=0;i<loadList.size();i++)
			{
				//System.out.println(loadList.get(i));
				name = loadList.get(i);
				lowandHigh = props.getProperty(name);
				String[] splitList = lowandHigh.split(",");
				low = splitList[0];
				high = splitList[1];
				populateKeysList(name,low,high);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void writeUserSettings(ArrayList<Room> From)
	{
		//System.out.println("Writing User Setting");
		try
		{
			FileOutputStream out = new FileOutputStream("airAutomation/userSettings.properties");
			Room getAtt;
			int s = From.size();
			if(s == 0)
			{
				System.out.println("There is nothing to save");
			}
			else
			{
				for(int k=0;k<s;k++)
				{
					getAtt = From.get(k);
					String nameToSet = getAtt.getName();
					String lowAndHigh = getAtt.getLowerBound() + "," + getAtt.getUpperBound();
					props.setProperty(nameToSet, lowAndHigh);
				}	
			}
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
		//System.out.println("Populate Room Box");
		int toPopSize = toPop.size();
		roomBox.removeAllItems();
		String name;
		Room temp;
		for(int i = 0; i < toPopSize; i++)
		{
			temp = toPop.get(i);
			name = temp.getName();
			roomBox.addItem(name);
		}
	}
	
	public void modifyKeys(ArrayList<Room> mv, String toModName, String newLow, String newHigh)
	{
		//System.out.println("Modify keys");
		Room temp;
		int SIZE = mv.size();
		for(int i = 0; i < SIZE; i++)
		{
			System.out.println(i);
			temp = mv.get(i);
			String name = temp.getName();
			if(name.equalsIgnoreCase(toModName))
			{
				mv.remove(i);
				temp.removeRoom(toModName);
				populateKeysList(toModName, newLow, newHigh);
			}
		}	
	}
	
	
	public void rmFromKeysList(String rm)
	{
		Room toRm = newRoom.getRoom(rm);
		if(keys.remove(toRm) && newRoom.removeRoom(rm))
		{
			
			//System.out.println(rm + " is removed");
			props.remove(rm);
			populateRoomBox(keys);
		}
	}
	

	public void showcustomize()
	{
		mainWin.setVisible(true);
		layOut();
	}

	public void hidecustomize(){
		mainWin.setVisible(false);
	}
}
