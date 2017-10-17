package Biblioteca;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.text.MaskFormatter;

public class TimeFormatter extends MaskFormatter {

    private static final long serialVersionUID = 1L;

    public TimeFormatter() { // set mask and placeholder
        try {
            setMask("##/##/####");
            setPlaceholderCharacter('0');
            setAllowsInvalid(false);
            setOverwriteMode(true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object stringToValue(String string) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if (string == null) {
            string = "00/00/0000";
        }
        return df.parse(string);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if (value == null) {
            value = new Date(0);
        }
        return df.format((Date) value);
    }

    private void MyGui() {
        final MaskFormatter formatter = new TimeFormatter(); // textfield 1: create formatter and textfield
        //formatter.setValueClass(java.util.Date.class);
        final JFormattedTextField tf2 = new JFormattedTextField(formatter);// textfield 2: create formatter and textfield
        tf2.setValue(new Date()); // no initial value        
        final JLabel label = new JLabel();
        JButton bt = new JButton("Show Value");// button to show current value
        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(" value 2 = " + tf2.getValue());
                System.out.println(" value 2 = " + tf2.getText());
                System.out.println("value class: " + formatter.getValueClass());
                label.setText(tf2.getText());
            }
        });        
        JFrame f = new JFrame(); // main frame
        f.getContentPane().setLayout(new GridLayout());
        f.getContentPane().add(tf2);
        f.getContentPane().add(label);
        f.getContentPane().add(bt);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        Runnable doRun = new Runnable() {
            @Override
            public void run() {
                new TimeFormatter().MyGui();
            }
        };
        SwingUtilities.invokeLater(doRun);
    }
}