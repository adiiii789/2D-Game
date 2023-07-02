package entity;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

import java.awt.Rectangle;


public class Entity {
	
	GamePanel gp;
	public int worldX, worldY;
	public int speed = 4;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; //speichert die PNG dateien TODO Buffer bescheiben
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle solidArea; //Erstellt ein Unsichtbbares Rechteck, welches uns als collisionbox dient. In modernen 3D Spielen nutzt man dafür einen Capsule collider
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	public void draw (Graphics2D g2) {
		BufferedImage image = null;
		int screenX = worldX - gp.player.worldX + gp.player.screenX; //Wo wird das Tile auf dem Fenster gezeichnet
		int screenY = worldY - gp.player.worldY + gp.player.screenY; //screenX = 0 heißt also vom Fenster die obere linke ecke
																	 //der part neben worldxy ist ein Offset, damit der Player nicht in der Ecke dargestellt wird
		  if(gp.player.worldX < gp.player.screenX) {
			   screenX = worldX;
			  }
			  if(gp.player.worldY < gp.player.screenY) {
			   screenY = worldY;
			  }   
			  int rightOffset = gp.screenWidth - gp.player.screenX;      
			  if(rightOffset > gp.worldWidth - gp.player.worldX) {
			   screenX = gp.screenWidth - (gp.worldWidth - worldX);
			  } 
			  int bottomOffset = gp.screenHeight - gp.player.screenY;
			  if(bottomOffset > gp.worldHeight - gp.player.worldY) {
			   screenY = gp.screenHeight - (gp.worldHeight - worldY);
			  }
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
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
			
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize,null);

			
		}
	}
	public BufferedImage setup(String imagePath) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath +".png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
//test
