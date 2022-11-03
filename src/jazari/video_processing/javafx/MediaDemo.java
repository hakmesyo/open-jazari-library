/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.video_processing.javafx;

/**
 *
 * @author cezerilab
 */
import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class MediaDemo extends Application {

    private static final String MEDIA_URL
            = "https://liveexample.pearsoncmg.com/common/sample.mp4";

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        Media media;
        try {
            media = new Media(new File("C:\\Users\\cezerilab\\Desktop\\traffic_1.mp4").toURI().toURL().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(mediaPlayer);

            Button playButton = new Button(">");
            playButton.setOnAction(e -> {
                if (playButton.getText().equals(">")) {
                    mediaPlayer.play();
                    playButton.setText("||");
                } else {
                    mediaPlayer.pause();
                    playButton.setText(">");
                }
            });

            Button rewindButton = new Button("<<");
            rewindButton.setOnAction(e -> mediaPlayer.seek(Duration.ZERO));

            Slider slVolume = new Slider();
            slVolume.setPrefWidth(150);
            slVolume.setMaxWidth(Region.USE_PREF_SIZE);
            slVolume.setMinWidth(30);
            slVolume.setValue(50);
            mediaPlayer.volumeProperty().bind(
                    slVolume.valueProperty().divide(100));

            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().addAll(playButton, rewindButton,
                    new Label("Volume"), slVolume);

            BorderPane pane = new BorderPane();
            pane.setCenter(mediaView);
            pane.setBottom(hBox);

            // Create a scene and place it in the stage
            Scene scene = new Scene(pane, 650, 500);
            primaryStage.setTitle("MediaDemo"); // Set the stage title
            primaryStage.setScene(scene); // Place the scene in the stage
            primaryStage.show(); // Display the stage    
        } catch (MalformedURLException ex) {
            Logger.getLogger(MediaDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * The main method is only needed for the IDE with limited JavaFX support.
     * Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
