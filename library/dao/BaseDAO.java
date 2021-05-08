package library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// Is Hibernate something we'll be looking into later on? 
// I'm curious as to the specific differences between what I'm doing here and an ORM

public abstract class BaseDAO<T> {
	protected Connection conn = null;
	
	public BaseDAO(Connection conn) {
		this.conn = conn;
	}
	
	public Integer update(String sql, Object[] args) throws SQLException {
		PreparedStatement statement =
				conn.prepareStatement(sql);
		int count = 1;
		for (Object o: args) {
			statement.setObject(count, o);
			count++;
		}
		System.out.println(statement);
		statement.executeUpdate();
	    try (ResultSet keys = statement.getGeneratedKeys()) {
	    	if (keys.next()) return keys.getInt(1);
	    }
		return null;
	}
	
	public List<T> read(String sql, Object[] args) throws SQLException {
		PreparedStatement statement =
				conn.prepareStatement(sql);
		int count = 1;
		for (Object o: args) {
			statement.setObject(count, o);
			count++;
		}
		ResultSet rs = statement.executeQuery();
		return extractData(rs);
	}
	
	abstract public List<T> extractData(ResultSet rs) throws SQLException;
}
