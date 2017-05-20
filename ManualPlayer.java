public class ManualPlayer implements PlayerHandler{
	Board gameBoard;
	String playerColor;
	MoveReader reader;
	
	public ManualPlayer(String color){
		reader = new MoveReader();
		playerColor = color;
	}
	
	@Override
	public Move getMove() {
		Move move = reader.getMove(playerColor);
		if(gameBoard.isFull())
			return null;
		while(!gameBoard.isValidMove(move)){
			System.out.println("ERROR: NOT A LEGAL MOVE");
			move = reader.getMove(playerColor);
		}
		return move;
	}

	@Override
	public void updateBoard(Board newBoard) {
		gameBoard = newBoard;
	}
}