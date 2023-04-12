
public class ACHsave extends Achievement {

	public ACHsave(String name, String conditionText) {
		super(name, conditionText);
	}

	@Override
	public boolean condition() {
		return getFlag("Save");
	}

}
