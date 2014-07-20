package Cat_Auditoria;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Connexion;
import Obj_Auditoria.LoadingBar2;
import Obj_Auditoria.Obj_Alimentacion_Cortes;
import Obj_Auditoria.Obj_Alimentacion_Denominacion;
import Obj_Auditoria.Obj_Alimentacion_Por_Denominacion;
import Obj_Auditoria.Obj_TicketCortes;
import Obj_Lista_de_Raya.Obj_Empleados;
import Obj_Lista_de_Raya.Obj_Puestos;
import Obj_Principal.Componentes;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Alimentacion_Cortes extends JFrame{

	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Connexion con = new Connexion();
	
	JLabel lblFolio_Empleado = new JLabel();
	JLabel lblNombre_Completo = new JLabel();
	JLabel lblEstablecimineto = new JLabel();
	JLabel lblPuesto = new JLabel();
	
	JLabel lblFoto = new JLabel();
	
	JTextArea txaObservaciones = new Componentes().textArea(new JTextArea(), "Observasiones", 500);
	JScrollPane Observasiones = new JScrollPane(txaObservaciones);
	
	JLabel lblDiferencia = new JLabel();
	JLabel lblDiferenciaCorte = new JLabel("$ 0.00");
	
	
	JButton btnFiltro = new JButton(new ImageIcon("imagen/Text preview.png"));
	JButton btnGuardarCorte = new JButton("Guardar");
	JButton btnCancelar = new JButton("Cancelar");
	JButton btnSalir = new JButton("Salir");
	
	
	
	
	
	JCheckBox chStatus = new JCheckBox("Status");
	
	JLabel lblFolio_Corte = new JLabel();
	
	
	JButton btnDeposito = new JButton("dep");
	
	
	
	
	JTextField txtFechaCorte = new JTextField("");
	
	JTextField txtCorteSistema = new JTextField("");
	
	JTextField txtDeposito = new JTextField("");
	JTextField txtEfectivo = new JTextField("");
	

	
	JTextField txtTiempoAire = new JTextField("");
	JTextField txtReciboLuz = new JTextField("");
	


	
	JButton btnEfectivo = new JButton("efe");
	

	
	 Border border = LineBorder.createGrayLineBorder();
//	    Icon warnIcon = MetalIconFactory.getTreeComputerIcon();

	
	
	String Efectivo = "";
	public String img = "";
	String file = "X:\\Empleados\\Un.JPG";
	
	int folio_usuario;
	JLabel lblUsuario = new JLabel("USUARIO: ");
	
	JButton btnAsignacion = new JButton("Asignaciones");
	JButton btnQuitarAsignacion = new JButton("Quitar");
	
	DefaultTableModel modelo_asignaciones = new DefaultTableModel(null,
            new String[]{"Asignacion", "F. Cajero(a)","Nombre Cajera(o)","Total","Cod Estab","Establecimiento","Fecha de Asignacion","Fecha de Liquidacion"}
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class
	    
         };
	     @SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
             return types[columnIndex];
         }
         public boolean isCellEditable(int fila, int columna){
        	 switch(columna){
        	 	case 0 : return false; 
        	 	case 1 : return false; 
        	 	case 2 : return false;
        	 	case 3 : return false;
        	 	case 4 : return false;
        	 	case 5 : return false;
        	 	case 6 : return false;
        	 	case 7 : return false;
        	 	} 				
 			return false;
 		}
	};
	
	JTable tabla_asignaciones = new JTable(modelo_asignaciones);
	JScrollPane scroll = new JScrollPane(tabla_asignaciones,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	JButton btnVauchers = new JButton("Vauchers");
	JButton btnQuitarVauchers = new JButton("Quitar");
	
	DefaultTableModel modelo_vauchers = new DefaultTableModel(null,
            new String[]{"Ticket", "Afiliacion", "Numero De Targeta",  "Fecha E.", "Cod. Aut.", "Tipo De Targeta", "Banco Emisor", "Tipo De Operacion", "Fecha Autorizacion", "Importe"}
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class
	    
         };
	     @SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
             return types[columnIndex];
         }
         public boolean isCellEditable(int fila, int columna){
        	 switch(columna){
        	 	case 0 : return false; 
        	 	case 1 : return false; 
        	 	case 2 : return false;
        	 	case 3 : return false;
        	 	case 4 : return false;
        	 	case 5 : return false;
        	 	case 6 : return false;
        	 	case 7 : return false;
        	 	case 8 : return false;
        	 	case 9 : return false;
        	 	} 				
 			return false;
 		}
	};
	
	JTable tabla_vauchers = new JTable(modelo_vauchers);
	JScrollPane scrollVauchers = new JScrollPane(tabla_vauchers,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	JTextField txtTotalVaucher = new JTextField("");
	
	JLabel lblMarco = new JLabel();
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	
	DecimalFormat formato = new DecimalFormat("#0.00");
	
	String cadenaAsignacionesSeleccionadas="";
	
	int fila = 0;
	int columna = 1;
	
	public Cat_Alimentacion_Cortes(int folio, String estab, String folio_corte) {
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Usuario.png"));
		this.setTitle("Alimentacion Cortes");
		
//		cont.setBackground(new Color(106,151,0));
		
		txaObservaciones.setBorder(border);
		
		lblMarco.setBorder(BorderFactory.createTitledBorder(blackline,"Empleado"));
		lblDiferencia.setBorder(BorderFactory.createTitledBorder(blackline,"Diferiencia de corte"));
		
		Font font = new Font("Verdana", Font.BOLD, 14);
		lblUsuario.setFont(font);
		
		lblDiferenciaCorte.setFont(new Font("arial", Font.BOLD, 65));
		lblDiferenciaCorte.setForeground(Color.BLACK);
		
		int x = 20, y=15, ancho=140, x2=530;
		txtCorteSistema.requestFocus();
		panel.setBorder(BorderFactory.createTitledBorder("Alimentacion Cortes"));
		
		CargarUsuario();
		
		panel.add(lblUsuario).setBounds(x2-50,y,ancho*2+90,20);
		
		panel.add(lblMarco).setBounds(x2-60,y+=25,ancho*2+160,135);
		
		panel.add(new JLabel("Folio Empleado:")).setBounds(x2-50,y+=15,ancho,20);
		panel.add(lblFolio_Empleado).setBounds(x2+50,y,ancho,20);
		panel.add(btnFiltro).setBounds(x2+90,y,30,20);
		
		panel.add(lblFoto).setBounds(x+ancho*5+70,y,110,110);
		
		panel.add(new JLabel("Nombre Completo:")).setBounds(x2-50,y+=30,ancho,20);
		panel.add(lblNombre_Completo).setBounds(x2+60,y,ancho+80,20);
		panel.add(new JLabel("Establecimineto:")).setBounds(x2-50,y+=30,ancho,20);
		panel.add(lblEstablecimineto).setBounds(x2+50,y,ancho+80,20);
		panel.add(new JLabel("Puesto:")).setBounds(x2-50,y+=30,ancho+40,20);
		panel.add(lblPuesto).setBounds(x2,y,ancho+110,20);

		y=20;
		
		panel.add(new JLabel("Folio Corte:")).setBounds(x,y,ancho,20);
		panel.add(lblFolio_Corte).setBounds(ancho+x,y,ancho*2-150,20);
		panel.add(chStatus).setBounds(x+ancho+70,y,70,20);
		
		panel.add(btnAsignacion).setBounds(x,y+=25,ancho,20);
		panel.add(btnQuitarAsignacion).setBounds(x*16,y,ancho,20);
		
		panel.add(scroll).setBounds(x,y+=25,ancho*3+20,105);
		
		panel.add(new JLabel("Efectivo:")).setBounds(x,y+=105,ancho,20);
		panel.add(txtEfectivo).setBounds(ancho-40,y,ancho-40,20);
		panel.add(btnEfectivo).setBounds(ancho*2-80,y,29,20);
		
		panel.add(new JLabel("Corte del Sistema:")).setBounds(x*10+50,y,ancho,20);
		panel.add(txtCorteSistema).setBounds(ancho+x*10+30,y,ancho*2-190,20);
		
		panel.add(new JLabel("Comentario: ")).setBounds(x2-50,y,100,20);
		panel.add(Observasiones).setBounds(x2-50,y+=20,420,80);
		
		panel.add(new JLabel("Fecha:")).setBounds(x,y+=5,ancho,20);
		panel.add(txtFechaCorte).setBounds(ancho-40,y,ancho-40,20);
		
		panel.add(new JLabel("Tiempo Aire: ")).setBounds(x*10+50,y,ancho,20);
		panel.add(txtTiempoAire).setBounds(ancho+x*10+30,y,ancho*2-190,20);
		
		panel.add(new JLabel("Deposito:")).setBounds(x,y+=25,ancho,20);
		panel.add(txtDeposito).setBounds(ancho-40,y,ancho-40,20);
		panel.add(btnDeposito).setBounds(ancho*2-80,y,29,20);
		
		panel.add(new JLabel("Recibo de luz: ")).setBounds(x*10+50,y,ancho,20);
		panel.add(txtReciboLuz).setBounds(ancho+x*10+30,y,ancho*2-190,20);
		
		panel.add(btnVauchers).setBounds(x,y+=25,ancho,20);
		panel.add(btnQuitarVauchers).setBounds(x*16,y,ancho,20);
		
		panel.add(scrollVauchers).setBounds(x,y+=25,ancho*3+20,105);
		
		panel.add(lblDiferencia).setBounds(x2-50,y,ancho*3,105);
		panel.add(lblDiferenciaCorte).setBounds(x2+10,y+15,ancho*2,80);
		
		panel.add(new JLabel("Total De Vauchers: ")).setBounds(x*10+50,y+=110,ancho,20);
		panel.add(txtTotalVaucher).setBounds(ancho+x*10+30,y,ancho*2-190,20);

		panel.add(btnGuardarCorte).setBounds(x*26,y,ancho-40,20);
		panel.add(btnCancelar).setBounds(x*26+110,y,ancho-40,20);
		panel.add(btnSalir).setBounds(x*26+220,y,ancho-40,20);

		tablaRender();
		
//		txtAsignacionCorte.setEnabled(true);
		lblEstablecimineto.setText(estab);
		
		btnAsignacion.addActionListener(opAsignacion);
		btnQuitarAsignacion.addActionListener(opQuitarAsignacion);
		btnVauchers.addActionListener(opVauchers);
		btnQuitarVauchers.addActionListener(opQuitarVauchers);
		btnGuardarCorte.addActionListener(guardar);
		btnCancelar.addActionListener(cancelar);
		btnSalir.addActionListener(salir);
		
		btnFiltro.addActionListener(filtro);
		btnCancelar.setEnabled(false);
		
		txaObservaciones.setLineWrap(true); 
		txaObservaciones.setWrapStyleWord(true);
		
		
		btnEfectivo.addActionListener(opAlimentarDenominacion);
		btnDeposito.addActionListener(opAlimentarDeposito);
		txtCorteSistema.addKeyListener(validaNumericoConPunto);
		txtDeposito.addKeyListener(validaNumericoConPunto2);
		
		lblFoto.setBorder(border);
		
//		Blokear funcion de cerrar ventana de windows
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		cont.add(panel);
		
		Obj_Empleados re = new Obj_Empleados().buscar(folio);
		
		if(re.getFolio()!=0){
			
			ImageIcon tmpIconAux = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp.jpg");
			Icon icono = new ImageIcon(tmpIconAux.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_DEFAULT));
			lblFoto.setIcon(icono);	
			
			lblFolio_Empleado.setText(re.getFolio()+"");
			lblNombre_Completo.setText(re.getNombre()+" "+re.getAp_paterno()+" "+re.getAp_materno()+"");
		}
		
		Obj_Puestos puesto = new Obj_Puestos().buscar(re.getPuesto());
		lblPuesto.setText(puesto.getPuesto());
		
		lblFolio_Corte.setText(folio_corte);

		chStatus.setSelected(true);
		
		txtFechaCorte.setEditable(false);
		txtCorteSistema.setEditable(false);
		txtDeposito.setEditable(false);
		txtEfectivo.setEditable(false);
		
		txtTiempoAire.setEditable(false);
		txtReciboLuz.setEditable(false);
		txtTotalVaucher.setEditable(false);
		
		chStatus.setEnabled(false);
		
		btnDeposito.setEnabled(false);
		btnEfectivo.setEnabled(false);
		
		this.setSize(940,450);
		this.setResizable(true);
		this.setLocationRelativeTo(null);

	}
	
	public void CargarUsuario()
	{
		  File archivo = null;
 	      FileReader fr = null;
 	      BufferedReader br = null;
		 try {
 	         archivo = new File ("Config/users");
 	         fr = new FileReader (archivo);
 	         br = new BufferedReader(fr);
 	         String linea;
 	         
 	        folio_usuario=Integer.parseInt(br.readLine());
 	         while((linea=br.readLine())!=null){
 	        	lblUsuario.setText("Usuario: "+linea);
 	         }
 	      }
 	      catch(Exception e){
 	         e.printStackTrace();
 	      }finally{
 	         try{                   
 	            if( null != fr ){  
 	               fr.close();    
 	            }                 
 	         }catch (Exception e2){
 	            e2.printStackTrace();
 	         }
 	      }
	}
	
	public void tablaRender(){
		tabla_asignaciones.getColumnModel().getColumn(0).setMaxWidth(70);
		tabla_asignaciones.getColumnModel().getColumn(0).setMinWidth(70);
		tabla_asignaciones.getColumnModel().getColumn(1).setMaxWidth(70);
		tabla_asignaciones.getColumnModel().getColumn(1).setMinWidth(70);
		tabla_asignaciones.getColumnModel().getColumn(2).setMaxWidth(210);
		tabla_asignaciones.getColumnModel().getColumn(2).setMinWidth(210);
		tabla_asignaciones.getColumnModel().getColumn(3).setMaxWidth(90);
		tabla_asignaciones.getColumnModel().getColumn(3).setMinWidth(90);
		tabla_asignaciones.getColumnModel().getColumn(4).setMaxWidth(70);
		tabla_asignaciones.getColumnModel().getColumn(4).setMinWidth(70);
		tabla_asignaciones.getColumnModel().getColumn(5).setMaxWidth(160);
		tabla_asignaciones.getColumnModel().getColumn(5).setMinWidth(160);
		tabla_asignaciones.getColumnModel().getColumn(6).setMaxWidth(140);
		tabla_asignaciones.getColumnModel().getColumn(6).setMinWidth(140);
		tabla_asignaciones.getColumnModel().getColumn(7).setMaxWidth(140);
		tabla_asignaciones.getColumnModel().getColumn(7).setMinWidth(140);
//		tabla_asignaciones.getColumnModel().getColumn(8).setMaxWidth(25);
//		tabla_asignaciones.getColumnModel().getColumn(8).setMinWidth(25);
		
		tabla_vauchers.getColumnModel().getColumn(0).setMaxWidth(90);
		tabla_vauchers.getColumnModel().getColumn(0).setMinWidth(90);
		tabla_vauchers.getColumnModel().getColumn(1).setMaxWidth(80);
		tabla_vauchers.getColumnModel().getColumn(1).setMinWidth(80);
		tabla_vauchers.getColumnModel().getColumn(2).setMaxWidth(110);
		tabla_vauchers.getColumnModel().getColumn(2).setMinWidth(110);
		tabla_vauchers.getColumnModel().getColumn(3).setMaxWidth(60);
		tabla_vauchers.getColumnModel().getColumn(3).setMinWidth(60);
		tabla_vauchers.getColumnModel().getColumn(4).setMaxWidth(60);
		tabla_vauchers.getColumnModel().getColumn(4).setMinWidth(60);
		tabla_vauchers.getColumnModel().getColumn(5).setMaxWidth(110);
		tabla_vauchers.getColumnModel().getColumn(5).setMinWidth(110);
		tabla_vauchers.getColumnModel().getColumn(6).setMaxWidth(100);
		tabla_vauchers.getColumnModel().getColumn(6).setMinWidth(100);
		tabla_vauchers.getColumnModel().getColumn(7).setMaxWidth(70);
		tabla_vauchers.getColumnModel().getColumn(7).setMinWidth(70);
		tabla_vauchers.getColumnModel().getColumn(8).setMaxWidth(130);
		tabla_vauchers.getColumnModel().getColumn(8).setMinWidth(130);
		tabla_vauchers.getColumnModel().getColumn(9).setMaxWidth(70);
		tabla_vauchers.getColumnModel().getColumn(9).setMinWidth(70);
		
		TableCellRenderer render = new TableCellRenderer() { 
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
			boolean hasFocus, int row, int column) { 
				
				tabla_asignaciones.setAutoResizeMode(JTable.AUTO_RESIZE_OFF );
				tabla_asignaciones.getTableHeader().setReorderingAllowed(false) ;
				
				tabla_vauchers.setAutoResizeMode(JTable.AUTO_RESIZE_OFF );
				tabla_vauchers.getTableHeader().setReorderingAllowed(false) ;
				
				Component componente = null;
				
						componente = new JLabel(value == null? "": value.toString());
						if(row %2 == 0){
							((JComponent) componente).setOpaque(true); 
							componente.setBackground(new java.awt.Color(177,177,177));	
						}
//						if(Boolean.parseBoolean(modelo_asignaciones.getValueAt(row,3).toString())){
//							((JComponent) componente).setOpaque(true); 
//							componente.setBackground(new java.awt.Color(186,143,73));
//						}
						if(table.getSelectedRow() == row){
							((JComponent) componente).setOpaque(true); 
							componente.setBackground(new java.awt.Color(186,143,73));
						}
						((JLabel) componente).setHorizontalAlignment(SwingConstants.LEFT);
				return componente;
			} 
		}; 
		tabla_asignaciones.getColumnModel().getColumn(0).setCellRenderer(render); 
		tabla_asignaciones.getColumnModel().getColumn(1).setCellRenderer(render); 
		tabla_asignaciones.getColumnModel().getColumn(2).setCellRenderer(render);
		tabla_asignaciones.getColumnModel().getColumn(3).setCellRenderer(render);
		tabla_asignaciones.getColumnModel().getColumn(4).setCellRenderer(render);
		tabla_asignaciones.getColumnModel().getColumn(5).setCellRenderer(render);
		tabla_asignaciones.getColumnModel().getColumn(6).setCellRenderer(render);
		tabla_asignaciones.getColumnModel().getColumn(7).setCellRenderer(render);
		
		tabla_vauchers.getColumnModel().getColumn(0).setCellRenderer(render); 
		tabla_vauchers.getColumnModel().getColumn(1).setCellRenderer(render); 
		tabla_vauchers.getColumnModel().getColumn(2).setCellRenderer(render);
		tabla_vauchers.getColumnModel().getColumn(3).setCellRenderer(render);
		tabla_vauchers.getColumnModel().getColumn(4).setCellRenderer(render);
		tabla_vauchers.getColumnModel().getColumn(5).setCellRenderer(render);
		tabla_vauchers.getColumnModel().getColumn(6).setCellRenderer(render);
		tabla_vauchers.getColumnModel().getColumn(7).setCellRenderer(render);
		tabla_vauchers.getColumnModel().getColumn(8).setCellRenderer(render);
		tabla_vauchers.getColumnModel().getColumn(9).setCellRenderer(render);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
