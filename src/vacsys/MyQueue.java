package vacsys;

import java.util.LinkedList;

/*
 * NOTES ON APPROACH
 * *****************
 * 
 * This is a class I wrote myself to represent a queue. I used a LinkedList as the
 * underlying data storage structure because the only thing I need to do to a queue
 * is add to one end and remove from the opposite end. LinkedLists lend very well
 * to FIFO data structures since we cannot access the internal nodes directly, nor
 * would we need to for this exercise.
 * The MyQueue also has its own compareTo() method and priorityVal instance variable.
 * This is because, sometimes, I need to know the priorityVal for a queue even after
 * I've emptied the queue of all its Patients. Normally I could just peek at the queue
 * but this will not work when the queue is empty, so as soon as the queue gains a
 * member it adopts that member's priority value.
 */

/**
 * Class to define a MyQueue object.
 * 
 * @author John Paul Welsh
 */
public class MyQueue<T> {
	protected LinkedList<Patient> queuedata;
	protected int priorityVal;

	/**
	 * Constructor to create a new MyQueue. Sets the initial priority value
	 * to zero.
	 */
	public MyQueue() {
		this.queuedata = new LinkedList<Patient>();
		this.priorityVal = 0;
	}

	/**
	 * Method that returns the size of the queue.
	 * 
	 * @return the number of elements in the queue
	 */
	public int queueSize() {
		return queuedata.size();
	}

	/**
	 * Method to determine whether the queue is empty.
	 * 
	 * @return true if the queue is empty, false otherwise
	 */
	public boolean isEmpty() {
		return queuedata.isEmpty();
	}

	/**
	 * Method to add a Patient object to the beginning of the queue. Also sets
	 * the priorityVal for the queue, since we want to be able to compare queues
	 * even if they have been emptied of all their Patients.
	 * 
	 * @param item
	 *            the Patient being added
	 */
	public void enqueue(Patient item) {
		priorityVal = item.priorityVal;
		queuedata.add(item);
	}

	/**
	 * Method to remove a Patient object from the end of the queue.
	 * 
	 * @return the Patient being removed
	 */
	public Patient dequeue() {
		return queuedata.remove();
	}

	/**
	 * Method to allow us to see the first Patient in the queue.
	 * 
	 * @return the first Patient in the queue, without removing it
	 */
	public Patient peek() {
		return queuedata.peek();
	}

	/**
	 * Method to define how two queues can be compared to each other.
	 * 
	 * @return the difference between the priorityVals of the queues being
	 *         compared. A negative number translates to "less than", a positive
	 *         number translates to "greater than", and 0 translates to "equal".
	 */
	public int compareTo(MyQueue<Patient> q2) {
		return (this.priorityVal - q2.priorityVal);
	}
}