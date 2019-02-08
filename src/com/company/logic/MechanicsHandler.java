package com.company.logic;

import com.company.GamePlay;
import com.company.elements.Ball;
import com.company.elements.Player;

import static com.company.Ref.*;

public class MechanicsHandler {

    private AudioHandler audio;
    private GamePlay gamePlay;

    /**
     * When the game is going on
     */
    private boolean isPlaying = true;

    public MechanicsHandler(GamePlay gamePlay) {
        audio = new AudioHandler(null, null);
        this.gamePlay = gamePlay;
    }

    /**
     * This method check if <code>ball</code> and <code>player</code> collide and if so, it handle this collision.
     * @param ball
     * @param player the player who is being checked
     */
    public void handlePlayerBallCollision(Ball ball, Player player) {
        double ballPosX = ball.getPosX() + BALL_R;
        double ballPosY = ball.getPosY() + BALL_R;
        double playerPosX = player.getPosX() + PLAYER_R;
        double playerPosY = player.getPosY() + PLAYER_R;
        double distance = Math.sqrt(Math.pow(ballPosX - playerPosX, 2) + Math.pow(ballPosY - playerPosY, 2));

        Player player1 = gamePlay.getPlayer1();
        Player player2 = gamePlay.getPlayer2();

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
    public void handleBallCollision(Ball b) {
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
        if(b.getPosY() + BALL_R * 2 > gamePlay.FLOOR_RECTANGLE.y) {
            b.setPosY(gamePlay.FLOOR_RECTANGLE.y - BALL_R * 2);
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

        if(centerY > gamePlay.NET_RECTANGLE.y) {
            if(Math.abs(centerX - gamePlay.NET_RECTANGLE.x) < BALL_R) {
                audio.playHit();
                b.setPosX(gamePlay.NET_RECTANGLE.x - BALL_R * 2);
                b.setxSpeed( -b.getxSpeed() );
            }
            else if(Math.abs(centerX - gamePlay.NET_RECTANGLE.x - gamePlay.NET_RECTANGLE.width) < BALL_R) {
                audio.playHit();
                b.setPosX(gamePlay.NET_RECTANGLE.x + gamePlay.NET_RECTANGLE.width);
                b.setxSpeed( -b.getxSpeed() );
            }
        }
        else if(centerX > gamePlay.NET_RECTANGLE.x && centerX < gamePlay.NET_RECTANGLE.x + gamePlay.NET_RECTANGLE.width && Math.abs(centerY - gamePlay.NET_RECTANGLE.y) < BALL_R) {
            audio.playHit();
            b.setPosY(gamePlay.NET_RECTANGLE.y - BALL_R * 2);
            b.setySpeed( -b.getySpeed() );
        }
        else if(Math.sqrt(Math.pow(centerX - gamePlay.NET_RECTANGLE.x, 2) + Math.pow(centerY - gamePlay.NET_RECTANGLE.y, 2)) < BALL_R) {
            audio.playHit();
            double speed = Math.abs(b.getySpeed()) + Math.abs(b.getxSpeed());
            double angle = Math.atan((centerY - gamePlay.NET_RECTANGLE.y) / (centerX - gamePlay.NET_RECTANGLE.x));
            b.setxSpeed(-Math.abs(speed * Math.cos(angle)));
            b.setySpeed(-Math.abs(speed * Math.sin(angle)));
        }
        else if(Math.sqrt(Math.pow(centerX - gamePlay.NET_RECTANGLE.x - gamePlay.NET_RECTANGLE.width, 2) + Math.pow(centerY - gamePlay.NET_RECTANGLE.y, 2)) < BALL_R) {
            audio.playHit();
            double speed = Math.abs(b.getySpeed()) + Math.abs(b.getxSpeed());
            double angle = Math.atan((centerY - gamePlay.NET_RECTANGLE.y) / (centerX - gamePlay.NET_RECTANGLE.x - gamePlay.NET_RECTANGLE.width));
            b.setxSpeed(Math.abs(speed * Math.cos(angle)));
            b.setySpeed(-Math.abs(speed * Math.sin(angle)));
        }
    }

    /**
     * This method check if <code>player</code> collide with environment and if so, it handle this collision.
     * @param player
     */
    public void handlePlayersCollisions(Player player) {
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
        if(player.getPosX() < gamePlay.NET_RECTANGLE.x && player.getPosX() + PLAYER_R * 2 > gamePlay.NET_RECTANGLE.x)
            player.setPosX(gamePlay.NET_RECTANGLE.x - PLAYER_R * 2);
        else if(player.getPosX() + PLAYER_R * 2 > gamePlay.NET_RECTANGLE.x && player.getPosX() < gamePlay.NET_RECTANGLE.x + gamePlay.NET_RECTANGLE.width)
            player.setPosX(gamePlay.NET_RECTANGLE.x + gamePlay.NET_RECTANGLE.width);

    }

    /**
     * If player get point, it handle it.
     * @param player number of player. 1 - player1, 2 - player2. If you send other number Exception will be thrown.
     */
    private void point(int player) {
        final int DELAY = 2000;
        Player player1 = gamePlay.getPlayer1();
        Player player2 = gamePlay.getPlayer2();


        isPlaying = false;

        if(player == 2) {
            player2.incrementPoints();
            player2.setGotPoint(true);
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            gamePlay.setBall( new Ball(SCREEN_DIMENSION.width - 100 - BALL_R * 2, 10) );
                            isPlaying = true;
                            player2.setGotPoint(false);
                        }
                    },
                    DELAY
            );
        }
        else if(player == 1) {
            player1.incrementPoints();
            player1.setGotPoint(true);
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            gamePlay.setBall( new Ball(100, 10) );
                            isPlaying = true;
                            player1.setGotPoint(false);
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


    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
