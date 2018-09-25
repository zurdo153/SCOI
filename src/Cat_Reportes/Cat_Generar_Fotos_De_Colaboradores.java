package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.BuscarSQL;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Generar_Fotos_De_Colaboradores extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Obj_tabla ObjTab =new Obj_tabla();
	int columnas = 6,checkbox=6;
	public void init_tablaf(){
    	this.tabla.getColumnModel().getColumn(0).setMaxWidth(50);
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(280);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(130);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(180);
    	this.tabla.getColumnModel().getColumn(4).setMinWidth(280);
    	this.tabla.getColumnModel().getColumn(5).setMaxWidth(30);
    	
		String comandof="select emp.folio,"
				+ "		dbo.nombre_empleado(emp.folio) as empleado,"
				+ "		estab.nombre as establecimiento,"
				+ "		depto.departamento,"
				+ "		p.nombre as puesto,"
				+ "		upper(st.nombre) as status"
				+ " from tb_empleado emp"
				+ " inner join tb_establecimiento estab on estab.folio = emp.establecimiento_id"
				+ " inner join tb_departamento depto on depto.folio = emp.departamento"
				+ " inner join tb_puesto p on p.folio = emp.puesto_id"
				+ " inner join tb_status_de_colaboradores st on st.folio = emp.status"
				+ " order by estab.nombre,emp.nombre, emp.ap_paterno, emp.ap_materno";
		
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tabla,modelo, columnas, comandof, basedatos,pintar,checkbox);
    }
	
	DefaultTableModel modelo = new DefaultTableModel(null,new String[]{"Folio", "Colaborador","Establecimiento","Departamento","Puesto","*"}
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.Boolean.class};
	     
	     @SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
             return types[columnIndex];
         }
         public boolean isCellEditable(int fila, int columna){
        	 switch(columna){
        	 	case 0 : return false; 
        	 	case 1 : return false; 
        	 	case 2 : return false; 
        	 	case 3 : return false; 
        	 	case 4 : return false; 
        	 	case 5 : return true;
        	 	} 				
 			return false;
 		}
	};
	JTable tabla = new JTable(modelo);
	JScrollPane scroll_tabla = new JScrollPane(tabla);
	
	JTextField txtFiltro = new Componentes().textfiltro(new JCTextField(), "Filtro De Colaboradores", 200, "String", tabla, 5);
	JCheckBox chbSelect = new JCheckBox("");
	JCButton btnGenerar = new JCButton("Generar", "", "Azul");
	
	Border blackline;
	public Cat_Generar_Fotos_De_Colaboradores() {
		this.cont.add(panel);
		this.setSize(1024,768);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setTitle("Generar Fotos De Colaboradores");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Fotos De Colaboradores"));
        cont.setBackground(new java.awt.Color(255, 255, 255));
   	    tabla.getTableHeader().setReorderingAllowed(false) ;
   	    
   	 int x=20, y=20,width=122,height=20;
   	 
		panel.add(txtFiltro).setBounds     (x   ,y  	,width*8-60   ,height );
		panel.add(chbSelect).setBounds     (width*8-30   ,y  	,20   ,height );
		panel.add(scroll_tabla).setBounds  (x   ,y+=20  ,width*8,600 );
		panel.add(btnGenerar).setBounds     (width*8-80   ,y+=600,100   ,height+10 );
		
		init_tablaf();
		chbSelect.addActionListener(opSeleccion);
		btnGenerar.addActionListener(opGenerarFotos);
   	    
	}
	
	ActionListener opSeleccion = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			for(int i=0; i<tabla.getRowCount(); i++){
				tabla.setValueAt(chbSelect.isSelected(), i, 5);
			}
		}
	};
	
	ActionListener opGenerarFotos = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			txtFiltro.setText("");
			ObjTab.Obj_Filtro(tabla, txtFiltro.getText().toUpperCase(), columnas,txtFiltro);
			txtFiltro.requestFocus();
			
			//crear ruta para carpeta principal de fotos de colaborador
			String rutaPrincipalFotos = "C:/FotosColaborador/";
			File folder = new File(rutaPrincipalFotos);
			
			// creamos fichero princila si no existe
			if(!folder.exists()){ 
				folder.mkdirs();
			}
        	
			//lista de colaboradores seleccionados
        	String folios = "";
			for(int i=0; i<tabla.getRowCount(); i++){
				if(Boolean.valueOf(tabla.getValueAt(i, 5).toString())){
					folios+=tabla.getValueAt(i, 0).toString().trim()+",";
				}
			}
			//quitar ultima coma, si existen registros
			if(folios.length()>0){
				folios = folios.substring(0, folios.length()-1);
			}
			
			try {
				//buscar fotos
				Object[][] lista = new BuscarSQL().GenerarFotos(folios);
				
				// preguntar si se encontraron registros
				if(lista.length>0){
					
//					//crear ruta para carpeta con la fecha del dia actual
//					String rutaConFecha = rutaPrincipalFotos+lista[0][0].toString()+"/";
//					File folderFecha = new File(rutaConFecha);
//					
//					// creamos fichero con fecha del dia si no existe
//					if(!folderFecha.exists()){ 
//						folderFecha.mkdirs();
//					}
//					
					//recorrer arreglo de fotos
					for(int i=0; i<lista.length; i++){
						
						//crear ruta con nombre del archivo
						File rutaFoto = new File(rutaPrincipalFotos+lista[i][1].toString().trim()+".jpg");
						
						//crear foto si no existe
						if(!rutaFoto.exists()){ 
							byte[] bytes = (byte[]) lista[i][2];
							Path path = Paths.get(rutaPrincipalFotos+lista[i][1].toString().trim()+".jpg");
							Files.write(path, bytes);
						}
					}
					
					String onlyPath = "C:\\FotosColaborador";
				    String completeCmd = "explorer.exe /select," + onlyPath;
				    new ProcessBuilder(("explorer.exe " + completeCmd).split(" ")).start();
					
					chbSelect.setSelected(false);
					init_tablaf();
					JOptionPane.showMessageDialog(null, "Las Fotos Fueron Creadas En La Siguente Ruta: "+rutaPrincipalFotos,"Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
					return;
				}else{
					JOptionPane.showMessageDialog(null, "No Se Encontraron Registros", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
    	         	return;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	};

	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Generar_Fotos_De_Colaboradores().setVisible(true);
		}catch(Exception e){	}
	}
}
