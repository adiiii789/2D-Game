package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity{

	GamePanel gp;

	public OBJ_Heart(GamePanel gp) {
		
		super(gp);
		
		name = "Heart";
		image = setup("/objects/full hearth",gp.tileSize,gp.tileSize);
		image2 = setup("/objects/half hearth",gp.tileSize,gp.tileSize);
		image3 = setup("/objects/no hearth",gp.tileSize,gp.tileSize);
		
		
		
		
	}
}
