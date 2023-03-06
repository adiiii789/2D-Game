package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	public boolean upPressed, downPressed, leftPressed, rightPressed;
	
	@Override
	public void keyTyped(KeyEvent e) {
		//wird erstmal nicht benötigt
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode(); //gibt einen integer wert wieder, welcher der Code des Keys ist
		
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
	}

}
