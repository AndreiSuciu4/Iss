package com.example.librarymanagement;

import domain.Subscriber;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import service.Service;

import java.io.IOException;

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
        Subscriber subscriber = service.findByUsernameAndPassword(username, password);
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
                loader.<LibraryController>getController();
        libraryController.setService(service);
        libraryController.setCurrentSubscriber(subscriber);
        Scene scene = new Scene(root, 681.0, 470);
        Stage stage;
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Library");
        stage.setScene(scene);
        stage.show();

    }

    public void signUp(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("signUp.fxml"));
        Parent root= null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SignUpController signUpController =
                loader.<SignUpController>getController();
        signUpController.setService(service);
        Scene scene = new Scene(root, 681.0, 470);
        Stage stage;
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Sign up");
        stage.setScene(scene);
        stage.show();
    }
}
