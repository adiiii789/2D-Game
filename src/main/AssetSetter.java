package main;

import entity.NPC_Link;
import monster.MON_BlueSlime;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

	GamePanel gp;
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
		
	}
	
	public void setObject() {
		
		gp.obj[0] = new OBJ_Key(gp);
		gp.obj[0].worldX = -1 * gp.tileSize;
		gp.obj[0].worldY = -1 * gp.tileSize;
		
		//gp.obj[1] = new OBJ_Door(gp);
		//gp.obj[1].worldX = 15 * gp.tileSize;
		//gp.obj[1].worldY = 5 * gp.tileSize;
		
//		gp.obj[2] = new OBJ_Chest(gp);
//		gp.obj[2].worldX = 8 * gp.tileSize;
//		gp.obj[2].worldY = 8 * gp.tileSize;
		
		gp.obj[2] = new OBJ_Boots(gp);
		gp.obj[2].worldX = 5 * gp.tileSize;
		gp.obj[2].worldY = 5 * gp.tileSize;
		
	}
	public void setNPC() {
		
		gp.npc[0] = new NPC_Link(gp);
		gp.npc[0].worldX = gp.tileSize*8 - gp.tileSize/2;
		gp.npc[0].worldY = gp.tileSize*2 - gp.tileSize/2;
	}
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