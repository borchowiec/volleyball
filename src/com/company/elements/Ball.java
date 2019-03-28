package com.company.elements;

/**
 * This class represents ball. Contains information about the state of the ball and contains methods to manipulate the ball.
 */
public class Ball {
    private double posX;
    private double posY;
    private double ySpeed = 0;
    private double xSpeed = 0;

    /**
     * Main ball constructor.
     * @param posX Start position x
     * @param posY Start position y
     */
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
     * This method moves ball. Next position depends from current speed.
     */
    public void move() {
        posX += xSpeed;
        posY += ySpeed;
    }

    /**
     * This method returns speed of ball in all dimensions. It's always absolute.
     * @return Speed of ball.
     */
    public double getSpeed() {
        return Math.abs(xSpeed) + Math.abs(ySpeed);
    }
}
