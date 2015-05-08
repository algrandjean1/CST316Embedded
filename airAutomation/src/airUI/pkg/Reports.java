package airUI.pkg;


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;


@SuppressWarnings("unused")
class ReportsListener implements ActionListener
{
    
	public void actionPerformed(ActionEvent event) {
        
        
	}
    
    
}


@SuppressWarnings("serial")
public class Reports extends JFrame {
    
    static JPanel mainPane = new JPanel();
    JButton backButton;
    MainDriver driver;
    TableModel model = new TableModel();
    @SuppressWarnings("rawtypes")
	protected JComboBox roomBox;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Reports(MainDriver mainDriver) {
        
        this.driver = mainDriver;
        // main panel for reports frame
        
        
        getContentPane().add(mainPane);
        FlowLayout fLayout = new FlowLayout();
        mainPane.setLayout(fLayout);
        getContentPane().add(mainPane);
        
        // create a left panel
        JPanel leftPane = new JPanel();
        BoxLayout boxLayout = new BoxLayout(leftPane, BoxLayout.PAGE_AXIS);
        leftPane.setLayout(boxLayout);
        
        JLabel rLabel = new JLabel("Rooms: ");
		rLabel.setBounds(10,100,80,30);
		leftPane.add(rLabel);
        roomBox = new JComboBox();
        
        roomBox.setBounds(60, 100, 100, 25);
		leftPane.add(roomBox);
        
        
        backButton = new JButton("Back");
        backButton.addActionListener(driver);
        
        
        
        // creating the J list for time
        
        String[] time = {"Daily","Weekly","Monthly", "Yearly"};
        final JList list = new JList(time);
        
        ;
        final String[] colNamesDaily = {"Time", "O2",  "CO2", "Humidity","Temperature"};
        final String[] colNamesWeekly = {"Week", "O2",  "CO2", "Humidity","Temperature"};
        final String[] colNamesMonthly = {"Month", "O2",  "CO2", "Humidity","Temperature"};
        final String[] colNamesYearly = {"Year", "O2",  "CO2", "Humidity","Temperature"};
        
        
        
        final Object[][] dataDaily = {
            {"6am", "2", "3", " 4", "1"},
            {"7am", "1", "2", "3", "0"},
            {"8am", "2", "0", "0", "0"},
            {"9am", "1", "4", "0", "0"}
        };
        
        final Object[][] dataWeekly = {
            {"Week1", "2", "3", " 4", "1"},
            {"Week2", "1", "2", "3", "0"},
            {"Week3", "2", "0", "0", "0"},
            {"Week4", "1", "4", "0", "0"}
        };
        
        final Object[][] dataMonthly = {
            {"January", "2", "3", " 4", "1"},
            {"February", "1", "2", "3", "0"},
            {"March", "2", "0", "0", "0"},
            {"April", "1", "4", "0", "0"},
            {"May", "6", "5", "3", "8"},
            {"June", "4", "6", "0", "9"},
            {"July", "5", "33", "76", "45"},
            {"August", "6", "76", "54", "65"},
            {"September", "8", "44", "23", "23"},
            {"October", "3", "9", "0", "11"},
            {"November", "2", "8", "6", "5"},
            {"December", "1", "2", "3", "3"}
            
        };
        
        
        
        final Object[][] dataYearly = {
            {"2013", "2", "3", " 4", "1"},
            {"2014", "1", "2", "3", "0"},
            {"2015", "2", "0", "0", "0"},
            {"2016", "1", "4", "0", "0"}
        };
        
        
        
        
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(90, 150));
        
        leftPane.add(listScroller);
        leftPane.add(backButton);
        mainPane.add(leftPane);
        
        // create a J table to show the reports
        
        JTable table = new JTable(model);
        JScrollPane jtScroller = new JScrollPane(table);
        jtScroller.setPreferredSize(new Dimension(450, 550));
        mainPane.add(jtScroller);
        
        //when list is selected
        list.addListSelectionListener(new ListSelectionListener() {
            
            public void valueChanged(ListSelectionEvent event) {
                if(list.getSelectedValue().equals("Daily")){
                    model.setData(colNamesDaily, dataDaily);
                    
                }
                if(list.getSelectedValue().equals("Monthly")){
                    
                    model.setData(colNamesMonthly, dataMonthly);
                    
                    
                }
                if(list.getSelectedValue().equals("Yearly")){
                    model.setData(colNamesYearly, dataYearly);
                    
                }
                if(list.getSelectedValue().equals("Weekly")){
                    model.setData(colNamesWeekly, dataWeekly);
                    
                }
                
                computeAverage();
                
            }
        });
        
        
        //      setVisible(true);
        setTitle("Reports");
        setSize(600,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        
    }
    
	public void showReportsGUI(){
        
		this.setVisible(true);
		populateRooms();
	}
    
	public void hideReportsGUI(){
        
		this.setVisible(false);
	}
	
	@SuppressWarnings("unchecked")
	private void populateRooms(){
		int sizeOfListRoom = Room.getSize();
		ArrayList<String> roomList = Room.getroomList();
		
		if(sizeOfListRoom == roomList.size())
		{
			for(int i = 0; i < sizeOfListRoom;++i)
			{
				roomBox.addItem(roomList.get(i));
			}
		}
	}
    
	public void computeAverage()
	{
		Double total[] = new Double [model.getColumnCount()];
		Arrays.fill(total, new Double(0));
        
		int numberOfValues = model.getRowCount()-1;
		for(int column = 1; column < model.getColumnCount()-1; column++){
			for (int i = 0; i < model.getRowCount()-1; i++){
				total[column] = total[column] + Double.valueOf((String) model.getValueAt(i, column));        // getValueAt(row, column)
			}
		}
        
		Object[] row = {"Average", total[1]/numberOfValues, total[2]/numberOfValues, total[3]/numberOfValues, total[4]/numberOfValues};
		model.addAverage(row);
        
	}
}