import java.util.List;


public class Klass {
	private String name;
	private String packageName;
	private List<String> outDependencies;
	private String linesOfCode;
	private String complexityScore;


	public Klass(String name){
		this.name = name;
	}
	
	public Klass(String name, String packageName, List<String> out){
		this.name = name;
		this.packageName = packageName;
		this.outDependencies = out;
	}

	public Klass(String name, String packageName, List<String> out, String loc, String complexScore){
		this.name = name;
		this.packageName = packageName;
		this.outDependencies = out;
		this.linesOfCode = loc;
		this.complexityScore = complexScore;
	}
	
	public String getName(){
		return this.name;
	}

	public void setPackageName(String packageName){
		this.packageName = packageName;
	}
	
	public String getPackageName(){
		return this.packageName;
	}

	public void setDependencies(List<String> dependencies){
		this.outDependencies = dependencies;
	}
	
	public List<String> getOutDependencies(){
		return this.outDependencies;
	}
	
	public void setLinesOfCode(String loc) {
		this.linesOfCode = loc;
	}
	
	public String getLinesOfCode() {
		return this.linesOfCode;
	}

	public void setComplexityScore(String comp) {
		this.complexityScore = comp;
	}
	
	public String getComplexityScore() {
		return this.complexityScore;
	}
}
