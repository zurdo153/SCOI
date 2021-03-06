package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import Obj_Lista_de_Raya.Obj_Autorizacion_Auditoria;

@SuppressWarnings("serial")
public class Cat_Autorizacion_Auditoria extends JDialog{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Obj_Autorizacion_Auditoria autori = new Obj_Autorizacion_Auditoria().buscar();
	boolean autorizar = autori.isAutorizar();
	
	JLabel lblAutorizar = new JLabel(new ImageIcon("imagen/ok-firma-icono-6722-64.png"));
      // DECLARAMOS EL OBJETO RUNTIME PARA EJECUTAR APLICACIONES DE WINDOWS
	  Runtime R = Runtime.getRuntime();
	
	public Cat_Autorizacion_Auditoria(){
		this.setTitle("Autorizaci�n Lista Raya");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Aplicar.png"));
		panel.setBorder(BorderFactory.createTitledBorder("-"));
		
		panel.add(new JLabel("Autorizar Lista Raya Por Auditoria")).setBounds(40,30,190,25);
		panel.add(lblAutorizar).setBounds(85,80,65,65);
		
		lblAutorizar.setEnabled(autorizar);
		lblAutorizar.addMouseListener(opAutorizar);
		cont.add(panel);
		
		this.setModal(true);
		this.setSize(240,200);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	MouseListener opAutorizar = new MouseListener() {
		public void mouseReleased(MouseEvent arg0) {
		}
		public void mousePressed(MouseEvent arg0) {
			
			if(autorizar != true){
				if(JOptionPane.showConfirmDialog(null, "�Desea autorizar la lista de raya? al  aceptar se autorizara por Auditoria y se cerrara SCOI") == 0){
					Obj_Autorizacion_Auditoria prue = new Obj_Autorizacion_Auditoria();
					autorizar = true;
					lblAutorizar.setEnabled(true);
					prue.setAutorizar(autorizar);
					prue.actualizar();
					dispose();			
					try {
						R.exec("taskkill /f /im javaw.exe");
					} catch (Exception e2){}
				}else{
					return;
				}
			}else{
				if(JOptionPane.showConfirmDialog(null, "�Desea no autorizar la lista de raya?") == 0){
					Obj_Autorizacion_Auditoria prue = new Obj_Autorizacion_Auditoria();
					autorizar = false;
					lblAutorizar.setEnabled(false);
					prue.setAutorizar(autorizar);
					prue.actualizar();
				}else{
					return;
				}
				
			}
				
		}
		public void mouseExited(MouseEvent arg0) {
		}
		public void mouseEntered(MouseEvent arg0) {
		}
		public void mouseClicked(MouseEvent arg0) {
		}
	};
	
}
