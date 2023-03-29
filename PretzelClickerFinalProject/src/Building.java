import javafx.scene.control.Button;

/**
 * Abstract class Building, this class is an abstract class that all other
 * building types inherit from. This class gives all basic getters and setters
 * for the child classes to use, and also has a method to buy a new building.
 * 
 * @author Quentyn May
 *
 */
public abstract class Building {

	private static double PPS;
	private static double cost;
	private static int BASE_COST;
	private static int numBuildings;
	private static double myPPS;
	private static Button graphic;

	/**
	 * Constructor for abstract class Building
	 * 
	 * @param PPS
	 * @param cost
	 * @param baseCost
	 * @param numBuildings
	 * @param myPPS
	 */
	public Building(double PPS, double cost, int baseCost, int numBuildings, double myPPS, String text) {
		Building.PPS = PPS;
		Building.cost = cost;
		Building.BASE_COST = baseCost;
		Building.updateMyPPS(myPPS);
		graphic = new Button(text);
	}

	public static void buyBuilding() {
			Player.updatePretzels(-Building.getCost());

			Building.updateNumBuildings(1);
			Player.updateBuildings(1);

			Building.updateMyPPS(Building.getPPS());
			Player.updatePPS(Building.getPPS());

			Building.setCost((double) Math.round(Building.getBASE_COST() * Math.pow(1.15, Building.getNumBuildings())));

	}

	public static int getNumBuildings() {
		return numBuildings;
	}

	public static void updateNumBuildings(int i) {
		numBuildings = numBuildings + i;
	}

	public static double getPPS() {
		return PPS;
	}

	public static double getCost() {
		return (double) Math.round(Building.getBASE_COST() * Math.pow(1.15, Building.getNumBuildings()));
	}

	public static void setCost(double cost) {
		Building.cost = cost;
	}

	public static int getBASE_COST() {
		return BASE_COST;
	}

	public static double getMyPPS() {
		return myPPS;
	}

	public static void updateMyPPS(double i) {
		Building.myPPS += i;
	}

	public static Button getGraphic() {
		return graphic;
	}

	public static void setGraphic(String text) {
		Building.getGraphic().requestFocus();
		Building.graphic.setText(text);
	}

}
