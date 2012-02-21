package pl.mjedynak.equals.guava;

import com.google.common.base.Objects;

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
        return Objects.equal(this.author, other.author) && Objects.equal(this.title, other.title) && Objects.equal(this.pages, other.pages);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(author, title, pages);
    }
}
