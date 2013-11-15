package vacsys;

public class Run {

	public static void main(String[] args) {
		VacSys vs = new VacSys("test1M.csv");
		vs.remove(999999, "result1M.csv");
		System.out.println("Done file remove!");
		vs.insert("John Paul Welsh", 20, 19135);
		vs.remove(1, "result01.csv");
		System.out.println("Done single remove!");
	}
}