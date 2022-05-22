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

public class ReturnController implements Observer {
    public TableColumn<Object, Object> code;
    public TableColumn<Object, Object> name;
    public TableColumn<Object, Object> author;
    public TableColumn<Object, Object> category;
    public TableView<BookModel> borrowedBooksTable;
    public TableView<BorrowingModel> borrowingTable;
    public TableColumn<Object, Object> bookId;
    public TableColumn<Object, Object> borrowingId;
    public TableColumn<Object, Object> date;
    public TableColumn<Object, Object> status;
    public ImageView bookImage;
    private Service service;
    private Subscriber subscriber;

    public void setService(Service service){
        this.service = service;
        this.service.addObserver(this);
        Image image = new Image("file:images/bookImage.jpg");
        bookImage.setImage(image);
        loadBorrowingTable();
        borrowingTable.setRowFactory(borrowingModelTableView -> {
            TableRow<BorrowingModel> row = new TableRow<>() {
                @Override
                protected void updateItem(BorrowingModel item, boolean empty) {
                    super.updateItem(item, empty);
                }
            };
            row.setOnMouseClicked(me -> {
                if (me.getClickCount() == 1) { // Simple click
                    System.out.println(row.getItem().getId());
                    Iterable<Book> books = service.findBooksByBorrowing(Integer.parseInt(row.getItem().getId()));
                    borrowedBooksTable.getItems().clear();
                    borrowedBooksTable.setItems(fillBookTable(books));
                }
            });
            return row;
        });
    }

    public void setCurrentSubscriber(Subscriber subscriber){
        this.subscriber = subscriber;
    }

    private void loadBorrowingTable() {
        setCellFactoryBookTable(bookId, code, name, author, category);
        setCellFactoryBorrowingTable(borrowingId, date, status);

        Iterable<Borrowing> borrowings = service.findAllBorrowingsForASubscriber(subscriber.getId());
        borrowingTable.setItems(fillBorrowingTable(borrowings));
    }

    private void setCellFactoryBookTable(TableColumn<Object, Object> currentid, TableColumn<Object, Object> currentCode, TableColumn<Object, Object> currentName, TableColumn<Object, Object> currentAuthor, TableColumn<Object, Object> currentCategory) {
        currentid.setCellValueFactory(new PropertyValueFactory<>("id"));
        currentCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        currentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        currentAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        currentCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
    }

    private void setCellFactoryBorrowingTable(TableColumn<Object, Object>id, TableColumn<Object, Object> date, TableColumn<Object, Object> status) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }


    private ObservableList<BorrowingModel> fillBorrowingTable(Iterable<Borrowing> borrowings){
        ArrayList<BorrowingModel> borrowingModels = new ArrayList<>();
        for(Borrowing borrowing : borrowings){
            borrowingModels.add(new BorrowingModel(borrowing.getId().toString(), borrowing.getData().toString(), borrowing.getStatus().toString()));
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
        ObservableList<BorrowingModel> bookModels = borrowingTable.getSelectionModel().getSelectedItems();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(bookModels.isEmpty()){
            alert.setTitle("Add error");
            alert.setContentText("Please select a column from the table and press the return button");
            alert.show();
            return;
        }
        int id = Integer.parseInt(bookModels.get(0).getId());
        service.returnBooks(id);
    }

    @Override
    public void update(Observable o, Object arg) {
        Iterable<Borrowing> borrowings = service.findAllBorrowingsForASubscriber(subscriber.getId());
        borrowingTable.getItems().clear();
        borrowingTable.setItems(fillBorrowingTable(borrowings));
    }
}
