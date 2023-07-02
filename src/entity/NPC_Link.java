package entity;

import main.GamePanel;

public class NPC_Link extends Entity{

	public NPC_Link(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 3;
		
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

}
