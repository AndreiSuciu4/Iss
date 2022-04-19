package com.example.librarymanagement;

import javafx.beans.property.SimpleStringProperty;

public class BookModel {
    private final SimpleStringProperty id;
    private final SimpleStringProperty code;
    private final SimpleStringProperty name;
    private final SimpleStringProperty author;
    private final SimpleStringProperty category;
    private final SimpleStringProperty status;

    public BookModel(String id, String code, String name, String author, String category, String status) {
        this.id = new SimpleStringProperty(id);
        this.code = new SimpleStringProperty(code);
        this.name = new SimpleStringProperty(name);
        this.author = new SimpleStringProperty(author);
        this.category = new SimpleStringProperty(category);
        this.status = new SimpleStringProperty(status);
    }

    public String getAuthor() {
        return author.get();
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
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

    public String getCode() {
        return code.get();
    }

    public SimpleStringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCategory() {
        return category.get();
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
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
