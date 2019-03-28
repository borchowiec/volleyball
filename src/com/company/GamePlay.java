package com.company;

import com.company.elements.Ball;
import com.company.elements.Face;
import com.company.elements.Player;
import com.company.layout.ChooseFrame;
import com.company.logic.MechanicsHandler;

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
 * This class contains every element of gameplay e.g. players, ball, environment.
 */
public class GamePlay extends JPanel {

    public final Rectangle FLOOR_RECTANGLE = new Rectangle(0, SCREEN_DIMENSION.height - FLOOR_HEIGHT, SCREEN_DIMENSION.width, FLOOR_HEIGHT);
    public final Rectangle NET_RECTANGLE = new Rectangle((SCREEN_DIMENSION.width - 50)/2, SCREEN_DIMENSION.height - NET_HEIGHT - FLOOR_HEIGHT, 50, NET_HEIGHT);

    public final Rectangle RESET_BUTTON = new Rectangle(SCREEN_DIMENSION.width - 40, SCREEN_DIMENSION.height - 40, 35,35);
    public final Rectangle MENU_BUTTON = new Rectangle(SCREEN_DIMENSION.width - 120, SCREEN_DIMENSION.height - 40, 70,35);

    private Player player1;
    private Player player2;
    private Ball ball;

    private Image sand;
    private Image bg;
    private Image net;
    private Image reset;
    private Image menu;

    private MechanicsHandler mechanics;

    /**
     * This timer run all those method like collision handlers or repaints frame.
     */
    private Timer timer = new Timer(8, e -> {
        ball.setySpeed(ball.getySpeed() + BALL_GRAVITY);
        player1.setYSpeed( player1.getYSpeed() + PLAYER_GRAVITY );
        player2.setYSpeed( player2.getYSpeed() + PLAYER_GRAVITY );

        ball.move();
        player1.move();
        player2.move();

        mechanics.handlePlayerBallCollision(ball, player1);
        mechanics.handlePlayerBallCollision(ball, player2);

        mechanics.handleBallCollision(ball);

        mechanics.handlePlayersCollisions(player1);
        mechanics.handlePlayersCollisions(player2);

        repaint();
    });

    /**
     * Main constructor.
     * @param face1 first player's face
     * @param face2 second player's face
     */
    public GamePlay(Face face1, Face face2) {
        this.setMinimumSize(SCREEN_DIMENSION);
        this.setPreferredSize(SCREEN_DIMENSION);
        this.setMaximumSize(SCREEN_DIMENSION);

        mechanics = new MechanicsHandler(this);

        loadImages();
        reset(face1, face2);
        initializeListeners(face1, face2);

        this.setFocusable(true);

        timer.start();
    }

    /**
     * This method loads all important images.
     */
    private void loadImages() {
        try {
            sand = ImageIO.read(new File("graphics/environment/sand.png"));
            bg = ImageIO.read(new File("graphics/environment/bg.png"));
            net = ImageIO.read(new File("graphics/environment/net.png"));
            reset = ImageIO.read(new File("graphics/environment/reset.png"));
            menu = ImageIO.read(new File("graphics/environment/menu.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes listeners
     * @param face1 first player's face
     * @param face2 second player's face
     */
    private void initializeListeners(Face face1, Face face2) {
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
                else if(keyCode == KeyEvent.VK_P) {
                    if(timer.isRunning())
                        timer.stop();
                    else
                        timer.start();
                }
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
                    reset(face1, face2);
                else if(e.getX() >= MENU_BUTTON.x && e.getX() <= MENU_BUTTON.x + MENU_BUTTON.width && e.getY() >= MENU_BUTTON.y && e.getY() <= MENU_BUTTON.y + MENU_BUTTON.height) {
                    new ChooseFrame();
                    dispose();
                }
            }
        });
    }

    /**
     * This method dispose the game.
     */
    private void dispose() {
        timer.stop();
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.removeAll();
        topFrame.dispose();
        timer = null;
    }

    /**
     * This method resets game. Creates new players and ball etc.
     */
    private void reset(Face face1, Face face2) {
        player1 = new Player(40, 600, face1);
        player2 = new Player(SCREEN_DIMENSION.width - 40 - PLAYER_R * 2, 600, face2);

        if((new Random()).nextBoolean())
            ball = new Ball(100, 10);
        else
            ball = new Ball(SCREEN_DIMENSION.width - 100 - BALL_R * 2, 10);

        mechanics.setPlaying(true);
    }

    @Override
    public void paint(Graphics g) {
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //environment
        g.drawImage(bg,0,0,null);
        g.drawImage(net, NET_RECTANGLE.x, NET_RECTANGLE.y, null);
        g.drawImage(sand, 0, SCREEN_DIMENSION.height - sand.getHeight(null), null);

        player1.paint(g, mechanics.isPlaying());
        player2.paint(g, mechanics.isPlaying());

        //ball
        g.setColor(BALL_COLOR);
        g.fillOval((int) ball.getPosX(), (int) ball.getPosY(), BALL_R * 2, BALL_R * 2);

        //cursor
        if(ball.getPosY() < -BALL_R * 2) {
            g.setColor(BALL_COLOR);
            g.fillRect((int) (ball.getPosX() + BALL_R - 10), 0, 20, 10);
        }

        //buttons
        g.drawImage(reset, RESET_BUTTON.x, RESET_BUTTON.y, null);
        g.drawImage(menu, MENU_BUTTON.x, MENU_BUTTON.y, null);

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

            //ball speed
            g.drawString("" + ball.getSpeed(), (int) ball.getPosX(), (int) ball.getPosY());
        }
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    /**
     * Creates new gameplay and gameplay's frame
     * @param face1 first player's face
     * @param face2 second player's face
     */
    public static void newGamePlay(Face face1, Face face2) {
        JFrame frame = new JFrame("Volleyball");
        frame.add(new GamePlay(face1, face2));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
