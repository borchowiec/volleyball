package com.company.elements;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class represents look of player character. Contains methods to manipulate this look e.g. animating.
 */
public class Face {
    public final Image MAIN_FACE;

    /**
     * Horizontal reversed face
     */
    public final Image MAIN_FACE_R;

    /**
     * Every frame of animation
     */
    public final Image[] ANIMATION;

    /**
     * an iterator that points the current frame
     */
    private int animationIt = 0;

    /**
     * If it's greater than <code>ANIMATION_DELAY</code> then set to 0 and change <code>animationIt</code>.
     * this operation is carried out by <code>getAnimationFrame</code> method.
     */
    private int animationCurrentTime = 0;

    /**
     * Delay between animation frames
     */
    private final int ANIMATION_DELAY;

    /**
     * Main constructor.
     * @param mainFace Image of face
     * @param anim Frames of animations
     * @param animationDelay delay between animation frames
     */
    public Face(Image mainFace, Image[] anim, int animationDelay) {
        this.MAIN_FACE = mainFace;
        this.MAIN_FACE_R = flipImageHorizontally(mainFace);
        ANIMATION = anim;
        ANIMATION_DELAY = animationDelay;
    }

    /**
     * This method returns flipped horizontally image.
     * @param img Image that you want to flip
     * @return Flipped <code>img</code>
     */
    private Image flipImageHorizontally(Image img) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-img.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter((BufferedImage) img, null);
    }

    /**
     * This method returns instance of <code>Face</code> class. You have to give
     * file of 'face' directory.
     * @param f File of 'face' directory.
     * @return instance of <code>Face</code> class.
     */
    public static Face toFace(File f) {
        try {
            Image img = ImageIO.read(new File(f + File.separator + "img.png"));
            Image[] anim = toImgArray(new File(f + File.separator + "animation"));

            return new Face(img, anim, 20);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Puts into array all of animation frames.
     * @param file Directory of faces animation
     * @return Array with animation frames.
     */
    private static Image[] toImgArray(File file) {
        File[] files = file.listFiles();
        Image[] images = new Image[files.length];
        for(int i = 0; i < files.length; i++) {
            try {
                images[i] = ImageIO.read(files[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return images;
    }

    /**
     * Returns current frame of animation. Sets which frame is current.
     * @return Current animation frame.
     */
    public Image getAnimationFrame() {
        animationCurrentTime++;

        if(animationCurrentTime >= ANIMATION_DELAY) {
            animationCurrentTime = 0;
            animationIt++;
        }

        if(animationIt >= ANIMATION.length)
            animationIt = 0;

        return ANIMATION[animationIt];
    }
}
