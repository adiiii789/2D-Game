package main;

import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {

	
	GamePanel gp;
	Graphics2D g2;
	Font arial_R_40;
	Font arial_R_80B = new Font("Arial Rounded MT", Font.BOLD, 80);
	BufferedImage keyImage;
	public boolean messageON = false;
	public String message = "";
	int messageCounter;
	public boolean gameFinished = false;
	
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial_R_40 = new Font("Arial Rounded MT", Font.PLAIN, 40); // configuriert die Font der UI

		OBJ_Key key = new OBJ_Key(gp);
		keyImage = key.image;
	}
	public void showMessage(String text) {
		
		message = text;
		messageON = true;
	}
	public void draw(Graphics2D g2) { 
		this.g2 = g2;
		
		g2.setFont(arial_R_40);
		g2.setColor(Color.white);
		
		if (gp.gameState == gp.playState) {
			
			
		}
		if (gp.gameState == gp.pauseState) {
			drawPauseScreen();
			
		}
		
		if (gameFinished == true) {
			
			g2.setFont(arial_R_40); //Schriftart einstellen
			g2.setColor(Color.white);
			
			String text;
			int textLength;
			int x;
			int y;
			
			text = "Treasure!";
			textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); //gibt die länge des Textes als int aus
			
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 - gp.tileSize*3;
			
			g2.drawString(text, x, y);
			
			text = "Your Time is: " + dFormat.format(playTime)+"!";
			textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); //gibt die länge des Textes als int aus
			
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + gp.tileSize*4;
			
			g2.drawString(text, x, y);
			
			g2.setFont(arial_R_80B); //Schriftart einstellen
			g2.setColor(Color.yellow);
			text = "You win!";
			textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); //gibt die länge des Textes als int aus
			
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 - textLength/2 + gp.tileSize*2;
			
			g2.drawString(text, x, y);
			
			gp.gameThread = null; //stoppt den Thread
			
			
		}
		else {
		g2.setFont(arial_R_40); //Schriftart einstellen
		g2.setColor(Color.white);
		g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
		g2.drawString("x "+gp.player.hasKey, 75, 60); //Position wird anhand einer Baseline bestimmt, welche unterhalb des Textes liegt
		
		playTime +=(double)1/60;
		g2.drawString("Time: "+dFormat.format(playTime), gp.tileSize*11, 65);
		
		if(messageON == true) {
			g2.setFont(g2.getFont().deriveFont(30F));
			g2.drawString(message, gp.tileSize/2, gp.tileSize*5);
			
			messageCounter++;
			if(messageCounter > 120) {
				messageCounter = 0;
				messageON = false;
				
			}
			}
		}
	}
	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
		int x =  gp.screenWidth/2 - length/2;
		return x;
		
	}
}
