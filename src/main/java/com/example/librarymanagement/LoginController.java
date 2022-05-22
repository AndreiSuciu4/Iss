package com.example.librarymanagement;

import domain.Subscriber;
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
import service.Service;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    public TextField usernameField;
    public PasswordField passwordField;
    public ImageView bookImage;
    private Service service;

    public void setService(Service service){
        this.service = service;
        Image image = new Image("file:images/bookImage.jpg");
        bookImage.setImage(image);
    }

    public void logIn(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Subscriber subscriber = service.findSubscriberByUsernameAndPassword(username, password);
        if(subscriber == null){
            Util.showWarning("error", "invalid username or password!");
            return;
        }
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("libraryView.fxml"));
        Parent root= null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LibraryController libraryController =
                loader.getController();
        libraryController.setService(service);
        libraryController.setCurrentSubscriber(subscriber);
        Scene scene = new Scene(Objects.requireNonNull(root), 1171, 470);
        Stage stage;
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Library");
        stage.setScene(scene);
        stage.show();

    }

    public void signUp(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("signUpView.fxml"));
        Parent root= null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SignUpController signUpController =
                loader.getController();
        signUpController.setService(service);
        Scene scene = new Scene(Objects.requireNonNull(root), 681.0, 470);
        Stage stage;
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Sign up");
        stage.setScene(scene);
        stage.show();
    }
}
