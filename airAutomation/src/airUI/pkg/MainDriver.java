package airUI.pkg;
/*
 *****************************************************************************************************************
 *  In this main driver that will open the GUI. The main that we all have been using for our individual
 *  code is now just commented out and all placed here.
 *****************************************************************************************************************
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainDriver implements ActionListener
{


	static MainPage mp = new MainPage(new MainDriver());
	static Reports fr = new Reports(new MainDriver());
	static Customize run = new Customize(new MainDriver());


	/*
     *****************************************************************************************************************
     *  Here is where the MainPage.java will be called to display the GUI that will be using.
     *****************************************************************************************************************
     */

	public static void main(String[] args)
	{
		// This is for the main page


		mp.showMainGUI();

        /*		javax.swing.SwingUtilities.invokeLater(new Runnable()
         {
         public void run()
         {
         mp.showGUI();
         }
         });
         */
		//this is for the reports page


		//this is for the Customize page

        /*		run.mainWin.setVisible(true);
         run.mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         run.layOut();
         */	}


	public void actionPerformed(ActionEvent event) {
        Object obj = event.getSource();

        if (obj == mp.reportsButton) {
            mp.hideMainGUI();
            fr.showReportsGUI();
        }

        if (obj == fr.backButton) {
            mp.showMainGUI();
            fr.hideReportsGUI();
        }


        if (obj == mp.customizeButton) {
            mp.hideMainGUI();
            run.showcustomize();
        }

        if(obj == run.backButton){
            mp.showMainGUI();
            run.hidecustomize();
        }

    }

}
