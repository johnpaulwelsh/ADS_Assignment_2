package vacsys;

public class Dingo {
	
	public static void main(String[] args) {
		VacSys vs = new VacSys("test1M.csv");
		
		vs.remove(1000000, "dingo.csv");
		System.out.println("Done file remove!");
		
		vs.insert("John Paul Welsh", 20, "19135");
		vs.insert("John Paul Jones", 50, "19135");
		vs.insert("John Paul Smith", 70, "19135");
		vs.insert("John Paul Dingo", 90, "19135");
		vs.remove(5, "dango.csv");
		System.out.println("Done single remove!");
	}
}