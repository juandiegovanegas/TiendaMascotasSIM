
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

        //  Obtener parámetros del formulario
        String to = request.getParameter("destinario"); 
        String subject = request.getParameter("asunto"); 
        String messageText = request.getParameter("mensaje");
        
        
        

        //  Credenciales del remitente 
        final String username = "jvanegasmunoz569@gmail.com"; // correo
        final String password = "pgpy kbii vvnx vowa";       // contraseña 

        // Configuración del servidor SMTP (Gmail)
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Crear sesión
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        
        
       
        
  
        try {
            // Crear mensaje
        	Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));       // remitente
            message.addRecipient(Message.RecipientType.TO, 
                                 new InternetAddress(to));        // destinatario
            message.setSubject(subject);                          // asunto
            message.setText(messageText);                         // mensaje simple

           
            // Enviar mensaje
            Transport.send(message);

            
            // Respuesta del cliente
            response.getWriter().println("Correo enviado correctamente.");

        } catch (MessagingException e) {
            throw new ServletException("Error al enviar correo", e);
        }
        
        
        
        
    }
}
