package main;

import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entity.Entity;
import entity.Player;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

//import object.OBJ_Key;

public class UI {

	
	GamePanel gp;
	Graphics2D g2;
	KeyHandler keyH;
	Font arial_R_40;
	Font arial_R_80B = new Font("Arial Rounded MT Bold", Font.BOLD, 80);
	BufferedImage heart_full, heart_half, heart_blank, mana_full, mana_dry;
//	BufferedImage keyImage;
	public boolean messageON = false;
//	public String message = "";
//	int messageCounter;
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean gameFinished = false;
	public String currentDialogue ="";
	public int commandNum = 0;
	public int slotCol = 0;
	public int slotRow = 0;
	
//	double playTime;
//	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial_R_40 = new Font("Arial Rounded MT Bold", Font.PLAIN, 40); // configuriert die Font der UI

//		OBJ_Key key = new OBJ_Key(gp);
//		keyImage = key.image;
		
		//HUD
		
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		Entity mana = new  OBJ_ManaCrystal(gp);
		mana_full = mana.image; 
		mana_dry = mana.image2;
	}
	public void addMessage(String text) {
		
//		message = text;
//		messageON = true;
		
		message.add(text);
		messageCounter.add(0);
	}
	/**
	 * Zeichnet den Titlescreen und die HUD des Spielers
	 * Zeichnet die HUD abhängig von Gamestate
	 * @param g2
	 */
	public void draw(Graphics2D g2) { 
		this.g2 = g2;
		
		g2.setFont(arial_R_40);
		g2.setColor(Color.white);
		
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		if (gp.gameState == gp.playState) {
			drawPlayerLife();
			drawMessage();
			
		}
		if (gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
			
		}
		if (gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
			
			
		}
		
		if (gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory();
		}
		
//		if (gameFinished == true) {
//			
//			g2.setFont(arial_R_40); //Schriftart einstellen
//			g2.setColor(Color.white);
//			
//			String text;
//			int textLength;
//			int x;
//			int y;
//			
//			text = "Treasure!";
//			textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); //gibt die länge des Textes als int aus
//			
//			x = gp.screenWidth/2 - textLength/2;
//			y = gp.screenHeight/2 - gp.tileSize*3;
//			
//			g2.drawString(text, x, y);
//			
//			text = "Your Time is: " + dFormat.format(playTime)+"!";
//			textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); //gibt die länge des Textes als int aus
//			
//			x = gp.screenWidth/2 - textLength/2;
//			y = gp.screenHeight/2 + gp.tileSize*4;
//			
//			g2.drawString(text, x, y);
//			
//			g2.setFont(arial_R_80B); //Schriftart einstellen
//			g2.setColor(Color.yellow);
//			text = "You win!";
//			textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); //gibt die länge des Textes als int aus
//			
//			x = gp.screenWidth/2 - textLength/2;
//			y = gp.screenHeight/2 - textLength/2 + gp.tileSize*2;
//			
//			g2.drawString(text, x, y);
//			
//			gp.gameThread = null; //stoppt den Thread
//			
//			
//		}
//		else {
//		g2.setFont(arial_R_40); //Schriftart einstellen
//		g2.setColor(Color.white);
//		g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
//		g2.drawString("x "+gp.player.hasKey, 75, 60); //Position wird anhand einer Baseline bestimmt, welche unterhalb des Textes liegt
//		
//		playTime +=(double)1/60;
//		g2.drawString("Time: "+dFormat.format(playTime), gp.tileSize*11, 65);
//		
//		if(messageON == true) {
//			g2.setFont(g2.getFont().deriveFont(30F));
//			g2.drawString(message, gp.tileSize/2, gp.tileSize*5);
//			
//			messageCounter++;
//			if(messageCounter > 120) {
//				messageCounter = 0;
//				messageON = false;
//				
//			}
//			}
//		}
	}
	/**
	 * HUD hauptsächlich für playstate
	 * Zeichnet Lebensleiste und Mana
	 */
	public void drawPlayerLife() {
		
//		gp.player.life = 5;
		
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		
		int i = 0;
		 if(gp.player.life <= 0) {
			 System.exit(0);
		 }
		while(i < gp.player.maxLife/2) {
			g2.drawImage(heart_blank,x,y,null);
			i++;
			x += gp.tileSize+20;
		}
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		
		 while ( i < gp.player.life) {
			 g2.drawImage(heart_half,x,y,null);
			 i++;
			 if (i < gp.player.life) {
				 g2.drawImage(heart_full,x,y,null);
			 }
			 i++;
			 x+= gp.tileSize+20;
			
		 }
		 x = gp.tileSize/2;
		 y = gp.tileSize*2;
		 i = 0;
		 while (i < gp.player.maxMana) {
			 g2.drawImage(mana_dry,x,y,null);
			 i++;
			 x += gp.tileSize+20;
		 }
		 x = gp.tileSize/2;
		 y = gp.tileSize*2;
		 i = 0;
		 
		 while(i < gp.player.mana) {
			 g2.drawImage(mana_full,x,y,null);
			 i++;
			 x += gp.tileSize+20;
		 }
		 
		
	}
	/**
	 * Nachrichten werden Angezeigt und gestapelt
	 * soll chat simulieren
	 */
	public void drawMessage() {
		int messageX = gp.tileSize;
		int messageY = gp.tileSize*4;
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,32F));
		
		for(int i = 0; i < message.size(); i++) {
			
			if(message.get(i) != null) {
				
				g2.setColor(Color.black);
				g2.drawString(message.get(i),messageX+2, messageY+2);
				g2.setColor(Color.white);
				g2.drawString(message.get(i),messageX, messageY);
				
				
				int counter = messageCounter.get(i) + 1; // messageCounter++
				messageCounter.set(i, counter); //counter == Array
				messageY += 50;
				
				if (messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
		}
	}
	/**
	 * Zeichnet in den Titlescreen den Text und den Playersprite vergrößert Zentriert
	 * 
	 */
	public void drawTitleScreen() {
		
	
		
		
		Font arial_R_70 = new Font("Arial Rounded MT Bold", Font.BOLD, 70);
		Font arial_R_48 = new Font("Arial Rounded MT Bold", Font.BOLD, 48);
		
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/klee/001.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g2.drawImage(image,0,0,gp.screenWidth,gp.screenHeight,null);
		
		g2.setFont(arial_R_70);
		String text = "The Legend of Klee";
		int x = getXforCenteredText(text);
		int y = gp.tileSize*3;
		
		g2.setColor(Color.red);
		g2.drawString (text, x+7, y+7); //SHADE
		
		g2.setColor(Color.white);
		g2.drawString(text,x,y); //MAIN
		
		x = gp.screenWidth/2 - (gp.tileSize*2)/2;
		y += gp.tileSize*2;			 
		
		g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null); //CHARACTER
		
		g2.setFont(arial_R_48);
		
		text = "NEW GAME";
		x = getXforCenteredText(text);
		y += gp.tileSize*4;
		g2.drawString(text,x,y);
		
		if (commandNum == 0) {
			g2.drawString(">",x-gp.tileSize,y);
		}
		
//		text = "LOAD GAME";
//		x = getXforCenteredText(text);
//		y += gp.tileSize;
//		g2.drawString(text,x,y);
//		if (commandNum == 1) {
//			g2.drawString(">",x-gp.tileSize,y);
//		}
		
		text = "QUIT";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text,x,y);
		if (commandNum == 1) {
			g2.drawString(">",x-gp.tileSize,y);
		}
		
		
		
	}
	/**
	 * Zentriert Pause
	 */
	public void drawPauseScreen() {
		
		g2.setFont(this.arial_R_80B);
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
		
		
		g2.setFont(this.arial_R_40);
		g2.setColor(new Color(255,0,0));
		text = "> Quit?";
		x = getXforCenteredText(text);
		y = gp.tileSize*8;
		
		g2.drawString(text, x, y);
		
		
	}
	/**
	 * Arrangiert die Dialogfenster
	 * \n wird erfasst, da der sonst \n gezeichnet werden würde
	 */
	public void drawDialogueScreen() {
		
		int x = gp.tileSize*2;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*4);
		int height = gp.tileSize*4;
		
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30));
		
		x += gp.tileSize;
		y += gp.tileSize;
		
		for(String line : currentDialogue.split("\n")) { //Split Text
			g2.drawString(line,x,y);
			y += 40;
		}
		
	}
	/**
	 * Im Characterstate werden alle Statuswerte festgehalten, welche sich ändern müssen
	 * Zeichnet momentane Objekte für Angriff und Verteidung
	 */
	public void drawCharacterScreen() {
		
		final int frameX = gp.tileSize;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize*5;
		final int frameHeight = gp.tileSize*10;
		drawSubWindow(frameX, frameY, frameWidth,frameHeight);
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(28F));
		
		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 35;
		
		g2.drawString("Level",textX,textY);
		textY += lineHeight;
		g2.drawString("Life",textX,textY);
		textY += lineHeight;
		g2.drawString("Mana",textX,textY);
		textY += lineHeight;
		g2.drawString("Strength",textX,textY);
		textY += lineHeight;
		g2.drawString("Dexterity",textX,textY);
		textY += lineHeight;
		g2.drawString("Attack",textX,textY);
		textY += lineHeight;
		g2.drawString("Defense",textX,textY);
		textY += lineHeight;
		g2.drawString("EXP",textX,textY);
		textY += lineHeight;
		g2.drawString("Next Level",textX,textY);
		textY += lineHeight;
		g2.drawString("Coin",textX,textY);
		textY += lineHeight +10;
		g2.drawString("Weapon",textX,textY);
		textY += lineHeight +15;
		g2.drawString("Shield",textX,textY);
		textY += lineHeight;
		
		int tailX = (frameX + frameWidth)-30;
		textY = frameY + gp.tileSize;
		String value;
		
		value = String.valueOf(gp.player.level);
		textX = getXforAlighToRightText(value, tailX);
		g2.drawString(value,textX,textY);
		textY += lineHeight;
		value = String.valueOf(gp.player.life +"/"+gp.player.maxLife);
		textX = getXforAlighToRightText(value, tailX);
		g2.drawString(value,textX,textY);
		textY += lineHeight;
		value = String.valueOf(gp.player.mana +"/"+gp.player.maxMana);
		textX = getXforAlighToRightText(value, tailX);
		g2.drawString(value,textX,textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.strength);
		textX = getXforAlighToRightText(value, tailX);
		g2.drawString(value,textX,textY);
		textY += lineHeight;
		value = String.valueOf(gp.player.dexterity);
		textX = getXforAlighToRightText(value, tailX);
		g2.drawString(value,textX,textY);
		textY += lineHeight;
		value = String.valueOf(gp.player.attack);
		textX = getXforAlighToRightText(value, tailX);
		g2.drawString(value,textX,textY);
		textY += lineHeight;
		value = String.valueOf(gp.player.defense);
		textX = getXforAlighToRightText(value, tailX);
		g2.drawString(value,textX,textY);
		textY += lineHeight;
		value = String.valueOf(gp.player.exp);
		textX = getXforAlighToRightText(value, tailX);
		g2.drawString(value,textX,textY);
		textY += lineHeight;
		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXforAlighToRightText(value, tailX);
		g2.drawString(value,textX,textY);
		textY += lineHeight;
		value = String.valueOf(gp.player.coin);
		textX = getXforAlighToRightText(value, tailX);
		g2.drawString(value,textX,textY);
		
		
		g2.drawImage(gp.player.currentWeapon.down1,tailX-gp.tileSize+15, textY+7, null);
		textY += gp.tileSize;
		g2.drawImage(gp.player.currentShield.down1,tailX-gp.tileSize+15,textY+7,null);
		
	}
	/** 
	 * Zeichnet weiteres Rechteck, welches durch Keylistener bewegt wird, dient als Cursor
	 * Alle Objekte, welche in der Arraylist Entity festgehalten werden sollen Abgebildet werden
	 * Schild und Schwert sollen auswählbar sein
	 * 
	 */
	public void drawInventory() {
		int frameX =gp.tileSize*9;
		int frameY =gp.tileSize;
		int frameWidth = gp.tileSize*6;
		int frameHeight = gp.tileSize*5;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY +20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize = gp.tileSize;
		
		for(int i = 0; i < gp.player.inventory.size(); i++) {
			
			if(gp.player.inventory.get(i) == gp.player.currentWeapon ||
					gp.player.inventory.get(i) == gp.player.currentShield) {
				g2.setColor(new Color(240,190,90));
				g2.fillRoundRect(slotX, slotY,gp.tileSize,gp.tileSize,10,10);
			}
			
			g2.drawImage(gp.player.inventory.get(i).down1,slotX,slotY, null);
			
			
			slotX += slotSize;
			
			if(i == 4 || i == 9 || i ==14 ) {
				slotX = slotXstart;
				slotY += slotSize;
			}
		}
		
		//CURSOR
		int cursorX = slotXstart + (gp.tileSize * slotCol);
		int cursorY = slotYstart + (gp.tileSize * slotRow);
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;
		
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10,10);
		
		int dFrameX = frameX;
		int dFrameY = frameY + frameHeight;
		int dFrameWidth = frameWidth;
		int dFrameHeight = gp.tileSize*5;
		
		
		int textX = dFrameX +20;
		int textY = dFrameY +gp.tileSize;
		g2.setFont(g2.getFont().deriveFont(28F));
		
		int itemIndex = getItemIndexOnSlot();
		
		if(itemIndex < gp.player.inventory.size()) {
			drawSubWindow(dFrameX,dFrameY,dFrameWidth,dFrameHeight);
			
			for(String line: gp.player.inventory.get(itemIndex).description.split("\n")) {
				g2.drawString(line, textX, textY);
				textY+= 32;
			}
			
			
		}
		
	}
	public int getItemIndexOnSlot() {
		int itemIndex = slotCol + (slotRow*5);
		return itemIndex;
	} 
	/**
	 * Zeichnet ein ausgefülltes, halb transparentes Rechteck mit einem weißen Rand
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0,0,0,180); // (R,G,B,Deckkraft)
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5,y+5,width-10, height-10, 25,25);
	}
	/**
	 * screenwidth/2 ist die mitte des screens
	 * wovon die hälfte der länge des Textes abgezogen wird
	 * @param text
	 * @return
	 */
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
		int x =  gp.screenWidth/2 - length/2;
		return x;
		
	}
	
	public int getXforAlighToRightText(String text, int tailX) {
		int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
		int x =  tailX - length;
		return x;
		
	}
}
