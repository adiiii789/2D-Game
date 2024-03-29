package main;

import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JFrame;

public class Main {
/**
 * Generiert ein Fenster
 * Im Fenster wird die Klasse GamePanel hinzugef�gt
 * Toolkit mit URL wird benutzt um ein bild als Icon zu konfigurieren
 * 
 * 
 * @param a
 */
	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//Wenn x gedr�ckt wird, schlie�t das Programm
		window.setResizable(false);								//Man kann nicht mit dem Mauszeiger das Fenster Transformieren
		window.setTitle("The Legend of Klee"); 				//Name der erstellten Java-Application
		URL url = Main.class.getResource("/klee/front1.png");
	    window.setIconImage(Toolkit.getDefaultToolkit().getImage(url));
		
		GamePanel gamePanel = new GamePanel();					//wir verbinden Gamepanel unidirektional
		window.add(gamePanel);									//wir f�gen  das GamePanel dem Fenster hinzu
		
		window.pack();											//passt das Fenster an die gr��e des GamePanels an
		
		window.setLocationRelativeTo(null); 					//das Fenster wird nach der Mitte des Bildschirmes gerichtet
		window.setVisible(true);								//Fenster wird ge�ffnet :o
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
		
	}
}


//test