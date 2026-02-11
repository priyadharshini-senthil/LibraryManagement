package com.kce.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.kce.book.bean.AuthorBean;
import com.kce.book.util.DBUtil;

public class AuthorDao {

    public AuthorBean getAuthor(int authorCode) {
        AuthorBean authorBean = null;
        String sql = "SELECT Author_code, Author_name, Contact_no FROM Author_Tbl WHERE Author_code = ?";

        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, authorCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    authorBean = new AuthorBean();
                    authorBean.setAuthorCode(rs.getInt("Author_code"));
                    authorBean.setAuthorName(rs.getString("Author_name"));
                    authorBean.setContactNo(rs.getLong("Contact_no"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authorBean;
    }

    public AuthorBean getAuthor(String authorName) {
        AuthorBean authorBean = null;
        String sql = "SELECT Author_code, Author_name, Contact_no FROM Author_Tbl WHERE Author_name = ?";

        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, authorName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    authorBean = new AuthorBean();
                    authorBean.setAuthorCode(rs.getInt("Author_code"));
                    authorBean.setAuthorName(rs.getString("Author_name"));
                    authorBean.setContactNo(rs.getLong("Contact_no"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authorBean;
    }
}
