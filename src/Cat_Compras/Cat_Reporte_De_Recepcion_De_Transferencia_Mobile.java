package Cat_Compras;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.Generacion_Reportes;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Recepcion_De_Transferencia_Mobile extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String[] tipoDeReporte = {"Selecciona Un Tipo De Reporte","Reporte De Productos Sin Transferir General","Reporte De Productos Sin Recepcionar, Por Transferencia","Reporte De Productos Con Ajuste, Por Transferencia","Reporte De Productos Con Diferencias, Por Transferencia","Reporte De Productos Cancelados, Por Transferencia","Reporte De Incidencias Por Transferencia","Reporte De Productos Recepcionados"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbTipoDeReporte = new JComboBox(tipoDeReporte);
	
	JTextField txtFolioTransferencia = new Componentes().text(new JCTextField(), "Folio De Transferencia", 12, "String");
	JTextField txtFolioRecepcion = new Componentes().text(new JCTextField(), "Folio De Recepcion", 12, "String");
	
	JDateChooser fhIn = new JDateChooser();
	JDateChooser fhFin = new JDateChooser();
	
	JCButton btnGenerar = new JCButton("Buscar", "buscar.png", "Azul");

	Border borde;
	public Cat_Reporte_De_Recepcion_De_Transferencia_Mobile() {
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/lista-icono-7220-32.png"));
		borde = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(borde,"Reporte De Movimiento De Productos En Recepcion Mobile"));
		this.setTitle("Reporte De Movimiento De Productos En Recepcion Mobile");
		
		int x=10,	y=20,	ancho=90;
		
		panel.add(new JLabel("Tipo De Reporte: ")).setBounds(x,y,ancho+30,20);
		panel.add(cmbTipoDeReporte).setBounds(x+ancho+40, y, ancho*3, 20);
		
		panel.add(new JLabel("Folio De Recepcion: ")).setBounds(x,y+=25,ancho+30,20);
		panel.add(txtFolioRecepcion).setBounds(x+ancho+40, y, ancho*3, 20);
		
		panel.add(new JLabel("Folio De Transferencia: ")).setBounds(x,y+=25,ancho+30,20);
		panel.add(txtFolioTransferencia).setBounds(x+ancho+40, y, ancho*3, 20);
		
		panel.add(new JLabel("Fecha                      Del:")).setBounds(x,y+=25,ancho+30,20);
		panel.add(fhIn).setBounds(x+ancho+40, y, ancho+30, 20);
		
		panel.add(new JLabel("Al:")).setBounds(x+ancho*3-10,y,30,20);
		panel.add(fhFin).setBounds(x+ancho*3+10, y, ancho+30, 20);
		
		
		
		panel.add(btnGenerar).setBounds(x+(ancho*3)+20,y+=25,ancho+20,20);
		
		cont.add(panel);
		
		camposInactivos();
		cmbTipoDeReporte.addActionListener(opTipoDeReporte);
		btnGenerar.addActionListener(opGenerar);
		
		this.setSize(425, 180);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	ActionListener opTipoDeReporte = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			camposInactivos();
		}
	};
	
	public void camposInactivos(){
		if(cmbTipoDeReporte.getSelectedIndex()==0){
			txtFolioTransferencia.setEnabled(false);
			txtFolioRecepcion.setEnabled(false);
			fhIn.setEnabled(false);
			fhFin.setEnabled(false);

		}else{
			
			if(cmbTipoDeReporte.getSelectedItem().toString().trim().equals("Reporte De Productos Recepcionados")){
				txtFolioTransferencia.setEnabled(false);
				txtFolioRecepcion.setEnabled(true);
				fhIn.setEnabled(false);
				fhFin.setEnabled(false);
				
				txtFolioTransferencia.setText("");
				fhIn.setDate(null);
				fhFin.setDate(null);
				
			}else{
				
				txtFolioTransferencia.setEnabled(true);
				txtFolioRecepcion.setEnabled(false);
				
				txtFolioRecepcion.setText("");
				
				if(cmbTipoDeReporte.getSelectedItem().toString().equals("Reporte De Incidencias Por Transferencia")){
					fhIn.setEnabled(false);
					fhFin.setEnabled(false);
					
					fhIn.setDate(null);
					fhFin.setDate(null);
				}else{
					fhIn.setEnabled(true);
					fhFin.setEnabled(true);
				}
				
			}
			
		}
	}
	
	String fecha_inicio = "";
	String fecha_final  = "";
	ActionListener opGenerar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			if(cmbTipoDeReporte.getSelectedIndex()==0){
				JOptionPane.showMessageDialog(null,"Favor De Seleccionar El Tipo De Reporte", "Mensaje!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				
				if(!validaCampos().equals("")){
					JOptionPane.showMessageDialog(null,"Los siguientes Campos Son Requeridos: \n"+validaCampos(), "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}else{
					
					if( cmbTipoDeReporte.getSelectedItem().toString().trim().equals("Reporte De Productos Recepcionados") || cmbTipoDeReporte.getSelectedItem().toString().trim().equals("Reporte De Incidencias Por Transferencia")){
						
						reporte();
							
					}else{
						fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(fhIn.getDate())+" 00:00:00";
						fecha_final  = new SimpleDateFormat("dd/MM/yyyy").format(fhFin.getDate())+"  23:59:00";
						
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
						Date fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
						Date fecha2 = sdf.parse(fecha_final , new ParsePosition(0));
						
						if(fecha1.before(fecha2)){	
					 		reporte();
					 	}else{
							JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
					
				}
			}
		}
	};
	
	public String validaCampos(){
		
		String error="";
		
		if(cmbTipoDeReporte.getSelectedItem().toString().trim().equals("Reporte De Incidencias Por Transferencia")){
			error+= txtFolioTransferencia.getText().equals("")?"Folio De Transferencia\n":"";
		}else{
			if(cmbTipoDeReporte.getSelectedItem().toString().trim().equals("Reporte De Productos Recepcionados")){
				error+= txtFolioRecepcion.getText().equals("")?"Folio Recepcion\n":"";
			}else{			
				String fechaIn = fhIn.getDate()+"";
				String fechaFin= fhFin.getDate()+"";
				
//				error+= txtFolioTransferencia.getText().equals("")?"Folio Transferencia\n":"";
				error+= fechaIn.equals("null")?"Fecha Inicial\n":"";
				error+= fechaFin.equals("null")?"Fecha Final\n":"";
			}
		}
		return error;
	}
	
	public void reporte(){
		String basedatos="2.98";
		String vista_previa_reporte="no";
		int vista_previa_de_ventana=0;
		
			String reporte = "";
			String consulta = "";
			
			switch(cmbTipoDeReporte.getSelectedItem().toString().trim()){
				case "Reporte De Productos Sin Transferir":		reporte = "Obj_Reporte_De_Productos_Recepcionados_Sin_Tranferir.jrxml";				consulta = "exec sp_select_detalle_de_recepcion '"+txtFolioTransferencia.getText().trim().toUpperCase()+"','ST','"+fecha_inicio+"','"+fecha_final+"'"; break;
				case "Reporte De Productos Sin Recepcionar":	reporte = "Obj_Reporte_De_Productos_Recepcionados_Sin_Tranferir.jrxml";				consulta = "exec sp_select_detalle_de_recepcion '"+txtFolioTransferencia.getText().trim().toUpperCase()+"','SR','"+fecha_inicio+"','"+fecha_final+"'"; break;
				case "Reporte De Productos Con Ajuste":			reporte = "Obj_Reporte_De_Productos_Recepcionados_Sin_Tranferir.jrxml";				consulta = "exec sp_select_detalle_de_recepcion '"+txtFolioTransferencia.getText().trim().toUpperCase()+"','A','"+fecha_inicio+"','"+fecha_final+"'"; break;
				case "Reporte De Productos Diferencias":		reporte = "Obj_Reporte_De_Productos_Recepcionados_Sin_Tranferir.jrxml";				consulta = "exec sp_select_detalle_de_recepcion '"+txtFolioTransferencia.getText().trim().toUpperCase()+"','','"+fecha_inicio+"','"+fecha_final+"'"; break;
				case "Reporte De Productos Con Cancelacion":	reporte = "Obj_Reporte_De_Productos_Recepcionados_Sin_Tranferir.jrxml";				consulta = "exec sp_select_detalle_de_recepcion '"+txtFolioTransferencia.getText().trim().toUpperCase()+"','C','"+fecha_inicio+"','"+fecha_final+"'"; break;
				case "Reporte De Productos Recepcionados":		reporte = "Obj_Reporte_De_Productos_Recepcionados.jrxml";							consulta = "exec sp_select_productos_recepcionados '"+txtFolioRecepcion.getText().trim().toUpperCase()+"'"; break;
				case "Reporte De Incidencias Por Transferencia":reporte = "Obj_Reporte_De_Incidencia_De_Productos_Por_Folio_De_Transferencia.jrxml";consulta = "exec sp_select_incidencias_por_folio_de_transferencia '"+txtFolioTransferencia.getText().trim().toUpperCase()+"'"; break;
				
				default:	JOptionPane.showMessageDialog(null,"Favor De Seleccionar El Tipo De Reporte", "Mensaje!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png")); break;
			}
			new Generacion_Reportes().Reporte(reporte, consulta, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		
	}
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Recepcion_De_Transferencia_Mobile().setVisible(true);
		}catch(Exception e){	}
	}

}
