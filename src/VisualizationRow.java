import java.util.ArrayList;
import java.util.List;


public class VisualizationRow {

	private int y;
	private int[] x;
	private final List<Package> packages;
	
	public VisualizationRow(int rowCount) {
		this.packages = new ArrayList<Package>();
		this.x = new int[rowCount];
		setXCoordinates();
	}
	
	private void setXCoordinates() {
		if (x.length == 3) {
			x = new int[] {250, 750, 1250};
		} else if (x.length == 2) {
			x = new int[] {500, 1000};
		} else if (x.length == 1) {
			x = new int[] {750};
		}
	}

	public void addPackage(Package pkge) {
		packages.add(pkge);
	}
	
	public int getLargestOrbit() {
		int max = 0;
		for (Package p : packages) {
			if (p.getDiameter() > max) {
				max = p.getDiameter();
			}
		}
		return max;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getY() {
		return y;
	}
	
	public List<Package> getPackages() {
		return packages;
	}

	public int[] getX() {
		return x;
	}
}
