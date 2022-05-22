package com.example.librarymanagement;

import javafx.beans.property.SimpleStringProperty;

public class LibrarianBorrowingModel {
    private final SimpleStringProperty id;
    private final SimpleStringProperty username;
    private final SimpleStringProperty date;
    private final SimpleStringProperty status;

    public LibrarianBorrowingModel(String id, String username, String date, String status) {
        this.id = new SimpleStringProperty(id);
        this.username = new SimpleStringProperty(username);
        this.date = new SimpleStringProperty(date);
        this.status = new SimpleStringProperty(status);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}

