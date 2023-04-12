import java.util.ArrayList;
import java.util.Scanner;

public abstract class Achievement {
	
	private String name;
	private String nameShort;
	private String conditionText;
	
	public static ArrayList<Achievement> achievementList = new ArrayList<Achievement>(0);
	public static ArrayList<Achievement> completedAchievements = new ArrayList<Achievement>(0);
	
	//flag achievement booleans
	private static boolean gotStart = false;
	private static boolean gotBiblicallyAccurate = false;
	private static boolean gotWeezer = false;
	private static boolean gotNice = false;
	private static boolean gotBuild = false;
	private static boolean gotPPS = false;
	private static boolean gotClick = false;
	private static boolean gotSave = false;
	private static boolean gotUpgrade = false;
	
	public Achievement(String name, String conditionText) {
		this.name = name;
		this.conditionText = conditionText;
	}
	
	public String getName() {
		return nameShort;
	}
	
	public void setName(String in) {
		nameShort = in;
	}
	
	//checks that achievement is completed
	public abstract boolean condition();
	
	//adds achievements
	public static void loadAchievements() {
		achievementList.add(new ACHstart("Getting Started", "Bake 1 pretzel"));
		achievementList.add(new ACHbibliclyAccurate("Biblically Accurate Pretzel", "Get the final pretzel upgrade"));
		achievementList.add(new ACHweezer("Weezer's Pretzels", "Input the Konami code easter egg"));
		achievementList.add(new ACHnice("Nice", "Get exactly 69 pretzels"));
		achievementList.add(new ACHbuild("Do It For Me", "Buy a building"));
		achievementList.add(new ACHpps("Now We're Cooking", "Reach 100 Pretzels Per Second"));
		achievementList.add(new ACHclick("Big Clicks", "Upgrade click value to 1024"));
		achievementList.add(new ACHsave("Better Safe Than Sorry", "Save your progress"));
		achievementList.add(new ACHupgrade("Lets Make This Faster", "Buy an upgrade"));
		//achievementList.add(new ACH("", "")); //if i decide to add a 10th
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
		case "Save":
			gotSave = true;
			break;
		case "Start":
			gotStart = true;
			break;
		case "Nice":
			gotNice = true;
			break;
		case "Build":
			gotBuild = true;
			break;
		case "PPS":
			gotPPS = true;
			break;
		case "Click":
			gotClick = true;
			break;
		case "Upgrade":
			gotUpgrade = true;
			break;
		}
	}
	public static boolean getFlag(String flag) {
		switch(flag) {
		
		case "Weezer":
			return gotWeezer;
		
		case "Bible":
			return gotBiblicallyAccurate;
			
		case "Save":
			return gotSave;
			
		case "Start":
			return gotStart;
			
		case "Nice":
			return gotNice;
			
		case "Build":
			return gotBuild;
			
		case "PPS":
			return gotPPS;
			
		case "Click":
			return gotClick;
			
		case "Upgrade":
			return gotUpgrade;
			
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
	
	public static String print() {
		String out = "";
		for(Achievement a : completedAchievements) {
			out += String.format("%s%n", a.nameShort);
		}		
		return out;
	}
	
	public static void loadSave(Scanner s) {
		while(s.hasNext()) {
			String in = s.nextLine();
			
			switch(in) {
			
			case "Start":
				achieveFlag("Start");
				break;
			case "Bible":
				achieveFlag("Bible");
				break;
			case "Weezer":
				achieveFlag("Weezer");
				break;
			case "Nice":
				achieveFlag("Nice");
				break;
			case "Build":
				achieveFlag("Build");
				break;
			case "PPS":
				achieveFlag("PPS");
				break;
			case "Click":
				achieveFlag("Click");
				break;
			case "Save":
				achieveFlag("Save");
				break;
			case "Upgrade":
				achieveFlag("Upgrade");
				break;
			}
		}
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s%n", name, conditionText);
	}
}
