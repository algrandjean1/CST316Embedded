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
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;

public class Customize implements ActionListener, ItemListener
{
	//Date time = new Date();
	//SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

	int TEMPRANGE = 27;
	int TIMERANGE = 49;

	ArrayList<String> roomList = new ArrayList<String>();
	ArrayList<String> tempR = new ArrayList<String>();
	ArrayList<String> tempR2 = new ArrayList<String>();
	ArrayList<String> timeR = new ArrayList<String>();
	ArrayList<String> timeR2 = new ArrayList<String>();

	protected JFrame mainWin;
	protected JPanel mainPan;

	protected JLabel rLabel;
	protected JLabel Temp;
	protected JLabel To;
	protected JLabel To2;
	protected JLabel SleepSch;
	protected JLabel RPre;
	protected JLabel newR;

	protected JTextField newRoom;

	protected JSpinner lowTemp;
	protected JSpinner highTemp;
	protected JSpinner lowTime;
	protected JSpinner highTime;

	protected SpinnerListModel tempModel;
	protected SpinnerListModel tempModel2;
	protected SpinnerListModel timeModel;
	protected SpinnerListModel timeModel2;

	protected JComboBox roomBox;
	protected JComboBox roomPreset;

	protected JButton addModRooms;
	protected JButton backButton;
	
	protected MainDriver driver;

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

		newRoom = new JTextField();

		addModRooms = new JButton("Add/Modify");
		backButton = new JButton("Back");

		lowTemp = new JSpinner();
		highTemp = new JSpinner();
		lowTime = new JSpinner();
		highTime = new JSpinner();

		roomBox = new JComboBox(roomList.toArray());

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
		lowTemp.setModel(tempModel);
		mainPan.add(lowTemp);

		//the label " to "
		To.setBounds(350,100,40,30);
		mainPan.add(To);

		//the Spinner on the right for temp
		highTemp.setBounds(420,100,120,30);
		highTemp.setModel(tempModel2);
		mainPan.add(highTemp);

		//The label "New Rooms Name: "
		newR.setBounds(170, 150, 120, 30);
		mainPan.add(newR);

		//the textfield that is blank for new Rooms
		newRoom.setBounds(295,150,120,30);
		mainPan.add(newRoom);

		//the button add or modify a rooms
		addModRooms.setBounds(430,150,120,30);
		mainPan.add(addModRooms);
		addModRooms.addActionListener(this);


		//the label "Sleep Schedule: "
		SleepSch.setBounds(170,200, 150, 30);
		mainPan.add(SleepSch);

		//the Spinner on the left for time
		lowTime.setBounds(170,250,80,30);
		lowTime.setModel(timeModel);
		mainPan.add(lowTime);

		//Second label " to "
		To2.setBounds(260,250,40,30);
		mainPan.add(To2);

		//the spinner on the right for time
		highTime.setBounds(290,250,80,30);
		highTime.setModel(timeModel2);
		mainPan.add(highTime);

		//the back button
		backButton.setBounds(500,500,60,30);
		mainPan.add(backButton);
		backButton.addActionListener(driver);

	}

	public void setUp()
	{
		mainWin.setVisible(true);
		mainPan.setVisible(true);
		mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		layOut();
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
			if(newRoom.getText() != null)
			{
				roomBox.addItem(newRoom.getText());
			}
			else
			{
				;
			}
		}
		else if(e.getSource() == backButton)
		{
			;
		}
	}

	public void itemStateChanged(ItemEvent event)
	{
		if(event.getStateChange() == ItemEvent.SELECTED)
		{
			Object compare = event.getSource();
		}
	}

	public void showcustomize(){
		mainWin.setVisible(true);
	}

	public void hidecustomize(){
		mainWin.setVisible(false);
	}
}
