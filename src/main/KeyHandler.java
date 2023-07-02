package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.event.MenuKeyEvent;

import java.awt.Graphics2D;

public class KeyHandler implements KeyListener {

	GamePanel gp;
	Graphics2D g2;
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed = false;
	
	boolean checkDrawTime = false;
	

	public KeyHandler(GamePanel gamePanel) {
		this.gp = gamePanel;
	}

	
	@Override
	public void keyTyped(KeyEvent e) {
		//wird erstmal nicht benötigt
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode(); //gibt einen integer wert wieder, welcher der Code des Keys ist
		
		int x = gp.screenWidth/2 - (gp.tileSize*2)/2;
		int y = gp.tileSize*2;
		
		if(gp.gameState == gp.titleState) {
			if (code == KeyEvent.VK_W) { //VK ist der Konstante integer wert von K, welcher durch getKeyCode ermittelt wird.
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
			}
			if (code == KeyEvent.VK_S) { //
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
			}
			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 0) {
					gp.gameState = gp.playState;
				}
				if(gp.ui.commandNum == 1) {
					//Coming soon
				}
				if(gp.ui.commandNum == 2) {
					System.exit(0);
				}
					
			}
			}
		
		
		if(gp.gameState == gp.playState) {
		if (code == KeyEvent.VK_W) { //VK ist der Konstante integer wert von K, welcher durch getKeyCode ermittelt wird.
			upPressed = true;
		}
		if (code == KeyEvent.VK_S) { //
			downPressed = true;
			
		}
		if (code == KeyEvent.VK_A) { //
			leftPressed = true;
		}
		if (code == KeyEvent.VK_D) { //
			rightPressed = true;
		}
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.pauseState;

		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
			
		}
		//DEBUG
		if (code == KeyEvent.VK_F3) { //
			if(checkDrawTime == false) {
				checkDrawTime = true;
			
				
			}
			else if (checkDrawTime == true) {
				checkDrawTime = false;
			}
		}
		}
		else if(gp.gameState == gp.pauseState) {
			if(code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.playState;
			}
			if (code == KeyEvent.VK_ENTER) {
				System.exit(0);
			}
			
		}
			

		
		if(gp.gameState == gp.dialogueState) {
			
			if(code == KeyEvent.VK_ENTER) {
				gp.gameState = gp.playState;
			}
			
		
				}
		 
		}
		
	
		


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
		int code =  e.getKeyCode();
		
		if (code == KeyEvent.VK_W) { //VK ist der Konstante integer wert von K, welcher durch getKeyCode ermittelt wird.
			upPressed = false;
		}
		if (code == KeyEvent.VK_S) { //
			downPressed = false;
			
		}
		if (code == KeyEvent.VK_A) { //
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D) { //
			rightPressed = false;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = false;
		}
	}

}
