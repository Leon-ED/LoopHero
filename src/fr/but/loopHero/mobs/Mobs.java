package fr.but.loopHero.mobs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import fr.but.loopHero.droppable.Card;
import fr.but.loopHero.droppable.Droppable;
import fr.but.loopHero.droppable.Rarity;
import fr.but.loopHero.droppable.Ressource;
import fr.but.loopHero.droppable.equipment.Equipement;
import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.graphics.GameGraphics;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.player.CombatEffects;
import fr.umlv.zen5.ApplicationContext;

public abstract class Mobs {
	
	
	private final int percentageOfSpawn;
	private final String name;
	private final  int maxHealth;
	private  int healthPoint;
	private final double strenght;
	private final double speed;
	private  Color color; // a remettre en final quand on aura fait despawn les mobs morts
	private Cell cell;
	private double evadeChance;
	private final double dropChance;
	private final ArrayList<Droppable> MOBS_DROPPABLE_ITEMS;
	
	
	public Mobs(String string, int percentageOfSpawn,int healthPoint,double strenght,double speed,Color color,double dropChance,Cell cell, ArrayList<Droppable> MOBS_DROPPABLE_ITEMS,double evadeChance) {
		this.name = Objects.requireNonNull(string);
		this.percentageOfSpawn = percentageOfSpawn;
		this.maxHealth = (int) ((int) (healthPoint*LoopHeroGameData.LEVEL)*(1+(LoopHeroGameData.LEVEL-1)*0.02));
		this.healthPoint = maxHealth;
		this.strenght = strenght;
		this.speed = speed;
		this.color = Objects.requireNonNull(color);
		this.dropChance = dropChance;
		this.cell = Objects.requireNonNull(cell);
		this.MOBS_DROPPABLE_ITEMS = MOBS_DROPPABLE_ITEMS;
		this.evadeChance = evadeChance;
		
	}
	
	
	public abstract void draw(Graphics2D graphics, int taille);
	
	public abstract void drawInCombat(Graphics2D graphics, int taille);
	
	
	public boolean isDead() {
		if (health() <= 0) { // a supprimer quand on aura fait despawn les mobs morts
			color = Color.red;
		}
		
		
		return healthPoint <=0;
	}
	
	public Color getColor() {
		return color;
	}


	public int takeDamage(int damages, CombatEffects usedSpecialEffect, GameGraphics graphics,ApplicationContext context,int attaque) {
		if(usedSpecialEffect == null) {
			graphics.showEffect(context, attaque, "Le mob a perdu : "+damages+" HP", Color.BLUE.GREEN);
			int realDamages = damages;
			healthPoint -= realDamages;
			return realDamages;
			}	
			
		
		switch (usedSpecialEffect) {
		case Counter -> {
			
			graphics.showEffect(context, attaque, "Le mob contre l'attaque, le héro perds : "+damages+" HP", Color.BLUE);
			return -damages;
			}
		
		case Evade ->{	
		graphics.showEffect(context, attaque, "Le mob a utilisé l'esquive et perds : 0 HP", Color.BLUE);
		return 0;
		}
		case Vampirism ->{
			graphics.showEffect(context, attaque, "Le mob a utilisé le vampirisme et obtient :"+damages+" HP en plus !", Color.BLUE);
			heal(damages);
			return 0;}			
		
		default -> {	
			throw new IllegalArgumentException("Impssible");
		}
	
		}
	}

	
	
	
	public int attack() {
		int lvl = LoopHeroGameData.LEVEL;
		return (int) ((int) (strenght*lvl)*(1+(lvl-1)*0.02));
	}

	

	public int health() {
		// TODO Auto-generated method stub
		return healthPoint;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Mobs))
			return false;
		Mobs mob = (Mobs) o;
		return this.getPos()[0] == mob.getPos()[0] && this.getPos()[1] == mob.getPos()[1] && healthPoint == mob.healthPoint && color.equals(mob.color);
		
	}
	
	public int hashCode() {
		return Objects.hash(cell, color, healthPoint);
		
	}
	
	
	public int[] getPos() {
		int[] pos = new int[2];
		pos[0] = cell.i();
		pos[1] = cell.j();
		return pos;
	}

	public ArrayList<ArrayList<Droppable>> getDroppedItems() {
		ArrayList<ArrayList<Droppable>> toDrop = new ArrayList<ArrayList<Droppable>>();
		toDrop.add(new ArrayList<Droppable>());
		toDrop.add(new ArrayList<Droppable>());
		toDrop.add(new ArrayList<Droppable>());
		
		for(Droppable drop : this.MOBS_DROPPABLE_ITEMS) {
			double d = Math.random() * 100;
			if ((d -= dropChance) < 0) {
    			if (drop instanceof Card)
    				toDrop.get(0).add(drop);
    			if (drop instanceof Equipement)
    				toDrop.get(1).add(drop);
    			if (drop instanceof Ressource)
    				toDrop.get(2).add(drop);
    			
    		}
		}
		return toDrop;
	}
	
	

	public Cell getCurrentCell() {
		return cell;
	}
	
	public void changeCell(Cell cell) {
		this.cell = Objects.requireNonNull(cell);
		}


	public String getName() {
		return name;
	}


	public int[] getHealths() {
		int[] vies = new int[2];
		vies[0] = healthPoint;
		vies[1] = maxHealth;
		
		return vies;
	}


	public void heal(int hp) {
		healthPoint = (healthPoint+hp > maxHealth ? maxHealth : healthPoint+hp);
		
	}


	public CombatEffects useSpecialEffect() {
		Random r = new Random();
		
		
		ArrayList<CombatEffects> liste = new ArrayList<>();
		//liste.add(CombatEffects.Counter);
		liste.add(CombatEffects.Evade);
		
		int chance = r.nextInt(liste.size());
		CombatEffects effet = liste.get(chance);
		
		chance = r.nextInt(99);
		
		switch (effet) {
//		case Counter -> {
//			if(chance < counterPercent*100)
//				return CombatEffects.Counter;
//		}
		
		case Evade ->{
			if(chance < evadeChance*100)
				return CombatEffects.Evade;
		}		

		default -> {
			return null;
		}
				
		}
		return null;
		
		
		
		
		
		
		
	}
	

	
	
}
