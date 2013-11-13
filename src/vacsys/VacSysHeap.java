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
		
		
		
		
		
		
		
		
		
		
		
		
		// REMOVE THE ENTRY FROM BOTH HASHMAPS
		
		/*
			String result = "";
			
			// We use .get(0) because we always remove from the top element
			Patient[] removedList = new Patient[heapdata.get(0).queueSize()];
			// Takes all the entries in the top MyQueue and puts them into an
			// array of removed elements
			for (int i = 0; i < heapdata.get(0).queueSize(); i++) {
				removedList[i] = (Patient) heapdata.get(0).dequeue();
			}
			// If there are no more elements in the queue
			if (heapdata.get(0).isEmpty()) {
				// Take it out of the heap (remove the node)
				heapdata.remove(0);
				// Put the last element into the first spot
				heapdata.add(0, heapdata.get(heapdata.size()-1));
				// Rebuild
				this.rebuildFromRemove();
				// reassign hashmaps
			}
			
			// Compile the list of removed elements into a string which we will return
			for (int j = 0; j < removedList.length; j++) {
				result = result + removedList[j].toString();
			}
			
			return result;
		*/
	}

	private void rebuildFromRemove() {
		
	}
}