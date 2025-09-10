package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	public static Connection conectarBD() {
		Connection connection = null;
		try {
			
			/* Es importante usar el driver "com.mysql.cj.jdbc.Driver" que corresponde a la versión moderna del conector MySQL,
			 compatible con MySQL 8.0 y versiones recientes.*/
			
			Class.forName("com.mysql.cj.jdbc.Driver");

		
			 /*Establecer la conexión con la base de datos MySQL
			usamos parametros de conexión como la url que es: jdbc:mysql,el codigo de localhost que es:3306,
		    el nombre de la base de datos que es bd_tienda_mascota, ,m 
			un usuario que es "root" que es el nombre del usuario de la base de datos
			 y la contraseña que es :"25562293"*/

			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/bd_tienda_mascotas?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
					"root", "2556229");

		} catch (ClassNotFoundException e) {
			System.out.println("No se encontró el driver JDBC: " + e.getMessage());
	     /* Esto puede suceder si el archivo JAR del conector MySQL no está incluido en el classpath del proyecto.
		 Se imprime un mensaje en consola indicando que no se pudo cargar el driver	*/
		
		} catch (SQLException e) {
		 System.out.println("Error al conectar a la base de datos: " + e.getMessage());
		/* Esto puede llegar a pasar si la base de datos esta relacionada con error, estos errores pueden incluir como 
		  credenciales incorrectas, URL mal formada, el servidor de la base de datos no esta disponibles, entre otros */
		 
		 
		  //Se imprimira un mensaje en consola con el error especifico//
		}
		
		//Finalmente aca retornara el objeto connection, que sera null si ocurrio alguna excepción
		return connection;
	}
}
