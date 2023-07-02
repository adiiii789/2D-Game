package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	//this hei�t btw diese klasse, also GamePanel
	
	// Bildschirmeinstellungen
	final int originalTileSize = 16; //16x16 tile, also eine Sprite nutzt 16x16 pixel
	final int scale = 3; 			 //hochskalierung der Sprite
	
	public final int tileSize = originalTileSize * scale; 
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; //anzahl an Tiles in der Horizontalen | 768 pixels
	public final int screenHeight = tileSize * maxScreenRow;//anzahl an Tiles in der Vertikalen   | 576 pixels
	public final int defaultPlayerSpeed = 4;
	
	//Welt einstellungen
	public final int maxWorldCol = 27; //Programm stirbt, wenn werte h�her wie die Karte sind
	public final int maxWorldRow = 15;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	//FPS
	public int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	
	KeyHandler keyH = new KeyHandler(this);				//KeyHandler wird implementiert
	Thread gameThread; 								//eine Thread wird einmal gestartet und l�uft danach kontinuierlich weiter
	public CollisionDetection cDetection = new CollisionDetection(this);
	public UI ui = new UI(this);
	public AssetSetter aSetter  = new AssetSetter(this);
	public Player player = new Player(this,keyH);	//Player Class wird implementiert
	public SuperObject obj[] = new SuperObject[10]; //10 slots f�r Objekte
	
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
	
	
	
	
	
	public GamePanel () {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); 						//dies ist ein extra painting buffer, welcher im hintergrund arbeitet TODO Google
		this.addKeyListener(keyH);							//Programm kann mit den implementierten inputs im Panel interagieren
		this.setFocusable(true);							//Programm "fokussiert" sich auf key inputs
	
		
	}
	public void setupGame() {
		
		aSetter.setObject();
		gameState = playState;
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this); 
		gameThread.start(); 
	}	//			|___________________
		//								|								
	@Override	//						V
	public void run() { //sobald die  gameThread.start() aufgerufen wird, wird diese Methode getriggered
		
		//hier wird die Zeit definiert, also, wie der Char sich im Bild bewegt. das hei�t die Logic muss sich aktualisieren und die Bilder m�ssen gezeichnet werden
		
		double drawInterval = 1000000000/FPS*(FPS/60);
		double delta = 0;						//die Gameloop wird hier nach der DeltaTime Programmiert
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) { //solande die gameThread existiert, wird das wiederholt
			
			//System.out.println("The game loop is running"); //Test, ob die gameThread aktiv ist
			
			//long currentTime = System.nanoTime(); //gibt den JVM nanosekundenwert der Zeit wieder | 1 Milliarde nanosekunden = 1 sekunde
			
				
				
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
		if (gameState == playState) {
			player.update(); // Player wird nur geupdated, wenn gameState auf playstate ist
		}
		if (gameState == pauseState) {
			
		}
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g; //erm�glicht besseren zugriff auf verschiedene wichtige tools f�r 2D graphiken.
		
		//DEBUG
		long drawStart = 0;
		if(keyH.checkDrawTime == true) {
			drawStart = System.nanoTime();
		}
		
		
		
		tileM.draw(g2); //wichtig das zuerst die Tiles, dann der Player Gezeichnet wird. (Render-Priorit�t)
		
		for (int i = 0; i < obj.length; i++) { //Jedes einzelne Objekt im Array wird aufgerufen
			if (obj[i] != null) { //Vorbeugung der 0pointer exception
				obj[i].draw(g2, this);
			}
		}
			
		
		player.draw(g2);
		
		ui.draw(g2);
		
		
		//DEBUG
		if(keyH.checkDrawTime == true) {
		long drawEnd = System.nanoTime();
		long passed = drawEnd - drawStart;
		g2.setColor(Color.WHITE);
		g2.drawString("Draw Time: " + passed, 10, 400);
		System.out.println("Draw Time: "+passed);
		}
		
		g2.dispose(); //save Memory, also erl�st den RAM
		
		
	}
}
//test