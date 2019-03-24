package Cat_Checador;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.GuardarSQL;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Departamentos_Autorizados_Para_Checar_Sin_Cuadrante_Capturado extends JFrame{

	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JToolBar menu_toolbar       = new JToolBar();
	
	JCButton btnNuevo = new JCButton("Nuevo","Nuevo.png","Azul");
	JCButton btnEditar = new JCButton("Editar","editar-16.png","Azul");
	JCButton btnDeshacer = new JCButton("Deshacer","deshacer16.png","Azul");
	JCButton btnGuardar = new JCButton("Guardar","guardar.png","Azul");
	
	JTextField txtFolio = new Componentes().text(new JCTextField(), "Folio", 10, "Int");
	
	Object[] estab = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(estab);
	
	Object[] depto = new Obj_Departamento().Combo_Departamento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbDepartamento = new JComboBox(depto);
	
	Object[] status = {"SI","NO"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbStatus = new JComboBox(status);
	
	JTextField txtFiltro = new Componentes().text(new JCTextField(), "Ingrese Los Datos Que Desee Buscar", 200, "String");
	 public static DefaultTableModel modelo = new DefaultTableModel(null,new String[]{"Folio", "Establecimiento", "Departamento","Estatus"}){
         @SuppressWarnings("rawtypes")
         Class[] types = new Class[]{
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
                  return false;
          }
 };
	JTable tabla = new JTable(modelo);
	JScrollPane scrollAsignado = new JScrollPane(tabla);
	
	Connexion con = new Connexion();
	Obj_tabla ObjTabf =new Obj_tabla();
	int columnas = 4,checkbox=-1;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(50);
    	this.tabla.getColumnModel().getColumn(0).setMaxWidth(50);
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(150);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(150);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(50);
    	
    	
//		String comandof="exec movimientos_en_cuentas_pagos_emitidos_cancelacion";
		String comandof=" select conf.folio as folio, "
						+ "		estab.nombre as establecimiento, "
						+ "		depto.departamento as departamento, "
						+ "		case conf.puede_checar_sin_cuadrante_capturado when 'S' then 'SI' else 'NO' end as status "
						+ " from checador_permitir_checar_sin_cuadrante_capturado conf "
						+ " inner join tb_establecimiento estab on estab.folio = conf.folio_establecimiento "
						+ " inner join tb_departamento depto on depto.folio = conf.folio_departamento ";
		String basedatos="26",pintar="si";
		modelo.setRowCount(0);
		ObjTabf.Obj_Refrescar(tabla,modelo, columnas, comandof, basedatos,pintar,checkbox);
    }
	
	Border blackline;
	
	public Cat_Departamentos_Autorizados_Para_Checar_Sin_Cuadrante_Capturado() {
		
		this.cont.add(panel);
		this.setSize(800,200);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
		this.setTitle("Departamentos Autorizados Para Checar Sin Cuadrante Capturado");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Configuracion De Departamentos"));
		
		this.menu_toolbar.setEnabled(false);
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
		
		int x=15, y=20, ancho=80;
		
		panel.add(menu_toolbar).setBounds                       (x         ,y      ,400     ,20 );
		
		panel.add(new JLabel("Folio:")).setBounds(x,y+=35,ancho,20);
		panel.add(txtFolio).setBounds(x+ancho,y,ancho,20);
		
		panel.add(new JLabel("Establecimiento:")).setBounds(x,y+=25,ancho,20);
		panel.add(cmbEstablecimiento).setBounds(x+ancho,y,ancho*3,20);
		
		panel.add(new JLabel("Departamento:")).setBounds(x,y+=25,ancho,20);
		panel.add(cmbDepartamento).setBounds(x+ancho,y,ancho*3,20);
		
		panel.add(new JLabel("Puede Checar Sin Cuadrante Capturado:")).setBounds(x,y+=25,ancho*3,20);
		panel.add(cmbStatus).setBounds(x+ancho*3-30,y,ancho+30,20);
		
		panel.add(txtFiltro).setBounds(x=370,y=20,ancho*5,20);
		panel.add(scrollAsignado).setBounds(x,y+=20,ancho*5,110);
		
		init_tabla();
		agregar();
		userActions("default");
		btnNuevo.addActionListener(opButtons);
		btnEditar.addActionListener(opButtons);
		btnDeshacer.addActionListener(opButtons);
		btnGuardar.addActionListener(opButtons);
	}
	
	private void agregar(){
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mousePressed(MouseEvent e) {
	        	userActions("tableSelect");
	        }
        });
    }
	
	String movimiento = "";
	ActionListener opButtons = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			if(e.getActionCommand().toString().trim().equals("Guardar")){
				
				String CamposRequeridos = validaCampos();
				
				if(CamposRequeridos.equals("")){
					
						if(movimiento.equals("Guardar")){
								
								boolean buscarCoincidencias = new BuscarSQL().buscar_Concidencia_en_configuracion_de_cuadrantes_para_checador(cmbEstablecimiento.getSelectedItem().toString().trim(), cmbDepartamento.getSelectedItem().toString().trim());
						
								if(!buscarCoincidencias){
									
										if(new GuardarSQL().Guardar_Configuracion_Para_Checar_Sin_Cuadrnate_Capturado(Integer.valueOf(txtFolio.getText().trim()), cmbEstablecimiento.getSelectedItem().toString().trim(), cmbDepartamento.getSelectedItem().toString().trim(), cmbStatus.getSelectedItem().toString().trim(), movimiento)){
//											deshacer();
											init_tabla();
										 	JOptionPane.showMessageDialog(null, "El Registro Fue Guardado Correctamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
//							                return;
										}else{
											JOptionPane.showMessageDialog(null,  "A Ocurrido Un Error Al Guardar, Avise Al Administrador Del Sistema","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
//											return;
										}
										
								}else{
									JOptionPane.showMessageDialog(null,  "No Se Puede Guardar El Registros Con Los Parametros Especificados, El Registro Que Intenta Guardar Ya Existe Con Otro Folio.","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
								}
						
						}else{
								if(new GuardarSQL().Guardar_Configuracion_Para_Checar_Sin_Cuadrnate_Capturado(Integer.valueOf(txtFolio.getText().trim()), cmbEstablecimiento.getSelectedItem().toString().trim(), cmbDepartamento.getSelectedItem().toString().trim(), cmbStatus.getSelectedItem().toString().trim(), movimiento)){
									init_tabla();
									JOptionPane.showMessageDialog(null, "El Registro Fue Modificado Correctamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
								}else{
									JOptionPane.showMessageDialog(null,  "A Ocurrido Un Error Al Modificar, Avise Al Administrador Del Sistema","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
								}
						}
						
				}else{
					JOptionPane.showMessageDialog(null,  "Los Siguientes Campos Son Requeridos:\n"+CamposRequeridos,"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				}
			}
			
			userActions(e.getActionCommand().toString().trim());
		}
	};
	
	public String validaCampos(){
		String coleccion = "";
			coleccion += cmbEstablecimiento.getSelectedIndex() == 0 ? "Establecimiento\n" : "";
			coleccion += cmbDepartamento.getSelectedIndex() == 0 ? "Departamento\n" : "";
		return coleccion;
	}
	
	public void userActions(String event){
		switch(event){
			case "Nuevo": txtFolio.setEditable(false);
							cmbEstablecimiento.setEnabled(true);
							cmbDepartamento.setEnabled(true);
							cmbStatus.setEnabled(true);
							btnNuevo.setEnabled(false);
							btnEditar.setEnabled(false);
							btnDeshacer.setEnabled(true);
							btnGuardar.setEnabled(true); 
							
							txtFolio.setText(new BuscarSQL().buscar_folio_consecutivo_por_folio_de_transaccion(91));//ChecarSinCuadranteCapturado
							cmbEstablecimiento.setSelectedIndex(0);
							cmbDepartamento.setSelectedIndex(0);
							cmbStatus.setSelectedIndex(0);
							
							movimiento = "Guardar";
							
							break;
			
			case "tableSelect": txtFolio.setEditable(false);
								cmbEstablecimiento.setEnabled(false);
								cmbDepartamento.setEnabled(false);
								cmbStatus.setEnabled(false);
								btnNuevo.setEnabled(false);
								btnEditar.setEnabled(true);
								btnDeshacer.setEnabled(true);
								btnGuardar.setEnabled(false); 
							
								txtFolio.setText(tabla.getValueAt(tabla.getSelectedRow(), 0).toString().trim());
								cmbEstablecimiento.setSelectedItem(tabla.getValueAt(tabla.getSelectedRow(), 1));
								cmbDepartamento.setSelectedItem(tabla.getValueAt(tabla.getSelectedRow(), 2));
								cmbStatus.setSelectedItem(tabla.getValueAt(tabla.getSelectedRow(), 3));
								
								break;
			
			case "Editar": txtFolio.setEditable(false);
							cmbEstablecimiento.setEnabled(true);
							cmbDepartamento.setEnabled(true);
							cmbStatus.setEnabled(true);
							btnNuevo.setEnabled(false);
							btnEditar.setEnabled(false);
							btnDeshacer.setEnabled(true);
							btnGuardar.setEnabled(true); 
							
							movimiento = "Modificar";
							
							break;
			
			default: txtFolio.setEditable(false);
						cmbEstablecimiento.setEnabled(false);
						cmbDepartamento.setEnabled(false);
						cmbStatus.setEnabled(false);
						btnNuevo.setEnabled(true);
						btnEditar.setEnabled(false);
						btnDeshacer.setEnabled(false);
						btnGuardar.setEnabled(false); 
						
						txtFolio.setText("");
						cmbEstablecimiento.setSelectedIndex(0);
						cmbDepartamento.setSelectedIndex(0);
						cmbStatus.setSelectedIndex(0);
						
						break;
		}
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Departamentos_Autorizados_Para_Checar_Sin_Cuadrante_Capturado().setVisible(true);
		}catch(Exception e){	}
	}
}
