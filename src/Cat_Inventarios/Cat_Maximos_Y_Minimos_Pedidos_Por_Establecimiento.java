package Cat_Inventarios;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.GuardarSQL;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Xml.CrearXmlString;

@SuppressWarnings({ "serial", "unused" })
public class Cat_Maximos_Y_Minimos_Pedidos_Por_Establecimiento extends JDialog{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Obj_tabla ObjTab =new Obj_tabla();
	int columnasb = 14,checkbox=-1;
	public void init_tablafp(String consulta_bd,String establecimiento){
    	this.tabla.getColumnModel().getColumn( 0).setMinWidth(50);
    	this.tabla.getColumnModel().getColumn( 1).setMinWidth(407);
    	this.tabla.getColumnModel().getColumn( 2).setMinWidth(80);
    	this.tabla.getColumnModel().getColumn( 3).setMinWidth(80);
    	this.tabla.getColumnModel().getColumn( 4).setMinWidth(80);
    	this.tabla.getColumnModel().getColumn( 5).setMinWidth(80);
    	this.tabla.getColumnModel().getColumn( 6).setMinWidth(80);
    	this.tabla.getColumnModel().getColumn( 7).setMinWidth(80);
    	this.tabla.getColumnModel().getColumn( 8).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn( 9).setMinWidth(250);
    	this.tabla.getColumnModel().getColumn(10).setMinWidth(250);
    	this.tabla.getColumnModel().getColumn(11).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(12).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(13).setMinWidth(100);
    	
    	String comandof=" exec consulta_maximos_y_minimos '"+consulta_bd+"','"+lblEstabSolicita.getText().trim()+"','"+lblEstabSurte.getText().trim()+"',"+lblFolioPedido.getText().toString().trim()+",'"+lblAreaTipoDistribucion.getText().trim()+"'";
    	String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tabla,modelo, columnasb, comandof, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] base (){
		Class[] types = new Class[columnasb];
		for(int i = 0; i<columnasb; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Cod_Prod","Descripcion","Minimo","Maximo","Exist_Estab","Sugerido","Exist_Surte","Confirmacion","Estatus_Prod","Area_Tipo_Distribucion","Categoria","Localizacion","Zona","Pasillo"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = base();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){
				return (columna==7 && getValueAt(fila, 8).toString().trim().equals("VIGENTE"))?true:false;
			}
	};

