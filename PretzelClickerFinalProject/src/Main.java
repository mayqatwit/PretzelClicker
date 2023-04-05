import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Main class for Pretzel clicker game. This class contains main, and all of the
 * methods required to run the pretzel clicker game.
 * 
 * @author Quentyn May
 * @author Andrew Giacalone
 *
 */
public class Main extends Application implements Initializable {

	@FXML
	Button saveButton; // Game functionality
	@FXML
	ImageView pretzelImage;
	@FXML
	Button resetButton;
	@FXML
	Button muteButton;
	@FXML
	VBox vbox;
	@FXML
	AnchorPane aPane;
	@FXML
	Text pretzelText = new Text(String.format("Pretzels: ,%f", Player.getPretzels()));
	@FXML
	Text PPSText;
	@FXML
	Text playerStats;
	@FXML
	Button clickerButton; // Building buttons
	@FXML
	Button grandpaButton;
	@FXML
	Button farmButton;
	@FXML
	Button mineButton;
	@FXML
	Button factoryButton;
	@FXML
	Button labButton;
	@FXML
	Button upgradeClicker; // Upgrade buttons
	@FXML
	Button upgradeGrandpa;
	@FXML
	Button upgradeFarm;
	@FXML
	Button upgradeMine;
	@FXML
	Button upgradeFactory;
	@FXML
	Button upgradeLab;
	@FXML
	Button upgradeClick;
	@FXML
	Button bakedUpgrade;
	@FXML
	Button chocolateUpgrade;
	@FXML
	Button marshmallowUpgrade;
	@FXML
	Button goldUpgrade;
	@FXML
	Button bibleUpgrade;

	boolean mute = false;

	boolean leftWasClicked = false; // This is used to only left click pretzel once

	Clip music = playSound("sounds/BackgroundMusic.wav");

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		loadSave(new Scanner(new File("Save")));

		Parent p = FXMLLoader.load(getClass().getResource("PretzelClickerFXML.fxml"));

