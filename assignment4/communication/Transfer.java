package assignment4.communication;

public class Transfer {

	public static void main(String[] args) {
		Buffer b = new Buffer(7);
		Producer p = new Producer(args[0], b);
		Consumer c = new Consumer(b);
		
		p.start();
		c.start();
		
	}

}
