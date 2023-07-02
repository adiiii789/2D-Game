package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity {

	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	public int hasKey = 0;
	
	public Player (GamePanel gp, KeyHandler keyH) {
		
		super(gp); //es ist da um einen komischen Fehler zu beheben
		
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2); //da dies eine final variable ist, wird diese nicht verändert, das heißt der Character wird immer im Zentrum des Fensters gezeichnet
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle(); //Auslagerung der Parameter
		solidArea.x = 12;
		solidArea.y = 21;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 24;
		solidArea.height = 24;
		
		
		setDefaultValues(); //aufruf der setdefautvalues methode
		getPlayerImage();
	}
	
	public void setDefaultValues () {
		
		worldX=gp.tileSize*8; //Zahl gibt startposition auf der karte wieder
		worldY=gp.tileSize*6; //
		speed = 4;
		
		direction = "down";
	}
	public void getPlayerImage () {
	
		up1 = setup("/klee/back1");
		up2 = setup("/klee/back2");
		down1 = setup("/klee/front1");
		down2 = setup("/klee/front2");
		left1 = setup("/klee/left1");
		left2 = setup("/klee/left2");
		right1 = setup("/klee/right1");
		right2 = setup("/klee/right2");

	}

	public void update () { //60 mal die Sekunde Aufgerufen
			if (keyH.upPressed == true || keyH.downPressed == true 
					|| keyH.leftPressed == true || keyH.rightPressed == true) { //Sprite wechselt nicht, wenn nichts gedrückt wird
				if(keyH.upPressed == true) { //aus dem KeyHandler input abfragen | true = buttonPressed , false = !buttonPressed
					direction = "up"; //hilfe damit wir einfacher auf die Richtung von dem Player zugreifen können
					
				}
				else if(keyH.downPressed == true) {
					direction = "down";
					
				}
				else if(keyH.leftPressed == true) {
					direction = "left";
					
				}
				else if(keyH.rightPressed == true) {
					direction = "right";
					
				}
				
				//Überprüfung der Tile Collision
				collisionOn = false;
				gp.cDetection.checkTile(this);
				
				//Überprüfung der Objekt Collision
				int objIndex = gp.cDetection.checkObject(this,true);
				pickUpObject(objIndex);
				
				int npcIndex = gp.cDetection.checkEntity(this, gp.npc);
				interactNPC(npcIndex);
				
				// wenn die Collision auf False steht, kann der Spieler sich bewegen
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
				}}
				
			
			}
				//Zusammenfassend ist playerSpeed die Anzahl an Pixeln, wie sich der Character auf dem Bildschirm bewegt. also 100 (X) + 4 (PlayerSpeed) = 104 (X)
				
	
	public void pickUpObject(int i) {
		
		if(i != 999) {
			
			
			String objectName = gp.obj[i].name;
			
			switch (objectName) {
			
			case "Key":
				hasKey++;
				gp.obj[i] = null; //sobald der Schlüssel berührt wird, wird er "gelöscht"
				gp.ui.showMessage("You got a Key!");
				break;
			case "Door":
				if (hasKey > 0) {
					gp.obj[i] = null;
					hasKey--;
					gp.ui.showMessage("You opend the door!");
				}
				else {
					gp.ui.showMessage("You need a key!");
					
				}
				
				break;
			case "Boots":
				speed += 2; //wenn die Schuhe eingesammelt werden, wird der Spieler Schneller
				gp.obj[i] = null;
				gp.ui.showMessage("Speed up!");
				break;
			case "Chest":
				gp.ui.gameFinished = true;
				break;
				
			
			
			}
			
		}
	}
	public void interactNPC(int i) {
		if(i != 999) {
			
			if(gp.keyH.enterPressed == true) {
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}
			
			
		}
		gp.keyH.enterPressed = false;
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
		
		int x = screenX;
		int y = screenY;
		
		if (screenX > worldX) {
			x = worldX;
		}
		if (screenY > worldY) {
			y = worldY;
		}
		int rightOffset = gp.screenWidth - screenX;
		if (rightOffset > gp.worldWidth - worldX) {
			x = gp.screenWidth - (gp.worldWidth - worldX);
		}
		int bottomOffset = gp.screenHeight - screenY;
		if (bottomOffset > gp.worldHeight - worldY) {
			y = gp.screenHeight - (gp.worldHeight - worldY);
		}
		
			
		
		g2.drawImage (image, x, y, null); //Zeichnet den Player
	
	
}
}
//test