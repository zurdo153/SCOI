package Obj_Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

public class JCSpinner {
	 
    public final JSpinner JCSpinnerHoras(int hora, int minutos){
    	
        Calendar calendar = new GregorianCalendar();
	    calendar.set(Calendar.HOUR_OF_DAY, hora); 
	    calendar.set(Calendar.MINUTE, minutos); 
	    SpinnerDateModel dateModel = new SpinnerDateModel(calendar.getTime(), null, null, Calendar.HOUR_OF_DAY);
	    
	    JSpinner jspine = new JSpinner(dateModel);
	    
	    new JSpinner(dateModel);
	    
	    JFormattedTextField tf = ((JSpinner.DefaultEditor) jspine.getEditor()).getTextField();
       
        DefaultFormatterFactory factory = (DefaultFormatterFactory) tf.getFormatterFactory();
	    DateFormatter formatter = (DateFormatter) factory.getDefaultFormatter();
	    formatter.setFormat(new SimpleDateFormat("HH:mm"));
        formatter.setAllowsInvalid(false); 
        formatter.setOverwriteMode(true);
        
      return jspine;
    }
	
}