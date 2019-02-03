package com.company;

/**
 * This class represents ball. Should be created only one instance of this class.
 */
public class Ball {
    private double posX;
    private double posY;
    private double ySpeed = 0;
    private double xSpeed = 0;

    public Ball(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getySpeed() {
        return ySpeed;
    }

    public void setySpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public double getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    /**
     * This method moves ball. Should be ran every frame.
     */
    public void move() {
        posX += xSpeed;
        posY += ySpeed;
    }
}
