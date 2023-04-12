
public class ACHupgrade extends Achievement {

	public ACHupgrade(String name, String conditionText) {
		super(name, conditionText);
	}

	@Override
	public boolean condition() {
		if(Player.getUpgrades() > 0) {
			return true;
		} else {
			return false;
		}
	}

}
