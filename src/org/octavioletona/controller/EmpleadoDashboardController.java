/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.octavioletona.controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.octavioletona.model.Usuario;

public class EmpleadoDashboardController implements Initializable {

    @FXML private Label lblBienvenida;
    private Usuario usuarioActual;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void iniciarUsuario(Usuario usuario){
        this.usuarioActual = usuario;
        if (lblBienvenida != null && usuario != null) {
            lblBienvenida.setText("Bienvenido querido empleado " + usuario.getUsername());
        }
    }
}

