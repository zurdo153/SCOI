package Cat_Inventarios;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.GuardarSQL;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Correccion_De_Mermas extends JFrame{

	JLayeredPane panel = new JLayeredPane();
	Container cont = getContentPane();
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio De Merma", 10, "Int");
	JTextField txtFolioReactivar = new Componentes().text(new JTextField(), "Folio De Merma", 10, "Int");
	
	Object[] estab = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimeto = new JComboBox(estab);
	
	JDateChooser fhInicial = new Componentes().jchooser(new JDateChooser()  ,"Fecha Inicial"  ,0);
	JDateChooser fhFinal = new Componentes().jchooser(new JDateChooser()  ,"Fecha Final"  ,0);
	
	JCButton btnActualizar = new JCButton("", "actualizar.png", "Azul");
	JCButton btnReactivar = new JCButton("Reactivar","guardar.png","Azul");
	
	DefaultTableModel modelo = new DefaultTableModel(null,new String[]{"Folio", "cod_estab","Establecimiento","Pz Guardadas","Pz Seg","Pz Aud","Guardó","Validó","Terminó","Status","Fecha Guardado","Fecha Validado","Fecha Terminado","Pz Guardadas 2","Pz Seg 2","Pz Aud 2","Guardó 2","Validó 2","Terminó 2","Status 2","Fecha Guardado 2","Fecha Validado 2","Fecha Terminado 2" }
			){
	     @SuppressWarnings("rawtypes")
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
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class};
	     
	     @SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
             return types[columnIndex];
         }
         public boolean isCellEditable(int fila, int columna){
 			return false;
 		}
	};
	JTable tabla = new JTable(modelo);
    JScrollPane scroll = new JScrollPane(tabla);
	
	public Cat_Correccion_De_Mermas(){
		this.setTitle("Correccion De Mermas Con Irregularidades");
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new java.awt.Color(105,105,105)), "Seleccionar Un Registro De Merma"));
		this.setSize(1024, 590);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		int x= 15,y=20,ancho=80;
		panel.add(new JLabel("Folio:")).setBounds(x, y, ancho, 20);
		panel.add(txtFolio).setBounds(x+ancho, y, ancho, 20);
		
		panel.add(new JLabel("Fecha:    Del")).setBounds(x, y+=25, ancho, 20);
		panel.add(fhInicial).setBounds(x+ancho, y, ancho+20, 20);
		panel.add(new JLabel("Al")).setBounds(x+ancho*3-45, y, ancho, 20);
		panel.add(fhFinal).setBounds(x+ancho*3-20, y, ancho+20, 20);
		
		panel.add(btnActualizar).setBounds(x+ancho*4+5, y, 45, 45);
		
		panel.add(new JLabel("Establecimiento:")).setBounds(x, y+=25, ancho, 20);
		panel.add(cmbEstablecimeto).setBounds(x+ancho, y, ancho*3, 20);
		
		panel.add(scroll).setBounds(x, y+=25, ancho*12+30, 420);
		panel.add(txtFolioReactivar).setBounds(x+ancho*10, y+=425, ancho, 30);
		panel.add(btnReactivar).setBounds(x+ancho*11, y, ancho+30, 30);
		
		txtFolioReactivar.setHorizontalAlignment(0);
		txtFolioReactivar.setEditable(false);
		actualizar();
		
		tabla.addMouseListener(opAgregar);
		btnActualizar.addActionListener(opActualizar);
		txtFolio.addActionListener(opActualizar);
		btnReactivar.addActionListener(opReactivarMerma);
		cont.add(panel);
	}
		
	MouseListener opAgregar = new MouseListener() {
		public void mouseReleased(MouseEvent e) {		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			int filaSeleccionada = tabla.getSelectedRow();
			txtFolioReactivar.setText(tabla.getValueAt(filaSeleccionada, 0).toString());
		}
		public void mouseExited(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {}
	};
	
	ActionListener opActualizar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			actualizar();
		}
	};
	
	public void actualizar(){
		String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(fhInicial.getDate())+" 00:00:00";
		String fecha_final  = new SimpleDateFormat("dd/MM/yyyy").format(fhFinal.getDate())+"  23:59:00";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
			  
		Date fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
		Date fecha2 = sdf.parse(fecha_final , new ParsePosition(0));
		
		  if(fecha1.before(fecha2)){
			  txtFolioReactivar.setText("");
			  init_tabla(fecha_inicio, fecha_final, Integer.valueOf(txtFolio.getText().trim().equals("")?"0":txtFolio.getText().trim()));
		  }else{
	        JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
		     return;
		  }
	}
	
	Obj_tabla ObjTab =new Obj_tabla();
	int columnasp = 23,checkbox=-1;
	String parametro="";
	public void init_tabla(String fecha_inicio, String fecha_final, int folio){
		modelo.setRowCount(0);
		this.tabla.getColumnModel().getColumn(0).setMinWidth(55);
    	String comandof= "exec sp_correccion_de_mermas_con_irregularidades '"+fecha_inicio+"','"+fecha_final+"','"+cmbEstablecimeto.getSelectedItem().toString().trim()+"',"+folio;
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tabla,modelo, columnasp, comandof, basedatos,pintar,checkbox);
    }
	
	ActionListener opReactivarMerma = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			  if(!txtFolioReactivar.getText().equals("")){
				  
					if(JOptionPane.showConfirmDialog(null, "¿Desea Reactivar El Folio De Merma ("+txtFolioReactivar.getText().trim()+")?", "Confirmación!", JOptionPane.YES_NO_OPTION) == 0){
						
						if(new GuardarSQL().reactivarMerma(Integer.valueOf(txtFolioReactivar.getText().toString().trim()))){
								JOptionPane.showMessageDialog(null, "La Merma Fue Reactivada Para Su Correccion\nfolio de merma: "+txtFolioReactivar.getText(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
								  txtFolioReactivar.setText("");
								  actualizar();
						}else{
						  JOptionPane.showMessageDialog(null,"No Se Pudo Reactivar La Merma","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						  return;
						}
						
					}
					
			  }else{
				 JOptionPane.showMessageDialog(null,"Es Necesario Seleccionar La Merma Que Deséa Reactivar","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				 return;
			  }
		}
	};
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Correccion_De_Mermas().setVisible(true);
		}catch(Exception e){	}
	}

}
