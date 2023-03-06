package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

	GamePanel gp;
	KeyHandler keyH;
	
	public Player (GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		setDefaultValues(); //aufruf der setdefautvalues methode
		getPlayerImage();
	}
	
	public void setDefaultValues () {
		
		x=100;
		y=100;
		
		direction = "down";
	}
	public void getPlayerImage () {
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/link_up_1.png")); //speichert die Bilder in Variablen aus Player
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/link_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/link_front_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/link_front_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/link_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/link_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/link_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/link_right_2.png"));
			
			
		} catch (IOException e) {
			e.printStackTrace(); //TODO erklären
		}
		
	}
	public void update () { //60 mal die Sekunde Aufgerufen
		//TODO einstellungen für eine Variable FPS anzahl
			if (keyH.upPressed == true || keyH.downPressed == true 
					|| keyH.leftPressed == true || keyH.rightPressed == true) { //Sprite wechselt nicht, wenn nichts gedrückt wird
				if(keyH.upPressed == true) { //aus dem KeyHandler input abfragen | true = buttonPressed , false = !buttonPressed
					direction = "up"; //hilfe damit wir einfacher auf die Richtung von dem Player zugreifen können
					y -= speed; // kurzschreibweise von playerY = playerY - playerSpeed;
				}
				else if(keyH.downPressed == true) {
					direction = "down";
						y += speed;
				}
				else if(keyH.leftPressed == true) {
					direction = "left";
					x -= speed;
				}
				else if(keyH.rightPressed == true) {
					direction = "right";
					x += speed;
				}
				
				spriteCounter++; 	//der Switch, welcher die Sprite Animation Steuert
				if (spriteCounter >12) {
					if(spriteNum ==1) {
						spriteNum =2;
					}
					else if (spriteNum == 2) {
						spriteNum = 1;
					}
					spriteCounter = 0;
				}
				
			
			}
				//Zusammenfassend ist playerSpeed die Anzahl an Pixeln, wie sich der Character auf dem Bildschirm bewegt. also 100 (X) + 4 (PlayerSpeed) = 104 (X)
				
	}
	public void draw (Graphics2D g2) {

		//g2.setColor(Color.white);
		//g2.fillRect(x, y, gp.tileSize, gp.tileSize);	//Zeichnet ein Rechteck und füllt es mit gegebener farbe
		
		BufferedImage image = null;
		switch (direction) {
			case "up": 
				if (spriteNum == 1) { //Animation, wenn das eine bild mit der ersten sprite gezeichnet wurde, wird dies mit dem zweiten ersetzt
					image = up1;//speichere ausgehend von dem Imput das Jeweilige bild
				}
				if (spriteNum == 2) {
					image = up2;
				}
				break;
			case "down":
				if (spriteNum == 1) {
					image = down1;
				}
				if (spriteNum == 2) {
					image = down2;
				}
				break;
			case "left":
				if (spriteNum == 1) {
					image = left1;
				}
				if (spriteNum == 2) {
					image = left2;
				}
				break;
			case "right":
				if (spriteNum == 1) {
					image = right1;
				}
				if (spriteNum == 2) {
					image = right2;
				}
				
				break;
		}
		g2.drawImage (image, x, y, gp.tileSize, gp.tileSize, null); //Zeichnet den Player
	
	
}
}
