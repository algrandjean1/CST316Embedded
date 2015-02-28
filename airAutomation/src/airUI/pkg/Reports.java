package airUI.pkg;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class ReportsListener implements ActionListener
{

	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}
public class Reports extends JFrame {
  
   public Reports() {
    
	   // main panel for reports frame   
       JPanel mainPane = new JPanel();
       mainPane.setPreferredSize(new Dimension(600, 600));
       getContentPane().add(mainPane);
       FlowLayout fLayout = new FlowLayout();
       mainPane.setLayout(fLayout);
       this.add(mainPane);
       
       // create a left panel
       JPanel leftPane = new JPanel();
       BoxLayout boxLayout = new BoxLayout(leftPane, BoxLayout.PAGE_AXIS);
       leftPane.setLayout(boxLayout);
       
       JButton backButton = new JButton("Back");
    

       
       // creating the J list for time
       
      String[] time = {"Daily", "Monthly", "Quarterly", "Yearly"};
      JList list = new JList(time);     
      list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
      list.setLayoutOrientation(JList.VERTICAL);
      list.setVisibleRowCount(-1);
      JScrollPane listScroller = new JScrollPane(list);
      listScroller.setPreferredSize(new Dimension(90, 150));
      
      leftPane.add(listScroller);
      leftPane.add(backButton);
      mainPane.add(leftPane);
    
      // create a J table to show the reports 
      String[] colName = {"Month", "O2",  "CO2", "Humidity","Temperature"};
      Object[][] data = {
          {"January", "Column 2", "Column 3", " Column 4", " Column 1"},
          {"February", "Column 1", "Column 2", "Column 3", ""},
          {"March", "Column 2", "", "", ""},
          {"April", "Column 1", "", "", ""},
          {"May", "", "", "", ""},
          {"June", "", "", "", ""},
          {"July", "", "", "", ""},
          {"August", "", "", "", ""},
          {"September", "", "", "", ""},
          {"October", "", "", "", ""},
          {"November", "", "", "", ""},
          {"December", "", "", "", ""}

      };

      JTable table = new JTable(data, colName);
      JScrollPane jtScroller = new JScrollPane(table);
      jtScroller.setPreferredSize(new Dimension(450, 550));
      mainPane.add(jtScroller);
      
      setVisible(true);
      setTitle("Reports");
      setSize(600,600);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      pack();
   
      
   }
 
   // main() method 
   /*
   public static void main(String[] args) 
   {
    
	   Reports fr = new Reports();
           
         
   }*/
}