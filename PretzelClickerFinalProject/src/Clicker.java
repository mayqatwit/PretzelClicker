import javafx.scene.control.Button;

/**
 * Concrete Clicker class. This class is used to keep track of all Clicker
 * stats, and allows for buying and upgrading clickers.
 * 
 * @author Quentyn May
 *
 */
public class Clicker implements Buildable {

	private static double PPS = 0.1;
	private static double cost = 15.0;
	private static final double BASE_COST = 15.0;
	private static int numClickers = 0;
	private static double myClickerPPS = 0.0;
	private static Button graphic = new Button(String.format("%d Clickers%nCost: %,.0f", numClickers, cost));
	private static int upgrades = 0;

	public Clicker() {
		buyBuilding();
	}

	@Override
	public void buyBuilding() {
		Player.updatePretzels(-Clicker.getCost());

		Clicker.setNumClickers(1);
		Player.updateBuildings(1);

		Clicker.setMyClickerPPS(Clicker.getPPS());
		Player.updatePPS(Clicker.getPPS());

		Clicker.setCost((double) Math.round(BASE_COST * Math.pow(1.15, numClickers)));
		Clicker.setGraphic(String.format("%d Clickers%nCost: %,.0f", numClickers, cost));
	}

	@Override
	public void upgrade() {

	}

	public static double getPPS() {
		return PPS;
	}

	public static void setPPS(double d) {
		PPS = d;
	}

	public static double getCost() {
		return cost;
	}

	public static void setCost(double cost) {
		Clicker.cost = cost;
	}

	public static int getNumClickers() {
		return numClickers;
	}

	public static void setNumClickers(int num) {
		Clicker.numClickers += num;
	}

	public static double getMyClickerPPS() {
		return myClickerPPS;
	}

	public static void setMyClickerPPS(double d) {
		Clicker.myClickerPPS += d;
	}

	public static Button getGraphic() {
		return graphic;
	}

	public static void setGraphic(String text) {
		graphic.setText(text);
	}

	public static int getUpgrades() {
		return upgrades;
	}
	
	public static void setUpgrades(int i) {
		upgrades = i;
	}

}
