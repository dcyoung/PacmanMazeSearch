import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

public class Search {
	MazeState ms;
	DrawingBoard dB;
	int numExpandedNodes;
	/**
	 * Constructor
	 * @param ms
	 */
	Search(MazeState ms){
		this.ms = ms;
		this.numExpandedNodes = 0;
	}
	
	/**
	 * Performs depth first search on a maze state.
	 * @return the goal state node once found, null if never found
	 * Can read the path used to find the goal by using node.getParent()
	 */
	public TreeNode DepthFirstSearch(){
		//DFS uses Stack data structure
		Stack s = new Stack();
		s.push(ms.rootNode);
		VisitNode(ms.rootNode);
		while(!s.isEmpty())
		{
			TreeNode n=(TreeNode)s.peek();
			TreeNode child = getAnUnvisitedChildNode(n);
			if(child!=null)
			{
				VisitNode(child);
				
				child.setParent(n);
				
				if(child.isGoal()){
					return child;
				}
				s.push(child);
			}
			else
			{
				s.pop();
			}
		}
		return null;
	}
	
	/**
	 * Performs breadth first search on a maze state
	 * @return
	 */
	public TreeNode BreadthFirstSearch(){
		//BFS uses Queue data structure
		Queue q=new LinkedList();
		q.add(ms.rootNode);
		
		VisitNode(ms.rootNode);
		
		while(!q.isEmpty())
		{
			TreeNode n=(TreeNode)q.remove();
			TreeNode child = null;
			while( (child = getAnUnvisitedChildNode(n)) != null)
			{
				VisitNode(child);
				if(child.getParent() == null){
					child.setParent(n);
				}
				if(child.isGoal()){
					return child;
				}
				q.add(child);
			}
		}
		return null;
	}
	
	public void UniformCostSearch(){
		
	}
	
