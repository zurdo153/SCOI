package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Cat_Reportes.Cat_Reportes_De_Diferencias_Entre_Dos_Listas_De_Raya;
import Cat_Reportes.Cat_Reportes_De_Diferencias_De_Sueldo_Y_Bonos_En_Listas_De_Raya;
import Cat_Reportes.Cat_Reportes_De_Fuente_De_Sodas;
import Cat_Reportes.Cat_Reportes_De_Infonacot_De_Lista_De_Raya;
import Cat_Reportes.Cat_Reportes_De_Infonavit_De_Lista_De_Raya;
import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Revision_De_Lista_Raya;
import Obj_Principal.Componentes;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Filtro_De_Listas_De_Raya_Pasadas extends JDialog {
		
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Connexion con = new Connexion();
		
		DefaultTableModel model = new DefaultTableModel(0,2){
			public boolean isCellEditable(int fila, int columna){
				if(columna < 0)
					return true;
				return false;
			}
		};
		
		JTable tabla = new JTable(model);
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		JTextField txtFolio = new Componentes().text(new JTextField(),"Teclee Folio Lista de Raya", 150, "Integer");
		JTextField txtFecha = new Componentes().text(new JTextField(),"Teclee Fecha Final de La Lista de Raya <Fecha de Cierre>", 150, "String");
	    int Catalogo=0;
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Cat_Filtro_De_Listas_De_Raya_Pasadas(Integer catalogo)	{
			Catalogo=catalogo;
			
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/Filter-List-icon16.png"));
			this.setTitle("Reporte de Listas de Raya Pasadas");

			campo.setBorder(BorderFactory.createTitledBorder("Seleccione La Lista de Raya a Consultar"));
			trsfiltro = new TableRowSorter(model); 
			tabla.setRowSorter(trsfiltro);  
			campo.add(getPanelTabla()).setBounds(15,42,220,360);
			campo.add(txtFolio).setBounds(15,20,100,20);
			campo.add(txtFecha).setBounds(116,20,100,20);
			
			agregar(tabla);
			cont.add(campo);
			
			txtFolio.addKeyListener(opFiltroFolio);
			txtFecha.addKeyListener(opFiltroFecha);
			this.setSize(255,445);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
		}
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		        		int fila = tabla.getSelectedRow();
		    			int folio = Integer.parseInt(tabla.getValueAt(fila, 0).toString().trim());
		    				    			
		    			if (new Obj_Revision_De_Lista_Raya().Lista_de_Raya_Pasada(folio)) {
		    				
		    				switch(Catalogo){
		    				case 1:		new Cat_Reportes_De_Fuente_De_Sodas().obtiene_lista_de_raya_selecionada(folio);
		    			           	dispose();
		    				break;
		    				
		    				case 2:		new Cat_Reportes_De_Prestamos_De_Lista_De_Raya().Impresion_de_Reporte_Prestamos_LRPasadas(folio);
    			           	dispose();
    				           break;
    				           
		    				case 3:		seleccionDeReporte(folio);
    			           	dispose();
    				           break;
		    				case 4:		new Cat_Reportes_De_Infonavit_De_Lista_De_Raya().obtiene_lista_de_raya_selecionada(folio);
    			           	dispose();
    				           break;
		    				case 5:		new Cat_Reportes_De_Diferencias_De_Sueldo_Y_Bonos_En_Listas_De_Raya().obtiene_lista_de_raya_selecionada(folio);
    			           	dispose();
    				           break;
		    				case 6:		new  Cat_Reportes_De_Diferencias_Entre_Dos_Listas_De_Raya().obtiene_lista_de_raya_selecionadac1(folio);
    			           	dispose();
    				           break;
		    				case 7:		new  Cat_Reportes_De_Diferencias_Entre_Dos_Listas_De_Raya().obtiene_lista_de_raya_selecionadac2(folio);
    			           	dispose();
    				           break;
		    				case 8:		new  Cat_Reportes_De_Infonacot_De_Lista_De_Raya().obtiene_lista_de_raya_selecionada(folio);
    			           	dispose();
    				           break;   
		    				case 9:		new  Cat_Reportes_De_Lista_De_Raya().obtiene_lista_de_raya_selecionada_pasada_limpia(folio);
    			           	dispose();
    				           break; 
		    				case 10:		new  Cat_Reportes_De_Lista_De_Raya().generar_reporte(folio,Catalogo);
    			           	dispose();
    				           break;  
		    				case 11:		new  Cat_Reportes_De_Lista_De_Raya().generar_reporte(folio,Catalogo);
    			           	dispose();
    				           break;     
		    				}
		    			}
		    			else{
		    				JOptionPane.showMessageDialog(null,"Error Al Intentar Abrir el Reporte","Error",JOptionPane.ERROR_MESSAGE);
		    			 }
		        	}
		        }
	        });
	    }
		
		public void seleccionDeReporte(int folio){
			
			Icon icon = new ImageIcon("Imagen/equipos-de-tarea-asignada-icono-7668-32.png");
	        
			String[] options = {"Lista De Raya", "Plantilla De Lista De Raya", "Cancelar"};
			String aviso = "";
			int seleccion = JOptionPane.showOptionDialog(null, aviso, "Seleccion De Status", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
			
			if(seleccion==0){
				new Cat_Reportes_De_Lista_De_Raya().obtiene_lista_de_raya_selecionada(folio);
			}
			if(seleccion==1){
				new Cat_Reportes_De_Lista_De_Raya().obtiene_lista_de_raya_selecionada2(folio);
			}
		} 
		
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
		
	   	private JScrollPane getPanelTabla()	{		
	    	tabla.getTableHeader().setReorderingAllowed(false) ;
	    	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			tabla.getColumnModel().getColumn(0).setHeaderValue("Num. Lista Raya");
			tabla.getColumnModel().getColumn(0).setMaxWidth(100);
			tabla.getColumnModel().getColumn(0).setMinWidth(100);
			tabla.getColumnModel().getColumn(1).setHeaderValue("Fecha Cierre");
			tabla.getColumnModel().getColumn(1).setMaxWidth(100);
			tabla.getColumnModel().getColumn(1).setMinWidth(100);
			
		    tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
		    tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
					
			Statement s;
			ResultSet rs;
			try {
				s = con.conexion().createStatement();
				rs = s.executeQuery("exec sp_filtro_lista_rayas_pasadas");
				String [] fila = new String[4];
				while (rs.next()) {
				   fila[0] = rs.getString(1)+"  ";
				   fila[1] = "   "+rs.getString(2);
				   
			  	   model.addRow(fila); 
				}	
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			 JScrollPane scrol = new JScrollPane(tabla);
			   
		    return scrol; 
		}
		
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Filtro_De_Listas_De_Raya_Pasadas(1).setVisible(true);
			}catch(Exception e){	}
		}
	}

