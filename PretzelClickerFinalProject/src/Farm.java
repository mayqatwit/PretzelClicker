import javafx.scene.control.Button;

public class Farm implements Buildable{

	private static double PPS = 0.1;
	private static double cost = 15.0;
	private static final double BASE_COST = 15.0;
	private static int numFarms = 0;
	private static double myFarmPPS = 0.0;
	private static Button graphic = new Button(String.format("%d Farms%nCost: %,.0f", numFarms, cost));
	private static int upgrades = 0;
	private static int[] upgradeCosts = {100,500,10000,50000};
	
	public Farm() {
		buyBuilding();
	}
	
	public Farm(int i) {
		upgrade();
	}
	
	@Override
	public void buyBuilding() {
		Player.updatePretzels(-Farm.getCost());

		Farm.updateNumFarms(1);
		Player.updateBuildings(1);

		Farm.updateMyFarmPPS(Farm.getPPS());
		Player.updatePPS(Farm.getPPS());

		Farm.setCost((double) Math.round(BASE_COST * Math.pow(1.15, numFarms)));
		
	}

	@Override
	public void upgrade() {
		Farm.updateUpgrades(1);
		Farm.setMyFarmPPS(Farm.getMyFarmPPS() * 2);
		Farm.setPPS(Farm.getPPS() * 2);
		Player.setClickValue(Player.getClickValue() * 2);
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
		Farm.cost = cost;
	}

	public static int getNumFarms() {
		return numFarms;
	}

	public static void setNumFarms(int num) {
		Farm.numFarms = num;
	}
	
	public static void updateNumFarms(int num) {
		Farm.numFarms += num;
	}

	public static double getMyFarmPPS() {
		return myFarmPPS;
	}

	public static void setMyFarmPPS(double d) {
		Farm.myFarmPPS = d;
	}
	
	public static void updateMyFarmPPS(double d) {
		Farm.myFarmPPS += d;
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

	public static int getUpgradeCost() {
		return upgradeCosts[upgrades];
	}

}