//	btnDeposito
	ActionListener opAlimentarDeposito = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
				btnFiltro.setEnabled(false);
				
				Obj_Alimentacion_Cortes folio_corte = new Obj_Alimentacion_Cortes().buscar_folio_corte_deposito(lblFolio_Corte.getText());
				
				if(folio_corte.getFolio_corte().equals("")){
					new Cat_Alimentacion_Deposito().setVisible(true);
				}else{
					new Cat_Alimentacion_Deposito_Modificar().setVisible(true);
				}
//			}
		}
	};
	
	
	
	
	
	
	
	
	
	
	
	
//	btnEfectivo
	ActionListener opAlimentarDenominacion = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
//			if(validacion()!="") {
//				JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n"+validacion(), "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
//				return;
//			}else{
				
				btnFiltro.setEnabled(false);
				
				Obj_Alimentacion_Cortes folio_corte = new Obj_Alimentacion_Cortes().buscar_folio_corte(lblFolio_Corte.getText());
				
				if(folio_corte.getFolio_corte().equals("")){
					new Cat_Alimentacion_Por_Denominacion().setVisible(true);
				}else{
					 Obj_Alimentacion_Por_Denominacion alimentacion = new Obj_Alimentacion_Por_Denominacion().buscar_folio_corte_modificar(lblFolio_Corte.getText());
					String fecha = (alimentacion.getFecha());
					
					new Cat_Alimentacion_Por_Denominacion_Modificar(fecha).setVisible(true);
				}
