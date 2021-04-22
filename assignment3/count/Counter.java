package assignment3.count;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Counter {

	public static void main(String[] args) {
		String dirName = args[0];
		char target = args[1].charAt(0);
		int counter = 0;
		
		String s = readFile(dirName);
		if (s!=null) {
			for (int i = 0; i < s.length(); ++i) {
				if (s.charAt(i) == target) counter++;
			}
			System.out.println("Found " + counter + " " + target + "'s");
		} else {
			System.out.println("Couldn't open file");
		}
		
		
	}
	
	public static String readFile(String path) {
		try {
			return new String(Files.readAllBytes(Paths.get(path)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
