package cs.projects.gameSource;

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
		List<Move> moveList = _getPossibleMoves(this.gameBoard, this.playerColor);
		
		if(moveList.size() == 0) // If there are no possible moves, return null.
			return null;
		
		int max = -1;
		int current;
		Move best = moveList.get(0);
		for(Move m : moveList){ // For each move, evaluate with a random walk.
			current = this.evaluate(m);
			if(current >= SAMPLE_SIZE * 0.7)
				return m;
			if(current > max){
				max = current;
				best = m;	
			}
		}
		return best;
	}
	
	private String _findOtherColor(String color){
		switch(color.toUpperCase()){
		case "BLACK":
			return "White";
		case "WHITE":
			return "Black";
		default:
			System.out.println("ERROR: Color of MonteCarloAI not found.");
			return null;
		}
	}
	
	private String _findOtherColor(char color){
		switch(color){
		case 'B':
			return "White";
		case 'W':
			return "Black";
		case 'b':
			return "White";
		case 'w':
			return "Black";
		default:
			System.out.println("ERROR: Color of MonteCarloAI not found.");
			return null;
		}
	}
	
	private int evaluate(Move m){
		Board start = this.gameBoard.deepCopy();
		start.makeMove(m);
		Board state = start.deepCopy(); // Create the board after the move has been made.
		
		int winSum = 0;
		List<Move> moveList;
		for(int trial = 1; trial <= SAMPLE_SIZE; trial++){
			moveList = this._getPossibleMoves(state, _findOtherColor(this.playerColor));
			Move randomMove;
			while(moveList.size() != 0){
				randomMove = moveList.get((int)(Math.random() * moveList.size()));
				state.makeMove(randomMove);
				moveList = this._getPossibleMoves(state, _findOtherColor(randomMove.getPlayerColor()));
			}
			
			switch(m.getPlayerColor()){
			case 'B':
				if(state.blackScore >= 32)
					winSum += 1;
				break;
			case 'W':
				if(state.whiteScore >= 32)
					winSum += 1;
				break;
			}
			
			state = start.deepCopy();
		}
		
		return winSum;
	}
	
	private List<Move> _getPossibleMoves(Board board, String color){
		List<Move> moveList = new LinkedList<Move>(); 
		char colorCharacter = color.toUpperCase().charAt(0); // Used to create SAN for finding if a move is legal. 
		
		Move currentMove;
		for(int row = 1; row <= 8; row++){ // Iterate over each row...
			for(char col : Constants.COLUMN_LETTERS){ // ...and column.
				currentMove = new Move("" + colorCharacter + col + row); // Create a theoretical move using SAN.
				if(board.isValidMove(currentMove) && // Board will tell you if it flips pieces
				   board.getColorOfSquare("" + currentMove.getColString() + currentMove.getRowString()) == ' ') // This tell you if the square is empty.
					moveList.add(currentMove); // If both are true, add the current move to the list.
			}
		}
		
		return moveList;
	}

	@Override
	public void updateBoard(Board newBoard) {
		this.gameBoard = newBoard;
	}
}