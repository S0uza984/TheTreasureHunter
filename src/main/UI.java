package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import object.OBJ_Key;
import entity.Entity;
import object.OBJ_Heart;

public class UI 
{
	GamePanel gp;
	Graphics2D g2;
	Font maruMonica, purisaB;
	BufferedImage keyImage, heart_full, heart_blank;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	public String currentDialogue = "";
	public int commandNum = 0;
	
	public UI(GamePanel gp)
	{
		this.gp = gp;
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/Purisa Bold/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
		    is = getClass().getResourceAsStream("/font/Purisa Bold/Purisa Bold.ttf");
			purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		OBJ_Key key = new OBJ_Key(gp);
		keyImage = key.down1;
		
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_blank = heart.image2;
	}
	
	public void showMessage(String text)
	{
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2)
	{ 	
		this.g2 =g2;
		Font fonteOriginal = g2.getFont();
		g2.setFont(maruMonica);
		g2.setColor(Color.white);
			
		//ESTADO DE TITULO
		if(gp.gameState == gp.titleState)
		{
			drawTitleScreen();
		}
		
		//ESTADO COMO JOGAR
		if(gp.gameState == gp.howToPlayState)
		{
			drawHowToPlay();
		}
		
		//ESTADO DE JOGO
		if(gp.gameState == gp.playState) {	
			
		if(gameFinished == true)
		{
			String text;
			int textLength;
			int x, y;
			g2.setFont(fonteOriginal.deriveFont(30F));
			text = "Você achou o tesouro!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); 
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 - gp.tileSize*3;
			g2.drawString(text, x, y);
			
			text = "Seu tempo foi de: "+dFormat.format(playTime)+"!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); 
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + gp.tileSize*4;
			g2.drawString(text, x, y);
			
			text = "Parabéns!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); 
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + gp.tileSize*2;
			g2.drawString(text, x, y);
			
			gp.gameThread = null;		
		}
		else
		{
			drawPlayerlife();
			g2.setFont(fonteOriginal.deriveFont(30F));
			g2.drawImage(keyImage,gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
			g2.drawString("x "+ gp.player.hasKey, 74, 65);
			
			// Tempo
			playTime +=(double)1/60;
			g2.drawString("Time:"+dFormat.format(playTime), gp.tileSize * 12, 45);
			
			// Mensagem
			if(messageOn == true)
			{
				g2.setFont(g2.getFont().deriveFont(30F));
				g2.drawString(message, 0, 100);
				messageCounter++;
				if(messageCounter > 120)
				{
					messageCounter = 0;
					messageOn = false;
					
				}
			}
		}
	}
		
		//ESTADO DE PAUSE
		if(gp.gameState == gp.pauseState) {
			drawPlayerlife();
			drawPauseScreen();
		}
		//ESTADO DE DIALOGO
		if(gp.gameState == gp.dialogueState) {
			drawPlayerlife();
			drawDialogueScreen();
		}
		
		//ESTADO DE FIM DE JOGO
		if(gp.gameState == gp.gameOverState)
		{
			drawGameOverScreen();
		}
	}
	
	public void drawPlayerlife()
	{
		int x = gp.tileSize*12;
		int y = 55;
		int i = 0;
		while(i < gp.player.maxLife)
		{
			g2.drawImage(heart_blank, x, y,null);
			i++;
			x +=gp.tileSize;
		}
		
		x = gp.tileSize*12;
		y = 55;
		i = 0;
		while(i < gp.player.life)
		{
			g2.drawImage(heart_full, x, y,null);
			i++;
			x +=gp.tileSize;
		}
	}
	
	public void drawGameOverScreen()
	{
		g2.setColor(new Color(0, 0, 0));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		// GAME OVER
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
		String text = "VOCÊ MORREU!";
		int x = getXforCenteredText(text);
		int y = gp.tileSize * 3;
		
		// SOMBRA
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);
		
		// COR PRINCIPAL
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		//MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
		text = "TENTAR NOVAMENTE";
		x = getXforCenteredText(text);
		y += gp.tileSize*3.5;
		g2.drawString(text, x, y);
		if(commandNum == 0)
		{
			g2.drawString(">", x - gp.tileSize, y);
		}
	
		text = "SAIR";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 1)
		{
			g2.drawString(">", x - gp.tileSize, y);
		}
	}
	
	public void drawTitleScreen()
	{
		g2.setColor(new Color(0, 0, 0));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		// NOME DO JOGO
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
		String text = "The Treasure Hunter";
		int x = getXforCenteredText(text);
		int y = gp.tileSize * 3;
		
		// SOMBRA
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);
		
		// COR PRINCIPAL
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		// IMAGEM DO PERSONAGEM
		x = gp.screenWidth/2 - (gp.tileSize*2)/2;
		y += gp.tileSize*2;
		g2.drawImage(gp.player.down1, x, y , gp.tileSize*2, gp.tileSize*2, null);
		
		//MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
		text = "NOVO JOGO";
		x = getXforCenteredText(text);
		y += gp.tileSize*3.5;
		g2.drawString(text, x, y);
		if(commandNum == 0)
		{
			g2.drawString(">", x - gp.tileSize, y);
		}
		
		text = "COMO JOGAR";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 1)
		{
			g2.drawString(">", x - gp.tileSize, y);
		}
		
		text = "SAIR";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 2)
		{
			g2.drawString(">", x - gp.tileSize, y);
		}	
	}
	
	public void drawHowToPlay() 
	{
		g2.setColor(new Color(0, 0, 0));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
		g2.setColor(Color.white);
		int x;
		int y = gp.tileSize*3;
		String text = "Para andar para cima use a tecla W";	
		x = getXforCenteredText(text);
		g2.drawString(text, x, y);
		
		text = ("Para andar para baixo use a tecla S");
		x = getXforCenteredText(text);
		y +=gp.tileSize;
		g2.drawString(text, x, y);
		
		text = ("Para andar para direita use a tecla D");
		x = getXforCenteredText(text);
		y +=gp.tileSize;
		g2.drawString(text, x, y);
		
		text = ("Para andar para esquerda use a tecla A");
		x = getXforCenteredText(text);
		y +=gp.tileSize;
		g2.drawString(text, x, y);
		
		text = ("Para interagir com o NPC use a tecla SPACE");
		x = getXforCenteredText(text);
		y +=gp.tileSize;
		g2.drawString(text, x, y);	
		
		text = ("Para voltar a tela de Título use a tecla SPACE");
		x = getXforCenteredText(text);
		y +=gp.tileSize;
		g2.drawString(text, x, y);	
	}
	
	public void drawPauseScreen() {
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
		String text = "PAUSADO";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	public void drawDialogueScreen() {
		
		//Janela
		int x = gp.tileSize*2;
		int y = gp.tileSize/2;
		int width = gp.screenWidth -(gp.tileSize*4);
		int height = (gp.tileSize*5)+1/2;
		
		drawSubWindow(x,y,width,height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
		x += gp.tileSize;
		y += gp.tileSize;
			for(String line : currentDialogue.split("\n")) {
				g2.drawString(line, x, y);
				y+= 40;
		}
	}
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0,0,0,220);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5,y+5, width-10, height-10, 25, 25);
	}
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
}
