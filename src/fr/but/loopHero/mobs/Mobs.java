package fr.but.loopHero.mobs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import fr.but.loopHero.droppable.Card;
import fr.but.loopHero.droppable.Droppable;
import fr.but.loopHero.droppable.Equipement;
import fr.but.loopHero.droppable.Ressource;
import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.objects.Cell;

public abstract class Mobs {
	
	
	private final int percentageOfSpawn;
	
	private final  int maxHealth;
	private  int healthPoint;
	private final double strenght;
	private final double speed;
	private  Color color; // a remettre en final quand on aura fait despawn les mobs morts
	private Cell cell;
	
	private final double dropChance;
	private final ArrayList<Droppable> MOBS_DROPPABLE_ITEMS;
	
	
	public Mobs(int percentageOfSpawn,int healthPoint,double strenght,double speed,Color color,double dropChance,Cell cell, ArrayList<Droppable> MOBS_DROPPABLE_ITEMS) {
		this.percentageOfSpawn = percentageOfSpawn;
		this.maxHealth = healthPoint;
		this.healthPoint = healthPoint;
		this.strenght = strenght;
		this.speed = speed;
		this.color = Objects.requireNonNull(color);
		this.dropChance = dropChance;
		this.cell = Objects.requireNonNull(cell);
		this.MOBS_DROPPABLE_ITEMS = MOBS_DROPPABLE_ITEMS;
		
	}
	
	
	public abstract void draw(Graphics2D graphics, int taille);
	
	public boolean isDead() {
		if (health() <= 0) { // a supprimer quand on aura fait despawn les mobs morts
			color = Color.red;
		}
		
		
		return healthPoint <=0;
	}
	
	public Color getColor() {
		return color;
	}


	public void takeDamage(int damages) {
		this.healthPoint -= damages;
		
		
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
    		Random rand = new Random();
    		int rand_number = rand.nextInt(99);
    		if (rand_number < dropChance) {
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

	
	
}
