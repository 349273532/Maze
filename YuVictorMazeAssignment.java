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
class YuVictorMazeAssignment{
  /**
   * This method has a duo functionality it inputs the file into an array, and it will find the start of the maze
   * @param Maze The array that will be filled
   * @param fileName The name of the file
   * @param locationSave The array that will store the location of the start
   */
  public static void fileToArray(char[][] Maze, String fileName, int[] locationSave){
    BufferedReader reader = null;
    String strCurrentLine;
    int row = 0; 

    //Fills the file with 
    try{reader = new BufferedReader(new FileReader(fileName));
      while ((strCurrentLine = reader.readLine()) != null) {
        for (int i = 0; i < strCurrentLine.length(); i++) {
          Maze[row][i] = strCurrentLine.charAt(i);
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
   * Using RECURSION
   * @param finishedMaze This is where the finished maze will be added to
   * @param mazeLayout This is taking in the maze layout and will be used to find the path
   * @param row Starting row 
   * @param column Starting column
   * @param length Length of the Maze
   * @param width Width of the Maze
   * @return This return will return true for open paths false for closed paths 
   *If the return for the overall function is true that means that there was a solution
   *If the return for the overall function is false that means there was no solution
   */
  public static boolean findPath(char[][] finishedMaze, char[][] mazeLayout, int row, int column, int length, int width){
    boolean moved = false;
    
    if(row < 0 || row > length || column < 0 || column > width){//Checks if it is out of bounds
      return false;
    }


    if('.' == mazeLayout[row][column] || 'G' == mazeLayout[row][column] || 'S' == mazeLayout[row][column]){//Checks if the location is open or not
      if('G' == mazeLayout[row][column]){//Returns True for the final movement
        return true;
      }
      mazeLayout[row][column] = '*';//Marks the locations that its' been
      /**
       * All the movement
       * When one reaches the end it will return true and in a cascade all of them will start returning true
       * and adding the path to the finished array
       * causing the overall method to return true
       * 
       * If all the movements are blocked it will return false until it can move 
       * If in all cases it can't move it will return false overall
       */

      moved = findPath(finishedMaze, mazeLayout, row-1, column, length, width);//up
      if(!moved){//down
        moved = findPath(finishedMaze, mazeLayout, row+1, column, length, width);
      }
      if(!moved){//left
        moved = findPath(finishedMaze, mazeLayout, row, column-1, length, width);
      }
      if(!moved){//right
        moved = findPath(finishedMaze, mazeLayout, row, column+1, length, width);
      }
    }
    
    if (moved){//If it is finished moving it will put a +
       finishedMaze[row][column] = '+';
    }
    return moved;
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
      System.out.println("Please enter the name of the maze file that you wish to run?");
      fileName = (input.nextLine() +".txt"); 

      //tries to open the file
      try{reader = new BufferedReader(new FileReader(fileName));
        System.out.println("Now opening "+ fileName);
        fileChoosing = false;

        //Gets the length and the width of the maze
        while ((strCurrentLine = reader.readLine()) != null) {
          length++;
          width = strCurrentLine.length();
        }
      }
      //If you can't find the file keeps it running
      catch(IOException e){
        System.out.println("File: " + fileName + ", not found\n");
      }
    }

    //Creating arrays and filling arrays
    char[][] mazeLayout = new char[length][width];
    char[][] finishedMaze = new char[length][width];

    fileToArray(mazeLayout, fileName, locationSave);
    fileToArray(finishedMaze, fileName, locationSave);



    //------------------------Maze Path Finding------------------------\\


    //Printing out the maze before the program
    System.out.println("Printing the Maze");
    printArray(mazeLayout);

    //Solving the maze using Recursion  
    if(findPath(finishedMaze, mazeLayout, locationSave[0], locationSave[1], length-1, width-1)){
      //Brings the S back in to the maze
      finishedMaze[locationSave[0]][locationSave[1]] = 'S';
      //Printing out the maze after the program
      System.out.println("There is a solution");
      System.out.println("Printing out the Solution");
      printArray(finishedMaze);
    }
    else{//If the is no solution
      System.out.println("There is no solution for this maze");
    }

    //closes the open readers
    input.close();
    reader.close();
  }
}