package Biblioteca;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;

public class TablePrepareRenderer extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private Date maturityDate = new Date();
    private Date todayDate = new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    private Date tableDate = new Date();
    private String strDate = "";
    private Date modifDate = new Date();
//    private Calendar cal;

    public TablePrepareRenderer() {
        Object[] columnNames = {"Type", "Company", "Shares", "Price", "Date"};
        Object[][] data = {
            {"Buy", "IBM", new Integer(1000), new Double(80.50), new Date()},
            {"Sell", "MicroSoft", new Integer(2000), new Double(6.25), new Date()},
            {"Sell", "Apple", new Integer(3000), new Double(7.35), new Date()},
            {"Buy", "Nortel", new Integer(4000), new Double(20.00), new Date()}
        };
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {

            private static final long serialVersionUID = 1L;

            @SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };
        table = new JTable(model) {

            private static final long serialVersionUID = 1L;

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                /*int firstRow = 0;
                int lastRow = table.getRowCount() - 1;
                if (row == lastRow) {
                ((JComponent) c).setBackground(Color.red);
                } else if (row == firstRow) {
                ((JComponent) c).setBackground(Color.blue);
                } else {
                ((JComponent) c).setBackground(table.getBackground());
                }*/
                if (!isRowSelected(row)) {
                    if (table.getColumnCount() >= 0) {
                        String type = (String) getModel().getValueAt(row, 0);
                        c.setBackground("Buy".equals(type) ? Color.YELLOW : Color.GREEN);
//
                        maturityDate = new Date();
                        todayDate = new Date();
                        strDate = sdf.format(todayDate);
                        try {
                            todayDate = sdf.parse(strDate);
                        } catch (ParseException ex) {
                            Logger.getLogger(TablePrepareRenderer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        tableDate = (Date) table.getValueAt(row, 4);
                        strDate = sdf.format(tableDate);
                        if (strDate != null) {
                            if (!strDate.isEmpty()) {
                                try {
                                    maturityDate = sdf.parse(strDate);
                                } catch (ParseException ex) {
                                    Logger.getLogger(TablePrepareRenderer.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                if (maturityDate != null) {
                                    int mmDiffDealToValue = (maturityDate).compareTo(todayDate);
                                    if (((mmDiffDealToValue < 0))) {
                                        c.setBackground(Color.orange);
                                        c.setFont(new Font("Serif", Font.BOLD, 12));
                                    }
                                }
                            }
                        }
//
                    }
                }
                if (isRowSelected(row) && isColumnSelected(column)) {
                    ((JComponent) c).setBorder(new LineBorder(Color.red));
                }
                return c;
            }
        };
        modifyDateInTable();
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);
    }

    private void modifyDateInTable() {
        Calendar c = Calendar.getInstance();
        c.setTime(modifDate);
        c.add(Calendar.DATE, - 1);
        modifDate = c.getTime();
        table.setValueAt(modifDate, 0, 4);
        c.setTime(modifDate);
        c.add(Calendar.DATE, +5);
        modifDate = c.getTime();
        table.setValueAt(modifDate, 1, 4);
        c.setTime(modifDate);
        c.add(Calendar.DATE, +1);
        modifDate = c.getTime();
        table.setValueAt(modifDate, 1, 4);
        c.setTime(modifDate);
        c.add(Calendar.DATE, - 16);
        modifDate = c.getTime();
        table.setValueAt(modifDate, 3, 4);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                TablePrepareRenderer frame = new TablePrepareRenderer();
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocation(150, 150);
                frame.setVisible(true);
            }
        });
    }
}