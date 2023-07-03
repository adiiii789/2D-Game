package entity;

import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;
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
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; //speichert die PNG dateien TODO Was ist BufferedImage?
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
	public BufferedImage image, image2, image3;
	public String direction = "down";
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle solidArea = new Rectangle(0,0, 48, 48); //Erstellt ein Unsichtbbares Rechteck, welches uns als collisionbox dient. In modernen 3D Spielen nutzt man daf�r einen Capsule collider
	public Rectangle attackArea = new Rectangle(0,0,0,0);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	public int actionLockCounter = 0;
	public boolean invincible = false;
	public int invincibleCounter = 0;
	int dyingCounter = 0;
	String dialogues[] = new String[20];
	int dialogueIndex = 0;
	boolean attacking = false;
	boolean alive = true;
	boolean dying = false;

	public String name;
	public boolean collision = false;
	public int type; //0 = Player, 1 = npc, !2 = monster!
	
	public int maxLife;
	public int life;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	public void setAction() { //kann von der Subklasse �berschrieben werden
		
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
		gp.cDetection.checkEntity(this, gp.npc);
		gp.cDetection.checkEntity(this, gp.monster);
		boolean contactplayer = gp.cDetection.checkPlayer(this);
		
		if (this.type == 2 && contactplayer == true) {
			if(gp.player.invincible == false) {
				gp.player.life -= 1;
				gp.player.invincible = true;
			}
		}
		
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
		if (invincible == true) {
			invincibleCounter++;
			if(invincibleCounter > 40) {
				invincible = false;
				invincibleCounter = 0;
			}
			
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
		   if(spriteNum == 1) {image = up1;}
		   if(spriteNum == 2) {image = up2;}
		   break;
		  case "down":
		   if(spriteNum == 1) {image = down1;}
		   if(spriteNum == 2) {image = down2;}
		   break;
		  case "left":
		   if(spriteNum == 1) {image = left1;}   
		   if(spriteNum == 2) {image = left2;}
		   break;
		  case "right":
		   if(spriteNum == 1) {image = right1;}   
		   if(spriteNum == 2) {image = right2;}
		   break;
		  }
			if (invincible == true) {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F)); //Spieler wird Halbtransparent angezeigt
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
		   
		   g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
		  }
	 }
	 

	public BufferedImage setup(String imagePath, int width, int height) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath +".png"));
			image = uTool.scaleImage(image, width, height);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
//test
