import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class PackageBuilderTest {

	private List<Klass> klasses;
	private List<Package> packages;

	@Before
	public void setUp() {
		packages = new ArrayList<Package>();
		klasses = new ArrayList<Klass>();
		createKlasses(klasses);
	}
		
	@Test
	public void testBuildPackagesHasRightPackageSize() {
		packages = PackageBuilder.buildPackages(klasses);
		assertEquals(2, packages.size());
	}
	
	@Test
	public void testBuildPackageHasCorrectNumberOfKlasses() {
		packages = PackageBuilder.buildPackages(klasses);
		List<Klass> lok1 = packages.get(0).getKlasses();
		List<Klass> lok2 = packages.get(1).getKlasses();
		assertEquals(4, lok1.size());
		assertEquals(4, lok2.size());
	}
	
	@Test
	public void testPackageConstructionOrder() {
		packages = PackageBuilder.buildPackages(klasses);
		Package package1 = packages.get(0);
		Package package2 = packages.get(1);
		assertEquals("PackageA", package1.getName());
		assertEquals("PackageB", package2.getName());
	}
	
	@Test
	public void testPackagesContainCorrectKlasses() {
		packages = PackageBuilder.buildPackages(klasses);
		List<Klass> lok1 = packages.get(0).getKlasses();
		List<Klass> lok2 = packages.get(1).getKlasses();
		assertEquals("Klass0", lok1.get(0).getName());
		assertEquals("Klass1", lok2.get(0).getName());
		assertEquals("Klass2", lok1.get(1).getName());
		assertEquals("Klass3", lok2.get(1).getName());
		assertEquals("Klass4", lok1.get(2).getName());
		assertEquals("Klass5", lok2.get(2).getName());
		assertEquals("Klass6", lok1.get(3).getName());
		assertEquals("Klass7", lok2.get(3).getName());
	}
	
	private void createKlasses(List<Klass> klasses) {
		for (int i = 0; i < 8; i++) {
			Klass klass = new Klass("Klass" + i);
			if (i % 2 == 0) {
				klass.setPackageName("PackageA");
			} else {
				klass.setPackageName("PackageB");
			}
			klasses.add(klass);
		}
	}
}

