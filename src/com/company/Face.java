package com.company;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Face {
    final Image MAIN_FACE;
    final Image MAIN_FACE_R;

    public Face(Image mainFace) {
        this.MAIN_FACE = mainFace;
        this.MAIN_FACE_R = flipImageVertically(mainFace);
    }

    private Image flipImageVertically(Image img) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-img.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter((BufferedImage) img, null);
    }
}
