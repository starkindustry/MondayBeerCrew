import java.io.*;
import java.util.*;

public class CSVParserSM {

	/*Add the filename and metric from each java file into a HashMap
	 * Taken from http://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/ */
	public Map<String,String> mapSmCSV(String filepath, int metricIndex) {
		Map<String, String> metric = new HashMap<String, String>();
		
		BufferedReader bReader = null;
		String line = "";
		String csvSeparator = ";"; //set separator in SM to semi-colon
		String filename = null;
		try {
			bReader = new BufferedReader(new FileReader(filepath));
			bReader.readLine();
			while ((line = bReader.readLine()) != null) {
				String [] jFile = line.split(csvSeparator);
				filename = jFile[3].substring(jFile[3].lastIndexOf("\\") + 1);
				metric.put(filename, jFile[metricIndex]);
				
			}
			for (Map.Entry<String, String> entry : metric.entrySet()) {
//				System.out.println(entry.getKey() + ", " + entry.getValue());
			};
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bReader != null) {
				try {
					bReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return metric;	
	}
}