import java.util.Scanner;
import java.util.Random;
public class Game {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner input = new Scanner(System.in);

		System.out.println("Welcome to hangman!\n");
		//variable word comes from pick word method
		String word = pickWord();
		//print said word
		System.out.println(word);
		//start at no letters guessed
		String lettersGuessed = "";
		//number of wrong guesses initializes at 0
		int numWrong=0;
		//start loop while the complete word consiting of word and letters guessed
		while(wordComplete(word,lettersGuessed))
		{
			//tell how many are wrong, variable numWrong
			printHangman(numWrong);
			//print word and 
			printWord(word,lettersGuessed);
			//print guessed call letters guessed variable
			printGuessed(lettersGuessed);
			
			System.out.println("\nPick a letter: ");
			//character guess is equal the input of next line
			System.out.println("Welcome to hangman: Enter guess.");
			char guess = input.next().charAt(0);
			//variable letters guessed is equal to letters guessed method + guess
			lettersGuessed = lettersGuessed + guess;

			if(!word.contains(String.valueOf(guess)))
			{
				
				numWrong++;
				if (numWrong == 11){
					break;
				}
			}
		}
		if(wordComplete(word,lettersGuessed))
		{
			printWord(word,lettersGuessed);
			System.out.println("\nCongratulations! You win!");
		}
		else
		{
			printHangman(numWrong);
			System.out.println("Sorry, you lose.\nThe word was" + word);
		}

		
	}
	public static boolean wordComplete(String word, String lettersGuessed)
	{
		for (int i = 0; i < lettersGuessed.length(); i++)
		{
			char letter = lettersGuessed.charAt(i);
			if(lettersGuessed.contains(String.valueOf(word)))
			{
				System.out.println("Congratulations: You Won!");
				System.exit(0);
				return(false);
			}
		}
		
		return(true);
		

	}
	
	

	public static String pickWord(){

		Random randomGenerator = new Random();
		int choice = randomGenerator.nextInt(60);
		
		
		
		switch(choice){
		case 1:
			return "pie";
		case 2:
			return "pupil";
		case 3:
			return "rythm";
		case 4:
			return "beverage";
		case 5:
			return "computer";
		case 6:
			return "science";
		case 7:
			return "nathan";
		case 8:
			return "lumps";
		case 9:
			return "greatest";
		case 10:
			return "common";
		case 11:
			return "factor";
		case 12:
			return "math";
		case 13:
			return "expectation";
		case 14:
			return "milestones";
		case 15:
			return "difficulties";
		case 16:
			return "abruptly";
		case 17:
			return "affix";
		case 18:
			return "askew";
		case 19:
			return "axiom";
		case 20:
			return "azure";
		case 21:
			return "bagpipes";
		case 22:
			return "bandwagon";
		case 23:
			return "banjo";
		case 24:
			return "bayou";
		case 25:
			return "bikini";
		case 26:
			return "blitz";
		case 27:
			return "bookworm";
		case 28:
			return "boxcar";
		case 29:
			return "boxful";
		case 30:
			return "buckaroo";
		case 31:
			return "buffalo";
		case 32:
			return "buffoon";
		case 33:
			return "cobweb";
		case 34:
			return "croquet";
		case 35:
			return "daiquiri";
		case 36:
			return "disavow";
		case 37:
			return "duplex";
		case 38:
			return "equip";
		case 39:
			return "exodus";
		case 40:
			return "fishhook";
		case 41:
			return "fixable";
		case 42:
			return "foxglove";
		case 43:
			return "galvanize";
		case 44:
			return "gazebo";
		case 45:
			return "gizmo";
		case 46:
			return "glowworm";
		case 47:
			return "guffaw";
		case 48:
			return "haiku";
		case 49:
			return "haphazard";
		case 50:
			return "hyphen";
		case 51:
			return "icebox";
		case 52:
			return "injury";
		case 53:
			return "ivory";
		case 54:
			return "ivy";
		case 55:
			return "jujitsu";
		case 56:
			return "khaki";
		case 57:
			return "kiwifruit";
		case 58:
			return "megahertz";
		case 59:
			return "numbskull";
		default:
			return "pneumonia";
		
		}

	}

	public static void printHangman(int numWrong)
	{
		if(numWrong == 0){
			System.out.println("|------|");
			System.out.println("|      |");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|________________");
		}
		else if(numWrong == 1){
			System.out.println("|------|");
			System.out.println("|      |");
			System.out.println("|   *****");
			System.out.println("|  *     *");
			System.out.println("| *       *");
			System.out.println("|  *     *");
			System.out.println("|   *****");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|________________");
		}
		else if(numWrong == 2){
			System.out.println("|------|");
			System.out.println("|      |");
			System.out.println("|   *****");
			System.out.println("|  *     *");
			System.out.println("| *       *");
			System.out.println("|  *     *");
			System.out.println("|   *****");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|________________");
		}
		else if(numWrong == 3){
			System.out.println("|------|");
			System.out.println("|      |");
			System.out.println("|   *****");
			System.out.println("|  *     *");
			System.out.println("| *       *");
			System.out.println("|  *     *");
			System.out.println("|   *****");
			System.out.println("|     *");
			System.out.println("| *****");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|________________");
		}
		else if(numWrong == 4){
			System.out.println("|------|");
			System.out.println("|      |");
			System.out.println("|   *****");
			System.out.println("|  *     *");
			System.out.println("| *       *");
			System.out.println("|  *     *");
			System.out.println("|   *****");
			System.out.println("|     *");
			System.out.println("| *********");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|");
			System.out.println("|________________");
		}
		else if(numWrong == 5){
			System.out.println("|------|");
			System.out.println("|      |");
			System.out.println("|   *****");
			System.out.println("|  *     *");
			System.out.println("| *       *");
			System.out.println("|  *     *");
			System.out.println("|   *****");
			System.out.println("|     *");
			System.out.println("| *********");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|    *");
			System.out.println("|   *");
			System.out.println("|  *");
			System.out.println("|________________");
		}
		else if(numWrong == 6){
			System.out.println("|------|");
			System.out.println("|      |");
			System.out.println("|   *****");
			System.out.println("|  *     *");
			System.out.println("| *       *");
			System.out.println("|  *     *");
			System.out.println("|   *****");
			System.out.println("|     *");
			System.out.println("| *********");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|    * *");
			System.out.println("|   *   *");
			System.out.println("|  *     *");
			System.out.println("|________________");
		}
		else if(numWrong == 7){
			System.out.println("|------|");
			System.out.println("|      |");
			System.out.println("|   *****");
			System.out.println("|  * o o *");
			System.out.println("| *       *");
			System.out.println("|  *     *");
			System.out.println("|   *****");
			System.out.println("|     *");
			System.out.println("| *********");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|    * *");
			System.out.println("|   *   *");
			System.out.println("|  *     *");
			System.out.println("|________________");
		}
		else if(numWrong == 8){
			System.out.println("|------|");
			System.out.println("|      |");
			System.out.println("|   *****");
			System.out.println("|  * o o *");
			System.out.println("| *   |   *");
			System.out.println("|  *     *");
			System.out.println("|   *****");
			System.out.println("|     *");
			System.out.println("| *********");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|    * *");
			System.out.println("|   *   *");
			System.out.println("|  *     *");
			System.out.println("|________________");
		}
		else if(numWrong == 9){
			System.out.println("|------|");
			System.out.println("|      |");
			System.out.println("|   *****");
			System.out.println("|  * o o *");
			System.out.println("| *   |   *");
			System.out.println("|  * --- *");
			System.out.println("|   *****");
			System.out.println("|     *");
			System.out.println("| *********");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|     *");
			System.out.println("|    * *");
			System.out.println("|   *   *");
			System.out.println("|  *     *");
			System.out.println("|________________");
		}
	}

	public static void printWord(String word, String lettersGuessed)
	{
		for (int i = 0; i < word.length(); i++){
			char letter = word.charAt(i);
			if(lettersGuessed.contains(String.valueOf(letter))){
				System.out.println(letter);
			}
			else{
				System.out.println("_");
			}
		}
		System.out.println("\n");
	}

	public static void printGuessed(String lettersGuessed){
		for (int i = 0; i < lettersGuessed.length(); i++){
			char letter = lettersGuessed.charAt(i);
			System.out.println(letter);
		}
	}

	
}