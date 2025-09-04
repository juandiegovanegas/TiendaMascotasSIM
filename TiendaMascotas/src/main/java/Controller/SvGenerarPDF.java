package Controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

@WebServlet("/SvGenerarPDF")
public class SvGenerarPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		
		// Obtenemos la acción solicitada desde la URL
		
		String accion = request.getParameter("accion");

		// Configuración de la respuesta: tipo de contenido PDF y descarga automática
		if ("generarPDF".equals(accion)) {
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=ReporteClientes.pdf");

			
			// Crear un documento PDF
			
			try {
				Document document = new Document();
				PdfWriter.getInstance(document, response.getOutputStream());
				document.open();

				// Título
				Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
				Paragraph title = new Paragraph("Reporte de Animales Registrados", titleFont);
				title.setAlignment(Element.ALIGN_CENTER);
				title.setSpacingAfter(20);
				document.add(title);

				// Tabla
				PdfPTable table = new PdfPTable(3); // solo 3 columnas
				table.setWidthPercentage(100);
				table.setSpacingBefore(10f);
				table.setSpacingAfter(10f);

				String[] headers = { "id_tipo", "nombre", "observaciones" };
				for (String header : headers) {
					PdfPCell cell = new PdfPCell(new Phrase(header));
					cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}

				// Consulta a la base de datos
				Connection con = Conexion.conectarBD();
				String sql = "SELECT id_tipo, nombre, observaciones FROM tbltipo_mascotas";
				PreparedStatement pst = con.prepareStatement(sql);
				ResultSet rs = pst.executeQuery();

				
				// Agregar filas de la consulta a la tabla
				
				while (rs.next()) {
					table.addCell(String.valueOf(rs.getInt("id_tipo")));
					table.addCell(rs.getString("nombre"));
					table.addCell(rs.getString("observaciones"));
				}

				// Agregar tabla y cerrar
				document.add(table);
				document.close();

				// Cierra la conexion a la base de datos
				
				rs.close();
				pst.close();
				con.close();

				response.sendRedirect("http://localhost:8080/TiendaMascotas/Index.jsp");

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	}
}
