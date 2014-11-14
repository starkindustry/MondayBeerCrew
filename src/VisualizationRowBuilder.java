import java.util.ArrayList;
import java.util.List;


public class VisualizationRowBuilder {

	private static final int SVG_BACKGROUND_WIDTH = 1500;
	private static final int ORBIT_PADDING = 15;
	private static final int SVG_PADDING = 25;
	
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
			System.out.println(previousRowY);
		}	
	}

	private List<VisualizationRow> populateVisRowsByPackageSize(List<VisualizationRow> rows,
			List<Package> packages) {
		
		if (packages.size() >= 3 && isWithinBackgroundWidth(packages, 3)) {
			VisualizationRow row = new VisualizationRow();
			row.addPackage(packages.get(0));
			row.addPackage(packages.get(1));
			row.addPackage(packages.get(2));
			rows.add(row);
			packages.remove(2);
			packages.remove(1);
			packages.remove(0);
		} else if (packages.size() >= 2 && isWithinBackgroundWidth(packages, 2)) {
			VisualizationRow row = new VisualizationRow();
			row.addPackage(packages.get(0));
			row.addPackage(packages.get(1));
			rows.add(row);
			packages.remove(1);
			packages.remove(0);
		} else if (packages.size() >= 1 && isWithinBackgroundWidth(packages, 1)) {
			VisualizationRow row = new VisualizationRow();
			row.addPackage(packages.get(0));
			rows.add(row);
			packages.remove(0);
		}
		
		return rows;
	}
	
	private boolean isWithinBackgroundWidth(List<Package> packages, int numPkgs) {
		int totalSize = 0;
		for (int i = 0; i < numPkgs; i++) {
			totalSize += (packages.get(i).size() * ORBIT_PADDING * 2);
		}
		
		return totalSize < SVG_BACKGROUND_WIDTH;
	}

	
	private static boolean checkSizeIsValid(List<Package> packages, int pkgeCount) {
		int largestOrbit = 0;
		for (Package p : packages) {
			if (p.getDiameter() > largestOrbit) {
				largestOrbit = p.getDiameter();
			}
		}
		if (largestOrbit < SVG_BACKGROUND_WIDTH / pkgeCount) {
			return true;
		}
		return false;
	}

}
