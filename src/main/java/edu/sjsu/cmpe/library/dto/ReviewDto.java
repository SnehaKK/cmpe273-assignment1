/**
 * 
 */
package edu.sjsu.cmpe.library.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Review;



/**
 * @author snehakulkarni
 *
 */

@JsonPropertyOrder(alphabetic = true)
public class ReviewDto extends LinksDto 
{

	 private List<Review> review = new ArrayList<Review>();
       
	 
	  public ReviewDto() {
		  super();
	  }
	 
	   /**
	     * @param review
	     */
	    public ReviewDto(Review reviewValue) {
		super();
		this.review.add(reviewValue);
	    }

		/**
		 * @return the review
		 */
		public List<Review> getReview() {
			return review;
		}

		/**
		 * @param review the review to set
		 */
		public void setReview(List<Review> review) {
			this.review = review;
		}
		
		/**
		 * @param review
		 */
		
		public void addReview(Review reviewValue) {
			this.review.add(reviewValue);
		}

	    
}
