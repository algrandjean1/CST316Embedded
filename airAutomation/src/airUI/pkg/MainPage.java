package airUI.pkg;

/* Sites that aided in the creation of this page:
 * http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/layout/AbsoluteLayoutDemoProject/src/layout/AbsoluteLayoutDemo.java
 * http://www.tutorialspoint.com/java/java_date_time.htm
 * http://www.macs.hw.ac.uk/cs/java-swing-guidebook/?name=JList&page=3
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.*;

public class MainPage
{
	JFrame frame = new JFrame("Main Page.");
	JButton customizeButton;
	JButton reportsButton;
	MainDriver driver;


	public MainPage(MainDriver driver){

		this.driver = driver;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addElements(frame.getContentPane());
		frame.setSize(600,600);
	}

	public void addElements(Container pane)
	{
		pane.setLayout(null);
		JList currentRoomList, currentlyOnList;
		JLabel roomLabel, onLabel, dateLabel;
		float co2Read = 0.0f;
		float o2Read = 0.0f;
		float tempRead = 0.0f;
		float humidRead = 0.0f;
		JTextArea co2Print, o2Print, tempPrint, humidPrint;
		DefaultListModel room = new DefaultListModel();
		DefaultListModel currOn = new DefaultListModel();

		Font bigText = new Font("Serif",Font.BOLD,20);

		String thisList[] = {"One", "Two", "Three", "Four", "Five"};

		for(int i=0; i<thisList.length; i++){
			room.addElement(thisList[i]);
			currOn.addElement(thisList[i]);
		}

		currentRoomList = new JList(room);
		currentRoomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane roomList = new JScrollPane(currentRoomList);
		roomList.setPreferredSize(new Dimension(100,200));
		pane.add(roomList);

		currentlyOnList = new JList(currOn);
		currentlyOnList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane onList = new JScrollPane(currentlyOnList);
		onList.setPreferredSize(new Dimension(100,200));
		pane.add(onList);

		customizeButton = new JButton("Customize");
		pane.add(customizeButton);

		reportsButton = new JButton("Reports");
		pane.add(reportsButton);

		reportsButton.addActionListener(driver);
		customizeButton.addActionListener(driver);

		roomLabel = new JLabel("Current Room: ");
		pane.add(roomLabel);

		onLabel = new JLabel("Currently On: ");
		pane.add(onLabel);

		co2Print = new JTextArea();
		co2Print.setFont(bigText);
		co2Print.setText("CO2: \n"+ co2Read);
		pane.add(co2Print);

		o2Print = new JTextArea();
		o2Print.setFont(bigText);
		o2Print.setText("O2: \n" + o2Read);
		pane.add(o2Print);

		tempPrint = new JTextArea();
		tempPrint.setFont(bigText);;
		tempPrint.setText("Temperature: \n" + tempRead);
		pane.add(tempPrint);

		humidPrint = new JTextArea();
		humidPrint.setFont(bigText);
		humidPrint.setText("Humidity: \n" + humidRead);
		pane.add(humidPrint);

		Date today = new Date();
		dateLabel = new JLabel();
		dateLabel.setFont(bigText);
		dateLabel.setText(today.toString());
		pane.add(dateLabel);

		Insets insets = pane.getInsets();
		Dimension size = roomList.getPreferredSize();
		roomList.setBounds(100 + insets.left, 250 + insets.right, size.width + 40, size.height + 20);


		size = onList.getPreferredSize();
		onList.setBounds(350 + insets.left, 250 + insets.right, size.width + 40, size.height + 20);

		size = customizeButton.getPreferredSize();
		customizeButton.setBounds(110 + insets.left, 500 + insets.right, size.width + 5, size.height + 5);

		size = reportsButton.getPreferredSize();
		reportsButton.setBounds(360 + insets.left, 500 + insets.right, size.width + 5, size.height + 5);

		size = roomLabel.getPreferredSize();
		roomLabel.setBounds(5 + insets.left, 250 + insets.right, size.width, size.height);

		size = onLabel.getPreferredSize();
		onLabel.setBounds(260 + insets.left,250 + insets.right, size.width, size.height);

		size = co2Print.getPreferredSize();
		co2Print.setBounds(10 + insets.left, 2 + insets.right, size.width + 65, size.height + 60);

		size = o2Print.getPreferredSize();
		o2Print.setBounds(135 + insets.left, 2 + insets.right, size.width + 78, size.height + 60);

		size = tempPrint.getPreferredSize();
		tempPrint.setBounds(270 + insets.left, 2 + insets.right, size.width + 10, size.height + 60);

		size = humidPrint.getPreferredSize();
		humidPrint.setBounds(420 + insets.left, 2 + insets.right, size.width + 40, size.height + 60);

		size = dateLabel.getPreferredSize();
		dateLabel.setBounds(150 + insets.left, 180 + insets.right, size.width, size.height);


	}

	public void launch(){
		showMainGUI();
	}

	public void showMainGUI(){

		frame.setVisible(true);
	}

	public void hideMainGUI(){

		frame.setVisible(false);
	}

	/*
	public static void main(String[] args)
	{

		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				showGUI();
			}
		});
	}*/

}