		Scene root = new Scene(p);
		primaryStage.setScene(root);
		primaryStage.setTitle("Pretzel Clicker");
		primaryStage.getIcons().add(new Image("sprites/Pretzel.png"));
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		vbox.setBackground(new Background(new BackgroundImage(
				new Image("sprites/PretzelBackground.jpg", 445, 662, false, true), BackgroundRepeat.REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
		aPane.setBackground(new Background(new BackgroundImage(
				new Image("sprites/StatsBackground.png", 310, 670, false, true), BackgroundRepeat.REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
		pretzelImage.setImage(new Image(Player.getImage()));
		
		music.start();
		music.loop(Clip.LOOP_CONTINUOUSLY);

		// Running a time-line to add pretzels every 0.01 seconds
		// base on the current PPS, using 0.01 seconds to make the pretzels
		// go up smoothly over time
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), e -> {
			Player.updatePretzels(Player.getPPS() / 10);
			Player.updateTotalPretzels(Player.getPPS() / 10);
			updatePretzels();
			updatePPS();
			updateButtons();
			playerStats.setText(Player.getStats());
		}));

		// Have the time-line run indefinitely and start it
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

		pretzelImage.setOnMousePressed((e) -> { // Clicking the pretzel

			if (e.isPrimaryButtonDown()) { // Check to see if the click was a left click

				if (!leftWasClicked) { // Make sure only one click is being registered

					Player.updatePretzels(Player.getClickValue());
					Player.updateTotalPretzels(Player.getClickValue());
					pretzelImage.setScaleX(pretzelImage.getScaleX() + 0.1);
					pretzelImage.setScaleY(pretzelImage.getScaleY() + 0.1);
					updatePretzels();
					Clip click = playSound("sounds/ClickSound.wav");
					if (click != null)
						click.start();

					// Used for when you release the mouse, tells the method that
					// it was the left mouse that was clicked
					leftWasClicked = true;
				}
			}
		});

		pretzelImage.setOnMouseReleased((e) -> { // Releasing click on the pretzel
			if (leftWasClicked) { // Check to see if it was the left mouse that was released
				pretzelImage.setScaleX(pretzelImage.getScaleX() - 0.1);
				pretzelImage.setScaleY(pretzelImage.getScaleY() - 0.1);
				leftWasClicked = false;
			}
		});

		/*
		 * Clicker buttons
		 */
		// Buying a clicker
		clickerButton.setOnAction(e -> {
			if (Player.getPretzels() >= Clicker.getCost()) {
				new Clicker();

				updatePretzels();
				clickerButton.setText(
						String.format("%d Clickers%nCost: %,.0f", Clicker.getNumClickers(), Clicker.getCost()));
				click();
			}
		});

		// Upgrade clickers
		makeToolTip(upgradeClicker,
				String.format("Doubles clickers PPS and click value%nCost: %,.0f", Clicker.getUpgradeCost()));
		upgradeClicker.setOnAction(e -> {
			if (Player.getPretzels() >= Clicker.getUpgradeCost()) {
				Player.updatePretzels(-Clicker.getUpgradeCost());
				click();
				new Clicker(1);
				if (Clicker.getUpgrades() == 4) {
					disappearAnimation(upgradeClicker);
				} else
					makeToolTip(upgradeClicker,
							String.format("Doubles clickers PPS and click value%nCost: %,.0f", Clicker.getUpgradeCost()));
			}
		});

		/*
		 * Grandpa buttons
		 */
		// Buying a grandpa
		grandpaButton.setOnAction(e -> {
			if (Player.getPretzels() >= Grandpa.getCost()) {
				new Grandpa();

				updatePretzels();
				grandpaButton.setText(
						String.format("%d Grandpas%nCost: %,.0f", Grandpa.getNumGrandpas(), Grandpa.getCost()));
				click();
			}
		});

		// Upgrade grandpas
		makeToolTip(upgradeGrandpa, String.format("Doubles Grandpa PPS%nCost: %,.0f", Grandpa.getUpgradeCost()));
		upgradeGrandpa.setOnAction(e -> {
			if (Player.getPretzels() >= Grandpa.getUpgradeCost()) {
				Player.updatePretzels(-Grandpa.getUpgradeCost());
				click();
				new Grandpa(1);
				if (Grandpa.getUpgrades() == 4) {
					disappearAnimation(upgradeGrandpa);
				} else
				makeToolTip(upgradeGrandpa,
						String.format("Doubles Grandpa PPS%nCost: %,.0f", Grandpa.getUpgradeCost()));
			}
		});

		/*
		 * Farm buttons
		 */

		// Buying a farm
		farmButton.setOnAction(e -> {
			if (Player.getPretzels() >= Farm.getCost()) {
				new Farm();

				updatePretzels();
				farmButton.setText(String.format("%d Farms%nCost: %,.0f", Farm.getNumFarms(), Farm.getCost()));
				click();
			}
		});

		// Upgrade farms
		makeToolTip(upgradeFarm, String.format("Doubles Farm PPS%nCost: %,.0f", Farm.getUpgradeCost()));
		upgradeFarm.setOnAction(e -> {
			if (Player.getPretzels() >= Farm.getUpgradeCost()) {
				Player.updatePretzels(-Farm.getUpgradeCost());
				click();
				new Farm(1);
				if (Farm.getUpgrades() == 4) {
					disappearAnimation(upgradeFarm);
				} else
				makeToolTip(upgradeFarm, String.format("Doubles Farm PPS%nCost: %,.0f", Farm.getUpgradeCost()));
			}
		});

		/*
		 * Mine buttons
		 */
		// Buying a mine
		mineButton.setOnAction(e -> {
			if (Player.getPretzels() >= Mine.getCost()) {
				new Mine();

				updatePretzels();
				mineButton.setText(String.format("%d Mines%nCost: %,.0f", Mine.getNumMines(), Mine.getCost()));
				click();
			}
		});
		
		// Upgrade mines
		makeToolTip(upgradeMine, String.format("Doubles Mine PPS%nCost: %,.0f", Mine.getUpgradeCost()));
		upgradeMine.setOnAction(e -> {
			if (Player.getPretzels() >= Mine.getUpgradeCost()) {
				Player.updatePretzels(-Mine.getUpgradeCost());
				click();
				new Mine(1);
				if (Mine.getUpgrades() == 4) {
					disappearAnimation(upgradeMine);
				} else
				makeToolTip(upgradeMine, String.format("Doubles Mine PPS%nCost: %,.0f", Mine.getUpgradeCost()));
			}
		});

		/*
		 * Factory buttons
		 */
		factoryButton.setOnAction(e -> {
			if (Player.getPretzels() >= Factory.getCost()) {
				new Factory();

				updatePretzels();
				factoryButton.setText(
						String.format("%d Factories%nCost: %,.0f", Factory.getNumFactories(), Factory.getCost()));
				click();
			}
		});
		
		// Upgrade factories
		makeToolTip(upgradeFactory, String.format("Doubles Factory PPS%nCost: %,.0f", Factory.getUpgradeCost()));
		upgradeFactory.setOnAction(e -> {
			if (Player.getPretzels() >= Factory.getUpgradeCost()) {
				Player.updatePretzels(-Factory.getUpgradeCost());
				click();
				new Factory(1);
				if (Factory.getUpgrades() == 4) {
					disappearAnimation(upgradeFactory);
				} else
				makeToolTip(upgradeFactory, String.format("Doubles Factory PPS%nCost: %,.0f", Factory.getUpgradeCost()));
			}
		});

		/*
		 * Lab buttons
		 */
		labButton.setOnAction(e -> {
			if (Player.getPretzels() >= Lab.getCost()) {
				new Lab();

				updatePretzels();
				labButton.setText(String.format("%d Labratories%nCost: %,.0f", Lab.getNumLabs(), Lab.getCost()));
				click();
			}
		});
		
		// Upgrade labs
		makeToolTip(upgradeLab, String.format("Doubles Labratory PPS%nCost: %,.0f", Lab.getUpgradeCost()));
		upgradeLab.setOnAction(e -> {
			if (Player.getPretzels() >= Lab.getUpgradeCost()) {
				Player.updatePretzels(-Lab.getUpgradeCost());
				click();
				new Lab(1);
				if (Lab.getUpgrades() == 4) {
					disappearAnimation(upgradeLab);
				} else
				makeToolTip(upgradeLab, String.format("Doubles Labratory PPS%nCost: %,.0f", Lab.getUpgradeCost()));
			}
		});
		
		/*
		 * General Upgrades
		 */
		
		makeToolTip(upgradeClick, String.format("Double your click value%nCost: %,.0f", Player.getClickCost()));
		upgradeClick.setOnAction(e ->{
			if(Player.getPretzels() >= Player.getClickCost()) {
				Player.updatePretzels(-Player.getClickCost());
				Player.updateClickValue(Player.getClickValue());
				Player.setClickCost(2*Player.getClickCost());
				Player.updateUpgrades(1);
				makeToolTip(upgradeClick, String.format("Double your click value%nCost: %,.0f", Player.getClickCost()));
			}
		});
		
		makeToolTip(bakedUpgrade, String.format("Increase your PPS by 5%s %nCost: %,.0f", "%", Player.getImageCost()));
		bakedUpgrade.setOnAction(e ->{
			if (Player.getPretzels() >= Player.getImageCost()) {
				Player.updatePretzels(-Player.getImageCost());
				Player.updateMultiplier(0.05);
				Player.updateImageUpgrades(1);
				pretzelImage.setImage(new Image(Player.getImage()));
				disappearAnimation(bakedUpgrade);
				makeToolTip(chocolateUpgrade, String.format("Increase your PPS by 5%s %nCost: %,.0f", "%", Player.getImageCost()));

			}
		});
		
		makeToolTip(chocolateUpgrade, String.format("Baked pretzel upgrade%nRequired!"));
		chocolateUpgrade.setOnAction(e ->{
			if (Player.getPretzels() >= Player.getImageCost() && Player.getImageUpgrades() == 1) {
				Player.upgrade(0.05);
				pretzelImage.setImage(new Image(Player.getImage()));
				disappearAnimation(chocolateUpgrade);
				makeToolTip(marshmallowUpgrade, String.format("Increase your PPS by 5%s %nCost: %,.0f", "%", Player.getImageCost()));

			}
		});
		
		makeToolTip(marshmallowUpgrade, String.format("Chocolate drizzle upgrade%nRequired!"));
		marshmallowUpgrade.setOnAction(e ->{
			if (Player.getPretzels() >= Player.getImageCost() && Player.getImageUpgrades() == 2) {
				Player.upgrade(0.05);
				pretzelImage.setImage(new Image(Player.getImage()));
				disappearAnimation(marshmallowUpgrade);
				makeToolTip(goldUpgrade, String.format("Increase your PPS by 10%s %nCost: %,.0f", "%", Player.getImageCost()));

			}
		});
		
		makeToolTip(goldUpgrade, String.format("Marshmallow drizzle upgrade%nRequired!"));
		goldUpgrade.setOnAction(e ->{
			if (Player.getPretzels() >= Player.getImageCost() && Player.getImageUpgrades() == 3) {
				Player.upgrade(0.1);
				pretzelImage.setImage(new Image(Player.getImage()));
				disappearAnimation(goldUpgrade);
				makeToolTip(bibleUpgrade, String.format("Increase your PPS by 25%s %nCost: %,.0f", "%", Player.getImageCost()));

			}
		});
		
		makeToolTip(bibleUpgrade, String.format("Gold pretzel upgrade%nRequired!"));
		bibleUpgrade.setOnAction(e ->{
			if (Player.getPretzels() >= Player.getImageCost() && Player.getImageUpgrades() == 4) {
				Player.upgrade(0.25);
				pretzelImage.setImage(new Image(Player.getImage()));
				disappearAnimation(bibleUpgrade);
				music.stop();
				music = playSound("sounds/FinalMusic.wav");
				music.start();
			}
		});


		/*
		 * Game functionality buttons
		 */
		muteButton.setOnAction(e -> {
			if (mute) {
				mute = false;
				muteButton.setText("Mute");
				music.start();
				music.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				mute = true;
				muteButton.setText("Unmute");
				music.stop();
			}
		});

		saveButton.setOnAction(e -> { // This is used to save stats to a save file
			saveGame();
			saveButton.setText("Saved!");
			click();
		});

		resetButton.setOnMouseEntered(e -> { // Make the background red if the mouse hovers over
			resetButton.setStyle("-fx-background-color: #FF0000;");
		});
		resetButton.setOnMouseExited(e -> { // Set it back to default color
			resetButton.setStyle("-fx-background-color: #d0d0d0;");
		});

		resetButton.setOnAction(e -> { // Sets stats to BlankSave and saves the game
			try {
				loadSave(new Scanner(new File("BlankSave")));
				saveGame();
				pretzelImage.setImage(new Image(Player.getImage()));
				click();
				System.exit(0);
			} catch (FileNotFoundException e1) {
			}
		});

		
		// Easter egg :)
		vbox.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.W)
				vbox.setOnKeyPressed(f -> {
					if (f.getCode() == KeyCode.W)
						vbox.setOnKeyPressed(g -> {
							if (g.getCode() == KeyCode.S)
								vbox.setOnKeyPressed(h -> {
									if (h.getCode() == KeyCode.S)
										vbox.setOnKeyPressed(i -> {
											if (i.getCode() == KeyCode.A)
												vbox.setOnKeyPressed(j -> {
													if (j.getCode() == KeyCode.D)
														vbox.setOnKeyPressed(k -> {
															if (k.getCode() == KeyCode.A)
																vbox.setOnKeyPressed(l -> {
																	if (l.getCode() == KeyCode.D)
																		vbox.setOnKeyPressed(m -> {
																			if (m.getCode() == KeyCode.B)
																				vbox.setOnKeyPressed(n -> {
																					if (n.getCode() == KeyCode.A) {
																						Clip egg = playSound(
																								"sounds/EasterEgg.wav");
																						egg.start();
																						pretzelImage.setImage(new Image(
																								"sprites/EasterEgg.png"));

																					}
																				});
																		});

																});
														});
												});
										});

								});

						});
				});

		});

