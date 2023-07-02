package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_Link extends Entity{

	public NPC_Link(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 2;
		
		getImage();
		
	}
	public void getImage () {
		
		up1 = setup("/player/link_up_1");
		up2 = setup("/player/link_up_2");
		down1 = setup("/player/link_front_1");
		down2 = setup("/player/link_front_2");
		left1 = setup("/player/link_left_1");
		left2 = setup("/player/link_left_2");
		right1 = setup("/player/link_right_1");
		right2 = setup("/player/link_right_2");

	}
	public void setAction() { //simple AI f�r den Charakter
		
		actionLockCounter++;
		
		
		if(actionLockCounter == 60) {
			Random random = new Random();
			int i = random.nextInt(100)+1; //Zuf�llige Zahl zwischen 1 und 99+1
		
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
