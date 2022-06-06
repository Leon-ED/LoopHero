package fr.but.loopHero.game.graphics;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.plaf.FontUIResource;

import fr.but.loopHero.droppable.Card;
import fr.but.loopHero.droppable.Droppable;
import fr.but.loopHero.droppable.equipment.Equipement;
import fr.but.loopHero.game.Combat;
import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.TimeData;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.game.objects.tiles.placedTiles.PlacedTiles;
import fr.but.loopHero.mobs.Mobs;
import fr.but.loopHero.player.Player;
import fr.umlv.zen5.ApplicationContext;

public record GameGraphics(int xOrigin, int yOrigin, int length, int width, int taille, Board plateau) {

	private void drawBoardBackGround(ApplicationContext context, Board plateau, Color color) {
		context.renderFrame(graphics -> {

			graphics.setColor(color);
			graphics.fill(new Rectangle2D.Float(xOrigin, yOrigin, 21 * taille, 12 * taille));
			graphics.draw(new Rectangle2D.Float(xOrigin, yOrigin, 21 * taille, 12 * taille));

		});
	}

	public void drawBoard(Board plateau, ApplicationContext context) {
		context.renderFrame(graphics -> {
			Cell[][] boardMatrix = plateau.getBoardMatrix();

			graphics.setColor(Color.DARK_GRAY);
			drawBoardBackGround(context, plateau, Color.DARK_GRAY);

			for (int i = 0; i < boardMatrix.length; i++) {
				for (int j = 0; j < boardMatrix[0].length; j++) {
					if (boardMatrix[i][j].isEmpty())
						continue;
					drawOneCell(plateau, context, i, j);
				}
			}

			drawSpeedIndicator(context, TimeData.SpeedIndicator);

		});
	}

	public void drawOutlineLoop(Board plateau, ApplicationContext context) {
		context.renderFrame(graphics -> {
			graphics.setColor(Color.BLACK);
			for (Cell cell : plateau.getlistCellsLoop()) {
				int i = cell.i();
				int j = cell.j();
				int x = xOrigin + j * taille;
				int y = yOrigin + (i * taille);
				graphics.draw(new Rectangle.Float(x, y, taille, taille));

			}

		});

	}

	public void drawHero(Board plateau, ApplicationContext context, Player hero) {
		int heroNextPos = hero.getCurrentCellIndex();
		context.renderFrame(graphics -> {
			if (heroNextPos - 1 >= 0)
				this.drawOneCell(plateau, context, plateau.getlistCellsLoop().get(heroNextPos - 1).i(),
						plateau.getlistCellsLoop().get(heroNextPos - 1).j());
			graphics.setColor(Color.BLACK);
			int startingPointx = xOrigin + plateau.getlistCellsLoop().get(heroNextPos).j() * taille + taille / 4;
			int startingPointy = yOrigin + plateau.getlistCellsLoop().get(heroNextPos).i() * taille + taille / 4;

			graphics.fillOval(startingPointx, startingPointy, taille / 2, taille / 2);

		});

	}

	public void drawOneCell(Board plateau, ApplicationContext context, int i, int j) {
		context.renderFrame(graphics -> {
			try {
				graphics.setColor(plateau.getBoardMatrix()[i][j].getColor()); // On dï¿½finis la couleur de la Tile
			} catch (ArrayIndexOutOfBoundsException e) {

				graphics.setColor(Color.red);
			}

			int x = xOrigin + j * taille;
			int y = yOrigin + (i * taille);
			graphics.fill(new Rectangle2D.Float(x, y, taille, taille));
			graphics.draw(new Rectangle2D.Float(x, y, taille, taille));
			if (plateau.getBoardMatrix()[i][j].type() instanceof PlacedTiles) {
				int police = 13;
				if (plateau.getBoardMatrix()[i][j].type().name().length() > 8)
					police = 10;
				drawString(context, plateau.getBoardMatrix()[i][j].type().name(), Color.WHITE, police, x, y);
			}
		});
	}

	public void drawBar(ApplicationContext context, int width, double timeFraction, int xOffset, int yOffset,
			Color color, int height) {
		context.renderFrame(graphics -> {
			graphics.setColor(Color.LIGHT_GRAY);
			graphics.fill(new Rectangle2D.Double(xOrigin + xOffset, (yOrigin - 20) + yOffset, width, height));
			graphics.setColor(color);
			graphics.fill(
					new Rectangle2D.Double(xOrigin + xOffset, (yOrigin - 20) + yOffset, width * timeFraction, height));
			graphics.setColor(Color.BLACK);
			graphics.draw(new Rectangle2D.Double(xOrigin + xOffset, (yOrigin - 20) + yOffset, width, height));
		});

	}

