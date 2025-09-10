
package Controller;

import java.io.IOException;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SvEnviarcorreos")
public class SvEnviarcorreos extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /* Obtener parámetros del formulario enviados por el cliente
    	 "to" que esto significa la direccion del correo electronico del destinatario
    	 "subject" que esto significa el asunto del correo
    	 "messageText" que esto viene siendo el cuerpo del correo*/
        String to = request.getParameter("destinario"); 
        String subject = request.getParameter("asunto"); 
        String messageText = request.getParameter("mensaje");
        
        
        

        //  Datos del remitente las cuales son el correo electronico y la contraseña
        final String username = "jvanegasmunoz569@gmail.com"; // correo
        final String password = "pgpy kbii vvnx vowa";       // contraseña 

        // Configuración del servidor SMTP (Gmail)
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); //habilitar auntentiación SMTP
        props.put("mail.smtp.starttls.enable", "true");// habilitar STARTTLS para cifrado
        props.put("mail.smtp.host", "smtp.gmail.com"); // servidor SMTP de gmail
        props.put("mail.smtp.port", "587"); // puerto SMTP para STARTTLS

        // Crear sesión con los datos del remitente
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        
        
       
        
  
        try {
            // Crear mensaje del correo
        	Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));       // remitente
            message.addRecipient(Message.RecipientType.TO, 
                                 new InternetAddress(to));        // destinatario
            message.setSubject(subject);                          // asunto
            message.setText(messageText);                         // mensaje simple

            
            // Enviar mensaje a traves del servidor SMTP ya configurado
            Transport.send(message);

            
            // Respuesta del cliente
            response.getWriter().println("Correo enviado correctamente.");

        } catch (MessagingException e) {
            throw new ServletException("Error al enviar correo", e);
        }
        
        
        
        
    }
}
