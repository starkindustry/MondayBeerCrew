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
	private static List<Klass> classList = new ArrayList<Klass>();
	// code stolen from http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/  
	public static void main(String argv[]) {

		try {

			File fXmlFile = new File("Result3.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			NodeList classes = doc.getElementsByTagName("class");
			for (int i = 0; i < classes.getLength(); i++) {

				Node currClass = classes.item(i);

				if (currClass.getNodeType() == Node.ELEMENT_NODE) {
					Element classElement = (Element) currClass;
					
					//class name
					String className = classElement.getElementsByTagName("name").item(0).getTextContent();

					NodeList outboundChildNodes = classElement.getElementsByTagName("outbound");			
					System.out.println("\nCurrent Class : " + className);
				
					System.out.println("Outbound dependencies: ");
					
					//outbound dependents
					ArrayList<String> outbounds = new ArrayList<String>();
					for (int j = 0; j < outboundChildNodes.getLength(); j++){
						String dep = classElement.getElementsByTagName("outbound").item(j).getTextContent();
						if (!outbounds.contains(dep) 
								&& !dep.contains(".ui.") 		// Do not include UI-related dependencies
								&& !dep.contains(".event.")) 	// Do not include event related dependencies
							outbounds.add(dep);
					}
					for (String s : outbounds)
						System.out.println(s);
					
					Klass theClass = new Klass(className, outbounds);
					classList.add(theClass);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}