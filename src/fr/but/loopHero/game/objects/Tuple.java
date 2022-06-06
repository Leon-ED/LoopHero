package fr.but.loopHero.game.objects;

import java.util.List;

public class Tuple {

	private int i;
	private int j;

	public Tuple(int i, int j) {
		this.i = i;
		this.j = j;
	}
	
	public int i() {
		return i;
	}
	
	public int j() {
		return j;
	}
	

	public static List<Tuple> getAdjactentsPos() {
		List<Tuple> lst = List.of(new Tuple(0,1),new Tuple(1,0),new Tuple(-1,0),new Tuple(0,-1));
		return lst;
	}
	
	public static List<Tuple> getNeighboursPos() {
		List<Tuple> lst = List.of(new Tuple(0,1),new Tuple(1,0),new Tuple(-1,0),new Tuple(0,-1),new Tuple(1,1),new Tuple(1,-1),new Tuple(-1,-1),new Tuple(-1,1));
		return lst;
	}
}
