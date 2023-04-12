
public class ACHbuild extends Achievement {

	public ACHbuild(String name, String conditionText) {
		super(name, conditionText);
	}

	@Override
	public boolean condition() {
		if(Player.getBuildings() > 0) {
			return true;
		} else {
			return false;
		}
	}

}
