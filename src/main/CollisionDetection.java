package main;

import entity.Entity;

public class CollisionDetection {

	GamePanel gp;
	public CollisionDetection (GamePanel gp) {
		this.gp = gp;
		
		
	}
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
}
//test