package Cat_Planeacion;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.SQLException;

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
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Renders.ColorCeldas;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Plan_Semanal_Base extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolio = new JTextField();
	JTextField txtPeriodo = new JTextField();
	
//	JTextArea txaDescripcion = new Componentes().textArea(new JTextArea(), "Descripción", 700);
//	JScrollPane Descripcion = new JScrollPane(txaDescripcion);
	DefaultTableModel model_objetivos = new DefaultTableModel(null, new String[]{"Status", "Objetivos", "Folio"}
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
        	 	case 0 : return activarColumna.equals("si")?true:false; 
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
		
	JTable tabla = null;
	String activarColumna = "no";
	String[] columnas ={"Folio", "Descripcion","Exige Evidencia","Exige Observacion","Prioridad"};
			
	@SuppressWarnings("rawtypes")
	Class[] tipos = {
	    	java.lang.Object.class,
	    	java.lang.Object.class,
	    	java.lang.Object.class,
			java.lang.Object.class,
	    	java.lang.Object.class,
	    	java.lang.Object.class,
	    	java.lang.Object.class,
	    	java.lang.Object.class,
         };
	
	public boolean celdasEditables(int filaPar, int ColumnaPar){
			switch(ColumnaPar){
		 	case 0 : return false; 
		 	case 1 : return false; 
		 	case 2 : return activarColumna.equals("no")?false:true;
		 	case 3 : return activarColumna.equals("no")?false:true;
		 	case 4 : return activarColumna.equals("no")?false:true;
		 	case 5 : return false;
		 	case 6 : return false;
		 	case 7 : return false;
		 	
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
    
    int bandera_periodo_actual = 0;
    
    int diaActual = 0;
    Obj_Usuario usuario = new Obj_Usuario().LeerSession();
    
	public Cat_Plan_Semanal_Base(){
		
		tabla = tablaLunes;
		
		activarColumna = "no";
		
		if(anchoMon<=1024){
			this.setSize(1020,580);
		}else{
			this.setSize(1310,580);
		}
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/calendario almacen.png"));

		lblFoto.setBorder(LineBorder.createGrayLineBorder());
		
		int x = 15, y=30;
		
		if(anchoMon<=1024){
			this.panel.add(new JLabel("Folio:")).setBounds  (15    ,y    ,100,20);
			this.panel.add(txtFolio).setBounds              (x+25  ,y    ,140,20);
			this.panel.add(new JLabel("Periodo:")).setBounds(15    ,y+=25,100,20);
			this.panel.add(txtPeriodo).setBounds            (x+25  ,y    ,140,20);
			
			panel.add(lblFoto).setBounds(15,y+=40,100,100);
			
			this.panel.add(new JLabel("Empleado:")).setBounds(x+95,y+=3,100,20);
			this.panel.add(txtEmpleado).setBounds(185, y,220,20);
			
			this.panel.add(new JLabel("Establecimiento:")).setBounds(x+95,y+=25,100,20);
			this.panel.add(txtEstablecimiento).setBounds(205, y,200,20);
			
			this.panel.add(new JLabel("Departamento:")).setBounds(x+95,y+=25,100,20);
			this.panel.add(txtDepartamento).setBounds(205, y,200,20);
			
			this.panel.add(new JLabel("Puesto:")).setBounds(x+95,y+=25,100,20);
			this.panel.add(txtPuesto).setBounds(185, y,220,20);
			
			this.panel.add(new JLabel("Objetivos De La Semana:")).setBounds(x,y+=60,150,20);
			this.panel.add(scroll_objetivos).setBounds(x, y+=15, 390,230);
			
			this.panel.add(pestanas).setBounds(410,30,595, 450);
			ancho_nivel_critico=110;
		}else{
			
			this.panel.add(new JLabel("Folio:")).setBounds  (x    ,y    ,100,20);
			this.panel.add(txtFolio).setBounds              (x+40,y    ,140,20);
			this.panel.add(new JLabel("Periodo:")).setBounds(x    ,y+=25,100,20);
			this.panel.add(txtPeriodo).setBounds            (x+40,y    ,140,20);
			
			panel.add(lblFoto).setBounds(x,y+=40,100,100);
			
			this.panel.add(new JLabel("Empleado:")).setBounds(x+110,y+=3,100,20);
			this.panel.add(txtEmpleado).setBounds(195, y,240,20);
			
			this.panel.add(new JLabel("Establecimiento:")).setBounds(x+110,y+=25,100,20);
			this.panel.add(txtEstablecimiento).setBounds(210, y,225,20);
			
			this.panel.add(new JLabel("Departamento:")).setBounds(x+110,y+=25,100,20);
			this.panel.add(txtDepartamento).setBounds(210, y,225,20);
			
			this.panel.add(new JLabel("Puesto:")).setBounds(x+110,y+=25,100,20);
			this.panel.add(txtPuesto).setBounds(195, y,240,20);
			
			this.panel.add(new JLabel("Objetivos De La Semana:")).setBounds(x,y+=70,150,20);
			this.panel.add(scroll_objetivos).setBounds(x, y+=15, 420,220);	
			
			this.panel.add(pestanas).setBounds(450,30,840, 450);
			ancho_nivel_critico=145;
		}
			
		ImageIcon tmpIconAux = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp_usuario/usuariotmp.jpg");
	    lblFoto.setIcon(new ImageIcon(tmpIconAux.getImage().getScaledInstance(lblFoto.getWidth(),lblFoto.getHeight(), Image.SCALE_DEFAULT)));	
	    
	    String[] datosEmpleado = new BuscarSQL().buscarEmpleadoParaPlanSemanal();
	    txtEmpleado.setText(datosEmpleado[0].toString());
	    txtEstablecimiento.setText(datosEmpleado[1].toString());
	    txtDepartamento.setText(datosEmpleado[2].toString());
	    txtPuesto.setText(datosEmpleado[3].toString());
	    
	    String[] folioPlan = new BuscarSQL().buscarFolioSemanaParaPlanSemanal(0);
	    bandera_periodo_actual = Integer.valueOf(folioPlan[0].toString().trim());
	    txtFolio.setText(folioPlan[0].toString());
	    txtPeriodo.setText(folioPlan[1].toString());
	    
	    pintarPeriodo();
	    
		this.pestanas.addTab("", pLunes);
		this.pestanas.addTab("", pMarte);
		this.pestanas.addTab("", pMiercoles);
		this.pestanas.addTab("", pJueves);
		this.pestanas.addTab("", pViernes);
		this.pestanas.addTab("", pSabado);
		this.pestanas.addTab("", pDomingo);
	    
//	    seleccionar pestaña del dia actual automaticamente
		diaActual = Integer.valueOf(folioPlan[9].toString().trim());
	    pestanas.setSelectedIndex(diaActual);
	    
		AccionDePeriodoYPestanas(0);

	    txtFolio.setEditable(false);
	    txtPeriodo.setEditable(false);
	    txtEmpleado.setEditable(false);
	    txtEstablecimiento.setEditable(false);
	    txtDepartamento.setEditable(false);
	    txtPuesto.setEditable(false);
		
		renders_objetivos(tabla_objetivos,"tb_principal");
		
		this.cont.add(panel);
	}
	
	public void pintarPeriodo(){
		if(bandera_periodo_actual == Integer.valueOf(txtFolio.getText().trim())){
			txtFolio.setBackground(Color.GREEN);
			txtPeriodo.setBackground(Color.GREEN);
		}else{
			txtFolio.setBackground(Color.WHITE);
			txtPeriodo.setBackground(Color.WHITE);
		}
	}
	
	public void AccionDePeriodoYPestanas(int diasRecorridos){
		
		String[] folioPlan = new BuscarSQL().buscarFolioSemanaParaPlanSemanal(diasRecorridos);
//	    bandera_periodo_actual = Integer.valueOf(folioPlan[0].toString().trim());
	    txtFolio.setText(folioPlan[0].toString());
	    txtPeriodo.setText(folioPlan[1].toString());
	    
		pestanas.setTitleAt(0, folioPlan[2].toString().substring(0, 2)+"-Lunes");
		pestanas.setTitleAt(1, folioPlan[3].toString().substring(0, 2)+"-Martes");
		pestanas.setTitleAt(2, folioPlan[4].toString().substring(0, 2)+"-Miércoles");
		pestanas.setTitleAt(3, folioPlan[5].toString().substring(0, 2)+"-Jueves");
		pestanas.setTitleAt(4, folioPlan[6].toString().substring(0, 2)+"-Viernes");
		pestanas.setTitleAt(5, folioPlan[7].toString().substring(0, 2)+"-Sábado");
		pestanas.setTitleAt(6, folioPlan[8].toString().substring(0, 2)+"-Domingo");
		
		pestanas.setToolTipTextAt(0, folioPlan[2].toString());
		pestanas.setToolTipTextAt(1, folioPlan[3].toString());
		pestanas.setToolTipTextAt(2, folioPlan[4].toString());
		pestanas.setToolTipTextAt(3, folioPlan[5].toString());
		pestanas.setToolTipTextAt(4, folioPlan[6].toString());
		pestanas.setToolTipTextAt(5, folioPlan[7].toString());
		pestanas.setToolTipTextAt(6, folioPlan[8].toString());
		
		ImageIcon tmpIconDefault = new ImageIcon("imagen/maleta-maleta-icono-5064-32.png");
		ImageIcon tmpIconDescanso = new ImageIcon("imagen/maletin-de-vacaciones-en-la-playa-de-verano-icono-4532-32.png");
		int  dia_de_descanso=0;
        try {
			  dia_de_descanso= new BuscarSQL().dia_descanso_colaborador(usuario.getFolio());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
        
        switch(dia_de_descanso){
      	
        	case 0:
        		pestanas.setIconAt(0, new ImageIcon(tmpIconDescanso.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(1, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(2, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(3, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(4, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(5, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(6, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        	break;	
        	
        	case 1:
        		pestanas.setIconAt(0, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(1, new ImageIcon(tmpIconDescanso.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(2, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(3, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(4, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(5, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(6, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        	break;	
        	
        	case 2:
        		pestanas.setIconAt(0, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(1, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(2, new ImageIcon(tmpIconDescanso.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(3, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(4, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(5, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(6, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        	break;	
        	
        	case 3:
        		pestanas.setIconAt(0, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(1, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(2, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(3, new ImageIcon(tmpIconDescanso.getImage().getScaledInstance(16,16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(4, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(5, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(6, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        	break;	
        	
        	case 4:
        		pestanas.setIconAt(0, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(1, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(2, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(3, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(4, new ImageIcon(tmpIconDescanso.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(5, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(6, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        	break;	
        	
        	case 5:
        		pestanas.setIconAt(0, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(1, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(2, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(3, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(4, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(5, new ImageIcon(tmpIconDescanso.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(6, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        	break;	
        	
        	case 6:
        		pestanas.setIconAt(0, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(1, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(2, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(3, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(4, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(5, new ImageIcon(tmpIconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        		pestanas.setIconAt(6, new ImageIcon(tmpIconDescanso.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        	break;	
        }
		
		
	}
	
	
	public void renders_objetivos(final JTable tb, String nombre_tabla){
		
		tb.getTableHeader().setReorderingAllowed(false) ;
		tb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		if(nombre_tabla.equals("tb_principal")){
			tb.getColumnModel().getColumn(0).setMaxWidth(80);
			tb.getColumnModel().getColumn(0).setMinWidth(80);
			tb.getColumnModel().getColumn(1).setMaxWidth(620);
			tb.getColumnModel().getColumn(1).setMinWidth(620);
			tb.getColumnModel().getColumn(2).setMaxWidth(80);
			tb.getColumnModel().getColumn(2).setMinWidth(80);
			
		}else{
			tb.getColumnModel().getColumn(0).setMaxWidth(80);
			tb.getColumnModel().getColumn(0).setMinWidth(80);
			tb.getColumnModel().getColumn(1).setMaxWidth(620);
			tb.getColumnModel().getColumn(1).setMinWidth(620);
			tb.getColumnModel().getColumn(2).setMaxWidth(80);
			tb.getColumnModel().getColumn(2).setMinWidth(80);
			
			tb.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
			tb.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tb.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
		}
	}
	
	public void renders(final JTable tb,final JLayeredPane panelRender, final JScrollPane scrollRender,final String dia){
		panelRender.setBorder(BorderFactory.createTitledBorder(dia));
		panelRender.setOpaque(true); 
		
		String color = (dia.equals("Martes")||dia.equals("Jueves")||dia.equals("Sabado"))?"Azul":"Naranje";
		panelRender.setBackground(new Color(Integer.parseInt(color.equals("Azul")?"C9C9C9":"AFAFAF",16)));
		
		if(anchoMon<=1024){
			panelRender.add(scrollRender).setBounds(10,20,570,390);
		}else{
			panelRender.add(scrollRender).setBounds(15,20,805,390);
		}
		
		tb.getTableHeader().setReorderingAllowed(false) ;
		tb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		int ancho=activarColumna.equals("si")?170:90;
		int ancho2=activarColumna.equals("si")?100:170;
		
		tb.getColumnModel().getColumn(0).setMaxWidth(50);
		tb.getColumnModel().getColumn(0).setMinWidth(200);
		tb.getColumnModel().getColumn(1).setMinWidth(390);
		tb.getColumnModel().getColumn(1).setMaxWidth(900);
		tb.getColumnModel().getColumn(2).setMaxWidth(ancho);
		tb.getColumnModel().getColumn(2).setMinWidth(ancho);
		tb.getColumnModel().getColumn(3).setMaxWidth(90);
		tb.getColumnModel().getColumn(3).setMinWidth(90);
		tb.getColumnModel().getColumn(4).setMaxWidth(ancho2);
		tb.getColumnModel().getColumn(4).setMinWidth(ancho2);
		
//		tb.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
//		tb.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
//		tb.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
//		tb.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
//		tb.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
	}
	
	public void PintarEstatusTabla(final JTable tb, String tipo_de_tabla, int columnas){
		//se crea instancia a clase FormatoTable y se indica columna patron
        ColorCeldas ft = new ColorCeldas(tipo_de_tabla,columnas);
        tb.setDefaultRenderer (Object.class, ft );
	}
	
}