package edu.sjsu.cmpe.library.repository;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;

public class BookRepository implements BookRepositoryInterface 
{
	/** In-memory map to store books. (Key, Value) -> (ISBN, Book) */
	private final ConcurrentHashMap<Long, Book> bookInMemoryMap;
	
	/** In-memory map to store authors. (Key, Value) -> (ISBN, Author) */
	private final ConcurrentHashMap<Long, Author> authorInMemoryMap;
	
	/** In-memory map to store books. (Key, Value) -> (ISBN, Review) */
	private final ConcurrentHashMap<Long, Review> reviewInMemoryMap;
	

	/** Never access this key directly; instead use generateISBNKey() */
	private long isbnKey;

	/** Never to be used directly. Use generateAuthorId() instead**/
	private int authorKey;
	
	/** Never to be used directly. Use generateReviewId() instead**/
	private int reviewKey;
	
	public BookRepository(ConcurrentHashMap<Long, Book> bookMap, ConcurrentHashMap<Long, Author> authorMap, ConcurrentHashMap<Long,Review> reviewMap) {
		checkNotNull(bookMap, "bookMap must not be null for BookRepository");
		checkNotNull(authorMap, "bookMap must not be null for BookRepository");
		checkNotNull(reviewMap, "bookMap must not be null for BookRepository");
		bookInMemoryMap = bookMap;
		authorInMemoryMap = authorMap;
		reviewInMemoryMap = reviewMap;
		isbnKey = 0;
		authorKey = 0;
		reviewKey = 0;
	}
	

	/**
	 * This should be called if and only if you are adding new books to the
	 * repository.
	 * 
	 * @return a new incremental ISBN number
	 */
	private final Long generateISBNKey() {
		// increment existing isbnKey and return the new value
		return Long.valueOf(++isbnKey);
	}

	/**
	 * This should be called if and only if you are adding new authors to the
	 * repository.
	 * 
	 * @return a new incremental Author Id
	 */
	private final int generateAuthorKey() {
		// increment existing isbnKey and return the new value
		return Integer.valueOf(++authorKey);
	}
	
	/**
	 * This should be called if and only if you are adding new books to the
	 * repository.
	 * 
	 * @return a new incremental ISBN number
	 */
	private final int generateReviewKey() {
		// increment existing isbnKey and return the new value
		return Integer.valueOf(++reviewKey);
	}
	
	/**
	 * This will update an existing book.
	 * @param oldBook
	 * @return
	 */
	@Override
	public void updateBook(Book oldBook, Long isbn){
		bookInMemoryMap.put(isbn, oldBook);
	}
	
	
	/**
	 * This will auto-generate unique ISBN for new books.
	 */
	@Override
	public Book saveBook(Book newBook) {
		checkNotNull(newBook, "newBook instance must not be null");
		// Generate new ISBN
		Long isbn = generateISBNKey();
		newBook.setIsbn(isbn);
		
		
		List<Author> authorList= newBook.getAuthors();
		for (int i = 0; i < authorList.size(); i++) {
			Author a= authorList.get(i);
			int id= generateAuthorKey();
			a.setAuthorId(id);
			authorInMemoryMap.putIfAbsent(isbn, a);
		}
		
		List<Review> reviewList= newBook.getReviews();
		for (int i = 0; reviewList != null && i < reviewList.size(); i++) {
			Review r= reviewList.get(i);
			int id= generateReviewKey();
			r.setReviewId(id);
			reviewInMemoryMap.putIfAbsent(isbn, r);
		}
		
		// Finally, save the new book into the map
		bookInMemoryMap.putIfAbsent(isbn, newBook);

		return newBook;
	}

	/**
	 * @see edu.sjsu.cmpe.library.repository.BookRepositoryInterface#getBookByISBN(java.lang.Long)
	 */
	@Override
	public Book getBookByISBN(Long isbn)
	{
		checkArgument(isbn > 0,
				"ISBN was %s but expected greater than zero value", isbn);
		return bookInMemoryMap.get(isbn);
	}

	/**
	 * Collect all the books
	 */
	@Override
	public Collection<Book> getAllBooks() 
	{
		return bookInMemoryMap.values();
	}


	/**
	 * Save Review by ISBN
	 */
	@Override
	public Review saveReview(Long isbn, Review newReview) {
		Book book= getBookByISBN(isbn);
		
		
		//List<Review> reviewList =book.getReviews();
	
		//reviewList.add(newReview);
		
		int id= generateReviewKey();
		//int i =reviewList.size() -1 ;
		
		newReview.setReviewId(id);
		  
		book.getReviews().add(newReview);
		bookInMemoryMap.put(isbn, book);
		
		//reviewList.get(reviewList.size()).setReviewId(id);
		reviewInMemoryMap.put(isbn, newReview);
		
		return newReview;
	}

	
	
	/** 
	 * @see edu.sjsu.cmpe.library.repository.BookRepositoryInterface#getReview(java.lang.Long, int)
	 */
	@Override
	public Review viewReview(Long isbn, int reviewId ) {
		List<Review> reviewList= getBookByISBN(isbn).getReviews();

		for(int i=0;i<reviewList.size();i++)
		{
			if (reviewList.get(i).getReviewId() == reviewId)
				return reviewList.get(i);
		}
		return null;
	}


	/* (non-Javadoc)
	 * @see edu.sjsu.cmpe.library.repository.BookRepositoryInterface#viewAllReview(java.lang.Long)
	 */
	@Override
	public List<Review> viewAllReview(Long isbn) {
		
		List<Review> reviewList = getBookByISBN(isbn).getReviews();
		return reviewList;
	}


	/**
	 * Delete the book based on ISBN
	 */
	@Override
	public void deleteBook(Long isbn) {
		// Delete the book from the bookInMemoryMap
		authorInMemoryMap.remove(isbn);
		reviewInMemoryMap.remove(isbn);
		bookInMemoryMap.remove(isbn);
	}


	@Override
	public Author viewAuthor(Long isbn, int authorId) {
		
		List<Author> authorList = bookInMemoryMap.get(isbn).getAuthors();
		
		for(int i=0; authorList!=null && i < authorList.size(); i++) {
			if(authorList.get(i).getAuthorId() == authorId){
				return authorList.get(i);
			}
		}
		
		
		return null;
	}


	@Override
	public List<Author> viewAllAuthors(Long isbn) {
		List<Author> authorList = getBookByISBN(isbn).getAuthors();
		return authorList;
	}



	

}
