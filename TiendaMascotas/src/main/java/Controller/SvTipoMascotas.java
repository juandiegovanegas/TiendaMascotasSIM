package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import com.sun.net.httpserver.Request;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;

import Model.TipoMascota;
import Model.TipoMascotaDAO;

@WebServlet("/SvTipoMascotas")
public class SvTipoMascotas extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** DAO para interactuar con la base de datos de Tipos de Mascotas */
	
	TipoMascotaDAO tmDao = new TipoMascotaDAO();

	/**
	 * Constructor por defecto del servlet
	 */
	
	public SvTipoMascotas() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Acción LISTAR: muestra todos los tipos de mascotas
		
		
		String accion = request.getParameter("accion");

		if ("listar".equals(accion)) {
			List<TipoMascota> lista = tmDao.listar();
			request.setAttribute("tipomascota", lista);
			request.getRequestDispatcher("View/Listar.jsp").forward(request, response);
		}
		
		// Acción CREAR: redirige al formulario de creación
		else if ("crear".equals(accion)) {
            request.getRequestDispatcher("View/Crear.jsp").forward(request, response);
        }
		
		// Acción EDITAR: carga datos en el formulario de edición
		else if ("editar".equals(accion)) {
		    String id = request.getParameter("id_tipo");

		    if (id != null && !id.isEmpty()) {
		        TipoMascota tm = tmDao.buscarPorId(Integer.parseInt(id)); 
		        request.setAttribute("tipomascota", tm);
		        request.getRequestDispatcher("View/Editar.jsp").forward(request, response);
		    } else {
		    	
		    	 // Si no hay id válido, vuelve a la lista
		    	
		        response.sendRedirect("SvTipoMascotas?accion=listar");
		    }
		
		}
		
		// Acción ELIMINAR: borra un tipo de mascota por id
		else if ("eliminar".equals(accion)) {
		    String id = request.getParameter("id");
		    System.out.println(id);
		    if (id != null && !id.isEmpty()) {
		        tmDao.delete(Integer.parseInt(id));
		    }
		    response.sendRedirect("SvTipoMascotas?accion=listar");
		}


		
		
		
		
	
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		// Acción CREATE: insertar nuevo registro en BD
		
		
		String accion = request.getParameter("accion");
		
		if (accion.equals("create")) {
            String nombre = request.getParameter("nombre");
            String observaciones = request.getParameter("observaciones");

            if (nombre != null && !nombre.isEmpty()) {
                TipoMascota tm = new TipoMascota();
                tm.setNombre(nombre);
                tm.setObservaciones(observaciones);

                tmDao.create(tm);
            } else {
                
            }

            
            response.sendRedirect("SvTipoMascotas?accion=listar");
        }
		
		// Acción EDITAR: actualizar registro existente
		
		else if ("editar".equals(accion)) {
	        
	        String id = request.getParameter("id_tipo");
	        String nombre = request.getParameter("nombre");
	        String observaciones = request.getParameter("observaciones");

	        if (id != null && !id.isEmpty() && nombre != null && !nombre.isEmpty()) {
	            TipoMascota tm = new TipoMascota();
	            tm.setId_tipo(Integer.parseInt(id));
	            tm.setNombre(nombre);
	            tm.setObservaciones(observaciones);

	            tmDao.update(tm); 
	        }

	        response.sendRedirect("SvTipoMascotas?accion=listar");
	    }
		
		

		
	}
	
	
	
	
	
}