	private Color getHealthColor(int[] healths) {
		double currentHealth = healths[0];
		double maxHealth = healths[1];
		double healthRatio = (currentHealth / maxHealth);
		if (healthRatio >= 0.4)
			return Color.green;
		if (healthRatio >= 0.15)
			return Color.orange;
		if (healthRatio < 0.15)
			return Color.red;

		return Color.GREEN;

	}

	public void drawHealthInfos(ApplicationContext context, int healths[], int epaisseur, int x, int y, int hauteur) {
		double currentHealth = healths[0];
		double maxHealth = healths[1];
		double healthRatio = (currentHealth / maxHealth);
		Color color = getHealthColor(healths);
		drawBar(context, epaisseur, healthRatio, x, y, color, hauteur);
		context.renderFrame(graphics -> {

			graphics.setFont(new FontUIResource("Arial", 0, 25));
			if (healths[1] >= 250)
				graphics.drawString((int) currentHealth + "/" + (int) maxHealth, 1500, 505);

		});

	}

	public void drawLevel(ApplicationContext context) {
		context.renderFrame(graphics -> {
			graphics.setColor(LoopHeroGameData.BG_COLOR);
			graphics.fillRect(1800, 20, 100, 35);

			graphics.setColor(Color.RED);
			graphics.setFont(new FontUIResource("Arial", 0, 25));
			graphics.drawString("Niveau: " + LoopHeroGameData.LEVEL, 1360, 570);
		});
	}

	public void drawMobs(ApplicationContext context, Board plateau) {
		context.renderFrame(graphics -> {
			ArrayList<Cell> listCellsLoop = plateau.getlistCellsLoop();
			for (Cell cell : listCellsLoop) {
				if (cell.hasMob()) {
					cell.getFirstMob().draw(graphics, taille);
				}
			}
		});
	}

	public void drawInventory(ApplicationContext context, Player hero) {
		ArrayList<ArrayList<Droppable>> inventory = hero.getInventory();
		drawInventoryCards(context, hero, inventory.get(0));
		drawInventoryEquipements(context, hero, inventory.get(1));
		drawInventoryRessources(context, hero, inventory.get(2));

	}

	public void drawStaticInventory(ApplicationContext context) {
		int startX = taille * 23;
		int startY = taille * 4;

		context.renderFrame(graphics -> {

			for (int i = 0; i < LoopHeroGameData.INV_HEIGHT; i++) {
				for (int j = 0; j < LoopHeroGameData.INV_WIDTH; j++) {
					int index = (i) * 4 + (j);
					int x = startX + (xOrigin + j * taille);
					int y = startY + (yOrigin + (i * taille));
					graphics.drawRect(x, y, taille, taille);
					drawString(context, LoopHeroGameData.EQUIPED_EQUIPEMENT_ORDER.get(index).toString(), Color.MAGENTA,
							15, x, y - taille * 3);
					graphics.drawRect(x, y - taille * 4, taille, taille);

				}

			}

		});

	}

	private void drawInventoryCards(ApplicationContext context, Player hero, ArrayList<Droppable> Cards) {
		context.renderFrame(graphics -> {
			graphics.setColor(LoopHeroGameData.BG_COLOR);
			graphics.fill(new Rectangle2D.Float(xOrigin, yOrigin + (12 * taille), taille * 14, (taille * 3) + 12));
		});
		for (int i = 0; i < Cards.size(); i++)
			drawCard(context, Cards.get(i), i);
	}

	private void drawInventoryEquipements(ApplicationContext context, Player hero, ArrayList<Droppable> Equipements) {
		context.renderFrame(graphics -> {
			graphics.setColor(LoopHeroGameData.BG_COLOR);
			graphics.fillRect(xOrigin + taille * 23, yOrigin + taille * 4, taille * 4, taille * 3);
			drawStaticInventory(context);
		});
		for (int i = 0; i < Equipements.size(); i++)
			drawEquipement(context, (Equipement) Equipements.get(i), (i / LoopHeroGameData.INV_WIDTH) + 4,
					((i % LoopHeroGameData.INV_WIDTH) + 23));
	}

	private void drawInventoryRessources(ApplicationContext context, Player hero, ArrayList<Droppable> Ressources) {
		context.renderFrame(graphics -> {
			graphics.setColor(LoopHeroGameData.BG_COLOR);
			graphics.fill(
					new Rectangle2D.Float(xOrigin, yOrigin + (12 * taille) + taille * 2, taille * 12, taille * 2));
		});
		HashMap<String, Integer> dict_ressources = new HashMap<String, Integer>();
		for (Droppable droppable : Ressources) {
			if (dict_ressources.containsKey(droppable.displayName())) {
				dict_ressources.put(droppable.displayName(), dict_ressources.get(droppable.displayName()) + 1);
			} else
				dict_ressources.put(droppable.displayName(), 1);
		}
		int count = 0;
		for (HashMap.Entry<String, Integer> ressource : dict_ressources.entrySet()) {
			count++;
			String key = ressource.getKey();
			Integer val = ressource.getValue();
			drawRessource(context, key, count, val);
		}
	}

