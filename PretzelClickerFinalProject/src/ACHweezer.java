
public class ACHweezer extends Achievement {

	public ACHweezer(String name, String conditionText) {
		super(name, conditionText);
	}

	@Override
	public boolean condition() {
		return getFlag("Weezer");
	}

}