//			}
		}
	};
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(validaCampos()!="") {
				JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n"+validaCampos(), "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
				return;
			}else{
				try{
					Obj_Alimentacion_Cortes corte = new Obj_Alimentacion_Cortes();
						
						corte.setFolio_corte(lblFolio_Corte.getText());
						corte.setFolio_empleado(Integer.parseInt(lblFolio_Empleado.getText()+""));
						corte.setNombre(lblNombre_Completo.getText()+"");
						corte.setPuesto(lblPuesto.getText()+"");
						corte.setEstablecimiento(lblEstablecimineto.getText()+"");
//						corte.setAsignacion(txtAsignacionCorte.getText()+"");
						
						float corteSistema=Float.parseFloat(txtCorteSistema.getText()+"");
						float deposito =Float.parseFloat(txtDeposito.getText()+"");
						float efectivo =Float.parseFloat(txtEfectivo.getText()+"");
						
						corte.setCorte_sistema(corteSistema);
						corte.setDeposito(deposito);
						corte.setEfectivo(efectivo);
						corte.setDiferencia_corte(corteSistema-(deposito+efectivo));
						
						if(txaObservaciones.getText().length()!=0){
							corte.setComentario(txaObservaciones.getText());
						}else{
							corte.setComentario("");
						}
						corte.setFecha(txtFechaCorte.getText()+"");
						
						if(chStatus.isSelected()){
							corte.setStatus(true);
						}else{
							corte.setStatus(false);
						}
						
						corte.setTiempo_aire(Float.parseFloat(txtTiempoAire.getText()));
						corte.setRecibo_luz(Float.parseFloat(txtReciboLuz.getText()));
						
						if(corte.guardar()){
//TICKET--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
							Obj_TicketCortes t = new Obj_TicketCortes();
							
							//Ticket
							t.setIzagar					("     SUPERMERCADO LA COMPETIDORA S.A DE C.V");
							t.setTalon					("                                  TALON DE CORTE");
							t.setFolio_emp				("  FOLIO EMPLEADO:    " +lblFolio_Empleado.getText());
							t.setEmpleado				("  EMPLEADO:   " +lblNombre_Completo.getText() );
							t.setPuesto					("  PUESTO:   " +lblPuesto.getText());
							t.setFolio_corte			("  FOLIO DE CORTE:       " +lblFolio_Corte.getText());
							t.setEstablecimineto		("  ESTABLECIMIENTO:   " +lblEstablecimineto.getText() );
							t.setFecha					("  FECHA: " + txtFechaCorte.getText());
//							t.setAsignacion				("  ASIGNACION:            " +txtAsignacionCorte.getText());
							t.setTabla					("  CORTE DEL SISTEMA    DEPOSITO    EFECTIVO");
							t.setCorte_sistema			("   " + txtCorteSistema.getText());
							t.setDeposito				("    " + txtDeposito.getText());
							t.setEfectivo				(txtEfectivo.getText());
							t.setDiferencia				("  DIFERENCIA DE CORTE:      " + lblDiferenciaCorte.getText());
							t.setEfectivo				(txtEfectivo.getText());
							t.setDiferencia				("  DIFERENCIA DE CORTE:      " + lblDiferenciaCorte.getText());
							t.setTiempo_aire			("  Tiempo Aire:      "+txtTiempoAire.getText());
							t.setResivo_luz				("  Recibo de Luz:      "+txtReciboLuz.getText());
							t.guardar();

							dispose();
							new LoadingBar2().setVisible(true);
						}else{
							JOptionPane.showMessageDialog(null, "Ha ocurrido un error al guardar el corte","Error",JOptionPane.WARNING_MESSAGE);
							return;
						}
//FIN DE GUARDAR CORTE-------------------------------------------------------------------------------------
					}catch(Exception ee)
					{
						JOptionPane.showMessageDialog(null,"Ha ocurrido un error\n Verifique los campos ");
						return;
					}
//FIN DE TICKET-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------				
				}	
			}
	};
	
	ActionListener cancelar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
					Obj_Alimentacion_Cortes folio_corte_denominacion = new Obj_Alimentacion_Cortes().buscar_folio_corte(lblFolio_Corte.getText());
					
					if(folio_corte_denominacion.getFolio_corte().equals("")){
						txtFechaCorte.setText("");
						txtEfectivo.setText("");
						lblDiferenciaCorte.setText("");
						btnFiltro.setEnabled(true);
						dispose();
						JOptionPane.showMessageDialog(null, "El registro no existe","Error",JOptionPane.ERROR_MESSAGE);
						return;
					}else{
						
						if(folio_corte_denominacion.eliminar(lblFolio_Corte.getText())){
							txtFechaCorte.setText("");
							txtEfectivo.setText("");
							lblDiferenciaCorte.setText("");
							btnFiltro.setEnabled(true);
							btnSalir.setEnabled(true);
							dispose();
							JOptionPane.showMessageDialog(null, "La registro se elimino exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE);
							return;
						}else{
							JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar eliminar el registro","Error",JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
				}
			};

			
	ActionListener opAsignacion = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Filtrar_Asignaciones().setVisible(true);
			
		}
	};
	
	ActionListener opVauchers = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			if(tabla_asignaciones.getRowCount()<1){
      			 JOptionPane.showMessageDialog(null, "Seleccione Por Lo Menos Un Folio De Asignacion","Aviso",JOptionPane.INFORMATION_MESSAGE);
                 return;
				}else{
					new Cat_Filtrar_Vauchers().setVisible(true);
				}
			
		}
	};
	
	ActionListener opQuitarAsignacion = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			int fila = tabla_asignaciones.getSelectedRow();
			
			if(fila<0){
//				mensaje (seleccionar fila que desea eliminar)
   			 JOptionPane.showMessageDialog(null, "Debe Seleccionar La Asignacion Que Desea Quitar","Aviso",JOptionPane.INFORMATION_MESSAGE);
              return;
			}else{
//				Object folio =  tabla.getValueAt(fila, 0);
				double cantidad =  Double.valueOf(tabla_asignaciones.getValueAt(fila, 3).toString().trim());
				
				txtCorteSistema.setText( formato.format((Double.valueOf(txtCorteSistema.getText()) - cantidad))+"" );
				
				modelo_asignaciones.removeRow(fila);
			}
			
			if(tabla_asignaciones.getRowCount()==0){
				btnDeposito.setEnabled(false);
				btnEfectivo.setEnabled(false);
			}
			
			 
		}
	};
	
	ActionListener opQuitarVauchers = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			int fila = tabla_vauchers.getSelectedRow();
			
			if(fila<0){
//				mensaje (seleccionar fila que desea eliminar)
   			 JOptionPane.showMessageDialog(null, "Debe Seleccionar El Folio Del Ticket Que Desea Quitar","Aviso",JOptionPane.INFORMATION_MESSAGE);
              return;
			}else{
//				Object folio =  tabla.getValueAt(fila, 0);
				double cantidad =  Double.valueOf(tabla_vauchers.getValueAt(fila, 9).toString().trim());
				
				txtTotalVaucher.setText( formato.format((Double.valueOf(txtTotalVaucher.getText()) - cantidad))+"" );
				
				modelo_vauchers.removeRow(fila);
			}
			
		}
	};
			
	ActionListener salir = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			dispose();
		}
	};
	
	ActionListener filtro = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			dispose();
			new Cat_Filtro_Alimentacion_Cortes_De_Cajeros().setVisible(true);
			
		}
	};	
	
	public void panelEnabledFalse(){	
		txtCorteSistema.setEditable(false);
	}

	KeyListener numerico_action = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
			char caracter = e.getKeyChar();

		   if(((caracter < '0') ||
		        (caracter > '9')) &&
		        (caracter != KeyEvent.VK_BACK_SPACE)){
		    	e.consume(); 
		    }			
		}
		@Override
		public void keyPressed(KeyEvent e){}
		@Override
		public void keyReleased(KeyEvent e){}
	};
		
	KeyListener validaNumericoConPunto = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
			char caracter = e.getKeyChar();
		    if(((caracter < '0') ||	
		    	(caracter > '9')) && 
		    	(caracter != '.' )){
		    	e.consume();
		    	}
		    	
		   if (caracter==KeyEvent.VK_PERIOD){
		    		    	
		    	String texto = txtCorteSistema.getText().toString();
				if (texto.indexOf(".")>-1) e.consume();
				
			}
		    		    		       	
		}
		@Override
		public void keyPressed(KeyEvent e){}
		@Override
		public void keyReleased(KeyEvent e){}
								
	};
	
	KeyListener validaNumericoConPunto2 = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
			char caracter = e.getKeyChar();
		    if(((caracter < '0') ||	
		    	(caracter > '9')) && 
		    	(caracter != '.' )){
		    	e.consume();
		    	}
		    	
		   if (caracter==KeyEvent.VK_PERIOD){
		    		    	
		    	String texto = txtDeposito.getText().toString();
				if (texto.indexOf(".")>-1) e.consume();
				
			}
		    		    		       	
		}
		@Override
		public void keyPressed(KeyEvent e){}
		@Override
		public void keyReleased(KeyEvent e){}
								
	};

