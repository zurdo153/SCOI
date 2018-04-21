package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Lista_de_Raya.Obj_Revision_De_Horario_Por_Nivel_Jerarquico;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Revision_De_Horario_Por_Nivel_Jerarquico extends JFrame{

		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		Obj_Usuario usuario = new Obj_Usuario().LeerSession();
		
		JCButton btnFiltroDeColaborador = new JCButton("", "Filter-List-icon16.png", "Azul");
		JCButton btnRefresh = new JCButton("Actualizar", "refrescar-volver-a-cargar-las-flechas-icono-4094-16.png", "Azul");
		
		JTextField txtFolioColaborador = new Componentes().text(new JTextField(), "Folio", 10, "Int");
		JTextField txtNombreColaborador = new Componentes().text(new JTextField(), "Colaborador", 200, "String");
		JTextField txtEstablecimiento = new Componentes().text(new JTextField(), "Establecimiento", 100, "String");
		JTextField txtDepartamento = new Componentes().text(new JTextField(), "Departamento", 100, "String");
		JTextField txtPuesto = new Componentes().text(new JTextField(), "Puesto", 100, "String");
		
		JCButton btnFiltroH1 = new JCButton("", "buscar.png", "Azul");
		JCButton btnFiltroH2 = new JCButton("", "buscar.png", "Azul");
		JCButton btnFiltroH3 = new JCButton("", "buscar.png", "Azul");
		
		JLabel lblFolioH1 = new JLabel("");
		JLabel lblFolioH2 = new JLabel("");
		JLabel lblFolioH3 = new JLabel("");
		
		JTextField txtHorario1 = new Componentes().text(new JCTextField(), "Horario 1", 200, "String");
		JTextField txtHorario2 = new Componentes().text(new JCTextField(), "Horario 2", 200, "String");
		JTextField txtHorario3 = new Componentes().text(new JCTextField(), "Horario 3", 200, "String");
		
		JRadioButton rbH1 = new JRadioButton("");
		JRadioButton rbH2 = new JRadioButton("");
		JRadioButton rbH3 = new JRadioButton("");
		
		ButtonGroup btnGroup = new ButtonGroup();
		
		String[] horarioRotativo = { "Sin Horario rotativo ", "2 Horarios", "3 Horarios" };
		 @SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbStatusRotativo = new JComboBox(horarioRotativo);
		
		Object[][] turnosCuadrante = new BuscarSQL().BuscarTurnos();
		public Object[] listaTurnoC(){
			Object[] lista = new Object[turnosCuadrante.length];
			
			for(int i=0; i<turnosCuadrante.length; i++){
				lista[i]=turnosCuadrante[i][1];
			}
			return lista;
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbTurnoCuadrante = new JComboBox(listaTurnoC());
		
		JLabel lblFotoColaborador = new JLabel();
		
		JTextField txtDescanso = new Componentes().text(new JTextField(), "Descanso", 50, "String");
		JTextField txtDobla = new Componentes().text(new JTextField(), "Dobla", 50, "String");
		
		JCButton btnGuardar = new JCButton("Guardar", "guardar.png", "Azul");
		
		Border blackline;
		
	public Cat_Revision_De_Horario_Por_Nivel_Jerarquico() {
		btnGroup.add(rbH1);
		btnGroup.add(rbH2);
		btnGroup.add(rbH3);
		
		this.lblFotoColaborador.setBorder(BorderFactory.createTitledBorder(blackline,""));
		
		int x=10, y=15, ancho=80;
		panel.add(new JLabel("Colaborador:")).setBounds(x, y, ancho, 20);
		panel.add(btnFiltroDeColaborador).setBounds(x+ancho, y, 30, 20);
		panel.add(txtFolioColaborador).setBounds(x+ancho+30, y, ancho-30, 20);
		panel.add(txtNombreColaborador).setBounds(x+ancho*2, y, ancho*4, 20);
		panel.add(btnRefresh).setBounds(x+ancho*6, y, ancho+40, 20);
		panel.add(lblFotoColaborador).setBounds(x+ancho*8, y, 100, 90);
		
		panel.add(new JLabel("Establecimiento:")).setBounds(x, y+=25, ancho+20, 20);
		panel.add(txtEstablecimiento).setBounds(x+ancho+30, y, ancho*5-30, 20);
		
		panel.add(new JLabel("Departamento:")).setBounds(x, y+=25, ancho+20, 20);
		panel.add(txtDepartamento).setBounds(x+ancho+30, y, ancho*5-30, 20);
		
		panel.add(new JLabel("Puesto:")).setBounds(x, y+=25, ancho+20, 20);
		panel.add(txtPuesto).setBounds(x+ancho+30, y, ancho*5-30, 20);
		
		panel.add(new JLabel("Horario 1:")).setBounds(x, y+=25, ancho+20, 20);
		panel.add(btnFiltroH1).setBounds(x+55, y, 20, 20);
		panel.add(lblFolioH1).setBounds(x+ancho, y, 30, 20);
		panel.add(txtHorario1).setBounds(x+ancho+30, y, ancho*5-30, 20);
		panel.add(rbH1).setBounds(x+ancho*6, y, 30, 20);
		
		panel.add(new JLabel("Tipo De Horario:")).setBounds(x+ancho*6+30, y, ancho+20, 20);
		panel.add(cmbStatusRotativo).setBounds(x+ancho*8-30, y, ancho*2-30, 20);
		
		panel.add(new JLabel("Horario 2:")).setBounds(x, y+=25, ancho+20, 20);
		panel.add(btnFiltroH2).setBounds(x+55, y, 20, 20);
		panel.add(lblFolioH2).setBounds(x+ancho, y, 30, 20);
		panel.add(txtHorario2).setBounds(x+ancho+30, y, ancho*5-30, 20);
		panel.add(rbH2).setBounds(x+ancho*6, y, 30, 20);
		
		panel.add(new JLabel("Descanso:")).setBounds(x+ancho*6+30, y, ancho+20, 20);
		panel.add(txtDescanso).setBounds(x+ancho*8-30, y, ancho*2-30, 20);
		
		panel.add(new JLabel("Horario 3:")).setBounds(x, y+=25, ancho+20, 20);
		panel.add(btnFiltroH3).setBounds(x+55, y, 20, 20);
		panel.add(lblFolioH3).setBounds(x+ancho, y, 30, 20);
		panel.add(txtHorario3).setBounds(x+ancho+30, y, ancho*5-30, 20);
		panel.add(rbH3).setBounds(x+ancho*6, y, 30, 20);
		
		panel.add(new JLabel("Dobla:")).setBounds(x+ancho*6+30, y, ancho+20, 20);
		panel.add(txtDobla).setBounds(x+ancho*8-30, y, ancho*2-30, 20);

		panel.add(new JLabel("Turno Cuadrante:")).setBounds(x, y+=25, ancho+20, 20);
		panel.add(cmbTurnoCuadrante).setBounds(x+ancho+30, y, ancho*2, 20);
		
		panel.add(btnGuardar).setBounds(x+ancho*8-30, y+=25, ancho*2-30, 30);
		
		cont.add(panel);
		
		txtFolioColaborador.setEditable(false);
		txtNombreColaborador.setEditable(false);
		txtEstablecimiento.setEditable(false);
		txtDepartamento.setEditable(false);
		txtPuesto.setEditable(false);
		txtHorario1.setEditable(false);
		txtHorario2.setEditable(false);
		txtHorario3.setEditable(false);
		cmbStatusRotativo.setEnabled(false);
		cmbTurnoCuadrante.setEnabled(false);
		txtDescanso.setEditable(false);
		txtDobla.setEditable(false);
		cmbStatusRotativo.setEnabled(false);
		
		caposActivos();
		
		btnFiltroDeColaborador.addActionListener(opFiltroEmpleados);
		btnRefresh.addActionListener(opRefrescar);
		
		rbH1.addActionListener(opRb);
		rbH2.addActionListener(opRb);
		rbH3.addActionListener(opRb);
		
		this.setSize(800,280);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
	}
	
	public void caposActivos(){
		
		if(txtFolioColaborador.getText().equals("")){
			btnFiltroH1.setEnabled(false);
			btnFiltroH2.setEnabled(false);
			btnFiltroH3.setEnabled(false);
			
			rbH1.setEnabled(false);
			rbH2.setEnabled(false);
			rbH3.setEnabled(false);
			
			cmbTurnoCuadrante.setEnabled(false);
		}else{
			
			cmbTurnoCuadrante.setEnabled(true);
			
			if(!txtHorario1.getText().toString().trim().equals("")){
				btnFiltroH1.setEnabled(true);
				rbH1.setEnabled(true);
			}
			if(!txtHorario2.getText().toString().trim().equals("")){
				btnFiltroH2.setEnabled(true);
				rbH2.setEnabled(true);
			}
			if(!txtHorario3.getText().toString().trim().equals("")){
				btnFiltroH3.setEnabled(true);
				rbH3.setEnabled(true);
			}
		}
	}
	
	ActionListener opFiltroEmpleados = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			new Filtro_Permiso_Empleado().setVisible(true);
		}
	};
	
	ActionListener opRb = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			descansoDobla();
		}
	};
	
	ActionListener opRefrescar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(!txtFolioColaborador.getText().equals("")){
				seleccionDeColaborador(Integer.valueOf(txtFolioColaborador.getText()));
			}else{
				//aviso (es necesario seleccionar un colaborador para poder actualizar la informacion)
			}
			
		}
	};
	
	//TODO Filtro COLABORADOR
	public class Filtro_Permiso_Empleado extends JDialog{
		
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Connexion con = new Connexion();
		
        public DefaultTableModel model = new DefaultTableModel(null,	new String[]{"Folio", "Colaborador", "Establecimiento", "Departamento", "Puesto"}){
				@SuppressWarnings("rawtypes")
				Class[] types = new Class[]{
					java.lang.Object.class,
					java.lang.Object.class, 
					java.lang.Object.class, 
					java.lang.Object.class, 
					java.lang.Object.class
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
					}
					return false;
				}
		};

		JTable tabla = new JTable(model);
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		JLabel lblBuscar = new JLabel("BUSCAR : ");
		JTextField txtBuscar = new Componentes().text(new JCTextField(), "Teclee Aqui Para Buscar En La Tabla", 250, "String");
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Filtro_Permiso_Empleado()	{
			this.setModal(true);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
			this.setTitle("Filtro Colaboradores Por Nivel Jerarquico");
			this.setSize(700,470);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			txtBuscar.addKeyListener(new KeyAdapter() {
				
			public void keyReleased(final KeyEvent e) { 
					int[] columnas = {0,1,2,3,4};
					new Obj_Filtro_Dinamico_Plus(tabla, txtBuscar.getText().toUpperCase(), columnas);
	            } 
	        });
		
			trsfiltro = new TableRowSorter(model); 
			tabla.setRowSorter(trsfiltro); 
			campo.add(txtBuscar).setBounds      (10,20,418,20);
			campo.add(getPanelTabla()).setBounds(10,40,680,390);

			
			cont.add(campo);
			agregar(tabla);
			tabla.addKeyListener(seleccionEmpleadoconteclado);
		}
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		        		int fila = tabla.getSelectedRow();
		        		int folioColaborador =  Integer.valueOf(tabla.getValueAt(fila, 0).toString().trim());
		    			seleccionDeColaborador(folioColaborador);
		    			dispose();
		        	}
		        }
	        });
	    }
		
		KeyListener seleccionEmpleadoconteclado = new KeyListener() {
			@SuppressWarnings("static-access")
			@Override
			public void keyTyped(KeyEvent e) {
				char caracter = e.getKeyChar();
				
				if(caracter==e.VK_ENTER){
					int fila=tabla.getSelectedRow()-1;
					int folioColaborador =  Integer.valueOf(tabla.getValueAt(fila, 0).toString().trim());
					seleccionDeColaborador(folioColaborador);
					dispose();
				}
			}
			@Override
			public void keyPressed(KeyEvent e){}
			@Override
			public void keyReleased(KeyEvent e){}
									
		};
		
		
		private JScrollPane getPanelTabla()	{		
			new Connexion();
			tabla.getTableHeader().setReorderingAllowed(false) ;
			tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			tabla.getColumnModel().getColumn(0).setMaxWidth(45);
			tabla.getColumnModel().getColumn(1).setMinWidth(280);
			tabla.getColumnModel().getColumn(2).setMinWidth(110);
			tabla.getColumnModel().getColumn(3).setMinWidth(100);
			tabla.getColumnModel().getColumn(4).setMinWidth(200);
			
		    tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",9)); 	
		    tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		    tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		    tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		    tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		    
		    Statement s;
			ResultSet rs;
			try {
				s = con.conexion().createStatement();
				rs = s.executeQuery("exec sp_filtro_empleado_actividades_status_vigente "+usuario.getFolio() );
				
				while (rs.next())
				{ 
				   String [] fila = new String[5];
				   fila[0] = rs.getString(1).trim();
				   fila[1] = rs.getString(2).trim();
				   fila[2] = rs.getString(3).trim();
				   fila[3] = rs.getString(4).trim();
				   fila[4] = rs.getString(5).trim();
				   
				   model.addRow(fila); 
				}	
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			 JScrollPane scrol = new JScrollPane(tabla);
			   
		    return scrol; 
		}
	}
	
	Obj_Revision_De_Horario_Por_Nivel_Jerarquico buscar;
	public void seleccionDeColaborador(int folioColaborador){
		buscar = new Obj_Revision_De_Horario_Por_Nivel_Jerarquico().buscar(folioColaborador);
		
//		buscar(llenar campos)
		txtFolioColaborador.setText(buscar.getFolio_colaborador()+"");
		txtNombreColaborador.setText(buscar.getColaborador());
		txtEstablecimiento.setText(buscar.getEstablecimiento());
		txtDepartamento.setText(buscar.getDepartamento());
		txtPuesto.setText(buscar.getPuesto());
		
		lblFolioH1.setText(buscar.getFolio_h1()+"");
		txtHorario1.setText(buscar.getHorario_1());
		
		lblFolioH2.setText(buscar.getFolio_h2()+"");
		txtHorario2.setText(buscar.getHorario_2());
		
		lblFolioH3.setText(buscar.getFolio_h3()+"");
		txtHorario3.setText(buscar.getHorario_3());
		
		rbH1.setSelected(buscar.isStatus_h1());
		rbH2.setSelected(buscar.isStatus_h2());
		rbH3.setSelected(buscar.isStatus_h3());
		
		cmbStatusRotativo.setSelectedIndex(buscar.getStatus_rotativo());
		cmbTurnoCuadrante.setSelectedItem(buscar.getTurno_cuadrante().toString());
		
		ImageIcon tmpIconDefault = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp.jpg");
         Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(lblFotoColaborador.getWidth(), lblFotoColaborador.getHeight(), Image.SCALE_DEFAULT));
         lblFotoColaborador.setIcon(iconoDefault);
		
         caposActivos();
         descansoDobla();
		
	}
	
	public void descansoDobla(){
		String descanso = rbH1.isSelected()?buscar.getDescanso_h1():(rbH2.isSelected()?buscar.getDescanso_h2():buscar.getDescanso_h3());
		String dobla = rbH1.isSelected()?buscar.getDobla_h1():(rbH2.isSelected()?buscar.getDobla_h2():buscar.getDobla_h3());
		
		txtDescanso.setText(descanso);
		txtDobla.setText(dobla);
	}
	
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Revision_De_Horario_Por_Nivel_Jerarquico().setVisible(true);
		}catch(Exception e){	}
	}
}
