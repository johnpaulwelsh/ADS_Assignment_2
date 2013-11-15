package vacsys;

import java.io.*;
import java.util.HashMap;

public class VacSys {
	protected VacSysPriorityQueue<Patient> vsh;
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

	/**
	 * Private method to calculate the zpops for Patients read in from a file,
	 * and insert them into the VacSysHeap.
	 */
	private void populate() {
		// Calculate all zpops and puts them into ziphash
		try {
			String line;
			String[] linelist = null;
			BufferedReader reader = new BufferedReader(new FileReader(
					this.filename));
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
			BufferedReader reader = new BufferedReader(new FileReader(
					this.filename));
			while ((line = reader.readLine()) != null) {
				linelist = line.split(",");

				// Strips all strings of leading and trailing whitespace
				for (int i = 0; i < linelist.length; i++) {
					linelist[i] = linelist[i].trim();
				}

				// Inserts patient (from file, so set boolean to true)
				this.insert(linelist[0], Integer.parseInt(linelist[1]),
						Integer.parseInt(linelist[2]), true);
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		} catch (ArrayIndexOutOfBoundsException x) {
			System.err.format("BadFileFormat: %s%n", x);
		}

		System.out.println("Insert from file is done.");
	}

	/**
	 * Method to insert a patient into the VacSysHeap. Used in 'main,'
	 * implements overloaded method with extra boolean parameter to show that it
	 * is not read in from a file.
	 * 
	 * @param name
	 *            the name of the Patient
	 * @param age
	 *            the age of the Patient
	 * @param zip
	 *            the zip code of the Patient
	 * @return true if calling the other insert method was successful, false
	 *         otherwise
	 */
	public boolean insert(String name, int age, int zip) {
		return this.insert(name, age, zip, false);
	}

	/**
	 * Method to insert a patient into the VacSysHeap. Used in 'populate',
	 * implemented in other 'insert' method.
	 * 
	 * @param name
	 *            the name of the Patient
	 * @param age
	 *            the age of the Patient
	 * @param zip
	 *            the zip code of the Patient
	 * @param readFromFile
	 *            determines whether we are inserting this Patient from a file
	 *            or manually
	 * @return true if the Patient was inserted successfully
	 */
	public boolean insert(String name, int age, int zip, boolean readFromFile) {
		// Checks if this zip code is in the hashmap already
		// We don't do this when readFromFile is true because it was already
		// done in the populate() method
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

	/**
	 * Method to remove one Patient from the VacSysHeap.
	 * 
	 * @return a String representation of the Patient that was removed
	 */
	public String remove() {

		// DECREMENT ENTRY IN ZIPHASH: DO THIS LATER
		/*
		 * int currVal = ziphash.get(vsh.heapdata.get(0).peek().zip);
		 * ziphash.put(vsh.heapdata.get(0).peek().zip, currVal-1);
		 */
		// Decrement tpop
		tpop--;

		return vsh.remove();
	}

	/**
	 * Method to remove multiple Patients from the VacSysHeap and write them to
	 * a file.
	 * 
	 * @param num
	 *            the number of Patients to remove
	 * @param filename
	 *            the path to and name of the file we are writing to
	 * @return true if the removals were successful, false otherwise
	 */
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