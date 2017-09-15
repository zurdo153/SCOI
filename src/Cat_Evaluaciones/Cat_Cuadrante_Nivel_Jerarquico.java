package Cat_Evaluaciones;

import java.awt.Container;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Obj_Evaluaciones.Obj_Nivel_Jerarquico;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Cuadrante_Nivel_Jerarquico extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolio = new JTextField();
	JCheckBox  chbStatus = new JCheckBox("Status",true);
	
	JTextField txtCuadrante = new JTextField();
	
	JTextArea txaDescripcion = new Componentes().textArea(new JTextArea(), "Descripción", 700);
	JScrollPane Descripcion = new JScrollPane(txaDescripcion);
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento_Empleados();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	String nivel_jerarquico[] = new Obj_Nivel_Jerarquico().combo_nivel_jerarquico();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbnivel_jerarquico = new JComboBox(nivel_jerarquico);
	
	String equipo_trabajo[] = new Obj_Establecimiento().Combo_Eq_Trabajo();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEquipo_Trabajo = new JComboBox(equipo_trabajo);
	
	String establecimientos[] = new Obj_Establecimiento().Combo_Establecimiento();
	
	public Cat_Cuadrante_Nivel_Jerarquico(String nombre, int folio){
	}
	
	/** JUEVES **/
//	public void copiar_todos_jueves(){
//		if(modelJueves.getRowCount() > 0){
//			
//			/** RESETEAR LAS TABLAS A LAS QUE SE LES VAN A AGREGAR DATOS **/
//			while(tablaDomingo.getRowCount() > 0){ modelDomingo.removeRow(0); }
//			while(tablaLunes.getRowCount() > 0){ modelLunes.removeRow(0); }
//			while(tablaMartes.getRowCount() > 0){ modelMartes.removeRow(0); }
//			while(tablaMiercoles.getRowCount() > 0){ modelMiercoles.removeRow(0); }
//			while(tablaViernes.getRowCount() > 0){ modelViernes.removeRow(0); }
//			while(tablaSabado.getRowCount() > 0){ modelSabado.removeRow(0); }
//			
//			/** CARGAR DOMINGO CON DATOS DEL ANFITRION **/
//			Object[] fila = {"","","","false","",""};
//			for(int i=0; i<modelJueves.getRowCount(); i++){
//				modelDomingo.addRow(fila);
//				modelDomingo.setValueAt(modelJueves.getValueAt(i,0).toString(), i,0);
//				modelDomingo.setValueAt(modelJueves.getValueAt(i,1).toString(), i,1);
//				modelDomingo.setValueAt(modelJueves.getValueAt(i,2).toString(), i,2);
//				modelDomingo.setValueAt(Boolean.parseBoolean(modelJueves.getValueAt(i,3).toString()), i,3);
//				modelDomingo.setValueAt(modelJueves.getValueAt(i,4).toString(), i,4);
//				modelDomingo.setValueAt(modelJueves.getValueAt(i,5).toString(), i,5);
//				
//			}
//			
//			chDomingo.setSelected(true);
//			btnRemoverDomingo.setEnabled(true);
//			btn_copiar_domingo_al_lunes.setEnabled(true);
//			tablaDomingo.setEnabled(true);
//			
//		}else{
//			JOptionPane.showMessageDialog(null,"No hay filas en la tabla", "Aviso", JOptionPane.WARNING_MESSAGE);
//		}
//	}
	
}
