package com.company;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import static com.company.Ref.*;

/**
 * This class is responsible for game mechanics. Every method to handle game like collision detecting or painting are here.
 */
public class GamePlay extends JPanel {

    public final Rectangle FLOOR_RECTANGLE = new Rectangle(0, SCREEN_DIMENSION.height - FLOOR_HEIGHT, SCREEN_DIMENSION.width, FLOOR_HEIGHT);
    public final Rectangle NET_RECTANGLE = new Rectangle((SCREEN_DIMENSION.width - 50)/2, SCREEN_DIMENSION.height - NET_HEIGHT - FLOOR_HEIGHT, 50, NET_HEIGHT);

    public final Rectangle RESET_BUTTON = new Rectangle(SCREEN_DIMENSION.width - 40, SCREEN_DIMENSION.height - 40, 35,35);

    private Player player1;
    private Player player2;
    private Ball ball;

    private Image sand;
    private Image bg;
    private Image net;
    private Image reset;

    private AudioHandler audio;
    /**
     * This timer run all those method like collision handlers every frame. At the end it's repaint frame.
     */
    private Timer timer = new Timer(8, e -> {
        ball.setySpeed(ball.getySpeed() + BALL_GRAVITY);
        player1.setYSpeed( player1.getYSpeed() + PLAYER_GRAVITY );
        player2.setYSpeed( player2.getYSpeed() + PLAYER_GRAVITY );

        ball.move();
        player1.move();
        player2.move();

        handlePlayerBallCollision(ball, player1);
        handlePlayerBallCollision(ball, player2);

        handleBallCollision(ball);

        handlePlayersCollisions(player1);
        handlePlayersCollisions(player2);

        repaint();
    });

    /**
     * When the game is going on
     */
    private boolean isPlaying = true;

    public GamePlay() {
        this.setMinimumSize(SCREEN_DIMENSION);
        this.setPreferredSize(SCREEN_DIMENSION);
        this.setMaximumSize(SCREEN_DIMENSION);

        audio = new AudioHandler(null, null);

        try {
            sand = ImageIO.read(new File("graphics/environment/sand.png"));
            bg = ImageIO.read(new File("graphics/environment/bg.png"));
            net = ImageIO.read(new File("graphics/environment/net.png"));
            reset = ImageIO.read(new File("graphics/environment/reset.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        reset();

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if(keyCode == P1_UP)
                    player1.setUpPressed(true);
                else if(keyCode == P1_LEFT)
                    player1.setLeftPressed(true);
                else if(keyCode == P1_RIGHT)
                    player1.setRightPressed(true);
                else if(keyCode == P2_UP)
                    player2.setUpPressed(true);
                else if(keyCode == P2_LEFT)
                    player2.setLeftPressed(true);
                else if(keyCode == P2_RIGHT)
                    player2.setRightPressed(true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if(keyCode == P1_UP)
                    player1.setUpPressed(false);
                else if(keyCode == P1_LEFT)
                    player1.setLeftPressed(false);
                else if(keyCode == P1_RIGHT)
                    player1.setRightPressed(false);
                else if(keyCode == P2_UP)
                    player2.setUpPressed(false);
                else if(keyCode == P2_LEFT)
                    player2.setLeftPressed(false);
                else if(keyCode == P2_RIGHT)
                    player2.setRightPressed(false);
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getX() >= RESET_BUTTON.x && e.getX() <= RESET_BUTTON.x + RESET_BUTTON.width && e.getY() >= RESET_BUTTON.y && e.getY() <= RESET_BUTTON.y + RESET_BUTTON.height)
                    reset();
            }
        });

        this.setFocusable(true);

        timer.start();
    }

    /**
     * This method resets game. Creates new players and ball etc.
     */
    private void reset() {
        player1 = new Player(40, 600);
        player2 = new Player(SCREEN_DIMENSION.width - 40 - PLAYER_R * 2, 600);

        if((new Random()).nextBoolean())
            ball = new Ball(100, 10);
        else
            ball = new Ball(SCREEN_DIMENSION.width - 100 - BALL_R * 2, 10);

        isPlaying = true;
    }

