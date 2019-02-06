package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class starts app. Create and run frames.
 */
public class Main extends JFrame {

    /**
     * This constructor create frame.
     */
    public Main() {

        try {
            Face face0 = new Face(ImageIO.read(new File("graphics/faces/face_0/img.png")));
            Face face1 = new Face(ImageIO.read(new File("graphics/faces/face_1/img.png")));
            this.add(new GamePlay(face0, face1));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
