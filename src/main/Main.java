package main;

import timer.Timer;
import map.MapController;

public class Main {
	
	static MapController mc;
	
	public static void main(String[] args) {
		int repetitions = 10;
		Timer t = new Timer();
		mc = new MapController("mymap4.txt");
		t.restart();
		String solution = new String();
		for (int i = 0; i < repetitions; i++)
		{
			solution = mc.solve();
		}
        System.out.println("Solution: " + solution);
        System.out.println("Path Length: " + solution.length());
		System.out.println("Avg time: " + t.timeSinceStartInSeconds() / repetitions);
		
		String sokobanditRoute = mc.calcRouteForSokobandit(solution, 'r', true, 25); 
		System.out.println("Route for robot: " + sokobanditRoute);
		System.out.println("Length of robot route: " + sokobanditRoute.length());
	}
	
}
