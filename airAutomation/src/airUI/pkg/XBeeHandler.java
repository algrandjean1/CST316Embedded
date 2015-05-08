/**
 * Air automation project to monitor indoor air quality
 */

package airUI.pkg;

/**
 * Author: bjduarte
 *@Version Spring 2015
 * Course project: 316 IndoorAirAutomation
 */

import java.io.File;
import java.io.FilenameFilter;

import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.XBeeNetwork;
import com.digi.xbee.api.ZigBeeDevice;
import com.digi.xbee.api.models.XBee64BitAddress;

/**
 * XBeeHandler class sets up a XBee network and locates remote devices to communicate with.
 * Will allows for the sending and receiving of serial data to and from a remote device from a java client
 */
public class XBeeHandler {
	private XBeeNetwork xbeeNetwork;
	private ZigBeeDevice xbee;

	/**
	 *  set up the XBee network
	 *  scan the network looking for remote devices
	 *  scans for 30 seconds 
	 *  returns devices or message that no devices were found
	 */
	@SuppressWarnings("unused")
	public XBeeHandler() throws Exception {
		try {
		xbee = new ZigBeeDevice(getFirstUsbPortName(), 115200);
		xbee.open();

		xbeeNetwork = xbee.getNetwork();
		RemoteXBeeDevice dragon = xbeeNetwork.getDevice("DRAGON");
		for (int tries = 0; xbeeNetwork.getNumberOfDevices() == 0 && tries < 3; tries++) {
			xbeeNetwork.startDiscoveryProcess();
			for (int i = 0; i < 30; i++) {
				Thread.sleep(1000);
				if (xbeeNetwork.isDiscoveryRunning()) {
					System.out.print('.');
					if (i == 29) {
						System.out.println();
						System.out.println("Discover still running after 30 seconds");
					}
				} else {
					System.out.println();
					System.out.println("Discover finished after " + i + " seconds");
					break;
				}
			}
			xbeeNetwork.stopDiscoveryProcess();

			if (xbeeNetwork.getNumberOfDevices() == 0 && tries < 3) {
				System.out.println("Node discover found nothing, trying again");
			}
		}

		System.out.println("Network stats");
		System.out.println("\tDevice Count: " + xbeeNetwork.getNumberOfDevices());
		for (RemoteXBeeDevice device : xbeeNetwork.getDevices()) {
			device.readDeviceInfo();
			System.out.println("\t\t" + device.get64BitAddress() + ":" + device.getNodeID() + " " + device.getFirmwareVersion());
		}
		} catch(Exception e) {
			System.err.println(e);
		}
	} // end constructor 

	/**
	 * make sure dclean up on shut down
	 */
	public void shutDown() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
	         @Override
	         public void run()
	         {
	            if (xbee.isOpen()) {
	               xbee.close();
	            }
	         }
	      });
	      	}
	
/**
	 * @return the xbeeNetwork
	 */
	public XBeeNetwork getXbeeNetwork() {
		return xbeeNetwork;
	}

	/**
	 * @return the xbee
	 */
	public ZigBeeDevice getXbee() {
		return xbee;
	}

/**
 * refer to devices by name
 * allows for point to point communication
 * @return all discovered devices on the network
 * @throws Exception
 */
	@SuppressWarnings("unused")
	public RemoteXBeeDevice getDevices() throws Exception {
		RemoteXBeeDevice dragon = xbeeNetwork.getDevice("DRAGON");
		
		if (dragon == null) {
			System.out.println("Didn't find dragon");
		} else {
			System.out.println("Dragon found!!");

			// Save off the previous destination, then point it to us
			XBee64BitAddress priorDestination = dragon.getDestinationAddress();
			dragon.setDestinationAddress(xbee.get64BitAddress());

		}
		return dragon;
	}

	/**
	 *scans the system for the serial port
	 *can detect Linux and Unix serial ports 
	 * @return serial port of machine
	 */
	private static String getFirstUsbPortName() {
		String firstXbee = null;

		File[] files = new File("/dev").listFiles(new FilenameFilter()
		{

			@Override
			public boolean accept(File arg0, String arg1)
			{
				return (arg1.startsWith("cu") || arg1.startsWith("tty")) && arg1.toLowerCase().contains("usb");
			}
		});
		for (File file : files)
		{
			String portName = file.getAbsolutePath().toLowerCase();
			boolean macUsb = portName.startsWith("/dev/cu") && portName.contains("usb");
			boolean linuxUsb = portName.startsWith("/dev/tty") && portName.contains("usb");
			if (macUsb || linuxUsb)
			{
				firstXbee = file.getAbsolutePath();
			}
		}

		return firstXbee;
	}

}
