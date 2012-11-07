package main;

import timer.Timer;
import map.MapController;

public class Main {
	
	static MapController mc;
	
	public static void main(String[] args) {
		
		Timer t = new Timer();
		mc = new MapController("mymap.txt");
		t.restart();
		String test = mc.solve();
        System.out.println("Solution: " + test);
		t.printTimeSinceStartInSeconds();
		System.out.println(mc.calcRouteForSokobandit(test, 'r', true, 50));
	}
	
}
