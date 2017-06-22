package Obj_Formato;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class XMLGenerator {
   
    public static void generate(String name,Object[][] array) throws Exception{

        if(array.length<=0){
            System.out.println("ERROR empty ArrayList");
            return;
        }else{

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, name, null);
            document.setXmlVersion("1.0");

            //Main Node
            Element raiz = document.getDocumentElement();
            
            //Por cada key creamos un item que contendrá la key y el value
            for(int i=0; i<array.length;i++){

            	//Item Node
                Element itemNode = document.createElement("fila"); 
	                
	                for(int j=0; j<5;j++){
	                	//Key Node
	                	Element keyNode = document.createElement("Columna_"+j); 
	                	Text nodeKeyValue = document.createTextNode(array[i][j].toString().trim());
	                	keyNode.appendChild(nodeKeyValue);      
	                
	                	//append keyNode and valueNode to itemNode
	                	itemNode.appendChild(keyNode);
	                }
	                //append itemNode to raiz
	                raiz.appendChild(itemNode); //pegamos el elemento a la raiz "Documento"
            }                
            
            //Generate XML
            Source source = new DOMSource(document);
            //Indicamos donde lo queremos almacenar
            Result result = new StreamResult(new java.io.File(name+".xml")); //nombre del archivo
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
        }
    }
    
	public static void main(String[] args) {
        String nombre_archivo = "Tabla";
        
        Object[][] arreglo = new Object[5][5];
        
        for(int i=0; i<5; i++){
        	for(int j=0; j<6; j++){
            	arreglo[i][j]=(j*i);
            }
        }

        try { 
            generate(nombre_archivo, arreglo);
            System.out.println("Correcto");
        } catch (Exception e) {
        	System.out.println("Falló");
        }
    }

}