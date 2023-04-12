
public class ACHclick extends Achievement {

	public ACHclick(String name, String conditionText) {
		super(name, conditionText);
		setName("Click");
	}

	@Override
	public boolean condition() {
		if(Player.getClickValue() >= 1024 || getFlag(getName())) {
			return true;
		} else {
			return false;
		}
	}

}
