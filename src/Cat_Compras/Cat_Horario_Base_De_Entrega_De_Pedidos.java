package Cat_Compras;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Obj_Compras.Obj_Horario_Base_De_Entrega_De_Pedidos;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Horario_Base_De_Entrega_De_Pedidos extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolio = new Componentes().text(new JCTextField(), "Folio", 10, "Int");
	
	String[] establecimiento = new Obj_Establecimiento().Combo_Establecimiento_Estado_resultados();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimientoOrigen = new JComboBox(establecimiento);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimientoDestino = new JComboBox(establecimiento);
	
	JCheckBox chbLunes = new JCheckBox("Lunes");
	JCheckBox chbMartes = new JCheckBox("Martes");
	JCheckBox chbMiercoles = new JCheckBox("Miercoles");
	JCheckBox chbJueves = new JCheckBox("Jueves");
	JCheckBox chbViernes = new JCheckBox("Viernes");
	JCheckBox chbSabado = new JCheckBox("Sabado");
	JCheckBox chbDomingo = new JCheckBox("Domingo");
	
	String[] status = {"VIGENTE","BAJA"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbStatus = new JComboBox(status);
	
	String[] aplicacion = {"TODOS LOS DIAS","DIAS ESPECIFICOS"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbDias = new JComboBox(aplicacion);
	
	SpinnerDateModel sdmUnicaRepeticion =  new SpinnerDateModel();
    JSpinner spTiempoEstimado = new JSpinner(sdmUnicaRepeticion);   
	JSpinner.DateEditor spTiempo = new JSpinner.DateEditor(spTiempoEstimado,"HH:mm"); 
	
	JButton btnBuscar = new JButton(new ImageIcon("Iconos/zoom_icon&16.png"));
	JButton btnNuevo = new JButton("Nuevo",new ImageIcon("imagen/Nuevo.png"));
	JButton btnEditar = new JButton("Editar",new ImageIcon("imagen/editara.png"));
	JButton btnSalir = new JButton("Salir",new ImageIcon("imagen/salir16.png"));
	JButton btnGuardar = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	
	JLabel lblDias = new JLabel();
	//declaracion de Bordes
	Border blackline;
	
	 public static DefaultTableModel tabla_model = new DefaultTableModel(new Obj_Horario_Base_De_Entrega_De_Pedidos().get_tabla_model_horario_base_de_entrega(),
	            new String[]{"Folio", "Establecimiento Origen","Establecimiento Destino", "Hora","Dias","Status"}){
	                    
	            @SuppressWarnings("rawtypes")
	            Class[] types = new Class[]{
	                       java.lang.Object.class,
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
        					case 0  : return false; 
                            case 1  : return false; 
                            case 2  : return false; 
                            case 3  : return false;
                            case 4  : return false;
                            case 5  : return false; 
            				}
	                     return false;
	             }
	    };
	    
		JTable tabla = new JTable(tabla_model);
		JScrollPane panelScroll = new JScrollPane(tabla);
	
		JTextField txtFolioFiltro = new Componentes().text(new JCTextField(), "Folio", 10, "Int");
		JTextField txtEstablecimientoFiltroOrigen = new Componentes().text(new JCTextField(), "Estab. Origen", 50, "String");
		JTextField txtEstablecimientoFiltroDestino = new Componentes().text(new JCTextField(), "Estab. Destino", 50, "String");
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Horario_Base_De_Entrega_De_Pedidos(){
		
		this.init_tabla();
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Toolbox.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Configuracion De Horario Para Entrega De Pedidos"));
		
		this.setTitle("Horario Para Entrega De Pedidos");
		
		this.lblDias.setBorder(BorderFactory.createTitledBorder(blackline,"Dias"));
		
		trsfiltro = new TableRowSorter(tabla_model); 
		tabla.setRowSorter(trsfiltro);
		
		int x = 45, y=30, ancho=100;
		
		panel.add(new JLabel("Folio:")).setBounds(x-25,y,ancho,20);
		panel.add(txtFolio).setBounds(ancho,y,ancho+40,20);
		panel.add(btnBuscar).setBounds(x+(ancho*2)-5,y,32,20);
		panel.add(btnNuevo).setBounds(x+(ancho*2)+30,y,ancho,20);
		panel.add(new JLabel("Estab. Origen:")).setBounds(x-25,y+=30,80,20);
		panel.add(cmbEstablecimientoOrigen).setBounds(ancho,y,ancho+70,20);
		panel.add(btnEditar).setBounds(x+(ancho*2)+30,y,ancho,20);
		panel.add(new JLabel("Estab. Destino:")).setBounds(x-25,y+=30,80,20);
		panel.add(cmbEstablecimientoDestino).setBounds(ancho,y,ancho+70,20);
		panel.add(btnDeshacer).setBounds(x+(ancho*2)+30,y,ancho,20);
		
		panel.add(new JLabel("Status:")).setBounds(x-25,y+=30,80,20);
		panel.add(cmbStatus).setBounds(ancho,y,ancho+70,20);
		
		
		panel.add(new JLabel("Aplica:")).setBounds(x-25,y+=30,80,20);
		panel.add(cmbDias).setBounds(ancho,y,ancho+70,20);
		
		panel.add(lblDias).setBounds(x-30, y+=25, ancho*3+60, 80);	
		panel.add(chbLunes).setBounds(x,y+=10,ancho-20,20);
		panel.add(chbMartes).setBounds(x+ancho+20,y,ancho-20,20);
		panel.add(chbMiercoles).setBounds(x+ancho*2+40,y,ancho-20,20);
		panel.add(chbJueves).setBounds(x,y+=20,ancho-20,20);
		panel.add(chbViernes).setBounds(x+ancho+20,y,ancho-20,20);
		panel.add(chbSabado).setBounds(x+ancho*2+40,y,ancho-20,20);
		panel.add(chbDomingo).setBounds(x,y+=20,ancho-20,20);
		
		panel.add(new JLabel("Hora:")).setBounds(x-25,y+=30,ancho,20);
		panel.add(spTiempoEstimado).setBounds(ancho-20,y,ancho-30,20);
		
		panel.add(btnGuardar).setBounds(x+ancho+20,y,ancho,20);
		panel.add(btnSalir).setBounds(x+(ancho*2)+30,y,ancho,20);
		panel.add(txtFolioFiltro).setBounds((x*2)+(ancho*3)-10,15,75,20);
		panel.add(txtEstablecimientoFiltroOrigen).setBounds((x*2)+(ancho*3)+65,15,140,20);
		panel.add(txtEstablecimientoFiltroDestino).setBounds((x*2)+(ancho*5)+5,15,140,20);
		panel.add(panelScroll).setBounds((x*2)+(ancho*3)-10,35,ancho+300,238);

		tiempodefault("00:00:00");
		aplicaDias(true);
		
		cmbEstablecimientoOrigen.setEnabled(false);
		cmbEstablecimientoDestino.setEnabled(false);
		spTiempoEstimado.setEnabled(false);
		cmbStatus.setEnabled(false);
		cmbDias.setEnabled(false);
		btnEditar.setEnabled(false);
		btnGuardar.setEnabled(false);
		txtFolio.requestFocus();
		
		cmbDias.addActionListener(opSeleccionDeDias);
		txtFolio.addActionListener(buscar);
		
		btnGuardar.addActionListener(guardar);
		btnSalir.addActionListener(cerrar);
		btnBuscar.addActionListener(buscar);
		btnDeshacer.addActionListener(deshacer);
		btnNuevo.addActionListener(nuevo);
		btnEditar.addActionListener(editar);
		
		txtFolioFiltro.addKeyListener(opFiltro);
		txtEstablecimientoFiltroOrigen.addKeyListener(opFiltro);
		txtEstablecimientoFiltroDestino.addKeyListener(opFiltro);
		
		agregar(tabla);
		cont.add(panel);
		
		this.setSize(800,312);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
            	txtFolio.requestFocus();
           }
        });
		
	}
	
	ActionListener opSeleccionDeDias = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			aplicaDias(cmbDias.getSelectedItem().equals("TODOS LOS DIAS")?true:false);
		}
	};
	
	public void blockChbDefault(boolean seleccion){
		chbLunes.setEnabled(seleccion);
		chbMartes.setEnabled(seleccion);
		chbMiercoles.setEnabled(seleccion);
		chbJueves.setEnabled(seleccion);
		chbViernes.setEnabled(seleccion);
		chbSabado.setEnabled(seleccion);
		chbDomingo.setEnabled(seleccion);
	}
	public void aplicaDias(boolean seleccion){
		chbLunes.setSelected(seleccion);
		chbMartes.setSelected(seleccion);
		chbMiercoles.setSelected(seleccion);
		chbJueves.setSelected(seleccion);
		chbViernes.setSelected(seleccion);
		chbSabado.setSelected(seleccion);
		chbDomingo.setSelected(seleccion);
		
		chbLunes.setEnabled(!seleccion);
		chbMartes.setEnabled(!seleccion);
		chbMiercoles.setEnabled(!seleccion);
		chbJueves.setEnabled(!seleccion);
		chbViernes.setEnabled(!seleccion);
		chbSabado.setEnabled(!seleccion);
		chbDomingo.setEnabled(!seleccion);
	}
	
	@SuppressWarnings("deprecation")
	public void tiempodefault(String valor){
		String[] inicioDefault = valor.split(":");
		spTiempoEstimado.setValue(new Time(Integer.parseInt(inicioDefault[0]),Integer.parseInt(inicioDefault[1]),Integer.parseInt(inicioDefault[2])));
		spTiempoEstimado.setEditor(spTiempo);
	}
	
    public void init_tabla(){
    	this.tabla.getTableHeader().setReorderingAllowed(false) ;
    	this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    this.tabla.getColumnModel().getColumn(0).setMinWidth(40);
                    this.tabla.getColumnModel().getColumn(1).setMinWidth(140);
                    this.tabla.getColumnModel().getColumn(2).setMinWidth(140);
                    this.tabla.getColumnModel().getColumn(3).setMinWidth(150);
                    this.tabla.getColumnModel().getColumn(4).setMinWidth(100);
                    this.tabla.getColumnModel().getColumn(4).setMinWidth(100);
                    render_tabla();
      }
    
    public void render_tabla(){
			tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
    }
    
	KeyListener opFiltro = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			new Obj_Filtro_Dinamico(tabla,"Folio", txtFolioFiltro.getText(),
											"Establecimiento Origen", txtEstablecimientoFiltroOrigen.getText().toUpperCase(),
											"Establecimiento Destino", txtEstablecimientoFiltroDestino.getText().toUpperCase(), 
											"", "");
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	ActionListener cerrar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			dispose();
		}
	};
	
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
				cmbStatus.setEnabled(true);
				cmbDias.setEnabled(true);
				btnEditar.setEnabled(false);
				btnGuardar.setEnabled(true);
				
				if(cmbDias.getSelectedItem().toString().trim().equals("DIAS ESPECIFICOS")){
					blockChbDefault(true);
				}
			}
		}
	};
	
	ActionListener buscar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			buscar();
		}
	};
	
	public void buscar(){

		if(txtFolio.getText().equals("")){
			JOptionPane.showMessageDialog(null, "Ingrese el No. de Folio","Error",JOptionPane.WARNING_MESSAGE);
			return;
		}else{
			try {
				Obj_Horario_Base_De_Entrega_De_Pedidos horario = new Obj_Horario_Base_De_Entrega_De_Pedidos().buscar(Integer.parseInt(txtFolio.getText()));
				if(horario.getFolio() != 0){
					
					txtFolio.setText(horario.getFolio()+"");
					cmbEstablecimientoOrigen.setSelectedItem(horario.getEstablecimientoOrigen()+"");
					cmbEstablecimientoDestino.setSelectedItem(horario.getEstablecimientoDestino()+"");
					cmbStatus.setSelectedItem(horario.getStatus());
					
					cmbDias.setSelectedItem(horario.getLunes()+
											horario.getMartes()+
											horario.getMiercoles()+
											horario.getJueves()+
											horario.getViernes()+
											horario.getSabado()+
											horario.getDomingo()==7?"TODOS LOS DIAS":"DIAS ESPECIFICOS");

					chbLunes.setSelected(horario.getLunes()==1?true:false);
					chbMartes.setSelected(horario.getMartes()==1?true:false);
					chbMiercoles.setSelected(horario.getMiercoles()==1?true:false);
					chbJueves.setSelected(horario.getJueves()==1?true:false);
					chbViernes.setSelected(horario.getViernes()==1?true:false);
					chbSabado.setSelected(horario.getSabado()==1?true:false);
					chbDomingo.setSelected(horario.getDomingo()==1?true:false);
					
					tiempodefault(horario.getHora());
					blockChbDefault(false);
					
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

	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {

			String folio = new Obj_Horario_Base_De_Entrega_De_Pedidos().buscar_nuevo();
				
			panelLimpiar();
			panelEnabledTrue();
			txtFolio.setText(folio+"");
			txtFolio.setEditable(false);
			cmbEstablecimientoOrigen.requestFocus();
			btnGuardar.setEnabled(true);
		}
	};
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==1){
	        		
	        		int fila = tabla.getSelectedRow();
	        		String id = tabla.getValueAt(fila,0).toString().trim();
	        
						txtFolio.setText(id+"");
						buscar();
	        	}
	        }
        });
    }
	
	@SuppressWarnings("rawtypes")
	public void guardarModificar(String movimiento){
		
		Obj_Horario_Base_De_Entrega_De_Pedidos horario = new Obj_Horario_Base_De_Entrega_De_Pedidos();

		horario.setFolio(Integer.valueOf(txtFolio.getText().trim()));
		horario.setEstablecimientoOrigen(cmbEstablecimientoOrigen.getSelectedItem().toString());
		horario.setEstablecimientoDestino(cmbEstablecimientoDestino.getSelectedItem().toString());
		horario.setStatus(cmbStatus.getSelectedItem().toString());
		
		horario.setLunes(chbLunes.isSelected()?1:0);
		horario.setMartes(chbMartes.isSelected()?1:0);
		horario.setMiercoles(chbMiercoles.isSelected()?1:0);
		horario.setJueves(chbJueves.isSelected()?1:0);
		horario.setViernes(chbViernes.isSelected()?1:0);
		horario.setSabado(chbSabado.isSelected()?1:0);
		horario.setDomingo(chbDomingo.isSelected()?1:0);
		
		horario.setHora(new SimpleDateFormat ("H:mm").format(spTiempoEstimado.getValue()));
		
		String cadenaDias= (chbLunes.isSelected()?"lunes,":"")+
							(chbMartes.isSelected()?"martes,":"")+
							(chbMiercoles.isSelected()?"miercoles,":"")+
							(chbJueves.isSelected()?"jueves,":"")+
							(chbViernes.isSelected()?"viernes,":"")+
							(chbSabado.isSelected()?"sabado,":"")+
							(chbDomingo.isSelected()?"domingo,":"");
		
		System.out.println(cadenaDias);
		
		if(cadenaDias.length()>0){
			
//TODO(mandar establecimiento origen a la validacion)
			Vector dias_repetidos = horario.validar_dias_disponibles(cadenaDias, cmbEstablecimientoOrigen.getSelectedItem().toString().trim(), cmbEstablecimientoDestino.getSelectedItem().toString().trim(), Integer.valueOf(txtFolio.getText().trim()));
			String dias_rep ="";
			for(int i = 0; i<dias_repetidos.size()-1; i++){
				dias_rep = dias_rep+"-"+dias_repetidos.get(i)+"\n";
			}
			dias_rep = dias_rep.length()>0?dias_rep.substring(0,dias_rep.length()-1):"";
			
			
			if(dias_repetidos.size()-1==0){
				if(horario.guardar(movimiento)){
				
						tabla_model.setRowCount(0);
						 Object [][] lista_tabla = new Obj_Horario_Base_De_Entrega_De_Pedidos().get_tabla_model_horario_base_de_entrega();
						 for(Object[] ls: lista_tabla){
							 tabla_model.addRow(ls);
						 }
			                                
						JOptionPane.showMessageDialog(null,"El registró se "+(movimiento.equals("GUARDAR")?"guardó":"actualizó")+" de forma segura","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//Exito.png"));
						deshacer();
						return;
					
				}else{
						JOptionPane.showMessageDialog(null, "El registro no se "+(movimiento.equals("GUARDAR")?"guardó":"actualizó"), "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
						deshacer();
						return;
				}
			}else{
				JOptionPane.showMessageDialog(null, "No Se Puede Realizar La Operacion Debido \nA Que Los Siguientes Dias Ya Fueron Registrados:\n"+dias_rep, "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
				return;	
			}
			
		}else{
			JOptionPane.showMessageDialog(null, "No Se Han Seleccionado Dias","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			return;
		}
		
	}
	
	ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "El folio es requerido \n", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
			}else{			
				try {
					Obj_Horario_Base_De_Entrega_De_Pedidos horario = new Obj_Horario_Base_De_Entrega_De_Pedidos().buscar(Integer.parseInt(txtFolio.getText()));
					
					if(horario.getFolio() == Integer.parseInt(txtFolio.getText())){
						if(JOptionPane.showConfirmDialog(null, "El registro ya existe, ¿desea cambiarlo?") == 0){
							if(validaCampos()!="") {
								JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n"+validaCampos(), "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
								return;
							}else{
								guardarModificar("MODIFICAR");
							}
						}else{
							return;
						}
					}else{
						if(validaCampos()!="") {
							JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n "+validaCampos(), "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
							return;
						}else{
//TODO(validar con establecimiento origen)
								int folio_repetido_encontrado = horario.buscar_establecimiento_y_hora_con_otro_registro(cmbEstablecimientoOrigen.getSelectedItem().toString().trim(),cmbEstablecimientoDestino.getSelectedItem().toString().trim(),new SimpleDateFormat ("H:mm").format(spTiempoEstimado.getValue()));
								if(folio_repetido_encontrado >0){
									JOptionPane.showMessageDialog(null, "Ya Existe La Hora De Entrega ["+(new SimpleDateFormat ("H:mm").format(spTiempoEstimado.getValue()))+"] En El Establecimiento ["+cmbEstablecimientoDestino.getSelectedItem().toString().trim()+"] \nPara Realizar Cambios, Consulte El Folio "+folio_repetido_encontrado, "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
									deshacer();
									return;
								}else{
									guardarModificar("GUARDAR");
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
			deshacer();
		}
	};
	
	public void deshacer(){
		panelLimpiar();
			panelEnabledFalse();
			txtFolio.setEditable(true);
			txtFolio.requestFocus();
			btnNuevo.setEnabled(true);
			btnEditar.setEnabled(true);
			btnGuardar.setEnabled(false);
			btnEditar.setEnabled(false);
			
			cmbDias.setSelectedItem("TODOS LOS DIAS");
			aplicaDias(cmbDias.getSelectedItem().equals("TODOS LOS DIAS")?true:false);
			txtFolio.requestFocus();
	}
	
	public void panelLimpiar(){
		txtFolio.setText("");
		cmbEstablecimientoOrigen.setSelectedIndex(0);
		cmbEstablecimientoDestino.setSelectedIndex(0);
		tiempodefault("00:00:00");
	}
	
	public void panelEnabledFalse(){
		txtFolio.setEditable(false);
		cmbEstablecimientoOrigen.setEnabled(false);
		cmbEstablecimientoDestino.setEnabled(false);
		spTiempoEstimado.setEnabled(false);
		cmbStatus.setEnabled(false);
		cmbDias.setEnabled(false);
	}		
	
	public void panelEnabledTrue(){	
		txtFolio.setEditable(true);
		cmbEstablecimientoOrigen.setEnabled(true);
		cmbEstablecimientoDestino.setEnabled(true);
		cmbStatus.setEnabled(true);
		cmbDias.setEnabled(true);
		spTiempoEstimado.setEnabled(true);
	}
	
	private String validaCampos(){
		String error="";
		if(cmbEstablecimientoOrigen.getSelectedIndex()==0) 			error+= "Establecimiento Origen\n";
		if(cmbEstablecimientoDestino.getSelectedIndex()==0) 			error+= "Establecimiento Destino\n";
		
		int diasSeleccionados = (chbLunes.isSelected()?1:0)+
								(chbMartes.isSelected()?1:0)+
								(chbMiercoles.isSelected()?1:0)+
								(chbJueves.isSelected()?1:0)+
								(chbViernes.isSelected()?1:0)+
								(chbSabado.isSelected()?1:0)+
								(chbDomingo.isSelected()?1:0);
		if(diasSeleccionados==0)								error+="Sin Dias Seleccionados\n";
		
		if(new SimpleDateFormat ("H:mm").format(spTiempoEstimado.getValue()).equals("0:00") || new SimpleDateFormat ("H:mm").format(spTiempoEstimado.getValue()).equals("00:00"))		error+= "Hora\n";
				
		return error;
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Horario_Base_De_Entrega_De_Pedidos().setVisible(true);
		}catch(Exception e){	}
	}
}