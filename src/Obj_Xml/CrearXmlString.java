package Obj_Xml;

import javax.xml.parsers.DocumentBuilderFactory;

import java.io.StringWriter;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CrearXmlString{
	
	public String CadenaXML(final JTable tabla, int[] ignorarColumnas){
		
		// se crea la cabecera de la tabla para poder leerla y modificarla
		TableColumnModel tcm = tabla.getTableHeader().getColumnModel();
		 
		// se obtiene la posición de la columna que queremos conocer y con getheadervalue se obtiene el
		// titulo de la columna para hacer las validaciones
		//		System.out.println(tcm.getColumn(0).getHeaderValue());
		
		String cadena="";
		try {
	         DocumentBuilderFactory dbFactory =
	         DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.newDocument();
	         
	         // root element
	         Element rootElement = doc.createElement("Root");
	         doc.appendChild(rootElement);

	         for(int i=0; i<tabla.getRowCount(); i++){
	        	 
	        	 // supercars element
	             Element supercar = doc.createElement("fila");
	             rootElement.appendChild(supercar);
	             
	             // setting attribute to element
//	             Attr attr = doc.createAttribute("company");
//	             attr.setValue("Ferrari");
//	             supercar.setAttributeNode(attr);
	             
	             for(int j=0; j<tabla.getColumnCount(); j++){
	            	 
	            	 boolean bandera_colum = false;
	            	 for(int colum=0; colum<ignorarColumnas.length; colum++){
	            		 if(j==ignorarColumnas[colum]){
	            			 bandera_colum = true;
	            		 }
	            	 }
	            	 
	            	 if(!bandera_colum){
	            		 // carname element
//		            	 System.out.println(tcm.getColumn(j).getHeaderValue().toString());
			             Element carname = doc.createElement(tcm.getColumn(j).getHeaderValue().toString());
//		           	 	Attr attrType = doc.createAttribute("type");
//		             	attrType.setValue("formula one");
//		             	carname.setAttributeNode(attrType);
		             	carname.appendChild(doc.createTextNode(tabla.getValueAt(i, j).toString()));
		             	supercar.appendChild(carname);
		
//		             	Element carname1 = doc.createElement("carname");
//		             	Attr attrType1 = doc.createAttribute("type");
//		             	attrType1.setValue("sports");
//		             	carname1.setAttributeNode(attrType1);
//		             	carname1.appendChild(doc.createTextNode(j+""));
//		             	supercar.appendChild(carname1);
	            	 }

	             }             
	         }
	         
	         // write the content into xml file
	         DOMSource domSource=new DOMSource(doc);
	         StringWriter writer=new StringWriter();
	         StreamResult result=new StreamResult(writer);
	         TransformerFactory tf=TransformerFactory.newInstance();
	         Transformer transformer=tf.newTransformer();
	         transformer.transform(domSource,result);
	         
//	         System.out.println(writer.toString());
	         cadena = writer.toString();
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	         cadena="";
	      }
		return cadena;
	}

   public static void main(String argv[]) {
	   new CrearXmlString();      
   }
}