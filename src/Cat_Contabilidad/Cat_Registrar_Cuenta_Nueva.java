package Cat_Contabilidad;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;

@SuppressWarnings("serial")
public class Cat_Registrar_Cuenta_Nueva extends JFrame{

	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtNoCuenta  = new Componentes().text(new JCTextField(), "Numero De Cuenta", 12, "Int");
	JTextField txtSaldo		= new Componentes().text(new JCTextField(), "Saldo", 12, "Double");
	
	JCButton btnGuardar = new JCButton("Guardar", "Guardar.png", "Azul");
	JCButton btnDeshacer = new JCButton("Deshacer", "Deshacer16.png", "Azul");
	
	public Cat_Registrar_Cuenta_Nueva() {
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
		this.setTitle("Registrar Cuenta Nueva");
		this.setSize(280, 160);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		panel.setBorder(BorderFactory.createTitledBorder("Cuenta Nueva"));

		int x=15,y=15,ancho=80;	
		
		panel.add(btnGuardar).setBounds(x, y, ancho+30, 20);
		panel.add(btnDeshacer).setBounds(x+ancho+32, y, ancho+30, 20);
		
		panel.add(new JLabel("No. Cuenta:")).setBounds(x, y+=40, ancho, 20);
		panel.add(txtNoCuenta).setBounds(x+ancho, y, ancho+50, 20);
		panel.add(new JLabel("Saldo Inicial:")).setBounds(x, y+=25, ancho, 20);
		panel.add(txtSaldo).setBounds(x+ancho, y, ancho+50, 20);
		
		btnGuardar.addActionListener(opBotton);
		btnDeshacer.addActionListener(opBotton);
		
		cont.add(panel);
	}
	
	ActionListener opBotton = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			switch (e.getActionCommand()) {
				case "Guardar": 
								String validador = validaCampos();
								if(validador.equals("")){
									guardar();
								}else{
									JOptionPane.showMessageDialog(null,  "Los Siguientes Campos Son Requeridos:\n"+validador,"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
									return;
								}
					break;
				default: deshacer(); break;
			}
		}
	};
	
	public String validaCampos(){
		String cadena = "";
		
			cadena += txtNoCuenta.getText().toString().trim().equals("")?"- No. Cuenta\n":"";
			cadena += txtSaldo.getText().toString().trim().equals("")?"- Saldo\n":"";
		
		return cadena;
	}
	
	public void deshacer(){
		txtNoCuenta.setText("");
		txtSaldo.setText("");
	}
	
	public void guardar(){

		String status = new BuscarSQL().Status_De_Cuenta_Movimientos_De_Cuenta(txtNoCuenta.getText().toString().trim());// T,D
		if(status.equals("V") || status.equals("D")){
			
				if(status.equals("V")){
						if(JOptionPane.showConfirmDialog(null, "La Cuenta Ya Existe, ¿Desea Modificarla?(Solo Se Modificará El Saldo Inicial)") == 0){// confirmacion para update
							if(new GuardarSQL().Guardar_Cuenta_movimientos_de_cuenta(txtNoCuenta.getText().toString().trim(), Double.valueOf(txtSaldo.getText().toString().trim()),status)){//guardar o modificacion correcta (continuar)  --------------------------  (pendiente)
									deshacer();
									JOptionPane.showMessageDialog(null, "La Cuenta Se Actualizó Correctamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
					                return;
							}else{
									JOptionPane.showMessageDialog(null,  "A Ocurrido Un Error Al Actualizada, Avise Al Administrador Del Sistema","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
									return;
							}
						}
				}else{
						if(new GuardarSQL().Guardar_Cuenta_movimientos_de_cuenta(txtNoCuenta.getText().toString().trim(), Double.valueOf(txtSaldo.getText().toString().trim()),status)){//guardar o modificacion correcta (continual)
							deshacer();
							JOptionPane.showMessageDialog(null, "La Cuenta Se Guardó Correctamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
			                return;
						}else{
							JOptionPane.showMessageDialog(null,  "A Ocurrido Un Error Al Guardar, Avise Al Administrador Del Sistema","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
							return;
						}
				}
		}else{
			JOptionPane.showMessageDialog(null,  "La Cuenta Ya Tiene Movimiento, Por Lo Que No Puede Ser Modificada","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
			return;
		}
	}

	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Registrar_Cuenta_Nueva().setVisible(true);
		}catch(Exception e){	}
	}
}
