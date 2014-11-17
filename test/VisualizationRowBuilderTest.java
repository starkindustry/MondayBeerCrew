
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class VisualizationRowBuilderTest {
	
	private VisualizationRowBuilder visRowBuilder;
	private VisualizationRowBuilderTestHelper vrbTestHelper;
	private List<VisualizationRow> visRows;
	private List<Package> lopOne, lopThree;
	private List<Klass> lokOne, lokFive, lokTen;
	
	@Before
	public void setUp() throws Exception {
		visRowBuilder = new VisualizationRowBuilder();
//		vrbTestHelper = new VisualizationRowBuilderTestHelper();
//		vrbTestHelper.initializeKlasses(lokOne, lokFive, lokTen);
		lopOne = new ArrayList<Package>();
		lopThree = new ArrayList<Package>();
		lokOne = new ArrayList<Klass>();
		lokFive = new ArrayList<Klass>();
		lokTen = new ArrayList<Klass>();
		populateKlasses(lokOne, lokFive, lokTen);
		populatePackages();
	}
	
	@After
	public void tearDown() throws Exception {
		lopOne.clear();
		lopThree.clear();
		lokOne.clear();
		lokFive.clear();
		lokTen.clear();
	}
	
	private void populateKlasses(List<Klass> lokOne, List<Klass> lokFive, List<Klass> lokTen) {
		lokOne.add(new Klass("A"));
		
		lokFive.add(new Klass("A"));
		lokFive.add(new Klass("B"));
		lokFive.add(new Klass("C"));
		lokFive.add(new Klass("D"));
		lokFive.add(new Klass("E"));

		lokTen.add(new Klass("A"));
		lokTen.add(new Klass("B"));
		lokTen.add(new Klass("C"));
		lokTen.add(new Klass("D"));
		lokTen.add(new Klass("E"));
		lokTen.add(new Klass("F"));
		lokTen.add(new Klass("G"));
		lokTen.add(new Klass("H"));
		lokTen.add(new Klass("I"));
		lokTen.add(new Klass("J"));
		
	}

	private void populatePackages() {
		Package package1 = new Package("package1");
		Package package2 = new Package("package2");
		Package package3 = new Package("package3");
		
		package1.addKlass(lokOne.get(0));
		
		for (int i = 0; i < lokFive.size(); i++) {
			package2.addKlass(lokFive.get(i));
		}
		
		for (int i = 0; i < lokTen.size(); i++) {
			package3.addKlass(lokTen.get(i));
		}	
		
		lopOne.add(package1);
		
		lopThree.add(package1);
		lopThree.add(package2);
		lopThree.add(package3);
	}


	@Test
	public void testPopulateVisRowsOnePkg() {
		visRows = visRowBuilder.populateVisRows(visRows, lopOne);
		assertEquals(1, visRows.size());
	}
	
	@Test
	public void testSetYOnePkg() {
		visRows = visRowBuilder.populateVisRows(visRows, lopOne);
		visRowBuilder.setYValues(visRows);
		assertEquals(115, visRows.get(0).getY());
	}
	
	@Test
	public void testPopulateVisRowsThreePkg() {
		visRows = visRowBuilder.populateVisRows(visRows, lopThree);
		assertEquals(1, visRows.size());
	}
	
	@Test
	public void testSetYThreePkg() {
		visRows = visRowBuilder.populateVisRows(visRows, lopThree);
		visRowBuilder.setYValues(visRows);
		for (int i = 0; i < visRows.size(); i ++) {
			assertEquals(250, visRows.get(i).getY());
		}
		
	}

}
