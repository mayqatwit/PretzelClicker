
public class ACHbuild extends Achievement {

	public ACHbuild(String name, String conditionText) {
		super(name, conditionText);
		setName("Build");
	}

	@Override
	public boolean condition() {
		if(Player.getBuildings() > 0 || getFlag(getName())) {
			return true;
		} else {
			return false;
		}
	}

}
