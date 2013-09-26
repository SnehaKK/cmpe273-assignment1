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
public class Author {
    @JsonProperty("id")
	private int authorId;
	@NotEmpty
    @JsonProperty("name")
	private String authorName;
	
	public int getAuthorId()
	{
		return this.authorId;
	}
	
	public void setAuthorId(int id)
	{
		this.authorId=id;
	}
	
	public String getAuthorName()
	{
		return this.authorName;
	}
	
	public void setAuthorName(String authorName)
	{
		this.authorName=authorName;
	}
	
}
