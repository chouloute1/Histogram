package com.heb.histogram;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The Histogram class to show how many times a word occurs in a file.
 */
public class Histogram {

	public static void main(String[] args) {

		String inputFileName = "resources/input.txt";
		String outputFileName = "resources/ouput.txt";
		Map<String, Integer> map = new HashMap<String, Integer>();
		boolean decending=false;
		String[] myArray;

		//Passes array of Strings from text file
		myArray = readFile(inputFileName);

		//Adds String and count as key value pairs
		map = addToMap(myArray);

		//Sorts map in descending order
		Map<String, Integer> sortedMapDesc = sortByComparator(map, decending);

		//Writes sorted keys and values in appropriate format to file
		writeFile(outputFileName, sortedMapDesc);

	}

	
	
	
	/**
	 * Reads from text file in applied directory
	 *
	 * @param inputFileName  File path
	 * 
	 * @return String[] the String array formed from file
	 */
	private static String[] readFile(String inputFileName) {

		BufferedReader br = null;
		FileReader fr = null;
		String unparsedFile = "";
		String[] myArray;

		try {

			fr = new FileReader(inputFileName);
			br = new BufferedReader(fr);

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				unparsedFile += sCurrentLine;
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		//split all spaces, commas and dots then return String Array
		return myArray = unparsedFile.toLowerCase().split("\\s+|\\.|\\,");

	}

	
	
	/**
	 * Creates and writes to a text file
	 *
	 * @param outputFileName  Path to create file
	 *            
	 * @param map   used to transfer values to text file     
	 *            
	 * @return void
	 */
	private static void writeFile(String outputFileName, Map<String, Integer> map) {

		BufferedWriter bw = null;
		FileWriter fw = null;
		int count = 0;
		int recordsToPrint = map.size();
		Iterator<Entry<String, Integer>> it = map.entrySet().iterator();

		try {

			fw = new FileWriter(outputFileName);
			bw = new BufferedWriter(fw);

			while (it.hasNext() && count < recordsToPrint) {
				Map.Entry<String, Integer> pairs = it.next();

				//writes string
				bw.write(pairs.getKey() + " | ");
				
				//loop to add "=" sign the appropriate number of times
				for (int i = 0; i < pairs.getValue(); i++) {
					bw.write("=");
				}

				//writes count
				bw.write(" (" + pairs.getValue() + ")");
				bw.newLine();

				// increment the record count once we have printed to the file
				count++;
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}

	
	
	/**
	 * Adds String array to Map
	 *
	 * @param str  String array with all values
	 *            
	 * @return Map<String, Integer>  Map with all values
	 */
	private static Map<String, Integer> addToMap(String[] str) {

		Map<String, Integer> map = new HashMap<String, Integer>();

		for (String item : str) {

			if (item.equals("")) {
				continue;
			}

			int count = map.containsKey(item) ? map.get(item) : 1;

			if (!map.containsKey(item)) {
				map.put(item, count);
			} else {
				map.put(item, ++count);
			}
		}

		return map;
	}

	
	
	/**
	 * Increment a value by delta and return the new value.
	 *
	 * @param unsortMap  this is an unsorted Map
	 *            
	 * @param order   true return ascending false returns descending           
	 *            
	 * @return Map<String, Integer> sorted Map
	 */
	private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order) {

		List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				if (order) {
					return o1.getValue().compareTo(o2.getValue());
				} else {
					return o2.getValue().compareTo(o1.getValue());

				}
			}
		});

		// Maintaining insertion order with the help of LinkedList
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Entry<String, Integer> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

}
