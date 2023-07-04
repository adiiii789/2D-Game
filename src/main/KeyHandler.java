package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.event.MenuKeyEvent;

import java.awt.Graphics2D;

public class KeyHandler implements KeyListener {

	GamePanel gp;
	Graphics2D g2;
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed, shiftPressed;
	public boolean bullethell = false;
	
	boolean checkDrawTime = false;
	

	public KeyHandler(GamePanel gamePanel) {
		this.gp = gamePanel;
		
	}

	
	@Override
	public void keyTyped(KeyEvent e) {
		//wird erstmal nicht benötigt
		
	}

	@Override
	/**
	 * über den KeyCode werden alle Keystrokes Registriert
	 * abhängig von Gamestate werden Andere Inputs verlangt
	 */
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode(); //gibt einen integer wert wieder, welcher der Code des Keys ist
		
		int x = gp.screenWidth/2 - (gp.tileSize*2)/2;
		int y = gp.tileSize*2;
		
		if(gp.gameState == gp.titleState) {
			titleState(code);
		
		}
		else if(gp.gameState == gp.playState) {
			playState(code);
		
		
		}
		else if(gp.gameState == gp.pauseState) {
			pauseState(code);
			

		}
		else if(gp.gameState == gp.dialogueState) {
			dialogueState(code);
		
			
		
		}
		else if (gp.gameState == gp.characterState) {
			characterState(code);
		}
		else if (gp.gameState == gp.bullethellState) {
			bullethellState(code);
		}
			
		
		
		 
		
	}
		

		/**
		 * Steuert durch Keystrokes Spiel startem oder Verlassen
		 * @param code
		 */
		public void titleState(int code) {
			if (code == KeyEvent.VK_W) { //VK ist der Konstante integer wert von K, welcher durch getKeyCode ermittelt wird.
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0) {
					gp.ui.commandNum = 1;
				}
			}
			if (code == KeyEvent.VK_S) { //
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 1) {
					gp.ui.commandNum = 0;
				}
			}
			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 0) {
					gp.gameState = gp.playState;
				}
				//if(gp.ui.commandNum == 1) {
					//Coming soon
				//}
				if(gp.ui.commandNum == 1) {
					System.exit(0);
				}
					
			}
			}
		
			
		/**
		 * GameStates werden durch einzelne TastenSchläge hervorgerufen
		 * 
		 * @param code
		 */
		public void playState(int code) {
			bullethell = false;
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
			if (code == KeyEvent.VK_B) {
				gp.gameState = gp.bullethellState;
			}
			if (code == KeyEvent.VK_C) {
				gp.gameState = gp.characterState;
			}
			if (code == KeyEvent.VK_ENTER) {
				enterPressed = true;
			
			}
			if(code == KeyEvent.VK_F) {
				shotKeyPressed = true;
				
			}
			if (code == KeyEvent.VK_F3) { //
				if(checkDrawTime == false) {
					checkDrawTime = true;
				}
				else if (checkDrawTime == true) {
					checkDrawTime = false;
				}
			}
			
	}
		
		public void pauseState(int code) {
			
		
			if(code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.playState;
			}
			if (code == KeyEvent.VK_ENTER) {
				System.exit(0);
			}
			
		
		}
		/**
		 * Dialoge werden durch Enter normal geschlossen, allerdings wird hier nochmal über eine weitere Variable 
		 * der gamestate vor öffnen des dialogueState festgehalten
		 * @param code
		 */
		public void dialogueState(int code) {
			if(code == KeyEvent.VK_ENTER) {
				if(this.bullethell == false) {
					gp.gameState = gp.playState;
				}
				if (this.bullethell == true) {
					gp.gameState = gp.bullethellState;
				}
			}
		}
		/**
		 * im characterState wird auch das Inventar aufgerufen, welches mit den Pfeiltasten gesteuert wird
		 * @param code
		 */
		public void characterState(int code) {
			if (code == KeyEvent.VK_C || code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.playState;
			}
			if (code == KeyEvent.VK_UP) {
				if(gp.ui.slotRow != 0) { 
					gp.ui.slotRow--;
				}
			}
			if (code == KeyEvent.VK_DOWN) {
				if(gp.ui.slotRow != 3) {
				gp.ui.slotRow++;
				}
			}
			if (code == KeyEvent.VK_LEFT) {
				if(gp.ui.slotCol != 0) {
				gp.ui.slotCol--;
				}
			}
			if (code == KeyEvent.VK_RIGHT) {
				if(gp.ui.slotCol != 4) {
				gp.ui.slotCol++;
				}
			}
			if(code == KeyEvent.VK_ENTER) {
				gp.player.selectItem();
			}
		}
		/**
		 * Alternative zu playstate, in welchen die Controls wesentlich verändert werden
		 * @param code
		 */
		private void bullethellState(int code) {
			 bullethell = true;
			if (code == KeyEvent.VK_UP) { 
				upPressed = true;
			}
			if (code == KeyEvent.VK_DOWN) { //
				downPressed = true;
				
			}
			if (code == KeyEvent.VK_LEFT) { //
				leftPressed = true;
			}
			if (code == KeyEvent.VK_RIGHT) { //
				rightPressed = true;
			}
			if(code == KeyEvent.VK_Y) {
				shotKeyPressed = true;
				
			}
			if(code == KeyEvent.VK_SHIFT) {
				gp.player.speed = 1;
				
			}
			if(code == KeyEvent.VK_B) {
				gp.gameState = gp.playState;
			}
			
				
			
			
		}


	@Override
	/**
	 * gerade bei bewegung wichtig Release festzuhalten
	 * 
	 */
	public void keyReleased(KeyEvent e) {
		
		
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
		if(code == KeyEvent.VK_F) {
			shotKeyPressed = false;
		}
		if(code == KeyEvent.VK_SHIFT) {
			gp.player.speed = 4;
		}
		if (code == KeyEvent.VK_UP) { //VK ist der Konstante integer wert von K, welcher durch getKeyCode ermittelt wird.
			upPressed = false;
		}
		if (code == KeyEvent.VK_DOWN) { //
			downPressed = false;
			
		}
		if (code == KeyEvent.VK_LEFT) { //
			leftPressed = false;
		}
		if (code == KeyEvent.VK_RIGHT) { //
			rightPressed = false;
		}
	}
	

}
