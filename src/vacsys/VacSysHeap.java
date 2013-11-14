package vacsys;

import java.util.ArrayList;
import java.util.HashMap;

public class VacSysHeap<T> implements VacSysPriorityQueue<T> {
	protected ArrayList<MyQueue<Patient>> heapdata;
	protected HashMap<Integer, MyQueue<Patient>> queuehash; // key = priorityVal, value = queue

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
			
		// The queuehash already has the queue we want and the heap is not empty
		if (!(heapdata.isEmpty()) && queuehash.containsKey(item.priorityVal)) {
			// Get the queue from queuehash
			MyQueue<Patient> existingQueue = queuehash.get(item.priorityVal);
			// Enqueue the patient
			existingQueue.enqueue(item);
			
		// The queue we need is not in the heap, or the heap is empty
		} else {
			// Make a new queue
			MyQueue<Patient> nQueue = new MyQueue<Patient>();
			// Enqueue the patient
			nQueue.enqueue(item);
			// Add it to the heap (at the end)
			heapdata.add(nQueue);
			// Add it to queuehash
			queuehash.put(nQueue.priorityVal, nQueue);
			
			// If the heap wasn't empty to start with
			if (heapdata.size() > 1) {
				rebuildFromInsert(heapdata.size()-1);
			}
		}
	}

	private void rebuildFromInsert(int index) {
		MyQueue<Patient> child = heapdata.get(index);
		MyQueue<Patient> parent = heapdata.get((index-1)/2);
		if (child.compareTo(parent) > 0) {
			this.swap(child, parent);
			this.rebuildFromInsert((index-1)/2);
		} else {
			return;
		}
	}
	
	private void swap(MyQueue<Patient> child, MyQueue<Patient> parent) {
		MyQueue<Patient> temp = child;
		child = parent;
		parent = temp;
		
		System.out.println("Child: " + child.priorityVal + " swapped with Parent: " + parent.priorityVal);
	}

  	@Override
	public String remove() {
		// Store top queue from heap
		MyQueue<Patient> topQueue = heapdata.get(0);
		
		// POSSIBLY REMOVE FROM ZIPHASH IN HERE, AFTER CHECKING THAT WE NEED TO
		
		// Store next patient from top queue
		Patient removedPat = topQueue.dequeue();
		
		// Could be the case because we just removed the last one in the line above
		if (topQueue.isEmpty()) {
			// Store last queue from heap
			MyQueue<Patient> lastQueue = heapdata.get(heapdata.size()-1);
			// Remove top queue from hash
			queuehash.remove(topQueue);
			// Remove top queue from heap
			heapdata.remove(0);
			// Put the last queue into the top spot
			heapdata.set(0, lastQueue);
			// Trickle down
			rebuildFromRemove(0);
		}
		
		// Finally, return the removed Patient (in String form)
		return removedPat.toString();
	}

	private void rebuildFromRemove(int index) {
		boolean outOfBounds = (index < heapdata.size());
		MyQueue<Patient> parent = heapdata.get(index);
		MyQueue<Patient> leftChild = heapdata.get(2*index+1);
		MyQueue<Patient> rightChild = heapdata.get(2*index+2);
		
		if (!(outOfBounds) && parent.compareTo(leftChild) < 0) {
			swap(parent, leftChild);
			this.rebuildFromRemove(2*index+1);
		} else if (!(outOfBounds) && parent.compareTo(rightChild) < 0) {
			swap(parent, rightChild);
			this.rebuildFromRemove(2*index+2);
		} else {
			return;
		}
	}
}