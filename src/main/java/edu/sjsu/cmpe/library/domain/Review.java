/**
 * 
 */
package edu.sjsu.cmpe.library.domain;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author snehakulkarni
 *
 */
public class Review {
	private int reviewId;
	
	@NotEmpty
    @JsonProperty("rating")
	private int reviewRatings;
	@NotEmpty
    @JsonProperty("comment")
	private String reviewComment;
	
	
	/**
     * @return the reviewId
     */
    public int getReviewId() 
    {
    	return reviewId;
    }

    /**
     * @param reviewId
     *            the reviewId to set
     */
    public void setReviewId(int reviewId) 
    {
    	this.reviewId = reviewId;
    }
    
    /**
     * @return the reviewRatings
     */
    public int getReviewRatings() 
    {
    	return reviewRatings;
    }

    /**
     * @param reviewRatings
     *            the reviewRatings to set
     */
    public void setReviewRatings(int reviewRatings) 
    { 
    	
    	  if(reviewRatings > 5 || reviewRatings < 1) {
    		  this.reviewRatings = 5;
    	  } else {
    	   this.reviewRatings = reviewRatings;
    	  }
    	
       	
    }

    /**
     * @return the reviewComment
     */
    public String getReviewComment() 
    {
    	return reviewComment;
    }

    /**
     * @param reviewComment
     *            the reviewComment to set
     */
    public void setReviewComment(String reviewComment) 
    {
    	this.reviewComment = reviewComment;
    }
    
}
