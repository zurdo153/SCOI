package Cat_Reportes;

import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Cat_Filtros_IZAGAR.Cat_Filtro_De_Busqueda_De_Productos;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Compras.Obj_Cotizaciones_De_Un_Producto;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Inventario_Parcial extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento201();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	String operadorProducto[] = {"Todos","Igual","Esta en lista","Menor que","Mayor que","Diferente"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Productos = new JComboBox(operadorProducto);
	
	String operadorClase[] = {"Todos","Igual","Esta en lista","Menor que","Mayor que","Diferente"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Clase = new JComboBox(operadorClase);

	String operadorCategoria[] = {"Todos","Igual","Esta en lista","Menor que","Mayor que","Diferente"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Categoria= new JComboBox(operadorCategoria);
	
	String operadorFamilia[] = {"Todos","Igual","Esta en lista","Menor que","Mayor que","Diferente"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Familia = new JComboBox(operadorFamilia);
	
	String operadorLinea[] = {"Todos","Igual","Esta en lista","Menor que","Mayor que","Diferente"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Linea = new JComboBox(operadorLinea);
	
	String operadorTalla[] = {"Todos","Igual","Esta en lista","Menor que","Mayor que","Diferente"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Talla = new JComboBox(operadorTalla);
	
	JButton btnFiltroProducto = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroProducto = new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
	JButton btnFiltroClase = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroClase= new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
	JButton btnFiltroCategoria = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroCategoria = new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
	JButton btnFiltroFamilia = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroFamilia = new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
	JButton btnFiltroLinea = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroLinea = new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
	JButton btnFiltroTalla = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroTalla = new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
	JButton btn_codcorto = new JButton  ("Reporte Codigo Corto",new ImageIcon("imagen/checklist-icon16.png"));
	JButton btn_codbarras = new JButton  ("Reporte Codigo Barras",new ImageIcon("imagen/checklist-icon16.png"));
	
	JLabel JLBestablecimiento= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	
	JTextField txtFiltroProducto = new JTextField("");
	JTextField txtcod_prod = new Componentes().text(new JTextField(), "Codigo del Producto", 15, "String");
	JTextField txtFiltroClase = new JTextField("");
	JTextField txtFiltroCategoria = new JTextField("");
	JTextField txtFiltroFamilia = new JTextField("");
	JTextField txtFiltroLinea = new JTextField("");
	JTextField txtFiltroTalla = new JTextField("");
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	
	String parametroGeneral = "";
	String Lista="";
	JLabel JLBdescripcion= new JLabel();
	
public void constructor(){
	this.setSize(760, 290);
	cont.add(panel);
	setResizable(false);
	setLocationRelativeTo(null);
	setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Sales-by-payment-method-icon-64.png"));
	setTitle("Reportes De Inventario Parcial");
	panel.setBorder(BorderFactory.createTitledBorder("Reportes De Inventario Parcial"));
	
	txtcod_prod.setBackground(Color.lightGray);
	txtcod_prod.setBorder(BorderFactory.createTitledBorder(""));
	txtcod_prod.setHorizontalAlignment(4);
	//      asigna el foco al JTextField deseado al arrancar la ventana
    this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
            	txtcod_prod.requestFocus();
         }
    });
    
	int x=15 ;
	int y=20 ;
	int l=100;
	int a=20;
	
	panel.add(new JLabel("Establecimiento:")).setBounds(x,y,l+50,a);
    panel.add(JLBestablecimiento).setBounds(x+80,y,a,a);
	panel.add(cmbEstablecimiento).setBounds(x+100,y,l+70,a);

	panel.add(new JLabel("Buscar un producto: ")).setBounds(x+300,y,125,a);
    panel.add(txtcod_prod).setBounds(x+400,y,75,a);
    
    panel.add(JLBdescripcion).setBounds(x+480,y-10,l+150,a*2);
	
	x=100;
	panel.add(new JLabel("Filtro De Productos:")).setBounds(x-85,y+=30,l+50,a);
	panel.add(cmbOperador_Productos				).setBounds(x+80,y,l-12,a);
    panel.add(txtFiltroProducto					).setBounds(x+170,y,l*4+20,a);

    panel.add(btnFiltroProducto					).setBounds(x+590,y,a,a);
    panel.add(btnLimpiarFiltroProducto			).setBounds(x+613,y,a,a);
    
	panel.add(new JLabel("Filtro De Clase De Productos:")).setBounds(x-85,y+=30,l+50,a); 
	panel.add(cmbOperador_Clase							 ).setBounds(x+80,y,l-12,a);  
    panel.add(txtFiltroClase							 ).setBounds(x+170,y,l*4+20,a);  
    panel.add(btnFiltroClase							 ).setBounds(x+590,y,a,a);    
    panel.add(btnLimpiarFiltroClase						 ).setBounds(x+613,y,a,a);
    
	panel.add(new JLabel("Filtro De Categoria De Productos:")).setBounds(x-85,y+=30,l+70,a); 
	panel.add(cmbOperador_Categoria							 ).setBounds(x+80,y,l-12,a);  
    panel.add(txtFiltroCategoria							 ).setBounds(x+170,y,l*4+20,a);  
    panel.add(btnFiltroCategoria							 ).setBounds(x+590,y,a,a);    
    panel.add(btnLimpiarFiltroCategoria						 ).setBounds(x+613,y,a,a);   
    
  	panel.add(new JLabel("Filtro Familia De Productos:")).setBounds(x-85,y+=30,l+50,a); 
	panel.add(cmbOperador_Familia						).setBounds(x+80,y,l-12,a);  
	panel.add(txtFiltroFamilia							).setBounds(x+170,y,l*4+20,a);  
    panel.add(btnFiltroFamilia							).setBounds(x+590,y,a,a);    
    panel.add(btnLimpiarFiltroFamilia					).setBounds(x+613,y,a,a);
    
	panel.add(new JLabel("Filtro De Linea De Productos:")).setBounds(x-85,y+=30,l+50,a); 
	panel.add(cmbOperador_Linea					 ).setBounds(x+80,y,l-12,a);  
    panel.add(txtFiltroLinea							 ).setBounds(x+170,y,l*4+20,a);  
    panel.add(btnFiltroLinea							 ).setBounds(x+590,y,a,a);    
    panel.add(btnLimpiarFiltroLinea						 ).setBounds(x+613,y,a,a); 
    
    panel.add(new JLabel("Filtro De Talla De Productos:")).setBounds(x-85,y+=30,l+50,a); 
	panel.add(cmbOperador_Talla							 ).setBounds(x+80,y,l-12,a);  
    panel.add(txtFiltroTalla							 ).setBounds(x+170,y,l*4+20,a);  
    panel.add(btnFiltroTalla							 ).setBounds(x+590,y,a,a);    
    panel.add(btnLimpiarFiltroTalla						 ).setBounds(x+613,y,a,a); 
    
    panel.add(btn_codcorto).setBounds(x+250,y+=25,l+80,a);
    panel.add(btn_codbarras).setBounds(x+450,y,l+80,a);
    
    txtFiltroProducto.setEditable(false); 
    txtFiltroClase.setEditable(false);
    txtFiltroCategoria.setEditable(false);
    txtFiltroFamilia.setEditable(false);
    txtFiltroLinea.setEditable(false);
    txtFiltroTalla.setEditable(false);
    

	


	if(cmbEstablecimiento.getSelectedIndex()!=0){
		btn_codcorto.setEnabled(true);
		btn_codbarras.setEnabled(true);
	}else{
		btn_codcorto.setEnabled(false);
		btn_codbarras.setEnabled(false);
	}
	
	btnFiltroProducto.addActionListener(op_filtro_productos);
	btnFiltroClase.addActionListener(op_filtro_clases);
	btnFiltroCategoria.addActionListener(op_filtro_categorias);
	btnFiltroFamilia.addActionListener(op_filtro_familias);
	btnFiltroLinea.addActionListener(op_filtro_lineas);
	btnFiltroTalla.addActionListener(op_filtro_talla);
	
	
	btnLimpiarFiltroProducto.addActionListener(limpiar_filtro_productos);
	btnLimpiarFiltroClase.addActionListener(limpiar_filtro_claces);
	btnLimpiarFiltroCategoria.addActionListener(limpiar_filtro_categorias);
	btnLimpiarFiltroFamilia.addActionListener(limpiar_filtro_familias);
	btnLimpiarFiltroLinea.addActionListener(limpiar_filtro_lineas);
	btnLimpiarFiltroTalla.addActionListener(limpiar_filtro_talla);
	
	txtcod_prod.addKeyListener(Buscar_Datos_Producto);
	btn_codcorto.addActionListener(op_generar);
	btn_codbarras.addActionListener(op_generar_cod_barras);
	
	cmbEstablecimiento.addActionListener(op_cmb);
	
    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
    getRootPane().getActionMap().put("escape", new AbstractAction(){
        public void actionPerformed(ActionEvent e)
        {                 	    btnLimpiarFiltroProducto.doClick();
      	    }
    });
    
  } ;
  
  public Cat_Reporte_De_Inventario_Parcial(){
	  constructor();
  };
  
	public Cat_Reporte_De_Inventario_Parcial(String parametro, String operador, String establecimiento){
		constructor();
		String operador_simbolo = "";
        if(!parametro.equals("")){

        	switch(operador){
	    		case "Igual"		:operador_simbolo=" = "; break;
	    		case "Esta en lista":operador_simbolo=" in "; break;
	    		case "Menor que"	:operador_simbolo=" < "; break;
	    		case "Mayor que"	:operador_simbolo=" > "; break;
	    		case "Diferente"	:operador_simbolo=" <> "; break;
    		}
        	txtFiltroProducto.setText(operador_simbolo+parametro);
        	cmbOperador_Productos.setSelectedItem(operador);
        	cmbEstablecimiento.setSelectedItem(establecimiento);
            panelEnableFalse();
        	btnLimpiarFiltroProducto.setEnabled(true);
        	btnLimpiarFiltroClase.setEnabled(true);
        }
	}

	ActionListener op_cmb = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(cmbEstablecimiento.getSelectedIndex()!=0){
				btn_codcorto.setEnabled(true);
				btn_codbarras.setEnabled(true);
			}else{
				btn_codcorto.setEnabled(false);
				btn_codbarras.setEnabled(false);
			}
		}
	};
	
	ActionListener op_filtro_productos = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Productos.getSelectedItem().toString().equals("Todos")){
				dispose();
				new Cat_Filtro_De_Busqueda_De_Productos("Reporte_De_Inventarios_Parciales",cmbOperador_Productos.getSelectedItem().toString(),cmbEstablecimiento.getSelectedItem().toString()).setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	ActionListener op_filtro_clases = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Clase.getSelectedItem().toString().equals("Todos")){
				new Cat_Filtro_Dinamico(cmbOperador_Clase.getSelectedItem().toString(),"clases_productos","clase_producto").setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	ActionListener op_filtro_categorias = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Categoria.getSelectedItem().toString().equals("Todos")){
				new Cat_Filtro_Dinamico(cmbOperador_Categoria.getSelectedItem().toString(),"categorias","categoria").setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	ActionListener op_filtro_familias = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Familia.getSelectedItem().toString().equals("Todos")){
				new Cat_Filtro_Dinamico(cmbOperador_Familia.getSelectedItem().toString(),"familias","familia").setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	ActionListener op_filtro_lineas = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Linea.getSelectedItem().toString().equals("Todos")){
				new Cat_Filtro_Dinamico(cmbOperador_Linea.getSelectedItem().toString(),"lineas_productos","linea_producto").setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	ActionListener op_filtro_talla = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Talla.getSelectedItem().toString().equals("Todos")){
				new Cat_Filtro_Dinamico(cmbOperador_Talla.getSelectedItem().toString(),"tallas","talla").setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
//LIMPIAR ----------------------------------------------------------------------------------------------------------------------------	
	ActionListener limpiar_filtro_productos = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
            txtFiltroProducto.setText("");
            txtFiltroClase.setText("");
            txtFiltroCategoria.setText("");
            txtFiltroFamilia.setText("");
            txtFiltroLinea.setText("");
        	panelEnableTrue();
        	Lista="";
        	limpiar_vacios("");
		}
	};
	
	ActionListener limpiar_filtro_claces = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
            txtFiltroProducto.setText("");
            txtFiltroClase.setText("");
            txtFiltroCategoria.setText("");
            txtFiltroFamilia.setText("");
            txtFiltroLinea.setText("");
        	panelEnableTrue();
        	Lista="";
        	limpiar_vacios("");
		}
	};
	
	ActionListener limpiar_filtro_categorias = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
            
			panelEnableFalse();
			txtFiltroCategoria.setText("");
			limpiar_vacios("");
		}
	};
	
	ActionListener limpiar_filtro_familias = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
           
			panelEnableFalse();
			txtFiltroFamilia.setText("");
			limpiar_vacios("");
		}
	};
	
	ActionListener limpiar_filtro_lineas = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			panelEnableFalse();
			txtFiltroLinea.setText("");
			limpiar_vacios("");
            	
		}
	};
	
	ActionListener limpiar_filtro_talla = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			panelEnableFalse();
			txtFiltroTalla.setText("");
			limpiar_vacios("boton talla");
			
            	
		}
	};
	
	public void limpiar_vacios(String boton){
		
		if(!boton.equals("boton talla")){
			panelEnableFalse();
		}
		
		
		txtFiltroLinea.setText("");
        btnFiltroLinea.setEnabled(true);
        btnLimpiarFiltroLinea.setEnabled(true);
        btnLimpiarFiltroFamilia.setEnabled(true);
        
        btnFiltroTalla.setEnabled(true);
        btnLimpiarFiltroTalla.setEnabled(true);
        
        	if(txtFiltroFamilia.getText().equals("")){
        		btnFiltroFamilia.setEnabled(true);
        		if(txtFiltroCategoria.getText().equals("")){
            		btnFiltroCategoria.setEnabled(true);
            		btnLimpiarFiltroCategoria.setEnabled(true);
            		if(txtFiltroClase.getText().equals("")){
	            		btnFiltroClase.setEnabled(true);
	            		btnLimpiarFiltroClase.setEnabled(true);
	            		btnFiltroProducto.setEnabled(true);
	            	}
            		btnLimpiarFiltroClase.setEnabled(true);
            	}
        		btnLimpiarFiltroCategoria.setEnabled(true);
        	}
        	btnLimpiarFiltroFamilia.setEnabled(true);
        	
        	cmbOperador_Productos.setSelectedIndex(0);
        	cmbOperador_Clase.setSelectedIndex(0);
        	cmbOperador_Categoria.setSelectedIndex(0);
        	cmbOperador_Familia.setSelectedIndex(0);
        	cmbOperador_Linea.setSelectedIndex(0);
        	
        	Lista="";
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------------
	
	ActionListener op_generar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			String estabs = cmbEstablecimiento.getSelectedItem().toString();
			String productos 	= txtFiltroProducto.getText().equals("")?"0":txtFiltroProducto.getText();
			String clases 		= txtFiltroClase.getText().equals("")?"0":txtFiltroClase.getText();
			String categorias 	= txtFiltroCategoria.getText().equals("")?"0":txtFiltroCategoria.getText();
			String familias 	= txtFiltroFamilia.getText().equals("")?"0":txtFiltroFamilia.getText();
			String lineas 		= txtFiltroLinea.getText().equals("")?"0":txtFiltroLinea.getText();
			String tallas 		= txtFiltroTalla.getText().equals("")?"0":txtFiltroTalla.getText();
			
			String basedatos="2.200";
			String vista_previa_reporte="si";
			int vista_previa_de_ventana=0;
			
			String comando="exec sp_Reporte_IZAGAR_de_inventarios_parciales '"+ estabs +"','"+ productos +"','"+ clases +"','"+ categorias +"','"+ familias +"','"+ lineas +"','"+ tallas +"','"+ new Obj_Usuario().LeerSession().getNombre_completo()+"'";
			String reporte = "Obj_Reporte_IZAGAR_De_Inventarios_Parciales.jrxml";
			
			new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
	};
	
	ActionListener op_generar_cod_barras = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			String estabs = cmbEstablecimiento.getSelectedItem().toString();
			String productos 	= txtFiltroProducto.getText().equals("")?"0":txtFiltroProducto.getText();
			String clases 		= txtFiltroClase.getText().equals("")?"0":txtFiltroClase.getText();
			String categorias 	= txtFiltroCategoria.getText().equals("")?"0":txtFiltroCategoria.getText();
			String familias 	= txtFiltroFamilia.getText().equals("")?"0":txtFiltroFamilia.getText();
			String lineas 		= txtFiltroLinea.getText().equals("")?"0":txtFiltroLinea.getText();
			String tallas 		= txtFiltroTalla.getText().equals("")?"0":txtFiltroTalla.getText();
			
			String basedatos="2.200";
			String vista_previa_reporte="si";
			int vista_previa_de_ventana=0;
			
			String comando="exec sp_Reporte_IZAGAR_de_inventarios_parciales '"+ estabs +"','"+ productos +"','"+ clases +"','"+ categorias +"','"+ familias +"','"+ lineas +"','"+ tallas +"','"+ new Obj_Usuario().LeerSession().getNombre_completo()+"'";
			String reporte = "Obj_Reporte_IZAGAR_De_Inventarios_Parciales_Con_Codigo_De_Barras.jrxml";
			
			new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
	};
	
	
	public void panelEnableFalse(){
		btnFiltroProducto.setEnabled(false);
		btnFiltroClase.setEnabled(false);
    	btnFiltroCategoria.setEnabled(false);
    	btnFiltroFamilia.setEnabled(false);
    	btnFiltroLinea.setEnabled(false);
    	btnLimpiarFiltroClase.setEnabled(false);
    	btnLimpiarFiltroCategoria.setEnabled(false);
    	btnLimpiarFiltroFamilia.setEnabled(false);
    	btnLimpiarFiltroLinea.setEnabled(false);
    	btnLimpiarFiltroTalla.setEnabled(false);
	}
	
	public void panelEnableTrue(){
		btnFiltroProducto.setEnabled(true);
		btnFiltroClase.setEnabled(true);
    	btnFiltroCategoria.setEnabled(true);
    	btnFiltroFamilia.setEnabled(true);
    	btnFiltroLinea.setEnabled(true);
    	btnLimpiarFiltroClase.setEnabled(true);
    	btnLimpiarFiltroCategoria.setEnabled(true);
    	btnLimpiarFiltroFamilia.setEnabled(true);
    	btnLimpiarFiltroLinea.setEnabled(true);
    	btnLimpiarFiltroTalla.setEnabled(true);
	}
	
	KeyListener Buscar_Datos_Producto = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				try {
						if(new Obj_Cotizaciones_De_Un_Producto().Existe_Producto(txtcod_prod.getText().toUpperCase().trim())){
							
						      Obj_Cotizaciones_De_Un_Producto  Datos_Producto= new Obj_Cotizaciones_De_Un_Producto().buscardatos_producto(txtcod_prod.getText().trim().toUpperCase()+"");
					            
								 if(!Datos_Producto.getDescripcion_Prod().toString().trim().equals("") || !txtcod_prod.getText().equals("")){
										cmbOperador_Productos.setSelectedItem("Igual");
								 }else{
									 	cmbOperador_Productos.setSelectedIndex(0);
								 }
								txtFiltroProducto.setText("=(''"+Datos_Producto.getCod_Prod().toString().trim()+"'')");
								JLBdescripcion.setText("<html> <FONT FACE="+"arial"+" SIZE=2 COLOR=BLUE><CENTER><b><p>"+Datos_Producto.getDescripcion_Prod().toString().trim()+"</p></b></CENTER></FONT></html>");
							}else{
								JLBdescripcion.setText("");
								JOptionPane.showMessageDialog(null, "El Codigo Esta Mal Escrito o El Producto No Existe" , "Aviso", JOptionPane.CANCEL_OPTION);
		                    }
					
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Error en Cat_Cotizaciones_De_Un_Producto_En_Proveedores  en la funcion existe_Producto \n SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				
				txtcod_prod.setText("");
			}
		}
	};
	
