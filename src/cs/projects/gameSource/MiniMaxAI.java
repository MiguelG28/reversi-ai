package cs.projects.gameSource;

public class MiniMaxAI implements PlayerHandler{

	Board gameBoard;
	String playerColor;
	
	int[][] hTable = {{20, 1,15,15,15,15, 1,20},
					  { 1, 0, 2, 2, 2, 2, 0, 1},
					  { 5, 2, 4, 3, 3, 4, 2, 5},
					  { 5, 2, 3, 3, 3, 3, 2, 5},
					  { 5, 2, 3, 3, 3, 3, 2, 5},
					  { 5, 2, 4, 3, 3, 4, 2, 5},
					  { 1, 0, 2, 2, 2, 2, 0, 1},
					  {20, 1, 5, 5, 5, 5, 1,20}};
	
	public MiniMaxAI(String color){
		this.playerColor = color;
	}
	
	@Override
	public Move getMove() {
		TreeNode head = new TreeNode(playerColor, null, gameBoard); // Creates the node for this board.
		
		int level = 7;
		
		head.generateChildren();
		if(head.getChildren().size() == 0)
			return null;
		TreeNode best = head.getChildren().get(0);
		int current;
		if(playerColor.toUpperCase().equals("BLACK")){
			int max = Integer.MIN_VALUE;
			for(TreeNode child : head.getChildren()){
				current = child.findMinValueOfChildren(level--) + hTable[child.moveMade.getRowIndex()][child.moveMade.getColIndex()];
				if(current > max){
					max = current;
					best = child;
				}
			}
			return best.moveMade;
		}
		else if(playerColor.toUpperCase().equals("WHITE")){
			int min = Integer.MAX_VALUE;
			for(TreeNode child : head.getChildren()){
				current = child.findMaxValueOfChildren(level--) - hTable[child.moveMade.getRowIndex()][child.moveMade.getColIndex()];
				if(current < min){
					min = current;
					best = child;
				}
			}
			return best.moveMade;
		}
		else{
			System.out.println("WARNING: Couldn't resolve player color in MiniMaxAI method getMove().");
			return null;
		}
			
	}
	
	@Override
	public void updateBoard(Board newBoard) {
		gameBoard = newBoard;
	}
}