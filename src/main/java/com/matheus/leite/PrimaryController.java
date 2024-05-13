package com.matheus.leite;

import java.io.IOException;
import java.sql.SQLException;

import com.matheus.leite.data.LoginDao;
import com.matheus.leite.model.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PrimaryController {

    @FXML
    TextField loginField;

    @FXML
    PasswordField passField;

    @FXML
    Button loginBtn;

    @FXML
    Button closeWindowBtn;

    private Parent root;

    public void initialize() {

    }

    public void closeWindow() {
        System.exit(0);
    }

    public void login() throws SQLException, IOException {
        String username = loginField.getText();
        String password = passField.getText();

        
        var loginDao = new LoginDao();
        User user = loginDao.authenticate(username, password);
        
        if (user != null) {
            mostrarMensagem("Sucesso", "Login realizado com sucesso:");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("secondary.fxml"));
            root = loader.load();
            DashboardController dashboardController = loader.getController();
            dashboardController.displayUsername(user.getUsername());
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 1100, 600));
            stage.show();

        } else {
            mostrarMensagem("Erro", "Erro ao autenticar");
        }

        loginField.setText("");
        passField.setText("");
    }

    private void mostrarMensagem(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(titulo);
        alert.setContentText(mensagem);
        alert.show();
    }
}
