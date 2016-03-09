package Cat_Compras;

import java.awt.Component;
import java.awt.Container;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import Conexiones_SQL.Connexion;
import Obj_Compras.Obj_Pago_De_Cascos_A_Proveedores;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Refrescar;

@SuppressWarnings("serial")
public class Cat_Pago_De_Cascos_Al_Proveedor extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	
	int columnas = 4,checkbox=-1;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(90);	
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(340);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(100);
    	
		String comando="select folio_producto, descripcion, costo,'' as cantidad  from tb_productos where status='V' and folio_uso=2 ORDER BY costo";
		String basedatos="26",pintar="si";
		new Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
    }
	
  public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio Producto","Descripcion","Costo","Cantidad Pagar"}){
	 @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
				java.lang.Object.class,
				java.lang.Object.class,
				java.lang.Object.class,
				java.lang.Object.class,
		};
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
         return types[columnIndex];
     }
		public boolean isCellEditable(int fila, int columna){
			if(columna ==3)
				return true; return false;
		}
    };
    
    JTable tabla = new JTable(modelo);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);
	
	JTextField txtFolioFactura = new Componentes().text(new JTextField(), "Folio Factura", 100, "String");
	JTextField txtFolioVenta = new Componentes().text(new JCTextField(), "Folio Venta", 30, "String");
	
	JTextField txtFolioProveedor =new Componentes().text(new JTextField(), "Folio del Proveedor", 25, "String");
	JTextField txtProveedor = new Componentes().text(new JTextField(), "Proveedor", 200, "String");
	JTextField txtNombre_Reecibe_Proveedor = new Componentes().text(new JCTextField(), "Nombre Del Proveedor Recibe", 300, "String");
	JTextField txtTotalAPagar= new Componentes().text(new JCTextField(), "Total A Cobrar",100,"String");
	
	JCButton btnBuscar  = new JCButton("Buscar"  ,"buscar.png"); 
	JCButton btnDeshacer= new JCButton("Deshacer","deshacer16.png");
	JCButton btnGuardar = new JCButton("Guardar" ,"Guardar.png");
	JCButton btnNuevo   = new JCButton("Nuevo"   ,"Nuevo.png");
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	int fila=0;
    int columna=0;

	public Cat_Pago_De_Cascos_Al_Proveedor(){
        this.cont.add(panel);
		this.setSize(680,305);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setTitle("Venta De Cascos A Proveedores");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Car-Battery64.png"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Venta De Cascos S Proveedores"));
        cont.setBackground(new java.awt.Color(255, 255, 255));
    	tabla.getTableHeader().setReorderingAllowed(false) ;
    	
		int x=20, y=20,width=100,height=20;
		panel.add(new JLabel("Cod. Proveedor:")).setBounds (x     ,y ,width   ,height);
		panel.add(txtFolioProveedor).setBounds             (x+=85 ,y ,width   ,height);
		panel.add(new JLabel("Factura:")).setBounds        (x+=115,y ,width   ,height);
		panel.add(txtFolioFactura).setBounds               (x+50  ,y ,width+35 ,height);
		panel.add(txtFolioVenta).setBounds                 (x+=195,y ,width+20,height);
		panel.add(btnBuscar).setBounds                     (x+=130,y ,width+10,height);
		x=20;y=50;
		panel.add(new JLabel("Nom. Proveedor:")).setBounds (x     ,y ,width   ,height);
		panel.add(txtProveedor).setBounds                  (x+=85 ,y ,width*3 ,height);
		panel.add(btnNuevo  ).setBounds                   (x+=440,y ,width+10,height);
		
		x=20;y=80;
		panel.add(new JLabel("Nom. Recibe:")).setBounds    (x     ,y ,width   ,height);
		panel.add(txtNombre_Reecibe_Proveedor).setBounds   (x+85  ,y ,width*5+50,height);

		panel.add(scroll_tabla).setBounds                  (x     ,y+=30,633  ,120);
		
		x=20;y=240;
		panel.add(btnDeshacer).setBounds                   (x+=100,y ,width+10,height);
		panel.add(btnGuardar).setBounds                    (x+=305,y ,width+10,height);
		panel.add(txtTotalAPagar).setBounds                (x+=125,y ,width+3,height);
		init_tabla();
		
		txtFolioProveedor.setEditable(false);
		txtProveedor.setEditable(false);
		txtFolioFactura.setEditable(false);
		
		btnDeshacer.setToolTipText("<ESC> Tecla Directa");
		btnGuardar.setToolTipText("<CTRL+G> Tecla Directa");
		
		btnGuardar.addActionListener(guardar);
		btnDeshacer.addActionListener(deshacer);
