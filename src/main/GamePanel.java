package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;

/**
 * Arraylist speichert Objekte f�r sp�teren abruf
 * Entity Object, NPC und Monster werden als EntityArray erstellt
 * 
 * @author adibl
 *
 */
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
	public final int maxWorldRow = 36;
	public final int worldWidth = tileSize * maxWorldCol; 
	public final int worldHeight = tileSize * maxWorldRow;
	
	//FPS
	public int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	
	public KeyHandler keyH = new KeyHandler(this);				//KeyHandler wird implementiert
	public EventHandler eHandler = new EventHandler(this);
	Thread gameThread; 								//eine Thread wird einmal gestartet und l�uft danach kontinuierlich weiter
	public CollisionDetection cDetection = new CollisionDetection(this);
	public UI ui = new UI(this);
	public AssetSetter aSetter  = new AssetSetter(this);
	public Player player = new Player(this,keyH);	//Player Class wird implementiert
	public Entity obj[] = new Entity[10]; //10 slots f�r Objekte
	public Entity[] npc = new Entity[10];
	public Entity monster[] = new Entity[20];
	ArrayList<Entity> entityList = new ArrayList<>();
	public ArrayList<Entity> projectileList = new ArrayList<>();
	
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int characterState = 4;
	public final int bullethellState = 5;
	
	
	
	
	/**
	 * Erg�nzung der Main methode
	 * setDoubleBuffered zeichnet das Bild im Hintergrund und zeigt dieses bei vervollst�ndigung an.
	 * wenn nichts gezeichnet wird, soll der Hintergrund Schwarz sein
	 * addKeylistener soll das Abfragen von Inputs �ber das Fenster erm�glichen
	 * setFocusable bedeutet das er speziell key inputs abfr�gt
	 * 
	 */
	public GamePanel () {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); 						//dies ist ein extra painting buffer, welcher im hintergrund arbeitet 
		this.addKeyListener(keyH);						//Programm kann mit den implementierten inputs im Panel interagieren
		this.setFocusable(true);							//Programm "fokussiert" sich auf key inputs
	
		
	/**
	 * Bei Start sollen bereits im Hintergrund die Enitiys platziert werden
	 * gameState wird auf titleState gesetzt, wodurch bei Start ein Titlescreen angezeigt wird
	 */
	}
	public void setupGame() {
		
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		gameState = titleState;
	}
	
	/**
	 * Thread greift bei Aktivierung auf Run methode �ber
	 * gameThread erstellt ein branch, welcher in der Run methode Parallel abgearbeitet wird
	 * 
	 */
	public void startGameThread() {
		
		gameThread = new Thread(this); 
		gameThread.start(); 
	}	//			|___________________
		//								|								
	@Override	//						V
	/**
	 * run berechnet eine simple form von Deltatime, welche die Geschwindigkeit, in welcher Bilder generiert werden, reguliert
	 */
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
//				System.out.println("FPS:"+drawCount);
				drawCount = 0;
				timer = 0;
			}
			
			
			
		}
		
		
	}
	/**
	 * Stetige Abfrage �ber Zustand der NPCs, wenn sie von Player klasse als tot deklariert wird, werden diese ausgeblendet
	 * update sorgt f�r die Logic des spiels
	 * wenn GameState !playstate oder !bullethellState ist, kann die Logic nicht aktualisiert werden
	 * 
	 */
	public void update() {  
		if (gameState == playState || gameState == bullethellState) {
			player.update(); // Player wird nur geupdated, wenn gameState auf playstate ist
			
			for (int i = 0; i < npc.length; i++) {
				if(npc[i] != null) {
					npc[i].update();
				}
			}
			for(int i = 0; i < monster.length; i++) {
				if (monster[i] != null) {
					if(monster[i].alive == true && monster[i].dying == false) {
					monster[i].update();}
					if(monster[i].alive == false) {
						monster[i] = null;}
				}
			}
			
			for(int i = 0; i < projectileList.size(); i++) {
				if (projectileList.get(i) != null) {
					
					if(projectileList.get(i).alive == true) {
						projectileList.get(i).update();}
					if(projectileList.get(i).alive == false) {
						projectileList.remove(i);
						}
				}
			}
				
		}
		if (gameState == pauseState) {
			//Maybe one Day...
		}
		
	}
	/**
	 * Zeichnet bei Start den Titlescreen
	 * Regelt Render Priorit�t, da sonst der Spieler �ber alles gerendert werden w�rde
	 * Debug, welches Kordinaten anzeigt und die DrawIntervalle in nanosekunden
	 * Graphics2D bibliothek Zeichnet alle Grafiken in diesem Programm
	 * 
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g; //erm�glicht besseren zugriff auf verschiedene wichtige tools f�r 2D graphiken.
		
		//DEBUG
		long drawStart = 0;
		if(keyH.checkDrawTime == true) {
			drawStart = System.nanoTime();
		}
		
		if (gameState == titleState) {
			ui.draw(g2);
			
		}
		else {
		
		tileM.draw(g2); //wichtig das zuerst die Tiles, dann der Player Gezeichnet wird. (Render-Priorit�t)
		
		entityList.add(player);
		
		for(int i = 0; i < npc.length; i++) {
			if(npc[i] != null) {
				entityList.add(npc[i]);
			}
		}
		
		
		for (int i = 0; i < obj.length; i++) {
			if(obj[i] != null) {
				entityList.add(obj[i]);
				
			}
		}
		for (int i = 0; i < monster.length; i++) {
			if(monster[i] != null) {
				entityList.add(monster[i]);
			}
		}
		for (int i = 0; i < projectileList.size(); i++) {
			if(projectileList.get(i) != null) {
				entityList.add(projectileList.get(i));
			}
		}
		Collections.sort(entityList, new Comparator<Entity>() {

			@Override
			public int compare(Entity e1, Entity e2) {
				int result = Integer.compare(e1.worldY, e2.worldY);
				return result;
			}
			
		});
		
		for(int i = 0; i < entityList.size(); i++) {
			entityList.get(i).draw(g2);
			
		}
		
		entityList.clear();
		
		ui.draw(g2);
		}
		
		//DEBUG
		if(keyH.checkDrawTime == true) {
		long drawEnd = System.nanoTime();
		long passed = drawEnd - drawStart;
		g2.setColor(Color.WHITE);
		g2.drawString("Draw Time: " + passed, 10, 550);
		System.out.println("Draw Time: "+passed);
		
		g2.drawString("X: "+ player.worldX +" Y: " +player.worldY,500,550);
		}
		
		g2.dispose(); //save Memory, also erl�st den RAM
		
		
	}
}
//test