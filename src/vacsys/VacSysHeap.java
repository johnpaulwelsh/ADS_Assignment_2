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

	public void printHeap() {
		for (int i = 0; i < heapdata.size(); i++) {
			System.out.println("Queue: " + heapdata.get(i).priorityVal);
		}
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
		// If the child is greater than the parent
		if (child.compareTo(parent) > 0) {
			// Swap them
			this.swap(child, parent, index, (index-1)/2);
			// Continue with recursion starting with the parent
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
			// Trickle down (if the heap is not empty)
			if (heapdata.size() != 0) {
				this.rebuildFromRemove(0);
			}
			
			System.out.println("Done rebuilding.");

			for (int i = 0; i < heapdata.size(); i++) {
				System.out.println("Queue: " + heapdata.get(i).priorityVal);
			}
		}

		// Finally, return the removed Patient (in String form)
		return removedPat.toString();
	}

	private void rebuildFromRemove(int index) {
		MyQueue<Patient> parent = heapdata.get(index);
		
		// Boolean variables that are true if the currant parent's left or right child
		// do not exist (their ArrayList index is out of bounds)
		boolean rightOOB = (2*index+2 >= heapdata.size());
		boolean leftOOB = (2*index+1 >= heapdata.size());
		
		// Neither the left nor right child is out of bounds, they both exist
		if (!leftOOB && !rightOOB) {
			MyQueue<Patient> leftChild = heapdata.get(2*index+1);
			MyQueue<Patient> rightChild = heapdata.get(2*index+2);
			// If the left child is bigger than the right child
			if (leftChild.compareTo(rightChild) > 0) {
				// Check if we need to swap the parent and child
				if (leftChild.compareTo(parent) > 0) {
					// Swap them
					this.swap(leftChild, parent, 2*index+1, index);
					// Continue recursion with the original parent's left child
					this.rebuildFromRemove(2*index+1);
				}
			// If the right child is bigger than the left child	
			} else {
				// Check if we need to swap the parent and child
				if (rightChild.compareTo(parent) > 0) {
					// Swap them
					// HAHAHAHAHAHAHA I LEFT THIS AS LEFTCHILD AND 2*INDEX+1 HAHAHAHAHAHAHAHA
					this.swap(rightChild, parent, 2*index+2, index);
					this.rebuildFromRemove(2*index+2);
				}
				
			}
		
		// The right child is out of bounds, the left exists
		} else if (!leftOOB && rightOOB) {
			MyQueue<Patient> leftChild = heapdata.get(2*index+1);
			// Check to see if we need to swap the parent and child
			if (leftChild.compareTo(parent) > 0) {
				// Swap the parent with the left child
				this.swap(leftChild, parent, 2*index+1, index);
			}
			
		// Both the left child and right child are out of bounds of the Arraylist
		} else {
			// You're done!
			return;
		}
		
		// left and right are null
		// right is null
		//     that means left is leaf, swap and you're done
		// neither are null
		//     pick which one is bigger!!!
	}
}