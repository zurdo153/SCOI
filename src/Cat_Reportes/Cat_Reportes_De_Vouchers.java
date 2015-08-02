package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import com.toedter.calendar.JDateChooser;
import Cat_Filtros_IZAGAR.Cat_Filtro_De_Busqueda_De_Asignaciones;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Vouchers extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento_Empleados();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);

	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio del Corte", 800, "String");
	
	JButton btnReporte_porfecha_estab_Exportar_deun_estab = new JButton("",new ImageIcon("imagen/tarjeta-de-credito-visa-icono-8242-16.png"));
	JButton btnReporte_porfecha_estab_Exportar = new JButton("",new ImageIcon("imagen/hoja-de-calculo-excel-icono-5223-16.png"));
	JButton btnReporte_asignacion = new JButton("",new ImageIcon("imagen/hoja-de-calculo-excel-icono-5223-16.png"));
    JButton btnSeleccionAsignacion =new JButton("",new ImageIcon ("imagen/Filter-List-icon16.png"));
	JButton btngenerar = new JButton("Generar",new ImageIcon("imagen/buscar.png"));
	
	JDateChooser cfecha = new JDateChooser();
	
	int tipo_Reporte = 0;
	String Asignacion;
	
	public Cat_Reportes_De_Vouchers(String asignacion){
		Asignacion=asignacion+"";
		setSize(320,380);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Reportes De Vouchers");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/tarjeta-de-credito-visa-icono-8242-32.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Seleccione el Tipo de Reporte"));
	
		btnReporte_porfecha_estab_Exportar_deun_estab.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Vouchers Por Establecimiento De Una Fecha</p></CENTER></FONT>" +
				"</html>");
		
		btnReporte_porfecha_estab_Exportar.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Vouchers Por Establecimiento De Una Fecha Para Exportar </p></CENTER></FONT>" +
				"</html>");	
		
		btnReporte_asignacion.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Vouchers Por Asignacion Para Exportar</p></CENTER></FONT>" +
				"</html>");	
		
		panel.add(btnReporte_porfecha_estab_Exportar_deun_estab).setBounds(10,25,295,40);
		panel.add(btnReporte_porfecha_estab_Exportar).setBounds(10,75,295,40);
		panel.add(btnReporte_asignacion).setBounds(10,125,295,40);
		
		panel.add(new JLabel("Folio De Asignacion:")).setBounds(10,180,200,20);		
		panel.add(txtFolio).setBounds(110,180,175,20);
		panel.add(btnSeleccionAsignacion).setBounds(285,180,20,20);
		panel.add(new JLabel("Fecha De Corte:")).setBounds(10,220,200,20);
		panel.add(cfecha).setBounds(110,220,195,20);
		panel.add(new JLabel("Establecimiento:")).setBounds(10,260,200,20);
		panel.add(cmbEstablecimiento).setBounds(110,260,195,20);
		
		panel.add(btngenerar).setBounds(100,300,120,30);
	    
	    txtFolio.setEditable(false);
	    cfecha.setEnabled(false);
	    btngenerar.setEnabled(false);
	    btnSeleccionAsignacion.setEnabled(false);
	    cmbEstablecimiento.setEnabled(false);
	    
	    if (!Asignacion.equals("")){
	    	txtFolio.setEditable(true);
			cfecha.setEnabled(false);
			btngenerar.setEnabled(true);
			tipo_Reporte=3;
			btnSeleccionAsignacion.setEnabled(true);
			txtFolio.setText("");
			cfecha.setDate(null);
			txtFolio.requestFocus();
			cmbEstablecimiento.setEnabled(false);
			txtFolio.setText(Asignacion+"");
		}
		
		cont.add(panel);
		btngenerar.addActionListener(opGenerar);
		btnReporte_porfecha_estab_Exportar_deun_estab.addActionListener(opReporte_Por_Fecha);
		btnReporte_porfecha_estab_Exportar.addActionListener(opReporte_Por_Fecha_para_exportar);
		btnReporte_asignacion.addActionListener(opReporte_por_asignacion);
		btnSeleccionAsignacion.addActionListener(opfiltroAsignacion);
		txtFolio.addKeyListener(op_buscar_enter);
		cfecha.addKeyListener(op_buscar_enter);
     	
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "buscarAsignacion");
          getRootPane().getActionMap().put("buscarLR", new AbstractAction(){
         public void actionPerformed(ActionEvent e)
         {                 	    btngenerar.doClick();
       	    }
     });
		
	}

	KeyListener op_buscar_enter = new KeyListener() {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				btngenerar.doClick();
			}
		}
		public void keyPressed(KeyEvent e) {}
	};
	
	
	
	ActionListener opReporte_Por_Fecha = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			txtFolio.setEditable(false);
			cfecha.setEnabled(true);
			btngenerar.setEnabled(true);
			btnSeleccionAsignacion.setEnabled(false);
			tipo_Reporte=1;
			cmbEstablecimiento.setSelectedIndex(0);
			cmbEstablecimiento.setEnabled(true);
			cfecha.setDate(null);
			cfecha.requestFocus();
			
		}
	};
	
	ActionListener opReporte_Por_Fecha_para_exportar = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			txtFolio.setEditable(false);
			cfecha.setEnabled(true);
			btngenerar.setEnabled(true);
			btnSeleccionAsignacion.setEnabled(false);
			tipo_Reporte=2;
			cmbEstablecimiento.setEnabled(true);
			cfecha.setDate(null);
			cfecha.requestFocus();
		}
	};

	ActionListener opReporte_por_asignacion = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			txtFolio.setEditable(true);
			cfecha.setEnabled(false);
			btngenerar.setEnabled(true);
			tipo_Reporte=3;
			btnSeleccionAsignacion.setEnabled(true);
			txtFolio.setText("");
			cfecha.setDate(null);
			txtFolio.requestFocus();
			cmbEstablecimiento.setEnabled(false);
		}
	};
	
	ActionListener opfiltroAsignacion = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			new Cat_Filtro_De_Busqueda_De_Asignaciones("Reportes De Vouchers").setVisible(true);
			dispose();
		}
	};
	
	
	public String validar_fechas(){
		String error = "";
		@SuppressWarnings("unused")
		String fechafinalNull = cfecha.getDate()+"";
	    if(cfecha.equals("null"))error+= "Fecha\n";
		return error;
	}
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="";
			String reporte = "";
		    String fechaNull = cfecha.getDate()+"";
			if(tipo_Reporte==1){
				         if(fechaNull.equals("null")){
								JOptionPane.showMessageDialog(null,"Necesita Selecionar una Fecha o la Fecha tecleada es Incorrecta","Mensaje",JOptionPane.WARNING_MESSAGE);
								return;
				         }
				         else{  
							String fecha = new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate());		
							comando = "exec sp_Reporte_De_Vouchers_Por_Establecimiento_A_Una_Fecha_Determinada '"+fecha+"','"+cmbEstablecimiento.getSelectedItem().toString()+"','mabg'";
							reporte="Obj_Reporte_De_Vouchers_Por_Establecimiento_De_Una_Fecha.jrxml";
							 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);	
				         }
			}if(tipo_Reporte==2){
				 if(fechaNull.equals("null")){
						JOptionPane.showMessageDialog(null,"Necesita Selecionar una Fecha o la Fecha tecleada es Incorrecta","Mensaje",JOptionPane.WARNING_MESSAGE);
						return;
		         }
		         else{  
					  String fecha = new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate());		
					  comando = "exec sp_Reporte_De_Vouchers_Por_Establecimiento_A_Una_Fecha_Determinada '"+fecha+"','"+cmbEstablecimiento.getSelectedItem().toString()+"','mabg'";
				 	  reporte="Obj_Reporte_De_Vouchers_Por_Establecimiento_De_Una_Fecha_Para_Exportar.jrxml";
					  new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);	
 					 return;	
		         }
			}if(tipo_Reporte==3){ 
				if(txtFolio.getText().equals("")){
					JOptionPane.showMessageDialog(null,"Necesitas Teclear Una Asignacion","Mensaje",JOptionPane.WARNING_MESSAGE);				
				}else{
					comando = "exec sp_Reporte_De_Vouchers_Por_Establecimiento_A_Una_Fecha_Determinada '01/01/1900','"+cmbEstablecimiento.getSelectedItem().toString()+"','"+txtFolio.getText().toString().toUpperCase().trim()+"'";
					reporte = "Obj_Reporte_De_Vouchers_Por_Establecimiento_De_Una_Fecha_Para_Exportar.jrxml";
					new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);	
				return;	
         }
			}	
			}
		
	};
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Reportes_De_Vouchers("").setVisible(true);
			}catch(Exception e){	}
		}
		
	}



