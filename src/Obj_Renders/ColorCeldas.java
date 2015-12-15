package Obj_Renders;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class ColorCeldas extends DefaultTableCellRenderer{

    private int columna_patron ;

    public ColorCeldas(int Colpatron)
    {
        this.columna_patron = Colpatron;
    }

    public Component getTableCellRendererComponent ( JTable table, Object value, boolean selected, boolean focused, int row, int column){
    	
    	Color cFondo = table.getValueAt(row, columna_patron).toString().trim().equals("PLA") ? Color.white : table.getValueAt(row, columna_patron).toString().trim().equals("RES") ? Color.green : Color.yellow;
		setBackground(cFondo);

        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        return this;
      }

}