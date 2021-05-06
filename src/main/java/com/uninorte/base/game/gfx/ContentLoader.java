package com.uninorte.base.game.gfx;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ContentLoader {

    public static BufferedImage loadImage(String path){
        try {
            return ImageIO.read(ContentLoader.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public static Icon loadImageGif(String path) {
        try {
            return new ImageIcon(ContentLoader.class.getResourceAsStream(path).readAllBytes());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

    public static InputStream loadAudio(String path) {
        try {
            return new BufferedInputStream(ContentLoader.class.getResourceAsStream(path));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

}