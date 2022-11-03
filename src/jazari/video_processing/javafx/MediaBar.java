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
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class MediaBar extends HBox { // MediaBar extends Horizontal Box

    // introducing Sliders
    Slider time = new Slider(); // Slider for time
    Slider vol = new Slider(); // Slider for volume
    Button PlayButton = new Button("||"); // For pausing the player
    Label volume = new Label("Volume: ");
    MediaPlayer player;
    Group root;

    public MediaBar(MediaPlayer play,Group root) { // Default constructor taking
        this.root=root;
                
        //System.out.println("merhaba");
        // the MediaPlayer object
        player = play;

        setAlignment(Pos.CENTER); // setting the HBox to center
        setPadding(new Insets(5, 10, 5, 10));
        // Settih the preference for volume bar
        vol.setPrefWidth(70);
        vol.setMinWidth(30);
        vol.setValue(100);
        
        time.setPrefWidth(300);
        time.setMinWidth(50);
        time.setValue(0);
        HBox.setHgrow(time, Priority.ALWAYS);
        PlayButton.setPrefWidth(50);
        

        // Adding the components to the bottom
        getChildren().add(PlayButton); // Playbutton
        getChildren().add(time); // time slider
        getChildren().add(volume); // volume slider
        getChildren().add(vol);
        
        

        // Adding Functionality
        // to play the media player
        PlayButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Status status = player.getStatus(); // To get the status of Player
                if (status == status.PLAYING) {

                    // If the status is Video playing
                    if (player.getCurrentTime().greaterThanOrEqualTo(player.getTotalDuration())) {

                        // If the player is at the end of video
                        player.seek(player.getStartTime()); // Restart the video
                        player.play();
                    } else {
                        // Pausing the player
                        player.pause();

                        PlayButton.setText(">");
                    }
                } // If the video is stopped, halted or paused
                if (status == Status.HALTED || status == Status.STOPPED || status == Status.PAUSED) {
                    player.play(); // Start the video
                    PlayButton.setText("||");
                }
            }
        });

        // Providing functionality to time slider
        player.currentTimeProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                updatesValues();
            }
        });

        // Inorder to jump to the certain part of video
        time.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (time.isPressed()) { // It would set the time
                    // as specified by user by pressing
                    player.seek(player.getMedia().getDuration().multiply(time.getValue() / 100));
                }
            }
        });

        // providing functionality to volume slider
        vol.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (vol.isPressed()) {
                    player.setVolume(vol.getValue() / 100); // It would set the volume
                    // as specified by user by pressing
                }
            }
        });
    }

    // Outside the constructor
    protected void updatesValues() {
        Platform.runLater(new Runnable() {
            public void run() {
                // Updating to the new time value
                // This will move the slider while running your video
                time.setValue(player.getCurrentTime().toMillis()
                        / player.getTotalDuration().toMillis()
                        * 100
                );
                Rectangle rect=new Rectangle(50, 50, 150, 250);
                rect.setFill(Color.TRANSPARENT);
                rect.setStroke(Color.LIGHTGREEN);
                rect.setStrokeWidth(3);
                root.getChildren().set(1, rect);
                //root.getChildren().set(1, new Line(0, 0, 200, 200));
            }
        }
        );
    }
}
