package Cat_Compras;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
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
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Orden_De_Compra extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Obj_tabla ObjTab =new Obj_tabla();	
	int columnaspo = 17,checkbox=-1;
	public void init_tablaordenes(String status_tiempo){
	 	this.tablafilordenes.getColumnModel().getColumn(0).setMinWidth (70 );	
	 	this.tablafilordenes.getColumnModel().getColumn(0).setMaxWidth (70 );	
    	this.tablafilordenes.getColumnModel().getColumn(1).setMinWidth (120);		
    	this.tablafilordenes.getColumnModel().getColumn(2).setMinWidth (120);
    	this.tablafilordenes.getColumnModel().getColumn(2).setMaxWidth (120);

    	this.tablafilordenes.getColumnModel().getColumn(3).setMinWidth (150);
    	this.tablafilordenes.getColumnModel().getColumn(3).setMaxWidth (150);
    	this.tablafilordenes.getColumnModel().getColumn(4).setMinWidth (150);
    	this.tablafilordenes.getColumnModel().getColumn(4).setMaxWidth (150);
    	this.tablafilordenes.getColumnModel().getColumn(5).setMinWidth (320);
    	this.tablafilordenes.getColumnModel().getColumn(6).setMinWidth (120);
    	this.tablafilordenes.getColumnModel().getColumn(7).setMinWidth (200);
    	this.tablafilordenes.getColumnModel().getColumn(8).setMinWidth (120);
    	this.tablafilordenes.getColumnModel().getColumn(9).setMinWidth (200);
    	this.tablafilordenes.getColumnModel().getColumn(10).setMinWidth(80 );
    	this.tablafilordenes.getColumnModel().getColumn(11).setMinWidth(80 );
    	this.tablafilordenes.getColumnModel().getColumn(12).setMinWidth(80 );
    	this.tablafilordenes.getColumnModel().getColumn(13).setMinWidth(80 );
    	this.tablafilordenes.getColumnModel().getColumn(14).setMinWidth(80 );
    	this.tablafilordenes.getColumnModel().getColumn(15).setMinWidth(120);
    	
		String comandof="exec orden_de_compra_filtro_ventana '"+status_tiempo+"'";
    	
		String basedatos="26",pintar="si";
		modeloor_filtro.setRowCount(0);
		ObjTab.Obj_Refrescar(tablafilordenes,modeloor_filtro, columnaspo, comandof, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] baseOr (){
		Class[] types = new Class[columnaspo];
		for(int i = 0; i<columnaspo; i++){  
			if(i==0){
				types[i]=java.lang.Boolean.class;
			}else{
				types[i]=java.lang.Object.class;
			}
		}
		 return types;
	}
	
	public DefaultTableModel modeloor_filtro = new DefaultTableModel(null, new String[]{"Folio Orden","Establecimiento","Estatus Orden","Estatus Surtido","Estatus Tiempo","Proveedor","Fecha Elaboracion","Elaboro","Fecha Autorizacion","Autorizo","Total","Condicion Pago","Plazo", "Fecha Expiracion", "Cantidad  Prod.","Notas","Cod. Proveedor"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = baseOr();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columnaspo){return false;}
	};
	
	JTable tablafilordenes = new JTable(modeloor_filtro);
	public JScrollPane scroll_tablafp = new JScrollPane(tablafilordenes);
     @SuppressWarnings({ "rawtypes", "unused" })
    private TableRowSorter trsfiltro;
    
 	JTextField txtBuscar    = new Componentes().text(new JCTextField(), ">>Teclee Aqui Para Realizar La Busqueda En La Tabla<<"    , 300, "String"); 
	JTextField txtFolio     = new Componentes().text(new JCTextField(), "Folio Orden De Compra" , 12, "String");
	
	JButton btngenerar        = new JCButton("Generar Orden De Compra"      ,"imprimir-16.png","Azul");
	JButton btngenerarSAut    = new JCButton("Generar Pre Orden De Compra"  ,"imprimir-16.png","Azul");
	JButton btngenerarOrdP    = new JCButton("Generar Orden Proveedor"      ,"imprimir-16.png","Azul");
	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	String nombre_usuario=usuario.getNombre_completo();
	
	String[] estatus ={"ACTIVAS","INACTIVAS"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbStatus = new JComboBox(estatus);
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Reporte_De_Orden_De_Compra(){
		this.setTitle("Consulta De Orden De Compra");
		this.setSize(1024,650);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/cesta-de-la-compra-verde-icono-9705-64.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Seleccione Las Personas Que Van A Firmar El Documento"));
		this.trsfiltro = new TableRowSorter(modeloor_filtro); 
		
		int x=15,y=20,width=220,height=20;
		this.panel.add(new JLabel("Folio Orden: ")).setBounds(x     ,y     ,width,height );
		this.panel.add(txtFolio).setBounds                   (x+=60 ,y     ,100  ,height );
		this.panel.add(btngenerar).setBounds                 (x+=160,y     ,width,height );
		this.panel.add(btngenerarSAut).setBounds             (x+=230,y     ,250  ,height );
		this.panel.add(btngenerarOrdP).setBounds             (x+=260,y     ,250  ,height );
		
		this.panel.add(txtBuscar).setBounds                  (x=10  ,y+=35 ,900  ,height );
		this.panel.add(cmbStatus).setBounds                  (x+=900,y     ,80   ,height );
		this.panel.add(scroll_tablafp).setBounds             (x=10  ,y+=25 ,995  ,520    );
		
		this.txtBuscar.addKeyListener(opFiltro);
		this.btngenerar.addActionListener(new opGenerar("OC"));
		this.btngenerarSAut.addActionListener(new opGenerar("POC"));
		this.btngenerarOrdP.addActionListener(new opGenerar("OCP"));
		
		cmbStatus.addActionListener(opCmb);
		this.agregar(tablafilordenes);
		
		init_tablaordenes(cmbStatus.getSelectedItem().toString().trim());
		cont.add(panel);
        this.addWindowListener(new WindowAdapter(){public void windowOpened( WindowEvent e ){txtFolio.requestFocus();}});
	}
	
	KeyListener opFiltro = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tablafilordenes, txtBuscar.getText().toUpperCase(), columnaspo,txtBuscar);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	class opGenerar implements ActionListener{   
		String parametro;
	    public opGenerar(final String parametrop){
	    	parametro = parametrop;
	    }
	    public void actionPerformed(ActionEvent evt){
	    	String basedatos="2.200";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String reporte ="";
			String comando = "";
			 
			if(!txtFolio.getText().equals("")){
				boolean existe=false;
				try {
					existe=new BuscarSQL().existeOrden_De_Compra(txtFolio.getText().toString()) ;
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if(parametro.equals("POC")) {
					 reporte = "Obj_Reporte_De_Orden_De_Compra_Sin_Autorizar.jrxml";
					 comando = "exec sp_consulta_orden_de_compra_autorizada '"+txtFolio.getText().toUpperCase()+"','"+nombre_usuario+"'";
					   new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
					   return;
					}
				
				if( existe) {
					if(parametro.equals("OC")) {
					 reporte = "Obj_Reporte_De_Orden_De_Compra_Consulta.jrxml";
					 comando = "exec sp_consulta_orden_de_compra_autorizada '"+txtFolio.getText().toUpperCase()+"','"+nombre_usuario+"'";
					}
					
					if(parametro.equals("OCP")) {
						 reporte = "Obj_Reporte_De_Orden_De_Compra_Proveedores.jrxml";
						 comando = "exec sp_consulta_orden_de_compra_autorizada '"+txtFolio.getText().toUpperCase()+"','"+nombre_usuario+"'";
					}
					
			   	    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
				    return;
				}else {
					 JOptionPane.showMessageDialog(null, "La Orden De Compra Tecleada Tiene Alguno De Los Siguientes Inconvenientes \n -Es Inexistente \n -El Folio Es Incorrecto \n -Esta Pendiente De Autorizar \n -Esta Cancelada La Orden De Compra","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				  	  txtFolio.requestFocus();
			          return;
				}
				}else{
		  	  JOptionPane.showMessageDialog(null, "Debe De Capturar Un FolioDe Orden De Compra","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
		  	  txtFolio.requestFocus();
	          return;
			}
	    }
	};
	
	ActionListener opCmb = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			init_tablaordenes(cmbStatus.getSelectedItem().toString().trim());
		}
	};
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==1){
	        		int fila = tbl.getSelectedRow();
	        		    txtFolio.setText("");
					    txtFolio.setText   (tbl.getValueAt(fila,0)+"");
	        	}
	        }
        });
    }
	
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Orden_De_Compra().setVisible(true);
		}catch(Exception e){	}
	}
}
