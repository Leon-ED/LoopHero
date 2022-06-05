package fr.but.loopHero.game;

public class TimeData {
	private long tick = System.currentTimeMillis();
	private long elapsedTotal = 0; 	// elapsed time since creation
	private long elapsedBob = 0; 	// elapsed time since last Bob reset()
	
	
	private final static long COMBAT_ATTACK_COOLDOWN = 2_000;
	private long combatStartTick = 0;
	private long combatLastAttackTick = 0;
	
	private boolean stopped;
	
	public static double DAY_MILLISECONDS = 24_000; //24_000;
	
	public final static int HERO_DELAY_DEFAULT = 1_500;
	public static int HERO_DELAY = 1_500; // 1_500
	
	private double previousTick = 1;
	

	private void tickTock() {
		long tock = System.currentTimeMillis();
		elapsedTotal += tock - tick;
		elapsedBob += tock - tick;
		tick = tock;
	}
	

	public double timeFraction() {
		if (!stopped) {
			tickTock();
		}
		return (elapsedTotal*(HERO_DELAY_DEFAULT/HERO_DELAY) % DAY_MILLISECONDS) / (double) DAY_MILLISECONDS;
	}

	public long elapsedBob() {
		if (stopped) {
			return 0;
		}
		tickTock();
		return elapsedBob;
	}

	public void resetElapsedBob() {
		elapsedBob = 0;
	}

	public boolean stopped() {
		return stopped;
	}

	public void stop() {
		if(stopped) {
			return;
		}
		tickTock();
		stopped = true;
	}

	public void start() {
		stopped = false;
		tick = System.currentTimeMillis();
	}
	
	public boolean isDayPased() {
		double timeFrac = timeFraction();
		if (previousTick > timeFrac) {
			this.previousTick = timeFrac;
				return true;
		}
		this.previousTick = timeFrac;
		return false;
	}

	
	public void startCombat() {
		combatStartTick = tick;
		
	}
	
	public boolean readyToAttack() {
		if(combatLastAttackTick == 0 ||  (System.currentTimeMillis()-combatLastAttackTick >= COMBAT_ATTACK_COOLDOWN)) {
			combatLastAttackTick = System.currentTimeMillis();
			return true;
		}
		return false;
	}


	public void stopCombat(){
		System.out.println("duree = " + (int)((System.currentTimeMillis()-combatStartTick)/1000l));
		combatLastAttackTick = 0;
	}

	public int accelerateTime() {
		HERO_DELAY /= 2;
		
		return (int) HERO_DELAY_DEFAULT/HERO_DELAY;
	}
	
	public int decelerateTime() {
		if (HERO_DELAY<HERO_DELAY_DEFAULT) {
			HERO_DELAY *= 2;
		}
		
		return (int) HERO_DELAY_DEFAULT/HERO_DELAY;
	}
	
}
