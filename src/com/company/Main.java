package com.company;

import javax.swing.*;

/**
 * This class starts app. Create and run frames.
 */
public class Main extends JFrame {

    /**
     * This constructor create frame.
     */
    public Main() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(new GamePlay());
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
