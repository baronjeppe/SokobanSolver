package boxSolver;

import tools.Astar;

class State {

    int map[][];
    int heuristic[][];
    int mover[];
    boolean boxMoved = false;
    int price;
    int noOfMoves;
    
    public static int original_map[][];

    public State() {}
    public State(int[][] Map, int[] Mover) {

    	map = new int[Map.length][Map[0].length];
		for (int i = 0; i<Map.length; i++)
			for (int j = 0; j<Map[0].length;j++)
				map[i][j] = Map[i][j];
		
		mover = new int[2];
		mover[0] = Mover[0];
		mover[1] = Mover[1];
		noOfMoves = 0;
		price = 0;
		Astar astar = new Astar();
		heuristic = astar.calcMoverRoute(original_map);

   
    }
    
    public State(State orig){
    	
    	map = new int[orig.map.length][orig.map[0].length];
		for (int i = 0; i<orig.map.length; i++)
			for (int j = 0; j<orig.map[0].length;j++)
				map[i][j] = orig.map[i][j];
		
		mover = new int[2];
		mover[0] = orig.mover[0];
		mover[1] = orig.mover[1];
		noOfMoves = orig.noOfMoves++;
		Astar astar = new Astar();
		int[] start = new int[2];
		start[0] = 0;start[1] = 0;
		heuristic = astar.calcMoverRoute(original_map);
		price = orig.price++;

    }
    
    private int calcNewBoxPrice(int newBoxX, int newBoxY, int boxX, int boxY){
    	return heuristic[newBoxX][newBoxY] - heuristic[boxX][boxY];
    }
	
    private void makeMove(int newX, int newY) {
    	map[newX][newY] = 10;
		map[mover[0]][mover[1]] = original_map[mover[0]][mover[1]];
    	// up
    	if ((newY - mover[1]) == -1){
    		if(map[mover[0]][mover[1] + 1] >= 1000 && map[mover[0]][mover[1] + 1] < 10000){
    			map[mover[0]][mover[1]] = map[mover[0]][mover[1] + 1];
    			map[mover[0]][mover[1] + 1] = original_map[mover[0]][mover[1] + 1];
    			boxMoved = true;
    			price += calcNewBoxPrice(mover[0],mover[1]+1, mover[0],mover[1]);
    		}
    	}	
    	// down
    	if ((newY - mover[1]) == 1){
    		if(map[mover[0]][mover[1] - 1] >= 1000 && map[mover[0]][mover[1] - 1] < 10000){
    			map[mover[0]][mover[1]] = map[mover[0]][mover[1] - 1];
    			map[mover[0]][mover[1] - 1] = original_map[mover[0]][mover[1] - 1];
    			boxMoved = true;
    			price += calcNewBoxPrice(mover[0],mover[1]-1, mover[0],mover[1]);

    		}
    	}	
    	// left
    	if ((newX - mover[0]) == -1){
    		if(map[mover[0] + 1][mover[1]] >= 1000 && map[mover[0] + 1][mover[1]] < 10000){
    			map[mover[0]][mover[1]] = map[mover[0] + 1][mover[1]];
    			map[mover[0] + 1][mover[1]] = original_map[mover[0] + 1][mover[1]];
    			boxMoved = true;
    			price += calcNewBoxPrice(mover[0]+1,mover[1], mover[0],mover[1]);

    		}
    	}	
    	// right
    	if ((newX - mover[0]) == 1){
    		if(map[mover[0] - 1][mover[1]] >= 1000 && map[mover[0] - 1][mover[1]] < 10000){
    			map[mover[0]][mover[1]] = map[mover[0] - 1][mover[1]];
    			map[mover[0] - 1][mover[1]] = original_map[mover[0] - 1][mover[1]];
    			boxMoved = true;
    			price += calcNewBoxPrice(mover[0]-1,mover[1], mover[0],mover[1]);

    		}
    	}	

    	mover[0] = newX;
    	mover[1] = newY;
    }
    
    private boolean isMoveLegal(int newX, int newY)
    {
    	if(map[newX][newY] == 2 || ((map[newX][newY]  >= 1000 && map[newX][newY] < 9999)))
    		return true;
    	return false;
    }

    public State actionUp() {
	if (!isMoveLegal(mover[0],mover[1]-1)) return(null);
	
	State newState = new State( this );
	newState.makeMove(mover[0],mover[1]-1);
	return(newState);	    
    }

    public State actionDown() {
    if (!isMoveLegal(mover[0],mover[1]+1)) return(null);
	
    State newState = new State( this );
	newState.makeMove(mover[0],mover[1]+1);
	return(newState);	    
    }

    public State actionLeft() {
    if (!isMoveLegal(mover[0]-1,mover[1])) return(null);
	
    State newState = new State( this );
	newState.makeMove(mover[0]-1,mover[1]);
	return(newState);	    
    }

    public State actionRight() {
    if (!isMoveLegal(mover[0]+1,mover[1])) return(null);
	
    State newState = new State( this );
	newState.makeMove(mover[0]+1,mover[1]);
	return(newState);	    
    }
    
    public boolean equalToMap(int[][] Map)
    {
    /*	System.out.println("Original map:");
    	printMap(Map);
    	System.out.println("Node map:");
    	printMap(map);
	*/	for (int i = 0; i<map.length; i++)
			for (int j = 0; j<map[0].length;j++)
				if(Map[i][j] != 100)
					if (Map[i][j] != map[i][j])
						return false;
		return true;
	}
    
	public void printMap(int[][] map){
		for(int i = 0 ; i < map[0].length ; i++){
			for(int j = 0; j < map.length ; j++)
				System.out.print(map[j][i] + " ");
			System.out.println();
		}
		System.out.println();
	}

 /*   public boolean equals( State s ) {
	for(int i=0;i<9;i++) {
	    if (tilePositions[i]!=s.tilePositions[i]) return(false);
	}
	return(true);
    }*/
}