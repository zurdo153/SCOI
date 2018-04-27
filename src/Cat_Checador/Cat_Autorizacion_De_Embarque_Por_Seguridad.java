package Cat_Checador;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Autorizacion_De_Embarque_Por_Seguridad extends JDialog  {
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		JTextField txtFolioUsuario = new Componentes().text(new JCTextField(), "Folio", 120, "Int");
		JTextField txtNombreUsuario = new Componentes().text(new JCTextField(), "Nombre", 120, "String");
		
		JTextField txtFiltrar = new Componentes().text(new JCTextField(), "Buscar", 200, "String");
		
		JButton btnActualizar = new JCButton("Actualizar", "actualizar.png", "Azul");
		JButton btnGuardar = new JCButton("Guardar", "guardar.png", "Azul");
		
		 public DefaultTableModel modeloFiltro = new DefaultTableModel(null,new String[]{"Transferencia","De","A","Status","Fecha","*"}){
	        @SuppressWarnings("rawtypes")
	        Class[] types = new Class[]{
	                    java.lang.Object.class,
	                    java.lang.Object.class,
	                    java.lang.Object.class, 
	                    java.lang.Object.class,
	                    java.lang.Object.class,
	                    java.lang.Boolean.class
	         };
	         @SuppressWarnings({ "rawtypes", "unchecked" })
	         public Class getColumnClass(int columnIndex) {
	                 return types[columnIndex];
	         }
	         public boolean isCellEditable(int fila, int columna){
	                 switch(columna){
	                         case 0  : return false; 
	                         case 1  : return false; 
	                         case 2  : return false; 
	                         case 3  : return false;
	                         case 4  : return false;
	                         case 5  : return tablaFiltro.getValueAt(fila, 3).toString().equals("EN CAMINO")?true:false; 
	                 }
	                  return false;
	         }
	     };
	     
	     JTable tablaFiltro = new JTable(modeloFiltro);
	     JScrollPane ScrollFiltro = new JScrollPane(tablaFiltro);

		public Cat_Autorizacion_De_Embarque_Por_Seguridad(){
			this.setModal(true);
			this.setSize(740, 325);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			
			this.setTitle("Autorización De Embarque");
			
			llamarRender();
			
			panel.add(new JLabel("USUARIO: ")).setBounds(20,20,80,20) ;
			panel.add(txtFolioUsuario).setBounds(80,20,40,20) ;
			panel.add(txtNombreUsuario).setBounds(120,20,240,20) ;
			panel.add(txtFiltrar).setBounds(20,45,690,20) ;
			panel.add(ScrollFiltro).setBounds(20,65,690,180) ;
			panel.add(btnActualizar).setBounds(20,260,120,30) ;
			panel.add(btnGuardar).setBounds(590,260,120,30) ;
			
			txtFolioUsuario.setEditable(false);
			txtNombreUsuario.setEditable(false);
			
			txtFolioUsuario.setText(new Obj_Usuario().LeerSession().getFolio()+"");
			txtNombreUsuario.setText(new Obj_Usuario().LeerSession().getNombre_completo());
			llenarTablaFiltro(""/*cmbEstablecimiento.getSelectedItem().toString().trim().toUpperCase()*/);
			
			btnActualizar.addActionListener(opActualizar);
			btnGuardar.addActionListener(opGuardar);
			txtFiltrar.addKeyListener(opFiltro);
			
			cont.add(panel);

		}
		
	   	private void llamarRender(){		
			tablaFiltro.getTableHeader().setReorderingAllowed(false) ;
			tablaFiltro.getColumnModel().getColumn(0).setMinWidth(70);
			tablaFiltro.getColumnModel().getColumn(0).setMaxWidth(100);
			
			tablaFiltro.getColumnModel().getColumn(1).setMinWidth(110);
			tablaFiltro.getColumnModel().getColumn(1).setMaxWidth(180);
			
			tablaFiltro.getColumnModel().getColumn(2).setMinWidth(110);
			tablaFiltro.getColumnModel().getColumn(2).setMaxWidth(180);
			
			tablaFiltro.getColumnModel().getColumn(3).setMinWidth(110);
			tablaFiltro.getColumnModel().getColumn(3).setMaxWidth(180);
			
			tablaFiltro.getColumnModel().getColumn(4).setMinWidth(140);
			tablaFiltro.getColumnModel().getColumn(4).setMaxWidth(180);
			
			tablaFiltro.getColumnModel().getColumn(5).setMinWidth(30);
			tablaFiltro.getColumnModel().getColumn(5).setMaxWidth(30);
			
			tablaFiltro.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
			tablaFiltro.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			tablaFiltro.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tablaFiltro.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tablaFiltro.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			tablaFiltro.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("CHB","centro","Arial","normal",12)); 	
	   	}
	   	
		public void llenarTablaFiltro(String estab){
			modeloFiltro.setRowCount(0);
			Object[][] lista = new BuscarSQL().getTransferenciasPendientesDeAutorizar(estab);
			for(Object[] row : lista){
				modeloFiltro.addRow(row);
			}
		}
		Obj_tabla ObjTabf =new Obj_tabla();
		KeyListener opFiltro = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				ObjTabf.Obj_Filtro(tablaFiltro, txtFiltrar.getText().toUpperCase(), 5,txtFiltrar);
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		ActionListener opActualizar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				llenarTablaFiltro("");
			}
		};
		
		ActionListener opGuardar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//				for(int i = 0; i<arreglo().size(); i++){
//					System.out.println(arreglo().get(i));
//				}
				if(new ActualizarSQL().validar_transferencia_por_seguridad(arreglo())){
					llenarTablaFiltro("");
					JOptionPane.showMessageDialog(null, "Autorizacion Guardada Correctamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
					return;
				}else{
					JOptionPane.showMessageDialog(null,"No Se Pudo Guardar La Autorizacion","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
					return;
				}
				
			}
		};
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Vector arreglo(){
			
			Vector v = new Vector();//columnas
			for(int i =0; i<tablaFiltro.getRowCount(); i++){
				if(Boolean.valueOf(tablaFiltro.getValueAt(i, 5).toString())){
					v.add(tablaFiltro.getValueAt(i, 0));
				}
			}
			return v;
		}
		
		public static void main(String [] arg){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Autorizacion_De_Embarque_Por_Seguridad().setVisible(true);
			}catch(Exception e){	}
		}
}

