package com.company.elements;

import com.company.Ref;

import java.awt.*;

/**
 * This class represents player. Contains information about state of player e.g. position, speed or which key is pressed.
 */
public class Player {
    private double posX;
    private double posY;
    private double ySpeed = 0;
    private boolean upPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private int points = 0;

    /**
     * The number of ball touches in a row.
     */
    private int contact = 0;

    /**
     * If you press left, the face should be horizontally reversed.
     */
    private boolean isFaceReversed = false;
    public final Face face;

    /**
     * If player gets point, some actions can be started e.g. animation
     */
    private boolean gotPoint = false;

    /**
     * Main constructor
     * @param posX Start position x
     * @param posY Start position y
     * @param face Face of player
     */
    public Player(double posX, double posY, Face face) {
        this.posX = posX;
        this.posY = posY;
        this.face = face;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getYSpeed() {
        return ySpeed;
    }

    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public void setGotPoint(boolean gotPoint) {
        this.gotPoint = gotPoint;
    }

    /**
     * This method moves player. Next position depends from current speed and which key is pressed.
     */
    public void move() {
        if(leftPressed) {
            posX -= Ref.MOVEMENT;
            isFaceReversed = true;
        }
        if(rightPressed) {
            posX += Ref.MOVEMENT;
            isFaceReversed = false;
        }
        if(upPressed && (posY + Ref.PLAYER_R * 2 >= Ref.SCREEN_DIMENSION.getHeight() - Ref.FLOOR_HEIGHT))
            ySpeed -= Ref.JUMP_STRENGTH;

        posY += ySpeed;
    }

    public int getPoints() {
        return points;
    }

    public void incrementPoints() {
        points++;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    /**
     * Returns face. If face is reversed, returns horizontally reversed face.
     * @return Player's face
     */
    public Image getFace() {
        if(!isFaceReversed)
            return face.MAIN_FACE;
        return face.MAIN_FACE_R;
    }

    /**
     * Paints player character. If player gets point, paints animation.
     * @param g graphics
     * @param isPlaying if somebody gets point, should be false.
     */
    public void paint(Graphics g, boolean isPlaying) {
        if(!isPlaying && gotPoint)
            g.drawImage(face.getAnimationFrame(), (int) posX, (int) posY, null);
        else
            g.drawImage(getFace(), (int) posX, (int) posY, null);
    }
}
