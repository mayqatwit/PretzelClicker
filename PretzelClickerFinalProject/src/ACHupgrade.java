
public class ACHupgrade extends Achievement {

	public ACHupgrade(String name, String conditionText) {
		super(name, conditionText);
		setName("Upgrade");
	}

	@Override
	public boolean condition() {
		if(Player.getUpgrades() > 0 || getFlag(getName())) {
			return true;
		} else {
			return false;
		}
	}

}
