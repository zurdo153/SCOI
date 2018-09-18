package Cat_Contabilidad;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
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
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Contabilidad.Obj_Orden_De_Gasto;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Validacion_De_Ordenes_De_Gasto extends JFrame {
	    String aceptar_negar="";
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Connexion con = new Connexion();
		Obj_tabla ObjTab= new Obj_tabla();
		Obj_Usuario usuario = new Obj_Usuario().LeerSession();
		
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
		
		int Cantidad_Real_De_Columnas=21,checkboxindex=1;
		public void init_tabla_principal(){
			this.tabla.getColumnModel().getColumn( 0).setMinWidth(20);
			this.tabla.getColumnModel().getColumn( 0).setMaxWidth(20);
			this.tabla.getColumnModel().getColumn( 1).setMinWidth(50);
	    	this.tabla.getColumnModel().getColumn( 1).setMaxWidth(50);
	    	this.tabla.getColumnModel().getColumn( 2).setMinWidth(330);
	    	this.tabla.getColumnModel().getColumn( 3).setMinWidth(60);
	    	this.tabla.getColumnModel().getColumn( 4).setMinWidth(400);
	    	this.tabla.getColumnModel().getColumn( 5).setMinWidth(110);
	    	this.tabla.getColumnModel().getColumn( 6).setMinWidth(80);
	    	this.tabla.getColumnModel().getColumn( 7).setMinWidth(230);
	    	this.tabla.getColumnModel().getColumn( 8).setMinWidth(120);
	    	this.tabla.getColumnModel().getColumn( 9).setMinWidth(80);
	    	this.tabla.getColumnModel().getColumn(10).setMinWidth(120);
	    	this.tabla.getColumnModel().getColumn(11).setMinWidth(200);
	    	this.tabla.getColumnModel().getColumn(12).setMinWidth(140);
	    	this.tabla.getColumnModel().getColumn(13).setMinWidth(90);
	    	this.tabla.getColumnModel().getColumn(14).setMinWidth(150);
	    	this.tabla.getColumnModel().getColumn(15).setMinWidth(100);
	    	this.tabla.getColumnModel().getColumn(16).setMinWidth(200);
	    	this.tabla.getColumnModel().getColumn(18).setMinWidth(200);
	    	this.tabla.getColumnModel().getColumn(19).setMinWidth(400);
	    	
			String comando = "orden_de_gasto_autorizacion_filtro '"+cmb_status.getSelectedItem().toString().trim()+"'";
			String basedatos="26",pintar="si";
			ObjTab.Obj_Refrescar(tabla,modelo, Cantidad_Real_De_Columnas, comando, basedatos,pintar,checkboxindex);
	    }
		
	 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"S","Folio","Proveedor","Concepto","Descripcion Gasto","Establecimiento","Importe Total","Usuario Solicita", "Fecha","Estatus","Fecha Autorizacion","Usuario Autorizo","Tipo Provedor","Folio Servicio", "Servicio", "Tipo","Usuario Valida", "Folio Pago", "Usuario Realizo Pago", "Observaciones Del Pago", "Folio Corte Caja Chica"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = tipos();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
	         return types[columnIndex];
	     }
			public boolean isCellEditable(int fila, int columna){
				if(columna==0)
					return true; return false;
			}
	    };
	    JTable tabla = new JTable(modelo);
		public JScrollPane scroll_tabla = new JScrollPane(tabla);
		
		String status[] = new Obj_Orden_De_Gasto().Combo_Cuentas();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox cmb_status = new JComboBox(status);
		
		JTextField txtFolio      = new Componentes().text(new JCTextField()  ,"Folio"   ,30   ,"String");
		JTextField txtPedientes  = new Componentes().text(new JCTextField(), "Cant. Pendientes", 150, "String");
		JTextField txtFiltro     = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<",300 , "String",tabla,Cantidad_Real_De_Columnas );
		JTextField txtTotal      = new Componentes().text(new JCTextField()  ,"Total"                     ,30   ,"String");
		
		JCButton btnAceptar      = new JCButton("Aceptar"     ,"Aplicar.png"     ,"Verde");
		JCButton btnNegar        = new JCButton("Negar"       ,"Delete.png"      ,"Rojo" );
		JCButton btnActualizar   = new JCButton("Actualizar"  ,"Actualizar.png"  ,"Azul" );	
		JCButton btnImprimir     = new JCButton("Imprimir"    ,"imprimir-16.png" ,"Azul" );
		
		public Cat_Validacion_De_Ordenes_De_Gasto()	{
			int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
			int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
			setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
			this.setTitle("Validación De Ordenes De Gasto");
			this.campo.setBorder(BorderFactory.createTitledBorder("Seleccione Las Solicitudes Que Desea Aceptar o Negar"));
            int x=15 ,y=20 ,width=115 ,height=20;

			campo.add(txtFiltro).setBounds    (x      ,y     ,400      ,height  );
			campo.add(cmb_status).setBounds   (x+=420 ,y     ,width    ,height  );
			campo.add(btnActualizar).setBounds(x+=130 ,y     ,width    ,height  );
			campo.add(btnImprimir).setBounds  (x+=130 ,y     ,width    ,height  );			
			campo.add(btnNegar).setBounds     (x+=150 ,y     ,width    ,height  );
			campo.add(btnAceptar).setBounds   (x+=130 ,y     ,width    ,height  );
			campo.add(scroll_tabla).setBounds (x=15   ,y+=25 , ancho-25,alto-125);
			cmb_status.setSelectedItem("EN VALIDACION");
			cmb_status.setEnabled(false);
			
			init_tabla_principal();			
			cont.add(campo);
			btnAceptar.addActionListener(opaceptar);
			btnNegar.addActionListener(opnegar);
			btnActualizar.addActionListener(OpActualizar);
			btnImprimir.addActionListener(opImprimir_Reporte);
		}
		
	    ActionListener OpActualizar = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				init_tabla_principal();
			 }
	    };
	    
	    ActionListener opImprimir_Reporte = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int fila;
				if(tabla.getSelectedRowCount()==0) {
				 JOptionPane.showMessageDialog(null, "Es Requerido Seleccione Un Registro De La Tabla \nPara Poder Generar La Solicitud De Gasto","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				}else {
					fila = tabla.getSelectedRow();
					String basedatos="2.26";
					String vista_previa_reporte="no";
					int vista_previa_de_ventana=0;
					String comando="orden_de_gasto_reporte '"+tabla.getValueAt(fila, 1)+"'";
					String reporte = "Obj_Reporte_De_Orden_De_Gasto.jrxml";
			  	    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
				}
			}
	  	};
		  	
		ActionListener opaceptar = new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
				aceptar_negar="A";
				verificar();
			}
		};
		
		ActionListener opnegar = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				aceptar_negar="N";
				verificar();
			}
		};
		
		public void verificar(){
			if(tabla.isEditing()){			tabla.getCellEditor().stopCellEditing();	}
			for (int i=0;i<tabla.getRowCount();i++){
			  if((tabla.getValueAt(i,checkboxindex-1).toString().trim()).equals("true")){
						actualizar();
				    	return;
			  };
			}
			JOptionPane.showMessageDialog(null, "Necesita Por Lo Menos Seleccionar Un Registro", "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			return;
		};
		
		public void actualizar(){
		Object[][] arreglo_guardado= new Object[tabla.getRowCount()][9];
		for (int i=0;i<tabla.getRowCount();i++){
			if(tabla.getValueAt(i,0).toString().trim().equals("true")){
	       	 arreglo_guardado[i][0]=tabla.getValueAt(i,1).toString().trim();//folio_solicitud
	         arreglo_guardado[i][1]=usuario.getFolio();//usuario_valida
	         arreglo_guardado[i][2]=aceptar_negar;//usuario_valida
			}
	    }
		
		if(new ActualizarSQL().Aceptar_Negar_Sueldo_o_Bono(arreglo_guardado)){
        	init_tabla_principal();;
			JOptionPane.showMessageDialog(null, "Se Guardaron Los Registros Correctamente", "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
		}else{
			JOptionPane.showMessageDialog(null, "Error Al Actualizar", "Avise al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		}
		
		
		}
		
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Validacion_De_Ordenes_De_Gasto().setVisible(true);
			}catch(Exception e){	}
		}
	}