//	private String validacion(){
//		String error="";
//		
////		if(txtAsignacionCorte.getText().equals(""))error+= "Asignacion\n";
//		if(txtCorteSistema.getText().equals(""))error+= "Corte del Sistema\n";
//		if(txtDeposito.getText().equals(""))error+= "Depocito\n";
//				
//		return error;
//	}
	
	private String validaCampos(){
		String error="";
		
//		if(txtAsignacionCorte.getText().equals(""))error+= "Asignacion\n";
		if(txtCorteSistema.getText().equals(""))error+= "Corte del Sistema\n";
		if(txtDeposito.getText().equals(""))error+= "Depocito\n";
		if(txtEfectivo.getText().equals(""))error+= "Efectivo\n";
				
		return error;
	}
	
	public int getFilas(String qry){
		int filas=0;
		try {
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery(qry);
			while(rs.next()){
				filas++;
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return filas;
	}
	

	//guardar denominaciones
	public class Cat_Alimentacion_Por_Denominacion extends JDialog {
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		JTextField txtAsignacion = new JTextField("as");
//		JTextField txtNombre_Completo = new JTextField();
//		JButton btn_refrescar= new JButton("Refrescar");
		
		public JToolBar menu_toolbar = new JToolBar();
		JButton btn_guardar= new JButton(new ImageIcon("Iconos/save_icon&16.png"));
		
		JLabel lblEmpleado = new JLabel();
		JDateChooser txtFecha = new JDateChooser();
		JTextField txtTotal = new JTextField();
		
		DefaultTableModel tabla_model = new DefaultTableModel(new Obj_Alimentacion_Por_Denominacion().get_tabla_model(), new String[]{"Folio", "Denominacion","# Denominacion", "Valor", "$ Cantidad"}) {
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
	        	 	case 0 : return false; 
	        	 	case 1 : return false; 
	        	 	case 2 : return false; 
	        	 	case 3 : return false; 
	        	 	case 4 :
	        	 		float suma = 0;
		    			for(int i=0; i<tabla.getRowCount(); i++){
		    				if(tabla_model.getValueAt(i,4).toString().length() == 0){
		    					suma = suma + 0;
		    				}else{
		    					if(isNumeric(tabla_model.getValueAt(i,4).toString().trim())){
			    					suma += (Float.parseFloat(tabla_model.getValueAt(i,4).toString()))*(Float.parseFloat(tabla_model.getValueAt(i,2).toString())*Float.parseFloat(tabla_model.getValueAt(i,3).toString()));
								}else{
									JOptionPane.showMessageDialog(null, "La cantidad en el Folio "+tabla_model.getValueAt(i,0).toString()+"  están mal en su formato:\n","Error",JOptionPane.ERROR_MESSAGE);
									tabla_model.setValueAt("", i, 4);
								}
		    				}
		    			}
		    			txtTotal.setText(suma+"");
	        	 		return true; 
	        	 } 				
	 			return false;
	 		}
		};
		
		JTable tabla = new JTable(tabla_model);
		JScrollPane scroll_tabla = new JScrollPane(tabla);
		
		public Cat_Alimentacion_Por_Denominacion(){
			
			Constructor();
		}
		
		public void Constructor(){
			this.setModal(true);
			this.setTitle("Alimentación de Denominaciones");
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/captura_nomina_icon&16.png"));
			this.txtFecha.setIcon(new ImageIcon("Iconos/calendar_icon&16.png"));
			
			lblEmpleado.setForeground(Color.GRAY);
			
			this.panel.add(menu_toolbar).setBounds(0,0,150,25);
			this.panel.add(lblEmpleado).setBounds(30,35,350,20);
			this.panel.add(new JLabel("Fecha: ")).setBounds(420,35,100,20);
			this.panel.add(txtFecha).setBounds(460,35,90,20);
			this.panel.add(txtAsignacion).setBounds(560,35,110,20);
			
			this.panel.add(scroll_tabla).setBounds(20,60,730,420);
			
			this.panel.add(new JLabel("Total de Cantidades:")).setBounds(470,485,100,20);
			this.panel.add(txtTotal).setBounds(580,485,90,20);
			
			this.menu_toolbar.add(btn_guardar);
			this.menu_toolbar.setEnabled(false);
			this.txtAsignacion.setEditable(false);
			this.txtTotal.setEditable(false);
			
			this.init_tabla();
			
			this.cont.add(panel);
			
			this.lblEmpleado.setText(lblNombre_Completo.getText());
			this.btn_guardar.addActionListener(op_guardar);
			this.tabla.addKeyListener(op_key);
			this.addWindowListener(op_limpiar);
			
			this.setSize(780,550);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
		
		WindowListener op_limpiar = new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
					txtEfectivo.setText("");
			}
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
		};
		
		KeyListener op_key = new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}
			public void keyReleased(KeyEvent e) {
				float suma = 0;
				for(int i=0; i<tabla.getRowCount(); i++){
					
					if(tabla_model.getValueAt(i,4).toString().equals("")){
						suma = suma + 0;
					}else{
						
						if(isNumeric(tabla_model.getValueAt(i,4).toString().trim())){
	    					suma += Float.parseFloat(tabla_model.getValueAt(i,4).toString())*(Float.parseFloat(tabla_model.getValueAt(i,2).toString())*Float.parseFloat(tabla_model.getValueAt(i,3).toString()));
						}else{
							JOptionPane.showMessageDialog(null, "La nomina en el establecimiento "+tabla_model.getValueAt(i,0).toString()+"  están mal en su formato:\n","Error",JOptionPane.ERROR_MESSAGE);
							tabla_model.setValueAt("", i, 4);
							return;
						}
					}
				}
				txtTotal.setText(suma+"");
				txtEfectivo.setText(suma+"");
				lblDiferenciaCorte.setText((Float.parseFloat(txtCorteSistema.getText())-(suma+Float.parseFloat(txtDeposito.getText())))+"");

			}
			public void keyPressed(KeyEvent e) {
			}
		};
		
		ActionListener op_guardar = new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
				if(tabla.isEditing()){
					tabla.getCellEditor().stopCellEditing();
				}
				
				if(valida_tabla() != ""){
					txtTotal.setText("0.0");
					JOptionPane.showMessageDialog(null, "Las siguientes celdas están mal en su formato:\n"+valida_tabla(),"Error",JOptionPane.ERROR_MESSAGE);
					return;
				}else{
					
						if(txtFecha.getDate()==null){
							JOptionPane.showMessageDialog(null, "La Fecha es Requerida","Error",JOptionPane.ERROR_MESSAGE);
							return;
						}else{
						
						if(JOptionPane.showConfirmDialog(null, "¿Desea guardar la lista de Denominaciones?") == 0){
							Obj_Alimentacion_Denominacion Alim_Denom = new Obj_Alimentacion_Denominacion();
							
							Alim_Denom.setAsignacion(txtAsignacion.getText().trim());
							Alim_Denom.setEmpleado(lblEmpleado.getText());
							Alim_Denom.setFecha(new SimpleDateFormat("dd/MM/yyyy").format(txtFecha.getDate()));
							Alim_Denom.setEstablecimiento(lblFolio_Corte.getText());

							if(Alim_Denom.guardar(tabla_guardar())){
								txtFechaCorte.setText(new SimpleDateFormat("dd/MM/yyyy").format(txtFecha.getDate()));
								
								if(!txtEfectivo.getText().equals("")){
									lblDiferenciaCorte.setText((Float.parseFloat(txtCorteSistema.getText())-(Float.parseFloat(txtEfectivo.getText())+Float.parseFloat(txtDeposito.getText())))+"");
								}else{
									txtEfectivo.setText("0.0");
									lblDiferenciaCorte.setText(Float.parseFloat(txtCorteSistema.getText())-(Float.parseFloat(txtDeposito.getText()))+"");
								}
								btnCancelar.setEnabled(true);
								btnSalir.setEnabled(false);
								dispose();
								JOptionPane.showMessageDialog(null, "La tabla Denominaciones se guardó exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE);
								return;
							}else{
								txtTotal.setText("0.0");
								JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar guardar la tabla","Error",JOptionPane.ERROR_MESSAGE);
								return;
							}
						}else{
							return;
						}
					}
				}
			}
		};
		
		private Object[][] tabla_guardar(){
			Object[][] matriz = new Object[tabla.getRowCount()][5];
			for(int i=0; i<tabla.getRowCount(); i++){
				
				matriz[i][1] = tabla_model.getValueAt(i,0).toString().trim();
				matriz[i][1] = tabla_model.getValueAt(i, 1).toString().trim();
				matriz[i][2] = tabla_model.getValueAt(i, 2).toString().trim();
				matriz[i][3] = tabla_model.getValueAt(i, 3).toString().trim();
				
				if(tabla_model.getValueAt(i,4).toString().trim().length() == 0){
					matriz[i][4] = Float.parseFloat("0"); 
				}else{
					matriz[i][4] = Float.parseFloat(tabla_model.getValueAt(i,4).toString().trim());
				}
			}
			return matriz;
		}
		
		private String valida_tabla(){
			String error = "";
			for(int i=0; i<tabla.getRowCount(); i++){
				try{
					if(!isNumeric(tabla_model.getValueAt(i,4).toString())){
						error += "   La celda de la columna Cantidad no es un número en el [Folio: "+tabla_model.getValueAt(i,0)+"]\t\n";
						tabla_model.setValueAt("",i, 4);
					}
				} catch(Exception e){
					JOptionPane.showMessageDialog(null, "La tabla tiene una celda con texto en lugar de un valor numérico: \n"+e,"Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			return error;
		}
		
		public void init_tabla(){
			this.tabla.getTableHeader().setReorderingAllowed(false) ;
			
	    	this.tabla.getColumnModel().getColumn(0).setMaxWidth(120);
	    	this.tabla.getColumnModel().getColumn(0).setMinWidth(120);		
	    	this.tabla.getColumnModel().getColumn(1).setMaxWidth(290);
	    	this.tabla.getColumnModel().getColumn(1).setMinWidth(290);
	    	this.tabla.getColumnModel().getColumn(2).setMaxWidth(120);
	    	this.tabla.getColumnModel().getColumn(2).setMinWidth(120);		
	    	this.tabla.getColumnModel().getColumn(3).setMaxWidth(120);
	    	this.tabla.getColumnModel().getColumn(3).setMinWidth(120);
	    	this.tabla.getColumnModel().getColumn(4).setMaxWidth(100);
	    	this.tabla.getColumnModel().getColumn(4).setMinWidth(100);
	    	
			TableCellRenderer render = new TableCellRenderer() { 
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
				boolean hasFocus, int row, int column) { 
					JLabel lbl = new JLabel(value == null? "": value.toString());
					if(row%2==0){
							lbl.setOpaque(true); 
							lbl.setBackground(new java.awt.Color(177,177,177));
					} 
					if(table.getSelectedRow() == row){
						lbl.setOpaque(true); 
						lbl.setBackground(new java.awt.Color(186,143,73));
					}
					switch(column){
						case 0 : lbl.setHorizontalAlignment(SwingConstants.CENTER); break;
						case 1 : lbl.setHorizontalAlignment(SwingConstants.LEFT); break;
						case 2 : lbl.setHorizontalAlignment(SwingConstants.CENTER); break;
						case 3 : lbl.setHorizontalAlignment(SwingConstants.CENTER); break;
						case 4 : lbl.setHorizontalAlignment(SwingConstants.CENTER); break;
					}
				return lbl; 
				} 
			}; 

			this.tabla.getColumnModel().getColumn(0).setCellRenderer(render); 
			this.tabla.getColumnModel().getColumn(1).setCellRenderer(render); 
			this.tabla.getColumnModel().getColumn(2).setCellRenderer(render); 
			this.tabla.getColumnModel().getColumn(3).setCellRenderer(render); 
			this.tabla.getColumnModel().getColumn(4).setCellRenderer(render); 
			
			float suma = 0;
			for(int i=0; i<tabla.getRowCount(); i++){
				if(tabla_model.getValueAt(i,4).toString().length() == 0){
					suma = suma + 0;
				}else{
					suma += Float.parseFloat(tabla_model.getValueAt(i,4).toString())*(Float.parseFloat(tabla_model.getValueAt(i,2).toString())*Float.parseFloat(tabla_model.getValueAt(i,3).toString()));
				}
			}
			txtTotal.setText(suma+"");
	    }
		
	    private boolean isNumeric(String cadena){
	    	try {
	    		if(cadena.equals("")){
	        		return true;
	    		}else{
	    			Float.parseFloat(cadena);
	        		return true;
	    		}
	    	} catch (NumberFormatException nfe){
	    		return false;
	    	}
	    }

	}
	
	//modificar denominaciones
	public class Cat_Alimentacion_Por_Denominacion_Modificar extends JDialog {
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		JTextField txtAsignacion = new JTextField("as");
		
		public JToolBar menu_toolbar = new JToolBar();
		JButton btn_actualizar= new JButton(new ImageIcon("Iconos/save_icon&16.png"));
		
		JLabel lblEmpleado = new JLabel();
		JDateChooser txtFecha = new JDateChooser();
		JTextField txtTotal = new JTextField();
		
		DefaultTableModel tabla_model = new DefaultTableModel(new Obj_Alimentacion_Por_Denominacion().get_tabla_model_modificar(lblFolio_Corte.getText()), new String[]{"Folio", "Denominacion","# Denominacion", "Valor", "$ Cantidad"}) {
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
	        	 	case 0 : return false; 
	        	 	case 1 : return false; 
	        	 	case 2 : return false; 
	        	 	case 3 : return false; 
	        	 	case 4 :
	        	 		float suma = 0;
		    			for(int i=0; i<tabla.getRowCount(); i++){
		    				if(tabla_model.getValueAt(i,4).toString().length() == 0){
		    					suma = suma + 0;
		    				}else{
		    					if(isNumeric(tabla_model.getValueAt(i,4).toString().trim())){
			    					suma += (Float.parseFloat(tabla_model.getValueAt(i,4).toString()))*(Float.parseFloat(tabla_model.getValueAt(i,2).toString())*Float.parseFloat(tabla_model.getValueAt(i,3).toString()));
								}else{
									JOptionPane.showMessageDialog(null, "La cantidad en el Folio "+tabla_model.getValueAt(i,0).toString()+"  están mal en su formato:\n","Error",JOptionPane.ERROR_MESSAGE);
									tabla_model.setValueAt("", i, 4);
								}
		    				}
		    			}
		    			txtTotal.setText(suma+"");
	        	 		return true; 
	        	 } 				
	 			return false;
	 		}
		};
		
		JTable tabla = new JTable(tabla_model);
		JScrollPane scroll_tabla = new JScrollPane(tabla);
		
		public Cat_Alimentacion_Por_Denominacion_Modificar(String fecha){
			Constructor();
			
			this.lblEmpleado.setText(lblNombre_Completo.getText());
//			this.txtAsignacion.setText(txtAsignacionCorte.getText());
			
			try {
				Date date_fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
				this.txtFecha.setDate(date_fecha);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		
		public void Constructor(){
			this.setModal(true);
			this.setTitle("Alimentación de Denominaciones");
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/captura_nomina_icon&16.png"));
			this.txtFecha.setIcon(new ImageIcon("Iconos/calendar_icon&16.png"));
			
			lblEmpleado.setForeground(Color.GRAY);
			
			this.panel.add(menu_toolbar).setBounds(0,0,250,25);
			this.panel.add(lblEmpleado).setBounds(30,35,350,20);
			this.panel.add(new JLabel("Fecha: ")).setBounds(420,35,100,20);
			this.panel.add(txtFecha).setBounds(460,35,90,20);
			this.panel.add(txtAsignacion).setBounds(560,35,110,20);
			
			this.panel.add(scroll_tabla).setBounds(20,60,730,420);
			
			this.panel.add(new JLabel("Total de Cantidades:")).setBounds(470,485,100,20);
			this.panel.add(txtTotal).setBounds(580,485,90,20);
			
			this.menu_toolbar.add(btn_actualizar);
			this.menu_toolbar.setEnabled(true);
			this.txtAsignacion.setEditable(false);
			this.txtTotal.setEditable(false);
			
			this.init_tabla();
			
			this.cont.add(panel);
			this.btn_actualizar.addActionListener(op_actualizar);
			
			this.tabla.addKeyListener(op_key);
			
			this.setSize(780,550);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
		
		KeyListener op_key = new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}
			public void keyReleased(KeyEvent e) {
				float suma = 0;
				for(int i=0; i<tabla.getRowCount(); i++){
					
					if(tabla_model.getValueAt(i,4).toString().equals("")){
						suma = suma + 0;
					}else{
						
						if(isNumeric(tabla_model.getValueAt(i,4).toString().trim())){
	    					suma += Float.parseFloat(tabla_model.getValueAt(i,4).toString())*(Float.parseFloat(tabla_model.getValueAt(i,2).toString())*Float.parseFloat(tabla_model.getValueAt(i,3).toString()));
						}else{
							JOptionPane.showMessageDialog(null, "La nomina en el establecimiento "+tabla_model.getValueAt(i,0).toString()+"  están mal en su formato:\n","Error",JOptionPane.ERROR_MESSAGE);
							tabla_model.setValueAt("", i, 4);
							return;
						}
					}
				}
				txtTotal.setText(suma+"");
				txtEfectivo.setText(suma+"");
				lblDiferenciaCorte.setText((Float.parseFloat(txtCorteSistema.getText())-(suma+Float.parseFloat(txtDeposito.getText())))+"");
			}
			public void keyPressed(KeyEvent e) {
			}
		};
		
		ActionListener op_actualizar = new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
				if(tabla.isEditing()){
					tabla.getCellEditor().stopCellEditing();
				}
				
				if(valida_tabla() != ""){
					txtTotal.setText("0.0");
					JOptionPane.showMessageDialog(null, "Las siguientes celdas están mal en su formato:\n"+valida_tabla(),"Error",JOptionPane.ERROR_MESSAGE);
					return;
				}else{
					
						if(txtFecha.getDate()==null){
							JOptionPane.showMessageDialog(null, "La Fecha es Requerida","Error",JOptionPane.ERROR_MESSAGE);
							return;
						}else{
						
						if(JOptionPane.showConfirmDialog(null, "¿Desea guardar la lista de Denominaciones?") == 0){
							Obj_Alimentacion_Denominacion Alim_Denom = new Obj_Alimentacion_Denominacion();
							
							Alim_Denom.setAsignacion(txtAsignacion.getText().trim());
							Alim_Denom.setEmpleado(lblEmpleado.getText());
							Alim_Denom.setFecha(new SimpleDateFormat("dd/MM/yyyy").format(txtFecha.getDate()));
							Alim_Denom.setEstablecimiento(lblFolio_Corte.getText());

							if(Alim_Denom.actualizar(tabla_guardar())){
								txtFechaCorte.setText(new SimpleDateFormat("dd/MM/yyyy").format(txtFecha.getDate()));
								
								if(!txtEfectivo.getText().equals("")){
									lblDiferenciaCorte.setText((Float.parseFloat(txtCorteSistema.getText())-(Float.parseFloat(txtEfectivo.getText())+Float.parseFloat(txtDeposito.getText())))+"");
								}else{
									txtEfectivo.setText("0.0");
									lblDiferenciaCorte.setText(Float.parseFloat(txtDeposito.getText())-(Float.parseFloat(txtDeposito.getText()))+"");
								}
								btnCancelar.setEnabled(true);
								btnSalir.setEnabled(false);
								dispose();
								JOptionPane.showMessageDialog(null, "La tabla Denominaciones se guardó exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE);
								return;
							}else{
								txtTotal.setText("0.0");
								JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar modificar la tabla","Error",JOptionPane.ERROR_MESSAGE);
								return;
							}
						}else{
							return;
						}
					}
				}
			}
		};
		
		private Object[][] tabla_guardar(){
			Object[][] matriz = new Object[tabla.getRowCount()][5];
			for(int i=0; i<tabla.getRowCount(); i++){
				
				matriz[i][0] = tabla_model.getValueAt(i,0).toString().trim();
				matriz[i][1] = tabla_model.getValueAt(i, 1).toString().trim();
				matriz[i][2] = tabla_model.getValueAt(i, 2).toString().trim();
				matriz[i][3] = tabla_model.getValueAt(i, 3).toString().trim();
				
				if(tabla_model.getValueAt(i,4).toString().trim().length() == 0){
					matriz[i][4] = Float.parseFloat("0"); 
				}else{
					matriz[i][4] = Float.parseFloat(tabla_model.getValueAt(i,4).toString().trim());
				}
			}
			return matriz;
		}
		
		private String valida_tabla(){
			String error = "";
			for(int i=0; i<tabla.getRowCount(); i++){
				try{
					if(!isNumeric(tabla_model.getValueAt(i,4).toString())){
						error += "   La celda de la columna Cantidad no es un número en el [Folio: "+tabla_model.getValueAt(i,0)+"]\t\n";
						tabla_model.setValueAt("",i, 4);
					}
				} catch(Exception e){
					JOptionPane.showMessageDialog(null, "La tabla tiene una celda con texto en lugar de un valor numérico: \n"+e,"Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			return error;
		}
		
		public void init_tabla(){
			this.tabla.getTableHeader().setReorderingAllowed(false) ;
			
	    	this.tabla.getColumnModel().getColumn(0).setMaxWidth(120);
	    	this.tabla.getColumnModel().getColumn(0).setMinWidth(120);		
	    	this.tabla.getColumnModel().getColumn(1).setMaxWidth(290);
	    	this.tabla.getColumnModel().getColumn(1).setMinWidth(290);
	    	this.tabla.getColumnModel().getColumn(2).setMaxWidth(120);
	    	this.tabla.getColumnModel().getColumn(2).setMinWidth(120);		
	    	this.tabla.getColumnModel().getColumn(3).setMaxWidth(120);
	    	this.tabla.getColumnModel().getColumn(3).setMinWidth(120);
	    	this.tabla.getColumnModel().getColumn(4).setMaxWidth(100);
	    	this.tabla.getColumnModel().getColumn(4).setMinWidth(100);
	    	
			TableCellRenderer render = new TableCellRenderer() { 
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
				boolean hasFocus, int row, int column) { 
					JLabel lbl = new JLabel(value == null? "": value.toString());
					if(row%2==0){
							lbl.setOpaque(true); 
							lbl.setBackground(new java.awt.Color(177,177,177));
					} 
					if(table.getSelectedRow() == row){
						lbl.setOpaque(true); 
						lbl.setBackground(new java.awt.Color(186,143,73));
					}
					switch(column){
						case 0 : lbl.setHorizontalAlignment(SwingConstants.CENTER); break;
						case 1 : lbl.setHorizontalAlignment(SwingConstants.LEFT); break;
						case 2 : lbl.setHorizontalAlignment(SwingConstants.CENTER); break;
						case 3 : lbl.setHorizontalAlignment(SwingConstants.CENTER); break;
						case 4 : lbl.setHorizontalAlignment(SwingConstants.CENTER); break;
					}
				return lbl; 
				} 
			}; 

			this.tabla.getColumnModel().getColumn(0).setCellRenderer(render); 
			this.tabla.getColumnModel().getColumn(1).setCellRenderer(render); 
			this.tabla.getColumnModel().getColumn(2).setCellRenderer(render); 
			this.tabla.getColumnModel().getColumn(3).setCellRenderer(render); 
			this.tabla.getColumnModel().getColumn(4).setCellRenderer(render); 
			
			float suma = 0;
			for(int i=0; i<tabla.getRowCount(); i++){
				if(tabla_model.getValueAt(i,4).toString().length() == 0){
					suma = suma + 0;
				}else{
					suma += Float.parseFloat(tabla_model.getValueAt(i,4).toString())*(Float.parseFloat(tabla_model.getValueAt(i,2).toString())*Float.parseFloat(tabla_model.getValueAt(i,3).toString()));
				}
			}
			txtTotal.setText(suma+"");
	    }
		
	    private boolean isNumeric(String cadena){
	    	try {
	    		if(cadena.equals("")){
	        		return true;
	    		}else{
	    			Float.parseFloat(cadena);
	        		return true;
	    		}
	    	} catch (NumberFormatException nfe){
	    		return false;
	    	}
	    }

	}
//	---------------------------------------------------------------------------------------------------------------------------------------------------------
	//guardar deposito
	public class Cat_Alimentacion_Deposito extends JDialog {
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		public JToolBar menu_toolbar = new JToolBar();
		JButton btn_guardar= new JButton(new ImageIcon("Iconos/save_icon&16.png"));
		
		JLabel lblEmpleado = new JLabel();
		JTextField txtTotal = new JTextField();
		
		String columnNames[] = { "Moneda", "Cantidad"};
		
		String dataValues[][] ={{ "0.10", ""},
								{ "0.20", ""},
								{ "0.50", ""},
								{ "1.00", ""},
								{ "2.00", ""},
								{ "5.00", ""},
								{ "10.00", ""},
								{ "20.00", ""},
								{ "EFECTIVO EN CAJA", ""}
							};
		
		DefaultTableModel tabla_model_depocitos = new DefaultTableModel(dataValues, columnNames) {
		     @SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
		    	java.lang.Object.class,
		    	java.lang.Object.class
	         };
		     
		     @SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
	             return types[columnIndex];
	         }
	         public boolean isCellEditable(int fila, int columna){
	        	 switch(columna){
	        	 	case 0 : return false; 
	        	 	case 1 : 
//	        	 		float suma = 0;
//		    			for(int i=0; i<tabla.getRowCount(); i++){
//		    				if(tabla_model.getValueAt(i,1).toString().length() == 0){
//		    					suma = suma + 0;
//		    				}else{
//		    					if(tabla_model.getValueAt(i,0).toString().equals("EFECTIVO EN CAJA")){
//		    						suma +=Float.parseFloat(tabla_model.getValueAt(i,1).toString());
//		    					}else{
//		    						if(isNumeric(tabla_model.getValueAt(i,1).toString().trim())){
//				    					suma += Float.parseFloat(tabla_model.getValueAt(i,0).toString())*Float.parseFloat(tabla_model.getValueAt(i,1).toString());
//									}else{
//										JOptionPane.showMessageDialog(null, "La cantidad en la Moneda "+tabla_model.getValueAt(i,0).toString()+"  están mal en su formato:\n","Error",JOptionPane.ERROR_MESSAGE);
//										tabla_model.setValueAt("", i, 1);
//									}
//		    					}
//		    				}
//		    			}
//		    			txtTotal.setText("$  "+suma);
	        	 		return true; 
	        	 } 				
	 			return false;
	 		}
		};
		
		JTable tabla_depositos = new JTable(tabla_model_depocitos);
		JScrollPane scroll_tabla_depositos = new JScrollPane(tabla_depositos);
		
		public Cat_Alimentacion_Deposito(){
			
			Constructor();
			
			this.lblEmpleado.setText(lblNombre_Completo.getText());
			
//          asigna el foco al JTextField deseado al arrancar la ventana
            this.addWindowListener(new WindowAdapter() {
                    public void windowOpened( WindowEvent e ){
                    	tabla_depositos.setEnabled(true);
            			tabla_depositos.editCellAt(fila, columna);
            			Component aComp=tabla_depositos.getEditorComponent();
            			aComp.requestFocus();
            			
                 }
            });
		
			
		}
		
		public void Constructor(){
			this.setModal(true);
			this.setTitle("Alimentación de Denominaciones");
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/captura_nomina_icon&16.png"));
			
			lblEmpleado.setForeground(Color.GRAY);
			
			this.panel.add(menu_toolbar).setBounds(0,0,250,25);
			this.panel.add(lblEmpleado).setBounds(30,35,350,20);
			
			this.panel.add(scroll_tabla_depositos).setBounds(20,60,400,420);
			
			this.panel.add(new JLabel("Total de Cantidades:")).setBounds(220,485,100,20);
			this.panel.add(txtTotal).setBounds(330,485,90,20);
			
			this.menu_toolbar.add(btn_guardar);
			this.menu_toolbar.setEnabled(true);
			this.txtTotal.setEditable(false);
			
			this.init_tabla();
			
			this.cont.add(panel);
			this.btn_guardar.addActionListener(op_guardar);
			
			this.tabla_depositos.addKeyListener(op_key);
			this.addWindowListener(op_limpiar);
			
			this.setSize(450,550);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
		
		WindowListener op_limpiar = new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
					txtDeposito.setText("");
			}
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
		};
		
		
		
		KeyListener op_key = new KeyListener() {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {
			if(CalcularImporte()==false){
				JOptionPane.showMessageDialog(null, "Se introdujo un valor no valido","Aviso",JOptionPane.INFORMATION_MESSAGE);
				tabla_depositos.setValueAt(0, fila, columna);
				return;
		}
			
		}
		public void keyPressed(KeyEvent e) {}
	};
		
		public boolean CalcularImporte(){
			boolean valor=false;
			if(Validar(fila,columna)){
				
				int cantidadDeFilas = tabla_depositos.getRowCount();
				fila+=1;

					if(fila == cantidadDeFilas){	fila=0;		}
					
					tabla_depositos.editCellAt(fila, columna);
					Component aComp=tabla_depositos.getEditorComponent();
					aComp.requestFocus();
			
					float suma = 0;
					for(int i=0; i<tabla_depositos.getRowCount(); i++){
						
						if(tabla_model_depocitos.getValueAt(i,1).toString().equals("")){
							suma = suma + 0;
						}else{
							if(tabla_model_depocitos.getValueAt(i,0).toString().equals("EFECTIVO EN CAJA")){
	    						suma += Float.parseFloat(tabla_model_depocitos.getValueAt(i,1).toString());
	    					}else{
	    						if(isNumeric(tabla_model_depocitos.getValueAt(i,1).toString().trim())){
			    					suma += Float.parseFloat(tabla_model_depocitos.getValueAt(i,0).toString())*Float.parseFloat(tabla_model_depocitos.getValueAt(i,1).toString());
								}else{
									JOptionPane.showMessageDialog(null, "La cantidad en la Moneda "+tabla_model_depocitos.getValueAt(i,0).toString()+"  están mal en su formato:\n","Error",JOptionPane.ERROR_MESSAGE);
									tabla_model_depocitos.setValueAt("", i, 1);
								}
	    					}
						}
					}
					txtTotal.setText(suma+"");
					txtDeposito.setText(suma+"");
					
					
					valor = true;
			}
			return valor;
		}
		
		@SuppressWarnings("unused")
		private boolean Validar(int fila, int columna) { 
			String valor=""; 
			
				if(tabla_depositos.getValueAt(fila,columna)==null) { 
					return false; 
				}else{ 
					
//					double numero =0;
					try{
//						numero = Double.valueOf(tabla_cobros.getValueAt(fila, columna).toString().trim());
						return true;
					}catch(NumberFormatException e){
					 return false;
					}
				} 
		}
		
		ActionListener op_guardar = new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
				if(tabla_depositos.isEditing()){
					tabla_depositos.getCellEditor().stopCellEditing();
				}
				
				if(valida_tabla() != ""){
					txtTotal.setText("0.0");
					JOptionPane.showMessageDialog(null, "Las siguientes celdas están mal en su formato:\n"+valida_tabla(),"Error",JOptionPane.ERROR_MESSAGE);
					return;
				}else{
						
						if(JOptionPane.showConfirmDialog(null, "¿Desea guardar la lista de Denominaciones?") == 0){
							Obj_Alimentacion_Denominacion Alim_Denom = new Obj_Alimentacion_Denominacion();
							
							Alim_Denom.setEmpleado(lblEmpleado.getText());
							Alim_Denom.setEstablecimiento(lblFolio_Corte.getText());

							if(Alim_Denom.guardar_deposito(tabla_guardar())){
								
								txtDeposito.setText(txtTotal.getText());
								
								btnCancelar.setEnabled(true);
								btnSalir.setEnabled(false);
								dispose();
								
								JOptionPane.showMessageDialog(null, "La tabla Depodito se guardó exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE);
								return;
							}else{
								txtTotal.setText("0.0");
								JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar modificar la tabla","Error",JOptionPane.ERROR_MESSAGE);
								return;
							}
						}else{
							return;
						}
				}
			}
		};
		
		private Object[][] tabla_guardar(){
			Object[][] matriz = new Object[tabla_depositos.getRowCount()][2];
			for(int i=0; i<tabla_depositos.getRowCount(); i++){
				
				matriz[i][0] = tabla_model_depocitos.getValueAt(i,0).toString().trim();
				
				if(tabla_model_depocitos.getValueAt(i,1).toString().trim().length() == 0){
					matriz[i][1] = Float.parseFloat("0"); 
				}else{
					matriz[i][1] = Float.parseFloat(tabla_model_depocitos.getValueAt(i,1).toString().trim());
				}
			}
			return matriz;
		}
		
		private String valida_tabla(){
			String error = "";
			for(int i=0; i<tabla_depositos.getRowCount(); i++){
				try{
					if(!isNumeric(tabla_model_depocitos.getValueAt(i,1).toString())){
						error += "   La celda de la columna Cantidad no es un número en el [Folio: "+tabla_model_depocitos.getValueAt(i,0)+"]\t\n";
						tabla_model_depocitos.setValueAt("",i, 1);
					}
				} catch(Exception e){
					JOptionPane.showMessageDialog(null, "La tabla tiene una celda con texto en lugar de un valor numérico: \n"+e,"Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			return error;
		}
		
		public void init_tabla(){
			this.tabla_depositos.getTableHeader().setReorderingAllowed(false) ;
			
	    	this.tabla_depositos.getColumnModel().getColumn(0).setMaxWidth(120);
	    	this.tabla_depositos.getColumnModel().getColumn(0).setMinWidth(120);		
	    	this.tabla_depositos.getColumnModel().getColumn(1).setMaxWidth(290);
	    	this.tabla_depositos.getColumnModel().getColumn(1).setMinWidth(290);
	    	
			TableCellRenderer render = new TableCellRenderer() { 
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
				boolean hasFocus, int row, int column) { 
					JLabel lbl = new JLabel(value == null? "": value.toString());
					if(row%2==0){
							lbl.setOpaque(true); 
							lbl.setBackground(new java.awt.Color(177,177,177));
					} 
					if(table.getSelectedRow() == row){
						lbl.setOpaque(true); 
						lbl.setBackground(new java.awt.Color(186,143,73));
					}
					switch(column){
						case 0 : lbl.setHorizontalAlignment(SwingConstants.CENTER); break;
						case 1 : lbl.setHorizontalAlignment(SwingConstants.CENTER); break;
					}
				return lbl; 
				} 
			}; 

			this.tabla_depositos.getColumnModel().getColumn(0).setCellRenderer(render); 
			this.tabla_depositos.getColumnModel().getColumn(1).setCellRenderer(render); 
			
			float suma = 0;
			for(int i=0; i<tabla_depositos.getRowCount(); i++){
				if(tabla_model_depocitos.getValueAt(i,1).toString().length() == 0){
					suma = suma + 0;
				}else{
					if(tabla_model_depocitos.getValueAt(i,0).toString().equals("EFECTIVO EN CAJA")){
						suma += Float.parseFloat(tabla_model_depocitos.getValueAt(i,1).toString());
					}else{
						if(isNumeric(tabla_model_depocitos.getValueAt(i,1).toString().trim())){
	    					suma += Float.parseFloat(tabla_model_depocitos.getValueAt(i,0).toString())*Float.parseFloat(tabla_model_depocitos.getValueAt(i,1).toString());
						}else{
							JOptionPane.showMessageDialog(null, "La cantidad en la Moneda "+tabla_model_depocitos.getValueAt(i,0).toString()+"  están mal en su formato:\n","Error",JOptionPane.ERROR_MESSAGE);
							tabla_model_depocitos.setValueAt("", i, 1);
						}
					}				}
			}
			txtTotal.setText(suma+"");
	    }
		
	    private boolean isNumeric(String cadena){
	    	try {
	    		if(cadena.equals("")){
	        		return true;
	    		}else{
	    			Float.parseFloat(cadena);
	        		return true;
	    		}
	    	} catch (NumberFormatException nfe){
	    		return false;
	    	}
	    }

	}
	
//  ---------------------------------------------------------------------------------------------------------------------------------------------------------	
	//modificar deposito
	public class Cat_Alimentacion_Deposito_Modificar extends JDialog {
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		public JToolBar menu_toolbar = new JToolBar();
		JButton btn_modificar= new JButton(new ImageIcon("Iconos/save_icon&16.png"));
		
		JLabel lblEmpleado = new JLabel();
		JTextField txtTotal = new JTextField();
		
		String columnNames[] = { "Moneda", "Cantidad"};
		
		public Object[][] get_tabla_modificar(String folio_corte){
			return new BuscarTablasModel().tabla_model_alimentacion_deposito_modificar(folio_corte);
		}
		
		DefaultTableModel tabla_model = new DefaultTableModel(this.get_tabla_modificar(lblFolio_Corte.getText()), columnNames) {
		     @SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
		    	java.lang.Object.class,
		    	java.lang.Object.class
	         };
		     
		     @SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
	             return types[columnIndex];
	         }
	         public boolean isCellEditable(int fila, int columna){
	        	 switch(columna){
	        	 	case 0 : return false; 
	        	 	case 1 : 
//	        	 		float suma = 0;
//		    			for(int i=0; i<tabla.getRowCount(); i++){
//		    				if(tabla_model.getValueAt(i,1).toString().length() == 0){
//		    					suma = suma + 0;
//		    				}else{
//		    					System.out.println(tabla_model.getValueAt(i,0).toString());
//		    					System.out.println(tabla_model.getValueAt(i,1).toString());
//		    					
//		    					if(tabla_model.getValueAt(i,0).toString().equals("EFECTIVO EN CAJA")){
//		    						suma +=Float.parseFloat(tabla_model.getValueAt(i,1).toString());
//		    					}else{
//		    						if(isNumeric(tabla_model.getValueAt(i,1).toString().trim())){
//				    					suma += Float.parseFloat(tabla_model.getValueAt(i,0).toString())*Float.parseFloat(tabla_model.getValueAt(i,1).toString());
//									}else{
//										JOptionPane.showMessageDialog(null, "La cantidad en la Moneda "+tabla_model.getValueAt(i,0).toString()+"  están mal en su formato:\n","Error",JOptionPane.ERROR_MESSAGE);
//										tabla_model.setValueAt("", i, 1);
//									}
//		    					}
//		    				}
//		    			}
//		    			txtTotal.setText("$  "+suma);
	        	 		return true; 
	        	 } 				
	 			return false;
	 		}
		};
		
		JTable tabla = new JTable(tabla_model);
		JScrollPane scroll_tabla = new JScrollPane(tabla);
		
		public Cat_Alimentacion_Deposito_Modificar(){
			Constructor();
			
			this.lblEmpleado.setText(lblNombre_Completo.getText());
			
		}
		
		public void Constructor(){
			this.setModal(true);
			this.setTitle("Alimentación de Denominaciones");
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/captura_nomina_icon&16.png"));
			
			lblEmpleado.setForeground(Color.GRAY);
			
			this.panel.add(menu_toolbar).setBounds(0,0,250,25);
			this.panel.add(lblEmpleado).setBounds(30,35,350,20);
			
			this.panel.add(scroll_tabla).setBounds(20,60,400,420);
			
			this.panel.add(new JLabel("Total de Cantidades:")).setBounds(220,485,100,20);
			this.panel.add(txtTotal).setBounds(330,485,90,20);
			
			this.menu_toolbar.add(btn_modificar);
			this.menu_toolbar.setEnabled(true);
			this.txtTotal.setEditable(false);
			
			this.init_tabla();
			
			this.cont.add(panel);
			this.btn_modificar.addActionListener(op_modificar);
			
			this.tabla.addKeyListener(op_key);
			
			this.setSize(450,550);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
		
		KeyListener op_key = new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}
			public void keyReleased(KeyEvent e) {
				float suma = 0;
				for(int i=0; i<tabla.getRowCount(); i++){
					
					if(tabla_model.getValueAt(i,1).toString().equals("")){
						suma = suma + 0;
					}else{
						if(tabla_model.getValueAt(i,0).toString().equals("EFECTIVO EN CAJA")){
    						suma += Float.parseFloat(tabla_model.getValueAt(i,1).toString());
    					}else{
    						if(isNumeric(tabla_model.getValueAt(i,1).toString().trim())){
		    					suma += Float.parseFloat(tabla_model.getValueAt(i,0).toString())*Float.parseFloat(tabla_model.getValueAt(i,1).toString());
							}else{
								JOptionPane.showMessageDialog(null, "La cantidad en la Moneda "+tabla_model.getValueAt(i,0).toString()+"  están mal en su formato:\n","Error",JOptionPane.ERROR_MESSAGE);
								tabla_model.setValueAt("", i, 1);
							}
    					}
					}
				}
				txtTotal.setText(suma+"");
				txtDeposito.setText(suma+"");
			}
			public void keyPressed(KeyEvent e) {
			}
		};
		
		ActionListener op_modificar = new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
				if(tabla.isEditing()){
					tabla.getCellEditor().stopCellEditing();
				}
				
				if(valida_tabla() != ""){
					txtTotal.setText("0.0");
					JOptionPane.showMessageDialog(null, "Las siguientes celdas están mal en su formato:\n"+valida_tabla(),"Error",JOptionPane.ERROR_MESSAGE);
					return;
				}else{
						
						if(JOptionPane.showConfirmDialog(null, "¿Desea guardar la lista de Denominaciones?") == 0){
							Obj_Alimentacion_Denominacion Alim_Denom = new Obj_Alimentacion_Denominacion();
							
							Alim_Denom.setEmpleado(lblEmpleado.getText());
							Alim_Denom.setEstablecimiento(lblFolio_Corte.getText());

							if(Alim_Denom.actualizar_deposito(tabla_guardar())){
								
								txtDeposito.setText(txtTotal.getText());
								
								btnCancelar.setEnabled(true);
								btnSalir.setEnabled(false);
								dispose();
								
								JOptionPane.showMessageDialog(null, "La tabla Depodito se guardó exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE);
								return;
							}else{
								txtTotal.setText("0.0");
								JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar modificar la tabla","Error",JOptionPane.ERROR_MESSAGE);
								return;
							}
						}else{
							return;
						}
				}
			}
		};
		
		private Object[][] tabla_guardar(){
			Object[][] matriz = new Object[tabla.getRowCount()][2];
			for(int i=0; i<tabla.getRowCount(); i++){
				
				matriz[i][0] = tabla_model.getValueAt(i,0).toString().trim();
				
				if(tabla_model.getValueAt(i,1).toString().trim().length() == 0){
					matriz[i][1] = Float.parseFloat("0"); 
				}else{
					matriz[i][1] = Float.parseFloat(tabla_model.getValueAt(i,1).toString().trim());
				}
			}
			return matriz;
		}
		
		private String valida_tabla(){
			String error = "";
			for(int i=0; i<tabla.getRowCount(); i++){
				try{
					if(!isNumeric(tabla_model.getValueAt(i,1).toString())){
						error += "   La celda de la columna Cantidad no es un número en el [Folio: "+tabla_model.getValueAt(i,0)+"]\t\n";
						tabla_model.setValueAt("",i, 1);
					}
				} catch(Exception e){
					JOptionPane.showMessageDialog(null, "La tabla tiene una celda con texto en lugar de un valor numérico: \n"+e,"Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			return error;
		}
		
		public void init_tabla(){
			this.tabla.getTableHeader().setReorderingAllowed(false) ;
			
	    	this.tabla.getColumnModel().getColumn(0).setMaxWidth(120);
	    	this.tabla.getColumnModel().getColumn(0).setMinWidth(120);		
	    	this.tabla.getColumnModel().getColumn(1).setMaxWidth(290);
	    	this.tabla.getColumnModel().getColumn(1).setMinWidth(290);
	    	
			TableCellRenderer render = new TableCellRenderer() { 
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
				boolean hasFocus, int row, int column) { 
					JLabel lbl = new JLabel(value == null? "": value.toString());
					if(row%2==0){
							lbl.setOpaque(true); 
							lbl.setBackground(new java.awt.Color(177,177,177));
					} 
					if(table.getSelectedRow() == row){
						lbl.setOpaque(true); 
						lbl.setBackground(new java.awt.Color(186,143,73));
					}
					switch(column){
						case 0 : lbl.setHorizontalAlignment(SwingConstants.CENTER); break;
						case 1 : lbl.setHorizontalAlignment(SwingConstants.CENTER); break;
					}
				return lbl; 
				} 
			}; 

			this.tabla.getColumnModel().getColumn(0).setCellRenderer(render); 
			this.tabla.getColumnModel().getColumn(1).setCellRenderer(render); 
			
			float suma = 0;
			for(int i=0; i<tabla.getRowCount(); i++){
				if(tabla_model.getValueAt(i,1).toString().length() == 0){
					suma = suma + 0;
				}else{
					System.out.println("-"+tabla_model.getValueAt(i,0).toString()+"-");
					if(tabla_model.getValueAt(i,0).toString().equals("EFECTIVO EN CAJA")){
						suma += Float.parseFloat(tabla_model.getValueAt(i,1).toString());
					}else{
						if(isNumeric(tabla_model.getValueAt(i,1).toString().trim())){
	    					suma += Float.parseFloat(tabla_model.getValueAt(i,0).toString())*Float.parseFloat(tabla_model.getValueAt(i,1).toString());
						}else{
							JOptionPane.showMessageDialog(null, "La cantidad en la Moneda "+tabla_model.getValueAt(i,0).toString()+"  están mal en su formato:\n","Error",JOptionPane.ERROR_MESSAGE);
							tabla_model.setValueAt("", i, 1);
						}
					}				
				}
			}
			txtTotal.setText(suma+"");
	    }
		
	    private boolean isNumeric(String cadena){
	    	try {
	    		if(cadena.equals("")){
	        		return true;
	    		}else{
	    			Float.parseFloat(cadena);
	        		return true;
	    		}
	    	} catch (NumberFormatException nfe){
	    		return false;
	    	}
	    }

	}
	
	
	
	
//	LLAMAR AL FILTRO DE ASIGNACIONES-----------------------------------------------------------------------------------------------------------------
	public class Cat_Filtrar_Asignaciones extends Cat_Filtro_De_Asignacion{
		
		
		public Cat_Filtrar_Asignaciones(){
			btnCargar.addActionListener(opCargar);
		}
		
		ActionListener opCargar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				limpiar_filtro();
				
				double suma_total =0;
				String[] fila = new String[8];
				
	    			for(int i=0; i<cargar_tabla_asignaciones_de_filtro().length; i++){
	    				
	    				if(cargar_tabla_asignaciones_de_filtro()[i][8].toString().trim()=="true"){
			    				
			    				 fila[0] = cargar_tabla_asignaciones_de_filtro()[i][0].toString().trim();
                                 fila[1] = cargar_tabla_asignaciones_de_filtro()[i][1].toString().trim();
                                 fila[2] = cargar_tabla_asignaciones_de_filtro()[i][2].toString().trim();
                                 fila[3] = cargar_tabla_asignaciones_de_filtro()[i][3].toString().trim();
                                 fila[4] = cargar_tabla_asignaciones_de_filtro()[i][4].toString().trim();
                                 fila[5] = cargar_tabla_asignaciones_de_filtro()[i][5].toString().trim();
                                 fila[6] = cargar_tabla_asignaciones_de_filtro()[i][6].toString().trim();
                                 fila[7] = cargar_tabla_asignaciones_de_filtro()[i][7].toString().trim();
                                 modelo_asignaciones.addRow(fila);
                                 
                                 suma_total += (Double.valueOf(cargar_tabla_asignaciones_de_filtro()[i][3].toString().trim()));
	    				}
	    			}
	    			
	    			if(txtCorteSistema.getText().toString().trim().equals("")){
	    				txtCorteSistema.setText(formato.format(suma_total)+"");
	    			}else{
	    				txtCorteSistema.setText(formato.format(Double.valueOf(txtCorteSistema.getText().toString().trim())+suma_total)+"");
	    			}
	    			
	    			if(tabla_asignaciones.getRowCount()>0){
	    				btnDeposito.setEnabled(true);
	    				btnEfectivo.setEnabled(true);
	    			}
	    		dispose();
			}
		};
		
		private Object[][] cargar_tabla_asignaciones_de_filtro(){

			Object[][] matriz = new Object[tablaFiltro.getRowCount()][9];
			
				for(int i=0; i<tablaFiltro.getRowCount(); i++){
						matriz[i][0] = modeloFiltro.getValueAt(i,0).toString().trim();
						matriz[i][1] = modeloFiltro.getValueAt(i,1).toString().trim();
						matriz[i][2] = modeloFiltro.getValueAt(i,2).toString().trim();
						matriz[i][3] = modeloFiltro.getValueAt(i,3).toString().trim();
						matriz[i][4] = modeloFiltro.getValueAt(i,4).toString().trim();
						matriz[i][5] = modeloFiltro.getValueAt(i,5).toString().trim();
						matriz[i][6] = modeloFiltro.getValueAt(i,6).toString().trim();
						matriz[i][7] = modeloFiltro.getValueAt(i,7).toString().trim();
						matriz[i][8] = modeloFiltro.getValueAt(i,8).toString().trim();
				}
			return matriz;
		}
	} 
	
	
	
	
