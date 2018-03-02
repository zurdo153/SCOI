package Cat_Evaluaciones;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.BuscarTablasModel;
import Obj_Evaluaciones.Obj_Contestacion_De_Cuestionario;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Renders.tablaRenderer;
import Obj_Xml.CrearXmlString;

@SuppressWarnings("serial")
public class Cat_Contestacion_De_Cuestionario extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFColaborador  	= new Componentes().text(new JCTextField(), "Folio", 15, "int");
	JTextField txtColaborador  		= new Componentes().text(new JCTextField(), "Nombre", 200, "String");
	JTextField txtEstablecimiento  	= new Componentes().text(new JCTextField(), "Establecimiento", 150, "String");
	JTextField txtDepartamento  	= new Componentes().text(new JCTextField(), "Departamento", 150, "String");
	JTextField txtPuesto  			= new Componentes().text(new JCTextField(), "Puesto", 150, "String");
	
	JLabel FCuest = new JLabel("");
	JLabel Cuest = new JLabel("");
	
	JCButton btnGuardar = new JCButton("Guardar", "guardar.png", "Azul");

	int fila_por_contestar = -1;
	int cantidad_de_columnas = 2;
	int columnas = 9,checkbox=8;
	int folio_colaborador = 0;
	public void init_tabla(int f_colaborador){
    	this.tabla_programacion.getColumnModel().getColumn(0).setMinWidth(60);
    	this.tabla_programacion.getColumnModel().getColumn(1).setMinWidth(60);
    	this.tabla_programacion.getColumnModel().getColumn(2).setMinWidth(300);
    	this.tabla_programacion.getColumnModel().getColumn(3).setMinWidth(135);
    	this.tabla_programacion.getColumnModel().getColumn(4).setMinWidth(130);
    	this.tabla_programacion.getColumnModel().getColumn(5).setMinWidth(60);
    	this.tabla_programacion.getColumnModel().getColumn(6).setMinWidth(300);
    	this.tabla_programacion.getColumnModel().getColumn(7).setMinWidth(30);
    	this.tabla_programacion.getColumnModel().getColumn(8).setMinWidth(30);
    	
    	modelo_programacion.setRowCount(0);
    	String comando="exec sp_cuestionarios_pendientes_por_colaborador '"+f_colaborador+"'";
		String basedatos="26",pintar="si";
		new Obj_tabla().Obj_Refrescar(tabla_programacion,modelo_programacion, columnas, comando, basedatos,pintar,checkbox);
		
		fila_por_contestar = -1;
		cantidad_de_columnas = 2;
		for(int i = 0; i<tabla_programacion.getRowCount(); i++){
			if(!Boolean.valueOf(tabla_programacion.getValueAt(i, 7).toString().trim())){
				fila_por_contestar=i;
				cantidad_de_columnas = Integer.valueOf(tabla_programacion.getValueAt(fila_por_contestar, 8).toString().trim())+2;
				FCuest.setText(tabla_programacion.getValueAt(fila_por_contestar,5).toString().trim());
				Cuest.setText(tabla_programacion.getValueAt(fila_por_contestar,6).toString().trim());
				break;
			}
		}
    }
	
	public DefaultTableModel modelo_programacion = new DefaultTableModel(null, new String[]{"Orden","F.Prog.","Programacion","Fecha_In","Fecha_Fin","F.Cuest.","Cuestionario","Listo","N"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
					java.lang.Object.class,
					java.lang.Object.class,
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
			public boolean isCellEditable(int fila, int columna){
				return false;
			}
	    };
	
	    JTable tabla_programacion = new JTable(modelo_programacion);
		public JScrollPane scroll_programacion = new JScrollPane(tabla_programacion);

		@SuppressWarnings("rawtypes")
		public Class[] listacolumnas(int Columnas){
		Class[] lista = new Class[Columnas];
		for (int i = 0; i<lista.length; i++){
			if(i>1){
				lista[i] =(Boolean.class);
			}else{
				lista[i] =(String.class);
			}
		 }
		 return lista;
	   };
		
		DefaultTableModel modelo_preguntas = null;
	    JTable tabla_preguntas = new JTable();
	    JScrollPane scrol = new JScrollPane(tabla_preguntas);
	    
			private void getPanelTabla(){	
				((DefaultTableCellRenderer)tabla_preguntas.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
				
				int a=90,b=300;
				
				tabla_preguntas.getColumnModel().getColumn(0).setHeaderValue("Folio");
				tabla_preguntas.getColumnModel().getColumn(0).setMaxWidth(a);
				tabla_preguntas.getColumnModel().getColumn(0).setMinWidth(a);
				tabla_preguntas.getColumnModel().getColumn(1).setHeaderValue("Pregunta");
				tabla_preguntas.getColumnModel().getColumn(1).setMaxWidth(b*2+a);
				tabla_preguntas.getColumnModel().getColumn(1).setMinWidth(b+a);
				
				for(int i=0; i<cantidad_de_columnas; i++){
					tabla_preguntas.getColumnModel().getColumn(i).setHeaderValue(i>1?i-1:tabla_preguntas.getColumnModel().getColumn(i).getHeaderValue());
					this.tabla_preguntas.getColumnModel().getColumn(i).setMinWidth(i==1?280:20);
					this.tabla_preguntas.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer(i<2?"texto":"CHB",i<2?"izquierda":"centro","Arial","negrita",12));
				}

				tabla_preguntas.getTableHeader().setReorderingAllowed(false) ;
		    	tabla_preguntas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			}
			
	public void cargarTablaDePreguntas(){
		init_tabla(folio_colaborador);
		modelo_preguntas = null;
		modelo_preguntas = new DefaultTableModel(0,cantidad_de_columnas){
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
		             return listacolumnas(cantidad_de_columnas)[columnIndex];
		         }   
			public boolean isCellEditable(int fila, int columna){
				return columna<2?false:true;
			}
		};
		tabla_preguntas.setModel(modelo_preguntas);
		getPanelTabla();
		buscarCuestionario();
	}
	
	public void buscarCuestionario(){
		
//		init_tabla();
//		modelo_preguntas.setColumnCount(2);
		
		if(tabla_programacion.getRowCount()>0){
			
				if(fila_por_contestar>-1){
					
					int folio_cuestionario = Integer.valueOf(tabla_programacion.getValueAt(fila_por_contestar, 5).toString().trim());
					Object[][] preg = new BuscarTablasModel().preguntas(folio_cuestionario,cantidad_de_columnas);
					
						if(preg.length>0){
							for(Object[] p:preg){
								modelo_preguntas.addRow(p);
							}
							
						}else{
							JOptionPane.showMessageDialog(null, "No Se Encontraron Peguntas Con El Folio De Cuestionario ["+folio_cuestionario+"]", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
							return;
						}
				}else{
					
					JOptionPane.showMessageDialog(null, "Todos Los Cuestionarios Ya Han Sido Contestados", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
		}else{
			JOptionPane.showMessageDialog(null, "No Hay Cuestionarios Por Contestar", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			return;
		}
	}
			
	public Cat_Contestacion_De_Cuestionario(int f_colaborador) {
		int x = 15, y=15, w=80;
		
		panel.add(new JLabel("Empleado:")).setBounds(x,y ,w,20);
		panel.add(txtFColaborador).setBounds(x+w-10,y ,w,20);
		panel.add(txtColaborador).setBounds(x+(w*2)-10,y ,w*3+40,20);
		
		panel.add(new JLabel("Estab.:")).setBounds(x+(w*6)-30,y ,w,20);
		panel.add(txtEstablecimiento).setBounds(x+(w*6)+20,y ,w*3-20,20);
		
		panel.add(new JLabel("Departamento:")).setBounds(x,y+=20 ,w*7,20);
		panel.add(txtDepartamento).setBounds(x+w+10,y ,w*4+20,20);
		
		panel.add(new JLabel("Puesto:")).setBounds(x+(w*6)-30,y ,w,20);
		panel.add(txtPuesto).setBounds(x+(w*6)+20,y ,w*3-20,20);
		
		panel.add(scroll_programacion).setBounds(x,y+=25 ,w*9,w+30);
		panel.add(new JLabel("Cuestionario:")).setBounds(x,y+=120 ,80,20);
		panel.add(FCuest).setBounds(x+=80,y ,50,20);
		panel.add(Cuest).setBounds(x+=50,y ,250,20);
		
		panel.add(scrol).setBounds(x=15,y+=20 ,w*9,w*4+50);
		
		panel.add(btnGuardar).setBounds(x+(w*7),y+=w*4+55 ,w*2,40);

		txtFColaborador.setEditable(false);
		txtColaborador.setEditable(false);
		txtEstablecimiento.setEditable(false);
		txtDepartamento.setEditable(false);
		txtPuesto.setEditable(false);
		
		folio_colaborador = f_colaborador;
		llenarDatosColaborador();
		cargarTablaDePreguntas();
		
		agregar(tabla_preguntas);
		
		btnGuardar.addActionListener(opGuardar);
		
		cont.add(panel);
		this.setSize(755,650);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void llenarDatosColaborador(){
		Obj_Contestacion_De_Cuestionario datos_colaborador = new Obj_Contestacion_De_Cuestionario().buscarColaborador(folio_colaborador);
		
		txtFColaborador.setText(datos_colaborador.getFolio_colaborador()+"");
		txtColaborador.setText(datos_colaborador.getColaborador());
		txtEstablecimiento.setText(datos_colaborador.getEstablecimiento());
		txtDepartamento.setText(datos_colaborador.getDepartamento());
		txtPuesto.setText(datos_colaborador.getPuesto());
		
	}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
	        	if(e.getClickCount()==1){
	        		
	        		int fila = tabla_preguntas.getSelectedRow();
	        		int columna = tabla_preguntas.getSelectedColumn();
	        		
	        		if(columna>1){
	        			for(int i=2; i<tabla_preguntas.getColumnCount(); i++){
		        			tabla_preguntas.setValueAt(false, fila, i);
		        		}
		        		tabla_preguntas.setValueAt(true, fila, columna);
	        		}
	        		
	        	}
	        }
        });
    }
	
	String Preguntas_sin_contestar = "";
	ActionListener opGuardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			Object[][] preguntas = arregloDePreguntas();
			
			if(!Preguntas_sin_contestar.toString().trim().equals("")){
				JOptionPane.showMessageDialog(null,"Las Siguientes Preguntas No Se Ha Contestado:\n"+Preguntas_sin_contestar, "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				Preguntas_sin_contestar = "";
				return;
			}else{
				
				if(tabla_preguntas.getRowCount()>0){
						Obj_Contestacion_De_Cuestionario contestacion = new Obj_Contestacion_De_Cuestionario();
						
						//llenar objecto -------------
						contestacion.setFolio_programacion(Integer.valueOf(tabla_programacion.getValueAt(fila_por_contestar, 1).toString().trim()));
						contestacion.setProgramacion(tabla_programacion.getValueAt(fila_por_contestar, 2).toString().trim());
						contestacion.setFolio_cuestionario(Integer.valueOf(FCuest.getText().toString().trim()));
						contestacion.setCuestionario(Cuest.getText().toString().trim());
						
						contestacion.setFolio_colaborador(Integer.valueOf(txtFColaborador.getText().toString().trim()));
						contestacion.setColaborador(txtColaborador.getText().toString().trim());
						contestacion.setEstablecimiento(txtEstablecimiento.getText().toString().trim());
						contestacion.setDepartamento(txtDepartamento.getText().toString().trim());
						contestacion.setPuesto(txtPuesto.getText().toString().trim());
						contestacion.setStatus("V");
						contestacion.setValor_de_escala(cantidad_de_columnas-2);//la cantidad de columnas agregadas determina el valor de la escala maxima para el cuestionario en proceso
						
						int[] ignorarColumnas = {-1};
						String xml=new CrearXmlString().CadenaXML2(preguntas, ignorarColumnas);
						contestacion.setCuestionario_xml(xml);
						
						if(contestacion.guardarCuestionario()){//(guardar)
							
							//recargar tabla de preguntas-------------------------------------------------------------------------------------------
							cargarTablaDePreguntas();
							//aviso (gurdado)
							JOptionPane.showMessageDialog(null,"El Cuestionario se Guardó de Forma Segura","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
							return;
						}else{
							JOptionPane.showMessageDialog(null,"Error Al Guarda La Contestacion Del Cuestionario, Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
							return;
						}
				}else{
					JOptionPane.showMessageDialog(null,"No Es Posible Guardar Debido A Que No Hay Cuestionarios Por Contestar","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
				
			}

		}
	};
	

	public Object[][] arregloDePreguntas(){
		
		Object[][] prg = new Object[tabla_preguntas.getRowCount()][3];
		
		for(int i=0; i<tabla_preguntas.getRowCount(); i++){
			
			int valor_pregunta=0;
			prg[i][0]= tabla_preguntas.getValueAt(i, 0).toString().trim();
			prg[i][1]= tabla_preguntas.getValueAt(i, 1).toString().trim();
			
			for(int j=2; j<tabla_preguntas.getColumnCount(); j++){
				if(Boolean.valueOf(tabla_preguntas.getValueAt(i, j).toString().trim())){
					valor_pregunta = j-1;
				}
			}
			prg[i][2]= valor_pregunta;
			if(valor_pregunta<=0){
				Preguntas_sin_contestar += tabla_preguntas.getValueAt(i, 1).toString().trim()+"\n";
			}
			valor_pregunta=0;
		}
		return prg;
	}
	
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Contestacion_De_Cuestionario(491).setVisible(true);
		}catch(Exception e){	}
	}

}
