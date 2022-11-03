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
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class Player extends BorderPane // Player class extend BorderPane
// in order to divide the media
// player into regions
{

    Media media;
    MediaPlayer player;
    MediaView view;
    Pane mpane;
    MediaBar bar;
    Group root;

    public Player(String file,Group root) throws MalformedURLException { // Default constructor
        this.root=root;
        File mediaFile = new File(file);
        media = new Media(mediaFile.toURI().toURL().toString());
        player = new MediaPlayer(media);
        view = new MediaView(player);
        mpane = new Pane();
        mpane.getChildren().add(view); // Calling the function getChildren

        // inorder to add the view
        setCenter(mpane);
        bar = new MediaBar(player,root); // Passing the player to MediaBar
        setBottom(bar); // Setting the MediaBar at bottom
        setStyle("-fx-background-color:#bfc2c7"); // Adding color to the mediabar
        player.play(); // Making the video play
    }
}
