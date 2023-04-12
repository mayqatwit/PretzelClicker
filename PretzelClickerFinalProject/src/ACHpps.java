
public class ACHpps extends Achievement{

	public ACHpps(String name, String conditionText) {
		super(name, conditionText);
		setName("PPS");
	}

	@Override
	public boolean condition() {
		if(Player.getPPS() >= 100 || getFlag(getName())) {
			return true;
		} else {
			return false;
		}
	}

}
