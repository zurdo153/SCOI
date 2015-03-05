package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Principal.Componentes;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Autorizacion_De_Cambios_De_Sueldo_O_Bono extends JDialog {
		
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Connexion con = new Connexion();
		

		
		
		DefaultTableModel model = new DefaultTableModel(0,9){
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
		             return listacolumnas(9)[columnIndex];
		         }   
			
			public boolean isCellEditable(int fila, int columna){
				if(columna < 8)
					return false;
				return true;
			}
		};

		
		@SuppressWarnings("rawtypes")
		public Class[] listacolumnas(int Columnas){
		Class[] lista = new Class[Columnas];
		for (int i = 0; i<lista.length; i++){
			if(i==8){
				lista[i]=(Boolean.class);
			}else{
				lista[i] =(String.class);
			}
		}
		return lista;
	};
		
		
		
		JTable tabla = new JTable(model);
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		JTextField txtFolio = new Componentes().text(new JTextField(),"Teclee Folio Lista de Raya", 150, "Integer");
		JTextField txtFecha = new Componentes().text(new JTextField(),"Teclee Fecha Final de La Lista de Raya <Fecha de Cierre>", 150, "String");
	    int Catalogo=0;
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Cat_Autorizacion_De_Cambios_De_Sueldo_O_Bono(Integer catalogo)	{
			this.setSize(1024,445);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			Catalogo=catalogo;
			
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
			this.setTitle("Reporte de Listas de Raya Pasadas");

			campo.setBorder(BorderFactory.createTitledBorder("Seleccione La Lista de Raya a Consultar"));
			trsfiltro = new TableRowSorter(model); 
			tabla.setRowSorter(trsfiltro);  
			campo.add(getPanelTabla()).setBounds(15,42,990,360);
			campo.add(txtFolio).setBounds(15,20,100,20);
			campo.add(txtFecha).setBounds(116,20,100,20);
			
//			agregar(tabla);
			cont.add(campo);
			
			txtFolio.addKeyListener(opFiltroFolio);
			txtFecha.addKeyListener(opFiltroFecha);

			
		}
		
