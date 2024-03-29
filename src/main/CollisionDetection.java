package main;

import entity.Entity;

public class CollisionDetection {

	GamePanel gp;
	public CollisionDetection (GamePanel gp) {
		this.gp = gp;
		
	
	}
	/**
	 * Erfasst Kollision abh�ngig von Bewegungsrichtung
	 * Kollision ist f�r alle entitys �bergreifend	
	 */
	public void checkTile(Entity entity) { //allgemeine Collision, nicht nur f�r den Player
		
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height; //im �bertragenen Sinne sind dies Coordinaten
		
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize; 
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		int tileNum1, tileNum2;
		
		switch (entity.direction) { //idee: wenn man bei den Anderen detections die Collision deaktivieren w�rde, so w�rde der charakter nur einseitig durch eine Collision laufen
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize; //wenn man sich nach Oben bewegt, wird nur die Obere Collision gepr�ft
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize; //wenn man sich nach Oben bewegt, wird nur die Obere Collision gepr�ft
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize; //wenn man sich nach Oben bewegt, wird nur die Obere Collision gepr�ft
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize; //wenn man sich nach Oben bewegt, wird nur die Obere Collision gepr�ft
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
			
		}
		
		
	}
	/**
	 * intersect pr�ft ob zwei Rectangles sich �berschneiden
	 * 
	 * 
	 * @param entity
	 * @param player
	 * @return
	 */
	public int checkObject (Entity entity, boolean player) {
		
		int index = 999;
		
		for (int i = 0; i < gp.obj.length; i++) {
			
			if (gp.obj[i] != null) {
				
				//entity position der solid area
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				//object position der solid area
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
				
				switch (entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) { //es wird �berpr�ft, ob diese beiden Rechtecke miteinander Collidieren, also entity mit Object
						if (gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if (player == true) {
							index = i;
						}
					break;
					}
				case "down":
					entity.solidArea.y += entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) { //es wird �berpr�ft, ob diese beiden Rechtecke miteinander Collidieren, also entity mit Object
						if (gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if (player == true) {
							index = i;
						}
					break;
					}
				case "left":
					entity.solidArea.x -= entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) { //es wird �berpr�ft, ob diese beiden Rechtecke miteinander Collidieren, also entity mit Object
						if (gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if (player == true) {
							index = i;
						}
					break;
					}
				case "right":
					entity.solidArea.x += entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) { //es wird �berpr�ft, ob diese beiden Rechtecke miteinander Collidieren, also entity mit Object
						if (gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if (player == true) {
							index = i;
						}
					break;
					}
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
			}
			
		}
		
		return index;
	}
	/**
	 * �berpr�ft ob EntityCollision sich mit EntityCollision intersected
	 * @param entity
	 * @param target
	 * @return
	 */
	public int checkEntity(Entity entity, Entity[] target) { //NPC collision
int index = 999;
		
		for (int i = 0; i < target.length; i++) {
			
			if (target[i] != null) {
				
				//entity position der solid area
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				//object position der solid area
				target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
				target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;
				
				switch (entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					if(entity.solidArea.intersects(target[i].solidArea)) { //es wird �berpr�ft, ob diese beiden Rechtecke miteinander Collidieren, also entity mit Object
						if(target[i] != entity) {
						entity.collisionOn = true;
						index = i;
						}
					}
					break;
					
				case "down":
					entity.solidArea.y += entity.speed;
					if(entity.solidArea.intersects(target[i].solidArea)) { //es wird �berpr�ft, ob diese beiden Rechtecke miteinander Collidieren, also entity mit Object
						if(target[i] != entity) {
						entity.collisionOn = true;
						index = i;
						}
					}
					break;
					
				case "left":
					entity.solidArea.x -= entity.speed;
					if(entity.solidArea.intersects(target[i].solidArea)) { //es wird �berpr�ft, ob diese beiden Rechtecke miteinander Collidieren, also entity mit Object
						if(target[i] != entity) {
						entity.collisionOn = true;
							index = i;
						}
					}
					break;
					
				case "right":
					entity.solidArea.x += entity.speed;
					if(entity.solidArea.intersects(target[i].solidArea)) {
						if(target[i] != entity) {
						
						entity.collisionOn = true;
						index = i;
					}
					}
					break;
					
					
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[i].solidArea.x = target[i].solidAreaDefaultX;
				target[i].solidArea.y = target[i].solidAreaDefaultY;
			}
			
		}
		
		return index;
	}
	/**
	 * setzt solidArea f�r den Spieler
	 * @param entity
	 * @return
	 */
	public boolean checkPlayer(Entity entity) {
		
		boolean contactPlayer = false;
			//entity position der solid area
			entity.solidArea.x = entity.worldX + entity.solidArea.x;
			entity.solidArea.y = entity.worldY + entity.solidArea.y;
			
			//object position der solid area
			gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
			gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
			
			switch (entity.direction) {
			case "up":
				entity.solidArea.y -= entity.speed;
				
					
				break;
				
			case "down":
				entity.solidArea.y += entity.speed;
				
					
				break;
				
			case "left":
				entity.solidArea.x -= entity.speed;
				
					
				break;
				
			case "right":
				entity.solidArea.x += entity.speed;
				
					
				break;
				}
			
		if(entity.solidArea.intersects(gp.player.solidArea)) { //es wird �berpr�ft, ob diese beiden Rechtecke miteinander Collidieren, also entity mit Object
		entity.collisionOn = true;
		contactPlayer = true;
		}
		
			entity.solidArea.x = entity.solidAreaDefaultX;
			entity.solidArea.y = entity.solidAreaDefaultY;
			gp.player.solidArea.x = gp.player.solidAreaDefaultX;
			gp.player.solidArea.y = gp.player.solidAreaDefaultY;
			return contactPlayer;
	}
}
//test