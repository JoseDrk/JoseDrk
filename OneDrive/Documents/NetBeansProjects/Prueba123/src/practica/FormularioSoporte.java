package practica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class FormularioSoporte extends javax.swing.JFrame {

    public FormularioSoporte() {
        initComponents();
        
        // Agregar eventos a los botones
        botonInsertar.addActionListener(e -> insertarRegistro());
        botonConsultar.addActionListener(e -> consultarRegistro());
        botonActualizar.addActionListener(e -> actualizarRegistro());
        botonEliminar.addActionListener(e -> eliminarRegistro());
    }

private void initComponents() {
    
    txtnom = new javax.swing.JTextField(20);
    txtcor = new javax.swing.JTextField(20);
    txtmen = new javax.swing.JTextArea(5, 20);
    botonInsertar = new javax.swing.JButton("Insertar");
    botonConsultar = new javax.swing.JButton("Consultar");
    botonActualizar = new javax.swing.JButton("Actualizar");
    botonEliminar = new javax.swing.JButton("Eliminar");

    // Configuración de propiedades de los componentes
    txtmen.setLineWrap(true);
    txtmen.setWrapStyleWord(true);
    javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(txtmen);  // Para hacer que el área de texto sea desplazable

    // Creación de etiquetas
    javax.swing.JLabel labelNombre = new javax.swing.JLabel("Nombre");
    javax.swing.JLabel labelCorreo = new javax.swing.JLabel("Correo");
    javax.swing.JLabel labelMensaje = new javax.swing.JLabel("Mensaje");

    // Configuración del layout
    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);

    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNombre)
                    .addComponent(labelCorreo)
                    .addComponent(labelMensaje)
                )
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtnom)
                    .addComponent(txtcor)
                    .addComponent(scrollPane)
                )
            )
            .addGroup(layout.createSequentialGroup()
                .addComponent(botonInsertar)
                .addComponent(botonConsultar)
                .addComponent(botonActualizar)
                .addComponent(botonEliminar)
            )
    );

    layout.setVerticalGroup(
        layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(labelNombre)
                .addComponent(txtnom)
            )
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(labelCorreo)
                .addComponent(txtcor)
            )
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(labelMensaje)
                .addComponent(scrollPane)
            )
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(botonInsertar)
                .addComponent(botonConsultar)
                .addComponent(botonActualizar)
                .addComponent(botonEliminar)
            )
    );

    pack();  // Ajusta el tamaño de la ventana automáticamente
}



    // Método de conexión a la base de datos
    private Connection conectar() throws Exception {
        String url = "jdbc:mysql://localhost:3306/Prueba";
        String usuario = "root";
        String contraseña = "123456";
        return DriverManager.getConnection(url, usuario, contraseña);
    }
    
    // Funciones CRUD
    
    // Función de inserción
    private void insertarRegistro() {
        try (Connection conexion = conectar()) {
            String query = "INSERT INTO soporte (nombre, correo, mensaje) VALUES (?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, txtnom.getText());
            statement.setString(2, txtcor.getText());
            statement.setString(3, txtmen.getText());
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registro insertado exitosamente.");
            limpiarCampos();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al insertar registro.");
        }
    }

    // Función de consulta
    private void consultarRegistro() {
        try (Connection conexion = conectar()) {
            String query = "SELECT * FROM soporte WHERE nombre = ?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, txtnom.getText());
            ResultSet resultado = statement.executeQuery();
            
            if (resultado.next()) {
                txtcor.setText(resultado.getString("correo"));
                txtmen.setText(resultado.getString("mensaje"));
                JOptionPane.showMessageDialog(this, "Registro encontrado.");
            } else {
                JOptionPane.showMessageDialog(this, "Registro no encontrado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al consultar registro.");
        }
    }

    // Función de actualización
    private void actualizarRegistro() {
        try (Connection conexion = conectar()) {
            String query = "UPDATE soporte SET correo = ?, mensaje = ? WHERE nombre = ?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, txtcor.getText());
            statement.setString(2, txtmen.getText());
            statement.setString(3, txtnom.getText());
            int filasActualizadas = statement.executeUpdate();

            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(this, "Registro actualizado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Registro no encontrado.");
            }
            limpiarCampos();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar registro.");
        }
    }

    // Función de eliminación
    private void eliminarRegistro() {
        try (Connection conexion = conectar()) {
            String query = "DELETE FROM soporte WHERE nombre = ?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, txtnom.getText());
            int filasEliminadas = statement.executeUpdate();

            if (filasEliminadas > 0) {
                JOptionPane.showMessageDialog(this, "Registro eliminado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Registro no encontrado.");
            }
            limpiarCampos();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar registro.");
        }
    }

    // Método para limpiar los campos después de cada operación
    private void limpiarCampos() {
        txtnom.setText("");
        txtcor.setText("");
        txtmen.setText("");
    }

    // Método principal (main)
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new FormularioSoporte().setVisible(true));
    }

    // Variables de la interfaz gráfica
    private javax.swing.JTextField txtnom;
    private javax.swing.JTextField txtcor;
    private javax.swing.JTextArea txtmen;
    private javax.swing.JButton botonInsertar;
    private javax.swing.JButton botonConsultar;
    private javax.swing.JButton botonActualizar;
    private javax.swing.JButton botonEliminar;
}






