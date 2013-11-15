package vacsys;

public class Run {

	public static void main(String[] args) {
		VacSys vs = new VacSys("test1K.csv");
		vs.remove(1000, "result1K.csv");
		System.out.println("Done!");
	}
}