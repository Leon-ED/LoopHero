package fr.but.loopHero.game;

import java.awt.Color;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import fr.but.loopHero.droppable.Card;
import fr.but.loopHero.droppable.Droppable;
import fr.but.loopHero.droppable.equipment.Armor;
import fr.but.loopHero.droppable.equipment.Equipement;
import fr.but.loopHero.droppable.equipment.Placement;
import fr.but.loopHero.droppable.equipment.Shield;
import fr.but.loopHero.droppable.equipment.Weapon;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.game.objects.tiles.placedTiles.BattleField;
import fr.but.loopHero.game.objects.tiles.placedTiles.Cemetery;
import fr.but.loopHero.game.objects.tiles.placedTiles.Grove;
import fr.but.loopHero.game.objects.tiles.placedTiles.Meadow;
import fr.but.loopHero.game.objects.tiles.placedTiles.Oblivion;
import fr.but.loopHero.game.objects.tiles.placedTiles.Rock;
import fr.but.loopHero.game.objects.tiles.placedTiles.Ruins;
import fr.but.loopHero.game.objects.tiles.placedTiles.Spider_Coccon;
import fr.but.loopHero.game.objects.tiles.placedTiles.VampireMansion;
import fr.but.loopHero.game.objects.tiles.placedTiles.Village;
import fr.but.loopHero.game.objects.tiles.placedTiles.WheatFields;
import fr.but.loopHero.mobs.Mobs;
import fr.but.loopHero.mobs.Slime;
import fr.but.loopHero.player.Player;
import fr.umlv.zen5.ApplicationContext;

public class LoopHeroGameData {

	public static int LEVEL = 1;
	public static final Color BG_COLOR = Color.WHITE;
	public static final Color TXT_COLOR_BLK = Color.BLACK;
	public static final Color TXT_COLOR_WHT = Color.WHITE;
	public static final Color TXT_COLOR_ERROR = Color.RED;
	public static final Color TXT_COLOR_WARNING = Color.YELLOW;
	public static final int INV_WIDTH = 4;
	public static final int INV_HEIGHT = 3;

	public static final Path GAME_RECAP_PATH = Paths.get("./files/recap.txt");
	public static final Path GAME_SAVE_PATH = Paths.get("./files/save.hero");
	public static int KILLED_ENEMIES = 0;
	public static int LOST_HP = 0;
	public static int USED_ITEMS = 0;
	public static int MOVED_SQUARES = 0;
	public static int USED_CARDS = 0;
	public static long startedTick = System.currentTimeMillis();
	public static int ATTACKS;
	public static int TAKEN_DAMAGES;
	public static int MADE_DAMAGES;

	public static Path BOUCLE_PATH = Paths.get("./files/board.txt");

	public static final ArrayList<Droppable> MOBS_DROPPABLE_ITEMS = new ArrayList<>();
	public static final ArrayList<Mobs> SPAWNABLE_MOBS = new ArrayList<>();

	public static final List<Card> START_CARDS = List.of(new Card("Grove", new Grove()), new Card("Rock", new Rock()),
			new Card("Meadow", new Meadow())

	);
	public static final List<Placement> EQUIPED_EQUIPEMENT_ORDER = List.of(Placement.Weapon, Placement.Empty,
			Placement.Empty, Placement.Empty, Placement.Ring, Placement.Empty, Placement.Empty, Placement.Empty,
			Placement.Shield, Placement.Armor, Placement.Empty, Placement.Empty);