	public TreeNode GreedySearch(){
		for(TreeNode[] row: ms.getState()){
			for(TreeNode n : row){
				CalculateDistance(n, ms.getGoalNode(), false);
			}
		}
		
		
		//nodes yet to be evaluated... sorted by FCost (green nodes)
		ArrayList<TreeNode> open = new ArrayList<TreeNode>();
		//nodes already evaluated (red nodes)  
		ArrayList<TreeNode> closed = new ArrayList<TreeNode>();
		
		//initialize the root nodes costs
		ms.getRootNode().setgCost(0);
		ms.getRootNode().sethCost(CalculateDistance(ms.getRootNode(), ms.getGoalNode(), false));
		
		//add the root node to open
		open.add(ms.getRootNode());
		open.sort(Comparator.comparing(TreeNode::gethCost));
		VisitNode(ms.getRootNode());
		
		TreeNode current;
		ArrayList<TreeNode> neighbors;
		
		while(!open.isEmpty()){
			//set current to be the unevaluated node with the lowest F-Cost
			current = open.get(0); 
			for(int i = 1; i < open.size(); i++ ){
				if( open.get(i).gethCost() < current.gethCost()){
					current = open.get(i);
				}else if(open.get(i).gethCost() == current.gethCost()){
					if(open.get(i).getfCost() < current.getfCost()){
						current = open.get(i);
					}
				}
			}
			
			open.remove(current);
			closed.add(current);
			dB.drawVisit(current.getRow(), current.getCol(), 50, StdDraw.RED);
			
			if(current == ms.getGoalNode()){
				return current;
			}
			
			neighbors = current.getNeighborNodes();
			if(neighbors != null && neighbors.size() != 0){
				//for each neighbor of the current node
				for(TreeNode n : neighbors){
					if(closed.contains(n)){
						//do nothing	
					}
					//if the "open" set does not yet contain the neighbor 
					else if(!open.contains(n)){
						//set the fcost of neighbor
						n.setgCost(current.getgCost()+1);
						n.sethCost(CalculateDistance(n,ms.getGoalNode(), false));
						
						//set parent
						n.setParent(current);
						
						//add n to the open list
						open.add(n);
						VisitNode(n);
						
					}
					//or the new hcost to neighbor is shorter
					else if( n.gethCost() > (current.gethCost()+1) ){
						//remove the neighbor from open set in order to change its fcost and reinsert 
						//it in sorted order
						open.remove(n);
						//set the fcost of n
						n.setgCost(current.getgCost()+1);
						n.sethCost(CalculateDistance(n,ms.getGoalNode(), false));
						//set parent
						n.setParent(current);
						//reinsert the neighbor into the open set in the correct order with its new fcost
						open.add(n);
					}
				}
			}
			
		}
		return null;
	}
	
	
	
	
	public TreeNode AStarSearch(){
		//nodes yet to be evaluated... sorted by FCost (green nodes)
		ArrayList<TreeNode> open = new ArrayList<TreeNode>();
		//nodes already evaluated (red nodes)  
		ArrayList<TreeNode> closed = new ArrayList<TreeNode>();
		
		//initialize the root nodes costs
		ms.getRootNode().setgCost(0);
		ms.getRootNode().sethCost(CalculateDistance(ms.getRootNode(), ms.getGoalNode(), false));
		
		//add the root node to open
		open.add(ms.getRootNode());
		open.sort(Comparator.comparing(TreeNode::getfCost));
		VisitNode(ms.getRootNode());
		
		TreeNode current;
		ArrayList<TreeNode> neighbors;
		
		while(!open.isEmpty()){
			//set current to be the unevaluated node with the lowest F-Cost
			current = open.get(0); 
			for(int i = 1; i < open.size(); i++ ){
				if( open.get(i).getfCost() < current.getfCost()){
					current = open.get(i);
				}else if(open.get(i).getfCost() == current.getfCost()){
					if(open.get(i).gethCost() < current.gethCost()){
						current = open.get(i);
					}
				}
			}
			
			open.remove(current);
			closed.add(current);
			dB.drawVisit(current.getRow(), current.getCol(), 50, StdDraw.RED);
			
			if(current == ms.getGoalNode()){
				return current;
			}
			
			neighbors = current.getNeighborNodes();
			if(neighbors != null && neighbors.size() != 0){
				//for each neighbor of the current node
				for(TreeNode n : neighbors){
					if(closed.contains(n)){
						//do nothing	
					}
					//if the "open" set does not yet contain the neighbor 
					else if(!open.contains(n)){
						//set the fcost of neighbor
						n.setgCost(current.getgCost()+1);
						n.sethCost(CalculateDistance(n, ms.getGoalNode(), false));
						
						//set parent
						n.setParent(current);
						
						//add n to the open list
						open.add(n);
						VisitNode(n);
						
					}
					//or the new path to neighbor is shorter
					else if( n.getgCost() > (current.getgCost()+1) ){
						//remove the neighbor from open set in order to change its fcost and reinsert 
						//it in sorted order
						open.remove(n);
						//set the fcost of n
						n.setgCost(current.getgCost()+1);
						n.sethCost(CalculateDistance(n, ms.getGoalNode(), false));
						//set parent
						n.setParent(current);
						//reinsert the neighbor into the open set in the correct order with its new fcost
						open.add(n);
					}
				}
			}
			
		}
		return null;
	}
	
	
	
	
	/**
	 * Calculates the manhattan/euclidian distance between two nodes
	 * @param n1
	 * @param n2
	 * @param useEuclidian - true if euclidian distance is desired, false if manhattan
	 * @return
	 */
	private float CalculateDistance(TreeNode n1, TreeNode n2, boolean useEuclidian) {
		int colDiff, rowDiff;
		colDiff = Math.abs(n1.getCol() - n2.getCol());
		rowDiff = Math.abs(n1.getRow() - n2.getRow());
		if(!useEuclidian){
			return (float) (colDiff+rowDiff);
		}
		else{
			return (float) Math.sqrt( Math.pow(colDiff, 2) + Math.pow(rowDiff, 2) );
		}
	}

