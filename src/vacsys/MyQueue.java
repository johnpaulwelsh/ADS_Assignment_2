package vacsys;

import java.util.LinkedList;

public class MyQueue<T> {
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
	
	public Patient peek() {
		return queuedata.peek();
	}
	
	public String toString() {
		String result = "";
		for (int i = 0; i < this.queueSize(); i++) {
			result.concat(queuedata.get(i).toString());
		}
		return result;
	}
}