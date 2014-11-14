import java.util.ArrayList;
import java.util.List;


public class Package {

	private final String name;
	private final List<Klass> klasses;
	
	public Package(String name) {
		this.name = name;
		this.klasses = new ArrayList<Klass>();
	}
	
	public int size() {
		return klasses.size();
	}
	
	public String getName() {
		return name;
	}
	
	public List<Klass> getKlasses() {
		return klasses;
	}
	
	public void addKlass(Klass klass) {
		klasses.add(klass);
	}
	
	public int getDiameter() {
		return size() * 30;
	}
}
