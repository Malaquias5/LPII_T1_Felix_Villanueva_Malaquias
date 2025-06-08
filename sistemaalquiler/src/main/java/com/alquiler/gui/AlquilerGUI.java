package com.alquiler.gui;

import com.alquiler.dao.AlquilerDAO;
import com.alquiler.dao.ClienteDAO;
import com.alquiler.dao.PeliculaDAO;
import com.alquiler.entidades.Alquiler;
import com.alquiler.entidades.Cliente;
import com.alquiler.entidades.EstadoAlquiler;
import com.alquiler.entidades.Pelicula;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class AlquilerGUI extends JFrame {

    private ClienteDAO clienteDAO = new ClienteDAO();
    private PeliculaDAO peliculaDAO = new PeliculaDAO();
    private AlquilerDAO alquilerDAO = new AlquilerDAO();

    private JComboBox<Cliente> cmbClientes;
    private JComboBox<Pelicula> cmbPeliculas;
    private JTextField txtCantidad;
    private JLabel lblMensaje;

    public AlquilerGUI() {
        setTitle("Alquiler de Películas");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JLabel lblCliente = new JLabel("Seleccionar Cliente:");
        cmbClientes = new JComboBox<>();
        JLabel lblPelicula = new JLabel("Seleccionar Película:");
        cmbPeliculas = new JComboBox<>();
        JLabel lblCantidad = new JLabel("Cantidad:");
        txtCantidad = new JTextField(10);
        JButton btnRegistrar = new JButton("Registrar Alquiler");
        lblMensaje = new JLabel();

        cargarClientes();
        cargarPeliculas();

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarAlquiler();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 5, 5));
        panel.add(lblCliente);
        panel.add(cmbClientes);
        panel.add(lblPelicula);
        panel.add(cmbPeliculas);
        panel.add(lblCantidad);
        panel.add(txtCantidad);
        panel.add(new JLabel());
        panel.add(btnRegistrar);
        panel.add(new JLabel("Mensaje:"));
        panel.add(lblMensaje);

        add(panel);
    }

    private void cargarClientes() {
        List<Cliente> clientes = clienteDAO.listarTodos();
        for (Cliente c : clientes) {
            cmbClientes.addItem(c);
        }
    }

    private void cargarPeliculas() {
        List<Pelicula> peliculas = peliculaDAO.listarTodos();
        for (Pelicula p : peliculas) {
            cmbPeliculas.addItem(p);
        }
    }

    private void registrarAlquiler() {
        Cliente cliente = (Cliente) cmbClientes.getSelectedItem();
        Pelicula pelicula = (Pelicula) cmbPeliculas.getSelectedItem();
        String cantidadTexto = txtCantidad.getText();

        if (cliente == null || pelicula == null || cantidadTexto.isEmpty()) {
            lblMensaje.setText("Por favor, complete todos los campos.");
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantidadTexto);
            BigDecimal total = calcularTotal(cantidad, pelicula.getPrecio());

            Alquiler alquiler = new Alquiler();
            alquiler.setCliente(cliente);
            alquiler.setFecha(new Date());
            alquiler.setEstado(EstadoAlquiler.ACTIVO);
            alquiler.setTotal(total);

            alquilerDAO.guardar(alquiler);
            lblMensaje.setText("Alquiler registrado con éxito.");
        } catch (NumberFormatException ex) {
            lblMensaje.setText("La cantidad debe ser un número.");
        }
    }

    private BigDecimal calcularTotal(int cantidad, double precio) {
        return BigDecimal.valueOf(cantidad * precio);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AlquilerGUI().setVisible(true);
        });
    }
}
