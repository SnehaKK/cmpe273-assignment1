package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;


public class Book {
    private long isbn;
    @NotEmpty
    private String title;
    @NotEmpty
    private String publicationDate; //(Required field)
    private String language; //(Optional field)
    private int numberOfPages; //(Optional field)
    public enum STATUS { available , checkedOut , inQueue, lost}; 
    @NotEmpty
    private STATUS bookStatus= STATUS.available; // default value available;
    private List<Author> authors =new ArrayList<Author>();
    private List<Review> reviews = new ArrayList<Review>();
    
   
	/**
     * @return the isbn
     */
    public long getIsbn() 
    {
    	return isbn;
    }

    /**
     * @param isbn
     *            the isbn to set
     */
    public void setIsbn(long isbn) 
    {
    	this.isbn = isbn;
    }

    /**
     * @return the title
     */
    public String getTitle()
    {
    	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title)
    {
    	this.title = title;
    }
    
    /**
     * @return the publicationDate
     */
    public String getPublicationDate() 
    {
    	return publicationDate;
    }

    /**
     * @param publicationDate
     *            the publicationDate to set
     */
    public void setPublicationDate(String publicationDate) 
    {
    	this.publicationDate = publicationDate;
    }
    
    /**
     * @return the language
     */
    public String getLanguage() 
    {
    	return language;
    }

    /**
     * @param language
     *            the language to set
     */
    public void setLanguage(String langugae)
    {
    	this.language = langugae;
    }
    
    /**
     * @return the numberOfPages
     */
    public int getNumberOfPages() 
    {
    	return numberOfPages;
    }

    /**
     * @param numberOfPages
     *            the numberOfPages to set
     */
    public void setNumberOfPages(int numberOfPages) 
    {
    	this.numberOfPages = numberOfPages;
    }
    
    /**
     * @return the status
     */
    public STATUS getBookStatus() 
    {
    	return bookStatus;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setBookStatus(STATUS bstatus) 
    {
    	this.bookStatus = bstatus;
//    	switch(bstatus)
//    	  {
//        	case available:
//        		this.bookStatus= status.available;break;
//    		  
//        	case checkedOut:
//        		this.bookStatus= status.checkedOut;break;
//    		  
//        	case inQueue:
//        		this.bookStatus= status.inQueue;break;
//    		  
//        	case lost:
//        		this.bookStatus= status.lost;break;  
//        	default:
//        			this.bookStatus= status.available;break;
//    	  } 
    }
    
    /**
     * @return the List of Authors
     */
    public List<Author> getAuthors() {
		return authors;
	}

    /**
     * @param List of Authors
     *            the List of Authors to set
     */
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	 
    /**
     * @return the List of Authors
     */
	public List<Review> getReviews() {
		return reviews;
	}

	 /**
     * @param List of Reviews
     *            the List of Reviews to set
     */
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	
    
}
