package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Root_Lista_Raya extends JFrame {
	
	public Container cont = getContentPane();
	public JLayeredPane panel = new JLayeredPane();
	
	public JToolBar menu_toolbar = new JToolBar();
	
	public JTextField txtNombre_Completo = new JTextField();
	
	public JDateChooser txtCalendario = new JDateChooser();
	
	public JButton btn_guardar = new JButton("Guardar",new ImageIcon("Iconos/save_icon&16.png"));
	public JButton btn_imprimir = new JButton("Imprimir",new ImageIcon("Iconos/print_icon&16.png"));
	public JButton btn_refrescar = new JButton("Guardar y Actualizar",new ImageIcon("Imagen/Actualizar.png"));
	public JButton btn_nomina = new JButton("Totales Nomina",new ImageIcon("Iconos/nomina_icon&16.png"));
	public JButton btn_generar = new JButton("Generar Lista Raya",new ImageIcon("Iconos/generar_icon&16.png"));
	//se agrega el nombre de la variable boton y se le agrega el icono
	public JButton btn_lista_raya_pasadas = new JButton("Consulta De Listas De Raya Pasadas",new ImageIcon("Iconos/consulta_lista_raya_icon&16.png"));
	
	JLabel JLBAutorizacion_finanzas= new JLabel(new ImageIcon("Imagen/circulo-verde-icono-4055-16.png") );
	JLabel JLBAutoriazacion_Auditoria= new JLabel(new ImageIcon("Imagen/circulo-verde-icono-4055-16.png") );
	JLabel JLBGuardado_Fte_Sodas= new JLabel(new ImageIcon("Imagen/fast-food-icon16.png") );
	JLabel JLBTotales_Nomina= new JLabel(new ImageIcon("Imagen/dinero-icono-8797-16.png") );
	
	public Cat_Root_Lista_Raya(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/list_bullets_icon&16.png"));
		
		this.panel.add(menu_toolbar).setBounds(30,0,700,25);
		this.panel.add(txtNombre_Completo).setBounds(105,35,475,20);
		this.panel.add(txtCalendario).setBounds(588,35,90,20);
		this.txtCalendario.setIcon(new ImageIcon("Iconos/calendar_icon&16.png"));
		
		this.menu_toolbar.add(btn_guardar);
			this.btn_guardar.setToolTipText("Guardar");
		this.menu_toolbar.add(btn_imprimir);
			this.btn_imprimir.setToolTipText("Imprimir Este Se Habilita Al Guardar y Solo Si Se Traspaso A Cobro La Fuente De Sodas");
		this.menu_toolbar.add(btn_refrescar);
			this.btn_refrescar.setToolTipText("Refrescar");
		this.menu_toolbar.add(btn_nomina);
			this.btn_nomina.setToolTipText("Captura E Impresion De Totales De Nomina y Cheque");
		this.menu_toolbar.add(btn_generar);
			this.btn_generar.setToolTipText("Generar La Lista De Raya Nueva");
//	se le agrega la etiqueta al boton		
			this.menu_toolbar.add(btn_lista_raya_pasadas);
			this.btn_lista_raya_pasadas.setToolTipText("Consulta de Lista de Raya Pasadas");
		
		this.menu_toolbar.setEnabled(false);
		
	}

}
