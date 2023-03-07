package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import java.awt.Graphics2D;

import main.GamePanel;

public class TileManager {

	GamePanel gp;
	Tile[] tile;
	int mapTileNum[][]; //2D Array für das Karten .txt
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tile = new Tile[10];
		mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow]; //Array wird erstellt
		
		getTileImage();
		loadMap();
		
	}
	
	public void getTileImage() {
		
		try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/ground1.png"));
			
			//Wall Green
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_main_green.png")); 
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_top_green.png")); 
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_top_left_green.png")); 
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_bot_left_green.png")); 
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_top_right_green.png")); 
			
			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_bot_right_green.png")); 
			
			
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void loadMap() {
		try {
			InputStream is = getClass().getResourceAsStream("/maps/map01.txt"); //Map ins Programm importieren
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
				String line = br.readLine(); //Kann eine textzeile Lesen, und packt diese in die Variable
				
				while (col < gp.maxScreenCol) {
					
					String numbers[] = line.split(" "); //Deswegen ist die Map mit leerzeichen geschrieben. dadurch wird jede Zahl einzeln abgespeichert im Array
					
					int num = Integer.parseInt(numbers[col]); //Zwar haben wir die Zahlen im Array, allerdings sind diese als String gespeichert, hier wird dies konvertiert in Int
					
					mapTileNum[col][row] = num;
					col++;
					
				}
				if (col == gp.maxScreenCol) { //Sobald bis an den Rechten Rand alle tiles gezeichnet sind
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
		
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while(col < gp.maxScreenCol && row < gp.maxScreenRow) { //Ich bin mir sicher das dies mit einer doppelten for schleife sehr ähnlich funktioniert,
			//wäre daher for (int j = 0; j >= maxScreenCol; j++) und for (int i = 0; i >= maxScrreenRow; i++) o.ä
			
			int tileNum = mapTileNum[col][row];
			
			g2.drawImage(tile[0].image, x, y, gp.tileSize, gp.tileSize,null);
			g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize,null);
			col++;
			x+= gp.tileSize;
			
			if(col == gp.maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y++;
				y += gp.tileSize-1; //ich muss das hier manuell offsetten da alle tiles 1 pixel abstand zueinander hatten.
			}
		}
	}
}
