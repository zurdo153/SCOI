package Cat_Servicios;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Cat_Principal.EmailSenderService;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Servicios.Obj_Catalogo_Servicios;
import Obj_Servicios.Obj_Servicios;

@SuppressWarnings("serial")
public class Cat_Seguimiento_De_Servicios extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	Obj_Catalogo_Servicios servicios	=new Obj_Catalogo_Servicios();
	Obj_Servicios servicios_solicitud = new Obj_Servicios();
	
	String Departamento= servicios.buscar_Departamento(usuario.getFolio()).getDepartamento();
	
	JTextField txtFiltro = new Componentes().text(new JCTextField(), "Teclea Aqui Para Buscar En La Tabla", 500, "String");
	Connexion con = new Connexion();
	Obj_tabla ObjTab =new Obj_tabla();
	int columnas = 23,checkbox=-1;
	public void init_tabla(String status_pedidos){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(45);
    	this.tabla.getColumnModel().getColumn(0).setMaxWidth(45);
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(400);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(80);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(80);
    	this.tabla.getColumnModel().getColumn(4).setMinWidth(120);
    	this.tabla.getColumnModel().getColumn(5).setMinWidth(150);
    	this.tabla.getColumnModel().getColumn(6).setMinWidth(240);
    	this.tabla.getColumnModel().getColumn(7).setMinWidth(90);
    	this.tabla.getColumnModel().getColumn(8).setMinWidth(300);
    	this.tabla.getColumnModel().getColumn(9).setMinWidth(80);
    	this.tabla.getColumnModel().getColumn(10).setMinWidth(120);
     	this.tabla.getColumnModel().getColumn(11).setMinWidth(120);
    	this.tabla.getColumnModel().getColumn(12).setMinWidth(250);
    	this.tabla.getColumnModel().getColumn(13).setMinWidth(400);
    	this.tabla.getColumnModel().getColumn(14).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(15).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(16).setMinWidth(150);
    	this.tabla.getColumnModel().getColumn(17).setMinWidth(400);
    	this.tabla.getColumnModel().getColumn(18).setMinWidth(60);
    	this.tabla.getColumnModel().getColumn(18).setMaxWidth(60);
    	this.tabla.getColumnModel().getColumn(19).setMinWidth(300);
    	this.tabla.getColumnModel().getColumn(20).setMinWidth(60);
    	this.tabla.getColumnModel().getColumn(21).setMinWidth(300);
    	
		String comando="exec sp_select_seguimiento_a_servicios_pendientes_2 '"+Departamento+"','"+status_pedidos+"',"+usuario.getFolio();
		
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
		
		if(tabla.getRowCount()>0){txtPedientes.setText(tabla.getValueAt(0, 22).toString());}else {txtPedientes.setText("0");}
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] base (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		return types;
	}
	
	 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio","Detalle","Estatus","Prioridad","Fecha Solicitud","Establecimiento Solicito","Usuario Solicito","Departamento","Servicio Solicitado","Archivo","Fecha Atendio","Folio Atendio","Usuario Atendio","Notas ","Costo ","Evaluacion","Archivo Respuesta","Comentario Evaluacion","Folio Equipo","Descripcion Equipo","Folio Colaborador A.","Colaborador Asignado","Cantidad de Pendientes"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = base();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){return false;}
	    };
	    JTable tabla = new JTable(modelo);
		public JScrollPane scroll_tabla = new JScrollPane(tabla);
	     @SuppressWarnings("rawtypes")
	    private TableRowSorter trsfiltro;
	     
	JTextField txtFolio         = new Componentes().text(new JCTextField(), "Folio", 9, "Int");
	JTextField txtCosto         = new Componentes().text(new JCTextField(), "Costo", 9, "Double");
	JTextField txtServicio      = new Componentes().text(new JCTextField(), "Descripción Del Servicio", 150, "String");
	JTextField txtUsuario       = new Componentes().text(new JCTextField(), "Usuario Solicito", 300, "String");
	JTextField txtFAtendio      = new Componentes().text(new JCTextField(), "", 10, "Int");
	JTextField txtAtendio       = new Componentes().text(new JCTextField(), "Usuario Atendio", 300, "String");
	JTextField txtFcSolicito    = new Componentes().text(new JCTextField(), "Fecha Solicito", 30, "String");
	JTextField txtFcAtendio     = new Componentes().text(new JCTextField(), "Fecha Atendio" , 30, "String");
	JTextField txtFcActual      = new Componentes().text(new JCTextField(), "Fecha Actual"  , 30, "String");
	JTextField txtFolioequipo   = new Componentes().text(new JCTextField(), "Folio", 9, "Int");
	JTextField txtEquipo        = new Componentes().text(new JCTextField(), "Equipo Del Servicio", 150, "String");
	JTextField txtFolioasignado = new Componentes().text(new JCTextField(), "Folio Asignado", 9, "Int");
	JTextField txtAsignado      = new Componentes().text(new JCTextField(), "Colaborador Asignado", 150, "String");
	JTextField txtPedientes     = new Componentes().text(new JCTextField(), "Cant. Pendientes", 150, "String");
	
	JCButton btnActualizar      = new JCButton("Actualizar"           ,"Actualizar.png","Azul");
	JCButton btnFiltro          = new JCButton(""              ,"Filter-List-icon16.png","Azul");
	JCButton btnDeshacer        = new JCButton("Deshacer"      ,"deshacer16.png"   ,"Azul");
	JCButton btnGuardar         = new JCButton("Guardar"       ,"Guardar.png"      ,"Azul");
	JCButton btnDescAdjunto     = new JCButton("No Contiene Archivos Adjuntos","arrow-descarga-unidad-de-disco-duro-hdd-icono-9640-16.png","AzulC");
	JCButton btnAdjuntar        = new JCButton("Adjuntar Archivo Evidencia Servicio","adjuntar-icono-7764-16.png","AzulC");
	JCButton btnAtendio         = new JCButton("Atendio"              ,"key-group-icone-5159-16.png","AzulC");
	JCButton btnEquipo          = new JCButton("Equipo"               ,"los-parametros-de-las-herramientas-de-icono-8319-16.png","AzulC");
	JCButton btnAsignado        = new JCButton("Asignacion"           ,"verde-de-usuario-icono-7340-16.png","AzulC");
	
	JLabel lblUsuario           = new JLabel("");
	JLabel lblDepartamento      = new JLabel("");
	JTextArea txaDetalle        = new Componentes().textArea(new JTextArea(), "", 500);
	JTextArea txaNotas          = new Componentes().textArea(new JTextArea(), "", 500);
	JScrollPane scrollDetalle   = new JScrollPane(txaDetalle);
	JScrollPane scrollNotas     = new JScrollPane(txaNotas);
	
	String[] status = {"SOLICITADO Y EN PROCESO","SOLICITADO","EN PROCESO","TERMINADO","CANCELADO","NEGADO","MIS SERVICIOS ASIGNADOS"};
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstatus = new JComboBox(status);
	
   String[] statuscontesta = {"SOLICITADO","EN PROCESO","TERMINADO","CANCELADO","NEGADO"};	  
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstatusCo = new JComboBox(statuscontesta);
	  
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstatusFiltrado = new JComboBox(status);
		  
	 String Departamentos[] = new Obj_Departamento().Combo_Departamento();
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbDepartamento = new JComboBox(Departamentos);  
	 
	 String Prioridades[] = new Obj_Catalogo_Servicios().Prioridad();
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbPrioridades = new JComboBox(Prioridades);  
	 
	 String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	 
	 String evaluacion[] = {"","EXCELENTE","BIEN","REGULAR","DEFICIENTE","PESIMO"};
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEvaluacionServicio = new JComboBox(evaluacion);
	 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Seguimiento_De_Servicios(){
//		int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
//		int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
//		this.setSize(ancho,700);
		this.setSize(1024,700);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/utilidades-agt-icono-6387-32.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Servicios"));
		this.setTitle("Seguimiento De Servicios");
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);
		int x = 15, y=10, width=100, height=20, sep=100;		

		this.panel.add(new JLabel("Usuario:")).setBounds     (x       ,y      ,width    ,height   );
		this.panel.add(lblUsuario).setBounds                 (x+=45   ,y      ,width*2  ,height   );
		this.panel.add(lblDepartamento).setBounds            (x+=250  ,y      ,width*2  ,height   );
		this.panel.add(btnActualizar).setBounds              (x+=250  ,y      ,width+20 ,height   );
		this.panel.add(cmbEstatusFiltrado).setBounds         (x+=130  ,y      ,width+75 ,height   );
		this.panel.add(txtPedientes).setBounds               (x+=180  ,y      ,width-30 ,height   );
		this.panel.add(txtFcActual).setBounds                (x+=75  ,y      ,width-30 ,height   );

		x=15;
		this.panel.add(txtFiltro).setBounds                  (x       ,y+=19  ,width*10 ,height   );
		this.panel.add(scroll_tabla).setBounds               (x       ,y+20   ,width*10,width+200);
		
		x=15;
		this.panel.add(new JLabel("Folio:")).setBounds       (x       ,y+=325 ,width    ,height   );
		this.panel.add(txtFolio).setBounds                   (x+=40   ,y      ,width    ,height   );  
		this.panel.add(new JLabel("Servicio:")).setBounds    (x+=110  ,y      ,width    ,height   );
		this.panel.add(txtServicio).setBounds                (x+=45   ,y      ,width+325,height   );
		this.panel.add(new JLabel("Estatus:")).setBounds     (x+=435  ,y      ,width    ,height   );
		this.panel.add(cmbEstatus).setBounds                 (x+=45   ,y      ,width-5  ,height   );
		this.panel.add(new JLabel("Prioridad:")).setBounds   (x+=105  ,y      ,width    ,height   );
		this.panel.add(cmbPrioridades).setBounds             (x+=45   ,y      ,width+75 ,height   );
		
		x=15;
		this.panel.add(new JLabel("Solicito:")).setBounds    (x       ,y+=25  ,width    ,height   );
		this.panel.add(txtUsuario).setBounds                 (x+=40   ,y      ,width+300,height   );
		this.panel.add(new JLabel("Establecimiento:")).setBounds(x+=410,y     ,width    ,height   );
		this.panel.add(cmbEstablecimiento).setBounds          (x+=80  ,y      ,width+85 ,height   );
		this.panel.add(new JLabel("Departamento:")).setBounds(x+=195  ,y      ,width    ,height   );
		this.panel.add(cmbDepartamento).setBounds            (x+=75   ,y      ,width*2  ,height   );

		x=15;
		this.panel.add(new JLabel("Detalle:")).setBounds     (x       ,y+=25  ,width    ,height   );
		this.panel.add(scrollDetalle).setBounds              (x+=40   ,y      ,958      ,height*5 ); 
		
		x=15;
		this.panel.add(new JLabel("Fecha S:")).setBounds     (x       ,y+=105  ,width    ,height   );  
		this.panel.add(txtFcSolicito).setBounds              (x+=40   ,y      ,width+28 ,height   );  
		this.panel.add(btnDescAdjunto).setBounds             (x+=145  ,y      ,width+150,height   );
		this.panel.add(new JLabel("Evaluacion:")).setBounds  (x+=265  ,y      ,width    ,height   );  
		this.panel.add(cmbEvaluacionServicio).setBounds      (x+=55   ,y      ,width+20 ,height   );
		this.panel.add(new JLabel("Estatus Resuelto:")).setBounds(x+=130,y    ,width    ,height   );  
		this.panel.add(cmbEstatusCo).setBounds               (x+=85   ,y      ,width+35 ,height   );
		this.panel.add(new JLabel("Costo:")).setBounds       (x+=144  ,y      ,width    ,height   ); 
		this.panel.add(txtCosto).setBounds                   (x+=34   ,y      ,width    ,height   ); 
		
		x=15;
		this.panel.add(new JLabel("Notas:")).setBounds       (x       ,y+=25  ,width    ,height   );
		this.panel.add(scrollNotas).setBounds                (x+=40   ,y      ,958      ,height*3 ); 

		x=15;
		this.panel.add(new JLabel("Atendio:")).setBounds     (x       ,y+=65  ,width    ,height   ); 
		this.panel.add(txtFAtendio).setBounds                (x+=40   ,y      ,50       ,height   );
		this.panel.add(txtAtendio).setBounds                 (x+=50   ,y      ,width+200,height   ); 
		this.panel.add(btnAtendio).setBounds                 (x+=303  ,y      ,width+20 ,height   ); 
		this.panel.add(new JLabel("Fecha Atendio:")).setBounds(x+=130 ,y      ,width    ,height   ); 
		this.panel.add(txtFcAtendio).setBounds                (x+=75  ,y      ,width+28 ,height   ); 
