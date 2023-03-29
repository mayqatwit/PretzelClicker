/**
 * Concrete class for Player. This class keeps track of all player information,
 * such as pretzels, click value, and pretzels-per-second (PPS).
 * 
 * @author Quentyn May
 *
 */
public class Player {

	private static double pretzels = 0;
	private static double totalPretzels = 0;
	private static double clickValue = 1;
	private static int buildings = 0;
	private static int upgrades = 0;
	private static double PPS = 0;

	/**
	 * 
	 * @return pretzels
	 */
	public static double getPretzels() {
		return pretzels;
	}
	
	/**
	 * Sets pretzels to s, overriding what pretzels originally was
	 * @param s
	 */
	public static void setPretzels(double s) {
		pretzels = s;
	}

	/**
	 * Adds s to current pretzels
	 * @param s
	 */
	public static void updatePretzels(double s) {
		Player.pretzels = Player.getPretzels() + s;
	}

	/**
	 * 
	 * @return totalPretzels
	 */
	public static double getTotalPretzels() {
		return totalPretzels;
	}
	
	/**
	 * Sets totalPretzels to s, overriding what totalPretzels originally was
	 * @param s
	 */
	public static void setTotalPretzels(double s) {
		totalPretzels = s;
	}
	
	/**
	 * Adds s to current totalPretzels
	 * @param s
	 */
	public static void updateTotalPretzels(double s) {
		totalPretzels += s;
	}

	/**
	 * 
	 * @return PPS
	 */
	public static double getPPS() {
		return PPS;
	}
	
	public static void setPPS(double s) {
		PPS = s;
	}

	public static void updatePPS(double s) {
		PPS += s;
	}

	/**
	 * 
	 * @return clickValue
	 */
	public static double getClickValue() {
		return clickValue;
	}
	
	public static void setClickValue(double s) {
		clickValue = s;
	}

	public static void updateClickValue(double s) {
		clickValue += s;
	}

	/**
	 * 
	 * @return buildings
	 */
	public static int getBuildings() {
		return buildings;
	}
	
	public static void setBuildings(int i) {
		buildings = i;
	}

	public static void updateBuildings(int s) {
		buildings += s;
	}

	/**
	 * 
	 * @return upgrades
	 */
	public static int getUpgrades() {
		return upgrades;
	}
	
	public static void setUpgrades(int i ) {
		upgrades = i;
	}
	
	public static void updateUpgrades(int s) {
		upgrades += s;
	}

	public static String getStats() {
		return String.format(
				"Pretzels(s) in bank: %,.0f%nTotal Pretzels Baked: %,.0f%n"
						+ "PPS: %,.1f%nClick Value: %.0f%nBuildings: %d%nUpgrades: %d%n",
				Player.getPretzels(), Player.getTotalPretzels(), Player.getPPS(), Player.getClickValue(),
				Player.getBuildings(), Player.getUpgrades());
	}

}
