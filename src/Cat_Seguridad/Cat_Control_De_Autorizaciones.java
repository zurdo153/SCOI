package Cat_Seguridad;

import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Seguridad.Obj_Autorizacion_Acceso_Proveedores;

@SuppressWarnings("serial")
public class Cat_Control_De_Autorizaciones extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	Obj_Autorizacion_Acceso_Proveedores autorizacion = new Obj_Autorizacion_Acceso_Proveedores();

	Obj_tabla ObjTab =new Obj_tabla();
	int columnas = 11,checkbox=-1;
	public void init_tabla_proveedores(String Estatus){
    	this.tabla_proveedores.getColumnModel().getColumn(0).setMinWidth (50 );
    	this.tabla_proveedores.getColumnModel().getColumn(0).setMaxWidth (50 );
    	this.tabla_proveedores.getColumnModel().getColumn(1).setMinWidth (150);
    	this.tabla_proveedores.getColumnModel().getColumn(2).setMinWidth (100);
    	this.tabla_proveedores.getColumnModel().getColumn(3).setMinWidth (240);
    	this.tabla_proveedores.getColumnModel().getColumn(4).setMinWidth (170);
    	this.tabla_proveedores.getColumnModel().getColumn(5).setMinWidth (80 );
    	this.tabla_proveedores.getColumnModel().getColumn(6).setMinWidth (120);
    	this.tabla_proveedores.getColumnModel().getColumn(7).setMinWidth (120);
    	this.tabla_proveedores.getColumnModel().getColumn(8).setMinWidth (250);
    	this.tabla_proveedores.getColumnModel().getColumn(9).setMinWidth (150);
    	this.tabla_proveedores.getColumnModel().getColumn(10).setMinWidth(300);

		String comandof="exec proveedores_autorizacion_de_acceso_filtro '"+Estatus+"'";
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tabla_proveedores,modelo, columnas, comandof, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] base (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio","Usuario Solicita","Establecimiento"," Nombre Proveedor","Ejecutivo","Estatus","Fecha Solicitud","Fecha Autorizacion","Usuario Autorizo","Motivo Negado","Observaciones"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = base();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){return false;}
	};
	
	JTable tabla_proveedores = new JTable(modelo);
	public JScrollPane scroll_tabla_proveedores = new JScrollPane(tabla_proveedores);
    @SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	     
	JTextField txtFiltro  = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La tabla_proveedores<<<", 500, "String");
	JTextField txtfolio            = new Componentes().text(new JCTextField(), "Folio"           ,10                                       , "Int"   );
	JTextField txtUsuario_solicita = new Componentes().text(new JCTextField(), "Usuario Solicita",200                                      , "String");
	JTextField txtEstablecimiento  = new Componentes().text(new JCTextField(), "Establecimiento" ,200                                      , "String");
	JTextField txtProveedor        = new Componentes().text(new JCTextField(), "Proveedor"       ,200                                      , "String");
	JTextField txtEjecutivo        = new Componentes().text(new JCTextField(), "Ejecutivo"       ,200                                      , "String");
	JTextField txtFcActual         = new Componentes().text(new JCTextField(), "Fecha Actual"    ,30                                       , "String");
	
	JCButton btnGuardar     = new JCButton("Guardar"      ,"Guardar.png","Azul");
	JCButton btnActualizar  = new JCButton("Actualizar"           ,"Actualizar.png","Azul");
	
	JLabel lblUsuario     = new JLabel();
	
	JTextArea  txaObservaciones  = new Componentes().textArea(new JTextArea(), "Observaciones Generales"       ,300);
	JScrollPane Observacion_Proveedores      = new JScrollPane(txaObservaciones);

	String estatus_solicitud[] = autorizacion.Combo_Estatus();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstatus_Solicitud = new JComboBox(estatus_solicitud);
	
	String estatus_solicitudtodos[] = autorizacion.Combo_Estatus_Todos();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstatus = new JComboBox(estatus_solicitudtodos);
	
	String motivo_negado[] = autorizacion.Combo_Negados();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbMotivo_Negado = new JComboBox(motivo_negado);
	
	JTabbedPane pestanas    = new JTabbedPane();
	JLayeredPane pProveedores       = new JLayeredPane(); 
	JLayeredPane pTrasladoMercancia = new JLayeredPane(); 
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cat_Control_De_Autorizaciones(){
		this.setSize(1024,555);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Control De Autorizaciónes Seguridad");
		this.panel.setBorder(BorderFactory.createTitledBorder("Control De Autorizaciónes Seguridad"));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/checklistbtn.png"));
		this.trsfiltro = new TableRowSorter(modelo); 
		this.tabla_proveedores.setRowSorter(trsfiltro);
		this.lblUsuario.setText("USUARIO: "+usuario.getNombre_completo());
		
		this.pestanas.addTab("Proveedores"    ,pProveedores );
		this.panel.add(lblUsuario).setBounds                     (650    ,15      ,400    ,20     );
		this.panel.add(pestanas).setBounds                       (10     ,15      ,1000   ,500    );
		
		 int x=5, y=9,width=350,height=20;
		this.pProveedores.setBorder(BorderFactory.createTitledBorder("Proveedores"));
		this.pProveedores.setOpaque(true); 
		this.pProveedores.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
		
		this.pProveedores.add(cmbEstatus).setBounds              (x+640  ,y       ,208    ,height );
		this.pProveedores.add(btnActualizar).setBounds           (x+865  ,y       ,120    ,height );
		this.pProveedores.add(txtFiltro).setBounds               (x      ,y+=25   ,985    ,height );
		this.pProveedores.add(scroll_tabla_proveedores).setBounds(x      ,y+=20   ,985    ,300    );
		this.pProveedores.add(txtfolio).setBounds                (x      ,y+=315  ,80     ,height );
		this.pProveedores.add(txtUsuario_solicita).setBounds     (x+80   ,y       ,width  ,height );
		this.pProveedores.add(txtEstablecimiento).setBounds      (x+430  ,y       ,200    ,height );	
		this.pProveedores.add(cmbEstatus_Solicitud).setBounds    (x+630  ,y       ,220    ,height );
		this.pProveedores.add(txtFcActual).setBounds             (x+860  ,y       ,80     ,height );
		this.pProveedores.add(txtProveedor).setBounds            (x      ,y+=20   ,width  ,height );
		this.pProveedores.add(txtEjecutivo).setBounds            (x+350  ,y       ,280    ,height );
		this.pProveedores.add(cmbMotivo_Negado).setBounds        (x+630  ,y       ,220    ,height );	
        this.pProveedores.add(Observacion_Proveedores).setBounds (x      ,y+=20   ,850    ,60     );  
		this.pProveedores.add(btnGuardar).setBounds              (x+860  ,y+=40   ,100    ,height );	
		
		this.tabla_proveedores.addMouseListener(opAgregar);
		this.cmbEstatus_Solicitud.addActionListener(seleccion_estatus_autorizacion);
		this.btnGuardar.addActionListener(Guardar_proveedores);
		this.btnActualizar.addActionListener(Actualizacion);
		this.cmbEstatus.addActionListener(Actualizacion_Por_Combo);
		this.txtFiltro.addKeyListener(op_filtro_nombre);
        this.init_tabla_proveedores("SOLICITADO");
        this.cmbEstatus.setSelectedItem("SOLICITADO");
        
        try {
			txtFcActual.setText(new BuscarSQL().fecha(0));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
        
    	panelenabledfalse();
		this.cont.add(panel);
	}
	
	ActionListener Actualizacion = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			init_tabla_proveedores(cmbEstatus.getSelectedItem().toString());
		}
	};
	
	ActionListener Actualizacion_Por_Combo = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			init_tabla_proveedores(cmbEstatus.getSelectedItem().toString());
		}
	};
	
	ActionListener seleccion_estatus_autorizacion = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(cmbEstatus_Solicitud.getSelectedItem().toString().trim().equals("NEGADO")){
				cmbMotivo_Negado.setEnabled(true);
				cmbMotivo_Negado.requestFocus();
				cmbMotivo_Negado.showPopup();
			}else {
				cmbMotivo_Negado.setEnabled(false);
				cmbMotivo_Negado.setSelectedIndex(0);
			}
		}
	};
	
	ActionListener Guardar_proveedores = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(cmbEstatus_Solicitud.getSelectedIndex()>0){
				if(cmbEstatus_Solicitud.getSelectedItem().toString().trim().equals("NEGADO")&&(cmbMotivo_Negado.getSelectedIndex()==0||txaObservaciones.getText().toString().equals("")) ){
					JOptionPane.showMessageDialog(null, "En Caso De Ser Negado el Acceso Es Requerido Selecione El Motivo y \n  Teclee Una Observacion Para Informar Al Solicitante ", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					cmbMotivo_Negado.setEnabled(true);
					cmbMotivo_Negado.requestFocus();
					cmbMotivo_Negado.showPopup();
				}else {
					  autorizacion.setFolio(Integer.valueOf(txtfolio.getText()));
				 	  autorizacion.setEstatus(cmbEstatus_Solicitud.getSelectedItem().toString());
					  autorizacion.setMotivo_negado_acceso(cmbMotivo_Negado.getSelectedItem().toString().trim());
				 	  autorizacion.setUsuario_autoriza(Integer.valueOf(usuario.getFolio() ));
                      autorizacion.setObservaciones(txaObservaciones.getText().toString().trim());
					  autorizacion.setNuevoModifica("M");		
					if(autorizacion.Guardar_Captura()) {
						panelenabledfalse();
						init_tabla_proveedores(cmbEstatus.getSelectedItem().toString());
						return;
					}else{
						JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
						return;
					}	
				}
			}else {
				JOptionPane.showMessageDialog(null, "Es Requerido Seleccione un Estatus De Solicitud", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				cmbEstatus_Solicitud.requestFocus();
				cmbEstatus_Solicitud.showPopup();
			}
		}
	};
	
	MouseListener opAgregar = new MouseListener() {
		public void mouseReleased(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseClicked(MouseEvent arg0) {

			if(arg0.getClickCount() == 2){
    			int fila = tabla_proveedores.getSelectedRow();
    			String fecha_atendio = (tabla_proveedores.getValueAt(fila,6).toString()).substring(0,10);
    
    		  if(fecha_atendio.equals(txtFcActual.getText())) {
    			  txtfolio.setText(tabla_proveedores.getValueAt(fila, 0).toString());
     			 txtUsuario_solicita.setText(tabla_proveedores.getValueAt(fila, 1).toString());
     			 txtEstablecimiento.setText(tabla_proveedores.getValueAt(fila, 2).toString());
     			 txtProveedor.setText(tabla_proveedores.getValueAt(fila, 3).toString());
     			 txtEjecutivo.setText(tabla_proveedores.getValueAt(fila, 4).toString());
     			 Observacion_Proveedores.setToolTipText(tabla_proveedores.getValueAt(fila, 10).toString());
     			 Observacion_Proveedores.setEnabled(true);
     			 cmbEstatus_Solicitud.setEnabled(true);
     			 cmbEstatus_Solicitud.requestFocus();
     			 cmbEstatus_Solicitud.showPopup();
    		  }else {
  				JOptionPane.showMessageDialog(null, "Solo Es Posible Editar Registros Del Mismo Dia", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
    		  }	 
    			 
        	}
		}
	};
	
	KeyListener op_filtro_nombre = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tabla_proveedores, txtFiltro.getText(), columnas,txtFiltro);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
		
	public void panelenabledfalse() {
		txtFcActual.setEditable(false);
		txtfolio.setEditable(false);
		txtUsuario_solicita.setEditable(false);
		txtEstablecimiento.setEditable(false);
	    txtProveedor.setEditable(false);
		txtEjecutivo.setEditable(false);
		cmbEstatus_Solicitud.setEnabled(false);
		cmbMotivo_Negado.setEnabled(false);
		Observacion_Proveedores.setEnabled(false);
	};
	
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Control_De_Autorizaciones().setVisible(true);
			}catch(Exception e){
				System.err.println("Error en Main: "+e.getMessage());
			}
		}
		
		
}
