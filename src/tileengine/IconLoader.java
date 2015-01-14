/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tileengine;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.nio.ByteBuffer;
import java.io.File;
import java.io.IOException;
import org.lwjgl.opengl.Display;

/**
 *
 * @author Jyri
 */
public class IconLoader {

    public static ByteBuffer loadIcon(String filename, int width, int height) throws IOException {
        BufferedImage image = ImageIO.read(new File(filename)); // load image

        // convert image to byte array
        byte[] imageBytes = new byte[width * height * 4];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = image.getRGB(j, i);
                for (int k = 0; k < 3; k++) // red, green, blue
                {
                    imageBytes[(i * 16 + j) * 4 + k] = (byte) (((pixel >> (2 - k) * 8)) & 255);
                }
                imageBytes[(i * 16 + j) * 4 + 3] = (byte) (((pixel >> (3) * 8)) & 255); // alpha
            }
        }
        return ByteBuffer.wrap(imageBytes);
    }

    public static void setIcon() {
        try {
            ByteBuffer[] icons = new ByteBuffer[2];
            icons[0] = loadIcon("res/icon_16.png", 16, 16);
            icons[1] = loadIcon("res/icon_32.png", 32, 32);
            Display.setIcon(icons);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
