import javafx.scene.control.Button;

public class Mine implements Buildable {

	private static double PPS = 47;
	private static double cost = 12000.0;
	private static final double BASE_COST = 12000.0;
	private static int numMines = 0;
	private static double myMinePPS = 0.0;
	private static Button graphic = new Button(String.format("%d Mines%nCost: %,.0f", numMines, cost));
	private static int upgrades = 0;
	private static double[] upgradeCosts = {120000,600000,6000000,600000000};
	
	public Mine() {
		buyBuilding();
	}
		
	public Mine(int i) {
		upgrade();
	}
	
	@Override
	public void buyBuilding() {
		Player.updatePretzels(-Mine.getCost());

		Mine.updateNumMines(1);
		Player.updateBuildings(1);

		Mine.updateMyMinePPS(Mine.getPPS());
		Player.updatePPS(PPS);

		Mine.setCost((double) Math.round(BASE_COST * Math.pow(1.15, numMines)));
		
	}

	@Override
	public void upgrade() {
		Mine.updateUpgrades(1);
		Mine.setMyMinePPS(Mine.getMyMinePPS() * 2);
		Mine.setPPS(Mine.getPPS() * 2);
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
		Mine.cost = cost;
	}

	public static int getNumMines() {
		return numMines;
	}

	public static void setNumMines(int num) {
		Mine.numMines = num;
	}
	
	public static void updateNumMines(int num) {
		Mine.numMines += num;
	}

	public static double getMyMinePPS() {
		return myMinePPS;
	}

	public static void setMyMinePPS(double d) {
		Mine.myMinePPS = d;
	}
	
	public static void updateMyMinePPS(double d) {
		Mine.myMinePPS += d;
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
