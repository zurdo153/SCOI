package Cat_Inventarios;

import java.awt.Container;
import java.awt.Toolkit;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Obj_Principal.Obj_tabla;
import Obj_Reportes.Obj_Reportes_De_Ventas;

@SuppressWarnings("serial")
public class Cat_Consulta_De_Cotizacion extends JDialog{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Obj_tabla ObjTab =new Obj_tabla();
	int cantidad_de_columnas =  (new Obj_Reportes_De_Ventas().cantidad_de_competidores()+1),checkbox=-1;
	DefaultTableModel modelo = new DefaultTableModel(0,cantidad_de_columnas){
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
	             return listacolumnas(cantidad_de_columnas)[columnIndex];
	         }   
		public boolean isCellEditable(int fila, int columna){
			if(columna == 0)
				return true;
			return false;
		}
	};
	
	@SuppressWarnings("rawtypes")
	public Class[] listacolumnas(int Columnas){
	Class[] lista = new Class[Columnas];
	for (int i = 0; i<lista.length; i++){
			lista[i] =(String.class);
	 }
	 return lista;
   };
	
   JTable tabla = new JTable(modelo);
    
	private JScrollPane getPanelTabla()	{		
		int a=95;
		tabla.getColumnModel().getColumn(0).setHeaderValue("FECHA");
		tabla.getColumnModel().getColumn(0).setMinWidth(120);
		
		try {
			String[] competidor = new Obj_Reportes_De_Ventas().lista_de_competidores();
			for(int i=1; i<cantidad_de_columnas; i++){
				tabla.getColumnModel().getColumn(i).setHeaderValue(competidor[(i-1)].toString());
				tabla.getColumnModel().getColumn(i).setMaxWidth(a+20);
				tabla.getColumnModel().getColumn(i).setMinWidth(a+20);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

    	tabla.getTableHeader().setReorderingAllowed(false) ;
    	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane scrol = new JScrollPane(tabla);
	    return scrol; 
	}
	
    @SuppressWarnings({ "rawtypes", "unused" })
    private TableRowSorter trsfiltro;
     
 	String FActividadesCargado ="";
	String[][] tablaprecargadaproductos;
	
     int columnas2 = 7;
		public void init_tabla_filtro_productos(){
	    	this.tabla2.getColumnModel().getColumn(0).setMinWidth(90);	
	    	this.tabla2.getColumnModel().getColumn(1).setMinWidth(410);
	    	this.tabla2.getColumnModel().getColumn(2).setMinWidth(150);
	    	this.tabla2.getColumnModel().getColumn(3).setMinWidth(190);
	    	this.tabla2.getColumnModel().getColumn(4).setMinWidth(100);
	    	this.tabla2.getColumnModel().getColumn(5).setMinWidth(100);
	    	
			String comando="exec inventarios_filtro_catalogo_de_productos_con_80_20 ''" ;
			String basedatos="200",pintar="si";
		
			ObjTab.Obj_Refrescar(tabla2, modelo2, columnas2, comando, basedatos,pintar,checkbox);
		}
			String establecimiento80="";
			
		 public DefaultTableModel modelo2 = new DefaultTableModel(null, new String[]{"Codigo Producto","Descripcion","Clase Producto","Categoria","Familia","Marca","80/20 De "+establecimiento80}){
				 @SuppressWarnings("rawtypes")
		 		Class[] types = new Class[]{
						java.lang.Object.class,
						java.lang.Object.class,
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
				
				public boolean isCellEditable(int fila, int columna2){
							if(columna2 ==3)
							return true; 
							return false;
						}
			    };
				    
			   JTable tabla2 = new JTable(modelo2);
				public JScrollPane scroll_tabla_filtro = new JScrollPane(tabla2);
				
	JLabel   JLBcod_prod               = new JLabel("");  
	JLabel   JLBdescripcion            = new JLabel("");  
	JLabel   JLBEstatus                = new JLabel("");
	JLabel   JLBClase_De_Producto      = new JLabel("");
	JLabel   JLBCategoria              = new JLabel("");  
	JLabel   JLBFamilia                = new JLabel("");
	JLabel   JLBArea                   = new JLabel("");  
	
	JLabel   JLBCostoPromedio          = new JLabel("");  
	JLabel   JLBPrecioVenta            = new JLabel("");  
	JLabel   JLBMargen          	   = new JLabel("");
	JLabel   JLBMFamilia			   = new JLabel("");
	JLabel   JLBPrecioPorVolumen	   = new JLabel("");  
	
	String htmlini ="<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=BLACK><RIGHT><b><p>";
	String htmlfin ="</p></b></RIGHT></FONT></html>";
	
	String htmlinib ="<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=BLUE><RIGHT><b><p>";
	String htmlfinb ="</p></b></RIGHT></FONT></html>";
	String[][] tablacompleta;
	  Object[]   vector = new Object[10];

	  String codProd = "";
	public Cat_Consulta_De_Cotizacion(String codigo, float costoProm, float PrecioVenta , float Margen, float MFamilia, String PrecioPorVolumen){
		this.setModal(true);
		setSize(1024,600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Datos De Productos BMS");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Escanee o Teclee El Codigo Del Producto"));
		
		codProd = codigo;

		ObjTab.tabla_precargada_derecha(tabla);
		
		int x=10, y=10, width=400,height=20,sep=25,sep2=130;
		
		width=600;
		panel.add(new JLabel(htmlinib+"PRODUCTO:"+htmlfinb)).setBounds        (x       ,y+=sep  ,width   ,height   );
		panel.add(JLBcod_prod).setBounds                                   	  (x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+" - "+htmlfinb)).setBounds        	  (x+200   ,y		,width   ,height   );
		panel.add(JLBdescripcion).setBounds                               	  (x+220   ,y       ,width+200 ,37   );
		 
		panel.add(new JLabel(htmlinib+"ESTATUS:"+htmlfinb)).setBounds	      (x       ,y+=sep*2,width   ,height   );
		panel.add(JLBEstatus).setBounds                              	      (x+sep2  ,y	    ,width   ,height   );
		
		panel.add(new JLabel(htmlinib+"CLASE:"+htmlfinb)).setBounds       	  (x       ,y+=sep	,width   ,height   );
		panel.add(JLBClase_De_Producto).setBounds                         	  (x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"CATEGORIA:"+htmlfinb)).setBounds       (x       ,y+=sep  ,width   ,height   );
		panel.add(JLBCategoria).setBounds                                     (x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"FAMILIA:"+htmlfinb)).setBounds         (x       ,y+=sep  ,width   ,height   );	
		panel.add(JLBFamilia).setBounds                                       (x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"AREA:"+htmlfinb)).setBounds            (x       ,y+=sep  ,width   ,height   );	
		panel.add(JLBArea).setBounds                                          (x+sep2  ,y       ,width   ,height   );
		panel.add(getPanelTabla()).setBounds                                  (x       ,y+=sep  ,1000     ,340      );
		
		x=600;
		y=85;
		sep2=230;
		width=500;
		
		panel.add(new JLabel(htmlinib+"COSTO PROMEDIO:"+htmlfinb)).setBounds    (x       ,y		  ,width   ,height   );
		panel.add(JLBCostoPromedio).setBounds                         	  		(x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"PRECIO DE VENTA:"+htmlfinb)).setBounds   (x       ,y+=sep  ,width   ,height   );
		panel.add(JLBPrecioVenta).setBounds                                     (x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"MARGEN:"+htmlfinb)).setBounds         	(x       ,y+=sep  ,width   ,height   );	
		panel.add(JLBMargen).setBounds                                       	(x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"META FAMILIA:"+htmlfinb)).setBounds      (x       ,y+=sep  ,width   ,height   );	
		panel.add(JLBMFamilia).setBounds                                        (x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"PRECIO POR VOLUMEN:"+htmlfinb)).setBounds(x       ,y+=sep  ,width   ,height   );	
		panel.add(JLBPrecioPorVolumen).setBounds                                (x+sep2  ,y       ,width   ,height   );
		
		cont.add(panel);
		
		JLBdescripcion.setVerticalAlignment(1);
		buscar();
		JLBCostoPromedio.setText(htmlini+costoProm+htmlfin);
		JLBPrecioVenta.setText(htmlini+PrecioVenta+htmlfin);
		JLBMargen.setText(htmlini+Margen+htmlfin);
		JLBMFamilia.setText(htmlini+MFamilia+htmlfin);
		JLBPrecioPorVolumen.setText(htmlini+PrecioPorVolumen+htmlfin);
		
		
		this.tabla.getColumnModel().getColumn( 0).setMinWidth(170);
		this.tabla.getColumnModel().getColumn( 7).setMinWidth(140);
		this.tabla.getColumnModel().getColumn( 8).setMinWidth(140);
		this.tabla.getColumnModel().getColumn( 9).setMinWidth(120);
	}
	
	public void cargar_datos_producto(String cod_prod){
			Object[] tablacompleta= new BuscarSQL().buscarProd(cod_prod);
		  
		    JLBcod_prod.setText         (htmlini+tablacompleta[0].toString()+htmlfin);
			JLBdescripcion.setText      (htmlini+tablacompleta[1].toString()+htmlfin);
			JLBClase_De_Producto.setText(htmlini+tablacompleta[2].toString()+htmlfin);
			JLBCategoria.setText        (htmlini+tablacompleta[3].toString()+htmlfin);
			JLBFamilia.setText          (htmlini+tablacompleta[4].toString()+htmlfin);
			JLBArea.setText             (htmlini+tablacompleta[5].toString()+htmlfin);
			JLBEstatus.setText          (htmlini+tablacompleta[6].toString()+htmlfin);		 
		}
	
	public void cargar_datos_tablas(String cod_prod){
		Object[][] arreglo= new BuscarSQL().Tabla_Precio_De_Competencia(cod_prod);
		
		if(!arreglo[0][0].toString().equals("")){
				for(Object[] obj:arreglo){
				  modelo.addRow(obj);
				}
		}else{
			JOptionPane.showMessageDialog(null,"No Se Encontraron Cotizaciones Del Producto En Los Ultimos 3 Meses","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
			return;
		}
		  
	}
	
	public void buscar(){
		String cod_prod_principal=new BuscarSQL().cod_prod_principal_bms(codProd);
		cargar_datos_producto(cod_prod_principal);
		cargar_datos_tablas(cod_prod_principal);
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Consulta_De_Cotizacion("00015",0,0,0,0,"0").setVisible(true);
		}catch(Exception e){	}
	}
}
