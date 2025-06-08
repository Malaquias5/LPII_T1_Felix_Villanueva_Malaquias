package com.alquiler.servlets;

import com.alquiler.dao.AlquilerDAO;
import com.alquiler.dao.ClienteDAO;
import com.alquiler.dao.DetalleAlquilerDAO;
import com.alquiler.dao.PeliculaDAO;
import com.alquiler.entidades.Alquiler;
import com.alquiler.entidades.Cliente;
import com.alquiler.entidades.DetalleAlquiler;
import com.alquiler.entidades.EstadoAlquiler;
import com.alquiler.entidades.Pelicula;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@WebServlet("/registrarAlquiler")
public class RegistrarAlquilerServlet extends HttpServlet {

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final PeliculaDAO peliculaDAO = new PeliculaDAO();
    private final AlquilerDAO alquilerDAO = new AlquilerDAO();
    private final DetalleAlquilerDAO detalleDAO = new DetalleAlquilerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Cliente> clientes = clienteDAO.listarTodos();
        List<Pelicula> peliculas = peliculaDAO.listarTodos();

        request.setAttribute("clientes", clientes);
        request.setAttribute("peliculas", peliculas);

        request.getRequestDispatcher("/WEB-INF/jsp/alquilerForm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int clienteId = Integer.parseInt(request.getParameter("clienteId"));
            int peliculaId = Integer.parseInt(request.getParameter("peliculaId"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));

            Cliente cliente = clienteDAO.buscarPorId(clienteId);
            Pelicula pelicula = peliculaDAO.buscarPorId(peliculaId);

            if (cliente == null || pelicula == null) {
                request.setAttribute("error", "Cliente o película no encontrados.");
                request.getRequestDispatcher("/WEB-INF/jsp/alquilerForm.jsp").forward(request, response);
                return;
            }

            // Crear el objeto Alquiler
            Alquiler alquiler = new Alquiler();
            alquiler.setCliente(cliente);
            alquiler.setFecha(new Date());
            alquiler.setEstado(EstadoAlquiler.ACTIVO); // puedes usar un enum si lo tienes
            alquiler.setTotal(BigDecimal.valueOf(pelicula.getPrecio() * cantidad));

            alquilerDAO.guardar(alquiler); // persistir y generar ID

            // Crear detalle
            DetalleAlquiler detalle = new DetalleAlquiler();
            detalle.setAlquiler(alquiler);
            detalle.setPelicula(pelicula);
            detalle.setCantidad(cantidad);

            detalleDAO.guardar(detalle);

            response.sendRedirect(request.getContextPath() + "/exito.jsp");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Datos inválidos en el formulario.");
            request.getRequestDispatcher("/WEB-INF/jsp/alquilerForm.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Ocurrió un error al registrar el alquiler.");
            request.getRequestDispatcher("/WEB-INF/jsp/alquilerForm.jsp").forward(request, response);
        }
    }
}
