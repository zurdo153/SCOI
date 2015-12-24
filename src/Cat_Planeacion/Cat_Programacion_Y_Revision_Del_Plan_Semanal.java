package Cat_Planeacion;

import java.awt.Color;
import java.awt.Component;
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
import java.util.Vector;

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
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Generacion_Reportes;
import Conexiones_SQL.GuardarSQL;
import Obj_Administracion_del_Sistema.Obj_Usuario;

@SuppressWarnings("serial")
public class Cat_Programacion_Y_Revision_Del_Plan_Semanal extends Cat_Plan_Semanal_Base{
	JButton btnderecha          = new JButton(                  new ImageIcon("imagen/la-flecha-verde-de-la-derecha-icono-8326-32.png")  );
	JButton btnizquierda        = new JButton(                  new ImageIcon("imagen/la-flecha-verde-de-la-izquierda-icono-8326-32.png"));
	JButton btnObjetivos        = new JButton("Objetivos"      ,new ImageIcon("imagen/mas-icono-4156-32.png")                            );
	JButton btnAgregarActividad = new JButton("Actividades"    ,new ImageIcon("imagen/anadir-mas-icono-6734-32.png")                     );
	JButton btnCancelarActividad = new JButton("Cancelar"    ,new ImageIcon("imagen/anadir-mas-icono-6734-32.png")                     	 );
	JButton btnReporte_cuadros  = new JButton("Reporte Cuadros",new ImageIcon("imagen/mensual-de-la-agenda-icono-7455-32.png")           );
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	
	JTable tabla = tablaLunes;
	DefaultTableModel modeloDia = modelLunes;
	Integer dia = 0;
	
	public Cat_Programacion_Y_Revision_Del_Plan_Semanal (){
		this.setTitle("Revisi�n y Programaci�n del Plan Semanal");
		this.panel.setBorder(BorderFactory.createTitledBorder("Detalle Del Plan Semanal"));
		init();
	}
	
//	public Cat_Programacion_Y_Revision_Del_Plan_Semanal (int folio){
//		init();
//		buscar(folio);
//		txtFolio.setText(folio+"");
//	}
	
	public void init(){
		
		int y=30;
		
		if(anchoMon<=1024){
			this.panel.add(btnizquierda).setBounds(170, y, 38,38);
			this.panel.add(btnderecha).setBounds(215, y, 38, 38);
			
			this.panel.add(btnObjetivos).setBounds(700,10,150,38);
			this.panel.add(btnAgregarActividad).setBounds(855,10,150,38);
			this.panel.add(btnCancelarActividad).setBounds(1005,10,150,38);
			
			this.panel.add(btnReporte_cuadros).setBounds(450,500,180,38);
		}else{
			this.panel.add(btnizquierda).setBounds(250, y, 38,38);
			this.panel.add(btnderecha).setBounds(320, y, 38, 38);
			
			this.panel.add(btnObjetivos).setBounds(800,10,130,38);
			this.panel.add(btnAgregarActividad).setBounds(950,10,130,38);
			this.panel.add(btnCancelarActividad).setBounds(1100,10,130,38);
			
			this.panel.add(btnReporte_cuadros).setBounds(450,500,180,38);
		}
		
		cargarObjetivos();
		PintarEstatusTabla(tabla_objetivos);
		
		this.btnizquierda.addActionListener(opAtras);
		this.btnderecha.addActionListener(opAdelante);
		this.btnObjetivos.addActionListener(opAgregarObjetivo);
		this.btnAgregarActividad.addActionListener(opAgregarActividad);
		this.btnCancelarActividad.addActionListener(opCancelarActividad);
		this.btnReporte_cuadros.addActionListener(opReporteCuadros);

		cargarActividades();
		renders(tablaLunes,pLunes,scrollLunes,"Lunes");
		renders(tablaMartes,pMarte,scrollMartes,"Martes");
		renders(tablaMiercoles,pMiercoles,scrollMiercoles,"Miercoles");		
		renders(tablaJueves,pJueves,scrollJueves,"Jueves");
		renders(tablaViernes,pViernes,scrollViernes,"Viernes");
		renders(tablaSabado,pSabado,scrollSabado,"Sabado");		
		renders(tablaDomingo,pDomingo,scrollDomingo,"Domingo");
	}
	
