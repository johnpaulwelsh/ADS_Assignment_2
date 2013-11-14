package vacsys;

import java.io.*;
import java.util.HashMap;

public class VacSys {
	protected VacSysHeap<Patient> vsh;
	protected String filename;
	protected int tpop;
	protected int zpop;
	protected HashMap<Integer, Integer> ziphash;

	public VacSys() {
		this.vsh = new VacSysHeap<Patient>();
		this.ziphash = new HashMap<Integer, Integer>();
		tpop = 0;
	}

	public VacSys(String filename) {
		this.vsh = new VacSysHeap<Patient>();
		this.ziphash = new HashMap<Integer, Integer>();
		this.filename = filename;
		this.populate();
	}

	private void populate() {
		// Calculate all zpops and puts them into ziphash
		try {
			String line;
			String[] linelist = null;
			BufferedReader reader = new BufferedReader(new FileReader(this.filename));
			while ((line = reader.readLine()) != null) {
				linelist = line.split(",");
				
				// Strips all strings of leading and trailing whitespace
				for (int i = 0; i < linelist.length; i++) {
					linelist[i] = linelist[i].trim();
				}

				// Checks if this zip code is in the hashmap already
				int currZip = Integer.parseInt(linelist[2]);
				// If it is, increment keyed value
				if (ziphash.containsKey(currZip)) {
					int currVal = ziphash.get(currZip);
					ziphash.put(currZip, currVal + 1);
				// If not, enter it as a new key and set it to 1
				} else {
					ziphash.put(currZip, 1);
				}
				
				// Increment tpop
				tpop++;
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		} catch (ArrayIndexOutOfBoundsException x) {
			System.err.format("BadFileFormat: %s%n", x);
		}
		
		System.out.println("Zpop mapping is done.");
		
		// Insert Patients
		try {
			String line;
			String[] linelist = null;
			BufferedReader reader = new BufferedReader(new FileReader(this.filename));
			while ((line = reader.readLine()) != null) {
				linelist = line.split(",");
				
				// Strips all strings of leading and trailing whitespace
				for (int i = 0; i < linelist.length; i++) {
					linelist[i] = linelist[i].trim();
				}
								
				// Inserts patient (from file, so set boolean to true)
				this.insert(linelist[0],
					Integer.parseInt(linelist[1]),
					Integer.parseInt(linelist[2]),
					true
					);
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		} catch (ArrayIndexOutOfBoundsException x) {
			System.err.format("BadFileFormat: %s%n", x);
		}
		
		System.out.println("Insert from file is done.");
	}

	/**
	 * Method to insert a patient into the VacSysHeap. Used in 'main,' implements
	 * overloaded method with extra boolean parameter to show that it is not read
	 * in from a file.
	 * 
	 * @param name
	 * @param age
	 * @param zip
	 * @return
	 */
	public boolean insert(String name, int age, int zip) {
		this.insert(name, age, zip, false);
		return true;
	}
	
	/**
	 * Method to insert a patient into the VacSysHeap. Used in 'populate', implemented
	 * in other 'insert' method.
	 * @param name
	 * @param age
	 * @param zip
	 * @param readFromFile
	 * @return
	 */
	public boolean insert(String name, int age, int zip, boolean readFromFile) {
		// Checks if this zip code is in the hashmap already
		// We don't do this when readFromFile == true because it was already done
		// in the populate() method
		if (readFromFile == false) {
			if (ziphash.containsKey(zip)) {
				int currVal = ziphash.get(zip);
				ziphash.put(zip, currVal + 1);
			} else {
				ziphash.put(zip, 1);
			}	
		}
		
		// Calculates zpop for this patient
		zpop = ziphash.get(zip);
		float fzpop = (float) zpop;
		float ftpop = (float) tpop;
		int priorityVal = (int) ((Math.abs(35 - age) / 5.0) + ((fzpop / ftpop) * 10.0));

		Patient p = new Patient(name, age, zip, priorityVal);
		vsh.insert(p);
		return true;
	}
	
	public void printHeap() {
		for (int i = 0; i < vsh.heapdata.size(); i++) {
			System.out.println("Queue: " + vsh.heapdata.get(i).priorityVal);
		}
	}

	public String remove() {
		System.out.println("Inside vacsys.remove()");
		
		// DECREMENT ENTRY IN ZIPHASH
		int currVal = ziphash.get(vsh.heapdata.get(0).peek().zip);
		ziphash.put(vsh.heapdata.get(0).peek().zip, currVal-1);
		
		// Decrement tpop
		tpop--;
		
		return vsh.remove();
	}

	public boolean remove(int num, String filename) {
		FileWriter fw;
		try {
			fw = new FileWriter(filename);
			// Adds each removed patient to a file
			for (int n = 0; n < num; n++) {
				fw.append(this.remove());
			}
			fw.flush();
			fw.close();
			return true;
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
	}
}