//		btnBuscar.addActionListener(buscar);
//		txtFolioVenta.addKeyListener(enterbuscarrecepcion);
		tabla.addKeyListener(op_key);
		
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
                txtFolioVenta.requestFocus();
          }
         });
             	///deshacer con escape
                getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
                  getRootPane().getActionMap().put("escape", new AbstractAction(){
                 public void actionPerformed(ActionEvent e)
                 {                 	    btnDeshacer.doClick();
               	    }
             });
             	///guardar con control+G
                 getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
                   getRootPane().getActionMap().put("guardar", new AbstractAction(){
                      public void actionPerformed(ActionEvent e)
                      {                 	    btnGuardar.doClick();
                    	    }
                 });
               ///guardar con F12
	              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar");
	                  getRootPane().getActionMap().put("guardar", new AbstractAction(){
	                      public void actionPerformed(ActionEvent e)
	                      {                 	    btnGuardar.doClick();
		                    	    }
	                 });
	                  ///DEL JTEXTFIELD A LA TABLA
				  	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				  	       KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "BUSCA");
					        getRootPane().getActionMap().put("BUSCA", new AbstractAction(){
					        @Override
					        public void actionPerformed(ActionEvent e)
					        {
					                    fila=0;
					                    columna=3;			        	        
					    				tabla.getSelectionModel().setSelectionInterval(fila, fila);
					    				tabla.editCellAt(fila, columna);
					    				if(modelo.getRowCount()>0){
					    				Component aComp=tabla.getEditorComponent();
					    				aComp.requestFocus();
					    				}
					    		}
					    });
	}
	
	ActionListener buscar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolioVenta.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null, "Es Necesario Teclear Un folio de Recepcion De Mercancia del Proveedor", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
               return;
			}
			
			Obj_Pago_De_Cascos_A_Proveedores pago_cascos= new Obj_Pago_De_Cascos_A_Proveedores().buscar(txtFolioVenta.getText().toString().trim().toUpperCase());
			
			if(Boolean.valueOf(pago_cascos.isExiste())){
				cascos_deuda=pago_cascos.getCantidad_cascos();
				txtFolioFactura.setText(pago_cascos.getFolio_factura());
				txtFolioProveedor.setText(pago_cascos.getCod_prv());
				txtProveedor.setText(pago_cascos.getNombre_proveedor());
				txtFolioVenta.setEditable(false);
				if(pago_cascos.getCantidad_cascos()>0){
				   init_tabla();
				   txtNombre_Reecibe_Proveedor.requestFocus();
				 }else{
					JOptionPane.showMessageDialog(null, "La Recepcion De Mercancia:"+txtFolioVenta.getText().toString().trim().toUpperCase()+"  No cuenta Con Cascos","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
			
			}else{
			JOptionPane.showMessageDialog(null, "El Folio Tecleado De La Recepcion No Existe", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			txtFolioVenta.requestFocus();
			return;
			}
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txtFolioVenta.setText("");
			txtNombre_Reecibe_Proveedor.setText("");
			txtProveedor.setText("");
			txtFolioVenta.setEditable(true);
			modelo.setRowCount(0);
			txtFolioVenta.requestFocus();
		}
	};
	
	ActionListener guardar = new ActionListener(){
	 public void actionPerformed(ActionEvent e){
		 Obj_Pago_De_Cascos_A_Proveedores pago_cascos= new Obj_Pago_De_Cascos_A_Proveedores();
	    if(validaCampos()!="") {
	    	                     JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n"+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			                   	 return;
	    } else{
	    	 pago_cascos.setTabla_obj(tabla_pagos());
	    	 pago_cascos.setCod_prv(txtFolioProveedor.getText().toString().trim().toUpperCase());
	    	 pago_cascos.setNombre_proveedor(txtProveedor.getText().toString().trim().toUpperCase());
	    	 pago_cascos.setFolio_factura(txtFolioFactura.getText().toString().trim().toUpperCase());
	    	 pago_cascos.setNombre_proveedor_recibe(txtNombre_Reecibe_Proveedor.getText().toUpperCase().toString().trim());
	    	 pago_cascos.setFolio_recepcion(txtFolioVenta.getText().toUpperCase().toString().trim());
	    	 pago_cascos.setCantidad_cascos_a_pagar(suma_cascos_pagar);
	    	 pago_cascos.setCantidad_cascos(cascos_deuda);
	    	 
	    	if(pago_cascos.guardar()){
                JOptionPane.showMessageDialog(null, "Se Guardo Correctamente"+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
            	btnDeshacer.doClick();
	    	}
	    	
	    	return;
	    }
	  }			
    };
         int suma_cascos_pagar=0, cascos_deuda=0;
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public String[][] tabla_pagos(){ 
			int cantidad_de_columnas_matriz=3;
			Vector vector = new Vector();
			for(int i=0; i<tabla.getRowCount(); i++){
				 if((tabla.getValueAt(i,4).toString().trim().equals("")?0:Integer.valueOf(tabla.getValueAt(i,4).toString().trim()))>0){
					 suma_cascos_pagar=suma_cascos_pagar+(Integer.valueOf(modelo.getValueAt(i,4).toString().trim()));
					 cascos_deuda=cascos_deuda+(Integer.valueOf(modelo.getValueAt(i,3).toString().trim()));
					  vector.add(modelo.getValueAt(i,1).toString().trim());
					  vector.add(modelo.getValueAt(i,3).toString().trim());
					  vector.add(modelo.getValueAt(i,4).toString().trim());
			     }
			}
				String[][] matriz = new String[vector.size()/cantidad_de_columnas_matriz][cantidad_de_columnas_matriz];
				 int i=0,j =0,columnafor=0;
				while(i<vector.size()){
					columnafor=0;
				      for(int f =0;  f<cantidad_de_columnas_matriz;  f++,columnafor++,i++  ){	
				  matriz[j][columnafor] = vector.get(i).toString();
				  }
				  j++;
			}
			return matriz;
		}

	private String validaCampos(){
		String error="";
		if(txtFolioVenta.getText().equals(""))           error+="  Folio Venta\n";
		if(txtNombre_Reecibe_Proveedor.getText().equals("")) error+="  Persona Recibe Del Proveedor\n";
		return error;
	};
	
	  KeyListener op_key = new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				ValidaValor();	
			}
			public void keyPressed(KeyEvent e) {}
		};
		
		 public boolean ValidaValor(){
				boolean valor=false;
							if(isNumeric(modelo.getValueAt(fila,3).toString().trim())){
										RecorridoFoco();
										valor = true;
								}else{
										tabla.getSelectionModel().setSelectionInterval(fila, fila);
										JOptionPane.showMessageDialog(null, "La Fila  [ "+(fila+1)+" ] En La Columna Cantidad Solo Acepta Numeros Enteros","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
										modelo.setValueAt(0, fila, columna);
										tabla.editCellAt(fila, columna);
										Component accion=tabla.getEditorComponent();
										accion.requestFocus();
								}
				return valor;
			}

	   private boolean isNumeric(String cadena){
				try {
					if(cadena.equals("")){
			    		return true;
					}else{
						Integer.parseInt(cadena);
			    		return true;
					}
				} catch (NumberFormatException nfe){
					return false;
				}
			}
		
		@SuppressWarnings("deprecation")
		public void RecorridoFoco(){
			int cantidadDeFilas = tabla.getRowCount();
			String sacarFocoDeTabla = "no";
			if(fila == cantidadDeFilas-1){
					if(columna==3){
						double total=0;
						 for(int i =0; i<tabla.getRowCount(); i++){
							total=total+(Float.valueOf(tabla.getValueAt(i, 2).toString().trim())*( (tabla.getValueAt(i, 3).toString().trim().equals(""))?0:(Float.valueOf(tabla.getValueAt(i, 3).toString().trim()))));
						 }
						txtTotalAPagar.setText(total+"");
						sacarFocoDeTabla="si";
							}
			}else{
				sacarFocoDeTabla = "no";
				double total=0;
				for(int i =0; i<tabla.getRowCount(); i++){
					total=total+(Float.valueOf(tabla.getValueAt(i, 2).toString().trim())*( (tabla.getValueAt(i, 3).toString().trim().equals(""))?0:(Float.valueOf(tabla.getValueAt(i, 3).toString().trim()))));
				}
				txtTotalAPagar.setText(total+"");
				fila=fila+1;
			}
			tabla.getSelectionModel().setSelectionInterval(fila, fila);
			tabla.editCellAt(fila, columna);
			Component accion=tabla.getEditorComponent();
		//seleccionar el valor de la celda completa	
			final JTextComponent jtc = (JTextComponent)accion;
			jtc.requestFocus();
			SwingUtilities.invokeLater(new Runnable()
			{
			    public void run()
			    {
			        jtc.selectAll();
			    }
			});
			
			if(sacarFocoDeTabla.equals("si")){
				tabla.lostFocus(null, null);
				txtNombre_Reecibe_Proveedor.requestFocus();
			}
		};
		
	KeyListener enterbuscarrecepcion = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				btnBuscar.doClick();
			}
		}
	};

//	En el constructor:
//
//		Código: 
//		tabla.addMouseListener(new MouseAdapter());
//
//		Fuera del constructor: 
//
//		Código: 
//		public void MouseClicked(MouseEvent e){
//		   int row = tabla.rowAtPoint(e.getPoint());
//
//		   /* row devolvera -1 si se ha clicado fuera de la fila pero dentro de la tabla, si no devolvera el indice de la fila en la que se ha clicado. */
//
//		   fieldNombre.setText(tabla.getValueAt(row, 0));
//		   fieldApellido.setText(tabla.getValueAt(row, 1));
//
//		   
		   
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Pago_De_Cascos_Al_Proveedor().setVisible(true);
		}catch(Exception e){	}
	}
};
	