//		this.panel.add(btnAdjuntar).setBounds                (x+=130  ,y      ,width+168 ,height  );

		x=15;width=120;sep=153;
		this.panel.add(new JLabel("Asignad:")).setBounds    (x       ,y+=25  ,width    ,height   ); 
		this.panel.add(txtFolioasignado).setBounds           (x+=40   ,y      ,50       ,height   );
		this.panel.add(txtAsignado).setBounds                (x+=50   ,y      ,width+180,height   );
		this.panel.add(btnAsignado).setBounds                (x+=303  ,y      ,width    ,height   );
		this.panel.add(btnDeshacer).setBounds                (x+=330  ,y      ,width    ,height   );
		this.panel.add(btnGuardar).setBounds                 (x+=sep  ,y      ,width    ,height   );
		
		x=15;
		this.panel.add(new JLabel("Equipo:")).setBounds      (x       ,y+=25  ,width    ,height   ); 
		this.panel.add(txtFolioequipo).setBounds             (x+=40   ,y      ,50       ,height   );
		this.panel.add(txtEquipo).setBounds                  (x+=50   ,y      ,width+180,height   );
		this.panel.add(btnEquipo).setBounds                  (x+=303  ,y      ,width    ,height   );
		
		lblUsuario.setText(usuario.getNombre_completo());
		lblDepartamento.setText(Departamento);
		
		init_tabla(cmbEstatusFiltrado.getSelectedItem().toString().trim());
		agregar(tabla);
		panelEnabledFalse();
		tabla.addKeyListener(seleccionservicio_teclado);
		
		btnDescAdjunto.addActionListener(opDescargarArchivoSolicitud);
		btnGuardar.addActionListener(guardar);
		btnDeshacer.addActionListener(deshacer);
		btnAtendio.addActionListener(modificaratendio);
		btnEquipo.addActionListener(modificarequipo);
		btnAsignado.addActionListener(asignanarcolaborador);
		btnActualizar.addActionListener(actualizartabla);
		cmbEstatusFiltrado.addActionListener(actualizartabla);
		txtFiltro.addKeyListener(opFiltroNombre);
		
		try {
			txtFcActual.setText(new BuscarSQL().fecha(0));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		cont.add(panel);
		
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
           	 txtFiltro.requestFocus();
           }
        });
        
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        getRootPane().getActionMap().put("escape", new AbstractAction(){
            public void actionPerformed(ActionEvent e)
            {                 	    btnDeshacer.doClick();
          	    }
        });
	
	}
	
	KeyListener opFiltroNombre = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tabla, txtFiltro.getText().toUpperCase(), columnas,txtFiltro);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener seleccionservicio_teclado = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {}
		@Override
		public void keyPressed(KeyEvent e){
		}
		@Override
		public void keyReleased(KeyEvent e){
			panelEnabledFalse();
    		int fila = tabla.getSelectedRow();
				txtFolio.setText(tabla.getValueAt(fila,0)+"");
				txaDetalle.setText(tabla.getValueAt(fila,1)+"");
				cmbEstatus.setSelectedItem(tabla.getValueAt(fila,2)+"");
				cmbEstatusCo.setSelectedItem(tabla.getValueAt(fila,2)+"");
				cmbPrioridades.setSelectedItem(tabla.getValueAt(fila,3)+"");
                cmbDepartamento.setSelectedItem(tabla.getValueAt(fila,7)+"");
                cmbEstablecimiento.setSelectedItem(tabla.getValueAt(fila,5)+"");
				txtUsuario.setText(tabla.getValueAt(fila,6)+"");
				txtServicio.setText(tabla.getValueAt(fila,7)+"");
				
				txtFAtendio.setText(tabla.getValueAt(fila,11)+"");
                txtAtendio.setText(tabla.getValueAt(fila,12)+"");
                txaNotas.setText(tabla.getValueAt(fila,13)+"");
                txtCosto.setText(tabla.getValueAt(fila,14)+"");
                cmbEvaluacionServicio.setSelectedItem(tabla.getValueAt(fila,15)+"");
                txtFolioequipo.setText(tabla.getValueAt(fila, 18)+"");
                txtEquipo.setText(tabla.getValueAt(fila, 19)+"");
                txtFolioasignado.setText(tabla.getValueAt(fila,20)+"");
                txtAsignado.setText(tabla.getValueAt(fila, 21)+"");
                
                //validacion del estatus de la actividad
                if(tabla.getValueAt(fila,2).toString().equals("TERMINADO")||tabla.getValueAt(fila,2).toString().equals("CANCELADO")){
                	cmbEstatusCo.setEnabled(false);
                	cmbEvaluacionServicio.setEnabled(false);
                	txtCosto.setEditable(false);
                	txaNotas.setEditable(false);
                	btnAtendio.setEnabled(false);
                	btnGuardar.setEnabled(false);
                	
                     	if(!tabla.getValueAt(fila,10).toString().equals("")){	
	                    	String fecha_atendio = (tabla.getValueAt(fila,10).toString()).substring(0,10);
	                    	if(txtFcActual.getText().toString().trim().equals(fecha_atendio)){
		                    	cmbEstatusCo.setEnabled(true);
		                    	cmbEstablecimiento.setEnabled(true);
		                    	txtCosto.setEditable(true);
		                    	txaNotas.setEditable(true);
		                    	btnAtendio.setEnabled(true);
		                    	btnGuardar.setEnabled(true);
		                    	//validacion si es el usuario creador para que clasifique la evaluacion
			                    if(tabla.getValueAt(fila,6).toString().equals(lblUsuario.getText())){
			                    	cmbEvaluacionServicio.setEnabled(true);
			                    }else{
			                    	cmbEvaluacionServicio.setEnabled(false);
			                    }
	                    	}
	                   	}	
                }else{
                	cmbEstablecimiento.setEnabled(true);
                	cmbEstatusCo.setEnabled(true);
                	txtCosto.setEditable(true);
                	txaNotas.setEditable(true);
                	btnAtendio.setEnabled(true);
                	btnAsignado.setEnabled(true);
                	btnGuardar.setEnabled(true);
                	
                	//validacion si es el usuario creador para que clasifique la evaluacion
                    if(tabla.getValueAt(fila,6).toString().equals(lblUsuario.getText())){
                    	cmbEvaluacionServicio.setEnabled(true);
                    }else{
                    	cmbEvaluacionServicio.setEnabled(false);
                    }
                }
				txtFcSolicito.setText(tabla.getValueAt(fila,4)+"");
				txtFcAtendio.setText(tabla.getValueAt(fila,10)+"");
				if(tabla.getValueAt(fila,9).toString().equals("")){
					btnDescAdjunto.setText("No Contiene Archivos Adjuntos");
					btnDescAdjunto.setEnabled(false);
				}else{
					btnDescAdjunto.setText("Descargar Archivo Adjunto");
					btnDescAdjunto.setEnabled(true);
				}
				return;
    	}
	};
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==1){
	        		
	        		panelEnabledFalse();
	        		int fila = tabla.getSelectedRow();
						txtFolio.setText(tabla.getValueAt(fila,0)+"");
						txaDetalle.setText(tabla.getValueAt(fila,1)+"");
						cmbEstatus.setSelectedItem(tabla.getValueAt(fila,2)+"");
						cmbEstatusCo.setSelectedItem(tabla.getValueAt(fila,2)+"");
						cmbPrioridades.setSelectedItem(tabla.getValueAt(fila,3)+"");
	                    cmbDepartamento.setSelectedItem(tabla.getValueAt(fila,7)+"");
	                    cmbEstablecimiento.setSelectedItem(tabla.getValueAt(fila,5)+"");
						txtUsuario.setText(tabla.getValueAt(fila,6)+"");
						txtServicio.setText(tabla.getValueAt(fila,7)+"");
						
						txtFAtendio.setText(tabla.getValueAt(fila,11)+"");
	                    txtAtendio.setText(tabla.getValueAt(fila,12)+"");
	                    txaNotas.setText(tabla.getValueAt(fila,13)+"");
	                    txtCosto.setText(tabla.getValueAt(fila,14)+"");
	                    cmbEvaluacionServicio.setSelectedItem(tabla.getValueAt(fila,15)+"");
	                    txtFolioequipo.setText(tabla.getValueAt(fila, 18)+"");
	                    txtEquipo.setText(tabla.getValueAt(fila, 19)+"");
	                    txtFolioasignado.setText(tabla.getValueAt(fila,20)+"");
	                    txtAsignado.setText(tabla.getValueAt(fila, 21)+"");
	                    
	                    //validacion del estatus de la actividad
	                    if(tabla.getValueAt(fila,2).toString().equals("TERMINADO")||tabla.getValueAt(fila,2).toString().equals("CANCELADO")){
	                    	cmbEstatusCo.setEnabled(false);
	                    	cmbEvaluacionServicio.setEnabled(false);
	                    	txtCosto.setEditable(false);
	                    	txaNotas.setEditable(false);
	                    	btnAtendio.setEnabled(false);
	                    	btnGuardar.setEnabled(false);
	                    	
	                         	if(!tabla.getValueAt(fila,10).toString().equals("")){	
			                    	String fecha_atendio = (tabla.getValueAt(fila,10).toString()).substring(0,10);
			                    	if(txtFcActual.getText().toString().trim().equals(fecha_atendio)){
				                    	cmbEstatusCo.setEnabled(true);
				                    	cmbEstablecimiento.setEnabled(true);
				                    	txtCosto.setEditable(true);
				                    	txaNotas.setEditable(true);
				                    	btnAtendio.setEnabled(true);
				                    	btnGuardar.setEnabled(true);
				                    	//validacion si es el usuario creador para que clasifique la evaluacion
					                    if(tabla.getValueAt(fila,6).toString().equals(lblUsuario.getText())){
					                    	cmbEvaluacionServicio.setEnabled(true);
					                    }else{
					                    	cmbEvaluacionServicio.setEnabled(false);
					                    }
			                    	}
			                   	}	
	                    }else{
	                    	cmbEstablecimiento.setEnabled(true);
	                    	cmbEstatusCo.setEnabled(true);
	                    	txtCosto.setEditable(true);
	                    	txaNotas.setEditable(true);
	                    	btnAtendio.setEnabled(true);
	                    	btnAsignado.setEnabled(true);
	                    	btnGuardar.setEnabled(true);
	                    	
	                    	//validacion si es el usuario creador para que clasifique la evaluacion
		                    if(tabla.getValueAt(fila,6).toString().equals(lblUsuario.getText())){
		                    	cmbEvaluacionServicio.setEnabled(true);
		                    }else{
		                    	cmbEvaluacionServicio.setEnabled(false);
		                    }
	                    }
						txtFcSolicito.setText(tabla.getValueAt(fila,4)+"");
						txtFcAtendio.setText(tabla.getValueAt(fila,10)+"");
						if(tabla.getValueAt(fila,9).toString().equals("")){
							btnDescAdjunto.setText("No Contiene Archivos Adjuntos");
							btnDescAdjunto.setEnabled(false);
						}else{
							btnDescAdjunto.setText("Descargar Archivo Adjunto");
							btnDescAdjunto.setEnabled(true);
						}
						return;
	        	}
	        }
        });
    }
	
	ActionListener actualizartabla = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelLimpiar();
			panelEnabledFalse();
			init_tabla(cmbEstatusFiltrado.getSelectedItem().toString().trim());
			txtFiltro.requestFocus();
		}
	};
	
	ActionListener opDescargarArchivoSolicitud = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int cantidadDeRegistrosGenerados =0;
			try {
				 cantidadDeRegistrosGenerados = new BuscarSQL().Archivos_Seguimiento_De_Servicios(Integer.valueOf(txtFolio.getText().toString()));
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if(cantidadDeRegistrosGenerados > 0){
				JOptionPane.showMessageDialog(null, "Se Genero el Archivo En La Ruta:\n C:\\REPORTES SCOI\\SERVICIOS\\","Aviso",JOptionPane.INFORMATION_MESSAGE);
				abrirarchivo("C:\\REPORTES SCOI\\SERVICIOS\\");
				return;
			}else{
				JOptionPane.showMessageDialog(null, "No Se Pudo Generar el Archivo Avise Al Administrador Del Sistema","Aviso",JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
	};
	
	public void abrirarchivo(String archivo){
	     try {
	            File objetofile = new File (archivo);
	            Desktop.getDesktop().open(objetofile);
	     }catch (IOException ex) {
	            System.out.println(ex);
	     }
	}       
	
	ActionListener modificaratendio = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Filtro_Colaborador("Atendio",Integer.valueOf(txtFolio.getText().toString())).setVisible(true);
		}		
	};
	
	ActionListener asignanarcolaborador = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Filtro_Colaborador("Asignar",Integer.valueOf(txtFolio.getText().toString())).setVisible(true);
		}		
	};
	
	ActionListener modificarequipo = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Filtro_Equipos().setVisible(true);
		}		
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelLimpiar();
			panelEnabledFalse();
			init_tabla(cmbEstatusFiltrado.getSelectedItem().toString().trim());
			txtFiltro.requestFocus();
		}
	};
	
    ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			int folio_servicio_solicitado=0;
             String Validacion_Guarda_Desde_PC="";
            try {
            	Validacion_Guarda_Desde_PC=servicios_solicitud.Validad_PC_Guarda(cmbEstablecimiento.getSelectedItem().toString().trim(),Departamento);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		  if(Validacion_Guarda_Desde_PC.equals("NO")){
				JOptionPane.showMessageDialog(null, "Es Requerido Que Esta Actividad Sea Contestada En el Establecimiento Que Lo Solicita", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
		  }else{			
				
			if(ValidaCampos()!=""){
				JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n"+ValidaCampos(), "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				servicios_solicitud.setServicio(txtServicio.getText().toUpperCase().trim());
				servicios_solicitud.setPrioridad(cmbPrioridades.getSelectedItem().toString().trim());
				servicios_solicitud.setDepartamento_solicito(lblDepartamento.getText().toString().trim());
				servicios_solicitud.setEstablecimiento_solicito(cmbEstablecimiento.getSelectedItem().toString().trim());
				servicios_solicitud.setDetalle(txaDetalle.getText().toUpperCase().trim());
				servicios_solicitud.setEquipo("PENDIENTE");
				servicios_solicitud.setEstatus_equipo("NO APLICA");
				servicios_solicitud.setEstatus(cmbEstatusCo.getSelectedItem().toString().trim());
				servicios_solicitud.setFolio_usuario_solicito(usuario.getFolio());
				servicios_solicitud.setEvaluacion(cmbEvaluacionServicio.getSelectedItem().toString().trim());
				servicios_solicitud.setFolio(Integer.valueOf(txtFolio.getText().trim()+"" ));
				servicios_solicitud.setCosto(Float.valueOf(txtCosto.getText().toString().trim()));
				servicios_solicitud.setNotas_servicio(txaNotas.getText().toUpperCase().trim());
				servicios_solicitud.setUsuario_realizo_servicio(Integer.valueOf(txtFAtendio.getText().trim()));
				servicios_solicitud.setGuardar_actualizar("A");
				servicios_solicitud.setFolio_usuario_modifico(usuario.getFolio());
				servicios_solicitud.setFolio_equipo(Integer.valueOf(txtFolioequipo.getText().toString()));

				folio_servicio_solicitado=servicios_solicitud.GuardarActualizar();
				if(folio_servicio_solicitado>0){
					   if(cmbEstatusCo.getSelectedItem().toString().trim().equals("TERMINADO")){
						   try {servicios_solicitud = new BuscarSQL().correo_informa_estatus_solicitud(Integer.valueOf(txtFolio.getText().toString().trim()),40);
							} catch (SQLException e1) {e1.printStackTrace();}
						   
						   if(!servicios_solicitud.getCorreos().toString().trim().equals("NO TIENE")){
							String Mensaje= " Hola "+txtUsuario.getText()+" Tu Servicio solicitado el dia "+txtFcSolicito.getText().toString()+" Detalle:"+txaDetalle.getText().toString().trim()+" fue atendido  y marcado como terminado el dia de hoy por "
									+txtAtendio.getText().toString().trim()+ " >>>"+txaNotas.getText().toString().trim()+"      Tienes el dia de hoy para Evaluar, despues de ese tiempo el servicio se cierra y se evaluara con la maxima calificacion  Saludos: SCOI";
							new EmailSenderService().enviarcorreo(servicios_solicitud.getCorreos(),servicios_solicitud.getCantidad_de_correos(),Mensaje, "Informe De Servicio Terminado "+servicios_solicitud.getFecha_guardado(),"Servicios");
						   }
					   }
					   
					   if(cmbEstatusCo.getSelectedItem().toString().trim().equals("CANCELADO") || cmbEstatusCo.getSelectedItem().toString().trim().equals("NEGADO")){
						   try {servicios_solicitud = new BuscarSQL().correo_informa_estatus_solicitud(Integer.valueOf(txtFolio.getText().toString().trim()),40);
							} catch (SQLException e1) {e1.printStackTrace();}
						   if(!servicios_solicitud.getCorreos().toString().trim().equals("NO TIENE")){
							String Mensaje= " Hola "+txtUsuario.getText()+" Tu Servicio solicitado el dia "+txtFcSolicito.getText().toString()+" con Detalle:"+txaDetalle.getText().toString().trim()+" fue atendido  y marcado como  "+cmbEstatusCo.getSelectedItem().toString().trim()+" el dia de hoy por "
									+txtAtendio.getText().toString().trim()+ " >>>informa que "+txaNotas.getText().toString().trim()+" Saludos: SCOI";
							new EmailSenderService().enviarcorreo(servicios_solicitud.getCorreos(),servicios_solicitud.getCantidad_de_correos(),Mensaje, "Informe De Servicio Terminado "+servicios_solicitud.getFecha_guardado(),"Servicios");
						   }
					   }
					init_tabla(cmbEstatusFiltrado.getSelectedItem().toString().trim());
					 btnDeshacer.doClick();
					JOptionPane.showMessageDialog(null,"El Registro Se Guardó Correctamente!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
					return;
				}else{
					JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
					return;
				}
			}
		  }	  
		}
	};
	
	public String ValidaCampos(){
		String error ="";
		if(txaNotas.getText().equals("")) 
			error+= "Notas Del Servicio\n";
		if(txtFAtendio.getText().equals("")||txtFAtendio.getText().toString().equals("0")) 
			error+= "Selecione Quien Atendio\n";
		if(cmbEstatusCo.getSelectedIndex()==0) 
			error+= "El Estatus Del Servicio debe de Cambiar De Solicitado\n";
		return error;
	}
	
	public void panelEnabledFalse(){	
		txtFolio.setEditable(false);
		txtUsuario.setEditable(false);
		txtServicio.setEditable(false);
		txaDetalle.setEditable(false);
		cmbEstatus.setEnabled(false);
		cmbPrioridades.setEnabled(false);
		cmbDepartamento.setEnabled(false);
		cmbEstablecimiento.setEnabled(false);
		txtFcAtendio.setEditable(false);
		txtFcSolicito.setEditable(false);
		btnDescAdjunto.setEnabled(false);
		txtFAtendio.setEditable(false);
		txtAtendio.setEditable(false);
		txtFcActual.setEditable(false);
    	cmbEstatusCo.setEnabled(false);
    	cmbEvaluacionServicio.setEnabled(false);
    	txtCosto.setEditable(false);
    	txaNotas.setEditable(false);
    	btnAtendio.setEnabled(false);
		txtFolioequipo.setEditable(false);
		txtEquipo.setEditable(false);
		btnAsignado.setEnabled(false);
		txtFolioasignado.setEditable(false);
		txtAsignado.setEditable(false);
		txtPedientes.setEditable(false);
	}		
	
	public void panelLimpiar(){	
		txtFolio.setText("");
		txtServicio.setText("");
		txtCosto.setText("");
		txaDetalle.setText("");
		txaNotas.setText("");
		txtUsuario.setText("");
		txtAtendio.setText("");
		txtFAtendio.setText("");
		txtFcAtendio.setText("");
		txtFolioequipo.setText("");
		txtEquipo.setText("");
		txtFolioasignado.setText("");
		txtAsignado.setText("");
		cmbEstatus.setSelectedIndex(0);
		cmbEstatusCo.setSelectedIndex(0);
		cmbPrioridades.setSelectedIndex(0);
		cmbDepartamento.setSelectedIndex(0);
		cmbEstablecimiento.setSelectedIndex(0);
		cmbEvaluacionServicio.setSelectedItem(0);
		servicios.setGuardar_actualizar("");
	}

	//TODO Filtro COLABORADOR
	public class Filtro_Colaborador extends JDialog{
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		JTextField txtFiltrof = new Componentes().text(new JCTextField(), "Teclea Aqui Para Buscar En La Tabla", 500, "String");
		Connexion con = new Connexion();
		Obj_tabla ObjTabf =new Obj_tabla();
		int columnas = 5,checkbox=-1;
		public void init_tablaf(){
	    	this.tablaf.getColumnModel().getColumn(0).setMinWidth(50);
	    	this.tablaf.getColumnModel().getColumn(0).setMaxWidth(50);
	    	this.tablaf.getColumnModel().getColumn(1).setMinWidth(270);
	    	this.tablaf.getColumnModel().getColumn(2).setMinWidth(120);
	    	this.tablaf.getColumnModel().getColumn(3).setMinWidth(120);
	    	this.tablaf.getColumnModel().getColumn(4).setMinWidth(220);
			String comandof="sp_select_servicios_relacion_de_colaboradores_del_establecimiento "+usuario.getFolio();
			String basedatos="26",pintar="si";
			ObjTabf.Obj_Refrescar(tablaf,modelf, columnas, comandof, basedatos,pintar,checkbox);
	    }
		
		@SuppressWarnings("rawtypes")
		public Class[] base (){
			Class[] types = new Class[columnas];
			for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
			return types;
		}
		
		 public DefaultTableModel modelf = new DefaultTableModel(null, new String[]{"Folio","Colaborador","Establecimiento","Departamento","Puesto"}){
			 @SuppressWarnings("rawtypes")
				Class[] types = base();
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
				public boolean isCellEditable(int fila, int columna){return false;}
		    };
		    JTable tablaf = new JTable(modelf);
			public JScrollPane scroll_tablaf = new JScrollPane(tablaf);
		     @SuppressWarnings("rawtypes")
		    private TableRowSorter trsfiltro;
		
		JCButton btnGuardarf      = new JCButton("Guardar Colaborador Asignado y Enviar Correo"       ,"Guardar.png"      ,"Azul");
		JTextField txtfolioselecionado = new Componentes().text(new JCTextField(), "Folio", 20, "Integer");
		JTextField txtselecionado = new Componentes().text(new JCTextField(), "Colaborador Asignado", 500, "String");
		JLabel lblBuscar      = new JLabel("BUSCAR : ");
		int folio_servicio=0;
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Filtro_Colaborador(String tipo_filtro,int Folio_solicitud)	{
			folio_servicio=Folio_solicitud;
			this.setModal(true);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
			this.setTitle("Filtro Colaboradores");
			this.setSize(805,470);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
			trsfiltro = new TableRowSorter(modelf); 
			tablaf.setRowSorter(trsfiltro); 
			campo.add(txtFiltrof).setBounds   (10,20,780,20);


			if (tipo_filtro.equals("Atendio")){
				campo.add(scroll_tablaf).setBounds(10,40,780,390);
				agregar(tablaf);
				tablaf.addKeyListener(seleccionEmpleadoconteclado);		
			}else{
				campo.add(scroll_tablaf).setBounds      (10 , 40,780,360);
				campo.add(txtfolioselecionado).setBounds(10 ,410, 50, 20);
				campo.add(txtselecionado).setBounds     (60 ,410,320, 20);
				campo.add(btnGuardarf).setBounds        (440,410,350, 20);
				txtfolioselecionado.setEditable(false);
				txtselecionado.setEditable(false);
				agregarasignado(tablaf);
				tablaf.addKeyListener(seleccionEmpleadocontecladopara_asignacion);	
				btnGuardarf.addActionListener(Guardar_asignacion);
			}
			
			init_tablaf();
			cont.add(campo);
			txtFiltrof.addKeyListener(opFiltrof);
		}
		
		ActionListener Guardar_asignacion = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
             if(txtfolioselecionado.getText().toString().equals("")){
 				JOptionPane.showMessageDialog(null, "Es Requerido Selecionar Primero Un colaborador:", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
            	 return;
             }else{
            	    servicios_solicitud.setFolio(folio_servicio);
            	    servicios_solicitud.setColaborador_asignado(Integer.valueOf(txtfolioselecionado.getText()+""));
            	    

            	    if(servicios_solicitud.GuardarAsignacion()){
	                    try { servicios_solicitud = new BuscarSQL().correo_informa_asignacion(Integer.valueOf(txtfolioselecionado.getText().toString().trim()));
	     					 }catch (SQLException e1) {e1.printStackTrace();}
	    				if(servicios_solicitud.getCorreos().toString().trim().equals("NO TIENE")){
				 				JOptionPane.showMessageDialog(null, "El Colaborador Selecionado No Tiene Correo Registrado \nPor Lo Cual Solo Se Guardará El Colaborador Asignado:", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						}else{
						String Mensaje= " HOLA "+txtselecionado.getText()+"  "+lblUsuario.getText()+"  TE HA ASIGNADO EL SERVICIO "+txtFolio.getText().toString()+"  "+txaDetalle.getText().toString().trim()+" SOLICITADO EL DIA"+txtFcSolicito.getText().toString()
									+" SALUDOS: SCOI";
						new EmailSenderService().enviarcorreo(servicios_solicitud.getCorreos(),servicios_solicitud.getCantidad_de_correos(),Mensaje, "Te An Asignado Una solicitud de Servicio "+servicios_solicitud.getFecha_guardado(),"Servicios");
						}
	    				
						JOptionPane.showMessageDialog(null,"El Registró Se Guardó Correctamente!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
						txtFolioasignado.setText(txtfolioselecionado.getText()+"");
						txtAsignado.setText(txtselecionado.getText()+"");
						dispose();
						init_tabla(cmbEstatusFiltrado.getSelectedItem().toString().trim());
						return;
					
            	}else{
					JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
					return;
                
			  }	
             }
			}
		};
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		        		int fila = tablaf.getSelectedRow();
		        		txtFAtendio.setText(tablaf.getValueAt(fila, 0).toString().trim());
		    			txtAtendio.setText (tablaf.getValueAt(fila, 1).toString().trim());
		    			dispose();
		    			return;
		        	}
		        }
	        });
	    }
		
		private void agregarasignado(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		        		int fila = tablaf.getSelectedRow();
		        		txtfolioselecionado.setText(tablaf.getValueAt(fila, 0).toString().trim());
		    			txtselecionado.setText (tablaf.getValueAt(fila, 1).toString().trim());
		    			return;
		        	}
		        }
	        });
	    }
		
		KeyListener opFiltrof = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				ObjTabf.Obj_Filtro(tablaf, txtFiltrof.getText().toUpperCase(), columnas,txtFiltrof);
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		KeyListener seleccionEmpleadoconteclado = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e){}
			@SuppressWarnings("static-access")
			@Override
			public void keyReleased(KeyEvent e){
				char caracter = e.getKeyChar();
				if(caracter==e.VK_ENTER){
				int fila=tablaf.getSelectedRow()-1;
				if(fila==-1){
					fila=tablaf.getRowCount()-1;
				}
	        	txtFAtendio.setText(tablaf.getValueAt(fila,0)+"");
	    		txtAtendio.setText (tablaf.getValueAt(fila, 1)+"");	
    			dispose();
    			return;
				}
			}
		};
		
		KeyListener seleccionEmpleadocontecladopara_asignacion = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e){
			}
			@Override
			public void keyReleased(KeyEvent e){
				int fila=tablaf.getSelectedRow();
        		txtfolioselecionado.setText(tablaf.getValueAt(fila, 0).toString().trim());
    			txtselecionado.setText (tablaf.getValueAt(fila, 1).toString().trim());
    			return;
			}
		};
		
	}
	
	//TODO Filtro Equipos
		public class Filtro_Equipos extends JDialog{
			Container cont = getContentPane();
			JLayeredPane campo = new JLayeredPane();
			JTextField txtFiltrof = new Componentes().text(new JCTextField(), "Teclea Aqui Para Buscar En La Tabla", 500, "String");
			Connexion con = new Connexion();
			Obj_tabla ObjTabf =new Obj_tabla();
			int columnas = 6,checkbox=-1;
			public void init_tablaf(){
		    	this.tablaf.getColumnModel().getColumn(0).setMinWidth(50);
		    	this.tablaf.getColumnModel().getColumn(0).setMaxWidth(50);
		    	this.tablaf.getColumnModel().getColumn(1).setMinWidth(290);
		    	this.tablaf.getColumnModel().getColumn(2).setMinWidth(180);
		    	this.tablaf.getColumnModel().getColumn(3).setMinWidth(130);
		    	this.tablaf.getColumnModel().getColumn(4).setMinWidth(130);
		    	this.tablaf.getColumnModel().getColumn(5).setMinWidth(220);
				String comandof="sp_select_filtro_equipos ";
				String basedatos="26",pintar="si";
				ObjTabf.Obj_Refrescar(tablaf,modelf, columnas, comandof, basedatos,pintar,checkbox);
		    }
			
			@SuppressWarnings("rawtypes")
			public Class[] base (){
				Class[] types = new Class[columnas];
				for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
				return types;
			}
			
			 public DefaultTableModel modelf = new DefaultTableModel(null, new String[]{"Folio","Descripcion","Tipo","Establecimiento","Departamento","Caracteristicas"}){
				 @SuppressWarnings("rawtypes")
					Class[] types = base();
					@SuppressWarnings({ "unchecked", "rawtypes" })
					public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
					public boolean isCellEditable(int fila, int columna){return false;}
			    };
			    JTable tablaf = new JTable(modelf);
				public JScrollPane scroll_tablaf = new JScrollPane(tablaf);
			     @SuppressWarnings("rawtypes")
			    private TableRowSorter trsfiltro;
			
			JLabel lblBuscar = new JLabel("BUSCAR: ");
			@SuppressWarnings({"rawtypes", "unchecked" })
			public Filtro_Equipos()	{
				this.setModal(true);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
				this.setTitle("Filtro Equipos");
				this.setSize(1000,470);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
				trsfiltro = new TableRowSorter(modelf); 
				tablaf.setRowSorter(trsfiltro); 
				campo.add(txtFiltrof).setBounds   (10,20,970,20);
				campo.add(scroll_tablaf).setBounds(10,40,970,390);
				init_tablaf();
				cont.add(campo);
				agregar(tablaf);
				txtFiltrof.addKeyListener(opFiltrof);
				tablaf.addKeyListener(seleccionEmpleadoconteclado);
			}
			
			private void agregar(final JTable tbl) {
		        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
			        	if(e.getClickCount() == 2){
			        		int fila = tablaf.getSelectedRow();
			        		txtFolioequipo.setText(tablaf.getValueAt(fila, 0).toString().trim());
			    			txtEquipo.setText (tablaf.getValueAt(fila, 1).toString().trim());
			    			dispose();
			    			return;
			        	}
			        }
		        });
		    }
			
			KeyListener opFiltrof = new KeyListener(){
				public void keyReleased(KeyEvent arg0) {
					ObjTabf.Obj_Filtro(tablaf, txtFiltrof.getText().toUpperCase(), columnas,txtFiltrof);
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyPressed(KeyEvent arg0) {}		
			};
			
			KeyListener seleccionEmpleadoconteclado = new KeyListener() {
				@SuppressWarnings("static-access")
				@Override
				public void keyTyped(KeyEvent e) {
					char caracter = e.getKeyChar();
					if(caracter==e.VK_ENTER){
					int fila=tabla.getSelectedRow()-1;
					txtFolioequipo.setText(tablaf.getValueAt(fila, 0).toString().trim());
					txtEquipo.setText (tablaf.getValueAt(fila, 1).toString().trim());
	    			dispose();
	    			return;
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
			new Cat_Seguimiento_De_Servicios().setVisible(true);
		}catch(Exception e){	}
	}
	
}