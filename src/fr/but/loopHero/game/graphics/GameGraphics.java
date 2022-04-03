package fr.but.loopHero.game.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.plaf.FontUIResource;

import fr.but.loopHero.droppable.Card;
import fr.but.loopHero.droppable.Droppable;
import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.TimeData;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.player.Player;
import fr.umlv.zen5.ApplicationContext;

public record GameGraphics(int xOrigin, int yOrigin, int length, int width, int taille) {
	
		public void drawBoard(Board plateau, ApplicationContext context) {
			context.renderFrame( graphics ->{
				Cell[][] boardMatrix = plateau.getBoardMatrix();
				
				graphics.setColor(Color.DARK_GRAY);
				
				graphics.fill(new Rectangle2D.Float(xOrigin,yOrigin,21*taille,12*taille)); // Grand rectangle de toute la surface
				graphics.draw(new Rectangle2D.Float(xOrigin,yOrigin,21*taille,12*taille));				
				for (int i = 0; i < boardMatrix.length; i++) {
					for (int j = 0; j < boardMatrix[0].length; j++) {
						//System.out.println(boardMatrix[i][j]);
						
						if (boardMatrix[i][j].isEmpty()) 
							continue;
							
							
						graphics.setColor(boardMatrix[i][j].getColor()); // On d�finis la couleur de la Tile
						int x = xOrigin + j*taille;
						int y = yOrigin + (i*taille);
						graphics.fill(new Rectangle2D.Float(x,y,taille,taille));
						graphics.draw(new Rectangle2D.Float(x,y,taille,taille));	
					}
					}
	
			});
		}
		
		public void drawOutlineLoop(Board plateau, ApplicationContext context) {
			context.renderFrame( graphics ->{
				graphics.setColor(Color.BLACK);
				for (Cell cell : plateau.getlistCellsLoop()) {
					int i = cell.i();
					int j = cell.j();
					int x = xOrigin + j*taille;
					int y = yOrigin + (i*taille);
					graphics.draw(new Rectangle.Float(x,y,taille,taille));
					
					
				}
				
				
				});
			
		}
		
		
		public void drawHero(Board plateau, ApplicationContext context, Player hero, TimeData timeData,int heroNextPos) {
			context.renderFrame( graphics ->{
				graphics.setColor(Color.BLACK);
				drawBar(graphics, 350, timeData.timeFraction(),0,0,Color.GREEN);
				

				//System.out.println(heroNextPos);
				int startingPointx = xOrigin + plateau.getlistCellsLoop().get(heroNextPos).j() * taille+taille/4;
				int startingPointy = yOrigin + plateau.getlistCellsLoop().get(heroNextPos).i()* taille +taille/4;
				
				graphics.fillOval(startingPointx,startingPointy,taille/2,taille/2);
				

			
			});
			
		}
		
		public void drawOneCell(Board plateau, ApplicationContext context, int i, int j) {
			context.renderFrame( graphics ->{
				graphics.setColor(plateau.getBoardMatrix()[i][j].getColor()); // On d�finis la couleur de la Tile
				int x = xOrigin + j*taille;
				int y = yOrigin + (i*taille);
				graphics.fill(new Rectangle2D.Float(x,y,taille,taille));
				graphics.draw(new Rectangle2D.Float(x,y,taille,taille));
			});
		}
		
		private void drawBar(Graphics2D graphics, int width, double timeFraction, int xOffset, int yOffset,Color color) {
			graphics.setColor(Color.LIGHT_GRAY);
			graphics.fill(new Rectangle2D.Double(xOrigin+xOffset, (yOrigin - 20)+yOffset, width, 10));
			graphics.setColor(color);
			graphics.fill(new Rectangle2D.Double(xOrigin+xOffset, (yOrigin - 20)+yOffset, width * timeFraction, 10));
			graphics.setColor(Color.BLACK);
			graphics.draw(new Rectangle2D.Double(xOrigin+xOffset, (yOrigin - 20)+yOffset, width, 10));
		}
		
