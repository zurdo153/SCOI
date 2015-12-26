package Cat_Planeacion;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Vector;

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
import javax.swing.JOptionPane;
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

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;
import Obj_Principal.Componentes;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Alimentacion_De_Plan_Semanal extends Cat_Plan_Semanal_Base{
	
	JButton button        = new JButton("");
	@SuppressWarnings("rawtypes")
	JComboBox cmbRespuesta = new JComboBox();
	
	JButton btnGuardarObjetivos = new JButton("Guardar Objetivos", new ImageIcon("imagen/guardar-documento-icono-7840-32.png"));
	JButton btnGuardarActividades = new JButton("Guardar Actividades", new ImageIcon("imagen/guardar-documento-icono-7840-32.png"));
	JButton btnAgregarActividad = new JButton("Actividades Extras"    ,new ImageIcon("imagen/boton-anadir-mas-azul-icono-7396-32.png")  );
	
	JTable tabla = tablaLunes;
	
	public Cat_Alimentacion_De_Plan_Semanal(){
		this.setTitle("Alimentacion De Plan Semanal");
		this.panel.setBorder(BorderFactory.createTitledBorder("Seleccione El Dia Que Desea Contestar Del Plan Semanal"));
		
		activarColumna = "si";
		
		this.panel.add(btnGuardarObjetivos).setBounds(30, 490, 190, 38);
		this.panel.add(btnAgregarActividad).setBounds(450, 490, 190, 38);
		this.panel.add(btnGuardarActividades).setBounds(1100, 490, 190, 38);
		
		agregarColumnas(tablaLunes,modelLunes);
		agregarColumnas(tablaMartes,modelMartes);
		agregarColumnas(tablaMiercoles,modelMiercoles);
		agregarColumnas(tablaJueves,modelJueves);
		agregarColumnas(tablaViernes,modelViernes);
		agregarColumnas(tablaSabado,modelSabado);
		agregarColumnas(tablaDomingo,modelDomingo);
		
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
		btnGuardarActividades.addActionListener(opGuardarActividades);
		btnGuardarObjetivos.addActionListener(opGuardarObjetivos);
		btnAgregarActividad.addActionListener(opActividadesExtras);
		
		renders(tablaLunes,pLunes,scrollLunes,"Lunes");
		renders(tablaMartes,pMarte,scrollMartes,"Martes");
		renders(tablaMiercoles,pMiercoles,scrollMiercoles,"Miercoles");		
		renders(tablaJueves,pJueves,scrollJueves,"Jueves");
		renders(tablaViernes,pViernes,scrollViernes,"Viernes");
		renders(tablaSabado,pSabado,scrollSabado,"Sabado");		
		renders(tablaDomingo,pDomingo,scrollDomingo,"Domingo");
	}
	
	int filaSeleccionada = 0;
	int columnaSeleccionada = 0;
	ActionListener opButton = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
				
			filaSeleccionada = tabla.getSelectedRow();
			columnaSeleccionada = tabla.getSelectedColumn();
			
//			obtiene en nombre de la columnas seleccionada
//			System.out.println(tabla.getTableHeader().getColumnModel().getColumn(columnaSeleccionada).getHeaderValue());
			
			switch(tabla.getTableHeader().getColumnModel().getColumn(columnaSeleccionada).getHeaderValue().toString()){
				case "Exige Evidencia":
						JFileChooser elegir = new JFileChooser();
		            	
		            	int opcion = elegir.showOpenDialog(button);
				                	
		                 //Si presionamos el boton ABRIR en pathArchivo obtenemos el path del archivo
		                 if(opcion == JFileChooser.APPROVE_OPTION){
		                    String pathArchivo = elegir.getSelectedFile().getPath(); //Obtiene path del archivo
		                    
		            	 	tabla.setValueAt(pathArchivo, filaSeleccionada, columnaSeleccionada+2);
		                 }
				break;
				
				case "Exige Observacion":
						new Cat_Observacion_De_Actividad(tabla.getValueAt(filaSeleccionada, columnaSeleccionada+2).toString()).setVisible(true);
				break;
				
			}
		}
	};
	
	ActionListener opGuardarObjetivos = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			
			if(new ActualizarSQL().Actualizar_Objetivos_De_Plan_Semanal(tabla_objetivos)){
				JOptionPane.showMessageDialog(null, "Los Objetivos Se Guardaron Correctamente","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
				return;
			}
		}
	};
	
	ActionListener opActividadesExtras = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
               new Cat_Actividades_De_Una_Planeacion("Cat_Alimentacion_De_Plan_Semanal",txtPeriodo.getText().toString().substring(0,10)).setVisible(true); 	
               dispose();
		}
	};
	
	
	ActionListener opGuardarActividades = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			
			String[][] tb_actividades_con_respuesta = tabla_actividades();
			
			if(tb_actividades_con_respuesta.length!=0){
				
					for(int i=0; i<tb_actividades_con_respuesta.length; i++){
					
						if(tb_actividades_con_respuesta[i][2].toString().equals("Si") && tb_actividades_con_respuesta[i][4].toString().equals("")){
							JOptionPane.showMessageDialog(null, "La Actividad Con Folio [ "+tb_actividades_con_respuesta[i][0].toString()+" ] Requiere Evidencia", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
							return;
						}
						if(tb_actividades_con_respuesta[i][3].toString().equals("Si") && tb_actividades_con_respuesta[i][5].toString().equals("")){
							JOptionPane.showMessageDialog(null, "La Actividad Con Folio [ "+tb_actividades_con_respuesta[i][0].toString()+" ] Requiere Observación", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
							return;
						}else{
							
							if(i==tb_actividades_con_respuesta.length-1){
								
//								se obtiene el dia seleccionado el la pestaña
								String dia = pLunes.isShowing()==true?"Lunes"
												 : (pMarte.isShowing()==true?"Martes"
														 : (pMarte.isShowing()==true?"Martes"
																 : (pMiercoles.isShowing()==true?"Miercoles"
																		 : (pJueves.isShowing()==true?"Jueves"
																				 : (pViernes.isShowing()==true?"Viernes"
																						 : (pSabado.isShowing()==true?"Sabado"
																								 : "Domingo"))))));
								
								if(JOptionPane.showConfirmDialog(null, "Usted Esta Intentando Guardar Las Actividades Del Dia \n>>>>>>>> "+dia+" >>>>>>>> \nDesea Continuar?","Aviso",0,1,new ImageIcon("imagen/usuario-icono-noes_usuario9131-64.png")) == 0){	
									if(new GuardarSQL().Guardar_Actividades_Con_Respuesta(tb_actividades_con_respuesta, dia)){
											JOptionPane.showMessageDialog(null, "Las Actividades Contestadas Se Guardaron Correctamente","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
											return;
									}
								}
							}
						}
					}
			}else{
				JOptionPane.showMessageDialog(null, "No Se Han Respondido Actividades", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String[][] tabla_actividades(){
		int cantidad_de_columnas_matriz=6;
		Vector vector = new Vector();
		for(int i=0; i<tabla.getRowCount(); i++){
			 if(!tabla.getValueAt(i, 2).toString().equals("RESPUESTA")){
				  vector.add(tabla.getValueAt(i,0).toString().trim());
				  vector.add(tabla.getValueAt(i,2).toString().trim());
				  
				  vector.add(tabla.getValueAt(i,3).toString().trim());
				  vector.add(tabla.getValueAt(i,4).toString().trim());
				  
				  vector.add(tabla.getValueAt(i,5).toString().trim());
				  vector.add(tabla.getValueAt(i,6).toString().trim());
		     }
		}
			String[][] matriz = new String[vector.size()/cantidad_de_columnas_matriz][cantidad_de_columnas_matriz];
			 int i=0,j =0,columnafor=0;
			while(i<vector.size()){
				columnafor=0;
			      for(int f =0;  f<cantidad_de_columnas_matriz;  f++,columnafor++,i++  ){	
			  matriz[j][columnafor] = vector.get(i).toString();
			  }
			  j++;
			}
		return matriz;
	}
	
	private void SeleccionarPestaniaDia(final JLayeredPane panelDia,final int dia) {
		panelDia.addAncestorListener(new AncestorListener() {
			public void ancestorRemoved(AncestorEvent event) {}
			public void ancestorMoved(AncestorEvent event) {}
			public void ancestorAdded(AncestorEvent event){
				
					switch(dia){
						case 0:
							tabla = tablaLunes;
							buscarActividadesPorDia(modelLunes,dia);
							botonesEnTablas();
							 break;
						case 1:
							tabla = tablaMartes;
							buscarActividadesPorDia(modelMartes,dia);
							botonesEnTablas();
							 break;
						case 2:
							tabla = tablaMiercoles;
							buscarActividadesPorDia(modelMiercoles,dia);
							botonesEnTablas();
							 break;
						case 3:
							tabla = tablaJueves;
							buscarActividadesPorDia(modelJueves,dia);
							botonesEnTablas();
							 break;
						case 4:
							tabla = tablaViernes;
							buscarActividadesPorDia(modelViernes,dia);	
							botonesEnTablas();
							 break;
						case 5:
							tabla = tablaSabado;
							buscarActividadesPorDia(modelSabado,dia);
							botonesEnTablas();
						    break;
						case 6:
							tabla = tablaDomingo;
							buscarActividadesPorDia(modelDomingo,dia);
							botonesEnTablas();
						    break;
						default: 
							tabla = tablaLunes;
							buscarActividadesPorDia(modelLunes,dia);
							botonesEnTablas();
						    break;
			     }
			}
		});
    }
	
	public void agregarColumnas(final JTable tb,final DefaultTableModel modelo){
		modelo.addColumn("E");
		modelo.addColumn("O");
		
//		tabla.moveColumn(4, 2);
		tb.getColumnModel().getColumn(2).setHeaderValue("Respuestas");
		tb.getColumnModel().getColumn(3).setHeaderValue("Exige Evidencia");
		tb.getColumnModel().getColumn(4).setHeaderValue("Exige Observacion");
		
		tb.getColumnModel().getColumn(5).setMaxWidth(25);
		tb.getColumnModel().getColumn(5).setMinWidth(25);
		tb.getColumnModel().getColumn(6).setMaxWidth(25);
		tb.getColumnModel().getColumn(6).setMinWidth(25);
		
		tb.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
		tb.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
	}
	
	public void botonesEnTablas(){
//    	AGREGAR BOTON A LA TABLA ACTIVIDADES
		tabla.getColumn("Exige Evidencia").setCellRenderer(new ButtonRenderer());
		tabla.getColumn("Exige Evidencia").setCellEditor(new ButtonEditor(new JCheckBox()));
		
		tabla.getColumn("Exige Observacion").setCellRenderer(new ButtonRenderer());
		tabla.getColumn("Exige Observacion").setCellEditor(new ButtonEditor(new JCheckBox()));
//    	AGREGAR COMBOBOX A LA TABLA ACTIVIDADES	
		tabla.getColumn("Respuestas").setCellEditor(new MyComboEditor());
		
//    	AGREGAR COMBOBOX A LA TABLA OBJETIVOS
		tabla_objetivos.getColumn("Status").setCellEditor(new MyComboEditor2());
	}
	
	public void buscarActividadesPorDia(final DefaultTableModel modeloDia, Integer dia){
		
		TableColumn respuesta = tabla.getColumnModel().getColumn(4);		
		respuesta.setCellEditor(new javax.swing.DefaultCellEditor(cmbRespuesta));
		
		modeloDia.setRowCount(0);
		String[][] actividades = new BuscarSQL().getTablaActividadesDiarias(txtPeriodo.getText().substring(0, txtPeriodo.getText().indexOf("-")).trim(), dia, "Alimentacion");
		for(String[] act: actividades){
			modeloDia.addRow(act);
		}
	}
	
	public void cargarObjetivos(){
		model_objetivos.setRowCount(0);
		String[][] objetivos = new BuscarSQL().buscarObjetivos_De_Plan_Semanal(txtPeriodo.getText().toString().substring(0,10));
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
	
//	combobox en tabla actividades--------------------------------------------------------------------------------------
	private class MyComboEditor extends DefaultCellEditor{
        @SuppressWarnings("rawtypes")
		public MyComboEditor(){
        	super(new JComboBox());
        }
         
        @SuppressWarnings({ "rawtypes", "unchecked" })
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        	JComboBox combo = (JComboBox)getComponent();
        	
        	combo.removeAllItems();
			try {
				//////TODO	se obtiene el dia seleccionado el la pestaña
				int dia = pLunes.isShowing()==true?0
								 : (pMarte.isShowing()==true?1
												 : (pMiercoles.isShowing()==true?2
														 : (pJueves.isShowing()==true?3
																 : (pViernes.isShowing()==true?4
																		 : (pSabado.isShowing()==true?5
																				  : 6)))));
				
				String[] tipo_de_respuestas = new Cargar_Combo().ComboTiposDeRespuesta_Plan_Semanal(Integer.valueOf(table.getValueAt(row, 0).toString().trim()),dia);
				
				
				for(int i=0; i<tipo_de_respuestas.length; i++)
	            	combo.addItem(tipo_de_respuestas[i]);
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
            return combo;          
        }
    }
	
//	combobox en tabla objetivos --------------------------------------------------------------------------------------
	private class MyComboEditor2 extends DefaultCellEditor{
        @SuppressWarnings("rawtypes")
		public MyComboEditor2(){
        	super(new JComboBox());
        }
         
        @SuppressWarnings({ "rawtypes", "unchecked" })
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        	JComboBox combo = (JComboBox)getComponent();
        	
        	combo.removeAllItems();
			try {
				
				String[] tipo_de_respuestas = {"PLANEADO","PROCESO","RESUELTO"};
				for(int i=0; i<tipo_de_respuestas.length; i++)
	            	combo.addItem(tipo_de_respuestas[i]);
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
            return combo;          
        }
    }
	
//	boton en tabla --------------------------------------------------------------------------------------
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
			button.setText(label);
			
			return button;
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
			public void actionPerformed(ActionEvent e){
				btnAprovar.doClick();           	    
			}
		});
		
		//deshacer con escape
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
		getRootPane().getActionMap().put("escape", new AbstractAction(){
			public void actionPerformed(ActionEvent e){
				btnDeshacer.doClick();
			}
		});
		
//		this.addWindowListener(new WindowAdapter() {
//			public void windowOpened( WindowEvent e ){
//		
//			}
//		});
	
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
			tabla.setValueAt(txaObservacion.getText().equals("")?"No":txaObservacion.getText().toUpperCase(), filaSeleccionada, columnaSeleccionada+2);
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
