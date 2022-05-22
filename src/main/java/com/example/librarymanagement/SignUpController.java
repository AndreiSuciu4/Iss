package com.example.librarymanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import repository.RepositoryException;
import service.Service;
import validator.ValidatorException;

import java.io.IOException;
import java.util.Objects;

public class SignUpController {
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField cnpField;
    public TextField phoneNoField;
    public TextField usernameField;
    public PasswordField passwordField;
    public ImageView bookImage;
    private Service service;

    public void setService(Service service){
        this.service = service;
        Image image = new Image("file:images/bookImage.jpg");
        bookImage.setImage(image);
    }

    public void signUp(ActionEvent actionEvent) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String cnp = cnpField.getText();
        String phoneNo = phoneNoField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            service.addSubscriber(username, firstName, lastName, cnp, phoneNo, password);
            Util.showWarning("Success sign up", "Congratulation!");
            logIn(actionEvent);
        } catch (RepositoryException | ValidatorException e) {
            Util.showWarning("error", e.getMessage());
        }
    }

    public void logIn(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("loginView.fxml"));
        Parent root= null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LoginController loginController =
                loader.getController();
        loginController.setService(service);
        Scene scene = new Scene(Objects.requireNonNull(root), 681.0, 470);
        Stage stage;
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
}
