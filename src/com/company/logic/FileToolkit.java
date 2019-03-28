package com.company.logic;

import com.company.elements.Face;

import java.io.File;
import java.util.ArrayList;

/**
 * This class contains methods that operate on files.
 */
public class FileToolkit {

    /**
     * This methods is looking for faces directories and returns list of instance of faces.
     * @return Array that contains faces.
     */
    public static Face[] getFaces() {

        File facesDirectory = new File("graphics/faces");
        File[] facesDirectories = facesDirectory.listFiles();

        ArrayList<Face> facesAR = new ArrayList<>();

        for(File f: facesDirectories) {
            if(isFaceDirectory(f))
                facesAR.add( Face.toFace(f) );
        }

        Face[] faces = new Face[facesAR.size()];

        for(int i = 0; i < faces.length; i++) {
            faces[i] = facesAR.get(i);
        }

        return faces;
    }

    /**
     * This method checks if face directory has every important elements.
     * @param f Directory of face.
     * @return If everything is ok, returns true.
     */
    private static boolean isFaceDirectory(File f) {
        if(!new File(f + File.separator + "img.png").exists())
            return false;
        if(!new File(f + File.separator + "animation").exists())
            return false;
        if(new File(f + File.separator + "animation").listFiles().length == 0)
            return false;
        return true;
    }
}
