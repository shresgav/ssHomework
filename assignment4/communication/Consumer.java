package assignment4.communication;

public class Consumer extends Thread {
	private Buffer buffer;
	
	public Consumer(Buffer b) {
		this.buffer = b;
	}
	
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			Integer i = buffer.delete();
			if (i == null) break;
			System.out.println("Removed " + i + " from buffer");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
