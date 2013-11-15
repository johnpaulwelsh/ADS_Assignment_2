package vacsys;

import java.util.ArrayList;
import java.util.HashMap;

public class VacSysHeap<T> implements VacSysPriorityQueue<T> {
	protected ArrayList<MyQueue<Patient>> heapdata;
	protected HashMap<Integer, MyQueue<Patient>> queuehash;

	// key = priorityVal, value = queue

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
				this.rebuildFromInsert(heapdata.size() - 1);
			}
		}
	}

	private void rebuildFromInsert(int index) {
		MyQueue<Patient> child = heapdata.get(index);
		MyQueue<Patient> parent = heapdata.get((index-1)/2);
		if (child.compareTo(parent) > 0) {
			this.swap(child, parent, index, (index-1)/2);
			this.rebuildFromInsert((index-1)/2);
		} else {
			return;
		}
	}

	private void swap(MyQueue<Patient> child, MyQueue<Patient> parent,
			int childIndex, int parentIndex) {
		MyQueue<Patient> temp = child;
		heapdata.set(childIndex, parent);
		heapdata.set(parentIndex, temp);
		System.out.println("Child: " + child.priorityVal
				+ " swapped with Parent: " + parent.priorityVal);
	}

	@Override
	public String remove() {
		// Store top queue from heap
		MyQueue<Patient> topQueue = heapdata.get(0);

		// POSSIBLY REMOVE FROM ZIPHASH IN HERE, AFTER CHECKING THAT WE NEED TO
		// Otherwise, do it in VacSys.java where the code already is

		// Store next patient from top queue
		Patient removedPat = topQueue.dequeue();

		// Could be the case because we just removed the last one
		if (topQueue.isEmpty()) {

			System.out.println("The top queue is now empty.");

			// Store last queue from heap
			MyQueue<Patient> lastQueue = heapdata.get(heapdata.size() - 1);
			// Remove top queue from hash
			queuehash.remove(topQueue);
			// Remove top queue from heap
			//heapdata.remove(0);
			// Move last queue into top spot and remove it
			heapdata.set(0, lastQueue);
			heapdata.remove(heapdata.size() - 1);
			// Trickle down
			this.rebuildFromRemove(0);

			System.out.println("Done rebuilding.");

			for (int i = 0; i < heapdata.size(); i++) {
				System.out.println("Queue: " + heapdata.get(i).priorityVal);
			}
		}

		// Finally, return the removed Patient (in String form)
		return removedPat.toString();
	}

	// HERE BE THE ERROR ------V
	private void rebuildFromRemove(int index) {
		
		boolean rightOOB = (2*index+2 >= heapdata.size());
		boolean leftOOB = (2*index+1 >= heapdata.size());
		
		MyQueue<Patient> parent = heapdata.get(index);
		
		// Both the left child and right child are out of bounds of the Arraylist
		if (leftOOB && rightOOB) {
			// You're done!
			return;
		
		// The right child is out of bounds, the left exists
		} else if (rightOOB) {
			MyQueue<Patient> leftChild = heapdata.get(2*index+1);
			this.swap(leftChild, parent, 2*index+1, index);
		
		// Neither are out of bounds, they both exist
		} else {
			MyQueue<Patient> leftChild = heapdata.get(2*index+1);
			MyQueue<Patient> rightChild = heapdata.get(2*index+2);
			// Check to see which child is bigger, swap with that one
			if (leftChild.priorityVal > rightChild.priorityVal) {
				this.swap(leftChild, parent, 2*index+1, index);
			} else {
				this.swap(leftChild, parent, 2*index+1, index);
			}
		}
		
		// left and right are null
		// right is null
		//     that means left is leaf, swap and you're done
		// neither are null
		//     pick which one is bigger!!!
		
		/*
		boolean outOfBounds = (2*index+1 >= heapdata.size());
		MyQueue<Patient> parent = heapdata.get(index);
		MyQueue<Patient> leftChild = heapdata.get(2*index+1);
		MyQueue<Patient> rightChild = heapdata.get(2*index+2);
		
		// We are not out of bounds AND the left child is bigger than the parent
		if (!(outOfBounds) && leftChild.compareTo(parent) > 0) {
			// Switch the parent with its left child
			this.swap(leftChild, parent, 2*index+1, index);
			// Continue recursion, starting with where the parent is now
			this.rebuildFromRemove(2*index+1);

		// We are not out of bounds AND the right child is bigger than the parent
		} if (!(outOfBounds) && rightChild.compareTo(parent) > 0) {
			// Switch the parent with its right child
			this.swap(rightChild, parent, 2*index+2, index);
			// Continue recursion, starting with where the parent is now
			this.rebuildFromRemove(2*index+2);
		
		// Either the current index is out of bounds OR
		// 		the element is where it needs to be
		} else {
			// STOP
			return;
		}
		*/
	}

	public void printHeap() {
		for (int i = 0; i < heapdata.size(); i++) {
			System.out.println("Queue: " + heapdata.get(i).priorityVal);
		}
	}
}