package Cat_Reportes;

import java.awt.AWTException;
import java.awt.Container;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;


@SuppressWarnings("serial")
public class Cat_Reporte_De_Impresion_De_Polizas extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	public String[] tipo(){
		try {			return new Cargar_Combo().tipos_de_polizas();
		} catch (SQLException e) {	e.printStackTrace(); }
		return null; 
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbTipo = new JComboBox(tipo());
	
	JTextField txtFolio = new Componentes().text(new JCTextField(), "Folio de la Poliza", 15, "String");
	JDateChooser Fecha 	= new JDateChooser();
	
	JButton btnReporte = new JButton("Reporte De Ventas",new ImageIcon("imagen/plan-icono-5073-16.png"));
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	int tipo_Reporte = 0;
	
	String tipo_poliza,folio_poliza,mes_año;
	
	public Cat_Reporte_De_Impresion_De_Polizas(String TipoPoliza,String MesAño, String FolioPoliza){
		tipo_poliza  = TipoPoliza;
		mes_año=MesAño;
		folio_poliza = FolioPoliza;
		
		cmbTipo.setSelectedItem(TipoPoliza);
		Fecha.setDate(cargar_fecha(mes_año));
		txtFolio.setText(FolioPoliza);
		
		Constructor();

		cmbTipo.setEnabled(false);
		txtFolio.setEditable(false);
		Fecha.setEnabled(false);
	}
	
	public Cat_Reporte_De_Impresion_De_Polizas(){
		Constructor();
	}
	
	public void Constructor(){
		setSize(425,220);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		cont.add(panel);
		setTitle("Reporte De Impresion De Polizas");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/presentar-una-pluma-para-escribir-icono-9594-32.png"));
		panel.setBorder(BorderFactory.createTitledBorder("p"));
		
		int x=15,y=25,width=100,height=20;
		
		panel.add(new JLabel("Tipo:")).setBounds(x, y, width/2, height);
		panel.add(cmbTipo).setBounds(x+=30, y, width, height);
		panel.add(Fecha).setBounds(x+=width+10,y,width+5,height);
		panel.add(new JLabel("Poliza:")).setBounds(x+=width+10, y, width/2, height);
		panel.add(txtFolio).setBounds(x+=35,y,width+5,height);
		
		x=15;
		panel.add(btnReporte ).setBounds(x+120, y+=50, width+50, height);
		panel.add(btnDeshacer).setBounds(x+290, y, width, height); 
		
		
//		btnReporte.setEnabled(false);
		
		txtFolio.addKeyListener(Buscar_Datos_Producto);
		btnDeshacer.addActionListener(opDeshacer);
		btnReporte.addActionListener(opGenerar);
		
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        getRootPane().getActionMap().put("escape", new AbstractAction(){
            public void actionPerformed(ActionEvent e)
            {  	    btnDeshacer.doClick();	    }
        });
	}
	
	public Date cargar_fecha( String mesaño){
		Date date1 = null;
		  try {
			date1 = new SimpleDateFormat("dd/MM/yyyy").parse(mesaño);
		} catch (ParseException e) {
			e.printStackTrace();
		}
      return date1;
	};
	
	public void buscarAutomatico(){
		Robot robot;
		try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            e.printStackTrace();
        }
	 };
	 
	ActionListener opDeshacer = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			txtFolio.setText("");
            btnReporte.setEnabled(false);
            txtFolio.setEditable(true);
            txtFolio.requestFocus();
            Fecha.setDate(null);
    		cmbTipo.setEnabled(true);
    		txtFolio.setEditable(true);
    		Fecha.setEnabled(true);
		}
	};
	
	
	KeyListener Buscar_Datos_Producto = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				if(txtFolio.getText().equals("")){
		   			JOptionPane.showMessageDialog(null,"Es Necesario Teclear Una Asignacion Para Hacer La Busqueda","Aviso!", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
		   			return;
				}
				if(existe_asignacion()){
					btnReporte.setEnabled(true);
					txtFolio.setEditable(false);
				}else{
		   			JOptionPane.showMessageDialog(null,"El Folio De Asignacion:"+txtFolio.getText().toString()+" No Existe","Aviso!", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
		   			return;
				}
		}
		}
	};
	

	public boolean existe_asignacion(){
		String query ="IF(SELECT COUNT(folio) FROM asignaciones_cajeros  where Folio='"+txtFolio.getText().toString().toUpperCase()+"' )=1 "
				     +" SELECT 'true' ELSE SELECT 'false' ";
		boolean existe = false;
		try { Connexion con = new Connexion();
			  Statement s = con.conexion_IZAGAR().createStatement();
			  ResultSet rs = s.executeQuery(query);
			while(rs.next()){
			    	existe = rs.getBoolean(1);
			      }
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en la funcion existe_asignacion \n SQLException: "+e1.getMessage()+query, "Avisa al Administrador del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		}
      return existe;
	}
	

	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String fecha = new SimpleDateFormat("dd/MM/yyyy").format(Fecha.getDate())+" 00:00:00";
			Obj_Usuario usuario = new Obj_Usuario().LeerSession();
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="exec sp_consulta_de_poliza '"+cmbTipo.getSelectedItem().toString().toUpperCase()+"','"+fecha+"','"+txtFolio.getText().toString().toUpperCase()+"','"+usuario.getNombre_completo()+"'" ;
			String reporte = "Obj_Reporte_De_Consulta_De_Poliza.jrxml";
							 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
	};
	
	
	public static void main(String args[]){
		try{			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//			new Cat_Reporte_De_Impresion_De_Polizas().setVisible(true);
			new Cat_Reporte_De_Impresion_De_Polizas("COMPRAS","01/09/2015","C019").setVisible(true);
			
		}catch(Exception e){	}	}
}
