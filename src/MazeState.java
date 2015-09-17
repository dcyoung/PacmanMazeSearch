import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MazeState {
	private int numRows;
	private int numCols;
	private TreeNode[][] state;
	TreeNode rootNode;
	TreeNode goalNode;
	TreeNode ghostStartNode;
	TreeNode ghostNode;
	List<TreeNode> ghostPath;
	int ghostStartIndex;
	int ghostPathSize;
	
	private boolean ghostExists;
	
	
	/**
	 * Construct the maze held in a default file
	 */
	public MazeState() {
		String filename = "./mazes/mediumMaze.txt";
		readInitialState(filename);
	}
	/**
	 * Construct the maze held in the file specified
	 * @param filename
	 */
	public MazeState(String filename){
		String f  = "./mazes/" + filename;
		readInitialState(f);
	}
	
	/**
	 * Read the initial state of the maze from the specified file
	 * @param filename
	 */
	public void readInitialState(String filename){
		System.out.println("\t MS: Reading Initial State");
		FileReader fr = new FileReader();
		fr.readFile(filename);
		numRows = fr.numRows;
		numCols = fr.numCols;
		state = new TreeNode[numRows][numCols];
		
		int arrayVal;
        for(int row = 0; row < numRows; row++){
			for(int col = 0; col < numCols; col++){
				arrayVal = fr.array[row][col];
				if(arrayVal == 1){
					//if grid space is a wall
					state[row][col] = new TreeNode(row, col, false, true, false, false, false);
				}else{
					if(arrayVal == 3){
						//starting position
						this.state[row][col] = new TreeNode(row, col, true, false, true, false, false);
						this.rootNode = state[row][col];
					}
					else if(arrayVal == 2){
						//goal
						this.state[row][col] = new TreeNode(row, col, false, false, false, true, false);
						this.goalNode = state[row][col];
					}
					else if(arrayVal == 4){
						//ghostpath
						if(!this.ghostExists){
							this.ghostPath = new ArrayList<TreeNode>();
							this.ghostExists = true;
						}
						this.state[row][col] = new TreeNode(row, col, false, false, false, true, false, false, true);
						this.ghostPath.add(state[row][col]);
					}
					else if(arrayVal == 5){
						//ghost
						if(!this.ghostExists){
							this.ghostPath = new ArrayList<TreeNode>();
							this.ghostExists = true;
						}
						this.state[row][col] = new TreeNode(row, col, false, false, false, true, false, true, true);
						this.ghostPath.add(state[row][col]);
						this.ghostStartNode = state[row][col];
						this.ghostNode = state[row][col];
					}
					else{
						//empty space
						this.state[row][col] = new TreeNode(row, col, false, false, false, false, false);
					}
				}
			}
		}
        //populate the neighbor nodes arraylists for each node
        updateNeighborNodes();
        //setup the ghost variables
        if(this.ghostExists){
        	this.SetupGhostBehavior();
        }
	}

	/**
	 * Populate the neighborNodes ArrayList for each node with any adjacent nodes that are not walls
	 */
	private void updateNeighborNodes() {
		System.out.println("\t MS: Updating Neighbor Nodes");
	  	
		//if neighboring nodes are only allowed to be up/down or right/left (ie: no diagonals)
		System.out.println("\t NeighboringNodes: Diagonal movement not considered.");
		for(int r = 0; r < numRows; r++){
			for(int c = 0; c < numCols; c++){
				for(int row = r-1; row <= r+1; row++ ){
					for(int col = c-1; col <= c+1; col++ ){
						if( row < 0 || col < 0 || row >= numRows || col >= numCols ){
							//do not add this neighbor... because the it is outside the boundaries of the maze 
						}
						//only consider the up/down and side/side movements, not the diagonals or the current cell itself
						else if( (Math.abs(row-r) + Math.abs(col-c)) == 1 ){
							//check if its a wall
							if(!state[row][col].isWall() ){
								//if the neighboring node is not a wall, add it to the neighbor nodes
								state[r][c].getNeighborNodes().add(state[row][col]);
							}
						}
						
					}
				}
			}
        }
		
		//Uncomment below if neighboring nodes are allowed to be diagonal
		/*
		 System.out.println("\t NeighboringNodes: Diagonal movement considered.");
		 for(int r = 0; r < numRows; r++){
			for(int c = 0; c < numCols; c++){
				for(int row = r-1; row <= r+1; row++ ){
					for(int col = c-1; col <= c+1; col++ ){
						if( (row == r && col == c) || row < 0 || col < 0 || row >= numRows || col >= numCols ){
							//do not add this neighbor... because
						}else{
							if(!state[row][col].isWall() ){
								//if the neighboring node is not a wall, add it to the neighbor nodes
								state[r][c].getNeighborNodes().add(state[row][col]);
							}
						}
						
					}
				}
			}
        }*/
	}
	
	
	public TreeNode DetermineGhostNode(int stepCount) {
		int sizeM1 = this.ghostPathSize-1;
		int n = (this.ghostStartIndex+stepCount) %(2*sizeM1);
		int index;
		if( n <= sizeM1 ){
			//just use it as the index
			index = n;
		}
		else{
			index = 2*sizeM1 - n;
		}
		return this.ghostPath.get(index);
	}
	
	private void SetupGhostBehavior(){
		this.ghostPath.sort(Comparator.comparing(TreeNode::getCol));
		this.ghostStartIndex = this.ghostPath.indexOf(ghostStartNode);
		this.ghostPathSize = this.ghostPath.size();
	}
	
	
	
	
	
	
	
	public int getNumRows() {
		return numRows;
	}
	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}
	public int getNumCols() {
		return numCols;
	}
	public void setNumCols(int numCols) {
		this.numCols = numCols;
	}
	public TreeNode[][] getState() {
		return state;
	}
	public void setState(TreeNode[][] state) {
		this.state = state;
	}
	public TreeNode getRootNode() {
		return rootNode;
	}
	public void setRootNode(TreeNode rootNode) {
		this.rootNode = rootNode;
	}
	public TreeNode getGoalNode() {
		return goalNode;
	}
	public void setGoalNode(TreeNode goalNode) {
		this.goalNode = goalNode;
	}
	public TreeNode getGhostNode() {
		return ghostNode;
	}
	public void setGhostNode(TreeNode ghostNode) {
		this.ghostNode = ghostNode;
	}
	public TreeNode getGhostStartNode() {
		return ghostStartNode;
	}
	public void setGhostStartNode(TreeNode ghostStartNode) {
		this.ghostStartNode = ghostStartNode;
	}
	public boolean isGhostExists() {
		return ghostExists;
	}
	public void setGhostExists(boolean ghostExists) {
		this.ghostExists = ghostExists;
	}
	public List<TreeNode> getGhostPath() {
		return ghostPath;
	}
	public void setGhostPath(List<TreeNode> ghostPath) {
		this.ghostPath = ghostPath;
	}
	
	
	
	
	public static void main(String[] args) {
/*		// TODO Auto-generated method stub
		List<String> filenames = Arrays.asList("bigMaze.txt", "mediumMaze.txt", "openMaze.txt", "tricky1.txt", "bigGhost.txt");
		
		System.out.println("Creating maze state");
		MazeState mazeState = new MazeState(filenames.get(4));
		
		DrawingBoard dB = new DrawingBoard(mazeState);
		dB.drawMazeInitialState();
		dB.drawVisit(mazeState.ghostStartNode.getRow(), mazeState.ghostStartNode.getCol(), 200, StdDraw.RED);
		TreeNode ghost = mazeState.ghostStartNode;
		TreeNode oldGhost = ghost;
		for(int i = 1; i <100; i++){
			
			ghost = mazeState.DetermineGhostNode(i);
			dB.drawVisit(oldGhost.getRow(), oldGhost.getCol(), 200, StdDraw.WHITE);
			dB.drawVisit(ghost.getRow(), ghost.getCol(), 200, StdDraw.RED);
			oldGhost = ghost;
		}*/
	}
	
}
