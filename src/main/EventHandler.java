package main;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics2D;

public class EventHandler {

	GamePanel gp;
	Graphics2D g2;
	EventRect eventRect[][];
	
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		
		eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
		
		int col = 0;
		int row = 0;
		while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
			
		eventRect[col] [row] = new EventRect();
		eventRect[col] [row].x = 23;
		eventRect[col] [row].y = 23;
		eventRect[col] [row].width = 2;
		eventRect[col] [row].height = 2;
		eventRect[col] [row].eventRectDefaultX = eventRect[col] [row].x;
		eventRect[col] [row].eventRectDefaultY = eventRect[col] [row].y;
		
		col++;
		if(col == gp.maxWorldCol) {
			col = 0;
			row++;
		}
		}	
	}
	public void checkEvent() {
		
		int xDistance = Math.abs(gp.player.worldX - previousEventX); //gibt absolute werte wieder, also werden negative werte trotzdem Positiv angezeigt, Distanz berechnen
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		if (distance >= gp.tileSize) { //Spieler ist 1 tile oder mehr entfernt
			canTouchEvent = true;
		}
		
		if(canTouchEvent == true) {
		if(hit(23,5,"any")== true) {
			damagePit(23,5,gp.dialogueState);
		}
		if(hit(21,5,"any")== true) {
			healingPool(21,5,gp.dialogueState);
		}
		}
		if(hit(4,1,"any")==true) {
			teleport(4,1,gp.dialogueState);
		}
	}
	public boolean hit(int col, int row, String reqDirection) {
		boolean hit = false;
				
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		eventRect[col] [row].x = col*gp.tileSize + eventRect[col] [row].x;
		eventRect[col] [row].y = row*gp.tileSize + eventRect[col] [row].y;
		
		if (gp.player.solidArea.intersects(eventRect[col] [row]) && eventRect[col][row].eventDone == false) { //intersect 
			if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;
				
				previousEventX = gp.player.worldX;
				previousEventY = gp.player.worldY;
			}
		}
			gp.player.solidArea.x = gp.player.solidAreaDefaultX;
			gp.player.solidArea.y = gp.player.solidAreaDefaultY;
			eventRect[col] [row].x = eventRect[col] [row].eventRectDefaultX;
			eventRect[col] [row].y = eventRect[col] [row].eventRectDefaultY; //folgt ähnlichen Prinzip wie Object collision
		
		
				return hit;
	}
	public void damagePit(int col, int row, int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDialogue = "You fall into a pit!";
		gp.player.life -= 1;
		//eventRect[col][row].eventDone = true;
		canTouchEvent = false;
	}
	public void healingPool (int col, int row, int gameState) {
		if (gp.keyH.enterPressed == true) {
			
			gp.gameState = gameState;
			gp.player.attackCanceled = true;
			gp.ui.currentDialogue = "You drink the Floor. \n your life has been recovered";
			gp.player.life = gp.player.maxLife;
			
		}
		
	}
	public void teleport (int col, int row, int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDialogue = "Where am I?";
		gp.player.worldX = gp.tileSize*10;
		gp.player.worldY = gp.tileSize*25;

		
		
		
	}

	

}

