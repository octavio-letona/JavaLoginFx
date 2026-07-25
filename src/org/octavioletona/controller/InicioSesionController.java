package org.octavioletona.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.octavioletona.dao.UsuarioDAO;
import org.octavioletona.model.Usuario;
import org.octavioletona.util.SecurityUtil;

public class InicioSesionController implements Initializable {

    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnIniciarSesion;
    @FXML
    private Label lblMensaje;

    private UsuarioDAO usuarioDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioDAO = new UsuarioDAO();
        if (lblMensaje != null) {
            lblMensaje.setText("");
        }
    }

    @FXML
    public void eventoInicioSesion(ActionEvent evento) {
        String usuario = txtUsuario != null ? txtUsuario.getText() : "";
        String password = txtPassword != null ? txtPassword.getText() : "";

        // Verificación si los datos están vacíos
        if (usuario.trim().isEmpty() || password.trim().isEmpty()) {
            if (lblMensaje != null) {
                lblMensaje.setText("Por favor, complete todos sus datos.");
            }
            return;
        }

        // Datos completos
        String passwordHash = SecurityUtil.hashSHA256(password);
        
        // Llamar al DAO para iniciar sesión
        Usuario usuarioIniciado = usuarioDAO.iniciarSesion(usuario.trim(), passwordHash);
        
        if (usuarioIniciado != null) {
            if (lblMensaje != null) {
                lblMensaje.setText("Inicio correcto");
            }
            abrirDashboard(usuarioIniciado);
        } else {
            if (lblMensaje != null) {
                lblMensaje.setText("Usuario o contraseña incorrectos");
            }
        }
    }

    private void abrirDashboard(Usuario usuario) {
        if (usuario == null || usuario.getRol() == null) {
            if (lblMensaje != null) {
                lblMensaje.setText("Error: Rol de usuario no válido.");
            }
            return;
        }

        String rutaFXML = "";
        String tituloDashboard = "";

        switch (usuario.getRol().toLowerCase()) {
            case "admin":
                rutaFXML = "/org/octavioletona/view/AdminDashboradView.fxml";
                tituloDashboard = "Panel de Administración";
                break;
            case "empleado":
                if (lblMensaje != null) {
                    lblMensaje.setText("Vista de empleado no disponible aún.");
                }
                return;
            default:
                if (lblMensaje != null) {
                    lblMensaje.setText("Rol no reconocido: " + usuario.getRol());
                }
                return;
        }
        
        try {
            FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent raiz = cargadorFXML.load();
            
            AdminDashboradController controlado = cargadorFXML.getController();
            if (controlado != null) {
                controlado.iniciarUsuario(usuario);            
            }
            
            Stage escenario = new Stage();
            escenario.setScene(new Scene(raiz));
            escenario.setTitle(tituloDashboard);
            escenario.show();
            
            if (btnIniciarSesion != null && btnIniciarSesion.getScene() != null) {
                Stage escenaActual = (Stage) btnIniciarSesion.getScene().getWindow();
                if (escenaActual != null) {
                    escenaActual.close();
                }
            }
            
        } catch (IOException e) {
            System.err.println("Error al cargar la vista: " + rutaFXML + " - " + e.getMessage());
            e.printStackTrace();
            if (lblMensaje != null) {
                lblMensaje.setText("Error interno al abrir la vista.");
            }
        }
    }
}
