package Biblioteca;

import javax.swing.JFrame;
import javax.swing.JSpinner;

import Obj_Principal.JCSpinner;

import java.awt.Container;
import javax.swing.JLayeredPane;
import javax.swing.UIManager;


@SuppressWarnings("serial")
public class MaskedFieldTest extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JSpinner  spSabado5 = new JCSpinner().JCSpinnerHoras(2, 10);
	
	public MaskedFieldTest(){
		this.setSize(430,250);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				
//		    Calendar calendar = new GregorianCalendar();
//		    calendar.set(Calendar.HOUR_OF_DAY, 2); 
//		    calendar.set(Calendar.MINUTE, 0); 
//		    SpinnerDateModel dateModel = new SpinnerDateModel(calendar.getTime(), null, null, Calendar.HOUR_OF_DAY);
//		    
//		    JSpinner spSabado5 = new JSpinner(dateModel);
//		    JFormattedTextField tf = ((JSpinner.DefaultEditor) spSabado5.getEditor()).getTextField();
//		    
//		    
//		    DefaultFormatterFactory factory = (DefaultFormatterFactory) tf.getFormatterFactory();
//		    DateFormatter formatter = (DateFormatter) factory.getDefaultFormatter();
//		    formatter.setFormat(new SimpleDateFormat("HH:mm"));
//	        formatter.setAllowsInvalid(false); 
//	        formatter.setOverwriteMode(true);
	        
	    this.panel.add(spSabado5).setBounds          (20    ,25       ,100   ,20 );
		this.cont.add(panel);
		
	}
	
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new MaskedFieldTest().setVisible(true);
		}catch(Exception e){	}
	}

}
