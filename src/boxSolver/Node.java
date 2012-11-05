package boxSolver;
import java.util.*;

class Node {
    public Node parent;
    public State state;
    public char move;

    public static int nodesExpanded = 0;

    Node(Node parent, char move, State state) {
	this.parent = parent;
	this.state = state;
	this.move = move;
    }


    LinkedList<Node> expand() {
		LinkedList<Node> childNodes = new LinkedList<Node>();
		nodesExpanded++;
	
		State newState;
		
		newState = state.actionRightN();
		if (newState!=null) childNodes.addLast( new Node(this, 'L', newState ) );
		newState = state.actionLeftN();
		if (newState!=null) childNodes.addLast( new Node(this, 'R', newState ) );	    
		newState = state.actionUpN();
		if (newState!=null) childNodes.addLast( new Node(this, 'D', newState ) );
		newState = state.actionDownN();
		if (newState!=null) childNodes.addLast( new Node(this, 'U', newState ) );
		newState = state.actionRight();
		if (newState!=null) childNodes.addLast( new Node(this, 'L', newState ) );
		newState = state.actionLeft();
		if (newState!=null) childNodes.addLast( new Node(this, 'R', newState ) );	    
		newState = state.actionUp();
		if (newState!=null) childNodes.addLast( new Node(this, 'D', newState ) );
		newState = state.actionDown();
		if (newState!=null) childNodes.addLast( new Node(this, 'U', newState ) );
		
		return(childNodes);
    }
    
    public String printSolution(){
    	if( parent!= null ) {
    	    System.out.print( move );
    	    return move + parent.printSolution();
    	}
    	return "";
    }
    
    @Override
    public boolean equals(Object obj)
    {
    	Node temp = (Node)obj;
    	
    /*	temp.state.printMap(temp.state.map);
    	state.printMap(state.map);
    	System.out.println(state.equalToMap(temp.state.map));*/
    	return state.equalToMap(temp.state.map);
    }

    
 
}