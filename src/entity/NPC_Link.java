package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_Link extends Entity{

	public NPC_Link(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		getImage();
		setDialogue();
		
	}
	public void getImage () {
		
		up1 = setup("/player/link_up_1",gp.tileSize,gp.tileSize);
		up2 = setup("/player/link_up_2",gp.tileSize,gp.tileSize);
		down1 = setup("/player/link_front_1",gp.tileSize,gp.tileSize);
		down2 = setup("/player/link_front_2",gp.tileSize,gp.tileSize);
		left1 = setup("/player/link_left_1",gp.tileSize,gp.tileSize);
		left2 = setup("/player/link_left_2",gp.tileSize,gp.tileSize);
		right1 = setup("/player/link_right_1",gp.tileSize,gp.tileSize);
		right2 = setup("/player/link_right_2",gp.tileSize,gp.tileSize);

	}
	public void setDialogue() {
		dialogues[0] = "Hello, unknown Stranger! \nas you may see, im an Adventurer \nmyself";
		dialogues[1] = "It is dangerous to go alone.";
		dialogues[2] = "Take this!";
		dialogues[3] = "Git Gud :D";
	}
	/**
	 * Die """AI""", ähnlich wie die Monster macht sie zufällige bewegungen
	 */
	public void setAction() { //simple AI für den Charakter
		
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
	public void speak() { //Methode für Charakterspezifische Eigenschaften
		
		super.speak();
		
		}
	}
	

