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
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.Connexion;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Autorizacion_De_Modificaciones_De_Sueldo_O_Bono extends JFrame {
	    String Activo ="";
	    int aceptar_negar=0;
	    
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Connexion con = new Connexion();
		
		public JTextField txtFiltro = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla <<<", 300, "String");

		@SuppressWarnings("rawtypes")
		public Class[] tipos(){
			Class[] tip = new Class[Cantidad_Real_De_Columnas];
			
			for(int i =0; i<Cantidad_Real_De_Columnas; i++){
				if(i==checkboxindex-1){
					tip[i]=java.lang.Boolean.class;
				}else{
					tip[i]=java.lang.Object.class;
				}
				
			}
			return tip;
		}
		
		int Cantidad_Real_De_Columnas=16,checkboxindex=16;
		public void init_tabla(){
	    	this.tabla.getColumnModel().getColumn(0).setMinWidth(40);		
	    	this.tabla.getColumnModel().getColumn(1).setMinWidth(300);
	    	this.tabla.getColumnModel().getColumn(2).setMinWidth(100);
	    	this.tabla.getColumnModel().getColumn(3).setMinWidth(120);
	    	this.tabla.getColumnModel().getColumn(4).setMinWidth(65);
	    	this.tabla.getColumnModel().getColumn(5).setMinWidth(65);
	    	this.tabla.getColumnModel().getColumn(6).setMinWidth(65);
	    	this.tabla.getColumnModel().getColumn(7).setMinWidth(65);
	    	this.tabla.getColumnModel().getColumn(8).setMinWidth(65);
	    	this.tabla.getColumnModel().getColumn(9).setMinWidth(65);
	    	this.tabla.getColumnModel().getColumn(10).setMinWidth(65);
	    	this.tabla.getColumnModel().getColumn(11).setMinWidth(65);
	    	this.tabla.getColumnModel().getColumn(12).setMinWidth(150);
	    	this.tabla.getColumnModel().getColumn(13).setMinWidth(100);
	    	this.tabla.getColumnModel().getColumn(14).setMinWidth(150);
	    	this.tabla.getColumnModel().getColumn(15).setMinWidth(60);
			String comando="exec sp_select_empleados_con_sueldo_pendiente_de_validar";
			String basedatos="26",pintar="si";
			new Obj_tabla().Obj_Refrescar(tabla,modelo, Cantidad_Real_De_Columnas, comando, basedatos,pintar,checkboxindex);
	    }
		
	 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio Empleado", "Empleado", "Establecimiento","Puesto", "Sueldo","Sueldo Nvo","Bono","Bono Nvo","B.Asistencia","B.Asist.Nvo","B.Puntualidad","B.Punt.Nvo","Empleado Modifico","Fecha","Observaciones","Seleccion"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = tipos();
		
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				
	         return types[columnIndex];
	     }
			public boolean isCellEditable(int fila, int columna){
				if(columna>=14)
					return true; return false;
			}
	    };
	    JTable tabla = new JTable(modelo);
		public JScrollPane scroll_tabla = new JScrollPane(tabla);
		
		JLabel JLBactivo= new JLabel();
	    JButton btnAceptar = new JButton("Aceptar",new ImageIcon("Imagen/Aplicar.png"));
	    JButton btnNegar = new JButton("Negar",new ImageIcon("Imagen/Delete.png"));
	    
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

			campo.add(scroll_tabla).setBounds(15,42,ancho-25,alto-125);
			campo.add(txtFiltro).setBounds(15,20,370,20);
			campo.add(btnNegar).setBounds(475,20,100,20);
			campo.add(btnAceptar).setBounds(643,20,100,20);
			
			Checar_Activo();
			init_tabla();
			
			campo.add(JLBactivo).setBounds(780,20,350,20);
			if(Activo.equals("Activada"))
			     {JLBactivo.setText("<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLUE><CENTER><b><p>Esta Funcion Se Encuentra: "+Activo+"</p></b></CENTER></FONT></html>");}
			else{JLBactivo.setText("<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=RED><CENTER><b><p>Esta Funcion Se Encuentra: "+Activo+"</p></b></CENTER></FONT></html>");}
			cont.add(campo);
			txtFiltro.addKeyListener(op_filtro);
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
			if((tabla.getValueAt(i,checkboxindex).toString().trim()).equals("true")){
				if((tabla.getValueAt(i,14).toString().trim()).equals("")){
					JOptionPane.showMessageDialog(null, "Todo Sueldo o Bono, Aprobado o Cancelado Necesita Tener Observacion \n El Sueldo o Bono Selecionado Del Colaborador: \n"+tabla.getValueAt(i,1).toString().trim()+" No Tiene" , "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
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
		Object[][] arreglo_guardado= new Object[tabla.getRowCount()][9];
		for (int i=0;i<tabla.getRowCount();i++){
	       	 arreglo_guardado[i][0]=tabla.getValueAt(i,0).toString().trim();//folio_empleado
	         arreglo_guardado[i][1]=tabla.getValueAt(i,5).toString().trim();//Sueldo Nuevo
	       	 arreglo_guardado[i][2]=tabla.getValueAt(i,7).toString().trim();//Bono Nuevo
	       	 arreglo_guardado[i][3]=tabla.getValueAt(i,9).toString().trim();//Bono Asistencia Nuevo
	       	 arreglo_guardado[i][4]=tabla.getValueAt(i,11).toString().trim();//Bono puntualidad Nuevo
	       	 arreglo_guardado[i][5]=tabla.getValueAt(i,13).toString().trim();//fecha
	       	 arreglo_guardado[i][6]=tabla.getValueAt(i,14).toString().trim();//observacion
	       	 arreglo_guardado[i][7]=tabla.getValueAt(i,15).toString().trim();//checkbox
	       	 arreglo_guardado[i][8]=aceptar_negar;
		    }
		
		if(new ActualizarSQL().Aceptar_Negar_Sueldo_o_Bono(arreglo_guardado)){
        	init_tabla();
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
		
		
		
		KeyListener op_filtro = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				int[] columnas ={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14};
				new Obj_Filtro_Dinamico_Plus(tabla, txtFiltro.getText().toUpperCase(), columnas);
			}
			public void keyTyped(KeyEvent arg0)   {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Autorizacion_De_Modificaciones_De_Sueldo_O_Bono().setVisible(true);
			}catch(Exception e){	}
		}
	}

