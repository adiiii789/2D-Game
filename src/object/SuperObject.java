package object;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.GamePanel;
import main.UtilityTool;

public class SuperObject {
//die Superklasse aller objektklassen, ähnlich wie Entity zu Player agiert
	
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	
	UtilityTool uTool = new UtilityTool();
	
	
	public void draw(Graphics2D g2, GamePanel gp) {
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX; //Wo wird das Tile auf dem Fenster gezeichnet
		int screenY = worldY - gp.player.worldY + gp.player.screenY; //screenX = 0 heißt also vom Fenster die obere linke ecke
																	 //der part neben worldxy ist ein Offset, damit der Player nicht in der Ecke dargestellt wird
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize,null);

			
		}
		
		
	}
}
//test