package fr.but.loopHero.game;

import java.awt.Color;
import java.awt.geom.Point2D;

import fr.but.loopHero.droppable.Card;
import fr.but.loopHero.game.graphics.GameGraphics;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.game.objects.SaveDatas;
import fr.but.loopHero.game.objects.tiles.placedTiles.Rock;
import fr.but.loopHero.player.Player;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;

public class Main {

	private final TimeData loopHeroTimeData = new TimeData();
	private final Board plateau = new Board(12, 21);
	private final GameGraphics loopHeroGraphics = new GameGraphics(50, 50, 1920, 1080, 60, plateau);
	private final Player hero = new Player();
	private final LoopHeroGameData gameData = new LoopHeroGameData();

	private void loopHero(ApplicationContext context) {
		// Boucle principale du jeu
		if(LoopHeroGameData.IS_DED)
			return;
		
		LoopHeroGameData.generateDroppableItems();
		plateau.fill();

		plateau.createLoop(34);
		loopHeroGraphics.drawBoard(plateau, context);
		loopHeroGraphics.drawStaticInventory(context);
		loopHeroGraphics.drawOptions(context);

		while (true) {
 
			if (!loopHeroTimeData.stopped()) {
				loopHeroGraphics.drawHeroInformations(context, hero);
				loopHeroGraphics.drawInventory(context, hero);
				loopHeroGraphics.drawBar(context, 350, loopHeroTimeData.timeFraction(), 0, 0, Color.GREEN, 10);
			}
			Cell heroCurrentCell = plateau.getlistCellsLoop().get(hero.getCurrentCellIndex());
			heroCurrentCell.type().doHeroOnEffect(context, hero, plateau, gameData, heroCurrentCell);
			if (Combat.combatAvailable(heroCurrentCell) && !loopHeroTimeData.stopped()) {
				loopHeroGraphics.drawHero(plateau, context, hero);
				new Combat(hero, heroCurrentCell, context, loopHeroTimeData, gameData, loopHeroGraphics);
				loopHeroGraphics.drawBoard(plateau, context);
			}
			moveHeroAndDraw(context);
			if (loopHeroTimeData.isDayPased()) {
				gameData.doNewDayEffects(context, hero, plateau);
			}

			if (hero.getCurrentCellIndex() == 0) {
				gameData.doNewLoopEffects(context, hero, plateau);
			}
			loopHeroGraphics.drawMobs(context, plateau);
			doEvent(context);
		}
	}

	@SuppressWarnings("incomplete-switch")
	private void doKeyAction(ApplicationContext context, Event event) {
		switch (event.getKey()) {
		case SPACE -> {
			System.out.println("Fin du jeu");
			context.exit(0);
			throw new AssertionError("ne devrait pas arriver");
		}
		case S -> {
			loopHeroTimeData.stop();
			System.out.println("Jeux mis en pause");
		}
		case D -> {
			loopHeroTimeData.start();
			System.out.println("Reprise du jeux");
		}
		case RIGHT -> {
			loopHeroTimeData.accelerateTime();
			loopHeroGraphics.drawSpeedIndicator(context, TimeData.SpeedIndicator);
			System.out.println("Acc�l�ration du temps");
		}
		case LEFT -> {
			loopHeroTimeData.decelerateTime();
			loopHeroGraphics.drawSpeedIndicator(context, TimeData.SpeedIndicator);
			System.out.println("Ralentissement du temps");
		}

		
		}
	}

	private void moveHeroAndDraw(ApplicationContext context) {
		int pos;
		if (loopHeroTimeData.elapsedBob() >= TimeData.HERO_DELAY) {
			pos = hero.addCurrentNumOfCell();
			loopHeroTimeData.resetElapsedBob();
		} else {
			pos = hero.getCurrentCellIndex();
		}

		loopHeroGraphics.drawHero(plateau, context, hero);

		if (pos <= 0)
			pos = plateau.getlistCellsLoop().size();
		loopHeroGraphics.drawOneCell(plateau, context, plateau.getlistCellsLoop().get(pos - 1).i(),
				plateau.getlistCellsLoop().get(pos - 1).j());

	}

	@SuppressWarnings("incomplete-switch")
	private void doEvent(ApplicationContext context) {

		Event event = context.pollOrWaitEvent(TimeData.HERO_DELAY);
		if (event == null) { // no event
			return;
		}
		switch (event.getAction()) {
		case KEY_PRESSED:
			doKeyAction(context, event);
			break;
		case POINTER_DOWN:
			if (loopHeroTimeData.stopped()) {
				doMouseAction(context, event);
			}
			break;
		}
	}

