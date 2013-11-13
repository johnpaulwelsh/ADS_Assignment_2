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
		this.filename = filename;
		this.populate();
	}

	private void populate() {
		// Calculate all zpops and puts them into ziphash
		try {
			String line;
			String[] linelist = null;
			BufferedReader reader = new BufferedReader(new FileReader(
					this.filename));
			while ((line = reader.readLine()) != null) {
				linelist = line.split(",");
				
				int currZip = Integer.parseInt(linelist[2]);
				if (ziphash.containsKey(currZip)) {
					int currVal = ziphash.get(currZip);
					ziphash.put(currZip, currVal + 1);
				} else {
					ziphash.put(currZip, 1);
				}
				tpop++;
			}
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
				
				this.insert(linelist[0],
					Integer.parseInt(linelist[1]),
					Integer.parseInt(linelist[2])
					);
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		} catch (ArrayIndexOutOfBoundsException x) {
			System.err.format("BadFileFormat: %s%n", x);
		}
	}

	public boolean insert(String name, int age, int zip) {
		zpop = ziphash.get(zip);
		float fzpop = (float) zpop;
		float ftpop = (float) tpop;
		int priorityVal = (int) ((Math.abs(35 - age) / 5.0) + ((fzpop / ftpop) * 10.0));

		Patient p = new Patient(name, age, zip, priorityVal);
		vsh.insert(p);
		return true;
	}

	public String remove() {
		ziphash.remove(vsh.heapdata.get(0).peek().zip);
		return vsh.remove();
	}

	public boolean remove(int num, String filename) {
		FileWriter fw;
		try {
			fw = new FileWriter(filename);
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