package Cat_Lista_de_Raya;

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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Obj_Configuracion_Del_Sistema;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Principal.Componentes;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Departamento extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio", 9, "Int");
	JTextField txtDepartamento = new Componentes().text(new JTextField(), "Departamento", 50, "String");
	JTextField txtAbreviatura = new Componentes().text(new JTextField(), "Abreviatura", 30, "String");
	
	JCheckBox chbStatus = new JCheckBox("Status");
	
	JButton btnBuscar = new JButton(new ImageIcon("Iconos/zoom_icon&16.png"));
	JButton btnNuevo = new JButton("Nuevo",new ImageIcon("imagen/Nuevo.png"));
	JButton btnEditar = new JButton("Editar",new ImageIcon("imagen/editara.png"));
	JButton btnSalir = new JButton("Salir",new ImageIcon("imagen/salir16.png"));
	JButton btnGuardar = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	
	 public static DefaultTableModel tabla_model = new DefaultTableModel(new Obj_Departamento().get_tabla_model_departamento(),
	            new String[]{"Folio", "Departamento", "Abreviatura"}){
	                    
	            @SuppressWarnings("rawtypes")
	            Class[] types = new Class[]{
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
	                            case 0  : return false; 
	                            case 1  : return false; 
	                            case 2  : return false; 
	                    }
	                     return false;
	             }
	    };
	    
		JTable tabla = new JTable(tabla_model);
		JScrollPane panelScroll = new JScrollPane(tabla);
	
		JTextField txtFolioFiltro = new JTextField();
		JTextField txtDepartamentoFiltro = new JTextField();
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Departamento(){
		
		this.init_tabla();
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Toolbox.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Departamento"));
		
		this.setTitle("Departamento");
		
		trsfiltro = new TableRowSorter(tabla_model); 
		tabla.setRowSorter(trsfiltro);
		
		txtFolioFiltro.setToolTipText("Filtro Por Folio");
		txtDepartamentoFiltro.setToolTipText("Filtro Por Departamento");
		
		int x = 45, y=30, ancho=100;
		
		chbStatus.setSelected(true);
		panel.add(new JLabel("Folio:")).setBounds(x-25,y,ancho,20);
		panel.add(txtFolio).setBounds(ancho,y,ancho+40,20);
		panel.add(btnBuscar).setBounds(x+(ancho*2),y,32,20);
		panel.add(chbStatus).setBounds(x+(ancho*2)+30,y,ancho-25,20);
		panel.add(new JLabel("Departamento:")).setBounds(x-25,y+=30,80,20);
		panel.add(txtDepartamento).setBounds(ancho,y,ancho+70,20);
		panel.add(btnNuevo).setBounds(x+(ancho*2)+30,y,ancho,20);
		panel.add(new JLabel("Abreviatura:")).setBounds(x-25,y+=30,ancho,20);
		panel.add(txtAbreviatura).setBounds(ancho,y,ancho+70,20);
		panel.add(btnEditar).setBounds(x+(ancho*2)+30,y,ancho,20);
		panel.add(btnDeshacer).setBounds(x+ancho,y+=30,ancho,20);
		panel.add(btnSalir).setBounds(x-30,y,ancho,20);
		panel.add(btnGuardar).setBounds(x+ancho*2+30,y,ancho,20);
		panel.add(txtFolioFiltro).setBounds((x*2)+(ancho*3)-10,15,60,20);
		panel.add(txtDepartamentoFiltro).setBounds((x*2)+(ancho*3)+50,15,220,20);
		panel.add(panelScroll).setBounds((x*2)+(ancho*3)-10,35,ancho+300,130);

		botonNuevoDepartamento();
		
		txtDepartamento.setEditable(false);
		txtAbreviatura.setEditable(false);
		chbStatus.setEnabled(false);
		btnEditar.setEnabled(false);
		txtFolio.requestFocus();
		txtFolio.addKeyListener(buscar_action);
		txtFolio.addKeyListener(numerico_action);
		
		btnGuardar.addActionListener(guardar);
		btnSalir.addActionListener(cerrar);
		btnBuscar.addActionListener(buscar);
		btnDeshacer.addActionListener(deshacer);
		btnNuevo.addActionListener(nuevo);
		btnEditar.addActionListener(editar);
		
		txtFolioFiltro.addKeyListener(opFiltroFolio);
		txtDepartamentoFiltro.addKeyListener(opFiltroDepartamento);
		agregar(tabla);
		cont.add(panel);
		
		this.setSize(800,210);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		 this.addWindowListener(new WindowAdapter() {
	            public void windowOpened( WindowEvent e ){
	           	 txtDepartamentoFiltro.requestFocus();
	           }
	        });
		
	}
	
    public void init_tabla(){
    	this.tabla.getTableHeader().setReorderingAllowed(false) ;
    	this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    int z=100;
                    this.tabla.getColumnModel().getColumn(0).setMaxWidth(60);
                    this.tabla.getColumnModel().getColumn(0).setMinWidth(60);
                    this.tabla.getColumnModel().getColumn(1).setMaxWidth(220);
                    this.tabla.getColumnModel().getColumn(1).setMinWidth(220);
                    this.tabla.getColumnModel().getColumn(2).setMaxWidth(z);
                    this.tabla.getColumnModel().getColumn(2).setMinWidth(z);
                    render_tabla();
      }
    
    public void render_tabla(){
			tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
	}
    
    
    
	KeyListener opFiltroFolio = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolioFiltro.getText(), 0));
		}
		public void keyTyped(KeyEvent arg0) {
			char caracter = arg0.getKeyChar();
			if(((caracter < '0') ||
				(caracter > '9')) &&
			    (caracter != KeyEvent.VK_BACK_SPACE)){
				arg0.consume(); 
			}	
		}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opFiltroDepartamento = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			new Obj_Filtro_Dinamico(tabla,"Departamento", txtDepartamentoFiltro.getText().toUpperCase(),"","");
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	ActionListener cerrar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			dispose();
		}
	};
	
	public void botonNuevoDepartamento(){
		Obj_Configuracion_Del_Sistema configs2 = new Obj_Configuracion_Del_Sistema().buscar2();
		if(configs2.isGuardar_departamento()==true){
			btnNuevo.setEnabled(true);
		}else{
			btnNuevo.setEnabled(false);
		}
	}
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "No hay registro que Editar","Error",JOptionPane.WARNING_MESSAGE);
				return;
			}else{
				panelEnabledTrue();
				txtFolio.setEditable(false);
				btnEditar.setEnabled(false);
				btnNuevo.setEnabled(true);
				chbStatus.setEnabled(true);
				btnEditar.setEnabled(false);
			}
		}
	};
	
	ActionListener buscar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Ingrese el No. de Folio","Error",JOptionPane.WARNING_MESSAGE);
				return;
			}else{
				try {
					Obj_Departamento departamento = new Obj_Departamento().buscar(Integer.parseInt(txtFolio.getText()));
					if(departamento.getFolio() != 0){
						txtFolio.setText(departamento.getFolio()+"");
						txtDepartamento.setText(departamento.getDepartamento()+"");
						txtAbreviatura.setText(departamento.getAbreviatura()+"");
						if(departamento.isStatus() == true){chbStatus.setSelected(true);}
						else{chbStatus.setSelected(false);}
						btnNuevo.setEnabled(false);
						btnEditar.setEnabled(true);
						panelEnabledFalse();
						txtFolio.setEditable(true);
						txtFolio.requestFocus();
					} else{
						JOptionPane.showMessageDialog(null, "El Registro no existe","Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} 			
			}
		}
	};

	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			try {
				Obj_Departamento turno = new Obj_Departamento().buscar_nuevo();
				
				if(turno.getFolio() != 0){
					panelLimpiar();
					panelEnabledTrue();
					txtFolio.setText(turno.getFolio()+1+"");
					txtFolio.setEditable(false);
					txtDepartamento.requestFocus();
				}else{
					panelLimpiar();
					panelEnabledTrue();
					txtFolio.setText(1+"");
					txtFolio.setEditable(false);
					txtDepartamento.requestFocus();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
	};
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==1){
	        		
	        		int fila = tabla.getSelectedRow();
	        		Object id = tabla.getValueAt(fila,0).toString().substring(3,tabla.getValueAt(fila,0).toString().length());
	        
						txtFolio.setText(id+"");
						txtDepartamento.setText(tabla.getValueAt(fila,1).toString().substring(3,tabla.getValueAt(fila,1).toString().length()));
						txtAbreviatura.setText(tabla.getValueAt(fila,2).toString().substring(3,tabla.getValueAt(fila,2).toString().length()));
						btnEditar.setEnabled(true);
						chbStatus.setSelected(true);
						
						btnEditar.setEnabled(true);
	        	}
	        }
        });
    }
	
	ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "El folio es requerido \n", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
			}else{			
				try {
					Obj_Departamento departamento = new Obj_Departamento().buscar(Integer.parseInt(txtFolio.getText()));
					
					if(departamento.getFolio() == Integer.parseInt(txtFolio.getText())){
						if(JOptionPane.showConfirmDialog(null, "El registro ya existe, ¿desea cambiarlo?") == 0){
							if(validaCampos()!="") {
								JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n"+validaCampos(), "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
								return;
							}else{
								departamento.setDepartamento(txtDepartamento.getText());
								departamento.setAbreviatura(txtAbreviatura.getText());
								departamento.setStatus(chbStatus.isSelected());
								if(departamento.actualizar(Integer.parseInt(txtFolio.getText()))){
									while(tabla.getRowCount()>0){
                                        tabla_model.removeRow(0);
									}
									 Object [][] lista_tabla = new Obj_Departamento().get_tabla_model_departamento();
				                        String[] fila = new String[9];
				                                        for(int i=0; i<lista_tabla.length; i++){
				                                                fila[0] = lista_tabla[i][0]+"";
				                                                fila[1] = lista_tabla[i][1]+"";
				                                                fila[2] = lista_tabla[i][2]+"";
				                                                tabla_model.addRow(fila);
				                                        }
				                                        
									JOptionPane.showMessageDialog(null,"El registró se actualizó de forma segura","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//Exito.png"));
									panelLimpiar();
									panelEnabledFalse();
									txtFolio.setEditable(true);
									txtFolio.requestFocus();
									return;
									
								}else{
									JOptionPane.showMessageDialog(null, "El registro no se actualizó", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
									return;
								}
							}
						}else{
							return;
						}
					}else{
						if(validaCampos()!="") {
							JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n "+validaCampos(), "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
							return;
						}else{
							departamento.setFolio(Integer.parseInt(txtFolio.getText()));
							departamento.setDepartamento(txtDepartamento.getText());
							departamento.setAbreviatura(txtAbreviatura.getText());
							departamento.setStatus(chbStatus.isSelected());
							
								if(departamento.guardar()){
									while(tabla.getRowCount()>0){
                                        tabla_model.removeRow(0);
									}

									Object [][] lista_tabla = new Obj_Departamento().get_tabla_model_departamento();
			                        String[] fila = new String[9];
                                    for(int i=0; i<lista_tabla.length; i++){
                                            fila[0] = lista_tabla[i][0]+"";
                                            fila[1] = lista_tabla[i][1]+"";
                                            fila[2] = lista_tabla[i][2]+"";
                                            tabla_model.addRow(fila);
                                    }
									
									JOptionPane.showMessageDialog(null,"El registró se guardó de forma segura","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//Exito.png"));
									panelLimpiar();
									panelEnabledFalse();
									txtFolio.setEditable(true);
									txtFolio.requestFocus();
									return;
								}else{
									JOptionPane.showMessageDialog(null, "El registro no se guardó", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
									return;
								}
							}
					}
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} 				
			}			
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelLimpiar();
			panelEnabledFalse();
			txtFolio.setEditable(true);
			txtFolio.requestFocus();
			btnNuevo.setEnabled(true);
			btnEditar.setEnabled(true);
		}
	};
	
	public void panelLimpiar(){	
		txtFolio.setText("");
		txtDepartamento.setText("");
		txtAbreviatura.setText("");
	}
	
	public void panelEnabledFalse(){
		txtFolio.setEditable(false);
		txtDepartamento.setEditable(false);
		txtAbreviatura.setEditable(false);
	}		
	
	public void panelEnabledTrue(){	
		txtFolio.setEditable(true);
		txtDepartamento.setEditable(true);
		txtAbreviatura.setEditable(true);
	}
	
	private String validaCampos(){
		String error="";
		if(txtDepartamento.getText().equals("")) 		error+= "Nombre\n";
		if(txtAbreviatura.getText().equals(""))		error+= "Horario\n";
				
		return error;
	}
	
	KeyListener buscar_action = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e){
		}
		@Override
		public void keyReleased(KeyEvent e) {	
		}
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				btnBuscar.doClick();
			}
		}
	};
	
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
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Departamento().setVisible(true);
		}catch(Exception e){	}
	}
}
