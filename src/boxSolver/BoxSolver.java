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
		
		LinkedList<Node> openList = new LinkedList<Node>();
		LinkedList<Node> closedList = new LinkedList<Node>();
		LinkedList<Node> temp = new LinkedList<Node>();

		
		State.original_map = makeOriginalMap(map);
		State initialState = new State(reverseMap(map), mover);
		
		Node initialNode = new Node(null,'-',initialState);
		
		openList.add(initialNode);
		while(true) {
		    if (openList.isEmpty() ) {
		    	System.out.println("No Solution Found\n");
		    	break;
		    }
		    
		    Node currentNode = openList.get(0);
		    
		    if (currentNode.state.equalToMap(map)) {
		    	System.out.println("Solution Found\n");
		    	ret = currentNode.printSolution();
		    	System.out.println("Price: " + currentNode.state.price);
		    	System.out.println("Nodes expanded: " + Integer.toString( Node.nodesExpanded ) );
		    	break;
		    }
		    temp = currentNode.expand();
		    
		    while(!temp.isEmpty()){
		    	boolean placeFound = false;
		    	
		    	if (!openList.contains(temp.getFirst()) && !closedList.contains(temp.getFirst())) {
			    	for(int i = 0; i < openList.size(); i++){
			    		if(temp.getFirst().state.price < openList.get(i).state.price){
			    			openList.add(i,temp.pollFirst());
			    			placeFound = true;
			    			break;
			    		}
			    	}
			    	if (!placeFound) {
		    			openList.add(temp.pollFirst());
			    	}

		    	}
		    	else {
		    		temp.removeFirst();
		    	}
		    }
		    System.out.println(Node.nodesExpanded);
	    	//System.out.println("Price: " + currentNode.state.price);
	    	//currentNode.state.printMap(currentNode.state.map);
	    	//System.out.println(closedList.size());
		    //System.out.println(openList.size());
		    if (!closedList.contains(currentNode))
		    	closedList.addFirst(currentNode);
		    openList.remove();

		}
		return ret;
	}
	
}
