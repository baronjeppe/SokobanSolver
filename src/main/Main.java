package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		try {
			stdin.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sokobanditRoute = mc.calcRouteForSokobandit(solution, 'u', true, 100); 
		System.out.println("Route for robot: " + sokobanditRoute);
		System.out.println("Length of robot route: " + sokobanditRoute.length());
	}
	
}
