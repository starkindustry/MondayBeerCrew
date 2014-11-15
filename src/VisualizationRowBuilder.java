import java.util.ArrayList;
import java.util.List;


public class VisualizationRowBuilder {

	private static final int SVG_BACKGROUND_WIDTH = 1500;
	private static final int ORBIT_PADDING = 15;
	private static final int SVG_PADDING = 100;
	private static final int PACKAGES_PER_ROW = 3;
	
	public VisualizationRowBuilder () {}	

	public List<VisualizationRow> populateVisRows(List<VisualizationRow> rows,
			List<Package> packages) {
		
		rows = new ArrayList<VisualizationRow>();
		
		while (!packages.isEmpty()) {
			populateVisRowsByPackageSize(rows, packages);
		}
		
		return rows;
		
	}
	
	public void setYValues(List<VisualizationRow> rows) {
		int previousRowY = 0;
		for (VisualizationRow row : rows) {
			int yValue = row.getLargestOrbit() + SVG_PADDING + previousRowY;
			row.setY(yValue - row.getLargestOrbit() / 2);
			previousRowY = yValue;
		}	
	}

	private List<VisualizationRow> populateVisRowsByPackageSize(List<VisualizationRow> rows,
			List<Package> packages) {
		
		int rowCounter = PACKAGES_PER_ROW;
		
		while (rowCounter > 0 && !packages.isEmpty()) {
			
			// Will iterate through packages by PACKAGES_PER_ROW chunks, decreasing by 1 if any boolean checks failed
			if (packages.size() >= rowCounter && isWithinBackgroundWidth(packages, rowCounter)
					&& isWithinScreenQuadrant(packages, rowCounter)) {
				VisualizationRow row = new VisualizationRow(rowCounter);
				row = populateRowByPackageSize(row, packages, rowCounter);
				rows.add(row);
				packages = removePopulatedPackages(packages, rowCounter);
			} 
			else {
				rowCounter--;
			}
		}
		
		return rows;
	}


	private VisualizationRow populateRowByPackageSize(VisualizationRow row,
			List<Package> packages, int pkgeCount) {
		
		for (int i = 0; i < pkgeCount; i++) row.addPackage(packages.get(i));
		return row;
	}
	
	private List<Package> removePopulatedPackages(List<Package> packages, int pkgeCount) {
		
		while (pkgeCount > 0) {
			packages.remove(pkgeCount - 1);
			pkgeCount--;
		}
		
		return packages;
	}

	private boolean isWithinBackgroundWidth(List<Package> packages, int numPkgs) {
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
	private boolean isWithinScreenQuadrant(List<Package> packages, int pkgeCount) {
		int largestOrbit = findLargestOrbitDiameter(packages, pkgeCount);
		
		if (largestOrbit < SVG_BACKGROUND_WIDTH / pkgeCount) {
			return true;
		}
		
		return false;
	}

	private int findLargestOrbitDiameter(List<Package> packages, int pkgeCount) {
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
