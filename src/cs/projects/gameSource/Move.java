package cs.projects.gameSource;

/**
 * Storage class for a given move in Reversi. Records player color, row position, and columns position.
 * Provides some additional functionality for writing moves in Standard Algebraic Notation (SAN).
 * Move is an immutable class (i.e. it provides no "setter" methods aside from the constructor).
 */
public class Move{
	private char player; // Player color. Either 'B' or 'W'.
	private int row; // Move row index. Must be in {0,1,2,3,4,5,6,7}.
	private int col; // Move col index. Must be in {0,1,2,3,4,5,6,7}.
	
	/**
	 * Constructor for Move class. Takes a Reversi move in SAN and stores the data in Move's fields.
	 * Since move method input will already be sanitized, we assume the parameters are in bounds.
	 * @param move - String input of SAN for move.
	 */
	public Move(String move){
		this.player = move.charAt(0);
		
		this.row = Integer.parseInt(move.substring(2));
		this.row--;
		
		this.col = move.charAt(1);
		this.col -= 97;
	}
	
	/**
	 * Returns the column value for this move as a string. Otherwise throws an AssertionError.
	 * @return the column for this move. Must be in {'a','b',...,'h'}.
	 */
	public char getColString(){
		switch(this.col){
		case 0:
			return 'a';
		case 1:
			return 'b';
		case 2:
			return 'c';
		case 3:
			return 'd';
		case 4:
			return 'e';
		case 5:
			return 'f';
		case 6:
			return 'g';
		case 7:
			return 'h';
		}
		assert false : "Column is not an int between 0 and 7, instead col = " + this.col;
		return ' ';
	}
	
	/**
	 * Returns the column value for this move as an integer.
	 * @return - the column for this move. Must be in {0,1,...,7}.
	 */
	public int getColIndex(){
		return this.col;
	}
	
	/**
	 * Returns the row value for this move as it would appear in SAN notation (i.e. from 1 to 8).
	 * @return a String of the row value from 1 to 8.
	 */
	public String getRowString(){
		return Integer.toString(row+1);
	}
	
	/**
	 * Returns the row index of this move as an integer.
	 * @return the row for this move. Must be in {0,1,...,7}.
	 */
	public int getRowIndex(){
		return this.row;
	}
	
	/**
	 * Returns the player color of this move as a char.
	 * @return the player's color for this move. Must be 'B' or 'W'.
	 */
	public char getPlayerColor(){
		return this.player;
	}

	/**
	 * Returns the move in Standard Algebraic Notation (SAN). SAN is player color + column letter + row number.
	 */
	public String toString(){
		return "" + this.player + this.getColString() + this.getRowString();
	}
}