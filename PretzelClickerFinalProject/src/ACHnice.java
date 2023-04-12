
public class ACHnice extends Achievement {

	public ACHnice(String name, String conditionText) {
		super(name, conditionText);
	}

	@Override
	public boolean condition() {
		if(Player.getPretzels() == 69) {
			return true;
		} else {
			return false;
		}
	}

}
