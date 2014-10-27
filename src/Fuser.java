import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Fuser {
	private  List<Klass> petFinderResults = new ArrayList<Klass>();
	private  List<Klass> vntfulResults = new ArrayList<Klass>();
	
	public static void main(String[] args) {
//		XMLParser parser = new XMLParser("Result XMLs/DepFinderPetResults.xml");
//		System.out.println("Code base: PetFinder");
//		parser.printClasses();
//		
//		XMLParser parser2 = new XMLParser("Result XMLs/DepFinderVntfulResults.xml");
//		System.out.println("Code base: Vntful");
//		parser2.printClasses();
		
		CSVParserSM csvReader = new CSVParserSM();
		System.out.println("Class size by LOC:");
		Map<String, String> linesOfCodeMap = csvReader.mapSmCSV("Result XMLs/SMVntfulDetails.csv", 4);
		
		System.out.println("--------------------------------");
		System.out.println("Class complexity:");
		Map<String, String> complexityMap = csvReader.mapSmCSV("Result XMLs/SMVntfulDetails.csv", 14);
		
		
		XMLParser parser = new XMLParser("Result XMLs/testXmls/test1.xml");
		System.out.println("Code base: No classes");
		parser.printClasses();
	}

}
