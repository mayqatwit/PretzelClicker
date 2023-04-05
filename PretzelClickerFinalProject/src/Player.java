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
	private static String[] images = {"sprites/Pretzel.png", "sprites/BakedPretzel.png", "sprites/ChocolatePretzel.png", "sprites/MarshmallowPretzel.png", "sprites/GoldPretzel.png", "sprites/BibliclyAccuratePretzel.png"};
	private static double[] imageCosts = {100000, 500000, 5000000, 20000000.0, 250000000.0};
	private static int imageUpgrades = 0;
	private static double multiplier = 1.0;
	private static double clickCost = 20000;

	public static void upgrade(double d) {
		Player.updatePretzels(-Player.getImageCost());
		Player.updateMultiplier(d);
		Player.updateImageUpgrades(1);
		Player.updateUpgrades(1);
	}
	
	/**
	 * 
	 * @return pretzels
	 */
	public static double getPretzels() {
		return pretzels;
	}
	
	/**
	 * Sets pretzels to s
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
	 * Sets totalPretzels equal to s
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
	
	/**
	 * Sets PPS equal to s
	 * @param s
	 */
	public static void setPPS(double s) {
		PPS = s;
	}

	/**
	 * Adds s to PPS
	 * @param s
	 */
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
	
	/**
	 * Sets clickValue equal to s
	 * @param s
	 */
	public static void setClickValue(double s) {
		clickValue = s;
	}

	/**
	 * Adds s to clickValue 
	 * @param s
	 */
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
	
	/**
	 * Sets buildings equal to i
	 * @param i
	 */
	public static void setBuildings(int i) {
		buildings = i;
	}
	
	/**
	 * Adds s to buildings
	 * @param s
	 */
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
	
	/**
	 * Sets upgrades equal to i
	 * @param i
	 */
	public static void setUpgrades(int i ) {
		upgrades = i;
	}
	
	/**
	 * Adds s to upgrades
	 * @param s
	 */
	public static void updateUpgrades(int s) {
		upgrades += s;
	}
	
	/**
	 * 
	 * @return stats
	 */
	public static String getStats() {
		return String.format(
				"Pretzels(s) in bank: %,.0f%nTotal Pretzels Baked: %,.0f%n"
						+ "PPS: %,.1f%nClick Value: %,.0f%nBuildings: %d%nUpgrades: %d%n",
				Player.getPretzels(), Player.getTotalPretzels(), Player.getPPS(), Player.getClickValue(),
				Player.getBuildings(), Player.getUpgrades());
	}
	
	public static String getImage() {
		return images[imageUpgrades];
	}
	
	public static int getImageUpgrades() {
		return imageUpgrades;
	}
	
	public static void setImageUpgrades(int i) {
		imageUpgrades = i;
	}
	
	public static void updateImageUpgrades(int i) {
		imageUpgrades += i;
	}
	
	public static double getImageCost() {
		try {
		return imageCosts[imageUpgrades];
		} catch (ArrayIndexOutOfBoundsException e) {
			return 0;
		}
	}

	public static double getMultiplier() {
		return multiplier;
	}

	public static void setMultiplier(double d) {
		multiplier = d;
	}
	
	public static void updateMultiplier(double d) {
		multiplier += d;
	}

	public static double getClickCost() {
		return clickCost;
	}

	public static void setClickCost(double d) {
		clickCost = d;
	}

}
