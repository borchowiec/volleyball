package com.company;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * This class is using to store basic variables like colors, sizes etc. All of them are final.
 */
public class Ref {
    public static final Dimension SCREEN_DIMENSION = new Dimension(1200, 900);
    public static final int FLOOR_HEIGHT = 50;
    public static final int NET_HEIGHT = 340;
    public static final Color POINTS_COLOR = new Color(215, 103, 58);
    public static final Color PLAYER_COLOR = new Color(215, 0, 5);
    public static final Color BALL_COLOR = new Color(0, 175, 29);
    public static final double PLAYER_GRAVITY = 0.1;
    public static final double BALL_GRAVITY = 0.08;
    public static final double JUMP_STRENGTH = 8;
    public static final double MOVEMENT = 4;
    public static final int PLAYER_R = 50;
    public static final int BALL_R = 40;
    public static final int P1_UP = KeyEvent.VK_W;
    public static final int P1_LEFT = KeyEvent.VK_A;
    public static final int P1_RIGHT = KeyEvent.VK_D;
    public static final int P2_UP = KeyEvent.VK_UP;
    public static final int P2_LEFT = KeyEvent.VK_LEFT;
    public static final int P2_RIGHT = KeyEvent.VK_RIGHT;
    public static final boolean DEV_VIEW = false;
}
