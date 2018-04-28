package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Descripcion_De_Puestos_y_Responsabilidades extends JFrame{

	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JLabel lblFolio = new JLabel("Folio:");
	JLabel lblPuesto = new JLabel("Nombre Del Puesto:");
	JLabel lblUnidadDeNegocio = new JLabel("Unidad De Negocio:");
	JLabel lblEstablecimiento = new JLabel("Establecimiento:");
	JLabel lblDepartamento = new JLabel("Departamento:");
	JLabel lblReportaA = new JLabel("Reporta A:");
	
	JLabel lblEdadIn = new JLabel("Rango De Edad De:");
	JLabel lblEdadFin = new JLabel("A:");
	
	JLabel lblObjetivoDelPuesto = new JLabel("Objetivo Del Puesto:");
	JLabel lblObjetivoDelPuestoNota = new JLabel("(¿Cuál es la misión o razón por la que existe el puesto?)");
	
	JLabel lblResponsabilidadesDelPuesto = new JLabel("Responsabilidades Del Puesto:");
	JLabel lblResponsabilidadesDelPuestoNota = new JLabel("(¿Especifique qué hace y para que lo hace?)");
//	------------------------------------------------------------------------------------------------------------------------------------------------------
	JTextField txtFolio = new Componentes().text(new JCTextField(), "Folio", 10, "Int");
	JTextField txtPuesto = new Componentes().text(new JCTextField(), "Nombre De Puesto", 120, "String");
	JCButton btnBuscarPuesto = new JCButton("", "buscar.png", "Azul");
	
	String[] UN = {"Alimentos","Ferreteria","Refaccionaria","Retail","Staff"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbUnidadDeNegocio = new JComboBox(UN);
	
	String[] estab = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(estab);
	
	String[] depto = new Obj_Departamento().Combo_Departamento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbDepartamento = new JComboBox(depto);
	
	JLabel lblFolioReportaA = new JLabel("1234");
	JTextField txtReporteA = new Componentes().text(new JCTextField(), "Reporta Al Puesto", 120, "String");
	JCButton btnReporteA = new JCButton("", "buscar.png", "Azul");
	
	JTextField txtEdadIn = new Componentes().text(new JCTextField(), "", 3, "Int");
	JTextField txtEdadFin = new Componentes().text(new JCTextField(), "", 3, "Int");
	
	JTextArea txaObjectivoDelPuesto = new Componentes().textArea(new JTextArea(), "Objectivo Del Puesto", 200);
	JScrollPane scrollObjectivoDelPuesto = new JScrollPane(txaObjectivoDelPuesto);
	
	
	JTextField txtResponsabilidad = new Componentes().text(new JCTextField(), "Ingresar Responsabilidad", 200, "String");//TODO se agregara a una tabla
	JCButton btnAgregarResponsabilidad = new JCButton("Agregar", "", "Azul");
	JCButton btnSubir = new JCButton("", "Up.png", "Azul");
	JCButton btnBajar = new JCButton("", "Down.png", "Azul");
	
	//TABLA MOVIMIENTOS BANCARIOS
		DefaultTableModel modeloResponsabilidadesDelPuesto = new DefaultTableModel(null,new String[]{"Orden", "Descripcion","*" }
				){
		     @SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
		    	java.lang.String.class,
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
		};
		JTable tablaResponsabilidadesDelPuesto = new JTable(modeloResponsabilidadesDelPuesto);
	    JScrollPane scrollResponsabilidadesDelPuesto = new JScrollPane(tablaResponsabilidadesDelPuesto);
	    JCButton btnQuitarfila= new JCButton("Eliminar Fila","boton-rojo-menos-icono-5393-16.png","Azul");
	    
	Border blackline;
	public Cat_Descripcion_De_Puestos_y_Responsabilidades() {
		this.setSize(965,700);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
//		efectos de bordes
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/usuario-grupo-icono-5183-64.png"));
		this.setTitle("Descripción De Puestos Y Responsabilidades");
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline, "Ingresar Datos DPR"));
		
		int x=10, y=15, ancho=80;
		
		panel.add(lblFolio).setBounds(x, y, ancho, 20);
		panel.add(txtFolio).setBounds(x+ancho+20, y, ancho*2, 20);
		panel.add(lblPuesto).setBounds(x, y+=25, ancho+20, 20);
		panel.add(txtPuesto).setBounds(x+ancho+20, y, ancho*5, 20);
		panel.add(btnBuscarPuesto).setBounds(x+(ancho*6)+20, y, 30, 20);
		
		panel.add(lblUnidadDeNegocio).setBounds(x, y+=25, ancho*3, 20);
		panel.add(cmbUnidadDeNegocio).setBounds(x+ancho+20, y, ancho*3, 20);
		panel.add(lblEstablecimiento).setBounds(x, y+=25, ancho*3, 20);
		panel.add(cmbEstablecimiento).setBounds(x+ancho+20, y, ancho*3, 20);
		
		panel.add(lblReportaA).setBounds(x+ancho*5, y, ancho*2, 20);
		panel.add(lblFolioReportaA).setBounds(x+ancho*6-20, y, 60, 20);
		panel.add(txtReporteA).setBounds(x+ancho*6+20, y, ancho*5, 20);
		panel.add(btnReporteA).setBounds(x+ancho*11+20, y, 30, 20);
		
		panel.add(lblDepartamento).setBounds(x, y+=25, ancho*3, 20);		
		panel.add(cmbDepartamento).setBounds(x+ancho+20, y, ancho*3, 20);
		
		panel.add(lblEdadIn).setBounds(x+ancho*5, y, ancho+20, 20);
		panel.add(txtEdadIn).setBounds(x+ancho*6+20, y, 40, 20);
		panel.add(lblEdadFin).setBounds(x+ancho*7+10, y, 30, 20);
		panel.add(txtEdadFin).setBounds(x+ancho*7+40, y, 40, 20);
		
		panel.add(lblObjetivoDelPuesto).setBounds(x, y+=25, ancho+30, 20);
		panel.add(lblObjetivoDelPuestoNota).setBounds(x+(ancho+30), y, ancho*4, 20);
		
		panel.add(scrollObjectivoDelPuesto).setBounds(x, y+=25, ancho*12-30, 100);
		
		panel.add(lblResponsabilidadesDelPuesto).setBounds(x, y+=105, ancho*2, 20);
		panel.add(lblResponsabilidadesDelPuestoNota).setBounds(x+(ancho*2), y, ancho*4, 20);
		
		panel.add(txtResponsabilidad).setBounds(x, y+=25, ancho*5, 20);
		panel.add(btnAgregarResponsabilidad).setBounds(x+(ancho*5), y , ancho+20, 20);
		
		panel.add(new JLabel("Mover:")).setBounds(x+(ancho*7), y , ancho, 20);
		panel.add(btnSubir).setBounds(x+(ancho*7)+40, y , 30, 20);
		panel.add(btnBajar).setBounds(x+(ancho*8), y , 30, 20);
		panel.add(btnQuitarfila).setBounds(x+(ancho*10), y , ancho+50, 20);
		
		panel.add(scrollResponsabilidadesDelPuesto).setBounds(x, y+=20, ancho*12-30, 200);
		
		init_tabla(tablaResponsabilidadesDelPuesto);
//		botonesEnTablas();
		
		btnBuscarPuesto.addActionListener(opFiltroPuestos);
		btnReporteA.addActionListener(opFiltroReporteA);
		
		agregar(tablaResponsabilidadesDelPuesto);
		txtResponsabilidad.addActionListener(opAgregarResponsabilidadesDelPuesto);
		btnAgregarResponsabilidad.addActionListener(opAgregarResponsabilidadesDelPuesto);
		btnSubir.addActionListener(opSubirResponsabilidadesDelPuesto);
		btnBajar.addActionListener(opBajarResponsabilidadesDelPuesto);
		btnQuitarfila.addActionListener(opQuitar);
		
		cont.add(panel);
	}
	
	ActionListener opAgregarResponsabilidadesDelPuesto = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			agregarRegistro();
			txtResponsabilidad.requestFocus();
		}
	};
	
	public void agregarRegistro(){
		if(!txtResponsabilidad.getText().toString().trim().equals("")){
			
			boolean encontroPalabra = false;
			for(int i=0; i<tablaResponsabilidadesDelPuesto.getRowCount(); i++){
				if(txtResponsabilidad.getText().toString().trim().equals(tablaResponsabilidadesDelPuesto.getValueAt(i, 1).toString().trim())){
					encontroPalabra=true;
				}
			}
			
			if(!encontroPalabra){
				Object[] reg = new Object[3];
				
				reg[0]=modeloResponsabilidadesDelPuesto.getRowCount()+1;
				reg[1]=txtResponsabilidad.getText().toString().trim();
				reg[2]=false;
				modeloResponsabilidadesDelPuesto.addRow(reg);
			}else{
				JOptionPane.showMessageDialog(null, "La Responsabilidad Del Puesto Que Intenta Registrar Ya Se Encuentra En La Lista","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
		  	  	return;
			}
			
		}else{
			JOptionPane.showMessageDialog(null, "Es Necesario Ingresar Una Responsabilidad Para Poder Agregarla A La Lista","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
	  	  	return;
		}
	}
	
	int filaResponabilidadSeleccionada = -1;
	int columnaResponabilidadSeleccionada = -1;
	String responsabilidadSeleccionada = "";
	String responsabilidadCambiar = "";
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount() == 1){
	        		
	        		filaResponabilidadSeleccionada = tbl.getSelectedRow();
	        		columnaResponabilidadSeleccionada = tbl.getSelectedColumn();
	        		
	        		if(columnaResponabilidadSeleccionada<2){
	        			responsabilidadSeleccionada = tbl.getValueAt(filaResponabilidadSeleccionada, 1).toString().trim();
	        			tbl.setRowSelectionInterval(filaResponabilidadSeleccionada, filaResponabilidadSeleccionada);
	        		}
	        	}
	        }
        });
    }
	
	ActionListener opSubirResponsabilidadesDelPuesto = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(filaResponabilidadSeleccionada<1){
				JOptionPane.showMessageDialog(null, "No Existen Registros Hacia Arriba O No Se A Seleccionado Ningun Registro","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
		  	  	return;
			}else{
				responsabilidadCambiar=tablaResponsabilidadesDelPuesto.getValueAt(filaResponabilidadSeleccionada-1, 1).toString().trim();
				tablaResponsabilidadesDelPuesto.setValueAt(responsabilidadSeleccionada, filaResponabilidadSeleccionada-1, 1);
				tablaResponsabilidadesDelPuesto.setValueAt(responsabilidadCambiar, filaResponabilidadSeleccionada, 1);
				filaResponabilidadSeleccionada--;
				tablaResponsabilidadesDelPuesto.setRowSelectionInterval(filaResponabilidadSeleccionada, filaResponabilidadSeleccionada);
			}
		}
	};
	
	ActionListener opBajarResponsabilidadesDelPuesto = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(filaResponabilidadSeleccionada==tablaResponsabilidadesDelPuesto.getRowCount()-1){
				JOptionPane.showMessageDialog(null, "No Existen Registros Hacia Abajo O No Se A Seleccionado Ningun Registro","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
		  	  	return;
			}else{
				responsabilidadCambiar=tablaResponsabilidadesDelPuesto.getValueAt(filaResponabilidadSeleccionada+1, 1).toString().trim();
				tablaResponsabilidadesDelPuesto.setValueAt(responsabilidadSeleccionada, filaResponabilidadSeleccionada+1, 1);
				tablaResponsabilidadesDelPuesto.setValueAt(responsabilidadCambiar, filaResponabilidadSeleccionada, 1);
				filaResponabilidadSeleccionada++;
				tablaResponsabilidadesDelPuesto.setRowSelectionInterval(filaResponabilidadSeleccionada, filaResponabilidadSeleccionada);
			}
		}
	};
	
	ActionListener opQuitar = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			
			int i=0;
			for(i=0; i<tablaResponsabilidadesDelPuesto.getRowCount(); i++){
				if(Boolean.valueOf(tablaResponsabilidadesDelPuesto.getValueAt(i, 2).toString().trim())){
					modeloResponsabilidadesDelPuesto.removeRow(i);
					i=0;
				}
				if(tablaResponsabilidadesDelPuesto.getRowCount()>0){
					modeloResponsabilidadesDelPuesto.setValueAt(i+1, i, 0);
				}
			}
		}
	};
	
	ActionListener opFiltroPuestos = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Filtro_Puestos("Puesto").setVisible(true);
		}
	};
	
	ActionListener opFiltroReporteA = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Filtro_Puestos("Reporta").setVisible(true);
		}
	};

	public void init_tabla(JTable tabla){
    	tabla.getColumnModel().getColumn(0).setMinWidth(30);	
    	tabla.getColumnModel().getColumn(1).setMinWidth(800);
    	tabla.getColumnModel().getColumn(2).setMinWidth(60);	
    	
    	tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
    	tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
    	tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("CHB","centro","Arial","negrita",12));
    }
	
	//TODO inicia filtro_puestos	
		public class Cat_Filtro_Puestos extends JDialog{
			Container contf = getContentPane();
			JLayeredPane panelf = new JLayeredPane();
			Connexion con = new Connexion();
			
			Obj_tabla ObjTab =new Obj_tabla();
			int columnasp = 2,checkbox=-1;
			public void init_tablafp(){
		    	this.tablafp.getColumnModel().getColumn(0).setMinWidth(55);
		    	this.tablafp.getColumnModel().getColumn(1).setMinWidth(375);
		    	String comandof="exec cuadrantes_puestos_para_nuevos_cuadrantes ";
				String basedatos="26",pintar="si";
				ObjTab.Obj_Refrescar(tablafp,modelof, columnasp, comandof, basedatos,pintar,checkbox);
		    }
			
			@SuppressWarnings("rawtypes")
			public Class[] base (){
				Class[] types = new Class[columnasp];
				for(int i = 0; i<columnasp; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
			
			public DefaultTableModel modelof = new DefaultTableModel(null, new String[]{"Folio","Puesto"}){
				 @SuppressWarnings("rawtypes")
					Class[] types = base();
					@SuppressWarnings({ "rawtypes", "unchecked" })
					public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
					public boolean isCellEditable(int fila, int columna){return false;}
			};
			
			JTable tablafp = new JTable(modelof);
			public JScrollPane scroll_tablafp = new JScrollPane(tablafp);
		     @SuppressWarnings({ "rawtypes" })
		    private TableRowSorter trsfiltro;
		     
				int columnasp2 = 4,checkbox2=-1;
				public void init_tablafp2(){
			    	this.tablafp2.getColumnModel().getColumn(0).setMinWidth(55);
			    	this.tablafp2.getColumnModel().getColumn(1).setMinWidth(375);
			    	this.tablafp2.getColumnModel().getColumn(2).setMinWidth(55);
			    	this.tablafp2.getColumnModel().getColumn(3).setMinWidth(280);
			    	String comandof="exec cuadrantes_puestos_para_nuevos_cuadrantes ";
					String basedatos="26",pintar="si";
					ObjTab.Obj_Refrescar(tablafp2,modelof2, columnasp2, comandof, basedatos,pintar,checkbox);
			    }
				
				@SuppressWarnings("rawtypes")
				public Class[] base2 (){
					Class[] types = new Class[columnasp2];
					for(int i = 0; i<columnasp2; i++){types[i]= java.lang.Object.class;}
					 return types;
				}
				
				public DefaultTableModel modelof2 = new DefaultTableModel(null, new String[]{"Folio","Puesto","Folio C.","Cuadrante"}){
					 @SuppressWarnings("rawtypes")
						Class[] types = base2();
						@SuppressWarnings({ "rawtypes", "unchecked" })
						public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
						public boolean isCellEditable(int fila, int columna){return false;}
				};
				
				JTable tablafp2 = new JTable(modelof2);
				public JScrollPane scroll_tablafp2 = new JScrollPane(tablafp2);
			     @SuppressWarnings({ "rawtypes" })
			    private TableRowSorter trsfiltro2;
			     
			JTextField txtBuscarfp  = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
			String parametro="";
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Cat_Filtro_Puestos(String btnparametro){
				parametro=btnparametro;
				
				if(parametro.equals("Puesto")){
					this.setSize(850,500);
					trsfiltro2 = new TableRowSorter(modelof2); 
					tablafp2.setRowSorter(trsfiltro2);
					this.panelf.add(txtBuscarfp).setBounds       (10 ,20 ,820 , 20 );
					this.panelf.add(scroll_tablafp2).setBounds   (10 ,40 ,820 ,415 );
					this.init_tablafp2();
					this.agregar(tablafp2);
					this.txtBuscarfp.addKeyListener  (opFiltropuestos2 );
				}else{
					this.setSize(500,500);
					trsfiltro = new TableRowSorter(modelof); 
					tablafp.setRowSorter(trsfiltro);
					this.panelf.add(txtBuscarfp).setBounds      (10 ,20 ,470 , 20 );
					this.panelf.add(scroll_tablafp).setBounds   (10 ,40 ,470 ,415 );
					this.init_tablafp();
					this.agregar(tablafp);
					this.txtBuscarfp.addKeyListener  (opFiltropuestos );
				}
				
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setModal(true);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
				this.panelf.setBorder(BorderFactory.createTitledBorder("Selecione Un Puesto Con Doble Click"));
				this.setTitle("Filtro De Puestos");
				
				contf.add(panelf);
				
			}
			
			private void agregar(final JTable tbl) {
		        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
			        	if(e.getClickCount()==1){
			        		if(parametro.equals("Puesto")){
			        			int fila = tablafp2.getSelectedRow();
			        			if(!tablafp2.getValueAt(fila,2).equals("0")){
									JOptionPane.showMessageDialog(null, "Este Puesto Ya esta En Uso En El Cuadrante \n"+tablafp2.getValueAt(fila,3)+" \n Selecione Otro", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
									txtBuscarfp.requestFocus();
									return;
			        			}else{		        			
							    txtPuesto.setText   (tablafp2.getValueAt(fila,1)+"");
			        			}
							    
			        		}else{
			        			int fila = tablafp.getSelectedRow();
			        			txtReporteA.setText  (tablafp.getValueAt(fila,1)+"");	
			        		}
			        		
							dispose();
			        	}
			        }
		        });
		    }
			
	        private KeyListener opFiltropuestos = new KeyListener(){
				public void keyReleased(KeyEvent arg0) {
					ObjTab.Obj_Filtro(tablafp, txtBuscarfp.getText().toUpperCase(), columnasp);
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyPressed(KeyEvent arg0) {}		
			};
			
			 private KeyListener opFiltropuestos2 = new KeyListener(){
					public void keyReleased(KeyEvent arg0) {
						ObjTab.Obj_Filtro(tablafp2, txtBuscarfp.getText().toUpperCase(), columnasp2);
					}
					public void keyTyped(KeyEvent arg0) {}
					public void keyPressed(KeyEvent arg0) {}		
				};
		}//termina filtro puestos
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Descripcion_De_Puestos_y_Responsabilidades().setVisible(true);
		}catch(Exception e){	}
	}

}
