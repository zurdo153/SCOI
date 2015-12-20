package Cat_Planeacion;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import Conexiones_SQL.BuscarSQL;
import Obj_Principal.Componentes;
import Obj_Renders.ColorCeldas;

@SuppressWarnings("serial")
public class Cat_Alimentacion_De_Plan_Semanal extends Cat_Plan_Semanal_Base {
	
	JButton button        = new JButton("");
	
	String[] resp = {"1","2"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbRespuesta = new JComboBox(resp);
	
	public Cat_Alimentacion_De_Plan_Semanal (){
		
		activarColumna = "si";
//		
		agregarColumnas(modelLunes);
		agregarColumnas(modelMartes);
		agregarColumnas(modelMiercoles);
		agregarColumnas(modelJueves);
		agregarColumnas(modelViernes);
		agregarColumnas(modelSabado);
		agregarColumnas(modelDomingo);
		
		cargarObjetivos();
		PintarEstatusTabla(tabla_objetivos);
		
		SeleccionarPestaniaDia(pLunes,0);
		SeleccionarPestaniaDia(pMarte,1);
		SeleccionarPestaniaDia(pMiercoles,2);
		SeleccionarPestaniaDia(pJueves,3);
		SeleccionarPestaniaDia(pViernes,4);
		SeleccionarPestaniaDia(pSabado,5);
		SeleccionarPestaniaDia(pDomingo,6);
		
		button.addActionListener(opButton);
		
		renders(tablaLunes,pLunes,scrollLunes,"Lunes");
		renders(tablaMartes,pMarte,scrollMartes,"Martes");
		renders(tablaMiercoles,pMiercoles,scrollMiercoles,"Miercoles");		
		renders(tablaJueves,pJueves,scrollJueves,"Jueves");
		renders(tablaViernes,pViernes,scrollViernes,"Viernes");
		renders(tablaSabado,pSabado,scrollSabado,"Sabado");		
		renders(tablaDomingo,pDomingo,scrollDomingo,"Domingo");
	}
	
//	public void agregarColumnas(final DefaultTableModel modelo){
//		modelo.addColumn("Evidencia");
//		modelo.addColumn("Observacion");
//	}
	
	JTable tabla = tablaLunes;
	
	int filaSeleccionada = 0;
	int columnaSeleccionada = 0;
	ActionListener opButton = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
				
			filaSeleccionada = tabla.getSelectedRow();
			columnaSeleccionada = tabla.getSelectedColumn();
			
			switch(columnaSeleccionada){
				case 2:
						JFileChooser elegir = new JFileChooser();
		            	
		            	int opcion = elegir.showOpenDialog(button);
				                	
		                 //Si presionamos el boton ABRIR en pathArchivo obtenemos el path del archivo
		                 if(opcion == JFileChooser.APPROVE_OPTION){
		                    String pathArchivo = elegir.getSelectedFile().getPath(); //Obtiene path del archivo
		            	 	tabla.setValueAt(pathArchivo, filaSeleccionada, columnaSeleccionada+3);
		                 }
				break;
				
				case 3:
					new Cat_Observacion_De_Actividad(tabla.getValueAt(filaSeleccionada, columnaSeleccionada+3).toString()).setVisible(true);
				break;
				
//				case 4:
//					
//				break;
			}
			
//			if(columnaSeleccionada==2){
////            	JFileChooser elegir = new JFileChooser();
////            	
////            	int opcion = elegir.showOpenDialog(button);
////		                	
////                 //Si presionamos el boton ABRIR en pathArchivo obtenemos el path del archivo
////                 if(opcion == JFileChooser.APPROVE_OPTION){
////                    String pathArchivo = elegir.getSelectedFile().getPath(); //Obtiene path del archivo
////            	 	tabla.setValueAt(pathArchivo, filaSeleccionada, columnaSeleccionada+3);
////                 }
//			}else{
////				System.out.println(tablaViernes.getRowCount()+" "+tablaViernes.getColumnCount());
////				for(int i = 0; i<tablaViernes.getRowCount(); i++){
////					for(int j = 0; j<tablaViernes.getColumnCount(); j++){
////						System.out.println(tablaViernes.getValueAt(i, j).toString());
////					}
////				}
//				
//					new Cat_Observacion_De_Actividad(tabla.getValueAt(filaSeleccionada, columnaSeleccionada+3).toString()).setVisible(true);
//			}
		}
	};
	
	
	private void SeleccionarPestaniaDia(final JLayeredPane panelDia,final int dia) {
		panelDia.addAncestorListener(new AncestorListener() {
			public void ancestorRemoved(AncestorEvent event) {}
			public void ancestorMoved(AncestorEvent event) {}
			public void ancestorAdded(AncestorEvent event){
				
					switch(dia){
						case 0:
							tabla = tablaLunes;
							botonesEnTablas();
							buscarActividadesPorDia(modelLunes,dia);
							 break;
						case 1:
							tabla = tablaMartes;
							botonesEnTablas();
							buscarActividadesPorDia(modelMartes,dia);
							 break;
						case 2:
							tabla = tablaMiercoles;
							botonesEnTablas();
							buscarActividadesPorDia(modelMiercoles,dia);
							 break;
						case 3:
							tabla = tablaJueves;
							botonesEnTablas();
							buscarActividadesPorDia(modelJueves,dia);
							 break;
						case 4:
							tabla = tablaViernes;
							botonesEnTablas();
							buscarActividadesPorDia(modelViernes,dia);	
							 break;
						case 5:
							tabla = tablaSabado;
							botonesEnTablas();
							buscarActividadesPorDia(modelSabado,dia);
						    break;
						case 6:
							tabla = tablaDomingo;
							botonesEnTablas();
							buscarActividadesPorDia(modelDomingo,dia);
						    break;
						default: 
							tabla = tablaLunes;
							botonesEnTablas();
							buscarActividadesPorDia(modelLunes,dia);
						    break;
			     }
			}
		});
    }
	
	public void botonesEnTablas(){
//    	AGREGAR BOTON ALA TABLA 
		tabla.getColumn("Exige Evidencia").setCellRenderer(new ButtonRenderer());
		tabla.getColumn("Exige Evidencia").setCellEditor(new ButtonEditor(new JCheckBox()));
		
		tabla.getColumn("Exige Observacion").setCellRenderer(new ButtonRenderer());
		tabla.getColumn("Exige Observacion").setCellEditor(new ButtonEditor(new JCheckBox()));
		
		tabla.getColumn("Prioridad").setCellRenderer(new ButtonRenderer());
		tabla.getColumn("Prioridad").setCellEditor(new ButtonEditor(new JCheckBox()));
	}
	
	public void buscarActividadesPorDia(final DefaultTableModel modeloDia, Integer dia){
		
		TableColumn respuesta = tablaJueves.getColumnModel().getColumn(4);		
		respuesta.setCellEditor(new javax.swing.DefaultCellEditor(cmbRespuesta));
		
		
		modeloDia.setRowCount(0);
		String[][] actividades = new BuscarSQL().getTablaActividadesDiarias(txtPeriodo.getText().substring(0, txtPeriodo.getText().indexOf("-")).trim(), dia, "Alimentacion");
		for(String[] act: actividades){
			modeloDia.addRow(act);
		}
	}
	
	public void cargarObjetivos(){
		model_objetivos.setRowCount(0);
		String[][] objetivos = new BuscarSQL().buscarObjetivos_De_Plan_Semanal(Integer.valueOf(txtFolio.getText()));
		for(String[] dt: objetivos){
			model_objetivos.addRow(dt);
		}
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
	
	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
		}
		
		public Component getTableCellRendererComponent(JTable table, Object value,
		boolean isSelected, boolean hasFocus, int row, int column) {
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}
	
	class ButtonEditor extends DefaultCellEditor {
		private String label;
		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
		}
		
		public Component getTableCellEditorComponent(JTable table, Object value,
		boolean isSelected, int row, int column) {
			label = (value == null) ? "" : value.toString();
//			txtFechaPermiso.setIcon(new ImageIcon("Iconos/calendar_icon&16.png"));
			button.setText(label);
//			cmbRespuesta.setSelectedItem(label);
			
			return column==4?cmbRespuesta:button;
//			return txtFechaPermiso;
		}
		
		public Object getCellEditorValue() {
			return new String(label);
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////TODO CATATOGO DE OBSERVACION	
public class Cat_Observacion_De_Actividad extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextArea txaObservacion = new Componentes().textArea(new JTextArea(), "Onservacion De La Actividad", 500);
	JScrollPane Observasion = new JScrollPane(txaObservacion);
			
	JButton btnAprovar = new JButton("Aplicar",new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer-icono-4321-32.png"));
	
	int fila_objetivos = 0;
	public Cat_Observacion_De_Actividad(String Observacion){
	this.setSize(710, 270);
	this.setLocationRelativeTo(null);
	this.setResizable(false);
	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/reinicio-pelota-cute-icono-7443-64.png"));
	this.setTitle("Observación De La Actividad");
	this.panel.setBorder(BorderFactory.createTitledBorder("Capture La Observación De La Actividad"));
	
	txaObservacion.setLineWrap(true);
	txaObservacion.setWrapStyleWord(true);
	
	int x=15,y=20,width=150,height=20;
	
	panel.add(Observasion).setBounds  (x    ,y+=25	,680     ,height*5+17);
	panel.add(btnDeshacer).setBounds  (x    ,y+=125	,width   ,height*2  );
	panel.add(btnAprovar).setBounds   (x+205,y     	,width   ,height*2  );
	
	txaObservacion.setText(Observacion.equals("No")?"":Observacion);
	
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
	public void actionPerformed(ActionEvent e){
		btnDeshacer.doClick();
		}
	});
	this.addWindowListener(new WindowAdapter() {
	public void windowOpened( WindowEvent e ){
	
	}
	});
	cont.add(panel);
	}
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txaObservacion.setText("");
			dispose();
		}
	};
	
	ActionListener opAprovar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			tabla.setValueAt(txaObservacion.getText().equals("")?"No":txaObservacion.getText().toUpperCase(), filaSeleccionada, columnaSeleccionada+3);
			dispose();
		}
	};
}
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Alimentacion_De_Plan_Semanal().setVisible(true);
			}catch(Exception e){
				System.err.println("Error :"+ e.getMessage());
			}
	}
}
