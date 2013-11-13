package vacsys;

public interface VacSysPriorityQueue<T> {

	public boolean isEmpty();

	public void insert(Patient item);

	public String remove();
}