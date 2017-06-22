package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Servicios_y_Regargas extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	String[] establecimiento = new Obj_Establecimiento().Combo_Establecimiento201();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	String status[] = {"VIGENTE","CANCELADO","TODOS"};
    @SuppressWarnings({ "rawtypes", "unchecked" })
    JComboBox cmbEstatus = new JComboBox(status);

	String operador[] = {"Selecciona Un Reporte"
			                ,"Servicios y Recargas En el Periodo De Fechas Vigentes Por Establecimiento" 
			                ,"Servicios y Recargas Por Asignacion" 
							};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbConcepto = new JComboBox(operador);
	
	JCButton btngenerar_reporte = new JCButton("Generar Reporte En Pantalla","hoja-de-calculo-icono-8865-32.png","Azul");
	
	JLabel JLBlinicio			= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin				= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento	= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBdepartamento		= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	JTextField txtAsignacion    = new Componentes().text(new JCTextField(), "Asignacion", 500, "String");
	
	public Cat_Reportes_De_Servicios_y_Regargas(){
		this.setSize(445,230);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Reportes De Rcargas y Servicios");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/actualizar-actualiza-icono-7372-128.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Seleccione  La Fecha, El Tipo de Reporte, el Establecimiento y Genere El Reporte "));

		int x=20, y=25, width=100,height=20;
		x=20;width=100;
		this.panel.add(cmbConcepto).setBounds                   (x     ,y      ,width*4  ,height    );
		this.panel.add(new JLabel("Fecha:")).setBounds          (x     ,y+=30  ,width    ,height    );
		this.panel.add(JLBlinicio).setBounds                    (x+=35 ,y      ,height   ,height    );
		this.panel.add(c_inicio).setBounds                      (x+=20 ,y      ,width    ,height    );
		this.panel.add(new JLabel("Fecha:")).setBounds          (x+=190,y      ,width    ,height    );
		this.panel.add(JLBfin).setBounds                        (x+=35 ,y      ,height   ,height    );
		this.panel.add(c_final).setBounds                       (x+=20 ,y      ,width    ,height    );
		this.panel.add(cmbEstablecimiento).setBounds            (x-300 ,y+=30  ,width+90 ,height    );
		this.panel.add(new JLabel("Estatus Tickets:")).setBounds(x+-80 ,y      ,width    ,height    );
        this.panel.add(cmbEstatus).setBounds                    (x     ,y      ,width    ,height    );
		this.panel.add(new JLabel("Asignacion:")).setBounds     (x-300 ,y+=30  ,width    ,height    );
		this.panel.add(txtAsignacion).setBounds                 (x-240 ,y      ,width+30 ,height    );
		x=70;width=300;
		this.panel.add(btngenerar_reporte).setBounds            (x     ,y+=35  ,width    ,height*2  );
		this.cont.add(panel);
		
		c_inicio.setDate( cargar_fechas(7));
		c_final.setDate( cargar_fechas(0));
		btngenerar_reporte.addActionListener(opGenerar_reporte);
		cmbConcepto.addActionListener(op_seleccion_reporte);
		 c_inicio.setEnabled(false);
		 c_final.setEnabled(false);
		 cmbEstablecimiento.setEnabled(false);
		 cmbEstatus.setEnabled(false);
		 txtAsignacion.setEnabled(false);
		 
	}
	
	public Date cargar_fechas(Integer dias){
		Date date1 = null;
				  try {
					date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(dias));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		return date1;
	};
	
	public String validar_campos(){
		String error = "";
		String fechainicioNull = c_inicio.getDate()+"";
		String fechafinalNull = c_final.getDate()+"";
	    if(fechainicioNull.equals("null"))error+= "Fecha  inicio\n";
		if(fechafinalNull.equals("null"))error+= "Fecha Final\n";
		return error;
	}
	
	ActionListener op_seleccion_reporte = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			 String concepto=cmbConcepto.getSelectedItem().toString().trim();
			 
			 if(concepto.equals("Selecciona Un Reporte")){
				 c_inicio.setEnabled(false);
				 c_final.setEnabled(false);
				 cmbEstablecimiento.setEnabled(false);
				 cmbEstatus.setEnabled(false);
				 txtAsignacion.setEnabled(false);
			 }
			 
			 if(concepto.equals("Servicios y Recargas En el Periodo De Fechas Vigentes Por Establecimiento" )){
				 c_inicio.setEnabled(true);
				 c_final.setEnabled(true);
				 cmbEstablecimiento.setEnabled(true);
				 cmbEstatus.setEnabled(true);
				 cmbEstablecimiento.showPopup();
				 txtAsignacion.setEnabled(false);
			 }
			 
			 if(concepto.equals("Servicios y Recargas Por Asignacion")){
				 c_inicio.setEnabled(false);
				 c_final.setEnabled(false);
				 cmbEstablecimiento.setEnabled(false);
				 cmbEstatus.setEnabled(false);
				 txtAsignacion.setEnabled(true);
				 txtAsignacion.requestFocus();
			 }
			 
		}
	};
	
	
	ActionListener opGenerar_reporte = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
					String basedatos="2.200";
					String vista_previa_reporte="no";
					int vista_previa_de_ventana=0;
					String comando= "";
					String reporte = "";
			 if(cmbConcepto.getSelectedIndex()==0){
			       JOptionPane.showMessageDialog(null,"Debe de Seleccionar Un Tipo De Reporte","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			        cmbConcepto.requestFocus();
			        cmbConcepto.showPopup();
				    return;		
			      }else{ 
						if(validar_campos().equals("")){
							 String concepto=cmbConcepto.getSelectedItem().toString().trim();
							 String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
							 String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+" 23:59:58";
							 
								if(concepto.equals("Servicios y Recargas En el Periodo De Fechas Vigentes Por Establecimiento")){
									comando="exec sp_Reporte_de_Gesto_Pago '"+fecha_inicio.substring(0, 10)+"','"+fecha_final+"','"+cmbEstatus.getSelectedItem().toString()+"','','"+cmbEstablecimiento.getSelectedItem().toString().trim()+"'";
									reporte ="Obj_Reporte_De_Venta_De_Productos_Servicios_y_Recargas.jrxml";
							    }
								
								
								if(concepto.equals("Servicios y Recargas Por Asignacion")){
									comando="exec sp_Reporte_de_Gesto_Pago '"+fecha_inicio.substring(0, 10)+"','"+fecha_final+"','"+cmbEstatus.getSelectedItem().toString()+"','"+txtAsignacion.getText().toString()+"','Selecciona un Establecimiento'";
									reporte ="Obj_Reporte_De_Venta_De_Productos_Servicios_y_Recargas.jrxml";
							    }
								
								
						}else{
						  JOptionPane.showMessageDialog(null, "Los Siguientes Campos Estan Vacios y Se Necesitan Para La Consulta:\n "+validar_campos(),"Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			               return;
						} 
		       }
			    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			   return;
		}
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Servicios_y_Regargas().setVisible(true);
		}catch(Exception e){	}
	}

}
