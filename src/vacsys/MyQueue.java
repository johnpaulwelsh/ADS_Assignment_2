package vacsys;

import java.util.LinkedList;

public class MyQueue<T> {
	protected LinkedList<Patient> queuedata;
	protected int priorityVal;

	public MyQueue() {
		this.queuedata = new LinkedList<Patient>();
		this.priorityVal = 0;
	}

	public int queueSize() {
		return queuedata.size();
	}
		
	public boolean isEmpty() {
		return queuedata.isEmpty();
	}

	public void enqueue(Patient item) {
		priorityVal = item.priorityVal;
		queuedata.add(item);
	}

	public Patient dequeue() {
		return queuedata.remove();
	}
	
	public Patient peek() {
		return queuedata.peek();
	}
	
	public int compareTo(MyQueue<Patient> q2) {
		// Any negative number translates to "less than"
		// Any positive number translates to "greater than"
		// 0 translates to "equal"
		return (this.priorityVal - q2.priorityVal);
	}
}