// FILTRO
		 	public class Cat_Filtro_Dinamico extends JDialog {
				
				Container cont = getContentPane();
				JLayeredPane campo = new JLayeredPane();
				
				Object[][] MatrizFiltro ;
				
				String Operador = "";
				
				DefaultTableModel modeloFiltro = new DefaultTableModel(null,
			            new String[]{"Folio", "Nombre",""}
						){
				     @SuppressWarnings("rawtypes")
					Class[] types = new Class[]{
				    	java.lang.Integer.class,
				    	java.lang.String.class,
				    	java.lang.Boolean.class
			         };
				     @SuppressWarnings({ "rawtypes", "unchecked" })
					public Class getColumnClass(int columnIndex) {
			             return types[columnIndex];
			         }
			         public boolean isCellEditable(int fila, int columna){
			        	 switch(columna){
			        	 	case 0 : return false; 
			        	 	case 1 : return false; 
			        	 	case 2 : return true;
			        	 		
			        	 } 				
			 			return false;
			 		}
			         
			            @Override
			            public void setValueAt(Object value, int row, int col) {
			                super.setValueAt(value, row, col);
			                if(!Operador.equals("Esta en lista")){
			                	if (col == 2 && value.equals(Boolean.TRUE))
			                    deselectValues(row, col);
			                }
			            }

			            private void deselectValues(int selectedRow, int col) {
			                for (int row = 0; row < getRowCount(); row++) {
			                    if (getValueAt(row, col).equals(Boolean.TRUE)
			                            && row != selectedRow) {
			                        setValueAt(Boolean.FALSE, row, col);
			                        fireTableCellUpdated(row, col);
			                    }
			                }
			            }
				};
				
				JTable tablaFiltro = new JTable(modeloFiltro);
			    JScrollPane scroll = new JScrollPane(tablaFiltro);
				
				@SuppressWarnings("rawtypes")
				private TableRowSorter trsfiltro;
				
				JTextField txtFolio = new JTextField();
				JTextField txtNombre_Completo = new JTextField();
				
				JButton btnAgregar = new JButton(new ImageIcon("Iconos/agregar.png"));
				
				String folio_columna = "";
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Cat_Filtro_Dinamico(String operad, String nombre_de_tabla,String folio_colum){
					
					this.setModal(true);
					setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
					setTitle("Filtro de "+nombre_de_tabla);
					campo.setBorder(BorderFactory.createTitledBorder("Seleccion "+folio_colum));
					trsfiltro = new TableRowSorter(modeloFiltro); 
					tablaFiltro.setRowSorter(trsfiltro);  
					
					Operador = operad;
					folio_columna=folio_colum;

					btnAgregar.setToolTipText("Agregar");
					
					campo.add(scroll).setBounds(15,43,364,360);
					
					campo.add(txtFolio).setBounds(15,20,40,20);
					campo.add(txtNombre_Completo).setBounds(56,20,280,20);
					campo.add(btnAgregar).setBounds(340,20,50,20);
					
					cont.add(campo);
					
					while(tablaFiltro.getRowCount()>0){
							modeloFiltro.removeRow(0);
	                }
                
					Object[][] getTablaFiltro = getTablaFiltro(operad,nombre_de_tabla);
					String[] fila = new String[3];
                        for(int i=0; i<getTablaFiltro.length; i++){
                                fila[0] = getTablaFiltro[i][0]+"";
                                fila[1] = getTablaFiltro[i][1]+"";
                                fila[2] = "false";
                                modeloFiltro.addRow(fila);
                        }
					
					llamar_render();
					
					txtFolio.addKeyListener(opFiltroFolio);
					txtNombre_Completo.addKeyListener(opFiltroNombre);
					
					btnAgregar.addActionListener(opAgregar);
					
					setSize(405,450);
					setResizable(false);
					setLocationRelativeTo(null);
					
					 this.addWindowListener(new WindowAdapter() {
		                    public void windowOpened( WindowEvent e ){
		                    	txtNombre_Completo.requestFocus();
		                 }
		            });
				}
				
				public void llamar_render(){
					
					tablaFiltro.getColumnModel().getColumn(0).setMaxWidth(40);
					tablaFiltro.getColumnModel().getColumn(0).setMinWidth(40);
					tablaFiltro.getColumnModel().getColumn(1).setMaxWidth(280);
					tablaFiltro.getColumnModel().getColumn(1).setMinWidth(280);
					tablaFiltro.getColumnModel().getColumn(2).setMaxWidth(30);
					tablaFiltro.getColumnModel().getColumn(2).setMinWidth(30);
					
					tablaFiltro.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",10)); 
					tablaFiltro.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
					tablaFiltro.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("CHB","centro","Arial","normal",12));
				}
				
				ActionListener opAgregar = new ActionListener() {
					@SuppressWarnings({ "unchecked" })
					public void actionPerformed(ActionEvent arg0) {
						txtNombre_Completo.setText("");
				 		new Obj_Filtro_Dinamico(tablaFiltro, "Nombre", "", "", "", "", "", "", "");
				 		
						if(tablaFiltro.isEditing()){
				 			tablaFiltro.getCellEditor().stopCellEditing();
						}
						trsfiltro.setRowFilter(RowFilter.regexFilter("", 0));
						txtFolio.setText("");
						
						int contador=0;
				 		 Lista="('";	
				 			for(int i=0; i<tablaFiltro.getRowCount(); i++){
				 				if(Boolean.parseBoolean(modeloFiltro.getValueAt(i, 2).toString()) == true){
				 					String posicion = modeloFiltro.getValueAt(i, 0).toString().trim();
				 					
				 					contador=contador+=1;
				 							if(contador == 1){
				 								Lista=Lista+"'"+posicion+"'";
						 					}else{
						 						Lista=Lista+"',''"+posicion+"'";
						 					}
				 				}
				 			}
				 			Lista=Lista+"')";

				 			if(Lista.equals("('')")){
				 				JOptionPane.showMessageDialog(null, "Es necesario seleccionar un argunemto", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
	 							return;
				 			}else{
				 		        String operador_simbolo = "";
				 		        	
				 		            switch(Operador){
				 			    		case "Igual"		:operador_simbolo=" = "; 
				 			    		panelEnableFalse();
				 			    		parametroGeneral=Lista;
				 			    		
				 			    		break;
				 			    		case "Esta en lista":operador_simbolo=" in "; 
				 			    		panelEnableFalse();
				 			    		
				 			    		break;
				 			    		case "Menor que"	:operador_simbolo=" < "; 
				 			    		panelEnableFalse();
				 			    		
				 			    		break;
				 			    		case "Mayor que"	:operador_simbolo=" > "; 
				 			    		panelEnableFalse();
				 			    		
				 			    		break;
				 			    		case "Diferente"	:operador_simbolo=" <> "; 
				 			    		panelEnableFalse();
				 			    		
				 			    		break;
				 		    		}
				 				Lista=operador_simbolo+Lista;
				 				switch(folio_columna){
				 				           
							 				case "clase_producto":	txtFiltroClase.setText(Lista)		;
							 										if(Operador.equals("Igual")||Operador.equals("Esta en lista")){
							 											btnFiltroCategoria.setEnabled(true);
							 											btnLimpiarFiltroCategoria.setEnabled(true);
							 										}
							 										
							 										btnLimpiarFiltroClase.setEnabled(true);
							 										btnFiltroProducto.setEnabled(false);
							 				break;
							 				
							 				case "categoria":		txtFiltroCategoria.setText(Lista)	;
													 				if(Operador.equals("Igual")||Operador.equals("Esta en lista")){
							 											btnFiltroFamilia.setEnabled(true);
							 											btnLimpiarFiltroFamilia.setEnabled(true);
							 										}
													 				btnLimpiarFiltroCategoria.setEnabled(true);
													 				btnFiltroProducto.setEnabled(false);
							 				break;
							 				
							 				
							 				case "familia":			txtFiltroFamilia.setText(Lista)		;
													 				if(Operador.equals("Igual")||Operador.equals("Esta en lista")){
							 											btnFiltroLinea.setEnabled(true);
							 											btnLimpiarFiltroLinea.setEnabled(true);
							 										}
													 				btnLimpiarFiltroFamilia.setEnabled(true);
													 				btnFiltroProducto.setEnabled(false);
							 				break;
							 				
							 				case "linea_producto":	txtFiltroLinea.setText(Lista)		;
							 				
							 										btnLimpiarFiltroLinea.setEnabled(true);
													 				btnFiltroProducto.setEnabled(false);
	 										break;

							 				case "talla":			txtFiltroTalla.setText(Lista)		;
												 					if(!txtFiltroTalla.getText().equals("")){
												 						txtcod_prod.setText("");
												 						txtFiltroProducto.setText("");
												 						txtFiltroCategoria.setText("");
												 						txtFiltroClase.setText("");
												 						txtFiltroFamilia.setText("");
												 						txtFiltroLinea.setText("");
												 						btnFiltroProducto.setEnabled(false);
												 						btnFiltroCategoria.setEnabled(false);
							 											btnFiltroClase.setEnabled(false);
							 											btnFiltroFamilia.setEnabled(false);
								 										btnFiltroLinea.setEnabled(false);
								 										btnFiltroTalla.setEnabled(false);
								 										
								 										btnLimpiarFiltroTalla.setEnabled(true);
							 										}
											break;
				 				}
					 			dispose();
				 			}
					}
				};
				
				
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
				
				KeyListener opFiltroNombre = new KeyListener(){
					public void keyReleased(KeyEvent arg0) {
						new Obj_Filtro_Dinamico(tablaFiltro,"Nombre", txtNombre_Completo.getText().toUpperCase(),"","", "", "", "", "");
					}
					public void keyTyped(KeyEvent arg0) {}
					public void keyPressed(KeyEvent arg0) {}		
				};
				
				
			   	public Object[][] getTablaFiltro(String operador, String nombre_de_tabla){
			   		String condicion = "";
			   		
			   		if(!Lista.equals("")){
			   			
			   			condicion = " where jerarquia "+Lista.replace("''","'");
			   		}
			   		
					String todos = "select "+folio_columna+" as folio,upper(nombre) from "+nombre_de_tabla+condicion+" order by nombre";
					
					System.out.println(todos);
					Statement s;
					ResultSet rs;
					try {
						s = new Connexion().conexion_IZAGAR().createStatement();
						rs = s.executeQuery(todos);
						
						MatrizFiltro = new Object[getFilas(todos)][3];
						int i=0;
						while(rs.next()){
							String folio = rs.getString(1);
							MatrizFiltro[i][0] = folio+"  ";
							MatrizFiltro[i][1] = "   "+rs.getString(2).trim();
							MatrizFiltro[i][2] = false;
							i++;
						}
						Lista="";
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				    return MatrizFiltro; 
				}
			   	
			   	public int getFilas(String qry){
					int filas=0;
					Statement stmt = null;
					try {
						stmt = new Connexion().conexion_IZAGAR().createStatement();
						ResultSet rs = stmt.executeQuery(qry);
						while(rs.next()){
							filas++;
						}
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					return filas;
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
				
			}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Inventario_Parcial("","Todos", "Seleccione un Establecimiento").setVisible(true);
		}catch(Exception e){	}
	}

}
