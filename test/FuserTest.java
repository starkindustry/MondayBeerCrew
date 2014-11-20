import java.util.LinkedList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>FuserTest</code> contains tests for the class <code>{@link Fuser}</code>.
 *
 * @version $Revision: 1.0 $
 */
public class FuserTest {
	/**
	 * Run the Fuser() constructor test.
	 *
	 * @generatedBy CodePro at 17/11/14 10:51 PM
	 */
	@Test
	public void testFuser_1()
		throws Exception {
		Fuser result = new Fuser();
		assertNotNull(result);
	}

	/**
	 * Run the void main(String[]) method test.
	 *
	 * @throws Exception
	 */
	@Test
	public void testMain_1()
		throws Exception {
		String[] args = new String[] {"Result XMLs/testXMLs/pizzatest.csv", "Result XMLs/testXMLs/test2.xml"};
		Fuser.main(args);
		List<Klass> list = Fuser.getClassList();
				
		assertTrue(list.size() == 6);
		
		assertTrue(list.get(0).getName().equals("ToppingDecorator.java"));
		assertTrue(list.get(0).getComplexityScore().equals("1"));
		assertTrue(list.get(0).getLinesOfCode().equals("20"));
		assertTrue(list.get(0).getOutDependencies().size() == 1);
		assertTrue(list.get(0).getOutDependencies().contains("Pizza"));
		
		assertTrue(list.get(5).getName().equals("Mozzarella.java"));
		assertTrue(list.get(5).getComplexityScore().equals("1"));
		assertTrue(list.get(5).getLinesOfCode().equals("25"));
		assertTrue(list.get(5).getOutDependencies().size() == 2);
		assertTrue(list.get(5).getOutDependencies().contains("Pizza"));
		assertTrue(list.get(5).getOutDependencies().contains("ToppingDecorator"));
	}


	

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 17/11/14 10:51 PM
	 */
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
	 * @generatedBy CodePro at 17/11/14 10:51 PM
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
	 * @generatedBy CodePro at 17/11/14 10:51 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(FuserTest.class);
	}
}