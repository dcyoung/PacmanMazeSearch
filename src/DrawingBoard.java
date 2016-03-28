import java.awt.Color;
import java.util.ArrayList;

/**
 * A class used to draw and animate the state of the maze under various
 * conditions.
 * 
 * @author dcyoung
 *
 */
public class DrawingBoard {
	private MazeState ms;

	DrawingBoard(MazeState ms) {
		this.ms = ms;
		createCanvas();
	}

	public void createCanvas() {
		int nCols = ms.getNumCols();
		int nRows = ms.getNumRows();
		int canvasScale = 200;
		int maxCanvasSize = 900;
		while (nCols * canvasScale > maxCanvasSize || nRows * canvasScale > maxCanvasSize) {
			canvasScale = canvasScale - 5;
			if (canvasScale <= 0) {
				canvasScale = 1;
				break;
			}
		}
		StdDraw.setCanvasSize(nCols * canvasScale, nRows * canvasScale);
		StdDraw.setXscale(0, nCols);
		StdDraw.setYscale(0, nRows);
	}

	public void drawWall(int row, int col) {
		StdDraw.setPenRadius();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.filledSquare(col + 0.5, (ms.getNumRows() - 1 - row) + 0.5, .45);
	}

	public void drawMazeWalls() {
		for (TreeNode[] gridLine : ms.getState()) {
			for (TreeNode n : gridLine) {
				if (n.isWall()) {
					drawWall(n.getRow(), n.getCol());
				}
			}
		}
	}

	public void drawMazeInitialState() {
		drawMazeWalls();
		drawGoal();
		drawStartingPos();
		StdDraw.show(5);
	}

	public void drawGoal() {
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.setPenRadius(.05);
		StdDraw.point(ms.getGoalNode().getCol() + 0.5, (ms.getNumRows() - 1 - ms.getGoalNode().getRow()) + 0.5);
	}

	public void drawStartingPos() {
		StdDraw.setPenColor(StdDraw.GREEN);
		StdDraw.setPenRadius(.05);
		StdDraw.point(ms.getRootNode().getCol() + 0.5, (ms.getNumRows() - 1 - ms.getRootNode().getRow()) + 0.5);
	}

	public void drawVisit(int row, int col, int pauseDuration, Color c) {
		StdDraw.setPenRadius();
		StdDraw.setPenColor(c);
		StdDraw.filledSquare(col + 0.5, (ms.getNumRows() - 1 - row) + 0.5, .45);
		StdDraw.show(pauseDuration);
	}

	public void drawPath(ArrayList<TreeNode> path, int pauseDuration) {
		for (int i = 0; i < path.size(); i++) {
			drawVisit(path.get(i).getRow(), path.get(i).getCol(), pauseDuration, StdDraw.BLUE);
		}
	}

	public void drawPathWithGhost(ArrayList<TreeNode> path, int pauseDuration) {
		StdDraw.clear();
		this.drawMazeInitialState();
		TreeNode ghost = this.ms.ghostStartNode;
		this.drawVisit(ghost.getRow(), ghost.getCol(), 0, StdDraw.CYAN);
		this.drawVisit(this.ms.getRootNode().getRow(), this.ms.getRootNode().getCol(), 1000, StdDraw.GREEN);
		boolean bUseMagenta;
		TreeNode oldGhost = ghost;
		int stepCount = 0;
		for (int i = 0; i < path.size(); i++) {
			int idleSteps = 0;
			if (i == 0)
				idleSteps = (int) path.get(i).getgCost();
			else
				idleSteps = (int) (path.get(i).getgCost() - path.get(i - 1).getgCost());

			bUseMagenta = false;
			while (idleSteps != 0) {
				ghost = this.ms.DetermineGhostNode(stepCount + 1);
				this.drawVisit(oldGhost.getRow(), oldGhost.getCol(), 0, StdDraw.WHITE);
				this.drawVisit(ghost.getRow(), ghost.getCol(), 0, StdDraw.CYAN);
				if (bUseMagenta)
					this.drawVisit(path.get(i).getRow(), path.get(i).getCol(), pauseDuration, StdDraw.MAGENTA);
				else
					this.drawVisit(path.get(i).getRow(), path.get(i).getCol(), pauseDuration, StdDraw.BLUE);
				bUseMagenta = true;
				// System.out.println("Ghost, " + ghost.toString() + " step:" +
				// stepCount + " node " +i);
				oldGhost = ghost;
				stepCount++;
				idleSteps--;
			}
		}
	}

	public static void main(String[] args) {

	}

}
