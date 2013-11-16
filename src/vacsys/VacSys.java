package vacsys;

import java.io.*;
import java.util.HashMap;

/*
 * NOTES ON APPROACH
 * *****************
 * 
 * I chose to use a HashMap in this class to keep track of the zpops for every
 * zip code I came across when reading in from a file. I looped through the file
 * once and calculated all the zpops, and looped through again to insert the
 * Patients. Since the Patients' priorityVals needed to be based off of the whole
 * population, not just those already in the system, I needed to know all zpops
 * before inserting any Patients.
 */

/**
 * Class to define a VacSys system.
 * 
 * @author John Paul Welsh
 */
public class VacSys {
	protected VacSysHeap<Patient> vsh;
	protected String filename;
	protected int tpop;
	protected int zpop;
	protected HashMap<String, Integer> ziphash;

	/**
	 * Constructor to create a new blank VacSys and initializing the HashMap for
	 * storing zpops.
	 */
	public VacSys() {
		this.vsh = new VacSysHeap<Patient>();
		this.ziphash = new HashMap<String, Integer>();
		tpop = 0;
	}

	/**
	 * Constructor to create a new VacSys that will be filled with Patients from
	 * a file.
	 * 
	 * @param filename
	 *            path to and name of file from which to import Patients
	 */
	public VacSys(String filename) {
		this.vsh = new VacSysHeap<Patient>();
		this.ziphash = new HashMap<String, Integer>();
		this.filename = filename;
		this.populate();
	}

	/**
	 * Private method to calculate the zpops for Patients read in from a file,
	 * and insert them into the VacSysHeap. Implemented in parameterized
	 * constructor.
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
				String currZip = linelist[2];
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
			reader.close();
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		} catch (ArrayIndexOutOfBoundsException x) {
			System.err.format("BadFileFormat: %s%n", x);
		}

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
						linelist[2], true);
			}
			reader.close();
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		} catch (ArrayIndexOutOfBoundsException x) {
			System.err.format("BadFileFormat: %s%n", x);
		}
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
	public boolean insert(String name, int age, String zip) {
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
	public boolean insert(String name, int age, String zip, boolean readFromFile) {
		// Checks if this zip code is in the hashmap already. We don't do this
		// when readFromFile is true because it was already done in the
		// populate() method
		if (readFromFile == false) {
			if (ziphash.containsKey(zip)) { // ERROR ERROR ERROR
				int currVal = ziphash.get(zip);
				ziphash.put(zip, currVal + 1);
			} else {
				ziphash.put(zip, 1);
			}
			// Increment tpop since normally that would happen in populate()
			tpop++;
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
		// Store the removed Patient (in string form)
		String removed = vsh.remove();
		// Check to make sure the heap is not empty
		if (!(vsh.heapdata.isEmpty())) {
			// Split up the string into pieces
			String[] removedList = removed.split(",");
			// Strips all strings of leading and trailing whitespace
			for (int i = 0; i < removedList.length; i++) {
				removedList[i] = removedList[i].trim();
			}
			// Store the zip code (in Integer form)
			String removedZip = removedList[2];
			// Get the zpop for the removed Patient's zip code
			int currVal = ziphash.get(removedZip);
			// Decrement the zpop for the removed Patient's zip code
			ziphash.put(removedZip, currVal-1);
			// Decrement tpop
			tpop--;
		// If the heap is empty
		} else {
			// Clear out everything, reset all variables
			this.clear();
		}
		return removed;
	}
	
	/**
	 * Method to reset the VacSys once we empty the heap.
	 */
	private void clear() {
		vsh = new VacSysHeap<Patient>();
		ziphash = new HashMap<String, Integer>();
		tpop = 0;
		zpop = 0;
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
		// Checks to make sure we are not trying to remove more Patients than
		// are in the system
		if (num > tpop) {
			num = tpop;
		}
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