package cs.projects.gameSource;


public class Board{
	
	/**
	 * Declaration of the directions. Used to describe the disk movements.
	 */
	public enum Direction{
		NORTH (-1, 0), 
		NORTHEAST (-1, 1), 
		EAST (0, 1), 
		SOUTHEAST (1, 1), 
		SOUTH (1, 0), 
		SOUTHWEST (1, -1), 
		WEST (0, -1), 
		NORTHWEST (-1, -1);
		
		public final int rowDir;
		public final int colDir;
		/**
		 * @param rowDirection 1 is up, 0 is neutral, -1 is down
		 * @param colDirection 1 is right, 0 is neutral, -1 is left
		 */
		Direction(int rowDirection, int colDirection){
			this.rowDir = rowDirection;
			this.colDir = colDirection;
		}
	}
	
	public char[][] board;
	public Move[] moveRecord = new Move[64];
	public int moveIndex = 0;
	public int blackScore;
	public int whiteScore;
	int timeCopyCalled = 0;
	
	public Board(){
		this.board = new char[8][8];
		clearBoardToStart();
	}
	
	/**
	 * Initial starting position of the board
	 * 
	 * Resets the board to the default Reversi starting position with white pieces on d4 and e5
	 * and black pieces on e4 and d5 with all other spaces empty. 
	 */
	public void clearBoardToStart(){ 
		for(int row = 0; row < this.board.length; row++){
			for(int col = 0; col < this.board[row].length; col++){
				if((row == 3 && col == 3) || (row == 4 && col == 4))
					this.board[row][col] = 'W';
				else if((row == 3 && col == 4) || (row == 4 && col == 3))
					this.board[row][col] = 'B';
				else{
					this.board[row][col] = ' ';
				}
			}
		}
		this.blackScore = 2;
		this.whiteScore = 2;
	}
	
	/**
	 * CHANGE TO MOVE CLASS (This is a move characteristic)
	 * 
	 * Checks to see if the given move is a valid one. 
	 * That is:
	 * 1) The piece to be placed touches other pieces, and
	 * 2) it can collect at least one piece of the opposing color.
	 * 3) the position is empty
	 * @param move - Instance of the Move class.
	 * @return true if it is a valid move, false otherwise.
	 */
	public boolean isValidMove(Move move){
		if(isPositionEmpty(move.getRowIndex(), move.getColIndex()))
			for(Direction dir : Direction.values())
				if(checkInDirection(move.getRowIndex()+dir.rowDir, move.getColIndex()+dir.colDir, move.getPlayerColor(), dir, 0))
					return true;
		return false;
	}
	
	/**
	 * Checks if a position on the board is empty.
	 * 
	 * @param r
	 * @param c
	 * @return
	 */
	public boolean isPositionEmpty(int row, int col){
		return this.board[row][col] == (' ');
	}
	
	public boolean isOutOfBounds(int row, int col){
		return row < 0 || row > 7 || col < 0 || col > 7;
	}
	
//	public boolean isPositionPlayer(){
	
	private boolean checkInDirection(int row, int col, char player, Direction dir, int piecesTurned){
		if(isOutOfBounds(row, col) || isPositionEmpty(row, col)) //when outside the board or empty position
			return false;
		
		else if(this.board[row][col] == player){ //arrived player colored disk
			if(piecesTurned > 0)
				return true;
			else
				return false;
		}
		else
			return checkInDirection(row+dir.rowDir, col+dir.colDir, player, dir, piecesTurned+1);
	}
	
	/**
	 * Flips the pieces on the board according to standard Reversi rules.
	 * That is:
	 * 1) See what surrounding pieces are of the opposite color.
	 * 2) Add pieces in that direction to a list till you reach...
	 * 	a piece of your color -> change pieces in that list to your color
	 * 	a border -> do nothing
	 * 	a blank space -> do nothing
	 * @param move - Instance of the Move class.
	 */
	private void flipPieces(Move move){
		this.board[move.getRowIndex()][move.getColIndex()] = move.getPlayerColor();
		switch(move.getPlayerColor()){
		case 'W':
			whiteScore++;
			break;
		case 'B':
			blackScore++;
			break;
		default:
			System.out.println("ERROR: Player color is not B or W");
			System.exit(0);
		}
		for(Direction dir : Direction.values()){
			if(checkInDirection(move.getRowIndex()+dir.rowDir, move.getColIndex()+dir.colDir, move.getPlayerColor(), dir, 0))
				flipPiecesInDirection(move.getRowIndex()+dir.rowDir, move.getColIndex()+dir.colDir, move.getPlayerColor(), dir);
		}
	}
	
	private void flipPiecesInDirection(int row, int col, char player, Direction dir){
		if(row < 0 || row > 7 || col < 0 || col > 7){
		}
		else if(this.board[row][col] == ' '){
		}
		else if(this.board[row][col] == player){
		}
		else{
			this.board[row][col] = player;
			switch(player){
			case 'W':
				whiteScore++;
				blackScore--;
				break;
			case 'B':
				blackScore++;
				whiteScore--;
				break;
			default:
				System.out.println("ERROR: Player color is not B or W");
				System.exit(0);
			}
			this.flipPiecesInDirection(row+dir.rowDir, col+dir.colDir, player, dir);
		}
	}
	
	/**
	 * If the move is valid, it flips the pieces.
	 * @param move
	 */
	public boolean makeMove(Move move){
		if(move == null)
			return false;
		else if(this.isValidMove(move)){
			this.flipPieces(move);
			this.moveRecord[moveIndex] = move;
			moveIndex++;
			return true;
		}
		else 
			return false;
	}
	
	public void _makeMoveWithoutChecking(Move move){
		this.flipPieces(move);
		this.moveRecord[moveIndex] = move;
		moveIndex++;
	}
	
	public String toString(){
		String boardString = "   a b c d e f g h\n";
		boardString += " - - - - - - - - - -";
		for(int row = 0; row < this.board.length; row++){
			boardString += "\n" + (row+1) + "| ";
			for(int col = 0; col < this.board[row].length; col++){
				boardString += this.board[row][col] + " ";
			}
			boardString += "|";
		}
		boardString += "\n - - - - - - - - - -";
		return boardString;
	}
	
	public char getColorOfSquare(String SAN){
		Move move = new Move('B' + SAN);
		return this.board[move.getRowIndex()][move.getColIndex()];
	}
	
	public String getScore(){
		return "B " + this.blackScore + " - " + this.whiteScore + " W";
	}
	
	public Board deepCopy(){
		Board newBoard = new Board();
		/*for(Move m : moveRecord){
			if(m != null)
				newBoard._makeMoveWithoutChecking(m);
		}*/
		for(int row = 0; row < 8; row++)
			for(int col = 0; col < 8; col++)
				newBoard.board[row][col] = this.board[row][col];
		return newBoard;
	}
}