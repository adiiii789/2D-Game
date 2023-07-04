package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.AlphaComposite;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Bomb_Nomal;
import object.OBJ_Boots;
import object.OBJ_Fireball;
import object.OBJ_Shield_Wood;

public class Player extends Entity {

	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	public int hasKey = 0;
	int standCounter = 0;
	public boolean attackCanceled = false;
	
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;
	int manacounter = 0;
	
	
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
		
		//attackArea.width = 48;
		//attackArea.height = 48;
		
		
		setDefaultValues(); //aufruf der setdefautvalues methode
		getPlayerImage();
		getPlayerAttackImage();
		setItems();
	}
	
	public void setDefaultValues () {
		
		worldX=gp.tileSize*8; //Zahl gibt startposition auf der karte wieder
		worldY=gp.tileSize*6; //
		speed = 4;
		
		direction = "down";
		
		level = 1;
		maxLife = 6;
		life = maxLife;
		maxMana = 4;
		mana = maxMana;
		strength = 1; //str = attack
		dexterity = 1; //dex = shield
		exp = 0;
		nextLevelExp = 5;
		coin = 0;
		currentWeapon = new OBJ_Bomb_Nomal(gp);
		currentShield = new OBJ_Shield_Wood(gp);
		projectile = new OBJ_Fireball(gp);
		attack = getAttack();
		defense = getDefense();
	}
	public void setItems() {
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		inventory.add(new OBJ_Boots(gp));
		
	}
	public int getAttack() {
		attackArea = currentWeapon.attackArea;
		
		return attack = strength *currentWeapon.attackValue;
	}
	public int getDefense() {
		return defense = dexterity * currentShield.defenseValue;
		
	}
	public void getPlayerImage () {
	
		up1 = setup("/klee/back1",gp.tileSize,gp.tileSize);
		up2 = setup("/klee/back2",gp.tileSize,gp.tileSize);
		down1 = setup("/klee/front1",gp.tileSize,gp.tileSize);
		down2 = setup("/klee/front2",gp.tileSize,gp.tileSize);
		left1 = setup("/klee/left1",gp.tileSize,gp.tileSize);
		left2 = setup("/klee/left2",gp.tileSize,gp.tileSize);
		right1 = setup("/klee/right1",gp.tileSize,gp.tileSize);
		right2 = setup("/klee/right2",gp.tileSize,gp.tileSize);

	}
	public void getPlayerAttackImage() {
		
		if(currentWeapon.type == type_bomb && gp.gameState == gp.playState) {
			attackUp1 = setup("/klee/back_attack1",gp.tileSize,gp.tileSize*2);
			attackUp2 = setup("/klee/back_attack2",gp.tileSize,gp.tileSize*2);
			attackDown1 = setup("/klee/front_attack1",gp.tileSize,gp.tileSize*2);
			attackDown2 = setup("/klee/front_attack2",gp.tileSize,gp.tileSize*2);
			attackLeft1 = setup("/klee/left_attack1",gp.tileSize*2,gp.tileSize);
			attackLeft2 = setup("/klee/left_attack2",gp.tileSize*2,gp.tileSize);
			attackRight1 = setup("/klee/right_attack1",gp.tileSize*2,gp.tileSize);
			attackRight2 = setup("/klee/right_attack2",gp.tileSize*2,gp.tileSize);
		}
		if(currentWeapon.type == type_xbomb && gp.gameState == gp.playState) {
			attackUp1 = setup("/klee/back_attack1",gp.tileSize,gp.tileSize*2);
			attackUp2 = setup("/klee/back_attackX2",gp.tileSize,gp.tileSize*2);
			attackDown1 = setup("/klee/front_attack1",gp.tileSize,gp.tileSize*2);
			attackDown2 = setup("/klee/front_attackX2",gp.tileSize,gp.tileSize*2);
			attackLeft1 = setup("/klee/left_attack1",gp.tileSize*2,gp.tileSize);
			attackLeft2 = setup("/klee/left_attackX2",gp.tileSize*2,gp.tileSize);
			attackRight1 = setup("/klee/right_attack1",gp.tileSize*2,gp.tileSize);
			attackRight2 = setup("/klee/right_attackX2",gp.tileSize*2,gp.tileSize);	
		}
		if (gp.gameState == gp.bullethellState) {
			up1 = setup("/klee/back1",gp.tileSize,gp.tileSize);
			up2 = setup("/klee/back2",gp.tileSize,gp.tileSize);
			down1 = setup("/klee/back1",gp.tileSize,gp.tileSize);
			down2 = setup("/klee/back2",gp.tileSize,gp.tileSize);
			left1 = setup("/klee/back1",gp.tileSize,gp.tileSize);
			left2 = setup("/klee/back",gp.tileSize,gp.tileSize);
			right1 = setup("/klee/back1",gp.tileSize,gp.tileSize);
			right2 = setup("/klee/back2",gp.tileSize,gp.tileSize);
		}
 	}

	public void update () { //60 mal die Sekunde Aufgerufen
			if(attacking == true) {
				attacking();
			}
		
			else if (keyH.upPressed == true || keyH.downPressed == true 
					|| keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) { //Sprite wechselt nicht, wenn nichts gedrückt wird
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
				
				int monsterIndex = gp.cDetection.checkEntity(this, gp.monster);
				contactMonster(monsterIndex);
				
				gp.eHandler.checkEvent();
				// wenn die Collision auf False steht, kann der Spieler sich bewegen
				if(collisionOn == false && keyH.enterPressed == false) { 
					
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
				if(keyH.enterPressed == true && attackCanceled == false) {
					attacking = true;
					spriteCounter =0;
					
				}
				
				attackCanceled = false;
				gp.keyH.enterPressed = false;
				
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
			else {
				standCounter++;
				if(standCounter == 20) {
					spriteNum = 1;
					standCounter = 0;
				}
			}
		if(gp.keyH.shotKeyPressed == true && projectile.alive == false && projectile.haveResource(this) == true) {
				
				
				projectile.set(worldX, worldY, direction, true, this);
				
				projectile.subtractResource(this);
				
				gp.projectileList.add(projectile);
				
				shotAvailableCounter = 0;
			}
		
		manacounter++;
		if(gp.player.mana < gp.player.maxMana && manacounter >= 200) {
			manacounter = 0;
			gp.player.mana +=1;
		}
			
				
			if (invincible == true) {
				invincibleCounter++;
				if(invincibleCounter > 60) {
					invincible = false;
					invincibleCounter = 0;
				}
				}
			
			}
				//Zusammenfassend ist playerSpeed die Anzahl an Pixeln, wie sich der Character auf dem Bildschirm bewegt. also 100 (X) + 4 (PlayerSpeed) = 104 (X)
				
	public void attacking() {
		spriteCounter++;
		
		if(spriteCounter <= 5) {
			spriteNum = 1;
		}
		if(spriteCounter > 5 && spriteCounter <= 25) {
			spriteNum = 2;
			
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			switch(direction) {
			case "up": worldY -= attackArea.height; break;
			case "down": worldY += attackArea.height; break;
			case "left": worldX -= attackArea.width; break;
			case "right": worldX += attackArea.width; break;
			}
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			
			int monsterIndex = gp.cDetection.checkEntity(this, gp.monster);
			damageMonster(monsterIndex, attack);
			
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		if(spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}
	public void pickUpObject(int i) {
		
		if(i != 999) {
			
			
			String text;
			
			if(inventory.size() != maxInventorySize) {
				
				inventory.add(gp.obj[i]);
				text = "got a "+ gp.obj[i].name +"!";
			}
			else {
				text ="You cannot carry any more!";
			}
			gp.ui.addMessage(text);
			
			gp.obj[i] = null;
			
		}
	}		
			
			
//			String objectName = gp.obj[i].name;
//			
//			switch (objectName) {
//			
//			case "Key":
//				hasKey++;
//				gp.obj[i] = null; //sobald der Schlüssel berührt wird, wird er "gelöscht"
//				gp.ui.addMessage("You got a Key!");
//				break;
//			case "Door":
//				if (hasKey > 0) {
//					gp.obj[i] = null;
//					hasKey--;
//					gp.ui.addMessage("You opend the door!");
//				}
//				else {
//					gp.ui.addMessage("You need a key!");
//					
//				}
//				
//				break;
//			case "Boots":
//				speed += 2; //wenn die Schuhe eingesammelt werden, wird der Spieler Schneller
//				gp.obj[i] = null;
//				gp.ui.addMessage("Speed up!");
//				break;
//			case "Chest":
//				gp.ui.gameFinished = true;
//				break;
//				
//			
//			
//			}
//			

	public void interactNPC(int i) {
		if(gp.keyH.enterPressed == true) {
		if(i != 999) {
				attackCanceled = true;
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
		}
	
			
		}
	}
	public void contactMonster(int i) {
		if(i != 999) {
			
			if (invincible == false && gp.monster[i].dying == false) {
				int damage = gp.monster[i].attack - defense;
				 if (damage < 0) {
					 damage = 0;
				 }
			life -= damage;
			invincible = true;
			}
		}
	}
	public void damageMonster(int i, int attack) {
		if (i != 999) {
			 if(gp.monster[i].invincible == false) {
				 
				 int damage = attack - gp.monster[i].defense;
				 if (damage < 0) {
					 damage = 0;
				 }
				 
				 gp.monster[i].life -= damage;
				 gp.ui.addMessage(damage + "damage!");
				 gp.monster[i].invincible = true;
				 gp.monster[i].damageReaction();
				 
				 if(gp.monster[i].life <= 0) {
					 gp.monster[i].dying = true;
					 gp.ui.addMessage("Killed the "+gp.monster[i].name+"!");
					 exp += gp.monster[i].exp;
					 checkLevelUp();
				 }
			 }
				 
		}
	}
	public void checkLevelUp() {
		if (exp >= nextLevelExp) {
			level++;
			nextLevelExp = nextLevelExp*2;
			maxLife += 2;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "You are level "+level +"now!\nYou feel stronger!";
			
		}
	}
	public void selectItem() {
		int itemIndex = gp.ui.getItemIndexOnSlot();
		
		if(itemIndex < inventory.size()) {
			Entity selectedItem = inventory.get(itemIndex);
			
			if(selectedItem.type == type_bomb || selectedItem.type == type_xbomb) {
				currentWeapon = selectedItem;
				attack = getAttack();
				getPlayerAttackImage();
				
			}
			if(selectedItem.type == type_shield) {
				currentShield = selectedItem;
				defense = getDefense(); 
			}
			if(selectedItem.type == type_consumable) {
				
			}
		}
	}
	public void draw (Graphics2D g2) {

		//g2.setColor(Color.white);
		//g2.fillRect(x, y, gp.tileSize, gp.tileSize);	//Zeichnet ein Rechteck und füllt es mit gegebener farbe
		
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
	
	
		switch (direction) {
			case "up": 
			if(attacking == false) {
				if (spriteNum == 1) {image = up1;}
				if (spriteNum == 2) {image = up2;}}
			if (attacking == true) {
				if (spriteNum == 1) {image = attackUp1;}
				if (spriteNum == 2) {image = attackUp2;}}
				break;
			case "down":
			if(attacking == false) {
				if (spriteNum == 1) {image = down1;}
				if (spriteNum == 2) {image = down2;}}
			if(attacking == true) {
				if (spriteNum == 1) {image = attackDown1;}
				if (spriteNum == 2) {image = attackDown2;}}
				break;
			case "left":
			if(attacking == false) {
				if (spriteNum == 1) {image = left1;}
				if (spriteNum == 2) {image = left2;}}
			if(attacking == true) {
				if (spriteNum == 1) {image = attackLeft1;}
				if (spriteNum == 2) {image = attackLeft2;}}
				break;
			case "right":
			if(attacking == false) {
				if (spriteNum == 1) {image = right1;}
				if (spriteNum == 2) {image = right2;}}
			if(attacking == true) {
				if (spriteNum == 1) {image = attackRight1;}
				if (spriteNum == 2) {image = attackRight2;}}
				break;
		}
	if (screenX > worldX) {
			
			tempScreenX = worldX;
	}
	if (screenY > worldY) {
			tempScreenY = worldY;

	}
		int rightOffset = gp.screenWidth - screenX;
		if (rightOffset > gp.worldWidth - worldX) {
				tempScreenX = gp.screenWidth - (gp.worldWidth - worldX);
		}
		int bottomOffset = gp.screenHeight - screenY;
		if (bottomOffset > gp.worldHeight - worldY) {
		
				tempScreenY = gp.screenHeight - (gp.worldHeight - worldY);
			
		}
			if (invincible == true) {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4F)); //Spieler wird Halbtransparent angezeigt
			}
		
	
			
			if (image == attackLeft1 || image == attackLeft2) {
				g2.drawImage (image, tempScreenX - gp.tileSize, tempScreenY, null); 
				}//Zeichnet den Player	
			else if (image == attackUp1 || image == attackUp2) {
				g2.drawImage (image, tempScreenX, tempScreenY - gp.tileSize, null);
				} //Zeichnet den Player	
			else {
			g2.drawImage (image, tempScreenX, tempScreenY, null);//Zeichnet den Player
			}
		//RESET
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1F));
		
		
//		g2.setFont(new Font("Arial Rounded TM Bold", Font.BOLD, 26));
//		g2.setColor(Color.white);
//		g2.drawString("Invincible: "+invincibleCounter, 10, 400);
	
	
}
}
//test