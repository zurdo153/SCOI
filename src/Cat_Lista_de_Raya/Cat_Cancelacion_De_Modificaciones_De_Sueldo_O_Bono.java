package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
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
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Cancelacion_De_Modificaciones_De_Sueldo_O_Bono extends JFrame {
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Connexion con = new Connexion();
		
		@SuppressWarnings("rawtypes")
		public Class[] tipos(){
			Class[] tip = new Class[Columnas];
				for(int i =0; i<Columnas; i++){
					if(i==checkboxindex-1){
						tip[i]=java.lang.Boolean.class;
					}else{
						tip[i]=java.lang.Object.class;
					}
				}
				return tip;
		}
		
		int Columnas=16,checkboxindex=16;
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
			new Obj_tabla().Obj_Refrescar(tabla,modelo, Columnas, comando, basedatos,pintar,checkboxindex);
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
		
		JCButton btnBuscar    = new JCButton(""              ,"cancelado_122x32.png"              ,"AzulO"); 
		JCButton btnActualizar= new JCButton("Actualizar"    ,"Actualizar_32x32.png"              ,"Verde"); 
		JTextField txtFiltro  = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 500, "String",tabla,Columnas);
		
		public Cat_Cancelacion_De_Modificaciones_De_Sueldo_O_Bono()	{
			this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
			this.setTitle("Cancelacion De Modificaciones De Sueldo O Bono");
			this.campo.setBorder(BorderFactory.createTitledBorder("Seleccione Los Sueldo o Bonos  y De Click Al Boton Cancelar"));
			int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
			int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
			campo.add(btnBuscar).setBounds    (590,10,132,34);
			campo.add(btnActualizar).setBounds(750,10,132,34);
			
			campo.add(txtFiltro).setBounds   (15,25,500,20);
			campo.add(scroll_tabla).setBounds(15,47,ancho-25,alto-125);

			init_tabla();
			cont.add(campo);
			btnBuscar.addActionListener(opCancelar);
			btnActualizar.addActionListener(opActualizar);
		}
		
		ActionListener opActualizar = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				init_tabla();
			}
		};
		
		
		ActionListener opCancelar = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tabla.isEditing()){tabla.getCellEditor().stopCellEditing();}
				for (int i=0;i<tabla.getRowCount();i++){
					if((tabla.getValueAt(i,checkboxindex-1).toString().trim()).equals("true")){
						if((tabla.getValueAt(i,14).toString().trim()).equals("")){
							JOptionPane.showMessageDialog(null, "Todo Sueldo o Bono Cancelado Necesita Tener Observacion \n El Sueldo o Bono Selecionado Del Colaborador: \n"+tabla.getValueAt(i,1).toString().trim()+" Le Falta Observacion" , "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			                return;
						}else{
							actualizar();
					    	return;
						}
					};
				}
				JOptionPane.showMessageDialog(null, "Necesita Por Lo Menos Seleccionar Un Registro", "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
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
	       	 arreglo_guardado[i][6]="Cancelado Por DH.:"+tabla.getValueAt(i,14).toString().trim();//observacion
	       	 arreglo_guardado[i][7]=tabla.getValueAt(i,15).toString().trim();//checkbox
	       	 arreglo_guardado[i][8]=0;
		    }
		
		if(new ActualizarSQL().Aceptar_Negar_Sueldo_o_Bono(arreglo_guardado)){
        	init_tabla();
			JOptionPane.showMessageDialog(null, "", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/cancelado_122x32.png"));
		}else{
			JOptionPane.showMessageDialog(null, "Error Al Actualizar", "Avise al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		}
		}
		
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Cancelacion_De_Modificaciones_De_Sueldo_O_Bono().setVisible(true);
			}catch(Exception e){	}
		}
	}

