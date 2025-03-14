package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{
	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed;
	// Debug
	boolean checkDrawTime = false;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) 
	{
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		int code = e.getKeyCode();
		
		//estado de titulo
		if(gp.gameState == gp.titleState)
		{
			gp.playSE(6);
			if(code == KeyEvent.VK_W)
			{
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0)
				{
					gp.ui.commandNum=1;
				}
			}
			if(code == KeyEvent.VK_S)
			{
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 2)
				{
					gp.ui.commandNum=0;
				}
			}
			if(code == KeyEvent.VK_ENTER)
			{
				if(gp.ui.commandNum == 0)
				{
					gp.gameState = gp.playState;
				}
				if(gp.ui.commandNum == 1)
				{
					gp.gameState = gp.howToPlayState;
				}
				if(gp.ui.commandNum == 2)
				{
					System.exit(0);
				}
			}
		}
		
		//estado como jogar
		if(gp.gameState == gp.howToPlayState)
		{
			if(code == KeyEvent.VK_SPACE)
			{
				gp.gameState = gp.titleState;
			}
		}
		
		//estado game over
		if(gp.gameState == gp.gameOverState)
		{
			gp.playSE(6);
			if(code == KeyEvent.VK_W)
			{
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0)
				{
					gp.ui.commandNum=1;
				}
			}
			if(code == KeyEvent.VK_S)
			{
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 1)
				{
					gp.ui.commandNum=0;
				}
			}
			if(code == KeyEvent.VK_ENTER)
			{
				if(gp.ui.commandNum == 0)
				{
					gp.gameState = gp.playState;
					gp.retry();
				}
				if(gp.ui.commandNum == 1)
				{
					System.exit(0);
				}
			}
		}
			
		
		//estado de jogo
		if(gp.gameState == gp.playState) {
			if(code == KeyEvent.VK_W)
			{
				upPressed=true;
			}
			if(code == KeyEvent.VK_S)
			{
				downPressed=true;
			}
			if(code == KeyEvent.VK_A)
			{
				leftPressed=true;
			}
			if(code == KeyEvent.VK_D)
			{
				rightPressed=true;
			}
			if(code == KeyEvent.VK_P)
			{
				gp.gameState = gp.pauseState;
				
			}
			if(code == KeyEvent.VK_SPACE)
			{
				spacePressed = true;
			}
			
			// Debug
			if(code == KeyEvent.VK_T)
			{
				if(checkDrawTime == false)
				{
					checkDrawTime = true;
				}
				else
				{
					checkDrawTime = false;
				}
			}
		}
		
		//estado pausado
		else if(gp.gameState == gp.pauseState) {
				if(code == KeyEvent.VK_P)
				{
					gp.gameState = gp.playState;
					
				}
			}
		//estado de dialogo
		else if(gp.gameState == gp.dialogueState) {
				if(code == KeyEvent.VK_SPACE) {
					gp.gameState = gp.playState;
				}
			}
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		int code = e.getKeyCode();
		if(code== KeyEvent.VK_W)
		{
			upPressed=false;
		}
		if(code== KeyEvent.VK_S)
		{
			downPressed=false;
		}
		if(code== KeyEvent.VK_A)
		{
			leftPressed=false;
		}
		if(code== KeyEvent.VK_D)
		{
			rightPressed=false;
		}
	}
}