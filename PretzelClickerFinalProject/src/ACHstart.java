
public class ACHstart extends Achievement {

	public ACHstart(String name, String conditionText) {
		super(name, conditionText);
		setName("Start");
	}

	@Override
	public boolean condition() {
		if(Player.getPretzels() >= 1 || getFlag(getName())) {
			return true;
		} else {
			return false;
		}
	}

}
