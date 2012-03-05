package pl.mjedynak.equals.java7;

import java.util.Objects;

public class Book {
    private String author;
    private String title;
    private int pages;

    public Book(String author, String title, int pages) {
        this.author = author;
        this.title = title;
        this.pages = pages;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        return Objects.equals(this.author, other.author) && Objects.equals(this.title, other.title) && Objects.equals(this.pages, other.pages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, title, pages);
    }

    @Override
    public String toString() {
        return "Book {author = " + Objects.toString(author) + ", title = " + Objects.toString(title) + ", pages = " + Objects.toString(pages) + "}";
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
