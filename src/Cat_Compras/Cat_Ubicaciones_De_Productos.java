package Cat_Compras;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.lowagie.text.pdf.CMapAwareDocumentFont;

import Cat_Principal.Cat_Comandos;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Compras.Obj_Cotizaciones_De_Un_Producto;
import Obj_Compras.Obj_Ubicaciones_De_Productos;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;

@SuppressWarnings("serial")
public class Cat_Ubicaciones_De_Productos extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento201();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	JTextField txtcodigo_prod     = new Componentes().text(new JCTextField(), "Codigo del Producto", 15, "String");
	JCButton btndeshacer     = new JCButton("Deshacer","deshacer-icono-4321-32.png","Azul");
	JLabel   JLBcod_prod          = new JLabel("");  
	JLabel   JLBdescripcion       = new JLabel("");  
	
	JLabel   JLBLocalizacion      = new JLabel("");
	JLabel   JLBClase_De_Producto = new JLabel("");
	JLabel   JLBCategoria         = new JLabel("");  
	JLabel   JLBFamilia           = new JLabel("");
	JLabel   JLBArea              = new JLabel("");  
	JLabel   JLBUltimo_Costo      = new JLabel("");
	JLabel   JLBCosto_Promedio    = new JLabel(""); 
	JLabel   JLBExistencia_Cedis  = new JLabel("");
	JLabel   JLBExistencia_Total  = new JLabel("");
	JLabel   JLBprecio_de_venta   = new JLabel("");
	JLabel   JLBCantidad_Negada_Ultimos_7_dias = new JLabel("");

	
	
	String htmlini ="<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=BLACK><RIGHT><b><p>";
	String htmlfin ="</p></b></RIGHT></FONT></html>";
	
	String htmlinib ="<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=BLUE><RIGHT><b><p>";
	String htmlfinb ="</p></b></RIGHT></FONT></html>";
	
	public Cat_Ubicaciones_De_Productos(){
		setSize(700,600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Ubicaciones De Productos ");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Seleccione Establecimiento y Escanee El Codigo Del Producto"));
		
		int x=20, y=25, width=400,height=20,sep=30,sep2=140;
		panel.add(cmbEstablecimiento).setBounds                           (x      ,y       ,width   ,height   );
		panel.add(btndeshacer).setBounds                                  (x+450  ,y       ,width/2 ,height*2 );
		
		panel.add(new JLabel("Codigo Del Producto:")).setBounds           (x      ,y+=30   ,width   ,height   );
		panel.add(txtcodigo_prod).setBounds                               (x+120  ,y       ,280     ,height   );
		
		width=600;
		panel.add(new JLabel(htmlinib+"CODIGO:"+htmlfinb)).setBounds      (x       ,y+=sep  ,width   ,height   );
		panel.add(JLBcod_prod).setBounds                                  (x+sep2  ,y       ,width   ,height   );
		
		panel.add(new JLabel(htmlinib+"LOCALIZACION:"+htmlfinb)).setBounds(x+330   ,y       ,width   ,height   );
		panel.add(JLBLocalizacion).setBounds                              (x+480   ,y       ,width   ,height   );
		
		panel.add(new JLabel(htmlinib+"DESCRIPCION:"+htmlfinb)).setBounds (x       ,y+=sep  ,width   ,height   );
		panel.add(JLBdescripcion).setBounds                               (x+sep2  ,y       ,width   ,37       );
		panel.add(new JLabel(htmlinib+"CLASE:"+htmlfinb)).setBounds       (x       ,y+=sep*2,width   ,height   );
		panel.add(JLBClase_De_Producto).setBounds                         (x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"CATEGORIA:"+htmlfinb)).setBounds   (x       ,y+=sep  ,width   ,height   );
		panel.add(JLBCategoria).setBounds                                 (x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"FAMILIA:"+htmlfinb)).setBounds     (x       ,y+=sep  ,width   ,height   );	
		panel.add(JLBFamilia).setBounds                                   (x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"AREA:"+htmlfinb)).setBounds        (x       ,y+=sep  ,width   ,height   );	
		panel.add(JLBArea).setBounds                                      (x+sep2  ,y       ,width   ,height   );
		
		cmbEstablecimiento.requestFocus();
		cont.add(panel);
		btndeshacer.addActionListener(opGenerar_reporte);
		txtcodigo_prod.addKeyListener(Buscar_Datos_Producto);
		
	}
	
	ActionListener opGenerar_reporte = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(cmbEstablecimiento.getSelectedIndex()==0){
			   JOptionPane.showMessageDialog(null,"Debe de Seleccionar Un Establecimiento: ","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			      cmbEstablecimiento.requestFocus();
				  cmbEstablecimiento.showPopup();
			   	 return;		
			}else{
				
			   return;
			}	 
		}
	};
	
	
	KeyListener Buscar_Datos_Producto = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				String codigo=txtcodigo_prod .getText().toUpperCase().trim();
				String cod_prod=new BuscarSQL().cod_prod_principal_bms(codigo);
				
				if(!cod_prod.equals("false no existe") ){
					cmbEstablecimiento.setEnabled(false);
					
					Obj_Ubicaciones_De_Productos  Datos_Producto= new Obj_Ubicaciones_De_Productos().buscardatos_producto(cod_prod,cmbEstablecimiento.getSelectedItem().toString().trim().toUpperCase()+"");

					JLBcod_prod.setText         (htmlini+Datos_Producto.getCod_Prod().toString().trim()+htmlfin);
					JLBdescripcion.setText      (htmlini+Datos_Producto.getDescripcion_Prod().toString().trim()+htmlfin);
					JLBClase_De_Producto.setText(htmlini+Datos_Producto.getClase_De_Producto().toString().trim()+htmlfin);
					JLBCategoria.setText        (htmlini+Datos_Producto.getCategoria().toString().trim()+htmlfin);
					JLBFamilia.setText          (htmlini+Datos_Producto.getFamilia().toString().trim()+htmlfin);
					JLBArea.setText             (htmlini+Datos_Producto.getArea().toString().trim()+htmlfin);
					JLBLocalizacion.setText     (htmlini+Datos_Producto.getLocalizacion().toString().trim()+htmlfin);
					txtcodigo_prod.requestFocus();
					}else{
						JOptionPane.showMessageDialog(null, "El Codigo "+cod_prod+" Esta Mal Escrito o El Producto No Existe" , "Aviso", JOptionPane.CANCEL_OPTION,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
						txtcodigo_prod.requestFocus();
						cmbEstablecimiento.setEnabled(true);
						return;
               }
			}
		}
	};
	
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Ubicaciones_De_Productos().setVisible(true);
		}catch(Exception e){	}
	}
}
