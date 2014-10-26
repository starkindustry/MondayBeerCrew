import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {
	private  List<Klass> classList = new ArrayList<Klass>();
	// code stolen from http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/  
	public XMLParser(String filePath) {
		try {
			File fXmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			NodeList classes = doc.getElementsByTagName("class");
			for (int i = 0; i < classes.getLength(); i++) {

				Node currClass = classes.item(i);
				Node parentNode = currClass.getParentNode();

				//Go as far back as needed to get to package nodes
				while(!parentNode.getNodeName().equals("package"))
					parentNode = parentNode.getParentNode();

				String packageName = "";
				if (parentNode.getNodeType() == Node.ELEMENT_NODE){
					Element parElement = (Element) parentNode;
					if (parElement.getElementsByTagName("name").getLength() > 0)
						packageName = parElement.getElementsByTagName("name").item(0).getTextContent();
				}

				if (currClass.getNodeType() == Node.ELEMENT_NODE) {
					Element classElement = (Element) currClass;

					//class name
					String className = classElement.getElementsByTagName("name").item(0).getTextContent();

					NodeList outboundChildNodes = classElement.getElementsByTagName("outbound");				

					//outbound dependents
					ArrayList<String> outbounds = new ArrayList<String>();
					for (int j = 0; j < outboundChildNodes.getLength(); j++){
						String dep = classElement.getElementsByTagName("outbound").item(j).getTextContent();
						if (!outbounds.contains(dep) 
								&& !dep.contains(".ui.") 		// Do not include UI-related dependencies
								&& !dep.contains(".event.")) 	// Do not include event related dependencies
							outbounds.add(dep);
					}					
					Klass theClass = new Klass(className, packageName, outbounds);
					classList.add(theClass);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void printClasses() {
		System.out.println("Total classes: " + classList.size());
		System.out.println("--------------------------------");
		for (int i = 0; i < classList.size(); i++){
			System.out.println((i+1) + ". " + classList.get(i).getName());
			System.out.println("Package name: " + classList.get(i).getPackageName());
			System.out.println("Dependencies: ");
			List<String> deps = classList.get(i).getOutDependencies();
			for (String s : deps)
				System.out.println(s);
			System.out.println("--------------------------------");
		}	
	}

	public List<Klass> getClassList(){
		return this.classList;
	}

	//	public static void main(String argv[]){
	//		XMLParser parser = new XMLParser("Result.xml");
	//		parser.printClasses();
	//	}

}