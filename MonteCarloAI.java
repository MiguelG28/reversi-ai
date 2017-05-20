import java.util.LinkedList;
import java.util.List;

public class MonteCarloAI implements PlayerHandler{
	Board gameBoard;
	String playerColor;
	
	int SAMPLE_SIZE = 700; // Sample size of the random walks.
	
	public MonteCarloAI(String color){
		this.playerColor = color;
	}
	
	@Override
	public Move getMove() {
		List<Move> moveList = getPossibleMoves(this.gameBoard, this.playerColor);
		
		if(moveList.size() == 0) // If there are no possible moves, return null.
			return null;
		
		int max = -1;
		int current; //Rating of the current move.
		Move bestMove = moveList.get(0);
		for(Move m : moveList){ // For each move, evaluate with a random walk.
			current = this.evaluate(m);
			if(current >= SAMPLE_SIZE * 0.7)
				return m;
			if(current > max){
				max = current;
				bestMove = m;	
			}
		}
		return bestMove;
	}
	
	private String getOppositeColor(String color){
		switch(color.toUpperCase()){
		case "BLACK":
			return "White";
		case "WHITE":
			return "Black";
		default: //Just for safety, MonkaS.
			System.out.println("ERROR: Color of MonteCarloAI not found.");
			return null;
		}
	}
	
	private String getOppositeColor(char color){
		switch(color){
		case 'B':
			return "White";
		case 'W':
			return "Black";
		case 'b':
			return "White";
		case 'w':
			return "Black";
		default: //Just for safety, MonkaS.
			System.out.println("ERROR: Color of MonteCarloAI not found.");
			return null;
		}
	}
	
	/**
	 * Analogy to an heuristic function.
	 * Gives a "rating" to a certain move. This will be used later to help the computer choose the best move available.
	 * 
	 * @param m
	 * @return
	 */
	private int evaluate(Move m){
		Board start = this.gameBoard.deepCopy(); //Board before the move has been made.
		start.makeMove(m);
		Board state = start.deepCopy(); // Create the board after the move has been made.
		
		int victories = 0;
		List<Move> moveList; //Future opponent possible movements,
		for(int i = 1; i <= SAMPLE_SIZE; i++){ //iterate 700 times
			moveList = this.getPossibleMoves(state , getOppositeColor(this.playerColor));
			Move randomMove;
			while(moveList.size() != 0){
				randomMove = moveList.get((int)(Math.random() * moveList.size()));
				state.makeMove(randomMove);
				moveList = this.getPossibleMoves(state, getOppositeColor(randomMove.getPlayerColor()));
			}
			switch(m.getPlayerColor()){
			case 'B':
				if(state.blackScore >= 32)
					victories += 1;
				break;
			case 'W':
				if(state.whiteScore >= 32)
					victories += 1;
				break;
			}	
			state = start.deepCopy();
		}
		return victories;
	}
	
	
	/**
	 * What is happening here?
	 * 1) We go to each available position(64 total)
	 * 2) Check if we can move to that position? (Simulate a move)
	 * 	2.1)The piece to be placed touches other pieces, and
	 * 	2.2)it can collect at least one piece of the opposing color.
	 * 	2.3)the position is empty.
	 * 3)If we can, add it to the possible moves list, else ignore that move option.
	 */
	private List<Move> getPossibleMoves(Board board, String color){
		List<Move> moveList = new LinkedList<Move>(); 
		char playerColor = color.toUpperCase().charAt(0); // Used to create SAN for finding if a move is legal. 
		Move currentMove;
		
		for(int row = 1; row <= 8; row++) // Iterate over each row...
			for(char col : Constants.COLUMN_LETTERS){ // ...and column.
				currentMove = new Move("" + playerColor + col + row); // Create a theoretical move using SAN.
				if(board.isValidMove(currentMove) && // Board will tell you if it flips pieces
				   board.isPositionEmpty(currentMove.getRowIndex(), currentMove.getColIndex())) // This tell you if the square is empty.
					moveList.add(currentMove); // If both are true, add the current move to the list.
		}
		return moveList;
	}
	
	/**
	 * This is the same as shallow cloning. (i.e. copy pointers only)
	 */
	@Override
	public void updateBoard(Board newBoard) {
		this.gameBoard = newBoard;
	}
}