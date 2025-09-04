package Model;

import javax.swing.JOptionPane;

import Controller.Conexion;

public class test {

	public static void main(String[] args) {
		
		 // Crear instancia de la clase Conexion
		
		Conexion test = new Conexion();
		
		 // Intentar conectarse a la base de datos
		
		if (test.conectarBD() != null) {
			
			 // Conexión exitosa
			
			JOptionPane.showMessageDialog(null, "Conectado a base datos");
			
			
			
		} else {
			
			 // Conexión fallida
			JOptionPane.showConfirmDialog(null, "no conectado a la BD");
		}

	}

}
