/**
 * AirAutomation project to monitor indoor air quality.
 */

package airUI.pkg;


/**
 * @author BMosAir
 * @Version Spring 2015
 * @Project CST316
 */

/**
 * Room object with sensor data
 */
public class Room {
	private String name, humidity, carbonDioxide, methane;
	private String lowerBound, upperBound;
	
/**
 *constructor for new Room 
 * @param name name of new room
 * @param lowerBound lower boundary of temperature to be set
 * @param upperBound upper boundary of temperature to be set
 */
	public Room(String name, String lowerBound, String upperBound) {
this.name = name;
this.lowerBound = lowerBound;
this.upperBound = upperBound;
humidity = "21";
carbonDioxide = "0";
methane = "0";
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the lowerBound
	 */
	public String getLowerBound() {
		return lowerBound;
	}

	/**
	 * @param lowerBound the lowerBound to set
	 */
	public void setLowerBound(String lowerBound) {
		this.lowerBound = lowerBound;
	}

	/**
	 * @return the upperBound
	 */
	public String getUpperBound() {
		return upperBound;
	}

	/**
	 * @param upperBound the upperBound to set
	 */
	public void setUpperBound(String upperBound) {
		this.upperBound = upperBound;
	}

	/**
	 * @return the humidity
	 */
	public String getHumidity() {
		return humidity;
	}

	/**
	 * @return the carbonDioxide
	 */
	public String getCarbonDioxide() {
		return carbonDioxide;
	}

	/**
	 * @return the methane
	 */
	public String getMethane() {
		return methane;
	}

}
