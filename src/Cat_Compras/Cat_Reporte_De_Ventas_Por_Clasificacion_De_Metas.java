package Cat_Compras;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import Conexiones_SQL.Generacion_Reportes;
import Obj_Compras.Obj_Metas_Establecimiento_periodo;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.JCButton;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Ventas_Por_Clasificacion_De_Metas extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	String[]Colum={"Folio","Nombre de Clasificacion ","Venta Real ","Sugerido A %","Meta de Venta 'A'","Sugerido B %","Meta de Venta 'B'","Sugerido C %","Meta de Venta 'C'"};

	 int columna=3;
	 int columna2=5;
	 int columna3=7;
     int fila=0;
	
	Obj_Metas_Establecimiento_periodo omep = new Obj_Metas_Establecimiento_periodo();
	
	JLabel lblEstablecimiento =new JLabel("Establecimiento:");
	JLabel lblAnio = new JLabel("Año: ");
	JLabel lblMes = new JLabel("Mes: ");
	JLabel lblGrupo = new JLabel("Grupo: ");
	
	JCButton btnGenerar    = new JCButton("Generar"    ,/*"bandera-a-cuadros-para-terminar-icono-8019-32.png"*/"","AzulC"); 

	    String Establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento_Estado_resultados();
	      @SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbEstablecimiento= new JComboBox(Establecimiento);
		
		String []anio=omep.Combo_Respuesta_anio(); 
		  @SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbAnio=new JComboBox(anio);
		
		   @SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbMes= new JComboBox(new String[]{"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"});

		   @SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbGrupo= new JComboBox(new String[]{"Seleccione un Grupo","Alimentos","Dulcerias","Papelerias","Refaccionarias","Supermercados"});
		
public Cat_Reporte_De_Ventas_Por_Clasificacion_De_Metas() {
	            this.setSize(330,160);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/bandera-a-cuadros-para-terminar-icono-8019-64.png"));
				this.setTitle("Alimentacion De Metas Mensuales Por Establecimiento");
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				panel.setBorder(BorderFactory.createTitledBorder("#"));

				int x=15, y=20, ancho=100,z=25;
				
				panel.add(lblEstablecimiento).setBounds(x, y, ancho-15, z);
				panel.add(cmbEstablecimiento).setBounds(x+ancho, y, ancho+80, z);

				panel.add(lblAnio).setBounds(x, y+=30, ancho/3, z);
				panel.add(cmbAnio).setBounds(lblAnio.getX()+lblAnio.getWidth(), y, ancho, z);
				
				panel.add(lblMes).setBounds(x+cmbAnio.getX()+cmbAnio.getWidth(), y, ancho/3, z);
				panel.add(cmbMes).setBounds(lblMes.getX()+lblMes.getWidth(), y, ancho, z);
				
				panel.add(lblGrupo).setBounds(x, y+=30,(ancho/2), z);
				panel.add(cmbGrupo).setBounds(lblGrupo.getX()+lblGrupo.getWidth()-10, y, (ancho+50), z);
				
				ancho=90;
				panel.add(btnGenerar).setBounds (x+cmbGrupo.getX()+cmbGrupo.getWidth()-15,y,ancho, z);
				
				cont.add(panel);
				
				btnGenerar.addActionListener(opGenerarTabla);

	}

	String basedatos="2.94";
	String vista_previa_reporte="no";
	int vista_previa_de_ventana=0;
	String comando="";
	String reporte = "";
	private void GenerarReporte(){    
				reporte = "Obj_Reporte_De_Ventas_Por_Clasificacion_De_Metas.jrxml";
				 comando = "exec sp_reporte_de_ventas_por_clasificacion_de_metas "+"'"+cmbGrupo.getSelectedItem()+"',"+cmbAnio.getSelectedItem()+","+"'"+cmbMes.getSelectedItem()+"',"+"'"+cmbEstablecimiento.getSelectedItem()+"'";
				
				 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
	}
	
	public double Redondear(double numero, float porcentaje){
		porcentaje=1+(porcentaje/100);
		return (Math.rint((((numero*porcentaje)/100)+0.5))*100);
    }
	
//	public void buscaMetaFecha(){    
//			Statement s;
//			ResultSet rs;
//
//			try {
//				Connexion con = new Connexion();
//				s = con.conexion_ventas().createStatement();
//				rs = s.executeQuery("exec sp_consulta_de_metas_mensuales_por_establecimiento '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"',"+cmbAnio.getSelectedItem().toString().trim()+","+cmbMes.getSelectedIndex());
//			 String [] fila = new String[10];
//			while (rs.next()){   
//				  fila[0] = rs.getString(1).trim();//clasificacionMeta
//				  fila[1] = rs.getString(2).trim();//code_esta
//				  fila[2] = rs.getString(3).trim();//meta_mensual 
//				  fila[3] = rs.getString(4).trim();//mes 
//				  fila[4] = rs.getString(5).trim();//año 
//				  fila[5] = rs.getString(6).trim();//estatus 
//				  fila[6] = rs.getString(7).trim();//porcentaje
//				  fila[7] = rs.getString(8).trim();//porcentaje de b 
//				  fila[8] = rs.getString(9).trim();//porcentaje de C 
//			}
//			
//		s.close();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//					JOptionPane.showMessageDialog(null, "Error en la  SQLException: "+e1.getMessage(), "Avisa al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
//										  }
//	}
	
	ActionListener opGenerarTabla = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
