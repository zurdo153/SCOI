package Cat_Contabilidad;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

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
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Renders.tablaRenderer;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Estado_De_Resultados extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	String operador[] = {"Selecciona Un Concepto","Mermas","Mermas Por Producto","Uso Interno","Uso Interno Por Producto","Uso Interno Administración","Uso Interno Administración Por Producto","Diferiencias De Inventario","Nomina","Nomina Gastos Venta Impuestos","Nomina Gastos Administracion Impuestos","Gastos De Ventas","Gastos De Ventas NC","Gastos De Administración","Gastos De Administración NC","Gastos Financieros","Gastos Financieros NC"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbConcepto = new JComboBox(operador);
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento_Estado_resultados();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	JButton btn_buscar = new JButton  ("",new ImageIcon("imagen/buscar-buscar-ampliar-icono-6234-32.png"));
	JButton btn_Origen = new JButton  ("",new ImageIcon("imagen/encontrar-busqueda-lupa-de-la-ventana-de-zoom-icono-4008-16.png"));
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBFactor= new JLabel();
	
	int cantidad_de_columnas =  (new BuscarSQL().Numero_De_Establecimientos_edo_Resultados()+3);
	DefaultTableModel model = new DefaultTableModel(0,cantidad_de_columnas){
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
	
	JTable tabla = new JTable(model);
	private JScrollPane getPanelTabla()	{		
		int a=100,b=300;
		tabla.getColumnModel().getColumn(0).setHeaderValue(".");
		tabla.getColumnModel().getColumn(0).setMaxWidth(2);
		tabla.getColumnModel().getColumn(0).setMinWidth(2);
		tabla.getColumnModel().getColumn(1).setHeaderValue("CONCEPTO");
		tabla.getColumnModel().getColumn(1).setMaxWidth(b*2);
		tabla.getColumnModel().getColumn(1).setMinWidth(b);
		try {
			String[] competidor = new BuscarSQL().Vector_De_Establecimientos_Edo_Resultados();
			for(int i=2; i<cantidad_de_columnas-1; i++){
				tabla.getColumnModel().getColumn(i).setHeaderValue(competidor[(i-2)].toString());
				tabla.getColumnModel().getColumn(i).setMinWidth(a);
				tabla.getColumnModel().getColumn(i).setMaxWidth(a+b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tabla.getColumnModel().getColumn(cantidad_de_columnas-1).setHeaderValue("TOTAL");
		tabla.getColumnModel().getColumn(cantidad_de_columnas-1).setMaxWidth(a);
		tabla.getColumnModel().getColumn(cantidad_de_columnas-1).setMinWidth(a+b);
		
    	tabla.getTableHeader().setReorderingAllowed(false) ;
    	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		 JScrollPane scrol = new JScrollPane(tabla);
	    return scrol; 
	}
    
	Border blackline, etched, raisedbevel, loweredbevel, empty;	
	String parametroGeneral = "";
	String Lista="";
	String factor="";
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	public Cat_Estado_De_Resultados(){
		int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		this.setSize(ancho,590);
		cont.add(panel);
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/diferiencia_de_sueldos_entre_listas_de_raya2_64.png"));
		setTitle("Estado De Resultados");
		panel.setBorder(BorderFactory.createTitledBorder("Seleccione Las Fechas Y De Click a Buscar"));
		
		btn_buscar.setText(	"<html> <FONT FACE="+"arial"+" SIZE=4 COLOR=BLACk>" +
				"		<CENTER><p>Buscar Información</p></CENTER></FONT>" +
				"</html>");
		btn_Origen.setText(	"<html> <FONT FACE="+"arial"+" SIZE=2 COLOR=BLACk>" +
				"		<CENTER><p>Generar Reporte Del Concepto</p></CENTER></FONT>" +
				"</html>");
		
		int x=15,y=25,l=100,a=20;

		this.panel.add(new JLabel("Fecha Inicial:")).setBounds(x   ,y    ,l  ,a);
		this.panel.add(JLBlinicio).setBounds                  (x+60,y    ,a  ,a);
		this.panel.add(c_inicio).setBounds                    (x+80,y    ,l  ,a);
		
		this.panel.add(JLBFactor).setBounds                   (x+200,y-15 ,l*5  ,a);
		this.panel.add(btn_buscar).setBounds                  (x+200,y+10,200,a*2);
		
		this.panel.add(cmbConcepto).setBounds                 (x+550,y-15 ,200,a);
		this.panel.add(cmbEstablecimiento).setBounds          (x+550,y+10 ,200,a);
		this.panel.add(btn_Origen  ).setBounds                (x+550,y+35 ,200,a);
		
		this.panel.add(new JLabel("Fecha Final:")).setBounds  (x   ,y+=30,l  ,a);
		this.panel.add(JLBfin).setBounds                      (x+60,y    ,a  ,a);
		this.panel.add(c_final).setBounds                     (x+80,y    ,l  ,a);

		panel.add(getPanelTabla()).setBounds(10,y+=30,ancho-30,460);
        
        c_inicio.setDate(cargar_fechas(1));
        c_final.setDate(cargar_fechas(0));
        cargar_factor();
        render_tabla();

		btn_buscar.addActionListener(op_generar);
		btn_Origen.addActionListener(opGenerarReporte_de_concepto);
		c_inicio.getDateEditor().addPropertyChangeListener(opfactorytasa);
		   
        this.addWindowListener(new WindowAdapter() { public void windowOpened( WindowEvent e ){
            	c_inicio.requestFocus();
                      } });
	}
	
	public void render_tabla(){
		for(int i = 0; i < cantidad_de_columnas; i++){
			if(i<=1){
				tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			}else{
				tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			}
		}
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String[][] calculo_fila_final(){
		int cantidad_de_columnas_matriz=(cantidad_de_columnas-1);
		Vector vector = new Vector();
		for(int i=0; i<tabla.getRowCount(); i++){
			 if(Boolean.valueOf(tabla.getValueAt(i,5).toString().trim())){
				  vector.add(model.getValueAt(i,0).toString().trim());
				  vector.add(model.getValueAt(i,1).toString().trim());
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

	public String validar_fechas(){
		String error = "";
		String fechainicioNull = c_inicio.getDate()+"";
	    if(fechainicioNull.equals("null"))error+= "Fecha  inicio\n";		
		return error;
	}
	PropertyChangeListener opfactorytasa = new PropertyChangeListener() {
 	     public void propertyChange(PropertyChangeEvent e) {
 	    	 if ("date".equals(e.getPropertyName())) {
 	    		if(c_inicio.getDate() != null){
 	    			cargar_factor();	
 	    		}
 	    	 }
 	      }
 	  };
 	  
	public void cargar_factor(){
    	factor= new BuscarSQL().Factor(new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00"); 
    	JLBFactor.setText(	"<html><FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>"+factor+"</p></CENTER></FONT></html>");
    if(factor.equals("Para El Calculo ISR Sobre Venta Falta Alimentar El Factor y Tasa")){
    	btn_buscar.setEnabled(false);
     }else{
    	btn_buscar.setEnabled(true);
   }
}
	  	  
	public Date cargar_fechas(int dias){
		Date date = null;
				  try {
					date = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(dias));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		return date;
	};
	
	ActionListener op_generar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			cargar_factor();
			if(factor.equals("Para El Calculo ISR Sobre Venta Falta Alimentar El Factor y Tasa")){	
				JOptionPane.showMessageDialog(null, "No Se Puede Generar La Consulta Porque Falta Alimentar El Factor y Tasa Del Mes Para El Calculo Del ISR","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
			
		  if(validar_fechas().equals("")){
				  String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
				  String fecha_final  = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+"  23:59:00";
				  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
				
				  Date fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
				  Date fecha2 = sdf.parse(fecha_final , new ParsePosition(0));

				if(fecha1.before(fecha2)){
				  BuscarSQL  matriz_resultados = new BuscarSQL();		
							while(tabla.getRowCount()>0){model.removeRow(0);}
							Object[][] matriz_tabla;
							try {
								matriz_tabla = matriz_resultados.Reporte_De_Estado_de_Resultados(cantidad_de_columnas,fecha_inicio,fecha_final);
								if(matriz_tabla.length==0){
									JOptionPane.showMessageDialog(null, "No se encontraron registros con las condiciones de busqueda proporcionada","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
									return;
								}else{
									 String[] fila = new String[cantidad_de_columnas];
										for(int i=0; i<matriz_tabla.length; i++){
											for(int j=0; j<cantidad_de_columnas; j++){
												fila[j] = matriz_tabla[i][j ]+"";
											}
											model.addRow(fila);
										}
										
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("SUPER_I"),               2);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("SUPER_II"),              3);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("SUPER_III"),             4);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("SUPER_IV"),              5);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("SUPER_V"),               6);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("REFACCIONARIA"),         7);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("FERRETERIA"),            8);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("FIESTILANDIA"),          9);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("PAPER_CITY"),           10);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("PAPER_CITY_II"),        11);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("ESPACIO_35"),           12);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("IZAGAR_FOOD_SUPER_II"), 13);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("IZAGAR_FOOD_SUPER_IV"), 14);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("NAPOLITANA"),           15);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("DEPOSITO_II"),          16);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("IZACEL"),               17);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("CEDIS"),                18);
								}	
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
				}else{
							JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE);
							return;
				}
			}
		}
	};
	
	ActionListener opGenerarReporte_de_concepto = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			 if(validar_fechas().equals("")){
				  String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
				  String fecha_final  = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+"  23:59:00";
				  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
				  Date fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
				  Date fecha2 = sdf.parse(fecha_final , new ParsePosition(0));
	 	if(fecha1.before(fecha2)){		  
			if(cmbConcepto.getSelectedItem().toString().trim().equals("Selecciona Un Concepto")){
			  JOptionPane.showMessageDialog(null,"Es Necesario Seleccionar Un Concepto Para Poder Generar El Reporte Del Concepto", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
               return;
			}else{
				if(cmbEstablecimiento.getSelectedItem().toString().trim().equals("Selecciona un Establecimiento")){	
					  JOptionPane.showMessageDialog(null,"Es Necesario Seleccionar Un Establecimiento Para Poder Generar El Reporte Del Concepto ", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
					  return;
				}else{
					int testigo=0;
					String basedatos="2.26";
					String vista_previa_reporte="no";
					int vista_previa_de_ventana=0;
					String comando="";
					String reporte ="";
					
					if(cmbConcepto.getSelectedItem().toString().trim().equals("Diferiencias De Inventario")){
						comando="exec sp_Reporte_De_Diferiencias_De_Inventario '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+fecha_inicio+"','"+fecha_final+"','"+usuario.getNombre_completo()+"'" ;
						reporte = "Obj_Reporte_De_Inventarios_Fisicos_Estado_Resultados.jrxml";
						testigo=1;
					}
					
					
					if(cmbConcepto.getSelectedItem().toString().trim().equals("Mermas")||cmbConcepto.getSelectedItem().toString().trim().equals("Uso Interno")||cmbConcepto.getSelectedItem().toString().trim().equals("Uso Interno Administración")){
			            comando="exec sp_reporte_de_Mercancia_De_Uso_Interno_en_Un_Periodo '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+fecha_inicio+"','"+fecha_final+"','"+cmbConcepto.getSelectedItem().toString().trim()+"','"+usuario.getNombre_completo()+"'" ;
			            reporte = "Obj_Reporte_De_Mercancia_De_Uso_Interno_O_Merma_Estado_Resultados.jrxml";
			            testigo=1;
					}
					
					if(cmbConcepto.getSelectedItem().toString().trim().equals("Mermas Por Producto")||cmbConcepto.getSelectedItem().toString().trim().equals("Uso Interno Por Producto")||cmbConcepto.getSelectedItem().toString().trim().equals("Uso Interno Administración Por Producto")){
			            comando="exec sp_reporte_de_Mercancia_De_Uso_Interno_en_Un_Periodo_Por_Producto '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+fecha_inicio+"','"+fecha_final+"','"+cmbConcepto.getSelectedItem().toString().trim()+"','"+usuario.getNombre_completo()+"'" ;
			            reporte = "Obj_Reporte_De_Mercancia_De_Uso_Interno_O_Merma_Estado_Resultados_Por_Producto.jrxml";
			            testigo=1;
					}

					if(cmbConcepto.getSelectedItem().toString().trim().equals("Nomina Gastos Administracion Impuestos")||cmbConcepto.getSelectedItem().toString().trim().equals("Nomina Gastos Venta Impuestos")||cmbConcepto.getSelectedItem().toString().trim().equals("Gastos De Administración")||cmbConcepto.getSelectedItem().toString().trim().equals("Gastos De Ventas")||cmbConcepto.getSelectedItem().toString().trim().equals("Gastos Financieros")||cmbConcepto.getSelectedItem().toString().trim().equals("Gastos De Ventas NC") ||cmbConcepto.getSelectedItem().toString().trim().equals("Gastos De Administración NC")||cmbConcepto.getSelectedItem().toString().trim().equals("Gastos Financieros NC")  ){
								comando="exec sp_Reporte_De_Gastos_En_Un_Periodo '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+fecha_inicio+"','"+fecha_final+"','"+cmbConcepto.getSelectedItem().toString().trim()+"','"+usuario.getNombre_completo()+"'" ;
								reporte = "Obj_Reporte_De_Gastos_Estado_Resultados.jrxml";
								testigo=1;
					}
				    
				    if(cmbConcepto.getSelectedItem().toString().trim().equals("Nomina") ){
								comando="exec sp_reporte_de_lista_de_raya_en_un_periodo_edo_resultados '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+fecha_inicio+"','"+fecha_final+"','"+cmbConcepto.getSelectedItem().toString().trim()+"','"+usuario.getNombre_completo()+"'" ;
								reporte = "Obj_Reporte_De_Listas_De_Raya_En_Un_Periodo.jrxml";
								testigo=1;
					}
				    
				    if(testigo==1){
						new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
						
				    }else{
								JOptionPane.showMessageDialog(null,"Error Concepto No Identificado","Avisa Al Adimistrador Del Sistema!", JOptionPane.ERROR_MESSAGE);
								return;
						 }
				}
			}		
	 	 }else{
			JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE);
			return;
			}
		}
			 
		}
	};
	
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Estado_De_Resultados().setVisible(true);
		}catch(Exception e){	}
	}

}
