import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Fuser {
	private static List<Klass> codeBase1Results = new ArrayList<Klass>();
	private static List<Klass> codeBase2Results = new ArrayList<Klass>();
	private static final int SVG_BACKGROUND_WIDTH = 1500;
	private static final int ORBIT_PADDING = 15;
	private static final int SVG_PADDING = 25;

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
		List<Package> packages = new ArrayList<Package>();
		for (Klass k : codeBase1Results) {
			if (packages.isEmpty() || !isPackageInPackages(k.getPackageName(), packages)) {
				Package pkge = new Package(k.getPackageName());
				pkge.addKlass(k);
				packages.add(pkge);
			} else {
				addKlassToPackage(k, packages);
			}
		}
		
		// Print out the created Package objects
		for (Package p : packages) {
			System.out.println("Package: " + p.getName());
			for (Klass k : p.getKlasses()) {
				System.out.println(k.getName());
			}
		}
		
		List<VisualizationRow> rows = new ArrayList<VisualizationRow>();
		while (!packages.isEmpty()) {
			if (packages.size() >= 3 && (packages.get(0).size() * ORBIT_PADDING * 2 + packages.get(1).size() * ORBIT_PADDING * 2 + packages.get(2).size() * ORBIT_PADDING * 2) < SVG_BACKGROUND_WIDTH) {
				VisualizationRow row = new VisualizationRow();
				row.addPackage(packages.get(0));
				row.addPackage(packages.get(1));
				row.addPackage(packages.get(2));
				rows.add(row);
				packages.remove(2);
				packages.remove(1);
				packages.remove(0);
			} else if (packages.size() >= 2 && (packages.get(0).size() * ORBIT_PADDING * 2 + packages.get(1).size() * ORBIT_PADDING * 2) < SVG_BACKGROUND_WIDTH) {
				VisualizationRow row = new VisualizationRow();
				row.addPackage(packages.get(0));
				row.addPackage(packages.get(1));
				rows.add(row);
				packages.remove(1);
				packages.remove(0);
			} else if (packages.size() >= 1 && (packages.get(0).size() * ORBIT_PADDING * 2) < SVG_BACKGROUND_WIDTH) {
				VisualizationRow row = new VisualizationRow();
				row.addPackage(packages.get(0));
				rows.add(row);
				packages.remove(0);
			}
		}
		int previousRowY = 0;
		for (VisualizationRow row : rows) {
			int yValue = row.getLargestOrbit() + SVG_PADDING + previousRowY;
			row.setY(yValue - row.getLargestOrbit() / 2);
			previousRowY = yValue;
			System.out.println(previousRowY);
		}
		
		
		
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

	private static void addKlassToPackage(Klass klass, List<Package> packages) {
		for (Package p : packages) {
			if (p.getName().equals(klass.getPackageName())) {
				p.addKlass(klass);
			}
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
	
	public static boolean isPackageInPackages(String name, List<Package> packages) {
		for (Package p : packages) {
			if (p.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean checkSizeIsValid(List<Package> packages, int pkgeCount) {
		int largestOrbit = 0;
		for (Package p : packages) {
			if (p.getDiameter() > largestOrbit) {
				largestOrbit = p.getDiameter();
			}
		}
		if (largestOrbit < SVG_BACKGROUND_WIDTH / pkgeCount) {
			return true;
		}
		return false;
	}

}
