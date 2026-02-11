package com.kce.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kce.book.bean.AuthorBean;
import com.kce.book.bean.BookBean;
import com.kce.book.util.DBUtil;

public class BookDao {

    public int createBook(BookBean bookBean) {
        int result = 0;
        String sql = "INSERT INTO Book_Tbl (ISBN, Book_title, Book_type, Author_code, Book_cost) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, bookBean.getIsbn());
            ps.setString(2, bookBean.getBookName());
            ps.setString(3, String.valueOf(bookBean.getBookType()));
            ps.setInt(4, bookBean.getAuthor().getAuthorCode());
            ps.setFloat(5, bookBean.getCost());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = 0;
        } catch (Exception e) {
            e.printStackTrace();
            result = 0;
        }
        return result;
    }

    public BookBean fetchBook(String isbn) {
        BookBean bookBean = null;
        String sql = "SELECT ISBN, Book_title, Author_code, Book_type, Book_cost FROM Book_Tbl WHERE ISBN = ?";

        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, isbn);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    bookBean = new BookBean();
                    bookBean.setIsbn(rs.getString("ISBN"));
                    bookBean.setBookName(rs.getString("Book_title"));
                    int authorCode = rs.getInt("Author_code");
                    AuthorDao authorDao = new AuthorDao();
                    AuthorBean authorBean = authorDao.getAuthor(authorCode);
                    bookBean.setAuthor(authorBean);
                    bookBean.setBookType(rs.getString("Book_type").charAt(0));
                    bookBean.setCost(rs.getFloat("Book_cost"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookBean;
    }
}
