package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_LaserBlue;

public class MON_BlueSlime extends Entity{

	GamePanel gp;
	public MON_BlueSlime(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		type = type_monster;
		name = "Blue Slime";
		speed = 1;
		maxLife = 4;
		life = maxLife;
		attack = 2;
		defense = 0;
		exp =2;
		projectile = new OBJ_LaserBlue(gp);
		
		solidArea.x = 0;
		solidArea.y = 15;
		solidArea.width = 48;
		solidArea.height = 33; 
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
	}
	public void getImage() {
		
		up1 = setup("/monster/blueslime_down_1",gp.tileSize,gp.tileSize);
		up2 = setup("/monster/blueslime_down_2",gp.tileSize,gp.tileSize);
		down1 = setup("/monster/blueslime_down_1",gp.tileSize,gp.tileSize);
		down2 = setup("/monster/blueslime_down_2",gp.tileSize,gp.tileSize);
		left1 = setup("/monster/blueslime_down_1",gp.tileSize,gp.tileSize);
		left2 = setup("/monster/blueslime_down_2",gp.tileSize,gp.tileSize);
		right1 = setup("/monster/blueslime_down_1",gp.tileSize,gp.tileSize);
		right2 = setup("/monster/blueslime_down_2",gp.tileSize,gp.tileSize);
	}
	public void setAction() {

		actionLockCounter++;
		
		
		if(actionLockCounter == 60) {
			Random random = new Random();
			int i = random.nextInt(100)+1; //Zufällige Zahl zwischen 1 und 99+1
		
			if (i <= 20) {
				direction = "up";
			}
			if (i > 20 && i <= 40) {
				direction = "down";
			}
			if (i > 40 && i <= 60) {
				direction = "left";
			}
			if (i > 60 && i <= 80) {
				direction = "right";
			if (i > 80 && i <= 100) {
				direction = "null";
			}
			
		}
		actionLockCounter = 0;
		
		
		
		}
		int i = new Random().nextInt(100)+1;
		if(i > 99 
				) {
			projectile.set(worldX, worldY, direction, true, this);
			gp.projectileList.add(projectile);
			shotAvailableCounter = 0;
		}
	}
	public void damageReaction() {//Extends Entity ig
		actionLockCounter = 0;
		direction = gp.player.direction; //bewegt sich weg vom Spieler
	}
}
