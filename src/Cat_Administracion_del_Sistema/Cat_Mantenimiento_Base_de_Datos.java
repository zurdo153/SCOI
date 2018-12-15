package Cat_Administracion_del_Sistema;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import Obj_Administracion_del_Sistema.Obj_Mantenimiento_Base_de_Datos;
import Obj_Principal.JCButton;

@SuppressWarnings("serial")
public class Cat_Mantenimiento_Base_de_Datos extends JFrame {
	Container cont=getContentPane();
	JLayeredPane panel=new JLayeredPane();
	
	JCButton btn_rbd        = new JCButton("Reducir Log de la Base de Datos SCOI"     ,"LOGO_SCOI_32X32PIX-01.png"                        ,"AzulO");
	JCButton btn_rbd_Venta  = new JCButton("Reducir Log de la Base de Datos Ventas"   ,"monedas-en-efectivo-en-moneda-icono-4023-32.png"  ,"Azul");
	JCButton btn_rbd_BMS    = new JCButton("Reducir Log de la Base de Datos BMS "     ,"Logo_Izagar32_Circulo.png"                        ,"Naranja");
	
	  //se crea el constructor de la ventana la regla es que el contructor  debe de llamarse igual que la clase
     	public Cat_Mantenimiento_Base_de_Datos (){
		      this.setSize(380,210); 
		      this.setLocationRelativeTo(null);
     	      this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Actualizar_32x32.png"));
     		  this.setTitle("Mantenimiento Bases de Datos");
     		  
     		 int x=20, y=10,width=325,sep=50;
     		  this.panel.add(btn_rbd).setBounds                                           (x  ,y      ,width ,40   );
     		  this.panel.add(btn_rbd_Venta).setBounds                                     (x  ,y+=sep ,width ,40   );
     		  this.panel.add(btn_rbd_BMS).setBounds                                       (x  ,y+=sep ,width ,40   );
		      this.cont.add(panel);
		      this.btn_rbd.addActionListener(reducirlog);
		      this.btn_rbd_Venta.addActionListener(reducirlogVentas);
		      this.btn_rbd_BMS.addActionListener(reducirlogBMS);
       	 }
     	
 	 ActionListener reducirlog =new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(new Obj_Mantenimiento_Base_de_Datos().reducir_Log()==true ){
				 JOptionPane.showMessageDialog(null, "Se Redujo el log de SCOI Satisfactoriamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/LOGO SCOI 64 X 64  PIX-01.png"));
			}	 
			else{JOptionPane.showMessageDialog(null,"No se Pudo Ejecutar la Reduccion","Aviso",JOptionPane.ERROR_MESSAGE);
			}
		}
	};
	
	ActionListener reducirlogVentas =new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(new Obj_Mantenimiento_Base_de_Datos().reducir_Log_Ventas()==true ){
				 JOptionPane.showMessageDialog(null, "Se Redujo el log de Ventas Satisfactoriamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/monedas-en-efectivo-en-moneda-icono-4023-64.png"));
			}	 
			else{JOptionPane.showMessageDialog(null,"No se Pudo Ejecutar la Reduccion","Aviso",JOptionPane.ERROR_MESSAGE);
			}
		}
	};
	
	 ActionListener reducirlogBMS =new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(new Obj_Mantenimiento_Base_de_Datos().reducir_Log_BMS()==true ){
					 JOptionPane.showMessageDialog(null, "Se Redujo el log de BMS principal Satisfactoriamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/Logotipo_IZAGAR_64x64.jpg"));
				}	 
				else{JOptionPane.showMessageDialog(null,"No se Pudo Ejecutar la Reduccion","Aviso",JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Mantenimiento_Base_de_Datos().setVisible(true);
		}catch(Exception e){}
	}
 }

