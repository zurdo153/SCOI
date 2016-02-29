package Cat_Planeacion;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Event;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
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
import Conexiones_SQL.Generacion_Reportes;
import Conexiones_SQL.GuardarSQL;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Alimentacion_De_Plan_Semanal extends Cat_Plan_Semanal_Base{
	
	JButton button        = new JButton("");
	@SuppressWarnings("rawtypes")
	JComboBox cmbRespuesta = new JComboBox();
	
	JButton btnGuardarObjetivos = new JButton("Guardar Objetivos", new ImageIcon("imagen/guardar-documento-icono-7840-32.png"));
	JButton btnGuardarActividades = new JButton("Guardar Actividades", new ImageIcon("imagen/guardar-documento-icono-7840-32.png"));
	JButton btnAgregarActividad = new JButton("Actividades Extras"    ,new ImageIcon("imagen/boton-anadir-mas-azul-icono-7396-32.png")  );
	

	JButton btnReporte_cntestad = new JButton("Plan Contestado "  ,new ImageIcon("imagen/mensual-de-la-agenda-contestado-7455-32.png")      );
	JButton btnReporte_lista  	= new JButton("Actividades Con Respuesta");
	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	
	public Cat_Alimentacion_De_Plan_Semanal(){
		this.setTitle("Alimentacion De Plan Semanal");
		this.panel.setBorder(BorderFactory.createTitledBorder("Seleccione El Dia Que Desea Contestar Del Plan Semanal"));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Planeacion_alimentacion.png"));
		ImageIcon tmpIconDefault = new ImageIcon(System.getProperty("user.dir")+"/imagen/checklistbtn.png");
	    Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
	    btnReporte_lista.setIcon(iconoDefault);
		
		activarColumna = "si";
		int y=490;
		if(anchoMon<=1024){
			this.panel.add(btnGuardarObjetivos).setBounds(15, 490, 190, 38);
			this.panel.add(btnAgregarActividad).setBounds(410, 490, 190, 38);
			this.panel.add(btnGuardarActividades).setBounds(815, 490, 190, 38);
		}else{
			
			this.panel.add(btnGuardarObjetivos).setBounds(30, 490, 190, 38);
			this.panel.add(btnAgregarActividad).setBounds(450, 490, 190, 38);
			this.panel.add(btnGuardarActividades).setBounds(1100, 490, 190, 38);
			
			this.panel.add(btnReporte_lista).setBounds(660,y,200,38);
			this.panel.add(btnReporte_cntestad).setBounds(875,y,150,38);
			
		}
		agregarColumnas(tablaLunes,modelLunes);
		agregarColumnas(tablaMartes,modelMartes);
		agregarColumnas(tablaMiercoles,modelMiercoles);
		agregarColumnas(tablaJueves,modelJueves);
		agregarColumnas(tablaViernes,modelViernes);
		agregarColumnas(tablaSabado,modelSabado);
		agregarColumnas(tablaDomingo,modelDomingo);
		
		cargarObjetivos();
		PintarEstatusTabla(tabla_objetivos,"Objetivos_De_La_Semana",0);//tipo_de_tabla , columnas 
		
		button.addActionListener(opButton);
		btnGuardarActividades.addActionListener(opGuardarActividades);
		btnGuardarObjetivos.addActionListener(opGuardarObjetivos);
		btnAgregarActividad.addActionListener(opActividadesExtras);
		
		inabilitarPestanas();
		cargarActividades();
		renders(tablaLunes,pLunes,scrollLunes,"Lunes");
		renders(tablaMartes,pMarte,scrollMartes,"Martes");
		renders(tablaMiercoles,pMiercoles,scrollMiercoles,"Miercoles");		
		renders(tablaJueves,pJueves,scrollJueves,"Jueves");
		renders(tablaViernes,pViernes,scrollViernes,"Viernes");
		renders(tablaSabado,pSabado,scrollSabado,"Sabado");		
		renders(tablaDomingo,pDomingo,scrollDomingo,"Domingo");
		
		this.btnReporte_lista.addActionListener(opReporteLista);
		this.btnReporte_cntestad.addActionListener(opReporteCuadroscontestado);
		
		tablaJueves.moveColumn(2, 0);
	}
	
	public void inabilitarPestanas(){
		int  dia_de_la_semana=0;
		int  dia_de_descanso=0;
        try {
			  dia_de_la_semana= new BuscarSQL().dias_de_la_semana();
			  dia_de_descanso= new BuscarSQL().dia_descanso_colaborador(usuario.getFolio());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
        
       if(dia_de_la_semana==0){ 
			pestanas.setEnabledAt(0, true);
			pestanas.setEnabledAt(1, false);
			pestanas.setEnabledAt(2, false);
			pestanas.setEnabledAt(3, false);
			pestanas.setEnabledAt(4, false);
			pestanas.setEnabledAt(5, false);
			pestanas.setEnabledAt(6, false);
           };
       
       if(dia_de_la_semana==1){
			pestanas.setEnabledAt(0, true);
			pestanas.setEnabledAt(1, true);
			pestanas.setEnabledAt(2, false);
			pestanas.setEnabledAt(3, false);
			pestanas.setEnabledAt(4, false);
			pestanas.setEnabledAt(5, false);
			pestanas.setEnabledAt(6, false);
	       };
       
	   if(dia_de_la_semana==2 && dia_de_descanso==1){
			pestanas.setEnabledAt(0, true);
			pestanas.setEnabledAt(1, true);
			pestanas.setEnabledAt(2, true);
			pestanas.setEnabledAt(3, false);
			pestanas.setEnabledAt(4, false);
			pestanas.setEnabledAt(5, false);
			pestanas.setEnabledAt(6, false);
    	   };
	   
	   if(dia_de_la_semana==2 && !(dia_de_descanso==1)){
			pestanas.setEnabledAt(0, false);
			pestanas.setEnabledAt(1, true);
			pestanas.setEnabledAt(2, true);
			pestanas.setEnabledAt(3, false);
			pestanas.setEnabledAt(4, false);
			pestanas.setEnabledAt(5, false);
			pestanas.setEnabledAt(6, false);
		   };
		   
	   if(dia_de_la_semana==3 && dia_de_descanso==2){
			pestanas.setEnabledAt(0, false);
			pestanas.setEnabledAt(1, true);
			pestanas.setEnabledAt(2, true);
			pestanas.setEnabledAt(3, true);
			pestanas.setEnabledAt(4, false);
			pestanas.setEnabledAt(5, false);
			pestanas.setEnabledAt(6, false);
	   	   };	 
	    	   
       if(dia_de_la_semana==3 && !(dia_de_descanso==2)){
			pestanas.setEnabledAt(0, false);
			pestanas.setEnabledAt(1, false);
			pestanas.setEnabledAt(2, true);
			pestanas.setEnabledAt(3, true);
			pestanas.setEnabledAt(4, false);
			pestanas.setEnabledAt(5, false);
			pestanas.setEnabledAt(6, false);
	   	   };	    	   
        		
       if(dia_de_la_semana==4 && dia_de_descanso==3){
			pestanas.setEnabledAt(0, false);
			pestanas.setEnabledAt(1, false);
			pestanas.setEnabledAt(2, true);
			pestanas.setEnabledAt(3, true);
			pestanas.setEnabledAt(4, true);
			pestanas.setEnabledAt(5, false);
			pestanas.setEnabledAt(6, false);
		   };	 
		   	   
	   if(dia_de_la_semana==4 && !(dia_de_descanso==3)){
			pestanas.setEnabledAt(0, false);
			pestanas.setEnabledAt(1, false);
			pestanas.setEnabledAt(2, false);
			pestanas.setEnabledAt(3, true);
			pestanas.setEnabledAt(4, true);
			pestanas.setEnabledAt(5, false);
			pestanas.setEnabledAt(6, false);
		   };
		   
	   if(dia_de_la_semana==5 && dia_de_descanso==4){
			pestanas.setEnabledAt(0, false);
			pestanas.setEnabledAt(1, false);
			pestanas.setEnabledAt(2, false);
			pestanas.setEnabledAt(3, true);
			pestanas.setEnabledAt(4, true);
			pestanas.setEnabledAt(5, true);
			pestanas.setEnabledAt(6, false);
	       }
	   
	   if(dia_de_la_semana==5 && !(dia_de_descanso==4)){
			pestanas.setEnabledAt(0, false);
			pestanas.setEnabledAt(1, false);
			pestanas.setEnabledAt(2, false);
			pestanas.setEnabledAt(3, false);
			pestanas.setEnabledAt(4, true);
			pestanas.setEnabledAt(5, true);
			pestanas.setEnabledAt(6, false);
	       }
		 
	   if(dia_de_la_semana==6 && (dia_de_descanso==5)){
			pestanas.setEnabledAt(0, false);
			pestanas.setEnabledAt(1, false);
			pestanas.setEnabledAt(2, false);
			pestanas.setEnabledAt(3, false);
			pestanas.setEnabledAt(4, true);
			pestanas.setEnabledAt(5, true);
			pestanas.setEnabledAt(6, true);
	       }
	   
	   if(dia_de_la_semana==6 && !(dia_de_descanso==5)){
			pestanas.setEnabledAt(0, false);
			pestanas.setEnabledAt(1, false);
			pestanas.setEnabledAt(2, false);
			pestanas.setEnabledAt(3, false);
			pestanas.setEnabledAt(4, false);
			pestanas.setEnabledAt(5, true);
			pestanas.setEnabledAt(6, true);
	   }

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
	
	int filaSeleccionada = 0;
	int columnaSeleccionada = 0;
	ActionListener opButton = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
				
			filaSeleccionada = tabla.getSelectedRow();
			columnaSeleccionada = tabla.getSelectedColumn();
			
//			obtiene en nombre de la columnas seleccionada
			switch(tabla.getTableHeader().getColumnModel().getColumn(columnaSeleccionada).getHeaderValue().toString()){
				case "Exige Evidencia":
						JFileChooser elegir = new JFileChooser();
		            	int opcion = elegir.showOpenDialog(button);
				                	
		                 //Si presionamos el boton ABRIR en pathArchivo obtenemos el path del archivo
		                 if(opcion == JFileChooser.APPROVE_OPTION){
		                    String pathArchivo = elegir.getSelectedFile().getPath(); //Obtiene path del archivo
		                    
		                    File mi_fichero = new File ( pathArchivo );
		                    double tamano_bytes = mi_fichero.length ( );
		                    double tamano_megas = tamano_bytes/(1024*1024);
		                    
		                    if(tamano_megas>3){
		                    	tabla.setValueAt("", filaSeleccionada, columnaSeleccionada+2);
		                    	JOptionPane.showMessageDialog(null, "El Archivo Que Intenta Agregar Es Muy Grande,\nEl Archivo Debe Medir Maximo 3 MB", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
		        				return;
		                    }else{
		                    	tabla.setValueAt(pathArchivo, filaSeleccionada, columnaSeleccionada+2);
		                    }
		                 }
				break;
				
				case "Exige Observacion":
						new Cat_Observacion_De_Actividad(tabla.getValueAt(filaSeleccionada, columnaSeleccionada+2).toString()).setVisible(true);
				break;
				
			}
		}
	};
	
	ActionListener opReporteLista = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="exec sp_Reporte_De_Actividades_Contestadas_Del_Plan_Semanal "+usuario.getFolio()+",'"+txtPeriodo.getText().toString().substring(0,10)+"','Reporte De Actividades Con Respuesta Por Periodo'"  ;
			String reporte = "Obj_Reportes_De_Actividades_Contestadas.jrxml";
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
	};
	
	ActionListener opReporteCuadroscontestado = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="exec sp_reporte_de_plan_semanal_por_dia_contestado "+usuario.getFolio()+",'"+txtPeriodo.getText().toString().substring(0,10)+" 23:59:59'"  ;
			String reporte = "Obj_Reporte_De_Plan_Semanal_Cuadros_Contestado.jrxml";
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
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
			String[][] tb_actividades_con_respuesta = tabla_actividades();
			int dianum = pLunes.isShowing()==true?0
					 : (pMarte.isShowing()==true?1
									 : (pMiercoles.isShowing()==true?2
											 : (pJueves.isShowing()==true?3
													 : (pViernes.isShowing()==true?4
															 : (pSabado.isShowing()==true?5
																	  : 6)))));
			new GuardarSQL().Guardar_Actividades_Con_Respuesta(tb_actividades_con_respuesta, dianum);
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
																: (pMiercoles.isShowing()==true?"Miercoles"
																		 : (pJueves.isShowing()==true?"Jueves"
																				 : (pViernes.isShowing()==true?"Viernes"
																						 : (pSabado.isShowing()==true?"Sabado"
																								 : "Domingo")))));
								int dianum = pLunes.isShowing()==true?0
										 : (pMarte.isShowing()==true?1
														 : (pMiercoles.isShowing()==true?2
																 : (pJueves.isShowing()==true?3
																		 : (pViernes.isShowing()==true?4
																				 : (pSabado.isShowing()==true?5
																						  : 6)))));
								
								if(JOptionPane.showConfirmDialog(null, "Usted Esta Intentando Guardar Las Actividades Del Dia \n>>>>>>>> "+dia.toUpperCase()+" >>>>>>>> \nDesea Continuar?","Aviso",0,1,new ImageIcon("imagen/usuario-icono-noes_usuario9131-64.png")) == 0){	
									if(new GuardarSQL().Guardar_Actividades_Con_Respuesta(tb_actividades_con_respuesta, dianum)){									
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
				PintarEstatusTabla(tabla,"Actividades_De_La_Semana",7);//tipo_de_tabla , columnas 
			}
		});
    }
	
	public void agregarColumnas(final JTable tb,final DefaultTableModel modelo){
		modelo.addColumn("E");
		modelo.addColumn("O");
		modelo.addColumn("Status Actividad");
		
//		tabla.moveColumn(4, 2);
		tb.getColumnModel().getColumn(2).setHeaderValue("Respuestas");
		tb.getColumnModel().getColumn(3).setHeaderValue("Exige Evidencia");
		tb.getColumnModel().getColumn(4).setHeaderValue("Exige Observacion");
		
		tb.getColumnModel().getColumn(5).setMaxWidth(25);
		tb.getColumnModel().getColumn(5).setMinWidth(25);
		tb.getColumnModel().getColumn(6).setMaxWidth(25);
		tb.getColumnModel().getColumn(6).setMinWidth(25);
		tb.getColumnModel().getColumn(7).setMaxWidth(125);
		tb.getColumnModel().getColumn(7).setMinWidth(125);
		
//		tb.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
//		tb.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
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
