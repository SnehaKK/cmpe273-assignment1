package edu.sjsu.cmpe.library.dto;

import java.util.ArrayList;
import java.util.List;

public class BooksDto {
	
    private List<BookDto> books = new ArrayList<BookDto>();

    public void addBooks(BookDto book) {
	books.add(book);
    }

    /**
     * @return the books
     */
    public List<BookDto> getBooks() {
	return books;
    }

    /**
     * @param books
     *            
     */
    public void setBooks(List<BookDto> books) {
	this.books = books;
    }

}
