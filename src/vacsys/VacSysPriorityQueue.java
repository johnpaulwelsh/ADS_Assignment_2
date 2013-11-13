package vacsys;

public interface VacSysPriorityQueue<Patient> {

	public boolean isEmpty();

	public void insert(Patient item);

	public String remove();
}