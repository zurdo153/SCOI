package Cat_Filtros_IZAGAR;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Cat_Compras.Cat_Alimentacion_De_Codigos_Adicionales_De_Un_Producto;
import Conexiones_SQL.Connexion;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Filtro_De_Busqueda_De_Productos_SCOI extends JDialog {
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
	    public DefaultTableModel modelo = new DefaultTableModel(null,	new String[]{"Folio Producto","Descripcion","Unidad De Medida","Uso_Producto","Codigo Barras Principal","Estatus"}){
	                    @SuppressWarnings("rawtypes")
	                    Class[] types = new Class[]{
	                               java.lang.Object.class, 
	                               java.lang.Object.class, 
	                               java.lang.Object.class,  
	                               java.lang.Object.class,
	                               java.lang.Object.class,
	                               java.lang.Object.class,
//	                               java.lang.Boolean.class
	                };
	                    @SuppressWarnings({ "unchecked", "rawtypes" })
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
	                                    case 5  : return false;
//	                                    case 6  : return true; 
	                            }
	                             return false;
	                     }
	            };
		
		JTable tabla = new JTable(modelo);
		JScrollPane panelScroll = new JScrollPane(tabla);
		
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
  	    JTextField txtNombre = new Componentes().text(new JCTextField(), "Teclee Los Datos Del Producto Que Desea Buscar", 200, "String");
     	Border blackline, etched, raisedbevel, loweredbevel, empty;
	    String valor_catalogo="";
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Filtro_De_Busqueda_De_Productos_SCOI(String bandera_origen_consulta_filtro){
		        valor_catalogo=bandera_origen_consulta_filtro;
				
		        this.setSize(935,768);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setModal(true);
				this.setTitle("Filtro De Productos");
				blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
				this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Seleccione El Producto"));
			    setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
			    agregar(tabla);
			    this.init_tabla();
				trsfiltro = new TableRowSorter(modelo); 
				tabla.setRowSorter(trsfiltro);  
				panel.add(txtNombre).setBounds(15,20,600,20);
				panel.add(panelScroll).setBounds(15,43,900,682);
				cont.add(panel);
				txtNombre.addKeyListener(opFiltroNombre);
	}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==2){
	        		int fila = tabla.getSelectedRow();
					if(valor_catalogo.equals("Cat_Alimentacion_De_Codigos_Adicionales_De_Un_Producto")){
						new Cat_Alimentacion_De_Codigos_Adicionales_De_Un_Producto(tabla.getValueAt(fila,0).toString()).setVisible(true);
				        dispose();
					}
	        	}
	        }
        });
    }
	
	

KeyListener opFiltroNombre = new KeyListener(){
	public void keyReleased(KeyEvent arg0) {
		int[] columnas = {0,1,2,3,4};
		new Obj_Filtro_Dinamico_Plus(tabla, txtNombre.getText().toUpperCase(), columnas);
	}
	public void keyTyped(KeyEvent arg0) {}
	public void keyPressed(KeyEvent arg0) {}
	
};

public void init_tabla(){
      	  tabla.getTableHeader().setReorderingAllowed(false) ;
	      tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		    this.tabla.getColumnModel().getColumn(0).setMinWidth(80);
		    this.tabla.getColumnModel().getColumn(0).setMaxWidth(80);
		    this.tabla.getColumnModel().getColumn(1).setMinWidth(250);
		    this.tabla.getColumnModel().getColumn(1).setMaxWidth(800);
		    this.tabla.getColumnModel().getColumn(2).setMinWidth(128);
		    this.tabla.getColumnModel().getColumn(2).setMaxWidth(180);
		    this.tabla.getColumnModel().getColumn(3).setMinWidth(180);
		    this.tabla.getColumnModel().getColumn(3).setMaxWidth(220);
		    this.tabla.getColumnModel().getColumn(4).setMinWidth(150);
		    this.tabla.getColumnModel().getColumn(4).setMaxWidth(200);
		    this.tabla.getColumnModel().getColumn(5).setMinWidth(90);
		    this.tabla.getColumnModel().getColumn(5).setMaxWidth(100);
		    
//		    this.tabla.getColumnModel().getColumn(6).setMinWidth(20);
//		    this.tabla.getColumnModel().getColumn(6).setMaxWidth(20);
            
            this.tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer( "texto" ,"izquierda",	"arial", "normal",12));
            this.tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer( "texto" ,"izquierda",	"arial", "normal",12));
            this.tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer( "texto" ,"izquierda",	"arial", "normal",12));
            this.tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer( "texto" ,"izquierda",	"arial", "normal",12));
            this.tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer( "texto" ,"izquierda",	"arial", "normal",12));
            this.tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer( "texto" ,"izquierda",	"arial", "normal",12));
//            this.tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer( "CHB" ,"centro",	"arial", "normal",12));
            refrestabla();
}
private void refrestabla(){
	modelo.setRowCount(0);
	Statement s;
	ResultSet rs;
	try {
		Connexion con = new Connexion();
		s = con.conexion().createStatement();
		rs = s.executeQuery(" select tb_productos.folio_producto "
							+ "		,tb_productos.descripcion "
							+ "		,tb_unidad_de_medida_de_productos.descripcion as udm "
							+ "		,tb_uso_de_productos.descripcion as uso "
							+ "		,tb_productos.codigo_de_barras_principal "
							+ "		,case when ( tb_productos.status = 'V') then 'VIGENTE' "
							+ "			else 'CANCELADO'  end as status "
							+ " from tb_productos "
							+ " inner join tb_unidad_de_medida_de_productos on tb_unidad_de_medida_de_productos.folio = tb_productos.folio_unidad_de_medida "
							+ " inner join tb_uso_de_productos on tb_uso_de_productos.folio = tb_productos.folio_uso ");
		
		while (rs.next()){ 
						   Object [] fila = new Object[7];
						   fila[0] = rs.getString(1).trim();
						   fila[1] = rs.getString(2).trim();
						   fila[2] = rs.getString(3).trim(); 
						   fila[3] = rs.getString(4).trim(); 
						   fila[4] = rs.getString(5).trim(); 
						   fila[5] = rs.getString(6).trim();  
//						   fila[6] = "false"; 
						   modelo.addRow(fila); 
		}	
	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en Cat_Filtro_Productos en la funcion refrestabla  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
	}
}
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Filtro_De_Busqueda_De_Productos_SCOI("Reporte_De_Ventas").setVisible(true);
			}catch(Exception e){	}
		}
		
}
