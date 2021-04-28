package assignment4.communication;

public class Consumer extends Thread {
	private Buffer buffer;
	
	public Consumer(Buffer b) {
		this.buffer = b;
	}
	
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			Integer i = buffer.delete();
			System.out.println("Removed " + i + " from buffer");
		}
	}
}
