package library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import library.model.Publisher;

public class PublisherDAO extends BaseDAO<Publisher>{

	public PublisherDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public Integer insertPublisher(Publisher pub) throws SQLException {
		return update("INSERT INTO tbl_publisher (publisherName, publisherAddress, publisherPhone) VALUES (?, ?, ?)", new Object[] {pub.getName(), pub.getAddress(), pub.getPhone()});
	}
	
	public void updatePublisher(Publisher pub) throws SQLException {
		update("UPDATE tbl_publisher SET publisherName = ?, publisherAddress = ?, publisherPhone WHERE publisherId = ?",
				new Object[] {pub.getName(), pub.getAddress(), pub.getPhone(), pub.getId()});
	}
	
	public void deletePublisher(Publisher pub) throws SQLException {
		update("DELETE FROM tbl_publisher WHERE publisherId = ?", new Object[] {pub.getId()});
	}
	
	public Publisher readPublisherByName(String name) throws SQLException {	
		List<Publisher> publishers = read("SELECT * FROM tbl_publisher WHERE publisherName = ?", new Object[] {name});
		if (publishers.isEmpty()) return null;
		return publishers.get(0);
	}
	
	public List<Publisher> readAllPublishers() throws SQLException {	
		return read("SELECT * FROM tbl_publisher", new Object[] {});
	}
	
	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<>();
		// Might need to change this logic to 
		// if (!rs.isBeforFirst) {...} else { do {...} while(rs.next()) }
		while(rs.next()) {
			Publisher pub = new Publisher();
			pub.setAddress(rs.getString("address"));
			pub.setId(rs.getInt("publisherId"));
			pub.setPhone(rs.getString("phone"));
			pub.setName(rs.getString("publisherName"));
			publishers.add(pub);
		}
		return publishers;
	}

}
