package vacsys;

/**
 * Class to define a VacSysPriorityQueue interface.
 * 
 * @author John Paul Welsh
 */
public interface VacSysPriorityQueue<T> {

	/**
	 * Method to determine whether the priority queue is empty.
	 * 
	 * @return true if the priority queue has no elements, false otherwise
	 */
	public boolean isEmpty();

	/**
	 * Method to determine the size of the priority queue.
	 * 
	 * @return the number of elements in the priority queue
	 */
	public int heapSize();

	/**
	 * Method to insert a Patient into the priority queue.
	 * 
	 * @param item
	 *            the Patient being inserted
	 */
	public void insert(Patient item);

	/**
	 * Method to remove a Patient from the priority queue.
	 * 
	 * @return a to-string interpretation of the Patient being removed
	 */
	public String remove();
}