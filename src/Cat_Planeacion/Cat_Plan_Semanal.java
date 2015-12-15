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
import java.sql.SQLException;
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

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;
import Obj_Evaluaciones.Obj_Cuadrante;
import Obj_Renders.ColorCeldas;

@SuppressWarnings("serial")
public class Cat_Plan_Semanal extends Cat_Plan_Semanal_Base {
	
//	boolean status_update = false;
	
	JButton btnderecha = new JButton(new ImageIcon("Iconos/right_icon&16.png"));
	JButton btnizquierda = new JButton(new ImageIcon("Iconos/left_icon&16.png"));
	
	JButton btnObjetivos = new JButton("Objetivo Semanal");
	JButton btnAgregarActividad = new JButton("Agregar Actividad");
	
	
	public Cat_Plan_Semanal (){
		init();
	}
	
//	public Cat_Plan_Semanal (int folio){
//		init();
//		buscar(folio);
//		txtFolio.setText(folio+"");
//	}
	
	public void init(){
		int y=30;
		
		if(anchoMon<=1024){
			this.panel.add(btnizquierda).setBounds(180, y, 21,21);
			this.panel.add(btnderecha).setBounds(215, y, 21, 21);
			this.panel.add(btnObjetivos).setBounds(835,20,120,20);
			this.panel.add(btnAgregarActividad).setBounds(955,20,120,20);
		}else{
			this.panel.add(btnizquierda).setBounds(265, y, 21,21);
			this.panel.add(btnderecha).setBounds(295, y, 21, 21);
			this.panel.add(btnObjetivos).setBounds(930,20,120,20);
			this.panel.add(btnAgregarActividad).setBounds(1060,20,120,20);
		}
		
		cargarObjetivos();
		PintarEstatusTabla(tabla_objetivos);
		
		buscarActividadesPorDia(modelLunes);
		
		this.btnizquierda.addActionListener(opAtras);
		this.btnderecha.addActionListener(opAdelante);
		this.btnObjetivos.addActionListener(opAgregarObjetivo);
		this.btnAgregarActividad.addActionListener(opAgregarActividad);
		
		SeleccionarPestaniaDia(pLunes,0);
		SeleccionarPestaniaDia(pMarte,1);
		SeleccionarPestaniaDia(pMiercoles,2);
		SeleccionarPestaniaDia(pJueves,3);
		SeleccionarPestaniaDia(pViernes,4);
		SeleccionarPestaniaDia(pSabado,5);
		SeleccionarPestaniaDia(pDomingo,6);
	}
	
	private void SeleccionarPestaniaDia(final JLayeredPane panelDia,int dia) {
		diaDeLaSemana = dia;
		panelDia.addAncestorListener(new AncestorListener() {
			public void ancestorRemoved(AncestorEvent event) {}
			public void ancestorMoved(AncestorEvent event) {}
			public void ancestorAdded(AncestorEvent event) {
				buscarActividadesPorDia(modelLunes);
			}
		});
    }
	
	int recorreFolio = 0;
	ActionListener opAtras = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			recorreFolio-=7;
			String[] folioPlan = new BuscarSQL().buscarFolioSemanaParaPlanSemanal(recorreFolio);
		    txtFolio.setText(folioPlan[0].toString());
		    txtPeriodo.setText(folioPlan[1].toString());
		    
		    cargarObjetivos();
//		    buscarActividadesPorDia();
		}
	};	
	
	int diaDeLaSemana = 0;
	ActionListener opAdelante = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			recorreFolio+=7;
			String[] folioPlan = new BuscarSQL().buscarFolioSemanaParaPlanSemanal(recorreFolio);
		    txtFolio.setText(folioPlan[0].toString());
		    txtPeriodo.setText(folioPlan[1].toString());
		    
		    cargarObjetivos();
