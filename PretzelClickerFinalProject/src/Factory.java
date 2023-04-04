import javafx.scene.control.Button;

public class Factory implements Buildable {
	private static double PPS = 260;
	private static double cost = 130000.0;
	private static final double BASE_COST = 130000.0;
	private static int numFactories = 0;
	private static double myFactoriesPPS = 0.0;
	private static Button graphic = new Button(String.format("%d Factories%nCost: %,.0f", numFactories, cost));
	private static int upgrades = 0;
	private static double[] upgradeCosts = {1300000,6500000,65000000,65000000000.0};
	
	public Factory() {
		buyBuilding();
	}
		
	public Factory(int i) {
		upgrade();
	}
	
	@Override
	public void buyBuilding() {
		Player.updatePretzels(-Factory.getCost());

		Factory.updateNumFactories(1);
		Player.updateBuildings(1);

		Factory.updateMyFactoriesPPS(Factory.getPPS());
		Player.updatePPS(PPS);

		Factory.setCost((double) Math.round(BASE_COST * Math.pow(1.15, numFactories)));
		
	}

	@Override
	public void upgrade() {
		Factory.updateUpgrades(1);
		Factory.setMyFactoriesPPS(Factory.getMyFactoriesPPS() * 2);
		Factory.setPPS(Factory.getPPS() * 2);
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
		Factory.cost = cost;
	}

	public static int getNumFactories() {
		return numFactories;
	}

	public static void setNumFactories(int num) {
		Factory.numFactories = num;
	}
	
	public static void updateNumFactories(int num) {
		Factory.numFactories += num;
	}

	public static double getMyFactoriesPPS() {
		return myFactoriesPPS;
	}

	public static void setMyFactoriesPPS(double d) {
		Factory.myFactoriesPPS = d;
	}
	
	public static void updateMyFactoriesPPS(double d) {
		Factory.myFactoriesPPS += d;
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
		try {
		return upgradeCosts[upgrades];
		} catch (ArrayIndexOutOfBoundsException e) {
			return 0;
		}	}
}
