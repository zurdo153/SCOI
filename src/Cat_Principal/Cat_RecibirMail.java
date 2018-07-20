package Cat_Principal;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

public class Cat_RecibirMail{
 
    public static void main(String[] args){
    	String de="scoi.gastos@gmail.com";
		String clave="Ragazi/*-1";
		
//		if(correo.equals("Servicios")) {
//		 de="scoi.izagar@gmail.com";
//		 clave="Ragazi/*-1";
//		}
//		
//		if(correo.equals("Gastos")) {
//		 de=
//		 clave=
//		}
      // Se obtiene la Session
        Properties prop = new Properties();
        prop.setProperty("mail.pop3.starttls.enable", "false");
        prop.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.pop3.socketFactory.fallback", "false");
        prop.setProperty("mail.pop3.port", "995");
        prop.setProperty("mail.pop3.socketFactory.port", "995");
        Session sesion = Session.getInstance(prop);
        // sesion.setDebug(true);

        try
        {
          // Se obtiene el Store y el Folder, para poder leer el
          // correo.
            Store store = sesion.getStore("pop3");
            store.connect("pop.gmail.com", de, clave);
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            // Se obtienen los mensajes.
            Message[] mensajes = folder.getMessages();

            // Se escribe from y subject de cada mensaje
            for (int i = 0; i < mensajes.length; i++){
            	
                
//                String sCadena = "Hola Mundo";
//                String sSubCadena = sCadena.substring(5,10);
//                System.out.println(sSubCadena);
                
                System.out.println(mensajes[i].getFrom()[0].toString()+"\n"+mensajes[i].getSubject());
                
                System.out.println("dATO:"+mensajes[i].getContent());
                
                analizaParteDeMensaje(mensajes[i]);
                
                System.out.println("mensaje>>>"+mensaje+"<<<fin mensaje");
                
            }

            folder.close(false);
            store.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    static String mensaje="";
    private static void analizaParteDeMensaje(Part unaParte){
    	try{
//           Si es multipart, se analiza cada una de sus partes recursivamente.
            if (unaParte.isMimeType("multipart/*")){
                Multipart multi;
                multi = (Multipart) unaParte.getContent();

                for (int j = 0; j < multi.getCount(); j++)
                {  analizaParteDeMensaje(multi.getBodyPart(j));    }
            }
            else{
              // Si es texto, se escribe el texto.
                if (unaParte.isMimeType("text/*")){
                    System.out.println("\n§"+unaParte.getContent()+"§");
                    mensaje="§"+unaParte.getContent();
                }
                
                else{
                  // Si es imagen, se guarda en fichero y se visualiza en JFrame
                    if (unaParte.isMimeType("image/*"))
                    {
                        System.out.println("Imagen " + unaParte.getContentType());
                        System.out.println("Fichero=" + unaParte.getFileName());

//                        salvaImagenEnFichero(unaParte);
//                        visualizaImagenEnJFrame(unaParte);
                    }
                    else{ // Si no es ninguna de las anteriores, se escribe en pantalla
                          // el tipo.
                          System.out.println("Recibido " + unaParte.getContentType());
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

//    /**
//     * Presupone que unaParte es una foto adjunta a un correo.
//     * Recoge la imagen y la visualiza en un JFrame
//     * @param unaParte Parte de un correo correspondiente a una imagen.
//     */
//    private static void visualizaImagenEnJFrame(Part unaParte)
//        throws IOException, MessagingException
//    {
//        JFrame v = new JFrame();
//        ImageIcon icono = new ImageIcon(
//                ImageIO.read(unaParte.getInputStream()));
//        JLabel l = new JLabel(icono);
//        v.getContentPane().add(l);
//        v.pack();
//        v.setVisible(true);
//    }
//
//    /**
//     * Supone que unaParte corresponde a una imagen de un fichero y que
//     * getFileName() esta relleno.
//     * Salva la imagen en d:\getFileName().
//      * @param unaParte Parte de un correo correspondiente a una imagen.
//     */
//    private static void salvaImagenEnFichero(Part unaParte)
//        throws FileNotFoundException, MessagingException, IOException
//    {
//        FileOutputStream fichero = new FileOutputStream(
//                "d:/" + unaParte.getFileName());
//        InputStream imagen = unaParte.getInputStream();
//        byte[] bytes = new byte[1000];
//        int leidos = 0;
//
//        while ((leidos = imagen.read(bytes)) > 0)
//        {
//            fichero.write(bytes, 0, leidos);
//        }
//    }
}
