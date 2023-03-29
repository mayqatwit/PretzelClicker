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

	public static void buyBuilding(Object o) {
		o = (Building) o;
	}

	public static double getPretzels() {
		return pretzels;
	}

	public static double getTotalPretzels() {
		return totalPretzels;
	}

	public static double getPPS() {
		return PPS;
	}

	public static double getClickValue() {
		return clickValue;
	}

	public static int getBuildings() {
		return buildings;
	}

	public static int getUpgrades() {
		return upgrades;
	}

	public static void updatePretzels(double s) {
		Player.pretzels = Player.getPretzels() + s;
	}

	public static void updateTotalPretzels(double s) {
		totalPretzels += s;
	}

	public static void updatePPS(double s) {
		PPS += s;
	}

	public static void setPPS(double s) {
		PPS = s;
	}

	public static void updateClickValue(double s) {
		clickValue += s;
	}

	public static void updateBuildings(int s) {
		buildings += s;
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
