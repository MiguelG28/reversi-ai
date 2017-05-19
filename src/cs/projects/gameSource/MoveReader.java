package cs.projects.gameSource;

import java.util.Scanner;

public class MoveReader{
	
	private Scanner reader;

	
	public MoveReader(){
		reader = new Scanner(System.in);
	}
	
	public Move getMove(String playerColor){
		System.out.print("\n----ENTER " + playerColor.toUpperCase() + "'s NEXT MOVE----\n:");
		String moveText = reader.next();
		
		if(moveText.length() != 2){
			System.out.println("That's not valid input. Try again.");
			return getMove(playerColor);
		}
		
		int row = Character.getNumericValue(moveText.charAt(1));
		int col = (int) moveText.charAt(0) - 98; //we use 98 because, ASCII value for 'a' is 97

		if(!isValidRow(row) || !isValidColumn(col)){
//			System.out.println("row: " + row);
//			System.out.println("col: " + col);
			System.out.println("That's not valid input (out of bounds). Try again.");
			return getMove(playerColor);
		}
		
		return new Move(playerColor.toUpperCase().substring(0, 1) + moveText);
	}
	
	private boolean isValidColumn(int col){
		return col > 0 && col <= 8;
	}
	
	private boolean isValidRow(int row){
		return row > 0 && row <= 8;
	}
}