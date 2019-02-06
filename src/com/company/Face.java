package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Face {
    final Image MAIN_FACE;
    final Image MAIN_FACE_R;
    final Image[] ANIMATION;

    private int animationIt = 0;
    private int animationCurrentDelay = 0;
    private final int ANIMATION_DELAY;

    public Face(Image mainFace, Image[] anim, int animationDelay) {
        this.MAIN_FACE = mainFace;
        this.MAIN_FACE_R = flipImageVertically(mainFace);
        ANIMATION = anim;
        ANIMATION_DELAY = animationDelay;
    }

    private Image flipImageVertically(Image img) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-img.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter((BufferedImage) img, null);
    }

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

    public Image getAnimationFrame() {

        animationCurrentDelay++;

        if(animationCurrentDelay >= ANIMATION_DELAY) {
            animationCurrentDelay = 0;
            animationIt++;
        }

        if(animationIt >= ANIMATION.length)
            animationIt = 0;

        return ANIMATION[animationIt];
    }
}
