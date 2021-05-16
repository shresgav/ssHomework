package library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import library.model.Borrower;

public class BorrowerDAO extends BaseDAO<Borrower> {

	public BorrowerDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public void updateBorrower(Borrower bor) throws SQLException {
		update("UPDATE tbl_borrower SET name = ?, address = ?, phone = ? WHERE cardNo = ?", new Object[] {bor.getName(), bor.getAddress(),bor.getPhone(), bor.getCardNo()});
	}
	
	public void insertBorrower(Borrower bor) throws SQLException {
		update("INSERT INTO tbl_borrower VALUES (?, ?, ?, ?)", new Object[] {bor.getCardNo(),bor.getName(), bor.getAddress(),bor.getPhone()});
	}
	
	public void deleteBorrower(Borrower bor) throws SQLException{
		update("DELETE FROM tbl_borrower WHERE cardNo = ?", new Object[] {bor.getCardNo()});
	}
	
	public Borrower readBorrowerByCardNo(Integer cardNo) throws SQLException {
		List<Borrower> blist = read("SELECT * FROM tbl_borrower WHERE cardNo = ?", new Object[] {cardNo});
		if (blist.isEmpty()) {
			return null;
		}
		return blist.get(0);
	}
	
	public List<Borrower> readAllBorrowers() throws SQLException {	
		return read("SELECT * FROM tbl_borrower", new Object[] {});
	}
	
	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower> borrowers = new ArrayList<>();
		while(rs.next()) {
			Borrower b = new Borrower();
			b.setName(rs.getString("name"));
			b.setCardNo(rs.getInt("cardNo"));
			b.setAddress(rs.getString("address"));
			b.setPhone(rs.getString("phone"));
			borrowers.add(b);
		}
		return borrowers;
	}

}