	private void drawRessource(ApplicationContext context, String name, int position, int occurence) {

		context.renderFrame(graphics -> {
			graphics.setColor(Color.BLACK);
			graphics.draw(new Rectangle2D.Float(xOrigin + (taille * position), yOrigin + (12 * taille) + taille * 2,
					taille, taille));

			graphics.setColor(Color.RED);
			graphics.setFont(new FontUIResource("Arial", 0, 10));
			graphics.drawString(name, xOrigin + (taille * position),
					yOrigin + (12 * taille) + taille * 2 + taille + 15);

			graphics.setFont(new FontUIResource("Arial", 0, 20));
			graphics.drawString(occurence + "", xOrigin + (taille * position),
					yOrigin + (12 * taille) + taille * 2 + taille);

		});
	}

	public void drawEquipement(ApplicationContext context, Equipement droppable, int i, int j) {
		int startX = 0;
		int startY = 0;
		context.renderFrame(graphics -> {
			graphics.setColor(droppable.getColor());
			int x = startX + (xOrigin + j * taille);
			int y = startY + (yOrigin + (i * taille));
			graphics.fillRect(x, y, taille, taille);
			drawString(context, droppable.displayName(), Color.BLACK, 15, x, y + taille - 40);
			drawString(context, "(" + droppable.getValue() + ")", Color.BLACK, 15, x, y + taille - 25);
			graphics.setColor(Color.black);
			graphics.drawRect(x, y, taille, taille);
		});

	}

