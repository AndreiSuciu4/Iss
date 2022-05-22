package com.example.librarymanagement;

import domain.Book;
import domain.Borrowing;
import domain.Subscriber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import service.Service;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class LibrarianController implements Observer {
    public TableColumn<Object, Object> code;
    public TableColumn<Object, Object> name;
    public TableColumn<Object, Object> author;
    public TableColumn<Object, Object> category;
    public TableView<BookModel> borrowedBooksTable;
    public TableView<LibrarianBorrowingModel> borrowingTable;
    public TableColumn<Object, Object> bookId;
    public TableColumn<Object, Object> borrowingId;
    public TableColumn<Object, Object> date;
    public TableColumn<Object, Object> status;
    public TableColumn username;
    private Service service;
    public ImageView bookImage;

    public void setService(Service service){
        this.service = service;
        this.service.addObserver(this);
        Image image = new Image("file:images/bookImage.jpg");
        bookImage.setImage(image);
        loadBorrowingTable();

        this.borrowingTable.setRowFactory(librarianBorrowingModelTableView -> {
            TableRow<LibrarianBorrowingModel> row = new TableRow<LibrarianBorrowingModel>() {
                @Override
                protected void updateItem(LibrarianBorrowingModel item, boolean empty) {
                    super.updateItem(item, empty);
                }
            };
            row.setOnMouseClicked(me -> {
                if (me.getClickCount() == 1) { // Simple click
                    Iterable<Book> books = service.findBooksByBorrowing(Integer.parseInt(row.getItem().getId()));
                    borrowedBooksTable.getItems().clear();
                    borrowedBooksTable.setItems(fillBookTable(books));
                }
            });
            return row;
        });
    }


    private void loadBorrowingTable() {
        setCellFactoryBookTable(bookId, code, name, author, category);
        setCellFactoryBorrowingTable(borrowingId, username, date, status);

        Iterable<Borrowing> borrowings = service.findAllBorrowingsPendingStatus();
        borrowingTable.setItems(fillBorrowingTable(borrowings));
    }

    private void setCellFactoryBookTable(TableColumn<Object, Object> currentid, TableColumn<Object, Object> currentCode, TableColumn<Object, Object> currentName, TableColumn<Object, Object> currentAuthor, TableColumn<Object, Object> currentCategory) {
        currentid.setCellValueFactory(new PropertyValueFactory<>("id"));
        currentCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        currentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        currentAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        currentCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
    }

    private void setCellFactoryBorrowingTable(TableColumn<Object, Object>id, TableColumn<Object, Object>username, TableColumn<Object, Object> date, TableColumn<Object, Object> status) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }


    private ObservableList<LibrarianBorrowingModel> fillBorrowingTable(Iterable<Borrowing> borrowings){
        ArrayList<LibrarianBorrowingModel> borrowingModels = new ArrayList<>();
        for(Borrowing borrowing : borrowings){
            borrowingModels.add(new LibrarianBorrowingModel(borrowing.getId().toString(), service.findSubscriberById(borrowing.getSubscriber()).getUsername(), borrowing.getData().toString(), borrowing.getStatus().toString()));
        }
        return FXCollections.observableArrayList(borrowingModels);
    }

    private ObservableList<BookModel> fillBookTable(Iterable<Book> books){
        ArrayList<BookModel> bookModelList = new ArrayList<>();
        for(Book book : books){
            bookModelList.add(new BookModel(book.getId().toString(), book.getCode(), book.getName(), book.getAuthor(), book.getCategory().toString(), book.getStatus().toString()));
        }
        return FXCollections.observableArrayList(bookModelList);
    }

    public void returnClicked(ActionEvent actionEvent) {
        ObservableList<LibrarianBorrowingModel> librarianBorrowingModels = borrowingTable.getSelectionModel().getSelectedItems();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(librarianBorrowingModels.isEmpty()){
            alert.setTitle("Add error");
            alert.setContentText("Please select a column from the table and press the accept return button");
            alert.show();
            return;
        }
        int id = Integer.parseInt(librarianBorrowingModels.get(0).getId());
        service.acceptReturn(id);
    }

    public void rejectClicked(ActionEvent actionEvent) {
        ObservableList<LibrarianBorrowingModel> librarianBorrowingModels = borrowingTable.getSelectionModel().getSelectedItems();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(librarianBorrowingModels.isEmpty()){
            alert.setTitle("Add error");
            alert.setContentText("Please select a column from the table and press the reject return button");
            alert.show();
            return;
        }
        int id = Integer.parseInt(librarianBorrowingModels.get(0).getId());
        service.rejectReturn(id);
    }

    @Override
    public void update(Observable o, Object arg) {
        Iterable<Borrowing> borrowings = service.findAllBorrowingsPendingStatus();
        this.borrowingTable.getItems().clear();
        borrowingTable.setItems(fillBorrowingTable(borrowings));
    }
}

