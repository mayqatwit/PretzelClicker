import javafx.scene.control.Button;

public class Grandpa implements Buildable {

	private static double PPS = 8;
	private static double cost = 1100.0;
	private static final double BASE_COST = 1100.0;
	private static int numGrandpas = 0;
	private static double myGrandpaPPS = 0.0;
	private static Button graphic = new Button(String.format("%d Grandpas%nCost: %,.0f", numGrandpas, cost));
	private static int upgrades = 0;
	private static int[] upgradeCosts = {100,500,10000,50000};
	
	public Grandpa() {
		buyBuilding();
	}
		
	public Grandpa(int i) {
		upgrade();
	}
	
	@Override
	public void buyBuilding() {
		Player.updatePretzels(-Grandpa.getCost());

		Grandpa.updateNumGrandpas(1);
		Player.updateBuildings(1);

		Grandpa.updateMyGrandpaPPS(Grandpa.getPPS());
		Player.updatePPS(PPS);

		Grandpa.setCost((double) Math.round(BASE_COST * Math.pow(1.15, numGrandpas)));
		
	}

	@Override
	public void upgrade() {
		Grandpa.updateUpgrades(1);
		Grandpa.setMyGrandpaPPS(Grandpa.getMyGrandpaPPS() * 2);
		Grandpa.setPPS(Grandpa.getPPS() * 2);
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
		Grandpa.cost = cost;
	}

	public static int getNumGrandpas() {
		return numGrandpas;
	}

	public static void setNumGrandpas(int num) {
		Grandpa.numGrandpas = num;
	}
	
	public static void updateNumGrandpas(int num) {
		Grandpa.numGrandpas += num;
	}

	public static double getMyGrandpaPPS() {
		return myGrandpaPPS;
	}

	public static void setMyGrandpaPPS(double d) {
		Grandpa.myGrandpaPPS = d;
	}
	
	public static void updateMyGrandpaPPS(double d) {
		Grandpa.myGrandpaPPS += d;
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
