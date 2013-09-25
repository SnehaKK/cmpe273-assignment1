/**
 * 
 */
package edu.sjsu.cmpe.library.domain;

/**
 * @author snehakulkarni
 *
 */
public class Author {
	private int authorId;
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
