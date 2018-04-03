package Cat_Contabilidad;

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

import Obj_Contabilidad.Obj_Saldo_Banco_Interno;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.Generacion_Reportes;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Movimientos_De_Banco_Interno extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	JDateChooser c_inicio = new Componentes().jchooser(new JDateChooser()  ,"Fecha Inicial",7);
	JDateChooser c_final  = new Componentes().jchooser(new JDateChooser()  ,"Fecha Final"  ,0);
	
	Obj_Saldo_Banco_Interno banco_interno= new Obj_Saldo_Banco_Interno();	
	
	String cuentas[] =  banco_interno.Combo_Cuentas("cuentas");
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbcuenta_bancaria = new JComboBox(cuentas);

	String operador[] = {"Selecciona Un Reporte"
			                ,"Saldo de Banco Interno en un Periodo por Cuenta" 
							};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbConcepto = new JComboBox(operador);
	
	JCButton btngenerar_reporte = new JCButton("Generar Reporte En Pantalla","hoja-de-calculo-icono-8865-32.png","Azul");
	
	JLabel JLBlinicio			= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin				= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento	= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBdepartamento		= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	
	public Cat_Reportes_De_Movimientos_De_Banco_Interno(){
		this.setSize(445,230);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Reportes De Movimientos De Banco Interno");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/actualizar-actualiza-icono-7372-128.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Seleccione  El Tipo de Reporte, Las Fechas, y Genere El Reporte "));
		
		int x=20, y=25, width=100,height=20;
		x=20;width=100;
		this.panel.add(cmbConcepto).setBounds                   (x     ,y      ,width*4  ,height    );
		this.panel.add(new JLabel("Fecha:")).setBounds          (x     ,y+=30  ,width    ,height    );
		this.panel.add(JLBlinicio).setBounds                    (x+=35 ,y      ,height   ,height    );
		this.panel.add(c_inicio).setBounds                      (x+=20 ,y      ,width    ,height    );
		this.panel.add(new JLabel("Fecha:")).setBounds          (x+=190,y      ,width    ,height    );
		this.panel.add(JLBfin).setBounds                        (x+=35 ,y      ,height   ,height    );
		this.panel.add(c_final).setBounds                       (x+=20 ,y      ,width    ,height    );
		this.panel.add(cmbcuenta_bancaria).setBounds            (x-300 ,y+=30  ,width+90 ,height    );
		x=70;width=300;
		this.panel.add(btngenerar_reporte).setBounds            (x     ,y+=35  ,width    ,height*2  );
		this.cont.add(panel);
		
		btngenerar_reporte.addActionListener(opGenerar_reporte);
		cmbConcepto.addActionListener(op_seleccion_reporte);
		 c_inicio.setEnabled(false);
		 c_final.setEnabled(false);
		 cmbcuenta_bancaria.setEnabled(false);
		 
	}
	
	public String validar_campos(){
		String error = "";	
		String fechainicioNull = c_inicio.getDate()+"";
		String fechafinalNull = c_final.getDate()+"";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
		Date fecha1=null;
		Date fecha2=null;
		
	    if(fechainicioNull.equals("null")){
	    	error+= "Fecha  inicio\n";      
	    }else{ 
	    	  String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
	    	   fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
	    };
	    
		if(fechafinalNull.equals("null")) {
		     error+= "Fecha  final\n";
		}else {
		    String fecha_final  = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+"  23:59:00";
			fecha2 = sdf.parse(fecha_final , new ParsePosition(0));	
		}
		
		if(!fechainicioNull.equals("null")){
			if(!fechafinalNull.equals("null")){
		      if(fecha2.before(fecha1))error+="La Fecha Esta Invertida Final\n"  ;
			}
		}
		if(cmbConcepto.getSelectedIndex()==0) {
			error+="Seleccionar Un Reporte";
		}
		return error;
	}
	
	ActionListener op_seleccion_reporte = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			 String concepto=cmbConcepto.getSelectedItem().toString().trim();
			 if(concepto.equals("Selecciona Un Reporte")){
				 c_inicio.setEnabled(false);
				 c_final.setEnabled(false);
				 cmbcuenta_bancaria.setEnabled(false);
			 }
			 if(concepto.equals("Saldo de Banco Interno en un Periodo por Cuenta")){
				 c_inicio.setEnabled(true);
				 c_final.setEnabled(true);
				 cmbcuenta_bancaria.setEnabled(true);
				 cmbcuenta_bancaria.showPopup();
			 }
		}
	};
	
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
						if(validar_campos().equals("")){
							 String concepto=cmbConcepto.getSelectedItem().toString().trim();
							 String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
							 String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+" 23:59:58";
								
								if(concepto.equals("Saldo de Banco Interno en un Periodo por Cuenta" )){
									comando="exec banco_interno_reporte_estado_de_cuenta '"+fecha_inicio.substring(0, 10)+"','"+fecha_final+"','"+cmbcuenta_bancaria.getSelectedItem().toString().trim()+"'";
									reporte ="Obj_Reporte_De_Saldo_Banco_Interno.jrxml";
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
			new Cat_Reportes_De_Movimientos_De_Banco_Interno().setVisible(true);
		}catch(Exception e){	}
	}

}
