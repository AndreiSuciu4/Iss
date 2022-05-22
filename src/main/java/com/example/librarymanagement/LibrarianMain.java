package com.example.librarymanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.bookRepository.BookRepository;
import repository.borrowingRepository.BorrowingRepository;
import repository.subcriberRepository.SubscriberRepository;
import service.Service;

import java.io.IOException;
import java.util.Properties;

public class LibrarianMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties properties = new Properties();
        try {
            properties.load(Main.class.getResourceAsStream("database.properties"));
            properties.list(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BookRepository bookRepository = new BookRepository();
        BorrowingRepository borrowingRepository = new BorrowingRepository();
        SubscriberRepository subscriberRepository = new SubscriberRepository();
        Service service = Service.getInstance();
        service.setRepo(bookRepository, subscriberRepository, borrowingRepository);
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("librarianView.fxml"));
        Parent root=loader.load();


        LibrarianController librarianController =
                loader.<LibrarianController>getController();
        librarianController.setService(service);
        Scene scene = new Scene(root, 930, 470);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Librarian View");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}