	/**
	 * Calculates a custom heuristic that takes into account alot of things
	 * @param n1
	 * @param n2
	 * @return
	 */
	private float CalculateCustomHeuristic(TreeNode n1, TreeNode n2, int turnCost) {
		
		int colDiff, rowDiff;
		colDiff = Math.abs(n1.getCol() - n2.getCol());
		rowDiff = Math.abs(n1.getRow() - n2.getRow());
		float ManhattanDist =  (float) (colDiff+rowDiff);
		int wallCount = 0;
		for(int row = Math.min(n1.getRow(), n2.getRow()) ; row <= Math.max(n1.getRow(), n2.getRow()); row++){
			for(int col = Math.min(n1.getCol(), n2.getCol()) ; col <= Math.max(n1.getCol(), n2.getCol()); col++){
				if(ms.getState()[row][col].isWall()){
					wallCount++;
				}
			}	
		}
		
		float wallDensity = (float) (1.0*wallCount/(1.0*(rowDiff+1)*(colDiff+1)));
				
		return CalculateDistance(n1,n2,false)+turnCost;
		//return 2*CalculateDistance(n1,n2,false)+turnCost;
		//return (float)( turnCost + wallDensity*(n1.getgCost()+CalculateDistance(n1,n2, false)) );
	}
	
	
	public TreeNode AStarSearchPenalizeTurns(int forwardCost, int rotateCost, boolean bUseManhattanDist){
		//nodes yet to be evaluated... sorted by FCost (green nodes)
		ArrayList<TreeNode> open = new ArrayList<TreeNode>();
		//nodes already evaluated (red nodes)  
		ArrayList<TreeNode> closed = new ArrayList<TreeNode>();
		
		//initialize the root nodes costs
		ms.getRootNode().setgCost(0);
		if(bUseManhattanDist)
			ms.getRootNode().sethCost(CalculateDistance(ms.getRootNode(), ms.getGoalNode(), false));
		else
			ms.getRootNode().sethCost(CalculateCustomHeuristic(ms.getRootNode(), ms.getGoalNode(),2));
		
		//add the root node to open
		open.add(ms.getRootNode());
		open.sort(Comparator.comparing(TreeNode::getfCost));
		VisitNode(ms.getRootNode()); 
		
		TreeNode current;
		ArrayList<TreeNode> neighbors;
		
		while(!open.isEmpty()){
			//set current to be the unevaluated node with the lowest F-Cost
			current = open.get(0); 
			for(int i = 1; i < open.size(); i++ ){
				if( open.get(i).getfCost() < current.getfCost()){
					current = open.get(i);
				}else if(open.get(i).getfCost() == current.getfCost()){
					if(open.get(i).gethCost() < current.gethCost()){
						current = open.get(i);
					}
				}
			}
			
			open.remove(current);
			closed.add(current);
			dB.drawVisit(current.getRow(), current.getCol(), 5, StdDraw.RED);
			
			if(current == ms.getGoalNode()){
				return current;
			}
			
			neighbors = current.getNeighborNodes();
			if(neighbors != null && neighbors.size() != 0){
				//for each neighbor of the current node
				for(TreeNode n : neighbors){
					if(closed.contains(n)){
						//do nothing	
					}
					//if the "open" set does not yet contain the neighbor 
					else if(!open.contains(n)){
						//set the fcost of neighbor
						int lastMove = FindOrientation(current, current.getParent());
						int thisMove = FindOrientation(n, current);
						int turnCost = CalculateMoveCost(thisMove, lastMove, forwardCost, rotateCost);
						
						n.setgCost(current.getgCost()+turnCost);
						
						if(bUseManhattanDist)
							n.sethCost(CalculateDistance(n, ms.getGoalNode(), false));
						else
							n.sethCost(CalculateCustomHeuristic(n, ms.getGoalNode(), turnCost));
						
						//set parent
						n.setParent(current);
						
						//add n to the open list
						open.add(n);
						VisitNode(n);
						
					}
					//or the new path to neighbor is shorter
					else if( n.getgCost() > (current.getgCost()+CalculateMoveCost(FindOrientation(n, current), FindOrientation(current, current.getParent()), forwardCost, rotateCost)) ){
						//remove the neighbor from open set in order to change its fcost and reinsert 
						//it in sorted order
						open.remove(n);
						//set the fcost of n
						n.setgCost(current.getgCost()+CalculateMoveCost(FindOrientation(n, current), FindOrientation(current, current.getParent()), forwardCost, rotateCost));
						if(bUseManhattanDist)
							n.sethCost(CalculateDistance(n, ms.getGoalNode(), false));
						else
							n.sethCost(CalculateCustomHeuristic(n, ms.getGoalNode(),CalculateMoveCost(FindOrientation(n, current), FindOrientation(current, current.getParent()), forwardCost, rotateCost)));
						//set parent
						n.setParent(current);
						//reinsert the neighbor into the open set in the correct order with its new fcost
						open.add(n);
					}
				}
			}
			
		}
		return null;
	}
	
	
	private int CalculateMoveCost(int thisMove, int lastMove, int forwardCost, int turnCost) {
		// TODO Auto-generated method stub
		//1 is upward, 2 is downward, 3 is rightward, 4 is leftward
		
		// reversal
		if( ((thisMove + lastMove) == 3) || ((thisMove + lastMove) == 7)){
			return 2*turnCost+forwardCost;
		}
		// same direction (ie: forward)
		else if( thisMove == lastMove){
			return forwardCost;
		}
		//turn
		else{
			return turnCost+forwardCost;
		}
	}

