package boxSolver;

import boxSolver.Node;

import java.util.LinkedList;

public class BoxSolver {

	public BoxSolver() {

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
					ret[i][j] = 2;
				else
					ret[i][j] = map[i][j];
			}
		return ret;
	}
	
	public String solve(int[][] map, int[] mover)
	{
		String ret = new String();
		
		LinkedList<Node> fringe = new LinkedList<Node>();
		LinkedList<Node> temp = new LinkedList<Node>();

		
		State.original_map = makeOriginalMap(map);
		State initialState = new State(reverseMap(map), mover);
		
		Node initialNode = new Node(null,'-',initialState);
		
		fringe.add(initialNode);
		while(true) {
		    if (fringe.isEmpty() ) {
		    	System.out.println("No Solution Found\n");
		    	break;
		    }
		    
		    //currentNode.state.printMap(currentNode.state.map);
		   // currentNode.printSolution();
		   // System.out.println();
		    
		    /*try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		    Node currentNode = fringe.getFirst();
		    
		    if (currentNode.state.equalToMap(map)) {
		    	System.out.println("Solution Found\n");
		    	ret = currentNode.printSolution();
		    	System.out.println("Nodes expanded: " + Integer.toString( Node.nodesExpanded ) );
		    	break;
		    }
		    temp = currentNode.expand();
		    while(!temp.isEmpty()){
		    	for(int i = 0; i < fringe.size(); i++){
		    		if(temp.getFirst().state.price <= fringe.get(i).state.price){
		    			fringe.add(i,temp.pollFirst());
		    			break;
		    		}
		    	}
		    }
		    System.out.println(fringe.getFirst().state.price);
		    fringe.remove();
		    
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    	
		    //System.out.println(Node.nodesExpanded);
		}
		return ret;
	}
	
}
