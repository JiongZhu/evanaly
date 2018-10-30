package com.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class ImageUtil {
	
	private static Random random = new Random();
	
	public static void outputImage(int w, int h, OutputStream os, String code) throws IOException {
		int verifySize = code.length();
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Random rand = new Random();
		Graphics2D graphics = image.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, w, h);
		
		Random random = new Random();
		for(int i = 0; i < 20; i++) {
			graphics.setColor(getRandColor(160, 200));
			int x1 = random.nextInt(w);
			int y1 = random.nextInt(h);
			int x2 = random.nextInt(w);
			int y2 = random.nextInt(h);
			graphics.drawLine(x1, y1, x2, y2);
		}
		

		float yawRate = 0.05f;
		int area = (int) (yawRate * w * h);
		for(int i = 0; i < area; i++) {
			int x = random.nextInt(w);
			int y = random.nextInt(h);
			int rgb = getRandomIntColor();
			image.setRGB(x, y, rgb);
		}
		
		shear(graphics, w, h, Color.WHITE);
		
		graphics.setColor(getRandColor(100, 160));
		int fontSize = h - 4;
		Font font = new Font("Algerian", Font.ITALIC, fontSize);
		graphics.setFont(font);
		char[] chars = code.toCharArray();
		
		for(int i = 0; i < verifySize; i++) {
			AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1), (w / verifySize) * i + fontSize/2, h/2);
            graphics.setTransform(affine);
            graphics.drawChars(chars, i, 1, ((w-10) / verifySize) * i + 5, h/2 + fontSize/2 - 10);
		}
		
		graphics.dispose();
		ImageIO.write(image, "jpg", os);
	}
	
	private static Color getRandColor(int fc, int bc) {
		if (fc > 255)
            fc = 255;
		
        if (bc > 255)
            bc = 255;
        
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        
        return new Color(r, g, b);
	}
	
	private static int getRandomIntColor() {
		int[] rgb = getRandomRgb();
		int color = 0;
		
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        
        return color;
	}
	
	private static int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }
	
	private static void shear(Graphics graphics, int w, int h, Color color) {
		shearX(graphics, w, h, color);
		shearY(graphics, w, h, color);
	}
	
	private static void shearX(Graphics graphics, int w, int h, Color color) {
		int period = random.nextInt(2);
		
		boolean borderGap = true;
		int frames = 1;
		int phase = random.nextInt(2);
		
		for (int i = 0; i < h; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                            + (6.2831853071795862D * (double) phase)
                            / (double) frames);
            graphics.copyArea(0, i, w, 1, (int) d, 0);
            if (borderGap) {
                graphics.setColor(color);
                graphics.drawLine((int) d, i, 0, i);
                graphics.drawLine((int) d + w, i, w, i);
            }
        }
	}
	
	private static void shearY(Graphics graphics, int w, int h, Color color) {
		int period = random.nextInt(40) + 10;
		
		boolean borderGap = true;
		int frames = 20;
		int phase = 7;
		
		for (int i = 0; i < 2; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                            + (6.2831853071795862D * (double) phase)
                            / (double) frames);
            graphics.copyArea(i, 0, 1, h, 0, (int) d);
            if (borderGap) {
                graphics.setColor(color);
                graphics.drawLine(i, (int) d, i, 0);
                graphics.drawLine(i, (int) d + h, i, h);
            }
        }
	}
}
