package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable
{
	// Config Tela
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;
	public final int tileSize = originalTileSize * scale; // 48x48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels (largura tela)
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels (altura tela)
	
	// Config Mundo
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	// FPS
	int FPS = 60;
	
	// Sistema
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Thread gameThread;
	
	// Entidade e Objeto
	public Player player = new Player(this,keyH);
	public Entity obj[] = new Entity[10]; // mostra 10 objetos ao mesmo tempo
	public Entity npc[] = new Entity[10];
	public Entity monster[] = new Entity[20];
	ArrayList<Entity> entityList = new ArrayList<>();
	
	//Status do Jogo
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int howToPlayState = 4;
	public final int gameOverState = 5;
	
	public GamePanel()
	{
		super.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void setupGame() // Usado para adicionar objetos
	{
		
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		playMusic(0);
		gameState = titleState;
	}
	
	public void retry()
	{
		player.setDefaultValues();
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		playMusic(0);
		gameState = playState;
		ui.playTime = 0;
	}
	
	public void startGameThread()
	{
		gameThread = new Thread(this);
		gameThread.start();
	}	
	
	@Override
	public void run() 
	{
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		while(gameThread!=null)
		{
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime); 
			lastTime = currentTime;
			if(delta>=1)
			{
				update();
				repaint();
				delta--;
				drawCount++;
			}
			if(timer >= 1000000000)
			{
				System.out.println("FPS:"+drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}
	
	public void update()
	{
		if(gameState == playState) 
		{
			player.update();
			
		//NPC
		for(int i = 0; i< npc.length; i++) 
		{
			if(npc[i] != null) 
			{
				npc[i].update();
			}	
		}
		//MONSTER
		for(int i = 0; i< monster.length; i++) 
		{
			if(monster[i] != null) 
			{
				monster[i].update();
			}		
		}
		
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		// Debug
		long drawStart = 0;
		if(keyH.checkDrawTime == true)
		{
			drawStart = System.nanoTime();		
		}
		
		// Title Screen
		if(gameState == titleState)
		{
			ui.draw(g2);;
		}
		// How to Play
		if(gameState == howToPlayState)
		{
			ui.draw(g2);
		}
		
		// Game Over
		if(gameState == gameOverState)
		{
			ui.draw(g2);
		}
		
		// Others
		else 
		{
			// Tile
			tileM.draw(g2);
			
			//ADD ENTITIES TO THE LIST
			for(int i = 0; i < npc.length; i++)
			{
				if(npc[i]!=null)
				{
					entityList.add(npc[i]);
				}
			}
			
			for(int i = 0; i < obj.length; i++)
			{
				if(obj[i]!=null)
				{
					entityList.add(obj[i]);
				}
			}
			
			for(int i = 0; i < monster.length; i++)
			{
				if(monster[i]!=null)
				{
					entityList.add(monster[i]);
				}
			}
			
			entityList.add(player);
			
			//DRAW ENTITIES
			for(int i = 0; i < entityList.size(); i++)
			{
				entityList.get(i).draw(g2);
			}
			
			//EMPTY ENTITY LIST
			for(int i = 0; i < entityList.size(); i++)
			{
				entityList.remove(i);
			}
			
			// UI
			ui.draw(g2);
		}
	
		// Debug
		if(keyH.checkDrawTime == true)
		{
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Tempo para desenhar "+passed, 10, 400);
			System.out.println("Tempo para desenhar "+passed);
		}
		
		g2.dispose();
	}
	
	public void playMusic(int i)
	{
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic()
	{
		music.stop();
	}
	
	public void playSE(int i)
	{
		se.setFile(i);
		se.play();
	}
	
}
