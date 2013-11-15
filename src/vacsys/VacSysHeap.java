package vacsys;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * NOTES ON APPROACH
 * *****************
 * 
 * I chose to use an ArrayList as the main storage for the heap because it allows
 * me to access specific indices so I can find the parent-child relationships
 * with ease. However, I did not want to have to handle resizing the array myself,
 * so I imported ArrayList to do it for me.
 * I have another HashMap in this class to pair together priority values and
 * entries in the heap. This HashMap is updated as I insert MyQueue objects. This
 * way, I do not have to traverse the heap every time I want to look for a certain
 * queue, such as when I am adding a new Patient and the queue it belongs in already
 * exists.
 */

/**
 * Class to define a VacSysHeap object, which implements a VacSysPriorityQueue.
 * 
 * @author John Paul Welsh
 */
public class VacSysHeap<T> implements VacSysPriorityQueue<T> {
	protected ArrayList<MyQueue<Patient>> heapdata;
	protected HashMap<Integer, MyQueue<Patient>> queuehash;

	/**
	 * Constructor to create a new VacSysHeap and initialize the HashMap for
	 * storing queues.
	 */
	public VacSysHeap() {
		this.heapdata = new ArrayList<MyQueue<Patient>>();
		this.queuehash = new HashMap<Integer, MyQueue<Patient>>();
	}

	/**
	 * Method to determine whether the heap is empty.
	 * 
	 * @return true if the heap has no elements, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return heapdata.isEmpty();
	}

	/**
	 * Method to determine the size of the heap.
	 * 
	 * @return the number of elements in the heap.
	 */
	@Override
	public int heapSize() {
		return heapdata.size();
	}

	/**
	 * Method to insert a Patient into the heap.
	 * 
	 * @param item
	 *            the Patient being inserted
	 */
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

	/**
	 * Private recursive method to reorder the heap after inserting a new
	 * MyQueue, because the Patient we are inserting needed a new queue.
	 * 
	 * @param index
	 *            the index of the heap that we are checking against its parent.
	 *            Recursively changes to travel up the heap
	 */
	private void rebuildFromInsert(int index) {
		MyQueue<Patient> child = heapdata.get(index);
		MyQueue<Patient> parent = heapdata.get((index - 1) / 2);
		// If the child is greater than the parent
		if (child.compareTo(parent) > 0) {
			// Swap them
			this.swap(child, parent, index, (index - 1) / 2);
			// Continue with recursion starting with the parent
			this.rebuildFromInsert((index - 1) / 2);
		} else {
			return;
		}
	}

	/**
	 * Private method to swap two MyQueues in the heap. Used in
	 * rebuildFromInsert and rebuidlFromRemove.
	 * 
	 * @param child
	 *            the child queue
	 * @param parent
	 *            the parent queue
	 * @param childIndex
	 *            the index of the heap where the child currently is
	 * @param parentIndex
	 *            the index of the heap where the parent currently is
	 */
	private void swap(MyQueue<Patient> child, MyQueue<Patient> parent,
			int childIndex, int parentIndex) {
		MyQueue<Patient> temp = child;
		heapdata.set(childIndex, parent);
		heapdata.set(parentIndex, temp);
	}

	/**
	 * Method to remove a Patient from the heap.
	 * 
	 * @return a to-string interpretation of the Patient being removed
	 */
	@Override
	public String remove() {
		// Store top queue from heap
		MyQueue<Patient> topQueue = heapdata.get(0);
		
		// Store next patient from top queue
		Patient removedPat = topQueue.dequeue();

		// If the top queue is empty, we need to get rid of the element
		// in the heap. Note: this usually ends up happening because we
		// just removed the last one in the line above
		if (topQueue.isEmpty()) {
			// Store last queue from heap
			MyQueue<Patient> lastQueue = heapdata.get(heapdata.size() - 1);
			// Remove top queue from hash
			queuehash.remove(topQueue);
			// Move last queue into top spot and remove it
			heapdata.set(0, lastQueue);
			heapdata.remove(heapdata.size() - 1);
			// Trickle down (if the heap is not empty)
			if (heapdata.size() != 0) {
				this.rebuildFromRemove(0);
			}
		}

		// Finally, return the removed Patient (in String form)
		return removedPat.toString();
	}

	/**
	 * Private recursive method to reorder the heap after removing a MyQueue,
	 * because the MyQueue is now empty of its Patients.
	 * 
	 * @param index
	 *            the index of the heap that we are checking against its
	 *            children. Recursively changes to travel down the heap
	 */
	private void rebuildFromRemove(int index) {
		MyQueue<Patient> parent = heapdata.get(index);

		// Boolean variables that are true if the currant parent's left or right
		// child do not exist (their ArrayList index is out of bounds)
		boolean rightOOB = (2 * index + 2 >= heapdata.size());
		boolean leftOOB = (2 * index + 1 >= heapdata.size());

		// Neither the left nor right child is out of bounds, they both exist
		if (!leftOOB && !rightOOB) {
			MyQueue<Patient> leftChild = heapdata.get(2 * index + 1);
			MyQueue<Patient> rightChild = heapdata.get(2 * index + 2);
			// If the left child is bigger than the right child
			if (leftChild.compareTo(rightChild) > 0) {
				// Check if we need to swap the parent and child
				if (leftChild.compareTo(parent) > 0) {
					// Swap them
					this.swap(leftChild, parent, 2 * index + 1, index);
					// Continue recursion with the original parent's left child
					this.rebuildFromRemove(2 * index + 1);
				}
			// If the right child is bigger than the left child
			} else {
				// Check if we need to swap the parent and child
				if (rightChild.compareTo(parent) > 0) {
					// Swap them
					this.swap(rightChild, parent, 2 * index + 2, index);
					// Continue recursion with the original parent's right child
					this.rebuildFromRemove(2 * index + 2);
				}
			}
		// The right child is out of bounds, the left exists
		} else if (!leftOOB && rightOOB) {
			MyQueue<Patient> leftChild = heapdata.get(2 * index + 1);
			// Check to see if we need to swap the parent and child
			if (leftChild.compareTo(parent) > 0) {
				// Swap the parent with the left child
				this.swap(leftChild, parent, 2 * index + 1, index);
			}
		// Both the left child and right child are out of bounds
		} else {
			// You're done!
			return;
		}
	}
}