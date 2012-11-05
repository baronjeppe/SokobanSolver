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
        t.printTimeSinceStartInSeconds();
		System.out.println(mc.calcRouteForSokobandit(test, 'r', true, 100));
		//kommentaren er en gittest... bare slet

	}
	
}
