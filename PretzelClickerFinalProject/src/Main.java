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
	Text pretzelText = new Text(String.format("Pretzels: ,%f", Player.getPretzels()));
	@FXML
	Text PPSText;
	@FXML
	Button clickerButton = Clicker.getGraphic();
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
	ImageView pretzelImage;
	@FXML
	Text playerStats = new Text(Player.getStats());
	@FXML
	Button saveButton;
	@FXML
	Button resetButton;
	@FXML
	Button muteButton;
	@FXML
	Button upgradeClicker1;
	@FXML
	VBox vbox;

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

		p.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.W)
				p.setOnKeyPressed(f -> {
					if (f.getCode() == KeyCode.W)
						p.setOnKeyPressed(g -> {
							if (g.getCode() == KeyCode.S)
								p.setOnKeyPressed(h -> {
									if (h.getCode() == KeyCode.S)
										p.setOnKeyPressed(i -> {
											if (i.getCode() == KeyCode.A)
												p.setOnKeyPressed(j -> {
													if (j.getCode() == KeyCode.D)
														p.setOnKeyPressed(k -> {
															if (k.getCode() == KeyCode.A)
																p.setOnKeyPressed(l -> {
																	if (l.getCode() == KeyCode.D)
																		p.setOnKeyPressed(m -> {
																			if (m.getCode() == KeyCode.B)
																				p.setOnKeyPressed(n -> {
																					if (n.getCode() == KeyCode.A) {
																						Clip egg = playSound(
																								"sounds/easterEgg.wav");
																						egg.start();
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

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		vbox.setBackground(new Background(
				new BackgroundImage(new Image("sprites/background.jpg", 445, 662, false, true), BackgroundRepeat.REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

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
			playerStats.setText(Player.getStats());
			clickerButton
					.setText(String.format("%d Clickers%nCost: %,.0f", Clicker.getNumClickers(), Clicker.getCost()));
			grandpaButton
					.setText(String.format("%d Grandpas%nCost: %,.0f", Grandpa.getNumGrandpas(), Grandpa.getCost()));
			farmButton.setText(String.format("%d Farms%nCost: %,.0f", Farm.getNumFarms(), Farm.getCost()));
			mineButton.setText(String.format("%d Mines%nCost: %,.0f", Mine.getNumMines(), Mine.getCost()));
			factoryButton
					.setText(String.format("%d Factories%nCost: %,.0f", Factory.getNumFactories(), Factory.getCost()));
			labButton.setText(String.format("%d Labs%nCost: %,.0f", Lab.getNumLabs(), Lab.getCost()));

		}));

		// Have the time-line run indefinitely and start it
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

		pretzelImage.setOnMousePressed((e) -> { // Clicking the cookie

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

		pretzelImage.setOnMouseReleased((e) -> { // Releasing click on the cookie
			if (leftWasClicked) { // Check to see if it was the left mouse that was released
				pretzelImage.setScaleX(pretzelImage.getScaleX() - 0.1);
				pretzelImage.setScaleY(pretzelImage.getScaleY() - 0.1);
				leftWasClicked = false;
			}
		});

		clickerButton.setOnAction(e -> { // Buying a clicker
			if (Player.getPretzels() >= Clicker.getCost()) {
				new Clicker();

				updatePretzels();
				clickerButton.setText(
						String.format("%d Clickers%nCost: %,.0f", Clicker.getNumClickers(), Clicker.getCost()));
				Clip click = playSound("sounds/ButtonClick.wav");
				if (click != null)
					click.start();
			}
		});

		grandpaButton.setOnAction(e -> {
			if (Player.getPretzels() >= Grandpa.getCost()) {
				new Grandpa();

				updatePretzels();
				grandpaButton.setText(
						String.format("%d Grandpas%nCost: %,.0f", Grandpa.getNumGrandpas(), Grandpa.getCost()));
				Clip click = playSound("sounds/ButtonClick.wav");
				if (click != null)
					click.start();
			}
		});

		farmButton.setOnAction(e -> { // Buying a farm
			if (Player.getPretzels() >= Farm.getCost()) {
				new Farm();

				updatePretzels();
				farmButton.setText(String.format("%d Farms%nCost: %,.0f", Farm.getNumFarms(), Farm.getCost()));
				Clip click = playSound("sounds/ButtonClick.wav");
				if (click != null)
					click.start();
			}
		});

		mineButton.setOnAction(e -> {
			if (Player.getPretzels() >= Mine.getCost()) {
				new Mine();

				updatePretzels();
				mineButton.setText(String.format("%d Mines%nCost: %,.0f", Mine.getNumMines(), Mine.getCost()));
				Clip click = playSound("sounds/ButtonClick.wav");
				if (click != null)
					click.start();
			}
		});

		factoryButton.setOnAction(e -> {
			if (Player.getPretzels() >= Factory.getCost()) {
				new Factory();

				updatePretzels();
				factoryButton.setText(
						String.format("%d Factories%nCost: %,.0f", Factory.getNumFactories(), Factory.getCost()));
				Clip click = playSound("sounds/ButtonClick.wav");
				if (click != null)
					click.start();
			}
		});

		labButton.setOnAction(e -> {
			if (Player.getPretzels() >= Lab.getCost()) {
				new Lab();

				updatePretzels();
				labButton.setText(String.format("%d Labratories%nCost: %,.0f", Lab.getNumLabs(), Lab.getCost()));
				Clip click = playSound("sounds/ButtonClick.wav");
				if (click != null)
					click.start();
			}
		});

		makeToolTip(upgradeClicker1,
				String.format("Doubles clickers PPS and click value%nCost: %d", Clicker.getUpgradeCost()));
		upgradeClicker1.setOnAction(e -> {
			if (Player.getPretzels() >= Clicker.getUpgradeCost()) {
				Player.updatePretzels(-Clicker.getUpgradeCost());
				Clip click = playSound("sounds/ButtonClick.wav");
				if (click != null)
					click.start();
				new Clicker(1);
				makeToolTip(upgradeClicker1,
						String.format("Doubles clickers PPS and click value%nCost: %d", Clicker.getUpgradeCost()));
			}
		});

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
			Clip click = playSound("sounds/ButtonClick.wav");
			if (click != null)
				click.start();
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
				Clip click = playSound("sounds/ButtonClick.wav");
				if (click != null)
					click.start();
			} catch (FileNotFoundException e1) {
			}
		});

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
					"%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n",
					Player.getPretzels(), Player.getTotalPretzels(), Player.getClickValue(), Player.getBuildings(),
					Player.getUpgrades(), Player.getPPS(), Clicker.getPPS(), Clicker.getCost(),
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
	}

	/**
	 * Method for updating the text field for the PPS. This method requests the
	 * focus for the text field that has the PPS and updates with the new PPS
	 */
	public void updatePPS() {
		Player.setPPS(Clicker.getMyClickerPPS() + Grandpa.getMyGrandpaPPS() + Farm.getMyFarmPPS() + Mine.getMyMinePPS()
				+ Factory.getMyFactoriesPPS() + Lab.getMyLabPPS());
		PPSText.setText(String.format("PPS: %,.1f", Player.getPPS()));
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

}
