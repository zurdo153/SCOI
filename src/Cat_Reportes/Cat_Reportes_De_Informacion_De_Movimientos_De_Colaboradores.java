package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_tabla;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Informacion_De_Movimientos_De_Colaboradores extends JDialog {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	String departamento[] = new Obj_Departamento().Combo_Departamento();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbDepartamento = new JComboBox(departamento);
	JTextField txtFolio = new Componentes().text(new JCTextField(), "Folio de la Lista de Raya", 15, "Int");
	
	JTextField txtFolioLRI = new Componentes().text(new JCTextField(), "Folio Inicial LR ", 15, "Int");
	JTextField txtFolioLRF = new Componentes().text(new JCTextField(), "Folio Final LR ", 15, "Int");
	
	JCButton btn_cant_personal            = new JCButton("Sueldos y Bonos General","asistencia-comunitaria-icono-9465-16.png","Azul");
	JCButton btn_sueldosybonos_pend       = new JCButton("Sueldos y Bonos Pendientes","asistencia-comunitaria-icono-9465-16.png","Azul");
	JCButton btnSeleccionLR               = new JCButton("Filtro","Filter-List-icon16.png","Azul");
	JCButton btnReporte_Infonavit_Pasados = new JCButton("Infonavit Pasado","orange-folder-saved-search-icone-8197-16.png","Azul");
	JCButton btnReporte_Infonavit_actual  = new JCButton("Infonavit Actual","infonavit.png","Azul");
	JCButton btnReporte_Infonacot_Pasados = new JCButton("Infonacot Pasado","orange-folder-saved-search-icone-8197-16.png","Azul");
	JCButton btnReporte_Infonacot_actual  = new JCButton("Infonacot Actual","infonacotsinfondo-1 32.png","Azul");
	JCButton btnReporte_Prestamos_Pasados = new JCButton("Prestamos Pasado","orange-folder-saved-search-icone-8197-16.png","Azul");
	JCButton btnReporte_Prestamos_actual  = new JCButton("Prestamos Actual","dinero-icono-8797-16.png","Azul");
	JCButton btnReporte_Infonavit_Fonacot = new JCButton("Infonavit E Infonacot","orange-folder-saved-search-icone-8197-16.png","Azul");
	
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBdepartamento= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	JLabel lbl0 = new JLabel();
	JLabel lbl1 = new JLabel();
	JLabel lbl2 = new JLabel();
	JLabel lblfolio = new JLabel();
	
	public Cat_Reportes_De_Informacion_De_Movimientos_De_Colaboradores(int folio_colaborador, String Colaborador){
		System.out.println(folio_colaborador);
		
		this.setSize(930,220);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		 cargar_fechas();
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/percepciones_y_deducciones32.png"));
		this.setTitle("Reportes De Percepciones y Deducciones");
		this.panel.setBorder(BorderFactory.createTitledBorder("Seleccione un Rango De Fechas y De Click al Reporte Deseado"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.lbl0.setBorder(BorderFactory.createTitledBorder(blackline,"Reportes En Un Periodo De Fechas"));
		this.lbl1.setBorder(BorderFactory.createTitledBorder(blackline,"Reportes De Lista De Raya Pasadas O Actual"));
		this.lbl2.setBorder(BorderFactory.createTitledBorder(blackline,"Reportes De Un Rango De Listas De Raya"));
		
		int x=15,y=30,width=100,height=20,sep=200;
		this.panel.add(lbl0).setBounds                          (x-10   ,y-15, 490, 100);
		this.panel.add(new JLabel("Fecha Inicio:")).setBounds   (x      ,y    ,width  ,height);
		this.panel.add(JLBlinicio).setBounds                    (x+=60  ,y    ,height ,height);
		this.panel.add(c_inicio).setBounds                      (x+=20  ,y    ,width  ,height);
	    this.panel.add(new JLabel("Establecimiento:")).setBounds(x+=120 ,y    ,width  ,height);
	    this.panel.add(JLBestablecimiento).setBounds            (x+=75  ,y    ,height ,height);
		this.panel.add(cmbEstablecimiento).setBounds            (x+=25  ,y    ,170    ,height);
		x=15;
		this.panel.add(new JLabel("Fecha Final:")).setBounds    (x      ,y+=25,width  ,height);
		this.panel.add(JLBfin).setBounds                        (x+=60  ,y    ,height ,height);
		this.panel.add(c_final).setBounds                       (x+=20  ,y    ,width  ,height);
//		this.panel.add(new JLabel("Departamento:")).setBounds   (x+=120 ,y    ,150    ,height);
//		this.panel.add(JLBdepartamento).setBounds               (x+=75  ,y    ,height ,height);
//		this.panel.add(cmbDepartamento).setBounds               (x+=25  ,y    ,170    ,height);
		
		x=15;width=225;height=25;sep=240;
		this.panel.add(btn_cant_personal).setBounds             (x      ,y+=25,width  ,height);
		this.panel.add(btn_sueldosybonos_pend).setBounds        (x+sep  ,y    ,width  ,height);

		x=510;y=30;width=180;sep=200;
		this.panel.add(lbl1).setBounds                          (x-10   ,y-15 ,420    ,170   );
		this.panel.add(txtFolio).setBounds                      (x+=10  ,y    ,width  ,height);
		this.panel.add(btnSeleccionLR).setBounds                (x+185  ,y    ,90     ,height);
		this.panel.add(btnReporte_Infonavit_Pasados).setBounds  (x      ,y+=35,width  ,height);
		this.panel.add(btnReporte_Infonavit_actual).setBounds   (x+sep  ,y    ,width  ,height);
		this.panel.add(btnReporte_Infonacot_Pasados).setBounds  (x      ,y+=35,width  ,height);
		this.panel.add(btnReporte_Infonacot_actual).setBounds   (x+sep  ,y    ,width  ,height);
		this.panel.add(btnReporte_Prestamos_Pasados).setBounds  (x      ,y+=35,width  ,height);
		this.panel.add(btnReporte_Prestamos_actual).setBounds   (x+sep  ,y    ,width  ,height);
		
		x=15;y=130;width=100;sep=200;height=20;
		this.panel.add(lbl2).setBounds                          (x-10   ,y-15 ,490    ,70   );
		this.panel.add(lblfolio).setBounds                      (x      ,y    ,width*3,height);
		this.panel.add(new JLabel(Colaborador)).setBounds       (x+50   ,y    ,width*3,height);
		this.panel.add(txtFolioLRI).setBounds                   (x      ,y+=25,width+5,height);
		this.panel.add(txtFolioLRF).setBounds                   (x+=120 ,y    ,width+5,height);
		this.panel.add(btnReporte_Infonavit_Fonacot).setBounds  (x+=120 ,y    ,225    ,height);
		this.cont.add(panel);
		
		lblfolio.setText(folio_colaborador+"");
		
		btn_cant_personal.addActionListener(op_generar);
		btn_sueldosybonos_pend.addActionListener(op_generar);
		btnSeleccionLR.addActionListener(op_filtro_listas_de_raya);
		btnReporte_Infonavit_Pasados.addActionListener(op_generar_pasado_actual);
		btnReporte_Infonavit_actual.addActionListener(op_generar_pasado_actual);
		btnReporte_Infonacot_Pasados.addActionListener(op_generar_pasado_actual);
		btnReporte_Infonacot_actual.addActionListener(op_generar_pasado_actual);
		btnReporte_Prestamos_Pasados.addActionListener(op_generar_pasado_actual);
		btnReporte_Prestamos_actual.addActionListener(op_generar_pasado_actual);
		btnReporte_Infonavit_Fonacot.addActionListener(op_reporte_infocacotinfonavitenrangolr);
	}
	
	public void cargar_fechas(){
		Date date1 = null;
				  try {
					date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(7));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		c_inicio.setDate(date1);
	    Date date2 = null;
					  try {
						date2 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(0));
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
		c_final.setDate(date2);
	};
	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	String basedatos="2.26";
	String vista_previa_reporte="no";
	int vista_previa_de_ventana=0;
	String comando="";
	String reporte = "";
	
	ActionListener op_reporte_infocacotinfonavitenrangolr = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
     	   if(txtFolioLRI.getText().equals("")||txtFolioLRF.getText().equals("")){
   			  JOptionPane.showMessageDialog(null, "Para Este Reporte Se Requiere Teclear La Lista De Raya Inicial y Lista de Raya Final","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
              return;       
           }else{
               if(Integer.valueOf(txtFolioLRI.getText())>Integer.valueOf(txtFolioLRF.getText()) ) {
    			   JOptionPane.showMessageDialog(null, "La Lista De Raya Inicial Debe De Ser Menor A La Final","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
                   return;
        	   }else{
        		   comando="exec sp_Reporte_De_Descuento_Fonacot_Infonavit_En_Un_Rango_De_Listas_De_Raya "+txtFolioLRI.getText()+","+txtFolioLRF.getText()+","+lblfolio.getText();
        		   reporte="Obj_Reporte_De_Descuento_Fonacot_Infonavit_En_Un_Rango_De_Listas_De_Raya.jrxml";
    			   new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
    			   return;   
        	   }
           }
		}
	};
	
	ActionListener op_generar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(validar_fechas().equals("")){
				String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
				String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+" 23:59:58";
			
				if(c_inicio.getDate().before(c_final.getDate())){
				   
				   if( e.getActionCommand().equals("Sueldos y Bonos General")){
					   reporte ="Obj_Reporte_De_Sueldos_y_Bonos_En_Un_Periodo_De_Fechas.jrxml";
					   comando="exec sp_Reporte_De_Sueldos_y_Bonos_En_Un_Periodo_De_Fechas '"+fecha_inicio+"','"+fecha_final+"',1,'"+cmbEstablecimiento.getSelectedItem().toString().trim()+"'";
				   }
				   
				   if( e.getActionCommand().equals("Sueldos y Bonos Pendientes")){
					   reporte ="Obj_Reporte_De_Sueldos_y_Bonos_En_Un_Periodo_De_Fechas.jrxml";
					   comando="exec sp_Reporte_De_Sueldos_y_Bonos_En_Un_Periodo_De_Fechas '"+fecha_inicio+"','"+fecha_final+"','N','"+cmbEstablecimiento.getSelectedItem().toString().trim()+"'";
				   }
			
				   new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
				   return;
				}else{
					  JOptionPane.showMessageDialog(null, "El Rango De Fechas Esta Invertido","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
                      return;
				}
			}else{
				  JOptionPane.showMessageDialog(null, "Los Siguientes Campos Estan Vacios y Se Necesitan Para La Consulta:\n "+validar_fechas(),"Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
                  return;
			}
		}
	};
	
	ActionListener op_generar_pasado_actual = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				if( e.getActionCommand().equals("Infonavit Pasado")){
					if(!txtFolio.getText().equals("")){
						 reporte = "Obj_Reporte_De_Infonavit_De_Lista_De_Raya.jrxml";
						 comando = "exec sp_reporte_de_infonavit_de_lista_de_raya 'Colaboradores Con Infonavit De Listas De Raya Pasadas',"+Integer.valueOf(txtFolio.getText())  ;
					}else{
						  JOptionPane.showMessageDialog(null, "El Campo Folio No Debe De Estar Vacio","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
    		                btnSeleccionLR.doClick();
						  return;
					}
			 	}

				if( e.getActionCommand().equals("Infonacot Pasado")){
					if(!txtFolio.getText().equals("")){
						 reporte = "Obj_Reporte_De_Infonacot_De_Lista_De_Raya.jrxml";
						 comando = "exec sp_Reporte_De_Infonacot_De_Lista_De_Raya 'Colaboradores Con Infonacot De Listas De Raya Pasadas',"+Integer.valueOf(txtFolio.getText())  ;
					}else{
						  JOptionPane.showMessageDialog(null, "El Campo Folio No Debe De Estar Vacio","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						  btnSeleccionLR.doClick();
						  return;
					}
			 	}
				
				if( e.getActionCommand().equals("Prestamos Pasado")){
					if(!txtFolio.getText().equals("")){
						 reporte = "Obj_Reporte_De_Prestamos_De_Listas_De_Raya_Pasadas.jrxml";
						 comando = "exec sp_consulta_de_prestamos_de_listas_de_raya_pasadas "+Integer.valueOf(txtFolio.getText())  ;
					}else{
						  JOptionPane.showMessageDialog(null, "El Campo Folio No Debe De Estar Vacio","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						  btnSeleccionLR.doClick();
						  return;
					}
			 	}
				
			   if( e.getActionCommand().equals("Infonacot Actual")){
				   reporte = "Obj_Reporte_De_Infonacot_De_Lista_De_Raya.jrxml";
			  	   comando = "exec sp_Reporte_De_Infonacot_De_Lista_De_Raya 'Colaboradores Con Infonacot En Lista De Raya Actual',0" ;
			   }
			   
			   if( e.getActionCommand().equals("Infonavit Actual")){
				   reporte = "Obj_Reporte_De_Infonavit_De_Lista_De_Raya.jrxml";
			  	   comando = "exec sp_reporte_de_infonavit_de_lista_de_raya 'Colaboradores Con Infonavit En Lista De Raya Actual',0" ;
			   }
			   
			   if( e.getActionCommand().equals("Prestamos Actual")){
				   reporte = "Obj_Reporte_De_Prestamos_De_Lista_De_Raya_Actual.jrxml";
			  	   comando = "exec sp_Reporte_De_Prestamos_De_Lista_De_Raya_Actual " ;
			   }
			   
			   new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			   return;
		}
	};
	
	ActionListener op_filtro_listas_de_raya = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			new Cat_Filtro_De_Listas_De_Raya_Pasadas().setVisible(true);
			return;
		}
	};
	
	public String validar_fechas(){
		String error = "";
		String fechainicioNull = c_inicio.getDate()+"";
		String fechafinalNull = c_final.getDate()+"";
	    if(fechainicioNull.equals("null"))error+= "Fecha  inicio\n";
		if(fechafinalNull.equals("null"))error+= "Fecha Final\n";
		return error;
	}
	
	/////TODO filtro listas de raya
  public class Cat_Filtro_De_Listas_De_Raya_Pasadas extends JDialog {
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Connexion con = new Connexion();
		public JTextField txtFiltro = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Buscar En La Tabla <<<", 300, "String");

		@SuppressWarnings("rawtypes")
		public Class[] tipos(){
			Class[] tip = new Class[columnas];
			for(int i =0; i<columnas; i++){
					tip[i]=java.lang.Object.class;
			}
			return tip;
		}
		
		int columnas = 3,checkbox=-1;
		public void init_tabla(){
	    	this.tabla.getColumnModel().getColumn(0).setMinWidth(100);		
	    	this.tabla.getColumnModel().getColumn(1).setMinWidth(100);
			String comando=("exec sp_filtro_lista_rayas_pasadas");
			String basedatos="26",pintar="si";
			new Obj_tabla().Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
	    }
		
	 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Num. Lista Raya", "Fecha Cierre"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = tipos();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
	         return types[columnIndex];
	     }
			public boolean isCellEditable(int fila, int columna){
              return false;
			}
	    };
	    
	    JTable tabla = new JTable(modelo);
		public JScrollPane scroll_tabla = new JScrollPane(tabla);
		
 	 public Cat_Filtro_De_Listas_De_Raya_Pasadas()	{
			this.setSize(255,445);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setModal(true);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/Filter-List-icon16.png"));
			this.setTitle("Reporte de Listas de Raya Pasadas");
			this.campo.setBorder(BorderFactory.createTitledBorder("Seleccione La Lista de Raya a Consultar"));
			this.campo.add(scroll_tabla).setBounds(15,42,220,360);
			this.campo.add(txtFiltro).setBounds   (15,20,220,20);
			init_tabla();
			agregar(tabla);
			cont.add(campo);
			this.txtFiltro.addKeyListener(op_filtro);
		}
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		        		int fila = tabla.getSelectedRow();
		    			txtFolio.setText(tabla.getValueAt(fila, 0).toString().trim());
		    			dispose();
		        	}
		        }
	        });
	    }
		
		KeyListener op_filtro = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {  int[] columnas ={0,1}; new Obj_Filtro_Dinamico_Plus(tabla, txtFiltro.getText().toUpperCase(), columnas); 	}
			public void keyTyped(KeyEvent arg0)   {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
     }

	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Informacion_De_Movimientos_De_Colaboradores(1, "MARCO ANTONIO BODART GUZMAN").setVisible(true);
		}catch(Exception e){	}
	}

}
