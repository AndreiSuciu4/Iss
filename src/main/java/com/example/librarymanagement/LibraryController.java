package com.example.librarymanagement;

import domain.Book;
import domain.Subscriber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import service.Service;

import java.util.ArrayList;

public class LibraryController {
    public TableColumn id;
    public TableColumn code;
    public TableColumn name;
    public TableColumn category;
    public TableColumn status;
    public TableView booksTable;
    public TableColumn author;
    public ImageView bookImage;
    private Service service;
    private Subscriber subscriber;

    public void setService(Service service){
        this.service = service;
        Image image = new Image("file:images/bookImage.jpg");
        bookImage.setImage(image);
        loadBooksTable();
    }

    public void setCurrentSubscriber(Subscriber subscriber){
        this.subscriber = subscriber;
    }

    private void loadBooksTable() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        code.setCellValueFactory(new PropertyValueFactory<>("code"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        Iterable<Book> trips = service.findAllBooks();
        System.out.println(trips);
        booksTable.setItems(loadTable(trips));


    }

    private ObservableList<BookModel> loadTable(Iterable<Book> books){
        ArrayList<BookModel> tripModelList = new ArrayList<>();
        for(Book book : books){
            tripModelList.add(new BookModel(book.getId().toString(), book.getCode(), book.getName(), book.getAuthor(), book.getCategory().toString(), book.getStatus().toString()));
        }
        return FXCollections.observableArrayList(tripModelList);
    }


}
