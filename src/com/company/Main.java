package com.company;

import com.company.layout.ChooseFrame;

import javax.swing.*;

/**
 * This class starts app. Create start frame.
 */
public class Main extends JFrame {

    /**
     * This constructor create choose frame.
     */
    public Main() {
        new ChooseFrame();
    }

    public static void main(String[] args) {
        new Main();
    }
}
