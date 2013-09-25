/**
 * 
 */
package edu.sjsu.cmpe.library.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Author;

/**
 * @author snehakulkarni
 *
 */

@JsonPropertyOrder(alphabetic = true)
public class AuthorDto extends LinksDto
{
	 private List<Author> authorList = new ArrayList<Author>();

	    /**
	     * @param author
	     */
	    public AuthorDto(Author author) {
		super();
		this.authorList.add(author);
	    }
         
	    public AuthorDto() {
	    	super();
	    }
		/**
		 * @return the authorList
		 */
		public List<Author> getAuthorList() {
			return authorList;
		}

		/**
		 * @param authorList the authorList to set
		 */
		public void setAuthorList(List<Author> authorList) {
			this.authorList = authorList;
		}
  
		public void addAuthor(Author author){
			this.authorList.add(author);
		}
	   
}