	public static void generateDroppableItems() {
		// Cartes
		MOBS_DROPPABLE_ITEMS.add(new Card("Grove", new Grove()));
		MOBS_DROPPABLE_ITEMS.add(new Card("Rock", new Rock()));
		MOBS_DROPPABLE_ITEMS.add(new Card("Meadow", new Meadow()));
		MOBS_DROPPABLE_ITEMS.add(new Card("Cemetery", new Cemetery()));
		MOBS_DROPPABLE_ITEMS.add(new Card("Vampire Mansion", new VampireMansion()));
		MOBS_DROPPABLE_ITEMS.add(new Card("Spider Coccon", new Spider_Coccon()));
		MOBS_DROPPABLE_ITEMS.add(new Card("BattleField", new BattleField()));
		MOBS_DROPPABLE_ITEMS.add(new Card("Oblivion", new Oblivion()));
		MOBS_DROPPABLE_ITEMS.add(new Card("Village", new Village()));
		MOBS_DROPPABLE_ITEMS.add(new Card("Ruins", new Ruins()));
		MOBS_DROPPABLE_ITEMS.add(new Card("Wheat Fields", new WheatFields()));

		// Equipement
		MOBS_DROPPABLE_ITEMS.add(new Weapon("Epee"));
		MOBS_DROPPABLE_ITEMS.add(new Weapon("Hache"));
		MOBS_DROPPABLE_ITEMS.add(new Weapon("Couteau"));
		MOBS_DROPPABLE_ITEMS.add(new Shield());
		MOBS_DROPPABLE_ITEMS.add(new Armor());
		MOBS_DROPPABLE_ITEMS.add(Equipement.newRing("Anneau"));
		MOBS_DROPPABLE_ITEMS.add(Equipement.newRing("Bague"));
		MOBS_DROPPABLE_ITEMS.add(Equipement.newRing("Anubis"));

	}

	public static void generateMobsList() {
		SPAWNABLE_MOBS.add(new Slime(null));
	}

	public static Cell selectedCell;
	public static boolean IS_DED = false;
	private Card selectedCard;
	private Equipement selectedEquipement;
	private Placement selectedInventoryEquipement;

	public LoopHeroGameData() {
		LoopHeroGameData.selectedCell = null;
		this.selectedCard = null;
		this.selectedEquipement = null;
	}

	public void selectCell(Cell cell) {
		LoopHeroGameData.selectedCell = cell;
	}

	public void selectCard(Droppable droppable) {
		try {
			Card card = (Card) droppable;
			this.selectedCard = card;

		} catch (ClassCastException e) {
			// Ne devrait pas arriver !
			throw new IllegalArgumentException("seulement entrer une carte en paramètres !");
		}

	}

	public Cell getSelectedCell() {
		return selectedCell;
	}

	public Card getSelectedCard() {
		// TODO Auto-generated method stub
		return selectedCard;
	}

	public boolean canBePlaced() {

		if (selectedCard == null || selectedCell == null)
			return false;
		if (selectedCard.displayName().equalsIgnoreCase("OBLIVION"))
			return true;

		return selectedCell.type().allowToPlace(selectedCard);

	}

	public void doNewDayEffects(ApplicationContext context, Player hero, Board plateau) {
		Cell[][] matricePlateau = plateau.getBoardMatrix();

		for (Cell[] cells : matricePlateau) {
			for (Cell cell : cells) {
				cell.type().doNewDayEffects(context, hero, plateau, this, cell);

			}
		}

	}

	public void doNewLoopEffects(ApplicationContext context, Player hero, Board plateau) {
		Cell[][] matricePlateau = plateau.getBoardMatrix();

		for (Cell[] cells : matricePlateau) {
			for (Cell cell : cells) {
				cell.type().doNewLoopEffects(context, hero, plateau, this, cell);

			}
		}

	}

	public boolean selectEquipement(ArrayList<Droppable> liste, int index) {
		try {
			this.selectedEquipement = (Equipement) liste.get(index);
			return true;
		} catch (IndexOutOfBoundsException e) {
			this.selectedEquipement = null;
			return false;
		}
	}

	public Equipement getSelectedEquipement() {
		return selectedEquipement;
	}

	public boolean selectEquipementPlacement(int index) {
		try {
			this.selectedInventoryEquipement = EQUIPED_EQUIPEMENT_ORDER.get(index);
			return true;
		} catch (IndexOutOfBoundsException e) {
			this.selectedInventoryEquipement = null;
			return false;
		}
	}

	public Placement getSelectedInventoryPlacement() {
		return selectedInventoryEquipement;
	}

	public boolean canPlaceEquipement() {
		return selectedEquipement.placement() == getSelectedInventoryPlacement();
	}

}
