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
	public String direction = "down";
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle solidArea = new Rectangle(0,0, 48, 48); //Erstellt ein Unsichtbbares Rechteck, welches uns als collisionbox dient. In modernen 3D Spielen nutzt man dafür einen Capsule collider
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	public int actionLockCounter = 0;
	String dialogues[] = new String[20];
	int dialogueIndex = 0;
	public BufferedImage image, image2, image3;
	public String name;
	public boolean collision = false;
	
	public int maxLife;
	public int life;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	public void setAction() { //kann von der Subklasse überschrieben werden
		
	}
	public void speak() {
		if (dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;
		
		switch (gp.player.direction) {
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;
		}
		
	}
	public void update() {
		setAction(); //priorisiert die setAction von der Subclass
		
		collisionOn = false;
		gp.cDetection.checkTile(this);
		gp.cDetection.checkObject(this, false);
		gp.cDetection.checkPlayer(this);
		
		if(collisionOn == false) { 
			
			switch(direction) {
			case "up":
				worldY -= speed; // kurzschreibweise von playerY = playerY - playerSpeed;
				break;
			case "down":
				worldY += speed;
				break;
			case "left":
				worldX -= speed;
				break;
			case "right":
				worldX += speed;
				break;
			case "null":
				worldX = 0;
			}
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
		
		
	
	 public void draw(Graphics2D g2) {
		  
		  BufferedImage image = null;
		  int screenX = worldX - gp.player.worldX + gp.player.screenX;
		  int screenY = worldY - gp.player.worldY + gp.player.screenY;
		  
		  // STOP MOVING CAMERA
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
		  ///////////////////
		  
		  switch(direction) {
		  case "up":
		   if(spriteNum == 1) {
		    image = up1;
		   }
		   if(spriteNum == 2) {
		    image = up2;
		   }
		   break;
		  case "down":
		   if(spriteNum == 1) {
		    image = down1;
		   }
		   if(spriteNum == 2) {
		    image = down2;
		   }
		   break;
		  case "left":
		   if(spriteNum == 1) {
		    image = left1;    
		   }   
		   if(spriteNum == 2) {
		    image = left2;
		   }
		   break;
		  case "right":
		   if(spriteNum == 1) {
		    image = right1;
		   }   
		   if(spriteNum == 2) {
		    image = right2;
		   }
		   break;
		  }
		  
		  if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
		     worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
		     worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
		     worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {      
		   g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		  }
		  // If player is around the edge, draw everything
		  else if(gp.player.worldX < gp.player.screenX ||
		    gp.player.worldY < gp.player.screenY ||
		    rightOffset > gp.worldWidth - gp.player.worldX ||
		    bottomOffset > gp.worldHeight - gp.player.worldY) {
		   g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); 
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
