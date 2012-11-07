package main;

import timer.Timer;
import map.MapController;

public class Main {
	
	static MapController mc;
	
	public static void main(String[] args) {
		
		Timer t = new Timer();
		mc = new MapController("mymap5.txt");
		t.restart();
		String solution = mc.solve();
        System.out.println("Solution: " + solution);
        System.out.println("Path Length: " + solution.length());
		t.printTimeSinceStartInSeconds();
		
		String sokobanditRoute = mc.calcRouteForSokobandit(solution, 'r', true, 50); 
		System.out.println("Route for robot: " + sokobanditRoute);
		System.out.println("Length of robot route: " + sokobanditRoute.length());
	}
	
}