    @Override
    public void paint(Graphics g) {
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //bg
        g.drawImage(bg,0,0,null);

        //net
        g.drawImage(net, NET_RECTANGLE.x, NET_RECTANGLE.y, null);

        //floor
        g.drawImage(sand, 0, SCREEN_DIMENSION.height - sand.getHeight(null), null);

        //player1
        g.setColor(PLAYER_COLOR);
        g.fillOval((int) player1.getPosX(), (int) player1.getPosY(), PLAYER_R * 2, PLAYER_R * 2);

        //player2
        g.setColor(PLAYER_COLOR);
        g.fillOval((int) player2.getPosX(), (int) player2.getPosY(), PLAYER_R * 2, PLAYER_R * 2);

        //ball
        g.setColor(BALL_COLOR);
        g.fillOval((int) ball.getPosX(), (int) ball.getPosY(), BALL_R * 2, BALL_R * 2);

        //cursor
        if(ball.getPosY() < -BALL_R * 2) {
            g.setColor(BALL_COLOR);
            g.fillRect((int) (ball.getPosX() + BALL_R - 10), 0, 20, 10);
        }

        //reset button
        g.drawImage(reset, RESET_BUTTON.x, RESET_BUTTON.y, null);

        //points
        g.setColor(POINTS_COLOR);
        g.setFont(new Font("SansSerif", Font.BOLD, 60));
        g.drawString(player1.getPoints() + " : " + player2.getPoints(), SCREEN_DIMENSION.width / 2 - 65, 50);

        //dev view
        if(DEV_VIEW) {
            //players collision area
            g.setColor(Color.BLACK);
            g.drawOval((int) player1.getPosX(), (int)  player1.getPosY(), PLAYER_R * 2, PLAYER_R * 2);
            g.drawOval((int) player2.getPosX(), (int)  player2.getPosY(), PLAYER_R * 2, PLAYER_R * 2);

            double centerX = ball.getPosX() + BALL_R;
            double centerY = ball.getPosY() + BALL_R;

            //distance ball-net
            g.setColor(Color.white);
            if(centerY > NET_RECTANGLE.y) {
                if(centerX > NET_RECTANGLE.x + NET_RECTANGLE.width)
                    g.drawLine((int) centerX, (int) centerY, NET_RECTANGLE.x + NET_RECTANGLE.width, (int) centerY);
                else
                    g.drawLine((int) centerX, (int) centerY, NET_RECTANGLE.x, (int) centerY);
            }
            else if(centerX > NET_RECTANGLE.x && centerX < NET_RECTANGLE.x + NET_RECTANGLE.width)
                g.drawLine((int) centerX, (int) centerY, (int) centerX, NET_RECTANGLE.y);
            else if(centerX < NET_RECTANGLE.x)
                g.drawLine((int) centerX, (int) centerY, NET_RECTANGLE.x, NET_RECTANGLE.y);
            else
                g.drawLine((int) centerX, (int) centerY, NET_RECTANGLE.x + NET_RECTANGLE.width, NET_RECTANGLE.y);

            //distance ball-players
            g.setColor(Color.MAGENTA);
            g.drawLine((int) centerX, (int) centerY, (int) player1.getPosX() + PLAYER_R, (int) player1.getPosY() + PLAYER_R);
            g.drawLine((int) centerX, (int) centerY, (int) player2.getPosX() + PLAYER_R, (int) player2.getPosY() + PLAYER_R);
        }
    }

