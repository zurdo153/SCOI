package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Venta_Por_Asignacion_De_Pilas_y_Cigarros extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio de la Rececion de Transferencia", 15, "String");
	JTextField txtFolio_scoi = new Componentes().text(new JCTextField(), "Folio Empleado", 15, "String");
	JTextField txtNombre_Cajero = new Componentes().text(new JCTextField(), "Nombre Del Cajero", 15, "String");
	JTextField txtfecha_incial = new Componentes().text(new JCTextField(), "Fecha Inicial", 15, "String");
	JTextField txtfecha_final = new Componentes().text(new JCTextField(), "Fecha Final", 15, "String");
	
	
	JButton btnReporte = new JButton("Reporte De Ventas",new ImageIcon("imagen/plan-icono-5073-16.png"));
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	int tipo_Reporte = 0;
	
	public Cat_Reporte_De_Venta_Por_Asignacion_De_Pilas_y_Cigarros(){
		setSize(425,220);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		cont.add(panel);
		setTitle("Reporte De Ventas Por Asignacion De Cigarros y Pilas");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/presentar-una-pluma-para-escribir-icono-9594-32.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Teclea El Folio De La Asignacion 3 Minutos Despues De La Ultima Venta"));
		
		int x=15,y=25,width=100,height=20;
		panel.add(new JLabel("Asignacion:")).setBounds(x, y, width, height);
		panel.add(txtFolio).setBounds(x+60,y,width,height);
		panel.add(txtFolio_scoi).setBounds(x+180, y, width, height); 
		panel.add(btnDeshacer).setBounds(x+290, y, width, height); 
		panel.add(txtNombre_Cajero).setBounds(x, y+=30, width*4-10, height);
		panel.add(txtfecha_incial).setBounds(x, y+=30, width+50, height);
		panel.add(txtfecha_final).setBounds(x+240, y, width+50, height);
		panel.add(btnReporte ).setBounds(x+120, y+=50, width+50, height);

		txtFolio_scoi.setEditable(false);
		txtNombre_Cajero.setEditable(false);
		txtfecha_incial.setEditable(false);
		txtfecha_final.setEditable(false);
		btnReporte.setEnabled(false);
		
		txtFolio.addKeyListener(Buscar_Datos_Producto);
		btnDeshacer.addActionListener(opDeshacer);
		btnReporte.addActionListener(opGenerar);
		
		 ///deshacer con escape
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        getRootPane().getActionMap().put("escape", new AbstractAction(){
            public void actionPerformed(ActionEvent e)
            {                 	    btnDeshacer.doClick();
          	    }
        });
	}
	
	
	ActionListener opDeshacer = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			txtFolio.setText("");
			txtFolio_scoi.setText("");
			txtNombre_Cajero.setText("");
			txtfecha_final.setText("");
            txtfecha_incial.setText(""); 
            btnReporte.setEnabled(false);
            txtFolio.setEditable(true);
            txtFolio.requestFocus();
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
					llenado_asignacionn();
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
	
	public void  llenado_asignacionn(){
		String query ="SELECT cajeros.e_mail,cajeros.nombre,convert(varchar(10),asignaciones_cajeros.fecha_inicial,103)+' 00:00:01' as fecha_inicial,case when (asignaciones_cajeros.fecha_liquidacion is null) then convert(varchar(10),getdate(),103)+' 23:59:59' else convert (varchar(10),asignaciones_cajeros.fecha_liquidacion,103)+' 23:59:59' end as fecha_final "
			      	+ "FROM asignaciones_cajeros  "
			      	+ "	inner join cajeros on cajeros.cajero=asignaciones_cajeros.cajero  where Folio='"+txtFolio.getText().toString().toUpperCase()+"'";
	
		try { Connexion con = new Connexion();
			  Statement s = con.conexion_IZAGAR().createStatement();
			  ResultSet rs = s.executeQuery(query);
			while(rs.next()){
				   txtFolio_scoi.setText(rs.getString(1).toString());
				   txtNombre_Cajero.setText(rs.getString(2).toString());
				   txtfecha_incial.setText(rs.getString(3).toString());
				   txtfecha_final.setText(rs.getString(4).toString());
			      }
			btnReporte.requestFocus();
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en la funcion existe_asignacion \n SQLException: "+e1.getMessage()+query, "Avisa al Administrador del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		}
	}

	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Obj_Usuario usuario = new Obj_Usuario().LeerSession();
			String basedatos="2.200";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="exec sp_Reporte_IZAGAR_De_Ventas_De_Cigarros_y_Pilas_Por_Asignacion '"+txtFolio.getText().toString().toUpperCase()+"','"+usuario.getNombre_completo()+"'" ;
			String reporte = "Obj_Reporte_IZAGAR_de_Venta_De_Cigarros_y_Pilas_Por_Asignacion_Ticket.jrxml";
							 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
	};
	
	
	
	public static void main(String args[]){
		try{			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Venta_Por_Asignacion_De_Pilas_y_Cigarros().setVisible(true);
		}catch(Exception e){	}	}
}
