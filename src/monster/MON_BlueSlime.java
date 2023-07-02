package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_BlueSlime extends Entity{

	public MON_BlueSlime(GamePanel gp) {
		super(gp);
		
		type = 2;
		name = "Blue Slime";
		speed = 1;
		maxLife = 4;
		life = maxLife;
		
		solidArea.x = 0;
		solidArea.y = 15;
		solidArea.width = 48;
		solidArea.height = 33; 
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
	}
	public void getImage() {
		
		up1 = setup("/monster/blueslime_down_1");
		up2 = setup("/monster/blueslime_down_2");
		down1 = setup("/monster/blueslime_down_1");
		down2 = setup("/monster/blueslime_down_2");
		left1 = setup("/monster/blueslime_down_1");
		left2 = setup("/monster/blueslime_down_2");
		right1 = setup("/monster/blueslime_down_1");
		right2 = setup("/monster/blueslime_down_2");
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
	}
	
}
