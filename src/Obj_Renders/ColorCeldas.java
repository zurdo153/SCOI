package Obj_Renders;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class ColorCeldas extends DefaultTableCellRenderer{

    private int columna_patron ;
    private String tipo_de_tabla;

    public ColorCeldas(String tipo_tabla,int Colpatron){
        this.columna_patron = Colpatron;
        this.tipo_de_tabla = tipo_tabla;
    }

    public Component getTableCellRendererComponent ( JTable table, Object value, boolean selected, boolean focused, int row, int column){
    	
    	Color cFondo = null;
    	Color cTexto = Color.BLACK;
    	if(tipo_de_tabla.equals("Objetivos_De_La_Semana")){
	    	cFondo = table.getValueAt(row, columna_patron).toString().trim().equals("PLANEADO") ? Color.white : table.getValueAt(row, columna_patron).toString().trim().equals("RESUELTO") ? Color.green : Color.yellow;
    	}
    	if(tipo_de_tabla.equals("Actividades_De_La_Semana")){
    		cFondo = table.getValueAt(row, columna_patron).toString().trim().equals("AUTOASIGNADAS") ? new Color(221, 230, 221) : table.getValueAt(row, columna_patron).toString().trim().equals("EXTRA") ? new Color(45, 209, 255) : table.getValueAt(row, columna_patron).toString().trim().equals("JERARQUIAASIGNADAS") ? new Color(110, 155, 255) : Color.WHITE; 
    		cTexto = table.getValueAt(row, columna_patron).toString().trim().equals("JERARQUIAASIGNADAS") ? Color.WHITE : Color.BLACK;
    	}
    	
    	if(tipo_de_tabla.equals("Revision De Entrada De Mercancia")){
	    	cFondo = table.getValueAt(row, columna_patron).toString().trim().equals("PENDIENTE") ? Color.RED : Color.GREEN;
    	}
    	
    	if(tipo_de_tabla.equals("Revision De Pedido De Establecimiento")){
	    	cFondo = table.getValueAt(row, columna_patron).toString().trim().equals("NUEVO") ? Color.RED : table.getValueAt(row, columna_patron).toString().trim().equals("RECEPCIONADO") ? Color.GREEN : Color.YELLOW;
    	}
    	
    	if(tipo_de_tabla.equals("Relacion_De_Estatus_De_Pedido_De_Clientes_Del_Dia")){
	    	cFondo = table.getValueAt(row, columna_patron).toString().trim().equals("NO SURTIDO") ? Color.RED : Color.WHITE;
    	}
    	if(tipo_de_tabla.equals("Filtro De Pedido De Pendientes Por Establecimiento")){
    		cFondo = (table.getValueAt(row, columna_patron).toString().trim().equals("NUEVO") || table.getValueAt(row, columna_patron).toString().trim().equals("VIGENTE")) ? Color.RED 
    					: table.getValueAt(row, columna_patron).toString().trim().equals("MULTIPLES") ? Color.ORANGE
    							: table.getValueAt(row, columna_patron).toString().trim().equals("ASIGNADO") ? Color.YELLOW
	    							: table.getValueAt(row, columna_patron).toString().trim().equals("RECIBIDO") ? new Color(229, 255, 165)
	    									: table.getValueAt(row, columna_patron).toString().trim().equals("SURTIDO") ? Color.GREEN
	    											: Color.WHITE;
    	}
    	
    	
		setBackground(cFondo);
		setForeground(cTexto);

        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        return this;
      }

}