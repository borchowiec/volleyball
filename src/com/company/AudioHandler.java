package com.company;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class AudioHandler {

    private Media hitMedia;
    private MediaPlayer player1MP;
    private MediaPlayer player2MP;

    public AudioHandler(File player1Song, File player2Song) {

        final JFXPanel fxPanel = new JFXPanel();

        hitMedia = new Media(new File("sounds/hit.wav").toURI().toString());
        //this.player1MP = new MediaPlayer(new Media(player1Song.toURI().toString()));
        //this.player2MP = new MediaPlayer(new Media(player2Song.toURI().toString()));

    }

    public void playHit() {
        playSound(hitMedia);
    }

    private void playSound(Media media) {
        MediaPlayer mp = new MediaPlayer(media);
        mp.play();
    }

}
