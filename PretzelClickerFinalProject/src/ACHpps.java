
public class ACHpps extends Achievement{

	public ACHpps(String name, String conditionText) {
		super(name, conditionText);
	}

	@Override
	public boolean condition() {
		if(Player.getPPS() >= 100) {
			return true;
		} else {
			return false;
		}
	}

}
