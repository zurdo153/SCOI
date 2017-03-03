package Cat_Principal;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSenderService {
 

		public boolean enviarcorreo(String para,int cantidad_de_correos,String mensaje,String asunto){
		boolean enviado = false;
		
		System.out.println(para);
		
		String de="scoi.izagar@gmail.com";
		String clave="Ragazi/*-1";
		
		try{

		String host="smtp.gmail.com";

		Properties prop=System.getProperties();

		prop.put("mail.smtp.starttls.enable","true");
		prop.put("mail.smtp.host",host);
		prop.put("mail.smtp.user",de);
		prop.put("mail.smtp.password",clave);
		prop.put("mail.smtp.port",587);
		prop.put("mail.smtp.auth","true");

		Session session=Session.getDefaultInstance(prop,null);
		MimeMessage message= new MimeMessage(session);

		message.setFrom(new InternetAddress(de));

		if(cantidad_de_correos==1){
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(para));
		}else{
		message.setRecipients(Message.RecipientType.TO, para );
		}
		
		message.setSubject(asunto);
		message.setText(mensaje);
		Transport transport=session.getTransport("smtp");
		transport.connect(host,de,clave);

		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		enviado=true;

		}catch (Exception e){
		e.printStackTrace();

		}
		return enviado;

		}

	};