    /**
     * This method check if <code>ball</code> and <code>player</code> collide and if so, it handle this collision.
     * @param ball
     * @param player
     */
    private void handlePlayerBallCollision(Ball ball, Player player) {
        double ballPosX = ball.getPosX() + BALL_R;
        double ballPosY = ball.getPosY() + BALL_R;
        double playerPosX = player.getPosX() + PLAYER_R;
        double playerPosY = player.getPosY() + PLAYER_R;
        double distance = Math.sqrt(Math.pow(ballPosX - playerPosX, 2) + Math.pow(ballPosY - playerPosY, 2));

        if(distance < PLAYER_R + BALL_R) {
            audio.playHit();

            player.setContact(player.getContact() + 1);

            if(player.equals(player1))
                player2.setContact(0);
            else
                player1.setContact(0);

            if(player.getContact() == 4 && isPlaying) {
                if(player.equals(player1))
                    point(2);
                else
                    point(1);
            }

            double angle = Math.toDegrees(Math.atan((ballPosY - playerPosY) / (ballPosX - playerPosX)));
            double speed = (Math.abs(ball.getySpeed()) + Math.abs(ball.getxSpeed())) * 0.98;

            if(player.isLeftPressed() || player.isRightPressed())
                speed += MOVEMENT * (1 - Math.abs(angle) / 90);

            if (ballPosX < playerPosX)
                player.setPosX(ballPosX + Math.cos(Math.toRadians(Math.abs(angle))) * (PLAYER_R + BALL_R) - PLAYER_R);
            else
                player.setPosX(ballPosX - Math.cos(Math.toRadians(Math.abs(angle))) * (PLAYER_R + BALL_R) - PLAYER_R);

            if(ballPosY < playerPosY) {

                player.setPosY(ballPosY + Math.sin(Math.toRadians(Math.abs(angle))) * (PLAYER_R + BALL_R) - PLAYER_R);

                ball.setySpeed(speed * (-Math.abs(angle) / 90));
                if(angle > 0)
                    ball.setxSpeed(-speed * (1 - Math.abs(angle) / 90));
                else
                    ball.setxSpeed(speed * (1 - Math.abs(angle) / 90));

            }
            else {
                player.setPosY(ballPosY - Math.sin(Math.toRadians(Math.abs(angle))) * (PLAYER_R + BALL_R) - PLAYER_R);

                ball.setySpeed(speed * (Math.abs(angle) / 90));
                if(angle > 0)
                    ball.setxSpeed(speed * (1 - Math.abs(angle) / 90));
                else
                    ball.setxSpeed(-speed * (1 - Math.abs(angle) / 90));
            }
        }
    }