	private void cargarActividades() {
		SeleccionarPestaniaDia(pLunes,0);
		SeleccionarPestaniaDia(pMarte,1);
		SeleccionarPestaniaDia(pMiercoles,2);
		SeleccionarPestaniaDia(pJueves,3);
		SeleccionarPestaniaDia(pViernes,4);
		SeleccionarPestaniaDia(pSabado,5);
		SeleccionarPestaniaDia(pDomingo,6);
	}
	
	 
	private void SeleccionarPestaniaDia(final JLayeredPane panelDia,final int diaa) {
		panelDia.addAncestorListener(new AncestorListener() {
			public void ancestorRemoved(AncestorEvent event) {}
			public void ancestorMoved(AncestorEvent event) {}
			public void ancestorAdded(AncestorEvent event){
				
				dia = diaa;
				
					switch(dia){
						case 0:
							tabla = tablaLunes;
							modeloDia = modelLunes;
							buscarActividadesPorDia();
							 break;
						case 1:
							tabla = tablaMartes;
							modeloDia = modelMartes;
							buscarActividadesPorDia();
							 break;
						case 2:
							tabla = tablaMiercoles;
							modeloDia = modelMiercoles;
							buscarActividadesPorDia();
							 break;
						case 3:
							tabla = tablaJueves;
							modeloDia = modelJueves;
							buscarActividadesPorDia();
							 break;
						case 4:
							tabla = tablaViernes;
							modeloDia = modelViernes;
							buscarActividadesPorDia();	
							 break;
						case 5:
							tabla = tablaSabado;
							modeloDia = modelSabado;
							buscarActividadesPorDia();
						    break;
						case 6:
							tabla = tablaDomingo;
							modeloDia = modelDomingo;
							buscarActividadesPorDia();
						    break;
						default: 
							tabla = tablaLunes;
							modeloDia = modelLunes;
							buscarActividadesPorDia();
						    break;
			     }
			}
		});
    }
	
	public void buscarActividadesPorDia(){
		modeloDia.setRowCount(0);
		String[][] actividades = new BuscarSQL().getTablaActividadesDiarias(txtPeriodo.getText().substring(0, txtPeriodo.getText().indexOf("-")).trim(), dia, "Revision");
		for(String[] act: actividades){
			modeloDia.addRow(act);
		}
	}
	
