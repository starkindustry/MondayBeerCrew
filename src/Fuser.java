import java.util.ArrayList;
import java.util.List;


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
		
		XMLParser parser = new XMLParser("Result XMLs/testXmls/test1.xml");
		System.out.println("Code base: No classes");
		parser.printClasses();
	}

}
