package com.kce.book.service;

import com.kce.book.bean.AuthorBean;
import com.kce.book.bean.BookBean;
import com.kce.book.dao.AuthorDao;
import com.kce.book.dao.BookDao;

public class Administrator {

    public String addBook(BookBean bookBean) {
        // ✅ MINIMAL VALIDATION (only required fields)
        if (bookBean == null
                || bookBean.getIsbn() == null || bookBean.getIsbn().trim().isEmpty()
                || bookBean.getBookName() == null || bookBean.getBookName().trim().isEmpty()
                || bookBean.getBookType() != 'G' && bookBean.getBookType() != 'T'
                || bookBean.getCost() <= 0) {
            return "INVALID";
        }

        // ✅ Get AUTHOR CODE from DB using author name (CRITICAL FIX)
        AuthorDao authorDao = new AuthorDao();
        String authorName = bookBean.getAuthor().getAuthorName();
        if (authorName == null || authorName.trim().isEmpty()) {
            return "INVALID";
        }

        // Lookup author code from database
        AuthorBean authorBean = authorDao.getAuthor(authorName.trim());
        if (authorBean == null) {
            return "INVALID"; // Author not found in DB
        }
        bookBean.setAuthor(authorBean); // Set proper AuthorBean with code

        BookDao bookDao = new BookDao();
        int result = bookDao.createBook(bookBean);

        if (result == 1) {
            return "SUCCESS";
        } else {
            return "FAILURE";
        }
    }

    public BookBean viewBook(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return null;
        }
        BookDao bookDao = new BookDao();
        return bookDao.fetchBoo.k(isbn);
    }
}
