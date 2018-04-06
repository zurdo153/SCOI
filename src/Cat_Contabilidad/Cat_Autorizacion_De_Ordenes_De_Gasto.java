package Cat_Contabilidad;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import Cat_Principal.EmailSenderService;
import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Contabilidad.Obj_Orden_De_Gasto;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Servicios.Obj_Servicios;

@SuppressWarnings("serial")
public class Cat_Autorizacion_De_Ordenes_De_Gasto extends JFrame {
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Connexion con = new Connexion();
		Obj_tabla ObjTab= new Obj_tabla();
		Obj_Servicios servicios_solicitud = new Obj_Servicios();
		Obj_Usuario usuario = new Obj_Usuario().LeerSession();
		
		JCButton btnAceptar  = new JCButton("Autorizar"   ,"Aplicar.png"                ,"Azul"); 
		JCButton btnNegar    = new JCButton("Negar"       ,"Delete.png"                 ,"Azul"); 
		JCButton btnCancelar = new JCButton("Cancelar"    ,"cancelar-icono-4961-16.png" ,"Azul"); 
		JCButton btnImprimir = new JCButton("Imprimir"    ,"imprimir-16.png"            ,"Azul");
		
		JLabel detalle     = new JLabel("Detalle De La Orden De Gasto");
		JTextField txtFolio= new Componentes().text(new JCTextField()  ,"Folio"   ,30   ,"String");
		JTextField txtPedientes     = new Componentes().text(new JCTextField(), "Cant. Pendientes", 150, "String");
		
	    JTextField txtFiltro = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla <<<", 300, "String");
		JTextField txtTotal  = new Componentes().text(new JCTextField()  ,"Total"                     ,30   ,"String");
		
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
		
