package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Contratacion_Por_Empleado extends JDialog{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio del Empleado", 10, "String");
	JTextField txtNombreEmpleado = new Componentes().text(new JTextField(), "Nombre del Empleado",100, "String");
	JTextField txtEstablecimiento = new Componentes().text(new JTextField(), "Establecimiento",100, "String");
	JTextField txtDepartamento = new Componentes().text(new JTextField(), "Departamento",100, "String");
	JTextField txtPuesto = new Componentes().text(new JTextField(), "Puesto",100, "String");
	JTextField txtSexo = new Componentes().text(new JTextField(), "Sexo",100, "String");
	JTextField txtEstadoCivil = new Componentes().text(new JTextField(), "Estado_Civil",100, "String");
	JTextField txtEdad = new Componentes().text(new JTextField(), "Edad",100, "String");
	JTextField txtDomicilio = new Componentes().text(new JTextField(), "Domicilio",100, "String");
	JTextField txtHorario = new Componentes().text(new JTextField(), "Horario",100, "String");
	JTextField txtSueldo_Base = new Componentes().text(new JTextField(), "Sueldo Base",100, "Double");
	JTextField txtSueldo_Letra = new Componentes().text(new JTextField(), "Sueldo En Letra",100, "String");
	JTextField txtTemporada = new Componentes().text(new JTextField(), "Temporada Para La Se Contrato",100, "String");
	JTextField txtTestigo1 = new Componentes().text(new JTextField(), "Nombre Completo del Testigo 1",100, "String");
	JTextField txtTestigo2 = new Componentes().text(new JTextField(), "Nombre Completo del Testigo 2",100, "String");
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBdepartamento= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	JLabel JLBpuesto= new JLabel(new ImageIcon("Imagen/verde-de-usuario-icono-7340-16.png") );
	JLabel JLBSexoM= new JLabel(new ImageIcon("Imagen/macho-icono-8093-16.png") );
	JLabel JLBSexoF= new JLabel(new ImageIcon("Imagen/hembra-icono-8352-16.png") );
	JLabel JLBEstado_civil= new JLabel(new ImageIcon("Imagen/Estado_Civil_16.png") );
	JLabel JLBEdad= new JLabel(new ImageIcon("Imagen/key-group-icone-5159-16.png") );
	JLabel JLBDomicilio= new JLabel(new ImageIcon("Imagen/inicio-inicio-icono-8866-16.png"));
	JLabel JLBHorario= new JLabel(new ImageIcon("Imagen/horas-reloj-icono-9852-16.png"));
	JLabel JLBSueldo= new JLabel(new ImageIcon("Imagen/dinero-icono-8797-16.png") );
	JLabel JLBTestigo = new JLabel(new ImageIcon("Imagen/icono_testigo_16.png") );
	JLabel JLBTestigo2 = new JLabel(new ImageIcon("Imagen/icono_testigo_16.png") );
	
	
	JButton btngenerar = new JButton("Generar",new ImageIcon("imagen/buscar.png"));
	
	String Cantidad_Letra="";
	double numero=0;
	public Cat_Reportes_De_Contratacion_Por_Empleado(String Folio,String Nombre,String Establecimiento,String Departamento,String Puesto, String Sexo, String Estado_Civil
			                                        ,String Edad ,String Domicilio ,String Sueldo,String NombreUsuario, String Horario){

         numero= Double.valueOf(Sueldo);

	     
		this.setSize(440, 500);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	     
		panel.setBorder(BorderFactory.createTitledBorder("Verifique La Informacion Antes De Imprimir"));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Lista.png"));
		this.setTitle("Reportes De Contratacion Del Empleado");
		
		int x=15, y=25,l=50,a=20;
		
		panel.add(txtFolio).setBounds(60,25,40,a);
		txtFolio.setEditable(false);
		txtFolio.setText(Folio);
				
		panel.add(new JLabel("Nombre:")).setBounds(x,y,l*2,a);
		panel.add(txtNombreEmpleado).setBounds(x+85,y,320,a);
		txtNombreEmpleado.setEditable(false);
        txtNombreEmpleado.setText(Nombre);
        
		panel.add(new JLabel("Establecimiento:")).setBounds(x,y+=30,80,a);
		panel.add(JLBestablecimiento).setBounds(x+=75,y,a,a);
		panel.add(txtEstablecimiento).setBounds(110,y,310,a);
		txtEstablecimiento.setEditable(false);
        txtEstablecimiento.setText(Establecimiento);
        
         x=15;
        
		panel.add(new JLabel("Departamento:")).setBounds(x,y+=30,80,a);
		panel.add(JLBdepartamento).setBounds(x+=75,y,a,a);
		panel.add(txtDepartamento).setBounds(110,y,310,a);
		txtDepartamento.setEditable(false);
        txtDepartamento.setText(Departamento);    
        
        x=15;
        
		panel.add(new JLabel("Puesto:")).setBounds(x,y+=30,80,a);
		panel.add(JLBpuesto).setBounds(x+=75,y,a,a);
		panel.add(txtPuesto).setBounds(110,y,310,a);
		txtPuesto.setEditable(false);
        txtPuesto.setText(Puesto);    
        
         x=15;
        
		panel.add(new JLabel("Sexo:")).setBounds(x,y+=30,80,a);
		panel.add(JLBSexoM).setBounds(x+=30,y,a,a);
		panel.add(JLBSexoF).setBounds(x+=15,y,a,a);
		panel.add(txtSexo).setBounds(x+20,y,l+40,a);
		txtSexo.setEditable(false);
        txtSexo.setText(Sexo);        
        
		panel.add(new JLabel("Estado Civil:")).setBounds(x+=125,y,80,a);
		panel.add(JLBEstado_civil).setBounds(x+=60,y,a,a);
		panel.add(txtEstadoCivil).setBounds(x+20,y,l+20,a);
//		txtEstadoCivil.setEditable(false);
        txtEstadoCivil.setText(Estado_Civil);     
        
		panel.add(new JLabel("Edad:")).setBounds(x+=100,y,l,a);
		panel.add(JLBEdad).setBounds(x+=25,y,a,a);
		panel.add(txtEdad).setBounds(x+20,y,l-20,a);
		txtEdad.setEditable(false);
		txtEdad.setText(Edad);   
        
	    x=15;
	        
		panel.add(new JLabel("Domicilio:")).setBounds(x,y+=30,80,a);
		panel.add(JLBDomicilio).setBounds(x+=45,y,a,a);
		panel.add(txtDomicilio).setBounds(x+=20,y,340,a);
		txtDomicilio.setEditable(false);
		txtDomicilio.setText(Domicilio);    
		 x=15;
	        
		panel.add(new JLabel("Horario:")).setBounds(x,y+=30,80,a);
		panel.add(JLBHorario).setBounds(x+=45,y,a,a);
		panel.add(txtHorario).setBounds(x+=20,y,340,a);
		txtHorario.setEditable(false);
		txtHorario.setText(Horario); 
			
			
			
 	    x=15;
 	   
		panel.add(new JLabel("Sueldo:")).setBounds(x,y+=30,80,a);
		panel.add(JLBSueldo).setBounds(x+=45,y,a,a);
		panel.add(txtSueldo_Base).setBounds(x+20,y,l,a);
		txtSueldo_Base.setText(Sueldo);    
		panel.add(txtSueldo_Letra).setBounds(x+=69,y,290,a);
		txtSueldo_Letra.setEditable(false); 
			
	    x=15;
		        
		panel.add(new JLabel("Temporada:")).setBounds(x,y+=30,80,a);
		panel.add(txtTemporada).setBounds(x+=65,y,340,a);

        x=15;
        
		panel.add(new JLabel("Testigo 1:")).setBounds(x,y+=30,80,a);
		panel.add(JLBTestigo).setBounds(x+=45,y,a,a);
		panel.add(txtTestigo1).setBounds(x+=20,y,340,a);
  
		
		x=15;
        
		panel.add(new JLabel("Testigo 2:")).setBounds(x,y+=30,80,a);
		panel.add(JLBTestigo2).setBounds(x+=45,y,a,a);
		panel.add(txtTestigo2).setBounds(x+=20,y,340,a);
		txtTestigo2.setEditable(false);
		txtTestigo2.setText(NombreUsuario); 
       x=15;
               
		panel.add(new JLabel("Fecha Inicio:")).setBounds(x,y+=30,l*2,a);
		panel.add(JLBlinicio).setBounds(x+=60,y,a,a);
		panel.add(c_inicio).setBounds(x+=20,y,80,a);
		c_inicio.setEnabled(false);
		
		panel.add(new JLabel("Fecha Final:")).setBounds(x+=165,y,l*2,a);
		panel.add(JLBfin).setBounds(x+=60,y,a,a);
		panel.add(c_final).setBounds(x+=20,y,80,a);
		c_final.setEnabled(true);
				
		panel.add(btngenerar).setBounds(150, y+=80, l*2, a);
		
		cont.add(panel);
		cargar_fechas();
		btngenerar.addActionListener(opGenerar);
	 
		 Cargar_Cantidad_Letra();
 	     txtSueldo_Base.addKeyListener(oprecalcular_cantidad_letra);
 	     txtEstadoCivil.addKeyListener(oppasar_a_sueldo);
	     
		
//      asigna el foco al JTextField folio_factura al arrancar la ventana y agrega al proveedor como nuevo
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened( WindowEvent e ){
                   txtEstadoCivil.requestFocus();
             }
        });
		

	}
	
	KeyListener oppasar_a_sueldo = new KeyListener(){
		public void keyReleased(KeyEvent e1) {
		 }
		public void keyTyped(KeyEvent e1) {
		  }
		public void keyPressed(KeyEvent e1) {
			if(e1.getKeyCode()==KeyEvent.VK_ENTER){
		        	   txtSueldo_Base.requestFocus();
			}
		 }	
	    };

	    
	KeyListener oprecalcular_cantidad_letra = new KeyListener(){
		public void keyReleased(KeyEvent e1) {
		}
		public void keyTyped(KeyEvent e1) {
		}
		public void keyPressed(KeyEvent e1) {
			if(e1.getKeyCode()==KeyEvent.VK_ENTER){
				if(txtSueldo_Base.getText().equals("")){
					numero=0;
				}else{				
				numero=Double.valueOf(txtSueldo_Base.getText());}
		           if(numero>20000){ txtSueldo_Base.setText("");
		                             numero=0;   }
		           if(numero==0){
		   			JOptionPane.showMessageDialog(null,"El Sueldo Esta Vacio o El Numero Es Muy Grande ","Aviso!", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
		           }else{
		        	   Cargar_Cantidad_Letra();
		        	   txtTemporada.requestFocus();
		           }
			}
		}	
	    };
	    
	public void cargar_fechas(){
		Date date1 = null;
				  try {
					date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(0));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		c_inicio.setDate(date1);
	    Date date2 = null;
					  try {
						date2 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(-30));
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
		c_final.setDate(date2);
	};
	
	public void Cargar_Cantidad_Letra(){
	try {
		Cantidad_Letra = (new BuscarSQL().numero_a_letra(numero));
	     txtSueldo_Letra.setText(Cantidad_Letra);
		  } catch (SQLException e1) {e1.printStackTrace();}
	}
	
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
     if(validar_fechas().equals("")){
    	    String Nombre_CompletoV =txtNombreEmpleado.getText()+"";
			String EstablecimientoV=txtEstablecimiento.getText()+"";
			String DepartamentoV=txtDepartamento.getText()+"";
			String PuestoV=txtPuesto.getText()+"";
			String SexoV=txtSexo.getText()+"";
			String Estado_CivilV=txtEstadoCivil.getText().toUpperCase().toString().trim();
			String EdadV=txtEdad.getText()+"";
			String DomicilioV=txtDomicilio.getText();
			String HorarioV=txtDomicilio.getText();
			String SueldoV=txtSueldo_Base.getText();
			String Sueldo_LetraV=txtSueldo_Letra.getText();
			String TemporadaV=txtTemporada.getText().toUpperCase().trim();
			String Testigo1=txtTestigo1.getText();
			String testigo2=txtTestigo2.getText();
			String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate());
			String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate());
			String folio_empleado=txtFolio.getText()+"";
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="";
			String reporte = "Obj_Reporte_De_Contrato.jrxml";
				comando = "exec sp_Reporte_De_Contrato '" +Nombre_CompletoV+"','"+EstablecimientoV+"','"+DepartamentoV+"','"+PuestoV+"','"+SexoV+"','"+Estado_CivilV+"','"+EdadV+"','"+DomicilioV+"','"+HorarioV
						+"','"+SueldoV+"','"+Sueldo_LetraV+"','"+TemporadaV+"','"+Testigo1+"','"+testigo2+"','"+fecha_inicio+"','"+fecha_final+"',"+folio_empleado;
      		 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
      		 
	 	}else{
			JOptionPane.showMessageDialog(null,"Los siguientes campos están vacíos: "+validar_fechas(),"Aviso!", JOptionPane.ERROR_MESSAGE);
			return;
		}
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
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Contratacion_Por_Empleado("1","MARCO ANTONIO BODART GUZMAN","SISTEMAS","SISTEMAS","LIDER SISTEMAS","MASCULINO","CASADO","35"," COL LA CUCHILLA ELDORADO,SINALOA","2025","NOMBRE COMPLETO USUARIO SCOI","8-7").setVisible(true);
		}catch(Exception e){	}
	}
}
