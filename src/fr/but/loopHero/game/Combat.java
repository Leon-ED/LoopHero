package fr.but.loopHero.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Objects;

import fr.but.loopHero.droppable.Droppable;
import fr.but.loopHero.game.graphics.GameGraphics;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.mobs.Mobs;
import fr.but.loopHero.player.CombatEffects;
import fr.but.loopHero.player.Player;
import fr.umlv.zen5.ApplicationContext;

public class Combat {

	public static boolean combatAvailable(Cell cell) {
		return cell.hasMob();
	}

	private final Player hero;
	private final Cell cell;
	private final Mobs mob;
	private final ArrayList<ArrayList<Droppable>> loot;

	public Combat(Player hero, Cell cell, ApplicationContext context, TimeData timedata, LoopHeroGameData data,
			GameGraphics graphics) {
		this.hero = Objects.requireNonNull(hero);
		this.cell = Objects.requireNonNull(cell);
		this.mob = Objects.requireNonNull(cell.getFirstMob());
		this.loot = Objects.requireNonNull(mob.getDroppedItems());
		initiateCombat(context, timedata, data, graphics);
	}

	private void initiateCombat(ApplicationContext context, TimeData timedata, LoopHeroGameData data,
			GameGraphics graphics) {
		graphics.drawCombat(context, this);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		timedata.resetElapsedBob();
		timedata.startCombat();

		makeCombat(context, timedata, data, graphics);

		if (isHeroWinner()) {
			heroDefeat(context, graphics);
			return;
		}
		heroVictory(context, graphics);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		endCombat(timedata);

	}

	private void makeCombat(ApplicationContext context, TimeData timedata, LoopHeroGameData data,
			GameGraphics graphics) {
		int attaque = 0;
		int attaquant = 0;
		graphics.drawHealthInfos(context, hero.getHealths(), 400, 1300, 450, 30);// Vie du joueur
		graphics.drawHealthInfos(context, mob.getHealths(), 100, 940, 350, 15); // Vie du mob

		while (!(mob.isDead() || hero.isDead())) {

			// Si les 1 secondes sont passées
			if (timedata.readyToAttack()) {
				graphics.drawBar(context, 350, timedata.timeFraction(), 0, 0, Color.GREEN, 10);
				// On dessine le stats du mobs
				graphics.drawMobInCombat(context, mob);
				// Le joueur attaque
				if (attaquant == 0) {
					attaquant = 1;
					int heroAttack = hero.attack();
					CombatEffects usedSpecialEffect = mob.useSpecialEffect();
					int mobRealDamages = mob.takeDamage(Math.abs(heroAttack), usedSpecialEffect, graphics, context,
							attaque);
					graphics.drawHealthInfos(context, mob.getHealths(), 100, 940, 350, 15); // Vie du mob
					graphics.drawDamages(context, 0, mobRealDamages, attaque);

				} else {
					attaquant = 0;
					int mobAttack = mob.attack();
					CombatEffects usedSpecialEffect = hero.useSpecialEffect();
					int heroRealDamages = hero.takeDamage(mobAttack, usedSpecialEffect, graphics, context, attaque);
					if (heroRealDamages < 0) {
						attaque++;
						mob.takeDamage(Math.abs(heroRealDamages), null, graphics, context, attaque);
					}
					graphics.drawDamages(context, heroRealDamages, 0, attaque);
				}
				attaque++;
				graphics.drawHealthInfos(context, hero.getHealths(), 400, 1300, 450, 30);// Vie du joueur
				graphics.drawHealthInfos(context, mob.getHealths(), 100, 940, 350, 15); // Vie du mob

			}
		}
		graphics.drawHealthInfos(context, hero.getHealths(), 400, 1300, 450, 30);// Vie du joueur
		graphics.drawHealthInfos(context, mob.getHealths(), 100, 940, 350, 15); // Vie du mob

	}

	private boolean isHeroWinner() {
		return hero.isDead();
	}

	private void heroVictory(ApplicationContext context, GameGraphics graphics) {
		LoopHeroGameData.KILLED_ENEMIES++;
		hero.addInventory(loot);
		graphics.drawInventory(context, hero);
	}

	private void heroDefeat(ApplicationContext context, GameGraphics graphics) {
		System.out.println("LE HERO EST MORT, VIVE LE HERO");
		graphics.drawDeathScreen(context, hero, this);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		context.exit(0);
	}

	private void endCombat(TimeData timedata) {
		cell.removeMob(mob);
		LoopHeroGameData.IS_DED = true;
		timedata.stopCombat();
	}

	public Mobs getOpponent() {
		return mob;
	}

	public Player getHero() {
		return hero;
	}

}