//	LLAMAR AL FILTRO DE VAUCHERS--------------------------------------------------------------------------------------------------------------------------
	public class Cat_Filtrar_Vauchers extends Cat_Filtro_De_Vauchers{
		
		public Cat_Filtrar_Vauchers(){
			llenar_vauchers();
			
			btnCargar.addActionListener(opCargar);
		}
		
		public void llenar_vauchers(){
					
					for(int i=0; i<tabla_asignaciones.getRowCount(); i++){
		                cadenaAsignacionesSeleccionadas+= "'"+(cargar_tabla_asignaciones()[i][0].toString().trim())+"',";
					}
						cadenaAsignacionesSeleccionadas = cadenaAsignacionesSeleccionadas.substring(0,cadenaAsignacionesSeleccionadas.length()-1);
						
			            limpiar_filtro();
							
					String[] fila = new String[11];
					        while(tabla_vaucher_filtro.getRowCount()>0){
					                modelo_vaucher_filtro.removeRow(0);
					        }
			    	Object[][] getTablaFiltroVauchers = getTablaFiltro(cadenaAsignacionesSeleccionadas);
			                for(int i=0; i<getTablaFiltroVauchers.length; i++){
			                        fila[0] = getTablaFiltroVauchers[i][0]+"";
			                        fila[1] = getTablaFiltroVauchers[i][1]+"";
			                        fila[2] = getTablaFiltroVauchers[i][2]+"";
			                        fila[3] = getTablaFiltroVauchers[i][3]+"";
			                        fila[4] = getTablaFiltroVauchers[i][4]+"";
			                        fila[5] = getTablaFiltroVauchers[i][5]+"";
			                        fila[6] = getTablaFiltroVauchers[i][6]+"";
			                        fila[7] = getTablaFiltroVauchers[i][7]+"";
			                        fila[8] = getTablaFiltroVauchers[i][8]+"";
			                        fila[9] = getTablaFiltroVauchers[i][9]+"";
			                        fila[10] = getTablaFiltroVauchers[i][10]+"";
			                        modelo_vaucher_filtro.addRow(fila);
			                }
		}
		
		private Object[][] cargar_tabla_asignaciones(){

			Object[][] matriz = new Object[tabla_asignaciones.getRowCount()][1];
			
			for(int i=0; i<tabla_asignaciones.getRowCount(); i++){
					matriz[i][0] = modelo_asignaciones.getValueAt(i,0).toString().trim();
			}
		return matriz;
		}
		
		ActionListener opCargar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				limpiar_filtro();
				
				double suma_total =0;
				String[] fila = new String[10];
				
	    			for(int i=0; i<cargar_tabla_vauchers_de_filtro().length; i++){
	    				
	    				if(cargar_tabla_vauchers_de_filtro()[i][10].toString().trim().equals("true")){
	    					
			    				 fila[0] = cargar_tabla_vauchers_de_filtro()[i][0].toString().trim();
                                 fila[1] = cargar_tabla_vauchers_de_filtro()[i][1].toString().trim();
                                 fila[2] = cargar_tabla_vauchers_de_filtro()[i][2].toString().trim();
                                 fila[3] = cargar_tabla_vauchers_de_filtro()[i][3].toString().trim();
                                 fila[4] = cargar_tabla_vauchers_de_filtro()[i][4].toString().trim();
                                 fila[5] = cargar_tabla_vauchers_de_filtro()[i][5].toString().trim();
                                 fila[6] = cargar_tabla_vauchers_de_filtro()[i][6].toString().trim();
                                 fila[7] = cargar_tabla_vauchers_de_filtro()[i][7].toString().trim();
                                 fila[8] = cargar_tabla_vauchers_de_filtro()[i][8].toString().trim();
                                 fila[9] = cargar_tabla_vauchers_de_filtro()[i][9].toString().trim();
                                 modelo_vauchers.addRow(fila);
                                 
                                 suma_total += (Double.valueOf(cargar_tabla_vauchers_de_filtro()[i][9].toString().trim()));
	    				}
	    			}
	    			
	    			if(txtTotalVaucher.getText().toString().trim().equals("")){
	    				txtTotalVaucher.setText(formato.format(suma_total)+"");
	    			}else{
	    				txtTotalVaucher.setText(formato.format(Double.valueOf(txtTotalVaucher.getText().toString().trim())+suma_total)+"");
	    			}
	    		dispose();
			}
		};
		
		private Object[][] cargar_tabla_vauchers_de_filtro(){

			Object[][] matriz = new Object[tabla_vaucher_filtro.getRowCount()][11];
			
				for(int i=0; i<tabla_vaucher_filtro.getRowCount(); i++){
						matriz[i][0] = modelo_vaucher_filtro.getValueAt(i,0).toString().trim();
						matriz[i][1] = modelo_vaucher_filtro.getValueAt(i,1).toString().trim();
						matriz[i][2] = modelo_vaucher_filtro.getValueAt(i,2).toString().trim();
						matriz[i][3] = modelo_vaucher_filtro.getValueAt(i,3).toString().trim();
						matriz[i][4] = modelo_vaucher_filtro.getValueAt(i,4).toString().trim();
						matriz[i][5] = modelo_vaucher_filtro.getValueAt(i,5).toString().trim();
						matriz[i][6] = modelo_vaucher_filtro.getValueAt(i,6).toString().trim();
						matriz[i][7] = modelo_vaucher_filtro.getValueAt(i,7).toString().trim();
						matriz[i][8] = modelo_vaucher_filtro.getValueAt(i,8).toString().trim();
						matriz[i][9] = modelo_vaucher_filtro.getValueAt(i,9).toString().trim();
						matriz[i][10] = modelo_vaucher_filtro.getValueAt(i,10).toString().trim();
				}
			return matriz;
		}
	}
}
