package Cat_Reportes;

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
import Obj_Principal.JCButton;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Ventas_Por_Clasificacion_De_Metas_Por_Grupos_De_Establecimientos extends JFrame {
	
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
	
	JCButton btnGenerar    = new JCButton("Generar"    ,"Report.png","Azul"); 

	
		String []anio=omep.Combo_Respuesta_anio(); 
		  @SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbAnio=new JComboBox(anio);
		
		   @SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbMes= new JComboBox(new String[]{"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"});

		   @SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbGrupo= new JComboBox(new String[]{"Seleccione un Grupo","Alimentos","Dulcerias","Papelerias","Refaccionarias","Supermercados"});
		
public Cat_Reportes_De_Ventas_Por_Clasificacion_De_Metas_Por_Grupos_De_Establecimientos() {
	            this.setSize(330,160);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/bandera-a-cuadros-para-terminar-icono-8019-64.png"));
				this.setTitle("Reporte De Metas Mensuales");
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				panel.setBorder(BorderFactory.createTitledBorder("#"));

				int x=15, y=20, width=100,height=20,sep=35;

				panel.add(lblAnio).setBounds   (x       , y     ,width ,height);
				panel.add(cmbAnio).setBounds   (x+sep   , y     ,width ,height);
				panel.add(lblMes).setBounds    (x+cmbAnio.getX()+cmbAnio.getWidth(), y, width/3, height);
				panel.add(cmbMes).setBounds    (lblMes.getX()+lblMes.getWidth(), y, width, height);
				panel.add(lblGrupo).setBounds  (x       , y+=35 ,(width/2), height);
				panel.add(cmbGrupo).setBounds  (x+sep   , y     ,width+148, height);
				panel.add(btnGenerar).setBounds(x+sep   , y+=35 ,width+148 ,height+5);
				
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
				 comando = "exec sp_reporte_de_ventas_por_clasificacion_de_metas "+"'"+cmbGrupo.getSelectedItem()+"',"+cmbAnio.getSelectedItem()+","+"'"+cmbMes.getSelectedItem()+"'";
				
				 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
	}
	
	public double Redondear(double numero, float porcentaje){
		porcentaje=1+(porcentaje/100);
		return (Math.rint((((numero*porcentaje)/100)+0.5))*100);
    }
	
	ActionListener opGenerarTabla = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				if(cmbGrupo.getSelectedItem().toString().trim().equals("Seleccione un Grupo")){
					JOptionPane.showMessageDialog(null, "Es Necesario Selecionar Un Grupo ", "Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
					cmbGrupo.requestFocus();
					cmbGrupo.showPopup();
					return;
				}
								GenerarReporte();
		}
	};
		
	ActionListener opDeshacer  = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmbAnio.setEnabled(true);
				cmbAnio.setSelectedIndex(0);
				cmbMes.setEnabled(true);
				cmbMes.setSelectedIndex(0);
				cmbGrupo.setEnabled(true);	
				cmbGrupo.setSelectedIndex(0);
				btnGenerar.setEnabled(true);
			   }  
	};
		
	
	public static void main(String [] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Ventas_Por_Clasificacion_De_Metas_Por_Grupos_De_Establecimientos().setVisible(true);
			}catch(Exception e){}
	}
  }

		  
