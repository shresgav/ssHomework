/**
 * 
 */
package assignment1;

import java.util.Scanner;

/**
 * A guessing game
 * 
 * @author Gavin Shrestha
 *
 */
public class GuessingGame {
	private int randNumber = 1 + (int) (Math.random() * 100);
	private int guesses = 0;

	public GuessingGame(int guesses) {
		super();
		this.guesses = guesses;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GuessingGame g = new GuessingGame(5);
		g.playGame();
	}

	public void playGame() {
		// Read input
		Scanner sc = new Scanner(System.in);
		do {
			System.out.print("Guess a number (1-100): ");
			int guess = readGuess(sc);
			if (guess > 0 && Math.abs(guess - randNumber) < 10) {
				System.out.println("You win! The number was " + randNumber);
				break;
			} else if (guess <= 0 || guess > 100) {
				System.out.println("Invalid guess");
				continue;
			} else {
				guesses--;
				System.out.println("WRONG! YOU NOW HAVE " + guesses + " GUESSES!");
			}

		} while (guesses > 0);

		if (guesses == 0) {
			System.out.println("YOU LOSE!");
		}
	}

	protected int readGuess(Scanner sc) {
		try {
			return Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			return -1;
		}
	}

}
