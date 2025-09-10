package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import Controller.Conexion;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;

public class TipoMascotaDAO {

	
	 /* Obtiene todos los tipos de mascota desde la base de datos.
	 *
	 * Ejecuta la consulta  SELECT * FROM tbltipo_mascotas, 
	 * mapea los resultados a objetos de TipoMascota y los 
	 * agrega a una lista.
	 *
	 * retorna lista de TipoMascota, o vacía si no hay resultados
	 *         o ocurre un error.
	 */
	
	public List<TipoMascota> listar() {
		List<TipoMascota> lista = new ArrayList<>();
		String sql = "SELECT * FROM tbltipo_mascotas";

		try (Connection con = Conexion.conectarBD();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				TipoMascota tm = new TipoMascota();
				tm.setId_tipo(rs.getInt("id_tipo"));
				tm.setNombre(rs.getString("nombre"));
				tm.setObservaciones(rs.getString("observaciones"));
				lista.add(tm);
			}

		} catch (SQLException e) {
			System.err.println("Error al listar tipos de mascota: " + e.getMessage());
		}

		return lista;
	}

	/**
	 * Inserta un nuevo tipo de mascota en la base de datos.
	 *
	 * Ejecuta un  INSERT en la tabla {@code tbltipo_mascotas} 
	 * usando los datos del objeto TipoMascota}.
	 * 
	 * objeto TipoMascota con el nombre y observaciones a guardar.
	 * el ServletException si ocurre un error en la ejecución del servlet.
	 */
	public void create(TipoMascota m) throws ServletException {
		String sql = "INSERT INTO tbltipo_mascotas (nombre,observaciones) VALUES (?, ?)";
		try (Connection con = Conexion.conectarBD(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, m.getNombre());
			ps.setString(2, m.getObservaciones());
			ps.executeUpdate();
			crearmascota("ATENCION!", "se ha creado una mascota nueva");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());

			

		}
	}

	
	/*Aca hacemos cada que alguien inserte una mascota nueva en la base 
	 * de datos nos envie un correo informando que ha inscrito una nueva mascota*/
	
	public void crearmascota(String asunto, String mensaje) throws ServletException {

		final String username = "jvanegasmunoz569@gmail.com";
		final String password = "pgpy kbii vvnx vowa";
		final String receptor = "juandiegovanegasmunoz@gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
			message.setSubject(asunto);
			message.setText(mensaje);

			Transport.send(message);

			System.out.println("Correo enviado correctamente.");

		} catch (MessagingException e) {
			throw new ServletException("Error al enviar correo", e);
		}
	}

	/* Actualiza un tipo de mascota existente
	 * obtiendo los datos de la base de datos que podemos modificar son el nombre
	 * y las observaciones
	 * */
	public void update(TipoMascota m) throws ServletException {
		String sql = "UPDATE tbltipo_mascotas SET nombre = ?, observaciones = ? WHERE id_tipo = ?";
		try (Connection con = Conexion.conectarBD(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, m.getNombre());
			ps.setString(2, m.getObservaciones());
			ps.setInt(3, m.getId_tipo());
			ps.executeUpdate();
			System.out.println("Registro actualizado con éxito");

			actualizarmascota("ATENCION!", "se ha actualizado una mascota");

		} catch (SQLException e) {
			System.out.println("Error al actualizar: " + e.getMessage());
		}
	}

	/*Aca envia un correo cuando una mascota sea actualizado en la pagina
	 * nos enviara un correo notificando sobre este cambio */
	
	public void actualizarmascota(String asunto, String mensaje) throws ServletException {

		final String username = "jvanegasmunoz569@gmail.com";
		final String password = "pgpy kbii vvnx vowa";
		final String receptor = "juandiegovanegasmunoz@gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
			message.setSubject(asunto);
			message.setText(mensaje);

			Transport.send(message);

			System.out.println("Correo enviado correctamente.");

		} catch (MessagingException e) {
			throw new ServletException("Error al enviar correo", e);
		}
	}

	// Buscamos una mascota por su ID que esta registrada en la base de datos
	public TipoMascota buscarPorId(int id) {
		TipoMascota tm = null;
		String sql = "SELECT * FROM tbltipo_mascotas WHERE id_tipo = ?";
		try (Connection con = Conexion.conectarBD(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				tm = new TipoMascota();
				tm.setId_tipo(rs.getInt("id_tipo"));
				tm.setNombre(rs.getString("nombre"));
				tm.setObservaciones(rs.getString("observaciones"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tm;
	}

	// Eliminamos una mascota mediante su id que esta en la base de datos
	public void delete(int id) throws ServletException {
		String sql = "DELETE FROM tbltipo_mascotas WHERE id_tipo = ?";
		try (Connection con = Conexion.conectarBD(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.executeUpdate();
			System.out.println("Registro eliminado con éxito");

			eliminarmascota("ATENCION!", "se ha eliminado una mascota");

		} catch (SQLException e) {
			System.out.println("Error al eliminar: " + e.getMessage());
		}
	}

	
	//Aca nos enviaran un correo cuando una mascota sea eliminada mediante por su id
	public void eliminarmascota(String asunto, String mensaje) throws ServletException {

		final String username = "jvanegasmunoz569@gmail.com";
		final String password = "pgpy kbii vvnx vowa";
		final String receptor = "juandiegovanegasmunoz@gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
			message.setSubject(asunto);
			message.setText(mensaje);

			Transport.send(message);

			System.out.println("Correo enviado correctamente.");

		} catch (MessagingException e) {
			throw new ServletException("Error al enviar correo", e);
		}
	}

}
