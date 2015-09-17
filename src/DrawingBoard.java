import java.awt.Color;
import java.util.ArrayList;

//import java.awt.color.*;


public class DrawingBoard {
	private MazeState ms;
	
	DrawingBoard(MazeState ms){
		this.ms = ms;
		createCanvas();
	}
	
	public void createCanvas(){
		int nCols = ms.getNumCols();
		int nRows = ms.getNumRows();
		int canvasScale = 200;
		int maxCanvasSize = 900;
		while(nCols*canvasScale > maxCanvasSize || nRows*canvasScale > maxCanvasSize){
			canvasScale = canvasScale - 5;
			if(canvasScale <= 0){
				canvasScale = 1;
				break;
			}
		}
		StdDraw.setCanvasSize(nCols*canvasScale, nRows*canvasScale);
		StdDraw.setXscale(0, nCols);
        StdDraw.setYscale(0, nRows);
	}
	
	public void drawWall(int row, int col){
		StdDraw.setPenRadius();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.filledSquare(col+0.5, (ms.getNumRows()-1-row)+0.5, .45 );
	}
	
	public void drawMazeWalls(){
		for(TreeNode[] gridLine : ms.getState()){
			for(TreeNode n : gridLine){
				if(n.isWall()){
					drawWall(n.getRow(), n.getCol());
				}
			}
		}
	}
	
	public void drawMazeInitialState(){
		drawMazeWalls();
		drawGoal();
		drawStartingPos();
		StdDraw.show(5);
	}
	
	
	public void drawGoal(){
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.setPenRadius(.05);
		StdDraw.point(ms.getGoalNode().getCol()+0.5, (ms.getNumRows()-1-ms.getGoalNode().getRow())+0.5);
	}
	
	public void drawStartingPos(){
		StdDraw.setPenColor(StdDraw.GREEN);
		StdDraw.setPenRadius(.05);
		StdDraw.point(ms.getRootNode().getCol()+0.5, (ms.getNumRows()-1-ms.getRootNode().getRow())+0.5);
	}
	
	
	public void drawVisit(int row, int col, int pauseDuration, Color c){
		StdDraw.setPenRadius();
		StdDraw.setPenColor(c);
		StdDraw.filledSquare(col+0.5, (ms.getNumRows()-1-row)+0.5, .45 );
		StdDraw.show(pauseDuration);
	}
	
	public void drawPath(ArrayList<TreeNode> path, int pauseDuration){
		for ( int i = 0 ; i <path.size() ; i++){
			drawVisit( path.get(i).getRow(), path.get(i).getCol(), pauseDuration, StdDraw.BLUE );
		}
	}
	public void drawPathWithGhost(ArrayList<TreeNode> path, int pauseDuration){
		TreeNode ghost = this.ms.ghostStartNode;
		TreeNode oldGhost = ghost;
		for ( int i = 0 ; i <path.size() ; i++){
			ghost = this.ms.DetermineGhostNode(i);
			this.drawVisit(oldGhost.getRow(), oldGhost.getCol(), 200, StdDraw.WHITE);
			this.drawVisit(ghost.getRow(), ghost.getCol(), 200, StdDraw.CYAN);
			oldGhost = ghost;
			this.drawVisit( path.get(i).getRow(), path.get(i).getCol(), pauseDuration, StdDraw.BLUE );
		}
	}
	
	
	
	public static void main(String[] args) {
		
	}

}
