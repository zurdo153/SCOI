package Cat_Administracion_del_Sistema;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Obj_Principal.JCButton;

@SuppressWarnings("serial")
public class Cat_Activacion_De_Lector_De_Huella_En_Establecimiento extends JFrame{

	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Object[][] matriz = new BuscarSQL().Buscar_Establecimientos_Para_Checador();
	
	public Object[] establecimiento(){
		Object[] lista = new Object[matriz.length];
		for(int i=0; i<matriz.length; i++){
			lista[i] = matriz[i][1].toString();
		}
		return lista;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento());
	
	String[] status = {"ACTIVO","INACTIVO"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbStatus = new JComboBox(status);
	
	JCButton btnGuardar = new JCButton("Guardar", "guardar.png", "Azul");
	
	public Cat_Activacion_De_Lector_De_Huella_En_Establecimiento() {
		this.setTitle("Configuracion Lectores De Huella Por Establecimiento");
		
		int x=10,y=15,ancho=80;
		
		panel.add(new JLabel("Establecimiento:")).setBounds(x, y, ancho, 20);
		panel.add(cmbEstablecimiento).setBounds(x+ancho, y, ancho*2, 20);
		
		panel.add(new JLabel("Status:")).setBounds(x, y+=25, ancho, 20);
		panel.add(cmbStatus).setBounds(x+ancho, y, ancho*2, 20);
		
		panel.add(btnGuardar).setBounds(x+ancho, y+=25, ancho*2, 20);
		
		validar_establecimiento();
		cmbEstablecimiento.addActionListener(opEstab);
		btnGuardar.addActionListener(opGuardar);
		
		cont.add(panel);
		this.setSize(270,130);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	ActionListener opEstab = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			validar_establecimiento();
		}
	};
	
	public void validar_establecimiento(){
		for(int i=0; i<matriz.length; i++){
			if(cmbEstablecimiento.getSelectedItem().toString().equals(matriz[i][1].toString())){
				if(Integer.valueOf(matriz[i][2].toString())==0){
					cmbStatus.setSelectedItem("INACTIVO");
				}else{
					cmbStatus.setSelectedItem("ACTIVO");
				}
				break;
			}
		}
	}
	
	ActionListener opGuardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
	
			int folio_estab = 0;
			int status = cmbStatus.getSelectedItem().equals("ACTIVO")?1:0;
			for(int i=0; i<matriz.length; i++){
				if(cmbEstablecimiento.getSelectedItem().toString().equals(matriz[i][1].toString())){
					folio_estab=(int) matriz[i][0];
					matriz[i][2]=status;
					break;
				}
			}
			
			if(new ActualizarSQL().Autorizar_Equipos_Para_Usar_Lector_De_Huella(folio_estab, status)){
				JOptionPane.showMessageDialog(null, "El Registro Se Guardo Correctamente", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
	        	return;
			}else{
				matriz = new BuscarSQL().Buscar_Establecimientos_Para_Checador();
				JOptionPane.showMessageDialog(null, "El Registro No Pudo Ser Guardado", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
	        	return;
			}
		}
	};

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Activacion_De_Lector_De_Huella_En_Establecimiento().setVisible(true);	
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | Error e) {
			System.out.println(e.getMessage());
		}
	}

}
