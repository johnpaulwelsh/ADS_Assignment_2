package vacsys;

import java.util.ArrayList;
import java.util.HashMap;

public class VacSysHeap<Patient> implements
VacSysPriorityQueue<Patient> {
	protected ArrayList<MyQueue<Patient>> heapdata;
	protected HashMap<Integer, MyQueue<Patient>> queuehash;

	public VacSysHeap() {
		this.heapdata = new ArrayList<MyQueue<Patient>>();
		this.ziphash = new HashMap<Integer, Integer>();
		this.queuehash = new HashMap<Integer, MyQueue<Patient>>();
	}

	@Override
	public boolean isEmpty() {
		return heapdata.isEmpty();
	}

	@Override
	public void insert(Patient item) {
		/*
		 * PSEUDOCODE 
		 * ==========
		 * 
		 * Check if the queuehash already has a queue with this priority value
		 *   If so, get the MyQueue from the hashmap and add the new element
		 *   If not, make a new MyQueue object, enqueue the item, and add it to
		 *     the queuehash, put it at the end of the heap (farthest left
		 *     available leaf spot), and rebuild the heap to make sure the
		 *     MyQueue is in the right spot
		 */
	}

	private void addToQueue(T item, int queuePriority) {

	}

	private void rebuildFromInsert() {
		// recursion!
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
			result = result + removedList[j].name + ", "
					+ removedList[j].age + ", "
					+ removedList[j].zip + ", "
					+ removedList[j].priorityVal + "\n";
		}
		
		return result;
		*/
	}

	private void rebuildFromRemove() {
		
	}
}