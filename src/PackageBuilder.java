import java.util.ArrayList;
import java.util.List;


public class PackageBuilder {

	public static List<Package> buildPackages(List<Klass> klasses) {
		List<Package> packages = new ArrayList<Package>();
		for (Klass k : klasses) {
			if (packages.isEmpty() || !isPackageInPackages(k.getPackageName(), packages)) {
				Package pkge = new Package(k.getPackageName());
				pkge.addKlass(k);
				packages.add(pkge);
			} else {
				addKlassToPackage(k, packages);
			}
		}
		return packages;
	}

	private static void addKlassToPackage(Klass klass, List<Package> packages) {
		for (Package p : packages) {
			if (p.getName().equals(klass.getPackageName())) {
				p.addKlass(klass);
			}
		}
	}
	
	private static boolean isPackageInPackages(String name, List<Package> packages) {
		for (Package p : packages) {
			if (p.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
}
