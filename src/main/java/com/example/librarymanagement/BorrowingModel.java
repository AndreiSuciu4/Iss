package com.example.librarymanagement;

import javafx.beans.property.SimpleStringProperty;

public class BorrowingModel {
    private final SimpleStringProperty id;
    private final SimpleStringProperty date;
    private final SimpleStringProperty status;

    public BorrowingModel(String id, String date, String status) {
        this.id = new SimpleStringProperty(id);
        this.date = new SimpleStringProperty(date);
        this.status = new SimpleStringProperty(status);
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

