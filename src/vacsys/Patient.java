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

	/**
	 * Method to define how two Patient objects can be compared to each other.
	 * 
	 * @return the difference between the priorityVals of the Patients being
	 *         compared. A negative number translates to "less than", a positive
	 *         number translates to "greater than", and 0 translates to "equal".
	 */
	@Override
	public int compareTo(Patient p2) {
		return (this.priorityVal - p2.priorityVal);
	}

	/**
	 * Method to create a string representation of the data in a Patient object.
	 * 
	 * @return a string representation of the Patient, with newline at the end
	 */
	public String toString() {
		return (name + "," + age + "," + zip + "," + priorityVal + "\n");
	}
}