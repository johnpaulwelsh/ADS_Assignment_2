package vacsys;

public class Run {

	public static void main(String[] args) {
		VacSys vs = new VacSys("test1K.csv");
		vs.remove(900, "result1K.csv");
	}
}