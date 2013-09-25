package edu.sjsu.cmpe.library.api.resources;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yammer.dropwizard.jersey.params.IntParam;
import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Errors;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.BooksDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.dto.ReviewDto;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;

@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
	/** bookRepository instance */
	private final BookRepositoryInterface bookRepository;

	/**
	 * BookResource constructor
	 * 
	 * @param bookRepository
	 *            a BookRepository instance
	 */
	public BookResource(BookRepositoryInterface bookRepository)
	{
		this.bookRepository = bookRepository;
	}

	@GET
	@Timed(name = "view-all-books")
	public BooksDto getBooks() 
	{
		Collection<Book> allBooks = bookRepository.getAllBooks();
		BooksDto allBooksResponse  = new BooksDto();
		Iterator<Book> bookIterator = allBooks.iterator();
		while(bookIterator.hasNext())
		{
			Book book = bookIterator.next();
			BookDto temp = new BookDto(book);
			temp.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(),"GET"));
			temp.addLink(new LinkDto("update-book",
					"/books/" + book.getIsbn(), "POST"));
			allBooksResponse.addBooks(temp);
		}

		return allBooksResponse;
	}

	@GET
	@Path("/{isbn}")
	@Timed(name = "view-book")
	public Response getBookByIsbn(@PathParam("isbn") LongParam isbn)
	{
		Book book = bookRepository.getBookByISBN(isbn.get());
	
		BookDto bookResponse = new BookDto(book);
		bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(),"GET"));
		bookResponse.addLink(new LinkDto("update-book","/books/" + book.getIsbn(), "PUT"));
		bookResponse.addLink(new LinkDto("delete-book","/books/" + book.getIsbn(), "DELETE"));
		bookResponse.addLink(new LinkDto("create-reviews","/books/" + book.getIsbn()+"/reviews", "POST"));
		//The next line should be displayed if there are reviews.
		if(book.getReviews().size() > 0) {
		bookResponse.addLink(new LinkDto("update-reviews","/books/" + book.getIsbn()+"/reviews", "GET"));
		}
		// add more links
      
		return Response.status(201).entity(bookResponse).build();
	}
	
	@DELETE
	@Path("/{isbn}")
	@Timed(name = "delete-book")
	public Response deleteBookByIsbn(@PathParam("isbn") LongParam isbn) 
	{
	
		bookRepository.deleteBook(isbn.get());
		LinksDto links = new LinksDto();
		links.addLink(new LinkDto("create-book", "/books", "POST"));

		return Response.ok(links).build();
		
	}
	
	
	/*Update book method
	 * 
	 */
	
	@PUT
	@Path("/{isbn}")
	@Timed(name = "update-book")
	public Response updateBook(@PathParam("isbn") LongParam isbn, @QueryParam("status") String status)
	{
		Book book = null;
		//Update the status
		if(status!= null || status.equalsIgnoreCase("")) {
			 book = bookRepository.getBookByISBN(isbn.get());
			
			if(status.equalsIgnoreCase(Book.STATUS.available.toString())) {
			book.setBookStatus(Book.STATUS.available); }
			else if(status.equalsIgnoreCase(Book.STATUS.checkedOut.toString())) {
				book.setBookStatus(Book.STATUS.checkedOut); }    
			else if(status.equalsIgnoreCase(Book.STATUS.lost.toString())) {
				book.setBookStatus(Book.STATUS.lost); }
			else if(status.equalsIgnoreCase(Book.STATUS.inQueue.toString())) {
				book.setBookStatus(Book.STATUS.inQueue); }
			else{
				book.setBookStatus(Book.STATUS.available);
				//Return an error here
			}
		}
		if(book != null) {
			//Save the book back to bookRepository
			bookRepository.updateBook(book, isbn.get());
		}
		
		String location = "/books/" + isbn;
		
		// The response should contain only links. View update delete create-review
		LinksDto links = new LinksDto();
		links.addLink(new LinkDto("view-book",location,"GET"));
		links.addLink(new LinkDto("update-book", location, "PUT"));
		links.addLink(new LinkDto("delete-book", location, "DELETE"));
		links.addLink(new LinkDto("create-review", location+"/reviews", "POST"));
		//The next line should be displayed if there are reviews.
		if(book.getReviews().size() > 0) {
		links.addLink(new LinkDto("view-all-reviews",location+"/reviews", "GET"));
		}
		
		return Response.ok(links).build();
	}

	@POST
	@Timed(name = "create-book")
	public Response createBook(Book request) 
	{
		// Store the new book in the BookRepository so that we can retrieve it.
		Book savedBook = bookRepository.saveBook(request);

		String location = "/books/" + savedBook.getIsbn();
		//BookDto bookResponse = new BookDto(savedBook);
		//bookResponse.addLink(new LinkDto("view-book", location, "GET"));
		//bookResponse.addLink(new LinkDto("update-book", location, "POST"));
		
		// The response should contain only links. View update delete create-review
		LinksDto links = new LinksDto();
		links.addLink(new LinkDto("view-book",location,"GET"));
		links.addLink(new LinkDto("update-book", location, "PUT"));
		links.addLink(new LinkDto("delete-book", location, "DELETE"));
		links.addLink(new LinkDto("create-review", location+"/reviews", "POST"));
		
		return Response.ok(links).build();

		//return Response.status(201).entity(bookResponse).build();
	}
	
	@POST
	@Path("/{isbn}/reviews")
	@Timed(name = "create-review")
	public Response createReview(@PathParam("isbn") LongParam isbn, Review request) 
	{
		// Get book with the isbn.
		Book book= bookRepository.getBookByISBN(isbn.get());
		// Store the new Review in the BookRepository so that we can retrieve it.
		Review savedReview = bookRepository.saveReview(isbn.get(), request);

		String location = "/books/" + book.getIsbn() + "/reviews/" + savedReview.getReviewId();
		
		LinksDto links = new LinksDto();
		links.addLink(new LinkDto("view-review",location,"GET"));

		return Response.status(201).entity(links).build();
	}
	
	
	
	@GET
	@Path("/{isbn}/reviews/{reviewId}")
	@Timed(name = "view-review")
	public Response viewReview(@PathParam("isbn") LongParam isbn,@PathParam("reviewId") IntParam reviewId)
	{
		// Get book with the isbn.
				//Book book= bookRepository.getBookByISBN(isbn.get());
				
				Review review = bookRepository.viewReview(isbn.get(), reviewId.get());
				ReviewDto response = new ReviewDto(review);
				response.addLink(new LinkDto("view-review", "/books/" + isbn.get()+ "/reviews/"+review.getReviewId(),"GET"));
				return Response.status(200).entity(response).build();
				
	}
	
	@GET
	@Path("/{isbn}/reviews")
	@Timed(name = "view-all-reviews")
	public Response viewAllReview(@PathParam("isbn") LongParam isbn)
	{
		// Get book with the isbn.
				//Book book= bookRepository.getBookByISBN(isbn.get());
				
				List<Review> reviewList = bookRepository.viewAllReview(isbn.get());
				ReviewDto response = new ReviewDto();
				for(int i=0; reviewList!=null && i<reviewList.size(); i++){
					response.addReview(reviewList.get(i));
				}
				
				
				//response.addLink(new LinkDto("view-review", "/books/" + isbn.get()+ "/reviews/"+review.getReviewId(),"GET"));
				return Response.status(200).entity(response).build();
				
	}
	
	
	@GET
	@Path("/{isbn}/authors/{authorId}")
	@Timed(name = "view-author")
	public Response viewAuthor(@PathParam("isbn") LongParam isbn,@PathParam("authorId") IntParam authorId)
	{
		        Author author = bookRepository.viewAuthor(isbn.get(), authorId.get());
				
		
				AuthorDto response = new AuthorDto(author);
				response.addLink(new LinkDto("view-review", "/books/" + isbn.get()+ "/authors/"+author.getAuthorId(),"GET"));
				return Response.status(200).entity(response).build();
				
	}
	
	@GET
	@Path("/{isbn}/authors")
	@Timed(name = "view-all-authors")
	public Response viewAllAuthors(@PathParam("isbn") LongParam isbn)
	{
		
				List<Author> authorList = bookRepository.viewAllAuthors(isbn.get());
				AuthorDto response = new AuthorDto();
				for(int i=0; authorList!=null && i<authorList.size(); i++){
					response.addAuthor(authorList.get(i));
				}
				
				
				//response.addLink(new LinkDto("view-review", "/books/" + isbn.get()+ "/reviews/"+review.getReviewId(),"GET"));
				return Response.status(200).entity(response).build();
				
	}
}


