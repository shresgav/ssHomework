package assignment4.communication;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Producer extends Thread {
	private Buffer buffer;
	private Scanner sc;
	
	public Producer(String f, Buffer b) {
		try {
			this.sc = new Scanner(new File(f));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.buffer = b;
	}
	
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			if (sc.hasNextLine()) {
				Integer next = Integer.parseInt(sc.nextLine());
				buffer.insert(next);
				System.out.println("New integer " + next + " added");
			} else {
				System.out.println("Reached EOF");
				break;
			}
		}
	}
}
