package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][]; //2D Array für das Karten .txt
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tile = new Tile[10];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; //Array wird erstellt
		
		getTileImage();
		loadMap("/maps/map01.txt");
		
	}
	
	public void getTileImage() {

			setup(0, "ground1", false); //tring wird in setup eingesetzt
			setup(1, "wall_main_green", true);
			setup(2, "wall_top_green", true);
			setup(3, "wall_top_left_green", true);
			setup(4, "wall_bot_left_green", true);
			setup(5, "wall_top_right_green", true);
			setup(6, "wall_bot_right_green", true);
			setup(7,"black", false);
			

	}
	public void setup(int index, String imageName, boolean collision) {
		
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName +".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath); //Map ins Programm importieren
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine(); //Kann eine textzeile Lesen, und packt diese in die Variable
				
				while (col < gp.maxWorldCol) {
					
					String numbers[] = line.split(" "); //Deswegen ist die Map mit leerzeichen geschrieben. dadurch wird jede Zahl einzeln abgespeichert im Array
					
					int num = Integer.parseInt(numbers[col]); //Zwar haben wir die Zahlen im Array, allerdings sind diese als String gespeichert, hier wird dies konvertiert in Int
					
					mapTileNum[col][row] = num;
					col++;
					
				}
				if (col == gp.maxWorldCol) { //Sobald bis an den Rechten Rand alle tiles gezeichnet sind
					col = 0;
					row++;
					
				}
			}
			br.close(); //den Buffer schließen, Memory save o.ä
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) { //Ich bin mir sicher das dies mit einer doppelten for schleife sehr ähnlich funktioniert,
			//wäre daher for (int j = 0; j >= maxScreenCol; j++) und for (int i = 0; i >= maxScrreenRow; i++) o.ä
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize; //position auf der karte
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX; //Wo wird das Tile auf dem Fenster gezeichnet
			int screenY = worldY - gp.player.worldY + gp.player.screenY; //screenX = 0 heißt also vom Fenster die obere linke ecke
																		 //der part neben worldxy ist ein Offset, damit der Player nicht in der Ecke dargestellt wird
			if (gp.player.screenX > gp.player.worldX) {
				screenX = worldX;
			}
			if (gp.player.screenY > gp.player.worldY) {
				screenY = worldY;
			}
			int rightOffset = gp.screenWidth - gp.player.screenX;
			if (rightOffset > gp.worldWidth - gp.player.worldX) {
				screenX = gp.screenWidth - (gp.worldWidth - worldX);
			}
			int bottomOffset = gp.screenHeight - gp.player.screenY;
			if (bottomOffset > gp.worldHeight - gp.player.worldY) {
				screenY = gp.screenHeight - (gp.worldHeight - worldY);
			}
			
			if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				
				g2.drawImage(tile[0].image, screenX, screenY,null);
				g2.drawImage(tile[tileNum].image, screenX, screenY,null);
				
			}
			else if (gp.player.screenX > gp.player.worldX ||
					 gp.player.screenY > gp.player.worldY ||
					 rightOffset > gp.worldWidth - gp.player.worldX ||
					 bottomOffset > gp.worldHeight - gp.player.worldY) {
				
				g2.drawImage(tile[0].image, screenX, screenY,null);
				g2.drawImage(tile[tileNum].image, screenX, screenY,null);
				
			}
			
			
			worldCol++; //um das nächste tile zu Zeichnen, wird quasi +1 addiert, und das auf jedes tile

			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0; 
				worldRow++; 
			}
		}
	}
}
//test