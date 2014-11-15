
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class VisualizationRowBuilderTest {

	private VisualizationRowBuilder visRowBuilder;
	private List<VisualizationRow> visRows;
	private List<Package> lopSmall, lopBig;
	private List<Klass> lokSmall, lokBig;
	
	@Before
	public void setUp() throws Exception {
		visRowBuilder = new VisualizationRowBuilder();
		lopSmall = new ArrayList<Package>();
		lokSmall = new ArrayList<Klass>();
		lokBig = new ArrayList<Klass>();
		populateKlasses(lokSmall, lokBig);
		populatePackages(lopSmall);
	}
	
	private void populateKlasses(List<Klass> lok1, List<Klass> lok2) {
		lok1.add(new Klass("A"));
		lok1.add(new Klass("B"));
		lok1.add(new Klass("C"));
		lok1.add(new Klass("D"));
		lok1.add(new Klass("E"));

		lok2.add(new Klass("A"));
		lok2.add(new Klass("B"));
		lok2.add(new Klass("C"));
		lok2.add(new Klass("D"));
		lok2.add(new Klass("E"));
		lok2.add(new Klass("F"));
		lok2.add(new Klass("G"));
		lok2.add(new Klass("H"));
		lok2.add(new Klass("I"));
		lok2.add(new Klass("J"));
		
	}

	private void populatePackages(List<Package> packages) {
		Package package1 = new Package("package1");
		Package package2 = new Package("package2");
		for (int i = 0; i < lokSmall.size(); i++) {
			package1.addKlass(lokSmall.get(i));
		}
		
		for (int i = 0; i < lokBig.size(); i++) {
			package2.addKlass(lokBig.get(i));
		}	
		
		lopSmall.add(package1);
		lopSmall.add(package2);
	}
	
//	private void populateEachVisRow(List<VisualizationRow> visRows) {
//		VisualizationRow vRow1 = new VisualizationRow();
//		for (int i = 0; i < lopSmall.size(); i++) {
//			vRow1.addPackage(lopSmall.get(i));
//		}
//		
//	}


	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPopulateVisRows() {
		visRows = visRowBuilder.populateVisRows(visRows, lopSmall);
		assertEquals(1, visRows.size());
	}

}
