package main;

import entity.NPC_Link;
import monster.MON_BlueSlime;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Shield_Blue;
import object.OBJ_X_Bomb;

public class AssetSetter {

	GamePanel gp;
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
		
	}
	/**
	 * Platziert die Objekte im Fenster im Verhältnis der Map
	 */
	public void setObject() {
		int i = 0;
		
		gp.obj[i] = new OBJ_Boots(gp);
		gp.obj[i].worldX = 5 *  gp.tileSize;
		gp.obj[i].worldY = 5 * gp.tileSize;
		i++;
		gp.obj[i] = new OBJ_X_Bomb(gp);
		gp.obj[i].worldX = 7 *  gp.tileSize;
		gp.obj[i].worldY = 7 * gp.tileSize;
		i++;
		gp.obj[i] = new OBJ_Shield_Blue(gp);
		gp.obj[i].worldX = 9 *  gp.tileSize;
		gp.obj[i].worldY = 7 * gp.tileSize;
		
	}
	/**
	 * Platziert die NPC im Fenster im Verhältnis der Map
	 */
	public void setNPC() {
		
		gp.npc[0] = new NPC_Link(gp);
		gp.npc[0].worldX = gp.tileSize*8 - gp.tileSize/2;
		gp.npc[0].worldY = gp.tileSize*2 - gp.tileSize/2;
	}
	/**
	 * Platziert die Gegner im Fenster im Verhältnis der Map
	 */
	public void setMonster() {
		gp.monster[0] = new MON_BlueSlime(gp);
		gp.monster[0].worldX = gp.tileSize*23;
		gp.monster[0].worldY = gp.tileSize*23;
		
		gp.monster[1] = new MON_BlueSlime(gp);
		gp.monster[1].worldX = gp.tileSize*23;
		gp.monster[1].worldY = gp.tileSize*30;
		
		gp.monster[2] = new MON_BlueSlime(gp);
		gp.monster[2].worldX = gp.tileSize*23;
		gp.monster[2].worldY = gp.tileSize*21;
		
		gp.monster[3] = new MON_BlueSlime(gp);
		gp.monster[3].worldX = gp.tileSize*21;
		gp.monster[3].worldY = gp.tileSize*30;
	}
}
//test