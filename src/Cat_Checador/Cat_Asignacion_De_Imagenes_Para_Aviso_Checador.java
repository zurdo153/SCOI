package Cat_Checador;

import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.GuardarSQL;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Asignacion_De_Imagenes_Para_Aviso_Checador extends JFrame{

	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String extencion = "";
	String movimiento = "";
	byte[] imagB = null;
	
	JToolBar menu_toolbar  	= new JToolBar();
	JCButton btnNuevo      	= new JCButton("Nuevo"        ,"Nuevo.png"                         ,"Azul");
	JCButton btnEditar  	= new JCButton("Editar"    	  ,"Modify.png"                        ,"Azul");
	JCButton btnDeshacer   	= new JCButton("Deshacer"     ,"deshacer16.png"                    ,"Azul");
	JCButton btnBuscar     	= new JCButton("Buscar"       ,"Filter-List-icon16.png"            ,"Azul"); 
	JCButton btnGuardar    	= new JCButton("Guardar"      ,"Guardar.png"                       ,"Azul");
	
	JTextField txtFolio       = new Componentes().text(new JCTextField()  ,"Folio"                     ,30   ,"String");
	
	String status[] = {"VIGENTE","CANCELADO"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	JTextField txtDescripcion = new Componentes().text(new JCTextField(), "Descripcion", 200, "String");
	JCButton btnExaminar = new JCButton("Examinar", "", "Azul");
	
	JLabel lblImagen = new JLabel();
	
	Border blackline;
	
	public Cat_Asignacion_De_Imagenes_Para_Aviso_Checador() {
		
		this.cont.add(panel);
		this.setSize(435,515);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Agregar Imagen Para Avisos De Checador");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
//		this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Avisos Checador"));
		
		this.menu_toolbar.add(btnNuevo    );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
   	    this.menu_toolbar.add(btnEditar	  );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnDeshacer );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnBuscar   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnGuardar  );
		this.menu_toolbar.setFloatable(false);
		
		lblImagen.setBorder(blackline);
		
		int x=15,y=35,ancho=80;
		this.panel.add(menu_toolbar).setBounds(0,5,470,20);
		panel.add(new JLabel("Folio:")).setBounds(x, y, ancho, 20);
		panel.add(txtFolio).setBounds(x+ancho-20, y, ancho, 20);
		panel.add(new JLabel("Status:")).setBounds(x+ancho*2, y, ancho, 20);
		panel.add(cmb_status).setBounds(x+ancho*3-20, y, ancho, 20);
		
		panel.add(new JLabel("Descripcion:")).setBounds(x, y+=25, ancho, 20);
		panel.add(txtDescripcion).setBounds(x+ancho-20, y, ancho*3, 20);
		panel.add(btnExaminar).setBounds(x+ancho*4-20, y, 100, 20);
		panel.add(lblImagen).setBounds(x, y+=25, ancho*5, ancho*5);
		
		cont.add(panel);
		
		ValoresDefault();
		
		btnNuevo.addActionListener(opNuevo);
		btnEditar.addActionListener(opEditar);
		btnDeshacer.addActionListener(opDeshacer);
		btnBuscar.addActionListener(opBuscar);
		btnGuardar.addActionListener(opGuardar);
		btnExaminar.addActionListener(opExaminar);
	}
	
	
	ActionListener opNuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e){

			movimiento = "GUARDAR";
			extencion = "";
			
			txtFolio.setText(new BuscarSQL().buscar_folio_consecutivo_por_folio_de_transaccion(90).toString().trim());
			cmb_status.setSelectedItem("VIGENTE");
			txtDescripcion.setText("");
			
			txtFolio.setEditable(false);
			cmb_status.setEnabled(true);
			txtDescripcion.setEditable(true);
			btnExaminar.setEnabled(true);
			
			btnNuevo.setEnabled(false);
			btnEditar.setEnabled(false);	
			btnDeshacer.setEnabled(true);
			btnBuscar.setEnabled(true);
			btnGuardar.setEnabled(true);
			
			try {
				imagB  = Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"/Iconos/Un.JPG")); 
				lblImagen.setIcon(crearIcon(imagB));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
	};
	
	ActionListener opEditar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			txtFolio.setEditable(false);
			txtDescripcion.setEditable(true);
			cmb_status.setEnabled(true);
			
			btnNuevo.setEnabled(false);
			btnEditar.setEnabled(false);	
			btnDeshacer.setEnabled(true);
			btnBuscar.setEnabled(false);
			btnGuardar.setEnabled(true);
			btnExaminar.setEnabled(true);
			
			System.out.println("Entro a: Editar");
		}
	};
	
	ActionListener opDeshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			ValoresDefault();
		}
	};
	
	public void ValoresDefault(){
		
		try {
			imagB  = Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"/Iconos/Un.JPG")); 
			lblImagen.setIcon(crearIcon(imagB));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
			movimiento = "";
			extencion = "";
			
			txtFolio.setText("");
			txtDescripcion.setText("");
			cmb_status.setSelectedItem("");
			
			txtFolio.setEditable(false);
			txtDescripcion.setEditable(false);
			cmb_status.setEnabled(false);
			
			btnNuevo.setEnabled(true);
			btnEditar.setEnabled(false);	
			btnDeshacer.setEnabled(false);
			btnBuscar.setEnabled(true);
			btnGuardar.setEnabled(false);
			btnExaminar.setEnabled(false);
	}
	
	public Icon crearIcon(byte[] bs){
		ImageIcon tmpIconDefault= new ImageIcon(bs);
		//			int anchoRealDeImagen = tmpIconDefault.getIconWidth();
//			int altoRealDeImg = tmpIconDefault.getIconHeight();
			int anchoDeImagenEscala = (lblImagen.getWidth());
			int altoDeImgagenEscala = (lblImagen.getHeight());
			return new ImageIcon(tmpIconDefault.getImage().getScaledInstance(anchoDeImagenEscala, altoDeImgagenEscala, Image.SCALE_DEFAULT));
	}
	
	ActionListener opBuscar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Filtro_Imagenes_Aviso_Checador().setVisible(true);
		}
	};
	
	
	
	ActionListener opGuardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			String camposRequeridos = validaCampos();
			
			if(camposRequeridos.equals("")){
				
					if(new GuardarSQL().Guardar_Imagen_Aviso_checador(Integer.valueOf(txtFolio.getText().trim()), txtDescripcion.getText().trim().toUpperCase(), cmb_status.getSelectedItem().toString().trim().toUpperCase(), imagB, extencion, movimiento)){
						ValoresDefault();
						JOptionPane.showMessageDialog(null, "El Imagen Se Guardo Correctamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
					}else{
						JOptionPane.showMessageDialog(null,"Error Al Intentar Guardar La Imagen","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
					}
					
			}else{
				JOptionPane.showMessageDialog(null,"Los Siguientes Campos Son Requeridos:\n"+camposRequeridos,"Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));

			}
			
		}
	};
	
	public String validaCampos(){
		String lista = "";
		
		lista += txtDescripcion.getText().trim().equals("")?"- Descripcion\n":"";
		lista += extencion.trim().equals("")?"- Seleccionar Una Imagen\n":"";
		
		return lista;
	}
	
	ActionListener opExaminar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
//			String btn = e.getActionCommand().trim();
			
			JFileChooser elegir = new JFileChooser();
        	int opcion = elegir.showOpenDialog(null);
        	
             //Si presionamos el boton ABRIR en pathArchivo obtenemos el path del archivo
             if(opcion == JFileChooser.APPROVE_OPTION){
                String pathArchivo = elegir.getSelectedFile().getPath(); //Obtiene path del archivo
                
                File mi_fichero = new File ( pathArchivo );
                double tamano_bytes = mi_fichero.length ( );
                double tamano_megas = tamano_bytes/(1024*1024);
                
                if(tamano_megas>3){
                	JOptionPane.showMessageDialog(null, "El Archivo Que Intenta Agregar Es Muy Grande,\nEl Archivo Debe Medir Maximo 3 MB", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
    				return;
                }else{
                	
                		extencion = pathArchivo.substring(pathArchivo.indexOf(".")+1, pathArchivo.length()).trim().toUpperCase();
                	
//         				if(pathArchivo.substring(pathArchivo.indexOf(".")+1, pathArchivo.length()).trim().toUpperCase().equals("JPG") || pathArchivo.substring(pathArchivo.indexOf(".")+1, pathArchivo.length()).trim().toUpperCase().equals("PNG")){
     					if(pathArchivo.substring(pathArchivo.indexOf(".")+1, pathArchivo.length()).trim().toUpperCase().equals("JPG") ){

             					try {
									imagB  = Files.readAllBytes(Paths.get(pathArchivo));
									lblImagen.setIcon(crearIcon(imagB));
								} catch (IOException e1) {
									e1.printStackTrace();
								}
             					
         				}else{
         					JOptionPane.showMessageDialog(null, "Solo Se Pueden Cargar Imagenes Con Extencion JPG.", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
            				return;
         				}
                }
             }
		
		}
	};
	
	public class Cat_Filtro_Imagenes_Aviso_Checador extends JFrame{
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		Connexion con = new Connexion();
		
//		String cat = "MATRICES_CONCEPTOS";
		String cat = "MATRICES_ETAPAS";
//		String cat = "MATRICES_UNIDAD_DE_INSPECCION";
		
		Obj_tabla ObjTab =new Obj_tabla();
		int columnas = 3,checkbox=-1;
		public void init_tablaf(){
	    	this.tabla.getColumnModel().getColumn(0).setMinWidth(35);
	    	this.tabla.getColumnModel().getColumn(1).setMinWidth(250);
	    	this.tabla.getColumnModel().getColumn(2).setMinWidth(90);
	    	
			String comandof="exec filtro_de_imagenes_aviso_checador";
			String basedatos="26",pintar="si";
			ObjTab.Obj_Refrescar(tabla,modelo, columnas, comandof, basedatos,pintar,checkbox);
	    }
		
		@SuppressWarnings("rawtypes")
		public Class[] base (){
			Class[] types = new Class[columnas];
			for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
			 return types;
		}
		
		public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio","Descripcion","Status"}){
			 @SuppressWarnings("rawtypes")
				Class[] types = base();
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
				public boolean isCellEditable(int fila, int columna){return false;}
		};
		
		JTable tabla = new JTable(modelo);
		public JScrollPane scroll_tabla = new JScrollPane(tabla);
	     @SuppressWarnings({ "rawtypes" })
	    private TableRowSorter trsfiltro;
		     
		JTextField txtBuscar  = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
		
		String NuevoModifica ="";
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Cat_Filtro_Imagenes_Aviso_Checador(){
			this.setSize(800,380);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/favoritos-ver-boton-icono-8318-32.png"));
			this.setTitle("Filtro");
			this.panel.setBorder(BorderFactory.createTitledBorder("filtro"));
			
			int x=15,y=20,width=500;
			this.panel.add(txtBuscar).setBounds                  (x     ,y      ,width      ,20 );
			this.panel.add(scroll_tabla).setBounds               (x     ,y+=20  ,width      ,300    );
			
			this.init_tablaf();
			
			this.agregar(tabla);
			this.txtBuscar.addKeyListener  (opFiltro );
			
			trsfiltro = new TableRowSorter(modelo); 
			tabla.setRowSorter(trsfiltro);
			
			cont.add(panel);
		}
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount()>1){
		        		
		        		int fila = tabla.getSelectedRow();
		        		        		
						movimiento = "MODIFICAR";
						
						txtFolio.setText  (tabla.getValueAt(fila,0)+"");
						txtDescripcion.setText(tabla.getValueAt(fila,1)+"");
					    cmb_status.setSelectedItem(tabla.getValueAt(fila,2)+"");
						
						txtFolio.setEditable(false);
						txtDescripcion.setEditable(false);
						cmb_status.setEditable(false);
						
						btnNuevo.setEnabled(false);
						btnEditar.setEnabled(true);	
						btnDeshacer.setEnabled(true);
						btnBuscar.setEnabled(true);
						btnGuardar.setEnabled(false);
						
						Object[] imags = new BuscarSQL().imagen_aviso_checador(Integer.valueOf(txtFolio.getText().trim()));
						
						imagB = (byte[]) imags[0];
						extencion = imags[1].toString().trim();
						lblImagen.setIcon(crearIcon(imagB));
						
						dispose();
		        	}
		        }
	        });
	    }
		
		KeyListener opFiltro = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				ObjTab.Obj_Filtro(tabla, txtBuscar.getText(), columnas,txtBuscar);
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};

		
	}
	

	
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Asignacion_De_Imagenes_Para_Aviso_Checador().setVisible(true);
		}catch(Exception e){	}
	}

}