	JTable tabla = new JTable(modelo);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);
	
	JLabel lblEstabSolicita = new JLabel("");
	JLabel lblEstabSurte = new JLabel("");
	JLabel lblFolioPedido = new JLabel("");
	JLabel lblAreaTipoDistribucion = new JLabel("");
	
	JTextArea txaObservacion = new Componentes().textArea(new JTextArea(), "Ingrese una observacíon", 250);
	JScrollPane scrollObservacion = new JScrollPane(txaObservacion);
	
	JTextField txtFiltro     = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<",300 , "String",tabla,columnasb );
	
	JCButton btnGuardar = new JCButton("Guardar", "guardar.png", "Azul");
	JCButton btnFinalizar = new JCButton("Finalizar", "actualizar.png", "Azul");
			
	public Cat_Maximos_Y_Minimos_Pedidos_Por_Establecimiento(String consultar_bd,String estabSolicita,String estabSurte,int folio_pedido,String area) {
		this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
		int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		int alto  = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		this.setModal(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Maximos y Minimos, Pedidos Por Establecimiento");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Pedidos Por Establecimiento"));
		
		int x=20, y=20, width=980,height=20;
		
		panel.add(new JLabel("Establecimiento Solicita:")).setBounds (x      ,y     ,120     , height);
		panel.add(lblEstabSolicita).setBounds                        (x+=130 ,y     ,200     , height);
		panel.add(new JLabel("Establecimiento Surte:")).setBounds    (x+=200 ,y     ,120     , height);
		panel.add(lblEstabSurte).setBounds                           (x+=130 ,y     ,200     , height);
		panel.add(new JLabel("Folio Pedido:")).setBounds             (x+=200 ,y     ,120     , height);
		panel.add(lblFolioPedido).setBounds                          (x+=120 ,y     ,200     , height);
		panel.add(txtFiltro).setBounds                               (x=20   ,y+=25 ,ancho-40, height);
		panel.add(scroll_tabla).setBounds                            (x      ,y+=20 ,ancho-40,450    );
		panel.add(new JLabel("Observacion:")).setBounds              (x      , y+=460, 80, 20);
		panel.add(scrollObservacion).setBounds                       (x+90   , y, 650, 45);
		panel.add(btnFinalizar).setBounds                            (x+width-210, y, 100, 40);
		panel.add(btnGuardar).setBounds                              (x+width-100, y, 100, 40);
		
		lblEstabSolicita.setText(estabSolicita);
		lblEstabSurte.setText(estabSurte);
		lblFolioPedido.setText(folio_pedido+"");
		lblAreaTipoDistribucion.setText(area);
		txaObservacion.setBackground(new Color(254,254,254));
		
		init_tablafp(consultar_bd,lblEstabSolicita.getText().toString().trim());

		this.tabla.addKeyListener(new op_validacelda_tabla());
		btnGuardar.addActionListener(opGuardar);
		btnFinalizar.addActionListener(opGuardar);
		
		btnFinalizar.setEnabled(consultar_bd.equals("BUSCAR EN BMS")?false:true);
		
		cont.add(panel);
	}
	
	ActionListener opGuardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
            if(tabla.isEditing()){tabla.getCellEditor().stopCellEditing();}
             txtFiltro.setText("");
             ObjTab.Obj_Filtro(tabla, "", columnasb, txtFiltro);
			if(modelo.getRowCount()>0){
				int[] ignorarColumnas ={1};
				String xml = new CrearXmlString().CadenaXML(tabla,ignorarColumnas);
				
				if(new GuardarSQL().Guardar_minimo_maximo_pedido_por_estab(xml, lblEstabSolicita.getText().toString().trim(), lblEstabSurte.getText().toString().trim(), Integer.valueOf(lblFolioPedido.getText().trim()) , txaObservacion.getText().trim(),e.getActionCommand().toString().trim() )){
					if(e.getActionCommand().toString().trim().toUpperCase().equals("FINALIZAR")){
						String folioPedidoBMS="";
						try {
							folioPedidoBMS = new GuardarSQL().Finalizar_minimo_maximo_pedido_por_estab(lblEstabSolicita.getText().trim(), lblEstabSurte.getText().trim(), Integer.valueOf(lblFolioPedido.getText().trim()), lblAreaTipoDistribucion.getText().trim());
						} catch (NumberFormatException e1) {
							e1.printStackTrace();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						//si esta en el rango siguiente es el folio del pedido-----------------------------------------------------------------
						if(folioPedidoBMS.length() > 0 && folioPedidoBMS.length() <= 13){
							JOptionPane.showMessageDialog(null,"El Registro Se Finalizo Correctamente Con El Folio: "+folioPedidoBMS,"Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
							dispose();
							return;
						}else{
							//trae aviso en caso de no retornar el folio de pedido de BMS------------------------------------------------------
							JOptionPane.showMessageDialog(null,folioPedidoBMS.length()==0?"No Se Pudo Realizar El Pedido":folioPedidoBMS,"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
							dispose();
							return;
						}
					}else{
						//solo guardado en la base de datos de SCOI----------------------------------------------------------------------------
						JOptionPane.showMessageDialog(null,"El Registro Se Guardó Correctamente!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
						dispose();
						return;
					}
				}else{
					JOptionPane.showMessageDialog(null, "No Se Pudo Guardar El Registro", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					dispose();
					return;
				}
			}else{
				JOptionPane.showMessageDialog(null, "La Tabla Esta Vacía", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				dispose();
				return;
			}
		}
	};
	
	int filak=0,columnak=0;
	class op_validacelda_tabla implements KeyListener{   
	    public op_validacelda_tabla (){}
	    public void actionPerformed(ActionEvent evt){}
		@Override
		public void keyPressed(KeyEvent arg0) {}
		@Override
		public void keyReleased(KeyEvent arg0) {
			filak=tabla.getSelectedRow();
			columnak=7;
            if(columnak>2){
            	if(ObjTab.validacelda(tabla,"decimal", filak, columnak)){
            		int cantidad_fila =tabla.getRowCount();
            		condicionRecorrido(cantidad_fila==filak+1 ? 0 : tabla.getSelectedRow());
            		ObjTab.RecorridoFocotabla(tabla, filak, columnak, "seguir");
  			     }
            }
		}
		@Override
		public void keyTyped(KeyEvent arg0) {}
	};
	
	public void condicionRecorrido(int partida){
		for(int i=partida; i<tabla.getRowCount()-1; i++){
			if(!tabla.getValueAt(i+(partida==0?0:1), 8).toString().trim().equals("VIGENTE")){
				filak=i+(partida==0?0:1);
			}else{
				break;
			}
		}
	}
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Maximos_Y_Minimos_Pedidos_Por_Establecimiento("BUSCAR EN SCOI","SUPER V","CEDIS",32,"De Linea Alimentos").setVisible(true);
		}catch(Exception e){	}
	}

}
