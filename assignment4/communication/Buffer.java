package assignment4.communication;

import java.util.ArrayDeque;

public class Buffer {
	private Integer maxSize = 0;
	private volatile ArrayDeque<Integer> buffer = new ArrayDeque<>();

	public Buffer(Integer size) {
		super();
		this.maxSize = size;
	}

	// We use method level synchronization to make the buffer class thread safe
	// This is essentially block level synchronization on the Producer/Consumer class level
	public synchronized void insert(Integer e) {
		try {
			while (buffer.size() == maxSize) {
				wait();
			}
			buffer.add(e);
			notify();			
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	public synchronized Integer delete() {
		try {
			while (buffer.isEmpty()) {
				wait();
			}
			Integer front = buffer.getFirst();
			buffer.remove();
			notify();
			return front;
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
