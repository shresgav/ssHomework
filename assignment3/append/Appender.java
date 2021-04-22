package assignment3.append;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

/**
 * Appends user-submitted text to file path specified in the command line
 * 
 * @author Gavin Shrestha
 *
 */

public class Appender {

	public static void main(String[] args) {
		
		System.out.print("Text to be appended: ");
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		sc.close();
		appendText(s, args[0]);
	}
	
	public static void appendText(String text, String path) {
		try {
			Files.write(Paths.get(path), text.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
