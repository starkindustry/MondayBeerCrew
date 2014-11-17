import java.util.ArrayList;
import java.util.List;


public class VisualizationRowBuilder {

	private static final int SVG_PADDING = 100;
	private static final int PACKAGES_PER_ROW = 3;
	
	VisualizationRowBuilderHelper vrbHelper;
	
	public VisualizationRowBuilder () {
		vrbHelper = new VisualizationRowBuilderHelper();
	}	

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
			if (packages.size() >= rowCounter && vrbHelper.isWithinBackgroundWidth(packages, rowCounter)
					&& vrbHelper.isWithinScreenQuadrant(packages, rowCounter)) {
				VisualizationRow row = new VisualizationRow(rowCounter);
				row = populateRowByPackageSize(row, packages, rowCounter);
				rows.add(row);
				packages = vrbHelper.removePopulatedPackages(packages, rowCounter);
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
	
}
