package cs.projects.gameSource;


/**
 * A PlayerHandler is the catch all interface for move input on a board. 
 * It is a mixed implementation of strategy and notifier patterns. Each PlayerHandler has its own method for 
 * producing the next move (hence strategy), as well as its own board which the ReversiApplication updates after
 * each move (hence notifier).
 */
public interface PlayerHandler{
	
	/**
	 * Gets the next move of this PlayerHandler based on its strategy.
	 * @return Next move for this PlayerHandler; or if no valid move, null.
	 */
	public Move getMove();
	
	/**
	 * Updates the PlayerHandler's internal board to match the current game board.
	 */
	public void updateBoard(Board newBoard);
	
}