package airUI.pkg;


/* Sites that aided in the creation of this page:
 * http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/layout/AbsoluteLayoutDemoProject/src/layout/AbsoluteLayoutDemo.java
 * http://www.tutorialspoint.com/java/java_date_time.htm
 * http://www.macs.hw.ac.uk/cs/java-swing-guidebook/?name=JList&page=3
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
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
	ArrayList<String> loadList = new ArrayList<String>();
    
	//Read the values from properties files
	String propFileName = "airAutomation/room.properties";
	Properties prop = new Properties();
	Properties roomProps = new Properties();
    
	float tempThresholdLow;
	float tempThresholdHigh;
	float humidityThresholdLow;
	float humidityThresholdHigh;
	float carbonDioxideThreshold;
	float methaneThreshold;
    
	MainDriver driver;
	
	private String co2Read = "0";
	private String methaneRead = "0";
	private String tempRead = "0";
	private String humidRead = "0";
	
	private float co2Parse = 0.0f;
	private float methaneParse = 0.0f;
	private float tempParse = 0.0f;
	private float humidParse = 0.0f;
	
	JTextArea co2Print, methanePrint, tempPrint, humidPrint;
    
	DefaultListModel room = new DefaultListModel();
	DefaultListModel currOn = new DefaultListModel();
    
    
	public MainPage(MainDriver driver)
	{
        
		this.driver = driver;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addElements(frame.getContentPane());
		frame.setSize(600,600);
        
		//Read Properties File
		readRoomProperties();
	}
    
	private void readRoomProperties() 
	{
		//InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		FileInputStream in;
		try
		{
			in = new FileInputStream(propFileName);
			prop.load(in);
			
            if (in != null) 
            {
                prop.load(in);
                
                tempThresholdLow 	= Float.parseFloat(prop.getProperty("tempThresholdLow"));
                tempThresholdHigh = Float.parseFloat(prop.getProperty("tempThresholdHigh"));
                humidityThresholdLow = Float.parseFloat(prop.getProperty("humidityThresholdLow"));
                humidityThresholdHigh= Float.parseFloat(prop.getProperty("humidityThresholdHigh"));
                carbonDioxideThreshold= Float.parseFloat(prop.getProperty("carbonDioxideThreshold"));
                methaneThreshold= Float.parseFloat(prop.getProperty("methaneThreshold"));
            } 
            else 
            {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            in.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
    
	public void addElements(Container pane)
	{
		pane.setLayout(null);
		JLabel roomLabel, onLabel, dateLabel;
        
		Font bigText = new Font("Serif",Font.BOLD,20);
        
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
				String co2Read = "0";
				String methaneRead = "0";
				String tempRead = "0";
				String humidRead = "0";
				
				try
				{
					String name;
					String lowandHigh;
					String low;
					String high;
					
					Room loadUsers;
					FileInputStream inIt = new FileInputStream("airAutomation/userSettings.properties");
					roomProps.load(inIt);
					inIt.close();	
					Enumeration keysToLoad = roomProps.propertyNames();
					
					while(keysToLoad.hasMoreElements())
					{
						loadList.add((String) keysToLoad.nextElement());
					}
					
					//System.out.println(loadList.size());
					for(int i=0;i<loadList.size();i++)
					{
						//System.out.println(loadList.get(i));
						name = loadList.get(i);
						lowandHigh = roomProps.getProperty(name);
						String[] splitList = lowandHigh.split(",");
						low = splitList[0];
						high = splitList[1];
						tempRead = splitList[2];
						humidRead = splitList[3];
						co2Read = splitList[4];
						methaneRead = splitList[5];
						
						setData(co2Read, methaneRead, tempRead, humidRead);
					}
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
				
                //setData(co2Read, methaneRead, tempRead, humidRead);
                
                /*
				if(!selectedRoom.getCarbonDioxide().trim().isEmpty()){
					co2Read = selectedRoom.getCarbonDioxide();
				}
                
				if(!selectedRoom.getMethane().trim().isEmpty()){
					methaneRead = selectedRoom.getMethane();
				}
                
				if(!selectedRoom.getTemperature().trim().isEmpty()){
					tempRead = selectedRoom.getTemperature();
				}
                
				if(!selectedRoom.getHumidity().trim().isEmpty()){
					humidRead = selectedRoom.getHumidity();
				}
				*/
                
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
    
	public void setData(String co2, String ch4, String temp, String humid){
		this.co2Read = co2;
		this.methaneRead = ch4;
		this.tempRead = temp;
		this.humidRead = humid;
		
		updateData();
	}
	
	public void updateData(){
		co2Print.setText("CO2: \n"+ co2Read + "%");
		methanePrint.setText("CH4: \n" + methaneRead + "%");
		tempPrint.setText("Temperature: \n" + tempRead + "F");
		humidPrint.setText("Humidity: \n" + humidRead + "%");
		
		co2Parse = Float.parseFloat(co2Read);
		methaneParse = Float.parseFloat(methaneRead);
		tempParse = Float.parseFloat(tempRead);
		humidParse = Float.parseFloat(humidRead);
        
		/*
		if(co2Parse < carbonDioxideThresholdLow){
			co2Print.setBackground(Color.GREEN);
		}else if(co2Parse > carbonDioxideThresholdLow && co2Parse < carbonDioxideThresholdHigh){
			co2Print.setBackground(Color.ORANGE);
		}else{
            co2Print.setBackground(Color.RED);
		}*/
		
		if(co2Parse > carbonDioxideThreshold)
		{
			co2Print.setBackground(Color.RED);
		}
		else
		{
			co2Print.setBackground(Color.GREEN);
		}
        
        /*
		if(methaneParse <methaneThresholdLow){
			methanePrint.setBackground(Color.GREEN);
		}else if(methaneParse >methaneThresholdLow && methaneParse <methaneThresholdHigh){
			methanePrint.setBackground(Color.ORANGE);
		}else{
			methanePrint.setBackground(Color.RED);
		}
		*/
		
		if(methaneParse > methaneThreshold)
		{
			co2Print.setBackground(Color.RED);
		}
		else
		{
			co2Print.setBackground(Color.GREEN);
		}
        
        
		if(tempParse <tempThresholdLow){
			tempPrint.setBackground(Color.GREEN);
		}else if(tempParse >tempThresholdLow && tempParse <tempThresholdHigh){
			methanePrint.setBackground(Color.ORANGE);
		}else{
            tempPrint.setBackground(Color.RED);
		}
        
        
		if(humidParse <humidityThresholdLow){
			humidPrint.setBackground(Color.GREEN);
		}else if(humidParse >humidityThresholdLow && humidParse <humidityThresholdHigh){
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