//		    buscarActividadesPorDia();
		}
	};
	
	public void cargarObjetivos(){
		model_objetivos.setRowCount(0);
		String[][] objetivos = new BuscarSQL().buscarObjetivos_De_Plan_Semanal(Integer.valueOf(txtFolio.getText()));
		for(String[] dt: objetivos){
			model_objetivos.addRow(dt);
		}
	}
	
	public void buscarActividadesPorDia(final DefaultTableModel modeloDia){
		tabla_limpiar();
		String[][] actividades = new BuscarSQL().getTablaActividadesDiarias(txtPeriodo.getText().substring(0, txtPeriodo.getText().indexOf("-")).trim(), diaDeLaSemana);
							
		for(String[] act: actividades){
			modeloDia.addRow(act);
		}
	}
	
	ActionListener opAgregarActividad = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			new Cat_Actividades_De_Una_Planeacion().setVisible(true);
		}
	};
	
	ActionListener opAgregarObjetivo = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			new Cat_Objectivos_De_La_Semana().setVisible(true);
		}
	};	
	
	
	public void tabla_limpiar(){		
		modelLunes.setRowCount(0);
		modelMartes.setRowCount(0);
		modelMiercoles.setRowCount(0);
		modelJueves.setRowCount(0);
		modelViernes.setRowCount(0);
		modelSabado.setRowCount(0);
		modelDomingo.setRowCount(0);
	}
	
	public String[][] DiasTablas(){
		
		int filas = tablaDomingo.getRowCount()+tablaLunes.getRowCount()+tablaMartes.getRowCount()+tablaMiercoles.getRowCount()+tablaJueves.getRowCount()+tablaViernes.getRowCount()+tablaSabado.getRowCount();
		
		String[][] tablas = new String[filas][7];
		
		int renglonesdomingo = tablaDomingo.getRowCount();
		int rengloneslunes = tablaLunes.getRowCount();
		int renglonesMartes = tablaMartes.getRowCount();
		int renglonesMiercoles = tablaMiercoles.getRowCount();
		int renglonesJueves = tablaJueves.getRowCount();
		int renglonesViernes = tablaViernes.getRowCount();
		int renglonesSabado = tablaSabado.getRowCount();
		
		int fila=0;
		int i=0;
		
		while(renglonesdomingo > 0){
				tablas[i][0] = modelDomingo.getValueAt(fila, 0)+"";
				tablas[i][1] = modelDomingo.getValueAt(fila, 1)+"";
				tablas[i][2] = modelDomingo.getValueAt(fila, 2)+"";
				tablas[i][3] = modelDomingo.getValueAt(fila, 3)+"";
				tablas[i][4] = modelDomingo.getValueAt(fila, 4)+"";
				tablas[i][5] = modelDomingo.getValueAt(fila, 5)+"";
				tablas[i][6] = "Domingo";
				i+=1;
				fila+=1;
			renglonesdomingo--;
		}
		fila=0;
		while(rengloneslunes > 0){
				tablas[i][0] = modelLunes.getValueAt(fila, 0)+"";
				tablas[i][1] = modelLunes.getValueAt(fila, 1)+"";
				tablas[i][2] = modelLunes.getValueAt(fila, 2)+"";
				tablas[i][3] = modelLunes.getValueAt(fila, 3)+"";
				tablas[i][4] = modelLunes.getValueAt(fila, 4)+"";
				tablas[i][5] = modelLunes.getValueAt(fila, 5)+"";
				tablas[i][6] = "Lunes";
				i+=1;
				fila+=1;
				rengloneslunes--;
		}
		
		fila=0;
		while(renglonesMartes > 0){
				tablas[i][0] = modelMartes.getValueAt(fila, 0)+"";
				tablas[i][1] = modelMartes.getValueAt(fila, 1)+"";
				tablas[i][2] = modelMartes.getValueAt(fila, 2)+"";
				tablas[i][3] = modelMartes.getValueAt(fila, 3)+"";
				tablas[i][4] = modelMartes.getValueAt(fila, 4)+"";
				tablas[i][5] = modelMartes.getValueAt(fila, 5)+"";
				tablas[i][6] = "Martes";
				i+=1;
				fila+=1;
				renglonesMartes--;
		}
		
		fila=0;
		while(renglonesMiercoles > 0){
				tablas[i][0] = modelMiercoles.getValueAt(fila, 0)+"";
				tablas[i][1] = modelMiercoles.getValueAt(fila, 1)+"";
				tablas[i][2] = modelMiercoles.getValueAt(fila, 2)+"";
				tablas[i][3] = modelMiercoles.getValueAt(fila, 3)+"";
				tablas[i][4] = modelMiercoles.getValueAt(fila, 4)+"";
				tablas[i][5] = modelMiercoles.getValueAt(fila, 5)+"";
				tablas[i][6] = "Mi�rcoles";
				i+=1;
				fila+=1;
				renglonesMiercoles--;
		}
		
		fila=0;
		while(renglonesJueves > 0){
				tablas[i][0] = modelJueves.getValueAt(fila, 0)+"";
				tablas[i][1] = modelJueves.getValueAt(fila, 1)+"";
				tablas[i][2] = modelJueves.getValueAt(fila, 2)+"";
				tablas[i][3] = modelJueves.getValueAt(fila, 3)+"";
				tablas[i][4] = modelJueves.getValueAt(fila, 4)+"";
				tablas[i][5] = modelJueves.getValueAt(fila, 5)+"";
				tablas[i][6] = "Jueves";
				i+=1;
				fila+=1;
				renglonesJueves--;
		}
		
		fila=0;
		while(renglonesViernes > 0){
				tablas[i][0] = modelViernes.getValueAt(fila, 0)+"";
				tablas[i][1] = modelViernes.getValueAt(fila, 1)+"";
				tablas[i][2] = modelViernes.getValueAt(fila, 2)+"";
				tablas[i][3] = modelViernes.getValueAt(fila, 3)+"";
				tablas[i][4] = modelViernes.getValueAt(fila, 4)+"";
				tablas[i][5] = modelViernes.getValueAt(fila, 5)+"";
				tablas[i][6] = "Viernes";
				i+=1;
				fila+=1;
				renglonesViernes--;
		}
		
		fila=0;
		while(renglonesSabado > 0){
				tablas[i][0] = modelSabado.getValueAt(fila, 0)+"";
				tablas[i][1] = modelSabado.getValueAt(fila, 1)+"";
				tablas[i][2] = modelSabado.getValueAt(fila, 2)+"";
				tablas[i][3] = modelSabado.getValueAt(fila, 3)+"";
				tablas[i][4] = modelSabado.getValueAt(fila, 4)+"";
				tablas[i][5] = modelSabado.getValueAt(fila, 5)+"";
				tablas[i][6] = "S�bado";
				i+=1;
				fila+=1;
				renglonesSabado--;
		}
		
		return tablas;
	}
	
	public void limpiar(){
		txtFolio.setEditable(true);
		txtFolio.requestFocus();
		txtFolio.setText("");
		txtPeriodo.setText("");
		txtEstablecimiento.setText("");
		
		model_objetivos.setRowCount(0);
		
		btnAgregarActividad.setEnabled(false);
		
		tablaDomingo.setEnabled(false);
		tablaLunes.setEnabled(false);
		tablaMartes.setEnabled(false);
		tablaMiercoles.setEnabled(false);
		tablaJueves.setEnabled(false);
		tablaViernes.setEnabled(false);
		tablaSabado.setEnabled(false);
		
		tabla_limpiar();
		
	}
	
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
	        	 		if(fila<cantidadDeFilas){return editable;}else{return true;}      	 		
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
			
			panel.add(new JLabel("Folio: ")).setBounds          (x    ,y		,80      ,20);
			panel.add(txtFolioObjetivo).setBounds          		(x+50 ,y		,80      ,20);
			panel.add(btnIzquierdaObjetivo).setBounds      		(x+135 ,y		,21      ,21);
			panel.add(btnDerechaObjetivo).setBounds        		(x+160 ,y		,21      ,21);
			
			panel.add(new JLabel("Periodo: ")).setBounds		(x+320,y		,130     ,20);
			panel.add(txtPeriodoObjetivo).setBounds				(x+370,y		,130     ,20);
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
								
								tabla_objetivos_de_la_semana.getSelectionModel().setSelectionInterval(fila_objetivos, fila_objetivos);
								tabla_objetivos_de_la_semana.editCellAt(fila_objetivos, 1);
								Component aComp=tabla_objetivos_de_la_semana.getEditorComponent();
								aComp.requestFocus();
		      			   
		      		   }
		        		 
