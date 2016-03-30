package Cat_Lista_de_Raya;

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import Obj_Lista_de_Raya.Obj_Autorizacion_Auditoria;
import Obj_Lista_de_Raya.Obj_Autorizacion_Finanzas;
import Obj_Lista_de_Raya.Obj_Deducciones_Y_Percepciones_De_Lista_De_Raya;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_Refrescar;

@SuppressWarnings("serial")

public class Cat_Traspaso_De_Deducciones_Por_Inasistencia_Sugerido_Por_Sistema extends JFrame {
	public Container cont = getContentPane();
	public JLayeredPane panel = new JLayeredPane();
	public JToolBar menu_toolbar = new JToolBar();

	public JButton btn_guardar = new JButton("Guardar",new ImageIcon("Imagen/Guardar.png"));
	public JButton btn_refrescar = new JButton(new ImageIcon("Imagen/refrescar-volver-a-cargar-las-flechas-icono-4094-16.png"));
	Runtime R = Runtime.getRuntime();
    
	public JTextField txtFiltro = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla <<<", 300, "String");
	@SuppressWarnings("rawtypes")
	public Class[] tipos(){
		Class[] tip = new Class[Cantidad_Real_De_Columnas];
		for(int i =0; i<Cantidad_Real_De_Columnas; i++){
			if(i==checkboxindex){
				tip[i]=java.lang.Boolean.class;
			}else{
				tip[i]=java.lang.Object.class;
			}
		}
		return tip;
	}
	
