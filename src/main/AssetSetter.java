package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter 
{
	GamePanel gp;
	public AssetSetter(GamePanel gp)
	{
		this.gp = gp;
	}
	
	public void setObject()
	{
		gp.obj[0] = new OBJ_Key(gp);
		gp.obj[0].worldX = 23 * gp.tileSize;
		gp.obj[0].worldY = 7 * gp.tileSize;
		
		gp.obj[1] = new OBJ_Key(gp);
		gp.obj[1].worldX = 23 * gp.tileSize;
		gp.obj[1].worldY = 40 * gp.tileSize;
		
		gp.obj[2] = new OBJ_Key(gp);
		gp.obj[2].worldX = 38 * gp.tileSize;
		gp.obj[2].worldY = 8 * gp.tileSize;
		
		gp.obj[3] = new OBJ_Door(gp);
		gp.obj[3].worldX = 10 * gp.tileSize;
		gp.obj[3].worldY = 12 * gp.tileSize;
		
		gp.obj[4] = new OBJ_Door(gp);
		gp.obj[4].worldX = 8 * gp.tileSize;
		gp.obj[4].worldY = 28 * gp.tileSize;
		
		gp.obj[5] = new OBJ_Door(gp);
		gp.obj[5].worldX = 12 * gp.tileSize;
		gp.obj[5].worldY = 23 * gp.tileSize;
		
		gp.obj[6] = new OBJ_Chest(gp);
		gp.obj[6].worldX = 10 * gp.tileSize;
		gp.obj[6].worldY = 8 * gp.tileSize;
		
		gp.obj[7] = new OBJ_Boots(gp);
		gp.obj[7].worldX = 37 * gp.tileSize;
		gp.obj[7].worldY = 42 * gp.tileSize;
		
		
	}
	public void setNPC() {
		
		gp.npc[0] = new NPC_OldMan(gp);
		gp.npc[0].worldX = gp.tileSize*21;
		gp.npc[0].worldY = gp.tileSize*21;
	}
	
	public void setMonster()
	{
		gp.monster[0] = new MON_GreenSlime(gp);
		gp.monster[0].worldX = gp.tileSize*23;
		gp.monster[0].worldY = gp.tileSize*36;
		
		gp.monster[1] = new MON_GreenSlime(gp);
		gp.monster[1].worldX = gp.tileSize*23;
		gp.monster[1].worldY = gp.tileSize*37;
		
		gp.monster[2] = new MON_GreenSlime(gp);
		gp.monster[2].worldX = gp.tileSize*23;
		gp.monster[2].worldY = gp.tileSize*35;
		
		gp.monster[3] = new MON_GreenSlime(gp);
		gp.monster[3].worldX = gp.tileSize*23;
		gp.monster[3].worldY = gp.tileSize*38;
		
		gp.monster[4] = new MON_GreenSlime(gp);
		gp.monster[4].worldX = gp.tileSize*23;
		gp.monster[4].worldY = gp.tileSize*23;
		
		gp.monster[5] = new MON_GreenSlime(gp);
		gp.monster[5].worldX = gp.tileSize*23;
		gp.monster[5].worldY = gp.tileSize*24;
		
		gp.monster[6] = new MON_GreenSlime(gp);
		gp.monster[6].worldX = gp.tileSize*23;
		gp.monster[6].worldY = gp.tileSize*25;
		
		gp.monster[7] = new MON_GreenSlime(gp);
		gp.monster[7].worldX = gp.tileSize*38;
		gp.monster[7].worldY = gp.tileSize*7;
		
		gp.monster[8] = new MON_GreenSlime(gp);
		gp.monster[8].worldX = gp.tileSize*38;
		gp.monster[8].worldY = gp.tileSize*6;
		
		gp.monster[9] = new MON_GreenSlime(gp);
		gp.monster[9].worldX = gp.tileSize*38;
		gp.monster[9].worldY = gp.tileSize*9;
		
		gp.monster[10] = new MON_GreenSlime(gp);
		gp.monster[10].worldX = gp.tileSize*38;
		gp.monster[10].worldY = gp.tileSize*10;
		
		gp.monster[11] = new MON_GreenSlime(gp);
		gp.monster[11].worldX = gp.tileSize*23;
		gp.monster[11].worldY = gp.tileSize*7;
		
		gp.monster[12] = new MON_GreenSlime(gp);
		gp.monster[12].worldX = gp.tileSize*23;
		gp.monster[12].worldY = gp.tileSize*12;
		
		gp.monster[13] = new MON_GreenSlime(gp);
		gp.monster[13].worldX = gp.tileSize*37;
		gp.monster[13].worldY = gp.tileSize*40;
		
		gp.monster[14] = new MON_GreenSlime(gp);
		gp.monster[14].worldX = gp.tileSize*37;
		gp.monster[14].worldY = gp.tileSize*39;
		
		gp.monster[15] = new MON_GreenSlime(gp);
		gp.monster[15].worldX = gp.tileSize*37;
		gp.monster[15].worldY = gp.tileSize*37;
		
	}
}
