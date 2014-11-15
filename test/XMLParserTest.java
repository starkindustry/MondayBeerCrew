import java.util.ArrayList;
import java.util.List;
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
	public void testXMLParser_1()
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
	public void testXMLParser_2()
		throws Exception {
		String filePath = "Result XMLs/testXmls/test1.xml";

		XMLParser parser = new XMLParser(filePath);
		
		assertNotNull(parser);
		
		Map<String,Klass> results = parser.getClassList();
		assertTrue(results.size() == 3);
		assertTrue(results.get(0).getName().equals("Mozzarella"));
		assertTrue(results.get(0).getPackageName().equals("test1"));
		assertTrue(results.get(0).getOutDependencies().get(0).equals("ToppingDecorator"));
	}

//	/**
//	 * Run the XMLParser(String) constructor test.
//	 *
//	 * @throws Exception
//	 *
//	 * @generatedBy CodePro at 26/10/14 2:07 PM
//	 */
//	@Test
//	public void testXMLParser_3()
//		throws Exception {
//		String filePath = "";
//
//		XMLParser result = new XMLParser(filePath);
//
//		// add additional test code here
//		assertNotNull(result);
//	}
//
//	/**
//	 * Run the XMLParser(String) constructor test.
//	 *
//	 * @throws Exception
//	 *
//	 * @generatedBy CodePro at 26/10/14 2:07 PM
//	 */
//	@Test
//	public void testXMLParser_4()
//		throws Exception {
//		String filePath = "";
//
//		XMLParser result = new XMLParser(filePath);
//
//		// add additional test code here
//		assertNotNull(result);
//	}
//
//	/**
//	 * Run the XMLParser(String) constructor test.
//	 *
//	 * @throws Exception
//	 *
//	 * @generatedBy CodePro at 26/10/14 2:07 PM
//	 */
//	@Test
//	public void testXMLParser_5()
//		throws Exception {
//		String filePath = "";
//
//		XMLParser result = new XMLParser(filePath);
//
//		// add additional test code here
//		assertNotNull(result);
//	}
//
//	/**
//	 * Run the XMLParser(String) constructor test.
//	 *
//	 * @throws Exception
//	 *
//	 * @generatedBy CodePro at 26/10/14 2:07 PM
//	 */
//	@Test
//	public void testXMLParser_6()
//		throws Exception {
//		String filePath = "";
//
//		XMLParser result = new XMLParser(filePath);
//
//		// add additional test code here
//		assertNotNull(result);
//	}
//
//	/**
//	 * Run the XMLParser(String) constructor test.
//	 *
//	 * @throws Exception
//	 *
//	 * @generatedBy CodePro at 26/10/14 2:07 PM
//	 */
//	@Test
//	public void testXMLParser_7()
//		throws Exception {
//		String filePath = "";
//
//		XMLParser result = new XMLParser(filePath);
//
//		// add additional test code here
//		assertNotNull(result);
//	}
//
//	/**
//	 * Run the XMLParser(String) constructor test.
//	 *
//	 * @throws Exception
//	 *
//	 * @generatedBy CodePro at 26/10/14 2:07 PM
//	 */
//	@Test
//	public void testXMLParser_8()
//		throws Exception {
//		String filePath = "";
//
//		XMLParser result = new XMLParser(filePath);
//
//		// add additional test code here
//		assertNotNull(result);
//	}
//
//	/**
//	 * Run the XMLParser(String) constructor test.
//	 *
//	 * @throws Exception
//	 *
//	 * @generatedBy CodePro at 26/10/14 2:07 PM
//	 */
//	@Test
//	public void testXMLParser_9()
//		throws Exception {
//		String filePath = "";
//
//		XMLParser result = new XMLParser(filePath);
//
//		// add additional test code here
//		assertNotNull(result);
//	}
//
//	/**
//	 * Run the XMLParser(String) constructor test.
//	 *
//	 * @throws Exception
//	 *
//	 * @generatedBy CodePro at 26/10/14 2:07 PM
//	 */
//	@Test
//	public void testXMLParser_10()
//		throws Exception {
//		String filePath = "";
//
//		XMLParser result = new XMLParser(filePath);
//
//		// add additional test code here
//		assertNotNull(result);
//	}
//
//	/**
//	 * Run the XMLParser(String) constructor test.
//	 *
//	 * @throws Exception
//	 *
//	 * @generatedBy CodePro at 26/10/14 2:07 PM
//	 */
//	@Test
//	public void testXMLParser_11()
//		throws Exception {
//		String filePath = "";
//
//		XMLParser result = new XMLParser(filePath);
//
//		// add additional test code here
//		assertNotNull(result);
//	}
//
//	/**
//	 * Run the XMLParser(String) constructor test.
//	 *
//	 * @throws Exception
//	 *
//	 * @generatedBy CodePro at 26/10/14 2:07 PM
//	 */
//	@Test
//	public void testXMLParser_12()
//		throws Exception {
//		String filePath = "";
//
//		XMLParser result = new XMLParser(filePath);
//
//		// add additional test code here
//		assertNotNull(result);
//	}
//
//	/**
//	 * Run the XMLParser(String) constructor test.
//	 *
//	 * @throws Exception
//	 *
//	 * @generatedBy CodePro at 26/10/14 2:07 PM
//	 */
//	@Test
//	public void testXMLParser_13()
//		throws Exception {
//		String filePath = "";
//
//		XMLParser result = new XMLParser(filePath);
//
//		// add additional test code here
//		assertNotNull(result);
//	}
//
//	/**
//	 * Run the XMLParser(String) constructor test.
//	 *
//	 * @throws Exception
//	 *
//	 * @generatedBy CodePro at 26/10/14 2:07 PM
//	 */
//	@Test
//	public void testXMLParser_14()
//		throws Exception {
//		String filePath = "";
//
//		XMLParser result = new XMLParser(filePath);
//
//		// add additional test code here
//		assertNotNull(result);
//	}
//
//	/**
//	 * Run the XMLParser(String) constructor test.
//	 *
//	 * @throws Exception
//	 *
//	 * @generatedBy CodePro at 26/10/14 2:07 PM
//	 */
//	@Test
//	public void testXMLParser_15()
//		throws Exception {
//		String filePath = "";
//
//		XMLParser result = new XMLParser(filePath);
//
//		// add additional test code here
//		assertNotNull(result);
//	}
//
//	/**
//	 * Run the XMLParser(String) constructor test.
//	 *
//	 * @throws Exception
//	 *
//	 * @generatedBy CodePro at 26/10/14 2:07 PM
//	 */
//	@Test
//	public void testXMLParser_16()
//		throws Exception {
//		String filePath = "";
//
//		XMLParser result = new XMLParser(filePath);
//
//		// add additional test code here
//		assertNotNull(result);
//	}
//
//	/**
//	 * Run the List<Klass> getClassList() method test.
//	 *
//	 * @throws Exception
//	 *
//	 * @generatedBy CodePro at 26/10/14 2:07 PM
//	 */
//	@Test
//	public void testGetClassList_1()
//		throws Exception {
//		XMLParser fixture = new XMLParser("");
//
//		List<Klass> result = fixture.getClassList();
//
//		// add additional test code here
//		assertNotNull(result);
//		assertEquals(0, result.size());
//	}
//
//	/**
//	 * Run the void printClasses() method test.
//	 *
//	 * @throws Exception
//	 *
//	 * @generatedBy CodePro at 26/10/14 2:07 PM
//	 */
//	@Test
//	public void testPrintClasses_1()
//		throws Exception {
//		XMLParser fixture = new XMLParser("");
//
//		fixture.printClasses();
//
//		// add additional test code here
//	}
//
//	/**
//	 * Run the void printClasses() method test.
//	 *
//	 * @throws Exception
//	 *
//	 * @generatedBy CodePro at 26/10/14 2:07 PM
//	 */
//	@Test
//	public void testPrintClasses_2()
//		throws Exception {
//		XMLParser fixture = new XMLParser("");
//
//		fixture.printClasses();
//
//		// add additional test code here
//	}
//
//	/**
//	 * Run the void printClasses() method test.
//	 *
//	 * @throws Exception
//	 *
//	 * @generatedBy CodePro at 26/10/14 2:07 PM
//	 */
//	@Test
//	public void testPrintClasses_3()
//		throws Exception {
//		XMLParser fixture = new XMLParser("");
//
//		fixture.printClasses();
//
//		// add additional test code here
//	}
//
//	/**
//	 * Perform pre-test initialization.
//	 *
//	 * @throws Exception
//	 *         if the initialization fails for some reason
//	 *
//	 * @generatedBy CodePro at 26/10/14 2:07 PM
//	 */
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