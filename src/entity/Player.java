package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity 
{

	KeyHandler keyH;
	public final int screenX;
	public final int screenY;
	public int hasKey = 0;
	
	public Player(GamePanel gp, KeyHandler keyH)
	{
		super(gp);
		this.keyH = keyH;
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		solidArea = new Rectangle(8, 16, 32, 32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() 
	{
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
		
		//Player Status
		maxLife = 3;
		life = maxLife;
	}
	
	public void getPlayerImage()
	{
		up1 = setup("/player/personagem_costa1");
		up2 = setup("/player/personagem_costa2");
		down1 = setup("/player/personagem_baixo1");
		down2 = setup("/player/personagem_baixo2");
		left1 = setup("/player/personagem_lado_esquerdo1");
		left2 = setup("/player/personagem_lado_esquerdo2");
		right1 = setup("/player/personagem_lado_direito1");
		right2 = setup("/player/personagem_lado_direito2");
	}
	
	
	public void update()
	{
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true )
		{	
			if(keyH.upPressed == true)
			{
				direction = "up";
			}
			else if(keyH.downPressed == true)
			{
				direction = "down";
			}
			else if(keyH.leftPressed == true)
			{
				direction = "left";
			}
			else if(keyH.rightPressed == true)
			{
				direction = "right";
			}
			
			// Check tile collision
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			// Check object collision
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			//Check Npc collision
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			//Check Monster collision
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);
			
			// If collision is false, player can move
			if(collisionOn == false)
			{
				switch(direction)
				{
					case "up":
						worldY -= speed;
						break;
					case "down":
						worldY += speed;
						break;
					case "left":
						worldX -= speed;
						break;
					case "right":
						worldX += speed;
						break;
				}
			}
			
			spriteCounter++;
			if(spriteCounter > 12)
			{
				if(spriteNum == 1)
				{
					spriteNum = 2;
				}
				else if(spriteNum == 2)
				{
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
		if(invincible == true)
		{
			invincibleCounter++;
			if(invincibleCounter > 60)
			{
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}
	
	public void pickUpObject(int i)
	{
		if(i != 999)
		{
			String objectName = gp.obj[i].name;
			switch(objectName)
			{
				case "Key":
					gp.playSE(1);
					hasKey++;
					gp.obj[i] = null;
					gp.ui.showMessage("Você obteve uma chave!");
					break;
				case "Door":
					if(hasKey > 0)
					{
						gp.playSE(3);
						gp.ui.showMessage("Você destravou uma porta!");
						gp.obj[i] = null;
						hasKey--;
					}
					else
					{
						gp.ui.showMessage("Você precisa de uma chave!");
					}
					break;
				case "Boots":
					gp.playSE(2);
					gp.ui.showMessage("Você obteve um par de botas!");
					speed += 1;
					gp.obj[i] = null;
					break;
				case "Chest":
					gp.ui.gameFinished = true;
					gp.stopMusic();
					gp.playSE(4);
					break;
			}
		}
	}
	
	public void interactNPC(int i){
		if(i != 999) {
			if(gp.keyH.spacePressed == true) {
			gp.gameState = gp.dialogueState;
			gp.npc[i].speak();
			}
		   }
		gp.keyH.spacePressed = false;
	}
	
	public void contactMonster(int i)
	{
		if(i != 999)
		{
			if(invincible == false)
			{
				life -= 1;
				invincible = true;
				if(life <= 0)
				{
					gp.gameState = gp.gameOverState;
					gp.stopMusic();
					gp.playSE(5);
				}
			}	
		}
	}
	
	public void draw(Graphics2D g2)
	{
		BufferedImage image = null;
		switch(direction)
		{
			case "up":
				if(spriteNum == 1)
				{
					image = up1;
				}
				if(spriteNum == 2)
				{
					image = up2;
				}
				break;
			case "down":
				if(spriteNum == 1)
				{
					image = down1;
				}
				if(spriteNum == 2)
				{
					image = down2;
				}
				break;
			case "left":
				if(spriteNum == 1)
				{
					image = left1;
				}
				if(spriteNum == 2)
				{
					image = left2;
				}
				break;
			case "right":
				if(spriteNum == 1)
				{
					image = right1;
				}
				if(spriteNum == 2)
				{
					image = right2;
				}
				break;
		}
		g2.drawImage(image, screenX, screenY, null);
	}	
}