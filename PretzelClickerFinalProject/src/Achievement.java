import java.util.ArrayList;

public abstract class Achievement {
	
	private String name;
	private String conditionText;
	
	public static ArrayList<Achievement> achievementList = new ArrayList<Achievement>(0);
	public static ArrayList<Achievement> completedAchievements = new ArrayList<Achievement>(0);
	
	public Achievement(String name, String conditionText) {
		this.name = name;
		this.conditionText = conditionText;
		achievementList.add(this);
	}
	
	//checks that achievement is completed
	public abstract boolean condition();
	
	public void complete() {
		completedAchievements.add(this);
		achievementList.remove(this);
	}
	
	//returns full list of completed achievements
	public static String getList() {
		String out = "";
		
		for(Achievement a : completedAchievements) {
			out = String.format("%s%s%n", out, a);
		}
		return out;
	}
	
	//returns full list of uncompleted achievements
	public static String getUnfinishedList() {
		String out = "";
		
		for(Achievement a : achievementList) {
			out = String.format("%s%s%n", out, a);
		}
		return out;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s", name, conditionText);
	}
}