	private void doMouseAction(ApplicationContext context, Event event) {

		Point2D.Float location = event.getLocation();
		int i = loopHeroGraphics.lineFromY(location.y);
		int j = loopHeroGraphics.columnFromX(location.x);

		// Si la s�lection est invalide
		if (i < 0 || j < 0)
			return;

		if (i < 12 && j < 21) {
			// Selection d'un Cell dans le plateau de jeu
			Cell selectedCell = gameData.getSelectedCell();

			if (selectedCell != null) {
				loopHeroGraphics.drawOneCell(plateau, context, selectedCell.i(), selectedCell.j());

			}
			gameData.selectCell(plateau.getBoardMatrix()[i][j]);
			loopHeroGraphics.drawSelection(context, i, j, Color.red);

			if (gameData.getSelectedCard() != null) {
				placeCard(context);

			}

			return;
		}
		if (i == 12) {
			// En dehors de la liste
			if (j >= hero.getInventory().get(0).size())
				return;
			Card selectedCard = gameData.getSelectedCard();

			if (selectedCard != null) {
				System.out.println("Une seule selection possible");
				loopHeroGraphics.drawInventory(context, hero);
				loopHeroGraphics.drawBoard(plateau, context);

			}

			gameData.selectCard(hero.getInventory().get(0).get(j));
			loopHeroGraphics.drawSelectionPlacement(context, gameData.getSelectedCard(), plateau);
			loopHeroGraphics.drawSelection(context, i, j, Color.RED);
			return;
		}

		// Selection dans l'equipement disponible :
		if ((i >= 4 && i <= 6) && (j >= 23 && j <= 26)) {
			int offsetI = 4;
			int offsetJ = 23;
			int index = (i - offsetI) * 4 + (j - offsetJ);

			if (gameData.selectEquipement(hero.getEquipementInventory(), index)) { // Equipement selectionne avec succes
				loopHeroGraphics.drawSelection(context, i, j, Color.RED);
				System.out.println(index);
				System.out.println("Selected : " + gameData.getSelectedEquipement().displayName());
				return;
			}
			// Echec de selection
			System.out.println("Selection invalide");
			return;

		}
		// Si on clique dans les cases pour s'equiper ET qu'on a deja un item
		// selectionne
		if (((i >= 0 && i <= 2) && (j >= 23 && j <= 26)) && gameData.getSelectedEquipement() != null) {
			int offsetJ = 23;
			int offsetI = 0;
			int index = (i - offsetI) * 4 + (j - offsetJ);
			gameData.selectEquipementPlacement(index);
			if (gameData.getSelectedInventoryPlacement() != null) {
				placeEquipement(context, i, j);

			}
		}
		if ((1400 <= location.x && location.x <= 1500) && (910 <= location.y && location.y <= 940)) {
			System.out.println("Sauvegarde");
			new SaveDatas(hero, gameData);
			System.out.println("Sauvegarde effectu�e avec... succ�s !");
		}

	}

	private void placeEquipement(ApplicationContext context, int i, int j) {
		if (gameData.getSelectedEquipement() == null || gameData.getSelectedInventoryPlacement() == null)
			return;
		if (!gameData.canPlaceEquipement()) {
			return;
		}
		loopHeroGraphics.drawEquipement(context, gameData.getSelectedEquipement(), i, j);
		hero.equipEquipement(gameData.getSelectedEquipement());
		loopHeroGraphics.drawHeroInformations(context, hero);
		loopHeroGraphics.drawInventory(context, hero);

		gameData.selectEquipementPlacement(-1);
		gameData.selectEquipementPlacement(-1);

	}

	private void placeCard(ApplicationContext context) {
		if (gameData.getSelectedCard() == null || gameData.getSelectedCell() == null)

			return;

		if (!gameData.canBePlaced()) {
			Cell selectedCell = gameData.getSelectedCell();
			loopHeroGraphics.drawOneCell(plateau, context, selectedCell.i(), selectedCell.j());
			gameData.selectCell(null);
			return;
		}

		Cell selectedCell = gameData.getSelectedCell();
		Card selectedCard = gameData.getSelectedCard();
		int i = selectedCell.i();
		int j = selectedCell.j();

		plateau.getBoardMatrix()[i][j].setType(selectedCard.cardType().generateNew());
		gameData.getSelectedCard().cardType().doEffects(context, hero, plateau, gameData); // On applique l'effet de la
																							// carte pos�e
		if (selectedCard.cardType().equals(new Rock())) // Si rock, on check les voisins pour ajt + de bonus
			Rock.doNeighborsEffects(plateau, selectedCell, hero);
		loopHeroGraphics.drawBoard(plateau, context);

		hero.deleteCardFromInventory(selectedCard);

		// On supprime la s�lection du joueur
		gameData.selectCard(null);
		gameData.selectCell(null);
		loopHeroGraphics.drawInventory(context, hero);
		loopHeroGraphics.drawHeroInformations(context, hero);

	}

	public static void main(String[] args) {
		Main controller = new Main();
		Application.run(Color.WHITE, controller::loopHero);

	}

}