import java.util.ArrayList;

/**
 * A grid space in the maze in the format of a node with associated properties
 * useful for the search algorithms.
 * 
 * @author dcyoung
 *
 */
public class TreeNode {
	private int row, col;
	private TreeNode parent;
	private boolean isRoot;
	private boolean isVisited;
	private boolean isWall;
	private boolean isPlayerLoc;
	private boolean isGoal;
	private boolean isGhost;
	private boolean isGhostPath;
	private ArrayList<TreeNode> neighborNodes;
	private float gCost;
	private float hCost;

	TreeNode() {
		// TODO: implement
	}

	TreeNode(int mazeRow, int mazeCol, boolean isRoot, boolean isWall, boolean isPlayerLoc, boolean isGoal,
			boolean isVisited) {
		this.row = mazeRow;
		this.col = mazeCol;
		this.isRoot = isRoot;
		this.isWall = isWall;
		this.isPlayerLoc = isPlayerLoc;
		this.isGoal = isGoal;
		this.isVisited = isVisited;
		this.neighborNodes = new ArrayList<TreeNode>();
		this.isGhost = false;
		this.isGhostPath = false;
	}

	TreeNode(int mazeRow, int mazeCol, boolean isRoot, boolean isWall, boolean isPlayerLoc, boolean isGoal,
			boolean isVisited, boolean isGhost, boolean isGhostPath) {
		this.row = mazeRow;
		this.col = mazeCol;
		this.isRoot = isRoot;
		this.isWall = isWall;
		this.isPlayerLoc = isPlayerLoc;
		this.isGoal = isGoal;
		this.isVisited = isVisited;
		this.neighborNodes = new ArrayList<TreeNode>();
		this.isGhost = isGhost;
		this.isGhostPath = isGhostPath;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}

	public boolean isWall() {
		return isWall;
	}

	public void setWall(boolean isWall) {
		this.isWall = isWall;
	}

	public boolean isPlayerLoc() {
		return isPlayerLoc;
	}

	public void setPlayerLoc(boolean isPlayerLoc) {
		this.isPlayerLoc = isPlayerLoc;
	}

	public boolean isGoal() {
		return isGoal;
	}

	public void setGoal(boolean isGoal) {
		this.isGoal = isGoal;
	}

	public boolean isGhost() {
		return isGhost;
	}

	public void setGhost(boolean isGhost) {
		this.isGhost = isGhost;
	}

	public ArrayList<TreeNode> getNeighborNodes() {
		return neighborNodes;
	}

	public void setNeighborNodes(ArrayList<TreeNode> neighborNodes) {
		this.neighborNodes = neighborNodes;
	}

	public float getfCost() {
		return gCost + hCost;
	}

	public float getgCost() {
		return gCost;
	}

	public void setgCost(float gCost) {
		this.gCost = gCost;
	}

	public float gethCost() {
		return hCost;
	}

	public void sethCost(float hCost) {
		this.hCost = hCost;
	}

	public boolean isGhostPath() {
		return isGhostPath;
	}

	public void setGhostPath(boolean isGhostPath) {
		this.isGhostPath = isGhostPath;
	}

	public String toString() {
		String s = "[Node] row: " + this.row + " col: " + this.col;
		if (this.isGoal) {
			s = s + " (Goal) ";
		}
		if (this.isRoot) {
			s = s + " (Root) ";
		}
		if (this.isWall) {
			s = s + " (Wall) ";
		}
		return s;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
