package Cat_Servicios;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Cat_Principal.EmailSenderService;
import Conexiones_SQL.Connexion;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
	public class Cat_Envio_De_Correos extends JDialog{
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		JTextField txtFiltrof = new Componentes().text(new JCTextField(), "Teclea Aqui Para Buscar En La Tabla", 500, "String");
		Connexion con = new Connexion();
		Obj_tabla ObjTabf =new Obj_tabla();
		int columnas = 6,checkbox=-1;
		public void init_tablaf(){
	    	this.tablaf.getColumnModel().getColumn(0).setMinWidth(50);
	    	this.tablaf.getColumnModel().getColumn(0).setMaxWidth(50);
	    	this.tablaf.getColumnModel().getColumn(1).setMinWidth(270);
	    	this.tablaf.getColumnModel().getColumn(2).setMinWidth(250);
	    	this.tablaf.getColumnModel().getColumn(3).setMinWidth(120);
	    	this.tablaf.getColumnModel().getColumn(4).setMinWidth(220);
	     	this.tablaf.getColumnModel().getColumn(5).setMinWidth(220);
			String comandof="sp_select_listado_de_colaboradores_con_correo ";
			String basedatos="26",pintar="si";
			ObjTabf.Obj_Refrescar(tablaf,modelf, columnas, comandof, basedatos,pintar,checkbox);
	    }
		
		@SuppressWarnings("rawtypes")
		public Class[] base (){
			Class[] types = new Class[columnas];
			for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
			return types;
		}
		
		 public DefaultTableModel modelf = new DefaultTableModel(null, new String[]{"Folio","Colaborador","Email","Establecimiento","Departamento","Puesto"}){
			 @SuppressWarnings("rawtypes")
				Class[] types = base();
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
				public boolean isCellEditable(int fila, int columna){return false;}
		    };
		    JTable tablaf = new JTable(modelf);
			public JScrollPane scroll_tablaf = new JScrollPane(tablaf);
		     @SuppressWarnings("rawtypes")
		    private TableRowSorter trsfiltro;
		
		JCButton btnGuardarf      = new JCButton("Enviar Correo"       ,"Guardar.png"      ,"Verde");
		JTextField txtfolioselecionado = new Componentes().text(new JCTextField(), "Folio", 20, "Integer");
		JTextField txtselecionado      = new Componentes().text(new JCTextField(), "Colaborador Correo", 500, "String");
		JTextField txtemail            = new Componentes().text(new JCTextField(), "Email", 200, "String");
		JTextField txtasunto           = new Componentes().text(new JCTextField(), "Asunto Del Correo", 200, "String");
		
	    JTextArea txaNota 	= new Componentes().textArea(new JTextArea(), "Nota", 500);
		JScrollPane Nota = new JScrollPane(txaNota);
		
		JLabel lblBuscar      = new JLabel("BUSCAR : ");
		int folio_servicio=0;
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Cat_Envio_De_Correos()	{
			this.setModal(true);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
			this.setTitle("Colaboradores");
			this.setSize(805,490);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
			trsfiltro = new TableRowSorter(modelf); 
			tablaf.setRowSorter(trsfiltro); 
			int x=10, y=20, width=100, height=20;
			campo.add(txtFiltrof).setBounds            (x       ,y      ,780        ,height  );
			campo.add(scroll_tablaf).setBounds         (x       ,y+=20  ,780        ,200     );
			campo.add(new JLabel("Destino:")).setBounds(x       ,y+=210 ,width      ,height  );  
			campo.add(txtfolioselecionado).setBounds   (x+=40   ,y      ,60         ,height  );
			campo.add(txtselecionado).setBounds        (x+=60   ,y      ,width*4    ,height  );
			campo.add(txtemail).setBounds              (x+=400  ,y      ,280        ,height  );
			
			x=10;
			campo.add(new JLabel("Asunto:")).setBounds (x       ,y+=25  ,width      ,height  );  
			campo.add(txtasunto).setBounds             (x+=40   ,y      ,width*7+39 ,height  );
			
			x=10;
			campo.add(new JLabel("Mensaje:")).setBounds(x       ,y+=25  ,width      ,height  );  
			campo.add(Nota).setBounds                  (x       ,y+=20  ,width*7+79 ,height*5);
			campo.add(btnGuardarf).setBounds           (590     ,y+=110 ,width*2    ,height  );
			
			txtfolioselecionado.setEditable(false);
			txtemail.setEditable(false);
			txtselecionado.setEditable(false);
			agregarasignado(tablaf);
			tablaf.addKeyListener(seleccionEmpleadocontecladopara_asignacion);	
			btnGuardarf.addActionListener(enviar_correo);
			
			init_tablaf();
			cont.add(campo);
			txtFiltrof.addKeyListener(opFiltrof);
		}
		
		ActionListener enviar_correo = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
             if(txtasunto.getText().toString().equals("")){
            	 JOptionPane.showMessageDialog(null, "Se Requiere Un Asunto Para Poder Enviar El Correo", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
               }else{
	    				
				 String Mensaje=txaNota.getText().toString().trim();
			     new EmailSenderService().enviarcorreo(txtemail.getText().toString().trim() ,0,Mensaje,txtasunto.getText().toString(),"Servicios");
			     txtasunto.setText("");
			     txaNota.setText("");
			     
               } 
			}
		};
		@SuppressWarnings("unused")
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		        		int fila = tablaf.getSelectedRow();
		    			dispose();
		    			return;
		        	}
		        }
	        });
	    }
		
		private void agregarasignado(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		        		int fila = tablaf.getSelectedRow();
		        		if(tablaf.getValueAt(fila, 2).toString().trim().equals("")){
			 				 JOptionPane.showMessageDialog(null, "El Colaborador Selecionado No Tiene Correo Registrado \nPor Lo Cual No Es Seleccionable Para Enviarle Correo \nSolicite a Desarrollo Humano El Que Se Le Alimente El Correo De La Empresa:", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			 				return;
		        		}else{
		        			
		        		txtfolioselecionado.setText(tablaf.getValueAt(fila, 0).toString().trim());
		    			txtselecionado.setText (tablaf.getValueAt(fila, 1).toString().trim());
		    			txtemail.setText (tablaf.getValueAt(fila, 2).toString().trim());
		    			return;
		        		}
		        	}
		        }
	        });
	    }
		
		KeyListener opFiltrof = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				ObjTabf.Obj_Filtro(tablaf, txtFiltrof.getText().toUpperCase(), columnas,txtFiltrof);
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		KeyListener seleccionEmpleadoconteclado = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e){}
			@SuppressWarnings("static-access")
			@Override
			public void keyReleased(KeyEvent e){
				char caracter = e.getKeyChar();
				if(caracter==e.VK_ENTER){
				int fila=tablaf.getSelectedRow()-1;
				if(fila==-1){
					fila=tablaf.getRowCount()-1;
				}
    			dispose();
    			return;
				}
			}
		};
		
		KeyListener seleccionEmpleadocontecladopara_asignacion = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e){
			}
			@Override
			public void keyReleased(KeyEvent e){
				int fila=tablaf.getSelectedRow();
        		if(tablaf.getValueAt(fila, 2).toString().trim().equals("")){
	 				JOptionPane.showMessageDialog(null, "El Colaborador Selecionado No Tiene Correo Registrado \nPor Lo Cual No Es Seleccionable Para Enviarle Correos \nSolicite a Desarrollo Humano El Que Se Le Alimente \nEl Correo De La Empresa:", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
	 				return;
        		}else{
        		txtfolioselecionado.setText(tablaf.getValueAt(fila, 0).toString().trim());
    			txtselecionado.setText (tablaf.getValueAt(fila, 1).toString().trim());
    			return;
        		}
			}
		};
		
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Envio_De_Correos().setVisible(true);
			}catch(Exception e){	}
		}
		
	}