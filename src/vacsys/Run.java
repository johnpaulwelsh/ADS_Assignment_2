package vacsys;

public class Run {

	public static void main(String[] args) {
		VacSys vs = new VacSys("test1M.csv");
		vs.remove(1000, "results1M.csv");
	}
}