
public class ACHstart extends Achievement {

	public ACHstart(String name, String conditionText) {
		super(name, conditionText);
		
	}

	@Override
	public boolean condition() {
		if(Player.getPretzels() == 1) {
			return true;
		} else {
			return false;
		}
	}

}
