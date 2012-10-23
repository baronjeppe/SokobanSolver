package main;

import map.MapController;

public class Main {
	
	static MapController mc;
	
	public static void main(String[] args) {
		
		mc = new MapController("mymap.txt");
		
		System.out.println(mc.calcRouteForSokobandit("rrdddlluurulrrrrdlullldddrruulur", 'r', true, 50));
		
	}
	
}
