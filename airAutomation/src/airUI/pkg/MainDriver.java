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
	static XBeeHandler xbeeHandler;
	static MainPage mp;
	static Reports fr;
	static Customize run;
	public static final String ROOM_PROPERTIES_PATH = "src/room.properties";
	public static final String ROOM_VALUES_PROPERTIES_PATH = "src/roomValues.properties";
	public static final String USER_PROPERTIES_PATH = "src/user.properties";
	public static final String USER_SETTINGS_PROPERTIES_PATH = "src/userSettings.properties";

	/*
     *****************************************************************************************************************
     *  Here is where the MainPage.java will be called to display the GUI that will be using.
     *****************************************************************************************************************
     */
	public static void main(String[] args) throws Exception
	{
		/*
		 * calling the XBeeHandler to set up the XBeeNetwork
		 */
		try {
			xbeeHandler = new XBeeHandler();
		} catch(Exception e) {
			System.err.println(e);
		}
		mp = new MainPage(new MainDriver());
		fr = new Reports(new MainDriver());
		run = new Customize(new MainDriver());

		// This is for the main page
		mp.showMainGUI();
	}

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
