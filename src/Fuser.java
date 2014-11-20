import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Fuser {

	private static final String ORBIT_DATA_FILENAME = "orbitData.csv";
	private static final String ORBIT_DATA_COLUMN_HEADERS = "origin_x,origin_y,number,planet_radius,period,package,title";
	private static final String DEPENDENCY_DATA_FILENAME = "dependencyData.csv";
	private static final String DEPENDENCY_DATA_COLUMN_HEADERS = "class_from,class_to";

	private static String sourceMonitorResultPath;
	private static String depFinderResultPath;
	private static List<Klass> results = new ArrayList<Klass>();

	private static List<Package> packages;
	private static List<VisualizationRow> visRows;

	public static void main(String[] args) {
		// Two args have to be passed to the jar. 
		// The first should be the path for the SourceMonitor result CSV for the code base. 
		// The second should be the path for the Dependency Finder XML for the code base.
		// e.g. "Result XMLs/SMVntfulDetails.csv" "Result XMLs/DepFinderVntfulResults.xml" for Vntful
		// e.g. "Result XMLs/SMPetFinderDetails.csv" "Result XMLs/DepFinderPetResults.xml" for Petfinder
		
		if (args.length == 2) {
			sourceMonitorResultPath = args[0];
			depFinderResultPath = args[1];

			CSVParserSM csvReader = new CSVParserSM();
			//			Map<String, String> linesOfCodeMap = csvReader.mapSmCSV("Result XMLs/SMVntfulDetails.csv", 4);
			Map<String, String> linesOfCodeMap = csvReader.mapSmCSV(sourceMonitorResultPath, 4);

			//			Map<String, String> complexityMap = csvReader.mapSmCSV("Result XMLs/SMVntfulDetails.csv", 14);
			Map<String, String> complexityMap = csvReader.mapSmCSV(sourceMonitorResultPath, 14);

			//			XMLParser xmlReader = new XMLParser("Result XMLs/DepFinderVntfulResults.xml");
			XMLParser xmlReader = new XMLParser(depFinderResultPath);

			Map<String, Klass> depMap = xmlReader.getClassList();

			Set<String> sourceMonitorClassList = linesOfCodeMap.keySet();
			Set<String> depFinderClassList = depMap.keySet();
			List<String> fullNameList = new ArrayList<String>();
			for (String s : sourceMonitorClassList){
				if (depFinderClassList.contains(s)){
					fullNameList.add(depMap.get(s).getFullName());
				}
			}

			for (String s : sourceMonitorClassList) {
				if (depFinderClassList.contains(s)){
					Klass aKlass = new Klass(s);
					aKlass.setFullName(depMap.get(s).getFullName());
					aKlass.setLinesOfCode(linesOfCodeMap.get(s));
					aKlass.setComplexityScore(complexityMap.get(s));
					aKlass.setPackageName(depMap.get(s).getPackageName());
					aKlass.setDependencies(depMap.get(s).getOutDependencies());
					results.add(aKlass);
				}
			}

			// Parse code base results into Package objects
			packages = PackageBuilder.buildPackages(results);
			calculatePackageCoords();

			for (Klass c : results) {
				List<String> deps = c.getOutDependencies(); 
				if (deps.retainAll(fullNameList)) {
					c.setDependencies(deps);
				}
			}

			// Generates CSV files
			generateOrbitalCSV(visRows);
			generateDependencyCSV(visRows);

//			printClasses(results);

		} else {
			System.out.println("Incorrect number of arguments passed");
		}
	}


	//------------HELPER FUNCTIONS -------------

	/**
	 * Populates a list of VisualizationRows to reflect package coordinates
	 * on screen.
	 */
	private static void calculatePackageCoords() {
		VisualizationRowBuilder visRowBuilder = new VisualizationRowBuilder();
		visRows = visRowBuilder.populateVisRows(visRows, packages);
		visRowBuilder.setYValues(visRows);
	}

	private static void generateOrbitalCSV(List<VisualizationRow> rows) {
		try {
			File file = new File(ORBIT_DATA_FILENAME);
			FileWriter writer = new FileWriter(file);

			writer.append(ORBIT_DATA_COLUMN_HEADERS + "\n"); 
			int i = 0;
			int j = 1;
			for (VisualizationRow vr : visRows) {
				for (Package p : vr.getPackages()) {
					for (Klass k : p.getKlasses()) {
						writer.append(vr.getX()[i] + "," 
								+ vr.getY() + "," 
								+ j + "," 
								+ k.getLinesOfCode() + "," 
								+ k.getComplexityScore() + "," 
								+ k.getPackageName() + "," 
								+ returnClassNameWithoutExtension(k.getName()) + "\n");
						j++;
					}
					j = 1;
					i++;
				}
				i = 0;
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void generateDependencyCSV(List<VisualizationRow> rows) {
		try {
			File file = new File(DEPENDENCY_DATA_FILENAME);
			FileWriter writer = new FileWriter(file);

			writer.append(DEPENDENCY_DATA_COLUMN_HEADERS + "\n"); 
			for (VisualizationRow vr : visRows) {
				for (Package p : vr.getPackages()) {
					for (Klass k : p.getKlasses()) {
						for (String dep : k.getOutDependencies()) {
							writer.append(returnClassNameWithoutExtension(k.getName()) + "," + dep.substring(dep.lastIndexOf(".") + 1) + "\n");
						}
					}
				}
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String returnClassNameWithoutExtension(String s) {
		return s.replaceFirst("[.][^.]+$", "");
	}

	public static void printClasses(List<Klass> classes) {
		System.out.println("Total classes: " + classes.size());
		System.out.println("--------------------------------");
		for (int i = 0; i < classes.size(); i++){
			System.out.println((i+1) + ". " + "\nClass name: " + classes.get(i).getFullName());
			System.out.println("Full name: " + classes.get(i).getFullName());
			System.out.println("Package name: " + classes.get(i).getPackageName());
			System.out.println("Size: " + classes.get(i).getLinesOfCode());
			System.out.println("Complexity: " + classes.get(i).getComplexityScore());
			System.out.println("Dependencies: ");
			List<String> deps = classes.get(i).getOutDependencies();
			for (String s : deps)
				System.out.println(s.substring(s.lastIndexOf(".") + 1));
			System.out.println("--------------------------------");
		}	
	}

	public static void printPackages(List<Package> packages) {
		// For DEBUG: Print out the created Package objects
		for (Package p : packages) {
			int classCount = 1;
			System.out.println("-===Package==-: " + p.getName());
			for (Klass k : p.getKlasses()) {
				System.out.println(classCount + ")" + k.getName());
				classCount++;
			}
		}
	}

	public static void printOrbitData() {
		// Prints our beautiful .csv data
		int i = 0;
		int j = 1;
		for (VisualizationRow vr : visRows) {
			for (Package p : vr.getPackages()) {
				for (Klass k : p.getKlasses()) {
					System.out.println(vr.getX()[i] + "," 
							+ vr.getY() + "," 
							+ j + "," 
							+ k.getLinesOfCode() + "," 
							+ k.getComplexityScore() + "," 
							+ k.getPackageName() + "," 
							+ k.getName());
					j++;
				}
				j = 0;
				i++;
			}
			i = 0;
		}
	}

	public static List<Klass> getClassList(){
		return results;
	}


}
