/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.octavioletona.system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Octavio Letona
 */
public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        //convertir .fxml en nodo raiz
        Parent raiz = FXMLLoader.load(
                getClass().getResource("/org/octavioletona/view/InicioSesionView.fxml"));        
        Scene escena = new Scene(raiz);
        
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }    
}