	private void drawCard(ApplicationContext context, Droppable droppable, int i) {
		context.renderFrame(graphics -> {
			graphics.setColor(Color.BLACK);
			graphics.draw(new Rectangle2D.Float(xOrigin + (taille * i), yOrigin + (12 * taille), taille, taille));
			String cardName = droppable.displayName();

			graphics.setColor(Color.RED);
			graphics.setFont(new FontUIResource("Arial", 0, 12));
			if (cardName.split(" ").length == 2) {
				graphics.drawString(cardName.split(" ")[0], xOrigin + (taille * i),
						yOrigin + (12 * taille) + taille + 15);
				graphics.drawString(cardName.split(" ")[1], xOrigin + (taille * i),
						yOrigin + (12 * taille) + taille + 35);

			} else {
				graphics.drawString(cardName, xOrigin + (taille * i), yOrigin + (12 * taille) + taille + 15);
			}
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

	public void drawSelection(ApplicationContext context, int i, int j, Color color) {

		context.renderFrame(graphics -> {
			graphics.setColor(color); // On dï¿½finis la couleur de la Tile
			int x = xOrigin + j * taille;
			int y = yOrigin + (i * taille);
			graphics.drawRect(x, y, taille, taille);
		});
	}

	public void drawSpeedIndicator(ApplicationContext context, int speedFactor) {
		context.renderFrame(graphics -> {
			graphics.setColor(LoopHeroGameData.BG_COLOR);
			graphics.fillRect(500, 15, 200, 35);

			graphics.setColor(LoopHeroGameData.TXT_COLOR_BLK);

			graphics.setFont(new FontUIResource("Arial", 0, 30));
			graphics.drawString("Vitesse : x" + speedFactor, 500, 40);
		});
	}

	// Affiche les informations du héros
	public void drawHeroInformations(ApplicationContext context, Player hero) {

		context.renderFrame(graphics -> {
			// graphics.setColor(LoopHeroGameData.BG_COLOR);
			graphics.setColor(Color.BLACK);
			graphics.fillRect(1350, 500, 400, 400);

			graphics.setColor(LoopHeroGameData.TXT_COLOR_WHT);
			graphics.setFont(new FontUIResource("Arial", 0, 30));
			graphics.drawString("Statistiques", 1475, 540);

			graphics.setFont(new FontUIResource("Arial", 0, 25));
			graphics.drawString("Dégâts : " + hero.getIntervalDamage(), 1360, 610);

			graphics.setFont(new FontUIResource("Arial", 0, 25));
			graphics.drawString("PV max : +" + (int) hero.maxHealth(), 1360, 650);

			graphics.setFont(new FontUIResource("Arial", 0, 25));
			graphics.drawString("Défense : " + hero.defensePoints(), 1360, 690);

			graphics.setFont(new FontUIResource("Arial", 0, 25));
			graphics.drawString("Contre : " + hero.counterPercent() + "%", 1360, 730);

			graphics.setFont(new FontUIResource("Arial", 0, 25));
			graphics.drawString("Esquive : " + hero.evadePercent() + "%", 1360, 770);

			graphics.setFont(new FontUIResource("Arial", 0, 25));
			graphics.drawString("Régénération par sec : +" + (int) hero.regenPerSecond(), 1360, 810);

			graphics.setFont(new FontUIResource("Arial", 0, 25));
			graphics.drawString("Vampirisme : " + (int) hero.vampirismPercent() + "%", 1360, 850);
		});

		drawLevel(context);
		drawHealthInfos(context, hero.getHealths(), 400, 1300, 450, 30);

	}

	public void drawCombat(ApplicationContext context, Combat combat) {
		drawBoardBackGround(context, plateau, Color.DARK_GRAY);
		drawString(context, "En combat contre : " + combat.getOpponent().getName(), LoopHeroGameData.TXT_COLOR_WHT, 30,
				(int) width / 2, yOrigin + 100);
		context.renderFrame(graphics -> {
			combat.getOpponent().drawInCombat(graphics, 60);
			graphics.setColor(Color.BLACK);
			int startingPointx = 200;
			int startingPointy = 400;
			graphics.fillOval(startingPointx, startingPointy, 60, 60);

		});
	}

	public void drawDamages(ApplicationContext context, int mobAttack, int heroAttack, int nbAttaque) {
		context.renderFrame(graphics -> {
			// Degats subis par le joueur
			graphics.setColor(Color.DARK_GRAY);
			int startingPointx;
			int startingPointy;
			if (mobAttack != 0) {
				startingPointx = 250;
				startingPointy = 400;
				graphics.fillRect(startingPointx, startingPointy - 20, 60, 20);
				drawString(context, "-" + mobAttack + " HP", Color.YELLOW, 20, startingPointx, startingPointy);

			}

			// Degats subis par le mob

			if (heroAttack != 0) {
				startingPointx = 1100;
				startingPointy = 400;
				graphics.fillRect(startingPointx, startingPointy - 20, 60, 30);
				drawString(context, "-" + heroAttack + " HP", Color.YELLOW, 20, startingPointx, startingPointy);

			}

		});

	}

	public void drawDeathScreen(ApplicationContext context, Player hero, Combat combat) {

	}

	public void drawString(ApplicationContext context, String string, Color color, int taille, int x, int y) {
		context.renderFrame(graphics -> {
			graphics.setFont(new FontUIResource("Arial", 0, taille));
			graphics.setColor(color);
			graphics.drawString(string, x, y);

		});
	}

	public void showEffect(ApplicationContext context, int attaque, String txt, Color color) {
		drawString(context, txt, color, 20, width / 2, 200 + (30 * attaque));

	}

	public void drawMobInCombat(ApplicationContext context, Mobs mob) {
		context.renderFrame(graphics -> {
			graphics.setColor(Color.BLACK);
			graphics.fillRect(900, 490, 300, 250);

			drawString(context, "Statistiques", Color.white, 20, 1000, 510);

			drawString(context, "Dégâts : " + mob.damagePossible(), Color.white, 20, 900, 540);
			drawString(context, "PV : " + mob.health() + "/" + (int) mob.getHealths()[1], Color.white, 20, 900, 570);
			drawString(context, "Défense : " + mob.defensePoints(), Color.white, 20, 900, 600);
			drawString(context, "Contre : " + (int) mob.counterPercent() + "%", Color.white, 20, 900, 630);
			drawString(context, "Esquive : " + (int) mob.evadePercent(), Color.white, 20, 900, 660);
			drawString(context, "Vampirisme : " + (int) mob.vampirismPercent(), Color.white, 20, 900, 690);
			drawString(context, "Vitesse d'attaque : " + mob.speed(), Color.white, 20, 900, 720);

		});
	}

	public void drawOptions(ApplicationContext context) {
		drawString(context, "Sauvegarder données", LoopHeroGameData.TXT_COLOR_BLK, 20, 1400, 930);
		context.renderFrame(graphics -> {
			graphics.drawRect(1400, 910, 200, 30);
			graphics.drawRect(1400, 950, 200, 30);
			graphics.drawRect(1400, 990, 200, 30);

		});

		drawString(context, "Sauvegarder partie", LoopHeroGameData.TXT_COLOR_BLK, 20, 1400, 970);
		drawString(context, "Charger partie", LoopHeroGameData.TXT_COLOR_BLK, 20, 1400, 1010);

	}

	public void drawSelectionPlacement(ApplicationContext context, Card selectedCard, Board plateau) {
		Cell[][] matrice = plateau.getBoardMatrix();
		for (Cell[] liste : matrice) {
			for (Cell cell : liste) {
				if (cell.type() != null && cell.type().allowToPlace(selectedCard)) {
					context.renderFrame(graphics -> {
						graphics.setColor(Color.orange);
						graphics.drawRect(xOrigin + cell.j() * taille, yOrigin + cell.i() * taille, taille, taille);

					});
				}

			}

		}

	}

}
