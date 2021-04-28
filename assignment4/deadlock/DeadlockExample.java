package assignment4.deadlock;

public class DeadlockExample {
	volatile public static Integer x = 1;
	volatile public static Integer y = 4;
	
	
	public static void main(String[] args) {
		Runnable t1 = new Runnable(){
			public void run() {
				synchronized(x) {
					try {
						Thread.sleep(100);
						System.out.println("Lock acquired for x");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized(y) {
						y = x * 2;
						System.out.println(x + "," + y);
					}
				}
			}
		};
		
		Runnable t2 = new Runnable() {
			public void run() {
				synchronized(y) {
					try {
						Thread.sleep(200);
						System.out.println("Lock acquired for y");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					synchronized(x) {
						x = y * 2;
						System.out.println(x + "," + y);
					}
				}
			}
 		};
 		
 		Thread a = new Thread(t1);
 		Thread b = new Thread(t2);
 		
 		a.start();
 		b.start();
	}

}