	int recorreFolio = 0;
	ActionListener opAtras = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			recorreFolio-=7;
			String[] folioPlan = new BuscarSQL().buscarFolioSemanaParaPlanSemanal(recorreFolio);
		    txtFolio.setText(folioPlan[0].toString());
		    txtPeriodo.setText(folioPlan[1].toString());
		    cargarObjetivos();
		    cargarActividades();
		}
	};	
	
	ActionListener opAdelante = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			recorreFolio+=7;
			String[] folioPlan = new BuscarSQL().buscarFolioSemanaParaPlanSemanal(recorreFolio);
		    txtFolio.setText(folioPlan[0].toString());
		    txtPeriodo.setText(folioPlan[1].toString());
		    cargarObjetivos();
		    cargarActividades();
		}
	};
	
	ActionListener opReporteCuadros = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="exec sp_reporte_de_plan_semanal_por_dia "+usuario.getFolio()+",'"+txtPeriodo.getText().toString().substring(0,10)+"'"  ;
			String reporte = "Obj_Reporte_De_Plan_Semanal_Cuadros.jrxml";
			System.out.println(comando);;
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		
		}
	};
	
	
	public void cargarObjetivos(){
		model_objetivos.setRowCount(0);
		String[][] objetivos = new BuscarSQL().buscarObjetivos_De_Plan_Semanal(txtPeriodo.getText().toString().substring(0,10));
		for(String[] dt: objetivos){
			model_objetivos.addRow(dt);
		}
	}
	
	ActionListener opAgregarActividad = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			new Cat_Actividades_De_Una_Planeacion("Cat_Programacion_Y_Revision_Del_Plan_Semanal",txtPeriodo.getText().toString().substring(0,10)).setVisible(true);
			dispose();
		}
	};
	
	ActionListener opCancelarActividad = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			int fila = tabla.getSelectedRow();
			
			if(fila<0){
				System.out.println("Seleccione La Actividad Que Desea Cancelar");
			}else{
				
				int folio_actividad = Integer.valueOf(tabla.getValueAt(fila, 0).toString().trim());
				
				if(new BuscarSQL().validar_si_el_usuario_puede_cancelar_la_actividad(folio_actividad)){
					
					if(new ActualizarSQL().Cancelar_Actividad_De_Usuario(folio_actividad)){
						System.out.println("Se Cancelo La Actividar Con Folio "+folio_actividad+" Correctamente");
						
						buscarActividadesPorDia();
						
//						 refrescar la tabla
					}
					
				}else{
					System.out.println("El Usuario No Puede Cancelar Una Actividad Que No Asigno");
				}
			}
		}
	};
	
	ActionListener opAgregarObjetivo = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			new Cat_Objectivos_De_La_Semana().setVisible(true);
		}
	};
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////TODO CATATOGO DE OBJETIVOS	
	public class Cat_Objectivos_De_La_Semana extends JFrame{
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		JTextField txtFolioObjetivo = new JTextField();
		JTextField txtPeriodoObjetivo = new JTextField();
		JButton btnDerechaObjetivo = new JButton(new ImageIcon("Iconos/right_icon&16.png"));
		JButton btnIzquierdaObjetivo = new JButton(new ImageIcon("Iconos/left_icon&16.png"));
		
		boolean editable = false;
		int cantidadDeFilas=0;
//		Object[][] filaDefault = {{"",""}};
		DefaultTableModel model_objetivos_de_la_semana = new DefaultTableModel(null, new String[]{"Folio", "Objetivos"}
				){
		     @SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
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
	        	 	case 1 : 
	        	 		if(fila<cantidadDeFilas){
//	        	 			System.out.println(fila+" if "+cantidadDeFilas);
	        	 			if(cantidadDeFilas==0){
	        	 				return true;
	        	 			}else{
	        	 				return editable;
	        	 			}
	        	 			
	        	 		}else{
//	        	 			System.out.println(fila+" else "+cantidadDeFilas);
	        	 			return true;}      	 		
	        	 } 				
	 			return false;
	 		}
			
		};
		
		JTable tabla_objetivos_de_la_semana = new JTable(model_objetivos_de_la_semana);
	    JScrollPane scroll_objetivos_de_la_semana = new JScrollPane(tabla_objetivos_de_la_semana,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    
		JButton btnAprovar = new JButton("Aplicar",new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
		JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer-icono-4321-32.png"));
		
		int fila_objetivos = 0;
		public Cat_Objectivos_De_La_Semana(){
			this.setSize(710, 320);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/reinicio-pelota-cute-icono-7443-64.png"));
			this.setTitle("Objetivos De La Semana");
			this.panel.setBorder(BorderFactory.createTitledBorder("Capture Los Objetivos Semanales"));
			
			int x=15,y=20,width=150,height=20;
			
			panel.add(new JLabel("Folio: ")).setBounds          (x    ,y		,80      ,height);
			panel.add(txtFolioObjetivo).setBounds          		(x+50 ,y		,80      ,height);
			panel.add(btnIzquierdaObjetivo).setBounds      		(x+135 ,y		,21      ,21);
			panel.add(btnDerechaObjetivo).setBounds        		(x+160 ,y		,21      ,21);
			
			panel.add(new JLabel("Periodo: ")).setBounds		(x+320,y		,130     ,height);
			panel.add(txtPeriodoObjetivo).setBounds				(x+370,y		,130     ,height);
			panel.add(scroll_objetivos_de_la_semana).setBounds  (x    ,y+=25	,680     ,height*9+7);
			panel.add(btnDeshacer).setBounds                    (x    ,y+=195	,width   ,height*2  );
			panel.add(btnAprovar).setBounds                     (x+205,y     	,width   ,height*2  );

		    String[] folioPlan = new BuscarSQL().buscarFolioSemanaParaPlanSemanal(0);
		    txtFolioObjetivo.setText(folioPlan[0].toString());
		    txtPeriodoObjetivo.setText(folioPlan[1].toString());
			
			txtFolioObjetivo.setEditable(false);
			txtPeriodoObjetivo.setEditable(false);
			btnIzquierdaObjetivo.setEnabled(false);
			
			renders_objetivos(tabla_objetivos_de_la_semana,"");
			
			tabla_objetivos_de_la_semana.addKeyListener(op_key);
			agregar(tabla_objetivos_de_la_semana);
			
			btnIzquierdaObjetivo.addActionListener(opAtrasObjetivo);
			btnDerechaObjetivo.addActionListener(opAdelanteObjetivo);
			btnAprovar.addActionListener(opAprovar);
			btnDeshacer.addActionListener(deshacer);
	        
			///guardar con control+A
	        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A,Event.CTRL_MASK),"guardar");
	             getRootPane().getActionMap().put("guardar", new AbstractAction(){
	                 public void actionPerformed(ActionEvent e)
	                 {                 	    btnAprovar.doClick();           	    }
	            });
	 	     //deshacer con escape
	 	                 getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
	 	                   getRootPane().getActionMap().put("escape", new AbstractAction(){
	 	                  public void actionPerformed(ActionEvent e)
	 	                  {                btnDeshacer.doClick();           	    }
	 	              });
	 	    this.addWindowListener(new WindowAdapter() {
	 	                     public void windowOpened( WindowEvent e ){
	 	                    	 
	 	                    	cargarObjetivosEdicion();
	 	                  }
	 	             });
	 	    cont.add(panel);
		}
		
		KeyListener op_key = new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				agregarFila();
			}
			public void keyPressed(KeyEvent e) {}
		};
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	
		        	if(e.getClickCount() == 1){
		        		
		        		
		        		
		        		if(model_objetivos_de_la_semana.isCellEditable(tabla_objetivos_de_la_semana.getSelectedRow(), 1)){
		        			
		        			fila_objetivos=tabla_objetivos_de_la_semana.getSelectedRow();
		        			
		        		}else{
		        			for(int i=0; i<tabla_objetivos_de_la_semana.getRowCount(); i++){
		        				if(model_objetivos_de_la_semana.isCellEditable(i, 1)){
		        					fila_objetivos=i;
		        					break;
		        				}
		        			}
		        		}
		        		
						tabla_objetivos_de_la_semana.getSelectionModel().setSelectionInterval(fila_objetivos, fila_objetivos);
						tabla_objetivos_de_la_semana.editCellAt(fila_objetivos, 1);
						Component aComp=tabla_objetivos_de_la_semana.getEditorComponent();
						aComp.requestFocus();
		        	}
		        }
	        });
	    }
		
		public void agregarFila(){ 
			
			int limiteObjetivos = new BuscarSQL().cantidad_de_actividades();
			
			if(tabla_objetivos_de_la_semana.getSelectedRow()+1!=limiteObjetivos){
					
					if(tabla_objetivos_de_la_semana.getSelectedRow()+1==tabla_objetivos_de_la_semana.getRowCount()){
						Object[] filaNueva = {"",""};
						model_objetivos_de_la_semana.addRow(filaNueva);
					}
					fila_objetivos+=1;
					CalcularFoliosTabla(tabla_objetivos_de_la_semana);
				}
				
			tabla_objetivos_de_la_semana.getSelectionModel().setSelectionInterval(fila_objetivos, fila_objetivos);
			tabla_objetivos_de_la_semana.editCellAt(fila_objetivos, 1);
			Component aComp=tabla_objetivos_de_la_semana.getEditorComponent();
			aComp.requestFocus();
		}
		
		int recorreFolioObjetivo = 0;
		ActionListener opAtrasObjetivo = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				recorreFolioObjetivo-=7;
				String[] folioPlan = new BuscarSQL().buscarFolioSemanaParaPlanSemanal(recorreFolioObjetivo);
			    txtFolioObjetivo.setText(folioPlan[0].toString());
			    txtPeriodoObjetivo.setText(folioPlan[1].toString());
			    btnIzquierdaObjetivo.setEnabled(false);
			    btnDerechaObjetivo.setEnabled(true);
			    
			    cargarObjetivosEdicion();
			}
		};	
		
		ActionListener opAdelanteObjetivo = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				recorreFolioObjetivo+=7;
				String[] folioPlan = new BuscarSQL().buscarFolioSemanaParaPlanSemanal(recorreFolioObjetivo);
			    txtFolioObjetivo.setText(folioPlan[0].toString());
			    txtPeriodoObjetivo.setText(folioPlan[1].toString());
			    btnIzquierdaObjetivo.setEnabled(true);
			    btnDerechaObjetivo.setEnabled(false);
			    
			    cargarObjetivosEdicion();
			}
		};
		
		public void cargarObjetivosEdicion(){
			
			if(tabla_objetivos_de_la_semana.isEditing()){ tabla_objetivos_de_la_semana.getCellEditor().stopCellEditing(); }
			model_objetivos_de_la_semana.setRowCount(0);
			
			String[][] objetivos = new BuscarSQL().buscarObjetivos_De_Plan_Semanal_Editable(Integer.valueOf(txtFolioObjetivo.getText()));
			
			String[] reg = {"",""};
			for(String[] dt: objetivos){
				model_objetivos_de_la_semana.addRow(dt);
			}
			model_objetivos_de_la_semana.addRow(reg);
			
			editable = new BuscarSQL().fila_Objetivos_de_la_semana(Integer.valueOf(txtFolioObjetivo.getText()));
			cantidadDeFilas = new BuscarSQL().cantidad_de_Objetivos_por_folio(Integer.valueOf(txtFolioObjetivo.getText()));
			
			fila_objetivos = tabla_objetivos_de_la_semana.getRowCount()-1;
			
			tabla_objetivos_de_la_semana.requestFocus();
			CalcularFoliosTabla(tabla_objetivos_de_la_semana);
		  
		    tabla_objetivos_de_la_semana.getSelectionModel().setSelectionInterval(fila_objetivos, fila_objetivos);
		    
		    tabla_objetivos_de_la_semana.editCellAt(fila_objetivos, 1);
			Component aComp=tabla_objetivos_de_la_semana.getEditorComponent();
			aComp.requestFocus();
			
		}
		
		ActionListener deshacer = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				model_objetivos_de_la_semana.setRowCount(0);
				Object[] filaNueva = {"",""};
				model_objetivos_de_la_semana.addRow(filaNueva);
				fila_objetivos = tabla_objetivos_de_la_semana.getRowCount()-1;
				
				tabla_objetivos_de_la_semana.getSelectionModel().setSelectionInterval(fila_objetivos, fila_objetivos);
				tabla_objetivos_de_la_semana.editCellAt(fila_objetivos, 1);
				Component aComp=tabla_objetivos_de_la_semana.getEditorComponent();
				aComp.requestFocus();
				
				CalcularFoliosTabla(tabla_objetivos_de_la_semana);
			}
		};
			
		ActionListener opAprovar = new ActionListener() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void actionPerformed(ActionEvent e) {

					if(tabla_objetivos_de_la_semana.isEditing()){
						tabla_objetivos_de_la_semana.getCellEditor().stopCellEditing();
					}
					
					Vector vector = new Vector();
					for(int i = 0; i<tabla_objetivos_de_la_semana.getRowCount(); i++){
						if(!tabla_objetivos_de_la_semana.getValueAt(i, 1).toString().equals("")){
							vector.addElement(tabla_objetivos_de_la_semana.getValueAt(i, 1).toString().toUpperCase().trim());
						}
					}

					if(vector.size()==0){
						JOptionPane.showMessageDialog(null, "No Se Han Agregado Objetivos", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
						return;
					}else{
						if(new GuardarSQL().Guardar_Objetivos_De_La_Semana(vector, txtFolioObjetivo.getText(), txtPeriodoObjetivo.getText())){
							cargarObjetivos();
							dispose();
						}
					}
			}
		};
	}
	
	public void CalcularFoliosTabla(final JTable tb){
		for(int i = 0; i<tb.getRowCount(); i++){
			tb.setBackground(Color.blue);
			tb.setForeground(Color.white);
			tb.setValueAt((i+1)+"", i, 0);
		}
	}
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Programacion_Y_Revision_Del_Plan_Semanal().setVisible(true);
			}catch(Exception e){
				System.err.println("Error :"+ e.getMessage());
			}
	}
}