		// Getting rid of pretzel upgrades if they have been purchased
		checkManaged();
	}

	private void checkManaged() {
		if(Player.getImageUpgrades() > 0) { 
			bakedUpgrade.setManaged(false);
			makeToolTip(chocolateUpgrade, String.format("Increase your PPS by 5%s %nCost: %,.0f", "%", Player.getImageCost()));
			if(Player.getImageUpgrades() > 1) {
				chocolateUpgrade.setManaged(false);
				makeToolTip(marshmallowUpgrade, String.format("Increase your PPS by 5%s %nCost: %,.0f", "%", Player.getImageCost()));
				if(Player.getImageUpgrades() > 2) {
					marshmallowUpgrade.setManaged(false);
					makeToolTip(goldUpgrade, String.format("Increase your PPS by 10%s %nCost: %,.0f", "%", Player.getImageCost()));
					if(Player.getImageUpgrades() > 3) {
						goldUpgrade.setManaged(false);
						makeToolTip(bibleUpgrade, String.format("Increase your PPS by 25%s %nCost: %,.0f", "%", Player.getImageCost()));
						if(Player.getImageUpgrades() > 4) {
							bibleUpgrade.setManaged(false);
							music.stop();
							music = playSound("sounds/FinalMusic.wav");
							music.start();
						}
					}
				}
			}
		}

		if (Clicker.getUpgradeCost() == 0)
			upgradeClicker.setManaged(false);
		if (Grandpa.getUpgradeCost() == 0)
			upgradeGrandpa.setManaged(false);
		if (Farm.getUpgradeCost() == 0)
			upgradeFarm.setManaged(false);
		if (Mine.getUpgradeCost() == 0)
			upgradeMine.setManaged(false);
		if (Factory.getUpgradeCost() == 0)
			upgradeFactory.setManaged(false);
		if (Lab.getUpgradeCost() == 0)
			upgradeLab.setManaged(false);
		
	}

	/**
	 * This method plays a click sound as long as the game is not muted
	 */
	private void click() {
		Clip click = playSound("sounds/ButtonClick.wav");
		if (click != null)
			click.start();

	}

	/**
	 * This method updates all of the text in each of the buttons used for buying
	 * new buildings
	 */
	private void updateButtons() {
		clickerButton.setText(String.format("%d Clickers%nCost: %,.0f", Clicker.getNumClickers(), Clicker.getCost()));
		grandpaButton.setText(String.format("%d Grandpas%nCost: %,.0f", Grandpa.getNumGrandpas(), Grandpa.getCost()));
		farmButton.setText(String.format("%d Farms%nCost: %,.0f", Farm.getNumFarms(), Farm.getCost()));
		mineButton.setText(String.format("%d Mines%nCost: %,.0f", Mine.getNumMines(), Mine.getCost()));
		factoryButton.setText(String.format("%d Factories%nCost: %,.0f", Factory.getNumFactories(), Factory.getCost()));
		labButton.setText(String.format("%d Labs%nCost: %,.0f", Lab.getNumLabs(), Lab.getCost()));

	}

	/**
	 * This method takes in a string that contains the path to a .WAV file and
	 * converts it to a Clip object that can be played.
	 * 
	 * @param string
	 * @return Clip clip
	 */
	public Clip playSound(String string) {
		if (!mute) {
			try {

				// Open an audio input stream.
				URL url = this.getClass().getClassLoader().getResource(string);
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
				// Get a sound clip resource.
				Clip clip = AudioSystem.getClip();
				// Open audio clip and load samples from the audio input stream.
				clip.open(audioIn);

				return clip;
			} catch (UnsupportedAudioFileException f) {
			} catch (IOException g) {
			} catch (LineUnavailableException h) {
			}
		}
		return null;
	}

	/**
	 * This method is used for loading the save when you open the game. It takes in
	 * as scanner that reads from a save file, and sets all saved stats to all
	 * applicable stats, such as player pretzels and player PPS.
	 * 
	 * @param Scanner s
	 */
	private void loadSave(Scanner s) {
		Player.setPretzels(Double.parseDouble(s.nextLine()));
		Player.setTotalPretzels(Double.parseDouble(s.nextLine()));
		Player.setClickValue(Double.parseDouble(s.nextLine()));
		Player.setBuildings((int) Double.parseDouble(s.nextLine()));
		Player.setUpgrades((int) Double.parseDouble(s.nextLine()));
		Player.setPPS(Double.parseDouble(s.nextLine()));
		Player.setImageUpgrades((int) Double.parseDouble(s.nextLine()));
		Clicker.setPPS(Double.parseDouble(s.nextLine()));
		Clicker.setCost(Double.parseDouble(s.nextLine()));
		Clicker.setNumClickers((int) Double.parseDouble(s.nextLine()));
		Clicker.setMyClickerPPS(Double.parseDouble(s.nextLine()));
		Clicker.setUpgrades((int) Double.parseDouble(s.nextLine()));
		Grandpa.setPPS(Double.parseDouble(s.nextLine()));
		Grandpa.setCost(Double.parseDouble(s.nextLine()));
		Grandpa.setNumGrandpas((int) Double.parseDouble(s.nextLine()));
		Grandpa.setMyGrandpaPPS(Double.parseDouble(s.nextLine()));
		Grandpa.setUpgrades((int) Double.parseDouble(s.nextLine()));
		Farm.setPPS(Double.parseDouble(s.nextLine()));
		Farm.setCost(Double.parseDouble(s.nextLine()));
		Farm.setNumFarms((int) Double.parseDouble(s.nextLine()));
		Farm.setMyFarmPPS(Double.parseDouble(s.nextLine()));
		Farm.setUpgrades((int) Double.parseDouble(s.nextLine()));
		Mine.setPPS(Double.parseDouble(s.nextLine()));
		Mine.setCost(Double.parseDouble(s.nextLine()));
		Mine.setNumMines((int) Double.parseDouble(s.nextLine()));
		Mine.setMyMinePPS(Double.parseDouble(s.nextLine()));
		Mine.setUpgrades((int) Double.parseDouble(s.nextLine()));
		Factory.setPPS(Double.parseDouble(s.nextLine()));
		Factory.setCost(Double.parseDouble(s.nextLine()));
		Factory.setNumFactories((int) Double.parseDouble(s.nextLine()));
		Factory.setMyFactoriesPPS(Double.parseDouble(s.nextLine()));
		Factory.setUpgrades((int) Double.parseDouble(s.nextLine()));
		Lab.setPPS(Double.parseDouble(s.nextLine()));
		Lab.setCost(Double.parseDouble(s.nextLine()));
		Lab.setNumLabs((int) Double.parseDouble(s.nextLine()));
		Lab.setMyLabPPS(Double.parseDouble(s.nextLine()));
		Lab.setUpgrades((int) Double.parseDouble(s.nextLine()));
	}

	/**
	 * This method creates a PrinterWriter and writes all current game stats to a
	 * file called "Save"
	 */
	private void saveGame() {
		try {
			PrintWriter writeSave = new PrintWriter(new File("Save"));
			writeSave.print(String.format(
					"%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n",
					Player.getPretzels(), Player.getTotalPretzels(), Player.getClickValue(), Player.getBuildings(),
					Player.getUpgrades(), Player.getPPS(), Player.getImageUpgrades(), Clicker.getPPS(), Clicker.getCost(),
					Clicker.getNumClickers(), Clicker.getMyClickerPPS(), Clicker.getUpgrades(), Grandpa.getPPS(),
					Grandpa.getCost(), Grandpa.getNumGrandpas(), Grandpa.getMyGrandpaPPS(), Grandpa.getUpgrades(),
					Farm.getPPS(), Farm.getCost(), Farm.getNumFarms(), Farm.getMyFarmPPS(), Farm.getUpgrades(),
					Mine.getPPS(), Mine.getCost(), Mine.getNumMines(), Mine.getMyMinePPS(), Mine.getUpgrades(),
					Factory.getPPS(), Factory.getCost(), Factory.getNumFactories(), Factory.getMyFactoriesPPS(),
					Factory.getUpgrades(), Lab.getPPS(), Lab.getCost(), Lab.getNumLabs(), Lab.getMyLabPPS(),
					Lab.getUpgrades()));

			writeSave.close();
		} catch (FileNotFoundException e1) {
		}

	}

	/**
	 * Method for updating the text field for the pretzels. This method requests the
	 * focus for the text field that has the number of pretzels, and updates with
	 * the new number of pretzels. If the number of pretzels is >= 1 billion, the
	 * number of pretzels will be shortened to just xxx.xx format followed by the
	 * magnitude of the pretzels (example: 32.79 Trillion).
	 */
	public void updatePretzels() {
		if (Player.getPretzels() >= 1000000000000000000.0) {
			pretzelText.setText(
					String.format("Pretzels: %,.2f %nQuintillion", Player.getPretzels() / 1000000000000000000.0));
		} else if (Player.getPretzels() >= 1000000000000000.0) {
			pretzelText
					.setText(String.format("Pretzels: %,.2f %nQuadrillion", Player.getPretzels() / 1000000000000000.0));
		} else if (Player.getPretzels() >= 1000000000000.0) {
			pretzelText.setText(String.format("Pretzels: %,.2f %nTrillion", Player.getPretzels() / 1000000000000.0));
		} else if (Player.getPretzels() >= 1000000000.0) {
			pretzelText.setText(String.format("Pretzels: %,.2f %nBillion", Player.getPretzels() / 1000000000.0));
		} else
			pretzelText.setText(String.format("Pretzels: %,.0f", Player.getPretzels()));
		pretzelText.setStyle("-fx-text-fill: #949494;");
	}

	/**
	 * Method for updating the text field for the PPS. This method requests the
	 * focus for the text field that has the PPS and updates with the new PPS
	 */
	public void updatePPS() {
		Player.setPPS((Clicker.getMyClickerPPS() + Grandpa.getMyGrandpaPPS() + Farm.getMyFarmPPS() + Mine.getMyMinePPS()
				+ Factory.getMyFactoriesPPS() + Lab.getMyLabPPS()) * Player.getMultiplier());
		PPSText.setText(String.format("PPS: %,.1f", Player.getPPS()));
		PPSText.setStyle("-fx-text-fill: #949494;");
	}

	/**
	 * Method for creating a Tool Tip for a button. This method takes in a button
	 * and a string you would like to put into the tool tip, and puts the tool tip
	 * onto the button
	 * 
	 * @param button
	 * @param s
	 */
	public void makeToolTip(Button button, String s) {
		final Tooltip toolTip = new Tooltip();
		toolTip.setText(s);
		toolTip.setStyle("-fx-text-fill: BLACK;" + "-fx-background-color: #B3B3B3;" + "-fx-font-weight: BOLD;"
				+ "-fx-border-color: BLACK;" + "-fx-border-size: 2;");
		toolTip.setShowDelay(Duration.millis(10));
		button.setTooltip(toolTip);
	}

	/**
	 * Method for making a button disappear this method takes in a Button object and
	 * uses the FadeTransition class in JavaFX to make the button disappear
	 * 
	 * @param button
	 */
	public void disappearAnimation(Button button) {
		// Animation to make the button disappear
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), button);
		fadeTransition.setToValue(0);
		fadeTransition.play();
		// Get rid of the button once the animation is over
		fadeTransition.setOnFinished(event -> {
			button.setManaged(false);
		});
	}
}
