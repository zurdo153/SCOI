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
import Obj_Renders.tablaRenderer;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Estado_De_Resultados extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	JButton btn_buscar = new JButton  ("",new ImageIcon("imagen/buscar-buscar-ampliar-icono-6234-32.png"));
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBFactor= new JLabel();
	
	int cantidad_de_columnas =  (new BuscarSQL().Numero_De_Establecimientos_edo_Resultados()+2);
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
		int a=120,b=300;
		tabla.getColumnModel().getColumn(0).setHeaderValue("CONCEPTO");
		tabla.getColumnModel().getColumn(0).setMaxWidth(b*2);
		tabla.getColumnModel().getColumn(0).setMinWidth(b+a);
		try {
			String[] competidor = new BuscarSQL().Vector_De_Establecimientos_Edo_Resultados();
			for(int i=1; i<cantidad_de_columnas-1; i++){
				tabla.getColumnModel().getColumn(i).setHeaderValue(competidor[(i-1)].toString());
				tabla.getColumnModel().getColumn(i).setMinWidth(a);
				tabla.getColumnModel().getColumn(i).setMaxWidth(a+b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tabla.getColumnModel().getColumn(cantidad_de_columnas-1).setHeaderValue("TOTAL");
		tabla.getColumnModel().getColumn(cantidad_de_columnas-1).setMaxWidth(b);
		tabla.getColumnModel().getColumn(cantidad_de_columnas-1).setMinWidth(b);
		
    	tabla.getTableHeader().setReorderingAllowed(false) ;
    	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		 JScrollPane scrol = new JScrollPane(tabla);
	    return scrol; 
	}
    
	Border blackline, etched, raisedbevel, loweredbevel, empty;	
	String parametroGeneral = "";
	String Lista="";
	String factor="";
	public Cat_Estado_De_Resultados(){
		int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		this.setSize(ancho,525);
		cont.add(panel);
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/diferiencia_de_sueldos_entre_listas_de_raya2_64.png"));
		setTitle("Estado De Resultados");
		panel.setBorder(BorderFactory.createTitledBorder("Seleccione Las Fechas Y De Click a Buscar"));
		
		btn_buscar.setText(	"<html> <FONT FACE="+"arial"+" SIZE=4 COLOR=BLACk>" +
				"		<CENTER><p>Buscar Información</p></CENTER></FONT>" +
				"</html>");
		
		int x=15,y=25,l=100,a=20;

		this.panel.add(new JLabel("Fecha Inicial:")).setBounds(x   ,y    ,l  ,a);
		this.panel.add(JLBlinicio).setBounds                  (x+60,y    ,a  ,a);
		this.panel.add(c_inicio).setBounds                    (x+80,y    ,l  ,a);
		
		this.panel.add(JLBFactor).setBounds                   (x+200,y-15 ,l*5  ,a);
		this.panel.add(btn_buscar).setBounds                  (x+200,y+10,200,a*2);
		
		this.panel.add(new JLabel("Fecha Final:")).setBounds  (x   ,y+=30,l  ,a);
		this.panel.add(JLBfin).setBounds                      (x+60,y    ,a  ,a);
		this.panel.add(c_final).setBounds                     (x+80,y    ,l  ,a);

		panel.add(getPanelTabla()).setBounds(10,y+=30,ancho-30,400);
        
        c_inicio.setDate(cargar_fechas(1));
        c_final.setDate(cargar_fechas(0));
		cargar_factor();
        render_tabla();

		btn_buscar.addActionListener(op_generar);
		c_inicio.getDateEditor().addPropertyChangeListener(opfactorytasa);
		   
        this.addWindowListener(new WindowAdapter() { public void windowOpened( WindowEvent e ){
            	c_inicio.requestFocus();
                      } });
	}
	
	public void render_tabla(){
		for(int i = 0; i < cantidad_de_columnas; i++){
			if(i<=0){
				tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			}else{
				tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			}
		}
	}
	
	
	///TODO
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
	
	
	
	
	
	
	
	
	
	
	
	
	///TODO
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public String[][] calculo_fila_final(){
//		int cantidad_de_columnas_matriz=cantidad_de_columnas;
//		Vector vector = new Vector();
//		for(int i=0; i<tabla.getRowCount(); i++){
//			 if(Boolean.valueOf(tabla.getValueAt(i,5).toString().trim())){
//				  vector.add(model.getValueAt(i,0).toString().trim());
//				  vector.add(model.getValueAt(i,1).toString().trim());
//		     }
//		}
//			String[][] matriz = new String[vector.size()/cantidad_de_columnas_matriz][cantidad_de_columnas_matriz];
//			 int i=0,j =0,columnafor=0;
//			while(i<vector.size()){
//				columnafor=0;
//			      for(int f =0;  f<cantidad_de_columnas_matriz;  f++,columnafor++,i++  ){	
//			  matriz[j][columnafor] = vector.get(i).toString();
//			  }
//			  j++;
//		}
//		return matriz;
//	}
	
//	private Object[][] tabla_guardar_movimientos_bancarios_iniciales(){
//		Object[][] matriz_bancarios = new Object[tabla_mov_bancarios.getRowCount()][7];
//		for(int i=0; i<tabla_mov_bancarios.getRowCount(); i++){
//			matriz_bancarios[i][0] = modelobancarios.getValueAt(i,0).toString().trim();
//			matriz_bancarios[i][1] = modelobancarios.getValueAt(i,1).toString().trim();
//			matriz_bancarios[i][2] = modelobancarios.getValueAt(i,2).toString().trim();
//			matriz_bancarios[i][3] = modelobancarios.getValueAt(i,3).toString().trim();
//			matriz_bancarios[i][4] = modelobancarios.getValueAt(i,4).toString().trim();
//			matriz_bancarios[i][5] = modelobancarios.getValueAt(i,5).toString().trim();
//			matriz_bancarios[i][6] = cmbCuentasBancarias.getSelectedItem().toString();
//		}
//		return matriz_bancarios;
//	}
	

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
	    	JLBFactor.setText(	"<html><FONT FACE="+"arial"+" SIZE=4 COLOR=BLACk>" +
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
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Estado_De_Resultados().setVisible(true);
		}catch(Exception e){	}
	}

}