	int Cantidad_Real_De_Columnas=12,checkboxindex=11;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(300);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(150);
    	String comando="exec sp_buscar_sugerido_sistemas_inasistencia";
		String basedatos="26",pintar="si";
		new Obj_Refrescar(tabla,modelo, Cantidad_Real_De_Columnas, comando, basedatos,pintar,checkboxindex);
    }
	
 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio", "Nombre Completo", "Establecimiento", "Inpuntualidad", "S.Impuntualidad","S.Bono Puntualidad", "S.Omisiones", "S.Gafete", "Faltas","S.Bono Asistencia","Inasistencia", "P.Fisic."}){
	 @SuppressWarnings("rawtypes")
		Class[] types = tipos();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
         return types[columnIndex];
     }
		public boolean isCellEditable(int fila, int columna){
			if(columna >3 )
				return true; return false;
		}
    };
    JTable tabla = new JTable(modelo);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);
	
	public Cat_Traspaso_De_Deducciones_Por_Inasistencia_Sugerido_Por_Sistema(){
		int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
		alto=alto-50;
		this.setSize(ancho, alto);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/actualizacion-del-sistema-icono-5792-48.png"));
		this.setTitle("Traspaso De Deducción por Inasistencia Sugerido Por Sistema");
		this.panel.add(menu_toolbar).setBounds(25,0,150,25);
		this.panel.add(txtFiltro).setBounds(30,30,800,25);
		panel.add(scroll_tabla).setBounds(30,60,ancho-80,alto-120);
		
		init_tabla();
		agregar(tabla);
		tabla.addKeyListener(op_key);

		this.cont.add(panel);
		this.btn_guardar.addActionListener(op_guardar);
		this.btn_guardar.setToolTipText("Guardar");
		this.btn_refrescar.setVisible(false);
		
		this.menu_toolbar.add(btn_guardar);
		this.menu_toolbar.setEnabled(false);
		this.btn_guardar.setToolTipText("Guardar");
		this.txtFiltro.addKeyListener(op_filtro);
		
	}


	ActionListener op_guardar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			Obj_Autorizacion_Auditoria auditoria = new Obj_Autorizacion_Auditoria().buscar();
			Obj_Autorizacion_Finanzas finanzas = new Obj_Autorizacion_Finanzas().buscar();
			
			boolean auditoriaBoolean = auditoria.isAutorizar();
			boolean finanzasBoolean = finanzas.isAutorizar();
			
				if((auditoriaBoolean == true)  || (finanzasBoolean == true)){
						JOptionPane.showMessageDialog(null, "La Lista De Raya Fue Autorizada No Puede Ser Modificada Ninguna Deduccion o Percepcion de Lista de Raya....."
						       +" \n Hasta Que Se Genere Por D.H o Se Desautorize por Finanzas o Auditoria <<>>","Aviso",JOptionPane.WARNING_MESSAGE);
				}else{
					new Obj_Filtro_Dinamico(tabla, "Folio","","Nombre Completo", "","Establecimiento","", "","");
			
						if(tabla.isEditing()){
							tabla.getCellEditor().stopCellEditing();
						}
							if(JOptionPane.showConfirmDialog(null, "¿Desea guardar la lista de traspaso por deducciones sugerido?") == 0){
								
								Obj_Deducciones_Y_Percepciones_De_Lista_De_Raya inasistencia = new Obj_Deducciones_Y_Percepciones_De_Lista_De_Raya();
								
									if(inasistencia.guardar_traspaso_de_deduccion_sugerido(tabla_guardar())){
										JOptionPane.showMessageDialog(null, "El Traspaso de Deducciones de Asistencia Sugerido Por Sistema \n Se Guardó Exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
										return;
									}else{
										JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar guardar la tabla","Error",JOptionPane.ERROR_MESSAGE);
										return;
									}
							}else{
								return;
							}
				}
		}
	};
	
	private Object[][] tabla_guardar(){
		Object[][] matriz = new Object[tabla.getRowCount()][tabla.getColumnCount()];
		for(int i=0; i<tabla.getRowCount(); i++){
			for(int j=0; j<tabla.getColumnCount(); j++){
								matriz[i][j] = (modelo.getValueAt(i,j).toString().trim().equals(""))?0:modelo.getValueAt(i,j).toString().trim();
			}
		}
		return matriz;
	}

	KeyListener op_filtro = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			int[] columnas ={0,1,2,3};
			new Obj_Filtro_Dinamico_Plus(tabla, txtFiltro.getText().toUpperCase(), columnas);
		}
		public void keyTyped(KeyEvent arg0)   {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	int columna=0,fila=0;
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	columna = tbl.getSelectedColumn();
        		fila    = tbl.getSelectedRow();
	        	if(e.getClickCount() == 1 && columna>3 && columna<11){
	        		RecorridoFoco(tbl.getSelectedRow()-1);
	        	}
	        }
        });
    }
	
	  KeyListener op_key = new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				ValidaValor();	
			}
			public void keyPressed(KeyEvent e) {}
		};
		
	  public boolean ValidaValor(){
				boolean valor=false;
							if(isNumeric(modelo.getValueAt(fila,columna).toString().trim())){
										RecorridoFoco(fila);
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
		public void RecorridoFoco(int filap){
			fila=filap;
			int cantidadDeFilas = tabla.getRowCount();
			String sacarFocoDeTabla = "no";
			if(fila == cantidadDeFilas-1){
						sacarFocoDeTabla="si";
			}else{
				sacarFocoDeTabla = "no";
				fila=fila+1;
			}
			tabla.getSelectionModel().setSelectionInterval(fila, fila);
			tabla.editCellAt(fila, columna);
  		  Component accion=tabla.getEditorComponent();
			final JTextComponent jtc = (JTextComponent)accion;
			  jtc.requestFocus();
			  jtc.selectAll();	
			
			if(sacarFocoDeTabla.equals("si")){
				tabla.lostFocus(null, null);
				txtFiltro.requestFocus();
				tabla.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
				tabla.getSelectionModel().clearSelection();
			}
		};
		
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new  Cat_Traspaso_De_Deducciones_Por_Inasistencia_Sugerido_Por_Sistema().setVisible(true);
		}catch(Exception e){	}
	}
}