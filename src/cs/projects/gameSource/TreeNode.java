package cs.projects.gameSource;

import java.util.LinkedList;
import java.util.List;

/**
 * TreeNode class to implement Alpha-Beta MiniMax AI.
 * @author John Wilkes
 */
public class TreeNode{
		String color; 
		Move moveMade; // Move made TO GET TO this node.
		List<TreeNode> children;
		int value; // Value is always calculated black-white.
		Board state;
		
		/**
		 * Constructor for TreeNode. Note, it doesn't specify a parent, or automatically evaluate the node or find
		 * its children.
		 * @param color - the color of the player this node will try and make the next move as. 
		 * @param moveMade - the move made to get to this node.
		 * @param state - the state of the board at this node.
		 */
		public TreeNode(String color, Move moveMade, Board state){
			this.color = color;
			this.moveMade = moveMade;
			this.state = state;
		}
		
		public int findMinValueOfChildren(int level){
			if(level <= 0) // If we are at the desired search depth then return the score.
				return this.evaluateNode();
			
			this.generateChildren(); // Otherwise, find all the children.
			if(this.children.size() == 0) // If there aren't any, return the score.
				return this.evaluateNode();
			
			int min = Integer.MAX_VALUE;
			int current;
			for(TreeNode child : this.children){ // For each child...
				current = child.findMaxValueOfChildren(level-1);
				if(current < min)
					min = current;
			}
			this.children = null;
			return min;
		}
		
		public int findMaxValueOfChildren(int level){
			if(level <= 0) // If we are at the desired search depth then return the score.
				return this.evaluateNode();
			
			this.generateChildren(); // Otherwise, find all the children.
			if(this.children.size() == 0) // If there aren't any, return the score.
				return this.evaluateNode();
			int max = Integer.MIN_VALUE;
			int current;
			for(TreeNode child : this.children){ // For each child...
				current = child.findMinValueOfChildren(level-1);
				if(current > max)
					max = current;
			}
			this.children = null;
			return max;
		}
		
		/**
		 * Generates the children as TreeNodes for every possible move from this state.
		 * just modify the underlying memory without creating a new board, but I'm not sure.
		 */
		public void generateChildren(){
			List<Move> moveList = this._getPossibleMoves(state, color); // Generate all possible moves.
			this.children = new LinkedList<TreeNode>(); // Instantiate a new LinkedList for the children.
			
			for(Move m : moveList) { // For each move...
				Board b = this.state.deepCopy(); // ...copy this board using deepCopy() utility...
				b.makeMove(m); // ...make the move on that board...
				this.children.add(new TreeNode(_findOtherColor(), m, b)); // ...and add it to child list.
			}
		}
		
		/**
		 * Returns all possible moves as a list for a given colors turn and board state.</br>
		 * TODO: Make algorithm more efficient than just checking if every square is a viable move.  
		 * @param board - state of the current board from which to generate moves.
		 * @param color - the color of the player who's turn it is.
		 * @return a list of all possible moves.
		 */
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
		
		/**
		 * Utility to tell what the other players color is. Used in generate children, because they will be acting as
		 * the other player.
		 * @return the color of the other player, either black, white, or null (for errors).
		 */
		private String _findOtherColor(){
			if(this.color.toUpperCase().equals("BLACK"))
				return "White";
			else if(this.color.toUpperCase().equals("WHITE"))
				return "Black";
			else
				return null;
		}
		
		public Move getMove(){
			return moveMade;
		}
		
		public List<TreeNode> getChildren(){
			return this.children;
		}

		/**
		 * Find's the score of this node by counting the black and white squares.
		 * @return the score of this node which is # black pieces - # white pieces.
		 */
		public int evaluateNode(){
			return this.state.blackScore - this.state.whiteScore;
		}

		public int getValue(){
			return this.value;
		}
		
		public void setValue(int value){
			this.value = value;
		}
		
		public String toString(){
			return this.state.toString();
		}
	}