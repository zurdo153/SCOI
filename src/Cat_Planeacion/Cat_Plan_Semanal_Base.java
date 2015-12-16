package Cat_Planeacion;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.BuscarSQL;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Plan_Semanal_Base extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolio = new JTextField();
	JTextField txtPeriodo = new JTextField();
	
//	JTextArea txaDescripcion = new Componentes().textArea(new JTextArea(), "Descripción", 700);
//	JScrollPane Descripcion = new JScrollPane(txaDescripcion);
	DefaultTableModel model_objetivos = new DefaultTableModel(null, new String[]{"Objetivos", "Status", "Folio"}
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
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
        	 } 				
 			return false;
 		}
		
	};
	
	JTable tabla_objetivos = new JTable(model_objetivos);
    JScrollPane scroll_objetivos = new JScrollPane(tabla_objetivos,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	JLabel lblFoto = new 	JLabel();
	
	JTextField txtEmpleado = new JTextField();
	JTextField txtEstablecimiento = new JTextField();
	JTextField txtDepartamento = new JTextField();
	JTextField txtPuesto = new JTextField();
	
	
	JTabbedPane pestanas = new JTabbedPane();
		JLayeredPane pLunes = new JLayeredPane(); 
		JLayeredPane pMarte = new JLayeredPane();
		JLayeredPane pMiercoles = new JLayeredPane(); 
		JLayeredPane pJueves = new JLayeredPane(); 
		JLayeredPane pViernes = new JLayeredPane(); 
		JLayeredPane pSabado = new JLayeredPane(); 
		JLayeredPane pDomingo = new JLayeredPane();
	
	String[] columnas ={"Folio", "Descripcion","Exige Evidencia","Exige Observacion","Prioridad"};
	@SuppressWarnings("rawtypes")
	Class[] tipos = {
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
         };
	
	public boolean celdasEditables(int filaPar, int ColumnaPar){
			switch(ColumnaPar){
		 	case 0 : return false; 
		 	case 1 : return false; 
		 	case 2 : return false;
		 	case 3 : return false;
		 	case 4 : return false;
		 }
			return false;
	}
	
	DefaultTableModel modelLunes = new DefaultTableModel(null,columnas){
		@SuppressWarnings("rawtypes")
		Class[] types = tipos;
	     
	     @SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
             return types[columnIndex];
         }
         public boolean isCellEditable(int fila, int columna){
        	 return celdasEditables(fila, columna);
 		}
	};
	
	DefaultTableModel modelMartes = new DefaultTableModel(null,columnas){
	     @SuppressWarnings("rawtypes")
	     Class[] types = tipos;
	     @SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
             return types[columnIndex];
         }
         public boolean isCellEditable(int fila, int columna){
        	 return celdasEditables(fila, columna);
 		}
	};
	
	DefaultTableModel modelMiercoles = new DefaultTableModel(null,columnas){
	     @SuppressWarnings("rawtypes")
	     Class[] types = tipos;
	     @SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
             return types[columnIndex];
         }
         public boolean isCellEditable(int fila, int columna){
        	 return celdasEditables(fila, columna);
 		}
	};
	
	DefaultTableModel modelJueves = new DefaultTableModel(null,columnas){
	     @SuppressWarnings("rawtypes")
	     Class[] types = tipos;
	     @SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
             return types[columnIndex];
         }
         public boolean isCellEditable(int fila, int columna){
        	 return celdasEditables(fila, columna);
 		}
	};
	
	DefaultTableModel modelViernes = new DefaultTableModel(null,columnas){
	     @SuppressWarnings("rawtypes")
	     Class[] types = tipos;
	     @SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
             return types[columnIndex];
         }
         public boolean isCellEditable(int fila, int columna){
        	 return celdasEditables(fila, columna);
 		}
	};
	
	DefaultTableModel modelSabado = new DefaultTableModel(null,columnas){
	     @SuppressWarnings("rawtypes")
	     Class[] types = tipos;
	     @SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
             return types[columnIndex];
         }
         public boolean isCellEditable(int fila, int columna){
        	 return celdasEditables(fila, columna);
 		}
		
	};
	
	DefaultTableModel modelDomingo = new DefaultTableModel(null,columnas){
	     @SuppressWarnings("rawtypes")
	     Class[] types = tipos;
	     @SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
            return types[columnIndex];
        }
        public boolean isCellEditable(int fila, int columna){
        	return celdasEditables(fila, columna);
		}
	};
	
	JTable tablaLunes = new JTable(modelLunes);
	JTable tablaMartes = new JTable(modelMartes);
	JTable tablaMiercoles = new JTable(modelMiercoles);
	JTable tablaJueves = new JTable(modelJueves);
	JTable tablaViernes = new JTable(modelViernes);
	JTable tablaSabado = new JTable(modelSabado);
	JTable tablaDomingo = new JTable(modelDomingo);
	
	JScrollPane scrollLunes = new JScrollPane(tablaLunes,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	JScrollPane scrollMartes = new JScrollPane(tablaMartes,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	JScrollPane scrollMiercoles = new JScrollPane(tablaMiercoles,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	JScrollPane scrollJueves = new JScrollPane(tablaJueves,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	JScrollPane scrollViernes = new JScrollPane(tablaViernes,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	JScrollPane scrollSabado = new JScrollPane(tablaSabado,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	JScrollPane scrollDomingo = new JScrollPane(tablaDomingo,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	int anchoMon = Toolkit.getDefaultToolkit().getScreenSize().width;
    int ancho_nivel_critico=0;
	public Cat_Plan_Semanal_Base(){
		if(anchoMon<=1024){
			this.setSize(1000,600);
		}else{
			this.setSize(1200,600);
		}
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/calendario almacen.png"));
		this.setTitle("Revisión y Programación del Plan Semanal");
		this.panel.setBorder(BorderFactory.createTitledBorder("Detalle Del Plan Semanal"));
		
		lblFoto.setBorder(LineBorder.createGrayLineBorder());
		
		int x = 30, y=30;
		
		this.panel.add(new JLabel("Folio:")).setBounds  (x    ,y    ,100,20);
		this.panel.add(txtFolio).setBounds              (x+40,y    ,140,20);
		this.panel.add(new JLabel("Periodo:")).setBounds(x    ,y+=25,100,20);
		this.panel.add(txtPeriodo).setBounds            (x+40,y    ,140,20);
		
		panel.add(lblFoto).setBounds(x,y+=40,100,100);
		
		this.panel.add(new JLabel("Empleado:")).setBounds(x+110,y+=3,100,20);
		this.panel.add(txtEmpleado).setBounds(200, y,240,20);
		
		this.panel.add(new JLabel("Establecimiento:")).setBounds(x+110,y+=25,100,20);
		this.panel.add(txtEstablecimiento).setBounds(220, y,220,20);
		
		this.panel.add(new JLabel("Departamento:")).setBounds(x+110,y+=25,100,20);
		this.panel.add(txtDepartamento).setBounds(220, y,220,20);
		
		this.panel.add(new JLabel("Puesto:")).setBounds(x+110,y+=25,100,20);
		this.panel.add(txtPuesto).setBounds(200, y,240,20);
		
		this.panel.add(new JLabel("Objetivos De La Semana:")).setBounds(x,y+=40,150,20);
		this.panel.add(scroll_objetivos).setBounds(x, y+=15, 410,250);	
		
		if(anchoMon<=1024){
			this.panel.add(pestanas).setBounds(450,30,525, 450);
			ancho_nivel_critico=110;
		}else{
			this.panel.add(pestanas).setBounds(450,30,730, 450);
			ancho_nivel_critico=145;
		}
			
		ImageIcon tmpIconAux = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp_usuario/usuariotmp.jpg");
	    lblFoto.setIcon(new ImageIcon(tmpIconAux.getImage().getScaledInstance(lblFoto.getWidth(),lblFoto.getHeight(), Image.SCALE_DEFAULT)));	
	    
	    String[] folioPlan = new BuscarSQL().buscarFolioSemanaParaPlanSemanal(0);
	    txtFolio.setText(folioPlan[0].toString());
	    txtPeriodo.setText(folioPlan[1].toString());
	    
	    String[] datosEmpleado = new BuscarSQL().buscarEmpleadoParaPlanSemanal();
	    txtEmpleado.setText(datosEmpleado[0].toString());
	    txtEstablecimiento.setText(datosEmpleado[1].toString());
	    txtDepartamento.setText(datosEmpleado[2].toString());
	    txtPuesto.setText(datosEmpleado[3].toString());
	    
	    txtFolio.setEditable(false);
	    txtPeriodo.setEditable(false);
	    txtEmpleado.setEditable(false);
	    txtEstablecimiento.setEditable(false);
	    txtDepartamento.setEditable(false);
	    txtPuesto.setEditable(false);
	    
		this.pestanas.addTab("Lunes", pLunes);
		this.pestanas.addTab("Martes", pMarte);
		this.pestanas.addTab("Miércoles", pMiercoles);
		this.pestanas.addTab("Jueves", pJueves);
		this.pestanas.addTab("Viernes", pViernes);
		this.pestanas.addTab("Sábado", pSabado);
		this.pestanas.addTab("Domingo", pDomingo);

		renders_objetivos(tabla_objetivos,"tb_principal");
		
		renders(tablaLunes,pLunes,scrollLunes,"Lunes");
		renders(tablaMartes,pMarte,scrollMartes,"Martes");
		renders(tablaMiercoles,pMiercoles,scrollMiercoles,"Miercoles");		
		renders(tablaJueves,pJueves,scrollJueves,"Jueves");
		renders(tablaViernes,pViernes,scrollViernes,"Viernes");
		renders(tablaSabado,pSabado,scrollSabado,"Sabado");		
		renders(tablaDomingo,pDomingo,scrollDomingo,"Domingo");

		this.cont.add(panel);
		

	}
	
	
	public void renders_objetivos(final JTable tb, String nombre_tabla){
		
		tb.getTableHeader().setReorderingAllowed(false) ;
		tb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		if(nombre_tabla.equals("tb_principal")){
			tb.getColumnModel().getColumn(0).setMaxWidth(620);
			tb.getColumnModel().getColumn(0).setMinWidth(620);
			tb.getColumnModel().getColumn(1).setMaxWidth(50);
			tb.getColumnModel().getColumn(1).setMinWidth(50);
			tb.getColumnModel().getColumn(2).setMaxWidth(40);
			tb.getColumnModel().getColumn(2).setMinWidth(40);
			
		}else{
			tb.getColumnModel().getColumn(0).setMaxWidth(50);
			tb.getColumnModel().getColumn(0).setMinWidth(50);
			tb.getColumnModel().getColumn(1).setMaxWidth(620);
			tb.getColumnModel().getColumn(1).setMinWidth(620);
			
			tb.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
			tb.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
		}
	}
	
	public void renders(final JTable tb,final JLayeredPane panelRender, final JScrollPane scrollRender,final String dia){
		panelRender.setBorder(BorderFactory.createTitledBorder(dia));
		panelRender.setOpaque(true); 
		
		String color = (dia.equals("Martes")||dia.equals("Jueves")||dia.equals("Sabado"))?"Azul":"Naranje";
		panelRender.setBackground(new Color(Integer.parseInt(color.equals("Azul")?"C9C9C9":"AFAFAF",16)));
		
		if(anchoMon<=1024){
			panelRender.add(scrollRender).setBounds(15,20,490,390);
		}else{
			panelRender.add(scrollRender).setBounds(15,20,695,390);
		}
		
		tb.getTableHeader().setReorderingAllowed(false) ;
		tb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		tb.getColumnModel().getColumn(0).setMaxWidth(50);
		tb.getColumnModel().getColumn(0).setMinWidth(200);
		tb.getColumnModel().getColumn(1).setMinWidth(390);
		tb.getColumnModel().getColumn(1).setMaxWidth(900);
		tb.getColumnModel().getColumn(2).setMaxWidth(90);
		tb.getColumnModel().getColumn(2).setMinWidth(100);
		tb.getColumnModel().getColumn(3).setMaxWidth(100);
		tb.getColumnModel().getColumn(3).setMinWidth(100);
		tb.getColumnModel().getColumn(4).setMaxWidth(60);
		tb.getColumnModel().getColumn(4).setMinWidth(100);
		
		tb.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
		tb.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
		tb.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
		tb.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
		tb.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
	}
	
}