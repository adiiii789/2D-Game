package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_LaserBlue extends Projectile{
	GamePanel gp;
	public OBJ_LaserBlue(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name ="Laser";
		speed = 10;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;
		alive = false;
		getImage();
		
	}
	public void getImage() {
		if (gp.gameState == gp.playState) {
		up1 = setup("/projectile/Slime laser top",gp.tileSize,gp.tileSize);
		up2 = setup("/projectile/Slime laser top",gp.tileSize,gp.tileSize);
		down1 = setup("/projectile/Slime laser top",gp.tileSize,gp.tileSize);
		down2 = setup("/projectile/Slime laser top",gp.tileSize,gp.tileSize);
		left1 = setup("/projectile/Slime laser side",gp.tileSize,gp.tileSize);
		left2 = setup("/projectile/Slime laser side",gp.tileSize,gp.tileSize);
		right1 = setup("/projectile/Slime laser side",gp.tileSize,gp.tileSize);
		right2 = setup("/projectile/Slime laser side",gp.tileSize,gp.tileSize);
		}
		
			
		
	}
}
