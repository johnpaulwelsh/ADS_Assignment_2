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
		queuedata.add(item);
		priorityVal = item.priorityVal;
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
	
	public String toString() {
		String result = "";
		for (int i = 0; i < this.queueSize(); i++) {
			result.concat(queuedata.get(i).toString());
		}
		return result;
	}
}