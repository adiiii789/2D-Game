package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Bomb_Nomal extends Entity{

	public OBJ_Bomb_Nomal(GamePanel gp) {
		super(gp);
		
		name = "Normal Bomb";
		down1 = setup("/objects/Klee Bomb1",gp.tileSize,gp.tileSize);
		attackValue = 1;
	}

	
}
