package Cat_Compras;

import java.awt.AWTException;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import Cat_Filtros_IZAGAR.Cat_Filtro_De_Busqueda_De_Productos_SCOI;
import Conexiones_SQL.Connexion;
import Obj_Compras.Obj_Alimentacion_De_Codigos_Adicionales;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Alimentacion_De_Codigos_Adicionales_De_Un_Producto extends JFrame{
				Container cont = getContentPane();
				JLayeredPane panel = new JLayeredPane();
				
				JTextField txtcod_prod = new Componentes().text(new JCTextField(), "Codigo del Producto", 15, "String");
				JButton btnBuscar_Producto = new JButton("",new ImageIcon("imagen/Filter-List-icon16.png"));
//				JButton btnBuscar_Proveedor= new JButton("",new ImageIcon("imagen/Filter-List-icon16.png"));
				JButton btnNuevo = new JButton("Nuevo",new ImageIcon("imagen/Nuevo.png"));
				JButton btnEditar = new JButton("Editar",new ImageIcon("imagen/editara.png"));
				JButton btnGuardar = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
				JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
				
				JLabel Marcoproveedor1= new JLabel();
				JLabel JLBdescripcion= new JLabel();
				JLabel JLBcosto= new JLabel();
				JLabel JLBprecio_Venta= new JLabel();
				JLabel JLBUDM= new JLabel();
				JLabel JLBuso= new JLabel();
				JLabel JLBEstatus= new JLabel();
				
				JTextField txtcodigo_adicional = new Componentes().text(new JCTextField(), "Codigo De Barras Adicional",20, "String");
				Connexion con = new Connexion();
				
				DefaultTableModel modelo_prv = new DefaultTableModel(null,
			            new String[]{"Folio Producto","Codigo Adicional", "Fecha Alta","Usuario Dio Alta Codigo Adicional"}
						){
				     @SuppressWarnings("rawtypes")
					Class[] types = new Class[]{
				    	java.lang.String.class,
				    	java.lang.String.class,
				    	java.lang.String.class,
				    	java.lang.String.class
			                                    };
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
			        	 } 				
			 			return false;
			 		}
				};
			    JTable tabla = new JTable(modelo_prv);
			    JScrollPane scrollAsignado = new JScrollPane(tabla);
				Border blackline, etched, raisedbevel, loweredbevel, empty;
			  String codigo_producto = "";
	          String Nombre_Catalogo_Para_Filtro="";
	  		  String descripcion="";
			  double costo=0;
			  double Precio_Venta=0;
			  String Unidad_Medida="";
			  String Uso="";
			  String Estatus="";
			  
			  String codigo_adicional_selecionado ="";
			  
	public Cat_Alimentacion_De_Codigos_Adicionales_De_Un_Producto(){	
		constructor("");
	}
	public Cat_Alimentacion_De_Codigos_Adicionales_De_Un_Producto(String cod_prod){	
		constructor(cod_prod);
	}
			  
			  
	public void constructor(String cod_prod){
		
		codigo_producto=cod_prod+"";
		txtcod_prod.setText(codigo_producto+"");

		setSize(620,600);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Alimentacion De Codigos Adicionales De Un Producto");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48 -mas.png"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Teclee el Codigo Del Producto"));

		int x=10,y=20,l=100,a=20;
		
		panel.add(txtcod_prod).setBounds(x,y,l+30,a);
		panel.add(btnBuscar_Producto).setBounds(x+=130,y,a,a);
		panel.add(JLBdescripcion).setBounds(x+=30,y,l+300,a);
		
		x=10;
		panel.add(new JLabel("Costo:")).setBounds           (x    ,y+=25,l  ,a);
		panel.add(JLBcosto).setBounds                       (x+=45,y    ,l  ,a);
		panel.add(new JLabel("Precio Venta:")).setBounds    (x+=75,y    ,l  ,a);
		panel.add(JLBprecio_Venta).setBounds                (x+=75,y    ,l  ,a);
		panel.add(new JLabel("Unidad De Medida:")).setBounds(x+=65,y    ,l  ,a);
		panel.add(JLBUDM).setBounds                         (x+=95,y    ,l  ,a);

		x=10;
		panel.add(new JLabel("Estatus:")).setBounds         (x    ,y+=25    ,l  ,a);
		panel.add(JLBEstatus).setBounds                     (x+=45,y        ,l  ,a);
		panel.add(new JLabel("Uso Producto:")).setBounds    (x+=75,y        ,l  ,a);
		panel.add(JLBuso).setBounds                         (x+=75,y        ,l*3,a);
		panel.add(btnDeshacer).setBounds                    (x+=300   ,y        ,l,a);

		x=10;
		panel.add(new JLabel("Codigo Adicional:")).setBounds(x     ,y+=25    ,l  ,a);
		panel.add(txtcodigo_adicional).setBounds            (x+=85 ,y        ,l*2-20,a);
		panel.add(btnNuevo).setBounds                       (x+=190,y        ,l,a);
		panel.add(btnEditar).setBounds                      (x+=110,y        ,l,a);
		panel.add(btnGuardar).setBounds                     (x+=110,y        ,l,a);
		
		x=10;
		panel.add(Tabla()).setBounds                        (x,y+=30,l*6-6,435);
		
		btnNuevo.setEnabled(false);
		btnDeshacer.setEnabled(true);
		btnEditar.setEnabled(false);
		btnGuardar.setEnabled(false);
		txtcodigo_adicional.setEnabled(false);
		
		cont.add(panel);
		
		Nombre_Catalogo_Para_Filtro=this.getClass().getSimpleName();
		btnBuscar_Producto.addActionListener(opBuscar_Producto);
		btnNuevo.addActionListener(nuevo);
		btnDeshacer.addActionListener(deshacer);
		btnGuardar.addActionListener(Guardar);
		btnEditar.addActionListener(editar);
		txtcod_prod.addKeyListener(Buscar_Datos_Producto);
		agregar(tabla);
		
        ///deshacer con escape
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        getRootPane().getActionMap().put("escape", new AbstractAction(){
            public void actionPerformed(ActionEvent e)
            {        btnDeshacer.doClick();   	    }
        });
        
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "buscarLR");
          getRootPane().getActionMap().put("buscarLR", new AbstractAction(){
         public void actionPerformed(ActionEvent e)
         {         
        	 btnBuscar_Producto.doClick();
       	    }
         });
          
			///FILTRO con F2
          getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "filtro");
              getRootPane().getActionMap().put("filtro", new AbstractAction(){
                  public void actionPerformed(ActionEvent e)
                  {                 	    btnBuscar_Producto.doClick();
                    	    }
             });
              
              
          
          if(codigo_producto.equals("")){
			          this.addWindowListener(new WindowAdapter() {
			                  public void windowOpened( WindowEvent e ){
			                  	txtcod_prod.requestFocus();
			                  	 }
			          });
          }else{
			          this.addWindowListener(new WindowAdapter() {
		                  public void windowOpened( WindowEvent e ){
		                  	txtcod_prod.requestFocus();
		                  	enterauto();
		                  	 }
		              });
          }
          
	}
	
	public void enterauto(){
		Robot robot;
		try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            e.printStackTrace();
        }
     };
	
	ActionListener opBuscar_Producto = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			dispose();
			new Cat_Filtro_De_Busqueda_De_Productos_SCOI(Nombre_Catalogo_Para_Filtro).setVisible(true);
		}
	};
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(!txtcod_prod.getText().toString().equals("")){
		    btnNuevo.setEnabled(false);
		    btnGuardar.setEnabled(true);
			txtcod_prod.setEnabled(false);
			codigo_adicional_selecionado="";
			txtcodigo_adicional.setText("");
		    txtcodigo_adicional.setEnabled(true);
			txtcodigo_adicional.requestFocus();
			btnEditar.setEnabled(false);
			}else{
				JOptionPane.showMessageDialog(null, "Es Necesario Buscar Un Producto Para Agregar Un Codigo Adicional" , "Aviso", JOptionPane.WARNING_MESSAGE, new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
			
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txtcod_prod.setText("");
			txtcod_prod.setEnabled(true);
			txtcodigo_adicional.setText("");
			txtcodigo_adicional.setEnabled(false);
			btnEditar.setEnabled(false);
			btnNuevo.setEnabled(false);
			btnGuardar.setEnabled(false);
			codigo_producto = "";
	  		descripcion="";
			costo=0;
			Precio_Venta=0;
			Unidad_Medida="";
			Uso="";
			Estatus="";
			carga_etiquetas();
			modelo_prv.setRowCount(0);
			txtcod_prod.requestFocus();
		}
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    btnNuevo.setEnabled(false);
		    btnGuardar.setEnabled(true);
			txtcod_prod.setEnabled(false);
		    txtcodigo_adicional.setEnabled(true);
			txtcodigo_adicional.requestFocus();
		}
	};
	
	ActionListener Guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(validaCampos()!="") {
				JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n"+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				Obj_Alimentacion_De_Codigos_Adicionales  Datos_Producto= new Obj_Alimentacion_De_Codigos_Adicionales();					
				Datos_Producto.setFolio_Producto(txtcod_prod.getText().toString().trim());
                Datos_Producto.setCodigo_Tecleado(txtcodigo_adicional.getText().toString().trim());
				Datos_Producto.setCodigo_Barras(codigo_adicional_selecionado);
				
				try {
					if(new Obj_Alimentacion_De_Codigos_Adicionales().Existe_Producto(txtcodigo_adicional.getText().trim().toUpperCase()+"")){	
						
						JOptionPane.showMessageDialog(null, "El Codigo Ya Existe y No Debe De Haber Codigos Duplicados" , "Aviso", JOptionPane.WARNING_MESSAGE, new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					}
					else{
					
					   if(Datos_Producto.Guardar_Codigo_Adicional()){
						   txtcodigo_adicional.setEnabled(false);
						   codigo_adicional_selecionado="";
						   txtcodigo_adicional.setText("");
						   btnGuardar.setEnabled(false);
						   btnNuevo.setEnabled(true);
						   btnEditar.setEnabled(false);
						   Llenar_tablaes ();
						   JOptionPane.showMessageDialog(null, "Se Guardo Correctamente:","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
						return;
					   }else{
					       JOptionPane.showMessageDialog(null, "Error  En La Funcion [Guardar]", "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
						   return;
						}
					}   
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
		   			   JOptionPane.showMessageDialog(null, "Error  En La Funcion [Guardar]", "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
						return;
				}
		}
		}
	};
	private String validaCampos(){
		String error="";
		if(codigo_producto.equals("")) error +="Codigo de Producto \n";
		if(txtcodigo_adicional.getText().equals("")) 		error+= "Codigo Adicional\n";
		return error;
	}
	
	
	KeyListener Buscar_Datos_Producto = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				try {
					
					if(new Obj_Alimentacion_De_Codigos_Adicionales().Existe_Producto(txtcod_prod.getText().trim().toUpperCase()+"")){
						Obj_Alimentacion_De_Codigos_Adicionales  Datos_Producto= new Obj_Alimentacion_De_Codigos_Adicionales().buscardatos_producto(txtcod_prod.getText().trim()+"");
						descripcion  =Datos_Producto.getDescripcion();
						costo        =Datos_Producto.getCosto();
						Precio_Venta =Datos_Producto.getPrecio_Venta();
						Unidad_Medida=Datos_Producto.getUDM();
						Uso          =Datos_Producto.getUso();
						Estatus      =Datos_Producto.getEstatus();
						txtcod_prod.setText(Datos_Producto.getFolio_Producto());
						btnNuevo.setEnabled(true);
						carga_etiquetas();
 					    Llenar_tablaes ();
						codigo_producto=txtcod_prod.getText().trim().toUpperCase()+"";
						txtcod_prod.setEnabled(false);
						
					}else{
						JOptionPane.showMessageDialog(null, "El Codigo Esta Mal Escrito o El Producto No Existe" , "Aviso", JOptionPane.WARNING_MESSAGE, new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
                    }
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Error en Cat_Alimentacion_De_codigos_Adicionales  en la funcion existe_Producto \n SQLException: "+e1.getMessage(), "Avisa al Administrador del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
					e1.printStackTrace();
				}
			}
		}
	};
	
	public void carga_etiquetas(){
		JLBdescripcion.setText("<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLUE><CENTER><b><p>"+descripcion+"</p></b></CENTER></FONT></html>");
		JLBcosto.setText("<html> <FONT FACE="+"arial"+" SIZE=4 COLOR=BLACk><p>"+costo+"</p></FONT></html>");
		JLBprecio_Venta.setText("<html> <FONT FACE="+"arial"+" SIZE=4 COLOR=BLACk><p>"+Precio_Venta+"</p></FONT></html>");
		JLBUDM.setText("<html> <FONT FACE="+"arial"+" SIZE=4 COLOR=BLACk><p>"+Unidad_Medida+"</p></FONT></html>");
		JLBuso.setText("<html> <FONT FACE="+"arial"+" SIZE=4 COLOR=BLACk><p>"+Uso+"</p></FONT></html>");
		JLBEstatus.setText("<html> <FONT FACE="+"arial"+" SIZE=4 COLOR=BLACk><p>"+Estatus+"</p></FONT></html>");
		
	}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==1){
	        		int fila = tabla.getSelectedRow();
	        		    txtcod_prod.setEnabled(false);
	        		    txtcodigo_adicional.setText(tabla.getValueAt(fila,1).toString());
	        		    codigo_adicional_selecionado=tabla.getValueAt(fila,1).toString();
						btnEditar.setEnabled(true);
						txtcodigo_adicional.setEnabled(false);
	        	}
	        }
        });
    }
				
		private JScrollPane Tabla()	{		
			    this.tabla.getTableHeader().setReorderingAllowed(false) ;
				this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				tabla.getColumnModel().getColumn(0).setMaxWidth(80);
				tabla.getColumnModel().getColumn(0).setMinWidth(80);
				tabla.getColumnModel().getColumn(1).setMaxWidth(150);
				tabla.getColumnModel().getColumn(1).setMinWidth(150);
				tabla.getColumnModel().getColumn(2).setMaxWidth(130);
				tabla.getColumnModel().getColumn(2).setMinWidth(130);
				tabla.getColumnModel().getColumn(3).setMinWidth(232);
				tabla.getColumnModel().getColumn(3).setMaxWidth(480);
				
				tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",10)); 
				tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
				tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("fecha","izquierda","Arial","normal",12));
				tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			   
			 JScrollPane scrol = new JScrollPane(tabla);
		    return scrol; 

		}
				
	public void Llenar_tablaes (){
		      modelo_prv.setRowCount(0);
	  		  Statement s;
			  ResultSet rs;
			try {
   					    s = con.conexion().createStatement();
						String cod_producto=txtcod_prod.getText().trim().toUpperCase()+"";
						rs = s.executeQuery("select folio_producto"
								+ "        	       ,codigo_barras_adicional"
								+ "    	           ,convert(varchar(20),fecha,103)+' '+ convert(varchar(20),fecha,108) as fecha"
								+ "          	   ,(select nombre+' '+ap_paterno+' '+ap_materno from tb_empleado where folio=tb_codigos_de_barras_adicionales.usuario) as usuario"
								+ "      	   from tb_codigos_de_barras_adicionales"
								+ "     where folio_producto ='"+cod_producto+"'");
						while (rs.next())
						{ 
						   Object [] fila = new Object[4];
						   fila[0] = rs.getString(1).trim();
						   fila[1] = rs.getString(2).trim();
						   fila[2] = rs.getString(3).trim(); 
						   fila[3] = rs.getString(4).trim(); 
						   modelo_prv.addRow(fila); 
						}	
					} catch (SQLException e1) {
					 e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error en Cat_Alimentacion_De_Codigos_Adicionales en la funcion Llenar_tabla  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					}
     }
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Alimentacion_De_Codigos_Adicionales_De_Un_Producto("").setVisible(true);
		}catch(Exception e){	}
	}
}
