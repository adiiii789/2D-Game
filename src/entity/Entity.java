package entity;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;


public class Entity {
	
	public int worldX, worldY;
	public int speed = 4;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; //speichert die PNG dateien TODO Buffer bescheiben
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle solidArea; //Erstellt ein Unsichtbbares Rechteck, welches uns als collisionbox dient. In modernen 3D Spielen nutzt man dafür einen Capsule collider
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
}
//test
 