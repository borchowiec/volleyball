package com.company.logic;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * This class is responsible for sound effects.
 */
public class AudioHandler {

    /**
     * hit sound
     */
    private Media hitMedia;

    /**
     * Main constructor
     */
    public AudioHandler() {
        new JFXPanel();
        hitMedia = new Media(new File("sounds/hit.wav").toURI().toString());
    }

    /**
     * Play hit sound
     */
    public void playHit() {
        playSound(hitMedia);
    }

    /**
     * This method plays sound effect
     * @param media Sound that you want to play.
     */
    private void playSound(Media media) {
        MediaPlayer mp = new MediaPlayer(media);
        mp.play();
    }

}
