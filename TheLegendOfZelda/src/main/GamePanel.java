package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	//this heißt btw diese klasse, also GamePanel
	
	// Bildschirmeinstellungen
	final int originalTileSize = 16; //16x16 tile, also eine Sprite nutzt 16x16 pixel
	final int scale = 3; 			 //hochskalierung der Sprite
	
	public final int tileSize = originalTileSize * scale; 
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; //anzahl an Tiles in der Horizontalen | 768 pixels
	public final int screenHeight = tileSize * maxScreenRow;//anzahl an Tiles in der Vertikalen   | 576 pixels
	public final int defaultPlayerSpeed = 4;
	
	//FPS
	public int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	
	KeyHandler keyH = new KeyHandler();				//KeyHandler wird implementiert
	Thread gameThread; 								//eine Thread wird einmal gestartet und läuft danach kontinuierlich weiter
	
	Player player = new Player(this,keyH);
	//Set Players default position
	
	int playerX = 100;
	int playerY = 100;
	int playerSpeed;
	
	public GamePanel () {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); 						//dies ist ein extra painting buffer, welcher im hintergrund arbeitet TODO Google
		this.addKeyListener(keyH);							//Programm kann mit den implementierten inputs im Panel interagieren
		this.setFocusable(true);							//Programm "fokussiert" sich auf key inputs
		
 		
		
	}
	public void startGameThread() {
		
		gameThread = new Thread(this); 
		gameThread.start(); 
	}	//			|___________________
		//								|								
	@Override	//						V
	public void run() { //sobald die  gameThread.start() aufgerufen wird, wird diese Methode getriggered
		
		//hier wird die Zeit definiert, also, wie der Char sich im Bild bewegt. das heißt die Logic muss sich aktualisieren und die Bilder müssen gezeichnet werden
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;						//die Gameloop wird hier nach der DeltaTime Programmiert
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) { //solande die gameThread existiert, wird das wiederholt
			
			//System.out.println("The game loop is running"); //Test, ob die gameThread aktiv ist
			
			//long currentTime = System.nanoTime(); //gibt den JVM nanosekundenwert der Zeit wieder | 1 Milliarde nanosekunden = 1 sekunde
			
			playerSpeed = defaultPlayerSpeed/(FPS/60); //Ermöglicht variable FPS | playerSpeed wird abhängig von FPS runtergerechnet. TODO DIY FPS
				
				
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval; // delta + (zeitvergangen)/Frames
			
			timer += (currentTime - lastTime);
			
			lastTime = currentTime;
			
			if (delta >= 1) {
				update (); //Logic aktualisiseren
				
				repaint (); //bild erstellen auf basis der Logic
				delta--; //reset delta
				drawCount++;
			}
			
			if (timer >= 1000000000) {
				System.out.println("FPS:"+drawCount);
				drawCount = 0;
				timer = 0;
			}
			
			
			
		}
		
		
	}
	public void update() {  
		player.update();
	}
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g; //ermöglicht besseren zugriff auf verschiedene wichtige tools für 2D graphiken.
		
		tileM.draw(g2); //wichtig das zuerst die Tiles, dann der Player Gezeichnet wird. (Render-Priorität)
		
		player.draw(g2);
		
		g2.dispose(); //save Memory, also erlöst den RAM
		
		
	}
}
