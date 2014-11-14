import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Fuser {
	
	private static List<Klass> codeBase1Results = new ArrayList<Klass>();
	private static List<Klass> codeBase2Results = new ArrayList<Klass>();
	
	private static List<Package> packages;
	private static List<VisualizationRow> visRows;

	public static void main(String[] args) {

		CSVParserSM csvReader = new CSVParserSM();
		Map<String, String> linesOfCodeMap = csvReader.mapSmCSV("Result XMLs/SMVntfulDetails.csv", 4);
		Map<String, String> complexityMap = csvReader.mapSmCSV("Result XMLs/SMVntfulDetails.csv", 14);

		XMLParser xmlReader = new XMLParser("Result XMLs/DepFinderVntfulResults.xml");
		Map<String, Klass> depMap = xmlReader.getClassList();

		Set<String> codeBase1SM = linesOfCodeMap.keySet();
		Set<String> codeBase1DF = depMap.keySet();

		for (String s : codeBase1SM) {
			if (codeBase1DF.contains(s)){
				Klass aKlass = new Klass(s);
				aKlass.setFullName(depMap.get(s).getFullName());
				aKlass.setLinesOfCode(linesOfCodeMap.get(s));
				aKlass.setComplexityScore(complexityMap.get(s));
				aKlass.setPackageName(depMap.get(s).getPackageName());
				aKlass.setDependencies(depMap.get(s).getOutDependencies());
				codeBase1Results.add(aKlass);
			}
		}
		
		// Parse code base results into Package objects
		packages = PackageBuilder.buildPackages(codeBase1Results);
		
//		// For DEBUG: Print out the created Package objects
//		for (Package p : packages) {
//			int classCount = 1;
//			System.out.println("-===Package==-: " + p.getName());
//			for (Klass k : p.getKlasses()) {
//				System.out.println(classCount + ")" + k.getName());
//				classCount++;
//			}
//		}
				
		calculatePackageCoords();
		
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
		
		generateOrbitalCSV(visRows);
		
	

//		System.out.println("Code base 1 results: ");
//		printClasses(codeBase1Results);
		
		// ===============================================================================================
		
		
		CSVParserSM csvReader2 = new CSVParserSM();
		Map<String, String> linesOfCodeMap2 = csvReader2.mapSmCSV("Result XMLs/SMPetFinderDetails.csv", 4);
		Map<String, String> complexityMap2 = csvReader2.mapSmCSV("Result XMLs/SMPetFinderDetails.csv", 14);

		XMLParser xmlReader2 = new XMLParser("Result XMLs/DepFinderPetResults.xml");
		Map<String, Klass> depMap2 = xmlReader2.getClassList();

		Set<String> codeBase2SM = linesOfCodeMap2.keySet();
		Set<String> codeBase2DF = depMap2.keySet();

		for (String s : codeBase2SM) {
			if (codeBase2DF.contains(s)){
				Klass aKlass = new Klass(s);
				aKlass.setFullName(depMap2.get(s).getFullName());
				aKlass.setLinesOfCode(linesOfCodeMap2.get(s));
				aKlass.setComplexityScore(complexityMap2.get(s));
				aKlass.setPackageName(depMap2.get(s).getPackageName());
				aKlass.setDependencies(depMap2.get(s).getOutDependencies());
				codeBase2Results.add(aKlass);
			}
		}
//		System.out.println("\nCode base 2 results: ");
//		printClasses(codeBase2Results);

	}

	/**
	 * Populates a list of VisualizationRows to reflect package coordinates
	 * on screen.
	 */
	private static void calculatePackageCoords() {
		VisualizationRowBuilder visRowBuilder = new VisualizationRowBuilder();
		visRows = visRowBuilder.populateVisRows(visRows, packages);
		visRowBuilder.setYValues(visRows);
	}
/*
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
			*/
	private static void generateOrbitalCSV(List<VisualizationRow> rows) {
		try {
			File file = new File("../KevinsFuckingCSV.csv");
			FileWriter writer = new FileWriter(file);
			
			writer.append("origin_x,origin_y,number,planet_radius,period,package,title\n");
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
								+ k.getName() + "\n");
						j++;
					}
					j = 0;
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

	public static void printClasses(List<Klass> classes) {
		System.out.println("Total classes: " + classes.size());
		System.out.println("--------------------------------");
		for (int i = 0; i < classes.size(); i++){
			System.out.println((i+1) + ". " + "Class name: " + classes.get(i).getFullName());
			System.out.println("Package name: " + classes.get(i).getPackageName());
			System.out.println("Size: " + classes.get(i).getLinesOfCode());
			System.out.println("Complexity: " + classes.get(i).getComplexityScore());
			System.out.println("Dependencies: ");
			List<String> deps = classes.get(i).getOutDependencies();
			for (String s : deps)
				System.out.println(s);
			System.out.println("--------------------------------");
		}	
	}
	
	

}
