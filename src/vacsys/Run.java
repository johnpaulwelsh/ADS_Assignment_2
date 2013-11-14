package vacsys;

public class Run {

	public static void main(String[] args) {
		VacSys vs = new VacSys("test1K.csv");
		vs.printHeap();
		//vs.remove(1000, "analysis1K.csv");
		System.out.println("Done!");
	}
}