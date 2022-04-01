package fr.but.loopHero.game.graphics;

import java.awt.Graphics2D;

import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.TimeData;
import fr.umlv.zen5.ApplicationContext;

/**
 * The GameView class is in charge of the graphical view of a clicky game.
 */
public interface GameView {
	/**
	 * Draws the game board from its data, using an existing Graphics2D object.
	 * 
	 * @param graphics a Graphics2D object provided by the default method
	 *                 {@code draw(ApplicationContext, GameData)}
	 * @param data     the SimpleGameData containing the game data.
	 * @param timedata    the TimeData containing the game time data.
	 */
	public void draw(Graphics2D graphics, LoopHeroGameData data, TimeData timeData);

	/**
	 * Draws the game board from its data, using an existing
	 * {@code ApplicationContext}.
	 * 
	 * @param context the {@code ApplicationContext} of the game
	 * @param data    the SimpleGameData containing the game data.
	 * @param timedata    the TimeData containing the game time data.
	 */
	public default void draw(ApplicationContext context, LoopHeroGameData data, TimeData timeData) {
		context.renderFrame(graphics -> draw(graphics, data, timeData));
	}

	/**
	 * Draws only the cell specified by the given coordinates in the game board from
	 * its data, using an existing Graphics2D object.
	 * 
	 * @param graphics a Graphics2D object provided by the default method
	 *                 {@code draw(ApplicationContext, GameData)}
	 * @param data     the GameData containing the game data.
	 * @param x        the float x-coordinate of the cell.
	 * @param y        the float y-coordinate of the cell.
	 */
	public void drawOnlyOneCell(Graphics2D graphics, LoopHeroGameData data, int x, int y);

	/**
	 * Draws only the cell specified by the given coordinates in the game board from
	 * its data, using an existing {@code ApplicationContext}.
	 * 
	 * @param context the {@code ApplicationContext} of the game
	 * @param data    the GameData containing the game data.
	 * @param x       the float x-coordinate of the cell.
	 * @param y       the float y-coordinate of the cell.
	 */
	public default void drawOnlyOneCell(ApplicationContext context, LoopHeroGameData data, int x, int y) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, data, x, y));
	}
}
