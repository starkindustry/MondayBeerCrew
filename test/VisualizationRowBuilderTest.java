
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class VisualizationRowBuilderTest {
	
	private VisualizationRowBuilder visRowBuilder;
	private VisualizationRowBuilderHelper visRowBuilderHelper;
	private List<VisualizationRow> visRows;
	private List<Package> lopOne, lopThree, lopBig;
	private List<Klass> lokOne, lokFive, lokTen, lokTwenty;
	
	@Before
	public void setUp() throws Exception {
		visRowBuilder = new VisualizationRowBuilder();
		visRowBuilderHelper = new VisualizationRowBuilderHelper();
		lopOne = new ArrayList<Package>();
		lopThree = new ArrayList<Package>();
		lopBig = new ArrayList<Package>();
		lokOne = new ArrayList<Klass>();
		lokFive = new ArrayList<Klass>();
		lokTen = new ArrayList<Klass>();
		lokTwenty = new ArrayList<Klass>();
		populateKlasses(lokOne, lokFive, lokTen, lokTwenty);
		populatePackages();
	}
	
	@After
	public void tearDown() throws Exception {
		lopOne.clear();
		lopThree.clear();
		lopBig.clear();
		lokOne.clear();
		lokFive.clear();
		lokTen.clear();
	}
	
	private void populateKlasses(List<Klass> lokOne, List<Klass> lokFive, List<Klass> lokTen, List<Klass> lokTwenty) {
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
		
		lokTwenty.add(new Klass("A"));
		lokTwenty.add(new Klass("B"));
		lokTwenty.add(new Klass("C"));
		lokTwenty.add(new Klass("D"));
		lokTwenty.add(new Klass("E"));
		lokTwenty.add(new Klass("F"));
		lokTwenty.add(new Klass("G"));
		lokTwenty.add(new Klass("H"));
		lokTwenty.add(new Klass("I"));
		lokTwenty.add(new Klass("J"));
		lokTwenty.add(new Klass("A"));
		lokTwenty.add(new Klass("B"));
		lokTwenty.add(new Klass("C"));
		lokTwenty.add(new Klass("D"));
		lokTwenty.add(new Klass("E"));
		lokTwenty.add(new Klass("F"));
		lokTwenty.add(new Klass("G"));
		lokTwenty.add(new Klass("H"));
		lokTwenty.add(new Klass("I"));
		lokTwenty.add(new Klass("J"));
	}

	private void populatePackages() {
		Package package1 = new Package("package1");
		Package package2 = new Package("package2");
		Package package3 = new Package("package3");
		Package packageBig = new Package("packageBig");
		
		package1.addKlass(lokOne.get(0));
		
		for (int i = 0; i < lokFive.size(); i++) {
			package2.addKlass(lokFive.get(i));
		}
		
		for (int i = 0; i < lokTen.size(); i++) {
			package3.addKlass(lokTen.get(i));
		}	
		
		for (int i = 0; i < lokTwenty.size(); i++) {
			packageBig.addKlass(lokTwenty.get(i));
		}	
		
		lopOne.add(package1);
		
		lopThree.add(package1);
		lopThree.add(package2);
		lopThree.add(package3);
		
		lopBig.add(package2);
		lopBig.add(package3);
		lopBig.add(packageBig);
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
	
	/*
	 * Should have two rows - One row with 2 packages, one row with 1 package
	 */
	@Test
	public void testPopulateVisRowsThreePkgInclBigPkg() {
		visRows = visRowBuilder.populateVisRows(visRows, lopBig);
		assertEquals(2, visRows.size());
		assertEquals(2, visRows.get(0).getPackages().size());
		assertEquals(1, visRows.get(1).getPackages().size());
	}
	
	@Test
	public void testIsWithinBackgroundWidthThreePkgInclBigPkg() {
		visRows = visRowBuilder.populateVisRows(visRows, lopBig);
		
		List<Package> firstRowPkgs = new ArrayList<Package>();
		List<Package> secondRowPkgs = new ArrayList<Package>();
		
		firstRowPkgs = visRows.get(0).getPackages();
		secondRowPkgs = visRows.get(1).getPackages();
		assertTrue(visRowBuilderHelper.isWithinBackgroundWidth(firstRowPkgs, 2));
		assertTrue(visRowBuilderHelper.isWithinBackgroundWidth(firstRowPkgs, 1));
		assertTrue(visRowBuilderHelper.isWithinBackgroundWidth(secondRowPkgs, 1));
	}
	
	@Test
	public void testfindLargestDiameterThreePkgInclBigPkg() {
		visRows = visRowBuilder.populateVisRows(visRows, lopBig);
		
		List<Package> firstRowPkgs = new ArrayList<Package>();
		List<Package> secondRowPkgs = new ArrayList<Package>();
		
		firstRowPkgs = visRows.get(0).getPackages();
		secondRowPkgs = visRows.get(1).getPackages();
		assertEquals(300, visRowBuilderHelper.findLargestOrbitDiameter(firstRowPkgs, 2));  // 10 klasses * 30/klass = 300
		assertEquals(600, visRowBuilderHelper.findLargestOrbitDiameter(secondRowPkgs, 1)); // 20 klasses * 30/klass = 600
	}
	
	@Test
	public void testIsWithinScreenQuadrantThreePkgInclBigPkg() {
		visRows = visRowBuilder.populateVisRows(visRows, lopBig);
		
		List<Package> firstRowPkgs = new ArrayList<Package>();
		List<Package> secondRowPkgs = new ArrayList<Package>();
		
		firstRowPkgs = visRows.get(0).getPackages();
		secondRowPkgs = visRows.get(1).getPackages();
		assertTrue(visRowBuilderHelper.isWithinScreenQuadrant(firstRowPkgs, 2));
		assertTrue(visRowBuilderHelper.isWithinScreenQuadrant(firstRowPkgs, 1));
		assertTrue(visRowBuilderHelper.isWithinScreenQuadrant(secondRowPkgs, 1));
	}

}
