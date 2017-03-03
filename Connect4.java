/**
 * This code was created by Joseph Davis and submitted on 03/01/2017 to Dr. Larry
 * Thomas for EECS 1510 - Intro to Object-Oriented Programming. The assignment was 
 * to create a console-based version of the popular board game, Connect 4. Connect
 * 4 a is a two-player game in which the players take turns dropping colored-disks 
 * into a six row by seven column grid. These colored disks [red (R) for Player 1 
 * and yellow (Y) for Player 2] fall to the bottom-most row in each column represe-
 * nting gravity. The goal is simple: place the disks in such a manner that yields 
 * a combination of four in a row. That combination can be achieved horizontally, 
 * vertically, or diagonally. After each successful drop of the disk, the program 
 * will run its boolean checks to determine if the most recent drop has resulted in 
 * a win (of any type) or draw (the board is full with no winner). If the most 
 * recent move has not resulted in a win or draw, then the console will switch pla-
 * yers and prompt for their column selection.
 */

import java.util.Scanner; // Imports the scanner from Java's Utility package

public class Connect4

{
	public static void main(String[] args)
	{
		char[][] board = new char[6][7]; // Create a 2-D array of size 6x7
		displayBoard(board);  //
		switchPlayer(board); // Two methods utilizing the main method's void return type
		
	}
	/**This next method will establish the header & footer, parameters, the
	 * respective rows and columns of my 2-D array, and the counting method I used
	 * to get my row numbers to decrease in a descending order. I know that I have
	 * 6 rows, but I want my 0 row on the bottom not the top (as Java begins its
	 * count at 0 and increments by one as you move down each row). So in order to 
	 * get what I need, I took my board length, which I know to be 6, minus 1 and 
	 * assigned that value to my count variable. With each iteration, my for loop 
	 * will post-decrement my count, so my row numbers will decrease in value after
	 * they have been printed to the left of column 0. 
	 */
	public static void displayBoard(char[][] board)
	{
		System.out.println("  +-+-+-+-+-+-+-+"); // Displays header
		for (int i = 0, count = board.length - 1; i < board.length; i++, count--)
		{
			System.out.print(count + " "); // Gives a space after each row number
			for (int j = 0; j < board[i].length; j++) // At each row i, j columns 
				System.out.print("|" + board[i][j]);
			System.out.println("|"); // Creates the right-most edge of my board
		}
		
		System.out.println("  +-+-+-+-+-+-+-+"); // Displays footer
		System.out.println("   0 1 2 3 4 5 6"); // Displays column numbers
		
		
	}
	/**This next method will establish how I will switch players because before I can
	 * even drop a disk, I need to know which player is going first, and establish
	 * the rules of how the respective char disks will be displayed. I will start with
	 * Player 1 being red. I'll initialize the Scanner from the Java utilities so I
	 * can get input from the console, set the game condition of winning to false
	 * (because that is not possible without any moves being made), set my column
	 * position value to 0, and establish the disk color. 
	 */
	public static void switchPlayer(char[][] board)
	{
		Scanner input = new Scanner(System.in); // Creates the scanner
		boolean gameWon = false, redTurn = true; // Game winning condition & how I
												// will know whose turn it is
		int column = 0; // Creates a place to store the position of the disk
		char color; // Allows the disk to be R or Y
		
		while(!gameWon) //Beginning of while loop for while winning conditions have 
						// not yet been satisfied
		{
			if(redTurn) // Prompting Player 1 for their column choice from 0 to 6
			{
				System.out.print("Player 1, your color is RED. Please choose a column"
						+ " 0-6: ");
				color = 'R'; // The char 'R' will signify RED
			}
			else
			{
				System.out.print("Player 2, your color is YELLOW. Please choose a "
						+ "column 0-6: ");
				color = 'Y'; // The char 'Y' will signify YELLOW
			}
			column = input.nextInt(); // Takes the input (integer value from the
									// scanner and places it into the column variable
			if (column < 0 || column > 6) //User chooses a value outside 0 to 6, then
			{							// this if clause initiates and executes
				System.out.println("Yoda says, 'A column from 0 to 6 you must choose.'");
				System.out.println("Please try again:");
			}
			else // If column choice is valid from 0 to 6, then proceed
			{
				if (!dropDisk(board, column, color))
				{
					redTurn = !redTurn; // Switches turns from one player to the next
					displayBoard(board);
					if (isWinner(board, column, color)) // Checks for winning combinations
					{
						gameWon = true;
						System.out.print(color + " has been declared the winner. Hooray!");
					}
					else
						if (checkForDraw(board))
						{
							gameWon = true;
							System.out.print("Alas, no winner has been declared. The game has ended in a tie.");
						}
				}
			}
		}// End while
	input.close(); // Closes my scanner or else I'll throw an error message
	}
	/**This next method will set the ground rules for how a disk will be 'dropped' to
	 * the lowest available row in the respective column (0-6) most recently chosen 
	 * by the user. This 'drop' will represent gravity in our physical world if two
	 * people were playing the tangible game. My for loop is pretty straightforward
	 * in that it takes the board's length, which we know to be 6, subtracts 1,
	 * which yields 5 -- which is our top-most row, if you remember. So while i is 
	 * greater than or equal to zero, we will post-decrement the value of i until we
	 * hit a location that is full or 0, which is the bottom-most row selection of the
	 * 2-D array. If the column is full, then the system prints the messages, which
	 * alert the user of the error and further asks for another column selection.
	 */
	public static boolean dropDisk(char[][] board, int column, char color)
	{
		for (int i = board.length - 1; i >= 0; i--) 
			if (board[i][column] == 0)
			{
				board[i][column] = color;
				return false;
			}
		System.out.println("Danger, Will Robinson! Danger! That column is full.");
		System.out.println("You must choose again, but choose wisely... the fate of the world depends upon it.");
		return true;
	}
	/** This method performs the overarching winning combination checks. I tried
	 * my best to incorporate the other three methods (horizontal, vertical, and 
	 * diagonal) into this one, but my code broke and so did I - but only for a short
	 * amount of time. I will do my best to fuse the aforementioned methods into this one
	 * over Spring Break when I will have some free time to hone my craft.
	 */
	public static boolean isWinner(char[][] board, int column, char color)
	{
		int row = 0;
		for (int i = 0; i < board.length; i++)
			if (board[i][column] != 0)
			{
				row = i;
				break;
			}
		
		if (winHorizontal(board, column, color, row)) // Performs horizontal checks
			return true; // If true is returned, then a win has been found
		
		if (winVertical(board, column, color, row)) 
			return true; 
		
		if (winDiagonal(board, column, color, row)) 
			return true; 
		
		return false;
	}
	/**This method will perform the various horizontal checks to see if a winning
	 * combination of four disks is present. I know this code is very redundant and
	 * I will soon be making necessary revisions to increase the efficiency, but I 
	 * had to leave it like this after I ran into MASSIVE issues today when trying 
	 * to consolidate these extraneous lines of code. The first for loop is a left
	 * check for a horizontal win because it post-decrements each step along the way
	 * iff that color to the left of it is the same as your initial value. If it is,
	 * then perform another post-decrement and so on and so forth until your count is
	 * 4 or you break out of it because the colors were not the same. The second for
	 * loop is a right check performing the same operations as the left-most check,
	 * only this time the column value, i, will be post-incremented iff it passes
	 * the color check to its immediate right.
	 */
	public static boolean winHorizontal(char[][] board, int column, char color, int row)
	{
		int count = 1;
		for (int i = column - 1; i >= 0; i--) // Starting at the initial column position
											// and post-decrement until you hit zero
			if (color == board[row][i]) // Checks to see if the color is the same
				count++;	// Increases my count by 1
			else
				break; // Pulls me out because the colors were not the same
		
		if (count >= 4)		// If your count hits 4, then this will trigger a win
			return true;
		
		for (int i = column + 1; i < board[0].length; i++) //Right check for loop
			if (color == board[row][i]) // Checks color
				count++; // Increases count by 1
			else
				break; // Breaks the loop if color is not the same
		
		if (count >= 4) 
			return true; // Triggers a win
		return false; // Does not satisfy the winning conditions for horizontal
	}
	/**This method will run through the logic of checking for a vertical combination
	 * of four chars of the same color - R or Y. My count starts at 1 because I know 
	 * that my first disk, regardless of color, is counted as one of the four needed
	 * to satisfy a 'connect four'. Further, I know there are 6 rows in this board 
	 * and there is there is no possible way to achieve a vertical connect four
	 * without encountering that fourth row (#3 as I have designated it). If we have 
	 * a count start in the bottom-most row, and if it is a successful connect four,
	 * then it will end in the fourth row (if the 2nd row, then ends in the 5th; and,
	 * lastly, the 3rd row, then ends in the 6th, or top-most, row) <-- This is true
	 * regardless in of which the seven columns you are checking. 
	 */
	public static boolean winVertical(char[][] board, int column, char color, int row)
	{
		int count = 1;
		if((row + 4) <= 6)
			// I only need to run this 3 times because of my constraint of needing 
			// four consecutive, same colored disks and the top-most rows ranging
			// from four to six; hence only 3 times.
			for (int i = row +1; i <= (row + 3); i++) 
				if (color == board[i][column])
					count++;
				else
					break;
		if (count == 4) // If the count hits 4, then we return a true value, which
			return true;				// will trigger a winning output statement
		
		return false; // Not a vertical win
	}
	/** This method will perform the necessary actions to check if a connect four has
	 * been initiated along a diagonal. Again, I know this code is quite redundant (I
	 * will be solving this issue within due time), but for now, please bear with the
	 * repetitive nature of this diagonal check. I will give a brief description about
	 * each of the four various checks before each respective for loop. 
	 */
	public static boolean winDiagonal(char[][] board, int column, char color, int row)
	{
		int count = 1;
		
		/**This first for loop is one that will check down and to the right of any location.
		 * We must remember that Java initializes a 2-D array with the row values
		 * increasing as we move further down; so, from top to bottom we read 0 to 5. 
		 * Remember - I manipulated the output of my board to show the exact opposite.
		 * But, as you can see the post-increment for the i and j values, my respective
		 * rows and columns, will take us down and to the right, checking for a possible
		 * connect four in this manner. Again, there are redundancies in my code from the 
		 * above horizontal and vertical check win methods. 
		 */
		for (int i = row + 1, j = column + 1; i < board.length && j < board[0].length; i++, j++)
			if (color == board[i][j]) // Checks to see if the color in the respective [i][j]
				count++;			// position (down and right) is the same
			else
				break; // If color is not the same, then back out
		
		if (count >= 4)
			return true; // If count hits four, then return true
		
		/**The second for loop performs a check up and to the left from any location on the
		 * board. The first for loop block comment should have provided you with enough 
		 * information to understand what this code is doing. --It is not my aim to come
		 * across in a rude manner but rather eliminate unnecessary verbiage.
		 */
		for (int i = row - 1, j = column - 1; i >= 0 && j >= 0; i--, j--)
			if (color == board[i][j])
				count++;
			else
				break;
		
		if (count >= 4)
			return true;
		
		/** This third for loop performs a check down and to the left along the board
		 * to see if there exists a sequence of four of the same colored disks.
		 */
		for (int i = row + 1, j = column - 1; i < board.length && j >= 0; i++, j--)
			if (color == board[i][j])
				count++;
		
			else
				break;
		
		if (count >= 4)
			return true;
		/**This last for loop performs a check up and to the right along the board
		 * to see if a four in a row has occurred.
		 */
		
		for (int i = row - 1, j = column + 1; i >= 0 && j < board[0].length; i--, j++)
			if (color == board[i][j])
				count++;
			else
				break;
		
		if (count >= 4)
			return true;
		
		return false;
	}
	/**This method will check for a draw, or tie; when the entire board is full with
	 * not having found any four connecting chars of the same type. 
	 */
	public static boolean checkForDraw(char[][] board)
	{
		for (int i = 0; i < board[0].length; i++)
			if (board[0][i] == 0)
				return false;
		return true;
	}
}