		int Cantidad_Real_De_Columnas=13,checkboxindex=1;
		public void init_tabla(){
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
			String comando = "orden_de_gasto_autorizacion_filtro '"+cmb_status.getSelectedItem().toString().trim()+"'";
			if(cmb_status.getSelectedItem().equals("PENDIENTE")||cmb_status.getSelectedItem().equals("AUTORIZADO")){
				  btnAceptar.setEnabled(true);btnCancelar.setEnabled(true);btnNegar.setEnabled(true);	
			}else{
				  btnAceptar.setEnabled(false);btnCancelar.setEnabled(false);btnNegar.setEnabled(false);	
			}
			
			String basedatos="26",pintar="si";
			ObjTab.Obj_Refrescar(tabla,modelo, Cantidad_Real_De_Columnas, comando, basedatos,pintar,checkboxindex);
			
//			if(tabla.getRowCount()>0){txtPedientes.setText(tabla.getValueAt(0, 22).toString());}else {txtPedientes.setText("0");}
	    }
		
	 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"S","Folio","Proveedor","Concepto","Descripcion Gasto","Establecimiento","Importe Total","Usuario Solicita", "Fecha","Estatus","Fecha Autorizacion","Usuario Autorizo","Tipo Provedor"}){
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
		
		int columnasd = 4,checkboxd=-1;
		public void init_tabla2(){
	    	this.tabla_detalle.getColumnModel().getColumn(0).setMinWidth(500);	
	    	this.tabla_detalle.getColumnModel().getColumn(1).setMinWidth(90);
	    	this.tabla_detalle.getColumnModel().getColumn(2).setMinWidth(90);
	    	this.tabla_detalle.getColumnModel().getColumn(3).setMinWidth(98);
	    	
			String comando="Select '' as Descripcion, 0 P_Unitario,0 as Cantidad,0 as Importe" ;
			String basedatos="26",pintar="si";
			ObjTab.Obj_Refrescar(tabla_detalle,modelo_detalle, columnasd, comando, basedatos,pintar,checkboxd);
			modelo_detalle.setRowCount(0);
	    }
		
		@SuppressWarnings("rawtypes")
		public Class[] base_detalle (){
			Class[] types2 = new Class[columnasd];
			for(int i = 0; i<columnasd; i++){types2[i]= java.lang.Object.class;}
			 return types2;
		}
		public DefaultTableModel modelo_detalle = new DefaultTableModel(null, new String[]{"Descripcion","Cantidad","P.Unitario","Importe"}){
			 @SuppressWarnings("rawtypes")
				Class[] types2 = base_detalle();
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public Class getColumnClass(int columnIndex) {return types2[columnIndex]; }
				public boolean isCellEditable(int fila, int columnad){return false;}
		};
		
		JTable tabla_detalle = new JTable(modelo_detalle);
		public JScrollPane scroll_tabla_detalle = new JScrollPane(tabla_detalle);
		

		
		String status[] = {"PENDIENTE","AUTORIZADO","CANCELADO","FINALIZADO","NEGADO","TODOS"};
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox cmb_status = new JComboBox(status);
		
		Obj_Orden_De_Gasto gasto = new Obj_Orden_De_Gasto();
	    String aceptar_negar="";
		JToolBar menu_toolbar       = new JToolBar();
		
		public Cat_Autorizacion_De_Ordenes_De_Gasto()	{
//			this.setSize(1024,768);
			this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
//			this.setResizable(false);
			int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
			this.setTitle("Autorizacion De Ordenes de Gasto");
			campo.setBorder(BorderFactory.createTitledBorder("Seleccione Las Ordenes De Gasto Que Desea Autorizar o Negar"));

			this.menu_toolbar.add(btnAceptar  );
		 	this.menu_toolbar.addSeparator(   );
		    this.menu_toolbar.addSeparator(   );
			this.menu_toolbar.add(btnCancelar );
			this.menu_toolbar.addSeparator(   );
			this.menu_toolbar.addSeparator(   );
			this.menu_toolbar.add(btnNegar    );
			this.menu_toolbar.addSeparator(   );
			this.menu_toolbar.addSeparator(   );
			this.menu_toolbar.add(btnImprimir );
			this.menu_toolbar.setFloatable(false);
			
			int x=15,y=15,height=20;	
			this.campo.add(menu_toolbar).setBounds           (x      ,y      ,350    ,height   );
			this.campo.add(new JLabel("Cantidad De Registros:")).setBounds(x+400,y,150,height  );
			this.campo.add(txtPedientes).setBounds           (x+510  ,y      ,100    ,height   );
			
			this.campo.add(new JLabel("Busqueda Por Estatus:")).setBounds(760,y,110  ,height   );
			this.campo.add(cmb_status).setBounds             (x+860  ,y      ,120    ,height   );
			this.campo.add(txtFiltro).setBounds              (x      ,y+=25  ,ancho-40,height  );
			this.campo.add(scroll_tabla).setBounds           (x      ,y+=20  ,ancho-40,430     );
			this.campo.add(detalle).setBounds                (x      ,y+=430 ,400    ,height   );
			this.campo.add(scroll_tabla_detalle).setBounds   (x      ,y+=18  ,780    ,150      );
			this.campo.add(new JLabel("Total De La Orden")).setBounds(800,y+=110,110 ,height   );
			this.campo.add(txtTotal).setBounds               (795    ,y+=20  ,110    ,height   );
			
			agregar(tabla);
			init_tabla();
			init_tabla2();
			cantidad();
			
			txtTotal.setEditable(false);
			txtPedientes.setEditable(false);
			
			cont.add(campo);
			txtFiltro.addKeyListener(op_filtro);
			btnAceptar.addActionListener(opaceptar);
			btnNegar.addActionListener(opnegar);
			btnCancelar.addActionListener(opacancelar);
			btnImprimir.addActionListener(opImprimir_Reporte);
			cmb_status.addActionListener(buscar_con_combo);
		}
		
		public void cantidad() {
			txtPedientes.setText(tabla.getRowCount()+"");
		}
		 private KeyListener op_filtro = new KeyListener(){
				public void keyReleased(KeyEvent arg0) {
					ObjTab.Obj_Filtro(tabla, txtFiltro.getText().toUpperCase(), Cantidad_Real_De_Columnas);
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyPressed(KeyEvent arg0) {}		
			};
		
	   ActionListener opImprimir_Reporte = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String basedatos="2.26";
				String vista_previa_reporte="no";
				int vista_previa_de_ventana=0;
				String comando="orden_de_gasto_reporte '"+txtFolio.getText().toString()+"'";
				String reporte = "Obj_Reporte_De_Orden_De_Gasto.jrxml";
		  	    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			}
	  	};
			  	
		ActionListener opacancelar = new ActionListener() {
		   	public void actionPerformed(ActionEvent e) {
				aceptar_negar="CANCELADA";
				verificar();
			}
		};
			
		ActionListener opaceptar = new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
				aceptar_negar="AUTORIZADA";
				verificar();
			}
		};
		
		ActionListener opnegar = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				aceptar_negar="NEGADA";
				verificar();
			}
		};
		
		ActionListener buscar_con_combo = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				init_tabla();
				cantidad();
			}
		};
		
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        	
	    		public void mouseReleased(MouseEvent e) {
	    			if(e.getClickCount()==1){
		        		modelo_detalle.setRowCount(0);
		        		int fila = tabla.getSelectedRow();
		        		detalle.setText("Detalle De La Orden De Gasto Folio:"+tabla.getValueAt(fila,1));
		        		txtFolio.setText(tabla.getValueAt(fila,1).toString());		        		
		        		String[][] tablacompleta=gasto.consulta_orden_de_gasto(Integer.valueOf(tabla.getValueAt(fila,1)+""));
		        	    Object[]   vectortabla = new Object[4];
		        	    for(int i=0;i<tabla.getRowCount();i++) {
		        	     modelo.setValueAt(false,i,0);
		        	    }
		        	    modelo.setValueAt(true,fila,0);
		        	    
		        	    
		        	    
		        		for(int i=0;i<tablacompleta.length;i++){
		        	  	  for(int j=0;j<3;j++){
		        		   vectortabla[j] = tablacompleta[i][j].toString();
		        		}
		        	  	 vectortabla[3] = 0;
		        			modelo_detalle.addRow(vectortabla);
		        		}
						calculo();
		        	}
		        }
	          });
			}
	        	
		
		public void calculo() {
			float importe=0;
			for(int i=0;i<tabla_detalle.getRowCount();i++) {
				tabla_detalle.setValueAt(Float.valueOf(tabla_detalle.getValueAt(i, 1)+"") * Float.valueOf(tabla_detalle.getValueAt(i, 2)+"")   , i, 3);
				importe=importe+Float.valueOf(tabla_detalle.getValueAt(i, 3)+"");
			}
			txtTotal.setText(importe+"");
		};
		
		public void verificar(){
		      if(tabla.isEditing()){tabla.getCellEditor().stopCellEditing();}
		  for (int i=0;i<tabla.getRowCount();i++){
				if((tabla.getValueAt(i,checkboxindex-1).toString().trim()).equals("true") ){
					 String[][] tabla_guardado = ObjTab.tabla_guardar(tabla);

					  if(new ActualizarSQL().Guardar_Autorizacion_De_Orden_De_Gasto(tabla_guardado,aceptar_negar.substring(0, 1))){
						  
//						  ""++++AQUI EN EL BUSCADO FALTA ENVIAR EL FOLIO DE LA SOLICITUD Y BUSCAR QUIEN LO SOLICITA Y SU CORREO
							   try {servicios_solicitud = new BuscarSQL().correo_informa_estatus_solicitud(Integer.valueOf(txtFolio.getText().toString().trim()),84);
								} catch (SQLException e1) {e1.printStackTrace();}
							   
							   System.out.println(servicios_solicitud.getCorreos());
							   System.out.println(txtFolio.getText().toString().trim());
							   
							   
							 
							   
							   if(!servicios_solicitud.getCorreos().toString().trim().equals("NO TIENE")){
							      String Mensaje= "Hola "+tabla_guardado[i][7].toString()+" Tu Solicitud De Orden De Gasto Folio:"+tabla_guardado[i][1].toString()+" a Nombre De "+tabla_guardado[i][2].toString()+" Fue "+aceptar_negar+" Por "+usuario.getNombre_completo()   ;
								  new EmailSenderService().enviarcorreo(servicios_solicitud.getCorreos(),servicios_solicitud.getCantidad_de_correos(),Mensaje, "INFORME DE SOLICITUD "+aceptar_negar+" DE LA ORDEN DE GASTO FOLIO:"+tabla_guardado[i][1].toString(),"Gastos");
							   }
						   
				        	init_tabla();
				        	txtTotal.setText("");
				        	modelo_detalle.setRowCount(0);
							JOptionPane.showMessageDialog(null, "Se Guardo Correctamente", "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
							detalle.setText("Detalle De La Orden De Gasto");
							return;
						}else{
							JOptionPane.showMessageDialog(null, "Error Al Actualizar", "Avise al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
							return;
						}
				}
		   }
		  JOptionPane.showMessageDialog(null, "Necesita Por Lo Menos Seleccionar Un Registro", "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			return;
		};
		
		
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Autorizacion_De_Ordenes_De_Gasto().setVisible(true);
			}catch(Exception e){	}
		}
	}

