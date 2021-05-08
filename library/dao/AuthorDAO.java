package library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import library.model.Author;

public class AuthorDAO extends BaseDAO<Author> {

	public AuthorDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public Integer insertAuthor(String name) throws SQLException {
		return update("INSERT INTO tbl_author (authorName) VALUES (?)", new Object[] {name});
	}
	
	public Author readAuthorByName(String name) throws SQLException {
		 List<Author> aList = read("SELECT * from tbl_author WHERE name = ?", new Object[] {name});
		 if (aList.isEmpty()) return null;
		return aList.get(0);
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<>();
		// Might need to change this logic to 
		// if (!rs.isBeforFirst) {...} else { do {...} while(rs.next()) }
		while(rs.next()) {
			Author author = new Author();
			author.setAuthorId(rs.getInt("authId"));
			author.setAuthorName(rs.getString("name"));
			authors.add(author);
		}
		return authors;
	}

}
