package boxSolver;

import tools.Astar;

public class State {

    public int map[][];
    int boxHeuristic[][];
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
		boxHeuristic = Astar.calcBoxHeuristic(original_map);
		
   
    }
    
    public State(State orig){
    	
    	map = new int[orig.map.length][orig.map[0].length];
		for (int i = 0; i<orig.map.length; i++)
			for (int j = 0; j<orig.map[0].length;j++)
				map[i][j] = orig.map[i][j];
		
		mover = new int[2];
		mover[0] = orig.mover[0];
		mover[1] = orig.mover[1];
		noOfMoves = orig.noOfMoves + 1;
		boxHeuristic = Astar.calcBoxHeuristic(original_map);
		price = orig.price + 1;

    }
    
    private int calcBoxPrice(int newBoxX, int newBoxY, int boxX, int boxY){
    	return (boxHeuristic[newBoxX][newBoxY] - boxHeuristic[boxX][boxY]);
    }
	
    private int calcMoverPrice(int newBoxX, int newBoxY, int boxX, int boxY){
    	return (boxHeuristic[newBoxX][newBoxY] - boxHeuristic[boxX][boxY]);
    }
    
    private void makeMove(int newX, int newY, boolean moveBox) {
    	map[newX][newY] = 10;
		map[mover[0]][mover[1]] = original_map[mover[0]][mover[1]];
    	// up
    	if ((newY - mover[1]) == -1){
    		if(map[mover[0]][mover[1] + 1] >= 1000 && map[mover[0]][mover[1] + 1] < 10000 && moveBox){
    			map[mover[0]][mover[1]] = map[mover[0]][mover[1] + 1];
    			map[mover[0]][mover[1] + 1] = original_map[mover[0]][mover[1] + 1];
    			boxMoved = true;
    			price += calcBoxPrice(mover[0],mover[1]-1, mover[0],mover[1]);
    		}
    	}	
    	// down
    	if ((newY - mover[1]) == 1){
    		if(map[mover[0]][mover[1] - 1] >= 1000 && map[mover[0]][mover[1] - 1] < 10000 && moveBox){
    			map[mover[0]][mover[1]] = map[mover[0]][mover[1] - 1];
    			map[mover[0]][mover[1] - 1] = original_map[mover[0]][mover[1] - 1];
    			boxMoved = true;
    			price += calcBoxPrice(mover[0],mover[1]+1, mover[0],mover[1]);

    		}
    	}	
    	// left
    	if ((newX - mover[0]) == -1){
    		if(map[mover[0] + 1][mover[1]] >= 1000 && map[mover[0] + 1][mover[1]] < 10000 && moveBox){
    			map[mover[0]][mover[1]] = map[mover[0] + 1][mover[1]];
    			map[mover[0] + 1][mover[1]] = original_map[mover[0] + 1][mover[1]];
    			boxMoved = true;
    			price += calcBoxPrice(mover[0]-1,mover[1], mover[0],mover[1]);

    		}
    	}	
    	// right
    	if ((newX - mover[0]) == 1){
    		if(map[mover[0] - 1][mover[1]] >= 1000 && map[mover[0] - 1][mover[1]] < 10000 && moveBox){
    			map[mover[0]][mover[1]] = map[mover[0] - 1][mover[1]];
    			map[mover[0] - 1][mover[1]] = original_map[mover[0] - 1][mover[1]];
    			boxMoved = true;
    			price += calcBoxPrice(mover[0]+1,mover[1], mover[0],mover[1]);

    		}
    	}	

    	price -= calcMoverPrice(newX,newY,mover[0],mover[1]);
    	
    	mover[0] = newX;
    	mover[1] = newY;
    }
    
    private boolean isMoveLegal(int newX, int newY)
    {
    	if(map[newX][newY] == 2 || ((map[newX][newY]  >= 100 && map[newX][newY] < 999)))
    		return true;
    	return false;
    }

    public State actionUp() {
	if (!isMoveLegal(mover[0],mover[1]-1)) return(null);
	
	State newState = new State( this );
	newState.makeMove(mover[0],mover[1]-1, true);
	return(newState);	    
    }

    public State actionUpN() {
	if (!isMoveLegal(mover[0],mover[1]-1) || map[mover[0]][mover[1]+1] < 1000) return(null);
	State newState = new State( this );
	newState.makeMove(mover[0],mover[1]-1, false);
	return(newState);	    
    }
    
    public State actionDown() {
    if (!isMoveLegal(mover[0],mover[1]+1)) return(null);
	
    State newState = new State( this );
	newState.makeMove(mover[0],mover[1]+1, true);
	return(newState);	    
    }
    
    public State actionDownN() {
	if (!isMoveLegal(mover[0],mover[1]+1) || map[mover[0]][mover[1]-1] < 1000) return(null);
	
	State newState = new State( this );
	newState.makeMove(mover[0],mover[1]+1, false);
	return(newState);	    
    }

    public State actionLeft() {
    if (!isMoveLegal(mover[0]-1,mover[1])) return(null);
	
    State newState = new State( this );
	newState.makeMove(mover[0]-1,mover[1], true);
	return(newState);	    
    }
    
    public State actionLeftN() {
    if (!isMoveLegal(mover[0]-1,mover[1]) || map[mover[0]+1][mover[1]] < 1000) return(null);

    State newState = new State( this );
	newState.makeMove(mover[0]-1,mover[1], false);
	return(newState);	    
    }

    public State actionRight() {
    if (!isMoveLegal(mover[0]+1,mover[1])) return(null);
	
    State newState = new State( this );
	newState.makeMove(mover[0]+1,mover[1], true);
	return(newState);	    
    }
    
    public State actionRightN() {
    if (!isMoveLegal(mover[0]+1,mover[1]) || map[mover[0]-1][mover[1]] < 1000) return(null);
	
    State newState = new State( this );
	newState.makeMove(mover[0]+1,mover[1], false);
	return(newState);	    
    }

    
    public boolean equalToMap(int[][] Map)
    {
    	for (int i = 0; i<map.length; i++)
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
}