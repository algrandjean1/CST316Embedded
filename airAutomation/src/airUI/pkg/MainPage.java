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
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
@SuppressWarnings({ "rawtypes", "unused" })
public class MainPage
{
	JFrame frame = new JFrame("Main Page.");
	JButton customizeButton;
	JButton reportsButton;
	JButton refreshButton;
	JList currentRoomList;
	JScrollPane roomListPane;
	ArrayList<String> roomList;
	ArrayList<String> loadList = new ArrayList<String>();
    
	//Read the values from properties files
	String propFileName = MainDriver.ROOM_PROPERTIES_PATH;
	Properties roomProp = new Properties();
	Properties userProp = new Properties();
    
	float tempThresholdLow;
	float tempThresholdHigh;
	float humidityThresholdLow;
	float humidityThresholdHigh;
	float carbonDioxideThreshold;
	float methaneThreshold;
    
	MainDriver driver;
	Room ro;
    
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
    
    
	@SuppressWarnings("hiding")
	public MainPage(MainDriver driver){
		this.driver = driver;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addElements(frame.getContentPane());
		frame.setSize(600,600);
        
		//Read Properties File
		readRoomProperties();
	}
    
	private void readRoomProperties()
	{
		FileInputStream in;
		try
		{
			//InputStream in = MainPage.class.getResourceAsStream(propFileName);
			in = new FileInputStream(propFileName);
			roomProp.load(in);
            
            tempThresholdLow = Float.parseFloat(roomProp.getProperty("tempThresholdLow"));
            tempThresholdHigh = Float.parseFloat(roomProp.getProperty("tempThresholdHigh"));
            humidityThresholdLow = Float.parseFloat(roomProp.getProperty("humidityThresholdLow"));
            humidityThresholdHigh= Float.parseFloat(roomProp.getProperty("humidityThresholdHigh"));
            carbonDioxideThreshold= Float.parseFloat(roomProp.getProperty("carbonDioxideThreshold"));
            methaneThreshold = Float.parseFloat(roomProp.getProperty("methaneThreshold"));
            
            in.close();
            
		}catch(FileNotFoundException e){
			//throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			System.out.println("property file '" + propFileName + "' not found in the classpath");
		}catch(NumberFormatException e){
			System.out.println("An Invalid format existes in one of the properties in the property file " + propFileName);
		}catch(NullPointerException e){
			//System.out.println("here");
			System.out.println("One of the properties in the property file " + propFileName + " is missing.");
		}catch(IOException e){
			System.out.println("IOException Error occured while reading from property file '" + propFileName);
			e.printStackTrace();
		}
	}
    
	@SuppressWarnings("unchecked")
	public void addToListModel(String ele)
	{
		room.addElement(ele);
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
			FileInputStream inIt = new FileInputStream(MainDriver.USER_SETTINGS_PROPERTIES_PATH);
			userProp.load(inIt);
			inIt.close();
			Enumeration keysToLoad = userProp.propertyNames();
            
			while(keysToLoad.hasMoreElements())
			{
				loadList.add((String) keysToLoad.nextElement());
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File is not found");
			e.printStackTrace();
		}
		catch(NullPointerException e)
		{
			System.out.println("File is null");
			e.printStackTrace();
		}
		catch(IOException e)
		{
			System.out.println("General IOException");
			e.printStackTrace();
		}
	}
    
	@SuppressWarnings("unchecked")
	public void addElements(Container pane)
	{
		pane.setLayout(null);
		JLabel roomLabel;
		final JLabel dateLabel;
        
		Font bigText = new Font("Serif",Font.BOLD,20);
        
		roomList = Room.getroomList();
        
		currentRoomList = new JList(room);
		currentRoomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
		currentRoomList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
                
                
				int lastIndex = e.getLastIndex();
				final Room selectedRoom = Room.getRoom(roomList.get(lastIndex));
                
                
				Timer timer = new Timer();
				timer.scheduleAtFixedRate(new TimerTask()
                                          {
					@SuppressWarnings("hiding")
					public void run()
					{
                        
						//readUserSettings();
						//String[] listArray = loadList.toArray(new String[loadList.size()]);
                        
                        
						System.out.println("Updating room data at: "+new Date().toString());
						selectedRoom.updateData();
						System.out.println("Room data updated at: "+new Date().toString());
						
						String co2Read = selectedRoom.getCarbonDioxide();
						String methaneRead = selectedRoom.getMethane();
						String tempRead = selectedRoom.getTemperature();
						String humidRead = selectedRoom.getHumidity();
						//System.out.println(co2Read+" , "+methaneRead+" , "+tempRead+" , "+humidRead);
						setData(co2Read, methaneRead, tempRead, humidRead);
					}
                    
				},0,5000);
                
			}
		});
		roomListPane = new JScrollPane(currentRoomList);
		roomListPane.setPreferredSize(new Dimension(100,200));
		pane.add(roomListPane);
        
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
        
        dateLabel = new JLabel();
        dateLabel.setFont(bigText);
        dateLabel.setText(new Date().toString());
        pane.add(dateLabel);
        pane.repaint();
        
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                dateLabel.setText(new Date().toString());
            }
        }, 1000, 1000);
        
		Insets insets = pane.getInsets();
		Dimension size = roomListPane.getPreferredSize();
        
		roomListPane.setBounds(230 + insets.left, 250 + insets.right, size.width + 40, size.height + 20);
        
		size = customizeButton.getPreferredSize();
		customizeButton.setBounds(110 + insets.left, 500 + insets.right, size.width + 5, size.height + 5);
        
		size = reportsButton.getPreferredSize();
		reportsButton.setBounds(360 + insets.left, 500 + insets.right, size.width + 5, size.height + 5);
        
		size = roomLabel.getPreferredSize();
        
		roomLabel.setBounds(130 + insets.left, 250 + insets.right, size.width, size.height);
        
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
		
		loadListModel(room, roomList);
		
	}
	@SuppressWarnings("unchecked")
	public void loadListModel(DefaultListModel r, ArrayList<String> rL)
	{
		r.clear();
		int s = rL.size();
		for(int i=0; i<s; i++){
            r.addElement(rL.get(i).toString());
        }
        currentRoomList = new JList(r);
        
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
    
	public void updateData()
	{
		co2Print.setText("CO: \n"+ co2Read + " PPM");
		methanePrint.setText("CH4: \n" + methaneRead + " PPM");
		tempPrint.setText("Temperature: \n" + tempRead + "\u00b0" + "F");
		humidPrint.setText("Humidity: \n" + humidRead + " %");
        
		co2Parse = Float.parseFloat(co2Read);
		methaneParse = Float.parseFloat(methaneRead);
		tempParse = Float.parseFloat(tempRead);
		humidParse = Float.parseFloat(humidRead);
		
		if(co2Parse > carbonDioxideThreshold)
		{
			co2Print.setBackground(Color.RED);
		}
		else
		{
			co2Print.setBackground(Color.GREEN);
		}
		
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
}