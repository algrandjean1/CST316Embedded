package airUI.pkg;

/*
*****************************************************************************************************************
*  In this main driver that will open the GUI. The main that we all have been using for our individual
*  code is now just commented out and all placed here.
*****************************************************************************************************************
*/

import javax.swing.JFrame;

public class MainDriver 
{
	
	/*
	*****************************************************************************************************************
	*  Here is where the MainPage.java will be called to display the GUI that will be using.
	*****************************************************************************************************************
	*/
	
	public static void main(String[] args) 
	{
		// This is for the main page
		final MainPage mp = new MainPage();
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				mp.showGUI();	
			}
		});
		
		//this is for the reports page
		Reports fr = new Reports();
		
		//this is for the Customize page
		Customize run = new Customize();
		run.setUp();
	}

}
