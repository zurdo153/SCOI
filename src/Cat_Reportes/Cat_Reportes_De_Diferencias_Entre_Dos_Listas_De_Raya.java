package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import Cat_Lista_de_Raya.Cat_Filtro_De_Listas_De_Raya_Pasadas;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Diferencias_Entre_Dos_Listas_De_Raya extends JFrame {
	 Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	
	 JTextField txtCampo1 = new Componentes().text(new JCTextField(), "Lista de Raya 1", 10, "Int");
	 JTextField txtcampo2 = new Componentes().text(new JCTextField(), "Lista de Raya 2", 10, "Int");
	 JButton btnReporte = new JButton("",new ImageIcon("imagen/Text preview.png"));
	 JButton btnReporte2 = new JButton("",new ImageIcon("imagen/Text preview.png"));
	 JButton btnSeleccionLR1 =new JButton("",new ImageIcon ("imagen/Filter-List-icon16.png"));
	 JButton btnSeleccionLR2 =new JButton("",new ImageIcon ("imagen/Filter-List-icon16.png"));
	 
	 Container cont = getContentPane();
 	 JLayeredPane panel = new JLayeredPane();
	 Border blackline, etched, raisedbevel, loweredbevel, empty;
	 TitledBorder title4; 

    public Cat_Reportes_De_Diferencias_Entre_Dos_Listas_De_Raya(){
			Constructor();
	}
    public Cat_Reportes_De_Diferencias_Entre_Dos_Listas_De_Raya(int campo1,int campo2){
		Constructor();
		
   	}
		
	public void Constructor() {
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/dinero-en-efectivo-cartera-monedero-icono-7127-32.png"));
		 blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		 panel.setBorder(BorderFactory.createTitledBorder(blackline,"Teclee Los Folios De Lista De Raya Que Desea Comparar"));
		this.setTitle("Diferencias Entre Dos Listas De Raya");
		 btnReporte.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Diferencias Entre Dos Listas De Raya Por Establecimiento</p></CENTER></FONT>" +
				"</html>");
		 btnReporte2.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
					"		<CENTER><p>Reporte De Diferencias Entre Dos Listas De Raya Por Concepto</p></CENTER></FONT>" +
					"</html>");
		 
		 int x=20,y=25,width=100,height=20;
		 
		panel.add(txtCampo1).setBounds(x, y, width, height);
//		panel.add(btnSeleccionLR1).setBounds(x+width, y, height, height);
		panel.add(txtcampo2).setBounds(x+180, y, width, height);
		panel.add(btnSeleccionLR2).setBounds(x+180+width, y, height, height);
		panel.add(btnReporte).setBounds(x,y+=40,width*3,height*2);
		panel.add(btnReporte2).setBounds(x,y+=60,width*3,height*2);
		
		btnReporte.addActionListener(opGenerar);
		btnReporte2.addActionListener(opGenerar2);
		
		btnSeleccionLR1.addActionListener(opfiltroLR1);
		btnSeleccionLR2.addActionListener(opfiltroLR2);
		
		cont.add(panel);
		this.setSize(350,220);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(txtCampo1.getText().toString().equals("")||txtcampo2.getText().toString().equals("")){
			  JOptionPane.showMessageDialog(null,"Tiene Que Alimentar Los folios De las Dos Listas De Raya Que Desea Comparar", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
               return;
			}else{
				if(Integer.valueOf(txtCampo1.getText().toString())<Integer.valueOf(txtcampo2.getText().toString())){	
					String basedatos="2.26";
					String vista_previa_reporte="no";
					int vista_previa_de_ventana=0;
					String comando="exec sp_Reporte_De_Diferiencias_Entre_Dos_Lista_De_Raya "+txtCampo1.getText().toString()+","+txtcampo2.getText().toString()+","+usuario.getFolio()+",'C'"  ;
					String reporte = "Obj_Reporte_De_Diferencias_Entre_Dos_Lista_de_Raya_Por_Establecimiento.jrxml";
				   new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
				}else{
					  JOptionPane.showMessageDialog(null,"El folio De La Lista De Raya 1 \nDebe De Ser Mayor Que \nEl  folio De La Lista De Raya 2 \nPara Poder Buscar", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
					  txtcampo2.setText("");
					  return;
				}
			}
		}
	};
	
	ActionListener opGenerar2 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(txtCampo1.getText().toString().equals("")||txtcampo2.getText().toString().equals("")){
			  JOptionPane.showMessageDialog(null,"Tiene Que Alimentar Los folios De las Dos Listas De Raya Que Desea Comparar", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
               return;
			}else{
				if(Integer.valueOf(txtCampo1.getText().toString())<Integer.valueOf(txtcampo2.getText().toString())){	
					String basedatos="2.26";
					String vista_previa_reporte="no";
					int vista_previa_de_ventana=0;
					String comando="exec sp_Reporte_De_Diferiencias_Entre_Dos_Lista_De_Raya "+txtCampo1.getText().toString()+","+txtcampo2.getText().toString()+","+usuario.getFolio()+",'D'"  ;
					String reporte = "Obj_Reporte_De_Diferencias_Entre_Dos_Lista_de_Raya_Por_Concepto.jrxml";
				   new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
				}else{
					  JOptionPane.showMessageDialog(null,"El folio De La Lista De Raya 1 \nDebe De Ser Mayor Que \nEl  folio De La Lista De Raya 2 \nPara Poder Buscar", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
					  txtcampo2.setText("");
					  return;
				}
			}
		}
	};
	
	ActionListener opfiltroLR1 = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			 new Cat_Filtro_De_Listas_De_Raya_Pasadas(6).setVisible(true);
		}
	};
	ActionListener opfiltroLR2 = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			 new Cat_Filtro_De_Listas_De_Raya_Pasadas(7).setVisible(true);
		}
	};
	
	String	Foliorecibido="";
	public  void obtiene_lista_de_raya_selecionadac1(final Integer folio){
			txtCampo1.setText(folio+"");
			System.out.println("valor"+folio);
			return;	
	}
	     
	
	public  void obtiene_lista_de_raya_selecionadac2(final Integer folio){
		 String	Foliorecibido = folio+"";
			txtcampo2.setText(Foliorecibido);
			return;	
		}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Diferencias_Entre_Dos_Listas_De_Raya().setVisible(true);
		}catch(Exception e){	}
	}
}
