package library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import library.model.LibraryBranch;

public class LibraryBranchDAO extends BaseDAO<LibraryBranch> {

	public LibraryBranchDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void updateBranch(LibraryBranch lb) throws SQLException {
		update("UPDATE tbl_library_branch SET branchName = ?, branchAddress = ? WHERE branchId = ?",
				new Object[] { lb.getBranchName(), lb.getBranchAddress(), lb.getBranchId() });
	}

	public void insertBranch(LibraryBranch lb) throws SQLException {
		update("INSERT INTO tbl_library_branch (branchName, branchAddress) VALUES (?, ?)",
				new Object[] { lb.getBranchName(), lb.getBranchAddress() });
	}

	public void deleteBranch(LibraryBranch lb) throws SQLException {
		update("DELETE FROM tbl_library_branch WHERE branchId = ?", new Object[] { lb.getBranchId() });
	}

	public LibraryBranch readLibraryByName(String name) throws SQLException {
		List<LibraryBranch> lb = read("SELECT * from tbl_library_branch WHERE branchName = ?", new Object[] { name });
		if (lb.isEmpty())
			return null;
		return lb.get(0);
	}

	public LibraryBranch readLibraryByCode(Integer code) throws SQLException {
		List<LibraryBranch> lb = read("SELECT * from tbl_library_branch WHERE branchId = ?", new Object[] { code });
		return lb.get(0);
	}

	public List<LibraryBranch> readAllLibrary() throws SQLException {
		return read("SELECT * FROM tbl_library_branch", new Object[] {});
	}

	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException {
		List<LibraryBranch> libraries = new ArrayList<>();
		while (rs.next()) {
			LibraryBranch lb = new LibraryBranch();
			lb.setBranchId(rs.getInt("branchId"));
			lb.setBranchName(rs.getString("branchName"));
			lb.setBranchAddress(rs.getString("branchAddress"));
			libraries.add(lb);
		}
		return libraries;
	}

}
