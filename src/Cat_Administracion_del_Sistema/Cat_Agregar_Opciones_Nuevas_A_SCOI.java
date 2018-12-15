package Cat_Administracion_del_Sistema;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import Obj_Administracion_del_Sistema.Obj_Mantenimiento_Base_de_Datos;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;

@SuppressWarnings("serial")
public class Cat_Agregar_Opciones_Nuevas_A_SCOI extends JFrame {
	Container cont=getContentPane();
	JLayeredPane panel=new JLayeredPane();
	
	JLabel lblmenu_id=new JLabel("Teclea el Numero del Menu_id:");
	
	JCButton btn_asubmenus  = new JCButton("Agregar Submenus Nuevos a Usuarios"  ,"circulo-rojo-icono-9411-16.png"  ,"Naranja" );
	JCButton btn_Guardar    = new JCButton("Guardar Nuevo Menu >>No Reversible"  ,"Guardar.png"                     ,"Rojo" );
	
	JTextField txtNombreSubmenu   = new Componentes().text(new JCTextField()  ,"Nombre de la Opción sin Cat ni Guión Bajo"	    ,150 ,"String");
	JTextField txtMenu_id         = new Componentes().text(new JCTextField()  ,"Numero del Submenu-Menu_id"						,3   ,"Int");
	JTextField txtMenu_Principal  = new Componentes().text(new JCTextField()  ,"Numero del grupo de Menu Principal"				,2   ,"Int");
	
	  //se crea el constructor de la ventana la regla es que el contructor  debe de llamarse igual que la clase
     	public Cat_Agregar_Opciones_Nuevas_A_SCOI (){
		      this.setSize(380,500); 
		      this.setLocationRelativeTo(null);
     	      this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Dialog-stop-hand64.png"));
     		  this.setTitle("Agregar Opciones Nuevas a SCOI");
     		  
     		 int x=20, y=10,width=325,height=20, sep=20;
     		  this.panel.add(btn_asubmenus).setBounds                                     (x  ,y      ,width ,height );
     		  this.panel.add(new JLabel("Teclea el Nombre de la Nueva Opción:")).setBounds(x  ,y+=sep ,width ,height );
     		  this.panel.add(new JLabel(">Nombre del Catalogo Sin Cat y Sin Guiones bajos")).setBounds(x  ,y+=sep ,width ,height );
    		  this.panel.add(txtNombreSubmenu).setBounds                                  (x  ,y+=sep ,width ,25 );
    		  this.panel.add(lblmenu_id).setBounds                                        (x  ,y+=sep ,width ,height );
    		  this.panel.add(txtMenu_id).setBounds                                        (x  ,y+=sep ,width ,height );    		  
    		  this.panel.add(new JLabel("Teclea el Numero del Menu Principal:")).setBounds(x  ,y+=sep ,width ,height );
    		  this.panel.add(new JLabel("Segun El catalogo>Cat_Usuarios:")).setBounds     (x  ,y+=sep ,width ,height );
    		  this.panel.add(new JLabel(" 1 - Administración Del Sistema")).setBounds     (x  ,y+=sep ,width ,height );
    		  this.panel.add(new JLabel(" 2 - Auditoria")).setBounds                      (x  ,y+=sep ,width ,height );
    		  this.panel.add(new JLabel(" 3 - Checador")).setBounds                       (x  ,y+=sep ,width ,height );
    		  this.panel.add(new JLabel(" 4 - Contabilidad")).setBounds                   (x  ,y+=sep ,width ,height );
    		  this.panel.add(new JLabel(" 5 - Evaluaciones")).setBounds                   (x  ,y+=sep ,width ,height );
    		  this.panel.add(new JLabel(" 6 - Lista de Raya")).setBounds                  (x  ,y+=sep ,width ,height );
    		  this.panel.add(new JLabel(" 7 - Seguridad")).setBounds                      (x  ,y+=sep ,width ,height );
    		  this.panel.add(new JLabel(" 8 - Compras")).setBounds                        (x  ,y+=sep ,width ,height );
    		  this.panel.add(new JLabel(" 9 - Punto de Venta")).setBounds                 (x  ,y+=sep ,width ,height );
       		  this.panel.add(new JLabel("10 - Inventarios")).setBounds                    (x  ,y+=sep ,width ,height );
       		  this.panel.add(new JLabel("11 - Servicios")).setBounds                      (x  ,y+=sep ,width ,height );
     		  this.panel.add(txtMenu_Principal).setBounds                                 (x  ,y+=sep ,width ,height );
       		  this.panel.add(btn_Guardar).setBounds                                       (x  ,y+=30  ,width ,height );
       		  
     		  txtNombreSubmenu.setEditable(false);
     		  txtMenu_id.setEditable(false);
     		  txtMenu_Principal.setEditable(false);
     		  btn_Guardar.setEnabled(false);
     		  
		      this.cont.add(panel);
		      this.btn_asubmenus.addActionListener(agregar_submenus_nuevos);
		      this.btn_Guardar.addActionListener(guardarsubmenu);	
       	 }
		
	ActionListener agregar_submenus_nuevos  =new ActionListener() {			
		public void actionPerformed(ActionEvent e) {
   		  txtNombreSubmenu.setEditable(true);
   		  txtMenu_id.setEditable(true);
   		  txtMenu_Principal.setEditable(true);
   		  btn_Guardar.setEnabled(true);
		}
	};
	
	ActionListener guardarsubmenu =new ActionListener(){
		public void actionPerformed(ActionEvent e){
		 if(txtMenu_Principal.getText().toString().equals("")) {
			 JOptionPane.showMessageDialog(null, "Es Requerido Teclee un Menu Principal Segun La Lista","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
			 return;
		 }else {	
			 if(txtMenu_id.getText().toString().equals("")) {
				 JOptionPane.showMessageDialog(null, "Es Requerido Teclee el Menu id al que va a pertenecer la Opcion","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				 return;
			 }else {	
				 if(Integer.valueOf(txtMenu_Principal.getText().toString())>11) {
					 JOptionPane.showMessageDialog(null, "El Numero de Menu Mayor es 10","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					 return;
				 }else { 
		   		  txtNombreSubmenu.setEditable(false);
		   		  txtMenu_id.setEditable(false);
		   		  txtMenu_Principal.setEditable(false);
		   		  btn_Guardar.setEnabled(false);
		   		  new Obj_Mantenimiento_Base_de_Datos().agregar_submenus_nuevos(txtNombreSubmenu.getText().toString(),Integer.valueOf(txtMenu_id.getText()),Integer.valueOf(txtMenu_Principal.getText()) );
		   		  JOptionPane.showMessageDialog(null, "Se Guardo Correctamente La Nueva Opcion de SCOI", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
				 }
			 }
		 }
		}
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Agregar_Opciones_Nuevas_A_SCOI().setVisible(true);
		}catch(Exception e){}
	}
 }

