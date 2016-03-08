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

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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

import com.toedter.calendar.JDateChooser;

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
	
	int columnas = 5,checkbox=-1;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(90);	
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(90);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(340);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(4).setMinWidth(103);
    	
		String comando="sp_select_cascos_de_la_recepcion_de_mercancia '"+txtFolioFactura.getText().toString().toUpperCase().trim()+"'";
		String basedatos="26",pintar="si";
		new Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
    }
	
  public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Codigo Producto","Folio Producto","Descripcion","Cantidad Factura","Cantidad Pagar"}){
	 @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
				java.lang.Object.class,
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
			if(columna ==4)
				return true; return false;
		}
    };
    
    JTable tabla = new JTable(modelo);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);

	
	JTextField txtFolioFacturaproveedor = new Componentes().text(new JTextField(), "Folio Factura", 100, "String");
	JTextField txtFolioFactura = new Componentes().text(new JCTextField(), "Folio Recepcion", 30, "String");
	JDateChooser txtFecha = new JDateChooser();
	
	JTextField txtFolioProveedor =new Componentes().text(new JTextField(), "Folio del Proveedor", 25, "String");
	JTextField txtProveedor = new Componentes().text(new JTextField(), "Proveedor", 200, "String");
	JTextField txtNombre_Reecibe_Proveedor = new Componentes().text(new JCTextField(), "Nombre Del Proveedor Recibe", 300, "String");
	
	JCButton btnBuscar  = new JCButton("Buscar","buscar.png"); 
	JCButton btnDeshacer= new JCButton("Deshacer","deshacer16.png");
	JCButton btnGuardar = new JCButton("Guardar","Guardar.png");
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	
	int fila=0;
    int columna=0;
	public Cat_Pago_De_Cascos_Al_Proveedor(){
        this.cont.add(panel);
		this.setSize(765,540);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setTitle("Pago De Cascos a Proveedores");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Car-Battery64.png"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Pago De Cascos a Proveedores"));
        cont.setBackground(new java.awt.Color(255, 255, 255));
    	tabla.getTableHeader().setReorderingAllowed(false) ;
    	
		int x=20, y=20,width=100,height=20;
		panel.add(new JLabel("Cod. Proveedor:")).setBounds (x     ,y ,width   ,height);
		panel.add(txtFolioProveedor).setBounds             (x+=85 ,y ,width   ,height);
		panel.add(new JLabel("Factura:")).setBounds        (x+=115,y ,width   ,height);
		panel.add(txtFolioFacturaproveedor).setBounds      (x+50 ,y ,width+35 ,height);
		panel.add(new JLabel("Folio Recepcion:")).setBounds(x+=205,y ,width   ,height);
		panel.add(txtFolioFactura).setBounds               (x+=80 ,y ,width+20,height);
		panel.add(btnBuscar).setBounds                     (x+=130,y ,width+10,height);
		x=20;y=50;
		panel.add(new JLabel("Nom. Proveedor:")).setBounds (x     ,y ,width   ,height);
		panel.add(txtProveedor).setBounds                  (x+=85 ,y ,width*3 ,height);
		panel.add(new JLabel("Fecha Factura:")).setBounds  (x+=320,y ,width   ,height);
		panel.add(txtFecha).setBounds                      (x+=80 ,y ,width+20,height);
		panel.add(btnDeshacer).setBounds                   (x+=130,y ,width+10,height);
		x=20;y=80;
		panel.add(new JLabel("Nom. Recibe:")).setBounds    (x     ,y ,width   ,height);
		panel.add(txtNombre_Reecibe_Proveedor).setBounds   (x+85  ,y ,width*5,height);
		panel.add(scroll_tabla).setBounds                  (x     ,y+=30,725,360);
		
		x=20;y=80;
		panel.add(btnGuardar).setBounds                    (x+=615,y*6,width+10,height);
		
		
		txtFolioProveedor.setEditable(false);
		txtProveedor.setEditable(false);
		txtFolioFacturaproveedor.setEditable(false);
		
		btnDeshacer.setToolTipText("<ESC> Tecla Directa");
		btnGuardar.setToolTipText("<CTRL+G> Tecla Directa");
		
		btnGuardar.addActionListener(guardar);
		btnDeshacer.addActionListener(deshacer);
		btnBuscar.addActionListener(buscar);
		txtFolioFactura.addKeyListener(enterbuscarrecepcion);
		tabla.addKeyListener(op_key);
		
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
                txtFolioFactura.requestFocus();
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
					                    columna=4;			        	        
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
			if(txtFolioFactura.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null, "Es Necesario Teclear Un folio de Recepcion De Mercancia del Proveedor", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
               return;
			}
			Obj_Pago_De_Cascos_A_Proveedores pago_cascos= new Obj_Pago_De_Cascos_A_Proveedores().buscar(txtFolioFactura.getText().toString().trim().toUpperCase());
			
			if(Boolean.valueOf(pago_cascos.isExiste())){
				txtFolioFacturaproveedor.setText(pago_cascos.getFolio_factura());
				txtFolioProveedor.setText(pago_cascos.getCod_prv());
				txtProveedor.setText(pago_cascos.getNombre_proveedor());
				txtFolioFactura.setEditable(false);
				if(pago_cascos.getCantidad_cascos()>0){
				   init_tabla();
				   txtNombre_Reecibe_Proveedor.requestFocus();
				 }else{
					JOptionPane.showMessageDialog(null, "La Recepcion De Mercancia:"+txtFolioFactura.getText().toString().trim().toUpperCase()+"  No cuenta Con Cascos","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
			
			}else{
			JOptionPane.showMessageDialog(null, "El Folio Tecleado De La Recepcion No Existe", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			txtFolioFactura.requestFocus();
			return;
			}
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txtFolioFactura.setText("");
			txtFolioFactura.setEditable(true);
			txtFecha.setDate(null);
			txtFecha.setEnabled(true);
			modelo.setRowCount(0);
			txtFolioFactura.requestFocus();
		}
	};
	
	ActionListener guardar = new ActionListener(){
	public void actionPerformed(ActionEvent e){
	    if(validaCampos()!="") {
	    	                     JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n"+validaCampos(), "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
			                   	 return;
	    } else{
	    	return;
	    }
		                       }			
};
	
	private String validaCampos(){
		String error="";
		String fechanull = txtFecha.getDate()+"";
		if(txtFolioFactura.getText().equals("")) error+="Folio Recepcion\n";
		if(fechanull.equals("null")) 			error+= "Fecha de Factura\n";
		if(txtProveedor.getText().equals(""))		error+= "Proveedor\n";
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
							if(isNumeric(modelo.getValueAt(fila,4).toString().trim())){
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
		
		public void RecorridoFoco(){
			int cantidadDeFilas = tabla.getRowCount();
			if(fila == cantidadDeFilas-1){
				fila=0;
			}else{
				fila=fila+1;
			}
			tabla.getSelectionModel().setSelectionInterval(fila, fila);
			tabla.editCellAt(fila, columna);
			Component accion=tabla.getEditorComponent();
			accion.requestFocus();
			////seleccionar el valor de la celda completa	
			final JTextComponent jtc = (JTextComponent)accion;
			jtc.requestFocus();
			SwingUtilities.invokeLater(new Runnable()
			{
			    public void run()
			    {
			        jtc.selectAll();
			    }
			});
			
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
	