	private int FindOrientation(TreeNode n, TreeNode parent){
		if(parent==null){
			return -1;
		}
		else{
			//was vertical move
			if(parent.getCol() == n.getCol()){
				//upward move
				if(parent.getRow() < n.getRow()){
					return 1;
				}
				//downward move
				else{
					return 2;
				}
			}
			//was horizontal move
			else if(parent.getRow() == n.getRow()){
				//was a rightward move
				if(parent.getCol() < n.getCol()){
					return 3;
				}
				//was a leftward move
				else{
					return 4;
				}
			}
		}
		
		
		return -1;
	}
	
	/**
	 * Visit a node, set the nodes visited flag, draw an indication on the drawing board
	 * and make note of the new visit in the tally of expanded nodes.
	 * @param n
	 */
	public void VisitNode(TreeNode n){
		n.setVisited(true);
		this.numExpandedNodes++;
		this.dB.drawVisit(n.getRow(), n.getCol(), 5, StdDraw.YELLOW);
	}

	/**
	 * Return the first unvisited child node according to the order the 
	 * neighboring nodes were originally added.
	 * @param node
	 * @return
	 */
	private TreeNode getAnUnvisitedChildNode(TreeNode node) {
		if(node.getNeighborNodes()== null || node.getNeighborNodes().size() == 0){
			//No adj nodes at all
			return null;
		}
		for( TreeNode n : node.getNeighborNodes() ){
			if(!n.isVisited()){
				//Found unvisited adj node
				return n;
			}
		}
		//Had adj nodes, but none were unvisited
		return null;
	}
	
	/**
	 * Print the path of nodes that resulted from a search method starting
	 * First node printed is the starting loc, last node printed is the goal
	 * @param path
	 */
	private static void PrintPath(ArrayList<TreeNode> path, boolean bPrintGCost, boolean bPrintHCost, boolean bPrintFCost) {
		for ( int i = 0 ; i <path.size() ; i++){
			System.out.print( path.get( i ).toString() ) ;
			if (bPrintGCost)
				System.out.print( "\tg-cost: "+path.get(i).getgCost()) ;
			if (bPrintHCost)
				System.out.print( "\th-cost: "+ round(path.get(i).gethCost(),1) ) ;
			if (bPrintFCost)
				System.out.print( "\tf-cost: "+ round(path.get(i).getfCost(),1) ) ;
			System.out.print("\n");
		}
	}
	
