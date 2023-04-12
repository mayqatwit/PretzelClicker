
public class ACHsave extends Achievement {

	public ACHsave(String name, String conditionText) {
		super(name, conditionText);
		setName("Save");
	}

	@Override
	public boolean condition() {
		return getFlag(getName());
	}

}
