import java.util.*;//Imports the data structures this program needs.

public class Main {//Main class.

	public static void main(String[] args) {//Start off the algorithm.
		String[][] Board = new String[][] {//This is the default game board. This is a 2d String array which is 8x8.
			{null,null,null,null,null,null,null,null},
			{null,null,null,null,null,null,null,null},
			{null,null,null,null,null,null,null,null},
			{null,null,null,null,null,null,null,null},
			{null,null,null,null,null,null,null,null},
			{null,null,null,null,null,null,null,null},
			{null,null,null,null,null,null,null,null},
			{null,null,null,null,null,null,null,null}};
			
			Dictionary QueenPositions = new Hashtable();//This is a dictionary which will hold the positions of the queens. Each column is the key and the row is the value.
			for(int col=0; col<8; col++) {//This will simply loop from 0-7, each number representing a column.
				QueenPositions.put(col,9);//This simply sets up each column key. 9 is the default value for a row, which tells the algorithm that the column doesn't contain a queen.
			}	
			int PLAYERROW = 0;//This is a constant which will hold the players chosen row.
			int PLAYERCOLUMN = 0;//This is a constant which will hold the players chosen column.
			Stack<Integer> stack = new Stack<Integer>();//This sets up the stack, which will be used to keep track of which column is being used.
			QueenSorter Sorter = new QueenSorter();//This makes a new instance of QueenSorter.
			int ColInUse = 0;//ColInUse will hold the value of the column which is being used.
			int PossibleRow = 9;//PossibleRow will be set to a free row, or -1 if no rows in the column are free.
			
			Sorter.PrintBoard(Board);//This prints out the board so the player can choose where to put their queen.
			Scanner sc = new Scanner(System.in);//Initiation of Scanner, so the player can input values.
			while(PLAYERROW <1 || PLAYERROW >8) {//Checks if the players input is within the boundaries of the board.
				System.out.println("Type in the desired row for your queen");//Asks player for a row.
				PLAYERROW = sc.nextInt();//This waits for an integer input. 
			}
			while(PLAYERCOLUMN <1 || PLAYERCOLUMN >8) {//Checks if the players input is within the boundaries of the board.
				System.out.println("Type in the desired column for your queen");//Asks player for a column.
				PLAYERCOLUMN = sc.nextInt();//This waits for an integer input.
			}
			sc.close();//Closes Scanner once inputs are gathered. 
			
			Sorter.AddTakenRow(PLAYERROW-1);//Adds the players queen row to taken rows.
			Board = Sorter.AddQueen(PLAYERROW-1, PLAYERCOLUMN-1, Board);//Adds the players queen to the board.
			QueenPositions.put(PLAYERCOLUMN-1,PLAYERROW-1);//Adds the players queen position to the dictionary. (Overwrites the current column key and value).
			for(int col = 0; col < 8; col++) {//This will loop through 0-7, each number representing a column.
				if(col!=(PLAYERCOLUMN-1)) {//Checks if the current column is not the same as the players queen column.
					stack.push(col);//Pushes the free column onto the stack.
					break;//Stops the loop since the objective is complete.
				}
			}
			System.out.println("All possible solutions for this placement:");
			System.out.println();//Prints a new line so the console isn't messy.
			while(!stack.empty()) {//Loop until the stack is empty. The rule is once the first column's queen moves off the board then all solutions have been found. 
				ColInUse = stack.pop();//Pop off the next column to be used.
				if(QueenPositions.get(ColInUse).equals(9)) {//Checks the dictionary to see if the current column doesn't have a queen.
					PossibleRow = Sorter.SearchForSpace(Board,ColInUse,Sorter.FindNextRow());//This will set PossibleRow to a row which is free in the column being used. It gets set to -1 if no rows are available, in which case the column gets popped off the stack and the previous column is used in the next loop.
					if(PossibleRow != -1) {//Checks if PossibleRow is actually an available row. 
						Board = Sorter.AddQueen(PossibleRow, ColInUse, Board);//Adds a queen to the board at the given coordinates.
						Sorter.AddTakenRow(PossibleRow);//Adds the free row to TakenRows.
						QueenPositions.put(ColInUse, PossibleRow);//Adds the new queen's location to the dictionary. 
						stack.push(ColInUse);//Pushes the column back onto the stack, since a queen has been placed. 
						if(stack.size() == 7) {//The stack will only ever be a max of 7 columns (since the player column is always taken). If the stack is full at this point then a solution has been found. 
							Sorter.PrintBoard(Board);//Prints out the found solution. 
						}
						else {//If the stack isn't full yet.
							stack.push(Sorter.FindNextCol(ColInUse,PLAYERCOLUMN-1));//Finds the next available column and pushes it onto the stack to be used next.
						}
					}
				}
				else {//This runs if the dictionary shows the column already has a queen.
					Board[(int)QueenPositions.get(ColInUse)][ColInUse] = null;//Removes the queen from the board. The row is taken from the dictionary. The value returned from the dictionary is of type object, so I had to manually convert it to an integer (values are always a number in this case, so no errors will be thrown).
					Sorter.RemoveTakenRow((int) QueenPositions.get(ColInUse)); //Removes the queen's row from TakenRows, making it available. 
					PossibleRow = Sorter.SearchForSpace(Board, ColInUse,(int) QueenPositions.get(ColInUse)+1);//This will set PossibleRow to a row which is free in the column being used. It gets set to -1 if no rows are available, in which case the column gets popped off the stack and the previous column is used in the next loop. This time, the starting row is the row after the queens old row (This is so that the old row doesn't get chosen again).
					QueenPositions.put(ColInUse, 9);//Removes the queen's location from the dictionary. 
					if(PossibleRow != -1) {//Checks if PossibleRow is actually an available row.
						Board = Sorter.AddQueen(PossibleRow, ColInUse, Board);//Adds a queen to the board at the given coordinates.
						Sorter.AddTakenRow(PossibleRow);//Adds the new free row to TakenRows.
						QueenPositions.put(ColInUse, PossibleRow);//Adds the queen's position to the dictionary.
						stack.push(ColInUse);//Pushes the column back onto the stack since a queen has been placed.
						if(stack.size() == 7) {//The stack will only ever be a max of 7 columns (since the player column is always taken). If the stack is full at this point then a solution has been found.
							Sorter.PrintBoard(Board);//Prints out the found solution.
						}
						else {//If the stack isn't full yet.
							stack.push(Sorter.FindNextCol(ColInUse, PLAYERCOLUMN-1));//Finds the next available column and pushes it onto the stack to be used next.
						}
					}
				}
			}
		}
	}