//		private void agregar(final JTable tbl) {
//	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
//		        public void mouseClicked(MouseEvent e) {
//		        	if(e.getClickCount() == 2){
//		        		int fila = tabla.getSelectedRow();
//		    			int folio = Integer.parseInt(tabla.getValueAt(fila, 0).toString().trim());
//		    				    			
//		    			if (new Obj_Revision_De_Lista_Raya().Lista_de_Raya_Pasada(folio)) {
//		    				
//		    				switch(Catalogo){
//		    				case 1:		new Cat_Reportes_De_Fuente_De_Sodas().obtiene_lista_de_raya_selecionada(folio);
//		    			           	dispose();
//		    				break;
//		    				
//		    				case 2:		new Cat_Reporte_De_Prestamos_De_Lista_De_Raya().Impresion_de_Reporte_Prestamos_LRPasadas(folio);
//    			           	dispose();
//    				           break;
//    				           
//		    				case 3:		new Cat_Reportes_De_Lista_De_Raya().obtiene_lista_de_raya_selecionada(folio);
//    			           	dispose();
//    				           break;
//		    				case 4:		new Cat_Reportes_De_Infonavit_De_Lista_De_Raya().obtiene_lista_de_raya_selecionada(folio);
//    			           	dispose();
//    				           break;
//		    				case 5:		new Cat_Reportes_De_Diferencias_De_Sueldo_Y_Bonos_En_Listas_De_Raya().obtiene_lista_de_raya_selecionada(folio);
//    			           	dispose();
//    				           break;
//		    				}
//		    			}
//		    			else{
//		    				JOptionPane.showMessageDialog(null,"Error Al Intentar Abrir el Reporte","Error",JOptionPane.ERROR_MESSAGE);
//		    			 }
//		        	}
//		        }
//	        });
//	    }
		
		
		
		KeyListener opFiltroFolio = new KeyListener(){
			@SuppressWarnings("unchecked")
			public void keyReleased(KeyEvent arg0) {
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolio.getText(), 0));
			}
			public void keyTyped(KeyEvent arg0) {
				char caracter = arg0.getKeyChar();
				if(((caracter < '0') ||
					(caracter > '9')) &&
				    (caracter != KeyEvent.VK_BACK_SPACE)){
					arg0.consume(); 
				}	
			}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		KeyListener opFiltroFecha = new KeyListener(){
			@SuppressWarnings("unchecked")
			public void keyReleased(KeyEvent arg0) {
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtFecha.getText().toUpperCase().trim(), 1));
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
    	public void llamar_render(){
    		//		tipo de valor = imagen,chb,texto
//    		tabla.getColumnModel().getColumn(# columna).setCellRenderer(new CellRenderer("tipo_de_valor","alineacion","tipo_de_letra","negrita",# tamanio_fuente));
        
    		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("numerico","derecha","Arial","negrita",12)); 
    		tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
    		tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
    		tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
    		tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("numerico","derecha","Arial","negrita",12)); 
    		tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("numerico","derecha","Arial","negrita",12)); 
    		tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
    		tabla.getColumnModel().getColumn(7).setCellRenderer(new tablaRenderer("fecha","centro","Arial","normal",12));
    		tabla.getColumnModel().getColumn(8).setCellRenderer(new tablaRenderer("chb","","","",0));
    	}
    	
		
	   	private JScrollPane getPanelTabla()	{		
			int a=60,b=200;
			
			tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
			tabla.getColumnModel().getColumn(0).setMaxWidth(30);
			tabla.getColumnModel().getColumn(0).setMinWidth(30);
			tabla.getColumnModel().getColumn(1).setHeaderValue("Empleado");
			tabla.getColumnModel().getColumn(1).setMaxWidth(b);
			tabla.getColumnModel().getColumn(1).setMinWidth(b);
			tabla.getColumnModel().getColumn(2).setHeaderValue("Establecimiento");
			tabla.getColumnModel().getColumn(2).setMaxWidth(b);
			tabla.getColumnModel().getColumn(2).setMinWidth(b);
			tabla.getColumnModel().getColumn(3).setHeaderValue("Puesto");
			tabla.getColumnModel().getColumn(3).setMaxWidth(b);
			tabla.getColumnModel().getColumn(3).setMinWidth(b);
			tabla.getColumnModel().getColumn(4).setHeaderValue("Sueldo");
			tabla.getColumnModel().getColumn(4).setMaxWidth(a);
			tabla.getColumnModel().getColumn(4).setMinWidth(a);
			tabla.getColumnModel().getColumn(5).setHeaderValue("Bono");
			tabla.getColumnModel().getColumn(5).setMaxWidth(a);
			tabla.getColumnModel().getColumn(5).setMinWidth(a);
			tabla.getColumnModel().getColumn(6).setHeaderValue("Empleado Modifico");
			tabla.getColumnModel().getColumn(6).setMaxWidth(b);
			tabla.getColumnModel().getColumn(6).setMinWidth(b);
			tabla.getColumnModel().getColumn(7).setHeaderValue("Fecha");
			tabla.getColumnModel().getColumn(7).setMaxWidth(b-a);
			tabla.getColumnModel().getColumn(7).setMinWidth(b-a);
			tabla.getColumnModel().getColumn(8).setHeaderValue("C");
			tabla.getColumnModel().getColumn(8).setMaxWidth(a/2);
			tabla.getColumnModel().getColumn(8).setMinWidth(a/2);
			
	    	tabla.getTableHeader().setReorderingAllowed(false) ;
	    	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			Statement s;
			ResultSet rs;
			try {
				s = con.conexion().createStatement();
				rs = s.executeQuery("exec sp_empleados_con_sueldo_pendiente_de_validar");
				Object [] fila = new Object[9];
				while (rs.next()) {
				   fila[0] = rs.getString(1)+"  ";
				   fila[1] = "   "+rs.getString(2);
				   fila[2] = "   "+rs.getString(3);
				   fila[3] = "   "+rs.getString(4);
				   fila[4] = "   "+rs.getString(5);
				   fila[5] = "   "+rs.getString(6);
				   fila[6] = "   "+rs.getString(7);
				   fila[7] = "   "+rs.getString(8);
				   fila[8] = false;
				   
			  	   model.addRow(fila); 
				}	
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			 JScrollPane scrol = new JScrollPane(tabla);
			   
		    return scrol; 
		}
		
		KeyListener validaCantidad = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e){
				char caracter = e.getKeyChar();				
				if(((caracter < '0') ||	
			    	(caracter > '9')) && 
			    	(caracter != '.' )){
			    	e.consume();
		    	}
			}
			@Override
			public void keyReleased(KeyEvent e) {	
			}
			@Override
			public void keyPressed(KeyEvent arg0) {
			}	
		};
		
		KeyListener validaNumericoConPunto = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				char caracter = e.getKeyChar();
				
			    if(((caracter < '0') ||	
			    	(caracter > '9')) && 
			    	(caracter != '.')){
			    	e.consume();
		    	}
			}
			@Override
			public void keyPressed(KeyEvent e){}
			@Override
			public void keyReleased(KeyEvent e){}
		};
		

		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Autorizacion_De_Cambios_De_Sueldo_O_Bono(1).setVisible(true);
			}catch(Exception e){	}
		}
	}

