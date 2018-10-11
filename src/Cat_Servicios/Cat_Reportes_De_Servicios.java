package Cat_Servicios;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import Conexiones_SQL.Generacion_Reportes;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Servicios extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser c_inicio = new Componentes().jchooser(new JDateChooser()  ,"Fecha Inicial"  ,7);
	JDateChooser c_final  = new Componentes().jchooser(new JDateChooser()  ,"Fecha Final"  ,0);
	
	String operador[] = {"Selecciona Un Reporte"
							,"Reporte De Servicios Atendidos Por Establecimiento Totales"
							,"Reporte De Servicios Atendidos Por Colaborador"
							,"Reporte De Servicios Terminados A Detalle"
							,"Reporte De Servicios Pendientes y En Proceso A Detalle"
							,"Reporte De Servicios Asignados"
	};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbConcepto = new JComboBox(operador);
	
	JCButton btngenerar_reporte = new JCButton("Generar Reporte En Pantalla","hoja-de-calculo-icono-8865-32.png","Azul");
	JCButton btngenerar_excel   = new JCButton("Generar Reporte En XLS ","xls-icon-3376-32px.png","Verde");
	
	JLabel JLBlinicio			= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin				= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento	= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBdepartamento		= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	
	 String Departamentos[] = new Obj_Departamento().Combo_Departamentoservicio();
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbDepartamento = new JComboBox(Departamentos);  
	 
	public Cat_Reportes_De_Servicios(){
		this.setSize(430,250);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Reportes De Servicios");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/system-config-servicios-icono-4771-32.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Seleccione  El Rango De Fechas,El Tipo de Reporte y Genere El Reporte "));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Report.png"));
		
		int x=20, y=25, width=100,height=20;
		this.panel.add(cmbConcepto).setBounds                 (x     ,y      ,380    ,height    );
		this.panel.add(new JLabel("Fecha Inicio:")).setBounds (x     ,y+=35  ,width  ,height    );
		this.panel.add(JLBlinicio).setBounds                  (x+=55 ,y      ,height ,height    );
		this.panel.add(c_inicio).setBounds                    (x+=20 ,y      ,width  ,height    );
		this.panel.add(new JLabel("Fecha Final:")).setBounds  (x+=130,y      ,width  ,height    );
		this.panel.add(JLBfin).setBounds                      (x+=55 ,y      ,height ,height    );
		this.panel.add(c_final).setBounds                     (x+=20 ,y      ,width  ,height    );
	    x=20; 
		this.panel.add(JLBdepartamento).setBounds             (x     ,y+=25  ,height ,height    );
		this.panel.add(cmbDepartamento).setBounds             (x+=20 ,y      ,170    ,height    );
		x=70;width=300;
		this.panel.add(btngenerar_reporte).setBounds          (x    ,y+=50   ,width   ,height*2 );

		this.cont.add(panel);
		btngenerar_reporte.addActionListener(opGenerar_reporte);
	}
	
	public String validar_fechas(){
		String error = "";
		String fechainicioNull = c_inicio.getDate()+"";
		String fechafinalNull = c_final.getDate()+"";
		String departamento =cmbDepartamento.getSelectedItem().toString();
	    if(fechainicioNull.equals("null"))error+= "Fecha  inicio\n";
		if(fechafinalNull.equals("null"))error+= "Fecha Final\n";
		if(departamento.equals("Selecciona un Departamento"))error+= "Departamento\n";
		return error;
	}
	
	ActionListener opGenerar_reporte = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				String basedatos="2.98";
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
						if(validar_fechas().equals("")){
							String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
							  String fecha_final  = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+"  23:59:00";
							  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
							  Date fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
							  Date fecha2 = sdf.parse(fecha_final , new ParsePosition(0));
							  
							    if(fecha1.before(fecha2)){
								  String concepto=cmbConcepto.getSelectedItem().toString().trim();
								if(concepto.equals("Reporte De Servicios Atendidos Por Establecimiento Totales")){
										comando="exec sp_Reporte_De_Servicios_Atendidos_Por_Establecimientos_En_Un_Periodo '"+fecha_inicio+"','"+fecha_final+"','"+cmbDepartamento.getSelectedItem().toString()+"'";
										reporte ="Obj_Reporte_De_Servicios_Atendidos_Por_Establecimientos_En_Un_Periodo.jrxml";
								}
								
								if(concepto.equals("Reporte De Servicios Atendidos Por Colaborador")){
									comando="exec Reporte_De_Servicios_Atendidos_Por_Colaborador '"+fecha_inicio+"','"+fecha_final+"','"+cmbDepartamento.getSelectedItem().toString()+"'";
									reporte ="Obj_Reporte_De_Servicios_Atendidos_Por_Colaborador.jrxml";
							   }
								
								if(concepto.equals("Reporte De Servicios Terminados A Detalle")){
									comando="exec sp_Reporte_De_Servicios_a_Detalle_Por_Departamento '"+fecha_inicio+"','"+fecha_final+"','"+cmbDepartamento.getSelectedItem().toString()+"','TERMINADO'";
									reporte ="Obj_Reporte_De_Servicios_A_Detalle.jrxml";
							    }
								
								if(concepto.equals("Reporte De Servicios Pendientes y En Proceso A Detalle")){
									comando="exec sp_Reporte_De_Servicios_a_Detalle_Por_Departamento '"+fecha_inicio+"','"+fecha_final+"','"+cmbDepartamento.getSelectedItem().toString()+"','SOLICITADO Y EN PROCESO'";
									reporte ="Obj_Reporte_De_Servicios_A_Detalle.jrxml";
								}
								
								if(concepto.equals("Reporte De Servicios Asignados")){
									comando="servicios_asignados_reporte '"+cmbDepartamento.getSelectedItem().toString().trim()+"'";
							  		reporte = "Obj_Reporte_De_Servicios_Asignados.jrxml";
								}
								
								
							  }else{
									   JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
									   return;
							  }
						}else{
						  JOptionPane.showMessageDialog(null, "Los Siguientes Campos Estan Vacios y Se Necesitan Para La Consulta:\n "+validar_fechas(),"Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
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
			new Cat_Reportes_De_Servicios().setVisible(true);
		}catch(Exception e){	}
	}

}
