import java.io.*;
import java.util.*;

public class FileReader {
	public static int[][] array;
    public static int numCols, numRows;
    
    public FileReader(){
    	//constructor
    }
    
    /**
     * Reads the contents of a maze stored in a text file into a 2d array. 
     * The text file must be in the following format. 
     * -first line contains the dimensions of the 2d maze 
     * 	as "NumberOfRows [space] NumberOfColumn" ex: 12 5 
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
            while(sc.hasNextLine()){
            	tempLine = sc.nextLine();
            	for(int col = 0; col < numCols; col++){
            		tempChar = tempLine.charAt(col);
            		//wall
            		if(tempChar == '%'){
            			array[lineNum][col] = 1;
            		}
            		//goal
            		else if(tempChar == '.'){
            			array[lineNum][col] = 2;
            		}
            		//starting position
            		else if(tempChar == 'P'){
            			array[lineNum][col] = 3;
            		}
            		//ghost path
            		else if(tempChar == 'g'){
            			array[lineNum][col] = 4;
            		}
            		//ghost starting position
            		else if(tempChar == 'G'){
            			array[lineNum][col] = 5;
            		}
            		//movable empty space
            		else{
            			array[lineNum][col] = 0;
            		}
            	}
            	lineNum++;
            	if(lineNum >=numRows){
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
		//String filename = "./mazes/mediumMaze.txt";
		String filename = "./mazes/test1.txt";
		readFile(filename);
		System.out.println("\tdim1 = " + numCols + "\n\tdim2 = " + numRows);
		
		for(int row = 0; row < numRows; row++){
			for(int col = 0; col < numCols; col++){
				System.out.print(array[row][col]);
			}
			System.out.println();
		}
		
//		Scanner input = new Scanner(System.in);
//        System.out.println("Welcome to File Reader.");
//        System.out.println("What is the name of the data file? ");
//        String file = input.nextLine();
//        readFile(file);
	}

}
