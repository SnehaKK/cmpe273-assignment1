package edu.sjsu.cmpe.library.repository;

import java.util.Collection;
import java.util.List;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;

/**
 * Book repository interface.
 * 
 * What is repository pattern?
 * 
 * @see http://martinfowler.com/eaaCatalog/repository.html
 */
public interface BookRepositoryInterface {
    /**
     * Save a new book in the repository
     * 
     * @param newBook
     *            a book instance to be create in the repository
     * @return a newly created book instance with auto-generated ISBN
     */
    Book saveBook(Book newBook);

    /**
     * Retrieve an existing book by ISBN
     * 
     * @param isbn
     *            a valid ISBN
     * @return a book instance
     */
    Book getBookByISBN(Long isbn);
    
    /**
     * Get all books in the library
     * 
     * @return all books in the library
     */
    
    Collection<Book> getAllBooks();

    // ToDo :Add other operations here
    
    /**
     * Save Review by book ISBN
     */
    Review saveReview(Long isbn, Review newReview);
    
    
    /**
     * View Review based on Review Id
     */
    Review viewReview(Long isbn,int reviewId);
    
    /**
     * View all reviews based on a book isbn
     */
    List<Review> viewAllReview(Long isbn);
    
    /**
     *  Delete an existing book by ISBN
     *  
     *  @param isbn
     *  @return void
     */
    void deleteBook(Long isbn);
    
    /**
     * Update an existing book. 
     * @param oldBook
     * @param isbn
     */
    void updateBook(Book oldBook, Long isbn);
    
    /**
     * View Author based on isbn and authorid
     */
    Author viewAuthor(Long isbn, int authorId);

    /**
     * View all reviews based on a book isbn
     */
    List<Author> viewAllAuthors(Long isbn);
    
}