//				int anio=Integer.valueOf((String) cmbAnio.getSelectedItem());
//				int mes =cmbMes.getSelectedIndex()+1;
//				String estab = (String)cmbEstablecimiento.getSelectedItem().toString().trim();
//				int cod_meta=cmbGrupo.getSelectedIndex();
				
				if(cmbEstablecimiento.getSelectedItem().toString().trim().equals("Selecciona un Establecimiento")){
					JOptionPane.showMessageDialog(null, "Es Necesario Selecionar Un Establecimiento ", "Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
					cmbEstablecimiento.requestFocus();
					cmbEstablecimiento.showPopup();
					return;
				}
				if(cmbGrupo.getSelectedItem().toString().trim().equals("Seleccione un Grupo")){
					JOptionPane.showMessageDialog(null, "Es Necesario Selecionar Un Grupo ", "Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
					cmbGrupo.requestFocus();
					cmbGrupo.showPopup();
					return;
				}
				
//				if( omep.buscar_metas_periodo(anio,mes,cmbEstablecimiento.getSelectedItem().toString().trim()).equals("si")){
//					
//					JOptionPane.showMessageDialog(null, "La Meta Que Estas Queriendo Calcular Ya Existe y No Puede Ser Modificada,\n Esta Se Cargará A Continuación", "Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
//					buscaMetaFecha();
//					btnGenerar.setEnabled(false);
//					cmbAnio.setEnabled(false);
//					cmbMes.setEnabled(false);
//					cmbEstablecimiento.setEnabled(false);
//					cmbGrupo.setEnabled(false);
//					return;
//			   }else{
//			            if(cmbGrupo.getSelectedItem().toString().trim().equals("Supermercados")){
//			            	cod_meta=1;
//			            }	 
//			            
//			            if(cmbGrupo.getSelectedItem().toString().trim().equals("Alimentos")){
//			            	cod_meta=7;
//			            }
//			            
//			            if(cmbGrupo.getSelectedItem().toString().trim().equals("Papelerias")){
//			            	cod_meta=16;
//			            }
//			            
//			            if(cmbGrupo.getSelectedItem().toString().trim().equals("Refaccionarias")){
//			            	cod_meta=23;
//			            }
//			            
//			            if(cmbGrupo.getSelectedItem().toString().trim().equals("Dulcerias")){
//			            	cod_meta=48;
//			            }

//						if(omep.buscar_metas_a_generar(anio, mes,cod_meta,estab).equals("no") ){
//								JOptionPane.showMessageDialog(null, "No Se Pudieron Calcular Metas Con Los Parametros Seleccionados "
//										+ "  \no No Existe Venta del Año Anterior Seleccionado:["+cmbAnio.getSelectedItem().toString().trim()+"]"
//										+ "  \nA Continuación Unos Ejemplos De Ayuda"
//										+ "  \nEjemplo 1: Establecimiento [SUPER I]        Grupo [Supermercados] "
//										+ "  \nEjemplo 2: Establecimiento [ESPACIO 35] Grupo [Alimentos]"
//										+ "  \nEjemplo 3: Establecimiento [PAPER CITY] Grupo [Papelerias]"
//										, "Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
//								return;						
//						}else{
								GenerarReporte();
//							    btnGenerar.setEnabled(false);
//								cmbAnio.setEnabled(false);
//								cmbMes.setEnabled(false);
//								cmbEstablecimiento.setEnabled(false);
//								cmbGrupo.setEnabled(false);
//								return;
//						} 
//			   }
		}
	};
		
	ActionListener opDeshacer  = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmbAnio.setEnabled(true);
				cmbAnio.setSelectedIndex(0);
				cmbMes.setEnabled(true);
				cmbMes.setSelectedIndex(0);
				cmbEstablecimiento.setEnabled(true);
				cmbEstablecimiento.setSelectedIndex(0);
				cmbGrupo.setEnabled(true);	
				cmbGrupo.setSelectedIndex(0);
				btnGenerar.setEnabled(true);
	            cmbEstablecimiento.showPopup();
			   }  
	};
		
	
	public static void main(String [] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Ventas_Por_Clasificacion_De_Metas().setVisible(true);
			}catch(Exception e){}
	}
  }

		  
