package Cat_Cuadrantes;

import java.awt.Container;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Obj_Cuadrantes.Obj_Actividad;
import Obj_Cuadrantes.Obj_Aspectos;
import Obj_Cuadrantes.Obj_Nivel_Critico;
import Obj_Evaluaciones.Obj_Opciones_De_Respuestas;
import Obj_Evaluaciones.Obj_Temporada;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Actividades extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
    JToolBar menu_toolbar  = new JToolBar();
    
    
    Obj_tabla ObjTab =new Obj_tabla();
	int columnas = 3,checkbox=-1;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(50);
    	this.tabla.getColumnModel().getColumn(0).setMaxWidth(50);
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(250);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(381);
		String comandof="select folio, actividad, descripcion from cuadrantes_actividades";
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tabla,modelo, columnas, comandof, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] base (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio","Actividad","Descripcion"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = base();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){return false;}
	};
	
	JTable tabla = new JTable(modelo);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);
    @SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
    
	JTextField txtFolio      = new Componentes().text(new JCTextField(), "Folio Actividad", 20, "Int");
	JTextField txttolerancia = new Componentes().text(new JCTextField(), "Tolerancia", 3, "Negativo");
	
	JTextArea txaActividad 	 = new Componentes().textArea(new JTextArea(), "Actividad", 200);
	JScrollPane scrollact    = new JScrollPane(txaActividad);
	
    JTextArea txaDescripcion = new Componentes().textArea(new JTextArea(), "Descripcion", 500);
	JScrollPane scrolltxa    = new JScrollPane(txaDescripcion);

	String respuesta[] = new Obj_Opciones_De_Respuestas().Combo_Respuesta();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbRespuesta = new JComboBox(respuesta);
	
	String atributo[] = new Obj_Aspectos().Combo_Aspecto();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbAspectos = new JComboBox(atributo);
	
	String nivel_critico[] = new Obj_Nivel_Critico().Combo_Nivel_Critico();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbNivelCritico = new JComboBox(nivel_critico);
	
	String temporada[] = new Obj_Temporada().Combo_Temporada();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbTemporada = new JComboBox(temporada);
	
	String status[] = {"Vigente","Cancelado"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	String Exige[] = {"No","Si"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_evidencia = new JComboBox(Exige);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmb_Observacion = new JComboBox(Exige);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbGeneraAlerta = new JComboBox(Exige);
	
	JCButton btnBuscar    = new JCButton("Buscar"    ,"Filter-List-icon16.png"     ,"Azul"); 
	JCButton btnNuevo     = new JCButton("Nuevo"     ,"Nuevo.png"                  ,"Azul");
	JCButton btnModificar = new JCButton("Modificar" ,"Modify.png"                 ,"Azul");
	JCButton btnGuardar   = new JCButton("Guardar"   ,"Guardar.png"                ,"Azul");
	JCButton btnDeshacer  = new JCButton("Deshacer"  ,"deshacer16.png"             ,"Azul");
	JCButton btnSimilar   = new JCButton("Similar"   ,"similar9036-16mas.png"      ,"Azul");
	JCButton btnderecha   = new JCButton(""          ,"adelante.png"               ,"Azul");
	JCButton btnizquierda = new JCButton(""          ,"atras.png"                  ,"Azul");
	
	String NuevoModifica="";
	
	public Cat_Actividades(){
		this.init();
		this.setSize(720,600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	 
	public Cat_Actividades(int Folio){
		this.init();
		this.setSize(720,600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    OBuscarActividad(Folio);
		panelEnabledFalse();
		btnModificar.setEnabled(true);
		btnSimilar.setEnabled(true);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void init(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-32-mas.png"));
		this.setTitle("Actividad");
		this.panel.setBorder(BorderFactory.createTitledBorder("Actividad General De Cuadrantes"));

		this.menu_toolbar.add(btnBuscar  );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.add(btnModificar);
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.add(btnSimilar );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.add(btnNuevo   );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.add(btnDeshacer);
		this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.add(btnGuardar );
		
		this.menu_toolbar.setFloatable(false);
		
		this.trsfiltro = new TableRowSorter(modelo); 
		this.tabla.setRowSorter(trsfiltro);
		
		int x=15, y=20,width=100,height=20,sep=25;
		this.panel.add(menu_toolbar).setBounds                  (x     ,y      ,width*9   ,height );
		this.panel.add(new JLabel("Folio:")).setBounds          (x     ,y+=35  ,width     ,height );
		this.panel.add(txtFolio).setBounds                      (x+=60 ,y      ,width     ,height );
		this.panel.add(btnizquierda).setBounds                  (x+=105,y      ,height    ,height );
		this.panel.add(btnderecha).setBounds                    (x+=25 ,y      ,height    ,height );
		this.panel.add(new JLabel("Estatus:")).setBounds        (x+=30 ,y      ,width     ,height );
		this.panel.add(cmb_status).setBounds                    (x+=40 ,y      ,width     ,height );
		 x=15;
		this.panel.add(new JLabel("Actividad:")).setBounds      (x     ,y+=sep ,width     ,height );
		this.panel.add(scrollact).setBounds                     (x+60  ,y      ,width*3   ,120    );
		this.panel.add(new JLabel("Respuesta:")).setBounds      (x     ,y+=130 ,width     ,height );
		this.panel.add(cmbRespuesta).setBounds                  (x+60  ,y      ,width*3   ,height );
		this.panel.add(new JLabel("Aspecto:")).setBounds        (x     ,y+=sep ,width     ,height );
		this.panel.add(cmbAspectos).setBounds                  (x+60  ,y       ,width*3   ,height );
		this.panel.add(new JLabel("Nivel Crítico:")).setBounds  (x     ,y+=sep ,width     ,height );
		this.panel.add(cmbNivelCritico).setBounds               (x+60  ,y      ,width*3   ,height );
		this.panel.add(new JLabel("Temporada:")).setBounds      (x     ,y+=sep ,width     ,height );
		this.panel.add(cmbTemporada).setBounds                  (x+60  ,y      ,width*3   ,height );
		this.panel.add(new JLabel("Exige Evidencia:")).setBounds(x     ,y+=sep ,width     ,height );
		this.panel.add(cmb_evidencia).setBounds                 (x+90  ,y      ,width-50  ,height );
		this.panel.add(new JLabel("Exige Observacion:")).setBounds(x+220,y     ,width     ,height );
		this.panel.add(cmb_Observacion).setBounds               (x+310 ,y      ,width-50  ,height );
		this.panel.add(new JLabel("Genera Evidencia:")).setBounds(x    ,y+=sep ,width     ,height );
		this.panel.add(cmbGeneraAlerta).setBounds               (x+90  ,y      ,width-50  ,height );
		this.panel.add(new JLabel("Tolerancia:")).setBounds     (x+220 ,y      ,width     ,height );
		this.panel.add(txttolerancia).setBounds                 (x+280 ,y      ,width-20     ,height );
		
		this.panel.add(new JLabel("Actividades Similares:")).setBounds(x,y+=sep,width*2   ,height );
		this.panel.add(scroll_tabla).setBounds                  (x     ,y+=18  ,683       ,180    );
		
        y=55; x=400;;		
		this.panel.add(new JLabel("Descripción:")).setBounds    (x     ,y      ,width     ,height );
		this.panel.add(scrolltxa).setBounds                     (x     ,y+=sep  ,width*3   ,276    );

		
		this.cont.add(panel);
		
		this.btnNuevo.addActionListener     (opNuevo      );
		this.btnDeshacer.addActionListener  (opDeshacer   );
		this.btnGuardar.addActionListener   (opGuardar    );
		this.btnBuscar.addActionListener    (opBuscar     );
		this.btnModificar.addActionListener (opModificar  );
		this.btnSimilar.addActionListener   (op_similar   );
		this.btnizquierda.addActionListener (opLeft       );
		this.btnderecha.addActionListener   (opderecha    );
		this.txaActividad.addKeyListener    (op_filtro    );
		
		panelEnabledFalse();
		init_tabla();
		
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "buscar");
		getRootPane().getActionMap().put("buscar", new AbstractAction(){public void actionPerformed(ActionEvent e){ btnBuscar.doClick();    }  });
		
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape"                );
		getRootPane().getActionMap().put("escape", new AbstractAction(){ public void actionPerformed(ActionEvent e){ btnDeshacer.doClick(); }  });
			             
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0), "nuevo"                     );
		getRootPane().getActionMap().put("nuevo", new AbstractAction(){ public void actionPerformed(ActionEvent e){ btnNuevo.doClick();     }  });
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N,Event.CTRL_MASK),"nuevo"          );
		getRootPane().getActionMap().put("nuevo", new AbstractAction(){ public void actionPerformed(ActionEvent e) { btnNuevo.doClick();    }  });
			             
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar"        );
		getRootPane().getActionMap().put("guardar", new AbstractAction(){public void actionPerformed(ActionEvent e){btnGuardar.doClick();   }  });
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar"                  );
		getRootPane().getActionMap().put("guardar", new AbstractAction(){public void actionPerformed(ActionEvent e) { btnGuardar.doClick(); }  });
	}
	
	public void panelEnabledFalse(){	
		txtFolio.setEditable(false);
		txttolerancia.setEditable(false);
		txaActividad.setEditable(false);
		txaDescripcion.setEditable(false);
		cmb_status.setEnabled(false);
		cmbRespuesta.setEnabled(false);
		cmbAspectos.setEnabled(false);
		cmbNivelCritico.setEnabled(false);
		cmbTemporada.setEnabled(false);
		cmb_evidencia.setEnabled(false);
		cmb_Observacion.setEnabled(false);
		cmbGeneraAlerta.setEnabled(false);
		btnModificar.setEnabled(false);
		btnGuardar.setEnabled(false);
		btnSimilar.setEnabled(false);
	}
	
	public void panelEnabledTrue(){	
		txaActividad.setEditable(true);
		txttolerancia.setEditable(true);
		txaDescripcion.setEditable(true);
		cmb_status.setEnabled(true);
		cmbRespuesta.setEnabled(true);
		cmbAspectos.setEnabled(true);
		cmbNivelCritico.setEnabled(true);
		cmbTemporada.setEnabled(true);
		cmb_evidencia.setEnabled(true);
		cmb_Observacion.setEnabled(true);
		cmbGeneraAlerta.setEnabled(true);
		btnGuardar.setEnabled(true);
	}
	
	public void panelLimpiar(){	
		txtFolio.setText("");
		txttolerancia.setText("");
		txaActividad.setText("");
		txaDescripcion.setText("");
		cmb_status.setSelectedIndex(0);
		cmbRespuesta.setSelectedIndex(0);
		cmbAspectos.setSelectedIndex(0);
		cmbNivelCritico.setSelectedIndex(0);
		cmbTemporada.setSelectedIndex(0);
		cmb_evidencia.setSelectedIndex(0);
		cmb_Observacion.setSelectedIndex(0);
		cmbGeneraAlerta.setSelectedIndex(0);
		init_tabla();
		ObjTab.Obj_Filtro(tabla, "", columnas);
	}
	
	public void OBuscarActividad(int folio_actividad){
		Obj_Actividad actividad = new Obj_Actividad().Buscar(folio_actividad);
		txtFolio.setText(actividad.getFolio()+"");
		txttolerancia.setText(actividad.getTolerancia_minutos()+"");
		txaActividad.setText(actividad.getActividad());
		txaDescripcion.setText(actividad.getDescripcion());
		cmb_status.setSelectedItem(actividad.getEstatus());
		cmbRespuesta.setSelectedItem(actividad.getRespuesta());
		cmbAspectos.setSelectedItem(actividad.getAspecto());
		cmbNivelCritico.setSelectedItem(actividad.getNivel_Critico());
		cmbTemporada.setSelectedItem(actividad.getTemporada());
		cmb_evidencia.setSelectedItem(actividad.getExige_Evidencia());
		cmb_Observacion.setSelectedItem(actividad.getExige_Observacion());
		cmbGeneraAlerta.setSelectedItem(actividad.getGenera_Alerta());
		ObjTab.Obj_Filtro(tabla, txaActividad.getText(), columnas);
	}
	
	KeyListener op_filtro = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tabla, txaActividad.getText(), columnas);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	ActionListener opNuevo = new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
			panelEnabledTrue();
			panelLimpiar();
			cmb_status.setSelectedIndex(0);
			txtFolio.setText(new Obj_Actividad().Nuevo()+"");
			txtFolio.setEditable(false);
			txaActividad.requestFocus();
			NuevoModifica="N";
		}
	};
	
	ActionListener opDeshacer = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			panelEnabledFalse();
			panelLimpiar();
			txtFolio.requestFocus();
		}
	};
	
	ActionListener opderecha = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			int Folio=0;
			if(!txtFolio.getText().equals("")){
				Folio=Integer.valueOf(txtFolio.getText().trim());
			}
		    OBuscarActividad(Folio+1);
			panelEnabledFalse();
			btnSimilar.setEnabled(true);
			btnModificar.setEnabled(true);
		}
	};
	
	ActionListener opLeft = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			int Folio=0;
			if(!txtFolio.getText().equals("")){
				Folio=Integer.valueOf(txtFolio.getText().trim());
			}
  		    OBuscarActividad(Folio-1);
			panelEnabledFalse();
			btnSimilar.setEnabled(true);
			btnModificar.setEnabled(true);
		}
	};
	
	ActionListener opModificar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
				panelEnabledTrue();
				txaActividad.requestFocus();
				NuevoModifica="M";
		}
	};
	
	ActionListener opBuscar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
				new Cat_Filtro_Actividades().setVisible(true);
				dispose();
		}
	};
	
	ActionListener op_similar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			if(!txtFolio.getText().equals("")){
				panelEnabledTrue();
				txaActividad.requestFocus();
				txtFolio.setText(new Obj_Actividad().Nuevo()+"");
				cmb_status.setSelectedIndex(0);
				NuevoModifica="N";
			}else{
				JOptionPane.showMessageDialog(null, "Busque un registro primero antes de hacer un similar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	ActionListener opGuardar = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			if(validaCampos() !=""){
				JOptionPane.showMessageDialog(null, "Los Siguientes Datos Son Requeridos:\n"+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				
				if(Integer.valueOf(txttolerancia.getText().toString())<-1){
					JOptionPane.showMessageDialog(null, "La Tolerancia Menor Posible Es -1 y Para Cuando No Aplica Tolerancia En Una Actividad:\n"+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}else{
					Obj_Actividad actividad = new Obj_Actividad();
					
					actividad.setFolio(Integer.valueOf(txtFolio.getText().toString()));
					actividad.setActividad(txaActividad.getText().toString().trim());
					actividad.setDescripcion(txaDescripcion.getText().toString().trim());
					actividad.setEstatus(cmb_status.getSelectedItem().toString());
					actividad.setRespuesta(cmbRespuesta.getSelectedItem().toString());
					actividad.setAspecto(cmbAspectos.getSelectedItem().toString());
					actividad.setNivel_Critico(cmbNivelCritico.getSelectedItem().toString());
					actividad.setTemporada(cmbTemporada.getSelectedItem().toString());
					actividad.setExige_Evidencia(cmb_evidencia.getSelectedItem().toString());
					actividad.setExige_Observacion(cmb_Observacion.getSelectedItem().toString());
					actividad.setGenera_Alerta(cmbGeneraAlerta.getSelectedItem().toString());
					actividad.setTolerancia_minutos(Integer.valueOf(txttolerancia.getText().toString()));
					actividad.setNuevoModifica(NuevoModifica);
					
					if(actividad.Guardar()){
						JOptionPane.showMessageDialog(null,"El Registro Se Guardó Correctamente!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
						btnDeshacer.doClick();
						return;
					}else{
						JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
						return;
					}	
				}
			}
		}
	};
	
	public String validaCampos(){
		String error="";
		
		if(txtFolio.getText().equals("")) error += "Folio\n";
		if(txaActividad.getText().equals("")) error += "Actividad\n";
		if(txaDescripcion.getText().equals("")) error += "Descripcion\n";
		if(cmbRespuesta.getSelectedIndex()==0) error += "Respuesta\n";
		if(cmbAspectos.getSelectedIndex()==0) error += "Aspecto\n";
		if(cmbNivelCritico.getSelectedIndex()==0) error += "Nivel Crítico\n";
		if(cmbTemporada.getSelectedIndex()==0) error += "Temporada\n";
		if(txttolerancia.getText().equals("")) error += "Tolerancia\n";
		return error;
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Actividades().setVisible(true);
		}catch(Exception e){
			System.err.println("Error en Main: "+e.getMessage());
		}
	}
}
