package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.Icon;
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
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Diferencia_De_Cortes;
import Obj_Lista_de_Raya.Obj_Diferencia_De_Cortes_Calculado;
import Obj_Principal.Componentes;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Filtro_Diferencia_De_Cortes extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Connexion con = new Connexion();
	
	DefaultTableModel	 modelo       = new DefaultTableModel(0,5)	{
		public boolean isCellEditable(int fila, int columna){
			if(columna < 0)
				return true;
			return false;
		}
	};
	JTable tabla                   = new JTable(modelo);
	JScrollPane panelScroll        = new JScrollPane(tabla);
	
	JLabel txtFolio_Empleado = new JLabel();
	JLabel txtNombre_Completo = new JLabel();
	
	JTextField txtSaldoFavor = new JTextField("");
	JTextField txtTotalAcumulado = new JTextField("");
	JTextField txtDiferenciaTotal = new JTextField("");
	JTextField txtAbono = new Componentes().text(new JTextField(), "Ingresar abono", 8, "Double");
	
	String status_cobro[] = {"Pendiente De Cobro","Cobrar"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbStatuscobro = new JComboBox(status_cobro);
	
	JButton btnFoto = new JButton();
	
	JButton btnFiltro = new JButton("Filtro",new ImageIcon("imagen/Text preview.png"));
	JButton btnEditar = new JButton("Editar",new ImageIcon("imagen//Modify.png"));
	JButton btnGuardar = new JButton("Guardar",new ImageIcon("imagen//Guardar.png"));
	
	public Cat_Filtro_Diferencia_De_Cortes(String folio) {
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/caja2.png"));
		this.setTitle("Diferencia de Cortes");
		int x = 20, y=15, ancho=140;
		txtAbono.requestFocus();
		
		panel.setBorder(BorderFactory.createTitledBorder("Cortes"));		

		panel.add(new JLabel("Empleado:")).setBounds(x,y,ancho,20);
		panel.add(txtFolio_Empleado).setBounds(x+ancho-50,y,50,20);
		panel.add(txtNombre_Completo).setBounds(x+ancho,y,ancho*2,20);
		
		panel.add(btnFoto).setBounds(x+ancho*3+50,y,ancho+55,160);
		
		panel.add(new JLabel("Total acumulado:")).setBounds(x,y+=25,ancho,20);
		panel.add(txtTotalAcumulado).setBounds(x+ancho,y,ancho-15,20);
		
		panel.add(btnFiltro).setBounds(x+ancho*2,y,100,20);
		
		panel.add(new JLabel("Saldo a favor:")).setBounds(x,y+=25,ancho,20);
		panel.add(txtSaldoFavor).setBounds(x+ancho,y,ancho-15,20);
		
		panel.add(new JLabel("Abono:")).setBounds(x,y+=25,ancho,20);
		panel.add(txtAbono).setBounds(x+ancho,y,ancho-15,20);
		
		panel.add(new JLabel("Diferencia total:")).setBounds(x,y+=25,ancho,20);
		panel.add(txtDiferenciaTotal).setBounds(x+ancho,y,ancho-15,20);
		
		panel.add(btnEditar).setBounds(x+ancho*2,y,100,20);
		
		panel.add(new JLabel("Status de Cobro:")).setBounds(x,y+=25,ancho,20);
		panel.add(cmbStatuscobro).setBounds(x+ancho,y,ancho-15,20);
		
		panel.add(btnGuardar).setBounds(x+ancho*2,y,100,20);
		
		panel.add(panelScroll).setBounds(x-10,y+=45,ancho+540,260);
		
		txtSaldoFavor.setEditable(false);
		txtTotalAcumulado.setEditable(false);
		txtDiferenciaTotal.setEditable(false);
		txtAbono.setEditable(false);
		cmbStatuscobro.setEnabled(false);
		btnGuardar.setEnabled(false);
		
		btnFiltro.addActionListener(filtro);
		btnEditar.addActionListener(opEditar);
		btnGuardar.addActionListener(opGuardar);
		
		cmbStatuscobro.setSelectedIndex(1);
	
		cont.add(panel);
		
		llamar_render(tabla);
		
		cargarTabla(folio);
				
		this.setSize(715,490);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	}
	
//	TODO (llamar render)
	public void llamar_render(JTable tbl){
		
		tbl.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		tbl.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 
		tbl.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
		tbl.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
		tbl.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
		

		tbl.getTableHeader().setReorderingAllowed(false) ;
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		tbl.getColumnModel().getColumn(0).setHeaderValue("Folio Corte");
		tbl.getColumnModel().getColumn(0).setMinWidth(70);
		tbl.getColumnModel().getColumn(0).setMinWidth(70);
		tbl.getColumnModel().getColumn(1).setHeaderValue("Fecha");
		tbl.getColumnModel().getColumn(1).setMinWidth(120);
		tbl.getColumnModel().getColumn(1).setMaxWidth(120);
		tbl.getColumnModel().getColumn(2).setHeaderValue("Establecimiento");
		tbl.getColumnModel().getColumn(2).setMinWidth(100);
		tbl.getColumnModel().getColumn(2).setMaxWidth(100);
		tbl.getColumnModel().getColumn(3).setHeaderValue("Dif. De Corte");
		tbl.getColumnModel().getColumn(3).setMinWidth(80);
		tbl.getColumnModel().getColumn(3).setMaxWidth(80);
		tbl.getColumnModel().getColumn(4).setHeaderValue("Valido");
		tbl.getColumnModel().getColumn(4).setMinWidth(300);
		tbl.getColumnModel().getColumn(4).setMaxWidth(200);
	}
	
//	TODO (cargar tabla)
	public void cargarTabla(String folio_empleado){
		
//		TODO (llenar tabla)
		String[][] Tabla = getMatriz(folio_empleado);
		Object[] fila = new Object[tabla.getColumnCount()];
		for(int i=0; i<Tabla.length; i++){
			modelo.addRow(fila); 
			for(int j=0; j<tabla.getColumnCount(); j++){
				modelo.setValueAt(Tabla[i][j]+"", i,j);
			}
		}
		
//		TODO (llenar datos)
//		Obj_Empleados re = new Obj_Empleados();
//		re = re.buscar(Integer.parseInt(folio_empleado));
		
		Obj_Diferencia_De_Cortes_Calculado corte_calc = new Obj_Diferencia_De_Cortes_Calculado().buscar(Integer.parseInt(folio_empleado));
		
		
		txtFolio_Empleado.setText(folio_empleado);
		txtNombre_Completo.setText(corte_calc.getNombre_completo());
		
		txtAbono.setText(corte_calc.getAbono()+"");
		txtDiferenciaTotal.setText(corte_calc.getDiferencia_total()+"");
		txtSaldoFavor.setText(corte_calc.getSaldo_a_favor()+"");
		txtTotalAcumulado.setText(corte_calc.getTotal_acumulado()+"");
		
		cmbStatuscobro.setSelectedIndex(corte_calc.getStatus_cobro());
	
		ImageIcon tmpIconDefault = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp.jpg");
        Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
        btnFoto.setIcon(iconoDefault);
        
        
	}
	
//	TODO (funcion Guardar)
	ActionListener opGuardar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(txtAbono.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "El campo de abono es requerido", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
				return;
			}else{
				
				double abono = Double.valueOf(txtAbono.getText().trim());
				double acumulado = Double.valueOf(txtSaldoFavor.getText().trim());
				double totalAcumulado = Double.valueOf(txtTotalAcumulado.getText().trim());
				
				if((abono+acumulado)>totalAcumulado){
					JOptionPane.showMessageDialog(null, "El empleado cuenta con un saldo a favor de ( "+acumulado+" ) para que el el descuento se aplique correctamente\ndebe ingresar un abono menor o igal a: "+(totalAcumulado-acumulado), "Aviso al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
					return;
				}else{
					if(new Obj_Diferencia_De_Cortes().actualizar_abono_de_cortes(Integer.valueOf(txtFolio_Empleado.getText().trim()),cmbStatuscobro.getSelectedItem().toString(),Double.valueOf(txtAbono.getText().trim()))){
						JOptionPane.showMessageDialog(null, "El abono se guardo exitosamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
						return;
					}
				}
				
				panelEnabledFalse();
			}
		}
	};
	
//	TODO (Filtro)
	ActionListener filtro = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			dispose();
			new Cat_Diferencia_De_Cortes().setVisible(true);
			
		}
	};	
	