	private static double round (double value, int precision) {
	    int scale = (int) Math.pow(10, precision);
	    return (double) Math.round(value * scale) / scale;
	}
	
	
	public TreeNode AStarSearchWithGhost(){
		//nodes yet to be evaluated... sorted by FCost (green nodes)
		ArrayList<TreeNode> open = new ArrayList<TreeNode>();
		//nodes already evaluated (red nodes)  
		ArrayList<TreeNode> closed = new ArrayList<TreeNode>();
		
		//initialize the root nodes costs
		ms.getRootNode().setgCost(0);
		ms.getRootNode().sethCost(CalculateDistance(ms.getRootNode(), ms.getGoalNode(), false));
		
		//add the root node to open
		open.add(ms.getRootNode());
		open.sort(Comparator.comparing(TreeNode::getfCost));
		VisitNode(ms.getRootNode()); //draws yellow
		
		TreeNode current = open.get(0);
		TreeNode previous = null;
		ArrayList<TreeNode> neighbors;
		int step;
		boolean bShouldIdle = false;
		while(!open.isEmpty()){
			//set current to be the unevaluated node with the lowest F-Cost
			if(!bShouldIdle){
				current = open.get(0); 
				for(int i = 1; i < open.size(); i++ ){
					if( open.get(i).getfCost() < current.getfCost()){
						current = open.get(i);
					}else if(open.get(i).getfCost() == current.getfCost()){
						if(open.get(i).gethCost() < current.gethCost()){
							current = open.get(i);
						}
					}
				}	
			}
			else{
				current.setgCost(current.getgCost()+1);
			}
			
			step = (int) (current.getgCost());
			
			open.remove(current);
			closed.add(current);
			dB.drawVisit(current.getRow(), current.getCol(), 50, StdDraw.RED);
			
			if(current == ms.getGoalNode()){
				return current;
			}
			
			neighbors = current.getNeighborNodes();
			if(neighbors != null && neighbors.size() != 0){
				//for each neighbor of the current node
				bShouldIdle = false;
				for(TreeNode n : neighbors){
					
					if(closed.contains(n)){
						//do nothing
					}
					//if the "open" set does not yet contain the neighbor 
					else if(!open.contains(n) &&  n != ms.DetermineGhostNode(step+1)){
						//set the fcost of neighbor
						n.setgCost(current.getgCost()+1);
						n.sethCost(CalculateDistance(n, ms.getGoalNode(), false));
						
						//set parent
						n.setParent(current);
						
						//add n to the open list
						open.add(n);
						VisitNode(n); //draws yellow
						bShouldIdle = false;
					}
					//or the new path to neighbor is shorter
					else if( n.getgCost() > (current.getgCost()+1) &&  n != ms.DetermineGhostNode(step+1)){
						//remove the neighbor from open set in order to change its fcost and reinsert 
						//it in sorted order
						open.remove(n);
						//set the fcost of n
						n.setgCost(current.getgCost()+1);
						n.sethCost(CalculateDistance(n, ms.getGoalNode(), false));
						//set parent
						n.setParent(current);
						//reinsert the neighbor into the open set in the correct order with its new fcost
						open.add(n);
						bShouldIdle = false;
					}
					else{
						//collision
						bShouldIdle = true;
					}
				}

			}
		}
		return null;
	}
	

	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> filenames = Arrays.asList("bigMaze.txt", "mediumMaze.txt", "openMaze.txt", "tricky1.txt", "smallGhost.txt", "mediumGhost.txt", "bigGhost.txt", "smallTurns.txt","bigTurns.txt");
		
		System.out.println("Creating maze state");
		MazeState mazeState = new MazeState(filenames.get(6));
		
		Search s = new Search(mazeState);
		
		System.out.println("Creating TestDraw");
		s.dB = new DrawingBoard(mazeState);
		s.dB.drawMazeInitialState();
		
		System.out.println("Starting Search");
		//TreeNode result = s.BreadthFirstSearch();
		//TreeNode result = s.DepthFirstSearch();
		//TreeNode result = s.AStarSearch();
		TreeNode result = s.AStarSearchWithGhost();
		//TreeNode result = s.AStarSearchPenalizeTurns(1,2, true);
		//TreeNode result = s.GreedySearch();
		
		
		if(result == null){
			System.out.println("\t Search Result: No Path Found");
		}
		else{
			System.out.println("\t Search Result: ReadingPath");
			ArrayList<TreeNode> path = new ArrayList<TreeNode>();
			while(result.getParent() != null){
				path.add(0, result);
				result = result.getParent();
			}
			path.add(0, result);
			//System.out.println("\t Search Result: Printing Path");
			//PrintPath(path, true, false, false);
			//PrintPath(path, true, true, true);
			System.out.println("\t Search Result: Number of Expanded Nodes = " + s.numExpandedNodes);
			System.out.println("\t Search Result: Number of Final Path Nodes = " + path.size());
			System.out.println("\t Search Result: Final Path Cost = " + path.get(path.size()-1).getgCost());
			System.out.println("\t Search Result: Drawing Path");
			if(mazeState.isGhostExists()){
				s.dB.drawPathWithGhost(path, 200);
			}else{
				s.dB.drawPath(path, 10);
			}
		}
		
	}


}
