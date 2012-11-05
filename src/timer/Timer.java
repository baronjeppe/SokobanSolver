package timer;
public class Timer {
	private long startTime = System.nanoTime();
	
	public Timer () {}
	
	public void restart () {
		startTime = System.nanoTime();
	}
	
	public long timeSinceStartInNS () {
		return System.nanoTime() - startTime;
	}
	
	public float timeSinceStartInMilliSeconds () {
		return (float) this.timeSinceStartInNS() / 1000000;
	}
	public float timeSinceStartInSeconds () {
		return (float) this.timeSinceStartInNS() / 1000000000;
	}
	public void printTimeSinceStartInSeconds () {
		System.out.println("Time elapsed: " + timeSinceStartInSeconds() + " seconds");;
	}
	public void printTimeSinceStartInMilliSeconds () {
		System.out.println("Time elapsed: " + timeSinceStartInMilliSeconds() + " ms");;
	}
	public void printTimeSinceStartInNanoSeconds () {
		System.out.println("Time elapsed: " + timeSinceStartInNS() + " ns");;
	}
}