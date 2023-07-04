package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity{

	public OBJ_Shield_Blue(GamePanel gp) {
		super(gp);
		
		type = type_shield;
		name = "Blue Shield";
		down1 = setup("/objects/Klee shield blue",gp.tileSize,gp.tileSize);
		
		defenseValue = 2;
		description = "[" +name +"]\n \"The same \n  but different...?\"\n";
	}

}
