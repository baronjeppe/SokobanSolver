package main;

import map.MapController;

public class Main {
	
	static MapController mc;
	
	public static void main(String[] args) {
		
		mc = new MapController("mymap.txt");
		String test = mc.solve();
		System.out.println(mc.calcRouteForSokobandit(test, 'r', true, 500));
        //kommentaren er en gittest... bare slet

	}
	
}
