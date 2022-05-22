package domain;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class Book extends EntityObj {
    private String code;
    private String name;
    private String author;

    @Enumerated(EnumType.STRING)
    private BookCategory category;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    public Book(String code, String name, String author, BookCategory category, BookStatus status) {
        this.code = code;
        this.name = name;
        this.author = author;
        this.category = category;
        this.status = status;
    }

    public Book(){}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }
}
