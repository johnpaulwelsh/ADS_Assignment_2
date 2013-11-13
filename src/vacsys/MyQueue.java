package vacsys;

import java.util.LinkedList;

public class MyQueue<Patient> {
	protected LinkedList<Patient> queuedata;

	public MyQueue() {
		this.queuedata = new LinkedList<Patient>();
	}

	public int queueSize() {
		return queuedata.size();
	}
		
	public boolean isEmpty() {
		return queuedata.isEmpty();
	}

	public void enqueue(Patient item) {
		queuedata.add(item);
	}

	public Patient dequeue() {
		return queuedata.remove();
	}
}