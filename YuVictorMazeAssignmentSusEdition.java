/**
 * Maze Assignment
 * Author: Victor Yu
 * Date: October 7th
 * Teacher: Mr. Ho
 */
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
class YuVictorMazeAssignmentSusEdition{
  /**
   * This method has a duo functionality it inputs the file into an array, and it will find the start of the maze
   * @param Maze The array that will be filled
   * @param fileName The name of the file
   * @param locationSave The array that will store the location of the start
   */
  public static void fileToArray(char[][] maze, String fileName, int[] locationSave){
    BufferedReader reader = null;
    String strCurrentLine;
    int row = 0; 

    //Fills the file with 
    try{reader = new BufferedReader(new FileReader(fileName));
      while ((strCurrentLine = reader.readLine()) != null) {
        for (int i = 0; i < strCurrentLine.length(); i++) {
          maze[row][i] = strCurrentLine.charAt(i);
          if(0 == Character.compare(strCurrentLine.charAt(i), 'S')){
            locationSave[0] = row;
            locationSave[1] = i;
          }
        }
        row++;
      }
    }
    //Tells you the error when there is one
    catch(IOException e){
      System.out.println("System Error: " + e);
    }
  }

  /**
   * Prints the array
   * @param maze the Array that is to be printed
   */
  public static void printArray(char[][] maze){
    for (int i = 0; i < maze.length; i++) {
       for (int j = 0; j < maze[i].length; j++){
          System.out.print (maze[i][j]);
       }
       System.out.println();
    }
    System.out.println();
  }

  /**
   * Using RECURSION Find the path of the solution if there is any and sets the path in the finished array
   * Checks if the current location is out of bounds
   * Marks the location if it is in bounds and is open
   * 
   * Tries to move in a direction until it is able to 
   * If I can't move anymore it will go back to the last location and look for another location
   * If there is no movement and it has exhausted every movement it returns false
   * 
   * If there is another direction open in the last location it will continue moving from that location
   * When it reaches the end it will start returning true and because it won't move anymore because it's already hit the goal
   * 
   * Sets the current locations for the finished path as '+' in the finished array
   * 
   * @param recurSusSolve This is where the finished maze will be added to
   * @param mazeLayout This is taking in the maze layout and will be used to find the path
   * @param row Starting row 
   * @param column Starting column
   * @param length Length of the Maze
   * @param width Width of the Maze
   * @return This return will return true for open paths false for closed paths 
   *If the return for the overall function is true that means that there was a solution
   *If the return for the overall function is false that means there was no solution
   */
  public static boolean findPath(char[][] recurSusSolve, char[][] mazeLayout, int row, int column, int length, int width){
    boolean moved = false;
    
    if(row < 0 || row > length || column < 0 || column > width){//Checks if it is out of bounds
      return false;
    }


    if('.' == mazeLayout[row][column] || 'G' == mazeLayout[row][column] || 'S' == mazeLayout[row][column]){//Checks if the location is open
      if('G' == mazeLayout[row][column]){//Returns True for the final movement
        return true;
      }
      mazeLayout[row][column] = '*';//Marks the locations that its' been

      //all the movement
      moved = findPath(recurSusSolve, mazeLayout, row-1, column, length, width);//up
      if(!moved){//down
        moved = findPath(recurSusSolve, mazeLayout, row+1, column, length, width);
      }
      if(!moved){//left
        moved = findPath(recurSusSolve, mazeLayout, row, column-1, length, width);
      }
      if(!moved){//right
        moved = findPath(recurSusSolve, mazeLayout, row, column+1, length, width);
      }
    }
    
    if (moved){//If it is finished moving it will put a +
       recurSusSolve[row][column] = 'ඞ';
    }
    return moved;//will return true or false based of if it reaches the end or not
  }



  //------------------------Main Program------------------------\\
  public static void main(String[] args) throws IOException{

    //Setting up 
    Scanner input = new Scanner(System.in);
    BufferedReader reader = null;
    boolean fileChoosing = true;
    String fileName = "";
    String strCurrentLine;
    int width = 0;
    int length = 0;

    //This array will store where the start is
    int[] locationSave = new int[2];


    //------------------------File Input------------------------\\


    //Chossing the file and finding out it's size
    while(fileChoosing){//asks for input
      System.out.println("Find Imposter's trail in map: ");
      fileName = (input.nextLine() +".txt"); 

      //tries to open the file
      try{reader = new BufferedReader(new FileReader(fileName));
        System.out.println("Entering "+ fileName);
        fileChoosing = false;

        //Gets the length and the width of the maze
        while ((strCurrentLine = reader.readLine()) != null) {
          length++;
          width = strCurrentLine.length();
        }
      }
      //If you can't find the file keeps it running
      catch(IOException e){
        System.out.println("Map: " + fileName + ", not found\n");
      }
    }

    //------------------------Array Setup------------------------\\

    //Creating arrays and filling arrays
    char[][] mazeLayout = new char[length][width];
    char[][] recurSusSolve = new char[length][width];

    fileToArray(mazeLayout, fileName, locationSave);
    fileToArray(recurSusSolve, fileName, locationSave);



    //------------------------Maze Path Finding------------------------\\


    //Printing out the maze before the solve
    System.out.println("Printing location of Body and Imposter:");
    printArray(mazeLayout);

    //Solving the maze using Recursion function returns true is there is a solution
    if(findPath(recurSusSolve, mazeLayout, locationSave[0], locationSave[1], length-1, width-1)){
      //Brings the S back in to the maze
      recurSusSolve[locationSave[0]][locationSave[1]] = 'S';
      //Printing out the maze the solve
      System.out.println("There is an imposter among us \nPrinting out the Sus path:");
      printArray(recurSusSolve);
    }


    else{//If the is no solution
      System.out.println("There is no imposter among us");
    }

    //closes the open readers
    input.close();
    reader.close();
  }
}
