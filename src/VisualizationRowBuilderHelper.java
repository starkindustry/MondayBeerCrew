import java.util.List;


public class VisualizationRowBuilderHelper {
	
	private static final int SVG_BACKGROUND_WIDTH = 1500;
	private static final int ORBIT_PADDING = 15;

	public VisualizationRowBuilderHelper() {

	}

	public List<Package> removePopulatedPackages(List<Package> packages, int pkgeCount) {

		while (pkgeCount > 0) {
			packages.remove(pkgeCount - 1);
			pkgeCount--;
		}

		return packages;
	}

	public boolean isWithinBackgroundWidth(List<Package> packages, int numPkgs) {
		int totalSize = 0;
		for (int i = 0; i < numPkgs; i++) {
			totalSize += (packages.get(i).size() * ORBIT_PADDING * 2);
		}

		return totalSize < SVG_BACKGROUND_WIDTH;
	}

	/**
	 * Determines if any package will exceed a "quadrant" of the screen.
	 * 
	 * Ex. If 2 packages are within 1500 px, but one package exceed 1500/2, they will overlap. 
	 * 		So return false.
	 * 
	 * @param packages = the package structure to be observed
	 * @param pkgeCount = number of packages to check
	 * @return true if does not exceed SVG_BACKGROUND_WIDTH/pkgeCount, else false
	 */
	public boolean isWithinScreenQuadrant(List<Package> packages, int pkgeCount) {
		int largestOrbit = findLargestOrbitDiameter(packages, pkgeCount);

		if (largestOrbit < SVG_BACKGROUND_WIDTH / pkgeCount) {
			return true;
		}

		return false;
	}

	public int findLargestOrbitDiameter(List<Package> packages, int pkgeCount) {
		int largestOrbitDiameter = 0;

		for (int i = 0; i < pkgeCount ; i++) {
			int packageDiameter = packages.get(i).getDiameter();

			if (packageDiameter > largestOrbitDiameter) {
				largestOrbitDiameter = packageDiameter;
			}
		}
		return largestOrbitDiameter;

	}
}
