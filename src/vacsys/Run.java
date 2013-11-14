package vacsys;

public class Run {

	public static void main(String[] args) {
		VacSys vs = new VacSys("test1M.csv");
		vs.printHeap();
		//vs.remove(1000, "analysis.txt");
	}
}