//	TODO (Editar)
	ActionListener opEditar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			panelEnabledTrue();
			btnGuardar.setEnabled(true);
		}
	};
	
	public void panelEnabledTrue(){	
		txtAbono.setEditable(true);
		cmbStatuscobro.setEnabled(true);
	}
	
	public void panelEnabledFalse(){	
		txtAbono.setEditable(false);
		cmbStatuscobro.setEnabled(false);
		
	}
	
	public void panelLimpiar(){	
		panelEnabledFalse();
		panelEnabledTrue();
		txtAbono.setText("");
		tabla.setSelectionMode(0);
		
	}
	
	KeyListener numerico_action = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
			char caracter = e.getKeyChar();

		   if(((caracter < '0') ||
		        (caracter > '9')) &&
		        (caracter != KeyEvent.VK_BACK_SPACE)){
		    	e.consume(); 
		    }			
		}
		@Override
		public void keyPressed(KeyEvent e){}
		@Override
		public void keyReleased(KeyEvent e){}
								
	};
		
//	TODO (Funcion para llenar arreglo de cortes por cobrar del empleado)
	public String[][] getMatriz(String folio_empleado){
		String qry = "exec sp_select_tabla_cortes_en_lista_de_cobro '"+folio_empleado+"'";
		
		String[][] Matriz = new String[getFilas(qry)][5];
		Statement s;
		ResultSet rs;
		try {
			s = con.conexion().createStatement();
			rs = s.executeQuery(qry);
			int i=0;
			while(rs.next()){
				
				DecimalFormat decimalFormat = new DecimalFormat("#0.00");

				Matriz[i][0] = rs.getString(1).trim();
				Matriz[i][1] = rs.getString(2).trim();
				Matriz[i][2] = rs.getString(3).trim();
				Matriz[i][3] = decimalFormat.format(Double.parseDouble(rs.getString(4)));
				Matriz[i][4] = "  "+rs.getString(5).trim();

				i++;
			}
			
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Error en Cat_Filtro_Diferencias_De_Cortes  en la funcion getMatriz \n  en el procedimiento : sp_select_total_de_cortes_folio_empleado  \n SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
	    return Matriz; 
	}
	
	public int getFilas(String qry){
		int filas=0;
		try {
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery(qry);
			while(rs.next()){
				filas++;
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return filas;
	}
	
//	TODO (main)
//	public static void main(String args[]){
//		try{
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//			new Cat_Filtro_Diferencia_De_Cortes("182","").setVisible(true);
//		}catch(Exception e){	}
//		
//	}

}