		private Color getHealthColor(int[] healths) {
			double currentHealth = healths[0];
			double maxHealth = healths[1];
			double healthRatio = (currentHealth/maxHealth);
		//	System.out.println(healthRatio);
			//System.out.println(currentHealth);
			if(healthRatio >= 0.4)
				return Color.green;
			if(healthRatio >= 0.15)
				return Color.orange;
			if(healthRatio < 0.15)
				return Color.red;
			
			return Color.GREEN;
			
			
			
		}
		
		public void drawHealthInfos(ApplicationContext context,Player hero) {
			int[] healths = hero.getHealths();
			double currentHealth = healths[0];
			double maxHealth = healths[1];
			double healthRatio = (currentHealth/maxHealth);
			Color color = getHealthColor(healths);

			context.renderFrame(graphics ->{
				//System.out.println(healthRatio);
				drawBar(graphics, 400, healthRatio, 1300,400,color);
				
				
				
				
			});
			
			
		}
		
		public void drawLevel(ApplicationContext context) {
			context.renderFrame(graphics ->{
				graphics.setColor(LoopHeroGameData.BG_COLOR);
				graphics.fillRect(1800, 20, 100, 35);
				
				graphics.setColor(LoopHeroGameData.TXT_COLOR);
				graphics.setFont(new FontUIResource("Arial", 0, 30));
				graphics.drawString("LVL: "+LoopHeroGameData.LEVEL, 1800, 50);
			});	
		}
		
		public void drawMobs(ApplicationContext context,Board plateau) {
			context.renderFrame( graphics ->{
				ArrayList<Cell> listCellsLoop = plateau.getlistCellsLoop();
				for (Cell cell : listCellsLoop ) {
					if (cell.hasMob()) {
						graphics.setColor(cell.getFirstMob().getColor()); // Couleur du slime
						int startingPointx = taille + cell.j() * taille;
						int startingPointy = taille + cell.i()* taille;
						graphics.fillOval(startingPointx,startingPointy,taille/2,taille/2);
					}
				
				}
			});
		}


		public void drawInventory(ApplicationContext context,Player hero) {
			context.renderFrame( graphics ->{		
				ArrayList<Droppable> inventory = hero.getInventory();
				graphics.setColor(LoopHeroGameData.BG_COLOR);
				graphics.fill(new Rectangle2D.Float(xOrigin, yOrigin+(12*taille), taille*12, taille*2));
				
				int i = 0;
				for (Droppable droppable : inventory) {
					if(droppable instanceof Card) {
						drawCard(context, (Card) droppable,i);
						i++;				
					}
					
					
					
				}
			
			
			});
		}

		private void drawCard(ApplicationContext context,Card card,int i) {
			context.renderFrame( graphics ->{
				graphics.setColor(Color.GRAY);
				//graphics.fill(new Rectangle2D.Float(xOrigin+(taille*2*i), yOrigin+900, taille*2, taille*2));
				graphics.setColor(Color.BLACK);
				graphics.draw(new Rectangle2D.Float(xOrigin+(taille*i), yOrigin+(12*taille), taille, taille));
				String cardName = card.displayName();
				
				graphics.setColor(Color.RED);
				graphics.setFont(new FontUIResource("Arial", 0, 17));
				graphics.drawString(cardName, xOrigin+(taille*i), yOrigin+(12*taille)+taille+15);
			
			});	
		}






		private int indexFromReaCoord(float coord, int origin) {
			return (int) ((coord - origin) / taille);
		}


		public int lineFromY(float y) {
			return indexFromReaCoord(y, yOrigin);
		}


		public int columnFromX(float x) {
			return indexFromReaCoord(x, xOrigin);
		}

		public void drawSelection(Board plateau, ApplicationContext context, int i, int j, Color color) {
			
			context.renderFrame( graphics ->{
				graphics.setColor(color); // On d�finis la couleur de la Tile
				int x = xOrigin + j*taille;
				int y = yOrigin + (i*taille);
				graphics.draw(new Rectangle2D.Float(x,y,taille,taille));
			});
		}


}


	
	
	