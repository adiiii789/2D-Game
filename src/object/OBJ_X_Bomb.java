package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_X_Bomb extends Entity{

	public OBJ_X_Bomb(GamePanel gp) {
		super(gp);
		
		type = type_xbomb;
		name = "X_Bomb";
		down1 = setup("/objects/klee X Bomb",gp.tileSize,gp.tileSize);
		attackValue = 2;
		attackArea.width = 48;
		attackArea.height = 48;
		description = "["+name+"]\n My Highscore!";
	}

}