//						fila_objetivos=tabla_objetivos_de_la_semana.getSelectedRow();
//						
//						tabla_objetivos_de_la_semana.getSelectionModel().setSelectionInterval(fila_objetivos, fila_objetivos);
//						tabla_objetivos_de_la_semana.editCellAt(fila_objetivos, 1);
//						Component aComp=tabla_objetivos_de_la_semana.getEditorComponent();
//						aComp.requestFocus();
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
			if(objetivos.length==0){
				model_objetivos_de_la_semana.addRow(reg);
				fila_objetivos=0;
			}else{
				for(String[] dt: objetivos){
					model_objetivos_de_la_semana.addRow(dt);
				}
				model_objetivos_de_la_semana.addRow(reg);
				cantidadDeFilas = new BuscarSQL().cantidad_de_Objetivos_por_folio(Integer.valueOf(txtFolioObjetivo.getText()));
				editable = new BuscarSQL().fila_Objetivos_de_la_semana(Integer.valueOf(txtFolioObjetivo.getText()));
			}
			
			
			tabla_objetivos_de_la_semana.requestFocus();
			CalcularFoliosTabla(tabla_objetivos_de_la_semana);
			
		    fila_objetivos = tabla_objetivos_de_la_semana.getRowCount()==1 ? 0 : tabla_objetivos_de_la_semana.getRowCount()-1;
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
				fila_objetivos=0;
				
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
	
	public void PintarEstatusTabla(final JTable tb){
		//se crea instancia a clase FormatoTable y se indica columna patron
        ColorCeldas ft = new ColorCeldas(1);
        tb.setDefaultRenderer (Object.class, ft );
	}
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Plan_Semanal().setVisible(true);
			}catch(Exception e){
				System.err.println("Error :"+ e.getMessage());
			}
	}
}
