import java.util.Scanner;

/**
 * ReversiApplication is the module you actually run. It instantiates the players and the board and has 
 * a main loop which goes until the game is completed or the thread terminates prematurely.
 *  
 * @author GRUPO03 rumo ao 37
 */
public class ReversiApplication{
	
	Board gameBoard;
	private PlayerHandler black;
	private PlayerHandler white;
	
	public ReversiApplication(PlayerHandler player1, PlayerHandler player2){
		gameBoard = new Board(); // Creates a new board. Board starts in standard Reversi rules.
		black = player1;
		white = player2;
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
			//1st Player turn
			System.out.println("\n" + gameBoard.toString()); // Black's move.
			if(this.black.getClass() == MonteCarloAI.class)
				System.out.println("\nWait please, the computer playing with black pieces is thinking...\n");
			black.updateBoard(gameBoard);
			if(!gameBoard.makeMove(black.getMove()))
				blackMoveFlag = false;
			
			//2nd Player turn
			System.out.println("\n"+gameBoard.toString()); // White's move;
			if(this.white.getClass() == MonteCarloAI.class)
				System.out.println("\nWait please, the computer playing with white pieces is thinking...\n");
			white.updateBoard(gameBoard);
			if(!gameBoard.makeMove(white.getMove()))
				whiteMoveFlag = false;
		}
		System.out.println("GAME OVER");
		System.out.println(gameBoard.getFinalScore());
	}
	
	public static void initialMenu(){
		System.out.println("    \u2605 REVERSI \u2605\n");
		
		System.out.println("\u2460   Player vs Player");
		System.out.println("\u2461   Player vs Computer");
		System.out.println("\u2462   Computer vs Computer");
		System.out.println("\u2463   Game instructions");
		System.out.println("\u2464   About");
		
		System.out.print("\n\u29C1 Choose your option: ");
	}
	
	public static void main(String[] args){
		ReversiApplication game;
		PlayerHandler player1, player2;
		Scanner sc = new Scanner(System.in);
		boolean retry = true;
		
		initialMenu();
		
		while(retry){
			String input = sc.next();
			switch(input){
			case "1":
				System.out.println("OK! Starting a Person vs Person game!\n");
				player1 = new ManualPlayer("Black");
				player2 = new ManualPlayer("White");
				game = new ReversiApplication(player1, player2);
				game.start();
				break;
			case "2":
				System.out.println("OK! Starting a Person vs Computer game!\n");
				player1 = new ManualPlayer("Black");
				player2 = new MonteCarloAI("White");
				game = new ReversiApplication(player1, player2);
				game.start();
				break;
			case "3":
				System.out.println("OK! Starting a Computer vs Computer game!\n");
				player1 = new MonteCarloAI("Black");
				player2 = new MonteCarloAI("White");
				game = new ReversiApplication(player1, player2);
				game.start();
				break;
			case "4":
				System.out.println("    \u2605 INSTRUCTIONS \u2605");
				System.out.println("\u2139 INPUT FORMAT"
								 + "\nTo make a valid move you have to write the position where you want to place a disk in the Standart AlgebraicNotation (SAN).\n"
						         + "For example:\n"
								 + "To place in row 4 and column c you have to write c4 or C4 or 4c or 4C (YES! Our program is not case sensitive. Thank god!)");
				
				System.out.println("\u2139 HOW TO PLAY"
						 + "\nA move consists in placing from outside one piece on the board. Placed pieces can never be moved to another square later in the game."
						 + "\nThe incorporation of the pieces must be made according to the following rules:"
						 + "\n\u2299The incorported piece must outflank one or more of the opponent placed pieces"
						 + "\n\u2299To outflank means that a single piece or one straight row (vertical, horizontal or diagonal) of pieces of the opponent is in both sides next to own pieces, with no empty squares between all those pieces"
						 + "\n\u2299The player who makes the move turns the outflanked pieces over, becoming all of them in own pieces"
						 + "\n\u2299If there is more than one outflanked row, all the involved pieces in those rows have to be flipped"
						 + "\n\u2299If it´s not possible to make this kind of move, turn is forfeited and the opponent repeats another move");
				
				System.out.println("\u2139 WINNING CONDITION"
						 + "\n\u2299The game is over when all the squares of the board are taken or none of the players can move."
						 + "\n\u2299In any case the winner is the player who has more pieces on the board."
						 + "\n\u2299The game ends in a draw when both players have the same number of pieces on the board.");
				System.out.println("\n");
				initialMenu();
				retry = true;
				break;
			case "5":
				System.out.println("    \u2605 ABOUT \u2605");
				System.out.println("Authors:\nJoao Andrade\nMiguel Guerreiro\nRicardo Reais\n\n\u00AE Ualg 2017, Artificial Intelligence "
						+ "\nProfessor Fernando Lobo");
				System.out.println("\n");
				initialMenu();
				retry = true;
				break;
			default:
				System.out.println("Please don't be silly!");
				System.out.print("\n\u29C1 Choose your option: ");
				retry = true;
				break;			
			}
		}
		sc.close();
	}
}