package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UtilityTool {
	
	/**
	 * Scaled alle Images separat, welches der Performance hilft
	 * @param original
	 * @param width
	 * @param height
	 * @return
	 */
	public BufferedImage scaleImage(BufferedImage original, int width, int height) {
		
	
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType()); //höhe, breite und Typ des Bildes wird auf die Variable übertragen
		Graphics2D g2 = scaledImage.createGraphics(); //gezeichnete Graphic wird als Graphics2D in die Variable gespeichert
		g2.drawImage(original, 0, 0, width, height, null); //Zeichnet das Bild, und Speichert es in die Variable
		g2.dispose();
		
		return scaledImage;
		
	}

}
