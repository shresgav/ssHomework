/**
 * 
 */
package assignment1;

/**
 * Shape printing
 * @author Gavin Shrestha
 *
 */
public class Shapes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Shapes s = new Shapes();
		
		System.out.println("Question 1:");
		s.leftTriangle();
		s.printLine();
		
		System.out.println("Question 2:");
		s.printLine();
		s.flippedTriangle();
		
		System.out.println("Question 3:");
		s.printPyramid(4);
		s.printLine();
		
		System.out.println("Question 4:");
		s.printLine();
		s.flippedPyramid(4);
		
		

	}
	
	public void printLine() {
		for (int i = 0; i < 10; ++i) {
			System.out.print("-");
		}
		System.out.println();
	}
	
	public void leftTriangle() {
		for (int i = 1; i < 5; ++i) {
			for (int j = 0; j < i; ++j) {
				System.out.print("*");
			}
			System.out.println();
		}
	}
	
	public void flippedTriangle() {
		for (int i = 4; i > 0; --i) {
			for (int j = 0; j < i; ++j) {
				System.out.print("*");
			}
			System.out.println();
		}
	}
	
	public void printPyramid(int n) {
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n-i; ++j) {
				System.out.print(" ");
			}
			for (int j = 0; j < 2 * i + 1; ++j) {
				System.out.print("*");
			}
			System.out.println();
		}
	}
	
	public void flippedPyramid(int n) {
		for (int i = n-1; i >= 0; --i) {
			for (int j = 0; j < n-i; ++j) {
				System.out.print(" ");
			}
			for (int j = 0; j < 2 * i + 1; ++j) {
				System.out.print("*");
			}
			System.out.println();
		}
	}
}
