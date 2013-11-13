package vacsys;

public class Patient implements Comparable<Patient> {
	protected String name;
	protected int age;
	protected int zip;
	protected int priorityVal;

	public Patient(String name, int age, int zip, int priorityVal) {
		this.name = name;
		this.age = age;
		this.zip = zip;
		this.priorityVal = priorityVal;
	}

	@Override
	public int compareTo(Patient p2) {
		// Any negative number translates to "less than"
		// Any positive number translates to "greater than"
		// 0 translates to "equal"
		return (this.priorityVal - p2.priorityVal);
	}
}