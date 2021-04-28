package assignment4.doublecheck;

public class Singleton {
	private volatile Singleton instance = null;
	private String s;
	
	private Singleton() {
		setS("Hello ,this is a private constructor.");
	}
	
	public Singleton getInstance() {
		if (instance == null) {
			synchronized(instance) {
				if (instance == null) {
					instance = new Singleton();
				}
			}
		}
		return instance;
		
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}
}
