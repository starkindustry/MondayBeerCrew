import java.util.List;


public class Klass {
	private String name;
	private String packageName;
//	private List<String> inDependencies;
	private List<String> outDependencies;
	
	public Klass(String name, String packageName, List<String> out){
		this.name = name;
		this.packageName = packageName;
//		this.inDependencies = in;
		this.outDependencies = out;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getPachageName(){
		return this.packageName;
	}
	
//	public List<String> getInDependencies(){
//		return this.inDependencies;
//	}
	
	public List<String> getOutDependencies(){
		return this.outDependencies;
	}

}
