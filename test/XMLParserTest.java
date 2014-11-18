import java.util.Map;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * The class <code>XMLParserTest</code> contains tests for the class <code>{@link XMLParser}</code>.
 *
 * 
 * @author parham
 */
public class XMLParserTest {
	/**
	 * Run the XMLParser(String) constructor test.
	 *
	 * @throws Exception
	 *
	 */
	@Test
	public void testXMLParserNoClasses()
		throws Exception {
		String filePath = "Result XMLs/testXmls/testNoClass.xml";

		XMLParser parser = new XMLParser(filePath);
		
		assertNotNull(parser);
		
		Map<String,Klass> results = parser.getClassList();
		assertTrue(results.size() == 0);
		
	}

	/**
	 * Run the XMLParser(String) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 26/10/14 2:07 PM
	 */
	@Test
	public void testXMLParser1()
		throws Exception {
		String filePath = "Result XMLs/testXmls/test1.xml";

		XMLParser parser = new XMLParser(filePath);
		
		assertNotNull(parser);
		
		Map<String,Klass> results = parser.getClassList();
		assertTrue(results.size() == 3);
		assertTrue(results.containsKey("Mozzarella.java"));	
		assertTrue(results.containsKey("Pizza.java"));
		assertTrue(results.containsKey("Object.java"));
		assertTrue(results.get("Mozzarella.java").getPackageName().equals("test1"));
		assertTrue(results.get("Mozzarella.java").getOutDependencies().contains("ToppingDecorator"));
		assertTrue(results.get("Mozzarella.java").getOutDependencies().contains("TestingSecond"));
		assertTrue(results.get("Pizza.java").getOutDependencies().size() == 0);
		assertTrue(results.get("Object.java").getOutDependencies().size() == 0);
	}
	
	/**
	 * Run the XMLParser(String) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 26/10/14 2:07 PM
	 */
	@Test
	public void testXMLParser2()
		throws Exception {
		String filePath = "Result XMLs/testXmls/test2.xml";

		XMLParser parser = new XMLParser(filePath);
		
		assertNotNull(parser);
		
		Map<String,Klass> results = parser.getClassList();
		assertTrue(results.size() == 7);
		assertTrue(results.containsKey("ToppingDecorator.java"));	
		assertTrue(results.containsKey("TomatoSauce.java"));
		assertTrue(results.containsKey("PlainPizza.java"));
		assertTrue(results.get("ToppingDecorator.java").getPackageName().equals("testPackageName"));
		assertTrue(results.get("ToppingDecorator.java").getOutDependencies().contains("Pizza"));
		assertTrue(!results.get("Mozzarella.java").getOutDependencies().contains("Mozzarella"));
		assertTrue(results.get("Pizza.java").getOutDependencies().size() == 0);
		assertTrue(results.get("javalangObject.java").getPackageName() == "");
	}


	@Before
	public void setUp()
		throws Exception {
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 26/10/14 2:07 PM
	 */
	@After
	public void tearDown()
		throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 26/10/14 2:07 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(XMLParserTest.class);
	}
}