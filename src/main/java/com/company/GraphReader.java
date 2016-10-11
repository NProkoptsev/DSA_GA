package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GraphReader {
    public static int[] seeBMPImage(String BMPFileName, ArrayList<Integer> reds, int redSize) throws IOException {
        BufferedImage image = ImageIO.read(new File(BMPFileName));

        int[] array2D = new int[image.getHeight() * image.getWidth()];
        int redX = 0, redY = 0, count = 0;
        int curX = -1, curY = -1;
        boolean isNewRedDot = true;
        int size = image.getHeight();
        for (int yPixel = 0; yPixel < image.getWidth(); yPixel++) {
            for (int xPixel = 0; xPixel < image.getHeight(); xPixel++) {
                int color = image.getRGB(xPixel, yPixel);
                if (color == Color.BLACK.getRGB()) {
                    array2D[yPixel * size + xPixel] = -1;
                } else {
                    array2D[yPixel * size + xPixel] = 0;

                    if (color == Color.RED.getRGB()) {
                        if (isNewRedDot) {
                            curX = xPixel;
                            curY = yPixel;
                            isNewRedDot = false;
                        }

                        if (Math.abs(xPixel - curX) > redSize || Math.abs(yPixel - curY) > redSize) {
                            reds.add(redY / count * size + redX / count);
                            redX = xPixel;
                            redY = yPixel;
                            count = 1;
                        } else {
                            redX += xPixel;
                            redY += yPixel;
                            count++;
                        }
                        curX = xPixel;
                        curY = yPixel;
                    }
                }
            }
        }
        reds.add(redY / count * size + redX / count);
        return array2D;
    }


}
