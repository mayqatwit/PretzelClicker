import javafx.scene.control.Button;

public class Lab implements Buildable{

	private static double PPS = 1400;
	private static double cost = 1400000.0;
	private static final double BASE_COST = 1400000.0;
	private static int numLabs = 0;
	private static double myLabPPS = 0.0;
	private static Button graphic = new Button(String.format("%d Labs%nCost: %,.0f", numLabs, cost));
	private static int upgrades = 0;
	private static double[] upgradeCosts = {14000000,70000000,700000000,70000000000.0};
	
	public Lab() {
		buyBuilding();
	}
		
	public Lab(int i) {
		upgrade();
	}
	
	@Override
	public void buyBuilding() {
		Player.updatePretzels(-Lab.getCost());

		Lab.updateNumLabs(1);
		Player.updateBuildings(1);

		Lab.updateMyLabPPS(Lab.getPPS());
		Player.updatePPS(PPS);

		Lab.setCost((double) Math.round(BASE_COST * Math.pow(1.15, numLabs)));
		
	}

	@Override
	public void upgrade() {
		Lab.updateUpgrades(1);
		Lab.setMyLabPPS(Lab.getMyLabPPS() * 2);
		Lab.setPPS(Lab.getPPS() * 2);
		Player.updateUpgrades(1);
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
		Lab.cost = cost;
	}

	public static int getNumLabs() {
		return numLabs;
	}

	public static void setNumLabs(int num) {
		Lab.numLabs = num;
	}
	
	public static void updateNumLabs(int num) {
		Lab.numLabs += num;
	}

	public static double getMyLabPPS() {
		return myLabPPS;
	}

	public static void setMyLabPPS(double d) {
		Lab.myLabPPS = d;
	}
	
	public static void updateMyLabPPS(double d) {
		Lab.myLabPPS += d;
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
	
	public static void updateUpgrades(int i) {
		upgrades += i;
	}

	public static double getUpgradeCost() {
		return upgradeCosts[upgrades];
	}
	
}
