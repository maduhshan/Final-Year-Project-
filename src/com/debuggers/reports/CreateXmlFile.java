package com.debuggers.reports;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;

public class CreateXmlFile {
	

public void createXml(String algorithmType, Hashtable<Integer, Integer> table) {

      try {
         DocumentBuilderFactory dbFactory =
         DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = 
            dbFactory.newDocumentBuilder();
         Document doc = dBuilder.newDocument();
         // root element
         Element rootElement = doc.createElement("Solutions");
         doc.appendChild(rootElement);

         //  supercars element
         Element algorithm = doc.createElement("Algorithm");
         rootElement.appendChild(algorithm);

         // setting attribute to element
         Attr attr = doc.createAttribute("company");
         attr.setValue(algorithmType);
         algorithm.setAttributeNode(attr);

         Iterator<Integer> it = table.keySet().iterator();
         while (it.hasNext()){
        	 Integer generationNo = it.next();
        	  // carname element
        	    Element carname = doc.createElement("CandidateSolution");
                Attr attrType = doc.createAttribute("type");
                attrType.setValue("Generation Number "+ generationNo);
                carname.setAttributeNode(attrType);
                carname.appendChild(
                doc.createTextNode(Integer.toString(table.get(generationNo))));
                algorithm.appendChild(carname);
         }

         // write the content into xml file
         TransformerFactory transformerFactory =
         TransformerFactory.newInstance();
         Transformer transformer =
         transformerFactory.newTransformer();
         DOMSource source = new DOMSource(doc);
         StreamResult result =
         new StreamResult(new File("C:\\Users\\Shenali\\Documents\\CS 04 - Learning materials\\SE2\\Project\\result.xml"));
         transformer.transform(source, result);
         // Output to console for testing
         StreamResult consoleResult =
         new StreamResult(System.out);
         transformer.transform(source, consoleResult);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
