package airUI.pkg;


/* Sites that aided in the creation of this page:
 * http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/layout/AbsoluteLayoutDemoProject/src/layout/AbsoluteLayoutDemo.java
 * http://www.tutorialspoint.com/java/java_date_time.htm
 * http://www.macs.hw.ac.uk/cs/java-swing-guidebook/?name=JList&page=3
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MainPage
{
	JFrame frame = new JFrame("Main Page.");
	JButton customizeButton;
	JButton reportsButton;
	JButton refreshButton;
	JList currentRoomList, currentlyOnListPane;
	JScrollPane roomListPane;
	ArrayList<String> roomList;
	
	//Read the values from properties files
	String propFileName = "room.properties";
	Properties prop = new Properties();
	
	float tempThresholdLow;
	float tempThresholdHigh;
	float humidityThresholdLow;
	float humidityThresholdHigh;
	float carbonDioxideThresholdLow;
	float carbonDioxideThresholdHigh;
	float methaneThresholdLow;
	float methaneThresholdHigh;
	
	MainDriver driver;
	private float co2Read = 0f;
	private float methaneRead = 0f;
	private float tempRead =0f;
	private float humidRead =0f;
	JTextArea co2Print, methanePrint, tempPrint, humidPrint;
	
	DefaultListModel room = new DefaultListModel();
	DefaultListModel currOn = new DefaultListModel();
    
    
	public MainPage(MainDriver driver){
        
		this.driver = driver;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addElements(frame.getContentPane());
		frame.setSize(600,600);
		
		//Read Properties File
		readRoomProperties();
	}
    
	private void readRoomProperties() {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		try{
            if (inputStream != null) {
                prop.load(inputStream);
                tempThresholdLow 	= Float.parseFloat(prop.getProperty("tempThresholdLow"));
                tempThresholdHigh = Float.parseFloat(prop.getProperty("tempThresholdHigh"));
                humidityThresholdLow = Float.parseFloat(prop.getProperty("humidityThresholdLow"));
                humidityThresholdHigh= Float.parseFloat(prop.getProperty("humidityThresholdHigh"));
                carbonDioxideThresholdLow= Float.parseFloat(prop.getProperty("carbonDioxideThresholdLow"));
                carbonDioxideThresholdHigh= Float.parseFloat(prop.getProperty("carbonDioxideThresholdHigh"));
                methaneThresholdLow= Float.parseFloat(prop.getProperty("methaneThresholdLow"));
                methaneThresholdHigh = Float.parseFloat(prop.getProperty("methaneThresholdHigh"));
                
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
		}catch(IOException e){
            
		}
		
        
	}
    
	public void addElements(Container pane)
	{
		pane.setLayout(null);
		JLabel roomLabel, onLabel, dateLabel;
        
		Font bigText = new Font("Serif",Font.BOLD,20);
        
        //		String thisList[] = {"Living Room", "Master Room", "Gary's Room", "David's Room", "Kitchen"};
		roomList = Room.getroomList();
        
        //	room.addElement(arg0)
        /*	for(int i=0; i<thisList.length; i++){
         room.addElement(thisList[i]);
         currOn.addElement(thisList[i]);
         }*/
        
		//currentRoomList = new JList(room);
		currentRoomList = new JList(room);
		currentRoomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		currentRoomList.addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent e) {
				
				int lastIndex = e.getLastIndex();
				Room selectedRoom = Room.getRoom(roomList.get(lastIndex));
				float co2Read = 0f;
				float methaneRead = 0f;
				float tempRead = 0f;
				float humidRead = 0f;
				
				if(!selectedRoom.getCarbonDioxide().trim().isEmpty()){
					co2Read =Float.valueOf(selectedRoom.getCarbonDioxide());
				}
				
				if(!selectedRoom.getMethane().trim().isEmpty()){
					methaneRead = Float.valueOf(selectedRoom.getMethane());
				}
				
				if(!selectedRoom.getTemperature().trim().isEmpty()){
					tempRead = Float.valueOf(selectedRoom.getTemperature());
				}
                
				if(!selectedRoom.getHumidity().trim().isEmpty()){
					humidRead = Float.valueOf(selectedRoom.getHumidity());
				}
                
                setData(co2Read, methaneRead, tempRead, humidRead);
				
			}
		});
		
		roomListPane = new JScrollPane(currentRoomList);
		roomListPane.setPreferredSize(new Dimension(100,200));
		pane.add(roomListPane);
        
		currentlyOnListPane = new JList(currOn);
		currentlyOnListPane.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane onList = new JScrollPane(currentlyOnListPane);
		onList.setPreferredSize(new Dimension(100,200));
		pane.add(onList);
		
		customizeButton = new JButton("Refresh");
		pane.add(customizeButton);
        
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
        
		methanePrint = new JTextArea();
		methanePrint.setFont(bigText);
		methanePrint.setText("CH4: \n" + methaneRead);
		pane.add(methanePrint);
        
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
		Dimension size = roomListPane.getPreferredSize();
		roomListPane.setBounds(100 + insets.left, 250 + insets.right, size.width + 40, size.height + 20);
        
        
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
        
		size = methanePrint.getPreferredSize();
		methanePrint.setBounds(135 + insets.left, 2 + insets.right, size.width + 78, size.height + 60);
        
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
		roomList = Room.getroomList();
        
		room.clear();
		currOn.clear();
		
        for(int i=0; i<roomList.size(); i++){
            room.addElement(roomList.get(i).toString());
            currOn.addElement(roomList.get(i).toString());
        }
        currentRoomList = new JList(room);
        currentlyOnListPane = new JList(currOn);
        
        //fireContentsChanged();
		
	}
    
	public void hideMainGUI(){
        
		frame.setVisible(false);
	}
    
	public void setData(float co2, float ch4, float temp, float humid){
		this.co2Read = co2;
		this.methaneRead = ch4;
		this.tempRead = temp;
		this.humidRead = humid;
		
		updateData();
		
	}
	public void updateData(){
		co2Print.setText("CO2: \n"+ co2Read + "F");
		methanePrint.setText("CH4: \n" + methaneRead + "F");
		tempPrint.setText("Temperature: \n" + tempRead + "F");
		humidPrint.setText("Humidity: \n" + humidRead + "%");
		
		if(co2Read <carbonDioxideThresholdLow){
			co2Print.setBackground(Color.GREEN);
		}else if(co2Read >carbonDioxideThresholdLow && co2Read <carbonDioxideThresholdHigh){
			co2Print.setBackground(Color.ORANGE);
		}else{
            co2Print.setBackground(Color.RED);
		}
		
        
		if(methaneRead <methaneThresholdLow){
			methanePrint.setBackground(Color.GREEN);
		}else if(methaneRead >methaneThresholdLow && methaneRead <methaneThresholdHigh){
			methanePrint.setBackground(Color.ORANGE);
		}else{
			methanePrint.setBackground(Color.RED);
		}
		
        
		if(tempRead <tempThresholdLow){
			tempPrint.setBackground(Color.GREEN);
		}else if(tempRead >tempThresholdLow && tempRead <tempThresholdHigh){
			methanePrint.setBackground(Color.ORANGE);
		}else{
            tempPrint.setBackground(Color.RED);
		}
		
        
		if(humidRead <humidityThresholdLow){
			humidPrint.setBackground(Color.GREEN);
		}else if(humidRead >humidityThresholdLow && humidRead <humidityThresholdHigh){
			methanePrint.setBackground(Color.ORANGE);
		}else{
			humidPrint.setBackground(Color.RED);
		}
        
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