package com.example.librarymanagement;

import domain.Book;
import domain.ReturnStatus;
import domain.Subscriber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import repository.RepositoryException;
import service.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import java.util.ArrayList;

public class LibraryController implements Observer {
    public TableColumn<Object, Object> id;
    public TableColumn<Object, Object> code;
    public TableColumn<Object, Object> name;
    public TableColumn<Object, Object> category;
    public TableColumn<Object, Object> status;
    public TableView<BookModel> booksTable;
    public TableColumn<Object, Object> author;
    public ImageView bookImage;
    public TableView<BookModel> currentBooksTable;
    public TableColumn<Object, Object> currentid;
    public TableColumn<Object, Object> currentCode;
    public TableColumn<Object, Object> currentName;
    public TableColumn<Object, Object> currentAuthor;
    public TableColumn<Object, Object> currentCategory;
    public TableColumn<Object, Object> currentStatus;
    private Service service;
    private Subscriber subscriber;
    private final List<Integer> books = new ArrayList<>();
    private ObservableList<BookModel> observableList;

    public void setService(Service service){
        this.service = service;
        service.addObserver(this);
        Image image = new Image("file:images/bookImage.jpg");
        bookImage.setImage(image);
        loadBooksTables();
    }

    public void setCurrentSubscriber(Subscriber subscriber){
        this.subscriber = subscriber;
    }

    private void loadBooksTables() {
        setCellFactory(id, code, name, author, category, status);
        setCellFactory(currentid, currentCode, currentName, currentAuthor, currentCategory, currentStatus);
        Iterable<Book> books = service.findAllBooks();
        builtItemsBooksTable(books);
        booksTable.setItems(observableList);
    }

    private void setCellFactory(TableColumn<Object, Object> currentid, TableColumn<Object, Object> currentCode, TableColumn<Object, Object> currentName, TableColumn<Object, Object> currentAuthor, TableColumn<Object, Object> currentCategory, TableColumn<Object, Object> currentStatus) {
        currentid.setCellValueFactory(new PropertyValueFactory<>("id"));
        currentCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        currentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        currentAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        currentCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        currentStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void builtItemsBooksTable(Iterable<Book> books){
        ArrayList<BookModel> bookModelList = new ArrayList<>();
        for(Book book : books){
            bookModelList.add(new BookModel(book.getId().toString(), book.getCode(), book.getName(), book.getAuthor(), book.getCategory().toString(), book.getStatus().toString()));
        }
        this.observableList = FXCollections.observableArrayList(bookModelList);
    }


    public void borrowBooksClicked(ActionEvent actionEvent) {
        int subscriber = this.subscriber.getId();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(books.size() == 0){
            alert.setTitle("Add error");
            alert.setContentText("Please select a book");
            alert.show();
            return;
        }
        try {
            this.service.addBorrowing(subscriber, books, LocalDateTime.now(), ReturnStatus.no);
            this.currentBooksTable.getItems().clear();
            this.books.clear();
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Success borrowing");
            alert1.setContentText("Success borrowing");
            alert1.show();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    public void selectClicked(ActionEvent actionEvent) {
        ObservableList<BookModel> bookModels = booksTable.getSelectionModel().getSelectedItems();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(bookModels.isEmpty()){
            alert.setTitle("Add error");
            alert.setContentText("Please select a column from the table and press the select button");
            alert.show();
            return;
        }
        int id = Integer.parseInt(bookModels.get(0).getId());
        if(books.contains(id)){
            alert.setTitle("Add error");
            alert.setContentText("Already selected");
            alert.show();
            return;
        }
        books.add(id);
        currentBooksTable.getItems().add(bookModels.get(0));
    }

    public void returnClicked(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("returnView.fxml"));
        Parent root= null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ReturnController returnController = loader.getController();
        returnController.setCurrentSubscriber(subscriber);
        returnController.setService(service);
        Scene scene = new Scene(Objects.requireNonNull(root), 843.0, 470);
        Stage stage = new Stage();
        stage.setTitle("Return");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void update(Observable o, Object arg) {
        Iterable<Book> books = service.findAllBooks();
        builtItemsBooksTable(books);
        booksTable.getItems().clear();
        booksTable.setItems(observableList);
    }

    public void logoutClicked(ActionEvent actionEvent) {
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
