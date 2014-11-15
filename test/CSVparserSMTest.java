import static org.junit.Assert.*;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;


public class CSVparserSMTest {
	
	private CSVParserSM testParser;
	private static String testcsv = "Result XMLs/testXmls/testcsv.csv";
	Map<String, String> locTest; 
	Map<String, String> complexityTest;
	String[] values = {"301", "33", "165", "11"};
	
	@Before
	public void setup() {
		testParser = new CSVParserSM();
		locTest = testParser.mapSmCSV(testcsv, 4);
		complexityTest = testParser.mapSmCSV(testcsv, 14);
		
	}
	
	@Test
	public void testMapSmCSV() {
		assertTrue(locTest.isEmpty() == false);
		assertTrue(locTest.size() == 4);	
		assertTrue(locTest.containsKey("MainView.java") == true);
		assertTrue(locTest.containsKey("Class1.java") == true);
		assertTrue(locTest.containsValue("301") == true);
				
		assertTrue(complexityTest.isEmpty() == false);
		assertTrue(complexityTest.size() == 4);
		assertTrue(locTest.containsKey("Class1.java") == true);
		assertTrue(locTest.containsKey("Class3.java") == false);
		assertTrue(complexityTest.containsValue("5"));
		
	}

}
