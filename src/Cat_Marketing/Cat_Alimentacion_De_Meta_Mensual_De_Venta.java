package Cat_Marketing;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Marketing.Obj_Alimentacion_De_Meta_Mensual_De_Venta;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Alimentacion_De_Meta_Mensual_De_Venta extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String[] meses = {"ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO","JULIO","AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbMeses = new JComboBox(meses);

	String[] anios = new BuscarSQL().parametros_de_anios();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbAnios = new JComboBox(anios);

	String[] establecimientosExterno = new Obj_Establecimiento().Combo_Establecimiento201();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimientos = new JComboBox(establecimientosExterno);
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio Meta Mensual", 10, "Int");
	JTextField txtMetaMensual = new Componentes().text(new JTextField(), "Cantidad Meta Mensual", 13, "Double");
	
	JButton btnGuardar = new JCButton("Guardar", "", "Azul");
	JButton btnCancelar = new JCButton("Cancelar", "", "Azul");
	
	@SuppressWarnings("rawtypes")
	 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio", "Año", "Mes","Establecimiento", "Usuario","Meta Mes","Fecha Guardado","Status","Fecha Cancelado","Usuario Cancelo"}){
		 Class[] types = new Class[]{
			    	java.lang.String.class,
			    	java.lang.String.class,
			    	java.lang.String.class,
			    	java.lang.String.class,
			    	java.lang.String.class,
			    	java.lang.String.class,
			    	java.lang.String.class,
			    	java.lang.String.class,
			    	java.lang.String.class,
			    	java.lang.String.class
		                                    };
		
			@SuppressWarnings({ "unchecked" })
			public Class getColumnClass(int columnIndex) {
				
	         return types[columnIndex];
	     }
			public boolean isCellEditable(int fila, int columna){
				switch(columna){
	        	 	case 0 : return false; 
	        	 	case 1 : return false; 
	        	 	case 2 : return false; 
	        	 	case 3 : return false; 
	        	 	case 4 : return false; 
	        	 	case 5 : return false; 
	        	 	case 6 : return false; 
	        	 	case 7 : return false; 
	        	 	case 8 : return false; 
	        	 	case 9 : return false; 
	        	 } 				
	 			return false;
			}
	    };
	    JTable tabla = new JTable(modelo);
		public JScrollPane scroll_tabla = new JScrollPane(tabla);
		
	public Cat_Alimentacion_De_Meta_Mensual_De_Venta() {
		this.setTitle("Alimentacion De Meta Mensual");
		
		int x=20, y=15, ancho=80;
		
		panel.add(new JLabel("Año: ")).setBounds(x, y, ancho/2, 20);
		panel.add(cmbAnios).setBounds(x+(ancho/2), y, ancho+15, 20);
		
		panel.add(new JLabel("Establecimiento: ")).setBounds(x+(ancho*2), y, ancho+40, 20);
		panel.add(cmbEstablecimientos).setBounds(x+(ancho*3)+20,y,ancho*2+20,20);
		
		panel.add(new JLabel("Mes: ")).setBounds(x, y+=25, ancho/2, 20);
		panel.add(cmbMeses).setBounds(x+(ancho/2),y,ancho+15,20);
		
		panel.add(new JLabel("Folio: ")).setBounds(x+(ancho*2)+52, y, ancho+40, 20);
		panel.add(txtFolio).setBounds(x+(ancho*3)+20,y,ancho,20);
		
		panel.add(new JLabel("Meta Mensual: ")).setBounds(x, y+=35, ancho+40, 20);
		panel.add(txtMetaMensual).setBounds(x+ancho+40,y,ancho,20);
		
		panel.add(btnGuardar).setBounds(x+(ancho*3)+20,y,ancho+20,20);
		panel.add(btnCancelar).setBounds(x+(ancho*4)+50,y,ancho+20,20);
		
		panel.add(scroll_tabla).setBounds(x, y+=30, ancho*12+10, 320);
		
		txtFolio.setEditable(false);
		
		llamar_render();
		bucar_metas();
		
		btnGuardar.addActionListener(opGuardar);
		btnCancelar.addActionListener(opCancelar);
		
		cont.add(panel);
		
		this.setSize(1024,480);
		this.setLocationRelativeTo(null);
	}
	
	public void bucar_metas(){
		
		txtFolio.setText(new BuscarSQL().buscar_folio_consecutivo_por_folio_de_transaccion(29)+"");
		
		String[][] metas = new BuscarTablasModel().listaDeMetas_Mensuales_Registradas();
		
		for(String[] meta: metas){
			modelo.addRow(meta);
		}
	}
	
	private void llamar_render(){		
		new Connexion();
		tabla.getTableHeader().setReorderingAllowed(false) ;
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		tabla.getColumnModel().getColumn(0).setMaxWidth(40);
		tabla.getColumnModel().getColumn(0).setMinWidth(40);
		tabla.getColumnModel().getColumn(1).setMaxWidth(40);
		tabla.getColumnModel().getColumn(1).setMinWidth(40);
		tabla.getColumnModel().getColumn(2).setMinWidth(80);
		tabla.getColumnModel().getColumn(3).setMinWidth(200);
		tabla.getColumnModel().getColumn(4).setMinWidth(250);
		tabla.getColumnModel().getColumn(5).setMinWidth(40);
		tabla.getColumnModel().getColumn(6).setMinWidth(120);
		tabla.getColumnModel().getColumn(7).setMinWidth(30);
		tabla.getColumnModel().getColumn(8).setMinWidth(40);
		tabla.getColumnModel().getColumn(9).setMinWidth(40);
		
	    tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",11)); 	
	    tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",11)); 
	    tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",11)); 
	    tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",11)); 
	    tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",11)); 	
	    tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",11)); 
	    tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",11)); 
	    tabla.getColumnModel().getColumn(7).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",11)); 
	    tabla.getColumnModel().getColumn(8).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",11)); 	
	    tabla.getColumnModel().getColumn(9).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",11)); 
	    
	}

	ActionListener opGuardar = new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
			
			Obj_Alimentacion_De_Meta_Mensual_De_Venta mark = new Obj_Alimentacion_De_Meta_Mensual_De_Venta();
			
			if(!txtMetaMensual.getText().trim().equals("")){
				
				mark.setAnios(Integer.valueOf(cmbAnios.getSelectedItem().toString()));
				mark.setMes(cmbMeses.getSelectedItem().toString());
				mark.setEstablecimiento(cmbEstablecimientos.getSelectedItem().toString());
				mark.setFolio(Integer.valueOf(txtFolio.getText().trim()));
				mark.setMeta_mensual(Double.valueOf(txtMetaMensual.getText().trim()));
				
				if(mark.guardar_meta_mensual_de_venta("GUARDAR")){
					
					txtMetaMensual.setText("");
					modelo.setRowCount(0);
					bucar_metas();
					
					JOptionPane.showMessageDialog(null, "La Alimentacion De La Meta Mensual Se Guardo Correctamente","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
					return;
				}else{
					JOptionPane.showMessageDialog(null, "No Se A Podido Gaurdar La Meta Mensual", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				} 
			}else{
				JOptionPane.showMessageDialog(null, "El Campo [ META MENSUAL ] esta vacio", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
			
		}
	};
	
	ActionListener opCancelar = new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
			
			Obj_Alimentacion_De_Meta_Mensual_De_Venta mark = new Obj_Alimentacion_De_Meta_Mensual_De_Venta();
			
			if(tabla.getSelectedRow()>=0){
				
				mark.setFolio(Integer.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 0).toString().trim()));
				
				if(JOptionPane.showConfirmDialog(null, "Estas Seguro De Cancelar La Meta Mensual De Venta Seleccionada?") == 0){
					if(mark.guardar_meta_mensual_de_venta("CANCELAR")){
						
						modelo.setRowCount(0);
						bucar_metas();
						
						JOptionPane.showMessageDialog(null, "La Alimentacion De La Meta Mensual Se Cancelo Correctamente","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
						return;
					}else{
						JOptionPane.showMessageDialog(null, "No Se A Podido Cancelar La Meta Mensual", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					} 
				}else{
					return;
				}
				
			}else{
				JOptionPane.showMessageDialog(null, "Para Cancelar Una Meta Es Necesario Seleccionarla De La Tabla", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
			
		}
	};
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Alimentacion_De_Meta_Mensual_De_Venta().setVisible(true);
		}catch(Exception e){	}

	}

}
