import java.util.List;


public class Klass {
	private String name;
	private String packageName;
//	private List<String> inDependencies;
	private List<String> outDependencies;
	private String linesOfCode;
	private String complexityScore;
	
	
	public Klass(String name, String packageName, List<String> out){
		
		this.name = name;
		this.packageName = packageName;
//		this.inDependencies = in;
		this.outDependencies = out;
		/** 
		 * TODO: implement loc and complexity score to klass
		this.linesOfcode = loc;
		this.complexityScore = complexScore;
		*/
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getPackageName(){
		return this.packageName;
	}
	
//	public List<String> getInDependencies(){
//		return this.inDependencies;
//	}
	
	public List<String> getOutDependencies(){
		return this.outDependencies;
	}
	
	public String getLinesOfCode() {
		return this.linesOfCode;
	}
	
	public String getComplexityScore() {
		return this.complexityScore;
	}
}
