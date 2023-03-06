package entity;

import java.awt.image.BufferedImage;



public class Entity {
	
	public int FPS = 144;
	public int x, y;
	public int speed = 4/(FPS/60);
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; //speichert die PNG dateien TODO Buffer bescheiben
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
}

