package uk.ac.lboro.jakerussell.cas.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

/**
 * LogFileUtils is responsible for all of the different operations to do with
 * the Log File
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public class LogFileUtils {

	/**
	 * Writes a line to the log file, given the data lines to add in an ArrayList
	 * 
	 * @param dataToAdd an ArrayList of the data lines that need to be added to the
	 *                  log file
	 */
	public static void writeToLogFile(ArrayList<String> dataToAdd) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		ArrayList<String> allLogFileData = extractLogData();
		for (String str : dataToAdd) {
			str += ", " + formatter.format(date);
			allLogFileData.add(str);
		}
		ArrayList<String> sortedLogFileData = sortLogsByDate(allLogFileData);

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("ActivityLog.txt"));
			for (String str : sortedLogFileData) {
				writer.write(str);
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("An error occured!");
			e.printStackTrace();
		}
	}


	private static ArrayList<String> sortLogsByDate(ArrayList<String> logs) {
		logs.sort(new Comparator<String>() {

			@Override
			public int compare(String log1, String log2) {
				String stringDate1 = log1.substring(log1.lastIndexOf(',') + 1);
				String stringDate2 = log2.substring(log2.lastIndexOf(',') + 1);
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				try {
					Date date1 = formatter.parse(stringDate1);
					Date date2 = formatter.parse(stringDate2);

					if (date1.equals(date2)) {
						return 0;
					} else if (date1.before(date2)) {
						return 1;
					}
					return -1;
				} catch (Exception e) {
					throw new RuntimeException("Could not convert dates.");
				}
			}

		});
		return logs;
	}


	private static ArrayList<String> extractLogData() {
		ArrayList<String> list = new ArrayList<String>();

		try {
			File inputFile = new File("ActivityLog.txt");
			Scanner fileScanner = new Scanner(inputFile);

			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				list.add(line);
			}
		} catch (Exception e) {
			System.out.println("Error. File not found.");
		}
		return list;
	}
}
