
public class QueenSorter {// Initiation of the sorting class
	int[] TakenRows = new int[] {9,9,9,9,9,9,9,9};//This will hold the rows which are taken. The null value is 9 due to null not being accepted in an integer data structure.
	
	public void PrintBoard(String[][] board) {//This method displays the board on the console.
		System.out.println(" |_1__2__3__4__5__6__7__8_");//This prints out the upper portion of the board
		for(int row = 0; row < 8; row++) {//This loops for every row on the board. It'll never go higher than 8.
			System.out.print((row+1)+"| ");//This prints out the left hand boundary of the board with it's specific row number (i).
			for(int col = 0; col < 8; col++) {//This loops for every column on the board. It'll never go higher than 8.
				if(board[row][col] == "q") {//This checks if there is a queen on the current square.
					System.out.print("Q  ");//This will print a queen on the board if a queen is present.
				}
				else {//If a queen isn't present.
					System.out.print("*  ");//Prints out a * where a queen isn't present.
				}
			}
			System.out.println();//This prints a new line so that the board is structured properly.
		}
		System.out.println();//This prints a new line so that there's a space between different solutions in the console.. 
	}
	
	public int FindNextCol(int PreviousCol, int PLAYERCOLUMN) {//This finds the next available column to place a queen.
		for(int i = PreviousCol+1; i<8; i++) {//This will loop from the previous column + 1 (so that it isn't chosen again) to 7. This is due to the board not being higher than 8 columns.
			if(i != PLAYERCOLUMN) {//This checks if the column isn't the player's column.
				return(i);//This will return the free column
			}
		}
		return(-1);//This is just a statement to satisfy the method. It will trigger if something has gone wrong with the code. It shouldn't trigger.
	}
	
	public int FindNextRow() {//This finds the next available row when called 
		for(int row = 0; row<8; row++) {//This will loop for 8 turns, which will never change. The row variable represents a row.
			if(!RowTaken(row)) {//This if statement checks if the current row is taken or not (RowTaken returns true if the row is taken, so a ! is needed). 
				return(row);//This returns the row which hasn't been taken.
			}
		}
		return(-1);//This is just a statement to satisfy the method. It will trigger if something has gone wrong with the code. It shouldn't trigger.
	}
	
	public void RemoveTakenRow(int row) {//This method will remove a row from TakenRows which has been freed.
		for(int i = 0; i<TakenRows.length; i++) {//This will loop through the TakenRows array.
			if(TakenRows[i] == row) {//This checks if the current item in the TakenRows array is the freed row.
				TakenRows[i] = 9;//This will take out the row and swap it with the default value.
				break;//This will stop the loop since the objective has been complete.
			}
		}
	}
	
	public void AddTakenRow(int row) {//This method adds a row to TakenRows which has been taken.
		for(int i = 0; i<TakenRows.length; i++) {//This will loop through the TakenRows array.
			if(TakenRows[i] == 9) {//This value checks if the current space in the array is free.
				TakenRows[i] = row;//This will place the new row into the array.
				break;//This will stop the loop since the objective has been complete.
			}
		}
	}
	
	private Boolean RowTaken(int row) {//This method checks if a given row is taken.
		for(int i = 0; i<TakenRows.length; i++) {//This will loop through the TakenRows array.
			if(TakenRows[i] == row) {//This will check if the current item in TakenRows is the same as the given row.
				return(true);//If the row is taken then it returns true.
			}
		}
		return(false);//If the loop finishes without returning true then the row is free, so false is returned.
	}
	
	public String[][] AddQueen(int row,int col,String[][] board){//This method simply adds a queen to the board at given coordinates.
		board[row][col] = "q";//Adds a queen to the board at given coordinates.
		return(board);//Returns the new board.
	}
	
	public int SearchForSpace(String[][] board, int col, int row) {//This method checks if the current space is free to use on the board.
		for(int i=row;i<8; i++) {//This will loop from the given row until 7. This won't go any higher since 7 is the board boundary. 
			if(!RowTaken(i)) {//This checks if the current row is taken.
				if(SearchNE(board,col,i)) {//This will search the board for a queen in a North East diagonal line.
					if(SearchNW(board,col,i)) {//This will search the board for a queen in a North West diagonal line.
						if(SearchSE(board,col,i)) {//This will search the board for a queen in a South East diagonal line.
							if(SearchSW(board,col,i)) {//This will search the board for a queen in a South West diagonal line.
								return(i);//If all the conditions are true then this spot is free, so it is returned. 
							}
						}
					}
				}
			}
		}
		return(-1);//This will return if no spots are free in this column. This means that the algorithm has to go back to the previous column to replace the queen. 
	}
	
	private Boolean SearchNE(String[][] board, int col, int row) {//This is a recursive method which searches the board in a North East diagonal line.
		Boolean ans;//Sets the answer variable up. 
		if(col > 7 || row < 0) {//This checks if the given row or column is within the bounds of the board. 
			return(true);//If the boundaries have been reached without finding a queen then true is returned. 
		}
		else if(board[row][col] == "q") {//This checks if the current square is taken by a queen.
			return(false);//If a queen has been found then false is returned.
		}
		else {//If the boundaries haven't been reached and a queen isn't on the current square.
			ans = SearchNE(board,col+1,row-1);//This will recurse to the next diagonal square. ans will equal the return value.
		}
		return(ans);//This will end up either being true or false. 
	}
	
	private Boolean SearchNW(String[][] board, int col, int row) {//This is a recursive method which searches the board in a North West diagonal line.
		Boolean ans;//Sets the answer variable up.
		if(col < 0 || row < 0) {//This checks if the given row or column is within the bounds of the board.
			return(true);//If the boundaries have been reached without finding a queen then true is returned.
		}
		else if(board[row][col] == "q") {//This checks if the current square is taken by a queen.
			return(false);//If a queen has been found then false is returned.
		}
		else {//If the boundaries haven't been reached and a queen isn't on the current square.
			ans = SearchNW(board,col-1,row-1);//This will recurse to the next diagonal square. ans will equal the return value.
		}
		return(ans);//This will end up either being true or false.
	}
	
	private Boolean SearchSE(String[][] board, int col, int row) {//This is a recursive method which searches the board in a South East diagonal line.
		Boolean ans;//Sets the answer variable up.
		if(col > 7 || row > 7) {//This checks if the given row or column is within the bounds of the board.
			return(true);//If the boundaries have been reached without finding a queen then true is returned.
		}
		else if(board[row][col] == "q") {//This checks if the current square is taken by a queen.
			return(false);//If a queen has been found then false is returned.
		}
		else {//If the boundaries haven't been reached and a queen isn't on the current square.
			ans = SearchSE(board,col+1,row+1);//This will recurse to the next diagonal square. ans will equal the return value.
		}
		return(ans);//This will end up either being true or false.
	}
	
	private Boolean SearchSW(String[][] board, int col, int row) {//This is a recursive method which searches the board in a South West diagonal line.
		Boolean ans;//Sets the answer variable up.
		if(col < 0 || row > 7) {//This checks if the given row or column is within the bounds of the board.
			return(true);//If the boundaries have been reached without finding a queen then true is returned.
		}
		else if(board[row][col] == "q") {//This checks if the current square is taken by a queen.
			return(false);//If a queen has been found then false is returned.
		}
		else {//If the boundaries haven't been reached and a queen isn't on the current square.
			ans = SearchSW(board,col-1,row+1);//This will recurse to the next diagonal square. ans will equal the return value.
		}
		return(ans);//This will end up either being true or false.
	}
}
