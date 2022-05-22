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
        BookRepository bookRepository = new BookRepository();
//        Book book = new Book("3rda", "Moara cu noroc", "Ioan Slavici", BookCategory.novel, BookStatus.available);
//        bookRepository.add(book);
//        book = new Book("7aww", "Moara cu noroc", "Ioan Slavici", BookCategory.novel, BookStatus.available);
//        bookRepository.add(book);
//        book = new Book("5mhs", "Fantasy", "John Cena", BookCategory.fantasy, BookStatus.available);
//        bookRepository.add(book);
//        book = new Book("4pqe", "Baltagul", "Mihail Sadoveanu", BookCategory.novel, BookStatus.available);
//        bookRepository.add(book);
//        book = new Book("3mfg", "Testamen", "Tudor Arghezi", BookCategory.romance, BookStatus.available);
//        bookRepository.add(book);
//        book = new Book("7pwd", "Castelul", "Franz Kafka", BookCategory.mystery, BookStatus.available);
//        bookRepository.add(book);
//        book = new Book("4erf", "Enigma Otiliei", "George Calinescu", BookCategory.novel, BookStatus.available);
//        bookRepository.add(book);

        BorrowingRepository borrowingRepository = new BorrowingRepository();
        SubscriberRepository subscriberRepository = new SubscriberRepository();
        Service service = Service.getInstance();
        service.setRepo(bookRepository, subscriberRepository, borrowingRepository);
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("loginView.fxml"));
        Parent root=loader.load();


        LoginController logInController =
                loader.getController();
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
