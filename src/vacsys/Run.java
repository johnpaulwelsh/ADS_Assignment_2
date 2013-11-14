package vacsys;

public class Run {

	public static void main(String[] args) {
		VacSys vs = new VacSys("test1K.csv");
		System.out.println("The initial heap looks like this: ");
		vs.printHeap();
		vs.remove(1000, "analysis.txt");
	}
}