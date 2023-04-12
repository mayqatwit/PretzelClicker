
public class ACHbibliclyAccurate extends Achievement {

	public ACHbibliclyAccurate(String name, String conditionText) {
		super(name, conditionText);
		setName("Bible");
	}

	@Override
	public boolean condition() {
		
		return getFlag(getName());
	}

}
