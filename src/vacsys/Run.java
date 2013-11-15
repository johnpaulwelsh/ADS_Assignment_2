package vacsys;

public class Run {

	public static void main(String[] args) {
		VacSys vs = new VacSys("test1M.csv");
		System.out.println("The heap looks like this: ");
		vs.vsh.printHeap();
		vs.remove(1000000, "result1M.csv");
		System.out.println("Done!");
	}
}