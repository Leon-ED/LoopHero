package fr.but.loopHero.game;

public class TimeData {
	private long tick = System.currentTimeMillis();
	private long elapsedTotal = 0; 	// elapsed time since creation
	private long elapsedBob = 0; 	// elapsed time since last Bob reset()
	private boolean stopped;
	public final static double DAY_MILLISECONDS = 24_000;
	public final static int HERO_DELAY = 1500;
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
		return (elapsedTotal % DAY_MILLISECONDS) / (double) DAY_MILLISECONDS;
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
	
}
