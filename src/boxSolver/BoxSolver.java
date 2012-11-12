package boxSolver;

import NodeHashing.NodeHashTable;
import boxSolver.Node;

import java.util.LinkedList;
import java.util.PriorityQueue;

import timer.Timer;

import map.Viewer;

public class BoxSolver {

	public BoxSolver() {
		Node.nodesExpanded = 0;
	}
	
	private int[][] makeOriginalMap(int[][] map)
	{
		int[][] ret = new int[map.length][map[0].length];
		for (int i = 0; i<map.length; i++)
			for (int j = 0; j<map[0].length;j++){
				if (map[i][j] == 100)
					ret[i][j] = 2;
				else if (map[i][j] == 10)
					ret[i][j] = 2;
				else if (map[i][j] == 1000)
					ret[i][j] = 100;
				else
					ret[i][j] = map[i][j];
			}
		return ret;
	}
	
	private int[][] reverseMap(int[][] map)
	{
		int[][] ret = new int[map.length][map[0].length];
		for (int i = 0; i<map.length; i++)
			for (int j = 0; j<map[0].length;j++){
				if (map[i][j] == 100)
					ret[i][j] = 1000;
				else if (map[i][j] == 1000)
					ret[i][j] = 100;
				else
					ret[i][j] = map[i][j];
			}
		return ret;
	}
	
	public String solve(int[][] map, int[] mover, Viewer viewer)
	{
		Node.nodesExpanded = 0;
		String ret = new String();
		
		PriorityQueue<Node> openList = new PriorityQueue<Node>();
		
		NodeHashTable closedList = new NodeHashTable();
		NodeHashTable openListHash = new NodeHashTable();
		
		LinkedList<Node> temp = new LinkedList<Node>();

		Timer timer = new Timer();
	    long time = 0;
		
		State.original_map = makeOriginalMap(map);
		State initialState = new State(reverseMap(map), mover);
		
		Node initialNode = new Node(null,'-',initialState);
		
		openList.add(initialNode);
		openListHash.insert(initialNode);
		
		while(true) {
		    if (openList.isEmpty() ) {
		    	System.out.println("No Solution Found\n");
		    	break;
		    }
		    
		    Node currentNode = openList.peek();
		    
		    if (currentNode.state.equalToMap(map)) {
		    	System.out.println("Solution Found\n");
		    	System.out.println("Collisions: " + closedList.getCollisions());
		    	ret = currentNode.printSolution();
		    	
		    	System.out.println("Price: " + currentNode.state.price);
		    	System.out.println("Nodes expanded: " + Integer.toString( Node.nodesExpanded ) );
		    	break;
		    }
		    temp = currentNode.expand();
		    
		    while(!temp.isEmpty()){
		    	if (!openListHash.contains(temp.getFirst()) && !closedList.contains(temp.getFirst())) {
		    		openList.add(temp.getFirst());
		    		openListHash.insert(temp.pollFirst());
		    	}
		    	else {
		    		temp.removeFirst();
		    	}
		    }
		    //System.out.println(Node.nodesExpanded);
	    	//System.out.println("Price: " + currentNode.state.price);
	    	//currentNode.state.printMap(currentNode.state.map);
	    	//System.out.println(closedList.size());
		    //System.out.println(openList.size());
		    /*
		    viewer.updateMap(currentNode.state.map);
		    try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   */
		    if ((timer.timeSinceStartInNS() - time) > 1000000000)
		    {
		    	time += 1000000000;
		    	System.out.println("Time elapsed: " + timer.timeSinceStartInSeconds() + " seconds \t Nodes expanded: " + Node.nodesExpanded + "\tOpenlist: " + openList.size());
		    }
		    
		    if (!closedList.contains(currentNode))
		    	closedList.insert(currentNode);
		    openList.remove(currentNode);
		    openListHash.remove(currentNode);
		}
		return ret;
	}
	
}
