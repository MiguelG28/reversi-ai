package cs.projects.gameSource;

import cs.projects.gameSource.Constants.Player;

/**
 * ReversiApplication is the module you actually run. It instantiates the players and the board and has 
 * a main loop which goes until the game is completed or the thread terminates prematurely.
 *  
 * @author John Wilkes - jcwilkes
 */
public class ReversiApplication{
	
	Board gameBoard;
	private PlayerHandler black;
	private PlayerHandler white;
	
	// ========SETTINGS========
	private Player BLACK_TYPE = Player.MONTECARLO;
	private Player WHITE_TYPE = Player.MONTECARLO;
	
	public ReversiApplication(){
		gameBoard = new Board(); // Creates a new board. Board starts in standard Reversi rules.
		
		switch(BLACK_TYPE){ // Switch flow creating BLACK player based on the settings above.
		case MANUAL:
			black = new ManualPlayer("Black");
			break;
		case MINIMAX:
			black = new MiniMaxAI("Black");
			break;
		case MONTECARLO:
			black = new MonteCarloAI("Black");
			break;
		default:
			black = new ManualPlayer("Black");
			break;
		}
		
		switch(WHITE_TYPE){ // Switch flow creating WHITE player based on the settings above.
		case MANUAL:
			white = new ManualPlayer("White");
			break;
		case MINIMAX:
			white = new MiniMaxAI("White");
			break;
		case MONTECARLO:
			white = new MonteCarloAI("White");
			break;
		default:
			white = new ManualPlayer("White");
			break;
		}
	}
	
	/**
	 * Starts the game. Requests moves from PlayerHandlers and notifies each PlayerHandler with board changes.
	 * Method returns when neither player has a valid move. 
	 * 
	 * TODO: Implement repeat function.
	 */
	public void start(){
		boolean blackMoveFlag = true; // End state detection system. Uses two flags which state if white and
		boolean whiteMoveFlag = true; // black have legal moves. True = has legal move.
		while(blackMoveFlag || whiteMoveFlag){
			blackMoveFlag = true;
			whiteMoveFlag = true;
			System.out.println(gameBoard.toString()); // Black's move.
			black.updateBoard(gameBoard);
			if(!gameBoard.makeMove(black.getMove()))
				blackMoveFlag = false;
			
			System.out.println(gameBoard.toString()); // White's move;
			white.updateBoard(gameBoard);
			if(!gameBoard.makeMove(white.getMove()))
				whiteMoveFlag = false;
		}
		System.out.println("GAME OVER");
		System.out.println(gameBoard.getScore());
	}
	
	public static void main(String[] args){
		//for(int index = 0; index < 100; index++){
			ReversiApplication game = new ReversiApplication();
			game.start();
		//}
	}
}