    /**
     * This method check <code>ball</code> collide with environment and if so, it handle this collision.
     * @param b ball
     */
    private void handleBallCollision(Ball b) {
        //walls
        if(b.getPosX() < 0) {
            audio.playHit();
            b.setPosX(0);
            b.setxSpeed(-b.getxSpeed());
        }
        else if(b.getPosX() + BALL_R * 2 > SCREEN_DIMENSION.getWidth()) {
            audio.playHit();
            b.setPosX(SCREEN_DIMENSION.getWidth() - BALL_R * 2);
            b.setxSpeed(-b.getxSpeed());
        }

        //floor
        if(b.getPosY() + BALL_R * 2 > FLOOR_RECTANGLE.y) {
            b.setPosY(FLOOR_RECTANGLE.y - BALL_R * 2);
            b.setySpeed( -b.getySpeed() / 2);
            b.setxSpeed( b.getxSpeed() * 0.9);

            if(isPlaying) {
                if (b.getPosX() < SCREEN_DIMENSION.getWidth() / 2)
                    point(2);
                else
                    point(1);
            }
        }

        //net
        double centerX = b.getPosX() + BALL_R;
        double centerY = b.getPosY() + BALL_R;

        if(centerY > NET_RECTANGLE.y) {
            if(Math.abs(centerX - NET_RECTANGLE.x) < BALL_R) {
                audio.playHit();
                b.setPosX(NET_RECTANGLE.x - BALL_R * 2);
                b.setxSpeed( -b.getxSpeed() );
            }
            else if(Math.abs(centerX - NET_RECTANGLE.x - NET_RECTANGLE.width) < BALL_R) {
                audio.playHit();
                b.setPosX(NET_RECTANGLE.x + NET_RECTANGLE.width);
                b.setxSpeed( -b.getxSpeed() );
            }
        }
        else if(centerX > NET_RECTANGLE.x && centerX < NET_RECTANGLE.x + NET_RECTANGLE.width && Math.abs(centerY - NET_RECTANGLE.y) < BALL_R) {
            audio.playHit();
            b.setPosY(NET_RECTANGLE.y - BALL_R * 2);
            b.setySpeed( -b.getySpeed() );
        }
        else if(Math.sqrt(Math.pow(centerX - NET_RECTANGLE.x, 2) + Math.pow(centerY - NET_RECTANGLE.y, 2)) < BALL_R) {
            audio.playHit();
            double speed = Math.abs(b.getySpeed()) + Math.abs(b.getxSpeed());
            double angle = Math.atan((centerY - NET_RECTANGLE.y) / (centerX - NET_RECTANGLE.x));
            b.setxSpeed(-Math.abs(speed * Math.cos(angle)));
            b.setySpeed(-Math.abs(speed * Math.sin(angle)));
        }
        else if(Math.sqrt(Math.pow(centerX - NET_RECTANGLE.x - NET_RECTANGLE.width, 2) + Math.pow(centerY - NET_RECTANGLE.y, 2)) < BALL_R) {
            audio.playHit();
            double speed = Math.abs(b.getySpeed()) + Math.abs(b.getxSpeed());
            double angle = Math.atan((centerY - NET_RECTANGLE.y) / (centerX - NET_RECTANGLE.x - NET_RECTANGLE.width));
            b.setxSpeed(Math.abs(speed * Math.cos(angle)));
            b.setySpeed(-Math.abs(speed * Math.sin(angle)));
        }
    }

    /**
     * If player get point, it handle it.
     * @param player number of player. 1 - player1, 2 - player2. If you send other number Exception will be thrown.
     */
    private void point(int player) {
        final int DELAY = 2000;

        isPlaying = false;

        if(player == 2) {
            player2.incrementPoints();
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            ball = new Ball(SCREEN_DIMENSION.width - 100 - BALL_R * 2, 10);
                            isPlaying = true;
                        }
                    },
                    DELAY
            );
        }
        else if(player == 1) {
            player1.incrementPoints();
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            ball = new Ball(100, 10);
                            isPlaying = true;
                        }
                    },
                    DELAY
            );
        }
        else
            throw new IllegalArgumentException();

        player1.setContact(0);
        player2.setContact(0);
    }

    /**
     * This method check if <code>player</code> collide with environment and if so, it handle this collision.
     * @param player
     */
    private void handlePlayersCollisions(Player player) {
        //walls
        if(player.getPosX() < 0)
            player.setPosX(0);
        else if(player.getPosX() + PLAYER_R * 2 > SCREEN_DIMENSION.getWidth())
            player.setPosX(SCREEN_DIMENSION.getWidth() - PLAYER_R * 2);

        //floor
        if(player.getPosY() + PLAYER_R * 2 > SCREEN_DIMENSION.getHeight() - FLOOR_HEIGHT) {
            player.setYSpeed(0);
            player.setPosY(SCREEN_DIMENSION.getHeight() - FLOOR_HEIGHT - PLAYER_R * 2);
        }

        //net
        if(player.getPosX() < NET_RECTANGLE.x && player.getPosX() + PLAYER_R * 2 > NET_RECTANGLE.x)
            player.setPosX(NET_RECTANGLE.x - PLAYER_R * 2);
        else if(player.getPosX() + PLAYER_R * 2 > NET_RECTANGLE.x && player.getPosX() < NET_RECTANGLE.x + NET_RECTANGLE.width)
            player.setPosX(NET_RECTANGLE.x + NET_RECTANGLE.width);

    }
}
