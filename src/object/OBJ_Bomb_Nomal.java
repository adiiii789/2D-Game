package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Bomb_Nomal extends Entity{

	public OBJ_Bomb_Nomal(GamePanel gp) {
		super(gp);
		
		type = type_bomb;
		name = "Normal Bomb";
		down1 = setup("/objects/Klee Bomb1",gp.tileSize,gp.tileSize);
		attackValue = 1; 
		description = "[" + name +"]\n \"Klee`s first Build\n \"toy\" ";
		attackArea.width = 36;
		attackArea.height = 36;
	}

	
}
