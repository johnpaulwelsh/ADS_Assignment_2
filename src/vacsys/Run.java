package vacsys;

public class Run {
	
	/*
	 * Test cases
	 * Add 1 million, remove 1 million
	 * 	done!
	 * Add 1 million, remove 10
	 * 	done!
	 * Add 1 million, add 1, remove 1 million+1
	 * 	done!
	 * Add 1 million, remove 1 million, add 1, remove 1 to new file
	 * 	done!
	 */

	public static void main(String[] args) {
		VacSys vs = new VacSys("test1M.csv");
		
		//vs.insert("John Paul Welsh", 20, 19135);
		vs.remove(1000000, "result1M.csv");
		System.out.println("Done file remove!");
		
		vs.insert("John Paul Welsh", 20, 19135);
		vs.remove(1, "resultJPW.csv");
		System.out.println("Done single remove!");
		
	}
}