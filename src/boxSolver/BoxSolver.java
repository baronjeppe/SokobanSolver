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
					ret[i][j] = 100;
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
		
		State initialState = new State(reverseMap(map), mover);
		State.original_map = makeOriginalMap(map);
		
		Node initialNode = new Node(null,'-',initialState);
		
		fringe.add(initialNode);
		while(true) {
		    if (fringe.isEmpty() ) {
		    	System.out.println("No Solution Found\n");
		    	break;
		    }

		    Node currentNode = fringe.removeFirst();

		    if (currentNode.state.equalToMap(map)) {
		    	System.out.println("Solution Found\n");
		    	ret = currentNode.printSolution();
		    	System.out.println("Nodes expanded: " + Integer.toString( Node.nodesExpanded ) );
		    	break;
		    }
		    fringe.addAll( currentNode.expand() );
		    //System.out.println(Node.nodesExpanded);
		}
		return ret;
	}
	
}
