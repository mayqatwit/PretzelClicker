
public class ACHbibliclyAccurate extends Achievement {

	public ACHbibliclyAccurate(String name, String conditionText) {
		super(name, conditionText);
		
	}

	@Override
	public boolean condition() {
		
		return getFlag("Bible");
	}

}
