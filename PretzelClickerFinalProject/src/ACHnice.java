
public class ACHnice extends Achievement {

	public ACHnice(String name, String conditionText) {
		super(name, conditionText);
		setName("Nice");
	}

	@Override
	public boolean condition() {
		if(Math.floor(Player.getPretzels()) == 69 || getFlag(getName())) {
			return true;
		} else {
			return false;
		}
	}

}
