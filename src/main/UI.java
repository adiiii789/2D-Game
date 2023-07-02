package main;

import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import entity.Player;
import object.OBJ_Heart;
import object.SuperObject;

//import object.OBJ_Key;

public class UI {

	
	GamePanel gp;
	Graphics2D g2;
	Font arial_R_40;
	Font arial_R_80B = new Font("Arial Rounded MT Bold", Font.BOLD, 80);
	BufferedImage heart_full, heart_half, heart_blank;
//	BufferedImage keyImage;
	public boolean messageON = false;
	public String message = "";
	int messageCounter;
	public boolean gameFinished = false;
	public String currentDialogue ="";
	public int commandNum = 0;
	
//	double playTime;
//	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial_R_40 = new Font("Arial Rounded MT Bold", Font.PLAIN, 40); // configuriert die Font der UI

//		OBJ_Key key = new OBJ_Key(gp);
//		keyImage = key.image;
		
		//HUD
		
		SuperObject heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
	}
	public void showMessage(String text) {
		
		message = text;
		messageON = true;
	}
	public void draw(Graphics2D g2) { 
		this.g2 = g2;
		
		g2.setFont(arial_R_40);
		g2.setColor(Color.white);
		
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		if (gp.gameState == gp.playState) {
			drawPlayerLife();
			
		}
		if (gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
			
		}
		if (gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
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
	public void drawPlayerLife() {
		
		gp.player.life = 5;
		
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		
		int i = 0;
		
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
		
	}
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
		
		text = "LOAD GAME";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text,x,y);
		if (commandNum == 1) {
			g2.drawString(">",x-gp.tileSize,y);
		}
		
		text = "QUIT";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text,x,y);
		if (commandNum == 2) {
			g2.drawString(">",x-gp.tileSize,y);
		}
		
		
		
	}
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
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0,0,0,180); // (R,G,B,Deckkraft)
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5,y+5,width-10, height-10, 25,25);
	}
	
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
		int x =  gp.screenWidth/2 - length/2;
		return x;
		
	}
}
