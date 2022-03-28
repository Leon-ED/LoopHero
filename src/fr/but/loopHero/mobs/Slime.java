package fr.but.loopHero.mobs;

import java.awt.Color;

public class Slime implements Mobs{
	private final int percentageOfSpawn;
	private final int healthPoint;
	private final double strenght;
	private final double speed;
	private final Color color;
	
	public Slime() {
		percentageOfSpawn = 5;
		healthPoint = 13;
		strenght = 3.3;
		speed = 0.6;
		color = Color.green;
		
	}
	
	public boolean isDead() {
		return healthPoint <=0;
	}
	
	public Color getColor() {
		return color;
	}
	
}
