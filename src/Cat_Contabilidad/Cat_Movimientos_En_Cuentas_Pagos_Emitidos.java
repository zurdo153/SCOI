package Cat_Contabilidad;

import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Obj_Contabilidad.Obj_Pagos_Emitidos;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Movimientos_En_Cuentas_Pagos_Emitidos extends JDialog{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JToolBar menu_toolbar      = new JToolBar();
	
	JCButton btnNuevo          = new JCButton("Nuevo"     ,"Nuevo.png"                   ,"Azul");
	JCButton btnBuscar         = new JCButton("Buscar"    ,"Filter-List-icon16.png"      ,"Azul"); 
	JCButton btnEditar         = new JCButton("Editar"    ,"Filter-List-icon16.png"      ,"Azul"); 
	JCButton btnGuardar        = new JCButton("Guardar"   ,"Guardar.png"                 ,"Azul");
	JCButton btnDeshacer       = new JCButton("Deshacer"  ,"deshacer16.png"              ,"Azul");
	
	Object[] cuentas = new Obj_Pagos_Emitidos().cuentas(); 
//		{"Selecciona Una Cuenta","00000001","00000002"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbCuentas = new JComboBox(cuentas); 
	
	String[] status = {"Selecciona Un Status","COBRADO","PENDIENTE DE COBRO"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbStatus = new JComboBox(status);
	
	JDateChooser DCFecha = new Componentes().jchooser(new JDateChooser()  ,"",0);
	
	JTextField txtFolio = new Componentes().text(new JCTextField(), "Folio", 15, "Int");
	JTextField txtCheque = new Componentes().text(new JCTextField(), "Folio De Cheque", 15, "String");
	JTextField txtImporte = new Componentes().text(new JCTextField(), "Importe", 20, "Double");
	
	JTextArea txaObservacion = new Componentes().textArea(new JTextArea(), "Observacion", 500);
	JScrollPane scrollObservacion = new JScrollPane(txaObservacion);

	String bandera = "";
	public Cat_Movimientos_En_Cuentas_Pagos_Emitidos() {
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
		this.setTitle("Pagos Emitidos");
		panel.setBorder(BorderFactory.createTitledBorder("Capturar"));
		
	    this.menu_toolbar.add(btnNuevo    );
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.add(btnBuscar   );
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.add(btnEditar   );
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnDeshacer );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnGuardar  );
		this.menu_toolbar.setFloatable(false);
		
		int x=20, y=15, ancho=80;
		
		panel.add(menu_toolbar).setBounds(x, y, ancho*5, 20);
		
		panel.add(new JLabel("Folio:")).setBounds(x, y+=30, ancho, 20);
		panel.add(txtFolio).setBounds(x+ancho, y, ancho*2, 20);
		
		panel.add(new JLabel("Cuenta:")).setBounds(x, y+=25, ancho, 20);
		panel.add(cmbCuentas).setBounds(x+ancho, y, ancho*2, 20);
		
		panel.add(new JLabel("Observacion:")).setBounds(x+(ancho*3)+30, y, ancho, 20);
		panel.add(scrollObservacion).setBounds(x+(ancho*3)+30, y+20, ancho*4, 100);
		
		panel.add(new JLabel("Cheque:")).setBounds(x, y+=25, ancho, 20);
		panel.add(txtCheque).setBounds(x+ancho, y, ancho*2, 20);
		
		panel.add(new JLabel("Fecha:")).setBounds(x, y+=25, ancho, 20);
		panel.add(DCFecha).setBounds(x+ancho, y, ancho*2, 20);
		
		panel.add(new JLabel("Importe:")).setBounds(x, y+=25, ancho, 20);
		panel.add(txtImporte).setBounds(x+ancho, y, ancho*2, 20);
		
		panel.add(new JLabel("Status:")).setBounds(x, y+=25, ancho, 20);
		panel.add(cmbStatus).setBounds(x+ancho, y, ancho*2, 20);
		
		cont.add(panel);
		
		deshacer();
		btnNuevo.addActionListener(opBtn);
		btnBuscar.addActionListener(opBtn);
		btnEditar.addActionListener(opBtn);
		btnDeshacer.addActionListener(opBtn);
		btnGuardar.addActionListener(opBtn);
		
		this.setSize(630,240);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}
	
	ActionListener opBtn = new ActionListener(){
		public void actionPerformed(ActionEvent e){

			switch(e.getActionCommand()){
				case "Nuevo"	: 	nuevo(); 	break;
				case "Buscar"	: 	buscar(); 	break;
				case "Editar"	: 	editar(); 	break;
				case "Deshacer"	:	deshacer(); break;
				case "Guardar"	:	guardar(); 	break;
			}
		}
	};
	
	public void nuevo(){
		txtFolio.setText(new BuscarSQL().buscar_folio_consecutivo_por_folio_de_transaccion(50));//pagos emitidos
		blockNuevo();
	}
	
	public void buscar(){
		new Cat_Filtro_De_Pagos_Emitidos().setVisible(true);
		bandera = "MODIFICAR";
	}
	
	public void editar(){
		bandera="MODIFICAR";
		blockEditar();
	}
	
	public void deshacer(){
		blockDefault();
	}
	
	public void guardar(){
		
		String CamposRequeridos = validaCampos();
		
		if(CamposRequeridos.equals("")){
				Obj_Pagos_Emitidos pagos = new Obj_Pagos_Emitidos();
				
				pagos.setFolio(Integer.parseInt(txtFolio.getText().trim()));
				pagos.setCuenta(cmbCuentas.getSelectedItem().toString().trim());
				pagos.setCheque(txtCheque.getText().trim());
				pagos.setFecha(new SimpleDateFormat("dd/MM/yyyy").format(DCFecha.getDate()));
				pagos.setImporte(Double.valueOf(txtImporte.getText().trim()));
				pagos.setStatus_cobro(cmbStatus.getSelectedItem().toString().trim());
				pagos.setObservacion(txaObservacion.getText().toUpperCase().trim());
				pagos.setBandera(bandera);
				
				if(pagos.guardar()){
					
						deshacer();
						JOptionPane.showMessageDialog(null, "El Registro Fue Guardado Correctamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
						return;
				 }else{
						JOptionPane.showMessageDialog(null,  "A Ocurrido Un Error En El Guardado, Avise Al Administrador Del Sistema","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
						return;
				 }
		}else{
				JOptionPane.showMessageDialog(null,  "Los Siguientes Campos Son Requeridos:\n"+CamposRequeridos,"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				return;
		}
		
	}
	
	public String validaCampos(){
		String cadena = "";
		
			cadena+=txtFolio.getText().trim().equals("") ? "- Folio\n" : "";
			cadena+=cmbCuentas.getSelectedIndex()==0 ? "- Cuenta\n":"";
			cadena+=txtCheque.getText().trim().equals("") ? "- Cheque\n":"";
			cadena+=DCFecha.getDate()==null ? "- Fecha\n":"";
			cadena+=txtImporte.getText().trim().equals("") ? "- Importe\n":"";
			cadena+=cmbStatus.getSelectedIndex()==0 ? "- Status\n":"";
			cadena+=txaObservacion.getText().trim().equals("") ? "- Observacion\n":"";
		
		return cadena;
	}
	
	public void blockDefault(){
		txtFolio.setEditable(false);
		cmbCuentas.setEnabled(false);
		txtCheque.setEditable(false);
		DCFecha.setEnabled(false);
		txtImporte.setEditable(false);
		cmbStatus.setEnabled(false);
		txaObservacion.setEditable(false);
		txaObservacion.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
		
		btnNuevo.setEnabled(true);
		btnBuscar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnDeshacer.setEnabled(true);
		btnGuardar.setEnabled(false);
		
		txtFolio.setText("");
		cmbCuentas.setSelectedIndex(0);
		txtCheque.setText("");
		DCFecha.setDate(null);
		txtImporte.setText("");
		cmbStatus.setSelectedIndex(0);
		txaObservacion.setText("");
		
		bandera = "";
		cmbStatus.setSelectedItem("PENDIENTE DE COBRO");
	}
	
	public void blockNuevo(){
		txtFolio.setEditable(false);
		cmbCuentas.setEnabled(true);
		txtCheque.setEditable(true);
		DCFecha.setEnabled(true);
		txtImporte.setEditable(true);
//		cmbStatus.setEnabled(true);
		txaObservacion.setEditable(true);
		txaObservacion.setBackground(new Color(Integer.parseInt("FFFFFF",16)));
		
		btnNuevo.setEnabled(false);
		btnBuscar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnDeshacer.setEnabled(true);
		btnGuardar.setEnabled(true);
		
		cmbCuentas.setSelectedIndex(0);
		txtCheque.setText("");
		DCFecha.setDate(null);
		txtImporte.setText("");
//		cmbStatus.setSelectedIndex(0);
		txaObservacion.setText("");
		
		bandera = "GUARDAR";
	}
	
	public void blockEditar(){
		txtFolio.setEditable(false);
		cmbCuentas.setEnabled(true);
		txtCheque.setEditable(true);
		DCFecha.setEnabled(true);
		txtImporte.setEditable(true);
//		cmbStatus.setEnabled(true);
		txaObservacion.setEditable(true);
		txaObservacion.setBackground(new Color(Integer.parseInt("FFFFFF",16)));
		
		btnNuevo.setEnabled(false);
		btnBuscar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnDeshacer.setEnabled(true);
		btnGuardar.setEnabled(true);
		
		bandera = "MODIFICAR";
	}
	
	//TODO filtro Busqueda de Pagos Emitidos
		@SuppressWarnings("serial")
		public class Cat_Filtro_De_Pagos_Emitidos extends JDialog {
			Container cont = getContentPane();
			JLayeredPane campo = new JLayeredPane();
			Connexion con = new Connexion();
			Obj_tabla ObjTabf =new Obj_tabla();
			int columnas = 9,checkbox=-1;
			public void init_tablaf(){
		    	this.tablaf.getColumnModel().getColumn(0).setMinWidth(50);
		    	this.tablaf.getColumnModel().getColumn(0).setMaxWidth(50);
		    	this.tablaf.getColumnModel().getColumn(1).setMinWidth(100);
		    	this.tablaf.getColumnModel().getColumn(2).setMinWidth(100);
		    	this.tablaf.getColumnModel().getColumn(3).setMinWidth(100);
		    	
		    	this.tablaf.getColumnModel().getColumn(4).setMinWidth(100);
		    	this.tablaf.getColumnModel().getColumn(5).setMinWidth(150);
		    	this.tablaf.getColumnModel().getColumn(6).setMinWidth(150);
		    	this.tablaf.getColumnModel().getColumn(7).setMinWidth(250);
		    	this.tablaf.getColumnModel().getColumn(8).setMinWidth(100);
		    	
				String comandof="exec movimientos_en_cuentas_pagos_emitidos_filtro";
				String basedatos="26",pintar="si";
				ObjTabf.Obj_Refrescar(tablaf,modelf, columnas, comandof, basedatos,pintar,checkbox);
		    }
			
			@SuppressWarnings("rawtypes")
			public Class[] base (){
				Class[] types = new Class[columnas];
				for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
				return types;
			}
			
			 public DefaultTableModel modelf = new DefaultTableModel(null, new String[]{"Folio","Cuenta","Cheque","Fecha","Importe","Status_Cobro","Observacion","Usuario","Fecha Ult. Mod."}){
				 @SuppressWarnings("rawtypes")
					Class[] types = base();
					@SuppressWarnings({ "rawtypes", "unchecked" })
					public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
					public boolean isCellEditable(int fila, int columna){return false;}
			    };
			    JTable tablaf = new JTable(modelf);
				public JScrollPane scroll_tablaf = new JScrollPane(tablaf);
			     JTextField txtFolioFiltroEmpleado  = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String",tablaf,columnas);

			public Cat_Filtro_De_Pagos_Emitidos(){
				this.setSize(1040,650);
				this.setModal(true);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
				this.setTitle("Filtro de Pagos Emitidos");
				campo.setBorder(BorderFactory.createTitledBorder("Filtro De Pagos Emitidos"));
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
				campo.add(scroll_tablaf).setBounds(15,42,1000,565);
				campo.add(txtFolioFiltroEmpleado).setBounds(15,20,300,20);
				
				init_tablaf();
				agregar(tablaf);
				cont.add(campo);
				txtFolioFiltroEmpleado.requestFocus();
				
	              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "foco");
	              getRootPane().getActionMap().put("foco", new AbstractAction(){       
	            	  @Override
	                  public void actionPerformed(ActionEvent e) {
	                	  txtFolioFiltroEmpleado.setText("");
	                	  txtFolioFiltroEmpleado.requestFocus();
	                  }
	              });
	              
	       		this.agregar(tablaf);
			}
			
			private void agregar(final JTable tbl) {
				tbl.addMouseListener(new MouseListener() {
					public void mouseReleased(MouseEvent e) {
				 	 if(e.getClickCount() == 2){try {
						funcion_agregar();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}}
					}
					public void mousePressed(MouseEvent e) {}
					public void mouseExited(MouseEvent e)  {}
					public void mouseEntered(MouseEvent e) {}
					public void mouseClicked(MouseEvent e) {}
				});
				tbl.addKeyListener(new KeyListener() {
					public void keyPressed(KeyEvent e)  {
						if(e.getKeyCode()==KeyEvent.VK_ENTER){
							try {
								funcion_agregar();
							} catch (ParseException e1) {
								e1.printStackTrace();
							}	
						}
					}
					public void keyReleased(KeyEvent e)   {}
					public void keyTyped   (KeyEvent e)   {}
				});
		    }
		    public void funcion_agregar() throws ParseException {
		    	
		    	btnNuevo.setEnabled(false);
		    	btnBuscar.setEnabled(false);
		    	btnEditar.setEnabled(true);
		    	
		    	int fila = tablaf.getSelectedRow();

		    	txtFolio.setText(tablaf.getValueAt(fila, 0).toString().trim());
				cmbCuentas.setSelectedItem(tablaf.getValueAt(fila, 1).toString().trim());
				txtCheque.setText(tablaf.getValueAt(fila, 2).toString().trim());
				DCFecha.setDate(fecha(tablaf.getValueAt(fila, 3).toString().trim()));
				txtImporte.setText(tablaf.getValueAt(fila, 4).toString().trim());
				cmbStatus.setSelectedItem(tablaf.getValueAt(fila, 5).toString().trim());
				txaObservacion.setText(tablaf.getValueAt(fila, 6).toString().trim());
				
				dispose();
		    };
			
		}
		
		public Date fecha(String fechap) {
			Date fecha = null;
			try {if(fechap.equals("")){	fecha=null;	}else {	fecha=new SimpleDateFormat("dd/MM/yyyy").parse(fechap);	}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return fecha;
		} 
		
		public static void main(String[] args) {
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Movimientos_En_Cuentas_Pagos_Emitidos().setVisible(true);
			}catch(Exception e){	}
		}
}