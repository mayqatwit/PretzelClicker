import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	ImageView pretzelImage;
	@FXML
	Text playerStats = new Text(Player.getStats());
	@FXML
	Button saveButton;

	boolean leftWasClicked = false; // This is used to only left click pretzel once

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
		primaryStage.getIcons().add(new Image("PretzelImage.png"));
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	private void loadSave(Scanner s) {
		String line = s.nextLine();
		Player.updatePretzels(Double.parseDouble(line));
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		pretzelImage.setOnMousePressed((e) -> { // Clicking the cookie

			if (e.isPrimaryButtonDown()) { // Check to see if the click was a left click

				if (!leftWasClicked) { // Make sure only one click is being registered
					// pretzelClickSound.stop();
					// pretzelClickSound.play();

					Player.updatePretzels(Player.getClickValue());
					Player.updateTotalPretzels(Player.getClickValue());
					pretzelImage.setScaleX(pretzelImage.getScaleX() + 0.1);
					pretzelImage.setScaleY(pretzelImage.getScaleY() + 0.1);
					updatePretzels();

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
				clickerButton.requestFocus();
				clickerButton.setText(
						String.format("%d Clickers%nCost: %,.0f", Clicker.getNumClickers(), Clicker.getCost()));
			}
		});

		// Running a time-line to add pretzels every 0.01 seconds
		// base on the current PPS, using 0.01 seconds to make the pretzels
		// go up smoothly over time
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), e -> {
			Player.updatePretzels(Player.getPPS() / 100);
			Player.updateTotalPretzels(Player.getPPS() / 100);
			updatePretzels();
			updateCPS();
			playerStats.setText(Player.getStats());
		}));
		
		saveButton.setOnAction(e -> {
			try {
				PrintWriter writeSave = new PrintWriter(new File("Save"));
				writeSave.print(Player.getPretzels());
				writeSave.close();
			} catch (FileNotFoundException e1) {}
		});

		// Have the time-line run indefinitely and start it
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	public void updatePretzels() {
		pretzelText.requestFocus();
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

	public void updateCPS() {
		PPSText.requestFocus();
		Player.setPPS(Clicker.getMyClickerPPS());
		PPSText.setText(String.format("CPS: %,.1f", Player.getPPS()));
	}
}
