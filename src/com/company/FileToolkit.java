package com.company;

import java.io.File;
import java.util.ArrayList;

public class FileToolkit {

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

        System.out.println(facesAR.size());

        return faces;
    }

    private static boolean isFaceDirectory(File f) {
        return new File(f + File.separator + "img.png").exists();
    }
}
