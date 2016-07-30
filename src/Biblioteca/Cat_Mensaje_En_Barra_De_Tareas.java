package Biblioteca;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
 
public class Cat_Mensaje_En_Barra_De_Tareas {

    public static PopupMenu crearMenu(){
        PopupMenu menu = new PopupMenu();
        MenuItem salir = new MenuItem("Salir");
        salir.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        menu.add(salir);
        return menu;
    }
    TrayIcon icono = new TrayIcon(Toolkit.getDefaultToolkit().getImage("imagen/Lista.png"),"Aviso Para Tareas Pendientes En Scoi",crearMenu());
    
    hilo seg = new hilo();
	public Cat_Mensaje_En_Barra_De_Tareas() throws AWTException {
        icono.setImageAutoSize(true);
        
		SystemTray.getSystemTray().add(icono);
      
	      icono.addActionListener(new ActionListener(){
	          @Override
	          public void actionPerformed(ActionEvent e){
	        	  
//     		   	  System.out.print(icono.);
	              JOptionPane.showMessageDialog(null, "Cambiar OptionPane Por Ventana", "Atencion!", JOptionPane.INFORMATION_MESSAGE);
	          }
	      });
		seg.start();
	}
	
    public class hilo extends Thread {
		public void run() {
		    try {
		    	
			Thread.sleep(5000);
			
			icono.displayMessage("Aviso: Tienes Pendientes Por Resolver", "da click aquí para abrir ", TrayIcon.MessageType.INFO);
			
//			reconsultar+=1;
//			if(reconsultar==300)
//			{
//				reconsultar=0;
				run();
//			}
		} catch (InterruptedException e) {
			System.err.println("Error: "+ e.getMessage());
		}
		}
    }
    
    public static void main(String args[]) throws AWTException{
       new Cat_Mensaje_En_Barra_De_Tareas();

    }
}
