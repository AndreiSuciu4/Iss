package com.example.librarymanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.bookRepository.BookRepository;
import repository.subcriberRepository.SubscriberRepository;
import service.Service;

import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties properties = new Properties();
        try {
            properties.load(Main.class.getResourceAsStream("database.properties"));
            properties.list(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BookRepository bookDbRepository = new BookRepository(properties);
        SubscriberRepository subscriberDbRepository = new SubscriberRepository(properties);
        Service service = new Service(bookDbRepository, subscriberDbRepository);
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("loginView.fxml"));
        Parent root=loader.load();


        LoginController logInController =
                loader.<LoginController>getController();
        logInController.setService(service);
        Scene scene = new Scene(root, 681.0, 470);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
