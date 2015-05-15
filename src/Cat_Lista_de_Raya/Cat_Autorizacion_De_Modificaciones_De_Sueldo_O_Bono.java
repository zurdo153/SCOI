package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.Connexion;
import Obj_Principal.Componentes;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Autorizacion_De_Modificaciones_De_Sueldo_O_Bono extends JFrame {
	    String Activo ="";
	    int aceptar_negar=0;
	    
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Connexion con = new Connexion();
		
		DefaultTableModel model = new DefaultTableModel(0,12){
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
		             return listacolumnas(12)[columnIndex];
		         }   
			public boolean isCellEditable(int fila, int columna){
				if(columna < 10)
					return false;
				return true;
			}
		};

		
		@SuppressWarnings("rawtypes")
		public Class[] listacolumnas(int Columnas){
		Class[] lista = new Class[Columnas];
		for (int i = 0; i<lista.length; i++){
			if(i==11){
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
		JLabel JLBactivo= new JLabel();
		JTextField txtFolio = new Componentes().text(new JTextField(),"Teclee Folio del Empleado", 150, "Integer");
		JTextField txtFecha = new Componentes().text(new JTextField(),"Teclee Nombre del Empleado", 150, "String");
	    JButton btnAceptar = new JButton("Aceptar",new ImageIcon("Imagen/Aplicar.png"));
	    JButton btnNegar = new JButton("Negar",new ImageIcon("Imagen/Delete.png"));
	    
	    
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Cat_Autorizacion_De_Modificaciones_De_Sueldo_O_Bono()	{
			int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
			int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
			
			setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
			
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
			this.setTitle("Autorizacion De Modificaciones De Sueldo O Bono");

			campo.setBorder(BorderFactory.createTitledBorder("Seleccione Los Sueldo o Bonos A Aplicar"));
			trsfiltro = new TableRowSorter(model); 
			tabla.setRowSorter(trsfiltro);  
			campo.add(getPanelTabla()).setBounds(15,42,ancho-25,alto-125);
			campo.add(txtFolio).setBounds(15,20,70,20);
			campo.add(txtFecha).setBounds(85,20,270,20);

			campo.add(btnNegar).setBounds(475,20,100,20);
			campo.add(btnAceptar).setBounds(643,20,100,20);
			
			Checar_Activo();
			Actualizar_tabla();
			cargar_render();
			
			campo.add(JLBactivo).setBounds(780,20,350,20);
			if(Activo.equals("Activada"))
			     {JLBactivo.setText("<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLUE><CENTER><b><p>Esta Funcion Se Encuentra: "+Activo+"</p></b></CENTER></FONT></html>");}
			else{JLBactivo.setText("<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=RED><CENTER><b><p>Esta Funcion Se Encuentra: "+Activo+"</p></b></CENTER></FONT></html>");}
//			agregar(tabla);
			cont.add(campo);
			txtFolio.addKeyListener(opFiltroFolio);
			txtFecha.addKeyListener(opFiltroFecha);
			btnAceptar.addActionListener(opaceptar);
			btnNegar.addActionListener(opnegar);
		}
		
		
		ActionListener opaceptar = new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
				aceptar_negar=1;
				verificar();
			}
		};
		
		ActionListener opnegar = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				aceptar_negar=0;
				verificar();
			}
		};
		
		
		public void verificar(){
			if(tabla.isEditing()){
				tabla.getCellEditor().stopCellEditing();
			}
			
			for (int i=0;i<tabla.getRowCount();i++){
			if((tabla.getValueAt(i,11).toString().trim()).equals("true")){
				if((tabla.getValueAt(i,10).toString().trim()).equals("")){
					JOptionPane.showMessageDialog(null, "Todo Sueldo Aprobado o Cancelado Necesita Tener Observacion \n El Sueldo Selecionado Del Colaborador: \n"+tabla.getValueAt(i,1).toString().trim()+" No Tiene" , "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
	                return;
				}else{
					actualizar();
			    	return;
				}
			};
			}
			JOptionPane.showMessageDialog(null, "Necesita Por Lo Menos Seleccionar Un Registro", "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			return;
		};
		
		
		public void actualizar(){
		Object[][] arreglo_guardado= new Object[tabla.getRowCount()][7];
		for (int i=0;i<tabla.getRowCount();i++){
	       	 arreglo_guardado[i][0]=tabla.getValueAt(i,0).toString().trim();
	         arreglo_guardado[i][1]=tabla.getValueAt(i,5).toString().trim();
	       	 arreglo_guardado[i][2]=tabla.getValueAt(i,7).toString().trim();
	       	 arreglo_guardado[i][3]=tabla.getValueAt(i,9).toString().trim();
	       	 arreglo_guardado[i][4]=tabla.getValueAt(i,10).toString().trim();
	       	 arreglo_guardado[i][5]=aceptar_negar;
	       	 arreglo_guardado[i][6]=tabla.getValueAt(i,11).toString().trim();
		    }
		if(new ActualizarSQL().Aceptar_Negar_Sueldo_o_Bono(arreglo_guardado)){
        	while(tabla.getRowCount()>0){	
	              model.removeRow(0);  }
        	Actualizar_tabla();
			JOptionPane.showMessageDialog(null, "Se Actualizo Correctamente", "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
		}else{
			JOptionPane.showMessageDialog(null, "Error Al Actualizar", "Avise al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		}
		}
		
		
		public String  Checar_Activo() {
			Connexion con = new Connexion();
			String query = "select case when validacion_sueldo_auditoria='true' then 'Activada' else 'Desactivada' end as Status_Auditoria "+
					       "         from tb_configuracion_sistema  ";
			
			Statement stmt = null;
			try {
				stmt = con.conexion().createStatement();
			    ResultSet rs = stmt.executeQuery(query);
				while(rs.next()){
					Activo =(rs.getString(1));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("Error");
				JOptionPane.showMessageDialog(null, "Error en Cat_Autorizacion_De_Cambio_De_Sueldo_O_Bono en la funcion Checar_Activo()"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				return Activo ;
			}
			finally{
				 if (stmt != null) { try {
					stmt.close();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error en Cat_Autorizacion_De_Cambio_De_Sueldo_O_Bono en la funcion Checar_Activo()"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
					e.printStackTrace();
				} }
			}
			return Activo;
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
		
    	public void cargar_render(){
    		//		tipo de valor = imagen,chb,texto
//    		tabla.getColumnModel().getColumn(# columna).setCellRenderer(new CellRenderer("tipo_de_valor","alineacion","tipo_de_letra","negrita",# tamanio_fuente));
        
    		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("numerico","derecha","Arial","negrita",10)); 
    		tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",10)); 
    		tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",10));
    		tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",9));
    		tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("numerico","derecha","Arial","negrita",11)); 
    		tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("numerico","derecha","Arial","negrita",11)); 
    		tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("numerico","derecha","Arial","negrita",11)); 
    		tabla.getColumnModel().getColumn(7).setCellRenderer(new tablaRenderer("numerico","derecha","Arial","negrita",11));
    		tabla.getColumnModel().getColumn(8).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",10));
    		tabla.getColumnModel().getColumn(9).setCellRenderer(new tablaRenderer("fecha","centro","Arial","normal",10));
    		tabla.getColumnModel().getColumn(10).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",10));
    		tabla.getColumnModel().getColumn(11).setCellRenderer(new tablaRenderer("chb","","","",0));
    	}
    	
		
	   	private JScrollPane getPanelTabla()	{		
			int a=70,b=200;
			
			tabla.getColumnModel().getColumn(0).setHeaderValue("Folio Empleado");
			tabla.getColumnModel().getColumn(0).setMaxWidth(a);
			tabla.getColumnModel().getColumn(0).setMinWidth(a);
			tabla.getColumnModel().getColumn(1).setHeaderValue("Empleado");
			tabla.getColumnModel().getColumn(1).setMaxWidth(b+a);
			tabla.getColumnModel().getColumn(1).setMinWidth(b+a);
			tabla.getColumnModel().getColumn(2).setHeaderValue("Establecimiento");
			tabla.getColumnModel().getColumn(2).setMaxWidth(b-80);
			tabla.getColumnModel().getColumn(2).setMinWidth(b-80);
			tabla.getColumnModel().getColumn(3).setHeaderValue("Puesto");
			tabla.getColumnModel().getColumn(3).setMaxWidth(b-30);
			tabla.getColumnModel().getColumn(3).setMinWidth(b-30);
			tabla.getColumnModel().getColumn(4).setHeaderValue("Sueldo Vig");
			tabla.getColumnModel().getColumn(4).setMaxWidth(a+5);
			tabla.getColumnModel().getColumn(4).setMinWidth(a+5);
			tabla.getColumnModel().getColumn(5).setHeaderValue("Sueldo Nvo");
			tabla.getColumnModel().getColumn(5).setMaxWidth(a+5);
			tabla.getColumnModel().getColumn(5).setMinWidth(a+5);
			tabla.getColumnModel().getColumn(6).setHeaderValue("Bono Vig");
			tabla.getColumnModel().getColumn(6).setMaxWidth(a);
			tabla.getColumnModel().getColumn(6).setMinWidth(a);
			tabla.getColumnModel().getColumn(7).setHeaderValue("Bono Nvo");
			tabla.getColumnModel().getColumn(7).setMaxWidth(a);
			tabla.getColumnModel().getColumn(7).setMinWidth(a);
			tabla.getColumnModel().getColumn(8).setHeaderValue("Empleado Modifico");
			tabla.getColumnModel().getColumn(8).setMaxWidth(b+a);
			tabla.getColumnModel().getColumn(8).setMinWidth(b+a);
			tabla.getColumnModel().getColumn(9).setHeaderValue("Fecha");
			tabla.getColumnModel().getColumn(9).setMaxWidth(a*2);
			tabla.getColumnModel().getColumn(9).setMinWidth(a*2);
			tabla.getColumnModel().getColumn(10).setHeaderValue("Observaciones");
			tabla.getColumnModel().getColumn(10).setMaxWidth(b+b-10);
			tabla.getColumnModel().getColumn(10).setMinWidth(b+b-10);
			tabla.getColumnModel().getColumn(11).setHeaderValue("Seleccion");
			tabla.getColumnModel().getColumn(11).setMaxWidth(a);
			tabla.getColumnModel().getColumn(11).setMinWidth(a);
			
	    	tabla.getTableHeader().setReorderingAllowed(false) ;
	    	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			

			 JScrollPane scrol = new JScrollPane(tabla);
			   
		    return scrol; 
		}
		
	   	public void Actualizar_tabla(){
			Statement s;
			ResultSet rs;
			try {
				s = con.conexion().createStatement();
				rs = s.executeQuery("exec sp_select_empleados_con_sueldo_pendiente_de_validar");
				Object [] fila = new Object[12];
				while (rs.next()) {
				   fila[0] = rs.getString(1)+"  ";
				   fila[1] = "   "+rs.getString(2);
				   fila[2] = "   "+rs.getString(3);
				   fila[3] = "   "+rs.getString(4);
				   fila[4] = "   "+rs.getString(5);
				   fila[5] = "   "+rs.getString(6);
				   fila[6] = "   "+rs.getString(7);
				   fila[7] = "   "+rs.getString(8);
				   fila[8] = "   "+rs.getString(9);
				   fila[9] = "   "+rs.getString(10);
				   fila[10] = "   "+rs.getString(11);
				   fila[11] = false;
				   
			  	   model.addRow(fila); 
				}	
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	   	};
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
				new Cat_Autorizacion_De_Modificaciones_De_Sueldo_O_Bono().setVisible(true);
			}catch(Exception e){	}
		}
	}

