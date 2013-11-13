package vacsys;

import java.util.ArrayList;
import java.util.HashMap;

public class VacSysHeap<T> implements VacSysPriorityQueue<T> {
	protected ArrayList<MyQueue<Patient>> heapdata;
	protected HashMap<Integer, MyQueue<Patient>> queuehash;

	public VacSysHeap() {
		this.heapdata = new ArrayList<MyQueue<Patient>>();
		this.queuehash = new HashMap<Integer, MyQueue<Patient>>();
	}

	@Override
	public boolean isEmpty() {
		return heapdata.isEmpty();
	}

	@Override
	public void insert(Patient item) {
		if (queuehash.containsKey(item.priorityVal)) {
			MyQueue<Patient> existingQueue = queuehash.get(item.priorityVal);
			existingQueue.enqueue(item);
		} else {
			MyQueue<Patient> nQueue = new MyQueue<Patient>();
			heapdata.add(heapdata.size()-1, nQueue); // heapdata.size()-1 is the last element
			rebuildFromInsert(heapdata.size()-1);
		}
	}

	private void rebuildFromInsert(int index) {
		Patient child = heapdata.get(index).peek();
		Patient parent = heapdata.get((index-1)/2).peek();
		if (child.compareTo(parent) > 0) {
			this.swap(child, parent);
			this.rebuildFromInsert((index-1)/2);
		} else {
			return;
		}
	}
	
	private void swap(Patient child, Patient parent) {
		Patient temp = child;
		child = parent;
		parent = temp;
	}

	@Override
	public String remove() {
		MyQueue<Patient> topQueue = heapdata.get(0);
		MyQueue<Patient> lastQueue = heapdata.get(heapdata.size()-1);
		queuehash.remove(topQueue);
		heapdata.remove(0);
		heapdata.set(0, lastQueue);
		rebuildFromRemove(0);
		return topQueue.toString();
	}

	private void rebuildFromRemove(int index) {
		
	}
}