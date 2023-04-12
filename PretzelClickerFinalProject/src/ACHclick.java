
public class ACHclick extends Achievement {

	public ACHclick(String name, String conditionText) {
		super(name, conditionText);
	}

	@Override
	public boolean condition() {
		if(Player.getClickValue() >= 1024) {
			return true;
		} else {
			return false;
		}
	}

}
