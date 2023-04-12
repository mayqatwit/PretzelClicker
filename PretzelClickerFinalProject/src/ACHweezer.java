
public class ACHweezer extends Achievement {

	public ACHweezer(String name, String conditionText) {
		super(name, conditionText);
		setName("Weezer");
	}

	@Override
	public boolean condition() {
		return getFlag(getName());
	}

}
