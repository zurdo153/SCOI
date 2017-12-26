package Cat_Inventarios;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import Obj_Inventarios.Obj_Generar_Cargar_Pedido_De_Maximos_Y_Minimos;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.JCButton;

@SuppressWarnings("serial")
public class Cat_Generar_Cargar_Pedido_De_Maximos_Y_Minimos extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String[] estab = new Obj_Establecimiento().Combo_Establecimiento_Estado_resultados();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimientoSolicita = new JComboBox(estab);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimientoSurte = new JComboBox(estab);
	String[] areas = new Obj_Generar_Cargar_Pedido_De_Maximos_Y_Minimos().Areas_tipo_distrib();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbAreas = new JComboBox(areas);
	
	JLabel lblFolio = new JLabel("");
	JLabel lblUsuario = new JLabel("");
	JLabel lblCantProductos = new JLabel("");
	JLabel lblCantPz = new JLabel("");
	
	JCButton btnBuscar = new JCButton("Buscar", "buscar.png", "Azul");
	JCButton btnGenerarPedidoNuevo = new JCButton("Generar Pedido Nuevo", "mas-icono-4156-32.png", "Azul");		
	JCButton btnCargarPedido = new JCButton("Cargar Pedido", "mas-icono-4156-32.png", "Azul");		

	public Cat_Generar_Cargar_Pedido_De_Maximos_Y_Minimos() {
		setSize(460,335);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Generar O Cargar Pedido De Maximos Y Minimos");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Pedidos Por Establecimiento"));
		
		int x=15,y=20,ancho=140;
		
		panel.add(new JLabel("Establecimiento Solicita:")).setBounds(x, y, ancho, 20);
		panel.add(cmbEstablecimientoSolicita).setBounds(x+ancho, y, ancho*2, 20);
		panel.add(new JLabel("Establecimiento Surte:")).setBounds(x, y+=25, ancho, 20);
		panel.add(cmbEstablecimientoSurte).setBounds(x+ancho, y, ancho*2, 20);
		panel.add(new JLabel("Area Tipo Distribucion:")).setBounds(x, y+=25, ancho, 20);
		panel.add(cmbAreas).setBounds(x+ancho, y, ancho*2, 20);
		panel.add(btnBuscar).setBounds(x+ancho*2+40, y+=25, ancho-40, 20);
		
		panel.add(new JLabel("Folio:")).setBounds(x, y+=45, ancho, 20);
		panel.add(lblFolio).setBounds(x+ancho, y, ancho, 20);
		panel.add(new JLabel("Usuario Guardo:")).setBounds(x, y+=25, ancho, 20);
		panel.add(lblUsuario).setBounds(x+ancho, y, ancho, 20);
		panel.add(new JLabel("Cant. Productos:")).setBounds(x, y+=25, ancho, 20);
		panel.add(lblCantProductos).setBounds(x+ancho, y, ancho, 20);
		panel.add(new JLabel("Piezas:")).setBounds(x, y+=25, ancho, 20);
		panel.add(lblCantPz).setBounds(x+ancho, y, ancho, 20);
		
		panel.add(btnGenerarPedidoNuevo).setBounds(x+ancho-40, y+=45, ancho+30, 40);
		panel.add(btnCargarPedido).setBounds(x+ancho*2, y, ancho, 40);
		
		cont.add(panel);
		
		btnGenerarPedidoNuevo.setEnabled(false);
		btnCargarPedido.setEnabled(false);
		
		btnBuscar.addActionListener(opButton);
		btnCargarPedido.addActionListener(opButton);
		btnGenerarPedidoNuevo.addActionListener(opButton);
	}
	
	ActionListener opButton = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			System.out.println(e.getActionCommand());
			
			//Buscar--------------------------------------------------------------------------------------------------------------
			if(e.getActionCommand().toString().trim().equals("Buscar")){
				
				if(validarEstablecimiento().equals("")){
					if(!cmbEstablecimientoSolicita.getSelectedItem().toString().trim().equals(cmbEstablecimientoSurte.getSelectedItem().toString().trim())){
//						System.out.println("buscar, si encuentra registros se habilita el boton [btnCargarPedido]");
						
						Obj_Generar_Cargar_Pedido_De_Maximos_Y_Minimos obj = new Obj_Generar_Cargar_Pedido_De_Maximos_Y_Minimos().buscar(cmbEstablecimientoSolicita.getSelectedItem().toString().trim(),cmbEstablecimientoSurte.getSelectedItem().toString().trim(),cmbAreas.getSelectedItem().toString().trim());
						lblFolio.setText(obj.getFolio_pedido()+"");
						lblUsuario.setText(obj.getUsuario());
						lblCantProductos.setText(obj.getCant_prod()+"");
						lblCantPz.setText(obj.getCant_pz()+"");
							
						if(obj.getFolio_pedido() != 0){
//							System.out.println("buscar en SCOI(datos)-------------------------------------------------------");
							btnGenerarPedidoNuevo.setEnabled(false);
							btnCargarPedido.setEnabled(true);
						}else{
//							System.out.println("buscar en BMS(datos)");
							btnGenerarPedidoNuevo.setEnabled(true);
							btnCargarPedido.setEnabled(false);							
						}
						
					}else{
						JOptionPane.showMessageDialog(null, "No Puede Solicitar y Surtir Del Mismo Establecimiento","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					}
				}else{
					JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n"+validarEstablecimiento(),"Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
				
			//Generar Pedido Nuevo --------------------------------------------------------------------------------------------------------------
			}
			if(e.getActionCommand().toString().trim().equals("Generar Pedido Nuevo")){
				int folio = new Obj_Generar_Cargar_Pedido_De_Maximos_Y_Minimos().folio_pedido(e.getActionCommand().toString().trim(), cmbEstablecimientoSolicita.getSelectedItem().toString().trim(), cmbEstablecimientoSurte.getSelectedItem().toString().trim(), cmbAreas.getSelectedItem().toString().trim());
				btnGenerarPedidoNuevo.setEnabled(false);
				if(folio>0){
					new Cat_Maximos_Y_Minimos_Pedidos_Por_Establecimiento("BUSCAR EN BMS",cmbEstablecimientoSolicita.getSelectedItem().toString().trim(),cmbEstablecimientoSurte.getSelectedItem().toString().trim(),folio,cmbAreas.getSelectedItem().toString().trim()).setVisible(true);
				}else{
					JOptionPane.showMessageDialog(null, "No Se Encontro Folio Consecutivo","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
			}
			
			//Cargar Pedido --------------------------------------------------------------------------------------------------------------
			if(e.getActionCommand().toString().trim().equals("Cargar Pedido")){
				int folio = new Obj_Generar_Cargar_Pedido_De_Maximos_Y_Minimos().folio_pedido(e.getActionCommand().toString().trim(), cmbEstablecimientoSolicita.getSelectedItem().toString().trim(), cmbEstablecimientoSurte.getSelectedItem().toString().trim(), cmbAreas.getSelectedItem().toString().trim());
				btnCargarPedido.setEnabled(false);
				if(folio>0){
					new Cat_Maximos_Y_Minimos_Pedidos_Por_Establecimiento("BUSCAR EN SCOI",cmbEstablecimientoSolicita.getSelectedItem().toString().trim(),cmbEstablecimientoSurte.getSelectedItem().toString().trim(),folio,cmbAreas.getSelectedItem().toString().trim()).setVisible(true);
				}else{
					JOptionPane.showMessageDialog(null, "No Se Encontro Folio Consecutivo","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
			}
		}
	};
	
	public String validarEstablecimiento(){
		String lista = "";
			lista += cmbEstablecimientoSolicita.getSelectedIndex()==0 ? "Seleccionar Establecimiento Solicitante\n":"";
			lista += cmbEstablecimientoSurte.getSelectedIndex()==0 	 ? "Seleccionar Establecimiento Surtidor\n":"";
			lista += cmbAreas.getSelectedIndex()==0 	 ? "Seleccionar Area\n":"";
		return lista;
	}

	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Generar_Cargar_Pedido_De_Maximos_Y_Minimos().setVisible(true);
		}catch(Exception e){	}
	}

}
