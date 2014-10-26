import java.util.ArrayList;
import java.util.List;


public class Fuser {
	private  List<Klass> classList = new ArrayList<Klass>();
	
	public static void main(String[] args) {
		XMLParser parser = new XMLParser("Result3.xml");
		parser.printClasses();
	}

}
