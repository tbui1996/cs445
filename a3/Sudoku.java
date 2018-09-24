package cs445.a3;

import java.util.List;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Sudoku 
{
    private static int[][] originalBoard; //stores the original board with no changes
    	

	static boolean isFullSolution(int[][] board) 
    {
		 //if the board can't be extended into a complete solution
        if(reject(board))
        {
        	return false;
        }
		
		for(int j = 0; j < board.length; j++)
        {
        	for(int k = 0; k < board.length; k++)
        	{
        		//if there are any 0's it is not a full solution
        		if(board[j][k] == 0)
        		{
        			return false;
        		}
        	}
        }
        
        return true;
    }

	/**
	 * checks if the board can ever be extended into a complete solution
	 * @param board: a partial solution
	 * @return true if board can be completed, false if not
	 */
    static boolean reject(int[][] board) 
    {
        //Check rows for duplicates
        for (int row = 0; row < board.length; row++) 
        {
            for (int col = 0; col < board[row].length; col++)
            {

                int value = board[row][col];
                if (value != 0) 
                { 
                    for (int nextCol = col + 1; nextCol < board[row].length; nextCol++) 
                    {
                        if (value == board[row][nextCol]) return true;
                    }
                }
            }

            //Check columns for duplicates
            for (int col = 0; col < board[row].length; col++) 
            {
                for (int k = 0; k < board.length; k++) 
                {
                    int value = board[k][col];
                    if (value != 0) 
                    {
                        for (int nextRow = k + 1; nextRow < board.length; nextRow++) 
                        {
                            if (value == board[nextRow][col]) return true;
                        }
                    }
                }
            }
        }
        //Check boxes for duplicates
        //iterating through every box, every first corner happens at index of 0, 3, or 6
        for (int rowBox = 0; rowBox <=6; rowBox += 3) 
        {
            for (int colBox = 0; colBox <= 6; colBox += 3)
            {

                //Look at each square within the 3x3 box
                for (int row = rowBox; row < rowBox + 3; row++) 
                {
                    for (int col = colBox; col < colBox + 3; col++)
                    {
                        int value = board[row][col];
                        if (value != 0) 
                        {

                            //Compare 'value' to every other nonzero element in the 3x3 box
                            for (int r = rowBox; r < rowBox + 3; r++) 
                            {
                                for (int c = colBox; c < colBox + 3; c++) 
                                {
                                    if (r != row || c != col) 
                                    {
                                        if (board[r][c] == value) return true;
                                    }
                                }
                            }
                        }

                    }
                }

            }
        }

        return false;
}
	
	/**
	 * extends a partial solution by adding one more choice
	 * @param board A partial solution
	 * @return partial solution with one more choice added, null if no more choices
	 */
    static int[][] extend(int[][] board) 
    {
        int[][] temp = new int[board.length][board.length];
        boolean extended = false;
        for(int row = 0; row < board.length; row++)
        {
        	for(int col = 0; col < board[row].length; col++)
        	{
        		//if the current index is not zero or the board has already been extended
        		if(board[row][col] != 0 || extended == true)
        		{
        			temp[row][col] = board[row][col];
        		}
        		//if the current index is not zero and the board has not been extended
        		else if(board[row][col] == 0)
        		{
        			temp[row][col] = 1;
        			extended = true;
        		}
        	}
        }
        if(extended == true)
        {
        	return temp;
        }
        else
        {
        	return null;
        }
        
    }

    /**
     * adds the most recent choice that has been changed to its next option
     * @param board A partial solution
     * @return partial solution with most recently changed choice, null if there are no more options
     */
    static int[][] next(int[][] board)
    {	
    	int[][] temp = new int[board.length][board.length];
    	boolean increment = false;
    	for(int row = board.length - 1; row >= 0; row--)
    	{
    		for(int col = board.length - 1; col >= 0; col--)
    		{
    			if(increment == false)
    			{
    				//if the original board index is not the same as the new board, increment
    				if(originalBoard[row][col] != board[row][col])
    				{
    					int value = board[row][col];
    					//if the index is less than 9, increment by 1
    					if(value < 9)
    					{
    						temp[row][col] = value + 1;
    						increment = true;
    					}
    					else
    					{
    						return null;
    					}
    				}	
    				//if the current index of the original board is filled, copy it to the new board
    				else if(originalBoard[row][col] != 0)
    				{
    					int value = originalBoard[row][col];
    					temp[row][col] = value;
    				}
    			}
    			else
    			{
    				int value = board[row][col];
    				temp[row][col] = value;
    			}
    		}
    	}
    	return temp;
    }
    

    /**
     * testing if the solution is full using a complete board, and a partial board
     */
    static void testIsFullSolution() 
    {
        int[][] full = readBoard("full.su");
        int[][] notFull = readBoard("notFull.su");
        System.out.println("-------------------------------------------------------");
        System.out.println();
        System.out.println("**TESTING IF FULL SOLUTION**");
        System.out.println();
        
        //testing a board that is full
        System.out.println("using the sample board 'full.su':");
        printBoard(full);
        System.out.println("* 'full.su' is a full board and should return true: " + isFullSolution(full));
        System.out.println();
        
        //testing a board that is not full
        System.out.println("using the sample board 'notFull.su':");
        printBoard(notFull);
        System.out.println();
        System.out.println("* 'notFull.su' is not a full board and should return false: " + isFullSolution(notFull));
        System.out.println();
    }

    /**
     * testing if reject method recognizes if a board can be extended into a full solution
     * using a boards with duplicates in the rows, columns, and 3x3 boxes
     */
    static void testReject()
    {
        int[][] horizontalTest = readBoard("rejectHorizontal.su");
        int[][] verticalTest = readBoard("rejectVertical.su");
        int[][] boxTest = readBoard("rejectBox.su");
        System.out.println("-------------------------------------------------------");
        System.out.println();
        System.out.println("**TESTING IF REJECTED**");
        System.out.println();
        
        //testing a board that has a duplicate in one of the rows
        System.out.println("using the sample board 'rejectHorizontal.su' to test for row duplicates: ");
        printBoard(horizontalTest);
        System.out.println();
        System.out.println("* 'rejectHorizontal.su' should return true since there is a duplicate in a row: " + reject(horizontalTest));
        System.out.println();
        
        //testing a board that has a duplicate in one of the columns
        System.out.println("using the sample board 'rejectVertical.su' to test for column duplicates:");
        printBoard(verticalTest);
        System.out.println();
        System.out.println("* 'rejectVertical.su' should return true since there is a duplicate in a column: " + reject(verticalTest));
        System.out.println();
        
        //testing a board that has a duplicate in one of the 3x3 boxes
        System.out.println("using the sample board 'rejectBox.su' to test for duplicates in the 3x3 box:");
        printBoard(boxTest);
        System.out.println();
        System.out.println("* 'rejectBox.su' should return true since it has a duplicate in a box: " + reject(boxTest));
        System.out.println();
        
    }

    /**
     * testing if the extend method extends a partial solution using a sample board that is a partial solution
     * and one that is a fully complete
     */
    static void testExtend() 
    {
    	int[][] input = readBoard("trivial.su");
        int[][] output = extend(input);
        
        System.out.println("-------------------------------------------------------");
    	System.out.println();
    	System.out.println("**TESTING EXTEND**");
    	System.out.println();

        //testing a board that is not yet complete
    	System.out.println("The original board from the sample 'trivial.su': ");
        printBoard(input);
        System.out.println();

        System.out.println("Extended:");
        printBoard(output);
        System.out.println();
        System.out.println("* Should return with the first empty element extended by 1");

        System.out.println();

        
        //testing a board that is already complete
        int[][] input2 = readBoard("full.su");
        int[][] output2 = extend(input2);

        System.out.println("Input board 2 uses sample board 'full.su' :");
        printBoard(input2);
        System.out.println();

        System.out.println("Output board 2:");
        printBoard(output2);
        System.out.println();
        System.out.println("* Should return null since 'full.su' is an already filled board ");
        System.out.println();
    }

    /**
     * testing if a the next method increments an extended solution correctly using a sample board with
     * a partial solution and one that is fully complete
     */
    static void testNext() 
    {
         originalBoard = readBoard("easy.su");
         int[][] extended = extend(originalBoard);
         System.out.println("-------------------------------------------------------");
         System.out.println();
    	 System.out.println("**TESTING NEXT**");
         System.out.println();
        
         //testing a board that is not yet complete
         System.out.println("Original board using sample board 'easy.su':");
         printBoard(originalBoard);
         System.out.println("Extended board:");
         printBoard(extended);
         System.out.println();
         
         System.out.println("Next Boards incrementing by 1 each iteration: ");
         System.out.println("1.)");
         int[][] next = next(extended);
         printBoard(next);
         for (int i=2; i < 9; i++) {
             System.out.println();
             System.out.println(i + ".)");
             next = next(next);
             printBoard(next);
         }
         System.out.println();
         System.out.println("9.) should return null because the index must be < 9");
         next = next(next);
         printBoard(next);
         System.out.println();

         
         //testing a board that is already completed
         originalBoard = readBoard("full.su");
         System.out.println("Original board using sample board 'full.su': ");
         printBoard(originalBoard);
         next = next(originalBoard);
         System.out.println();
         System.out.println("Next board: ");
         printBoard(next);
         System.out.println("* should be the same as Original Board because the board is already full");
         System.out.println();
 }
    
    static void printBoard(int[][] board)
    {
        if (board == null) 
        {
            System.out.println("No assignment");
            return;
        }
        for (int i = 0; i < 9; i++) 
        {
            if (i == 3 || i == 6) 
            {
                System.out.println("----+-----+----");
            }
            for (int j = 0; j < 9; j++) 
            {
                if (j == 2 || j == 5)
                {
                    System.out.print(board[i][j] + " | ");
                } 
                else 
                {
                    System.out.print(board[i][j]);
                }
            }
            System.out.print("\n");
        }
        System.out.println();
    }

    static int[][] readBoard(String filename) 
    {
        List<String> lines = null;
        try
        {
            lines = Files.readAllLines(Paths.get(filename), Charset.defaultCharset());
        } 
        catch (IOException e)
        {
            return null;
        }
        int[][] board = new int[9][9];
        int val = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) 
            {
                try 
                {
                    val = Integer.parseInt(Character.toString(lines.get(i).charAt(j)));
                } 
                catch (Exception e)
                {
                    val = 0;
                }
                board[i][j] = val;
            }
        }
        return board;
    }

    static int[][] solve(int[][] board)
    {
        if (reject(board)) return null;
        if (isFullSolution(board)) return board;
        int[][] attempt = extend(board);
        while (attempt != null) 
        {
            int[][] solution = solve(attempt);
            if (solution != null) return solution;
            attempt = next(attempt);
        }
        return null;
    }

    public static void main(String[] args)
    {
    	if (args[0].equals("-t")) 
        {
    		testIsFullSolution();
            testReject();
            testExtend();
            testNext();
        }
        else 
        {
            int[][] board = readBoard(args[0]);
            originalBoard = readBoard(args[0]);
            printBoard(solve(board));
        }
    }
}
