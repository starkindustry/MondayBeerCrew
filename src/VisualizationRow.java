import java.util.ArrayList;
import java.util.List;


public class VisualizationRow {

	private int y;
	private final List<Package> packages;
	
	public VisualizationRow() {
		this.packages = new ArrayList<Package>();
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
}
