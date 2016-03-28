import java.io.*;
import java.util.*;

public class FileReader {
	public static int[][] array;
	public static int numCols, numRows;

	/**
	 * Constructor
	 */
	public FileReader() {

	}

	/**
	 * Reads the contents of a maze stored in a text file into a 2d array. The
	 * text file must be in the following format. 
	 * -first line contains dimensions of 2D maze as "NumRows [space] NumCols" ex: 12 5 
	 * -'%' for walls 
	 * -'P' for starting location 
	 * -'.' for goal location
	 * @param file
	 */
	public static void readFile(String file) {
		try {
			Scanner sc = new Scanner(new File(file));
			numRows = sc.nextInt();
			numCols = sc.nextInt();
			sc.nextLine();
			array = new int[numRows][numCols];

			int lineNum = 0;
			char tempChar;
			String tempLine;
			while (sc.hasNextLine()) {
				tempLine = sc.nextLine();
				for (int col = 0; col < numCols; col++) {
					tempChar = tempLine.charAt(col);
					switch (tempChar) {
					case '%': // wall
						array[lineNum][col] = 1;
						break;
					case '.': // goal
						array[lineNum][col] = 2;
						break;
					case 'P': // starting position
						array[lineNum][col] = 3;
						break;
					case 'g': // ghost path
						array[lineNum][col] = 4;
						break;
					case 'G': // ghost starting position
						array[lineNum][col] = 5;
						break;
					default: // movable empty space
						array[lineNum][col] = 0;
						break;
					}
				}
				lineNum++;
				if (lineNum >= numRows) {
					break;
				}
			}
			sc.close();
		}

		catch (Exception e) {
			System.out.println("Error: Could not read the specified file.");
		}
	}

	public static void main(String[] args) {
		// String filename = "./mazes/mediumMaze.txt";
		String filename = "./mazes/test1.txt";
		readFile(filename);
		System.out.println("\tdim1 = " + numCols + "\n\tdim2 = " + numRows);

		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				System.out.print(array[row][col]);
			}
			System.out.println();
		}

		// Scanner input = new Scanner(System.in);
		// System.out.println("Welcome to File Reader.");
		// System.out.println("What is the name of the data file? ");
		// String file = input.nextLine();
		// readFile(file);
	}

}
