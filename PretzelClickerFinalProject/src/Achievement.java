import java.util.ArrayList;

public abstract class Achievement {
	
	private String name;
	private String conditionText;
	
	public static ArrayList<Achievement> achievementList = new ArrayList<Achievement>(0);
	public static ArrayList<Achievement> completedAchievements = new ArrayList<Achievement>(0);
	
	private static boolean gotBiblicallyAccurate = false;
	private static boolean gotWeezer = false;
	
	public Achievement(String name, String conditionText) {
		this.name = name;
		this.conditionText = conditionText;
		//achievementList.add(this);
	}
	
	//checks that achievement is completed
	public abstract boolean condition();
	
	//adds achievements
	public static void loadAchievements() {
		achievementList.add(new ACHstart("Getting Started", "Bake 1 pretzel"));
		achievementList.add(new ACHbibliclyAccurate("Biblically Accurate Pretzel", "Get the final pretzel upgrade"));
		achievementList.add(new ACHweezer("Weezer's Pretzels", "Input the Konami code easter egg"));
	}
	
	//loop through each uncompleted achievement, removing them once they are completed
	public static void checkCompletion() {

		for(int i = achievementList.size()-1; i >= 0; i--) {
			if(achievementList.get(i).condition()) {
				achievementList.get(i).complete();
			}
		}
	}
	
	//achievements that activate when specific things happen call this
	public static void achieveFlag(String flag) {
		switch(flag) {
		
		case "Weezer":
			gotWeezer = true;
			break;
		case "Bible":
			gotBiblicallyAccurate = true;
			break;
			
		}
	}
	public static boolean getFlag(String flag) {
		switch(flag) {
		
		case "Weezer":
			return gotWeezer;
		
		case "Bible":
			return gotBiblicallyAccurate;
			
		}
		
		//if anything doesnt work, return false
		return false;
	}
	
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
