import java.util.Scanner;

public class MoveReader{
	
	private Scanner reader;

	
	public MoveReader(){
		reader = new Scanner(System.in);
	}
	
	public Move getMove(String playerColor){
		int row, col;
		Move result;
		System.out.print("\n\u29C1 Please " + playerColor + " pieces player choose your next move: ");
		String moveText = reader.next(); //Reads in SAN
		
		if(moveText.length() != 2){
			System.out.println("That's not valid input (too long or too small). Try again.");
			return getMove(playerColor);
		}
		
		if(!Character.isLetter(moveText.charAt(0))) //If using reverse SAN
			moveText = reverse(moveText);
		if(Character.isUpperCase(moveText.charAt(0))) //NOT case-sensitive
			moveText = "" + Character.toLowerCase(moveText.charAt(0)) + moveText.charAt(1);
				
		row = Character.getNumericValue(moveText.charAt(1)) - 1;
		col = (int) moveText.charAt(0) - 97;//we use 97 because, ASCII value for 'a' is 97
		result = new Move(playerColor.charAt(0) + moveText);
//		System.out.println("row:"+row);
//		System.out.println("col:"+col);
		if(result.isOutOfBounds(row, col)){
			System.out.println("That's not valid input (out of bounds). Try again.");
			return getMove(playerColor);
		}
		else
			return result;
	}
	
	public static String reverse(String input){
	    char[] in = input.toCharArray();
	    int begin=0;
	    int end=in.length-1;
	    char temp;
	    while(end>begin){
	        temp = in[begin];
	        in[begin]=in[end];
	        in[end] = temp;
	        end--;
	        begin++;
	    }
	    return new String(in);
	}
}