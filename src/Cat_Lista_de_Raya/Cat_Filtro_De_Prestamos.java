package Cat_Lista_de_Raya;

import java.awt.Color;
import java.awt.Container;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Prestamos;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;

@SuppressWarnings({ "serial", "unchecked" })
public class Cat_Filtro_De_Prestamos extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	
	DefaultTableModel	 modelo       = new DefaultTableModel(0,8)	{
		public boolean isCellEditable(int fila, int columna){
			if(columna < 0)
				return true;
			return false;
		}
	};
	
	JTable tabla                   = new JTable(modelo);
	JScrollPane panelScroll        = new JScrollPane(tabla);
	
	JLabel txtFolio_Empleado = new JLabel();
	JLabel txtNombre_Completo = new JLabel();
	
	JLabel lblRango = new JLabel();
	JLabel lblEtiquetaRango = new JLabel("Limite de Prestamo:");
	
	String status[] = {"Vigente","Cancelado Temporal"};
	@SuppressWarnings("rawtypes")
	JComboBox cmbStatus = new JComboBox(status);
	
	String tipo_prestamo[] = {"Contable","No Contable"};
	@SuppressWarnings({ "rawtypes"})
	JComboBox cmbtipo_prestamo = new JComboBox(tipo_prestamo);
	
	JLabel lblTotal = new JLabel("");
	
	JTextField txtCantidad       = new Componentes().text(new JCTextField() , "Cantidad"                        ,16    ,"Double" );
	JTextField txtDescuento      = new Componentes().text(new JCTextField() , "Descuento"                       ,16    ,"Double" );
	JTextField txtSaldo          = new Componentes().text(new JCTextField() , "Saldo"                           ,16    ,"Double" );
	JTextField txtAbonos         = new Componentes().text(new JCTextField() , "Abono"                           ,16    ,"Double" );
	
	JDateChooser txtCalendario = new Componentes().jchooser(new JDateChooser()  ,"",0);
	
	JToolBar menu_toolbar   = new JToolBar();
	JCButton btnFiltro      = new JCButton("Buscar"    ,"Filter-List-icon16.png"              ,"Azul"); 
	JCButton btnGuardar     = new JCButton("Guardar"   ,"Guardar.png"                         ,"Azul");
	JCButton btnDeshacer    = new JCButton("Deshacer"  ,"deshacer16.png"                      ,"Azul");
	JCButton btnEditar      = new JCButton("Editar"    ,"Modify.png"                          ,"Azul");
	JCButton btnNuevo       = new JCButton("Nuevo"     ,"Nuevo.png"                           ,"Azul");
	
	String empleado="";
	Double rangoIn;
	Double rangoFin;
	float saldo = 0;
	String NuevoModificar="";
	public Cat_Filtro_De_Prestamos(String algo) {
		empleado=algo;
		this.setSize(650,315);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/prestamo.png"));
		this.setTitle("Prestamos");

		txtCantidad.requestFocus();
		this.panel.setBorder(BorderFactory.createTitledBorder("Prestamo"));
		
		this.menu_toolbar.add(btnFiltro   );
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnNuevo    );    
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.addSeparator(   );	    
		this.menu_toolbar.add(btnEditar   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnDeshacer );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnGuardar  );
		this.menu_toolbar.setFloatable(false);
		
		tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
		tabla.getColumnModel().getColumn(0).setMinWidth(60);
		tabla.getColumnModel().getColumn(0).setMaxWidth(60);
		tabla.getColumnModel().getColumn(1).setHeaderValue("Fecha");
		tabla.getColumnModel().getColumn(1).setMinWidth(80);
		tabla.getColumnModel().getColumn(1).setMaxWidth(80);
		tabla.getColumnModel().getColumn(2).setHeaderValue("Cantidad");
		tabla.getColumnModel().getColumn(2).setMinWidth(70);
		tabla.getColumnModel().getColumn(2).setMaxWidth(70);
		tabla.getColumnModel().getColumn(3).setHeaderValue("Descuento");
		tabla.getColumnModel().getColumn(3).setMinWidth(70);
		tabla.getColumnModel().getColumn(3).setMaxWidth(70);
		tabla.getColumnModel().getColumn(4).setHeaderValue("Saldo");
		tabla.getColumnModel().getColumn(4).setMinWidth(70);
		tabla.getColumnModel().getColumn(4).setMaxWidth(70);
		tabla.getColumnModel().getColumn(5).setHeaderValue("Total Abonos");
		tabla.getColumnModel().getColumn(5).setMinWidth(80);
		tabla.getColumnModel().getColumn(5).setMaxWidth(80);
		tabla.getColumnModel().getColumn(6).setHeaderValue("Estatus");
		tabla.getColumnModel().getColumn(6).setMinWidth(105);
		tabla.getColumnModel().getColumn(7).setHeaderValue("Tipo Prestamo");
		tabla.getColumnModel().getColumn(7).setMinWidth(78);
		
		agregar(tabla);
		
		int x = 10,	y=20, ancho=130,a=20;
		
		panel.add(menu_toolbar).setBounds                 (x   ,y    ,400   ,a);
		panel.add(new JLabel("Folio Empleado:")).setBounds(x,y+=25,ancho-20,a);
		panel.add(txtFolio_Empleado).setBounds(x+=80,y,ancho-40,a);
		panel.add(new JLabel("Nombre:")).setBounds(x+=40,y,ancho-50,a);
		panel.add(txtNombre_Completo).setBounds(x+=45,y,ancho*2,a);
		panel.add(lblEtiquetaRango).setBounds(x+=220,y,ancho+40,a);
		panel.add(lblRango).setBounds(x+125 ,y,ancho+40,a);
		
		panel.add(new JLabel("Fecha:")).setBounds        (x=10    ,y+=30 ,ancho    ,a);
		panel.add(txtCalendario).setBounds               (x+=60   ,y     ,ancho    ,a);
		panel.add(new JLabel("Estatus:")).setBounds      (x+=160  ,y     ,ancho    ,a);
		panel.add(cmbStatus).setBounds                   (x+=50   ,y     ,ancho    ,a);
		panel.add(new JLabel("Tipo Prestamo:")).setBounds(x+=150  ,y     ,ancho    ,a);
		panel.add(cmbtipo_prestamo).setBounds            (x+=70   ,y     ,ancho    ,a);
		
		panel.add(new JLabel("Cantidad:")).setBounds     (x=10    ,y+=30 ,ancho    ,a);
		panel.add(txtCantidad).setBounds                 (x+=60   ,y     ,ancho    ,a);
		panel.add(new JLabel("Saldo:")).setBounds        (x+=160  ,y     ,ancho    ,a);
		panel.add(txtSaldo).setBounds                    (x+=50   ,y     ,ancho    ,a);
		
		panel.add(new JLabel("Descuento:")).setBounds    (x=10    ,y+=25 ,ancho    ,a);
		panel.add(txtDescuento).setBounds                (x+=60   ,y     ,ancho    ,a);
		panel.add(new JLabel("Abonos:")).setBounds       (x+=160  ,y     ,ancho    ,a);
		panel.add(txtAbonos).setBounds                   (x+=50   ,y     ,ancho    ,a);
		
		panel.add(lblTotal).setBounds                    (x=350   ,y     ,400, 200);
		panel.add(panelScroll).setBounds                 (10      ,y+=35 ,615,100);
		
		lblTotal.setFont(new java.awt.Font("Algerian",0,60));
		lblRango.setFont(new java.awt.Font("SansSerif",8,14));
		lblEtiquetaRango.setFont(new java.awt.Font("SansSerif",8,14));	
		lblEtiquetaRango.setForeground(Color.blue);
		lblRango.setForeground(Color.blue);
		
		
		btnFiltro.addActionListener(filtro);
		btnEditar.addActionListener(Editar);
		btnGuardar.addMouseListener(guardar);
		btnDeshacer.addActionListener(deshacer);
		btnNuevo.addActionListener(Nuevo);
		cont.add(panel);
		txtFolio_Empleado.setText(empleado);

		panelEnabledTrue();

		String[][] Tabla = getMatriz(empleado);
    	tabla.getTableHeader().setReorderingAllowed(false) ;
    	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    	
		Object[] fila = new Object[tabla.getColumnCount()];
		for(int i=0; i<Tabla.length; i++){
			modelo.addRow(fila); 
			for(int j=0; j<tabla.getColumnCount(); j++){
				modelo.setValueAt(Tabla[i][j]+"", i,j);
			}
		}
		cmbtipo_prestamo.setSelectedItem(Tabla[0][7].toString().trim()  );
		cmbStatus.setSelectedItem(Tabla[0][6].toString().trim()  );
		txtNombre_Completo.setText(Tabla[0][8].toString().trim() );
		rangoIn  = Double.valueOf(Tabla[0][9].toString().trim()  );
		rangoFin = Double.valueOf(Tabla[0][10].toString().trim() );
		lblRango.setText(rangoIn+" - "+rangoFin);
		
			try {Date date = new SimpleDateFormat("dd/MM/yyyy").parse(modelo.getValueAt(0,1)+"");
				 txtCalendario.setDate(date);
			} catch (ParseException e) {e.printStackTrace();}
			
			txtCantidad.setText(modelo.getValueAt(0, 2)+"");
			txtDescuento.setText(modelo.getValueAt(0, 3)+"");
			txtSaldo.setText(modelo.getValueAt(0, 4)+"");
			txtAbonos.setText(modelo.getValueAt(0,5)+"");
			
			panelEnabledFalse();
			btnGuardar.setEnabled(false);
		
         ///buscar F2
         getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "filtro");
             getRootPane().getActionMap().put("filtro", new AbstractAction(){
                 public void actionPerformed(ActionEvent e)
                 {             	  btnFiltro.doClick();     	    }        });
             
         
		///deshacer con escape
	     getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
	     	getRootPane().getActionMap().put("escape", new AbstractAction(){
	     		public void actionPerformed(ActionEvent e)
	      		{                 	    btnDeshacer.doClick();   	    }	  });

	     	///guardar con F12
       getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar");
           getRootPane().getActionMap().put("guardar", new AbstractAction(){
               public void actionPerformed(ActionEvent e)
               {                 	    btnGuardar.doClick();                }        });
           
    ///editar con F10
           getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0), "editar");
               getRootPane().getActionMap().put("editar", new AbstractAction(){
                   public void actionPerformed(ActionEvent e)
                   {                 	    btnEditar.doClick();       	    }            });
    ///editar con Ctrl+E
           getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E,Event.CTRL_MASK), "editar");
               getRootPane().getActionMap().put("editar", new AbstractAction(){
                   public void actionPerformed(ActionEvent e)
                   {                 	    btnEditar.doClick();       	    }            });
				

		
	}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        		panelEnabledFalse();
	        		btnGuardar.setEnabled(false);
	        		int fila = tabla.getSelectedRow();
	        		
	        		try {
	    				Date date = new SimpleDateFormat("dd/MM/yyyy").parse(modelo.getValueAt(fila,1)+"");
	    				txtCalendario.setDate(date);
	    			} catch (ParseException e1) {
	    				e1.printStackTrace();
	    			}
	    			txtCantidad.setText(modelo.getValueAt(fila, 2)+"");
	    			txtDescuento.setText(modelo.getValueAt(fila, 3)+"");
	    			cmbStatus.setSelectedItem(modelo.getValueAt(fila, 6)+"");
	    			
	    			if(modelo.getValueAt(fila, 7).equals("Contable")){
	    				cmbtipo_prestamo.setSelectedIndex(0);
	    			}else{
	    				cmbtipo_prestamo.setSelectedIndex(1);
	    				 }
	        }
        });
    }
	
	MouseListener guardar = new MouseListener() {
		@Override
		public void mousePressed(MouseEvent e) {
	    	 
			if(validaCampos()!="") {
				JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n"+validaCampos(), "Mensaje", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				if((Double.parseDouble(txtCantidad.getText())-Double.parseDouble(txtAbonos.getText()))   > rangoFin){
					JOptionPane.showMessageDialog(null,"Se Le Quiere Poner Un Prestamo De  Cantidad-Total Abonos="+(Double.parseDouble(txtCantidad.getText())-Double.parseDouble(txtAbonos.getText()))+
							                   "  \n y Solo Tiene Autorizado Para Prestamo La Cantidad Maxima De $"+rangoFin+
							                   " \n Avise Al Encargado De Desarrollo Humano Si Se Le Autoriza Subir El A", "Mensaje", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
				if(Double.parseDouble(txtDescuento.getText())>Double.parseDouble(txtCantidad.getText())){
					JOptionPane.showMessageDialog(null,"El Descuento Es Mayor Que El Prestamo \n Descuento:"+Double.parseDouble(txtDescuento.getText())+" Cantidad Prestamo:"+Double.parseDouble(txtCantidad.getText()), "Mensaje", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
				if(Double.parseDouble(txtCantidad.getText())<Double.parseDouble(txtAbonos.getText())){
					JOptionPane.showMessageDialog(null,"La Cantidad No Puede Ser Menor Que La Suma De Los Abonos \n Cantidad:"+Double.parseDouble(txtCantidad.getText())+" Total Abonos:"+Double.parseDouble(txtAbonos.getText()), "Mensaje", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
				
				Obj_Prestamos pres = new Obj_Prestamos();
				pres.setFolio(Integer.parseInt(txtFolio_Empleado.getText()));
				pres.setFolio_empleado(Integer.parseInt(txtFolio_Empleado.getText()));
				pres.setNombre_Completo(txtNombre_Completo.getText());
				pres.setFecha(new SimpleDateFormat("dd/MM/yyyy").format(txtCalendario.getDate()));
				pres.setCantidad(Double.parseDouble(txtCantidad.getText()));
				pres.setDescuento(Double.parseDouble(txtDescuento.getText()));
				pres.setSaldo(Double.parseDouble(txtCantidad.getText()));
				pres.setStatus(cmbStatus.getSelectedItem().toString().trim());
				pres.setStatus_descuento(cmbStatus.getSelectedIndex()+1);
				pres.setTipo_prestamo(cmbtipo_prestamo.getSelectedIndex());
				pres.setNuevo_Modificar(NuevoModificar);
				
				switch(NuevoModificar.equals("N")?0:1){
					case 0: 
						if(Double.parseDouble(txtDescuento.getText()) > Double.parseDouble(txtCantidad.getText())){
							JOptionPane.showMessageDialog(null, "El Descuento:"+Double.parseDouble(txtDescuento.getText())+" Es Mayor Que La Cantidad:"+Double.parseDouble(txtCantidad.getText()), "Mensaje", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
							return;	
						}else{
							pres.guardar();
							JOptionPane.showMessageDialog(null,"El registro se guardo exitosamente", "Mensaje", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
							dispose();
							new Cat_Prestamos().setVisible(true);
						}
						break;
					case 1: 
						if(Double.parseDouble(txtDescuento.getText()) > Double.parseDouble(modelo.getValueAt(0,4)+"")){
							JOptionPane.showMessageDialog(null,"El Descuento Que Quiere Aplicar Es Mayor Que Con Lo Que Salda La Cuenta", "Mensaje", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
							return;
						}
						if(Double.parseDouble(txtCantidad.getText()) < Double.parseDouble(txtDescuento.getText()) || 
								Double.parseDouble(txtCantidad.getText()) == 0 ){
							JOptionPane.showMessageDialog(null,"No Es Posible Agregar Una Cantidad Menor Que El Descuento", "Mensaje", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
							return;
						}					
						if(Double.parseDouble(txtCantidad.getText()) == 0 ){
							JOptionPane.showMessageDialog(null,"La Cantidad Tiene Valor Cero", "Mensaje", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
							return;
						}
						if(JOptionPane.showConfirmDialog(null, "Desea Actualizar el registro existente ?") == JOptionPane.YES_OPTION) {
							pres.guardar();
							JOptionPane.showMessageDialog(null,"El registro se guardo actualizo Correctamente", "Mensaje", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
							dispose();
							new Cat_Prestamos().setVisible(true);
						}
						break;
				}
				panelEnabledFalse();
			}
		}
		public void mouseReleased(MouseEvent e) {}		
		public void mouseExited(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {}
	};
	
	ActionListener filtro = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			dispose();
			new Cat_Prestamos().setVisible(true);
			
		}
	};	
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			dispose();
			new Cat_Filtro_De_Prestamos(empleado).setVisible(true);
			
		panelEnabledFalse();	
		}
	};	
	
   ActionListener Editar = new ActionListener(){
			public void actionPerformed(ActionEvent e){
			panelEnabledTrue();
			NuevoModificar="M";
			txtCantidad.requestFocus();
			btnGuardar.setEnabled(true);
		}
	};
	
	ActionListener Nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e){
		if(Double.valueOf(txtSaldo.getText())>0 ) {
			JOptionPane.showMessageDialog(null,"El Colaborador Ya cuenta Con Un Prestamo Sumelo Al Existente", "Mensaje", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			NuevoModificar="M";
			return;
		}else {	
		panelEnabledTrue();
		NuevoModificar="N";
		txtCantidad.requestFocus();
		btnGuardar.setEnabled(true);
		modelo.setRowCount(0);
		}
	}
    };
	
	public void panelEnabledTrue(){	
		txtCantidad.setEnabled(true);
		txtDescuento.setEnabled(true);
		cmbStatus.setEnabled(true);
		cmbtipo_prestamo.setEnabled(true); 
		txtCalendario.setEnabled(true);
		txtAbonos.setEnabled(false);
		txtSaldo.setEnabled(false);
	}
	
	public void panelEnabledFalse(){	
		txtCantidad.setEnabled(false);
		txtDescuento.setEnabled(false);
		cmbStatus.setEnabled(false);
		cmbtipo_prestamo.setEnabled(false);
		txtCalendario.setEnabled(false);
		txtSaldo.setEnabled(false);
		txtAbonos.setEnabled(false);
	}
	
	private String validaCampos(){
		String error="";
		String fechaNull = txtCalendario.getDate()+"";
		if(txtNombre_Completo.getText().equals(""))error+= "Nombre Completo\n";
		if(txtCantidad.getText().equals(""))error+= "Cantidad\n";
		if(txtDescuento.getText().equals(""))error+= "Descuento\n";
		if(fechaNull.equals("null"))error+= "Fecha\n";
				
		return error;
	}

	public String[][] getMatriz(String folio_empleado){
		String qry = "exec prestamo_select_por_folio "+folio_empleado;
		String[][] Matriz = new String[getFilas(qry)][11];
		Statement s;
		ResultSet rs;
		try {
			s = con.conexion().createStatement();
			rs = s.executeQuery(qry);
			int i=0;
			while(rs.next()){
					DecimalFormat decimalFormat = new DecimalFormat("#0.00");
					Matriz[i][0] = rs.getInt(1)+"";
					Matriz[i][1] = rs.getString(2).trim();
					Matriz[i][2] = decimalFormat.format(Double.parseDouble(rs.getString(3)))+"";
					Matriz[i][3] = decimalFormat.format(Double.parseDouble(rs.getString(4)))+"";
					Matriz[i][4] = decimalFormat.format(Double.parseDouble(rs.getString(5)))+"";
					Matriz[i][5] = decimalFormat.format(Double.parseDouble(rs.getString(6)))+"";
					Matriz[i][6] = rs.getString(7);
					Matriz[i][7] = rs.getString(8);
					Matriz[i][8] = rs.getString(9);		
					Matriz[i][9] = rs.getString(10);		
					Matriz[i][10] = rs.getString(11);		
					i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return Matriz; 
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
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Filtro_De_Prestamos("1").setVisible(true);
		}catch(Exception e){	}
	}

	
}
