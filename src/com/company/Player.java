package com.company;

/**
 * This class represents player.
 */
public class Player {
    private double posX;
    private double posY;
    private double ySpeed = 0;
    private boolean upPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private int points = 0;
    private int contact = 0;

    public Player(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
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

    /**
     * This method moves player. Should be ran every frame.
     */
    public void move() {
        if(leftPressed)
            posX -= Ref.MOVEMENT;
        if(rightPressed)
            posX += Ref.MOVEMENT;
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
}
