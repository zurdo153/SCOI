package Cat_Servicios;

import java.awt.Container;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Time;
import java.text.SimpleDateFormat;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Servicios.Obj_Catalogo_Servicios;

@SuppressWarnings("serial")
public class Cat_Catalogo_De_Servicios extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	Obj_Catalogo_Servicios servicios	=new Obj_Catalogo_Servicios();
	
	String Departamento= servicios.buscar_Departamento(usuario.getFolio()).getDepartamento();
	
	JTextField txtFiltro = new Componentes().text(new JCTextField(), "Teclea Aqui Para Buscar En La Tabla", 500, "String");
	Connexion con = new Connexion();
	Obj_tabla ObjTab =new Obj_tabla();
	int columnas = 12,checkbox=-1;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(60);	
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(350);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(500);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(6).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(8).setMinWidth(120);
    	this.tabla.getColumnModel().getColumn(9).setMinWidth(250);
    	this.tabla.getColumnModel().getColumn(10).setMinWidth(120);
    	this.tabla.getColumnModel().getColumn(11).setMinWidth(250);
		String comando="sp_select_servicios_catalogo '"+Departamento+"'";
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] base (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		return types;
	}
	
	 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio","Servicio","Detalle","Prioridad","Dias","Tiempo","Departamento","Estatus","Fecha","Usuario","Fecha Modificacion","Usuario Modifico"}){
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
	     
	JTextField txtFolio        = new Componentes().text(new JCTextField(), "Folio", 9, "Int");
	JTextField txtServicio       = new Componentes().text(new JCTextField(), "Teclea La Descripción Del Servicio", 150, "String");
	
	JCButton btnFiltro         = new JCButton(""              ,"Filter-List-icon16.png","Azul");
	JCButton btnSalir          = new JCButton("Salir"         ,"salir16.png"      ,"Azul");
	JCButton btnDeshacer       = new JCButton("Deshacer"      ,"deshacer16.png"   ,"Azul");
	JCButton btnGuardar        = new JCButton("Guardar"       ,"Guardar.png"      ,"Azul");
	JCButton btnEditar         = new JCButton("Editar"        ,"editara.png"      ,"Azul");
	JCButton btnNuevo          = new JCButton("Nuevo"         ,"Nuevo.png"        ,"Azul");
	
	JLabel lblUsuario          = new JLabel("");
	JLabel lblDepartamento     = new JLabel("");
	
	JTextArea txaDetalle       = new Componentes().textArea(new JTextArea(), "", 1000);
	JScrollPane scrollDetalle  = new JScrollPane(txaDetalle);
	
	String[] status = {"VIGENTE","CANCELADO"};
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstatus = new JComboBox(status);
	
	 String Departamentos[] = new Obj_Departamento().Combo_Departamentoservicio();
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbDepartamento = new JComboBox(Departamentos);  
	 
	 String Prioridades[] = new Obj_Catalogo_Servicios().Prioridad();
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbPrioridades = new JComboBox(Prioridades);  
	 
	SpinnerDateModel sdmUnicaRepeticion =  new SpinnerDateModel();
    JSpinner spTiempoEstimado = new JSpinner(sdmUnicaRepeticion);   
	JSpinner.DateEditor spTiempo = new JSpinner.DateEditor(spTiempoEstimado,"HH:mm"); 
  
	JSpinner spDiasEstimados = new JSpinner(new SpinnerNumberModel( 0, 0, 90, 1 )); 
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Catalogo_De_Servicios(){
		this.setSize(1035,750);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/utilidades-agt-icono-6387-32.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Servicios"));
		this.setTitle("Alimentacion y Modificacion De Servicios");
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);
		 int x = 15, y=15, width=100, height=20, sep=100;		
		this.panel.add(new JLabel("Folio:")).setBounds      (x      ,y      ,width    ,height  );
		this.panel.add(txtFolio).setBounds                  (x+=45  ,y      ,width+32    ,height  );
		this.panel.add(cmbEstatus).setBounds                (x+=160  ,y      ,width    ,height  );
		this.panel.add(cmbPrioridades).setBounds            (x+=120 ,y      ,width+45 ,height  );
		this.panel.add(lblUsuario).setBounds                (x+=170 ,y      ,width*2  ,height  );
		this.panel.add(lblDepartamento).setBounds           (x+=170 ,y      ,width*2  ,height  );		
		  x=15;
		this.panel.add(new JLabel("Servicio:")).setBounds   (x      ,y+=30  ,width    ,height  );
		this.panel.add(txtServicio).setBounds               (x+=45  ,y      ,width+325,height  );
		this.panel.add(new JLabel("Dias Estimado:")).setBounds(x+=440,y     ,width    ,height  );
		this.panel.add(spDiasEstimados).setBounds           (x+=70  ,y      ,width-60 ,height  );
		this.panel.add(new JLabel("Horas Estimado:")).setBounds(x+=70,y     ,width    ,height  );
		this.panel.add(spTiempoEstimado).setBounds          (x+=80  ,y      ,width-40 ,height  );
		this.panel.add(cmbDepartamento).setBounds           (x+=90  ,y      ,width*2  ,height  );
		  x=15;
		this.panel.add(new JLabel("Detalle:")).setBounds    (x      ,y+=30  ,width    ,height  );
		this.panel.add(scrollDetalle).setBounds             (x+=45  ,y      ,953      ,height*4); 
		  x=15;
		this.panel.add(txtFiltro).setBounds         (x      ,y+=90  ,width*10 ,height  );
		this.panel.add(scroll_tabla).setBounds              (x      ,y+20   ,width*10 ,width*5-20 );
		  width=120;sep=220;
		this.panel.add(btnNuevo).setBounds                  (x      ,y+=510 ,width    ,height  );
		this.panel.add(btnSalir).setBounds                  (x+=sep ,y      ,width    ,height  );
		this.panel.add(btnDeshacer).setBounds               (x+=sep ,y      ,width    ,height  );
		this.panel.add(btnEditar).setBounds                 (x+=sep ,y      ,width    ,height  );
		this.panel.add(btnGuardar).setBounds                (x+=sep ,y      ,width    ,height  );

		lblUsuario.setText(usuario.getNombre_completo());
		cmbDepartamento.setSelectedItem(Departamento);
		
		init_tabla();
		tiempodefault("00:00:00");
		agregar(tabla);
		panelEnabledFalse();
		
		btnGuardar.addActionListener(guardar);
		btnSalir.addActionListener(cerrar);
		btnDeshacer.addActionListener(deshacer);
		btnNuevo.addActionListener(nuevo);
		btnEditar.addActionListener(editar);
		btnEditar.setEnabled(false);
		txtFolio.setEditable(false);
		txtFiltro.addKeyListener(opFiltroNombre);
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
	
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0), "nuevo");
        getRootPane().getActionMap().put("nuevo", new AbstractAction(){
            public void actionPerformed(ActionEvent e)
            {                 	    btnNuevo.doClick();
              	    }
        });
                    
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N,Event.CTRL_MASK),"nuevo");
	         getRootPane().getActionMap().put("nuevo", new AbstractAction(){
	             public void actionPerformed(ActionEvent e)
	             {                 	    btnNuevo.doClick();
	              	    }
	       });
	         
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0), "editar");
            getRootPane().getActionMap().put("editar", new AbstractAction(){
                public void actionPerformed(ActionEvent e)
                {                 	    btnEditar.doClick();
                  	    }
           });

            getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E,Event.CTRL_MASK), "editar");
            getRootPane().getActionMap().put("editar", new AbstractAction(){
                public void actionPerformed(ActionEvent e)
                {                 	    btnEditar.doClick();
                  	    }
           });
         
	}
	
	KeyListener opFiltroNombre = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tabla, txtFiltro.getText().toUpperCase(), columnas);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	@SuppressWarnings("deprecation")
	public void tiempodefault(String valor){
		String[] inicioDefault = valor.split(":");
		spTiempoEstimado.setValue(new Time(Integer.parseInt(inicioDefault[0]),Integer.parseInt(inicioDefault[1]),Integer.parseInt(inicioDefault[2])));
		spTiempoEstimado.setEditor(spTiempo);
	}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==1){
	        		if(servicios.getGuardar_actualizar().equals("E")){	
	        			if(JOptionPane.showConfirmDialog(null, "Esta Editando Un Registro y No A Guardado Los Datos, \n"
	        					                             + "Al Dar Si Se Cargaran Los Datos Del Registro Seleccionado Sin Guardar La Edicion \n ¿Desea Continuar? " ) == 0){
	        				panelEnabledFalse();
	    	        		int fila = tabla.getSelectedRow();
	    						txtFolio.setText(tabla.getValueAt(fila,0)+"");
	    						txtServicio.setText(tabla.getValueAt(fila,1)+"");
	    						txaDetalle.setText(tabla.getValueAt(fila,2)+"");
	    						cmbPrioridades.setSelectedItem(tabla.getValueAt(fila,3)+"");
	    						spDiasEstimados.setValue(Integer.valueOf(tabla.getValueAt(fila,4)+""));
	    						tiempodefault(tabla.getValueAt(fila,5)+"");
	                            cmbDepartamento.setSelectedItem(tabla.getValueAt(fila,6)+"");
	                            cmbEstatus.setSelectedItem(tabla.getValueAt(fila,7)+"");
	    						btnEditar.setEnabled(true);
	    						servicios.setGuardar_actualizar("");
	        				return;
	        			}
	        		}else{		
	        		panelEnabledFalse();
	        		int fila = tabla.getSelectedRow();
						txtFolio.setText(tabla.getValueAt(fila,0)+"");
						txtServicio.setText(tabla.getValueAt(fila,1)+"");
						txaDetalle.setText(tabla.getValueAt(fila,2)+"");
						cmbPrioridades.setSelectedItem(tabla.getValueAt(fila,3)+"");
						spDiasEstimados.setValue(Integer.valueOf(tabla.getValueAt(fila,4)+""));
						tiempodefault(tabla.getValueAt(fila,5)+"");
                        cmbDepartamento.setSelectedItem(tabla.getValueAt(fila,6)+"");
                        cmbEstatus.setSelectedItem(tabla.getValueAt(fila,7)+"");
						btnEditar.setEnabled(true);
						return;
	        	 }
	        	}
	        }
        });
    }
	
ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(ValidaCampos()!=""){
				JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n"+ValidaCampos(), "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				servicios.setFolio(Integer.valueOf(txtFolio.getText().trim()+"" ));
				servicios.setServicio(txtServicio.getText().toUpperCase().trim());
				servicios.setDetalle(txaDetalle.getText().toUpperCase().trim());
				servicios.setPrioridad(cmbPrioridades.getSelectedItem().toString().trim());
				servicios.setDias_estimado(Integer.valueOf(spDiasEstimados.getValue().toString()));
				servicios.setTiempo_estimado(new SimpleDateFormat ("H:mm").format(spTiempoEstimado.getValue()));
				servicios.setDepartamento(cmbDepartamento.getSelectedItem().toString().trim());
				servicios.setEstatus(cmbEstatus.getSelectedItem().toString().trim());
				servicios.setUsuario(usuario.getFolio());
				servicios.setUsuario_modifico(usuario.getFolio());
				
				if(servicios.getGuardar_actualizar().equals("E")){
						if(JOptionPane.showConfirmDialog(null, "El Registro Ya Existe, ¿Desea Cambiarlo?") == 0){
							if(servicios.GuardarActualizar()){
								init_tabla();
								  btnDeshacer.doClick();
								JOptionPane.showMessageDialog(null,"El Registró Se Guardó Correctamente!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
								return;
							}else{
								JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
								return;
							}
						}
				 }else{
						if(servicios.GuardarActualizar()){
							init_tabla();
							   btnDeshacer.doClick();
							JOptionPane.showMessageDialog(null,"El Registró Se Guardó Correctamente!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
							return;
						}else{
							JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
							return;
						}
				}
			}
		}
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			servicios.setGuardar_actualizar("E");//ACTUALIZAR
			panelEnabledTrue();
			btnEditar.setEnabled(false);
			btnNuevo.setEnabled(true);
		}		
	};
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			panelLimpiar();
			int valor =servicios.buscar_nuevo();
			if(valor != 0){
				txtFolio.setText(valor+1+"");
			}else{
				txtFolio.setText(1+"");
			}
			panelEnabledTrue();
			txtServicio.requestFocus();
			servicios.setGuardar_actualizar("S");//guardar
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelLimpiar();
			panelEnabledFalse();
			txtFiltro.requestFocus();
			btnNuevo.setEnabled(true);
			btnEditar.setEnabled(false);
			init_tabla();
		}
	};
	
	ActionListener cerrar = new ActionListener(){
		public void actionPerformed(ActionEvent e){	dispose();}
	};
	
	public void panelEnabledFalse(){	
		txtServicio.setEditable(false);
		txaDetalle.setEditable(false);
		spTiempoEstimado.setEnabled(false);
		spDiasEstimados.setEnabled(false);
		cmbEstatus.setEnabled(false);
		cmbPrioridades.setEnabled(false);
		cmbDepartamento.setEnabled(false);
	}		
	
	public void panelEnabledTrue(){	
		txtServicio.setEditable(true);
		txaDetalle.setEditable(true);
		spTiempoEstimado.setEnabled(true);
		spDiasEstimados.setEnabled(true);
		cmbEstatus.setEnabled(true);
		cmbPrioridades.setEnabled(true);
	}
	
	public void panelLimpiar(){	
		servicios.buscar_Departamento(usuario.getFolio());
		cmbDepartamento.setSelectedItem(Departamento);
		txtFolio.setText("");
		txtServicio.setText("");
		txaDetalle.setText("");
		cmbEstatus.setSelectedIndex(0);
		cmbPrioridades.setSelectedIndex(0);
		spDiasEstimados.setValue(Integer.valueOf(0));
		tiempodefault("00:00:00");
		servicios.setGuardar_actualizar("");//guardar
	}
	
	public String ValidaCampos(){
		String error ="";
		
		if(txtServicio.getText().equals("")) 
			error+= "Servicio\n";
		if(txaDetalle.getText().equals("")) 
			error+= "Detalle\n";
		
		if(cmbPrioridades.getSelectedIndex()==0) 
			error+= "Prioridad\n";
		
		if(cmbDepartamento.getSelectedIndex()==0) 
			error+= "Departamento\n";

		return error;
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Catalogo_De_Servicios().setVisible(true);
		}catch(Exception e){	}
	}
	
}